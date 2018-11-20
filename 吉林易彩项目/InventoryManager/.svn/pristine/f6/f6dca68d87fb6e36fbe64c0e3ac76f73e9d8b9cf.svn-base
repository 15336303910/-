package manage.login.web;
import base.util.CommonUtil;
import base.util.ErrorMessage;
import base.util.GetProperties;
import base.util.JsonUtil;
import base.util.functions;
import base.util.md5;
import base.web.PaginationAction;
import interfaces.irmsInterface.interfaces.auther.CellMountApp2IRMSService;
import interfaces.irmsInterface.interfaces.auther.CellMountApp2IRMSServiceLocator;
import interfaces.irmsInterface.interfaces.auther.CellMountApp2IRMSServicePortType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import manage.login.servive.LoginService;
import manage.main.pojo.MenuInfoBean;
import manage.user.pojo.UserInfoBean;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.langchao.comm.authority.ResponseMessageObj;
import com.langchao.comm.authority.UserinfoObj;
import com.langchao.proxy.authority.SenUserProxy;
public class LoginAction extends PaginationAction{
	
	private static final long serialVersionUID = -3486300674796123267L;
	private LoginService webLoginService;
	private UserInfoBean user;
	private String vcode;
	private String result;
	private ErrorMessage errorMessage;
	private boolean success = false;
	public static GetProperties properties = new GetProperties();
	private static final Logger log = Logger.getLogger(LoginAction.class);
	private String urll;

	public LoginAction(){
		System.out.println("LoginAction 实例化");
	}

	public String login() throws Exception{
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String authCode = (String)session.getAttribute("AuthCode");
			String isAuth = properties.getValueByKey("isAuth");
			String province = properties.getValueByKey("province");
			if(isAuth.equals("true")){
				System.out.println("XH:Current Province Value in config file is "+province);
				if(province.equals("湖南")){
					CellMountApp2IRMSService authLocator = new CellMountApp2IRMSServiceLocator();
	    			CellMountApp2IRMSServicePortType authType = authLocator.getCellMountApp2IRMSServiceHttpPort();
	    			Date date = new Date();
	    			SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd hh24:mm:ss");
	    			String detail = "";
	    			detail+="<opDetail>";
	    			detail+="	  <recordInfo>";
	    			detail+="		  <fieldInfo>";
	    			detail+="			  <fieldEnName>account</fieldEnName>";
	    			detail+="			  <fieldContent>"+this.user.getUsername()+"</fieldContent>";
	    			detail+="		  </fieldInfo>";
	    			detail+="		  <fieldInfo>";
	    			detail+="			  <fieldEnName>password</fieldEnName>";
	    			detail+="			  <fieldContent>"+this.user.getPassword()+"</fieldContent>";
	    			detail+="		  </fieldInfo>";
	    			detail+="	  </recordInfo>";
	    			detail+="</opDetail>";
	    			String outStr = authType.loginAuthority("IRMS", "CellMount", "123456", sdf.format(date), detail);
	    			Map<String, Object> map = JsonUtil.getMap4Json(outStr);
	    			String resultCode = map.get("resultCode")+"";
	    			if(resultCode.equals("1")){
	    				this.errorMessage = new ErrorMessage();
	    				this.errorMessage.setMessage("用户名密码错误");
	    				this.success = false;
	    				this.result = "用户名不存在或密码错误";
	    			}else{
	    				UserInfoBean userInfoBean = new UserInfoBean();
	        			userInfoBean.setUsername(map.get("userAccount")+"");
	        			userInfoBean.setRealname(map.get("userName")+"");
	        			userInfoBean.setPassword(this.user.getPassword());
	        			userInfoBean.setLasttime(new Date());
	        			userInfoBean.setAreaName(map.get("userCompany")+"");
	    				userInfoBean.setLastip(functions.getIpAddr(getRequest()));
	    				String powerStr = this.webLoginService.getPowerStr();
	    				userInfoBean.setPowerstr(powerStr);
	    				getSession().setAttribute("userBean",userInfoBean);
	    				this.success = true;
	    				this.result = "index.jsp";
	    			}
				}else{
					SenUserProxy senUserProxy = new SenUserProxy();
					Object userAuthObject = senUserProxy.checkLogin(this.user.getUsername(),this.user.getPassword());
					UserinfoObj userinfo = null;
					if(userAuthObject instanceof ResponseMessageObj){
						this.errorMessage = new ErrorMessage();
						this.errorMessage.setMessage("用户名密码错误");
						this.success = false;
						this.result = "用户名不存在或密码错误";
					}else if(userAuthObject instanceof UserinfoObj){
						userinfo = (UserinfoObj) userAuthObject;
	        			UserInfoBean userInfoBean = new UserInfoBean();
	        			userInfoBean.setUsername(userinfo.getUseraccount());
	        			userInfoBean.setRealname(userinfo.getEmpname());
	        			userInfoBean.setPassword(this.user.getPassword());
	        			userInfoBean.setLasttime(new Date());
	        			userInfoBean.setAreaName(userinfo.getCompname());
	    				userInfoBean.setLastip(functions.getIpAddr(getRequest()));
	    				String powerStr = this.webLoginService.getPowerStr();
	    				userInfoBean.setPowerstr(powerStr);
	    				getSession().setAttribute("userBean",userInfoBean);
	    				this.success = true;
	    				this.result = "index.jsp";
	    			}
				}
			}else{
				System.out.println("===>This.User."+(this.user==null));
				if(this.user!=null){
					System.out.println("===>This.User.GetPassword."+this.user.getPassword());
					this.user.setPassword(md5.strToMD5(
						(this.user.getPassword()==null?"":this.user.getPassword())
					));
				}
				UserInfoBean userInfoBean = this.webLoginService.login(this.user);
				if(userInfoBean!=null){
					userInfoBean.setLasttime(new Date());
					userInfoBean.setLastip(functions.getIpAddr(getRequest()));
					this.webLoginService.updateUserLog(userInfoBean);
					MenuInfoBean menu = new MenuInfoBean();
					menu.setUserId(userInfoBean.getUserId());
					List list = this.webLoginService.getMenucodeByUserId(menu);
					getSession().setAttribute("userBean", userInfoBean);
					this.success = true;
					this.result = "index.jsp";
				}else{
					this.errorMessage = new ErrorMessage();
					this.errorMessage.setMessage("用户名密码错误");
					this.success = false;
					this.result = "用户名不存在或密码错误";
				}
				if (!authCode.equalsIgnoreCase(vcode)) {
					this.success = false;
					this.result = "验证码输入错误";
				}
			}
			return "login";
		}catch (Exception e) {
			log.error("LoginAction.login",e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
			return "error";
		}

  public String unlock()
    throws Exception
  {
    try
    {
      this.user.setPassword(md5.strToMD5(this.user.getPassword()));
      UserInfoBean userInfoBean = this.webLoginService.login(this.user);
      if (userInfoBean != null)
      {
        MenuInfoBean menu = new MenuInfoBean();
        menu.setUserId(userInfoBean.getUserId());
        List list = this.webLoginService.getMenucodeByUserId(menu);
        this.urll = "";
        list = CommonUtil.getSorts(list);
        for (int i = 0; i < list.size(); ++i) {
          if ((((MenuInfoBean)list.get(i)).getLeaf() != null) && (((MenuInfoBean)list.get(i)).getLeaf().equals("1"))) {
            this.urll = ((MenuInfoBean)list.get(i)).getUrl();
            userInfoBean.setUrl(this.urll);
            break;
          }
        }
        if (this.urll.equals("")) {
          this.urll = ((MenuInfoBean)list.get(0)).getUrl();
          userInfoBean.setUrl(this.urll);
        }
        getSession().setAttribute("userBean", userInfoBean);
        this.success = true;
      } else {
        this.errorMessage = new ErrorMessage();
        this.errorMessage.setMessage("用户名密码错误");
        this.success = false;
        this.result = "用户名不存在或密码错误";
      }
      return "unlock";
    }
    catch (Exception e) {
      log.error("LoginAction.login", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public void lock() throws Exception
  {
    try {
      getSession().setAttribute("userBean", null);
    } catch (Exception e) {
      log.error("LoginAction.lock", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
    }
  }

  public UserInfoBean getUser()
  {
    return this.user;
  }

  public void setUser(UserInfoBean user) {
    this.user = user;
  }

  public String getVcode() {
    return this.vcode;
  }

  public void setVcode(String vcode) {
    this.vcode = vcode;
  }

  public String getResult() {
    return this.result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public ErrorMessage getErrorMessage() {
    return this.errorMessage;
  }

  public void setErrorMessage(ErrorMessage errorMessage) {
    this.errorMessage = errorMessage;
  }

  public static long getSerialVersionUID() {
    return -3486300674796123267L;
  }

  public boolean isSuccess() {
    return this.success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public static Logger getLog() {
    return log;
  }

  public LoginService getWebLoginService() {
    return this.webLoginService;
  }

  public void setWebLoginService(LoginService webLoginService) {
    this.webLoginService = webLoginService;
  }

  public String getUrll() {
    return this.urll;
  }

  public void setUrll(String urll) {
    this.urll = urll;
  }
}