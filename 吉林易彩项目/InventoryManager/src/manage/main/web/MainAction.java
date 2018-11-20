package manage.main.web;

import base.web.PaginationAction;
import java.util.List;
import manage.main.pojo.MenuInfoBean;
import manage.main.service.MainService;
import manage.user.pojo.UserInfoBean;
import org.apache.log4j.Logger;

public class MainAction extends PaginationAction
{
  private static final long serialVersionUID = -5374356110712260272L;
  private static final Logger log = Logger.getLogger(MainAction.class);
  private MainService mainService;
  private List<MenuInfoBean> menuList;
  private MenuInfoBean menu;

  public String getMenus()
  {
    try
    {
      UserInfoBean userInfoBean = (UserInfoBean)getSession().getAttribute("userBean");
      this.menuList = this.mainService.getMenus(userInfoBean);
      this.menu = new MenuInfoBean();
      this.menu.setMenus(this.menuList);
    } catch (Exception e) {
      UserInfoBean userInfoBean = (UserInfoBean)getSession().getAttribute("userBean");
      log.error("MainAction.getMenus", e);
    }
    return "menus";
  }

  public MainService getMainService() {
    return this.mainService;
  }

  public void setMainService(MainService mainService) {
    this.mainService = mainService;
  }

  public List<MenuInfoBean> getMenuList() {
    return this.menuList;
  }

  public void setMenuList(List<MenuInfoBean> menuList) {
    this.menuList = menuList;
  }

  public MenuInfoBean getMenu() {
    return this.menu;
  }

  public void setMenu(MenuInfoBean menu) {
    this.menu = menu;
  }
}
