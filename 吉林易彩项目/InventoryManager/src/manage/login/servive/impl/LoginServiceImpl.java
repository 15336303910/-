 package manage.login.servive.impl;
 
 import base.database.DataBase;
 import base.exceptions.DataAccessException;
 import java.util.List;
 import manage.login.servive.LoginService;
 import manage.main.pojo.MenuInfoBean;
 import manage.user.pojo.UserCkBean;
 import manage.user.pojo.UserInfoBean;
 
 public class LoginServiceImpl extends DataBase
   implements LoginService
 {
   private static final String GET_POWER_STR = "login.getUserPowers";
   private static String LOGIN = "login.getLogin";
   private static final String GET_USER_CK = "login.getUserCk";
   private static final String UPDATE_USER_LOG = "login.updateUserLog";
   private static final String GET_POWER_URL = "login.getPowerUrl";
 
   public UserInfoBean login(UserInfoBean user)throws DataAccessException{
	   UserInfoBean userinfo = (UserInfoBean)getObject(LOGIN, user);
	   if(userinfo!=null){
		   MenuInfoBean power = new MenuInfoBean();
		   power.setUserId(userinfo.getUserId());
		   List powers = getObjects("login.getMenus", power);
		   String powerstr = "";
		   if((powers!=null) && (!(powers.isEmpty()))){
			   for(int i=0;i<powers.size();++i){
				   if(i==(powers.size()-1)){
					   powerstr = powerstr+((String)powers.get(i));
			   	   }else{
			   		   powerstr = powerstr+((String)powers.get(i))+",";
			   	   }
			   }
		   }
		   userinfo.setPowerstr(powerstr);
       }
       return userinfo;
   }
   
   
   /**
    * 得到所有的菜单
    * @return
    */
   public String getPowerStr(){
	   MenuInfoBean power = new MenuInfoBean();
	   //这个地方有遗留问题暂时赋予root用户权限
       power.setUserId(1);
       List powers = getObjects("login.getMenus", power);
       String powerstr = "";
       if ((powers != null) && (!(powers.isEmpty()))) {
         for (int i = 0; i < powers.size(); ++i) {
           if (i == powers.size() - 1)
             powerstr = powerstr + ((String)powers.get(i));
           else {
             powerstr = powerstr + ((String)powers.get(i)) + ",";
           }
         }
       }
       return powerstr;
   }
 
   public void updateUserLog(UserInfoBean userInfoBean) throws DataAccessException {
     update("login.updateUserLog", userInfoBean);
   }
 
   public List<MenuInfoBean> getMenucodeByUserId(MenuInfoBean menu) throws DataAccessException
   {
     List list = getObjects("login.getPowerUrl", menu);
     return list;
   }
  }
