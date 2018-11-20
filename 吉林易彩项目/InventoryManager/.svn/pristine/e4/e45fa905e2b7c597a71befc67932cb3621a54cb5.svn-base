package manage.main.service.impl;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import java.util.List;
import manage.main.pojo.MenuInfoBean;
import manage.main.service.MainService;
import manage.user.pojo.UserInfoBean;

public class MainServiceImpl extends DataBase
  implements MainService
{
  private static final String GET_MENUS = "main.getMenus";

  public List<MenuInfoBean> getMenus(UserInfoBean userInfoBean)
    throws DataAccessException
  {
    return getObjects("main.getMenus", userInfoBean);
  }
}
