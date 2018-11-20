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
@Controller("com.function.index.region.ElecCostPayout")
@RequestMapping(value="/elecCostPayout")
public class ElecCostPayout{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	private static String resUserName = "RMW";
	
	/*
	 * 
	 * 	获取地市
	 * 
	 * */
	@RequestMapping("/findCitys.ilf")
	public void findCitys(@RequestParam String typeCode,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject jsonObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			if(typeCode!=null && "A".equals(typeCode)){
				/*
				 * 	已确认电费数.
				 * 
				 * */
				String sql = "SELECT DISTINCT(CITY) FROM RMW.WY_ELECTRI_FEE_DS WHERE CITY IS NOT NULL";
				if(!loginUserUtil.isProvince(request)){
					sql+=" AND CITY LIKE '%"+loginUserUtil.getBelongArea(request)+"%'";
				}
				jsonObject.put("Citys",jdbcTemplate.queryForList(sql));
			}else if(typeCode!=null && "B".equals(typeCode)){
				/*
				 * 	场租待续签数.
				 * 
				 * */
				String sql = "SELECT DISTINCT(CITY) FROM RMW.WY_CONTRACT_INFO WHERE CITY IS NOT NULL";
				if(!loginUserUtil.isProvince(request)){
					sql+=" AND CITY LIKE '%"+loginUserUtil.getBelongArea(request)+"%'";
				}
				jsonObject.put("Citys",jdbcTemplate.queryForList(sql));
			}else if(typeCode!=null && "C".equals(typeCode)){
				/*
				 * 	已巡检站数.
				 * 
				 * */
				String sql = "SELECT DISTINCT(CITY) FROM RMW.WH_BASIC_INSPECT WHERE CITY IS NOT NULL";
				if(!loginUserUtil.isProvince(request)){
					sql+=" AND CITY LIKE '%"+loginUserUtil.getBelongArea(request)+"%'";
				}
				jsonObject.put("Citys",jdbcTemplate.queryForList(sql));
			}else if(typeCode!=null && "D".equals(typeCode)){
				/*
				 * 	活动告警数.
				 * 
				 * */
				String sql = "SELECT DISTINCT(CITY_NAME) FROM TOWERCRNOP.YWJK_ACTIVE_ALARM WHERE CITY_NAME IS NOT NULL";
				if(!loginUserUtil.isProvince(request)){
					sql+=" AND CITY_NAME LIKE '%"+loginUserUtil.getBelongArea(request)+"%'";
				}
				jsonObject.put("Citys",jdbcTemplate.queryForList(sql));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(JSONObject.fromObject(jsonObject).toString());
		}
	}
	
	/*
	 * 
	 * 	获取区县
	 * 
	 * */
	@RequestMapping("/findRegions.ilf")
	public void findRegions(
		@RequestParam String typeCode,
		@RequestParam String cityName,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject jsonObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			if(typeCode!=null && "1".equals(typeCode)){
				/*
				 * 	已确认电费数.
				 * 
				 * */
				String sql = "SELECT DISTINCT(AREA) AS REGION_NAME FROM RMW.WY_ELECTRI_FEE_DS WHERE AREA IS NOT NULL AND CITY = '"+cityName+"'";
				jsonObject.put("Regions",jdbcTemplate.queryForList(sql));
			}else if(typeCode!=null && "2".equals(typeCode)){
				/*
				 * 	场租待续签数.
				 * 
				 * */
				String sql = "SELECT DISTINCT(AREA) AS REGION_NAME FROM RMW.WY_CONTRACT_INFO WHERE AREA IS NOT NULL AND CITY = '"+cityName+"'";
				jsonObject.put("Regions",jdbcTemplate.queryForList(sql));
			}else if(typeCode!=null && "3".equals(typeCode)){
				/*
				 * 	已巡检站数.
				 * 
				 * */
				String sql = "SELECT DISTINCT(REGION_ID) AS REGION_NAME FROM RMW.WH_BASIC_INSPECT WHERE REGION_ID IS NOT NULL AND CITY = '"+cityName+"'";
				jsonObject.put("Regions",jdbcTemplate.queryForList(sql));
			}else if(typeCode!=null && "4".equals(typeCode)){
				/*
				 * 	活动告警数.
				 * 
				 * */
				String sql = "SELECT DISTINCT(COUNTY_NAME) FROM TOWERCRNOP.YWJK_ACTIVE_ALARM WHERE COUNTY_NAME IS NOT NULL AND CITY_NAME = '"+cityName+"'";
				jsonObject.put("Regions",jdbcTemplate.queryForList(sql));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(JSONObject.fromObject(jsonObject).toString());
		}
	}
	
	/*
	 * 	查询电费缴纳明细
	 * 
	 * */
	@RequestMapping("/findDatas.ilf")
	public void findDatas(@RequestParam String tableparam,@RequestParam String conditions,HttpServletRequest request,HttpServletResponse response)throws Exception{
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
		 * 	场租续签数
		 * 
		 * */
		String sql = "";
		sql+="SELECT * FROM (";
		sql+="	  SELECT '直供电支付台账' 台账类型,T.CITY 地市,T.AREA 区县,T.POWER_SUPPLY 供电公司,T.SITE_CODE 站址编码,T.SITE_NAME 站址名称,T.PAY_NUMBER 缴费编号,T.POWER_SUPPLY_T 供电方式,T.ELECTRI_FEE 缴费日期,T.ELECTRI_START \"电表数（起）\",T.ELECTRI_END \"电表数（止）\",T.UNIT_PRICE 单价,T.PAY_ELECTRI_FEE 缴费金额,SUBSTR(T.ELECTRI_FEE,0,4) || SUBSTR(T.ELECTRI_FEE,6,2) MONTH";
		sql+="	  FROM RMW.WY_ELECTRI_FEE_DS T";
		sql+="	  WHERE T.MOVE_PAYQR = 0 AND T.TELECOM_PAYQR = 0 AND T.UNICOM_PAYQR = 0 AND T.STATEFLAG = 1";
		sql+="	  UNION ALL";
		sql+="	  SELECT '转供电支付台账' 台账类型,T.CITY 地市,T.AREA 区县,T.POWER_SUPPLY 供电公司,T.SITE_CODE 站址编码,T.SITE_NAME 站址名称,T.PAY_NUMBER 缴费编号,T.POWER_SUPPLY_T 供电方式,T.ELECTRI_FEE 缴费日期,T.ELECTRI_START \"电表数（起）\",T.ELECTRI_END \"电表数（止）\",T.UNIT_PRICE 单价,T.PAY_ELECTRI_FEE 缴费金额,SUBSTR(T.ELECTRI_FEE,0,4) || SUBSTR(T.ELECTRI_FEE,6,2) MONTH";
		sql+="	  FROM RMW.WY_ELECTRI_FEE_TURN T";
		sql+="	  WHERE T.MOVE_PAYQR = 0 AND T.TELECOM_PAYQR = 0 AND T.UNICOM_PAYQR = 0 AND T.STATEFLAG = 1";
		sql+="	  UNION ALL";
		sql+="	  SELECT '预付费核销台账' 台账类型,T.CITY 地市,T.AREA 区县,T.POWER_SUPPLY 供电公司,T.SITE_CODE 站址编码,T.SITE_NAME 站址名称,T.PAY_NUMBER 缴费编号,T.POWER_SUPPLY_T 供电方式,T.ELECTRI_FEE 缴费日期,T.ELECTRI_START \"电表数（起）\",T.ELECTRI_END \"电表数（止）\",T.UNIT_PRICE 单价,T.PAY_ELECTRI_FEE 缴费金额,SUBSTR(T.ELECTRI_FEE,0,4) || SUBSTR(T.ELECTRI_FEE,6,2) MONTH";
		sql+="	  FROM RMW.WY_ELECTRI_FEE_YFHX T";
		sql+="	  WHERE T.MOVE_PAYQR = 0 AND T.TELECOM_PAYQR = 0 AND T.UNICOM_PAYQR = 0 AND T.STATEFLAG = 1";
		sql+=") S WHERE S.MONTH IN (SELECT MAX(TO_NUMBER(R.MONTH))FROM ((";
		sql+="	  SELECT SUBSTR(T.ELECTRI_FEE,0,4) || SUBSTR(T.ELECTRI_FEE,6,2) MONTH FROM RMW.WY_ELECTRI_FEE_DS T WHERE T.MOVE_PAYQR = 0 AND T.TELECOM_PAYQR = 0 AND T.UNICOM_PAYQR = 0 AND T.STATEFLAG = 1";
		sql+="	  UNION ALL";
		sql+="	  SELECT SUBSTR(T.ELECTRI_FEE,0,4) || SUBSTR(T.ELECTRI_FEE,6,2) MONTH FROM RMW.WY_ELECTRI_FEE_TURN T WHERE T.MOVE_PAYQR = 0 AND T.TELECOM_PAYQR = 0 AND T.UNICOM_PAYQR = 0 AND T.STATEFLAG = 1 ";
		sql+="	  UNION ALL";
		sql+="	  SELECT SUBSTR(T.ELECTRI_FEE,0,4) || SUBSTR(T.ELECTRI_FEE,6,2) MONTH FROM RMW.WY_ELECTRI_FEE_YFHX T WHERE T.MOVE_PAYQR = 0 AND T.TELECOM_PAYQR = 0 AND T.UNICOM_PAYQR = 0 AND T.STATEFLAG = 1";
		sql+=")) R WHERE 1 = 1";
		if(conditonMap.containsKey("SITE_NAME") && !"".equals(conditonMap.get("SITE_NAME").toString())){
			sql+=" AND 站址名称  LIKE '%"+conditonMap.get("SITE_NAME").toString()+"%'";
		}
		if(!IS_PROVICE){
			sql+=" AND 地市  LIKE '%"+CITY_NAME+"%'";
		}else{
			if(conditonMap.containsKey("CITY") && !"".equals(conditonMap.get("CITY").toString()) && !"-".equals(conditonMap.get("CITY").toString())){
				sql+=" AND 地市   LIKE '%"+conditonMap.get("CITY").toString()+"%'";
			}
		}
		if(conditonMap.containsKey("REGION") && !"".equals(conditonMap.get("REGION").toString()) && !"-".equals(conditonMap.get("REGION").toString())){
			sql+=" AND 区县   LIKE '%"+conditonMap.get("REGION").toString()+"%'";
		}
		sql+=")";
		Integer count = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM("+sql+")");
		Integer lastIndex = displayStart+iDisplayLength;
		String pageSql = "";
		pageSql+="SELECT X.* FROM(";
		pageSql+="	SELECT V.*,ROWNUM AS RN FROM("+sql+") V WHERE ROWNUM <= "+lastIndex;
		pageSql+=") X WHERE X.RN > "+displayStart;
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
