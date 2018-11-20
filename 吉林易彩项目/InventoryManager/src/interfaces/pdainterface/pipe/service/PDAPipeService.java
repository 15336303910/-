package interfaces.pdainterface.pipe.service;

import base.exceptions.DataAccessException;

import java.util.List;

import manage.pipe.pojo.FaceInfoBean;
import manage.pipe.pojo.LedupInfoBean;
import manage.pipe.pojo.PipeInfoBean;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.pipe.pojo.TubeInfoBean;
import manage.pipe.pojo.WellInfoBean;

public abstract interface PDAPipeService
{
  public abstract List<WellInfoBean> getWell(WellInfoBean paramWellInfoBean)
    throws DataAccessException;

  public abstract Integer insertWell(WellInfoBean paramWellInfoBean)
    throws DataAccessException;

  public abstract Integer updateWell(WellInfoBean paramWellInfoBean)
    throws DataAccessException;

  public abstract Integer deleteWell(WellInfoBean paramWellInfoBean)
    throws DataAccessException;

  public abstract List<PipeInfoBean> getPipe(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract Integer insertPipe(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract Integer updatePipe(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract Integer deletePipe(PipeInfoBean paramPipeInfoBean)
    throws DataAccessException;

  public abstract List<PipeSegmentInfoBean> getPipeseg(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract Integer insertPipeseg(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract Integer updatePipeseg(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract Integer deletePipeseg(PipeSegmentInfoBean paramPipeSegmentInfoBean)
    throws DataAccessException;

  public abstract List<FaceInfoBean> getFace(FaceInfoBean paramFaceInfoBean)
    throws DataAccessException;

  public abstract Integer insertFace(FaceInfoBean paramFaceInfoBean)
    throws DataAccessException;

  public abstract Integer updateFace(FaceInfoBean paramFaceInfoBean)
    throws DataAccessException;

  public abstract Integer deleteFace(FaceInfoBean paramFaceInfoBean)
    throws DataAccessException;

  public abstract List<TubeInfoBean> getTube(TubeInfoBean paramTubeInfoBean)
    throws DataAccessException;

  public abstract Integer insertTube(TubeInfoBean paramTubeInfoBean)
    throws DataAccessException;

  public abstract Integer updateTube(TubeInfoBean paramTubeInfoBean)
    throws DataAccessException;

  public abstract Integer deleteTube(TubeInfoBean paramTubeInfoBean)
    throws DataAccessException;

  public abstract List<LedupInfoBean> getLedup(LedupInfoBean paramLedupInfoBean)
    throws DataAccessException;

  public abstract Integer insertLedup(LedupInfoBean paramLedupInfoBean)
    throws DataAccessException;

  public abstract Integer updateLedup(LedupInfoBean paramLedupInfoBean)
    throws DataAccessException;

  public abstract Integer deleteLedup(LedupInfoBean paramLedupInfoBean)
    throws DataAccessException;
  
  /**
   * 得到实体对象
   * @param pipeseg
   * @return
   * @throws Exception
   */
  public PipeSegmentInfoBean getPipeSegObj(PipeSegmentInfoBean pipeseg) throws Exception;
  
  
  /**
   * 得到管道段长度
   * @param pipeseg
   * @return
   */
  public PipeSegmentInfoBean setPipeSegLength(PipeSegmentInfoBean pipeseg);
  
  
  /**
   * 
   * @param well
   * @return
   */
  public WellInfoBean getWellObj(WellInfoBean well);
  
  
  /**
   * 得到井的敷设
   * @param well
   * @return
   */
  public boolean getWellLay(WellInfoBean well);
  
  
  /**
   * 根据id得到井对象
   * @param wellId
   * @return
   */
  public WellInfoBean getWellByid(Integer wellId);
  
  
  /**
   * 得到敷设关系
   * @param tube
   * @return
   */
  public TubeInfoBean getTubeObj(TubeInfoBean tube);
  
  
  /**
   * 批量增加管孔
   * @param tube
   * @return
   */
  public List<TubeInfoBean> beatchTube(TubeInfoBean tube);
  
  
  /**
   * 批量增加子孔
   * @param list
   */
  public void beatchSubTube(List<TubeInfoBean> list,String rentFlag);
  
  
  /**
   * 根据井得到管孔
   * @param wellId
   * @return
   */
  public List<TubeInfoBean> getTubeBywell(String wellId);
  
  /**
   * 根据语句得到
   * 管孔信息
   * @param id
   * @param isFather
   * @return
   */
  public List<TubeInfoBean> getTubeList(String id,String isFather);
  
  
  /**
   * 执行下段的删除
   * @param wellId
   */
  public void delPipeSeg(String wellId);
  
  
  /**
   * 根据管道段得到管孔
   * @param segId
   * @return
   */
  public List<TubeInfoBean> getTubeByPipe(String segId);
  
  
  /**
   * 修改井后更改相应的段信息
   * @param wellId
   * @param wellName
   */
  public void upPipeSeg(Integer wellId,String wellName);
  
  
  /**
   * 删除相应的
   * 引上段数据
   * @param wellId
   */
  public void delLeadupStage(String wellId) ;
}
