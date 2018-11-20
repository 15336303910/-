package com.function.nettool.plugin;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;

import com.function.rules.service.DbService;
import com.systemConfig.model.DataTableResult;
@Controller("com.function.nettool.plugin.TransferXYAction")
@RequestMapping(value="/transferXYAction")
public class TransferXYAction{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DbService dbService;
	
	public static String enterpriseKey = "efe7a731a924fe5ea543c4cb1a757149";
	
	/*
	 * 	将非高德坐标系（GPS、百度等）的坐标转化为高德坐标系的坐标
	 * 
	 * */
	@RequestMapping("/xytransfer.ilf")
	public void xytransfer(@RequestParam String params,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			final JSONObject paramObject = JSONObject.fromObject(params);
			String strURL = "http://restapi.amap.com/v3/assistant/coordinate/convert?key="+enterpriseKey+"&locations="+paramObject.getString("xPoint")+","+paramObject.getString("yPoint")+"&coordsys="+paramObject.getString("xyType");
			/*
			 * 	根据链接和参数打开连接
			 * 
			 * */
			URL url = new URL(strURL);
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.connect();
			/*
			 * 	读取结果
			 * 
			 * */
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			String line;
			StringBuilder stringBuilder = new StringBuilder();
			while((line=bufferedReader.readLine())!=null){  
				stringBuilder.append(line);  
            }  
			bufferedReader.close();  
            urlConnection.disconnect(); 
            /*
			 * 	解析结果
			 * 
			 * */
			JSONObject resultObj = JSONObject.fromObject(stringBuilder.toString());
			if(Integer.parseInt(resultObj.get("status").toString())==1){
				resultObject.put("success",true);
				resultObject.put("EXPORT_X",resultObj.get("locations").toString().split(",")[0]);
				resultObject.put("EXPORT_Y",resultObj.get("locations").toString().split(",")[1]);
			}else{
				resultObject.put("success",false);
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject = JSONObject.fromObject("{success:false}");
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}	
	}
	
	/*
	 * 	经纬度转化模板下载.
	 * 
	 * */
	@RequestMapping("/downloadTemplate.ilf")
	public void downloadTemplate(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String strDirPath = request.getSession().getServletContext().getRealPath("/")+"templates/XYTransfer.xlsx";
		File templateFile = new File(strDirPath);
		if(templateFile.exists()){
			FileInputStream fileInputStream = new FileInputStream(templateFile);
			long filesize = templateFile.length();
			response.setContentType("text/html;charset=gb2312");
			response.addHeader("content-type","application/x-msdownload;");
			response.setHeader("Content-Disposition","attachment;filename=\""+new String((templateFile.getName()).getBytes(),"ISO8859-1"));
			response.addHeader("content-length",Long.toString(filesize));
			OutputStream output = response.getOutputStream();
			byte[] bytes = new byte[1024];
			int i = 0;    
			while((i=fileInputStream.read(bytes))>0){
				output.write(bytes,0,i);    
			}    
			output.flush();
			output.close();
			fileInputStream.close();
		}
	}
	
	/*
	 * 	数据文件下载.
	 * 
	 * */
	@RequestMapping("/downloadDataFile.ilf")
	public void downloadDataFile(@RequestParam String fileName,HttpServletRequest request,HttpServletResponse response)throws Exception{
		String strDirPath = request.getSession().getServletContext().getRealPath("/")+"uploads/"+fileName;
		File templateFile = new File(strDirPath);
		if(templateFile.exists()){
			FileInputStream fileInputStream = new FileInputStream(templateFile);
			long filesize = templateFile.length();
			response.setContentType("text/html;charset=gb2312");
			response.addHeader("content-type","application/x-msdownload;");
			response.setHeader("Content-Disposition","attachment;filename=\""+new String((templateFile.getName()).getBytes(),"ISO8859-1"));
			response.addHeader("content-length",Long.toString(filesize));
			OutputStream output = response.getOutputStream();
			byte[] bytes = new byte[1024];
			int i = 0;    
			while((i=fileInputStream.read(bytes))>0){
				output.write(bytes,0,i);    
			}    
			output.flush();
			output.close();
			fileInputStream.close();
		}
	}
	
	/*
	 * 	批量上传经纬度数据
	 * 
	 * */
	@RequestMapping(value="/uploadDataFile.ilf",method=RequestMethod.POST)
	public void uploadDataFile(@RequestParam("TemplateUpload")MultipartFile fileToUpload,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		Integer jobCode = -1;
		try{
			if(!fileToUpload.isEmpty()){
				/*
				 * 	先将文件保存到指定目录
				 * 
				 * */
				String strDirPath = request.getSession().getServletContext().getRealPath("/")+"uploads/";
				String newFileName = new Date().getTime()+".xlsx";
				File file = new File(strDirPath+newFileName);
				if(file.exists()){
					file.delete();
				}
				fileToUpload.transferTo(file);
				/*
				 * 	解析文件并查询入库
				 * 
				 * */
				FileInputStream fis = new FileInputStream(file);
				XSSFWorkbook wk = new XSSFWorkbook(fis);
				Iterator<XSSFSheet> sheetCar = wk.iterator();
				while(sheetCar.hasNext()){
					XSSFSheet sheet = (XSSFSheet)sheetCar.next();
		            Integer rowNumber = sheet.getLastRowNum();
		            if(rowNumber>=3){
		            	/*
		            	 * 	创建监控
		            	 * 
		            	 * */
		            	jobCode = jdbcTemplate.queryForInt("SELECT SEQ_NET_PLUGIN_JOB.NEXTVAL FROM DUAL");
		            	jdbcTemplate.execute("INSERT INTO NET_PLUGIN_JOB(ID,JOB_TYPE,START_DATE) VALUES("+jobCode+",'XY_TRANSFER',SYSDATE)");
		            	/*
		            	 * 	保存数据
		            	 * 
		            	 * */
		            	Connection connection = dbService.getConnection();
						connection.setAutoCommit(false);
						String sql = "";
						sql+="INSERT INTO NET_JOB_DATA(";
						sql+="	ID,BELONG_JOB,X_INPUT,Y_INPUT,INPUT_TYPE,TRANSFER_RESULT,X_OUTPUT,Y_OUTPUT";
						sql+=")values(SEQ_NET_JOB_DATA.NEXTVAL,"+jobCode+",?,?,?,?,?,?)";					
						PreparedStatement pStatement = connection.prepareStatement(sql);
		            	for(int j=3;j<=rowNumber;j++){
			                XSSFRow currentRow = sheet.getRow(j);
			                if(currentRow!=null){
			                	XSSFCell xValueCell = currentRow.getCell(1);
			                	XSSFCell yValueCell = currentRow.getCell(2);
			                	XSSFCell xyValueType = currentRow.getCell(3);
			                	if(xValueCell!=null && yValueCell!=null && xyValueType!=null && !"".equals(xValueCell.getNumericCellValue()) && !"".equals(yValueCell.getNumericCellValue()) || !"".equals(xyValueType.getStringCellValue())){
			                		/*
			                		 * 	定义变量
			                		 * 
			                		 * */
			                		String X_VALUE = xValueCell.getNumericCellValue()+"";
			                		String Y_VALUE = yValueCell.getNumericCellValue()+"";
			                		String INPUT_TYPE = xyValueType.getStringCellValue();
			                		String TRANSFER_RESULT = "";
			                		String X_OUTPUT = "";
			                		String Y_OUTPUT = "";
			                		/*
			                		 * 	执行转化
			                		 * 
			                		 * */
			            			URL url = new URL("http://restapi.amap.com/v3/assistant/coordinate/convert?key="+enterpriseKey+"&locations="+X_VALUE+","+Y_VALUE+"&coordsys="+INPUT_TYPE);
			            			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			            			urlConnection.connect();
			            			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			            			String line;
			            			StringBuilder stringBuilder = new StringBuilder();
			            			while((line=bufferedReader.readLine())!=null){  
			            				stringBuilder.append(line);  
			                        }  
			            			bufferedReader.close();  
			                        urlConnection.disconnect(); 
			            			JSONObject resultObj = JSONObject.fromObject(stringBuilder.toString());
			            			if(Integer.parseInt(resultObj.get("status").toString())==1){
			            				TRANSFER_RESULT = "转化成功";
			            				X_OUTPUT = resultObj.get("locations").toString().split(",")[0];
			            				Y_OUTPUT = resultObj.get("locations").toString().split(",")[1];
			            			}else{
			            				TRANSFER_RESULT = "转化失败";
			            			}
			            			/*
			                		 * 	保存入库
			                		 * 
			                		 * */
			            			pStatement.setString(1,X_VALUE);
									pStatement.setString(2,Y_VALUE);
									pStatement.setString(3,INPUT_TYPE);
									pStatement.setString(4,TRANSFER_RESULT);
									pStatement.setString(5,X_OUTPUT);
									pStatement.setString(6,Y_OUTPUT);
									pStatement.addBatch();
									/*
									 * 	写入文件
									 * 
									 * */
									for(int b=4;b<=6;b++){
										XSSFCell xssfCell = currentRow.getCell(b);
										if(xssfCell==null){
											currentRow.createCell(b);
										}
									}
									for(int b=4;b<=6;b++){
										XSSFCell xssfCell = currentRow.getCell(b);
										if(b==4){
											xssfCell.setCellValue(TRANSFER_RESULT);
										}else if(b==5){
											xssfCell.setCellValue(X_OUTPUT);
										}else if(b==6){
											xssfCell.setCellValue(Y_OUTPUT);
										}
									}
			                	}
			                }
			                if(j%3000==0){
								pStatement.executeBatch();
								connection.commit();
								pStatement.clearBatch();
							}
			            }
		            	pStatement.executeBatch();
						connection.commit();
						pStatement.clearBatch();
						/*
						 * 	关闭数据库连接.
						 * 
						 * */
						if(pStatement!=null){
							pStatement.close();
						}
						if(connection!=null){
							connection.close();
						}
		            }
		            break;
				}
				String outputFileName = new Date().getTime()+".xlsx";
				File outputFile = new File(strDirPath+outputFileName);
				if(outputFile.exists()){
					outputFile.delete();
				}
				FileOutputStream fos = new FileOutputStream(outputFile);
				wk.write(fos);
				fos.close();
				resultObject.put("jobCode",jobCode);
				resultObject.put("outputFile",outputFileName);
				System.out.println("提示:成功输出结果数据文件："+outputFileName);
			}else{
				resultObject.put("success",false);
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	查询列表
	 * 
	 * */
	@RequestMapping("/findItems.ilf")
	public void findItems(@RequestParam String tableparam,@RequestParam String conditions,HttpServletResponse response)throws Exception{
		Long sEcho = 0L;
		Integer displayStart = 0;
		Integer iDisplayLength = 0;
		JSONArray jsons = JSONArray.fromObject(tableparam);
		HashMap<String,Object> conditonMap = new HashMap<String,Object>();
		if(jsons!=null && jsons.size()!=0){
			for(int i=0;i<jsons.size();i++){
				JSONObject json = JSONObject.fromObject(jsons.get(i));
				String key = json.getString("name");
				if(key.equals("sEcho")){
					sEcho = Long.parseLong(json.getString("value"));
				}else if(key.equals("iDisplayStart")){
					displayStart = Integer.parseInt(json.getString("value"));
					conditonMap.put("iDisplayStart",displayStart);
				}else if(key.equals("iDisplayLength")){
					iDisplayLength = Integer.parseInt(json.getString("value"));
					conditonMap.put("iDisplayLength",iDisplayLength);
				}
			}
		}
		JSONArray condition = JSONArray.fromObject(conditions);
		if(conditions!=null && condition.size()!=0){
			for(int i=0;i<condition.size();i++){
				JSONObject jsonObject = JSONObject.fromObject(condition.get(i));
				if(jsonObject.get("value")!=null && !"".equals(jsonObject.getString("value"))){
					conditonMap.put(jsonObject.getString("name"),jsonObject.getString("value"));
				}
			}
		}
		List<Map<String,Object>> dataDetails = jdbcTemplate.queryForList("SELECT * FROM NET_JOB_DATA WHERE BELONG_JOB = "+conditonMap.get("BELONG_JOB").toString());
		Integer count = dataDetails.size();
		DataTableResult<Map<String,Object>> tableData = new DataTableResult<Map<String,Object>>();
		tableData.setsEcho(sEcho);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		tableData.setAaData(dataDetails);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
}
