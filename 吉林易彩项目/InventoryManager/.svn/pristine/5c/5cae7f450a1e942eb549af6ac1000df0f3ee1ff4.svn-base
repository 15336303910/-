 package manage.tree.web;

 import base.util.ErrorMessage;
 import base.web.PaginationAction;
 import javax.servlet.http.HttpSession;
 import manage.user.pojo.UserInfoBean;
 import org.apache.log4j.Logger;
 
 public class TreeAction extends PaginationAction
 {
   private static final long serialVersionUID = -28535499634966598L;
   private static final Logger log = Logger.getLogger(TreeAction.class);
   private ErrorMessage errorMessage;
   private String domainCode;
   private String domainName;
   private String manage;
   private UserInfoBean userInfoBean;
 
   public String treeHref()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       return this.manage;
     } catch (Exception e) {
       log.error("DomainAction.loadDomainTree", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }

public ErrorMessage getErrorMessage() {
	return errorMessage;
}

public void setErrorMessage(ErrorMessage errorMessage) {
	this.errorMessage = errorMessage;
}

public String getDomainCode() {
	return domainCode;
}

public void setDomainCode(String domainCode) {
	this.domainCode = domainCode;
}

public String getDomainName() {
	return domainName;
}

public void setDomainName(String domainName) {
	this.domainName = domainName;
}

public String getManage() {
	return manage;
}

public void setManage(String manage) {
	this.manage = manage;
}

public UserInfoBean getUserInfoBean() {
	return userInfoBean;
}

public void setUserInfoBean(UserInfoBean userInfoBean) {
	this.userInfoBean = userInfoBean;
}

public static long getSerialversionuid() {
	return serialVersionUID;
}

public static Logger getLog() {
	return log;
}
}
