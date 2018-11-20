package manage.poleline.service;

import base.exceptions.DataAccessException;
import java.util.List;
import manage.pipe.pojo.PipeInfoBean;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.poleline.pojo.PoleInfoBean;
import manage.poleline.pojo.PolelineInfoBean;
import manage.poleline.pojo.PolelineSegmentInfoBean;
import manage.poleline.pojo.SupportInfoBean;
import manage.poleline.pojo.SuspensionWireInfoBean;
import manage.poleline.pojo.SuspensionWireSegInfoBean;
import manage.route.pojo.CableRouteInfoBean;

public abstract interface PolelineInfoService
{
  public abstract PolelineInfoBean getPolelineInfoBean(PolelineInfoBean paramPolelineInfoBean)
    throws DataAccessException;

  public abstract int deletePolelineInfoBean(PolelineInfoBean paramPolelineInfoBean)
    throws DataAccessException;

  public abstract int updatePolelineInfoBean(PolelineInfoBean paramPolelineInfoBean)
    throws DataAccessException;

  public abstract int updatePolelineInfoBean11(PolelineInfoBean paramPolelineInfoBean)
    throws DataAccessException;

  public abstract int updatePolelineInfoBean22(PolelineInfoBean paramPolelineInfoBean)
    throws DataAccessException;

  public abstract int updatePolelineInfoBean1(PolelineInfoBean paramPolelineInfoBean)
    throws DataAccessException;

  public abstract int updatePolelineInfoBean2(PolelineInfoBean paramPolelineInfoBean)
    throws DataAccessException;

  public abstract int insertPolelineInfoBean(PolelineInfoBean paramPolelineInfoBean)
    throws DataAccessException;

  public abstract List<PolelineInfoBean> getPolelineLists(PolelineInfoBean paramPolelineInfoBean)
    throws DataAccessException;

  public abstract PolelineInfoBean getPolelineList(PolelineInfoBean paramPolelineInfoBean)
    throws DataAccessException;

  public abstract PolelineSegmentInfoBean getPolelineSegmentList(PolelineSegmentInfoBean paramPolelineSegmentInfoBean)
    throws DataAccessException;

  public abstract PolelineSegmentInfoBean getPolelineSegmentInfoBean(PolelineSegmentInfoBean paramPolelineSegmentInfoBean)
    throws DataAccessException;

  public abstract int updatePolelineSegmentInfoBean(PolelineSegmentInfoBean paramPolelineSegmentInfoBean)
    throws DataAccessException;

  public abstract int updatePolelineSegmentInfoBean1(PolelineSegmentInfoBean paramPolelineSegmentInfoBean)
    throws DataAccessException;

  public abstract int updatePolelineSegmentInfoBean2(PolelineSegmentInfoBean paramPolelineSegmentInfoBean)
    throws DataAccessException;

  public abstract int deletePolelineSegmentInfoBean(PolelineSegmentInfoBean paramPolelineSegmentInfoBean)
    throws DataAccessException;

  public abstract int insertPolelineSegmentInfoBean(PolelineSegmentInfoBean paramPolelineSegmentInfoBean)
    throws DataAccessException;

  public abstract PoleInfoBean getPoleInfoBean(PoleInfoBean paramPoleInfoBean)
    throws DataAccessException;

  public abstract int updatePoleInfoBean(PoleInfoBean paramPoleInfoBean)
    throws DataAccessException;

  public abstract int deletePoleInfoBean(PoleInfoBean paramPoleInfoBean)
    throws DataAccessException;

  public abstract List<PoleInfoBean> getPoleLists(PoleInfoBean paramPoleInfoBean)
    throws DataAccessException;

  public abstract PoleInfoBean getPoleList(PoleInfoBean paramPoleInfoBean, int paramInt1, int paramInt2)
    throws DataAccessException;

  public abstract int insertPoleInfoBean(PoleInfoBean paramPoleInfoBean)
    throws DataAccessException;

  public abstract List<PipeInfoBean> getPipeList(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract int updatePipeSegmentInfoBean(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract int updatePipeInfoBean11(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract int updatePipeInfoBean22(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract int updatePipeInfoBean1(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract int updatePipeInfoBean2(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract List<PipeSegmentInfoBean> getPipeSegmentList(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract int updatePipeSegmentInfoBean11(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract int updatePipeSegmentInfoBean22(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract int updatePipeSegmentInfoBean1(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract int updatePipeSegmentInfoBean2(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract List<PolelineSegmentInfoBean> getPolelineSegmentListAll(PolelineSegmentInfoBean paramPolelineSegmentInfoBean)
    throws DataAccessException;

  public abstract List<CableRouteInfoBean> getCableRouteList(CableRouteInfoBean paramCableRouteInfoBean)
    throws DataAccessException;

  public abstract PoleInfoBean addPoleLS(PoleInfoBean paramPoleInfoBean)
    throws DataAccessException;

  public abstract int getPoleIdList(PoleInfoBean paramPoleInfoBean)
    throws DataAccessException;

  public abstract List<PoleInfoBean> getPoleListOfPL(PolelineInfoBean paramPolelineInfoBean)
    throws DataAccessException;

  public abstract void insertPoleList(List<PoleInfoBean> paramList)
    throws DataAccessException;

  public abstract List<PoleInfoBean> getPoleInfoBeanImport(PoleInfoBean paramPoleInfoBean)
    throws DataAccessException;

  public abstract void updatePoleImport(PoleInfoBean paramPoleInfoBean)
    throws DataAccessException;

  public abstract void insertPolelineSegments(List<PolelineSegmentInfoBean> paramList)
    throws DataAccessException;

  public abstract PoleInfoBean poleLog(PoleInfoBean paramPoleInfoBean)
    throws DataAccessException;

  public abstract PolelineInfoBean polelineLog(PolelineInfoBean paramPolelineInfoBean)
    throws DataAccessException;

  public abstract PolelineSegmentInfoBean PolelineSegmentLog(PolelineSegmentInfoBean paramPolelineSegmentInfoBean)
    throws DataAccessException;

  public abstract SuspensionWireInfoBean getSuspension(SuspensionWireInfoBean paramSuspensionWireInfoBean)
    throws DataAccessException;

  public abstract SuspensionWireSegInfoBean getSuspensionseg(SuspensionWireSegInfoBean paramSuspensionWireSegInfoBean)
    throws DataAccessException;

  public abstract SupportInfoBean getSupport(SupportInfoBean paramSupportInfoBean)
    throws DataAccessException;
}

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.poleline.service.PolelineInfoService
 * JD-Core Version:    0.5.3
 */