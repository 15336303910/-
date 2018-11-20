package com.function.dbmanage.action;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.function.dbmanage.model.BasicDb;
import com.function.dbmanage.model.BasicDbTable;
import com.function.dbmanage.service.BasicColumnService;
import com.function.dbmanage.service.BasicDbService;
import com.function.dbmanage.service.BasicDbTableService;
import com.systemConfig.model.DataTableResult;
@Controller("com.function.dbmanage.action.BasicDbTableAction")
@RequestMapping(value="/basicDbTableAction")
public class BasicDbTableAction {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private BasicDbService basicDbService;
	
	@Autowired
	private BasicDbTableService basicDbTableService;
	
	@Autowired
	private BasicColumnService basicColumnService;
	
	/*
	 * 	获取一个详情
	 * 
	 * */
	@RequestMapping("/findOne.ilf")
	public void findOne(@RequestParam String tableId,HttpServletResponse response)throws Exception{
		BasicDbTable basicDbTable = null;
		try{
			basicDbTable = basicDbTableService.selectModel(Integer.parseInt(tableId));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(JSONObject.fromObject(basicDbTable).toString());
		}
	}
	
	/*
	 * 	获取一个数据源列表
	 * 
	 * */
	@RequestMapping("/findDbOptions.ilf")
	public void findDbOptions(HttpServletResponse response)throws Exception{
		List<BasicDb> basicDbs = new ArrayList<BasicDb>();
		try{
			basicDbs = basicDbService.selectModelsByHql("from BasicDb");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(JSONArray.fromObject(basicDbs).toString());
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
		Integer count = basicDbTableService.getCount(conditonMap);
		List<BasicDbTable> dbTables = basicDbTableService.getTablePage(conditonMap);
		if(dbTables.size()>0){
			for(int i=0;i<dbTables.size();i++){
				BasicDb basicDb = basicDbService.selectModel(dbTables.get(i).getBELONG_DB());
				dbTables.get(i).setDB_NAME(basicDb.getDB_NAME());
			}
		}	
		DataTableResult<BasicDbTable> tableData = new DataTableResult<BasicDbTable>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(dbTables);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	保存/修改
	 * 
	 * */
	@RequestMapping("/testConnect.ilf")
	public void testConnect(@RequestParam String params,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'连接测试成功.'}");
		final JSONObject thisObject = JSONObject.fromObject(params);
		Connection connection = null;	
		Statement stateMent = null;
		ResultSet resultSet = null;
		try{
			BasicDb basicDb = basicDbService.selectModel(Integer.parseInt(thisObject.getString("belongDb")));			
			if("Oracle".equals(basicDb.getDB_TYPE())){
				Class.forName("oracle.jdbc.driver.OracleDriver");
			}else{
				Class.forName("com.mysql.jdbc.Driver");
			}
			DriverManager.setLoginTimeout(5000);
			
			if("Oracle".equals(basicDb.getDB_TYPE())){
				connection = DriverManager.getConnection("jdbc:oracle:thin:@"+basicDb.getIP_ADDRESS()+":"+basicDb.getPORT()+":"+basicDb.getSID()+"",basicDb.getUSER_NAME(),basicDb.getUSER_PASS());
			}else if("MySQL".equals(basicDb.getDB_TYPE())){
				connection = DriverManager.getConnection("jdbc:mysql://"+basicDb.getIP_ADDRESS()+":"+basicDb.getPORT()+"/"+basicDb.getSID()+"",basicDb.getUSER_NAME(),basicDb.getUSER_PASS());
			}
			Boolean isClosed = connection.isClosed();
			if(isClosed){
				resultObject.put("success",false);
				resultObject.put("message","数据库连接已关闭.");
			}else{
				String tableName = thisObject.getString("tableName");				
				stateMent = connection.createStatement();
				resultSet = stateMent.executeQuery("SELECT COUNT(1) AS TOTAL_COUNT FROM "+tableName);		
			}			
		}catch(SQLException e){
			e.printStackTrace();
			resultObject.put("success",false);
			Integer errorCode = e.getErrorCode();
			if(errorCode==1146){
				resultObject.put("message","测试失败，请检查配置.");
			}
		}finally{
			if(resultSet!=null){
				resultSet.close();
			}
			if(stateMent!=null){
				stateMent.close();
			}
			if(connection!=null){
				connection.close();
			}
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
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'配置成功.'}");
		try{
			BasicDbTable basicDbTable = null;
			final JSONObject thisObject = JSONObject.fromObject(params);
			if(thisObject.get("id")==null || "".equals(thisObject.get("id").toString())){
				basicDbTable = new BasicDbTable();
			}else{
				basicDbTable = basicDbTableService.selectModel(Integer.parseInt(thisObject.get("id").toString()));
			}
			basicDbTable.setBELONG_DB(Integer.parseInt(thisObject.getString("belongDb")));
			basicDbTable.setBELONG_MAJOR(thisObject.getString("belongMajor"));
			basicDbTable.setTABLE_ALIAS(thisObject.getString("aliasName"));
			basicDbTable.setTABLE_NAME(thisObject.getString("tableName"));		
			if(thisObject.get("id")==null || "".equals(thisObject.get("id").toString())){
				basicDbTable.setUSE_STATE("Y");
				basicDbTableService.insertModel(basicDbTable);
			}else{
				basicDbTable.setUSE_STATE(thisObject.getString("isUsing"));
				basicDbTableService.updateModel(basicDbTable);
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
	 * 	删除
	 * 
	 * */
	@RequestMapping("/deleteItem.ilf")
	public void deleteItem(@RequestParam String itemCode,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			/*删除模型*/
			basicDbTableService.deleteModel(Integer.parseInt(itemCode));
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
			jdbcTemplate.execute("UPDATE BASIC_DB_TABLE SET USE_STATE = '"+isUsing+"' WHERE ID = "+checkedCode);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
}
