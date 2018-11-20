package com.function.index.greyList.action;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.function.index.greyList.model.GreyHistory;
import com.function.index.greyList.model.GreyList;
import com.function.index.greyList.service.IGreyListService;
import com.function.index.risk.RiskControlTable;
import com.system.LoginUserUtil;
import com.systemConfig.model.DataTableResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("com.function.index.greyList.action.todoListDataDetail")
@RequestMapping(value="/todoListDataDetail")
public class todoListDataDetail {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private IGreyListService IGreyListService;
	/*
	 * 	展示灰名单数据列表
	 * 
	 * */
	@RequestMapping("/showDataDetail.ilf")
	public void  showDataDetail(
		@RequestParam String id,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{	
		JSONObject jsonObject=JSONObject.fromObject("{success:true}");
	/*
		 * 	获取灰名单数据详情的sql语句
		 * 
		 * */
		String sql = "SELECT T. ID,T.COUNTY,T.SA_NAME,SA_CODE,T.CITY,T.TABLE_SOURCE,T.ORIGINATOR,T.GL_TYPE,T.GL_DESCRIBE,T.ATTRIBUTION,T.DATA_SOURCE,"
				+ "T.GL_RULE,to_char(T.LAUNCH_TIME,'yyyy-MM-DD')  LAUNCH_TIME,T.CITY_APPROVER,to_char(T.C_A_TIME,'yyyy-MM-DD')  C_A_TIME,to_char(T.GLS_TIME,'yyyy-MM-DD')  GLS_TIME,T.VALID_TIME,to_char(EXPIRE_TIME,'yyyy-MM-DD')  EXPIRY_TIME,  REMARKS   FROM GL_GREY_LIST  T "
				+ "WHERE T.ID="+id;
		
		jsonObject.put("list",jdbcTemplate.queryForList(sql));
		System.out.println(jdbcTemplate.queryForList(sql).toString());
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonObject.toString());
	}	
	
	/*
	 * 	审批完成数据操作
	 * 
	 * */
	@RequestMapping("/approveDone.ilf")
	public void  approveDone(
		@RequestParam String id,
		@RequestParam String approve_decision,
		HttpServletRequest request,
		HttpServletResponse response,
		String rejectReason
	)throws Exception{	
		
		
		/***
		 * 获取登录信息判断是否是省级人员或者找出地市人员的所属地市
		 * 获取登录人员姓名
		 */
		JSONObject jsonObject=JSONObject.fromObject("{success:true}");
		Boolean IS_PROVICE = false;
		String LoginUserName="";
		Object loginObject = request.getSession().getAttribute("LoginUserInfo");
		if(loginObject!=null){
			Map<String,Object> loginUser = (HashMap<String,Object>)loginObject;
			if(loginUser.get("BELONG_AREA").toString().indexOf("省")!=-1){
				IS_PROVICE = true;
			}
			else
			{
				IS_PROVICE = false;
			}
			if(loginUser.get("EMPLOYEE_NAME")==null){
				LoginUserName= "-";
			}else{
				LoginUserName= loginUser.get("EMPLOYEE_NAME").toString();
			}
			
		}
		
	/*
		 * 	获取灰名单数据详情的sql语句
		 * 
		 * */
		String hql="from GreyList where id=?";
		GreyList greyList=IGreyListService.getGreyListById(hql, Long.parseLong(id)).get(0);
		String status=greyList.getProcedureStatus();
		Calendar calendar=Calendar.getInstance();
		Integer vaildTime=1;
		if ("apply".equals(status)) {
			if(IS_PROVICE)
			{
				if(approve_decision.equals("No"))
				{
					IGreyListService.dataRecovery(Integer.parseInt(id));
					greyList=IGreyListService.getGreyListById(hql, Long.parseLong(id)).get(0);
					greyList.setRejectReason(rejectReason);
				}
				else{
					greyList.setExpireStatus(1);
					greyList.setProvinceApprover(LoginUserName);
					greyList.setPaTime(new Date());
					greyList.setProcedureSegment("3");
					greyList.setGlsTime(new Date());
					calendar.setTime(new Date());
					vaildTime=greyList.getValidTime();
					calendar.add(Calendar.MONTH, vaildTime);
					greyList.setExpireTime(calendar.getTime());
				}
			}
			else
			{
				if(approve_decision.equals("No"))
				{
					IGreyListService.dataRecovery(Integer.parseInt(id));
					greyList=IGreyListService.getGreyListById(hql, Long.parseLong(id)).get(0);
					greyList.setRejectReason(rejectReason);
				}
				else{
					greyList.setExpireStatus(1);
					greyList.setCityApprover(LoginUserName);
					greyList.setCaTime(new Date());
					greyList.setProcedureSegment("2");
				}
				
		
			}
			IGreyListService.updateGreyOrder(greyList);
		}else if ("expire".equals(status)) {
			if(IS_PROVICE)
			{
				if(approve_decision.equals("No"))
				{
					IGreyListService.dataRecovery(Integer.parseInt(id));
					greyList=IGreyListService.getGreyListById(hql, Long.parseLong(id)).get(0);
					greyList.setRejectReason(rejectReason);
				}
				else{
					greyList.setProvinceApprover(LoginUserName);
					greyList.setPaTime(new Date());
					greyList.setProcedureSegment("3");
					calendar.setTime(greyList.getExpireTime());
					vaildTime=greyList.getValidTime();
					calendar.add(Calendar.MONTH, vaildTime);
					greyList.setExpireTime(calendar.getTime());
				}
			}
			else
			{
				if(approve_decision.equals("No"))
				{
					IGreyListService.dataRecovery(Integer.parseInt(id));
					greyList=IGreyListService.getGreyListById(hql, Long.parseLong(id)).get(0);
					greyList.setRejectReason(rejectReason);
				}
				else{
					greyList.setCityApprover(LoginUserName);
					greyList.setCaTime(new Date());
					greyList.setProcedureSegment("2");
				}
			}
			IGreyListService.updateGreyOrder(greyList);
		}else if ("remove".equals(status)) {
			if(IS_PROVICE)
			{
				if(approve_decision.equals("No"))
				{
					IGreyListService.dataRecovery(Integer.parseInt(id));
					greyList=IGreyListService.getGreyListById(hql, Long.parseLong(id)).get(0);
					greyList.setRejectReason(rejectReason);
				}
				else{
					greyList.setProvinceApprover(LoginUserName);
					greyList.setPaTime(new Date());
					greyList.setProcedureSegment("3");
					greyList.setGlExitTime(new Date());
					greyList.setExpireStatus(0);
				}
			}
			else
			{
				if(approve_decision.equals("No"))
				{
					IGreyListService.dataRecovery(Integer.parseInt(id));
					greyList=IGreyListService.getGreyListById(hql, Long.parseLong(id)).get(0);
					greyList.setRejectReason(rejectReason);
				}
				else{
					greyList.setCityApprover(LoginUserName);
					greyList.setCaTime(new Date());
					greyList.setProcedureSegment("2");
				}
			}
			IGreyListService.updateGreyOrder(greyList);
		}
    	response.setContentType("application/json; charset=UTF-8");
 	   	response.getWriter().print(jsonObject.toString());

	
	
		
	}	
	
	
	
}

	
	
	
	
	
	

