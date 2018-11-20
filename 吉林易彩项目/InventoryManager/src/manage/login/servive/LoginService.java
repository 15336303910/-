package manage.login.servive;

import base.exceptions.DataAccessException;

import java.util.List;

import manage.main.pojo.MenuInfoBean;
import manage.user.pojo.UserInfoBean;

public abstract interface LoginService
{
  public abstract UserInfoBean login(UserInfoBean paramUserInfoBean)
    throws DataAccessException;

  public abstract void updateUserLog(UserInfoBean paramUserInfoBean)
    throws DataAccessException;

  public abstract List<MenuInfoBean> getMenucodeByUserId(MenuInfoBean paramMenuInfoBean)
    throws DataAccessException;
  
  
  /**
   * 得到所有的菜单
   * @return
   */
  public String getPowerStr();
}

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.login.servive.LoginService
 * JD-Core Version:    0.5.3
 */