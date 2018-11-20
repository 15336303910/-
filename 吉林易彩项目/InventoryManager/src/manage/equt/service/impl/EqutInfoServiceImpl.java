package manage.equt.service.impl;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.CommonUtil;
import interfaces.hwinterface.interfaceUtil.util.DataUtil;
import interfaces.hwinterface.interfaces.equt.sender.EqutSender;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import manage.domain.pojo.DomainBean;
import manage.equt.pojo.ApplyInfoBean;
import manage.equt.pojo.CodeIndexInfoBean;
import manage.equt.pojo.EqutInfoBean;
import manage.equt.pojo.EqutModelInfoBean;
import manage.equt.service.EqutInfoService;
import manage.fiber.pojo.JumpFiberInfoBean;
import manage.point.pojo.PointAlarmInfoBean;
import manage.point.pojo.PointEventInfoBean;
import manage.point.pojo.PointInfoBean;
import manage.route.pojo.CableInfoBean;
import manage.tree.pojo.Tree;
import manage.tree.util.TreeUtil;
import manage.user.pojo.UserInfoBean;

public class EqutInfoServiceImpl extends DataBase
  implements EqutInfoService
{
  private static final String GET_EQUT = "equt.getEqut";
  private static final String GET_EQUT_CHECK = "equt.getEqutCheck";
  private static final String GET_EQUT_LIST_ALL = "equt.getEqutListAll";
  private static final String GET_UP_EQUT = "equt.getUpEqutInCable";
  private static final String GET_DOWN_EQUT = "equt.getDownEqutInCable";
  private static final String GET_CHILD_EQUT = "equt.getChildEqut";
  private static final String GET_DOMAIN = "equt.getDomain";
  private static final String COUNT_EQUT_IN_DOMAIN = "equt.countEqutInDomain";
  private static final String UPDATE_EQUT = "equt.updateEqut";
  private static final String GET_EMODEL = "equt.getEmodel";
  private static final String GET_USER_CK = "equt.getEqutCK";
  private static final String SAVE_USER_LASTEID = "equt.saveLastEid";
  private static final String INSERT_POINT_WEB_ALARM = "equt.insertPointWebAlarm";
  private static final String UPDATE_ISLATEST = "equt.updateIsLatest";
  private static final String SELECT_OFPLIST = "equt.selectOfpList";
  private static final String UPDATE_OFPLIST = "equt.updateOfpList";
  private static final String GET_USER = "equt.getUser";
  private static final String GET_PRL_USER_LIST = "equt.getPrlUserList";
  private static final String GET_POINT_ALARM = "equt.getPointAlarm";
  private static final String SAVE_EQUT_ONLINE = "equt.saveEqutOnline";
  private static final String GET_ORDER = "equt.getOrder";
  private static final String GET_OFP = "equt.getOfpList";
  private static final String UPDATE_ORDER = "equt.updateWorkOrder";
  private static final String INSERT_LOG = "equt.insertOrderLog";
  private static final String DELETE_EQUT = "equt.deleteEqut";
  private static final String DELETE_POINT = "equt.deletePoint";
  private static final String UPDATE_LAST_EID = "equt.updateLastEid";
  private static final String GET_CABLE_LIST = "equt.getCableList";
  private static final String DELETE_EVENT = "equt.deleteEvent";
  private static final String DELETE_ALARM = "equt.deleteAlarm";
  private static final String DELETE_ALARM_MASK = "equt.deleteAlarmMask";
  private static final String GET_CABLE_BY_EID = "equt.getCableByEid";
  private static final String UPDATE_CABLE = "equt.updateCable";
  private static final String UPDATE_JUMP_FIBER_1 = "equt.updateJumpFiber1";
  private static final String UPDATE_JUMP_FIBER_2 = "equt.updateJumpFiber2";
  private static final String INSERT_EQUT = "equt.insertEqut";
  private static final String GET_CABLE_LIST_BY_AREANO = "equt.getCableListByAreano";
  private static final String GET_CABLE_ALARM_COUNT = "equt.getCableAlarmCount";
  private static final String GET_EQUT_CONFIG = "equt.getEqutConfig";
  private static final String INSERT_POINT_EVENT = "workorder.insertPointEvent";
  private static final String DELETE_POINT_ALARM = "workorder.deleteAlarm";
  private static final String UPDATE_EVENT_TO_OLD = "workorder.updateEventToOld";
  private static final String SET_POINT_DEL = "pdapoint.setPointDel";
  private static final String UPDATE_EQUT_DOMAIN = "equt.updateEqutDomain";
  private static final String UPDATE_POINT_AREANO = "equt.updatePointAreano";
  private static final String UPDATE_EVENT_AREANO = "equt.updateEventAreano";
  private static final String UPDATE_ALARM_AREANO = "equt.updateAlarmAreano";
  private static final String UPDATE_ALARM_MASK_AREANO = "equt.updateAlarmMaskAreano";
  private static final String UPDATE_CABLE_AREANO = "equt.updateCableAreano";
  private static final String GET_NO_LAT_LON_EQUT = "equt.getNoLatLonEqut";
  private static final String GET_SON_EQUT = "equt.getSonEqut";
  private static final String GET_SON_POINT = "equt.getSonPoint";
  private static final String GET_SON_POINT_COUNT = "equt.getSonPointCount";
  private static final String INSERT_CODE_FIBERCODE = "workorder.insertCodeFibercode";
  private static final String GetCodeIndex = "equt.getCodeIndex";
  private static final String InsertCodeIndex = "equt.updateIndex";
  private static final String UpdatePointStatment = "equt.updatePoint";
  private static final String COUNT_FIBERCODE_EXSIT = "equt.countFibercodeExist";
  private static final String GetApplyList = "equt.getApplyList";
  private static final String GetApplyCount = "equt.getApplyCount";
  private static final String DelApplyStatment = "equt.delApply";

  public EqutInfoBean getEqutInfoBean(EqutInfoBean equtInfo)
    throws DataAccessException
  {
    EqutInfoBean equtInfoBean = (EqutInfoBean)getObject("equt.getEqut", equtInfo);
    return equtInfoBean;
  }

  public List<EqutInfoBean> getEqutCheck(EqutInfoBean equtInfoBean) throws DataAccessException {
    return getObjects("equt.getEqutCheck", equtInfoBean);
  }

  public List<EqutInfoBean> getEqutList(EqutInfoBean equtInfo) throws DataAccessException {
    return getObjects("equt.getEqut", equtInfo);
  }

  public List<EqutInfoBean> getUpEqutList(EqutInfoBean equtInfoBean)
    throws DataAccessException
  {
    List<EqutInfoBean> equtlist = getObjects("equt.getUpEqutInCable", equtInfoBean);
    List resultlist = new ArrayList();
    String eids = "";
    for (EqutInfoBean e : equtlist) {
      if (eids.length() == 0) {
        eids = eids + "#" + e.getEid() + "#";
        resultlist.add(e);
      }
      if (!(eids.contains("#" + e.getEid() + "#"))) {
        eids = eids + e.getEid() + "#";
        resultlist.add(e);
      }
    }
    return resultlist;
  }

  public List<EqutInfoBean> getDownEqutList(EqutInfoBean equtInfoBean)
    throws DataAccessException
  {
    List<EqutInfoBean> equtlist = getObjects("equt.getDownEqutInCable", equtInfoBean);
    List resultlist = new ArrayList();
    String eids = "";
    for (EqutInfoBean e : equtlist) {
      if (eids.length() == 0) {
        eids = eids + "#" + e.getEid() + "#";
        resultlist.add(e);
      }
      if (!(eids.contains("#" + e.getEid() + "#"))) {
        eids = eids + e.getEid() + "#";
        resultlist.add(e);
      }
    }
    return resultlist;
  }

  public List<Tree> getDomainAndEqutTree(DomainBean domain) throws DataAccessException
  {
    domain = (DomainBean)getObject("equt.getDomain", domain);
    DomainBean childDomain = new DomainBean();
    childDomain.setParentId(domain.getDomainId());
    List<DomainBean> domianList = getObjects("equt.getDomain", childDomain);

    List treeList = new ArrayList();
    for (DomainBean d : domianList) {
      int equtAmount = getCount("equt.countEqutInDomain", d);
      if (equtAmount != 0)
        d.setHasEqut(true);
      else {
        d.setHasEqut(false);
      }
      treeList.add(TreeUtil.domainToTreeNoHref(d));
    }

    EqutInfoBean equt = new EqutInfoBean();
    equt.setAreano(domain.getDomainCode());
    List<EqutInfoBean> equtList = getObjects("equt.getChildEqut", equt);

    for (EqutInfoBean e : equtList) {
      treeList.add(TreeUtil.equtToTree(e));
    }
    return treeList;
  }

  public int updateEqutInfo(EqutInfoBean equtInfoBean) throws DataAccessException
  {
    return update("equt.updateEqut", equtInfoBean);
  }

  public List<EqutInfoBean> getEqutListAll(EqutInfoBean equtInfoBean)
    throws DataAccessException
  {
    return getObjects("equt.getEqutListAll", equtInfoBean);
  }

  public List<Tree> getEqutInfoTreeList(EqutInfoBean equtInfoBean) throws DataAccessException
  {
    List<EqutInfoBean> list = getObjects("equt.getEqut", equtInfoBean);

    List treeList = new ArrayList();

    for (EqutInfoBean e : list) {
      treeList.add(TreeUtil.equtToTree(e));
    }
    return treeList;
  }

  public EqutModelInfoBean getEmodelInfoBean(EqutInfoBean equtInfoBean) throws DataAccessException
  {
    return ((EqutModelInfoBean)getObject("equt.getEmodel", equtInfoBean));
  }

  public List<UserInfoBean> getEqutUserCK(EqutInfoBean equtInfoBean)
    throws DataAccessException
  {
    List userList = getObjects("equt.getEqutCK", equtInfoBean);
    return userList;
  }

  public void saveLastEid(UserInfoBean userInfoBean) throws DataAccessException
  {
    update("equt.saveLastEid", userInfoBean);
  }

  public void saveEqutHeartBeat(EqutInfoBean equtInfoBean)
    throws Exception
  {
    if (equtInfoBean != null) {
      update("equt.saveEqutOnline", equtInfoBean);
    }
  }


  public UserInfoBean getUserInfo(EqutInfoBean equtInfoBean) throws DataAccessException {
    return ((UserInfoBean)getObject("equt.getUser", equtInfoBean));
  }

  public List<UserInfoBean> getPrlUserList(EqutInfoBean equtInfoBean) throws DataAccessException
  {
    return getObjects("equt.getPrlUserList", equtInfoBean);
  }

  public void deleteEqut(EqutInfoBean equtInfoBean)
    throws DataAccessException
  {
    delete("equt.deleteEqut", equtInfoBean);

    delete("equt.deletePoint", equtInfoBean);

    update("equt.updateLastEid", equtInfoBean);

    delete("equt.deleteEvent", equtInfoBean);

    delete("equt.deleteAlarm", equtInfoBean);

    delete("equt.deleteAlarmMask", equtInfoBean);

    delete("equt.updateJumpFiber1", equtInfoBean);
    delete("equt.updateJumpFiber2", equtInfoBean);

    List<CableInfoBean> clist = getObjects("equt.getCableByEid", equtInfoBean);
    for (CableInfoBean c : clist) {
      if (equtInfoBean.getEid().equals(c.getStarteid())) {
        c.setStarteid("");
        c.setStartDeviceName("");
      } else {
        c.setEndeid("");
        c.setEndDeviceName("");
      }
      update("equt.updateCable", c);
    }
  }

  public List<CableInfoBean> getCableList(CableInfoBean cableInfoBean)
    throws DataAccessException
  {
    return getObjects("equt.getCableList", cableInfoBean);
  }

  public void insertEqutInfo(EqutInfoBean equtInfoBean) throws DataAccessException
  {
    insert("equt.insertEqut", equtInfoBean);
  }

  public List<EqutInfoBean> getEqutByAreano(EqutInfoBean equtInfoBean) {
    return getObjects("equt.getCableListByAreano", equtInfoBean);
  }

  public int getCableAlarmCount(EqutInfoBean equtInfoBean)
  {
    return getCount("equt.getCableAlarmCount", equtInfoBean);
  }

  public int updateEqutDomain(EqutInfoBean equtInfoBean)
    throws DataAccessException
  {
    Integer n = Integer.valueOf(update("equt.updateEqutDomain", equtInfoBean));
    if ((n != null) && (n.intValue() != 0))
    {
      update("equt.updatePointAreano", equtInfoBean);

      update("equt.updateEventAreano", equtInfoBean);

      update("equt.updateAlarmAreano", equtInfoBean);

      update("equt.updateAlarmMaskAreano", equtInfoBean);

      List<CableInfoBean> clist = getObjects("equt.getCableByEid", equtInfoBean);
      for (CableInfoBean c : clist)
      {
        String str = c.getAreanoLink();
        String[] arr = new String[0];
        String startAreanoLink = "";
        String endAreanoLink = "";
        if ((str != null) && (!("".equals(str)))) {
          arr = str.split("\\*");
        }

        if (equtInfoBean.getEid().equals(c.getStarteid())) {
          if ((equtInfoBean.getAreano() != null) && (!("".equals(equtInfoBean.getAreano()))))
            startAreanoLink = equtInfoBean.getAreano();
          else if ((arr[1] != null) && (!("".equals(arr[1])))) {
            startAreanoLink = arr[1];
          }
          endAreanoLink = arr[2];
        } else {
          if ((equtInfoBean.getAreano() != null) && (!("".equals(equtInfoBean.getAreano()))))
            endAreanoLink = equtInfoBean.getAreano();
          else if ((arr[2] != null) && (!("".equals(arr[2])))) {
            endAreanoLink = arr[2];
          }
          startAreanoLink = arr[1];
        }
        String newAreanoLink = "*" + startAreanoLink + "*" + endAreanoLink + "*";
        c.setAreanoLink(newAreanoLink);
        update("equt.updateCableAreano", c);
      }

    }

    return n.intValue();
  }

  public List<EqutInfoBean> getNoLatLonEqut(EqutInfoBean equtInfoBean) throws DataAccessException
  {
    List list = getObjects("equt.getNoLatLonEqut", equtInfoBean);
    return list;
  }

  public List<EqutInfoBean> getSonList(EqutInfoBean equtInfoBean) throws DataAccessException
  {
    List list = getObjects("equt.getSonEqut", equtInfoBean);
    return list;
  }

  public PointInfoBean getEqutPoint(EqutInfoBean equtInfoBean) throws DataAccessException
  {
    List plist = getObjects("equt.getSonPoint", equtInfoBean);

    int total = getCount("equt.getSonPointCount", equtInfoBean);
    PointInfoBean p = new PointInfoBean();
    p.setPoints(plist);
    p.setTotal(Integer.valueOf(total));
    return p;
  }

  public void insertCodeFibercode(JumpFiberInfoBean j) throws DataAccessException
  {
    insert("workorder.insertCodeFibercode", j);
  }
  public int getIndex(CodeIndexInfoBean ci) throws DataAccessException
  {
    CodeIndexInfoBean c = (CodeIndexInfoBean)getObject("equt.getCodeIndex", ci);
    int index = c.getCodenum().intValue();
    return index;
  }

  public void insertIndex(CodeIndexInfoBean ci) throws DataAccessException {
    update("equt.updateIndex", ci); }

  public void updatePoint(PointInfoBean point) throws DataAccessException {
    update("equt.updatePoint", point);
  }

  public boolean isFibercodeExist(JumpFiberInfoBean j) throws DataAccessException
  {
    int num = getCount("equt.countFibercodeExist", j);

    return (num != 0);
  }

  public ApplyInfoBean applyGrid(ApplyInfoBean applyBean)
    throws DataAccessException
  {
    ApplyInfoBean ab = new ApplyInfoBean();
    List abs = getObjects("equt.getApplyList", applyBean);
    int total = getCount("equt.getApplyCount", applyBean);
    ab.setApplys(abs);
    ab.setTotal(Integer.valueOf(total));
    return ab;
  }

  public int delApplyBean(ApplyInfoBean applyBean) throws DataAccessException {
    delete("equt.delApply", applyBean);
    return 1;
  }

  public int updateEqutIP(EqutInfoBean equtInfoBean) throws DataAccessException
  {
    Integer n = Integer.valueOf(update("equt.updateEqut", equtInfoBean));
    return n.intValue();
  }

  public EqutInfoBean getEqutGrid(EqutInfoBean equt) throws DataAccessException
  {
    List<EqutInfoBean> list = getObjects("equt.getEqut", equt);
    List lists = new ArrayList();
    for (EqutInfoBean e : list) {
      UserInfoBean user = new UserInfoBean();

      lists.add(e);
    }
    equt = new EqutInfoBean();
    equt.setEquts(lists);
    return equt;
  }

@Override
public void insertCodeFibercode(List<JumpFiberInfoBean> paramList)
		throws DataAccessException {
	// TODO Auto-generated method stub
	
}
}