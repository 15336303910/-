package com.function.compare.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.function.compare.model.CompareColumn;
import com.function.compare.model.CompareData;
import com.function.compare.model.CompareDetail;
import com.function.compare.model.CompareQuartz;
import com.function.compare.service.CompareColumnService;
import com.function.compare.service.CompareDataService;
import com.function.compare.service.CompareDetailService;
import com.function.compare.service.CompareMonitorService;
import com.function.compare.service.CompareQuartzService;
import com.function.dbmanage.service.BasicDbService;
import com.function.dbmanage.service.BasicDbTableService;
import com.function.rules.util.StyleUtil;
import com.system.LoginUserUtil;
import com.systemConfig.model.DataTableResult;
@Controller("com.function.compare.action.CompareDetailAction")
@RequestMapping(value="/compareDetailAction")
public class CompareDetailAction{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CompareDetailService compareDetailService;
	
	@Autowired
	private CompareQuartzService compareQuartzService;
	
	@Autowired
	private CompareColumnService compareColumnService;
	
	@Autowired
	private BasicDbService basicDbService;
	
	@Autowired
	private BasicDbTableService basicDbTableService;
	
	@Autowired
	private CompareMonitorService compareMonitorService;
	
	@Autowired
	private CompareDataService compareDataService;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	/*
	 * 	保存/修改
	 * 
	 * */
	@RequestMapping("/saveAudit.ilf")
	public void saveAudit(@RequestParam String params,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'比对规则配置成功.'}");
		Integer newDetailCode = -1;
		String connectColumn = "";
		try{
			final JSONObject jsonObject = JSONObject.fromObject(params);
			connectColumn = jsonObject.getString("CONNECT_COLUMN");
			/*
			 * 	保存基本信息
			 * 
			 * */
			CompareDetail editingDetail = new CompareDetail(jsonObject);
			if(Integer.parseInt(jsonObject.get("RULE_ID").toString())!=-1){
				CompareDetail oldDetail = compareDetailService.selectOne(Integer.parseInt(jsonObject.get("RULE_ID").toString()));
				editingDetail.setRULE_CODE(oldDetail.getRULE_CODE());
				editingDetail.setLAST_ACTION_DATE(oldDetail.getLAST_ACTION_DATE());
				editingDetail.setID(oldDetail.getID());
				compareDetailService.updateModel(editingDetail);
				newDetailCode = editingDetail.getID();
				/*
				 * 	删除配套的信息
				 * 
				 * */
				jdbcTemplate.execute("DELETE FROM COMPARE_EXEC_QUARTZ WHERE BELONG_RULE = "+newDetailCode);
				jdbcTemplate.execute("DELETE FROM COMPARE_COLUMN WHERE BELONG_COMPARE_RULE = "+newDetailCode);
			}else{
				Date date = new Date();
				String ruleCode = "RULE-"+date.getTime();
				editingDetail.setRULE_CODE(ruleCode);
				newDetailCode = compareDetailService.insertModel(editingDetail);
			}
			/*
			 * 	保存调度配置
			 * 
			 * */
			JSONObject quartzObj = jsonObject.getJSONObject("QUARTZ_DETAIL");
			CompareQuartz compareQuartz = new CompareQuartz(newDetailCode,quartzObj);
			compareQuartzService.insertModel(compareQuartz);
			/*
			 * 	保存字段对应信息
			 * 
			 * */
			JSONArray matchColumns = jsonObject.getJSONArray("MATCH_COLUMNS");
			for(int i=0;i<matchColumns.size();i++){
				JSONObject matchColumn = matchColumns.getJSONObject(i);				
				CompareColumn compareColumn = new CompareColumn();
				compareColumn.setBELONG_COMPARE_RULE(newDetailCode);
				compareColumn.setA_COLUMN_ID(Integer.parseInt(matchColumn.getString("ID")));
				compareColumn.setA_COLUMN_NAME(matchColumn.getString("COLUMN_NAME"));
				Map<String,Object> matchedColumn = jdbcTemplate.queryForMap("SELECT * FROM BASIC_DB_COLUMN WHERE BELONG_TABLE = "+editingDetail.getZ_TABLE_ID()+" AND COLUMN_NAME = '"+matchColumn.getString("MATCHED_COLUMN")+"'");
				if(matchedColumn!=null){
					compareColumn.setZ_COLUMN_ID(Integer.parseInt(matchedColumn.get("ID").toString()));
				}else{
					compareColumn.setZ_COLUMN_ID(-1);
				}
				compareColumn.setZ_COLUMN_NAME(matchColumn.getString("MATCHED_COLUMN"));
				if(connectColumn.equals(compareColumn.getA_COLUMN_NAME())){
					compareColumn.setIS_CONNECT_COLUMN("Y");
				}else{
					compareColumn.setIS_CONNECT_COLUMN("N");
				}
				compareColumnService.insertModel(compareColumn);
			}
			resultObject.put("editingRuleCode",newDetailCode);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
			resultObject.put("message","系统异常.");
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}	
	}
	
	
	/*
	 * 	获取一个详情
	 * 
	 * */
	@RequestMapping("/findOne.ilf")
	public void findOne(@RequestParam String compareRuleId,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			CompareDetail compareDetail = compareDetailService.selectOne(Integer.parseInt(compareRuleId));
			/*比对规则名称*/
			resultObject.put("RULE_NAME",compareDetail.getRULE_NAME());
			/*比对规则描述*/
			resultObject.put("RULE_DESC",compareDetail.getRULE_DESC());
			/*是否在用*/
			resultObject.put("IS_USING",compareDetail.getIS_USING());
			/*是否增量比对*/
			resultObject.put("IS_INCREASE",compareDetail.getIS_INCREASE());
			/*核查表编号、名称*/
			resultObject.put("A_BELONG_DB",basicDbTableService.selectModel(compareDetail.getA_TABLE_ID()).getBELONG_DB());
			resultObject.put("A_TABLE_CODE",compareDetail.getA_TABLE_ID());
			resultObject.put("A_TABLE_NAME",basicDbTableService.selectModel(compareDetail.getA_TABLE_ID()).getTABLE_ALIAS());
			/*参照表编号、名称*/
			resultObject.put("Z_BELONG_DB",basicDbTableService.selectModel(compareDetail.getZ_TABLE_ID()).getBELONG_DB());
			resultObject.put("Z_TABLE_CODE",compareDetail.getZ_TABLE_ID());
			resultObject.put("Z_TABLE_NAME",basicDbTableService.selectModel(compareDetail.getZ_TABLE_ID()).getTABLE_ALIAS());
			/*是否核查一致性、是否核查仅A端有数据的情况、是否核查仅Z端有数据的情况*/
			resultObject.put("IS_UNIFORM",compareDetail.getIS_UNIFORM());
			resultObject.put("IS_A_ONLY",compareDetail.getIS_A_ONLY());
			resultObject.put("IS_Z_ONLY",compareDetail.getIS_Z_ONLY());
			resultObject.put("IS_A_FATUAL",compareDetail.getIS_A_FATUAL());
			resultObject.put("IS_Z_FATUAL",compareDetail.getIS_Z_FATUAL());
			/*效用字段*/
			Map<String,Object> columnObject = jdbcTemplate.queryForMap("SELECT * FROM BASIC_DB_COLUMN WHERE ID IN(SELECT A_COLUMN_ID FROM COMPARE_COLUMN WHERE BELONG_COMPARE_RULE = "+compareRuleId+" AND IS_CONNECT_COLUMN = 'Y')");
			resultObject.put("CONNECT_COLUMN_ALIAS",columnObject.get("COLUMN_ALIAS").toString());
			resultObject.put("CONNECT_COLUMN_NAME",columnObject.get("COLUMN_NAME").toString());
			/*匹配列表*/
			String columnSql = "";
			columnSql+="SELECT S.A_COLUMN_ID,T.COLUMN_NAME AS A_COLUMN_NAME,T.COLUMN_ALIAS AS A_COLUMN_ALIAS,S.Z_COLUMN_ID,Z.COLUMN_NAME AS Z_COLUMN_NAME,Z.COLUMN_ALIAS AS Z_COLUMN_ALIAS FROM (";
			columnSql+="	SELECT A_COLUMN_ID,Z_COLUMN_ID FROM COMPARE_COLUMN WHERE BELONG_COMPARE_RULE = "+compareRuleId;
			columnSql+=") S,BASIC_DB_COLUMN T,BASIC_DB_COLUMN Z WHERE S.A_COLUMN_ID = T.ID AND S.Z_COLUMN_ID = Z.ID";
			List<Map<String,Object>> matchedColumns = jdbcTemplate.queryForList(columnSql);
			resultObject.put("MATCHED_COLUMNS",matchedColumns);
			/*调度详情*/
			if("Y".equals(compareDetail.getIS_QUARTZ())){
				Map<String,Object> quartzObject = jdbcTemplate.queryForMap("SELECT * FROM COMPARE_EXEC_QUARTZ WHERE BELONG_RULE = "+compareRuleId);
				resultObject.put("QUARTZ_DETAIL",quartzObject);
			}else{
				resultObject.put("QUARTZ_DETAIL","-");
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
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
		Integer count = compareDetailService.getCount(conditonMap);
		List<CompareDetail> compareDetails = compareDetailService.getDbPage(conditonMap);
		/*封装数据*/
		DataTableResult<CompareDetail> tableData = new DataTableResult<CompareDetail>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(compareDetails);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	查询统计数据
	 * 
	 * */
	@RequestMapping("/findCountItems.ilf")
	public void findCountItems(@RequestParam String tableparam,@RequestParam String conditions,HttpServletRequest request,HttpServletResponse response)throws Exception{
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
		 * 	统计数量SQL
		 * 
		 * */
		String countSql = "SELECT COUNT(1) FROM COMPARE_MONITOR WHERE 1 = 1";
		if(conditonMap.containsKey("BELONG_COMPARE") && conditonMap.get("BELONG_COMPARE")!=null){
			countSql+=" AND BELONG_COMPARE = "+conditonMap.get("BELONG_COMPARE").toString();
		}
		Integer count = jdbcTemplate.queryForInt(countSql);
		/*
		 * 	数据分页SQL
		 * 
		 * */
		String itemSql = "SELECT ID,BELONG_COMPARE,START_DATE,IS_FINISHED,FINISH_DATE,A_ONLY AS AONLY,Z_ONLY AS ZONLY,NOT_UNIFORM,A_FATUAL AS AFATUAL,Z_FATUAL AS ZFATUAL,IS_FATAL AS ISFATAL FROM COMPARE_MONITOR WHERE 1 = 1";
		if(conditonMap.containsKey("BELONG_COMPARE") && conditonMap.get("BELONG_COMPARE")!=null){
			itemSql+=" AND BELONG_COMPARE = "+conditonMap.get("BELONG_COMPARE").toString();
		}
		itemSql+=" ORDER BY START_DATE DESC";
		List<Map<String,Object>> monitors = jdbcTemplate.queryForList(itemSql);
		Boolean IS_PROVINCE = loginUserUtil.isProvince(request);
		if(!IS_PROVINCE && monitors.size()>0){
			String BELONG_AREA = loginUserUtil.getBelongArea(request);
			for(int i=0;i<monitors.size();i++){
				Map<String,Object> thisMonitor = monitors.get(i);
				String MONITOR_ID = thisMonitor.get("ID").toString();
				/*
				 * 	某地市[数据不一致]的数据
				 * 
				 * */
				List<Map<String,Object>> aaas = jdbcTemplate.queryForList("SELECT * FROM COMPARE_CITY_COUNT WHERE MONITOR_ID = "+MONITOR_ID+" AND CITY_NAME LIKE '%"+BELONG_AREA+"%' AND FATAL_TYPE = 'IS_UNIFORM'");
				if(aaas.size()>0){
					monitors.get(i).put("NOT_UNIFORM",Integer.parseInt(aaas.get(0).get("FATAL_COUNT").toString()));
				}else{
					monitors.get(i).put("NOT_UNIFORM",0);
				}
				/*
				 * 	某地市[仅A端有数据]的数据
				 * 
				 * */
				List<Map<String,Object>> bbbs = jdbcTemplate.queryForList("SELECT * FROM COMPARE_CITY_COUNT WHERE MONITOR_ID = "+MONITOR_ID+" AND CITY_NAME LIKE '%"+BELONG_AREA+"%' AND FATAL_TYPE = 'IS_A_ONLY'");
				if(bbbs.size()>0){
					monitors.get(i).put("AONLY",Integer.parseInt(bbbs.get(0).get("FATAL_COUNT").toString()));
				}else{
					monitors.get(i).put("AONLY",0);
				}
				/*
				 * 	某地市[仅Z端有数据]的数据
				 * 
				 * */
				List<Map<String,Object>> cccs = jdbcTemplate.queryForList("SELECT * FROM COMPARE_CITY_COUNT WHERE MONITOR_ID = "+MONITOR_ID+" AND CITY_NAME LIKE '%"+BELONG_AREA+"%' AND FATAL_TYPE = 'IS_Z_ONLY'");
				if(cccs.size()>0){
					monitors.get(i).put("ZONLY",Integer.parseInt(cccs.get(0).get("FATAL_COUNT").toString()));
				}else{
					monitors.get(i).put("ZONLY",0);
				}
				/*
				 * 	某地市[A端数据异常]的数据
				 * 
				 * */
				List<Map<String,Object>> ddds = jdbcTemplate.queryForList("SELECT * FROM COMPARE_CITY_COUNT WHERE MONITOR_ID = "+MONITOR_ID+" AND CITY_NAME LIKE '%"+BELONG_AREA+"%' AND FATAL_TYPE = 'IS_A_FATAL'");
				if(ddds.size()>0){
					monitors.get(i).put("AFATUAL",Integer.parseInt(ddds.get(0).get("FATAL_COUNT").toString()));
				}else{
					monitors.get(i).put("AFATUAL",0);
				}
				/*
				 * 	某地市[Z端数据异常]的数据
				 * 
				 * */
				List<Map<String,Object>> eees = jdbcTemplate.queryForList("SELECT * FROM COMPARE_CITY_COUNT WHERE MONITOR_ID = "+MONITOR_ID+" AND CITY_NAME LIKE '%"+BELONG_AREA+"%' AND FATAL_TYPE = 'IS_Z_FATAL'");
				if(eees.size()>0){
					monitors.get(i).put("ZFATUAL",Integer.parseInt(eees.get(0).get("FATAL_COUNT").toString()));
				}else{
					monitors.get(i).put("ZFATUAL",0);
				}
			}
		}
		/*封装数据*/
		DataTableResult<Map<String,Object>> tableData = new DataTableResult<Map<String,Object>>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(monitors);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	由[监控编号]+[错误类型]获取问题数据.
	 * 
	 * */
	@RequestMapping("/findProblems.ilf")
	public void findProblems(
		@RequestParam String tableparam,
		@RequestParam String conditions,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
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
		Boolean IS_PROVINCE = loginUserUtil.isProvince(request);
		if(!IS_PROVINCE){
			String CITY_NAME = loginUserUtil.getBelongArea(request);
			conditonMap.put("DATA_CITY",CITY_NAME);
		}
		String sql = "";
		sql+="SELECT DATA_CITY,PRIMARY_VALUE,RESOURCE_NAME,PROBLEM_DESC ";
		sql+="FROM COMPARE_DATA ";
		sql+="WHERE 1 = 1";
		if(!IS_PROVINCE){
			sql+=" AND DATA_CITY LIKE '%"+conditonMap.get("DATA_CITY").toString()+"%'";
		}
		if(conditonMap.containsKey("BELONG_MONITOR") && !"".equals(conditonMap.get("BELONG_MONITOR").toString())){
			sql+=" AND BELONG_MONITOR = "+conditonMap.get("BELONG_MONITOR").toString();
		}
		if(conditonMap.containsKey("RESOURCE_NAME") && !"".equals(conditonMap.get("RESOURCE_NAME").toString())){
			sql+=" AND RESOURCE_NAME '%"+conditonMap.get("RESOURCE_NAME").toString()+"%'";
		}
		if(conditonMap.containsKey("PROBLEM_TYPE") && !"".equals(conditonMap.get("PROBLEM_TYPE").toString())){
			sql+=" AND PROBLEM_TYPE = '"+conditonMap.get("PROBLEM_TYPE").toString()+"'";
		}
		sql+=" ORDER BY ID ASC";
		/*
		 * 	统计总数.
		 * 
		 * */
		Integer count = 0;
		if(conditonMap.containsKey("FATAL_COUNT") && !"".equals(conditonMap.get("FATAL_COUNT").toString())){
			count = Integer.parseInt(conditonMap.get("FATAL_COUNT").toString());
		}else{
			count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM ("+sql+")");
		}
		/*
		 * 	分页.
		 * 
		 * */
		Integer iDisplayStart = Integer.parseInt(conditonMap.get("iDisplayStart").toString());
		Integer iDisplayLimit = iDisplayStart+Integer.parseInt(conditonMap.get("iDisplayLength").toString()); 
		String pageSql = "";
		pageSql+="SELECT B.* FROM(";
		pageSql+="	  SELECT A.*,ROWNUM AS RN FROM("+sql+") A WHERE ROWNUM <= "+iDisplayLimit;
		pageSql+=") B WHERE B.RN > "+iDisplayStart;
		System.out.println("查找问题数据开始："+pageSql);
		List<Map<String,Object>> compareDatas = jdbcTemplate.queryForList(pageSql);
		System.out.println("查找问题数据结束.");
		/*封装数据*/
		DataTableResult<Map<String,Object>> tableData = new DataTableResult<Map<String,Object>>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(compareDatas);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	查询统计数据
	 * 
	 * */
	@RequestMapping("/findProDetailsOfCenter.ilf")
	public void findProDetailsOfCenter(
		@RequestParam String tableparam,
		@RequestParam String conditions,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
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
		String sql = "";
		sql+="SELECT A.*,ROWNUM AS RN FROM COMPARE_DATA A WHERE A.BELONG_MONITOR IN(";
		sql+="	  SELECT ID AS MONITOR_ID FROM COMPARE_MONITOR WHERE ID IN(";
		sql+="		  SELECT MONITOR_ID FROM COMPARE_SUMMARY1 WHERE CITY_NAME = '"+conditonMap.get("CITY_NAME").toString()+"' AND C_DATE = '"+conditonMap.get("COUNT_DATE").toString()+"' AND DATA = '"+conditonMap.get("DATA_TYPE").toString()+"'";
		sql+="	  )";
		sql+=") AND A.DATA_CITY LIKE '%"+conditonMap.get("CITY_NAME").toString()+"%'";
		if(conditonMap.containsKey("FATAL_DATA_TYPE") && !"".equals(conditonMap.get("FATAL_DATA_TYPE").toString()) && !"-".equals(conditonMap.get("FATAL_DATA_TYPE").toString())){
			sql+=" AND A.PROBLEM_TYPE = '"+conditonMap.get("FATAL_DATA_TYPE").toString()+"'";
		}
		if(conditonMap.containsKey("RES_NAME") && !"".equals(conditonMap.get("RES_NAME").toString()) && !"-".equals(conditonMap.get("RES_NAME").toString())){
			sql+=" AND A.RESOURCE_NAME LIKE '%"+conditonMap.get("RES_NAME").toString()+"%'";
		}
		Integer count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM ("+sql+")");
		List<Map<String,Object>> compareDatas = jdbcTemplate.queryForList("SELECT W.* FROM ("+sql+" AND ROWNUM <= "+(displayStart+iDisplayLength)+") W WHERE W.RN > "+displayStart);
		if(compareDatas.size()>0){
			for(int i=0;i<compareDatas.size();i++){
				String BELONG_MONITOR = compareDatas.get(i).get("BELONG_MONITOR").toString();
				compareDatas.get(i).put("COMPARE_RULE_NAME",jdbcTemplate.queryForMap("SELECT RULE_NAME FROM COMPARE_DETAIL WHERE ID = (SELECT BELONG_COMPARE FROM COMPARE_MONITOR WHERE ID = "+BELONG_MONITOR+")").get("RULE_NAME").toString());
			}
		}
		/*封装数据*/
		DataTableResult<Map<String,Object>> tableData = new DataTableResult<Map<String,Object>>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(compareDatas);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	数据中心 --> 数据类型钻取 --> 导出明细
	 * 
	 * */
	@RequestMapping("/exportDetailsInDbCenter.ilf")
	public void exportDetailsInDbCenter(
		@RequestParam String CITY_NAME,
		@RequestParam String COUNT_DATE,
		@RequestParam String DATA_TYPE,
		@RequestParam String FATAL_TYPE,
		@RequestParam String KEY_WORD,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		try{
			CITY_NAME = java.net.URLDecoder.decode(CITY_NAME,"UTF-8");
			DATA_TYPE = java.net.URLDecoder.decode(DATA_TYPE,"UTF-8");
			KEY_WORD = java.net.URLDecoder.decode(KEY_WORD,"UTF-8");
			/*
			 * 	查询数据.
			 * 
			 * */
			String sql = "";
			sql+="SELECT A.*,ROWNUM AS RN FROM COMPARE_DATA A WHERE A.BELONG_MONITOR IN(";
			sql+="	  SELECT ID AS MONITOR_ID FROM COMPARE_MONITOR WHERE ID IN(";
			sql+="		  SELECT MONITOR_ID FROM COMPARE_SUMMARY1 WHERE CITY_NAME = '"+CITY_NAME+"' AND C_DATE = '"+COUNT_DATE+"' AND DATA = '"+DATA_TYPE+"'";
			sql+="	  )";
			sql+=") AND A.DATA_CITY LIKE '%"+CITY_NAME+"%'";
			if(FATAL_TYPE!=null && !"".equals(FATAL_TYPE)){
				sql+=" AND A.PROBLEM_TYPE = '"+FATAL_TYPE+"'";
			}
			if(KEY_WORD!=null && !"".equals(KEY_WORD)){
				sql+=" AND A.RESOURCE_NAME LIKE '%"+KEY_WORD+"%'";
			}
			Map<Integer,String> monitorMap = new HashMap<Integer,String>();
			List<Map<String,Object>> compareDatas = jdbcTemplate.queryForList(sql);
			if(compareDatas.size()>0){
				for(int i=0;i<compareDatas.size();i++){
					String BELONG_MONITOR = compareDatas.get(i).get("BELONG_MONITOR").toString();
					if(monitorMap.containsKey(Integer.parseInt(BELONG_MONITOR))){
						compareDatas.get(i).put("COMPARE_RULE_NAME",monitorMap.get(Integer.parseInt(BELONG_MONITOR)));
					}else{
						compareDatas.get(i).put("COMPARE_RULE_NAME",jdbcTemplate.queryForMap("SELECT RULE_NAME FROM COMPARE_DETAIL WHERE ID = (SELECT BELONG_COMPARE FROM COMPARE_MONITOR WHERE ID = "+BELONG_MONITOR+")").get("RULE_NAME").toString());
						monitorMap.put(Integer.parseInt(BELONG_MONITOR),compareDatas.get(i).get("COMPARE_RULE_NAME").toString());
					}
				}
			}
			/*
			 * 	生成文件.
			 * 
			 * */
			XSSFWorkbook wk = new XSSFWorkbook();
			/*
			 * 	字体（通用）.
			 * 
			 * */
	        XSSFFont font = wk.createFont();
	        font.setFontName("宋体");
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	        font.setFontHeightInPoints((short)8);
	        font.setColor((short)16711680); 
	        /*
			 * 	Style（纵向:居中,横向:居中,底色:浅灰,边框:有）.
			 * 
			 * */
	        XSSFCellStyle titleStyle = wk.createCellStyle();
	        titleStyle.setFont(font);
	        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
	        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);              
	        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);           
	        titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	        /*
			 * 	Style（纵向:居中,横向:居中,底色:浅黄色,边框:有）.
			 * 
			 * */
	        XSSFCellStyle yellowTitleStyle = wk.createCellStyle();
	        yellowTitleStyle.setFont(font);
	        yellowTitleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	        yellowTitleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
	        yellowTitleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);              
	        yellowTitleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        yellowTitleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        yellowTitleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        yellowTitleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);           
	        yellowTitleStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
	        /*
			 * 	Style（纵向:居中,横向:居左,底色:白色,边框:有）.
			 * 
			 * */
	        XSSFCellStyle normalStyle = wk.createCellStyle();
	        normalStyle.setFont(font);
	        normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	        normalStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	        normalStyle.setWrapText(true);
	        normalStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);              
	        normalStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        normalStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        normalStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        normalStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);           
	        normalStyle.setFillForegroundColor(HSSFColor.WHITE.index);
	        /*
			 * 	Style（纵向:居中,横向:居左,底色:白色,边框:无）.
			 * 
			 * */
	        XSSFCellStyle noBorderStyle = wk.createCellStyle();
	        noBorderStyle.setFont(font);
	        noBorderStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	        noBorderStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	        noBorderStyle.setFillForegroundColor(HSSFColor.WHITE.index);
	        /*
	         * 	开始写入数据.
	         * 
	         * */
	        XSSFSheet sheet = wk.createSheet();
	        sheet.setColumnWidth(0,500);
	        sheet.setColumnWidth(1,2000);
	        sheet.setColumnWidth(2,5000);
	        sheet.setColumnWidth(3,6000);
	        sheet.setColumnWidth(4,6000);
	        sheet.setColumnWidth(5,6000);
	        sheet.setColumnWidth(6,10000);
	        /*创建空行*/
	        XSSFRow nullRow = sheet.createRow(0);            
	        nullRow.setHeight(new Integer(200).shortValue());
	        for(int k=0;k<=6;k++){
	            XSSFCell nullCell = nullRow.createCell(k);
	            nullCell.setCellStyle(noBorderStyle);
	        }
	        sheet.addMergedRegion(new CellRangeAddress(0,0,0,6));
	        /*创建标题.*/
	        XSSFRow headRow = sheet.createRow(1);            
	        headRow.setHeight(new Integer(500).shortValue());
	        for(int k=0;k<=6;k++){
	            XSSFCell headCell = headRow.createCell(k);
	            if(k==0){
	            	headCell.setCellStyle(noBorderStyle);
	            	headCell.setCellValue("");
	            }else{
	            	headCell.setCellStyle(titleStyle);
	            	if(k==1){
		            	headCell.setCellValue("所属地市");
		            }else if(k==2){
		            	headCell.setCellValue("资源编号");
		            }else if(k==3){
		            	headCell.setCellValue("资源名称");
		            }else if(k==4){
		            	headCell.setCellValue("问题类型");
		            }else if(k==5){
		            	headCell.setCellValue("所属比对规则");
		            }else if(k==6){
		            	headCell.setCellValue("问题描述");
		            }
	            }
	        }
	        for(int rowIndex=2;rowIndex<(compareDatas.size()+2);rowIndex++){
	        	Map<String,Object> cityMap = compareDatas.get(rowIndex-2);
	        	XSSFRow dataRow = sheet.createRow(rowIndex);
	        	dataRow.setHeight(new Integer(1000).shortValue());
	        	for(int j=0;j<=6;j++){
	        		XSSFCell xssfCell = dataRow.createCell(j);
	        		if(j==0){
	        			xssfCell.setCellStyle(noBorderStyle);
	        			xssfCell.setCellValue("");
		            }else{
		            	xssfCell.setCellStyle(normalStyle);
		            	if(j==1){
			            	xssfCell.setCellValue(cityMap.get("DATA_CITY")==null?"":cityMap.get("DATA_CITY").toString());
			            }else if(j==2){
			            	xssfCell.setCellValue(cityMap.get("PRIMARY_VALUE")==null?"":cityMap.get("PRIMARY_VALUE").toString());
			            }else if(j==3){
			            	xssfCell.setCellValue(cityMap.get("RESOURCE_NAME")==null?"":cityMap.get("RESOURCE_NAME").toString());
			            }else if(j==4){
			            	if(cityMap.get("PROBLEM_TYPE")==null || "".equals(cityMap.get("PROBLEM_TYPE").toString())){
			            		xssfCell.setCellValue("-");
			            	}else{
			            		String PROBLEM_TYPE = cityMap.get("PROBLEM_TYPE").toString();
			            		if("IS_UNIFORM".equals(PROBLEM_TYPE)){
			            			PROBLEM_TYPE = "S标准表与C参考表数据不一致";
								}else if("IS_A_ONLY".equals(PROBLEM_TYPE)){
									PROBLEM_TYPE = "仅S标准表有数据";
								}else if("IS_Z_ONLY".equals(PROBLEM_TYPE)){
									PROBLEM_TYPE = "仅C参考表有数据";
								}else if("IS_A_FATAL".equals(PROBLEM_TYPE)){
									PROBLEM_TYPE = "S标准表数据异常";
								}else if("IS_Z_FATAL".equals(PROBLEM_TYPE)){
									PROBLEM_TYPE = "C参考表数据异常";
								}
			            		xssfCell.setCellValue(PROBLEM_TYPE);
			            	}
			            }else if(j==5){
			            	xssfCell.setCellValue(cityMap.get("COMPARE_RULE_NAME")==null?"":cityMap.get("COMPARE_RULE_NAME").toString());
			            }else if(j==6){
			            	xssfCell.setCellValue(cityMap.get("PROBLEM_DESC")==null?"":cityMap.get("PROBLEM_DESC").toString());
			            }
		            }
		        }
	        }
	        String folderPath = request.getSession().getServletContext().getRealPath("/")+"jsp"+File.separator+"dataBd"+File.separator+"files";
			File fileFolder = new File(folderPath);
			if(!fileFolder.exists()){
				fileFolder.mkdirs();
			}
			String fileName = new Date().getTime()+".xlsx";
			String finalExportPath = folderPath+File.separator+fileName;
			File finalExportFile = new File(finalExportPath);
			if(finalExportFile.exists()){
				finalExportFile.delete();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(finalExportFile);
			wk.write(fileOutputStream);
			fileOutputStream.close();
			/*
			 * 	将文件写出.
			 * 
			 * */
			finalExportFile = new File(finalExportPath);
			FileInputStream fileInputStream = new FileInputStream(finalExportFile);
			long filesize = finalExportFile.length();
			response.setContentType("text/html;charset=gb2312");
			response.addHeader("content-type","application/x-msdownload;");
			response.setHeader("Content-Disposition","attachment;filename=\""+new String((finalExportFile.getName()).getBytes(),"ISO8859-1"));
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
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*
	 * 	删除
	 * 
	 * */
	@RequestMapping("/deleteItem.ilf")
	public void deleteItem(@RequestParam String itemCode,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{	
			jdbcTemplate.execute("DELETE FROM COMPARE_EXEC_QUARTZ WHERE BELONG_RULE = "+itemCode);
			jdbcTemplate.execute("DELETE FROM COMPARE_COLUMN WHERE BELONG_COMPARE_RULE = "+itemCode);
			jdbcTemplate.execute("DELETE FROM COMPARE_DETAIL WHERE ID = "+itemCode);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	删除某批次比对任务
	 * 
	 * */
	@RequestMapping("/deleteJobItem.ilf")
	public void deleteJobItem(@RequestParam String monitorId,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{	
			jdbcTemplate.execute("DELETE FROM COMPARE_DATA WHERE BELONG_MONITOR = "+monitorId);
			jdbcTemplate.execute("DELETE FROM COMPARE_CITY_COUNT WHERE MONITOR_ID = "+monitorId);
			jdbcTemplate.execute("DELETE FROM COMPARE_MONITOR WHERE ID = "+monitorId);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	最近一次报表
	 * 
	 * */
	@RequestMapping("/barChartNewest.ilf")
	public void barChartNewest(@RequestParam String ruleCode,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		List<Integer> values = new ArrayList<Integer>();
		try{	
			List<Map<String,Object>> monitorCounts = jdbcTemplate.queryForList("SELECT * FROM COMPARE_MONITOR WHERE IS_FINISHED = 'Y' AND BELONG_COMPARE = "+ruleCode+" ORDER BY START_DATE DESC");
			if(monitorCounts.size()>0){
				Map<String,Object> theNew = monitorCounts.get(0);
				values.add(theNew.get("NOT_UNIFORM")==null?0:Integer.parseInt(theNew.get("NOT_UNIFORM").toString()));
				values.add(theNew.get("A_ONLY")==null?0:Integer.parseInt(theNew.get("A_ONLY").toString()));
				values.add(theNew.get("Z_ONLY")==null?0:Integer.parseInt(theNew.get("Z_ONLY").toString()));
				values.add(theNew.get("A_FATUAL")==null?0:Integer.parseInt(theNew.get("A_FATUAL").toString()));
				values.add(theNew.get("Z_FATUAL")==null?0:Integer.parseInt(theNew.get("Z_FATUAL").toString()));	
			}
			resultObject.put("values",values);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	获取当前登录用户是否为省级用户
	 * 
	 * */
	@RequestMapping("/isLoginProvince.ilf")
	public void isLoginProvince(
		HttpServletRequest request,
		HttpServletResponse response,
		@RequestParam String compareId
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			Boolean IS_PROVINCE = false;
			Integer loginUserId = getLoginUserId(request);
			Map<String,Object> loginUserMap = jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_USER WHERE ID = "+loginUserId);
			if(loginUserMap.get("USER_NAME").toString().toUpperCase().indexOf("ROOT")!=-1){
				IS_PROVINCE = true;
			}else{
				String countSql = "";
				countSql+="SELECT COUNT(1) FROM S_SYSTEM_ROLE WHERE ID IN(";
				countSql+="	   SELECT ROLE_ID FROM S_SYSTEM_USER_ROLE WHERE USER_ID = "+loginUserId;
				countSql+=") AND ROLE_NAME LIKE '%省级IT管理员%'";
				Integer countExist = jdbcTemplate.queryForInt(countSql);
				if(countExist>0){
					IS_PROVINCE = true;
				}else{
					IS_PROVINCE = false;
				}
			}
			resultObject.put("IS_PROVINCE",IS_PROVINCE);
			resultObject.put("COMPARE_DETAIL",
				jdbcTemplate.queryForList("SELECT * FROM COMPARE_DETAIL WHERE ID = "+compareId).size()>0?jdbcTemplate.queryForList("SELECT * FROM COMPARE_DETAIL WHERE ID = "+compareId).get(0):null
			);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	资源数据下载.
	 * 
	 * */
	@RequestMapping("/exportMyFatalData.ilf")
	public void exportMyFatalData(
		@RequestParam String moditorId,
		@RequestParam String fatalType,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		try{
			String fatalSql = "SELECT * FROM COMPARE_DATA WHERE BELONG_MONITOR = "+moditorId+" AND PROBLEM_TYPE = '"+fatalType+"'";
			Boolean isProvinceUser = loginUserUtil.isProvince(request);
			if(!isProvinceUser){
				String belongCity = loginUserUtil.getBelongArea(request);
				if(belongCity!=null && !"".equals(belongCity)){
					fatalSql+=" AND DATA_CITY LIKE '%"+belongCity+"%'";
				}
			}
			List<Map<String,Object>> fatalDatas = jdbcTemplate.queryForList(fatalSql);
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
	        XSSFRow tableHead = xssfSheet.createRow(1);
	        tableHead.setHeight(new Integer(430).shortValue());
	        for(int r=1;r<=4;r++){
				XSSFCell cell = tableHead.createCell(r);
				cell.setCellStyle(centerGray);
			}
	        xssfSheet.addMergedRegion(new CellRangeAddress(1,1,1,4));
	        Map<String,Object> compareDetal = jdbcTemplate.queryForMap("SELECT * FROM COMPARE_DETAIL WHERE ID IN(SELECT BELONG_COMPARE FROM COMPARE_MONITOR WHERE ID = "+moditorId+")");
	        String compareName = compareDetal.get("RULE_NAME").toString();
	        if("IS_UNIFORM".equals(fatalType)){
	        	compareName+="（数据不一致）";
	        }else if("IS_A_ONLY".equals(fatalType)){
	        	compareName+="（仅核查模型有数据）";
	        }else if("IS_Z_ONLY".equals(fatalType)){
	        	compareName+="（仅标准模型有数据）";
	        }else if("IS_A_FATAL".equals(fatalType)){
	        	compareName+="（核查模型数据异常）";
	        }else if("IS_Z_FATAL".equals(fatalType)){
	        	compareName+="（标准模型数据异常）";
	        }
	        xssfSheet.getRow(1).getCell(1).setCellValue(compareName);
	        /*字段列表*/
	        XSSFRow columnHead = xssfSheet.createRow(2);
	        columnHead.setHeight(new Integer(430).shortValue());
	        for(int r=1;r<=4;r++){
	        	XSSFCell hCell = columnHead.createCell(r);
	        	hCell.setCellStyle(leftWhites);
	        	if(r==1){
	        		hCell.setCellValue("所属地市");
	        	}else if(r==2){
	        		hCell.setCellValue("资源编号");
	        	}else if(r==3){
	        		hCell.setCellValue("资源名称");
	        	}else if(r==4){
	        		hCell.setCellValue("问题描述");
	        	}
	        	
			}
	        /*渲染具体的数据*/
	        Integer WIDTH_1 = 5000;
			Integer WIDTH_2 = 5000;
			Integer WIDTH_3 = 5000;
			Integer WIDTH_4 = 5000;
			Integer CELL_WIDTH = 500;
	        Integer currentRowIndex = 3;
			for(int i=0;i<fatalDatas.size();i++){
				XSSFRow dataRow = xssfSheet.createRow(currentRowIndex);
				dataRow.setHeight(new Integer(430).shortValue());
				for(int r=1;r<=4;r++){
		        	XSSFCell dCell = dataRow.createCell(r);
		        	dCell.setCellStyle(leftWhites);
		        	if(r==1){
		        		dCell.setCellValue(
		        			fatalDatas.get(i).get("DATA_CITY")==null?"":fatalDatas.get(i).get("DATA_CITY").toString()
		        		);
		        		if(fatalDatas.get(i).get("DATA_CITY")!=null){
		        			Integer newColumn1Width = fatalDatas.get(i).get("DATA_CITY").toString().length()*CELL_WIDTH;
		        			if(newColumn1Width>WIDTH_1){
		        				WIDTH_1 = newColumn1Width;
		        			}
		        		}
		        	}else if(r==2){
		        		dCell.setCellValue(
		        			fatalDatas.get(i).get("PRIMARY_VALUE")==null?"":fatalDatas.get(i).get("PRIMARY_VALUE").toString()
		        		);
		        		if(fatalDatas.get(i).get("PRIMARY_VALUE")!=null){
		        			Integer newColumn2Width = fatalDatas.get(i).get("PRIMARY_VALUE").toString().length()*CELL_WIDTH;
		        			if(newColumn2Width>WIDTH_2){
		        				WIDTH_2 = newColumn2Width;
		        			}
		        		}
		        	}else if(r==3){
		        		dCell.setCellValue(
		        			fatalDatas.get(i).get("RESOURCE_NAME")==null?"":fatalDatas.get(i).get("RESOURCE_NAME").toString()
		        		);
		        		if(fatalDatas.get(i).get("RESOURCE_NAME")!=null){
		        			Integer newColumn3Width = fatalDatas.get(i).get("RESOURCE_NAME").toString().length()*CELL_WIDTH;
		        			if(newColumn3Width>WIDTH_3){
		        				WIDTH_3 = newColumn3Width;
		        			}
		        		}
		        	}else if(r==4){
		        		dCell.setCellValue(
		        			fatalDatas.get(i).get("PROBLEM_DESC")==null?"":fatalDatas.get(i).get("PROBLEM_DESC").toString()
		        		);
		        		if(fatalDatas.get(i).get("PROBLEM_DESC")!=null){
		        			Integer newColumn4Width = fatalDatas.get(i).get("PROBLEM_DESC").toString().length()*CELL_WIDTH;
		        			if(newColumn4Width>WIDTH_4){
		        				WIDTH_4 = newColumn4Width;
		        			}
		        		}
		        	}
				}
				currentRowIndex++;
			}
			xssfSheet.setColumnWidth(1,WIDTH_1);
			xssfSheet.setColumnWidth(2,WIDTH_2);
			xssfSheet.setColumnWidth(3,WIDTH_3);
			xssfSheet.setColumnWidth(4,WIDTH_4);
			String fileName = "Compare"+new Date().getTime()+".xlsx";
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
