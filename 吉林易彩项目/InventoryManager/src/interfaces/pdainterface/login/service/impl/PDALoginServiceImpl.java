 package interfaces.pdainterface.login.service.impl;
 
 import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.TextUtil;
import interfaces.pdainterface.login.service.PDALoginService;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import manage.domain.pojo.DomainBean;
import manage.user.pojo.SmsAuthCodeBean;
import manage.user.pojo.UserCkBean;
import manage.user.pojo.UserInfoBean;
 
 public class PDALoginServiceImpl extends DataBase
   implements PDALoginService
 {
   private JdbcTemplate jdbcTemplate;
   private static final String LOGIN = "login.getLogin";
   private static final String GET_NO_ALLOW_EQUT = "login.getNoAllowEqut";
   private static final String GET_USER_CK = "login.getUserCk";
   private static final String UPDATE_PWD = "login.updateUserPwd";
   private static final String UPDATE_OFF_LINE_PWD = "login.updateUserOffLinePwd";
   private static final String GET_DOMAIN = "login.getDomain";
 
   
   /**
    * 得到版本号
    * 必须更新的版本号
    * @return
    */
   public String getVersion(){
	   String versionCode = "";
	   String sql = "select id,version_num,version_must from version_control where id =("+
			"select maxId from max_version) and version_must = 'true'";
	   List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
	   if(TextUtil.isNotNull(list)){
		   Map<String, Object> map = list.get(0);
		   versionCode = map.get("version_num").toString();
	   }
	   return versionCode;
   }

	public UserInfoBean login(UserInfoBean user) throws DataAccessException {
		String passWD = user.getPassword();
		UserInfoBean userInfoBean = (UserInfoBean) getObject("login.getLoginByName", user);

		if (userInfoBean != null && null != userInfoBean.getPassword()) {
			if(userInfoBean.getPassword().equals(passWD)){
				String modulestr = userInfoBean.getModulestr();
				char[] moduleArr = modulestr.toCharArray();
				for (int i = 0; i < moduleArr.length / 2; ++i) {
					if (moduleArr[i] == '1') {
						userInfoBean.setUsertype((i + 1) + "");
						break;
					}
				}

				List userCKList = getObjects("login.getUserCk", userInfoBean);
				String equtstr = "";
				if ((userCKList == null) || (userCKList.size() == 0))
					equtstr = "''";
				else {
					for (int i = 0; i < userCKList.size(); ++i) {
						UserCkBean u = (UserCkBean) userCKList.get(i);
						equtstr = equtstr + u.getEid();
						if (i == userCKList.size() - 1) {
							break;
						} else {
							equtstr = equtstr + ",";
						}
					}
				}
				userInfoBean.setEqutstr(equtstr);
			}			
		}
		return userInfoBean;
	}
   
   /**
    * 增加登录日志
    * @param user
    */
   public void addLog(UserInfoBean user){
	   String sql = "insert into log_login(userName,areaNo,loginTime,loginIp)"
	   		+ " values('"+user.getRealname()+"','"+user.getAreano()+"',now(),'"+user.getLastip()+"')";
	   this.jdbcTemplate.execute(sql);
   }
   
   /**
    * 添加验证码
    * @param obj
    * @return
    */
   public int addAuthCode(SmsAuthCodeBean obj){
	   int id = (Integer) this.insert("login.insertAuthCode", obj);
	   return id;
   }
   
   
   /**
    * 检查验证码
    * @param user
    * @return
    */
   public String checkAuthCode(UserInfoBean user){
	   String result= "";
	   String sql = "select t.authCode"
	   		+ " from  sms_authCode t "
	   		+ " where unix_timestamp(t.validTime) > unix_timestamp(now())"
	   		+ " and  t.userName ='"+user.getUsername()+"' and t.status =0";
	   List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
	   if(TextUtil.isNotNull(list)){
		   for(Map<String,Object> map : list){
			   result +=map.get("authCode")+",";
		   }
	   }
	   if(result.endsWith(",")){
		   result = result.substring(0,result.length()-1);
	   }
	   return result;
   }
   
   /**
    * 
    * @param user
    * @return
    */
   public String getAuthCode(UserInfoBean user) {
	   String result = "";
	   
	   String sql = "select t.authCode,t.smsContent as smsContent "
		   		+ " from  sms_authCode t "
		   		+ " where unix_timestamp(t.validTime) > unix_timestamp(now())"
		   		+ " and  t.userName ='"+user.getUsername()+"' and t.status =0";
	   List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
	   if(TextUtil.isNotNull(list)) {
		   Map<String, Object> map = list.get(0);
		   result = map.get("smsContent")+"";
	   }
	   return result;
   }
   
   /**
    * 将验证码重置
    * @param userName
    */
   public void upAuthCode(String userName){
	   String sql ="update sms_authCode set status =1"
	   		+ " where userName ='"+userName+"'"
	   		+ " and unix_timestamp(validTime) <"
	   		+ " unix_timestamp(date_sub(now(), interval 3 hour))";
	   this.jdbcTemplate.execute(sql);
   }
 
   public void changeOffLinePwd(UserInfoBean user) throws DataAccessException
   {
     update("login.updateUserOffLinePwd", user);
   }
 
   public void changePwd(UserInfoBean user) throws DataAccessException
   {
     update("login.updateUserPwd", user);
   }
 
   public List<DomainBean> getDomainList(DomainBean domain) throws DataAccessException
   {
     return getObjects("login.getDomain", domain);
   }

public JdbcTemplate getJdbcTemplate() {
	return jdbcTemplate;
}

public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
}
 }

