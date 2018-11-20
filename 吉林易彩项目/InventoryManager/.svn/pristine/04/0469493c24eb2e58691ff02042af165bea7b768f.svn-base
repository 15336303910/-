 package manage.pipe.service.impl;
 
 import base.database.DataBase;
 import base.exceptions.DataAccessException;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import manage.pipe.pojo.FaceInfoBean;
 import manage.pipe.pojo.LedupInfoBean;
 import manage.pipe.pojo.PipeInfoBean;
 import manage.pipe.pojo.PipeSegmentInfoBean;
 import manage.pipe.pojo.TubeInfoBean;
 import manage.pipe.pojo.WellInfoBean;
 import manage.pipe.service.PipeInfoService;
 import manage.poleline.pojo.PoleInfoBean;
 import manage.route.pojo.CableRouteInfoBean;
 
 public class PipeInfoServiceImpl extends DataBase
   implements PipeInfoService
 {
   private static String PipeCountStatment = "pipe.getPipeCount";
   private static String PipeListStatment = "pipe.getPipeList";
   private static String PipeStatment = "pipe.getPipe";
   private static String UpdatePipeStatment = "pipe.updatePipe";
   private static String DeletePipeStatment = "pipe.deletePipe";
   private static String InsertPipeStatment = "pipe.insertPipe";
   private static String PipeSegmentCountStatment = "pipe.getPipeSegmentCount";
   private static String PipeSegmentListStatment = "pipe.getPipeSegmentList";
   private static String WellStatment = "pipe.getWell";
   private static String PipeSegmentStatment = "pipe.getPipeSegment";
   private static String UpdatePipeSegmentStatment = "pipe.updatePipeSegment";
   private static String DeletePipeSegmentStatment = "pipe.deletePipeSegment";
   private static String InsertPipeSegmentStatment = "pipe.insertPipeSegment";
   private static String TubeCountStatment = "pipe.getTubeCount";
   private static String TubeListStatment = "pipe.getTubeList";
   private static String TubeStatment = "pipe.getTube";
   private static String UpdateTubeStatment = "pipe.updateTube";
   private static String DeleteTubeStatment = "pipe.deleteTube";
   private static String InsertTube = "pipe.InsertTube";
   private static String WellCountStatment = "pipe.getWellCount";
   private static String WellListStatment = "pipe.getWellList";
   private static String PoleStatment = "pipe.getPole";
   private static String PoleCountStatment = "pipe.getPoleCount";
   private static String PoleListStatment = "pipe.getPoleList";
   private static String WellInfoBeanStatment = "pipe.getWellInfoBean";
   private static String UpdateWellStatment = "pipe.updateWell";
   private static String DeleteWellStatment = "pipe.deleteWell";
   private static String InsertWell = "pipe.insertWell";
   private static String updatePipeSegmentStart = "pipe.updatePipeSegmentStart";
   private static String updatePipeSegmentEnd = "pipe.updatePipeSegmentEnd";
   private static String updatePipeStart = "pipe.updatePipeStart";
   private static String updatePipeEnd = "pipe.updatePipeEnd";
   private static String LedupInfoBeanStatment = "pipe.getLedupInfoBean";
   private static String LedupInfoBeanCount = "pipe.getLedupCount";
 
   private static String Insertledup = "pipe.insertLedup";
   private static String UpdateledupInfoBean = "pipe.updateledupInfoBean";
   private static String TubeSonListStatment = "pipe.getTubeSonList";
   private static String TubeSonCountStatment = "pipe.getTubeSonCount";
   private static String InsertTubeInfoSon = "pipe.InsertTubeSon";
   private static String DeletePipesPipeSegment = "pipe.deletePipesPipeSegment";
   private static String LedupInfoBeanByIdStatment = "pipe.getLedupInfoBeanById";
   private static String UpdatePipeSegAndTubeAreaStatment = "pipe.updatePipeSegAndTubeArea";
   private static final String UpdatePipeSegAreaStatment = "pipe.updatePipeSegArea";
   private static String JbpipeStatment = "pipe.getJbCount";
   private static String JbpipeSegmentListStatment = "pipe.getJbList";
   private static String InsertJbPipe = "pipe.insertJbPipe";
   private static String UpdateJbPipeSegmentStatment = "pipe.updateJbPipeSegmentStatment";
   private static String UpdateEPipeinfoStart = "pipe.updateEPipeinfoStart";
   private static String UpdateEPipeinfoEnd = "pipe.updateEPipeinfoEnd";
   private static String UpdateEPipeSegmentinfoStart = "pipe.updateEPipeSegmentinfoStart";
   private static String UpdateEPipeSegmentinfoEnd = "pipe.updateEPipeSegmentinfoEnd";
   private static String GetPipeSegmentAll = "pipe.getPipeSegmentAll";
   private static String UpdatePipeSegmentInfoStatment = "pipe.updatePipeSegmentInfoBean";
   private static String DeleteLedupStatment = "pipe.deleteledup";
   private static String GetFaceRelationStatment = "pipe.getFaceRelation";
   private static String GetCableRouteListStatment = "pipe.getCableRouteList";
   private static String GetFaceInfoBeanStatment = "pipe.getFaceInfoBean";
 
   private static String GET_WELL_FACE = "pipe.getWellface";
   private static String GET_FACE_TUBE = "pipe.getFacetube";
 
   private static String DeleteFaceRelationStatment = "pipe.deleteFaceRelation";
   private static String InsertFaceRelation = "pipe.insertFaceRelation";
   private static String UpdateFaceStatment = "pipe.updateFace";
   private static String UpdateFaceDisTubeStatment = "pipe.updateFaceDisTube";
   private static final String GET_FACE = "pdaresourcecheck.getFace";
   private static final String UPDATE_FACE = "pdaresourcecheck.updateFace";
   private static final String INSERT_FACE = "pdaresourcecheck.insertFace";
   private static final String DELETE_FACE_RELATION = "pdaresourcecheck.deleteFaceRelation";
   private static final String INSERT_FACE_RELATION = "pdaresourcecheck.insertFaceRelation";
   private static final String GET_FACE_RELATION = "pdaresourcecheck.getFaceRelation";
   private static final String DELETE_CABLE_ROUTE = "pdaresourcecheck.deleteCableRoute";
   private static final String INSERT_CABLE_ROUTE = "pdaresourcecheck.insertCableRoute";
   private static final String GET_TUBE = "pdaresourcecheck.getTube";
   private static final String GET_CABLE_ROUTE = "pdaresourcecheck.getCableRoute";
   private static final String UPDATE_CABLE_ROUTE = "pdaresourcecheck.updateCableRoute";
   private static final String UPDATE_TUBE = "pdaresourcecheck.updateTube";
   private static String PipeLog = "log.insertPipeLog";
   private static String PipeSegmentLog = "log.insertPipeSegmentLog";
   private static String WellLog = "log.insertWellLog";
   private static String FaceLog = "log.insertFaceLog";
   private static String LedupLog = "log.insertLedupLog";
 
   public int getCount(PipeInfoBean pipeInfoBean) throws DataAccessException
   {
     return getCount(PipeCountStatment, pipeInfoBean);
   }
 
   public List<PipeInfoBean> getPipeList(PipeInfoBean pipeInfo, int startPage, int pageSize)
     throws DataAccessException
   {
     List resultList = getObjectsByPage(PipeListStatment, pipeInfo, startPage, pageSize);
     return resultList;
   }
 
   public PipeInfoBean getPipeList(PipeInfoBean pipeInfoBean) throws DataAccessException {
     List list = getObjects(PipeListStatment, pipeInfoBean);
     int total = getCount(PipeCountStatment, pipeInfoBean);
     PipeInfoBean pipe = new PipeInfoBean();
     pipe.setPipes(list);
     pipe.setTotal(Integer.valueOf(total));
     return pipe;
   }
 
   public PipeInfoBean getPipeInfoBean(PipeInfoBean pipeInfo) throws DataAccessException
   {
     PipeInfoBean pipeInfoBean = (PipeInfoBean)getObject(PipeStatment, pipeInfo);
     return pipeInfoBean;
   }
 
   public Integer updatePipeinfo(PipeInfoBean pipeInfo) throws DataAccessException
   {
     int rows = update(UpdatePipeStatment, pipeInfo);
 
     update(UpdatePipeSegAndTubeAreaStatment, pipeInfo);
 
     update("pipe.updatePipeSegArea", pipeInfo);
     return Integer.valueOf(rows);
   }
 
   public int deletePipeInfo(PipeInfoBean pipeInfo) throws DataAccessException
   {
     int i = update(DeletePipeStatment, pipeInfo);
     update(DeletePipesPipeSegment, pipeInfo);
     return i;
   }
 
   public int insertPipeInfo(PipeInfoBean pipeInfo) throws DataAccessException
   {
     return ((Integer)insert(InsertPipeStatment, pipeInfo)).intValue();
   }
 
   public List<PipeSegmentInfoBean> getPipeSegmentList(PipeSegmentInfoBean pipeSegmentInfo, int startPage, int pageSize) throws DataAccessException {
     List resultList = getObjectsByPage(PipeSegmentListStatment, pipeSegmentInfo, startPage, pageSize);
     return resultList;
   }
 
   public PipeSegmentInfoBean getPipeSegmentList(PipeSegmentInfoBean pipeSegmentInfoBean) throws DataAccessException {
     List list = getObjects(PipeSegmentListStatment, pipeSegmentInfoBean);
     int total = getCount(PipeSegmentCountStatment, pipeSegmentInfoBean);
     PipeSegmentInfoBean pipesegment = new PipeSegmentInfoBean();
     pipesegment.setPipesegments(list);
     pipesegment.setTotal(Integer.valueOf(total));
     return pipesegment;
   }
 
   public int getPipeSegmentCount(PipeSegmentInfoBean pipeSegmentInfo) throws DataAccessException
   {
     return getCount(PipeSegmentCountStatment, pipeSegmentInfo);
   }
 
   public WellInfoBean getWell(WellInfoBean wellInfo) throws DataAccessException {
     WellInfoBean wellInfoBean = (WellInfoBean)getObject(WellStatment, wellInfo);
     return wellInfoBean;
   }
 
   public PipeSegmentInfoBean getPipeSegmentInfoBean(PipeSegmentInfoBean pipeSegmentInfo) throws DataAccessException
   {
     PipeSegmentInfoBean pipeSegmentInfoBean = (PipeSegmentInfoBean)getObject(PipeSegmentStatment, pipeSegmentInfo);
     return pipeSegmentInfoBean;
   }
 
   public Integer updatePipeSegmentinfo(PipeSegmentInfoBean pipeSegmentInfo) throws DataAccessException
   {
     PipeSegmentInfoBean checkPSeg = new PipeSegmentInfoBean();
     checkPSeg.setPipeSegmentId(pipeSegmentInfo.getPipeSegmentId());
     checkPSeg = (PipeSegmentInfoBean)getObject(PipeSegmentStatment, checkPSeg);
     FaceInfoBean f;
     if ((checkPSeg != null) && (checkPSeg.getPipeSegmentType() != null)) {
       if (checkPSeg.getPipeSegmentType().equals("1")) {
         if ((checkPSeg.getStartDeviceId() != null) && (checkPSeg.getStartDeviceId().intValue() != 0)) {
           f = new FaceInfoBean();
           f.setWellId(checkPSeg.getStartDeviceId());
           f.setPipeSegmentId(checkPSeg.getPipeSegmentId()+"");
           f = (FaceInfoBean)getObject("pdaresourcecheck.getFace", f);
           if (f != null) {
             f.setPipeSegmentId("");
             update("pdaresourcecheck.updateFace", f);
             f.setLogTime(pipeSegmentInfo.getLogTime());
             f.setLogOperater(pipeSegmentInfo.getLogOperater());
             insert(FaceLog, f);
           }
         }
         if ((checkPSeg.getEndDeviceId() != null) && (checkPSeg.getEndDeviceId().intValue() != 0)) {
           f = new FaceInfoBean();
           f.setWellId(checkPSeg.getEndDeviceId());
           f.setPipeSegmentId(checkPSeg.getPipeSegmentId()+"");
           f = (FaceInfoBean)getObject("pdaresourcecheck.getFace", f);
           if (f != null) {
             f.setPipeSegmentId("");
             update("pdaresourcecheck.updateFace", f);
             f.setLogTime(pipeSegmentInfo.getLogTime());
             f.setLogOperater(pipeSegmentInfo.getLogOperater());
             insert(FaceLog, f);
           }
         }
       }
     } else if ((checkPSeg != null) && (checkPSeg.getPipeSegmentType().equals("2"))) {
       if ((checkPSeg.getStartDeviceId() != null) && (checkPSeg.getStartDeviceId().intValue() != 0)) {
         f = new FaceInfoBean();
         f.setWellId(checkPSeg.getStartDeviceId());
         f.setPipeSegmentId(checkPSeg.getPipeSegmentId()+"");
         f = (FaceInfoBean)getObject("pdaresourcecheck.getFace", f);
         if (f != null) {
           f.setPipeSegmentId("");
           update("pdaresourcecheck.updateFace", f);
           f.setLogTime(pipeSegmentInfo.getLogTime());
           f.setLogOperater(pipeSegmentInfo.getLogOperater());
           insert(FaceLog, f);
         }
       }
     } else if ((checkPSeg != null) && (checkPSeg.getPipeSegmentType().equals("3")) && 
       (checkPSeg.getEndDeviceId() != null) && (checkPSeg.getEndDeviceId().intValue() != 0)) {
       f = new FaceInfoBean();
       f.setWellId(checkPSeg.getEndDeviceId());
       f.setPipeSegmentId(checkPSeg.getPipeSegmentId()+"");
       f = (FaceInfoBean)getObject("pdaresourcecheck.getFace", f);
       if (f != null) {
         f.setPipeSegmentId("");
         update("pdaresourcecheck.updateFace", f);
         f.setLogTime(pipeSegmentInfo.getLogTime());
         f.setLogOperater(pipeSegmentInfo.getLogOperater());
         insert(FaceLog, f);
       }
     }
 
     int rows = update(UpdatePipeSegmentStatment, pipeSegmentInfo);
     int faceId1 = 0;
     int faceId2 = 0;
     FaceInfoBean f1;
     if ((pipeSegmentInfo.getStartFaceLocation() != null) && (!(pipeSegmentInfo.getStartFaceLocation().equals("")))) {
       String startFaceNo = pipeSegmentInfo.getStartFaceLocation();
       f1 = new FaceInfoBean();
       f1.setWellId(pipeSegmentInfo.getStartDeviceId());
       f1.setLocationNo(startFaceNo);
       f1 = (FaceInfoBean)getObject("pdaresourcecheck.getFace", f1);
       if (f1 != null) {
         faceId1 = f1.getFaceId().intValue();
         f1.setPipeSegmentId(pipeSegmentInfo.getPipeSegmentId()+"");
         update("pdaresourcecheck.updateFace", f1);
         f1.setLogTime(pipeSegmentInfo.getLogTime());
         f1.setLogOperater(pipeSegmentInfo.getLogOperater());
         insert(FaceLog, f1);
         delete("pdaresourcecheck.deleteFaceRelation", f1);
       }
     }
 
     if ((pipeSegmentInfo.getEndFaceLocation() != null) && (!(pipeSegmentInfo.getEndFaceLocation().equals("")))) {
       String endFaceNo = pipeSegmentInfo.getEndFaceLocation();
       f1 = new FaceInfoBean();
       f1.setWellId(pipeSegmentInfo.getEndDeviceId());
       f1.setLocationNo(endFaceNo);
       f1 = (FaceInfoBean)getObject("pdaresourcecheck.getFace", f1);
       if (f1 != null) {
         faceId2 = f1.getFaceId().intValue();
         f1.setPipeSegmentId(pipeSegmentInfo.getPipeSegmentId()+"");
         update("pdaresourcecheck.updateFace", f1);
         f1.setLogTime(pipeSegmentInfo.getLogTime());
         f1.setLogOperater(pipeSegmentInfo.getLogOperater());
         insert(FaceLog, f1);
         delete("pdaresourcecheck.deleteFaceRelation", f1);
       }
     }
     if ((pipeSegmentInfo.getStartFaceLocation() != null) && (!(pipeSegmentInfo.getStartFaceLocation().equals(""))) && (pipeSegmentInfo.getEndFaceLocation() != null) && (!(pipeSegmentInfo.getEndFaceLocation().equals("")))) {
       FaceInfoBean f2 = new FaceInfoBean();
       f2.setFaceId(Integer.valueOf(faceId1));
       f2.setOppoFaceId(Integer.valueOf(faceId2));
       insert("pdaresourcecheck.insertFaceRelation", f2);
     }
 
     return Integer.valueOf(rows);
   }
 
   public int deletePipeSegmentInfo(PipeSegmentInfoBean pipeSegmentInfo)
     throws DataAccessException
   {
     int i = delete(DeletePipeSegmentStatment, pipeSegmentInfo);
     return i;
   }
 
   public int insertPipeSegmentInfo(PipeSegmentInfoBean pipeSegmentInfo)
     throws DataAccessException
   {
     int pipeSegmentId = ((Integer)insert(InsertPipeSegmentStatment, pipeSegmentInfo)).intValue();
     if ((!(pipeSegmentInfo.getPipeSegmentType().equals("2"))) && (!(pipeSegmentInfo.getPipeSegmentType().equals("3")))) {
       String startFaceNo = pipeSegmentInfo.getStartFaceLocation();
       String endFaceNo = pipeSegmentInfo.getEndFaceLocation();
       int faceId1 = 0;
       int faceId2 = 0;
       FaceInfoBean f;
       if ((!(startFaceNo.equals(""))) && (startFaceNo != null)) {
         f = new FaceInfoBean();
         f.setWellId(pipeSegmentInfo.getStartDeviceId());
         f.setLocationNo(startFaceNo);
         f = (FaceInfoBean)getObject("pdaresourcecheck.getFace", f);
         if (f != null) {
           faceId1 = f.getFaceId().intValue();
           f.setPipeSegmentId((pipeSegmentId)+"");
           update("pdaresourcecheck.updateFace", f);
           f.setLogTime(pipeSegmentInfo.getLogTime());
           f.setLogOperater(pipeSegmentInfo.getLogOperater());
           insert(FaceLog, f);
           delete("pdaresourcecheck.deleteFaceRelation", f);
         }
       }
       if ((endFaceNo != null) && (!(endFaceNo.equals("")))) {
         f = new FaceInfoBean();
         f.setWellId(pipeSegmentInfo.getEndDeviceId());
         f.setLocationNo(endFaceNo);
         f = (FaceInfoBean)getObject("pdaresourcecheck.getFace", f);
         if (f != null) {
           faceId2 = f.getFaceId().intValue();
           f.setPipeSegmentId((pipeSegmentId)+"");
           update("pdaresourcecheck.updateFace", f);
           f.setLogTime(pipeSegmentInfo.getLogTime());
           f.setLogOperater(pipeSegmentInfo.getLogOperater());
           insert(FaceLog, f);
           delete("pdaresourcecheck.deleteFaceRelation", f);
         }
       }
       if ((!(startFaceNo.equals(""))) && (startFaceNo != null) && (endFaceNo != null) && (!(endFaceNo.equals("")))) {
         f = new FaceInfoBean();
         f.setFaceId(Integer.valueOf(faceId1));
         f.setOppoFaceId(Integer.valueOf(faceId2));
         insert("pdaresourcecheck.insertFaceRelation", f);
       }
     }
     return pipeSegmentId;
   }
 
   public int getTubeCount(TubeInfoBean tubeInfoBean)
     throws DataAccessException
   {
     return getCount(TubeCountStatment, tubeInfoBean);
   }
 
   public List<TubeInfoBean> getTubeList(TubeInfoBean tubeInfo, int startPage, int pageSize)
     throws DataAccessException
   {
     List<TubeInfoBean> resultList = getObjectsByPage(TubeListStatment, tubeInfo, startPage, pageSize);
     if (resultList != null) {
       for (TubeInfoBean t : resultList) {
         if (t.getPipeSegmentId() != null) {
           PipeSegmentInfoBean pipeSeg = new PipeSegmentInfoBean();
           pipeSeg.setPipeSegmentId(Integer.valueOf(Integer.parseInt(t.getPipeSegmentId())));
           pipeSeg = (PipeSegmentInfoBean)getObject(PipeSegmentListStatment, pipeSeg);
           if (pipeSeg != null) {
             t.setPipeSegmentName(pipeSeg.getPipeSegmentName());
             if (pipeSeg.getPipeId() != null) {
               PipeInfoBean pipe = new PipeInfoBean();
               pipe.setPipeId(pipeSeg.getPipeId());
               pipe = (PipeInfoBean)getObject(PipeListStatment, pipe);
               if (pipe != null) {
                 t.setPipeName(pipe.getPipeName());
               }
             }
           }
           if ((t.getWellId() != null) && (!(t.getWellId().equals("")))) {
             WellInfoBean w = new WellInfoBean();
             w.setWellId(Integer.valueOf(Integer.parseInt(t.getWellId())));
             w = (WellInfoBean)getObject(WellStatment, w);
             if (w != null) {
               t.setWellName(w.getWellName());
             }
           }
           if ((t.getFaceId() != null) && (!(t.getFaceId().equals("0")))) {
             FaceInfoBean f = new FaceInfoBean();
             f.setFaceId(Integer.valueOf(Integer.parseInt(t.getFaceId())));
             f = (FaceInfoBean)getObject("pdaresourcecheck.getFace", f);
             if (f != null) {
               t.setFaceNo(f.getLocationNo());
             }
           }
         }
       }
     }
     return resultList;
   }
 
   public TubeInfoBean getTubeInfoBean(TubeInfoBean tubeBean)
     throws DataAccessException
   {
     TubeInfoBean tubeInfoBean = (TubeInfoBean)getObject(TubeStatment, tubeBean);
     return tubeInfoBean;
   }
 
   public Integer updateTubeinfo(TubeInfoBean tubeInfoBean)
     throws DataAccessException
   {
     int rows = update(UpdateTubeStatment, tubeInfoBean);
 
     return Integer.valueOf(rows);
   }
 
   public int deleteTubeInfo(TubeInfoBean tubeInfoBean)
     throws DataAccessException
   {
     int i = delete(DeleteTubeStatment, tubeInfoBean);
     return i;
   }
 
   public void insertTubeInfo(TubeInfoBean tubeInfoBean)
     throws DataAccessException
   {
     insert(InsertTube, tubeInfoBean);
   }
 
   public int getWellCount(WellInfoBean wellInfoBean)
     throws DataAccessException
   {
     return getCount(WellCountStatment, wellInfoBean);
   }
 
   public List<WellInfoBean> getWellList(WellInfoBean wellInfo, int startPage, int pageSize)
     throws DataAccessException
   {
     List resultList = getObjectsByPage(WellListStatment, wellInfo, startPage, pageSize);
     return resultList;
   }
 
   public WellInfoBean getWellList(WellInfoBean wellInfoBean) throws DataAccessException {
     List list = getObjects(WellListStatment, wellInfoBean);
     int total = getCount(WellCountStatment, wellInfoBean);
     WellInfoBean well = new WellInfoBean();
     well.setWells(list);
     well.setTotal(Integer.valueOf(total));
     return well;
   }
 
   public PoleInfoBean getPoleInfoBean(PoleInfoBean poleInfo)
     throws DataAccessException
   {
     PoleInfoBean poleInfoBean = (PoleInfoBean)getObject(PoleStatment, poleInfo);
     return poleInfoBean;
   }
 
   public int getPoleCount(PoleInfoBean poleInfoBean)
     throws DataAccessException
   {
     int i = getCount(PoleCountStatment, poleInfoBean);
     return i;
   }
 
   public List<PoleInfoBean> getPoleList(PoleInfoBean poleInfoBean, int startPage, int pageSize)
     throws DataAccessException
   {
     List resultList2 = getObjectsByPage(PoleListStatment, poleInfoBean, startPage, pageSize);
     return resultList2;
   }
 
   public WellInfoBean getWellInfoBean(WellInfoBean wellInfo)
     throws DataAccessException
   {
     WellInfoBean wellInfoBean = (WellInfoBean)getObject(WellInfoBeanStatment, wellInfo);
     return wellInfoBean;
   }
 
   public Integer updateWellinfo(WellInfoBean wellInfoBean)
     throws DataAccessException
   {
     int rows = update(UpdateWellStatment, wellInfoBean);
 
     return Integer.valueOf(rows);
   }
 
   public int deleteWellInfo(WellInfoBean wellInfoBean)
     throws DataAccessException
   {
     int i = delete(DeleteWellStatment, wellInfoBean);
     FaceInfoBean face = new FaceInfoBean();
     face.setWellId(wellInfoBean.getWellId());
     List <FaceInfoBean>fsList = getObjects("pdaresourcecheck.getFace", face);
     TubeInfoBean to;
     CableRouteInfoBean cr1;
     for (FaceInfoBean fs : fsList) {
       face = new FaceInfoBean();
       face.setFaceId(fs.getFaceId());
       face = (FaceInfoBean)getObject("pdaresourcecheck.getFaceRelation", face);
 
       if (face != null) {
         FaceInfoBean oppof = new FaceInfoBean();
         if (face.getFaceId1().intValue() == fs.getFaceId().intValue())
           oppof.setFaceId(face.getFaceId2());
         else {
           oppof.setFaceId(face.getFaceId1());
         }
         oppof = (FaceInfoBean)getObject("pdaresourcecheck.getFace", oppof);
         to = new TubeInfoBean();
         to.setFaceId(oppof.getFaceId()+"");
         List <TubeInfoBean>ots = getObjects("pdaresourcecheck.getTube", to);
         for (TubeInfoBean ts : ots) {
           cr1 = new CableRouteInfoBean();
           cr1.setTubeId(ts.getTubeId());
           List <CableRouteInfoBean>crList = getObjects("pdaresourcecheck.getCableRoute", cr1);
           for (CableRouteInfoBean caru : crList) {
             caru.setDeletedFlag("1");
             caru.setDeletionDate(new Date());
 
             update("pdaresourcecheck.updateCableRoute", caru);
           }
         }
       }
 
       delete("pdaresourcecheck.deleteFaceRelation", fs);
       fs.setDeletedFlag("1");
 
       update("pdaresourcecheck.updateFace", fs);
     }
 
     TubeInfoBean t = new TubeInfoBean();
     t.setWellId(wellInfoBean.getWellId()+"");
     List <TubeInfoBean>tList = getObjects("pdaresourcecheck.getTube", t);
     for (TubeInfoBean ts : tList) {
       CableRouteInfoBean cr = new CableRouteInfoBean();
       cr.setTubeId(ts.getTubeId());
       List <CableRouteInfoBean>crList = getObjects("pdaresourcecheck.getCableRoute", cr);
       for (CableRouteInfoBean caru : crList) {
         caru.setDeletedFlag("1");
         caru.setDeletionDate(new Date());
 
         update("pdaresourcecheck.updateCableRoute", caru);
       }
 
       ts.setDeletedFlag("1");
       ts.setDeletionDate(new Date());
 
       update("pdaresourcecheck.updateTube", ts);
     }
 
     return i;
   }
 
   public int insertWellInfo(WellInfoBean wellInfoBean)
     throws DataAccessException
   {
     return ((Integer)insert(InsertWell, wellInfoBean)).intValue();
   }
 
   public Integer updatePipeSegmentInfoBean(PipeSegmentInfoBean pipeSegmentInfoBean)
     throws DataAccessException
   {
     int rows = update(UpdatePipeSegmentInfoStatment, pipeSegmentInfoBean);
 
     return Integer.valueOf(rows);
   }
 
   public Integer updatePipeSegmentinfoStart(PipeSegmentInfoBean pipeSegmentInfoBean) throws DataAccessException
   {
     int rows = update(updatePipeSegmentStart, pipeSegmentInfoBean);
 
     return Integer.valueOf(rows);
   }
 
   public Integer updatePipeSegmentinfoEnd(PipeSegmentInfoBean pipeSegmentInfoBean)
     throws DataAccessException
   {
     int rows = update(updatePipeSegmentEnd, pipeSegmentInfoBean);
 
     return Integer.valueOf(rows);
   }
 
   public Integer updatePipeinfoStart(PipeInfoBean pipeInfoBean)
     throws DataAccessException
   {
     int rows = update(updatePipeStart, pipeInfoBean);
 
     return Integer.valueOf(rows);
   }
 
   public Integer updatePipeinfoEnd(PipeInfoBean pipeInfoBean)
     throws DataAccessException
   {
     int rows = update(updatePipeEnd, pipeInfoBean);
 
     return Integer.valueOf(rows);
   }
 
   public LedupInfoBean getLedupInfoBean(LedupInfoBean ledupInfo)
     throws DataAccessException
   {
     List list = getObjects(LedupInfoBeanStatment, ledupInfo);
     int total = getCount(LedupInfoBeanCount, ledupInfo);
     ledupInfo = new LedupInfoBean();
     ledupInfo.setLedups(list);
     ledupInfo.setTotal(Integer.valueOf(total));
     return ledupInfo;
   }
 
   public Integer insertLedupInfoBean(LedupInfoBean ledupInfoBean)
     throws DataAccessException
   {
     return ((Integer)insert(Insertledup, ledupInfoBean));
   }
 
   public Integer updateLedupInfoBean(LedupInfoBean ledupInfoBean)
     throws DataAccessException
   {
     int rows = update(UpdateledupInfoBean, ledupInfoBean);
 
     return Integer.valueOf(rows);
   }
 
   public int getTubeSonCount(TubeInfoBean tubeInfoBean)
     throws DataAccessException
   {
     return getCount(TubeSonCountStatment, tubeInfoBean);
   }
 
   public List<TubeInfoBean> getTubeSonList(TubeInfoBean tubeInfo, int startPage, int pageSize)
     throws DataAccessException
   {
     List resultList = getObjectsByPage(TubeSonListStatment, tubeInfo, startPage, pageSize);
     return resultList;
   }
 
   public void insertTubeInfoSon(TubeInfoBean tubeInfoBean)
     throws DataAccessException
   {
     insert(InsertTubeInfoSon, tubeInfoBean);
   }
 
   public LedupInfoBean getLedupInfoBeanById(LedupInfoBean ledupInfo)
     throws DataAccessException
   {
     LedupInfoBean ledupInfoBean = (LedupInfoBean)getObject(LedupInfoBeanByIdStatment, ledupInfo);
     return ledupInfoBean;
   }
 
   public int getJbpipeSegmentCount(PipeSegmentInfoBean pipeSegmentInfoBean)
     throws DataAccessException
   {
     return getCount(JbpipeStatment, pipeSegmentInfoBean);
   }
 
   public List<PipeSegmentInfoBean> getJbpipeSegmentList(PipeSegmentInfoBean pipeSegmentInfo, int startPage, int pageSize)
     throws DataAccessException
   {
     List resultList = getObjectsByPage(JbpipeSegmentListStatment, pipeSegmentInfo, startPage, pageSize);
     return resultList;
   }
 
   public int insertPipeInfoBean(PipeInfoBean pipeInfoBean)
     throws DataAccessException
   {
     return ((Integer)insert(InsertJbPipe, pipeInfoBean)).intValue();
   }
 
   public Integer updateJbPipeSegmentinfo(PipeSegmentInfoBean pipeSegmentInfoBean)
     throws DataAccessException
   {
     int rows = update(UpdateJbPipeSegmentStatment, pipeSegmentInfoBean);
 
     return Integer.valueOf(rows);
   }
 
   public Integer updateEPipeinfoStart(PipeInfoBean pipeInfoBean)
     throws DataAccessException
   {
     int rows = update(UpdateEPipeinfoStart, pipeInfoBean);
 
     return Integer.valueOf(rows);
   }
 
   public Integer updateEPipeinfoEnd(PipeInfoBean pipeInfoBean)
     throws DataAccessException
   {
     int rows = update(UpdateEPipeinfoEnd, pipeInfoBean);
 
     return Integer.valueOf(rows);
   }
 
   public Integer updateEPipeSegmentinfoStart(PipeSegmentInfoBean pipeSegmentInfoBean)
     throws DataAccessException
   {
     int rows = update(UpdateEPipeSegmentinfoStart, pipeSegmentInfoBean);
 
     return Integer.valueOf(rows);
   }
 
   public Integer updateEPipeSegmentinfoEnd(PipeSegmentInfoBean pipeSegmentInfoBean)
     throws DataAccessException
   {
     int rows = update(UpdateEPipeSegmentinfoEnd, pipeSegmentInfoBean);
 
     return Integer.valueOf(rows);
   }
 
   public FaceInfoBean getFaceInfoBean(FaceInfoBean f) throws DataAccessException
   {
     return ((FaceInfoBean)getObject("pdaresourcecheck.getFace", f));
   }
 
   public List<PipeSegmentInfoBean> getPipeSegmentAll(PipeSegmentInfoBean pipeSegmentInfoBean)
     throws DataAccessException
   {
     List resultList = getObjects(GetPipeSegmentAll, pipeSegmentInfoBean);
 
     return resultList;
   }
 
   public Integer insertFaceInfo(FaceInfoBean faceInfoBean) throws DataAccessException
   {
     return ((Integer)insert("pdaresourcecheck.insertFace", faceInfoBean));
   }
 
   public Integer updateFaceInfo(FaceInfoBean faceInfoBean) throws DataAccessException
   {
     return Integer.valueOf(update("pdaresourcecheck.updateFace", faceInfoBean));
   }
 
   public List<TubeInfoBean> getTubeList(TubeInfoBean tubeInfoBean) throws DataAccessException
   {
     return getObjects(TubeStatment, tubeInfoBean);
   }
 
   public Integer getOppoFaceId(FaceInfoBean faceInfoBean) throws DataAccessException
   {
     FaceInfoBean fR = new FaceInfoBean();
     fR = (FaceInfoBean)getObject("pdaresourcecheck.getFaceRelation", faceInfoBean);
     if (fR != null) {
       if (fR.getFaceId1().intValue() == faceInfoBean.getFaceId().intValue()) {
         return fR.getFaceId2();
       }
       return fR.getFaceId1();
     }
 
     return null;
   }
 
   public int deleteLedupInfoBean(LedupInfoBean ledupInfoBean)
     throws DataAccessException
   {
     return delete(DeleteLedupStatment, ledupInfoBean);
   }
 
   public Boolean getFaceRelation(FaceInfoBean f)
     throws DataAccessException
   {
     FaceInfoBean facerelation = (FaceInfoBean)getObject(GetFaceRelationStatment, f);
     if (facerelation != null) {
       return Boolean.valueOf(true);
     }
     return Boolean.valueOf(false);
   }
 
   public List<CableRouteInfoBean> getCableRouteList(CableRouteInfoBean cableRouteInfoBean)
     throws DataAccessException
   {
     List list = getObjects(GetCableRouteListStatment, cableRouteInfoBean);
     return list;
   }
 
   public FaceInfoBean getFaceInfoBean1(FaceInfoBean f)
     throws DataAccessException
   {
     FaceInfoBean faceInfo = (FaceInfoBean)getObject(GetFaceInfoBeanStatment, f);
     return faceInfo;
   }
 
   public int deleteFaceRelation(FaceInfoBean f)
     throws DataAccessException
   {
     int row = delete(DeleteFaceRelationStatment, f);
     return row;
   }
 
   public WellInfoBean getWellfaceRelation(WellInfoBean wellInfo)
     throws DataAccessException
   {
     WellInfoBean w = (WellInfoBean)getObject(WellInfoBeanStatment, wellInfo);
     String fin = "";
     if (w != null) {
       String wellmap = w.getWellmap();
       if ((wellmap != null) && (!("".equals(wellmap))))
         for (int i = 0; i < 8; ++i) {
           char c = wellmap.charAt(i);
           if (c == '0') {
             fin = fin + "0";
           } else {
             FaceInfoBean f = new FaceInfoBean();
             f.setWellId(w.getWellId());
             f.setLocationNo((c)+"");
             f = (FaceInfoBean)getObject("pdaresourcecheck.getFace", f);
 
             if (f != null) {
               List fr = getObjects("pdaresourcecheck.getFaceRelation", f);
               if ((fr == null) || (fr.size() == 0))
                 fin = fin + "0";
               else
                 fin = fin + c;
             }
             else
             {
               fin = fin + "0";
             }
           }
         }
       else {
         fin = "00000000";
       }
       w.setHrwellmap(fin);
     }
     return w;
   }
 
   public List<WellInfoBean> getWellListOfPL(PipeInfoBean pipeInfoBean) throws DataAccessException
   {
     List wells = new ArrayList();
     PipeSegmentInfoBean ps = new PipeSegmentInfoBean();
     ps.setPipeId(pipeInfoBean.getPipeId());
     List plss = getObjects(PipeSegmentListStatment, ps);
     WellInfoBean w = new WellInfoBean();
     Integer startid = Integer.valueOf(0);
     Integer endid = Integer.valueOf(0);
     for (int i = 0; i < plss.size(); ++i) {
       if (((PipeSegmentInfoBean)plss.get(i)).getStartDeviceType().equals("1"))
         startid = ((PipeSegmentInfoBean)plss.get(i)).getStartDeviceId();
       else {
         startid = Integer.valueOf(0);
       }
       if (((PipeSegmentInfoBean)plss.get(i)).getEndDeviceType().equals("1"))
         endid = ((PipeSegmentInfoBean)plss.get(i)).getEndDeviceId();
       else {
         endid = Integer.valueOf(0);
       }
 
       if ((startid != null) && (startid.intValue() != 0)) {
         w = new WellInfoBean();
         w.setWellId(startid);
         w = (WellInfoBean)getObject(WellStatment, w);
         if ((w != null) && 
           (noHasWell(wells, w))) {
           wells.add(w);
         }
       }
 
       if ((endid != null) && (endid.intValue() != 0)) {
         w = new WellInfoBean();
         w.setWellId(endid);
         w = (WellInfoBean)getObject(WellStatment, w);
         if ((w == null) || 
           (!(noHasWell(wells, w)))) continue;
         wells.add(w);
       }
 
     }
 
     return wells;
   }
 
   public static boolean noHasWell(List<WellInfoBean> menus, WellInfoBean parent) {
     if (menus.isEmpty()) {
       return true;
     }
     for (int i = 0; i < menus.size(); ++i) {
       WellInfoBean m = (WellInfoBean)menus.get(i);
       if (m.getWellId().equals(parent.getWellId())) {
         return false;
       }
     }
 
     return true;
   }
 
   public void insertFaceRelation(FaceInfoBean f) throws DataAccessException {
     insert(InsertFaceRelation, f);
   }
 
   public Integer updateFace(FaceInfoBean f) throws DataAccessException {
     return Integer.valueOf(update(UpdateFaceStatment, f)); }
 
   public Integer updateFaceDisTube(FaceInfoBean f) throws DataAccessException {
     return Integer.valueOf(update(UpdateFaceDisTubeStatment, f));
   }
 
   public PipeInfoBean pipeLog(PipeInfoBean pipe)
     throws DataAccessException
   {
     insert(PipeLog, pipe);
     return null;
   }
 
   public PipeSegmentInfoBean pipesegmentLog(PipeSegmentInfoBean pipeSegment)
     throws DataAccessException
   {
     insert(PipeSegmentLog, pipeSegment);
     return null;
   }
 
   public WellInfoBean wellLog(WellInfoBean well)
     throws DataAccessException
   {
     insert(WellLog, well);
     return null;
   }
 
   public FaceInfoBean faceLog(FaceInfoBean face)
     throws DataAccessException
   {
     insert(FaceLog, face);
     return null;
   }
 
   public LedupInfoBean ledupLog(LedupInfoBean ledup)
     throws DataAccessException
   {
     insert(LedupLog, ledup);
     return null;
   }
 
   public FaceInfoBean getFace(FaceInfoBean face)
     throws DataAccessException
   {
     List <FaceInfoBean>list = getObjects(GET_WELL_FACE, face);
     List lists = new ArrayList();
     for (FaceInfoBean f : list) {
       f.setCols(Integer.valueOf(f.getCols().intValue() * f.getRows().intValue()));
       if ((f.getDisableTubes() != null) && (!(f.getDisableTubes().equals("")))) {
         f.setDisableTubes((f.getDisableTubes().split(",").length)+"");
       }
       lists.add(f);
     }
     face = new FaceInfoBean();
     face.setFaces(lists);
     return face;
   }
 
   public TubeInfoBean getFacetube(TubeInfoBean tube)
     throws DataAccessException
   {
     List list = getObjects(GET_FACE_TUBE, tube);
     TubeInfoBean tube1 = new TubeInfoBean();
     if (list.size() == 0) {
       tube1.setDuifaceId(tube.getFaceId());
       tube1.setDuiwellId(tube.getWellId());
       tube1.setIsFather(tube.getIsFather());
       List <TubeInfoBean>lists = getObjects(GET_FACE_TUBE, tube1);
       for (TubeInfoBean t : lists) {
         t.setWellId(t.getDuiwellId());
         t.setFaceId(t.getDuifaceId());
         t.setTubeName(t.getDuitubeName());
         t.setTubeNumber(t.getDuitubeNumber());
         list.add(t);
       }
     }
     tube = new TubeInfoBean();
     tube.setTubes(list);
     return tube;
   }
 }

