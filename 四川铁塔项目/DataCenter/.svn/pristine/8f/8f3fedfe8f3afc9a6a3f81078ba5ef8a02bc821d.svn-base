package com.function.index.region;
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

import com.system.LoginUserUtil;
import com.systemConfig.model.DataTableResult;
@Controller("com.function.index.region.AlarmDetail")
@RequestMapping(value="/alarmDetail")
public class AlarmDetail{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	
	/*
	 * 	活动告警明细
	 * 
	 * */
	@RequestMapping("/findDatas.ilf")
	public void findDatas(
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
		Boolean IS_PROVICE = false;
		String CITY_NAME = "";
		Object loginObject = request.getSession().getAttribute("LoginUserInfo");
		if(loginObject!=null){
			Map<String,Object> loginUser = (HashMap<String,Object>)loginObject;
			if(loginUser.get("BELONG_AREA").toString().indexOf("省")!=-1){
				IS_PROVICE = true;
				CITY_NAME = loginUser.get("BELONG_AREA").toString();
			}else{
				CITY_NAME = loginUser.get("BELONG_AREA").toString();
			}
			if(CITY_NAME.length()>2){
				CITY_NAME = CITY_NAME.substring(0,2);
			}
		}
		/*
		 * 	活动告警明细
		 * 
		 * */
		String sql = "SELECT T.* FROM TOWERCRNOP.YWJK_ACTIVE_ALARM T WHERE T.ALARM_LEVEL IS NOT NULL ";
		if(conditonMap.containsKey("SITE_CODE_OR_NAME") && !"".equals(conditonMap.get("SITE_CODE_OR_NAME").toString())){
			sql+=" AND (T.SITE_CODE LIKE '%"+conditonMap.get("SITE_CODE_OR_NAME").toString()+"%' OR T.TOWER_NAME LIKE '%"+conditonMap.get("SITE_CODE_OR_NAME").toString()+"%')";
		}
		if(conditonMap.containsKey("ALARM_NAME") && !"".equals(conditonMap.get("ALARM_NAME").toString())){
			sql+=" AND T.ALARM_NAME LIKE '%"+conditonMap.get("ALARM_NAME").toString()+"%'";
		}
		if(!IS_PROVICE){
			sql+=" AND T.CITY_NAME LIKE '%"+CITY_NAME+"%'";
		}else{
			if(conditonMap.containsKey("CITY_NAME") && !"".equals(conditonMap.get("CITY_NAME").toString()) && !"-".equals(conditonMap.get("CITY_NAME").toString())){
				sql+=" AND T.CITY_NAME LIKE '%"+conditonMap.get("CITY_NAME").toString()+"%'";
			}
		}
		if(conditonMap.containsKey("COUNTRY_NAME") && !"".equals(conditonMap.get("COUNTRY_NAME").toString()) && !"-".equals(conditonMap.get("COUNTRY_NAME").toString())){
			sql+=" AND T.COUNTY_NAME LIKE '%"+conditonMap.get("COUNTRY_NAME").toString()+"%'";
		}
		sql+=" ORDER BY T.ALARM_LEVEL DESC";
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
