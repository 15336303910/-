package com.function.project;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
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
@Controller("com.function.project.ProjectCheckinAction")
@RequestMapping(value="/projectCheckinAction")
public class ProjectCheckinAction{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	/*
	 * 	查询列表（包含已外验、外验中、未发起外验的项目）
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
			sql+="SELECT I.CURRENT_NODE,I.CONTENTS,D.*,E.NAME AS CITY_NAME,F.REGION_NAME,G.DICTNAME,H.DICTNAME AS PROJECT_NODE ";
			sql+="FROM ( ";
			sql+="	  SELECT A.PRJ_ID,A.SITE_ID,A.SITE_NAME,A.PRJ_TYPE,A.PRJ_YEAR,A.PROJECT_NAME,A.CITY,A.COUNTY,A.PRJ_STATUS ";
			sql+="	  FROM TOWERCRNOP.PMS_MID_EXPORT_FORMS A ";
			sql+="	  WHERE A.PRJ_ID IN (";
			sql+="		  SELECT PRJ_ID FROM TOWERCRNOP.PMS_MID_PRJ_STATUS WHERE DEALNODE1 = '内部验收'";
			sql+="	  ) ";
			if(!loginUserUtil.isProvince(request)){
				sql+="  AND A.PROJECT_NAME LIKE '%"+loginUserUtil.getBelongArea(request)+"%' ";
			}
			if(conditonMap.containsKey("PRO_NAME") && conditonMap.get("PRO_NAME")!=null && !"".equals(conditonMap.get("PRO_NAME").toString())){
				sql+="	AND A.PROJECT_NAME LIKE '%"+conditonMap.get("PRO_NAME").toString()+"%' ";
			}
			sql+=") D,DB_QUALITY.PROJECT_JOB I,TOWERCRNOP.OBP_CITY E,TOWERCRNOP.GJ_RES_SPC_REGION F,(SELECT * FROM TOWERCRNOP.GJ_PMS_DICT WHERE DICTTYPEID = 'PROJ_TYPE') G,(SELECT * FROM TOWERCRNOP.GJ_PMS_DICT WHERE DICTTYPEID = 'PROJ_STU') H ";
			sql+="WHERE ";
			sql+="	  D.CITY = E.CODE(+) AND ";
			sql+="	  D.COUNTY = F.CODE(+) AND ";
			sql+="	  D.PRJ_TYPE = G.DICTID(+) AND ";
			sql+="	  D.PRJ_STATUS = H.DICTID(+) AND ";
			sql+="	  D.PRJ_ID = I.PROJECT_ID(+)";
			/*
			 * 	统计数量
			 * 
			 * */
			Integer count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM("+sql+")");
			/*
			 * 	分页SQL
			 * 
			 * */
			String pageSql = "";
			pageSql+="SELECT J.* FROM(";
			pageSql+="	  SELECT I.*,ROWNUM AS RN FROM (";
			pageSql+="		  "+sql;
			pageSql+="	  ) I WHERE ROWNUM <= "+(displayStart+iDisplayLength);
			pageSql+=") J WHERE J.RN > "+displayStart;
			List<Map<String,Object>> ruleJobs = jdbcTemplate.queryForList(pageSql);		
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
	 * 	查询列表（获取我的待审批）
	 * 
	 * */
	@RequestMapping("/findMyWaitings.ilf")
	public void findMyWaitings(
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
			sql+="SELECT I.CURRENT_NODE,I.CONTENTS,D.*,E.NAME AS CITY_NAME,F.REGION_NAME,G.DICTNAME,H.DICTNAME AS PROJECT_NODE FROM(";
			sql+="	  SELECT A.PRJ_ID,A.SITE_ID,A.SITE_NAME,A.PRJ_TYPE,A.PRJ_YEAR,A.PROJECT_NAME,A.CITY,A.COUNTY,A.PRJ_STATUS ";
			sql+="	  FROM TOWERCRNOP.PMS_MID_EXPORT_FORMS A ";
			sql+="	  WHERE 1 = 1 ";
			if(!loginUserUtil.isProvince(request)){
				sql+="  AND A.PROJECT_NAME LIKE '%"+loginUserUtil.getBelongArea(request)+"%'";
			}
			if(conditonMap.containsKey("PRO_NAME") && conditonMap.get("PRO_NAME")!=null && !"".equals(conditonMap.get("PRO_NAME").toString())){
				sql+="	AND A.PROJECT_NAME LIKE '%"+conditonMap.get("PRO_NAME").toString()+"%' ";
			}
			if(conditonMap.containsKey("PRO_STATUS") && conditonMap.get("PRO_STATUS")!=null && !"".equals(conditonMap.get("PRO_STATUS").toString()) && !"-".equals(conditonMap.get("PRO_STATUS").toString())){
				sql+="	AND A.PRJ_STATUS LIKE '%"+conditonMap.get("PRO_STATUS").toString()+"%' ";
			}
			sql+="	  ORDER BY A.PRJ_YEAR DESC";
			sql+=") D,PROJECT_JOB I,TOWERCRNOP.OBP_CITY E,TOWERCRNOP.GJ_RES_SPC_REGION F,(SELECT * FROM TOWERCRNOP.GJ_PMS_DICT WHERE DICTTYPEID = 'PROJ_TYPE') G,(SELECT * FROM TOWERCRNOP.GJ_PMS_DICT WHERE DICTTYPEID = 'PROJ_STU') H WHERE D.CITY = E.CODE(+) AND D.COUNTY = F.CODE(+) AND D.PRJ_TYPE = G.DICTID(+) AND D.PRJ_STATUS = H.DICTID(+) AND D.PRJ_ID = I.PROJECT_ID AND I.WAITING_USER = "+loginUserUtil.getLoginUserId(request);
			/*
			 * 	统计数量
			 * 
			 * */
			Integer count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM("+sql+")");
			/*
			 * 	分页SQL
			 * 
			 * */
			String pageSql = "";
			pageSql+="SELECT J.* FROM(";
			pageSql+="	  SELECT I.*,ROWNUM AS RN FROM (";
			pageSql+="		  "+sql;
			pageSql+="	  ) I WHERE ROWNUM <= "+(displayStart+iDisplayLength);
			pageSql+=") J WHERE J.RN > "+displayStart;
			List<Map<String,Object>> ruleJobs = jdbcTemplate.queryForList(pageSql);		
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
	 * 	查询列表（获取已发起外验的项目）
	 * 
	 * */
	@RequestMapping("/findProjectsChecked.ilf")
	public void findProjectsChecked(
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
			sql+="SELECT I.CURRENT_NODE,I.CONTENTS,D.*,E.NAME AS CITY_NAME,F.REGION_NAME,G.DICTNAME,H.DICTNAME AS PROJECT_NODE FROM(";
			sql+="	  SELECT A.PRJ_ID,A.SITE_ID,A.SITE_NAME,A.PRJ_TYPE,A.PRJ_YEAR,A.PROJECT_NAME,A.CITY,A.COUNTY,A.PRJ_STATUS ";
			sql+="	  FROM TOWERCRNOP.PMS_MID_EXPORT_FORMS A ";
			sql+="	  WHERE 1 = 1 ";
			if(!loginUserUtil.isProvince(request)){
				sql+="  AND A.PROJECT_NAME LIKE '%"+loginUserUtil.getBelongArea(request)+"%'";
			}
			if(conditonMap.containsKey("PRO_NAME") && conditonMap.get("PRO_NAME")!=null && !"".equals(conditonMap.get("PRO_NAME").toString())){
				sql+="	AND A.PROJECT_NAME LIKE '%"+conditonMap.get("PRO_NAME").toString()+"%' ";
			}
			if(conditonMap.containsKey("PRO_STATUS") && conditonMap.get("PRO_STATUS")!=null && !"".equals(conditonMap.get("PRO_STATUS").toString()) && !"-".equals(conditonMap.get("PRO_STATUS").toString())){
				sql+="	AND A.PRJ_STATUS LIKE '%"+conditonMap.get("PRO_STATUS").toString()+"%' ";
			}
			sql+="	  ORDER BY A.PRJ_YEAR DESC";
			sql+=") D,PROJECT_JOB I,TOWERCRNOP.OBP_CITY E,TOWERCRNOP.GJ_RES_SPC_REGION F,(SELECT * FROM TOWERCRNOP.GJ_PMS_DICT WHERE DICTTYPEID = 'PROJ_TYPE') G,(SELECT * FROM TOWERCRNOP.GJ_PMS_DICT WHERE DICTTYPEID = 'PROJ_STU') H WHERE D.CITY = E.CODE(+) AND D.COUNTY = F.CODE(+) AND D.PRJ_TYPE = G.DICTID(+) AND D.PRJ_STATUS = H.DICTID(+) AND D.PRJ_ID = I.PROJECT_ID ";
			/*
			 * 	统计数量
			 * 
			 * */
			Integer count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM("+sql+")");
			/*
			 * 	分页SQL
			 * 
			 * */
			String pageSql = "";
			pageSql+="SELECT J.* FROM(";
			pageSql+="	  SELECT I.*,ROWNUM AS RN FROM (";
			pageSql+="		  "+sql;
			pageSql+="	  ) I WHERE ROWNUM <= "+(displayStart+iDisplayLength);
			pageSql+=") J WHERE J.RN > "+displayStart;
			List<Map<String,Object>> ruleJobs = jdbcTemplate.queryForList(pageSql);		
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
	 * 	获取单个信息
	 * 
	 * */
	@RequestMapping(value="/findOneDetail.ilf")
	public void findOneDetail(
		@RequestParam String projectId,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			/*
			 * 	基本信息
			 * 
			 * */
			List<Map<String,Object>> checkJobs = jdbcTemplate.queryForList("SELECT * FROM PROJECT_JOB WHERE PROJECT_ID = "+projectId);
			if(checkJobs.size()>0){
				resultObject.put("IS_DOING",true);
				resultObject.put("DOING_INFO",checkJobs.get(0));
				resultObject.put("NODE_INFO",JSONObject.fromObject(checkJobs.get(0).get("NODE_JSON").toString()));
			}else{
				resultObject.put("IS_DOING",false);
			}
			
			/*
			 * 	上传的[项目交付单 ]附件.
			 * 
			 * */
			List<Map<String,Object>> uploadeds = jdbcTemplate.queryForList("SELECT * FROM PROJECT_UPLOAD_FILE WHERE PROJECT_ID = "+projectId);
			if(uploadeds.size()>0){
				resultObject.put("IS_UPLOADED_CHECK_FILE",true);
				resultObject.put("CHECK_FILE_OBJECT",JSONObject.fromObject(uploadeds.get(0)));
			}else{
				resultObject.put("IS_UPLOADED_CHECK_FILE",false);
			}
			/*
			 * 	上传的[项目完工照片]附件.
			 * 
			 * */
			List<Map<String,Object>> images = jdbcTemplate.queryForList("SELECT * FROM PROJECT_UPLOAD_IMAGE WHERE PROJECT_ID = "+projectId);
			if(images.size()>0){
				resultObject.put("IS_UPLOADED_IMAGE",true);
				resultObject.put("IMAGES",JSONArray.fromObject(images));
			}else{
				resultObject.put("IS_UPLOADED_IMAGE",false);
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
			resultObject.put("MESSAGE","服务端异常，请联系系统管理员.");
		}finally{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	获取图片信息
	 * 
	 * */
	@RequestMapping(value="/findImagePage.ilf")
	public void findImagePage(
		@RequestParam String projectId,
		@RequestParam String pageStart,//Oracle里的Rownum是从1开始的.
		@RequestParam String pageLimit,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			/*
			 * 	上传[项目交付单 ]附件.
			 * 
			 * */
			String sql = "";
			sql+="SELECT B.* FROM(";
			sql+="	  SELECT A.*,ROWNUM AS RN FROM (";
			sql+="		  SELECT * FROM PROJECT_UPLOAD_IMAGE WHERE PROJECT_ID = "+projectId+" ORDER BY ID ASC";
			sql+="	  ) A WHERE ROWNUM <= "+pageLimit;
			sql+=") B WHERE B.RN >= "+pageStart;
			List<Map<String,Object>> images = jdbcTemplate.queryForList(sql);
			resultObject.put("ITEMS",images);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
			resultObject.put("MESSAGE","服务端异常，请联系系统管理员.");
		}finally{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	删除图片信息
	 * 
	 * */
	@RequestMapping(value="/removeImage.ilf")
	public void removeImage(
		@RequestParam String imageId,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			jdbcTemplate.execute("DELETE FROM PROJECT_UPLOAD_IMAGE WHERE ID = "+imageId);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
			resultObject.put("MESSAGE","服务端异常，请联系系统管理员.");
		}finally{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	上传交付文件
	 * 
	 * */
	@RequestMapping(value="/uploadData.ilf",method=RequestMethod.POST)
	public void uploadData(
		@RequestParam("TemplateUpload")MultipartFile fileToUpload,
		@RequestParam String projectId,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			if(!fileToUpload.isEmpty()){
				/*
				 * 	验证是否有目录./DataCenter/uploads/project/files/
				 * 
				 * */
				String projectFileFolder = request.getSession().getServletContext().getRealPath("/")+"uploads"+File.separator+"project"+File.separator+"files"+File.separator;
				File folder = new File(projectFileFolder);
				if(!folder.exists()){
					folder.mkdirs();
				}
				/*
				 * 	执行文件上传.
				 * 
				 * */
				String aFileName = fileToUpload.getOriginalFilename();
				String zFileName = aFileName;
				File targetFileA = new File(projectFileFolder+File.separator+aFileName);
				if(targetFileA.exists()){
					zFileName = new Date().getTime()+aFileName.substring(aFileName.lastIndexOf("."),aFileName.length());
				}
				File targetFileB = new File(projectFileFolder+File.separator+zFileName);
				fileToUpload.transferTo(targetFileB);
				/*
				 * 	保存记录.
				 * 
				 * */
				jdbcTemplate.execute("DELETE FROM PROJECT_UPLOAD_FILE WHERE PROJECT_ID = "+projectId);
				String sql = "";
				sql+="INSERT INTO PROJECT_UPLOAD_FILE(";
				sql+="	  ID,PROJECT_ID,FILE_NAMES,FILE_ALIAS";
				sql+=")VALUES(";
				sql+="	  SEQ_PROJECT_UPLOAD_FILE.NEXTVAL,"+projectId+",'"+aFileName+"','"+zFileName+"'";
				sql+=")";
				jdbcTemplate.execute(sql);
			}else{
				resultObject.put("SUCCESS",false);
				resultObject.put("MESSAGE","服务端异常，请联系系统管理员.");
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
			resultObject.put("MESSAGE","服务端异常，请联系系统管理员.");
		}finally{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	上传照片./DataCenter/uploads/project/image/
	 * 
	 * */
	@RequestMapping(value="/uploadImage.ilf",method=RequestMethod.POST)
	public void uploadImage(
		@RequestParam("imageUpload")MultipartFile imageUpload,
		@RequestParam String projectId,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			if(!imageUpload.isEmpty()){
				/*
				 * 	验证是否有目录.
				 * 
				 * */
				String projectImageFolder = request.getSession().getServletContext().getRealPath("/")+"uploads"+File.separator+"project"+File.separator+"image"+File.separator;
				File folder = new File(projectImageFolder);
				if(!folder.exists()){
					folder.mkdirs();
				}
				/*
				 * 	执行文件上传.
				 * 
				 * */
				String aFileName = imageUpload.getOriginalFilename();
				String zFileName = new Date().getTime()+aFileName.substring(aFileName.lastIndexOf("."),aFileName.length());
				File targetFileB = new File(projectImageFolder+File.separator+zFileName);
				imageUpload.transferTo(targetFileB);
				/*
				 * 	保存记录
				 * 
				 * */
				String sql = "";
				sql+="INSERT INTO PROJECT_UPLOAD_IMAGE(";
				sql+="	  ID,PROJECT_ID,IMAGE_NAME";
				sql+=")VALUES(";
				sql+="	  SEQ_PROJECT_UPLOAD_IMAGE.NEXTVAL,"+projectId+",'"+zFileName+"'";
				sql+=")";
				jdbcTemplate.execute(sql);
			}else{
				resultObject.put("SUCCESS",false);
				resultObject.put("MESSAGE","服务端异常，请联系系统管理员.");
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
			resultObject.put("MESSAGE","服务端异常，请联系系统管理员.");
		}finally{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	发起任务
	 * 
	 * */
	@RequestMapping(value="/startNewJob.ilf")
	public void startNewJob(
		@RequestParam String projectId,
		@RequestParam String nextOperateUser,
		@RequestParam String flowNode,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			jdbcTemplate.execute("DELETE FROM PROJECT_JOB WHERE PROJECT_ID = "+projectId);
			JSONObject jsonObject = JSONObject.fromObject(flowNode);
			String sql = "";
			sql+="INSERT INTO PROJECT_JOB(";
			sql+="	  ID,PROJECT_ID,CREATE_DATE,WAITING_USER,CURRENT_NODE,NODE_JSON";
			sql+=")VALUES(";
			sql+="	  SEQ_PROJECT_JOB.NEXTVAL,"+projectId+",SYSDATE,"+nextOperateUser+",'"+jsonObject.getString("name")+"','"+jsonObject.toString()+"'";
			sql+=")";
			jdbcTemplate.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
			resultObject.put("MESSAGE","服务端异常，请联系系统管理员.");
		}finally{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	传递任务.
	 * 
	 * */
	@RequestMapping(value="/nextChainNode.ilf")
	public void nextChainNode(
		@RequestParam String projectId,
		@RequestParam String nextOperateUser,
		@RequestParam String flowNode,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			String sql = "";
			if(nextOperateUser!=null && "-1".equals(nextOperateUser)){
				/*
				 * 	已不存在下一环节、外验完毕.
				 * 
				 * */
				sql+="UPDATE PROJECT_JOB ";
				sql+="SET ";
				sql+="	  WAITING_USER = -1,";
				sql+="	  CURRENT_NODE = '已外验',";
				sql+="	  CONTENTS = NULL,";
				sql+="	  NODE_JSON = NULL ";
				sql+="WHERE PROJECT_ID = "+projectId;
			}else if(nextOperateUser!=null && "-2".equals(nextOperateUser)){
				Map<String,Object> nowNode = jdbcTemplate.queryForList("SELECT * FROM PROJECT_JOB WHERE PROJECT_ID = "+projectId).get(0);
				/*
				 * 	驳回至指定节点.
				 * 
				 * */
				JSONObject jsonObject = JSONObject.fromObject(flowNode);
				sql+="UPDATE PROJECT_JOB ";
				sql+="SET ";
				sql+="	  WAITING_USER = -1,";
				sql+="	  CURRENT_NODE = '"+nowNode.get("CURRENT_NODE").toString()+"驳回',";
				sql+="	  CONTENTS = '"+jsonObject.get("rejectContent").toString()+"',";
				sql+="	  NODE_JSON = '"+jsonObject.toString()+"' ";
				sql+="WHERE PROJECT_ID = "+projectId;
			}else{
				/*
				 * 	正常进入下一环节.
				 * 
				 * */
				JSONObject jsonObject = JSONObject.fromObject(flowNode);
				sql+="UPDATE PROJECT_JOB ";
				sql+="SET ";
				sql+="	  WAITING_USER = "+nextOperateUser+",";
				sql+="	  CURRENT_NODE = '"+jsonObject.getString("name")+"',";
				sql+="	  CONTENTS = NULL,";
				sql+="	  NODE_JSON = '"+jsonObject.toString()+"' ";
				sql+="WHERE PROJECT_ID = "+projectId;
			}
			jdbcTemplate.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
			resultObject.put("MESSAGE","服务端异常，请联系系统管理员.");
		}finally{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	下载文件./DataCenter/uploads/project/files/
	 * 
	 * */
	@RequestMapping("/downloadFile.ilf")
	public void downloadApp(
		@RequestParam String projectId,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		String fileName = jdbcTemplate.queryForList("SELECT * FROM PROJECT_UPLOAD_FILE WHERE PROJECT_ID = "+projectId).get(0).get("FILE_ALIAS").toString();
		String strDirPath = request.getSession().getServletContext().getRealPath("/")+"uploads"+File.separator+"project"+File.separator+"files"+File.separator+fileName;
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
}
