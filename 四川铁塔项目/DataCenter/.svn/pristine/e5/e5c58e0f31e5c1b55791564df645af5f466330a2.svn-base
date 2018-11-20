package com.function.index.risk;
import java.util.ArrayList;
/**
 * @author fengfeng02
 * 
 */
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
@Controller("com.function.index.risk.ParameterConfigurationAction")
@RequestMapping(value="/ParameterConfigurationAction")
public class ParameterConfigurationAction {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	private static String resUserName1 = "TOWERCRNOP";
	//展示这四大类的数据
	@RequestMapping("/findParameterConfigurationDatas.ilf")
	public void findParameterConfigurationDatas(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject jsonObject=JSONObject.fromObject("{success:true}");
		String sql="SELECT  ROWNUM NO,ID,RISK_NAME,PARA_VALUE FROM TOWERCRNOP.ORC_PARA_CONFIG WHERE RISK_TYPE ='资金问题'";
		String sqla="SELECT ROWNUM NO,ID,RISK_NAME,PARA_VALUE FROM TOWERCRNOP.ORC_PARA_CONFIG WHERE RISK_TYPE ='场租费'";
		String sqlb="SELECT ROWNUM NO,ID,RISK_NAME,PARA_VALUE FROM TOWERCRNOP.ORC_PARA_CONFIG WHERE RISK_TYPE ='收入方面'";
		String sqlc="SELECT ROWNUM NO,ID,RISK_NAME,PARA_VALUE FROM TOWERCRNOP.ORC_PARA_CONFIG WHERE RISK_TYPE ='工程类'";
		//资金问题
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		//场租费
		List<Map<String, Object>> lista=new ArrayList<Map<String,Object>>();
		//收入方面 
		List<Map<String, Object>> listb=new ArrayList<Map<String,Object>>();
		 //工程类
		List<Map<String, Object>> listc=new ArrayList<Map<String,Object>>();
		//资金问题
		list=jdbcTemplate.queryForList(sql);
		//场租费
		lista=jdbcTemplate.queryForList(sqla);
		//收入方面
		listb=jdbcTemplate.queryForList(sqlb);
		//工程类
		listc=jdbcTemplate.queryForList(sqlc);
		jsonObject.put("fund_list", list);
		jsonObject.put("colocation_list", lista);
		jsonObject.put("income_list", listb);
		jsonObject.put("engineering_list", listc);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonObject.toString());
	}
	//查询数据
	@RequestMapping("/findParameterConfigurationEditChaDatas.ilf")
	public void findParameterConfigurationEditChaDatas(HttpServletRequest request,HttpServletResponse response,int num)throws Exception{
		JSONObject jsonObject=JSONObject.fromObject("{success:true}");
		//资金问题
		String sql="SELECT  ROWNUM NO,ID,RISK_NAME,PARA_NAME,PARA_VALUE,RISK_TYPE FROM TOWERCRNOP.ORC_PARA_CONFIG WHERE ID = "+num;
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		list=jdbcTemplate.queryForList(sql);
		jsonObject.put("fund_list", list);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonObject.toString());
	}

	//更新数据
	@RequestMapping("/findParameterConfigurationEditGxDatas.ilf")
	public void findParameterConfigurationEditGxDatas(HttpServletRequest request,HttpServletResponse response,int wb_id,int wb_value)throws Exception{
		JSONObject jsonObject=JSONObject.fromObject("{success:true}");
		String sql="UPDATE TOWERCRNOP.ORC_PARA_CONFIG  SET PARA_VALUE =  "+ wb_value +" WHERE ID = "+wb_id;
		//资金问题
		jdbcTemplate.execute(sql);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonObject.toString());
	}
	
}
