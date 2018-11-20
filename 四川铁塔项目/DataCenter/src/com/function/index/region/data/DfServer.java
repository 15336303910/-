package com.function.index.region.data;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller("com.function.index.region.data.DfServer")
@RequestMapping(value="/dfServer")
public class DfServer{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*
	 * 	已确认电费站.
	 * 
	 * */
	@RequestMapping("/yqxzsServer.ilf")
	public void yqxzsServer(
		@RequestParam String CITY_NAME,
		@RequestParam String COUNT_DATE,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject jsonObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			String sql = "";
			sql+="SELECT PROVINCE,CITY,REGION_ID,SITE_CODE,SITE_NAME,SITE_TYPE,SITE_LEVEL,STATE ";
			sql+="FROM RMW.ZG_SITE WHERE SITE_CODE IN (";
			sql+="	  SELECT SITE_CODE FROM RMW.WY_ELECTRI_FEE_TOTAL ";
			sql+="	  WHERE ELECTRI_FEE = '"+COUNT_DATE+"' AND MOVE_QRSTATUS = '0' AND TELECOM_QRSTATUS = '0' AND UNICOM_QRSTATUS = '0' AND CITY LIKE '%"+CITY_NAME+"%'";
			sql+=")";
			jsonObject.put("DATAS",jdbcTemplate.queryForList(sql));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(jsonObject.toString());
		}
	}
	
	/*
	 * 	已确认未支付站.
	 * 
	 * */
	@RequestMapping("/yqrwzfServer.ilf")
	public void yqrwzfServer(
		@RequestParam String CITY_NAME,
		@RequestParam String COUNT_DATE,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject jsonObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			String sql = "";
			sql+="SELECT PROVINCE,CITY,REGION_ID,SITE_CODE,SITE_NAME,SITE_TYPE,SITE_LEVEL,STATE ";
			sql+="FROM RMW.ZG_SITE WHERE SITE_CODE IN (";
			sql+="	  SELECT SITE_CODE FROM RMW.WY_ELECTRI_FEE_TOTAL ";
			sql+="	  WHERE ELECTRI_FEE = '"+COUNT_DATE+"' AND MOVE_QRSTATUS = '0' AND TELECOM_QRSTATUS = '0' AND UNICOM_QRSTATUS = '0' AND MOVE_PAYQR = '1' OR TELECOM_PAYQR = '1' OR UNICOM_PAYQR = '1' AND CITY LIKE '%"+CITY_NAME+"%'";
			sql+=")";
			jsonObject.put("DATAS",jdbcTemplate.queryForList(sql));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(jsonObject.toString());
		}
	}
	
	/*
	 * 	已确认已支付站.
	 * 
	 * */
	@RequestMapping("/yqryzfServer.ilf")
	public void yqryzfServer(
		@RequestParam String CITY_NAME,
		@RequestParam String COUNT_DATE,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject jsonObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			String sql = "";
			sql+="SELECT PROVINCE,CITY,REGION_ID,SITE_CODE,SITE_NAME,SITE_TYPE,SITE_LEVEL,STATE ";
			sql+="FROM RMW.ZG_SITE WHERE SITE_CODE IN (";
			sql+="	  SELECT SITE_CODE FROM RMW.WY_ELECTRI_FEE_TOTAL ";
			sql+="	  WHERE ELECTRI_FEE = '"+COUNT_DATE+"' AND MOVE_QRSTATUS = '0' AND TELECOM_QRSTATUS = '0' AND UNICOM_QRSTATUS = '0' AND MOVE_PAYQR = '0' AND TELECOM_PAYQR = '0' AND UNICOM_PAYQR = '0' AND CITY LIKE '%"+CITY_NAME+"%'";
			sql+=")";
			jsonObject.put("DATAS",jdbcTemplate.queryForList(sql));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(jsonObject.toString());
		}
	}
	
	/*
	 * 	未确认站.
	 * 
	 * */
	@RequestMapping("/wqrzServer.ilf")
	public void wqrzServer(
		@RequestParam String CITY_NAME,
		@RequestParam String COUNT_DATE,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject jsonObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			String sql = "";
			sql+="SELECT PROVINCE,CITY,REGION_ID,SITE_CODE,SITE_NAME,SITE_TYPE,SITE_LEVEL,STATE ";
			sql+="FROM RMW.ZG_SITE WHERE SITE_CODE IN (";
			sql+="	  SELECT SITE_CODE FROM RMW.WY_ELECTRI_FEE_TOTAL ";
			sql+="	  WHERE ELECTRI_FEE = '"+COUNT_DATE+"' AND MOVE_QRSTATUS = '1' AND TELECOM_QRSTATUS = '1' AND UNICOM_QRSTATUS = '1' AND CITY LIKE '%"+CITY_NAME+"%'";
			sql+=")";
			jsonObject.put("DATAS",jdbcTemplate.queryForList(sql));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(jsonObject.toString());
		}
	}
	
	/*
	 * 	未确认已支付站.
	 * 
	 * */
	@RequestMapping("/wqryzfzServer.ilf")
	public void wqryzfzServer(
		@RequestParam String CITY_NAME,
		@RequestParam String COUNT_DATE,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject jsonObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			String sql = "";
			sql+="SELECT PROVINCE,CITY,REGION_ID,SITE_CODE,SITE_NAME,SITE_TYPE,SITE_LEVEL,STATE ";
			sql+="FROM RMW.ZG_SITE WHERE SITE_CODE IN (";
			sql+="	  SELECT SITE_CODE FROM RMW.WY_ELECTRI_FEE_TOTAL ";
			sql+="	  WHERE ELECTRI_FEE = '"+COUNT_DATE+"' AND (MOVE_QRSTATUS = '1' OR TELECOM_QRSTATUS = '1' OR UNICOM_QRSTATUS = '1') AND (MOVE_PAYQR = '0' AND TELECOM_PAYQR = '0' AND UNICOM_PAYQR = '0') AND CITY LIKE '%"+CITY_NAME+"%'";
			sql+=")";
			jsonObject.put("DATAS",jdbcTemplate.queryForList(sql));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(jsonObject.toString());
		}
	}
}
