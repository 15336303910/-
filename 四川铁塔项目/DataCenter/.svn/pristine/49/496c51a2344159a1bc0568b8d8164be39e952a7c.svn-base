package com.function.rules.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.function.rules.service.RuleDetailService;
import com.function.rules.service.RuleJobService;
import com.function.rules.util.StyleUtil;
import com.systemConfig.model.DataTableResult;
@Controller("com.function.rules.action.TaskMonitorAction")
@RequestMapping(value="/taskMonitorAction")
public class TaskMonitorAction{

	@Autowired
	private RuleJobService ruleJobService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private RuleDetailService ruleDetailService;
	
	/*
	 * 	查询列表
	 * 
	 * */
	@RequestMapping("/findItems.ilf")
	public void findItems(@RequestParam String tableparam,@RequestParam String conditions,HttpServletRequest request,HttpServletResponse response)throws Exception{
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
			sql+="SELECT B.ID,A.ID AS RULE_ID,A.RULE_NAME,B.START_TIME,B.END_TIME,B.IS_FINISHED,B.RECORD_TOTAL,B.FATUAL_TOTAL,100-B.NORMAL_RATIO AS FATUAL_RATIO ";
			sql+="FROM RULE_DETAIL A,RULE_JOB B ";
			sql+="WHERE A.ID = B.RULE_ID AND A.CREATE_USER = "+getLoginUserId(request);
			if(conditonMap.containsKey("IS_FINISHED") && !"".equals(conditonMap.get("IS_FINISHED").toString())){
				sql+=" AND B.IS_FINISHED = '"+conditonMap.get("IS_FINISHED").toString()+"'";
			}
			/*
			 * 	统计数量
			 * 
			 * */
			Integer count = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM("+sql+")");
			/*
			 * 	分页SQL
			 * 
			 * */
			String pageSql = "";
			pageSql+="SELECT W.* FROM (";
			pageSql+="	 SELECT M.*,ROWNUM AS RN FROM(";
			pageSql+="		"+sql;
			pageSql+="	 ) M WHERE ROWNUM <= "+(displayStart+iDisplayLength);
			pageSql+=") W WHERE W.RN > "+displayStart;
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
	 * 	问题数据列表
	 * 
	 * */
	@RequestMapping("/findProblems.ilf")
	public void findProblems(@RequestParam String tableparam,@RequestParam String conditions,HttpServletResponse response)throws Exception{
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
		 * 	问题数量
		 * 
		 * */
		Integer count = Integer.parseInt(conditonMap.get("COUNTS").toString());	
		/*
		 * 	基础分页查询SQL
		 * 
		 * */
		Integer lastIndex = displayStart+iDisplayLength;
		String sql = "";
		sql+="SELECT PRIMARY_VALUE,NAME_VALUE,LISTAGG(PROBLEM_DESC,';') WITHIN GROUP(ORDER BY PRIMARY_VALUE ASC) AS PROBLEM_DESCRIBE FROM(";
		sql+="    SELECT * FROM RULE_JOB_DATA WHERE JOB_ITEM_ID IN(";
		sql+="		  SELECT ID AS ITEM_ID FROM RULE_JOB_ITEM WHERE JOB_ID IN (";
		sql+="			  SELECT ID FROM RULE_JOB WHERE RULE_ID = "+conditonMap.get("RULE_ID").toString();
		sql+="		  )";
		sql+="	  )AND PRIMARY_VALUE IN (";
		sql+="		  SELECT F.PRIMARY_VALUE FROM(";
		sql+="			  SELECT W.*, ROWNUM AS RN FROM(";
		sql+="				  SELECT DISTINCT PRIMARY_VALUE FROM RULE_JOB_DATA WHERE JOB_ITEM_ID IN(";
		sql+="					  SELECT ID AS ITEM_ID FROM RULE_JOB_ITEM WHERE JOB_ID IN(";
		sql+="						   SELECT ID FROM RULE_JOB WHERE RULE_ID = "+conditonMap.get("RULE_ID").toString();
		sql+="					  )";
		sql+="				  )";
		if(conditonMap.get("NAME_VALUE")!=null && !"".equals(conditonMap.get("NAME_VALUE").toString())){
        	sql+=" AND NAME_VALUE LIKE '%"+conditonMap.get("NAME_VALUE").toString()+"%'";
        }
        if(conditonMap.get("PROBLEM_DESC")!=null && !"".equals(conditonMap.get("PROBLEM_DESC").toString())){
        	sql+=" AND PROBLEM_DESC LIKE '%"+conditonMap.get("PROBLEM_DESC").toString()+"%'";
        }	
		sql+="			  ) W WHERE ROWNUM <= "+lastIndex;
		sql+="		 ) F WHERE RN > "+displayStart;
		sql+="	  )";
		sql+=")GROUP BY PRIMARY_VALUE, NAME_VALUE";
		List<Map<String,Object>> datas = jdbcTemplate.queryForList(sql);	
		DataTableResult<Map<String,Object>> tableData = new DataTableResult<Map<String,Object>>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(datas);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	获取错误类型
	 * 
	 * */
	@RequestMapping("/selectTypes.ilf")
	public void selectTypes(@RequestParam String ruleCode,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			String sql = "";
			sql+="SELECT DISTINCT(PROBLEM_DESC) FROM (";
			sql+="	SELECT * FROM RULE_JOB_DATA WHERE JOB_ITEM_ID IN(";
			sql+="		SELECT ID AS ITEM_ID FROM RULE_JOB_ITEM WHERE JOB_ID IN(";
			sql+="			SELECT ID FROM RULE_JOB WHERE RULE_ID = "+ruleCode;
			sql+="		)";
			sql+="	)ORDER BY JOB_ITEM_ID ASC,PRIMARY_VALUE ASC";
			sql+=")";
			List<Map<String,Object>> items = jdbcTemplate.queryForList(sql);
			resultObject.put("options",items);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	移除任务监控
	 * 
	 * */
	@RequestMapping("/deleteMonitor.ilf")
	public void deleteMonitor(@RequestParam String jobId,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			List<Map<String,Object>> jobs = jdbcTemplate.queryForList("SELECT * FROM RULE_JOB WHERE ID = "+jobId);
			if(jobs.size()>0){
				String RULE_ID = jobs.get(0).get("RULE_ID").toString();
				/*
				 * 	RULE_JOB_DATA
				 * 
				 * */
				String dataDelete = "";
				dataDelete+="DELETE FROM RULE_JOB_DATA WHERE JOB_ITEM_ID IN(";
				dataDelete+="	SELECT ID AS ITEM_ID FROM RULE_JOB_ITEM WHERE JOB_ID IN(";
				dataDelete+="		SELECT ID AS JOB_ID FROM RULE_JOB WHERE RULE_ID = "+RULE_ID;
				dataDelete+="	)";
				dataDelete+=")";
				jdbcTemplate.execute(dataDelete);
				/*
				 * 	RULE_JOB_ITEM
				 * 
				 * */
				String itemDelete = "";
				itemDelete+="DELETE FROM RULE_JOB_ITEM WHERE JOB_ID IN(";
				itemDelete+="	SELECT ID AS JOB_ID FROM RULE_JOB WHERE RULE_ID = "+RULE_ID;
				itemDelete+=")";
				jdbcTemplate.execute(itemDelete);
				/*
				 * 	RULE_JOB
				 * 
				 * */
				jdbcTemplate.execute("DELETE FROM RULE_JOB WHERE RULE_ID = "+RULE_ID);
				/*
				 * 	修改RULE_DETAIL里的状态
				 * 
				 * */
				jdbcTemplate.execute("UPDATE RULE_DETAIL SET LAST_ACTION_TIME = NULL,FILE_REPORT = NULL WHERE ID = "+RULE_ID);
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	资源数据下载.
	 * 
	 * */
	@RequestMapping("/exportData.ilf")
	public void exportData(
		@RequestParam String ruleCode,
		@RequestParam String fatalType,
		@RequestParam String keyWord,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		try{
			fatalType = java.net.URLDecoder.decode(fatalType,"UTF-8");
			keyWord = java.net.URLDecoder.decode(keyWord,"UTF-8");
			String sql = "";
			sql+="SELECT * FROM RULE_JOB_DATA WHERE JOB_ITEM_ID IN(";
			sql+="	  SELECT ID AS RULE_JOB_ITEM_ID FROM RULE_JOB_ITEM WHERE JOB_ID IN(";
			sql+="	  	  SELECT ID AS RULE_JOB_ID FROM RULE_JOB WHERE RULE_ID = "+ruleCode;
			sql+="	  )";
			sql+=")";
			if(fatalType!=null && !"".equals(fatalType) && !"-".equals(fatalType)){
				sql+=" AND PROBLEM_DESC = '"+fatalType+"'";
			}
			if(keyWord!=null && !"".equals(keyWord) && !"-".equals(keyWord)){
				sql+=" AND NAME_VALUE LIKE '%"+keyWord+"%'";
			}
			List<Map<String,Object>> objs = jdbcTemplate.queryForList(sql);
			XSSFSheet xssfSheet = null;
	        FileInputStream fileInputStreams = new FileInputStream(new File(
	        	jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'dataExportTemplate'").get("PRO_VALUE").toString()
	        ));
	        XSSFWorkbook workBook = new XSSFWorkbook(fileInputStreams);      
	        Iterator<XSSFSheet> sheetCar = workBook.iterator();
	        while(sheetCar.hasNext()){
	        	xssfSheet = (XSSFSheet)sheetCar.next();
	        	break;
	        }
	        XSSFCellStyle leftWhites = StyleUtil.getStyle(workBook,HSSFCellStyle.ALIGN_LEFT,HSSFColor.WHITE.index);
	        XSSFCellStyle centerGray = StyleUtil.getStyle(workBook,HSSFCellStyle.ALIGN_CENTER_SELECTION,HSSFColor.GREY_25_PERCENT.index);
	        /*表头*/
	        Map<String,Object> modelMap = jdbcTemplate.queryForMap("SELECT TABLE_ALIAS,TABLE_NAME FROM BASIC_DB_TABLE WHERE ID IN(SELECT BIND_TABLE FROM RULE_DETAIL WHERE ID = "+ruleCode+")");
	        Map<String,Object> rulesMap = jdbcTemplate.queryForMap("SELECT ID AS RULE_ID,BIND_TABLE AS MODEL_ID,RULE_NAME FROM RULE_DETAIL WHERE ID = "+ruleCode);
	        XSSFRow tableHead = xssfSheet.createRow(1);
	        tableHead.setHeight(new Integer(430).shortValue());
	        for(int r=1;r<=4;r++){
				XSSFCell cell = tableHead.createCell(r);
				cell.setCellStyle(centerGray);
			}
	        xssfSheet.addMergedRegion(new CellRangeAddress(1,1,1,4));
	        xssfSheet.getRow(1).getCell(1).setCellValue(
	        	modelMap.get("TABLE_ALIAS").toString()+"("+modelMap.get("TABLE_NAME").toString()+")["+rulesMap.get("RULE_NAME").toString()+"]"
	        );
	        /*字段列表*/
	        XSSFRow columnHead = xssfSheet.createRow(2);
	        columnHead.setHeight(new Integer(430).shortValue());
	        for(int r=1;r<=4;r++){
	        	XSSFCell hCell = columnHead.createCell(r);
	        	hCell.setCellStyle(leftWhites);
	        	if(r==1){
	        		hCell.setCellValue("资源编号");
	        	}else if(r==2){
	        		hCell.setCellValue("资源名称");
	        	}else if(r==3){
	        		hCell.setCellValue("资源数据问题描述");
	        	}else if(r==4){
	        		hCell.setCellValue("原值");
	        	}
	        	xssfSheet.setColumnWidth(r,7000);
			}
	        /*渲染具体的数据*/
	        Integer currentRowIndex = 3;
	        for(int v=0;v<objs.size();v++){
	        	Map<String,Object> dataMap = objs.get(v);
	        	XSSFRow dataRow = xssfSheet.createRow(currentRowIndex);
				dataRow.setHeight(new Integer(430).shortValue());
				for(int r=1;r<=4;r++){
		        	XSSFCell dCell = dataRow.createCell(r);
		        	dCell.setCellStyle(leftWhites);
		        	if(r==1){
		        		dCell.setCellValue(dataMap.get("PRIMARY_VALUE")==null?"":dataMap.get("PRIMARY_VALUE").toString());
		        	}else if(r==2){
		        		dCell.setCellValue(dataMap.get("NAME_VALUE")==null?"":dataMap.get("NAME_VALUE").toString());
		        	}else if(r==3){
		        		dCell.setCellValue(dataMap.get("PROBLEM_DESC")==null?"":dataMap.get("PROBLEM_DESC").toString());
		        	}else if(r==4){
		        		dCell.setCellValue(dataMap.get("COLUMN_VALUE")==null?"":dataMap.get("COLUMN_VALUE").toString());
		        	}
				}
				currentRowIndex++;
	        }
			String fileName = new Date().getTime()+".xlsx";
			FileOutputStream fileOutputStream = new FileOutputStream(new File(
				jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'reportFolder'").get("PRO_VALUE").toString()+fileName)
			);
			workBook.write(fileOutputStream);
			fileOutputStream.close();
			fileInputStreams.close();
			File dataFile = new File(
				jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'reportFolder'").get("PRO_VALUE").toString()+fileName
			);
			if(dataFile.exists()){
				FileInputStream fileInputStream = new FileInputStream(dataFile);
				long filesize = dataFile.length();
				response.setContentType("text/html;charset=gb2312");
				response.addHeader("content-type","application/x-msdownload;");
				response.setHeader("Content-Disposition","attachment;filename=\""+new String((dataFile.getName()).getBytes(),"ISO8859-1"));
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
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Integer getLoginUserId(HttpServletRequest request){
		Object loginObj = request.getSession().getAttribute("LoginUserInfo");
		if(loginObj!=null){
			Map<String,Object> userObject = (Map<String,Object>)loginObj;
			return Integer.parseInt(userObject.get("ID").toString());
		}else{
			return null;
		}
	}
}
