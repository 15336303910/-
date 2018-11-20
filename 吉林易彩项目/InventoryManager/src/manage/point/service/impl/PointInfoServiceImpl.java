package manage.point.service.impl;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.CommonUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import manage.equt.pojo.EqutInfoBean;
import manage.fiber.pojo.JumpFiberInfoBean;
import manage.point.pojo.PointEventInfoBean;
import manage.point.pojo.PointInfoBean;
import manage.point.pojo.PointPstatBean;
import manage.point.service.PointInfoService;
import manage.route.pojo.CableInfoBean;
import manage.route.pojo.RouteInfoBean;
import manage.user.pojo.UserInfoBean;

public class PointInfoServiceImpl extends DataBase
  implements PointInfoService
{
  private static final String GET_POINT = "point.getPoint";
  private static final String GET_POINT2 = "point.getPoint2";
  private static final String UPDATE_POINT = "point.updatePoint";
  private static final String UPDATE_OPPOPOINT = "point.updateOppoPoint";
  private static final String GET_POINT_EVENT = "point.getEvent";
  private static final String GET_POINT_ALARM = "point.getAlarm";
  private static final String GET_POINT_PSTAT_COUNT = "point.getPointPstatCount";
  private static final String GetOfpListStatment = "point.getOfpList";
  private static final String GetOfpListCountStatment = "point.getOfpListCount";
  private static final String GetOfpCodeListStatment = "point.getOfpCodeList";
  private static final String GetOfpCodeListCountStatment = "point.getOfpCodeListCount";
  private static final String SubOfpCountStatment = "point.getSubOfpCodeCount";
  private static final String GetSubOfpListStatment = "point.getSubOfpList";
  private static final String GetFatherOfpStatment = "point.getFatherOfp";
  private static final String GetEqutListStatment = "point.getEqutList";
  private static final String GetOfpUserListStatment = "point.getOfpUserList";
  private static final String GetSubOfpCodeStatment = "point.getSubOfp";
  private static final String GetOfpOnuStatment = "point.getOfpOnu";
  private static final String GetSameOfpLIstStatment = "point.getSameOfpList";
  private static final String GetJumpFibeStatment = "point.getJumpFibe";
  private static final String GetJumpFibeStatment1 = "point.getJumpFibe1";
  private static final String GetJumpFibeStatment2 = "point.getJumpFibe2";
  private static final String INSERT_POINT = "point.insertPoint";
  private static final String UPDATE_OS_POINT_USE_DOC = "point.docUpdateOsPoint";
  private static final String INSERT_FIBER = "point.insertFiber";
  private static final String GetUserStatment = "point.getUserInfo";
  private static final String GetEqutStatment = "point.getEqutInfo";
  private static final String GetOppoEqutStatment = "point.getOppoEqutList";
  private static final String UPDATE_POINT2 = "point.updatePoint2";
  private static final String UPDATE_POINT3 = "point.updatePoint3";
  private static final String GET_EQUT = "point.getEqut";
  private static final String GET_ROUNT_COUNT_BY_NAME = "point.getRouteCountByName";
  private static final String GET_CABLE_COUNT_BY_NAME = "point.getCableCountByName";
  private static final String INSERT_ROUTE_LIST = "point.insertRoute";
  private static final String INSERT_CABLE_LIST = "point.insertCable";
  private static final String GetFiberCodeByCode = "point.getFiberCodeByCode";
  private static final String GetJumpFiberList = "point.getJumpFiberList";
  private static final String GetPointLogListStatment = "point.getPointLogList";
  private static final String GetPointLogCountStatment = "point.getPointLogCount";

  public PointInfoBean getPointInfoBean(PointInfoBean pointInfoBean)
    throws DataAccessException
  {
    PointInfoBean pInfoBean = (PointInfoBean)getObject("point.getPoint", pointInfoBean);
    return pInfoBean;
  }

  public List<PointInfoBean> getPointInfoList(PointInfoBean pointInfoBean)
    throws DataAccessException
  {
    List resultList = getObjects("point.getPoint", pointInfoBean);

    return resultList;
  }

  public void updatePointInfo(PointInfoBean pointInfoBean) throws DataAccessException
  {
    update("point.updatePoint", pointInfoBean);
  }

  public List<PointInfoBean> getListPointInfo(PointInfoBean pointInfoBean) throws DataAccessException
  {
    return getObjects("point.getPoint", pointInfoBean);
  }

  public List<PointEventInfoBean> getEventList(PointEventInfoBean point) throws DataAccessException
  {
    return getObjects("point.getEvent", point);
  }

  public List<PointPstatBean> getPointStatCount(PointInfoBean pointInfoBean)
    throws DataAccessException
  {
    List resultList = getObjects("point.getPointPstatCount", pointInfoBean);
    return resultList;
  }

  public PointInfoBean getOFPList(PointInfoBean pointInfoBean) throws DataAccessException
  {
    List<PointInfoBean> list = getObjects("point.getOfpList", pointInfoBean);
    PointInfoBean pointInfo = new PointInfoBean();
    pointInfo.setPoints(list);
    pointInfo.setTotal(Integer.valueOf(getCount("point.getOfpListCount", pointInfoBean)));
    return pointInfo;
  }

  public PointInfoBean getOFPListport(PointInfoBean pointInfoBean) throws DataAccessException
  {
    List<PointInfoBean> list = getObjects("point.getOfpCodeList", pointInfoBean);
    PointInfoBean pointInfo = new PointInfoBean();
    return pointInfo;
  }

  public PointInfoBean getOFPOfCodeList(PointInfoBean pointInfoBean)
    throws DataAccessException
  {
    PointInfoBean pointInfo = new PointInfoBean();
    return pointInfo;
  }

  public PointInfoBean getSubOFPList(PointInfoBean pointInfoBean) throws DataAccessException
  {
    return null;
  }

  public PointInfoBean getOFPOfUserList(EqutInfoBean equt) throws DataAccessException
  {
    List<EqutInfoBean> equtlist = getObjects("point.getEqutList", equt);
    List lists = new ArrayList();
    List<PointInfoBean> pointlist = new ArrayList();
    PointInfoBean pointInfoBean = new PointInfoBean();
    for (EqutInfoBean equtInfo : equtlist) {
      PointInfoBean point = new PointInfoBean();
      point.setEid(equtInfo.getEid());
      List<PointInfoBean> list = getObjects("point.getOfpUserList", point);
      for (PointInfoBean p : list)
        lists.add(p);
    }
    int j;
    if (equt.getStart().intValue() + equt.getLimit().intValue() < lists.size()) {
      for (int i = equt.getStart().intValue(); i < equt.getStart().intValue() + equt.getLimit().intValue(); ++i)
        pointlist.add((PointInfoBean)lists.get(i));
    }
    else {
      for (int i = equt.getStart().intValue(); i < lists.size(); ++i) {
        pointlist.add((PointInfoBean)lists.get(i));
      }
    }

    if ((pointlist != null) && (pointlist.size() != 0)) {
      for (PointInfoBean point : pointlist) {
        PointInfoBean p = getOfpNodeSort(point);
        if (p.getPoints() != null) {
          EqutInfoBean e = new EqutInfoBean();
          e.setEid(((PointInfoBean)p.getPoints().get(0)).getEid());
          e = (EqutInfoBean)getObject("point.getEqutInfo", e);
          e = new EqutInfoBean();
          e.setEid(((PointInfoBean)p.getPoints().get(p.getPoints().size() - 1)).getEid());
          e = (EqutInfoBean)getObject("point.getEqutInfo", e);
        }
      }
    }
    pointInfoBean.setPoints(pointlist);
    pointInfoBean.setTotal(Integer.valueOf(lists.size()));
    return pointInfoBean;
  }

  public PointInfoBean getSubOFPCoderList(PointInfoBean pointInfoBean) throws DataAccessException
  {
    return null;
  }

  public List<PointEventInfoBean> getAlarmList(PointEventInfoBean pointEventInfoBean) throws DataAccessException {
    return getObjects("point.getAlarm", pointEventInfoBean);
  }

  public PointInfoBean getOFPOfOnuList(EqutInfoBean equt)
    throws DataAccessException
  {
    return null;
  }

  public PointInfoBean getOfpNodeSort(PointInfoBean pointInfoBean)
    throws DataAccessException
  {
    PointInfoBean pointInfo = new PointInfoBean();
    return pointInfo;
  }

  public void insertPointList(List<PointInfoBean> pointList) throws DataAccessException
  {
    batchInsert("point.insertPoint", pointList);
  }

  public void updateOsPointWithDoc(EqutInfoBean equt) throws DataAccessException
  {
    update("point.docUpdateOsPoint", equt);
  }

  public void insertJumpFiber(JumpFiberInfoBean fiber) throws DataAccessException
  {
    insert("point.insertFiber", fiber);
  }

  public EqutInfoBean getEqutInfo(EqutInfoBean equt) throws DataAccessException
  {
    EqutInfoBean e = (EqutInfoBean)getObject("point.getEqutInfo", equt);
    return e;
  }

  public UserInfoBean getUserInfo(UserInfoBean user) throws DataAccessException
  {
    UserInfoBean u = (UserInfoBean)getObject("point.getUserInfo", user);
    return u;
  }

  public List<EqutInfoBean> getOppoEqutList(PointInfoBean pointInfoBean) throws DataAccessException
  {
    List list = getObjects("point.getOppoEqutList", pointInfoBean);
    return list;
  }

  public void updatePointInfo2(List<PointInfoBean> points) throws DataAccessException
  {
  }

  public int getRouteCountByName(RouteInfoBean hsr)
    throws DataAccessException
  {
    return getCount("point.getRouteCountByName", hsr);
  }

  public int getCableCountByName(CableInfoBean hsc)
    throws DataAccessException
  {
    return getCount("point.getCableCountByName", hsc);
  }

  public void batchInsertCable(List<CableInfoBean> cableList)
    throws DataAccessException
  {
    batchInsert("point.insertCable", cableList);
  }

  public void batchInsertRoute(List<RouteInfoBean> routeList)
    throws DataAccessException
  {
    batchInsert("point.insertRoute", routeList);
  }

  public List<JumpFiberInfoBean> getJumpList(JumpFiberInfoBean jump) throws DataAccessException {
    return getObjects("point.getJumpFiberList", jump);
  }

  public PointInfoBean getPointLogList(PointInfoBean pointInfoBean) throws DataAccessException {
    List points = getObjects("point.getPointLogList", pointInfoBean);
    int total = getCount("point.getPointLogCount", pointInfoBean);
    PointInfoBean point = new PointInfoBean();
    point.setTotal(Integer.valueOf(total));
    point.setPoints(points);
    return point;
  }
}