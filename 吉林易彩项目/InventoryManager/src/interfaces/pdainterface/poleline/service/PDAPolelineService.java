package interfaces.pdainterface.poleline.service;

import base.exceptions.DataAccessException;

import java.util.List;

import manage.poleline.pojo.PoleInfoBean;
import manage.poleline.pojo.PolelineInfoBean;
import manage.poleline.pojo.PolelineSegmentInfoBean;
import manage.poleline.pojo.SupportInfoBean;
import manage.poleline.pojo.SusLocationBean;
import manage.poleline.pojo.SuspensionWireInfoBean;
import manage.poleline.pojo.SuspensionWireSegInfoBean;

public abstract interface PDAPolelineService
{
  public abstract List<PoleInfoBean> getPoleByIds(String paramString)
    throws DataAccessException;

  public abstract void updatePole(List<PoleInfoBean> paramList)
    throws DataAccessException;

  public abstract List<PoleInfoBean> getPole(PoleInfoBean paramPoleInfoBean)
    throws DataAccessException;

  public abstract Integer insertPole(PoleInfoBean paramPoleInfoBean)
    throws DataAccessException;

  public abstract Integer updatePole(PoleInfoBean paramPoleInfoBean)
    throws DataAccessException;

  public abstract Integer deletePole(PoleInfoBean paramPoleInfoBean)
    throws DataAccessException;

  public abstract List<PoleInfoBean> getPoleline(PolelineInfoBean paramPolelineInfoBean)
    throws DataAccessException;

  public abstract Integer insertPoleline(PolelineInfoBean paramPolelineInfoBean)
    throws DataAccessException;

  public abstract Integer updatePoleline(PolelineInfoBean paramPolelineInfoBean)
    throws DataAccessException;

  public abstract Integer deletePoleline(PolelineInfoBean paramPolelineInfoBean)
    throws DataAccessException;

  public abstract List<PolelineSegmentInfoBean> getPolelineSeg(PolelineSegmentInfoBean paramPolelineSegmentInfoBean)
    throws DataAccessException;

  public abstract Integer updatePolelineSeg(PolelineSegmentInfoBean paramPolelineSegmentInfoBean)
    throws DataAccessException;

  public abstract Integer insertPolelineSeg(PolelineSegmentInfoBean paramPolelineSegmentInfoBean)
    throws DataAccessException;

  public abstract Integer deletePolelineSeg(PolelineSegmentInfoBean paramPolelineSegmentInfoBean)
    throws DataAccessException;

  public abstract List<SupportInfoBean> getSupport(SupportInfoBean paramSupportInfoBean)
    throws DataAccessException;

  public abstract Integer insertSupport(SupportInfoBean paramSupportInfoBean)
    throws DataAccessException;

  public abstract Integer updateSupport(SupportInfoBean paramSupportInfoBean)
    throws DataAccessException;

  public abstract Integer deleteSupport(SupportInfoBean paramSupportInfoBean)
    throws DataAccessException;

  public abstract List<SuspensionWireInfoBean> getSuspensionWire(SuspensionWireInfoBean paramSuspensionWireInfoBean)
    throws DataAccessException;

  public abstract Integer insertSuspensionWire(SuspensionWireInfoBean paramSuspensionWireInfoBean)
    throws DataAccessException;

  public abstract Integer updateSuspensionWire(SuspensionWireInfoBean paramSuspensionWireInfoBean)
    throws DataAccessException;

  public abstract Integer deleteSuspensionWire(SuspensionWireInfoBean paramSuspensionWireInfoBean)
    throws DataAccessException;

  public abstract List<SuspensionWireSegInfoBean> getSuspensionseg(SuspensionWireSegInfoBean paramSuspensionWireSegInfoBean)
    throws DataAccessException;

  public abstract Integer insertSuspensionseg(SuspensionWireSegInfoBean paramSuspensionWireSegInfoBean)
    throws DataAccessException;

  public abstract Integer updateSuspensionseg(SuspensionWireSegInfoBean paramSuspensionWireSegInfoBean)
    throws DataAccessException;

  public abstract Integer deleteSuspensionseg(SuspensionWireSegInfoBean paramSuspensionWireSegInfoBean)
    throws DataAccessException;

  /**
   * 得到实体对象
   * @param polelineSeg
   * @return
   * @throws Exception
   */
  public PolelineSegmentInfoBean getPolelineSegObj(PolelineSegmentInfoBean polelineSeg) throws Exception;
  
  
  /**
   * 计算两个点的位置
   * @param obj
   * @return
   */
  public PolelineSegmentInfoBean setPoleSegDistince(PolelineSegmentInfoBean obj);
  
  
  /**
   * 得到电杆数据
   * @param pole
   * @return
   */
  public PoleInfoBean getPoleObj(Integer id);
  
  
  /**
   * 得到电杆是否
   * 存在敷设信息
   * @param obj
   * @return
   */
  public boolean getPoleLay(PoleInfoBean obj);
  
  
  /**
   * 得到敷设信息
   * @param obj
   * @return
   * @throws Exception
   */
  public PolelineSegmentInfoBean getPolelineLay(PolelineSegmentInfoBean obj) throws Exception;
  
  
  /**
   * 修改段的数据
   * @param poleId
   */
  public void upPloeLineSeg(String poleId);
  
  
  
  /**
   * 更新段信息
   * @param poleId
   * @param poleName
   */
  public void upPoleSeg(Integer poleId,String poleName);
}

