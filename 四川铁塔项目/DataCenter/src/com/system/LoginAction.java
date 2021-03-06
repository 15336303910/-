package com.system;
import java.security.MessageDigest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller("com.system.LoginAction")
@RequestMapping(value="/loginAction")
public class LoginAction{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	/*
	 * 	修改密码.
	 * 
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updatePassword.ilf")
	public void updatePassword(@RequestParam String oldPass,@RequestParam String newPass,HttpServletRequest resquest,HttpServletResponse response)throws Exception{
		HttpSession httpSession = resquest.getSession();
		if(httpSession.getAttribute("LoginUserInfo")!=null){
			Map<String,Object> loginObject = (Map<String,Object>)httpSession.getAttribute("LoginUserInfo");
			jdbcTemplate.execute("UPDATE S_SYSTEM_USER SET USER_PASS = '"+newPass+"' WHERE ID = "+loginObject.get("ID").toString());
			httpSession.removeAttribute("LoginUserInfo");
		}
		String path = resquest.getContextPath();
		String basePath = resquest.getScheme()+"://"+resquest.getServerName()+":"+resquest.getServerPort()+path;
		response.setHeader("Cache-Control","no-store");
		response.setDateHeader("Expires",0);
		response.setHeader("Prama","no-cache");
		response.sendRedirect(basePath+"/login.jsp");
	}	
	
	/*
	 * 	退出登录.
	 * 
	 * */
	@RequestMapping("/quit.ilf")
	public void quit(@RequestParam String thisTime,HttpServletRequest resquest,HttpServletResponse response)throws Exception{
		JSONObject quitAction = new JSONObject(); 
		HttpSession httpSession = resquest.getSession();
		/*
		 * 	删除用户登录信息
		 * 
		 * */
		if(httpSession.getAttribute("LoginUserInfo")!=null){
			httpSession.removeAttribute("LoginUserInfo");
		}
		/*
		 * 	删除缓存的用户信息、菜单信息
		 * 
		 * */
		if(httpSession.getAttribute("LOGIN_INFO")!=null){
			httpSession.removeAttribute("LOGIN_INFO");
		}
		/*
		 * 	删除首页缓存数据
		 * 
		 * */
		if(httpSession.getAttribute("INDEX_DATA")!=null){
			httpSession.removeAttribute("INDEX_DATA");
		}
		if(httpSession.getAttribute("HB_MONTHLY_CHART")!=null){
			httpSession.removeAttribute("HB_MONTHLY_CHART");
		}
		if(httpSession.getAttribute("TB_MONTHLY_CHART")!=null){
			httpSession.removeAttribute("TB_MONTHLY_CHART");
		}
		/*
		 * 	删除区域标准化数据
		 * 
		 * */
		if(httpSession.getAttribute("REGION_STANDARD")!=null){
			httpSession.removeAttribute("REGION_STANDARD");
		}
		if(httpSession.getAttribute("REGION_STANDARD_DATA")!=null){
			httpSession.removeAttribute("REGION_STANDARD_DATA");
		}
		/*
		 * 	删除区域标准化菜单
		 * 
		 * */
		if(httpSession.getAttribute("HOME_MENUS")!=null){
			httpSession.removeAttribute("HOME_MENUS");
		}
		/*
		 * 	删除区域标准化菜单
		 * 
		 * */
		if(httpSession.getAttribute("LEFT_UP_MENUS")!=null){
			httpSession.removeAttribute("LEFT_UP_MENUS");
		}
		if(httpSession.getAttribute("BUILD_MENUS")!=null){
			httpSession.removeAttribute("BUILD_MENUS");
		}
		/**
		 * 删除在线风控中省份跟地市信息
		 */
		if(httpSession.getAttribute("IS_PROVINCE")!=null) {
			httpSession.removeAttribute("IS_PROVINCE");
		}
		if(httpSession.getAttribute("BELONG_AREA")!=null) {
			httpSession.removeAttribute("BELONG_AREA");
		}
		quitAction.put("success",true);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(quitAction.toString());
	}	
	
	/*
	 * 	自动登录：Cookie.
	 * 
	 * */
	@RequestMapping("/loginCookie.ilf")
	public void loginCookie(@RequestParam String thisTime,HttpServletRequest resquest,HttpServletResponse response)throws Exception{
		JSONObject autoLogin = new JSONObject(); 
		Cookie[] requestCookies = resquest.getCookies();
		if(requestCookies!=null && requestCookies.length>0){			
			Boolean isHaveUserName = false;
			Boolean isHavePassWord = false;			
			for(int i=0;i<requestCookies.length;i++){
				Cookie thisCookie = requestCookies[i];
				if("userName".equals(thisCookie.getName())){
					autoLogin.put("userName",thisCookie.getValue());
					isHaveUserName = true;
				}else if("passWord".equals(thisCookie.getName())){
					autoLogin.put("passWord",thisCookie.getValue());
					isHavePassWord = true;
				}
			}
			if(isHaveUserName && isHavePassWord){
				autoLogin.put("autoAble",true);
			}else{
				autoLogin.put("autoAble",false);
			}
		}else{
			autoLogin.put("autoAble",false);
		}
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(autoLogin.toString());
	}	
	
	/*
	 * 	登录.
	 * 
	 * */
	@SuppressWarnings("deprecation")
	@RequestMapping("/doLogin.ilf")
	public void doLogin(@RequestParam String isAutoLogin,@RequestParam String userName,@RequestParam String passWord,HttpServletRequest resquest,HttpServletResponse response)throws Exception{
		JSONObject loginResult = new JSONObject();
		/*MD5加密*/
		MessageDigest digest = MessageDigest.getInstance("md5");
		byte[] result = digest.digest(passWord.getBytes());  
        StringBuffer buffer = new StringBuffer();  
        for(byte b:result){  
            int number = b & 0xff;  
            String str = Integer.toHexString(number);  
            if(str.length()==1){  
                buffer.append("0");  
            }  
            buffer.append(str);
        }  
        String md5PassWord = buffer.toString();
		System.out.println("=>"+userName+"/"+passWord+" is loging.");
		List<Map<String,Object>> userObjects = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_USER WHERE USER_NAME = '"+userName+"' AND USER_PASS = '"+md5PassWord+"'");
		if(userObjects.size()>0){
			Map<String,Object> userObject = userObjects.get(0);	
			loginResult.put("isUserExist",true);
			Boolean isLoginSuccess = true;		
			if("Y".equals(userObject.get("IS_LOCKED").toString())){
				loginResult.put("isLocked",true);
				loginResult.put("message","用户已锁定.请联系系统管理员.");
				isLoginSuccess = false;
			}else{
				loginResult.put("isLocked",false);
			}
			loginResult.put("isRoleDisabled",false);		
			loginResult.put("isLoginSuccess",isLoginSuccess);			
			if(isLoginSuccess){
				jdbcTemplate.execute("UPDATE S_SYSTEM_USER SET LAST_LOGIN_DATETIME = SYSDATE WHERE USER_NAME = '"+userName+"' AND USER_PASS = '"+md5PassWord+"'");
				Date thisDate = new Date();
				int thisHours = thisDate.getHours();
				String helloWords = "";
				if(thisHours>=0 && thisHours<=6){
					helloWords = "凌晨好，"+userObject.get("EMPLOYEE_NAME").toString();
				}else if(thisHours>6 && thisHours<=12){
					helloWords = "上午好，"+userObject.get("EMPLOYEE_NAME").toString();
				}else if(thisHours>12 && thisHours<=14){
					helloWords = "中午好，"+userObject.get("EMPLOYEE_NAME").toString();
				}else if(thisHours>14 && thisHours<=18){
					helloWords = "下午好，"+userObject.get("EMPLOYEE_NAME").toString();
				}else if(thisHours>18 && thisHours<=24){
					helloWords = "晚上好，"+userObject.get("EMPLOYEE_NAME").toString();
				}
				userObject.put("HELLO_MESSAGE",helloWords);
				resquest.getSession().setAttribute("LoginUserInfo",userObject);
				/*
				 * 	记录日志.
				 * 
				 * */
				String logSql = "";
				logSql+="INSERT INTO S_SYSTEM_LOG(";
				logSql+="	ID,LOGIN_USER,BELONG_CITY,LOG_TYPE,LOG_INFO,EXEC_DATE";
				logSql+=")VALUES(";
				logSql+="	SEQ_S_SYSTEM_LOG.NEXTVAL,'"+userObject.get("USER_NAME").toString()+"','"+userObject.get("BELONG_AREA").toString()+"','用户登录','登录系统.',SYSDATE";
				logSql+=")";
				jdbcTemplate.execute(logSql);
				/*
				 * 	是否自动登录.
				 * 
				 * */
				if(isAutoLogin!=null && "Y".equals(isAutoLogin)){
					Cookie userNameCookie = new Cookie("userName",userName);
					userNameCookie.setMaxAge(60*60*24*7);
					response.addCookie(userNameCookie);
					Cookie passWordCookie = new Cookie("passWord",passWord);
					passWordCookie.setMaxAge(60*60*24*7);
					response.addCookie(passWordCookie);
				}else{
					Cookie[] requestCookies = resquest.getCookies();
					if(requestCookies.length>0){
						for(int i=0;i<requestCookies.length;i++){
							Cookie thisCookie = requestCookies[i];
							if("userName".equals(thisCookie.getName()) || "passWord".equals(thisCookie.getName())){
								thisCookie.setMaxAge(0);
								thisCookie.setValue(null);
								response.addCookie(thisCookie);
							}
						}
					}
				}
			}
		}else{
			loginResult.put("isUserExist",false);
			loginResult.put("message","账号或密码错误.");
		}
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(loginResult.toString());
	}
	
	/*
	 * 	功能使用日志.
	 * 
	 * */
	@RequestMapping("/logFunctionUse.ilf")
	public void logFunctionUse(
		@RequestParam String MENU_CODE,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			if(request.getSession().getAttribute("LoginUserInfo")!=null){
				String MENU_NAME = jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_MENU WHERE ID = "+MENU_CODE).get("MENU_NAME").toString();
				if("返回门户".equals(MENU_NAME)){
					MENU_NAME = "首页";
				}
				String logSql = "";
				logSql+="INSERT INTO S_SYSTEM_LOG(";
				logSql+="	ID,LOGIN_USER,BELONG_CITY,LOG_TYPE,LOG_INFO,EXEC_DATE";
				logSql+=")VALUES(";
				logSql+="	SEQ_S_SYSTEM_LOG.NEXTVAL,'"+loginUserUtil.getUserAccount(request)+"','"+loginUserUtil.getBelongArea(request)+"','功能使用','用户进入["+MENU_NAME+"]功能.',SYSDATE";
				logSql+=")";
				jdbcTemplate.execute(logSql);
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
}
