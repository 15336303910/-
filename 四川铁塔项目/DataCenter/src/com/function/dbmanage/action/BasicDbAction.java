package com.function.dbmanage.action;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.function.dbmanage.model.BasicDb;
import com.function.dbmanage.service.BasicDbService;
import com.function.dbmanage.service.BasicDbTableService;
import com.systemConfig.model.DataTableResult;
@Controller("com.function.dbmanage.action.BasicDbAction")
@RequestMapping(value="/basicDbAction")
public class BasicDbAction{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private BasicDbService basicDbService;
	
	@Autowired
	private BasicDbTableService basicDbTableService;
	
	/*
	 * 	获取一个详情
	 * 
	 * */
	@RequestMapping("/findOne.ilf")
	public void findOne(@RequestParam String dbId,HttpServletResponse response)throws Exception{
		BasicDb basicDb = basicDbService.selectModel(Integer.parseInt(dbId));
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(basicDb).toString());
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
		Integer count = basicDbService.getCount(conditonMap);
		List<BasicDb> organizations = basicDbService.getDbPage(conditonMap);	
		DataTableResult<BasicDb> tableData = new DataTableResult<BasicDb>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(organizations);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	测试数据库是否可用
	 * 
	 * */
	@RequestMapping("/testConnect.ilf")
	public void testConnect(@RequestParam String params,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'连接测试成功.'}");
		final JSONObject thisObject = JSONObject.fromObject(params);
		try{
			if("Oracle".equals(thisObject.getString("dbType"))){
				Class.forName("oracle.jdbc.driver.OracleDriver");
			}else{
				Class.forName("com.mysql.jdbc.Driver");
			}
			DriverManager.setLoginTimeout(5000);
			Connection connection = null;			
			if("Oracle".equals(thisObject.getString("dbType"))){
				connection = DriverManager.getConnection("jdbc:oracle:thin:@"+thisObject.getString("ipAddress")+":"+thisObject.getString("portName")+":"+thisObject.getString("sidName")+"",thisObject.getString("userName"),thisObject.getString("passWord"));
			}else if("MySQL".equals(thisObject.getString("dbType"))){
				connection = DriverManager.getConnection("jdbc:mysql://"+thisObject.getString("ipAddress")+":"+thisObject.getString("portName")+"/"+thisObject.getString("sidName")+"",thisObject.getString("userName"),thisObject.getString("passWord"));
			}
			Boolean isClosed = connection.isClosed();
			if(isClosed){
				resultObject.put("success",false);
				resultObject.put("message","数据库连接已关闭.");
			}else{
				connection.close();
			}			
		}catch(SQLException e){
			e.printStackTrace();
			Integer errorCode = e.getErrorCode();	
			resultObject.put("success",false);
			if("Oracle".equals(thisObject.getString("dbType"))){
				if(errorCode==17002){
					resultObject.put("message","请检查数据库配置的I.P和Port属性.");
				}else if(errorCode==0){
					resultObject.put("message","请检查数据库配置的SID属性.");
				}else if(errorCode==1017){
					resultObject.put("message","请检查数据库配置用户名和密码是否正确.");
				}else{
					resultObject.put("message","无法连接至数据库，请检查配置是否正确.");
				}
			}else{
				if(errorCode==0){
					resultObject.put("message","请检查数据库配置的I.P和Port属性.");
				}else if(errorCode==1049){
					resultObject.put("message","请检查数据库配置的SID属性.");
				}else if(errorCode==1045){
					resultObject.put("message","请检查数据库配置用户名和密码是否正确.");
				}else{
					resultObject.put("message","无法连接至数据库，请检查配置是否正确.");
				}
			}
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
	 * 	保存/修改
	 * 
	 * */
	@RequestMapping("/saveAudit.ilf")
	public void saveAudit(@RequestParam String params,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'组织配置成功.'}");
		try{
			BasicDb basicDb = null;
			final JSONObject thisObject = JSONObject.fromObject(params);
			if(thisObject.get("id")==null || "".equals(thisObject.get("id").toString())){
				basicDb = new BasicDb();
			}else{
				basicDb = basicDbService.selectModel(Integer.parseInt(thisObject.get("id").toString()));
			}
			basicDb.setDB_NAME(thisObject.getString("dbName"));
			basicDb.setDB_TYPE(thisObject.getString("dbType"));
			basicDb.setIP_ADDRESS(thisObject.getString("ipAddress"));
			basicDb.setPORT(thisObject.getString("portName"));
			basicDb.setSID(thisObject.getString("sidName"));
			basicDb.setUSER_NAME(thisObject.getString("userName"));
			basicDb.setUSER_PASS(thisObject.getString("passWord"));
			if(thisObject.get("id")==null || "".equals(thisObject.get("id").toString())){
				basicDb.setUSE_STATE("Y");
				basicDbService.insertModel(basicDb);
			}else{
				basicDbService.updateModel(basicDb);
			}
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
	 * 	删除数据源
	 * 
	 * */
	@RequestMapping("/deleteItem.ilf")
	public void deleteItem(@RequestParam String itemCode,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{			
			basicDbService.deleteModel(Integer.parseInt(itemCode));
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	修改状态
	 * 
	 * */
	@RequestMapping("/updateState.ilf")
	public void updateState(@RequestParam String checkedCode,@RequestParam String isUsing,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			jdbcTemplate.execute("UPDATE BASIC_DB SET USE_STATE = '"+isUsing+"' WHERE ID = "+checkedCode);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	获取数据源列表
	 * 
	 * */
	@RequestMapping("/getDbNameList.ilf")
	public void getDbNameList(HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			List<Map<String,Object>> dbList = jdbcTemplate.queryForList("SELECT ID,DB_NAME FROM BASIC_DB");
			resultObject.put("dbList",dbList);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
}