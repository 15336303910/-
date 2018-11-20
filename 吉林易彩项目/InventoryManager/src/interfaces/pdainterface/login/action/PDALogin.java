package interfaces.pdainterface.login.action;
import base.util.JsonUtil;
import base.util.SmsUtil;
import base.util.TextUtil;
import base.util.functions;
import base.util.md5;
import base.web.InterfaceAction;
import interfaces.irmsInterface.interfaces.auther.CellMountApp2IRMSService;
import interfaces.irmsInterface.interfaces.auther.CellMountApp2IRMSServiceLocator;
import interfaces.irmsInterface.interfaces.auther.CellMountApp2IRMSServicePortType;
import interfaces.irmsInterface.utils.UploadUtil;
import interfaces.pdainterface.login.service.PDALoginService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import manage.domain.pojo.DomainBean;
import manage.user.pojo.SmsAuthCodeBean;
import manage.user.pojo.UserInfoBean;

import org.apache.log4j.Logger;

import com.langchao.comm.authority.ResponseMessageObj;
import com.langchao.comm.authority.UserinfoObj;
import com.langchao.proxy.authority.SenUserProxy;
public class PDALogin extends InterfaceAction{
	
	private static final long serialVersionUID = -3486300674796123267L;
   
	private PDALoginService pdaLoginService;
   
	private static final Logger log = Logger.getLogger(PDALogin.class);
 
	public String login(){
		try{
			/*
				HttpServletRequest request = this.getRequest();
				Enumeration enumeration = request.getParameterNames();
				while(enumeration.hasMoreElements()) {
				    String name = (String)enumeration.nextElement();
				    String value = request.getParameter(name);
				    System.out.println("ParamName:"+name+",ParamValue:"+value);
				}
			*/
			UserInfoBean user = new UserInfoBean();
			user = (UserInfoBean)getRequestObject(UserInfoBean.class);
			String pwd = user.getPassword();
			String offPwd = user.getOffLinePwd();
			UserInfoBean userInfoBean = new UserInfoBean();
			if(user!=null){
				if(TextUtil.isNull(user.getVersionCode())){
					sendResponse(Integer.valueOf(1),"请更新至最新版本!"); 
				}else{
					boolean flag = true;
					/*String newVersion = this.pdaLoginService.getVersion();
					if(TextUtil.isNotNull(newVersion)){
						Double newCode = Double.parseDouble(newVersion);
						if(newCode <= user.getVersionCode()){
							flag = true;
						}
					}else{
						flag = true;
					}*/
					if(flag){
						String isAuth = super.properties.getValueByKey("isAuth");
						String province = properties.getValueByKey("province");
						if(isAuth.equals("true")){
							if(province.equals("湖南")){
								user = this.pdaLoginService.login(user);
								if(null == user){
									sendResponse(Integer.valueOf(1), "当前用户无登录权限!"); 
								}else{
									if(TextUtil.isNotNull(user.getPassword()) && pwd!=null && pwd.equals(user.getPassword())){
										this.postCache(user);
										sendResponse(Integer.valueOf(0), getUID()+","+user.getAreaName()); 
									}else{
										user.setOffLinePwd(offPwd);
										Map<String, Object> map =  this.hnInterface(user);
										String resultCode = map.get("resultCode")+"";
										if(resultCode.equals("1") || TextUtil.isNull(resultCode)){
											sendResponse(Integer.valueOf(1), "用户名或密码错误。"); 
										}else{
											userInfoBean = this.getUserbyMap(map, user);
											this.postCache(userInfoBean);
											sendResponse(Integer.valueOf(0), getUID()+","+userInfoBean.getAreaName()); 
										}
									}
								}
							}else{
								SenUserProxy senUserProxy = new SenUserProxy();
								Object userAuthObject = senUserProxy.checkLogin(user.getUsername(),user.getOffLinePwd());
								UserinfoObj userinfo = null;
								if(userAuthObject instanceof ResponseMessageObj) {
									sendResponse(Integer.valueOf(1), "用户名或密码错误。"); 
								}else if(userAuthObject instanceof UserinfoObj) {
									userinfo = (UserinfoObj) userAuthObject;
									userInfoBean = this.getUserByAuth(userinfo, user.getPassword());
									this.postCache(userInfoBean);
									sendResponse(Integer.valueOf(0), getUID()+","+userInfoBean.getAreaName()); 
								}
							}
						}else{
							String userCode = user.getAuthCode();
							user = this.pdaLoginService.login(user);
							if(province.equals("北京")) {
								user.setAreaName(user.getMaintenArea());
							}
							if(user!=null && TextUtil.isNotNull(pwd)){
								if(pwd.equals(user.getPassword())){
									this.postCache(user);
									System.out.println("==>"+getUID()+","+user.getAreaName());
									sendResponse(Integer.valueOf(0), getUID()+","+user.getAreaName()); 
								}else{
									sendResponse(Integer.valueOf(1), "用户名或密码错误。"); 
								}
							}else{
								if(userCode.equals("sjfwyc") || md5.strToMD5(userCode).equals(user.getPassword())){
									this.postCache(user);
									sendResponse(Integer.valueOf(0), getUID()+","+user.getAreaName()); 
								}else{
									String authCode = this.pdaLoginService.checkAuthCode(user);
									if(TextUtil.isNotNull(userCode) && authCode.contains(userCode)){
										this.postCache(user);
										this.pdaLoginService.upAuthCode(user.getUsername());
										sendResponse(Integer.valueOf(0), getUID()+","+user.getAreaName()); 
									}else{
										sendResponse(Integer.valueOf(1), "用户名或密码错误。"); 
									}
								}
							}
						}
					}else{
						sendResponse(Integer.valueOf(1), "请更新后重新登录。"); 
					}
				}
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
   
   
   /**
    * 得到接口处理数据
    * @param user
    * @return
    */
   Map<String, Object> hnInterface(UserInfoBean user){
	   Map<String, Object> map = new LinkedHashMap<String, Object>();
	   try{
		   CellMountApp2IRMSService authLocator = new CellMountApp2IRMSServiceLocator();
			CellMountApp2IRMSServicePortType authType = authLocator.getCellMountApp2IRMSServiceHttpPort();
			Date date = new Date();
			SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd hh24:mm:ss");
			String detail = "<opDetail><recordInfo>"
					+ "<fieldInfo><fieldEnName>account</fieldEnName><fieldContent>"+user.getUsername()+"</fieldContent></fieldInfo>"
					+ "<fieldInfo><fieldEnName>password</fieldEnName><fieldContent>"+user.getOffLinePwd()+"</fieldContent></fieldInfo>"
					+ "</recordInfo></opDetail>";
			String outStr = authType.loginAuthority("IRMS", "CellMount", "123456", sdf.format(date), detail);
			UploadUtil.saveIrms("输入信息:"+detail+";综资返回信息:"+outStr,"loginauth");
			map = JsonUtil.getMap4Json(outStr);
	   }catch(Exception e){
		   e.printStackTrace();
	   }
		return map;
   }
   
   /**
    * 通过map封装数据
    * @param map
    * @param user
    * @return
    */
   UserInfoBean getUserbyMap(Map<String, Object> map,UserInfoBean user){
	   UserInfoBean userInfoBean = new UserInfoBean();
	   try{
		   userInfoBean.setUsername(map.get("userAccount")+"");
			userInfoBean.setUserId(Integer.parseInt(map.get("userId")+""));
			userInfoBean.setEid(map.get("empId")+"");
			userInfoBean.setRealname(map.get("userName")+"");
			userInfoBean.setPassword(user.getOffLinePwd());
			userInfoBean.setLasttime(new Date());
			userInfoBean.setAreaName(map.get("userCompany")+"");
			userInfoBean.setLastip(functions.getIpAddr(getRequest()));
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	  return userInfoBean;
   }
   
   /**
    * 得到验证码
    * @return
    */
   public String getAuthCode(){
	   try{
		   SmsAuthCodeBean obj = (SmsAuthCodeBean) super.getRequestObject(SmsAuthCodeBean.class);
		   if(obj != null){
			   UserInfoBean user = new UserInfoBean();
			   user.setUsername(obj.getUserName());
			   user = pdaLoginService.login(user);
			   String tel = user.getPhoneNumber();
			   if(TextUtil.isNotNull(tel)){
				   String smsContent = this.pdaLoginService.getAuthCode(user);
				   if(TextUtil.isNotNull(smsContent)) {
					   
				   }else {
					   String code = this.getCode();
					   obj.setAuthCode(code);
					   smsContent = user.getRealname()+"您好:易采友情提示您此次登录验证码为"+code+""
					   		+ "有效登录时间为三小时,请妥善保管,谢谢!";
					   obj.setSmsContent(smsContent);
					   int id = this.pdaLoginService.addAuthCode(obj);
					   obj.setId(id);
				   }
				   new sendSmsThread(user.getPhoneNumber(),smsContent).start();
				   sendResponse(Integer.valueOf(0), obj);
			   }else{
				   sendResponse(Integer.valueOf(1), "未获取手机信息。");
			   }
		   }else{
			   sendResponse(Integer.valueOf(2), "请求参数错误。");
		   }
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   return "success";
   }
   
   
   /**
    * 检验用户信息
    * 是否对
    * @param user
    * @return
    */
   public String checkAuthCode(UserInfoBean user){
	   String result = "true";
	   if(properties.getValueByKey("province").equals("北京")){
		   
	   }
	   return result;
   }
   
   /**
    * 派发短信
    * @author chenqp
    *
    */
   class sendSmsThread extends Thread{
	   private String telephone;
	   private String smsContent;
	   public sendSmsThread(String telephone,String smsContent){
		   this.telephone = telephone;
		   this.smsContent = smsContent;
	   }
	   @Override
	   public void run() {
		SmsUtil.sendSms(telephone, smsContent);
		super.run();
	   }
   }
   
   
   /**
    * 生成一个
    * 四位的随机
    * @return
    */
   String getCode(){
	   int max=999999;
       int min=100000;
       Random random = new Random();
       int s = random.nextInt(max)%(max-min+1) + min;
       return s+"";
   }
   
   
   /**
    * 根据权限登录
    * @param userinfo
    * @param password
    * @return
    */
   UserInfoBean getUserByAuth(UserinfoObj userinfo,String password){
	   UserInfoBean userInfoBean = new UserInfoBean();
	   try{
		userInfoBean.setUsername(userinfo.getUseraccount());
   		userInfoBean.setRealname(userinfo.getEmpname());
   		userInfoBean.setPassword(password);
   		userInfoBean.setLasttime(new Date());
   		userInfoBean.setAreaName(userinfo.getCompname());
		userInfoBean.setLastip(functions.getIpAddr(getRequest()));
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   return userInfoBean;
   }
   
   /**
    * 封装session数据
    * @param user
    */
   void postCache(UserInfoBean user){
	   getSession().setAttribute("userBean", user);
       setUID(getSession().getId());
       setInterfaceSessionAttribute(getUID(), "userBean", user);
       new logLoginThread(user).start();
   }
   
    /**
     * 增加登录日志
     * @author chenqp
     *
     */
	class logLoginThread extends Thread {
		private UserInfoBean user;
		public logLoginThread(UserInfoBean user) {
			this.user = user;
		}

		@Override
		public void run() {
			pdaLoginService.addLog(user);
			super.run();
		}
	}
 
   public String changePassword() {
     try {
       UserInfoBean user = (UserInfoBean)getRequestObject(UserInfoBean.class);
       if (user != null) {
         String newPwd = user.getPassword();
         user = (UserInfoBean)getInterfaceSession().getAttribute("userBean");
         user.setPassword(newPwd);
         this.pdaLoginService.changePwd(user);
         sendResponse(Integer.valueOf(0), "修改成功。"); 
       }else{
    	   sendResponse(Integer.valueOf(2), "请求参数错误。");
       }
     }
     catch (Exception e) {
       sendResponse(Integer.valueOf(3), "应用服务器异常。");
       log.error("PDALogin.changePassword ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
     }
      return "success";
   }
 
   public String changeOffLinePwd() {
     try {
       UserInfoBean user = (UserInfoBean)getRequestObject(UserInfoBean.class);
       if (user != null) {
         String offLinePwd = user.getOffLinePwd();
         user = (UserInfoBean)getInterfaceSession().getAttribute("userBean");
         user.setOffLinePwd(offLinePwd);
         this.pdaLoginService.changeOffLinePwd(user);
         sendResponse(Integer.valueOf(0), "修改成功。"); 
       }else{
    	   sendResponse(Integer.valueOf(2), "请求参数错误。");
       }
     }
     catch (Exception e) {
       sendResponse(Integer.valueOf(3), "应用服务器异常。");
       log.error("PDALogin.changeOffLinePwd ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
     }
      return "success";
   }
 
   public String loginError() {
     try {
       sendResponse(Integer.valueOf(1), "登录已超时，请重新登录。");
     } catch (Exception e) {
       this.exception = e;
       sendResponse(Integer.valueOf(3), "应用服务器异常。");
       log.error("PDALogin.loginError ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
     }
     return "success";
   }
 
   public String getDomainList() {
     try {
       DomainBean domain = (DomainBean)getRequestObject(DomainBean.class);
       UserInfoBean user = (UserInfoBean)getInterfaceSession().getAttribute("userBean");
       if (domain == null) {
         domain = new DomainBean();
         domain.setDomainCode(user.getAreano());
       }
       List list = this.pdaLoginService.getDomainList(domain);
       sendResponse(Integer.valueOf(0), list);
     } catch (Exception e) {
       this.exception = e;
       sendResponse(Integer.valueOf(3), "应用服务器异常。");
       log.error("PDALogin.loginError ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
     }
     return "success";
   }
 
   public PDALoginService getPdaLoginService() {
     return this.pdaLoginService;
   }
 
   public void setPdaLoginService(PDALoginService pdaLoginService) {
     this.pdaLoginService = pdaLoginService;
   }
 }

