package manage.pipe.service;

import base.exceptions.DataAccessException;
import java.util.List;
import manage.pipe.pojo.FaceInfoBean;
import manage.pipe.pojo.LedupInfoBean;
import manage.pipe.pojo.PipeInfoBean;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.pipe.pojo.TubeInfoBean;
import manage.pipe.pojo.WellInfoBean;
import manage.poleline.pojo.PoleInfoBean;
import manage.route.pojo.CableRouteInfoBean;

public abstract interface PipeInfoService
{
  public abstract int getCount(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract List<PipeInfoBean> getPipeList(PipeInfoBean paramPipeInfoBean, int paramInt1, int paramInt2)
    throws DataAccessException;

  public abstract PipeInfoBean getPipeInfoBean(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract Integer updatePipeinfo(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract int deletePipeInfo(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract int insertPipeInfo(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract PipeInfoBean getPipeList(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract PipeSegmentInfoBean getPipeSegmentList(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract int getPipeSegmentCount(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract List<PipeSegmentInfoBean> getPipeSegmentList(PipeSegmentInfoBean paramPipeSegmentInfoBean, int paramInt1, int paramInt2)
    throws DataAccessException;

  public abstract WellInfoBean getWell(WellInfoBean paramWellInfoBean)
    throws DataAccessException;

  public abstract PipeSegmentInfoBean getPipeSegmentInfoBean(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract Integer updatePipeSegmentinfo(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract int deletePipeSegmentInfo(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract int insertPipeSegmentInfo(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract int getTubeCount(TubeInfoBean paramTubeInfoBean)
    throws DataAccessException;

  public abstract List<TubeInfoBean> getTubeList(TubeInfoBean paramTubeInfoBean, int paramInt1, int paramInt2)
    throws DataAccessException;

  public abstract TubeInfoBean getTubeInfoBean(TubeInfoBean paramTubeInfoBean)
    throws DataAccessException;

  public abstract Integer updateTubeinfo(TubeInfoBean paramTubeInfoBean)
    throws DataAccessException;

  public abstract int deleteTubeInfo(TubeInfoBean paramTubeInfoBean)
    throws DataAccessException;

  public abstract void insertTubeInfo(TubeInfoBean paramTubeInfoBean)
    throws DataAccessException;

  public abstract int getWellCount(WellInfoBean paramWellInfoBean)
    throws DataAccessException;

  public abstract List<WellInfoBean> getWellList(WellInfoBean paramWellInfoBean, int paramInt1, int paramInt2)
    throws DataAccessException;

  public abstract PoleInfoBean getPoleInfoBean(PoleInfoBean paramPoleInfoBean)
    throws DataAccessException;

  public abstract WellInfoBean getWellList(WellInfoBean paramWellInfoBean)
    throws DataAccessException;

  public abstract int getPoleCount(PoleInfoBean paramPoleInfoBean)
    throws DataAccessException;

  public abstract List<PoleInfoBean> getPoleList(PoleInfoBean paramPoleInfoBean, int paramInt1, int paramInt2)
    throws DataAccessException;

  public abstract WellInfoBean getWellInfoBean(WellInfoBean paramWellInfoBean)
    throws DataAccessException;

  public abstract Integer updateWellinfo(WellInfoBean paramWellInfoBean)
    throws DataAccessException;

  public abstract int deleteWellInfo(WellInfoBean paramWellInfoBean)
    throws DataAccessException;

  public abstract int insertWellInfo(WellInfoBean paramWellInfoBean)
    throws DataAccessException;

  public abstract Integer updatePipeSegmentinfoStart(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract Integer updatePipeSegmentinfoEnd(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract Integer updatePipeinfoStart(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract Integer updatePipeinfoEnd(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract LedupInfoBean getLedupInfoBean(LedupInfoBean paramLedupInfoBean)
    throws DataAccessException;

  public abstract Integer insertLedupInfoBean(LedupInfoBean paramLedupInfoBean)
    throws DataAccessException;

  public abstract Integer updateLedupInfoBean(LedupInfoBean paramLedupInfoBean)
    throws DataAccessException;

  public abstract int getTubeSonCount(TubeInfoBean paramTubeInfoBean)
    throws DataAccessException;

  public abstract List<TubeInfoBean> getTubeSonList(TubeInfoBean paramTubeInfoBean, int paramInt1, int paramInt2)
    throws DataAccessException;

  public abstract void insertTubeInfoSon(TubeInfoBean paramTubeInfoBean)
    throws DataAccessException;

  public abstract LedupInfoBean getLedupInfoBeanById(LedupInfoBean paramLedupInfoBean)
    throws DataAccessException;

  public abstract int deleteLedupInfoBean(LedupInfoBean paramLedupInfoBean)
    throws DataAccessException;

  public abstract int getJbpipeSegmentCount(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract List<PipeSegmentInfoBean> getJbpipeSegmentList(PipeSegmentInfoBean paramPipeSegmentInfoBean, int paramInt1, int paramInt2)
    throws DataAccessException;

  public abstract int insertPipeInfoBean(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract Integer updateJbPipeSegmentinfo(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract Integer updateEPipeinfoStart(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract Integer updateEPipeinfoEnd(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract Integer updatePipeSegmentInfoBean(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract Integer updateEPipeSegmentinfoStart(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract Integer updateEPipeSegmentinfoEnd(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract FaceInfoBean getFaceInfoBean(FaceInfoBean paramFaceInfoBean)
    throws DataAccessException;

  public abstract Boolean getFaceRelation(FaceInfoBean paramFaceInfoBean)
    throws DataAccessException;

  public abstract List<PipeSegmentInfoBean> getPipeSegmentAll(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract Integer insertFaceInfo(FaceInfoBean paramFaceInfoBean)
    throws DataAccessException;

  public abstract Integer updateFaceInfo(FaceInfoBean paramFaceInfoBean)
    throws DataAccessException;

  public abstract List<TubeInfoBean> getTubeList(TubeInfoBean paramTubeInfoBean)
    throws DataAccessException;

  public abstract Integer getOppoFaceId(FaceInfoBean paramFaceInfoBean)
    throws DataAccessException;

  public abstract List<CableRouteInfoBean> getCableRouteList(CableRouteInfoBean paramCableRouteInfoBean)
    throws DataAccessException;

  public abstract FaceInfoBean getFaceInfoBean1(FaceInfoBean paramFaceInfoBean)
    throws DataAccessException;

  public abstract int deleteFaceRelation(FaceInfoBean paramFaceInfoBean)
    throws DataAccessException;

  public abstract WellInfoBean getWellfaceRelation(WellInfoBean paramWellInfoBean)
    throws DataAccessException;

  public abstract void insertFaceRelation(FaceInfoBean paramFaceInfoBean)
    throws DataAccessException;

  public abstract Integer updateFace(FaceInfoBean paramFaceInfoBean)
    throws DataAccessException;

  public abstract Integer updateFaceDisTube(FaceInfoBean paramFaceInfoBean)
    throws DataAccessException;

  public abstract List<WellInfoBean> getWellListOfPL(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract PipeInfoBean pipeLog(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract PipeSegmentInfoBean pipesegmentLog(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract WellInfoBean wellLog(WellInfoBean paramWellInfoBean)
    throws DataAccessException;

  public abstract FaceInfoBean faceLog(FaceInfoBean paramFaceInfoBean)
    throws DataAccessException;

  public abstract LedupInfoBean ledupLog(LedupInfoBean paramLedupInfoBean)
    throws DataAccessException;

  public abstract FaceInfoBean getFace(FaceInfoBean paramFaceInfoBean)
    throws DataAccessException;

  public abstract TubeInfoBean getFacetube(TubeInfoBean paramTubeInfoBean)
    throws DataAccessException;
}

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.pipe.service.PipeInfoService
 * JD-Core Version:    0.5.3
 */