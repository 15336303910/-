package com.function.rules.action;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.function.rules.model.RuleDetail;
import com.function.rules.model.RuleJob;
import com.function.rules.service.RuleDetailService;
import com.function.rules.service.RuleJobService;
import com.system.LoginUserUtil;
import com.systemConfig.model.DataTableResult;
@Controller("com.function.rules.action.RuleManageAction")
@RequestMapping(value="/ruleManageAction")
public class RuleManageAction {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	@Autowired
	private RuleDetailService ruleDetailService;
	
	@Autowired
	private RuleJobService ruleJobService;
	
	/*
	 * 	查询列表
	 * 
	 * */
	@RequestMapping("/findItems.ilf")
	public void findItems(@RequestParam String tableparam,@RequestParam String conditions,HttpServletRequest request,HttpServletResponse response)throws Exception{
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
		conditonMap.put("CREATE_USER",getLoginUserId(request));
		Integer count = ruleDetailService.getCount(conditonMap);
		List<RuleDetail> ruleDetails = ruleDetailService.getDbPage(conditonMap);	
		DataTableResult<RuleDetail> tableData = new DataTableResult<RuleDetail>();
		Map<Integer,String> acheUsers = new HashMap<Integer,String>();
		if(ruleDetails.size()>0){
			for(int w=0;w<ruleDetails.size();w++){
				/*
				 * 	创建人员
				 * 
				 * */
				if(acheUsers.containsKey(ruleDetails.get(w).getCREATE_USER())){
					ruleDetails.get(w).setCREATE_USER_NAME(acheUsers.get(ruleDetails.get(w).getCREATE_USER()).toString());
				}else{
					List<Map<String,Object>> userList = jdbcTemplate.queryForList("SELECT EMPLOYEE_NAME FROM S_SYSTEM_USER WHERE ID = "+ruleDetails.get(w).getCREATE_USER());
					if(userList.size()>0){
						ruleDetails.get(w).setCREATE_USER_NAME(userList.get(0).get("EMPLOYEE_NAME").toString());
						acheUsers.put(ruleDetails.get(w).getCREATE_USER(),userList.get(0).get("EMPLOYEE_NAME").toString());
					}else{
						ruleDetails.get(w).setCREATE_USER_NAME("");
					}
				}
				/*
				 * 	问题数量
				 * 
				 * */
				List<RuleJob> ruleJobs = ruleJobService.selectModelsByHql("from RuleJob where RULE_ID = "+ruleDetails.get(w).getID());
				if(ruleJobs.size()>0 && ruleJobs.get(0).getFATUAL_TOTAL()!=null){
					ruleDetails.get(w).setPROBLEM_TOTAL(ruleJobs.get(0).getFATUAL_TOTAL().toString());;
				}else{
					ruleDetails.get(w).setPROBLEM_TOTAL("-");
				}
			}
		}	
		tableData.setsEcho(sEcho);
		tableData.setAaData(ruleDetails);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	获取资源树
	 * 
	 * */
	@RequestMapping("/findDbData.ilf")
	public void findDbData(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONArray nodes = new JSONArray();	
		List<Map<String,Object>> dbList = jdbcTemplate.queryForList("SELECT ID,DB_NAME FROM BASIC_DB WHERE USE_STATE = 'Y'");
		if(dbList.size()>0){
			for(int i=0;i<dbList.size();i++){
				/*数据库节点*/
				Map<String,Object> dbObj = dbList.get(i);
				JSONObject topNode = new JSONObject();
				topNode.put("text",dbObj.get("DB_NAME").toString());
				JSONObject stateObj = new JSONObject();
				if(i==0){
					stateObj.put("opened",true);
				}else{
					stateObj.put("opened",false);
				}
				topNode.put("state",stateObj);
				/*专业节点*/
				List<Map<String,Object>> majorList = jdbcTemplate.queryForList("SELECT DISTINCT(BELONG_MAJOR) AS MAJOR_NAME FROM BASIC_DB_TABLE WHERE BELONG_DB = "+dbObj.get("ID").toString()+" AND USE_STATE = 'Y'");
				JSONArray majorNodes = new JSONArray();
				if(majorList.size()>0){
					for(int j=0;j<majorList.size();j++){
						JSONObject majorNode = new JSONObject();
						majorNode.put("text",majorList.get(j).get("MAJOR_NAME").toString());
						JSONObject majorNodeState = new JSONObject();
						majorNodeState.put("opened",true);
						majorNodeState.put("selected",false);
						majorNode.put("state",majorNodeState);
						/*表节点*/
						JSONArray leafNodes = new JSONArray();
						List<Map<String,Object>> leafTables = jdbcTemplate.queryForList("SELECT * FROM BASIC_DB_TABLE WHERE USE_STATE = 'Y' AND BELONG_DB = "+dbObj.get("ID").toString()+" AND BELONG_MAJOR = '"+majorList.get(j).get("MAJOR_NAME").toString()+"'");
						if(leafTables.size()>0){
							for(int w=0;w<leafTables.size();w++){
								JSONObject leafNode = new JSONObject();
								leafNode.put("id",Integer.parseInt(leafTables.get(w).get("ID").toString()));
								leafNode.put("text",leafTables.get(w).get("TABLE_ALIAS").toString());
								JSONObject leafNodeState = new JSONObject();
								leafNodeState.put("selected",false);
								leafNode.put("state",leafNodeState);
								leafNodes.add(leafNode);
							}
						}					
						majorNode.put("children",leafNodes);
						majorNodes.add(majorNode);			
					}
				}
				topNode.put("children",majorNodes);			
				nodes.add(topNode);
			}
		}	
		JSONObject result = new JSONObject();
		result.put("success",true);
		result.put("nodes",nodes);
		Map<String,Object> propertyObject = jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'data_operate'");
		result.put("linkurl",propertyObject.get("PRO_VALUE").toString()+loginUserUtil.getUserAccount(request));
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(result.toString());
	}
	
	/*
	 * 	获取资源树
	 * 
	 * */
	@RequestMapping("/findModelsByKey.ilf")
	public void findModelsByKey(
		@RequestParam String modelName,
		HttpServletResponse response
	)throws Exception{
		Integer tableExist = 0;
		JSONArray nodes = new JSONArray();
		if(modelName!=null && !"".equals(modelName)){
			tableExist = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM BASIC_DB_TABLE WHERE (TABLE_ALIAS LIKE '%"+modelName+"%' OR TABLE_NAME LIKE '%"+modelName+"%')");
			if(tableExist==0){
				JSONObject topNode = new JSONObject();
				topNode.put("text","根据名称未查找到模型");
				JSONObject stateObj = new JSONObject();
				stateObj.put("opened",false);
				topNode.put("state",stateObj);
				nodes.add(topNode);
			}else{
				List<Map<String,Object>> dbList = jdbcTemplate.queryForList("SELECT ID,DB_NAME FROM BASIC_DB WHERE USE_STATE = 'Y' AND ID IN(SELECT DISTINCT(BELONG_DB) FROM BASIC_DB_TABLE WHERE (TABLE_ALIAS LIKE '%"+modelName+"%' OR TABLE_NAME LIKE '%"+modelName+"%'))");
				for(int i=0;i<dbList.size();i++){
					Map<String,Object> dbObj = dbList.get(i);
					JSONObject topNode = new JSONObject();
					topNode.put("text",dbObj.get("DB_NAME").toString());
					topNode.put("state",JSONObject.fromObject("{opened:true}"));
					/*专业节点*/
					List<Map<String,Object>> majorList = jdbcTemplate.queryForList("SELECT DISTINCT(BELONG_MAJOR) AS MAJOR_NAME FROM BASIC_DB_TABLE WHERE BELONG_DB = "+dbObj.get("ID").toString()+" AND USE_STATE = 'Y' AND BELONG_MAJOR IN(SELECT DISTINCT(BELONG_MAJOR) FROM BASIC_DB_TABLE WHERE (TABLE_ALIAS LIKE '%"+modelName+"%' OR TABLE_NAME LIKE '%"+modelName+"%'))");
					JSONArray majorNodes = new JSONArray();
					if(majorList.size()>0){
						for(int j=0;j<majorList.size();j++){
							JSONObject majorNode = new JSONObject();
							majorNode.put("text",majorList.get(j).get("MAJOR_NAME").toString());
							JSONObject majorNodeState = new JSONObject();
							majorNodeState.put("opened",true);
							majorNodeState.put("selected",false);
							majorNode.put("state",majorNodeState);
							/*表节点*/
							JSONArray leafNodes = new JSONArray();
							List<Map<String,Object>> leafTables = jdbcTemplate.queryForList("SELECT * FROM BASIC_DB_TABLE WHERE USE_STATE = 'Y' AND BELONG_DB = "+dbObj.get("ID").toString()+" AND BELONG_MAJOR = '"+majorList.get(j).get("MAJOR_NAME").toString()+"' AND (TABLE_ALIAS LIKE '%"+modelName+"%' OR TABLE_NAME LIKE '%"+modelName+"%')");
							if(leafTables.size()>0){
								for(int w=0;w<leafTables.size();w++){
									JSONObject leafNode = new JSONObject();
									leafNode.put("id",Integer.parseInt(leafTables.get(w).get("ID").toString()));
									leafNode.put("text",leafTables.get(w).get("TABLE_ALIAS").toString());
									JSONObject leafNodeState = new JSONObject();
									leafNodeState.put("selected",false);
									leafNode.put("state",leafNodeState);
									leafNodes.add(leafNode);
								}
							}					
							majorNode.put("children",leafNodes);
							majorNodes.add(majorNode);			
						}
					}
					topNode.put("children",majorNodes);			
					nodes.add(topNode);
				}
			}
		}else{
			List<Map<String,Object>> dbList = jdbcTemplate.queryForList("SELECT ID,DB_NAME FROM BASIC_DB WHERE USE_STATE = 'Y'");
			for(int i=0;i<dbList.size();i++){
				Map<String,Object> dbObj = dbList.get(i);
				JSONObject topNode = new JSONObject();
				topNode.put("text",dbObj.get("DB_NAME").toString());
				topNode.put("state",JSONObject.fromObject("{opened:true}"));
				/*专业节点*/
				List<Map<String,Object>> majorList = jdbcTemplate.queryForList("SELECT DISTINCT(BELONG_MAJOR) AS MAJOR_NAME FROM BASIC_DB_TABLE WHERE BELONG_DB = "+dbObj.get("ID").toString()+" AND USE_STATE = 'Y'");
				JSONArray majorNodes = new JSONArray();
				if(majorList.size()>0){
					for(int j=0;j<majorList.size();j++){
						JSONObject majorNode = new JSONObject();
						majorNode.put("text",majorList.get(j).get("MAJOR_NAME").toString());
						JSONObject majorNodeState = new JSONObject();
						majorNodeState.put("opened",true);
						majorNodeState.put("selected",false);
						majorNode.put("state",majorNodeState);
						/*表节点*/
						JSONArray leafNodes = new JSONArray();
						List<Map<String,Object>> leafTables = jdbcTemplate.queryForList("SELECT * FROM BASIC_DB_TABLE WHERE USE_STATE = 'Y' AND BELONG_DB = "+dbObj.get("ID").toString()+" AND BELONG_MAJOR = '"+majorList.get(j).get("MAJOR_NAME").toString()+"'");
						if(leafTables.size()>0){
							for(int w=0;w<leafTables.size();w++){
								JSONObject leafNode = new JSONObject();
								leafNode.put("id",Integer.parseInt(leafTables.get(w).get("ID").toString()));
								leafNode.put("text",leafTables.get(w).get("TABLE_ALIAS").toString());
								JSONObject leafNodeState = new JSONObject();
								leafNodeState.put("selected",false);
								leafNode.put("state",leafNodeState);
								leafNodes.add(leafNode);
							}
						}					
						majorNode.put("children",leafNodes);
						majorNodes.add(majorNode);			
					}
				}
				topNode.put("children",majorNodes);			
				nodes.add(topNode);
			}
		}
		JSONObject result = new JSONObject();
		result.put("success",true);
		result.put("nodes",nodes);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(result.toString());
	}
	
	/*
	 * 	获取我的订阅树
	 * 
	 * */
	@RequestMapping("/findMyDesigned.ilf")
	public void findMyDesigned(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONArray nodes = new JSONArray();
		String USER_ACCOUNT = loginUserUtil.getUserAccount(request);
		Integer tableExist = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM DATA_DESIGN WHERE USER_ACCOUNT = '"+USER_ACCOUNT+"'");
		if(tableExist==0){
			JSONObject topNode = new JSONObject();
			topNode.put("text","根据名称未找到已订阅模型");
			JSONObject stateObj = new JSONObject();
			stateObj.put("opened",false);
			topNode.put("state",stateObj);
			nodes.add(topNode);
		}else{
			String dbSql = "";
			dbSql+="SELECT ID,DB_NAME ";
			dbSql+="FROM BASIC_DB ";
			dbSql+="WHERE USE_STATE = 'Y' AND ID IN(SELECT DISTINCT(BELONG_DB) FROM BASIC_DB_TABLE WHERE ID IN(SELECT DISTINCT(MODEL_ID) FROM DATA_DESIGN WHERE USER_ACCOUNT = '"+USER_ACCOUNT+"'))";
			List<Map<String,Object>> dbList = jdbcTemplate.queryForList(dbSql);
			for(int i=0;i<dbList.size();i++){
				Map<String,Object> dbObj = dbList.get(i);
				JSONObject topNode = new JSONObject();
				topNode.put("text",dbObj.get("DB_NAME").toString());
				topNode.put("state",JSONObject.fromObject("{opened:true}"));
				/*专业节点*/
				String majorSql = "";
				majorSql+="SELECT DISTINCT(BELONG_MAJOR) AS MAJOR_NAME FROM(";
				majorSql+="	  SELECT * FROM BASIC_DB_TABLE WHERE BELONG_DB = "+dbObj.get("ID").toString()+" AND ID IN(";
				majorSql+="		  SELECT DISTINCT(MODEL_ID) FROM DATA_DESIGN WHERE USER_ACCOUNT = '"+USER_ACCOUNT+"'";
				majorSql+="	  )";
				majorSql+=")";
				List<Map<String,Object>> majorList = jdbcTemplate.queryForList(majorSql);
				JSONArray majorNodes = new JSONArray();
				if(majorList.size()>0){
					for(int j=0;j<majorList.size();j++){
						JSONObject majorNode = new JSONObject();
						majorNode.put("text",majorList.get(j).get("MAJOR_NAME").toString());
						JSONObject majorNodeState = new JSONObject();
						majorNodeState.put("opened",true);
						majorNodeState.put("selected",false);
						majorNode.put("state",majorNodeState);
						/*表节点*/
						String modelSql = "";
						modelSql+="SELECT * FROM BASIC_DB_TABLE WHERE BELONG_DB = "+dbObj.get("ID").toString()+" AND BELONG_MAJOR = '"+majorList.get(j).get("MAJOR_NAME").toString()+"' AND ID IN(";
						modelSql+="	  SELECT DISTINCT(MODEL_ID) FROM DATA_DESIGN WHERE USER_ACCOUNT = '"+USER_ACCOUNT+"'";
						modelSql+=")";
						JSONArray leafNodes = new JSONArray();
						List<Map<String,Object>> leafTables = jdbcTemplate.queryForList(modelSql);
						if(leafTables.size()>0){
							for(int w=0;w<leafTables.size();w++){
								JSONObject leafNode = new JSONObject();
								leafNode.put("id",Integer.parseInt(leafTables.get(w).get("ID").toString()));
								leafNode.put("text",leafTables.get(w).get("TABLE_ALIAS").toString());
								JSONObject leafNodeState = new JSONObject();
								leafNodeState.put("selected",false);
								leafNode.put("state",leafNodeState);
								leafNodes.add(leafNode);
							}
						}					
						majorNode.put("children",leafNodes);
						majorNodes.add(majorNode);			
					}
				}
				topNode.put("children",majorNodes);			
				nodes.add(topNode);
			}
		}	
		JSONObject result = new JSONObject();
		result.put("success",true);
		result.put("nodes",nodes);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(result.toString());
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
