 package interfaces.pdainterface.point.service.impl;
 
 import base.database.DataBase;
 import base.exceptions.DataAccessException;
 import base.util.CommonUtil;
 import interfaces.hwinterface.interfaceUtil.util.GetFibercode;
 import interfaces.pdainterface.point.service.PDAPointInfoService;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import manage.equt.pojo.EqutInfoBean;
 import manage.fiber.pojo.JumpFiberInfoBean;
 import manage.point.pojo.PointEventInfoBean;
 import manage.point.pojo.PointInfoBean;
 import manage.user.pojo.UserInfoBean;
 
 public class PDAPointInfoServiceImpl extends DataBase
   implements PDAPointInfoService
 {
   private static final String GET_POINT = "pdapoint.getPoint";
   private static final String GET_POINT_WITH_EQUT = "pdapoint.getPointWithEqut";
   private static final String UPDATE_POINT = "pdapoint.updatePoint";
   private static final String GET_NO_ALLOW_EQUT = "pdapoint.getNoAllowEqut";
   private static final String GET_JUMPFIBER_USE_EVENT = "pdapoint.getJumpFiberUseEvent";
   private static final String GET_POINT_LIST_COUNT = "pdapoint.getPointListCount";
   private static final String INSERT_POINT_EVENT = "pdapoint.insertPointEvent";
   private static final String INSERT_POINT_ALARM = "pdapoint.insertPointAlarm";
   private static final String UPDATE_POINT_EVENTSTATE = "pdapoint.updatePointEventState";
   private static final String INSERT_ISOLUTE_POINT_ALARM = "pdapoint.insertIsoluteAlarm";
   private static final String SAVE_POINT_SYNC = "pdapoint.savePointSync";
   private static final String GET_FIBER_USE_POINT = "pdapoint.getJumpFiberUsePoint";
   private static final String GET_EQUT = "pdapoint.getEqut";
   private static final String GET_EVENT = "pdapoint.getEvent";
   private static final String DELETE_ALARM = "pdapoint.deleteAlarm";
   private static final String UPDATE_EVENT_TO_OLD = "pdapoint.updateEventToOld";
   private static final String SET_POINT_DEL = "pdapoint.setPointDel";
   private static final String UPDATE_EQUT = "pdapoint.updateEqut";
   private static final String GET_POINT_IN_ODM = "pdapoint.getPointInOdm";
   private static final String GET_FIBER_USE_FIBERCODE = "pdapoint.getFiberUseFibercode";
   private static final String DELETE_JUMP_FIBER_BY_POINT = "pdapoint.delJumpFiberByPoint";
   private static final String INSERT_JUMP_FIBER = "pdapoint.insertJumpFiberInfo";
 
   public List<PointInfoBean> getPointInfoList(PointInfoBean pointInfoBean)
     throws DataAccessException
   {
     List<PointInfoBean> resultList = new ArrayList();
     return resultList;
   }
 
   public int getPointInfoCount(PointInfoBean pointInfoBean) throws DataAccessException
   {
     return getCount("pdapoint.getPointListCount", pointInfoBean);
   }
 
   public List<PointInfoBean> getPointInfoList(PointInfoBean pointInfoBean, int startpage, int pagesize) throws DataAccessException
   {
     startpage = (startpage - 1) * pagesize;
     List <PointInfoBean>resultList = getObjectsByPage("pdapoint.getPointWithEqut", pointInfoBean, startpage, pagesize);
     for (PointInfoBean p : resultList) {
       JumpFiberInfoBean j = (JumpFiberInfoBean)getObject("pdapoint.getJumpFiberUsePoint", p);
     }
     return resultList;
   }
 
   public void savePointSync(PointInfoBean pointInfoBean) throws DataAccessException
   {
     update("pdapoint.savePointSync", pointInfoBean);
   }
 
   public EqutInfoBean getEqutInfo(PointInfoBean pointInfoBean) throws DataAccessException
   {
     EqutInfoBean equtInfoBean = (EqutInfoBean)getObject("pdapoint.getEqut", pointInfoBean);
     return equtInfoBean;
   }
 
   public List<PointInfoBean> changeODM(PointInfoBean point, UserInfoBean tmp) {
     List pointList = null;
     EqutInfoBean equt = (EqutInfoBean)getObject("pdapoint.getEqut", point);
     PointInfoBean p;
     if (point.getDel().equals("0"))
     {
       p = new PointInfoBean();
       p.setEid(point.getEid());
       p.setPid(point.getPid());
       p.setMflag(Integer.valueOf(1));
       p.setDel("1");
       update("pdapoint.setPointDel", p);
     }
     else if (point.getDel().equals("1"))
     {
       p = new PointInfoBean();
       p.setEid(point.getEid());
       p.setPid(point.getPid());
       p.setMflag(Integer.valueOf(1));
       p.setDel("0");
       update("pdapoint.setPointDel", p);
 
       pointList = getObjects("pdapoint.getPointInOdm", p);
     }
     return pointList;
   }
 
   public void savePointFiber(JumpFiberInfoBean fiber, UserInfoBean user)
     throws DataAccessException
   {
     delete("pdapoint.delJumpFiberByPoint", fiber);
 
     String[] codes = new GetFibercode().getJumpFiberCode();
     fiber.setFibercode1(codes[0]);
     fiber.setFibercode2(codes[1]);
     fiber.setFiberstate("2");
     fiber.setCreater(user.getUsername());
     insert("pdapoint.insertJumpFiberInfo", fiber);
   }
 
   public void savePointOppo(PointInfoBean point, UserInfoBean user)
     throws DataAccessException
   {
   }
 }
