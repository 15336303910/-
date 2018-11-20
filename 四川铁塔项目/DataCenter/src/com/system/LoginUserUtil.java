package com.system;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Repository;
@Repository("loginUserUtil")
public class LoginUserUtil{

	/*
	 * 	当前是否有登录账户
	 * 
	 * */
	public static Boolean isLogined(HttpServletRequest request){
		Object object = request.getSession().getAttribute("LoginUserInfo");
		if(object!=null){
			return true;
		}else{
			return false;
		}
	}
	
	/*
	 * 	获取当前登录用户:ID --------------------------------------------------------------------------------------
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static Integer getLoginUserId(HttpServletRequest request){
		Map<String,Object> loginUser = (Map<String,Object>)request.getSession().getAttribute("LoginUserInfo");
		return Integer.parseInt(loginUser.get("ID").toString());
	}
	
	/*
	 * 	获取当前登录用户:NAME
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static String getEmployeeName(HttpServletRequest request){
		Map<String,Object> loginUser = (Map<String,Object>)request.getSession().getAttribute("LoginUserInfo");
		if(loginUser.get("EMPLOYEE_NAME")==null){
			return "-";
		}else{
			return loginUser.get("EMPLOYEE_NAME").toString();
		}
	}
	
	/*
	 * 	获取当前登录用户:Company_Name ----------------------------------------------------------------------------
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static String getUserCompanyName(HttpServletRequest request){
		Map<String,Object> loginUser = (Map<String,Object>)request.getSession().getAttribute("LoginUserInfo");
		return loginUser.get("EMPLOYEE_COMPANY").toString();
	}
	
	/*
	 * 	获取当前登录用户:Company_Code
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static Integer getUserCompanyCode(HttpServletRequest request){
		Map<String,Object> loginUser = (Map<String,Object>)request.getSession().getAttribute("LoginUserInfo");
		return Integer.parseInt(loginUser.get("EMPLOYEE_COMPANY_CODE").toString());
	}
	
	/*
	 * 	获取当前登录用户:Depart_Name ----------------------------------------------------------------------------
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static String getUserDepartName(HttpServletRequest request){
		Map<String,Object> loginUser = (Map<String,Object>)request.getSession().getAttribute("LoginUserInfo");
		return loginUser.get("EMPLOYEE_DEPARTNAME").toString();
	}
	
	/*
	 * 	获取当前登录用户:Depart_Code
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static Integer getUserDepartCode(HttpServletRequest request){
		Map<String,Object> loginUser = (Map<String,Object>)request.getSession().getAttribute("LoginUserInfo");
		return Integer.parseInt(loginUser.get("EMPLOYEE_DEPART_CODE").toString());
	}
	
	/*
	 * 	获取当前登录用户:ICON
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static String getUserIcon(HttpServletRequest request){
		Map<String,Object> loginUser = (Map<String,Object>)request.getSession().getAttribute("LoginUserInfo");
		return loginUser.get("USER_ICON").toString();
	}
	
	/*
	 * 	获取当前登录用户:ICON
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static String getUserAccount(HttpServletRequest request){
		Map<String,Object> loginUser = (Map<String,Object>)request.getSession().getAttribute("LoginUserInfo");
		return loginUser.get("USER_NAME").toString();
	}
	
	/*
	 * 	获取当前登录用户是否为省用户
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static Boolean isProvince(HttpServletRequest request){
		Map<String,Object> loginUser = (Map<String,Object>)request.getSession().getAttribute("LoginUserInfo");
		if(loginUser.get("BELONG_AREA").toString().indexOf("省")!=-1){
			return true;
		}else if(loginUser.get("USER_NAME").toString().toUpperCase().indexOf("ROOT")!=-1){
			return true;
		}else{
			return false;
		}
	}
	
	/*
	 * 	获取当前登录用户所属地市
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static String getBelongArea(HttpServletRequest request){
		Map<String,Object> loginUser = (Map<String,Object>)request.getSession().getAttribute("LoginUserInfo");
		return loginUser.get("BELONG_AREA").toString();
	}
}
