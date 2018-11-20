package com.function.project;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
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

import com.system.LoginUserUtil;
import com.systemConfig.model.DataTableResult;
@Controller("com.function.project.ProjectFilesAction")
@RequestMapping(value="/projectFilesAction")
public class ProjectFilesAction{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	/*
	 * 	查询列表
	 * 
	 * */
	@RequestMapping("/findItems.ilf")
	public void findItems(
		@RequestParam String tableparam,
		@RequestParam String conditions,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		try{
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
			/*
			 * 	基本SQL
			 * 
			 * */
			String sql = "";
			sql+="SELECT A.*,B.EMPLOYEE_NAME FROM PROJECT_FILES A,S_SYSTEM_USER B WHERE A.UPLOADER = B.ID ";
			if(conditonMap.containsKey("BELONG_PROJECT") && conditonMap.get("BELONG_PROJECT")!=null && !"".equals(conditonMap.get("BELONG_PROJECT").toString())){
				sql+=" AND A.BELONG_PROJECT = '"+conditonMap.get("BELONG_PROJECT").toString()+"' ";
			}
			/*
			 * 	统计数量
			 * 
			 * */
			Integer count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM("+sql+")");
			/*
			 * 	分页SQL
			 * 
			 * */
			List<Map<String,Object>> ruleJobs = jdbcTemplate.queryForList(sql);	
			if(ruleJobs.size()>0){
				for(int i=0;i<ruleJobs.size();i++){
					ruleJobs.get(i).put("LOGIN_USER_ID",loginUserUtil.getLoginUserId(request));
				}
			}
			DataTableResult<Map<String,Object>> tableData = new DataTableResult<Map<String,Object>>();
			tableData.setsEcho(sEcho);
			tableData.setAaData(ruleJobs);
			tableData.setiTotalRecords(count);
			tableData.setiTotalDisplayRecords(count);
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(JSONObject.fromObject(tableData).toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*
	 * 	上传数据文件.
	 * 
	 * */
	@RequestMapping(value="/uploadDataFile.ilf",method=RequestMethod.POST)
	public void uploadDataFile(
		@RequestParam("TemplateUpload")MultipartFile fileToUpload,
		@RequestParam String projectCode,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			if(!fileToUpload.isEmpty()){
				/*
				 * 	文件上传：/files/projectfile/
				 * 
				 * */
				String strDirPath = request.getSession().getServletContext().getRealPath("/")+"files"+File.separator+"projectfile"+File.separator;
				File folder = new File(strDirPath);
				if(!folder.exists()){
					folder.mkdirs();
				}
				String originalFilename = fileToUpload.getOriginalFilename();
				String targetesFilename = new Date().getTime()+originalFilename.substring(originalFilename.lastIndexOf("."),originalFilename.length());
				File targetFile = new File(strDirPath+targetesFilename);
				fileToUpload.transferTo(targetFile);
				/*
				 * 	保存记录
				 * 
				 * */
				String sql = "";
				sql+="INSERT INTO PROJECT_FILES(";
				sql+="	  ID,BELONG_PROJECT,FILE_NAME,FILE_ALIAS,UPLOADER,UPLOAD_DATE";
				sql+=")VALUES(";
				sql+="	  SEQ_PROJECT_FILES.NEXTVAL,'"+projectCode+"','"+originalFilename+"','"+targetesFilename+"','"+loginUserUtil.getLoginUserId(request)+"',SYSDATE";
				sql+=")";
				jdbcTemplate.execute(sql);
			}else{
				resultObject.put("SUCCESS",false);
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	下载模板.
	 * 
	 * */
	@RequestMapping("/downloadTemplate.ilf")
	public void downloadApp(
		@RequestParam String projectCode,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		String fileNameA = jdbcTemplate.queryForList("SELECT * FROM PROJECT_FILES WHERE ID = "+projectCode).get(0).get("FILE_ALIAS").toString();
		String fileNameB = jdbcTemplate.queryForList("SELECT * FROM PROJECT_FILES WHERE ID = "+projectCode).get(0).get("FILE_NAME").toString();
		String strDirPath = request.getSession().getServletContext().getRealPath("/")+"files"+File.separator+"projectfile"+File.separator+fileNameA;
		File templateFile = new File(strDirPath);
		if(templateFile.exists()){
			FileInputStream fileInputStream = new FileInputStream(templateFile);
			long filesize = templateFile.length();
			response.setHeader("Content-Disposition","attachment;filename=\""+new String((fileNameB).getBytes("gbk"),"ISO8859-1"));
			response.setContentType("application/x-download;");
			response.setHeader("Content-Length", String.valueOf(filesize));
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
	 * 	下载模板.
	 * 
	 * */
	@RequestMapping("/removeFile.ilf")
	public void removeFile(
		@RequestParam String fileCode,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			jdbcTemplate.execute("DELETE FROM PROJECT_FILES WHERE ID = "+fileCode);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
}