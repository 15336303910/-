package com.function.index.greyList.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.system.LoginUserUtil;
import com.systemConfig.model.DataTableResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("com.function.index.greyList.action.todoListDataList")
@RequestMapping(value="/todoListDataList")
public class todoListDataList {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private LoginUserUtil loginUserUtil;
	
	
	/*
	 * 	展示灰名单数据列表
	 * 
	 * */
	@RequestMapping("/showtodoListDataList.ilf")
	public void  showtodoListDataList(
		@RequestParam String tableparam,
		@RequestParam String conditions,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		Long sEcho = 0L;
		Integer displayStart = 0;
		Integer iDisplayLength = 0;
		JSONArray jsons = JSONArray.fromObject(tableparam);
		HashMap<String,Object> conditionMap = new HashMap<String,Object>();
		if(jsons!=null && jsons.size()!=0){
			for(int i=0;i<jsons.size();i++){
				JSONObject json = JSONObject.fromObject(jsons.get(i));
				String key = json.getString("name");
				if(key.equals("sEcho")){
					sEcho = Long.parseLong(json.getString("value"));
				}else if(key.equals("iDisplayStart")){
					displayStart = Integer.parseInt(json.getString("value"));
					conditionMap.put("iDisplayStart",displayStart);
				}else if(key.equals("iDisplayLength")){
					iDisplayLength = Integer.parseInt(json.getString("value"));
					conditionMap.put("iDisplayLength",iDisplayLength);
				}
			}
		}
		JSONArray condition = JSONArray.fromObject(conditions);
		if(conditions!=null && condition.size()!=0){
			for(int i=0;i<condition.size();i++){
				JSONObject jsonObject = JSONObject.fromObject(condition.get(i));
				if(jsonObject.get("value")!=null && !"".equals(jsonObject.getString("value"))){
					conditionMap.put(jsonObject.getString("name"),jsonObject.getString("value"));
				}
			}
		}		
		
		/***
		 * 获取登录信息判断是否是省级人员或者找出地市人员的所属地市
		 */
		Boolean IS_PROVICE = false;
		String BELONG_CITY = "";
	
		Object loginObject = request.getSession().getAttribute("LoginUserInfo");
		
		String rolename=(String) request.getSession().getAttribute("ROLENAME");
		
		
		if(loginObject!=null){
			Map<String,Object> loginUser = (HashMap<String,Object>)loginObject;
			if(loginUser.get("BELONG_AREA").toString().indexOf("省")!=-1){
				IS_PROVICE = true;
				BELONG_CITY = loginUser.get("BELONG_AREA").toString();
			}else{
				BELONG_CITY = loginUser.get("BELONG_AREA").toString();
			}
			
	
			if(BELONG_CITY.length()>2){
				BELONG_CITY = BELONG_CITY.substring(0,2);
			}
			
		
			
		}
		/*
		 * 	获取灰名单列表的sql语句
		 * 
		 * */
		String sql = "SELECT T. ID,T.CITY,to_char(T.LAUNCH_TIME,'yyyy-MM')  LAUNCH_TIME,T.GL_TYPE,T.GL_DESCRIBE,T.ATTRIBUTION,T.DATA_SOURCE,"
				+ "to_char(T.GLS_TIME,'yyyy-MM-DD')  GLS_TIME,T.PROCEDURE_SEGMENT,to_char(EXPIRE_TIME,'yyyy-MM-DD')  EXPIRY_TIME, T.PROCEDURE_STATUS   FROM GL_GREY_LIST  T  ";
	
		/***
		 * 搜索设置根据地市和日期
		 */
		String searchCity="";
		String searchDate="";
		String searchRequestType="";
		if(conditionMap.containsKey("CITY") 
				&& !"".equals(conditionMap.get("CITY").toString()) 
				&& !"--".equals(conditionMap.get("CITY").toString()) 
				&& !"--请选择--".equals(conditionMap.get("CITY").toString())
				&& !"四川".equals(conditionMap.get("CITY").toString())
				&& !"全省".equals(conditionMap.get("CITY").toString())) {
			searchCity=conditionMap.get("CITY").toString();
		}
		if(conditionMap.containsKey("DATE") && !"".equals(conditionMap.get("DATE").toString())) {
			searchDate=conditionMap.get("DATE").toString();
		}
		if(conditionMap.containsKey("Request_Type")&& !"".equals(conditionMap.get("Request_Type").toString()) ){
			searchRequestType=conditionMap.get("Request_Type").toString();
		}
			
	
		if(!IS_PROVICE) {
			sql+="where CITY='"+BELONG_CITY+"' ";
			if(!searchDate.equals("")) {
				sql+=" and to_char(T.LAUNCH_TIME,'yyyy-MM')='"+searchDate+"' ";
			}
			if   (!searchRequestType.equals("全部类型")&& !searchRequestType.equals(""))
			{
				sql+=" and PROCEDURE_STATUS='"+searchRequestType+"' ";
			}
			sql+=" and PROCEDURE_SEGMENT='1'";
			//sql+=" and EXPIRE_STATUS<>0";
			
		}else {
			sql+=" where  PROCEDURE_SEGMENT='2'";
			//sql+=" and EXPIRE_STATUS<>0";
			
			if(!searchCity.equals("") && !searchDate.equals("")) {
				sql+=" and CITY='"+searchCity+"' and  to_char(T.LAUNCH_TIME,'yyyy-MM')='"+searchDate+"'";
			}else if(searchCity.equals("") && !searchDate.equals("")) {
				sql+=" and  to_char(T.LAUNCH_TIME,'yyyy-MM')='"+searchDate+"'";
			}else if(!searchCity.equals("") && searchDate.equals("")) {
				sql+=" and CITY='"+searchCity+"'";
			}
			if   (!searchRequestType.equals("全部类型") && !searchRequestType.equals(""))
			{
				sql+=" and PROCEDURE_STATUS='"+searchRequestType+"'";
			}
			
			if ("灰名单省专业管理员（市场）".equals(rolename)) {
				sql+=" and ATTRIBUTION='市场'";
			}else if ("灰名单省专业管理员（维护）".equals(rolename)) {
				sql+=" and ATTRIBUTION='维护'";
			}else if ("灰名单省专业管理员（财务）".equals(rolename)) {
				sql+=" and ATTRIBUTION='财务'";
			}
			
			
			
			
		}
	
		Integer count = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM("+sql+")");
		Integer lastIndex = displayStart+iDisplayLength;
		String pageSql = "";
		pageSql+="SELECT B.* FROM(";
		pageSql+="	SELECT A.*,ROWNUM AS RN FROM("+sql+") A WHERE ROWNUM <= "+lastIndex;
		pageSql+=") B WHERE B.RN > "+displayStart;
		List<Map<String,Object>> objectList = jdbcTemplate.queryForList(pageSql);
		DataTableResult<Map<String,Object>> tableData = new DataTableResult<Map<String,Object>>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(objectList);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
}

	
	
	
	
	
	

