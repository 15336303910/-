 package manage.poleline.service.impl;
 
 import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.CommonUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import manage.pipe.pojo.PipeInfoBean;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.poleline.pojo.PoleInfoBean;
import manage.poleline.pojo.PolelineInfoBean;
import manage.poleline.pojo.PolelineSegmentInfoBean;
import manage.poleline.pojo.SupportInfoBean;
import manage.poleline.pojo.SuspensionWireInfoBean;
import manage.poleline.pojo.SuspensionWireSegInfoBean;
import manage.poleline.service.PolelineInfoService;
import manage.route.pojo.CableRouteInfoBean;
 
 public class PolelineInfoServiceImpl extends DataBase
   implements PolelineInfoService
 {
   private static String PolelineStatment = "poleline.getPoleline";
   private static String PolelineListStatment = "poleline.getPolelineList";
   private static String PolelineCountStatment = "poleline.getPolelineCount";
   private static String DeletePolelineStatment = "poleline.deletePoleline";
   private static String UpdatePolelineStatment = "poleline.updatePoleline";
   private static String UpdatePolelineStatment11 = "poleline.updatePoleline11";
   private static String UpdatePolelineStatment22 = "poleline.updatePoleline22";
   private static String UpdatePoleline1Statment = "poleline.updatePoleline1";
   private static String UpdatePoleline2Statment = "poleline.updatePoleline2";
   private static String InsertPolelineStatment = "poleline.insertPoleline";
   private static String PolelineSegmentListStatment = "poleline.getPolelineSegmentList";
   private static String PolelineSegmentStatment = "poleline.getPolelineSegment";
   private static String PolelineSegmentCountStatment = "poleline.getPolelineSegmentCount";
   private static String UpdatePolelineSegmentStatment = "poleline.updatePolelineSegment";
   private static String DeletePolelineSegmentStatment = "poleline.deletePolelineSegment";
   private static String InsertPolelineSegmentStatment = "poleline.insertPolelineSegment";
   private static String PoleStatment = "poleline.getPole";
   private static String PoleCountStatment = "poleline.getPoleCount";
   private static String PoleListStatment = "poleline.getPoleList";
   private static String UpdatePoleStatment = "poleline.updatePole";
   private static String DeletePoleStatment = "poleline.deletePole";
   private static String InsertPoleStatment = "poleline.insertPole";
   private static String UpdatePolelineSegment1Statment = "poleline.updatePolelineSegment1";
   private static String UpdatePolelineSegment2Statment = "poleline.updatePolelineSegment2";
   private static String PolelineListsStatment = "poleline.getPolelineLists";
   private static String UpdatePolelineSegAreaStatment = "poleline.updatePolelineSegmentArea";
   private static String GetPipeListStatment = "poleline.getPipeList";
   private static String GetPipeSegmentListStatment = "poleline.getPipeSegmentList";
   private static String UpdatePipeStatment11 = "poleline.updatePipe11";
   private static String UpdatePipeStatment22 = "poleline.updatePipe22";
   private static String UpdatePipeStatment1 = "poleline.updatePipe1";
   private static String UpdatePipeStatment2 = "poleline.updatePipe2";
   private static String UpdatePipeSegmentStatment11 = "poleline.updatePipeSegment11";
   private static String UpdatePipeSegmentStatment22 = "poleline.updatePipeSegment22";
   private static String UpdatePipeSegmentStatment1 = "poleline.updatePipeSegment1";
   private static String UpdatePipeSegmentStatment2 = "poleline.updatePipeSegment2";
   private static String UpdatePipeSegmentStatment = "poleline.updatePipeSegment";
   private static String GetPolelineSegmentListAll = "poleline.getPolelineSegmentListAll";
   private static String GetCableRouteListStatment = "poleline.getCableRouteList";
   private static String ADDPOLELS = "poleline.addPoleLS";
   private static String ADDPOLELSCOUNT = "poleline.addPoleLSCount";
   private static String POLEIDLIST = "poleline.poleIdList";
   private static String PoleOfPLStatment = "poleline.getPoleOfPL";
   private static String UpdatePoleImport = "poleline.updatePoleImport";
   private static String PoleImportStatment = "poleline.getPoleImport";
 
   private static String PoleLineLog = "log.insertPolelineLog";
   private static String PoleLog = "log.insertPoleLog";
   private static String PolelineSegmentLog = "log.insertPolelineSegmentLog";
   private static final String GET_SUPPORT = "poleline.getSupport";
   private static final String GET_SUSPENSION = "poleline.getSuspension";
   private static final String GET_SUSPENSIONSEG = "poleline.getSuspensionseg";
   private static final String GET_SUPPORT_COUNT = "poleline.getSupportCount";
   private static final String GET_SUSPENSION_COUNT = "poleline.getSuspensionCount";
   private static final String GET_SUSPENSIONSEG_COUNT = "poleline.getSuspensionsegCount";
   private static final String GET_POLE = "poleline.getPole";
 
   public PolelineInfoBean getPolelineList(PolelineInfoBean polelineInfo)
     throws DataAccessException
   {
     List lists = getObjects(PolelineListStatment, polelineInfo);
     int total = getCount(PolelineCountStatment, polelineInfo);
     PolelineInfoBean poleline = new PolelineInfoBean();
     poleline.setPolelines(lists);
     poleline.setTotal(Integer.valueOf(total));
     return poleline;
   }
 
   public PolelineInfoBean getPolelineInfoBean(PolelineInfoBean polelineInfo) throws DataAccessException {
     PolelineInfoBean polelineInfoBean = (PolelineInfoBean)getObject(PolelineStatment, polelineInfo);
     return polelineInfoBean;
   }
 
   public int deletePolelineInfoBean(PolelineInfoBean polelineInfoBean) throws DataAccessException {
     int i = delete(DeletePolelineStatment, polelineInfoBean);
     return i;
   }
 
   public int updatePolelineInfoBean(PolelineInfoBean polelineInfoBean) throws DataAccessException {
     int rows = update(UpdatePolelineStatment, polelineInfoBean);
     update(UpdatePolelineSegAreaStatment, polelineInfoBean);
     return rows;
   }
 
   public int insertPolelineInfoBean(PolelineInfoBean polelineInfoBean) throws DataAccessException {
     return ((Integer)insert(InsertPolelineStatment, polelineInfoBean)).intValue();
   }
 
   public PolelineSegmentInfoBean getPolelineSegmentList(PolelineSegmentInfoBean polelineSegmentInfoBean)
     throws DataAccessException
   {
     List <PolelineSegmentInfoBean> list = getObjects(PolelineSegmentListStatment, polelineSegmentInfoBean);
     List lists = new ArrayList();
     int total = getCount(PolelineSegmentCountStatment, polelineSegmentInfoBean);
     for (PolelineSegmentInfoBean p : list) {
       PoleInfoBean pole = new PoleInfoBean();
       pole.setPoleId(p.getStartDeviceId());
       pole = (PoleInfoBean)getObject(PoleStatment, pole);
       if (pole == null)
         p.setStartDeviceName("");
       else {
         p.setStartDeviceName(pole.getPoleNameSub());
       }
       pole = new PoleInfoBean();
       pole.setPoleId(p.getEndDeviceId());
       pole = (PoleInfoBean)getObject(PoleStatment, pole);
       if (pole == null)
         p.setEndDeviceName("");
       else {
         p.setEndDeviceName(pole.getPoleNameSub());
       }
       lists.add(p);
     }
     PolelineSegmentInfoBean polels = new PolelineSegmentInfoBean();
     polels.setPolelinesegments(lists);
     polels.setTotal(Integer.valueOf(total));
     return polels;
   }
 
   public PoleInfoBean getPoleInfoBean(PoleInfoBean poleInfo) throws DataAccessException
   {
     PoleInfoBean poleInfoBean = (PoleInfoBean)getObject(PoleStatment, poleInfo);
     return poleInfoBean;
   }
 
   /**
    * 验证杆路段信息
    */
   public PolelineSegmentInfoBean getPolelineSegmentInfoBean(PolelineSegmentInfoBean polelineSegmentInfo) throws DataAccessException
   {
     PolelineSegmentInfoBean polelineSegmentInfoBean = (PolelineSegmentInfoBean)getObject(PolelineSegmentStatment, polelineSegmentInfo);
     /*PoleInfoBean pole = new PoleInfoBean();
     pole.setPoleId(polelineSegmentInfoBean.getStartDeviceId());
     pole = (PoleInfoBean)getObject(PoleStatment, pole);
     if (pole != null)
       polelineSegmentInfoBean.setStartDeviceName(pole.getPoleNameSub());
     else {
       polelineSegmentInfoBean.setStartDeviceName("");
     }
     pole = new PoleInfoBean();
     pole.setPoleId(polelineSegmentInfoBean.getEndDeviceId());
     pole = (PoleInfoBean)getObject(PoleStatment, pole);
     if (pole != null)
       polelineSegmentInfoBean.setEndDeviceName(pole.getPoleNameSub());
     else {
       polelineSegmentInfoBean.setEndDeviceName("");
     }*/
     return polelineSegmentInfoBean;
   }
 
   public int updatePolelineSegmentInfoBean(PolelineSegmentInfoBean polelineSegmentInfoBean) throws DataAccessException
   {
     int i = update(UpdatePolelineSegmentStatment, polelineSegmentInfoBean);
     return i;
   }
 
   public int deletePolelineSegmentInfoBean(PolelineSegmentInfoBean polelineSegmentInfoBean) throws DataAccessException
   {
     int i = delete(DeletePolelineSegmentStatment, polelineSegmentInfoBean);
     return i; }
 
   public int updatePolelineSegmentInfoBean1(PolelineSegmentInfoBean polelineSegmentInfoBean) throws DataAccessException {
     int i = update(UpdatePolelineSegment1Statment, polelineSegmentInfoBean);
     return i; }
 
   public int updatePolelineSegmentInfoBean2(PolelineSegmentInfoBean polelineSegmentInfoBean) throws DataAccessException {
     int i = update(UpdatePolelineSegment2Statment, polelineSegmentInfoBean);
     return i;
   }
 
   public int insertPolelineSegmentInfoBean(PolelineSegmentInfoBean polelineSegmentInfoBean) throws DataAccessException
   {
     return ((Integer)insert(InsertPolelineSegmentStatment, polelineSegmentInfoBean)).intValue();
   }
 
   public PoleInfoBean getPoleList(PoleInfoBean poleInfoBean, int startPage, int pageSize) throws DataAccessException
   {
     List poles = getObjectsByPage(PoleListStatment, poleInfoBean, startPage, pageSize);
     int total = getCount(PoleCountStatment, poleInfoBean);
     PoleInfoBean pole = new PoleInfoBean();
     pole.setPoles(poles);
     pole.setTotal(Integer.valueOf(total));
     return pole;
   }
 
   public int deletePoleInfoBean(PoleInfoBean poleInfoBean) throws DataAccessException {
     int i = update(DeletePoleStatment, poleInfoBean);
     return i;
   }
 
   public int updatePoleInfoBean(PoleInfoBean poleInfoBean) throws DataAccessException {
     int i = update(UpdatePoleStatment, poleInfoBean);
     return i;
   }
 
   public int insertPoleInfoBean(PoleInfoBean poleInfoBean) throws DataAccessException {
     return ((Integer)insert(InsertPoleStatment, poleInfoBean)).intValue();
   }
 
   public int updatePolelineInfoBean1(PolelineInfoBean polelineInfoBean) throws DataAccessException {
     int i = update(UpdatePoleline1Statment, polelineInfoBean);
     return i;
   }
 
   public int updatePolelineInfoBean2(PolelineInfoBean polelineInfoBean) throws DataAccessException {
     int i = update(UpdatePoleline2Statment, polelineInfoBean);
     return i;
   }
 
   public List<PoleInfoBean> getPoleLists(PoleInfoBean poleInfoBean) throws DataAccessException {
     List lists = getObjects(PoleListStatment, poleInfoBean);
     return lists;
   }
 
   public List<PolelineInfoBean> getPolelineLists(PolelineInfoBean polelineInfoBean) throws DataAccessException {
     List pllist = getObjects(PolelineListsStatment, polelineInfoBean);
     return pllist;
   }
 
   public List<PipeInfoBean> getPipeList(PipeInfoBean pipeInfoBean) throws DataAccessException {
     List pipeList = getObjects(GetPipeListStatment, pipeInfoBean);
     return pipeList;
   }
 
   public List<PipeSegmentInfoBean> getPipeSegmentList(PipeSegmentInfoBean pipeSegmentInfoBean) throws DataAccessException {
     List pipeSegmentList = getObjects(GetPipeSegmentListStatment, pipeSegmentInfoBean);
     return pipeSegmentList;
   }
 
   public int updatePipeInfoBean1(PipeInfoBean pipeInfoBean) throws DataAccessException {
     return update(UpdatePipeStatment1, pipeInfoBean);
   }
 
   public int updatePipeInfoBean2(PipeInfoBean pipeInfoBean) throws DataAccessException {
     return update(UpdatePipeStatment2, pipeInfoBean);
   }
 
   public int updatePipeSegmentInfoBean1(PipeSegmentInfoBean pipeSegmentInfoBean) throws DataAccessException {
     return update(UpdatePipeSegmentStatment1, pipeSegmentInfoBean);
   }
 
   public int updatePipeSegmentInfoBean2(PipeSegmentInfoBean pipeSegmentInfoBean) throws DataAccessException {
     return update(UpdatePipeSegmentStatment2, pipeSegmentInfoBean);
   }
 
   public int updatePipeInfoBean11(PipeInfoBean pipeInfoBean) throws DataAccessException {
     return update(UpdatePipeStatment11, pipeInfoBean);
   }
 
   public int updatePipeInfoBean22(PipeInfoBean pipeInfoBean) throws DataAccessException {
     return update(UpdatePipeStatment22, pipeInfoBean);
   }
 
   public int updatePipeSegmentInfoBean11(PipeSegmentInfoBean pipeSegmentInfoBean) throws DataAccessException {
     return update(UpdatePipeSegmentStatment11, pipeSegmentInfoBean);
   }
 
   public int updatePipeSegmentInfoBean22(PipeSegmentInfoBean pipeSegmentInfoBean) throws DataAccessException {
     return update(UpdatePipeSegmentStatment22, pipeSegmentInfoBean);
   }
 
   public int updatePolelineInfoBean11(PolelineInfoBean polelineInfoBean) throws DataAccessException {
     return update(UpdatePolelineStatment11, polelineInfoBean);
   }
 
   public int updatePolelineInfoBean22(PolelineInfoBean polelineInfoBean) throws DataAccessException {
     return update(UpdatePolelineStatment22, polelineInfoBean);
   }
 
   public List<PolelineSegmentInfoBean> getPolelineSegmentListAll(PolelineSegmentInfoBean polelineSegmentInfoBean) throws DataAccessException
   {
     List List = getObjects(GetPolelineSegmentListAll, polelineSegmentInfoBean);
     return List;
   }
 
   public int updatePipeSegmentInfoBean(PipeSegmentInfoBean pipeSegmentInfoBean) throws DataAccessException {
     return update(UpdatePipeSegmentStatment, pipeSegmentInfoBean);
   }
 
   public List<CableRouteInfoBean> getCableRouteList(CableRouteInfoBean cableRouteInfoBean) throws DataAccessException {
     List cableRouteList = getObjects(GetCableRouteListStatment, cableRouteInfoBean);
     return cableRouteList;
   }
 
   public PoleInfoBean addPoleLS(PoleInfoBean poleInfoBean) throws DataAccessException {
     List list = getObjects(ADDPOLELS, poleInfoBean);
     int total = getCount(ADDPOLELSCOUNT, poleInfoBean);
     poleInfoBean = new PoleInfoBean();
     poleInfoBean.setTotal(Integer.valueOf(total));
     poleInfoBean.setPoles(list);
     return poleInfoBean;
   }
 
   public int getPoleIdList(PoleInfoBean poleInfoBean) throws DataAccessException {
     List  <PoleInfoBean>list = getObjects(POLEIDLIST, poleInfoBean);
 
     PolelineInfoBean poleline = new PolelineInfoBean();
     poleline.setPoleLineId(poleInfoBean.getPoleLineId());
 
     for (PoleInfoBean t : list) {
       t.setPoleLineId(poleline.getPoleLineId());
       update(UpdatePoleStatment, t);
       t.setLogTime(poleInfoBean.getLogTime());
       t.setLogOperater(poleInfoBean.getLogOperater());
       insert(PoleLog, t);
     }
 
     poleline = (PolelineInfoBean)getObject(PolelineListStatment, poleline);
 
     List pSegList = new ArrayList();
     int i;
     double lat1;
     for (int i1 = 0; i1 < list.size() - 1; ++i1) {
       PoleInfoBean pStart = (PoleInfoBean)list.get(i1);
       PoleInfoBean pEnd = (PoleInfoBean)list.get(i1 + 1);
       String name = poleline.getPoleLineName() + "(" + pStart.getPoleNameSub() + "_" + pEnd.getPoleNameSub() + ")";
 
       PolelineSegmentInfoBean pSegCheck = new PolelineSegmentInfoBean();
       pSegCheck.setPoleLineSegmentName(name);
       pSegCheck = (PolelineSegmentInfoBean)getObject(PolelineSegmentListStatment, pSegCheck);
       if (pSegCheck != null) {
         continue;
       }
       PolelineSegmentInfoBean pSeg = new PolelineSegmentInfoBean();
       pSeg.setPoleLineId(pStart.getPoleLineId());
       pSeg.setPoleLineSegmentName(name);
       pSeg.setStatus("2");
       pSeg.setStartDeviceId(pStart.getPoleId());
       pSeg.setEndDeviceId(pEnd.getPoleId());
 
       lat1 = 0.0D;
       double lon1 = 0.0D;
       double lat2 = 0.0D;
       double lon2 = 0.0D;
       try
       {
         lat1 = Double.parseDouble(pStart.getPoleLatitude());
         lon1 = Double.parseDouble(pStart.getPoleLongitude());
 
         lat2 = Double.parseDouble(pEnd.getPoleLatitude());
         lon2 = Double.parseDouble(pEnd.getPoleLongitude());
       } catch (Exception e) {
         e.printStackTrace();
         lat1 = 0.0D;
         lon1 = 0.0D;
       }
 
       if ((lat1 != 0.0D) && (lon1 != 0.0D) && (lat2 != 0.0D) && (lon2 != 0.0D)) {
         String dis = CommonUtil.GetDistance(lat1, lon1, lat2, lon2)+"";
         dis = dis.substring(0, dis.indexOf(46) + 2);
         pSeg.setPoleLineSegmentLength(dis);
       }
 
       pSeg.setCreationDate(new Date());
       pSeg.setLastUpdateDate(new Date());
       pSeg.setCstate("0");
       pSeg.setCuser(poleline.getCuser());
       pSeg.setAreano(poleline.getAreano());
       pSeg.setAreaname(poleline.getAreaname());
 
       pSegList.add(pSeg);
     }
 
     List i1 = batchInsertReturnIdStr(InsertPolelineSegmentStatment, pSegList);
     for (int s = 0; s < pSegList.size(); ++s) {
       PolelineSegmentInfoBean poleSeg = (PolelineSegmentInfoBean)pSegList.get(s);
       poleSeg.setPoleLineSegmentId((Integer)i1.get(s));
       poleSeg.setLogTime(new Date());
       poleSeg.setLogOperater(poleInfoBean.getLogOperater());
       insert(PolelineSegmentLog, poleSeg);
     }
 
     PolelineSegmentInfoBean ps = new PolelineSegmentInfoBean();
     ps.setPoleLineId(poleline.getPoleLineId());
     List <PolelineSegmentInfoBean> pslist = getObjects(PolelineSegmentStatment, ps);
 
     double allL = 0.0D;
 
     for (PolelineSegmentInfoBean thisP : pslist) {
       String l = thisP.getPoleLineSegmentLength();
       double length = 0.0D;
       try {
         length = Double.parseDouble(l);
       } catch (Exception e) {
         length = 0.0D;
       }
 
       allL += length;
     }
 
     PolelineInfoBean line = new PolelineInfoBean();
     line.setPoleLineId(poleline.getPoleLineId());
     line.setPoleLineLength(allL+"");
     update(UpdatePolelineStatment, line);
     line.setLogTime(new Date());
     line.setLogOperater(poleInfoBean.getLogOperater());
     insert(PoleLineLog, line);
     return 1;
   }
 
   public List<PoleInfoBean> getPoleListOfPL(PolelineInfoBean polelineInfoBean) throws DataAccessException {
     List poles = new ArrayList();
     PoleInfoBean p = new PoleInfoBean();
     p.setPoleLineId(polelineInfoBean.getPoleLineId());
     poles = getObjects(PoleOfPLStatment, p);
     return poles;
   }
 
   public void insertPoleList(List<PoleInfoBean> poles) throws DataAccessException {
     String user = ((PoleInfoBean)poles.get(0)).getLogOperater();
     List i = batchInsertReturnIdStr(InsertPoleStatment, poles);
     for (int s = 0; s < poles.size(); ++s) {
       PoleInfoBean pole = (PoleInfoBean)poles.get(s);
       pole.setPoleId((Integer)i.get(s));
       insert(PoleLog, pole); }
   }
 
   public List<PoleInfoBean> getPoleInfoBeanImport(PoleInfoBean poleInfo) throws DataAccessException {
     List poles = getObjects(PoleImportStatment, poleInfo);
     return poles;
   }
 
   public void insertPolelineSegments(List<PolelineSegmentInfoBean> polelineSegments) throws DataAccessException {
     String user = ((PolelineSegmentInfoBean)polelineSegments.get(0)).getLogOperater();
     List i = batchInsertReturnIdStr(InsertPolelineSegmentStatment, polelineSegments);
     for (int s = 0; s < i.size(); ++s) {
       PolelineSegmentInfoBean poleSeg = (PolelineSegmentInfoBean)polelineSegments.get(s);
       poleSeg.setLogOperater(user);
       poleSeg.setPoleLineSegmentId((Integer)i.get(s));
       insert(PolelineSegmentLog, poleSeg);
     }
   }
 
   public void updatePoleImport(PoleInfoBean p) throws DataAccessException {
     update(UpdatePoleImport, p);
   }
 
   public PoleInfoBean poleLog(PoleInfoBean pole)
     throws DataAccessException
   {
     insert(PoleLog, pole);
     return null;
   }
 
   public PolelineInfoBean polelineLog(PolelineInfoBean poleline) throws DataAccessException {
     insert(PoleLineLog, poleline);
     return null;
   }
 
   public PolelineSegmentInfoBean PolelineSegmentLog(PolelineSegmentInfoBean polelineSegmentInfoBean) throws DataAccessException
   {
     insert(PolelineSegmentLog, polelineSegmentInfoBean);
     return null;
   }
 
   public SuspensionWireInfoBean getSuspension(SuspensionWireInfoBean suspension) throws DataAccessException {
     List list = getObjects("poleline.getSuspension", suspension);
     int total = getCount("poleline.getSuspensionCount", suspension);
     suspension = new SuspensionWireInfoBean();
     suspension.setSuspensions(list);
     suspension.setTotal(Integer.valueOf(total));
     return suspension;
   }
 
   public SuspensionWireSegInfoBean getSuspensionseg(SuspensionWireSegInfoBean suspensionseg) throws DataAccessException {
     List <SuspensionWireSegInfoBean> list = getObjects("poleline.getSuspensionseg", suspensionseg);
     for (SuspensionWireSegInfoBean s : list) {
       if (s.getSuspensionWireId() != null) {
         SuspensionWireInfoBean suspension = new SuspensionWireInfoBean();
         suspension.setSuspensionWireId(s.getSuspensionWireId());
         suspension = (SuspensionWireInfoBean)getObject("poleline.getSuspension", suspension);
         if (suspension != null)
           s.setSuspensionWireName(suspension.getSuspensionWireName());
       }
       PoleInfoBean pole;
       Object support;
       if (s.getStartType().equals("电杆")) {
         pole = new PoleInfoBean();
         pole.setPoleId(s.getStartId());
         pole = (PoleInfoBean)getObject("poleline.getPole", pole);
         if (pole != null)
           s.setStartName(pole.getPoleNameSub());
       }
       else if (s.getStartType().equals("撑点")) {
         support = new SupportInfoBean();
         ((SupportInfoBean) support).setSupportId(s.getStartId());
         support = (SupportInfoBean)getObject("poleline.getSupport", support);
         if (support != null) {
           s.setStartName(((SupportInfoBean) support).getSupportName());
         }
       }
       if (s.getEndType().equals("电杆")) {
         support = new PoleInfoBean();
         ((PoleInfoBean) support).setPoleId(s.getEndId());
         support = (PoleInfoBean)getObject("poleline.getPole", support);
         if (support != null)
           s.setEndName(((PoleInfoBean) support).getPoleNameSub());
       }
       else if (s.getEndType().equals("撑点")) {
         support = new SupportInfoBean();
         ((SupportInfoBean) support).setSupportId(s.getEndId());
         support = (SupportInfoBean)getObject("poleline.getSupport", support);
         if (support != null) {
           s.setEndName(((SupportInfoBean) support).getSupportName());
         }
       }
     }
 
     int total = getCount("poleline.getSuspensionsegCount", suspensionseg);
     suspensionseg = new SuspensionWireSegInfoBean();
     suspensionseg.setSuspensionwiresegs(list);
     suspensionseg.setTotal(Integer.valueOf(total));
     return suspensionseg;
   }
 
   public SupportInfoBean getSupport(SupportInfoBean support) throws DataAccessException {
     List list = getObjects("poleline.getSupport", support);
     int total = getCount("poleline.getSupportCount", support);
     support = new SupportInfoBean();
     support.setSupports(list);
     support.setTotal(Integer.valueOf(total));
     return support;
   }
 }

