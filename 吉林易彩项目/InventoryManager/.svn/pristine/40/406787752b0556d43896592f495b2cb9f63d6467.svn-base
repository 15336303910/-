package base.util;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 
 * @author chenqp
 *
 */
public class BeanUtil extends PropertyUtils {
	
	/**  
	 * override BeanUtils method copyProperties, support not copy null or blank  
	 * property  
	 * 
	 *  
	 * @param dest  
	 * @param src  
	 * @throws IllegalAccessException  
	 * @throws InvocationTargetException  
	 * @throws IllegalArgumentException  
	 */  
	  
	public static void copyProperties(Object dest, Object src) {   
	    // 为两个参数时，skipNull 默认为true   
	    copyProperties(dest, src, true);   
	}   
	  
	/**  
	 * override BeanUtils method copyProperties, support not copy null or blank  
	 * property  
	 *  
	 * @param dest  
	 * @param src  
	 * @throws IllegalAccessException  
	 * @throws InvocationTargetException  
	 * @throws IllegalArgumentException  
	 */  
	public static void copyProperties(Object dest, Object src, boolean skipNull) {   
	    // Validate existence of the specified beans   
	    if (dest == null) {   
	        throw new IllegalArgumentException("No destination bean specified");   
	    }   
	  
	    if (src == null) {   
	        throw new IllegalArgumentException("No origin bean specified");   
	    }   
	    // 获取所有src中的属性，存入于数组中   
	    PropertyDescriptor[] origDescriptors = PropertyUtils   
	            .getPropertyDescriptors(src);   
	       
	    for (int i = 0; i < origDescriptors.length; i++) {   
	        // 取出src中属性名   
	        String name = origDescriptors[i].getName();   
	  
	        if ("class".equals(name)) {   
	            continue; // No point in trying to set an object's class   
	        }   
	  
	        if (PropertyUtils.isReadable(src, name)   
	                && PropertyUtils.isWriteable(dest, name)) {   
	  
	            Object value = null;   
	            try {   
	                // 取出属性的值   
	                value = PropertyUtils.getSimpleProperty(src, name);   
	            } catch (Exception e) {   
	                throw new RuntimeException();   
	            }   
	  
	            if (skipNull) {   
	                if (value == null) {   
	                    continue;   
	                }   
	            } else if (value == null){   
	                    value = null;   
	            }   
	  
	            try {  
	                setProperty(dest, name, value);   
	            } catch (Exception e) {   
	                throw new RuntimeException();   
	            }   
	        }   
	    }   
	}  
	
	/**
	 * 将MAP转换成对象
	 * @param map
	 * @param beanClass
	 * @return
	 * @throws Exception
	 */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null)   
            return null;    
        Object obj = beanClass.newInstance();  
        try{
        	BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
            for (PropertyDescriptor property : propertyDescriptors) {  
                Method setter = property.getWriteMethod();    
                if (setter != null) {
                	if(map.containsKey(property.getName())){
                		setter.invoke(obj, map.get(property.getName())); 
                	}
                }  
            }
        }catch(Exception e){
        	
        }
          
        return obj;  
    } 
    
    /**
     * 得到数据
     * @param list
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static List<Object> mapListToObjList(List<Map<String,Object>> list,Class<?> beanClass) throws Exception{
    	List<Object> result = new ArrayList<Object>();
    	for(Map<String, Object> map : list){
    		Object obj= BeanUtil.mapToObject(map, beanClass);
    		result.add(obj);
    	}
    	return result;
    }
    
    /**
     * 将对象转成hashmap
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj) throws Exception {    
        if(obj == null)  
            return null;      
  
        Map<String, Object> map = new HashMap<String, Object>();   
  
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
        for (PropertyDescriptor property : propertyDescriptors) {    
            String key = property.getName();    
            if (key.compareToIgnoreCase("class") == 0) {   
                continue;  
            }  
            Method getter = property.getReadMethod();  
            Object value = getter!=null ? getter.invoke(obj) : null;  
            map.put(key, value);  
        }    
  
        return map;  
    } 


}
