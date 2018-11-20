package manage.domain.web;

import base.util.ErrorMessage;
import base.web.PaginationAction;
import java.util.List;
import javax.servlet.http.HttpSession;
import manage.domain.pojo.DomainBean;
import manage.domain.service.DomainService;
import manage.tree.pojo.Tree;
import manage.user.pojo.UserInfoBean;
import org.apache.log4j.Logger;

public class DomainAction extends PaginationAction
{
  private static final long serialVersionUID = -6671118577433610578L;
  private DomainService domainService;
  private static final Logger log = Logger.getLogger(DomainAction.class);
  private UserInfoBean userInfoBean;
  private DomainBean newDomain;
  private UserInfoBean newUser;
  private DomainBean updateDomain;
  private DomainBean delDomain;
  private DomainBean searchDomain;
  private DomainBean loadDomain;
  private List<DomainBean> responsibles;
  private DomainBean data;
  private ErrorMessage errorMessage;
  private Tree domianTree;
  private boolean success = false;

  private String verifyMsg = null;

  private String deleteMsg = null;

  private String[] powerstr = null;
  private List<Tree> domianTrees;
  private List<Tree> domianAndEquitTrees;
  private String confirmDelete;
  private UserInfoBean responsible;

  public String loadDomainTree()
    throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      if (this.searchDomain == null) {
        this.searchDomain = new DomainBean();
      }
      this.searchDomain.setDomainCode(this.userInfoBean.getAreano());
      this.domianTrees = this.domainService.getDomianTree(this.searchDomain);
      return "loadDomainTree";
    }
    catch (Exception e) {
      log.error("DomainAction.loadDomainTree", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String loadDomainTree2() throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      if (this.searchDomain == null) {
        this.searchDomain = new DomainBean();
      }
      this.searchDomain.setDomainCode(this.userInfoBean.getAreano());
      this.domianTrees = this.domainService.getDomianTree2(this.searchDomain);
      return "loadDomainTree2";
    }
    catch (Exception e) {
      log.error("DomainAction.loadDomainTree", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String loadJQueryDomainTree() throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      if (this.searchDomain == null) {
        this.searchDomain = new DomainBean();
      }
      this.searchDomain.setDomainCode(this.userInfoBean.getAreano());
      this.domianTrees = this.domainService.getDomianTree(this.searchDomain);
      return "loadDomainTree";
    }
    catch (Exception e) {
      log.error("DomainAction.loadDomainTree", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String searchDomainTree() throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      this.searchDomain.setDomainCode(this.userInfoBean.getAreano());
      this.domianTrees = this.domainService.findDomianTree(this.searchDomain);
      if ((this.domianTrees != null) && (!(this.domianTrees.isEmpty())))
        this.success = true;
      else {
        this.success = false;
      }
      return "searchDomainTree";
    }
    catch (Exception e) {
      log.error("DomainAction.searchDomainTree", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String verifyDomain() throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if (this.newDomain == null) {
        this.success = false;
      } else {
        int n = this.domainService.getVerifyDomain(this.newDomain);
        if (n == 0) {
          this.success = true;
          this.verifyMsg = "可以使用";
        } else if (n >= 1) {
          this.success = false;
          this.verifyMsg = "已存在";
        }
      }
      return "verifyDomain";
    } catch (Exception e) {
      log.error("DomainAction.verifyDomain", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String addDomain() throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      int n = this.domainService.insertNewdomain(this.newDomain);

      if (n == 1) {
        this.success = true;
      }
      this.success = false;
    }
    catch (Exception e) {
      log.error("DomainAction.addDomain", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      this.success = false;
    }
    label88: return "addDomain";
  }

  public String loadDomain()
    throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      if ((this.loadDomain == null) || (this.loadDomain.getDomainId() == null) || ("".equals(this.loadDomain.getDomainId()))) {
        this.success = false;
      }
      this.data = this.domainService.getLoadDomain(this.loadDomain);
      if (this.data != null)
        this.success = true;
      else {
        this.success = false;
      }
      return "loadDomain";
    } catch (Exception e) {
      log.error("DomainAction.loadDomain", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String updateDomainBean()
    throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try
    {
      int n = this.domainService.updateDomain(this.updateDomain);
      if (n == 1) {
        this.success = true;
        StringBuffer description = new StringBuffer("用户");
        description.append(this.userInfoBean.getRealname());
        description.append("修改地区成功");
      } else {
        this.success = false;
      }
      return "updateDomainBean";
    } catch (Exception e) {
      log.error("UserAction.updateDomainBean", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String deleteDomain()
    throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try
    {
      if ((this.confirmDelete == null) || (!("确认删除".equals(this.confirmDelete)))) {
        this.success = false;
        this.deleteMsg = " 删除失败！您输入的信息不正确!";
        return "deleteDomain";
      }
      if (this.delDomain == null) {
        this.success = false;
        this.deleteMsg = "删除用户信息失败!";
        return "deleteDomain";
      }

      List ns = this.domainService.deleteDomain(this.delDomain);
      if (ns != null) {
        this.success = true;
        this.deleteMsg = "您成功删除了&nbsp;" + ns.get(0) + "&nbsp;条地区!&nbsp" + 
          ns.get(1) + "&nbsp条子地区";
      } else {
        this.success = false;

        this.deleteMsg = "删除用户信息失败!";
      }
      return "deleteDomain";
    } catch (Exception e) {
      log.error("UserAction.addUser", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String searchResponsibleUser() throws Exception
  {
    if (this.loadDomain == null) {
      return "responsible";
    }
    this.responsible = this.domainService.getResponsibleUser(this.loadDomain);
    return "responsible";
  }

  public String searchDomainAndEquitTree() throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try
    {
      DomainBean domain = new DomainBean();
      domain.setDomainCode(this.userInfoBean.getAreano());
      this.domianAndEquitTrees = this.domainService.getDomainAndEquitTree(domain);
      return "searchDomainAndEquitTree";
    } catch (Exception e) {
      log.error("DomainAction.searchDomainAndEquitTree", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public DomainService getDomainService()
  {
    return this.domainService;
  }

  public void setDomainService(DomainService domainService) {
    this.domainService = domainService;
  }

  public UserInfoBean getUserInfoBean() {
    return this.userInfoBean;
  }

  public void setUserInfoBean(UserInfoBean userInfoBean) {
    this.userInfoBean = userInfoBean;
  }

  public DomainBean getUpdateDomain() {
    return this.updateDomain;
  }

  public void setUpdateDomain(DomainBean updateDomain) {
    this.updateDomain = updateDomain;
  }

  public DomainBean getDelDomain() {
    return this.delDomain;
  }

  public void setDelDomain(DomainBean delDomain) {
    this.delDomain = delDomain;
  }

  public DomainBean getLoadDomain() {
    return this.loadDomain;
  }

  public void setLoadDomain(DomainBean loadDomain) {
    this.loadDomain = loadDomain;
  }

  public ErrorMessage getErrorMessage() {
    return this.errorMessage;
  }

  public void setErrorMessage(ErrorMessage errorMessage) {
    this.errorMessage = errorMessage;
  }

  public Tree getDomianTree() {
    return this.domianTree;
  }

  public void setDomianTree(Tree domianTree) {
    this.domianTree = domianTree;
  }

  public List<Tree> getDomianTrees() {
    return this.domianTrees;
  }

  public void setDomianTrees(List<Tree> domianTrees) {
    this.domianTrees = domianTrees;
  }

  public List<Tree> getDomianAndEquitTrees() {
    return this.domianAndEquitTrees;
  }

  public void setDomianAndEquitTrees(List<Tree> domianAndEquitTrees) {
    this.domianAndEquitTrees = domianAndEquitTrees;
  }

  public DomainBean getNewDomain() {
    return this.newDomain;
  }

  public void setNewDomain(DomainBean newDomain) {
    this.newDomain = newDomain;
  }

  public boolean isSuccess() {
    return this.success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getVerifyMsg() {
    return this.verifyMsg;
  }

  public void setVerifyMsg(String verifyMsg) {
    this.verifyMsg = verifyMsg;
  }

  public DomainBean getData() {
    return this.data;
  }

  public void setData(DomainBean data) {
    this.data = data;
  }

  public void setResponsibles(List<DomainBean> responsibles) {
    this.responsibles = responsibles;
  }

  public String getDeleteMsg() {
    return this.deleteMsg;
  }

  public void setDeleteMsg(String deleteMsg) {
    this.deleteMsg = deleteMsg;
  }

  public String getConfirmDelete() {
    return this.confirmDelete;
  }

  public void setConfirmDelete(String confirmDelete) {
    this.confirmDelete = confirmDelete;
  }

  public List<DomainBean> getResponsibles() {
    return this.responsibles;
  }

  public UserInfoBean getResponsible() {
    return this.responsible;
  }

  public void setResponsible(UserInfoBean responsible) {
    this.responsible = responsible;
  }

  public DomainBean getSearchDomain() {
    return this.searchDomain;
  }

  public void setSearchDomain(DomainBean searchDomain) {
    this.searchDomain = searchDomain;
  }

  public UserInfoBean getNewUser() {
    return this.newUser;
  }

  public void setNewUser(UserInfoBean newUser) {
    this.newUser = newUser;
  }

  public static long getSerialVersionUID() {
    return -6671118577433610578L;
  }

  public static Logger getLog() {
    return log;
  }

  public String[] getPowerstr() {
    return this.powerstr;
  }

  public void setPowerstr(String[] powerstr) {
    this.powerstr = powerstr;
  }
}