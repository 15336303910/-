package interfaces.pdainterface.route.service;

import base.exceptions.DataAccessException;
import interfaces.pdainterface.equt.pojo.EqutCableInfo;

import java.util.List;
import java.util.Map;

import manage.point.pojo.PointInfoBean;
import manage.route.pojo.BatchRackBean;
import manage.route.pojo.CableInfoBean;
import manage.route.pojo.FiberBoxInfoBean;
import manage.route.pojo.FiberInfoBean;
import manage.route.pojo.JointInfoBean;
import manage.route.pojo.RouteInfoBean;

public abstract interface PDARouteService
{
  public abstract List<RouteInfoBean> getRoute(RouteInfoBean paramRouteInfoBean)
    throws DataAccessException;

  public abstract Integer updateRoute(RouteInfoBean paramRouteInfoBean)
    throws DataAccessException;

  public abstract Integer insertRoute(RouteInfoBean paramRouteInfoBean)
    throws DataAccessException;

  public abstract List<CableInfoBean> getCable(CableInfoBean paramCableInfoBean)
    throws DataAccessException;

  public abstract Integer updateCable(CableInfoBean paramCableInfoBean)
    throws DataAccessException;

  public abstract Integer insertCable(CableInfoBean paramCableInfoBean)
    throws DataAccessException;

  public abstract Integer deleteCable(CableInfoBean paramCableInfoBean)
    throws DataAccessException;

  public abstract Integer delteRoute(RouteInfoBean paramRouteInfoBean)
    throws DataAccessException;

  public abstract List<JointInfoBean> getJoint(JointInfoBean paramJointInfoBean)
    throws DataAccessException;

  public abstract Integer insertJoint(JointInfoBean paramJointInfoBean)
    throws DataAccessException;

  public abstract Integer updateJoint(JointInfoBean paramJointInfoBean)
    throws DataAccessException;

  public abstract Integer deleteJoint(JointInfoBean paramJointInfoBean)
    throws DataAccessException;

  public abstract List<FiberBoxInfoBean> getFiberbox(FiberBoxInfoBean paramFiberBoxInfoBean)
    throws DataAccessException;

  public abstract Integer insertFiberbox(FiberBoxInfoBean paramFiberBoxInfoBean)
    throws DataAccessException;

  public abstract Integer updateFiberbox(FiberBoxInfoBean paramFiberBoxInfoBean)
    throws DataAccessException;

  public abstract Integer deleteFiberbox(FiberBoxInfoBean paramFiberBoxInfoBean)
    throws DataAccessException;
  
  /**
   * 得到纤芯的数据
   * @param fiber;
   * @return
   */
  public List<FiberInfoBean> getFiber(FiberInfoBean fiber);
  
  
  /**
   * 修改纤芯数据
   * @param fiber
   * @return
   */
  public Integer updateFiber(FiberInfoBean fiber);
  
  
  /**
   * 增加纤芯数据
   * @param fiber
   * @return
   */
  public Integer insertFiber(FiberInfoBean fiber);
  
  
  /**
   * 得到光缆段计算距离
   * @param cable
   * @return
   */
  public String getCableLength(CableInfoBean cable);
  
  /**
   * 得到光缆段信息
   * @param cable
   * @return
   */
  public CableInfoBean getCableObj(CableInfoBean cable);
  
  
  /**
   * 得到敷设的光缆段
   * @param ecable
   * @return
   */
  public List<CableInfoBean> getLayCable(EqutCableInfo ecable);
  
  
  /**
   * 根据纤芯的主键
   * 得到纤芯封装数据
   * @param fiber
   * @return
   */
  public FiberInfoBean getFiberObj(FiberInfoBean fiber);
  
  
  /**
   * 纤芯直接上架
   * @param type
   * @param cable
   */
  public void setCableRack(String type,CableInfoBean cable);
  
  
  /**
   * 根据光缆段得
   * 到纤芯数据
   * @param fiber
   * @return
   */
  public List<FiberInfoBean> getFiberList(FiberInfoBean fiber);
  
  /**
   * 得到纤芯数据
   * @param cableId
   * @param fiberIds
   * @return
   */
  public List<FiberInfoBean> getFiberList(BatchRackBean obj);
  
  /**
   * 批量修改端子的跳纤成端信息
   * @param pointList
   */
  public void batchPutPoint(List<PointInfoBean> pointList);
  
  /**
   * 批量更新
   * @param pointIds
   */
  public void batchPoint(String pointIds);
  
  /**
   * 批量纤芯成端
   * @param obj
   * @param list
   */
  public List<PointInfoBean> batchFiber(BatchRackBean obj ,List<FiberInfoBean> list);
  
}