package interfaces.irmsInterface.utils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import base.util.GetProperties;


public class UploadUtil {

	
	
	/**
	 * 上传文件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static  String saveXml(String str ) throws IOException{
		String path ="";
		try{
			GetProperties getProperties = new GetProperties();
			String commonPath = getProperties.getValueByKey("filePath");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			String datePath=sdf.format(new Date());
			String dirPath=commonPath+"/"+datePath+"/";
			File dir = new File(dirPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			InputStream is=new ByteArrayInputStream(str.getBytes());
			long currentTime = System.currentTimeMillis();// 生成随机数
			int randD = (int) (Math.random() * 1000);// 相加得到流水号
			long flowId = currentTime + randD;
			path=dirPath+flowId+".xml";//文件上传真正路径
			FileOutputStream outputStream = new FileOutputStream(path);
	        byte[] buffer = new byte[1024];
	        int len = -1;
	  		while ((len = is.read(buffer, 0, 1024)) != -1) {
	  			outputStream.write(buffer, 0, len);
	  		}
	  		outputStream.close();
	        is.close();
	        outputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return path;
	}
	
	
	/**
	 * 保存交互
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static  String saveIrms(String str ,String taskId) throws IOException{
		String path ="";
		try{
			GetProperties getProperties = new GetProperties();
			String commonPath = getProperties.getValueByKey("filePath");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			String datePath=sdf.format(new Date());
			String dirPath=commonPath+"/"+datePath+"/"+taskId+"/";
			File dir = new File(dirPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			InputStream is=new ByteArrayInputStream(str.getBytes());
			long currentTime = System.currentTimeMillis();// 生成随机数
			int randD = (int) (Math.random() * 1000);// 相加得到流水号
			long flowId = currentTime + randD;
			path=dirPath+flowId+".xml";//文件上传真正路径
			FileOutputStream outputStream = new FileOutputStream(path);
	        byte[] buffer = new byte[1024];
	        int len = -1;
	  		while ((len = is.read(buffer, 0, 1024)) != -1) {
	  			outputStream.write(buffer, 0, len);
	  		}
	  		outputStream.close();
	        is.close();
	        outputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return path;
	}
}
