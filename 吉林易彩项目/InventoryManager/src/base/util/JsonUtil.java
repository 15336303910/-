package base.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;

public class JsonUtil
{
  public static String objectToJson(Object object)
  {
    StringBuilder json = new StringBuilder();
    if (object == null)
      json.append("\"\"");
    else if ((object instanceof String) || (object instanceof Integer))
      json.append("\"").append(object.toString()).append("\"");
    else if(object instanceof Date){
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      json.append("\"").append(sdf.format(object)).append("\"");
    }
    else {
      json.append(beanToJson(object));
    }
    return json.toString();
  }

  public static String beanToJson(Object bean)
  {
    StringBuilder json = new StringBuilder();
    json.append("{");
    PropertyDescriptor[] props = (PropertyDescriptor[])null;
    try {
      props = Introspector.getBeanInfo(bean.getClass(), Object.class)
        .getPropertyDescriptors();
    } catch (IntrospectionException localIntrospectionException) {
    }
    if (props != null) {
      for (int i = 0; i < props.length; ++i)
        try {
          String name = objectToJson(props[i].getName());
          String value = objectToJson(props[i].getReadMethod().invoke(bean, new Object[0]));
          json.append(name);
          json.append(":");
          json.append(value);
          json.append(",");
        }
        catch (Exception localException) {
        }
      json.setCharAt(json.length() - 1, '}');
    } else {
      json.append("}");
    }
    return json.toString();
  }

  public static String listToJson(List<?> list)
  {
    StringBuilder json = new StringBuilder();
    json.append("[");
    if ((list != null) && (list.size() > 0)) {
      for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) { Object obj = localIterator.next();
        json.append(objectToJson(obj));
        json.append(",");
      }
      json.setCharAt(json.length() - 1, ']');
    } else {
      json.append("]");
    }
    return json.toString();
  }

  public static Object JsonToObject(JSONObject object, Class c)
  {
    return JSONObject.toBean(object, c);
  }
  
  
  /** */
	/**
	 * 从一个JSON 对象字符格式中得到一个java对象
	 * 
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	public static Object getObject4JsonString(String jsonString, Class pojoCalss) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return pojo;
	}

	/**
	 * 从json HASH表达式中获取一个map，该map支持嵌套功能
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map<String, Object> getMap4Json(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator<String> keyIter = jsonObject.keys();
		String key;
		Object value;
		Map<String, Object> valueMap = new HashMap<String, Object>();
		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}
		return valueMap;
	}

	public static String getJsonString4Map(Map<?, ?> map) {

		JSONArray jsonArray = JSONArray.fromObject(map);
		return jsonArray.toString();
	}

	public static String getJsonString4List(Collection<?> list) {

		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
	}

	public static String getJsonString4List(Collection<?> list,
			JsonConfig jsonConfig) {

		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		return jsonArray.toString();
	}

	/** */
	/**
	 * 从json数组中得到相应java数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Object[] getObjectArray4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();
	}
	
	public static List getList4Json(String jsonString, Class pojoClass) {
		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpherEx(new String[] { "yyyy-MM-dd HH:mm:ss",
                        "yyyy-MM-dd", "yyyy-MM-dd't'HH:mm:ss" }, (Date) null));//调用DateMorpherEx，defaultValue为null
		
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;

		List list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {

			jsonObject = jsonArray.getJSONObject(i);
			String Format[] = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"};
			

//			JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(Format)); 
			pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);
			

		}
		return list;

	}
	
	/**
	 * 替换下字符
	 * @param myString
	 * @return
	 */
	public static String rePattern(String myString){  
        String newString=null;  
        Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");  
        Matcher m = CRLF.matcher(myString);  
        if (m.find()) {  
          newString = m.replaceAll("<br>");  
        }  
        return newString;  
    }


	/** */
	/**
	 * 从json数组中解析出java字符串数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static String[] getStringArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			stringArray[i] = jsonArray.getString(i);

		}

		return stringArray;
	}

	/** */
	/**
	 * 从json数组中解析出javaLong型对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Long[] getLongArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Long[] longArray = new Long[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			longArray[i] = jsonArray.getLong(i);

		}
		return longArray;
	}

	/** */
	/**
	 * 从json数组中解析出java Integer型对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Integer[] getIntegerArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Integer[] integerArray = new Integer[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			integerArray[i] = jsonArray.getInt(i);

		}
		return integerArray;
	}
	/**
	 * 从json数组中解析出java Integer型对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Double[] getDoubleArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Double[] doubleArray = new Double[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			doubleArray[i] = jsonArray.getDouble(i);

		}
		return doubleArray;
	}

	/** */
	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param javaObj
	 * @return
	 */
	public static String getJsonString4JavaPOJO(Object javaObj) {

		JSONObject json;
		json = JSONObject.fromObject(javaObj);
		return json.toString();

	}
	
	/**
	 * 将java对象转换成json字符串,并设定日期格式
	 * 
	 * @param javaObj
	 * @param dataFormat
	 * @return
	 */
	public static String getJsonString4JavaPOJO(Object javaObj,
			String dataFormat) {

		JSONObject json;
		JsonConfig jsonConfig = configJson(dataFormat);
		json = JSONObject.fromObject(javaObj, jsonConfig);
		return json.toString();

	}

	/**
	 * 将java对象转换成json字符串,并设定日期格式
	 * 
	 * @param javaObj
	 * @param dataFormat
	 * @return
	 */
	public static String getJsonString4JavaPOJO(Object javaObj,
			String[] excludes, String dataFormat) {

		JSONObject json;
		JsonConfig jsonConfig = configJson(excludes, dataFormat);
		json = JSONObject.fromObject(javaObj, jsonConfig);
		return json.toString();

	}

	/**
	 * JSON 时间解析器具
	 * 
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "" });
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class,
				new CustJsonBeanProcessor(datePattern));
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,
				new CustJsonBeanProcessor(datePattern));

		return jsonConfig;
	}

	/**
	 * 
	 * @param excludes
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String[] excludes, String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class,
				new CustJsonBeanProcessor(datePattern));

		return jsonConfig;
	}
	
	 /**
	  * add by jiashl
     * 向POJO中批量设置属性
     * @param attributes
     * @param className
     * @return
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws NoSuchFieldException 
     * @throws SecurityException 
	 * @throws ParseException 
     */
    public static Object saveAttributesToPOJO(JSONObject attributes,String className) throws Exception
    {
        Class clazz = Class.forName(className);
        Object obj = clazz.newInstance();
        Method[] methods = clazz.getMethods();
        String methodName;
        String temp;
        Class fieldType;
        Class[] fieldTypes;
        for(Method method : methods)
        {
            methodName = method.getName();
            if(methodName.startsWith("set"))
            {
                temp = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
                fieldTypes = method.getParameterTypes();
                fieldType = fieldTypes[0];
                if(String.class.toString().equals(fieldType.toString()))
                {
                    method.invoke(obj, new Object[]{attributes.get(temp)==null?"": attributes.get(temp) + ""});
                }
                else if(Long.class.toString().equals(fieldType.toString()))
                {
                    if(attributes.get(temp) != null && !"".equals(attributes.get(temp)))
                    {
                        method.invoke(obj, new Object[]{new Long(attributes.get(temp)+"")});
                    }
                }
                else if(Integer.class.toString().equals(fieldType.toString()))
                {
                    if(attributes.get(temp) != null && !"".equals(attributes.get(temp)))
                    method.invoke(obj, new Object[]{new Integer(attributes.get(temp) + "")});
                }
                else if(Date.class.toString().equals(fieldType.toString()))
                {
                    if(TextUtil.isNull(String.valueOf(attributes.get(temp))))
                        continue;
                    else
                       method.invoke(obj, new Object[]{new SimpleDateFormat("yyyy-MM-dd").parse(attributes.get(temp).toString())});
                }
            }
        }
        return obj;
    }
}
