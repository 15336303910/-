package com.function.index;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.system.LoginUserUtil;
@Controller("com.function.index.BuildAssistCountAction")
@RequestMapping(value="/buildAssistCountAction")
public class BuildAssistCountAction{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	/*
	 * 	获取首页统计数据
	 * 
	 * */
	@RequestMapping("/findCountInIndex.ilf")
	public void findCountInIndex(
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			Boolean isProvince = loginUserUtil.isProvince(request);
			String belongArea = loginUserUtil.getBelongArea(request);
			String sql = "";
			/*
			 * 	统计.
			 * 
			 * */
			if(isProvince){
				resultObject.put("IS_PROVINCE",true);
				sql+="SELECT SUM(T.ALL_SUM) AS PMS在建项目数,SUM(T.COMPLETE_SUM) AS 工程超期数,ROUND(AVG(T.PROJECT_DELAY_RATIO),2) AS 工程超期率 FROM(";
				sql+="	  SELECT T.CITY_NAME,T.ALL_SUM,T.COMPLETE_SUM,ROUND(T.RATE,2) AS PROJECT_DELAY_RATIO";
				sql+="	  FROM S_HOME_JSFZ T ";
				sql+="	  WHERE T.DATE_TYPE = 4 AND T.DATE_TIME IN (";
				sql+="	  	  SELECT MAX(T.DATE_TIME) FROM S_HOME_JSFZ T WHERE T.DATE_TYPE = 4";
				sql+="	  )";
				sql+=") T";
				List<Map<String,Object>> countObjs = jdbcTemplate.queryForList(sql);
				resultObject.put("TITLE_DATA_COUNT",countObjs.size()==0?new HashMap():countObjs.get(0));
			}else{
				resultObject.put("IS_PROVINCE",false);
				resultObject.put("COUNT_CITY",loginUserUtil.getBelongArea(request));
				sql+="SELECT SUM(T.ALL_SUM) AS PMS在建项目数,SUM(T.COMPLETE_SUM) AS 工程超期数,ROUND(AVG(T.PROJECT_DELAY_RATIO),2) AS 工程超期率 FROM(";
				sql+="	  SELECT T.CITY_NAME,T.ALL_SUM,T.COMPLETE_SUM,ROUND(T.RATE,2) AS PROJECT_DELAY_RATIO";
				sql+="	  FROM S_HOME_JSFZ T ";
				sql+="	  WHERE T.DATE_TYPE = 4 AND T.DATE_TIME IN (";
				sql+="	  	  SELECT MAX(T.DATE_TIME) FROM S_HOME_JSFZ T WHERE T.DATE_TYPE = 4";
				sql+="	  )";
				sql+=") T WHERE T.CITY_NAME LIKE '%"+belongArea+"%'";
				List<Map<String,Object>> countObjs = jdbcTemplate.queryForList(sql);
				resultObject.put("TITLE_DATA_COUNT",countObjs.size()==0?new HashMap():countObjs.get(0));
			}
			/*
			 * 	需求承接预审
			 * 
			 * */
			sql = "";
			if(isProvince){
				sql+="SELECT T.CITY_NAME 地市区县,SUM(T.ALL_SUM) 需求预审单数,SUM(T.COMPLETE_SUM) 审批完成数,ROUND(SUM(T.COMPLETE_SUM) / NVL(SUM(T.ALL_SUM), 999999), 2) 审批完成率  ";
				sql+="FROM S_HOME_JSFZ T  ";
				sql+="WHERE T.DATE_TYPE = 1 AND T.DATE_TIME IN (";
				sql+="	  SELECT MAX(T.DATE_TIME) FROM S_HOME_JSFZ T WHERE T.DATE_TYPE = 1";
				sql+=") GROUP BY T.CITY_NAME ORDER BY 审批完成率 DESC";
				List<Map<String,Object>> countObjs = jdbcTemplate.queryForList(sql);
				resultObject.put("ACCEPT_NEEDS_AUDIT_FINISH_RATIO",countObjs);
			}else{
				sql+="SELECT T.COUNTY_NAME 地市区县,T.ALL_SUM 需求预审单数,T.COMPLETE_SUM 审批完成数,ROUND(T.RATE, 2) 审批完成率 ";
				sql+="FROM S_HOME_JSFZ T ";
				sql+="WHERE T.DATE_TYPE = 1 AND T.DATE_TIME IN (";
				sql+="	  SELECT MAX(T.DATE_TIME) FROM S_HOME_JSFZ T WHERE T.DATE_TYPE = 1";
				sql+=") AND T.CITY_NAME LIKE '%"+belongArea+"%' ORDER BY 审批完成率 DESC ";
				List<Map<String,Object>> countObjs = jdbcTemplate.queryForList(sql);
				resultObject.put("ACCEPT_NEEDS_AUDIT_FINISH_RATIO",countObjs);
			}
			/*
			 * 	服务订单管理
			 * 
			 * */
			sql = "";
			if(isProvince){
				sql+="SELECT T.CITY_NAME 地市区县,SUM(T.ALL_SUM) 服务订单发起数,SUM(T.COMPLETE_SUM) 审批完成数,ROUND(SUM(T.COMPLETE_SUM) / NVL(SUM(T.ALL_SUM), 999999), 2) 审批完成率   ";
				sql+="FROM S_HOME_JSFZ T   ";
				sql+="WHERE T.DATE_TYPE = 2 AND T.DATE_TIME IN (";
				sql+="	  SELECT MAX(T.DATE_TIME) FROM S_HOME_JSFZ T WHERE T.DATE_TYPE = 2";
				sql+=") GROUP BY T.CITY_NAME ORDER BY 审批完成率 DESC ";
				List<Map<String,Object>> countObjs = jdbcTemplate.queryForList(sql);
				resultObject.put("SERVICE_ORDER_COUNT",countObjs);
			}else{
				sql+="SELECT T.COUNTY_NAME 地市区县,T.ALL_SUM 服务订单发起数,T.COMPLETE_SUM 审批完成数,ROUND(T.RATE, 2) 审批完成率  ";
				sql+="FROM S_HOME_JSFZ T  ";
				sql+="WHERE T.DATE_TYPE = 2 AND T.DATE_TIME IN (";
				sql+="	  SELECT MAX(T.DATE_TIME) FROM S_HOME_JSFZ T WHERE T.DATE_TYPE = 2";
				sql+=") AND T.CITY_NAME LIKE '%"+belongArea+"%' ORDER BY 审批完成率 DESC";
				List<Map<String,Object>> countObjs = jdbcTemplate.queryForList(sql);
				resultObject.put("SERVICE_ORDER_COUNT",countObjs);
			}
			/*
			 * 	审签管理
			 * 
			 * */
			sql = "";
			if(isProvince){
				sql+="SELECT T.CITY_NAME 地市区县,SUM(T.ALL_SUM) 审签发起工单数,SUM(T.COMPLETE_SUM) 审批完成数,ROUND(SUM(T.COMPLETE_SUM) / NVL(SUM(T.ALL_SUM), 999999), 2) 审批完成率 ";
				sql+="FROM S_HOME_JSFZ T  ";
				sql+="WHERE T.DATE_TYPE = 3 AND T.DATE_TIME IN (";
				sql+="	  SELECT MAX(T.DATE_TIME) FROM S_HOME_JSFZ T WHERE T.DATE_TYPE = 3";
				sql+=") GROUP BY T.CITY_NAME ORDER BY 审批完成率 DESC ";
				List<Map<String,Object>> countObjs = jdbcTemplate.queryForList(sql);
				resultObject.put("CHECK_AUDIT_COUNT",countObjs);
			}else{
				sql+="SELECT T.COUNTY_NAME 地市区县,T.ALL_SUM 审签发起工单数,T.COMPLETE_SUM 审批完成数,ROUND(T.RATE, 2) 审批完成率  ";
				sql+="FROM S_HOME_JSFZ T  ";
				sql+="WHERE T.COUNTY_NAME IS NOT NULL AND T.DATE_TYPE = 3 AND T.DATE_TIME IN ( ";
				sql+="	  SELECT MAX(T.DATE_TIME) FROM S_HOME_JSFZ T WHERE T.DATE_TYPE = 3";
				sql+=") AND T.CITY_NAME LIKE '%"+belongArea+"%' ORDER BY 审批完成率 DESC";
				List<Map<String,Object>> countObjs = jdbcTemplate.queryForList(sql);
				resultObject.put("CHECK_AUDIT_COUNT",countObjs);
			}
			/*
			 * 	外验交付管理
			 * 
			 * */
			sql = "";
			if(isProvince){
				sql+="SELECT T.CITY_NAME 地市区县,SUM(T.ALL_SUM) 外验发起工单数,SUM(T.COMPLETE_SUM) 工单完成数,ROUND(SUM(T.COMPLETE_SUM) / NVL(SUM(T.ALL_SUM), 999999), 2) 工单完成率   ";
				sql+="FROM DB_QUALITY.S_HOME_JSFZ T  ";
				sql+="WHERE T.DATE_TYPE = 5 AND T.DATE_TIME IN (";
				sql+="	  SELECT MAX(T.DATE_TIME) FROM DB_QUALITY.S_HOME_JSFZ T WHERE T.DATE_TYPE = 5";
				sql+=") GROUP BY T.CITY_NAME ORDER BY 工单完成率 DESC ";
				List<Map<String,Object>> countObjs = jdbcTemplate.queryForList(sql);
				resultObject.put("OUTCHECK_JOBS",countObjs);
			}else{
				sql+="SELECT T.COUNTY_NAME 地市区县,T.ALL_SUM 外验发起工单数,T.COMPLETE_SUM 工单完成数,ROUND(T.RATE, 2) 工单完成率 ";
				sql+="FROM DB_QUALITY.S_HOME_JSFZ T ";
				sql+="WHERE T.DATE_TYPE = 5 AND T.DATE_TIME IN (";
				sql+="	  SELECT MAX(T.DATE_TIME) FROM DB_QUALITY.S_HOME_JSFZ T WHERE T.DATE_TYPE = 5";
				sql+=") AND T.CITY_NAME LIKE '%"+belongArea+"%' ORDER BY 工单完成率 DESC";
				List<Map<String,Object>> countObjs = jdbcTemplate.queryForList(sql);
				resultObject.put("OUTCHECK_JOBS",countObjs);
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
}
