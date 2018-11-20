package manage.point.service;

import base.exceptions.DataAccessException;
import java.util.List;
import manage.equt.pojo.EqutInfoBean;
import manage.fiber.pojo.JumpFiberInfoBean;
import manage.point.pojo.PointEventInfoBean;
import manage.point.pojo.PointInfoBean;
import manage.point.pojo.PointPstatBean;
import manage.route.pojo.CableInfoBean;
import manage.route.pojo.RouteInfoBean;
import manage.user.pojo.UserInfoBean;

public abstract interface PointInfoService
{
  public abstract PointInfoBean getPointInfoBean(PointInfoBean paramPointInfoBean)
    throws DataAccessException;

  public abstract List<PointInfoBean> getPointInfoList(PointInfoBean paramPointInfoBean)
    throws DataAccessException;

  public abstract void updatePointInfo(PointInfoBean paramPointInfoBean)
    throws DataAccessException;

  public abstract List<PointInfoBean> getListPointInfo(PointInfoBean paramPointInfoBean)
    throws DataAccessException;

  public abstract List<PointEventInfoBean> getEventList(PointEventInfoBean paramPointEventInfoBean)
    throws DataAccessException;

  public abstract List<PointPstatBean> getPointStatCount(PointInfoBean paramPointInfoBean)
    throws DataAccessException;

  public abstract List<PointEventInfoBean> getAlarmList(PointEventInfoBean paramPointEventInfoBean)
    throws DataAccessException;

  public abstract void insertPointList(List<PointInfoBean> paramList)
    throws DataAccessException;

  public abstract void updateOsPointWithDoc(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract void insertJumpFiber(JumpFiberInfoBean paramJumpFiberInfoBean)
    throws DataAccessException;

  public abstract PointInfoBean getOFPList(PointInfoBean paramPointInfoBean)
    throws DataAccessException;

  public abstract PointInfoBean getSubOFPList(PointInfoBean paramPointInfoBean)
    throws DataAccessException;

  public abstract PointInfoBean getOFPListport(PointInfoBean paramPointInfoBean)
    throws DataAccessException;

  public abstract PointInfoBean getOFPOfCodeList(PointInfoBean paramPointInfoBean)
    throws DataAccessException;

  public abstract PointInfoBean getSubOFPCoderList(PointInfoBean paramPointInfoBean)
    throws DataAccessException;

  public abstract PointInfoBean getOFPOfUserList(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract PointInfoBean getOFPOfOnuList(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract PointInfoBean getOfpNodeSort(PointInfoBean paramPointInfoBean)
    throws DataAccessException;

  public abstract EqutInfoBean getEqutInfo(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract UserInfoBean getUserInfo(UserInfoBean paramUserInfoBean)
    throws DataAccessException;

  public abstract List<EqutInfoBean> getOppoEqutList(PointInfoBean paramPointInfoBean)
    throws DataAccessException;

  public abstract void updatePointInfo2(List<PointInfoBean> paramList)
    throws DataAccessException;

  public abstract int getRouteCountByName(RouteInfoBean paramRouteInfoBean)
    throws DataAccessException;

  public abstract int getCableCountByName(CableInfoBean paramCableInfoBean)
    throws DataAccessException;

  public abstract void batchInsertRoute(List<RouteInfoBean> paramList)
    throws DataAccessException;

  public abstract void batchInsertCable(List<CableInfoBean> paramList)
    throws DataAccessException;

  public abstract List<JumpFiberInfoBean> getJumpList(JumpFiberInfoBean paramJumpFiberInfoBean)
    throws DataAccessException;

  public abstract PointInfoBean getPointLogList(PointInfoBean paramPointInfoBean)
    throws DataAccessException;
}

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.point.service.PointInfoService
 * JD-Core Version:    0.5.3
 */