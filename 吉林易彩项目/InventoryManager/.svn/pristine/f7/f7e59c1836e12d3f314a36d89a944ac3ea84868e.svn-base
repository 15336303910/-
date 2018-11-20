package base.util;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
public class GetProperties {
	
	InputStream in = null;
	
	Properties loadfile = new Properties();
	
	private static final String INSPUR_PROP_FILE = "/classes/comm.properties";

	public GetProperties(){
		try{
			String path = java.net.URLDecoder.decode(getClassPathFile(GetProperties.class).getAbsolutePath(),"UTF-8");
			path = path.substring(0,(path.lastIndexOf("WEB-INF")+7));
			in = new BufferedInputStream(new FileInputStream(new File(path+INSPUR_PROP_FILE)));
			loadfile.load(in);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 根据key 值得到value 
	 * 
	 * @param keyString
	 *            String
	 * @return String
	 */
	public String getValueByKey(String keyString) {
		return (String) loadfile.getProperty(keyString);
	}
	
	
	/**
	 * 根据value得到key
	 * @param value
	 * @return
	 */
	public String getKeyByValue(String value){
		Set<Object> set = loadfile.keySet();
		Iterator iter = set.iterator();
		String result ="";
		while (iter.hasNext()) {  
			if(loadfile.getProperty(iter.next()+"").equals(value)){
				result = iter.next()+"";
				break;
			}
	    }  
		return result;
	}

	/**
	 *关闭in
	 */
	public void destroy() {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static File getClassFile(Class clazz) {
		URL path = clazz.getResource(clazz.getName().substring(
				clazz.getName().lastIndexOf(".") + 1)
				+ ".class");
		if (path == null) {
			String name = clazz.getName().replaceAll("[.]", "/");
			path = clazz.getResource("/" + name + ".class");
		}
		return new File(path.getFile());
	}

	public static File getClassPathFile(Class clazz) {
		File file = getClassFile(clazz);
		for (int i = 0, count = clazz.getName().split("[.]").length; i < count; i++)
			file = file.getParentFile();

		if (file.getName().toUpperCase().endsWith(".JAR!")) {
			file = file.getParentFile();
		}

		return file;
	}

}
