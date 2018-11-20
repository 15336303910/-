package interfaces.irmsInterface.utils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Field; 
import java.lang.reflect.InvocationTargetException; 
import java.lang.reflect.Method; 
import java.lang.reflect.ParameterizedType; 
import java.text.SimpleDateFormat; 
import java.util.ArrayList; 
import java.util.Date; 
import java.util.HashMap;
import java.util.Iterator; 
import java.util.List; 
import java.util.Map;
import java.util.Set; 

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.dom4j.Attribute; 
import org.dom4j.Document; 
import org.dom4j.DocumentHelper; 
import org.dom4j.Element; 
import org.dom4j.dom.DOMDocument; 
import org.dom4j.dom.DOMElement; 
import org.dom4j.io.SAXReader;

import edu.emory.mathcs.backport.java.util.LinkedList;


/** 
 * xml工具类,依赖于dom4j解析
 * 
 * @author dengbin 
 * 
 */ 
public class XmlUtil { 

 protected static final Logger logger = Logger.getLogger(XmlUtil.class); 
 private  org.w3c.dom.Document document;//xml文档对象
 private  File f;//文件
 private  Class cl;//类

 /*========================================================解析XML==================================================================*/ 
/**
 * 得到xml的document对象
 * 创建一个util
 */
 public XmlUtil(String name,Class c){
	 try{
		 f= new File(name);
		 if(!f.exists()){
			 //创建一个新页面
			 f.createNewFile();
			 String ss=c.getSimpleName().toLowerCase()+"s";
			 PrintWriter pw=new PrintWriter(f);
			 pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			 pw.println("<"+ss+">");
			 pw.println("</"+ss+">");
			 pw.flush();
			 pw.close();
		 }
		 DocumentBuilderFactory df=DocumentBuilderFactory.newInstance();
		 DocumentBuilder db=df.newDocumentBuilder();
		 document=db.parse(f);
		 cl = c;
	 }catch (Exception e) {
		e.printStackTrace();
	}
 }

 /** 
 * xml字符串转对象 
 * @param xmlstr 
 * @param clazz 目标对象类型 
 * @return 
 */ 
 public static Object stringToObject(String xmlstr,Class<?> clazz){ 
 Document doc = stringToXml(xmlstr); 
     try{ 
            Element root = doc.getRootElement(); 
            @SuppressWarnings("unchecked") 
            Iterator<Element> iters = root.elementIterator(); 
            return findObject(iters,clazz); 
            
     }catch(Exception e){ 
         e.printStackTrace(); 
     } 
     return null; 
 } 

 /** 
 * xml字符串转集合 
 * @param xmlstr 
 * @param clazz list中对象类型 
 * @return 
 */ 
 public static List<Object> stringToList(String xmlstr,Class<?> clazz){ 
	 Document doc = stringToXml(xmlstr); 
	 try{ 
            Element root = doc.getRootElement(); 
            @SuppressWarnings("unchecked") 
            Iterator<Element> iters = root.elementIterator(); 
            return findList(iters,clazz); 
     }catch(Exception e){ 
         e.printStackTrace(); 
     } 
     return null; 
 } 

 /** 
 * 字符串转换成Document对象 
 * 
 * @param xmlStr 
 *            xml字符串 
 * @return Document 
 */ 
 protected static Document stringToXml(String xmlStr) { 
	 try { 
		 if (xmlStr == null || "".equals(xmlStr)) 
			 return null; 
		 else { 
			 return DocumentHelper.parseText(xmlStr); 
		 } 
	 } catch (Exception e) { 
		 logger.error(e.getMessage()); 
		 return null; 
	 } 
 } 

 
 /**
  * 将xml解析成map
  * @param xml
  * @return
  */
 public static Map<String, String> getMapXml(String xml){
	 Map<String, String> map = new HashMap<String, String>();
	 try{
			SAXReader reader = new SAXReader();
			Document document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			Element root = document.getRootElement();
			Iterator it = root.elementIterator();
			while (it.hasNext()) {
				Element element = (Element) it.next();
				Iterator attrIt = element.attributeIterator();
				while (attrIt.hasNext()) {
					Attribute a = (Attribute) attrIt.next();
					map.put(a.getName(), a.getValue());
				}
			}
	 }catch(Exception e){
		 e.printStackTrace();
	 }
	 return map;
 }
 
 
 /**
  * 解析端口数据
  * @param xml
  * @return
  */
 public static List<Map<String, String>> getPointList(String xml){
	 List<Map<String, String>> resuList = new ArrayList<Map<String,String>>();
	 try{
		 SAXReader reader = new SAXReader();            
		 Document document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));;
		 Element root = document.getRootElement();
		 Element odm = root.element("odm");
		 Element terminals = odm.element("terminals");
		 Iterator term = terminals.elementIterator();
		 while(term.hasNext()){
		   Map<String, String> map = new HashMap<String, String>();
		   Element terminal = (Element) term.next();
		   Iterator attrIt = terminal.attributeIterator();
		   while(attrIt.hasNext()){
			   Attribute a = (Attribute) attrIt.next();
		    	if(a.getName().equals("tooltip")){
		    		map.put("name", a.getValue());
		    	}
		    	if(a.getName().equals("id")){
		    		map.put("resNum", a.getValue());
		    	}
		    	if(a.getName().equals("row")){
		    		if(a.getValue().length() ==1){
		    			map.put("row", "0"+a.getValue());
		    		}else{
		    			map.put("row", a.getValue());
		    		}
		    	}
		    	if(a.getName().equals("col")){
		    		if(a.getValue().length() ==1){
		    			map.put("col", "0"+a.getValue());
		    		}else{
		    			map.put("col", a.getValue());
		    		}
		    	}
		    }
		   resuList.add(map);
		 }
	 }catch(Exception e){
		 e.printStackTrace();
	 }
	 return resuList;
 }
 
 /**
  * 得到ODM的实例id
  * @param xml
  * @return
  */
 public static String getOdmId(String xml){
	 String odmId = "";
	 try{
		 SAXReader reader = new SAXReader();            
		 Document document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		 Element root = document.getRootElement();
		 Element odm = root.element("odm");
		 Iterator odmAtt = odm.attributeIterator();
		 while(odmAtt.hasNext()){
		   while(odmAtt.hasNext()){
			   Attribute oat = (Attribute) odmAtt.next();
			   if(oat.getName().equals("id")){
				   odmId = oat.getValue();
			   }
		   }
		 }
	 }catch(Exception e){
		 e.printStackTrace();
	 }
	 return odmId;
 }
 
 /**
  * 得到新增资源id
  * @param xml
  * @return
  */
 public static String getAddRes(String xml,String appId){
	 String resourceId = "";
	 try{
		SAXReader reader = new SAXReader();            
		Document document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		Element root = document.getRootElement();
		Element ids = root.element("idsMapping");
		Element id = ids.element("idMapping");
		for(Iterator i = ids.elementIterator("idMapping");i.hasNext();){
			Element foo = (Element) i.next(); 
			String oldId = foo.attributeValue("oldid");
			if(oldId.equals("new-"+appId)){
				resourceId =foo.attributeValue("newid");
			}
		}
	 }catch(Exception e){
		 e.printStackTrace();
	 }
	 return resourceId;
 }
 
 /**
  * 得到错误的信息
  * @param xml
  * @return
  */
 public static String getErrorId(String xml) {
	 String resId = "";
	 try {
		SAXReader reader = new SAXReader();            
		Document document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		Element root = document.getRootElement();
		Element errors = root.element("errors");
		Element error = errors.element("error");
		if(xml.contains("info")) {
			String info = error.attributeValue("info");
			if(info.contains("已存在") || info.contains("两个承载点之间只能建一条承载段")) {
				resId = error.attributeValue("id");
			}else {
				resId = "error_"+info;
			}
		}else {
			resId ="error_数据解析异常";
		}
	 }catch(Exception e) {
		 e.printStackTrace();
	 }
	 return resId;
 }
 
 /**
  * 得到数据字符串
  * @param xml
  * @return
  */
	public static List<Map<String, String>> getAddResList(String xml) {
		List<Map<String, String>> list = new LinkedList();
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new ByteArrayInputStream(xml
					.getBytes("UTF-8")));
			Element root = document.getRootElement();
			Element ids = root.element("idsMapping");
			Element id = ids.element("idMapping");
			for (Iterator i = ids.elementIterator("idMapping"); i.hasNext();) {
				Element foo = (Element) i.next();
				String oldId = foo.attributeValue("oldid");
				String resourceId = foo.attributeValue("newid");
				Map<String, String> map= new HashMap<String, String>();
				map.put(oldId, resourceId);
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
 
 /** 
 * 解析xml，递归需找子集 
 * @param iters 
 * @param clazz 
 * @return 
 */ 
 protected static Object findObject(Iterator<Element> iters,Class<?> clazz) { 
	 Attribute  attr=null; 
	 try { 
		 Object object = clazz.newInstance(); 
		 while (iters.hasNext()) { 
			 Element item = (Element) iters.next(); 
			 Field[] fields = clazz.getDeclaredFields(); 
			 for (Field field : fields) { 
				 if(isSimpleType(field.getType())){ 
					 attr = item.attribute(field.getName()); 
					 if(attr==null) 
						 continue; 
					 setter(object, firstLetterToUpper(field.getName()), 
							 attr.getValue(), 
							 field.getType()); 
				 }else if(List.class == field.getType()){ 
                        ParameterizedType pt = (ParameterizedType) field.getGenericType();   
                        Class<?> childzz = (Class<?>)pt.getActualTypeArguments()[0]; 
                     @SuppressWarnings("unchecked") 
                     Iterator<Element> children = item.elementIterator(); 
                     Object o =findList(children,childzz); 
                     setter(object, firstLetterToUpper(field.getName()),o,field.getType()); 
                 }else{ 
                     @SuppressWarnings("unchecked") 
                     Iterator<Element> children = item.elementIterator(); 
                     System.out.println(field.getType().getCanonicalName()); 
                     Object o =findObject(children,field.getType()); 
                     setter(object, firstLetterToUpper(field.getName()),o,field.getType()); 
                 } 
			 } 
			 return object; 
		 } 

	 } catch (Exception e) { 
		 logger.error(e.getMessage()); 
	 } 
	 return null; 
 	} 

 /** 
 * 解析xml，递归需找子集 
 * @param iters 
 * @param clazz 
 * @return 
 */ 
 protected static List<Object> findList(Iterator<Element> iters,Class<?> clazz) { 
	 List<Object> list = new ArrayList<Object>(); 
	 try { 
		 while (iters.hasNext()) { 
			 Object object = clazz.newInstance(); 
			 Element item = (Element) iters.next(); 
			 Field[] fields = clazz.getDeclaredFields(); 
			 for (Field field : fields) { 
				 if(isSimpleType(field.getType())){ 
					 setter(object, firstLetterToUpper(field.getName()), 
							 item.attribute(field.getName()).getValue(), 
							 field.getType()); 
				 }else if(List.class == field.getType()){ 
                     ParameterizedType pt = (ParameterizedType) field.getGenericType();   
                     Class<?> childzz = (Class<?>)pt.getActualTypeArguments()[0]; 
                     @SuppressWarnings("unchecked") 
                     Iterator<Element> children = item.elementIterator(); 
                     Object o =findList(children,childzz); 
                     setter(object, firstLetterToUpper(field.getName()),o,field.getType()); 
                 }else{ 
                     @SuppressWarnings("unchecked") 
                     Iterator<Element> children = item.elementIterator(); 
                     Object o =findObject(children,field.getType()); 
                     setter(object, firstLetterToUpper(field.getName()),o,field.getType()); 
                 } 
			 } 
			 list.add(object); 
		 } 
		 return list; 
		 
	 } catch (Exception e) { 
		 logger.error(e.getMessage()); 
	 } 
	 return null; 
 	} 





/*=============================================================封装XML================================================================*/ 




 /** 
 * 对象转xml字符串 
 * @param obj 
 * @return 
 */ 
 public static String objectToString(Object obj){ 
	 Document doc = ObjectToXml(obj); 
	 return XmlToString(doc); 
 } 

 /** 
 * Document对象转换成字符串 
 * 
 * @param doc 
 *            Document对象 
 * @return xml字符串 
 */ 
 protected static String XmlToString(Document doc) { 
	 if (doc == null) 
		 return null; 
	 else 
		 return doc.asXML(); 
 } 




 /** 
 * 对象转换成Document,spring已包含类似功能 
 * 
 * @param object 
 *            对象 
 * @param scheme 
 *            模式 
 * @param nodeType 
 *            节点名称类型 
 * @return Document 
 */ 
 protected static Document ObjectToXml(Object object) { 
	 if (object == null) 
		 return null; 
	 else { 
		 Document doc = new DOMDocument(); 
		 if (object instanceof List) { 
			 for (Object obj : (List<?>) object) { 
				 findObject(doc, obj); 
			 } 
		 } else if (object instanceof Set) { 
			 for (Object obj : (Set<?>) object) { 
				 findObject(doc, obj); 
			 } 
		 } else { 
			 findObject(doc, object); 
		 } 
		 return doc; 
	 } 
 } 


 //protected static void 
 /** 
 * 组装xml 
 * @param root 
 * @param object 
 */ 
 protected static void findObject(Document root, Object object) { 
	 Class<?> clazz = object.getClass(); 
	 Field[] fields = clazz.getDeclaredFields(); 
	 Element element=null; 
	 element= new DOMElement(clazz.getSimpleName()); 
	 for (Field field : fields) { 
		 parseChildren(field, element, object); 
	 } 
	 root.add(element); 
 } 

 @SuppressWarnings("deprecation") 
 protected static void parseChildren(Field field, Element element, Object object) { 
	 Class<?> type = field.getType(); 
	 if (isSimpleType(type)) { 
		 try{ 
			 element.setAttributeValue( 
					 field.getName(), 
					 toString(getter(object, firstLetterToUpper(field.getName())))); 
 	}catch(Exception e){} 
	 } else { 
		 try { 
			 Object child = getter(object, 
					 firstLetterToUpper(field.getName())); 
			 if(child instanceof List){ 
				 for(Object obj : (List<?>)child){ 
					 Element chidElelment= new DOMElement(obj.getClass().getSimpleName()); 
					 Field[] fields = obj.getClass().getDeclaredFields(); 
					 for (Field chidField : fields) { 
						 parseChildren(chidField, chidElelment, obj); 
					 } 
					 element.add(chidElelment); 
				 } 

			 }else{ 
				 if (child != null) { 
					 Element chidElelment =null; 
					 chidElelment= new DOMElement(child.getClass().getSimpleName()); 
					 Field[] chidFields = child.getClass().getDeclaredFields(); 
					 for (Field chidField : chidFields) 
						 parseChildren(chidField, chidElelment, child); 
					 element.add(chidElelment); 
				 } 
			 } 

		 } catch (Exception e) { 
			 e.printStackTrace(); 
			 logger.error(e.getMessage()); 
		 } 
	 } 
 } 



 /*===============================================================公用方法================================================================*/ 

 /** 
 * 简单数据类型判断 
 * @param type 数据类型 
 * @return 
 */ 
 protected static boolean isSimpleType(Class<?> type){ 
	 if (type == String.class 
			 || type == int.class || type == Integer.class 
			 || type == double.class || type == Double.class 
			 || type == char.class || type == Character.class 
			 || type == long.class || type == Long.class 
			 || type == float.class || type == Float.class 
			 || type == byte.class || type == Byte.class 
			 || type == boolean.class || type == Boolean.class 
			 || type == short.class || type == Short.class 
			 || type==Date.class) { 
		 return true; 
	 }else{ 
		 return false; 
	 } 
 } 

 /** 
 * 调用对象的属性get方法 
 * 
 * @param obj 
 * @param att 
 * @return 
 * @throws NoSuchMethodException 
 * @throws SecurityException 
 * @throws InvocationTargetException 
 * @throws IllegalAccessException 
 * @throws IllegalArgumentException 
 */ 
 protected static Object getter(Object obj, String att) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException { 
	 Method method = obj.getClass().getMethod("get" + att); 
	 return method.invoke(obj); 
 } 

 /** 
 * 调用对象属性的set方法 
 * 
 * @param obj 
 * @param att 
 * @param value 
 * @param type 
 */ 
 protected static void setter(Object obj, String att, Object value, Class<?> type) { 
     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss"); 
     try { 
    	 Method method = obj.getClass().getMethod("set" + att, type); 
    	 if (type == String.class) 
    		 method.invoke(obj, toString(value)); 
    	 else if (type == Integer.class || type == int.class) 
    		 method.invoke(obj, toInteger(value)); 
    	 else if (type == double.class || type == Double.class) 
    		 method.invoke(obj, toDouble(value)); 
    	 else if(type == char.class || type == Character.class) 
    		 method.invoke(obj,toCharacter(value)); 
    	 else if(type == long.class || type == Long.class) 
    		 method.invoke(obj,toLong(value)); 
    	 else if(type == float.class || type == Float.class) 
    		 method.invoke(obj,toFloat(value)); 
    	 else if(type == byte.class || type == Byte.class) 
    		 method.invoke(obj,toByte(value)); 
    	 else if(type == boolean.class || type == Boolean.class) 
    		 method.invoke(obj,toBoolean(value)); 
    	 else if(type == short.class || type == Short.class) 
    		 method.invoke(obj,toShort(value)); 
    	 else if(type == java.util.Date.class){ 
    		 method.invoke(obj, df.parse(String.valueOf(value))); 
    	 } 
    	 else 
    		 method.invoke(obj,value); 
     } catch (Exception e) { 
    	 logger.error(e.getMessage()); 
     } 
 } 



 /** 
 * 首字母大写 
 * 
 * @param str 
 * @return 
 */ 
 protected static String firstLetterToUpper(String str) { 
	 char[] array = str.toCharArray(); 
	 array[0] -= 32; 
	 return String.valueOf(array); 
 } 

 /** 
 * 首字母小写 
 * 
 * @param str 
 * @return 
 */ 
 protected static String firstUpperToLetter(String str) { 
	 char[] array = str.toCharArray(); 
	 array[0] += 32; 
	 return String.valueOf(array); 
 } 

 /** 
 * 对象转换成字符串 
 * 
 * @param object 
 * @return 
 */ 
 protected static String toString(Object object) { 
	 if (object == null) 
		 return ""; 
	 else 
		 return object.toString(); 
 } 

 /** 
 * 对象转换成整形 
 * 
 * @param object 
 * @return 
 */ 
 protected static Integer toInteger(Object object) { 
	 String str = toString(object); 
	 if ("".equals(str)) 
		 return 0; 
	 else 
		 return Integer.parseInt(str); 
 } 

 /** 
 * 对象转换成double 
 * @param object 
 * @return 
 */ 
 protected static Double toDouble(Object object){ 
	 String str = toString(object); 
	 if("".equals(str)) 
		 return 0.0; 
	 else 
		 return Double.parseDouble(str); 
 } 

 /** 
 * 对象转换成float 
 * @param object 
 * @return 
 */ 
 protected static Float toFloat(Object object){ 
	 String str = toString(object); 
	 if("".equals(str)) 
		 return 0.0f; 
	 else 
		 return Float.parseFloat(str); 
 } 

 /** 
 * 对象转换成long 
 * @param object 
 * @return 
 */ 
 protected  static Long toLong(Object object){ 
	 String str = toString(object); 
	 if("".equals(str)) 
		 return 0l; 
	 else 
		 return Long.parseLong(str); 
 } 

 /** 
 * 对象转换成booean 
 * @param object 
 * @return 
 */ 
 protected static Boolean toBoolean(Object object){ 
	 String str = toString(object); 
	 if("".equals(str)) 
		 return true; 
	 else 
		 return Boolean.parseBoolean(str); 
 } 

 /** 
 * 对象转换成short 
 * @param object 
 * @return 
 */ 
 protected static Short toShort (Object object){ 
	 String str = toString(object); 
	 if("".equals(str)) 
		 return 0; 
	 else 
	return Short.parseShort(str); 
 } 

 /** 
 * 对象转换成byte 
 * @param object 
 * @return 
 */ 
 protected static Byte toByte(Object object){ 
	 String str = toString(object); 
	 if("".equals(str)) 
		 return 0; 
	 else 
		 return Byte.parseByte(str); 
 } 

 /** 
 * 对象转换成char 
 * @param object 
 * @return 
 */ 
 protected static Character toCharacter(Object object){ 
	 if(object==null) 
		 return '\u0beb'; 
	 else 
		 return (Character) object; 
 }

public Class getCl() {
	return cl;
}

public void setCl(Class cl) {
	this.cl = cl;
}



public org.w3c.dom.Document getDocument() {
	return document;
}
public void setDocument(org.w3c.dom.Document document) {
	this.document = document;
}
public File getF() {
	return f;
}

public void setF(File f) {
	this.f = f;
} 


}