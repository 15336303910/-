package manage.domain.service.impl;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.CommonUtil;
import base.util.md5;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import manage.domain.pojo.DomainBean;
import manage.domain.service.DomainService;
import manage.main.pojo.MenuInfoBean;
import manage.tree.pojo.Tree;
import manage.tree.util.TreeUtil;
import manage.user.pojo.RoleInfoBean;
import manage.user.pojo.UserInfoBean;

public class DomainServiceImpl extends DataBase
  implements DomainService
{
  private static String GET_ROOT_DOMAIN = "domain.getRootDomain";

  private static String GET_ALL_CHILDREN_DOMAIN = "domain.getAllChildrenDomain";

  private static String GEI_MAX_DOMAINCODE = "domain.getMaxCode";

  private static String GET_PARENT_DOMAINCODE = "domain.getParentDomianCode";

  private static String INSERT_NEW_DOMAIN = "domain.insertNewDomain";
  private static String UPDATE_DOMAIN = "domain.updateDomain";

  private static String LOAD_DOMIAN = "domain.loadDomian";

  private static String VERIFY_DOMAIN = "domain.verifyDomain";

  private static String GET_DOMAIN_RESPONSIBLES = "domain.getResponsibles";

  private static String DELETE_DOMAIN = "domain.deleteDomain";

  private static String DELETE_SUB_DOMAIN = "domain.deleteSubDomain";

  private static String DELETE_DOMAIN_USER = "domain.deleteDomainUser";

  private static String SELECT_RESPONSIBLE_USER = "domain.selectResponsibleUser";
  private static final String GET_FIND_DOMIAN = "domain.getFindDomian";
  private static final String INSERT_ORDER_CONFIGBEAN = "domain.insertOrderConfig";
  private static String INSERT_USER_POWERS = "user.insertUserPowers";

  private static String INSERT_NEW_USER = "user.insertNewUwer";
  private static final String GET_PARENT_MENU = "user.getParentMenu";
  private static final String GET_DOMAIN_BY_CODE = "domain.getDomainByCode";
  private static final String GetRoleBean = "domain.getRoleBean";

  public List<Tree> getDomianTree(DomainBean domain)
    throws DataAccessException
  {
    DomainBean rootDomain = new DomainBean();
    Tree rootTree = new Tree();
    rootDomain = (DomainBean)getObject(GET_ROOT_DOMAIN, domain);

    List <DomainBean>childrenDomain = getObjects(
      GET_ALL_CHILDREN_DOMAIN, domain);
    List chTrees = new ArrayList();
    List levels = new ArrayList();
    if (rootDomain != null) {
      rootTree = TreeUtil.domainToTree(rootDomain);
      rootTree.setHref("tree!treeHref.action?domainCode=" + 
        rootDomain.getDomainCode() + "&domainName=" + 
        rootDomain.getDomainName() + "&manage=" + 
        domain.getManage());
      for (DomainBean cdomain : childrenDomain)
      {
        chTrees.add(TreeUtil.domainToTree(cdomain));
        Tree t = TreeUtil.domainToTree(cdomain);

        t.setHref("tree!treeHref.action?domainCode=" + 
          cdomain.getDomainCode() + "&domainName=" + 
          cdomain.getDomainName() + "&manage=" + 
          domain.getManage());

        levels = addLevels(levels, t);
      }

    }

    rootTree = getRootTree(rootTree, levels);

    List result = new ArrayList();
    result.add(rootTree);

    return result;
  }

  public List<Tree> getDomianTree2(DomainBean domain)
    throws DataAccessException
  {
    DomainBean rootDomain = new DomainBean();
    Tree rootTree = new Tree();
    rootDomain = (DomainBean)getObject(GET_ROOT_DOMAIN, domain);

    List <DomainBean>childrenDomain = getObjects(
      GET_ALL_CHILDREN_DOMAIN, domain);
    List chTrees = new ArrayList();
    List levels = new ArrayList();
    if (rootDomain != null) {
      rootTree = TreeUtil.domainToTree2(rootDomain);
      rootTree.setHref("tree!treeHref.action?domainCode=" + 
        rootDomain.getDomainCode() + "&domainName=" + 
        rootDomain.getDomainName() + "&manage=" + 
        domain.getManage());
      for (DomainBean cdomain : childrenDomain)
      {
        chTrees.add(TreeUtil.domainToTree2(cdomain));
        Tree t = TreeUtil.domainToTree2(cdomain);

        t.setHref("tree!treeHref.action?domainCode=" + 
          cdomain.getDomainCode() + "&domainName=" + 
          cdomain.getDomainName() + "&manage=" + 
          domain.getManage());

        levels = addLevels(levels, t);
      }

    }

    rootTree = getRootTree(rootTree, levels);

    List result = new ArrayList();
    result.add(rootTree);

    return result;
  }

  private Tree removeFalseParent(Tree rootTree)
  {
    if (rootTree == null) {
      return null;
    }
    if (rootTree.getChildren().isEmpty()) {
      rootTree.setChildren(null);
    } else {
      List<Tree> list = rootTree.getChildren();
      for (Tree t : list) {
        removeFalseParent(t);
      }
    }
    return rootTree;
  }

  private Tree getRootTree(Tree rootTree, List<List<Tree>> levels)
  {
    for (int i = 0; i < levels.size(); ++i) {
      if (rootTree.getId().equals(((Tree)((List)levels.get(i)).get(0)).getParentId())) {
        rootTree.getChildren().addAll((Collection)levels.get(i));
      }
    }
    for (int j = 0; j < rootTree.getChildren().size(); ++j) {
      Tree t = (Tree)rootTree.getChildren().get(j);
      getRootTree(t, levels);
    }
    return rootTree;
  }

  private List<List<Tree>> addLevels(List<List<Tree>> levels, Tree t)
  {
    List newlevels = new ArrayList();
    if (levels.isEmpty()) {
      List list = new ArrayList();
      list.add(t);
      levels.add(list);
    } else {
      boolean have = true;
      for (int i = 0; i < levels.size(); ++i) {
        List list = (List)levels.get(i);
        if (((Tree)list.get(0)).getParentId().equals(t.getParentId())) {
          list.add(t);
          have = false;
          break;
        }
      }
      if (have) {
        List li = new ArrayList();
        li.add(t);
        newlevels.add(li);
      }
    }
    levels.addAll(newlevels);
    return levels;
  }

  public List<Tree> getDomainAndEquitTree(DomainBean domain) throws DataAccessException
  {
    DomainBean rootDomain = new DomainBean();
    Tree rootTree = new Tree();
    rootDomain = (DomainBean)getObject(GET_ROOT_DOMAIN, domain);

    List<DomainBean> childrenDomain = getObjects(
      GET_ALL_CHILDREN_DOMAIN, domain);
    List chTrees = new ArrayList();
    List levels = new ArrayList();
    if (rootDomain != null) {
      rootTree = TreeUtil.domainToTree(rootDomain);
      rootTree.setHref("equt!showTopo.action?areano=" + 
        rootDomain.getDomainCode());
      for (DomainBean cdomain : childrenDomain) {
        chTrees.add(TreeUtil.domainToTree(cdomain));
        Tree t = TreeUtil.domainToTree(cdomain);
        t.setHref("equt!showTopo.action?areano=" + 
          cdomain.getDomainCode());

        levels = addLevels(levels, t);
      }
    }

    rootTree = getRootTree(rootTree, levels);

    List result = new ArrayList();
    result.add(rootTree);

    return result;
  }

  public int getVerifyDomain(DomainBean newDomain)
    throws DataAccessException
  {
    List <DomainBean>list = getObjects(VERIFY_DOMAIN, newDomain);
    int n = 0;
    if (newDomain.getDomainId() == null) {
      return ((list == null) ? 0 : list.size());
    }
    for (DomainBean d : list) {
      if (!(newDomain.getDomainId().equals(d.getDomainId()))) {
        return 1;
      }
    }
    return n;
  }

  public int insertNewDomain(DomainBean newDomain, UserInfoBean newUser)
    throws DataAccessException
  {
    if (newDomain == null) {
      return 0;
    }

    if (newDomain.getDomainCode() == null)
    {
      String maxCode = (String)getObject(GEI_MAX_DOMAINCODE, 
        newDomain);
      if ((maxCode == null) || ("".equals(maxCode))) {
        String parentCode = (String)getObject(GET_PARENT_DOMAINCODE, newDomain);
        String newCode = parentCode + "01";
        newDomain.setDomainCode(newCode);
      }
      else {
        String codeFront = maxCode.substring(0, maxCode.length() - 2);
        String codeBack = maxCode.substring(maxCode.length() - 2, 
          maxCode.length());
        int newIntCode = Integer.parseInt(codeBack) + 1;
        String newDomainCode = "0" + 
          newIntCode;
        newDomain.setDomainCode(codeFront + newDomainCode);
      }

    }

    String modulestr = "0000000000000000000000000000000000000000000000000000000000000000";
    char[] modulechar = modulestr.toCharArray();
    switch (Integer.parseInt(newUser.getUsertype().trim()))
    {
    case 1:
      modulechar[0] = '1'; break;
    case 2:
      modulechar[1] = '1'; break;
    case 3:
      modulechar[2] = '1'; break;
    case 4:
      modulechar[3] = '1'; break;
    case 5:
      modulechar[4] = '1';
    }
    newUser.setAreano(newDomain.getDomainCode());
    newUser.setModulestr(new String(modulechar));
    newUser.setPassword(md5.strToMD5(newUser.getPassword()));
    int uid = ((Integer)insert(INSERT_NEW_USER, newUser)).intValue();

    String[] powers = newUser.getPowerstr().split(",");
    List menus = new ArrayList();
    for (int i = 0; i < powers.length; ++i) {
      MenuInfoBean power = new MenuInfoBean();
      power.setUserId(Integer.valueOf(uid));
      power.setCode(powers[i].trim());
      menus.add(power);
      MenuInfoBean parent = (MenuInfoBean)getObject("user.getParentMenu", power);
      if (CommonUtil.noHasMenu(menus, parent)) {
        parent.setUserId(Integer.valueOf(uid));
        menus.add(parent);
      }
    }

    batchInsert(INSERT_USER_POWERS, menus);

    newDomain.setIsParent("false");
    newDomain.setResponsibleid(Integer.valueOf(uid));
    insert(INSERT_NEW_DOMAIN, newDomain);

    DomainBean parent = new DomainBean();
    parent.setDomainId(newDomain.getParentId());
    parent.setIsParent("true");
    update(UPDATE_DOMAIN, parent);

    return 1; }

  public int insertNewdomain(DomainBean newDomain) throws DataAccessException {
    if (newDomain == null) {
      return 0;
    }

    if (newDomain.getDomainCode() == null)
    {
      String maxCode = (String)getObject(GEI_MAX_DOMAINCODE, newDomain);
      if ((maxCode == null) || ("".equals(maxCode))) {
        String parentCode = (String)getObject(GET_PARENT_DOMAINCODE, newDomain);
        String newCode = parentCode + "01";
        newDomain.setDomainCode(newCode);
      }
      else {
        String codeFront = maxCode.substring(0, maxCode.length() - 2);
        String codeBack = maxCode.substring(maxCode.length() - 2, 
          maxCode.length());
        int newIntCode = Integer.parseInt(codeBack) + 1;
        String newDomainCode = "0" + 
          newIntCode;
        newDomain.setDomainCode(codeFront + newDomainCode);
      }

    }

    newDomain.setIsParent("false");
    insert(INSERT_NEW_DOMAIN, newDomain);

    DomainBean parent = new DomainBean();
    parent.setDomainId(newDomain.getParentId());
    parent.setIsParent("true");
    update(UPDATE_DOMAIN, parent);

    return 1;
  }

  public DomainBean getLoadDomain(DomainBean loadDomain)
    throws DataAccessException
  {
    DomainBean domain = (DomainBean)
      getObject(LOAD_DOMIAN, loadDomain);
    String type = "";
    if ((domain.getRoleId() != null) && (!(domain.getRoleId().equals("")))) {
      RoleInfoBean role = new RoleInfoBean();
      role.setRoleId(Integer.valueOf(domain.getRoleId()));
      role = (RoleInfoBean)getObject("domain.getRoleBean", role);
      domain.setRoleName(role.getRoleName());
    }
    if ((domain != null) && 
      ("0".equals(domain.getDomainCode().trim()))) {
      domain.setParentName(domain.getDomainName());
    }

    List Responsibles = getObjects(
      GET_DOMAIN_RESPONSIBLES, loadDomain);
    domain.setDomains(Responsibles);
    return domain;
  }

  public List<Integer> deleteDomain(DomainBean delDomain)
    throws DataAccessException
  {
    List ns = new ArrayList();
    Integer x = Integer.valueOf(0); Integer y = Integer.valueOf(0); Integer z = Integer.valueOf(0);
    if (delDomain != null) {
      x = Integer.valueOf(delete(DELETE_DOMAIN, delDomain));
      if ((x != null) && (delDomain.getDomainCode() != null) && 
        (!("".equals(delDomain.getDomainCode())))) {
        y = Integer.valueOf(delete(DELETE_SUB_DOMAIN, delDomain));
        z = Integer.valueOf(delete(DELETE_DOMAIN_USER, delDomain));
      }
    }
    ns.add(x);
    ns.add(y);
    ns.add(z);
    return ns;
  }

  public UserInfoBean getResponsibleUser(DomainBean loadDomain)
    throws DataAccessException
  {
    UserInfoBean user = (UserInfoBean)getObject(
      SELECT_RESPONSIBLE_USER, loadDomain);
    String type = "";
    if (user != null) {
      String powerStr = user.getModulestr();
      if ((powerStr != null) && (!("".equals(powerStr)))) {
        char[] powers = powerStr.toCharArray();
        if (powers != null) {
          for (int j = 0; j < powers.length; ++j) {
            if (powers[j] == '1') {
              switch (j)
              {
              case 0:
                type = "管理员";
                break;
              case 1:
                type = "施工人员";
                break;
              case 2:
                type = "维护人员";
                break;
              case 3:
                type = "查看人员";
                break;
              case 4:
                type = "代维人员";
              }

              user.setUsertype(type);
              break;
            }
          }
        }
      }
    }

    return user;
  }

  public int updateDomain(DomainBean updateDomain)
    throws DataAccessException
  {
    return update(UPDATE_DOMAIN, updateDomain);
  }

  public List<Tree> findDomianTree(DomainBean searchDomain)
    throws DataAccessException
  {
    List <DomainBean>list = getObjects("domain.getFindDomian", searchDomain);
    List tlist = new ArrayList();
    for (DomainBean domain : list) {
      domain.setIsParent("false");
      Tree t = TreeUtil.domainToTree(domain);
      t.setHref("tree!treeHref.action?domainCode=" + 
        domain.getDomainCode() + "&domainName=" + 
        domain.getDomainName() + "&manage=" + 
        searchDomain.getManage());
      tlist.add(t);
    }
    return tlist;
  }

  public List<Tree> findEqutDomianTree(DomainBean domainBean) throws DataAccessException
  {
    List <DomainBean>list = getObjects("domain.getFindDomian", domainBean);
    List tlist = new ArrayList();
    for (DomainBean domain : list) {
      domain.setIsParent("false");
      Tree t = TreeUtil.domainToTree(domain);
      t.setHref("equt!showTopo.action?areano=" + 
        domain.getDomainCode());
      tlist.add(t);
    }
    return tlist;
  }

  public DomainBean getDomain(DomainBean domainBean)
    throws DataAccessException
  {
    return ((DomainBean)getObject("domain.getDomainByCode", domainBean));
  }
}