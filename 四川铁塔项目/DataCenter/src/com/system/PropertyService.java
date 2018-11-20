package com.system;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller("com.system.PropertyService")
@RequestMapping(value="/propertyService")
public class PropertyService{
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/*
	 * 	获取专业列表.
	 * 
	 * */
	@RequestMapping("/getMajorList.ilf")
	public void getMajorList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			resultObject.put("MAJORS",jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'MODEL_MAJOR'"));
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
}
