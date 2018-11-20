package com.function.index.risk;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.systemConfig.model.DataTableResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("com.function.index.risk.feedbackDataListDetailsAction")
@RequestMapping(value="/feedbackDataListDetailsAction")
public class FeedbackDataListDetailsAction {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static String resUserName = "TOWERCRNOP";
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/findFeedbackDataListDetails.ilf")
	public void findFeedbackDataListDetails(HttpServletRequest request,HttpServletResponse response,String id,String risk_name,String tab_id) throws Exception{
		JSONObject jsonObject=JSONObject.fromObject("{success:true}");
		String sql="";
		sql="select * from "+resUserName+"."+RiskControlTable.getValueByKey(tab_id)+" where ID="+id;
		jsonObject.put("list",jdbcTemplate.queryForList(sql));
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonObject.toString());
	}
	
}
