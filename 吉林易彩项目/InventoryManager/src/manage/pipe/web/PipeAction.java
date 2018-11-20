 package manage.pipe.web;
 
 import base.exceptions.DataAccessException;
 import base.util.CommonUtil;
 import base.util.ErrorMessage;
 import base.util.FileToZip;
 import base.util.PositionUtil;
 import base.web.PaginationAction;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.PrintWriter;
 import java.net.URLDecoder;
 import java.net.URLEncoder;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.Iterator;
 import java.util.List;
 import javax.servlet.ServletContext;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import manage.equt.web.EqutAction;
 import manage.pipe.pojo.FaceInfoBean;
 import manage.pipe.pojo.LedupInfoBean;
 import manage.pipe.pojo.PipeInfoBean;
 import manage.pipe.pojo.PipeSegmentInfoBean;
 import manage.pipe.pojo.TubeInfoBean;
 import manage.pipe.pojo.WellInfoBean;
 import manage.pipe.service.PipeInfoService;
 import manage.poleline.pojo.PoleInfoBean;
 import manage.route.pojo.CableRouteInfoBean;
 import manage.user.pojo.UserInfoBean;
 import net.sf.json.JSONArray;
 import org.apache.log4j.Logger;
 import org.apache.poi.hssf.usermodel.HSSFCell;
 import org.apache.poi.hssf.usermodel.HSSFCellStyle;
 import org.apache.poi.hssf.usermodel.HSSFFont;
 import org.apache.poi.hssf.usermodel.HSSFRow;
 import org.apache.poi.hssf.usermodel.HSSFSheet;
 import org.apache.poi.hssf.usermodel.HSSFWorkbook;
 import org.apache.poi.poifs.filesystem.POIFSFileSystem;
 import org.apache.poi.ss.usermodel.IndexedColors;
 import org.apache.struts2.ServletActionContext;
 
 public class PipeAction extends PaginationAction
 {
   private UserInfoBean userInfoBean;
   private PipeInfoBean pipeInfoBean;
   private PipeInfoService pipeInfoService;
   public static final String MODULENAME = "pipemanage";
   private List<PipeInfoBean> resultList;
   private List<PipeSegmentInfoBean> segresultList;
   private List<TubeInfoBean> tuberesultList;
   private List<WellInfoBean> wellresultList;
   private List<PoleInfoBean> poleresultList;
   private ErrorMessage errorMessage;
   private static final Logger log = Logger.getLogger(EqutAction.class);
   private Integer pipeId;
   private Integer pipeSegmentId;
   private Integer tubeId;
   private PipeSegmentInfoBean pipeSegmentInfoBean;
   private WellInfoBean wellInfoBean;
   private TubeInfoBean tubeInfoBean;
   private String wellAddress;
   private String wellName;
   private String manholeTypeEnumId;
   private PoleInfoBean poleInfoBean;
   private String startDeviceName;
   private String endDeviceName;
   private String poleAddress;
   private String poleName;
   private Integer fatherPoreId;
   private Integer wellId;
   private String pipeSegmentType;
   private Integer startDeviceId;
   private Integer endDeviceId;
   private LedupInfoBean ledupInfoBean;
   private Integer fatherId;
   private TubeInfoBean fatherTube;
   private String strPipe;
   private String strPipeStement;
   private String pipeSegmentName;
   private String pipeName;
   private String pipeLevel;
   private Boolean flag;
   private String tubeName;
   private String c_id;
   private String c_receivername;
   private Integer eid;
   private String areaname;
   private String[] startFaceArray;
   private String[] endFaceArray;
   private String startFaceNo;
   private String endFaceNo;
   private String mapstr;
   private String heng;
   private String shu;
   private String num;
   private String cname;
   private String cdirection;
   private Integer cwellNo;
   private Integer cwellSubNo;
   private Integer poleId;
   private String locationNo;
   private CableRouteInfoBean cableRouteInfoBean;
   private List<CableRouteInfoBean> cableRouteList;
   private String facebasic;
   private String direction;
   private String wellNo;
   private String wellSubNo;
   private String str1;
   private String str2;
   private WellInfoBean wellInfo;
   private FaceInfoBean faceInfo;
   private String faceId;
   private Integer limit;
   private Integer start;
   private List<String> ids;
   private boolean success;
   private String deleteMsg;
   private String faceIds;
   private String pipeids;
   private String filepath;
 
   public String list()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       if (this.pipeInfoBean == null) {
         this.pipeInfoBean = new PipeInfoBean();
       }
       this.pipeInfoBean.setLimit(this.limit);
       this.pipeInfoBean.setStart(this.start);
       this.pipeInfoBean = this.pipeInfoService.getPipeList(this.pipeInfoBean);
       return "pipeInfoBean";
     } catch (DataAccessException e) {
       log.error("PipeAction.list 获取信息异常...", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String welllist()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       if (this.wellInfoBean == null) {
         this.wellInfoBean = new WellInfoBean();
       }
       this.wellInfoBean.setStart(this.start);
       this.wellInfoBean.setLimit(this.limit);
       this.wellInfoBean = this.pipeInfoService.getWellList(this.wellInfoBean);
       return "wellInfoBean";
     } catch (DataAccessException e) {
       log.error("PipetAction.welllist 获取信息异常...", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String search() throws Exception
   {
     try {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
 
       setTotalCount(this.pipeInfoService.getCount(this.pipeInfoBean));
       int startPage = getStartPage();
       this.resultList = this.pipeInfoService.getPipeList(this.pipeInfoBean, startPage, this.pageSize);
       if (this.resultList != null) {
         for (int i = 0; i < this.resultList.size(); ++i) {
           PipeInfoBean pipeInfo = (PipeInfoBean)this.resultList.get(i);
           if ((pipeInfo == null) || 
             (pipeInfo.getPipeSegmentType() == null) || ("".equals(pipeInfo.getPipeSegmentType()))) continue;
           Integer type = Integer.valueOf(Integer.parseInt(pipeInfo.getPipeSegmentType()));
         }
 
       }
 
       return "success";
     } catch (DataAccessException e) {
       log.error("EqutAction.search", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String searchPipeSegmentlist()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       if (this.pipeSegmentInfoBean == null) {
         this.pipeSegmentInfoBean = new PipeSegmentInfoBean();
       }
       this.pipeSegmentInfoBean.setLimit(this.limit);
       this.pipeSegmentInfoBean.setStart(this.start);
       this.pipeSegmentInfoBean = this.pipeInfoService.getPipeSegmentList(this.pipeSegmentInfoBean);
       return "pipeSegmentInfoBean";
     } catch (DataAccessException e) {
       log.error("PipeAction.searchPipeSegmentlist", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String editpipe()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.pipeInfoBean = new PipeInfoBean();
       this.pipeInfoBean.setPipeId(Integer.valueOf(((String)this.ids.get(0)).trim()));
       this.pipeInfoBean = this.pipeInfoService.getPipeInfoBean(this.pipeInfoBean);
       if (this.pipeInfoBean != null)
         this.success = true;
       else {
         this.success = false;
       }
       return "loadPipe";
     } catch (DataAccessException e) {
       log.error("EqutAction.editequt", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       return "error";
     } catch (Exception e) {
       log.error("EqutAction.editequt", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String editpipeSegment()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.pipeSegmentInfoBean = new PipeSegmentInfoBean();
       this.pipeSegmentInfoBean.setPipeSegmentId(Integer.valueOf(((String)this.ids.get(0)).trim()));
       this.pipeSegmentInfoBean = this.pipeInfoService.getPipeSegmentInfoBean(this.pipeSegmentInfoBean);
       if (this.pipeSegmentInfoBean != null)
         this.success = true;
       else {
         this.success = false;
       }
       this.startDeviceName = this.pipeSegmentInfoBean.getStartDeviceName();
       this.endDeviceName = this.pipeSegmentInfoBean.getEndDeviceName();
       if (this.pipeSegmentInfoBean.getPipeId() != null) {
         this.pipeInfoBean = new PipeInfoBean();
         this.pipeInfoBean.setPipeId(this.pipeSegmentInfoBean.getPipeId());
         this.pipeInfoBean = this.pipeInfoService.getPipeInfoBean(this.pipeInfoBean);
         if (this.pipeInfoBean != null) {
           this.pipeSegmentInfoBean.setPipeName(this.pipeInfoBean.getPipeName());
         }
       }
       this.pipeSegmentType = this.pipeSegmentInfoBean.getPipeSegmentType();
       this.startDeviceId = this.pipeSegmentInfoBean.getStartDeviceId();
       this.endDeviceId = this.pipeSegmentInfoBean.getEndDeviceId();
       if ((this.pipeSegmentType != null) && ("2".equals(this.pipeSegmentType))) {
         if ((this.startDeviceId != null) && (this.endDeviceId != null)) {
           this.ledupInfoBean = new LedupInfoBean();
           this.ledupInfoBean.setWellId(this.startDeviceId);
           this.ledupInfoBean.setPoleId(this.endDeviceId);
           this.ledupInfoBean = this.pipeInfoService.getLedupInfoBean(this.ledupInfoBean);
           if (this.ledupInfoBean != null) {
             this.ledupInfoBean.setWellName(this.startDeviceName);
             this.ledupInfoBean.setPoleName(this.endDeviceName);
           }
         }
       } else if ((this.pipeSegmentType != null) && ("3".equals(this.pipeSegmentType))) {
         if ((this.startDeviceId != null) && (this.endDeviceId != null)) {
           this.ledupInfoBean = new LedupInfoBean();
           this.ledupInfoBean.setWellId(this.endDeviceId);
           this.ledupInfoBean.setPoleId(this.startDeviceId);
           this.ledupInfoBean = this.pipeInfoService.getLedupInfoBean(this.ledupInfoBean);
           if (this.ledupInfoBean != null) {
             this.ledupInfoBean.setWellName(this.endDeviceName);
             this.ledupInfoBean.setPoleName(this.startDeviceName);
           }
         }
       } else if (this.pipeSegmentType != null)
       {
         WellInfoBean w;
         String wellmap;
         int i;
         char c;
         FaceInfoBean f;
         if (this.startDeviceId != null) {
           w = new WellInfoBean();
           w.setWellId(this.startDeviceId);
           w = this.pipeInfoService.getWell(w);
           wellmap = w.getWellmap();
           List startFaceList = new ArrayList();
           for (i = 0; i < 8; ++i) {
             c = wellmap.charAt(i);
             if (c != '0') {
               startFaceList.add(c);
             }
           }
           this.startFaceArray = ((String[])startFaceList.toArray(new String[1]));
 
           f = new FaceInfoBean();
           f.setWellId(this.startDeviceId);
           f.setPipeSegmentId((String)this.ids.get(0));
           f = this.pipeInfoService.getFaceInfoBean(f);
           if (f != null) {
             this.pipeSegmentInfoBean.setStartFaceLocation(f.getLocationNo());
             this.startFaceNo = f.getLocationNo();
           } else {
             this.pipeSegmentInfoBean.setStartFaceLocation("");
             this.startFaceNo = "";
           }
         }
         if (this.endDeviceId != null) {
           w = new WellInfoBean();
           w.setWellId(this.endDeviceId);
           w = this.pipeInfoService.getWell(w);
           wellmap = w.getWellmap();
           List endFaceList = new ArrayList();
           for (int f1 = 0; f1 < 8; ++f1) {
             c = wellmap.charAt(f1);
             if (c != '0') {
               endFaceList.add(c);
             }
           }
           this.endFaceArray = ((String[])endFaceList.toArray(new String[1]));
 
           f = new FaceInfoBean();
           f.setWellId(this.endDeviceId);
           f.setPipeSegmentId((String)this.ids.get(0));
           f = this.pipeInfoService.getFaceInfoBean(f);
           if (f != null) {
             this.pipeSegmentInfoBean.setEndFaceLocation(f.getLocationNo());
             this.endFaceNo = f.getLocationNo();
           } else {
             this.pipeSegmentInfoBean.setEndFaceLocation("");
             this.endFaceNo = "";
           }
         }
       }
       return "loadPipeSegment";
     } catch (DataAccessException e) {
       log.error("PipeAction.editpipeSegment", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       return "error";
     } catch (Exception e) {
       log.error("PipeAction.editpipeSegment", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String editwell()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.wellInfoBean = new WellInfoBean();
       this.wellInfoBean.setWellId(Integer.valueOf(((String)this.ids.get(0)).trim()));
       this.wellInfoBean = this.pipeInfoService.getWellInfoBean(this.wellInfoBean);
       if (this.wellInfoBean != null)
         this.success = true;
       else {
         this.success = false;
       }
       this.direction = this.wellInfoBean.getDirection();
       if (this.direction.equals(""))
         this.direction = "";
       else if (this.direction.equals("东"))
         this.direction = "1";
       else if (this.direction.equals("南"))
         this.direction = "2";
       else if (this.direction.equals("西"))
         this.direction = "3";
       else {
         this.direction = "4";
       }
       this.wellInfoBean.setDirection(this.direction);
       return "loadWell";
     } catch (DataAccessException e) {
       log.error("PipeAction.editwell", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       return "error";
     } catch (Exception e) {
       log.error("PipeAction.editwell", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String dosave()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       Date lastUpdateDate = new Date();
       this.pipeInfoBean.setLastUpdateDate(lastUpdateDate);
       int n = this.pipeInfoService.updatePipeinfo(this.pipeInfoBean).intValue();
       if (n == 1) {
         this.success = true;
       }
       else {
         this.success = false;
       }
       return "updatePipe";
     } catch (DataAccessException e) {
       log.error("PipeAction.dosave", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       return "error";
     } catch (Exception e) {
       log.error("PipeAction.dosave", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String dosavePipeSegment()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       Date lastUpdateDate = new Date();
       this.pipeSegmentInfoBean.setLastUpdateDate(lastUpdateDate);
       this.pipeSegmentType = this.pipeSegmentInfoBean.getPipeSegmentType();
       if ((!(this.pipeSegmentType.equals("2"))) && (!(this.pipeSegmentType.equals("3"))) && (!(this.pipeSegmentType.equals("")))) {
         this.faceInfo = new FaceInfoBean();
         this.faceInfo.setWellId(this.pipeSegmentInfoBean.getStartDeviceId());
         this.faceInfo.setLocationNo(this.pipeSegmentInfoBean.getStartFaceLocation());
         this.faceInfo = this.pipeInfoService.getFaceInfoBean(this.faceInfo);
 
         FaceInfoBean oppoFaceInfo = new FaceInfoBean();
         oppoFaceInfo.setWellId(this.pipeSegmentInfoBean.getEndDeviceId());
         oppoFaceInfo.setLocationNo(this.pipeSegmentInfoBean.getEndFaceLocation());
         oppoFaceInfo = this.pipeInfoService.getFaceInfoBean(oppoFaceInfo);
         if (this.facebasic.equals("1")) {
           oppoFaceInfo.setCols(this.faceInfo.getCols());
           oppoFaceInfo.setRows(this.faceInfo.getRows());
           this.pipeInfoService.updateFace(oppoFaceInfo);
           oppoFaceInfo.setLogTime(lastUpdateDate);
           oppoFaceInfo.setLogOperater(this.userInfoBean.getUsername());
           this.pipeInfoService.faceLog(oppoFaceInfo);
         } else if (this.facebasic.equals("2")) {
           this.faceInfo.setCols(oppoFaceInfo.getCols());
           this.faceInfo.setRows(oppoFaceInfo.getRows());
           this.pipeInfoService.updateFace(this.faceInfo);
           oppoFaceInfo.setLogTime(lastUpdateDate);
           oppoFaceInfo.setLogOperater(this.userInfoBean.getUsername());
           this.pipeInfoService.faceLog(oppoFaceInfo);
         }
 
       }
 
       this.pipeSegmentInfoBean.setLogTime(lastUpdateDate);
       this.pipeSegmentInfoBean.setLogOperater(this.userInfoBean.getUsername());
       int n = this.pipeInfoService.updatePipeSegmentinfo(this.pipeSegmentInfoBean).intValue();
       if (n == 1) {
         this.pipeInfoService.pipesegmentLog(this.pipeSegmentInfoBean);
         this.success = true;
       } else {
         this.success = false;
       }
 
       if ((this.pipeSegmentType != null) && ((("2".equals(this.pipeSegmentType)) || ("3".equals(this.pipeSegmentType)))) && ((
         (this.ledupInfoBean.getWellName() != null) || (this.ledupInfoBean.getPoleName() != null)))) {
         LedupInfoBean ledupInfo = this.pipeInfoService.getLedupInfoBeanById(this.ledupInfoBean);
         if (this.ledupInfoBean.getLedupPointId() == null) {
           if (ledupInfo == null) {
             this.ledupInfoBean.setCreationDate(lastUpdateDate);
             this.ledupInfoBean.setLastUpdateDate(lastUpdateDate);
             int i = this.pipeInfoService.insertLedupInfoBean(this.ledupInfoBean).intValue();
             this.ledupInfoBean.setLogTime(lastUpdateDate);
             this.ledupInfoBean.setLogOperater(this.userInfoBean.getUsername());
             this.ledupInfoBean.setLedupPointId(Integer.valueOf(i));
             this.pipeInfoService.ledupLog(this.ledupInfoBean);
           }
           else {
             this.ledupInfoBean.setLedupPointId(ledupInfo.getLedupPointId());
             this.pipeInfoService.updateLedupInfoBean(this.ledupInfoBean);
             this.ledupInfoBean.setLogTime(lastUpdateDate);
             this.ledupInfoBean.setLogOperater(this.userInfoBean.getUsername());
             this.pipeInfoService.ledupLog(this.ledupInfoBean);
           }
         }
         else if (ledupInfo == null) {
           this.pipeInfoService.updateLedupInfoBean(this.ledupInfoBean);
           this.ledupInfoBean.setLogTime(lastUpdateDate);
           this.ledupInfoBean.setLogOperater(this.userInfoBean.getUsername());
           this.pipeInfoService.ledupLog(this.ledupInfoBean);
         } else {
           this.pipeInfoService.updateLedupInfoBean(this.ledupInfoBean);
           this.ledupInfoBean.setLogTime(lastUpdateDate);
           this.ledupInfoBean.setLogOperater(this.userInfoBean.getUsername());
           this.pipeInfoService.ledupLog(this.ledupInfoBean);
         }
 
       }
 
       return "updatePipeSegment";
     } catch (DataAccessException e) {
       log.error("PipeAction.dosavePipeSegment", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       return "error";
     } catch (Exception e) {
       log.error("PipeAction.dosavePipeSegment", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String dosaveWell()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       Date lastUpdateDate = new Date();
       this.wellInfoBean.setLastUpdateDate(lastUpdateDate);
       this.wellName = this.wellInfoBean.getWellName();
       this.direction = this.wellInfoBean.getDirection();
       this.wellNo = this.wellInfoBean.getWellNo();
       this.wellSubNo = this.wellInfoBean.getWellSubNo();
 
       if (this.direction.equals(""))
         this.direction = "";
       else if (this.direction.equals("1"))
         this.direction = "东";
       else if (this.direction.equals("2"))
         this.direction = "西";
       else if (this.direction.equals("3"))
         this.direction = "南";
       else {
         this.direction = "北";
       }
 
       if ((this.wellSubNo != null) && (!(this.wellSubNo.equals(""))))
         this.startDeviceName = this.wellName + this.direction + "#" + this.wellNo + "_" + this.wellSubNo;
       else {
         this.startDeviceName = this.wellName + this.direction + "#" + this.wellNo;
       }
       this.wellInfoBean.setWellNameSub(this.startDeviceName);
       List <TubeInfoBean>tlist=null;
       if ((!(this.wellName.equals(this.cname))) || (!(this.direction.equals(this.cdirection))) || (!(this.wellNo.equals(this.cwellNo))) || (!(this.wellSubNo.equals(this.cwellSubNo)))) {
         this.pipeInfoBean = new PipeInfoBean();
         this.pipeInfoService.updateEPipeinfoStart(this.pipeInfoBean);
 
         this.pipeInfoBean = new PipeInfoBean();
         this.pipeInfoService.updateEPipeinfoEnd(this.pipeInfoBean);
 
         TubeInfoBean t = new TubeInfoBean();
         t.setWellId(this.wellInfoBean.getWellId()+"");
              tlist = this.pipeInfoService.getTubeList(t);
         for (TubeInfoBean e : tlist) {
           String name = e.getTubeName();
           if (name.contains("面")) {
             name = this.startDeviceName + name.substring(name.lastIndexOf("面-") - 2);
             e.setTubeName(name);
             this.pipeInfoService.updateTubeinfo(e);
           }
         }
 
       }
 
       if ((!(this.wellName.equals(this.cname))) || (!(this.direction.equals(this.cdirection))) || (!(this.wellNo.equals(this.cwellNo))) || (!(this.wellSubNo.equals(this.cwellSubNo)))) {
         this.pipeSegmentInfoBean = new PipeSegmentInfoBean();
         this.pipeSegmentInfoBean.setStartDeviceId(this.wellInfoBean.getWellId());
         this.pipeSegmentInfoBean.setStartDeviceType("1");
         this.segresultList = this.pipeInfoService.getPipeSegmentAll(this.pipeSegmentInfoBean);
         PipeSegmentInfoBean pipeSegmentInfo;
         String pipesname;
         for (Iterator segIterator = this.segresultList.iterator(); segIterator.hasNext(); ) 
         { pipeSegmentInfo = (PipeSegmentInfoBean)segIterator.next();
           if ((pipeSegmentInfo.getEndDeviceName() != null) || (pipeSegmentInfo.getEndDeviceName() != ""))
           {
             if ((pipeSegmentInfo.getPipeId() != null) && (pipeSegmentInfo.getPipeId().intValue() != 0)) {
               this.pipeInfoBean = new PipeInfoBean();
               this.pipeInfoBean.setPipeId(pipeSegmentInfo.getPipeId());
               this.pipeInfoBean = this.pipeInfoService.getPipeInfoBean(this.pipeInfoBean);
               pipesname = this.pipeInfoBean.getPipeName() + "(" + this.startDeviceName + "_" + pipeSegmentInfo.getEndDeviceName() + ")";
               pipeSegmentInfo.setPipeSegmentName(pipesname);
               pipeSegmentInfo.setStartDeviceName(this.startDeviceName);
               this.pipeInfoService.updatePipeSegmentInfoBean(pipeSegmentInfo);
             } else {
               pipesname = this.startDeviceName + "_" + pipeSegmentInfo.getEndDeviceName();
               pipeSegmentInfo.setPipeSegmentName(pipesname);
               pipeSegmentInfo.setStartDeviceName(this.startDeviceName);
               this.pipeInfoService.updatePipeSegmentInfoBean(pipeSegmentInfo);
             }
 
           }
           else if ((pipeSegmentInfo.getPipeId() != null) && (pipeSegmentInfo.getPipeId().intValue() != 0)) {
             this.pipeInfoBean = new PipeInfoBean();
             this.pipeInfoBean.setPipeId(pipeSegmentInfo.getPipeId());
             this.pipeInfoBean = this.pipeInfoService.getPipeInfoBean(this.pipeInfoBean);
             pipesname = this.pipeInfoBean.getPipeName() + "(" + this.startDeviceName + "_" + ")";
             pipeSegmentInfo.setPipeSegmentName(pipesname);
             pipeSegmentInfo.setStartDeviceName(this.startDeviceName);
             this.pipeInfoService.updatePipeSegmentInfoBean(pipeSegmentInfo);
           } else {
             pipesname = this.startDeviceName + "_";
             pipeSegmentInfo.setPipeSegmentName(pipesname);
             pipeSegmentInfo.setStartDeviceName(this.startDeviceName);
             this.pipeInfoService.updatePipeSegmentInfoBean(pipeSegmentInfo);
           }
 
         }
 
         this.pipeSegmentInfoBean = new PipeSegmentInfoBean();
         this.pipeSegmentInfoBean.setEndDeviceId(this.wellInfoBean.getWellId());
         this.pipeSegmentInfoBean.setEndDeviceType("1");
         this.segresultList = this.pipeInfoService.getPipeSegmentAll(this.pipeSegmentInfoBean);
         for (Iterator segIterator = this.segresultList.iterator(); segIterator.hasNext(); ) 
         { pipeSegmentInfo = (PipeSegmentInfoBean)segIterator.next();
           if ((pipeSegmentInfo.getStartDeviceName() != null) || (pipeSegmentInfo.getStartDeviceName() != "")) {
             if ((pipeSegmentInfo.getPipeId() != null) && (pipeSegmentInfo.getPipeId().intValue() != 0)) {
               this.pipeInfoBean = new PipeInfoBean();
               this.pipeInfoBean.setPipeId(pipeSegmentInfo.getPipeId());
               this.pipeInfoBean = this.pipeInfoService.getPipeInfoBean(this.pipeInfoBean);
               pipesname = this.pipeInfoBean.getPipeName() + "(" + pipeSegmentInfo.getStartDeviceName() + "_" + this.startDeviceName + ")";
               pipeSegmentInfo.setPipeSegmentName(pipesname);
               pipeSegmentInfo.setEndDeviceName(this.startDeviceName);
               this.pipeInfoService.updatePipeSegmentInfoBean(pipeSegmentInfo);
             } else {
               pipesname = pipeSegmentInfo.getStartDeviceName() + "_" + this.startDeviceName;
               pipeSegmentInfo.setPipeSegmentName(pipesname);
               pipeSegmentInfo.setEndDeviceName(this.startDeviceName);
               this.pipeInfoService.updatePipeSegmentInfoBean(pipeSegmentInfo);
             }
           }
           else if ((pipeSegmentInfo.getPipeId() != null) && (pipeSegmentInfo.getPipeId().intValue() != 0)) {
             this.pipeInfoBean = new PipeInfoBean();
             this.pipeInfoBean.setPipeId(pipeSegmentInfo.getPipeId());
             this.pipeInfoBean = this.pipeInfoService.getPipeInfoBean(this.pipeInfoBean);
             pipesname = "_" + this.startDeviceName;
             pipeSegmentInfo.setPipeSegmentName(pipesname);
             pipeSegmentInfo.setEndDeviceName(this.startDeviceName);
             this.pipeInfoService.updatePipeSegmentInfoBean(pipeSegmentInfo);
           } else {
             pipesname = this.pipeInfoBean.getPipeName() + "(" + "_" + this.startDeviceName + ")";
             pipeSegmentInfo.setPipeSegmentName(pipesname);
             pipeSegmentInfo.setEndDeviceName(this.startDeviceName);
             this.pipeInfoService.updatePipeSegmentInfoBean(pipeSegmentInfo);
           }
         }
 
       }
 
       this.wellInfoBean.setDirection(this.direction);
       int n = this.pipeInfoService.updateWellinfo(this.wellInfoBean).intValue();
       if (n == 1) {
         this.wellInfoBean.setLogTime(lastUpdateDate);
         this.wellInfoBean.setLogOperater(this.userInfoBean.getUsername());
         this.pipeInfoService.wellLog(this.wellInfoBean);
         this.success = true;
       }
       else {
         this.success = false;
       }
       return "updateWell";
     } catch (DataAccessException e) {
       log.error("EqutAction.dosave", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       return "error";
     } catch (Exception e) {
       log.error("EqutAction.dosave", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String dodelete()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       Date deletionDate = new Date();
       this.pipeInfoBean = new PipeInfoBean();
       this.pipeInfoBean.setPipeId(Integer.valueOf(((String)this.ids.get(0)).trim()));
       this.pipeInfoBean = this.pipeInfoService.getPipeInfoBean(this.pipeInfoBean);
       int n = this.pipeInfoService.deletePipeInfo(this.pipeInfoBean);
       if (n != 0) {
         this.pipeInfoBean.setDeletedFlag("1");
         this.pipeInfoService.pipeLog(this.pipeInfoBean);
         this.success = true;
         this.deleteMsg = "您成功删除了&nbsp;" + n + "&nbsp;条管道信息!";
 
         StringBuffer description = new StringBuffer("用户");
         description.append(this.userInfoBean.getRealname());
         description.append("删除管道成功");
       } else {
         this.success = false;
         this.deleteMsg = "删除管道信息失败!";
       }
       return "deletePipe";
     } catch (DataAccessException e) {
       log.error("PipeAction.dodelete", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String dodeletepipeSegment()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.pipeSegmentInfoBean = new PipeSegmentInfoBean();
       this.pipeSegmentInfoBean.setPipeSegmentId(Integer.valueOf(((String)this.ids.get(0)).trim()));
       this.pipeSegmentInfoBean = this.pipeInfoService.getPipeSegmentInfoBean(this.pipeSegmentInfoBean);
       this.startDeviceId = this.pipeSegmentInfoBean.getStartDeviceId();
       this.endDeviceId = this.pipeSegmentInfoBean.getEndDeviceId();
       this.pipeSegmentId = this.pipeSegmentInfoBean.getPipeSegmentId();
       this.pipeSegmentType = this.pipeSegmentInfoBean.getPipeSegmentType();
       Date deletionDate = new Date();
       this.pipeSegmentInfoBean.setDeletionDate(deletionDate);
       int n = this.pipeInfoService.deletePipeSegmentInfo(this.pipeSegmentInfoBean);
       this.pipeSegmentInfoBean.setLogTime(deletionDate);
       this.pipeSegmentInfoBean.setLogOperater(this.userInfoBean.getUsername());
       this.pipeSegmentInfoBean.setDeletedFlag("1");
       this.pipeInfoService.pipesegmentLog(this.pipeSegmentInfoBean);
       if ((!(this.pipeSegmentType.equals("2"))) && (!(this.pipeSegmentType.equals("3")))) {
         this.faceInfo = new FaceInfoBean();
         this.faceInfo.setWellId(this.startDeviceId);
         this.faceInfo.setPipeSegmentId((this.pipeSegmentId)+"");
         this.faceInfo = this.pipeInfoService.getFaceInfoBean1(this.faceInfo);
 
         FaceInfoBean faceInfo1 = new FaceInfoBean();
         faceInfo1.setWellId(this.endDeviceId);
         faceInfo1.setPipeSegmentId((this.pipeSegmentId)+"");
         faceInfo1 = this.pipeInfoService.getFaceInfoBean1(faceInfo1);
         if ((this.faceInfo != null) && (faceInfo1 != null)) {
           FaceInfoBean faceInfo2 = new FaceInfoBean();
           faceInfo2.setFaceId1(this.faceInfo.getFaceId());
           faceInfo2.setFaceId2(faceInfo1.getFaceId());
           this.pipeInfoService.deleteFaceRelation(faceInfo2);
           faceInfo2 = new FaceInfoBean();
           faceInfo2.setFaceId1(faceInfo1.getFaceId());
           faceInfo2.setFaceId2(this.faceInfo.getFaceId());
           this.pipeInfoService.deleteFaceRelation(faceInfo2);
         }
       }
       if (n != 0) {
         this.success = true;
         this.deleteMsg = "您成功删除了&nbsp;" + n + "&nbsp;条管道段信息!";
 
         StringBuffer description = new StringBuffer("用户");
         description.append(this.userInfoBean.getRealname());
         description.append("删除管道段成功");
       } else {
         this.success = false;
         this.deleteMsg = "删除管道信息失败!";
       }
       return "deletePipeSegment";
     } catch (DataAccessException e) {
       log.error("EqutAction.deleteequt", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String dodeletewell()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       Date deletionDate = new Date();
       this.wellInfoBean = new WellInfoBean();
       this.wellInfoBean.setWellId(Integer.valueOf(((String)this.ids.get(0)).trim()));
       this.wellInfoBean = this.pipeInfoService.getWellInfoBean(this.wellInfoBean);
       this.wellInfoBean.setDeletionDate(deletionDate);
       int n = this.pipeInfoService.deleteWellInfo(this.wellInfoBean);
 
       this.pipeInfoBean = new PipeInfoBean();
       this.pipeInfoService.updatePipeinfoStart(this.pipeInfoBean);
 
       this.pipeInfoBean = new PipeInfoBean();
       this.pipeInfoService.updatePipeinfoEnd(this.pipeInfoBean);
       this.pipeInfoService.pipeLog(this.pipeInfoBean);
 
       this.pipeSegmentInfoBean = new PipeSegmentInfoBean();
       this.pipeSegmentInfoBean.setStartDeviceType("1");
       this.pipeSegmentInfoBean.setStartDeviceId(Integer.valueOf(((String)this.ids.get(0)).trim()));
       this.pipeInfoService.updatePipeSegmentinfoStart(this.pipeSegmentInfoBean);
       this.pipeSegmentInfoBean.setLogTime(deletionDate);
       this.pipeSegmentInfoBean.setLogOperater(this.userInfoBean.getUsername());
       this.pipeInfoService.pipesegmentLog(this.pipeSegmentInfoBean);
 
       this.pipeSegmentInfoBean = new PipeSegmentInfoBean();
       this.pipeSegmentInfoBean.setEndDeviceType("1");
       this.pipeSegmentInfoBean.setEndDeviceId(Integer.valueOf(((String)this.ids.get(0)).trim()));
       this.pipeInfoService.updatePipeSegmentinfoEnd(this.pipeSegmentInfoBean);
       this.pipeSegmentInfoBean.setLogTime(deletionDate);
       this.pipeSegmentInfoBean.setLogOperater(this.userInfoBean.getUsername());
       this.pipeInfoService.pipesegmentLog(this.pipeSegmentInfoBean);
 
       if (n != 0) {
         this.wellInfoBean.setLogTime(deletionDate);
         this.wellInfoBean.setLogOperater(this.userInfoBean.getUsername());
         this.wellInfoBean.setDeletedFlag("1");
         this.pipeInfoService.wellLog(this.wellInfoBean);
         this.success = true;
         this.deleteMsg = "您成功删除了&nbsp;" + n + "&nbsp;条井信息!";
 
         StringBuffer description = new StringBuffer("用户");
         description.append(this.userInfoBean.getRealname());
         description.append("删除井成功");
       } else {
         this.success = false;
         this.deleteMsg = "删除井信息失败!";
       }
       return "deleteWell";
     } catch (DataAccessException e) {
       log.error("EqutAction.deleteequt", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String doadd()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       Date creationDate = new Date();
       this.pipeInfoBean.setCreationDate(creationDate);
       this.pipeInfoBean.setLastUpdateDate(creationDate);
       Integer n = Integer.valueOf(this.pipeInfoService.insertPipeInfo(this.pipeInfoBean));
       if (n != null) {
         this.pipeInfoBean.setPipeId(n);
         this.success = true;
       } else {
         this.success = false;
       }
       return "addPipe";
     } catch (Exception e) {
       log.error("PipeAction.doadd", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String dosaveOppoface()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       Integer oppoFaceId = this.faceInfo.getOppoFaceId();
 
       this.faceInfo = this.pipeInfoService.getFaceInfoBean(this.faceInfo);
       this.faceInfo.setOppoFaceId(oppoFaceId);
 
       FaceInfoBean oppoFaceInfo = new FaceInfoBean();
       oppoFaceInfo.setFaceId(oppoFaceId);
       oppoFaceInfo = this.pipeInfoService.getFaceInfoBean(oppoFaceInfo);
       oppoFaceInfo.setCols(this.faceInfo.getCols());
       oppoFaceInfo.setRows(this.faceInfo.getRows());
       this.pipeInfoService.updateFace(oppoFaceInfo);
       oppoFaceInfo.setLogTime(new Date());
       oppoFaceInfo.setLogOperater(this.userInfoBean.getUsername());
       this.pipeInfoService.faceLog(oppoFaceInfo);
 
       WellInfoBean w1 = new WellInfoBean();
       w1.setWellId(this.faceInfo.getWellId());
       w1 = this.pipeInfoService.getWell(w1);
 
       WellInfoBean w2 = new WellInfoBean();
       w2.setWellId(oppoFaceInfo.getWellId());
       w2 = this.pipeInfoService.getWell(w2);
 
       PipeSegmentInfoBean pSeg = new PipeSegmentInfoBean();
       pSeg.setPipeId(Integer.valueOf(0));
       pSeg.setPipeSegmentId(Integer.valueOf(0));
       pSeg.setPipeSegmentName(w1.getWellNameSub() + "_" + w2.getWellNameSub());
       pSeg.setPipeSegmentType("1");
       pSeg.setStartDeviceId(w1.getWellId());
       pSeg.setStartDeviceType("1");
       pSeg.setStartDeviceName(w1.getWellNameSub());
       pSeg.setEndDeviceId(w2.getWellId());
       pSeg.setEndDeviceType("1");
       pSeg.setEndDeviceName(w2.getWellNameSub());
       pSeg.setAreano(w1.getAreano());
       pSeg.setAreaname(w1.getAreaname());
       List pSegList = new ArrayList();
       pSegList.add(pSeg);
       List faceList = new ArrayList();
       faceList.add(this.faceInfo);
       faceList.add(oppoFaceInfo);
       return "dosaveoppoface";
     } catch (Exception e) {
       log.error("PipeAction.updateResource", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String doaddpipeSegment()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       Date creationDate = new Date();
       this.pipeSegmentType = this.pipeSegmentInfoBean.getPipeSegmentType();
       if ((!(this.pipeSegmentType.equals("2"))) && (!(this.pipeSegmentType.equals("3"))) && (!(this.pipeSegmentType.equals("")))) {
         this.faceInfo = new FaceInfoBean();
         this.faceInfo.setWellId(this.pipeSegmentInfoBean.getStartDeviceId());
         this.faceInfo.setLocationNo(this.pipeSegmentInfoBean.getStartFaceLocation());
         this.faceInfo = this.pipeInfoService.getFaceInfoBean(this.faceInfo);
 
         FaceInfoBean oppoFaceInfo = new FaceInfoBean();
         oppoFaceInfo.setWellId(this.pipeSegmentInfoBean.getEndDeviceId());
         oppoFaceInfo.setLocationNo(this.pipeSegmentInfoBean.getEndFaceLocation());
         oppoFaceInfo = this.pipeInfoService.getFaceInfoBean(oppoFaceInfo);
 
         if (this.facebasic.equals("1")) {
           oppoFaceInfo.setCols(this.faceInfo.getCols());
           oppoFaceInfo.setRows(this.faceInfo.getRows());
           this.pipeInfoService.updateFace(oppoFaceInfo);
           oppoFaceInfo.setLogTime(creationDate);
           oppoFaceInfo.setLogOperater(this.userInfoBean.getUsername());
           this.pipeInfoService.faceLog(oppoFaceInfo);
         } else if (this.facebasic.equals("2")) {
           this.faceInfo.setCols(oppoFaceInfo.getCols());
           this.faceInfo.setRows(oppoFaceInfo.getRows());
           this.pipeInfoService.updateFace(this.faceInfo);
           this.faceInfo.setLogTime(creationDate);
           this.faceInfo.setLogOperater(this.userInfoBean.getUsername());
           this.pipeInfoService.faceLog(this.faceInfo);
         }
 
       }
 
       this.pipeSegmentInfoBean.setCreationDate(creationDate);
       this.pipeSegmentInfoBean.setLastUpdateDate(creationDate);
       int n = this.pipeInfoService.insertPipeSegmentInfo(this.pipeSegmentInfoBean);
       this.pipeSegmentInfoBean.setPipeSegmentId(Integer.valueOf(n));
       this.pipeSegmentInfoBean.setLogTime(creationDate);
       this.pipeSegmentInfoBean.setLogOperater(this.userInfoBean.getUsername());
       this.pipeInfoService.pipesegmentLog(this.pipeSegmentInfoBean);
       if (("2".equals(this.pipeSegmentType)) || ("3".equals(this.pipeSegmentType))) {
         this.poleId = this.ledupInfoBean.getPoleId();
         this.wellId = this.ledupInfoBean.getWellId();
         LedupInfoBean ledupInfo = new LedupInfoBean();
         ledupInfo.setPoleId(this.poleId);
         ledupInfo.setWellId(this.wellId);
         ledupInfo = this.pipeInfoService.getLedupInfoBean(ledupInfo);
         if (ledupInfo != null) {
           this.ledupInfoBean.setLedupPointId(ledupInfo.getLedupPointId());
           this.pipeInfoService.updateLedupInfoBean(this.ledupInfoBean);
           this.ledupInfoBean.setLogTime(creationDate);
           this.ledupInfoBean.setLogOperater(this.userInfoBean.getUsername());
           this.pipeInfoService.ledupLog(this.ledupInfoBean);
         } else {
           this.ledupInfoBean.setCreationDate(creationDate);
           this.ledupInfoBean.setLastUpdateDate(creationDate);
           int i = this.pipeInfoService.insertLedupInfoBean(this.ledupInfoBean).intValue();
           this.ledupInfoBean.setLedupPointId(Integer.valueOf(i));
           this.ledupInfoBean.setLogTime(creationDate);
           this.ledupInfoBean.setLogOperater(this.userInfoBean.getUsername());
           this.pipeInfoService.ledupLog(this.ledupInfoBean);
         }
       }
       if (n == 1) {
         this.success = true;
       }
       else {
         this.success = false;
       }
       return "addPipeSegment";
     } catch (Exception e) {
       log.error("EqutAction.addequt", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String doaddwell()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       Date creationDate = new Date();
       this.wellInfoBean.setCreationDate(creationDate);
       this.wellInfoBean.setLastUpdateDate(creationDate);
       this.wellInfoBean.setWellmap("00000000");
       this.wellName = this.wellInfoBean.getWellName();
       this.direction = this.wellInfoBean.getDirection();
       this.wellNo = this.wellInfoBean.getWellNo();
       this.wellSubNo = this.wellInfoBean.getWellSubNo();
 
       if (this.direction.equals(""))
         this.direction = "";
       else if (this.direction.equals("1"))
         this.direction = "东";
       else if (this.direction.equals("2"))
         this.direction = "南";
       else if (this.direction.equals("3"))
         this.direction = "西";
       else {
         this.direction = "北";
       }
       if ((this.wellSubNo != null) && (!(this.wellSubNo.equals(""))))
         this.startDeviceName = this.wellName + this.direction + "#" + this.wellNo + "_" + this.wellSubNo;
       else {
         this.startDeviceName = this.wellName + this.direction + "#" + this.wellNo;
       }
       this.wellInfoBean.setDirection(this.direction);
       this.wellInfoBean.setWellNameSub(this.startDeviceName);
       Integer n = Integer.valueOf(this.pipeInfoService.insertWellInfo(this.wellInfoBean));
       if (n != null) {
         this.wellInfoBean.setLogTime(creationDate);
         this.wellInfoBean.setLogOperater(this.userInfoBean.getUsername());
         this.wellInfoBean.setWellId(n);
         this.pipeInfoService.wellLog(this.wellInfoBean);
         this.success = true;
 
         StringBuffer description = new StringBuffer("用户");
         description.append(this.userInfoBean.getRealname());
         description.append("添加井成功");
       } else {
         this.success = false;
       }
       return "addWell";
     } catch (Exception e) {
       log.error("PipeAction.doaddwell", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public void checkFaceRelationJSON() throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       FaceInfoBean face = new FaceInfoBean();
       face.setWellId(this.wellId);
       face.setLocationNo(this.locationNo);
       face = this.pipeInfoService.getFaceInfoBean(face);
       Boolean flag1 = Boolean.valueOf(false);
       Boolean flag2 = Boolean.valueOf(false);
       if (face != null) {
         this.faceInfo = new FaceInfoBean();
         this.faceInfo.setFaceId1(face.getFaceId());
         flag1 = this.pipeInfoService.getFaceRelation(this.faceInfo);
 
         this.faceInfo = new FaceInfoBean();
         this.faceInfo.setFaceId2(face.getFaceId());
         flag2 = this.pipeInfoService.getFaceRelation(this.faceInfo);
       }
       if ((flag1.booleanValue()) || (flag2.booleanValue()))
         this.flag = Boolean.valueOf(true);
       else {
         this.flag = Boolean.valueOf(false);
       }
       HttpServletResponse response = ServletActionContext.getResponse();
       response.setContentType("text/json");
       response.setCharacterEncoding("utf-8");
       response.getWriter().write(this.flag.toString());
       response.getWriter().flush();
     } catch (Exception e) {
       log.error("PipeAction.checkFaceRelationJSON", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
     }
   }
 
   public String searchJSON()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       if (this.wellInfoBean == null) {
         this.wellInfoBean = new WellInfoBean();
       }
       this.wellInfoBean.setStart(this.start);
       this.wellInfoBean.setLimit(this.limit);
       this.wellInfoBean = this.pipeInfoService.getWellList(this.wellInfoBean);
       this.wellresultList = this.wellInfoBean.getWells();
       for (WellInfoBean w : this.wellresultList) {
         String wellmap = w.getWellmap();
         if (wellmap != null) {
           String faceids = "";
           for (int i = 0; i < wellmap.length(); ++i) {
             if (wellmap.charAt(i) != '0') {
               FaceInfoBean face = new FaceInfoBean();
               face.setWellId(w.getWellId());
               face.setLocationNo((wellmap.charAt(i))+"");
               face = this.pipeInfoService.getFaceInfoBean(face);
               if (face != null)
                 faceids = faceids + face.getFaceId() + ",";
               else
                 faceids = faceids + "0,";
             }
             else {
               faceids = faceids + "0,";
             }
           }
           w.setFaceids(faceids);
         }
       }
       return "wellInfoBean";
     } catch (DataAccessException e) {
       log.error("EqutAction.search", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public void searchPoleJSON() throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.poleInfoBean = new PoleInfoBean();
 
       this.poleAddress = URLDecoder.decode(this.poleAddress, "utf-8");
       this.poleName = URLDecoder.decode(this.poleName, "utf-8");
       if (this.areaname != null) {
         this.areaname = URLDecoder.decode(this.areaname, "utf-8");
       }
       this.poleInfoBean.setPoleName(CommonUtil.setNullForString(this.poleName));
       this.poleInfoBean.setPoleAddress(CommonUtil.setNullForString(this.poleAddress));
       this.poleInfoBean.setAreaname(CommonUtil.setNullForString(this.areaname));
 
       setTotalCount(this.pipeInfoService.getPoleCount(this.poleInfoBean));
       int startPage = (this.page - 1) * 10;
       this.poleresultList = this.pipeInfoService.getPoleList(this.poleInfoBean, startPage, this.pageSize);
       JSONArray poleList = JSONArray.fromObject(this.poleresultList);
       poleList.add(Integer.valueOf(this.totalCount));
       HttpServletResponse response = ServletActionContext.getResponse();
       response.setContentType("text/json");
       response.setCharacterEncoding("utf-8");
       response.getWriter().write(poleList.toString());
       response.getWriter().flush();
     }
     catch (DataAccessException e)
     {
       log.error("EqutAction.search", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
     }
   }
 
   public void searchPsJSON() throws Exception
   {
     try {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.pipeSegmentInfoBean = new PipeSegmentInfoBean();
       this.areaname = URLDecoder.decode(this.areaname, "utf-8");
       this.pipeSegmentName = URLDecoder.decode(this.pipeSegmentName, "utf-8");
 
       this.pipeSegmentInfoBean.setPipeSegmentName(CommonUtil.setNullForString(this.pipeSegmentName));
       this.pipeSegmentInfoBean.setPipeSegmentType(CommonUtil.setNullForString(this.pipeSegmentType));
       this.pipeSegmentInfoBean.setAreaname(CommonUtil.setNullForString(this.areaname));
 
       setTotalCount(this.pipeInfoService.getPipeSegmentCount(this.pipeSegmentInfoBean));
       int startPage = (this.page - 1) * 10;
       this.segresultList = this.pipeInfoService.getPipeSegmentList(this.pipeSegmentInfoBean, startPage, this.pageSize);
       JSONArray poleList = JSONArray.fromObject(this.segresultList);
       poleList.add(Integer.valueOf(this.totalCount));
       HttpServletResponse response = ServletActionContext.getResponse();
       response.setContentType("text/json");
       response.setCharacterEncoding("utf-8");
       response.getWriter().write(poleList.toString());
       response.getWriter().flush();
     }
     catch (DataAccessException e)
     {
       log.error("EqutAction.search", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
     }
   }
 
   public void searchPipeJSON() throws Exception
   {
     try {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.pipeInfoBean = new PipeInfoBean();
       this.pipeName = URLDecoder.decode(this.pipeName, "utf-8");
       this.areaname = URLDecoder.decode(this.areaname, "utf-8");
 
       this.pipeInfoBean.setPipeName(CommonUtil.setNullForString(this.pipeName));
       this.pipeInfoBean.setPipeLevel(CommonUtil.setNullForString(this.pipeLevel));
       this.pipeInfoBean.setAreaname(CommonUtil.setNullForString(this.areaname));
 
       setTotalCount(this.pipeInfoService.getCount(this.pipeInfoBean));
       int startPage = (this.page - 1) * 10;
       this.resultList = this.pipeInfoService.getPipeList(this.pipeInfoBean, startPage, this.pageSize);
       JSONArray poleList = JSONArray.fromObject(this.resultList);
       poleList.add(Integer.valueOf(this.totalCount));
       HttpServletResponse response = ServletActionContext.getResponse();
       response.setContentType("text/json");
       response.setCharacterEncoding("utf-8");
       response.getWriter().write(poleList.toString());
       response.getWriter().flush();
     }
     catch (DataAccessException e)
     {
       log.error("EqutAction.search", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
     }
   }
 
   public void searchpipeJSON() throws Exception
   {
     try {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.pipeInfoBean = new PipeInfoBean();
       this.pipeInfoBean.setPipeId(this.pipeId);
       this.pipeInfoBean = this.pipeInfoService.getPipeInfoBean(this.pipeInfoBean);
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       HttpServletResponse response = ServletActionContext.getResponse();
       response.setContentType("text/json");
       response.setCharacterEncoding("utf-8");
       response.getWriter().write(JSONArray.fromObject(this.pipeInfoBean).toString());
       response.getWriter().flush();
     } catch (Exception e) {
       log.error("PointAction.searchpipeJSON", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
     }
   }
 
   public String checkPipeNameJSON()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.pipeInfoBean = new PipeInfoBean();
       this.pipeInfoBean.setPipeName(this.pipeName);
       this.pipeInfoBean = this.pipeInfoService.getPipeInfoBean(this.pipeInfoBean);
       if (this.pipeInfoBean != null)
         this.success = false;
       else {
         this.success = true;
       }
       return "vetifyPipeName";
     } catch (Exception e) {
       log.error("PipeAction.checkPipeNameJSON", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String checkPipeSegmentNameJSON()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.pipeSegmentInfoBean = new PipeSegmentInfoBean();
       this.pipeSegmentInfoBean.setPipeSegmentName(this.pipeSegmentName);
       this.pipeSegmentInfoBean = this.pipeInfoService.getPipeSegmentInfoBean(this.pipeSegmentInfoBean);
       if (this.pipeSegmentInfoBean != null)
         this.success = false;
       else {
         this.success = true;
       }
       return "vetifyPipeSegmentName";
     } catch (Exception e) {
       log.error("PipeAction.checkPipeSegmentNameJSON", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public void checkTubeNameJSON() throws Exception
   {
     this.tubeName = URLDecoder.decode(this.tubeName, "utf-8");
     this.tubeName = CommonUtil.todecing(this.tubeName);
     try {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.tubeInfoBean = new TubeInfoBean();
       this.tubeInfoBean.setTubeName(this.tubeName);
       this.tubeInfoBean = this.pipeInfoService.getTubeInfoBean(this.tubeInfoBean);
       if (this.tubeInfoBean != null)
         this.flag = Boolean.valueOf(false);
       else {
         this.flag = Boolean.valueOf(true);
       }
       HttpServletResponse response = ServletActionContext.getResponse();
       response.setContentType("text/json");
       response.setCharacterEncoding("utf-8");
       response.getWriter().write(this.flag.toString());
       response.getWriter().flush();
     } catch (Exception e) {
       log.error("PointAction.checkJSON", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
     }
   }
 
   public String checkWellNameJSON()
     throws Exception
   {
     if (this.direction.equals(""))
       this.direction = "";
     else if (this.direction.equals("1"))
       this.direction = "东";
     else if (this.direction.equals("2"))
       this.direction = "南";
     else if (this.direction.equals("3"))
       this.direction = "西";
     else
       this.direction = "北";
     try
     {
       this.wellInfoBean = new WellInfoBean();
       this.wellInfoBean.setWellName(CommonUtil.setNullForString(this.wellName));
       this.wellInfoBean.setDirection(CommonUtil.setNullForString(this.direction));
       this.wellInfoBean.setWellNo(CommonUtil.setNullForString(this.wellNo));
       this.wellInfoBean.setWellSubNo(CommonUtil.setNullForString(this.wellSubNo));
       this.wellInfoBean = this.pipeInfoService.getWellInfoBean(this.wellInfoBean);
       if (this.wellInfoBean != null)
         this.success = false;
       else {
         this.success = true;
       }
       return "vetifyWellName";
     } catch (Exception e) {
       log.error("PointAction.checkJSON", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String addJb() throws Exception
   {
     try {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       return "addjb";
     } catch (Exception e) {
       log.error("EqutAction.addequt", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String serchJbpipeSegment() throws Exception
   {
     try {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
 
       this.pageSize = 20;
 
       setTotalCount(this.pipeInfoService.getJbpipeSegmentCount(this.pipeSegmentInfoBean));
       int startPage = getStartPage();
       this.segresultList = this.pipeInfoService.getJbpipeSegmentList(this.pipeSegmentInfoBean, startPage, this.pageSize);
       return "jbpipeSegmentList";
     } catch (Exception e) {
       log.error("EqutAction.addequt", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String addPipeAndSegment() throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       String[] sid = this.c_id.split(",");
 
       if ((this.pipeInfoBean.getPipeId() == null) || (this.pipeInfoBean.getPipeId().equals(""))) {
         String pipeName = this.pipeInfoBean.getPipeName();
         int pipeId = this.pipeInfoService.insertPipeInfoBean(this.pipeInfoBean);
         this.pipeInfoBean.setPipeId(Integer.valueOf(pipeId));
         this.pipeInfoService.pipeLog(this.pipeInfoBean);
         if ((pipeName != null) && 
           (sid != null)) {
           for (int i = 0; i < sid.length; ++i) {
             this.pipeSegmentInfoBean = new PipeSegmentInfoBean();
             this.pipeSegmentInfoBean.setPipeSegmentId(Integer.valueOf(Integer.parseInt(sid[i])));
             this.pipeSegmentInfoBean = this.pipeInfoService.getPipeSegmentInfoBean(this.pipeSegmentInfoBean);
             this.pipeSegmentInfoBean.setPipeId(Integer.valueOf(pipeId));
             this.pipeSegmentInfoBean.setPipeSegmentName(this.pipeInfoBean.getPipeName() + "(" + this.pipeSegmentInfoBean.getStartDeviceName() + "_" + this.pipeSegmentInfoBean.getEndDeviceName() + ")");
             this.pipeInfoService.updateJbPipeSegmentinfo(this.pipeSegmentInfoBean);
             this.pipeSegmentInfoBean.setLogTime(new Date());
             this.pipeSegmentInfoBean.setLogOperater(this.userInfoBean.getUsername());
             this.pipeInfoService.pipesegmentLog(this.pipeSegmentInfoBean);
           }
         }
 
         this.success = true;
       } else {
         if (sid != null) {
           for (int i = 0; i < sid.length; ++i) {
             this.pipeSegmentInfoBean = new PipeSegmentInfoBean();
             this.pipeSegmentInfoBean.setPipeSegmentId(Integer.valueOf(Integer.parseInt(sid[i])));
             this.pipeSegmentInfoBean = this.pipeInfoService.getPipeSegmentInfoBean(this.pipeSegmentInfoBean);
             this.pipeSegmentInfoBean.setPipeId(this.pipeInfoBean.getPipeId());
             this.pipeSegmentInfoBean.setPipeSegmentName(this.pipeInfoBean.getPipeName() + "(" + this.pipeSegmentInfoBean.getStartDeviceName() + "_" + this.pipeSegmentInfoBean.getEndDeviceName() + ")");
             this.pipeInfoService.updateJbPipeSegmentinfo(this.pipeSegmentInfoBean);
             this.pipeSegmentInfoBean.setLogTime(new Date());
             this.pipeSegmentInfoBean.setLogOperater(this.userInfoBean.getUsername());
             this.pipeInfoService.pipesegmentLog(this.pipeSegmentInfoBean);
           }
         }
         this.success = true;
       }
       return "addPipeAndSegment";
     } catch (Exception e) {
       log.error("RouteAction.editRoute 获取信息异常...", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public void wellDW()
     throws Exception
   {
     this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
     String lon = "0";
     String lat = "0";
     String addr = "";
     String wn = "";
     String bm = "";
     String an = "";
     String mt = "";
 
     String ct = "";
     String ba = "";
     String md = "";
     String ml = "";
     String mw = "";
     String ms = "";
     String msf = "";
     String mst = "";
     String rt = "";
     String rs = "";
     String cm = "";
     String cs = "";
     String cq = "";
     String wt = "";
     String st = "";
     try
     {
       this.wellInfoBean = new WellInfoBean();
       this.wellInfoBean.setWellId(this.eid);
 
       this.wellInfoBean = this.pipeInfoService.getWellInfoBean(this.wellInfoBean);
 
       lon = this.wellInfoBean.getLongitude();
       lat = this.wellInfoBean.getLatitude();
       wn = URLEncoder.encode(this.wellInfoBean.getWellName(), "utf-8");
       an = URLEncoder.encode(this.wellInfoBean.getAreaname(), "utf-8");
       bm = URLEncoder.encode(this.wellInfoBean.getAlias(), "utf-8");
 
       if ((mt != null) && (!("".equals(mt)))) {
         String[] manList = { "未知", "人孔", "手孔", "双人孔", "三人孔", "局前井", "地下室" };
         mt = manList[Integer.parseInt(mt)];
       } else {
         mt = "未知";
       }
       if ((ms != null) && (!("".equals(ms)))) {
         String[] msList = { "未知", "直通型", "丁字型", "十字型", "15度斜通型", "30度斜通型", "45度斜通型", "60度斜通型", "75度斜通型", "不规则型", "扇型", "拐弯型(左)", "拐弯型(右)", "手井", "方井", "穿线孔" };
         ms = msList[Integer.parseInt(ms)];
       } else {
         ms = "未知";
       }
       if ((msf != null) && (!("".equals(msf)))) {
         String[] msfList = { "未知", "特大号", "大号", "中号", "小号", "手孔" };
         msf = msfList[Integer.parseInt(msf)];
       } else {
         msf = "未知";
       }
       if ((mst != null) && (!("".equals(mst)))) {
         String[] mstList = { "未知", "砖砌体", "钢筋混凝土体", "装配式" };
         mst = mstList[Integer.parseInt(mst)];
       } else {
         mst = "未知";
       }
       mt = URLEncoder.encode(mt, "utf-8");
       ms = URLEncoder.encode(ms, "utf-8");
       msf = URLEncoder.encode(msf, "utf-8");
       mst = URLEncoder.encode(mst, "utf-8");
       rt = URLEncoder.encode(rt, "utf-8");
       rs = URLEncoder.encode(rs, "utf-8");
     }
     catch (Exception e) {
       log.error("PointAction.checkJSON", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
     }
 
     HttpServletResponse response = ServletActionContext.getResponse();
     response.setContentType("text/plain");
     response.setHeader("Charset", "UTF-8");
     PrintWriter out = response.getWriter();
     out.println("{\"lon\":\"" + lon + "\",\"lat\":\"" + lat + "\",\"addr\":\"" + addr + "\",\"wn\":\"" + wn + "\",\"an\":\"" + an + "\",\"mt\":\"" + mt + "\",\"bm\":\"" + bm + "\",\"ct\":\"" + ct + "\",\"ba\":\"" + ba + "\",\"md\":\"" + md + "\",\"ml\":\"" + ml + "\",\"mw\":\"" + mw + "\",\"ms\":\"" + ms + "\",\"msf\":\"" + msf + "\",\"mst\":\"" + mst + "\",\"rt\":\"" + rt + "\",\"rs\":\"" + rs + "\",\"cm\":\"" + cm + "\",\"cs\":\"" + cs + "\",\"cq\":\"" + cq + "\",\"wt\":\"" + wt + "\",\"st\":\"" + st + "\"}");
 
     out.flush();
     out.close();
   }
 
   public String pipeSegmentDW() throws Exception {
     this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
     WellInfoBean wellInfoBean1 = new WellInfoBean();
     WellInfoBean wellInfoBean2 = new WellInfoBean();
     PoleInfoBean poleInfoBean1 = new PoleInfoBean();
     this.wellresultList = new ArrayList();
     this.poleresultList = new ArrayList();
     try {
       this.pipeSegmentInfoBean = new PipeSegmentInfoBean();
       this.pipeSegmentInfoBean.setPipeSegmentId(Integer.valueOf(((String)this.ids.get(0)).trim()));
       this.pipeSegmentInfoBean = this.pipeInfoService.getPipeSegmentInfoBean(this.pipeSegmentInfoBean);
       String st = this.pipeSegmentInfoBean.getStartDeviceType();
       String et = this.pipeSegmentInfoBean.getEndDeviceType();
       Integer sd = this.pipeSegmentInfoBean.getStartDeviceId();
       Integer ed = this.pipeSegmentInfoBean.getEndDeviceId();
       if ((sd != null) && (sd.intValue() != 0)) {
         if ("1".equals(st)) {
           wellInfoBean1.setWellId(sd);
           wellInfoBean1 = this.pipeInfoService.getWellInfoBean(wellInfoBean1);
           this.wellresultList.add(wellInfoBean1);
         }
         else if ("2".equals(st)) {
           poleInfoBean1.setPoleId(sd);
           poleInfoBean1 = this.pipeInfoService.getPoleInfoBean(poleInfoBean1);
           this.poleresultList.add(poleInfoBean1);
         }
       }
       if ((ed != null) && (ed.intValue() != 0)) {
         if ("1".equals(et)) {
           wellInfoBean2.setWellId(ed);
           wellInfoBean2 = this.pipeInfoService.getWellInfoBean(wellInfoBean2);
           this.wellresultList.add(wellInfoBean2);
         }
         else if ("2".equals(et)) {
           poleInfoBean1.setPoleId(ed);
           poleInfoBean1 = this.pipeInfoService.getPoleInfoBean(poleInfoBean1);
           this.poleresultList.add(poleInfoBean1);
         }
       }
       return "pipeSegmentDW";
     }
     catch (Exception e) {
       log.error("PolelineAction.addpole 获取信息异常...", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String pipeDW() throws Exception
   {
     this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
     WellInfoBean wellInfoBean1 = new WellInfoBean();
     WellInfoBean wellInfoBean2 = new WellInfoBean();
     PoleInfoBean poleInfoBean1 = new PoleInfoBean();
     this.wellresultList = new ArrayList();
     this.poleresultList = new ArrayList();
     try {
       this.pipeInfoBean = new PipeInfoBean();
       this.pipeInfoBean.setPipeId(Integer.valueOf(((String)this.ids.get(0)).trim()));
       this.pipeInfoBean = this.pipeInfoService.getPipeInfoBean(this.pipeInfoBean);
       this.pipeSegmentInfoBean = new PipeSegmentInfoBean();
       this.pipeSegmentInfoBean.setPipeId(Integer.valueOf(((String)this.ids.get(0)).trim()));
       this.segresultList = this.pipeInfoService.getPipeSegmentAll(this.pipeSegmentInfoBean);
       for (int i = 0; i < this.segresultList.size(); ++i) {
         PipeSegmentInfoBean pipeSegmentInfo = (PipeSegmentInfoBean)this.segresultList.get(i);
         String st1 = pipeSegmentInfo.getStartDeviceType();
         String et1 = pipeSegmentInfo.getEndDeviceType();
         Integer sd1 = pipeSegmentInfo.getStartDeviceId();
         Integer ed1 = pipeSegmentInfo.getEndDeviceId();
         if ((sd1 != null) && (sd1.intValue() != 0)) {
           if ("1".equals(st1)) {
             wellInfoBean1 = new WellInfoBean();
             wellInfoBean1.setWellId(sd1);
             wellInfoBean1 = this.pipeInfoService.getWellInfoBean(wellInfoBean1);
             this.wellresultList.add(wellInfoBean1);
           } else if ("2".equals(st1)) {
             poleInfoBean1 = new PoleInfoBean();
             poleInfoBean1.setPoleId(sd1);
             poleInfoBean1 = this.pipeInfoService.getPoleInfoBean(poleInfoBean1);
             this.poleresultList.add(poleInfoBean1);
           }
         }
         if ((ed1 != null) && (ed1.intValue() != 0)) {
           if ("1".equals(et1)) {
             wellInfoBean2 = new WellInfoBean();
             wellInfoBean2.setWellId(ed1);
             wellInfoBean2 = this.pipeInfoService.getWellInfoBean(wellInfoBean2);
             this.wellresultList.add(wellInfoBean2);
           } else if ("2".equals(et1)) {
             poleInfoBean1 = new PoleInfoBean();
             poleInfoBean1.setPoleId(ed1);
             poleInfoBean1 = this.pipeInfoService.getPoleInfoBean(poleInfoBean1);
             this.poleresultList.add(poleInfoBean1);
           }
         }
       }
       return "pipeDW";
     }
     catch (Exception e) {
       log.error("PolelineAction.addpole 获取信息异常...", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String duili()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.wellInfoBean = new WellInfoBean();
       this.wellInfoBean.setWellId(this.wellId);
       this.wellInfoBean = this.pipeInfoService.getWellInfoBean(this.wellInfoBean);
       String wellmap = this.wellInfoBean.getWellmap();
       String faceids = "";
       for (int i = 0; i < wellmap.length(); ++i) {
         if (wellmap.charAt(i) != '0') {
           FaceInfoBean face = new FaceInfoBean();
           face.setWellId(this.wellId);
           face.setLocationNo(wellmap.charAt(i)+"");
           face = this.pipeInfoService.getFaceInfoBean(face);
           if (face != null)
             faceids = faceids + face.getFaceId() + ",";
           else
             faceids = faceids + "0,";
         }
         else {
           faceids = faceids + "0,";
         }
       }
       this.wellInfoBean.setFaceids(faceids);
       return "duiliWell";
     } catch (DataAccessException e) {
       log.error("EqutAction.editequt", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       return "error";
     } catch (Exception e) {
       log.error("EqutAction.editequt", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String updateWellMap()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.wellInfoBean = new WellInfoBean();
       this.wellInfoBean.setWellId(this.wellInfo.getWellId());
       this.wellInfoBean = this.pipeInfoService.getWellInfoBean(this.wellInfoBean);
       this.wellInfoBean.setWellmap(this.wellInfo.getWellmap());
       List wellList = new ArrayList();
       wellList.add(this.wellInfoBean);
 
       List faceList = new ArrayList();
       String wellMap = this.wellInfoBean.getWellmap();
       String[] faceids = this.wellInfo.getFaceids().split(",");
       for (int i = 0; i < wellMap.length(); ++i) {
         if ((wellMap.charAt(i) == '0') || 
           (!(faceids[i].equals("0")))) continue;
         FaceInfoBean f = new FaceInfoBean();
         f.setWellId(this.wellInfo.getWellId());
         f.setCols(Integer.valueOf(1));
         f.setRows(Integer.valueOf(1));
         f.setLocationNo(wellMap.charAt(i)+"");
         faceList.add(f);
       }
       this.wellInfoBean = new WellInfoBean();
       this.wellInfoBean.setWellId(this.wellInfo.getWellId());
       this.wellInfoBean = this.pipeInfoService.getWellInfoBean(this.wellInfoBean);
       String wellmap = this.wellInfoBean.getWellmap();
       this.faceIds = "";
       for (int i = 0; i < wellmap.length(); ++i) {
         if (wellmap.charAt(i) != '0') {
           FaceInfoBean face = new FaceInfoBean();
           face.setWellId(this.wellInfo.getWellId());
           face.setLocationNo(wellmap.charAt(i)+"");
           face = this.pipeInfoService.getFaceInfoBean(face);
           if (face != null)
           {
             PipeAction tmp424_423 = this; tmp424_423.faceIds = tmp424_423.faceIds + face.getFaceId() + ",";
           } else {
             this.faceIds += "0,";
           }
         } else {
           this.faceIds += "0,";
         }
       }
       return "updateWellMap";
     } catch (Exception e) {
       log.error("PipeAction.updateResource", e);
       e.printStackTrace();
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String updateFaceRelation()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       Integer oppoFaceId = this.faceInfo.getOppoFaceId();
 
       this.faceInfo = this.pipeInfoService.getFaceInfoBean(this.faceInfo);
       this.faceInfo.setOppoFaceId(oppoFaceId);
       FaceInfoBean oppoFaceInfo = new FaceInfoBean();
       oppoFaceInfo.setFaceId(oppoFaceId);
       oppoFaceInfo = this.pipeInfoService.getFaceInfoBean(oppoFaceInfo);
 
       WellInfoBean w1 = new WellInfoBean();
       w1.setWellId(this.faceInfo.getWellId());
       w1 = this.pipeInfoService.getWell(w1);
 
       WellInfoBean w2 = new WellInfoBean();
       w2.setWellId(oppoFaceInfo.getWellId());
       w2 = this.pipeInfoService.getWell(w2);
 
       PipeSegmentInfoBean pSeg = new PipeSegmentInfoBean();
       pSeg.setPipeId(Integer.valueOf(0));
       pSeg.setPipeSegmentId(Integer.valueOf(0));
       pSeg.setPipeSegmentName(w1.getWellNameSub() + "-" + w2.getWellNameSub());
       pSeg.setPipeSegmentType("1");
       pSeg.setStartDeviceId(w1.getWellId());
       pSeg.setStartDeviceType("1");
       pSeg.setStartDeviceName(w1.getWellNameSub());
       pSeg.setEndDeviceId(w2.getWellId());
       pSeg.setEndDeviceType("1");
       pSeg.setEndDeviceName(w2.getWellNameSub());
       pSeg.setAreano(w1.getAreano());
       pSeg.setAreaname(w1.getAreaname());
       List pSegList = new ArrayList();
       pSegList.add(pSeg);
       return "updatefacerelation";
     } catch (Exception e) {
       log.error("PipeAction.updateResource", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String updateFace()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       Integer oppoFaceId = this.pipeInfoService.getOppoFaceId(this.faceInfo);
       this.faceInfo.setOppoFaceId(oppoFaceId);
       List faceList = new ArrayList();
       faceList.add(this.faceInfo);
       return "updateface";
     } catch (Exception e) {
       log.error("PipeAction.updateResource", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String getTubeNameListForFace()
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.faceInfo = this.pipeInfoService.getFaceInfoBean(this.faceInfo);
       Integer oppoFaceId = this.pipeInfoService.getOppoFaceId(this.faceInfo);
       if (oppoFaceId != null) {
         FaceInfoBean oppoFace = new FaceInfoBean();
         oppoFace.setFaceId(oppoFaceId);
         oppoFace = this.pipeInfoService.getFaceInfoBean(oppoFace);
         if (oppoFace != null) {
           WellInfoBean w2 = new WellInfoBean();
           w2.setWellId(oppoFace.getWellId());
           w2 = this.pipeInfoService.getWell(w2);
 
           this.faceInfo.setOppoWellName(w2.getWellNameSub());
           this.faceInfo.setOppoWellId(w2.getWellId());
           this.faceInfo.setOppolocationNo(oppoFace.getLocationNo());
           this.faceInfo.setOppoFaceId(oppoFace.getFaceId());
         }
       }
       this.tubeInfoBean = new TubeInfoBean();
       this.tubeInfoBean.setFaceId(this.faceInfo.getFaceId()+"");
       List <TubeInfoBean>tubeList = this.pipeInfoService.getTubeList(this.tubeInfoBean);
       String tubeNames = "";
       if (tubeList != null) {
         for (TubeInfoBean t : tubeList) {
           String ifath = t.getIsFather();
           if ("1".equals(ifath)) {
             TubeInfoBean tson = new TubeInfoBean();
             tson.setFatherPoreId(t.getTubeId());
             List<TubeInfoBean> tubeSonList = this.pipeInfoService.getTubeList(tson);
             Boolean hb = Boolean.valueOf(false);
             for (TubeInfoBean t1 : tubeSonList) {
               if ((t1.getBearCableSegmentId() != null) && (!("".equals(t1.getBearCableSegmentId())))) {
                 hb = Boolean.valueOf(true);
               }
             }
             if (hb.booleanValue()) {
               tubeNames = tubeNames + t.getTubeNumber() + ",";
             }
 
           }
           else if ((t.getBearCableSegmentId() != null) && (!("".equals(t.getBearCableSegmentId())))) {
             tubeNames = tubeNames + t.getTubeNumber() + ",";
           }
         }
       }
 
       this.faceInfo.setTubeNames(tubeNames);
       return "TubeNameForFace";
     } catch (Exception e) {
       log.error("PipeAction.getTubeNameListForFace", e);
       e.printStackTrace();
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String saveTube()
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       if (this.tubeInfoBean == null) {
         this.tubeInfoBean = new TubeInfoBean();
       }
       this.tubeInfoBean.setCuser(this.userInfoBean.getUsername());
       if ((this.tubeInfoBean.getTubeId() != null) && (this.tubeInfoBean.getTubeId().intValue() != 0)) {
         TubeInfoBean tubeInfo = new TubeInfoBean();
         tubeInfo.setTubeId(this.tubeInfoBean.getTubeId());
         tubeInfo = this.pipeInfoService.getTubeInfoBean(tubeInfo);
         this.tubeInfoBean.setCstate(tubeInfo.getCstate());
         this.tubeInfoBean.setSubTubeAmount(tubeInfo.getSubTubeAmount());
       }
       return "saveTube";
     } catch (Exception e) {
       log.error("PipeAction.updateResource", e);
       e.printStackTrace();
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
     return "error";
   }
 
   public String getTube()
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       List tList = this.pipeInfoService.getTubeList(this.tubeInfoBean);
       if ((tList != null) && (tList.size() != 0)) {
         this.tubeInfoBean = ((TubeInfoBean)tList.get(0));
         if ("1".equals(this.tubeInfoBean.getIsFather())) {
           TubeInfoBean t = new TubeInfoBean();
           t.setFaceId(this.tubeInfoBean.getFaceId());
           t.setFatherPoreId(this.tubeInfoBean.getTubeId());
           List <TubeInfoBean>sonList = this.pipeInfoService.getTubeList(t);
           if (sonList != null) {
             String tubeNames = "";
             for (TubeInfoBean tube : sonList) {
               tubeNames = tubeNames + tube.getTubeNumber();
             }
             this.tubeInfoBean.setTubeNames(tubeNames);
           }
         }
       } else {
         this.tubeInfoBean = new TubeInfoBean();
       }
       return "loadTube";
     } catch (Exception e) {
       log.error("PipeAction.updateResource", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       e.printStackTrace(); }
     return "error";
   }
 
   public void searchledupJSON()
   {
     try {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.ledupInfoBean = new LedupInfoBean();
       this.ledupInfoBean.setPoleId(this.poleId);
       this.ledupInfoBean.setWellId(this.wellId);
       this.ledupInfoBean = this.pipeInfoService.getLedupInfoBean(this.ledupInfoBean);
 
       HttpServletResponse response = ServletActionContext.getResponse();
       response.setContentType("text/json");
       response.setCharacterEncoding("utf-8");
       response.getWriter().write(JSONArray.fromObject(this.ledupInfoBean).toString());
       response.getWriter().flush();
     } catch (Exception e) {
       log.error("PipeAction.searchledupJSON", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       e.printStackTrace();
     }
   }
 
   public String searchPipeSegmentToCable()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.tubeInfoBean = new TubeInfoBean();
       this.tubeInfoBean.setPipeSegmentId((String)this.ids.get(0));
       this.tuberesultList = this.pipeInfoService.getTubeList(this.tubeInfoBean);
       if (this.tuberesultList.size() != 0) {
         for (TubeInfoBean tubeinfo : this.tuberesultList) {
           this.cableRouteInfoBean = new CableRouteInfoBean();
           this.cableRouteInfoBean.setTubeId(tubeinfo.getTubeId());
           this.cableRouteList = this.pipeInfoService.getCableRouteList(this.cableRouteInfoBean);
           if (this.cableRouteList.size() != 0) {
             this.success = false;
           }
           this.success = true;
         }
       }
       else {
         this.success = true;
       }
       return "vetifyCableToPipeSegment";
     } catch (Exception e) {
       log.error("PointAction.checkpolelinesegmentJSON", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       e.printStackTrace(); }
     return "error";
   }
 
   public String searchWellToCable()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.tubeInfoBean = new TubeInfoBean();
       this.tubeInfoBean.setWellId(((String)this.ids.get(0)).trim());
       this.tuberesultList = this.pipeInfoService.getTubeList(this.tubeInfoBean);
       if (this.tuberesultList.size() != 0) {
         for (TubeInfoBean tubeinfo : this.tuberesultList) {
           this.cableRouteInfoBean = new CableRouteInfoBean();
           this.cableRouteInfoBean.setTubeId(tubeinfo.getTubeId());
           this.cableRouteList = this.pipeInfoService.getCableRouteList(this.cableRouteInfoBean);
           if (this.cableRouteList.size() != 0) {
             this.success = false;
           }
           this.success = true;
         }
       }
       else {
         this.success = true;
       }
            return "vetifyTubeToWell";
     } catch (Exception e) {
       log.error("PipeAction.searchWellToCable", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       e.printStackTrace(); }
     return "error";
   }
 
   public String facerelation()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.wellInfoBean = this.pipeInfoService.getWellfaceRelation(this.wellInfoBean);
       return "loadWell";
     } catch (Exception e) {
       log.error("PointAction.facerelation", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       e.printStackTrace(); }
     return "error";
   }
 
   public String checkfacetube()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.faceInfo = new FaceInfoBean();
       this.faceInfo.setWellId(this.startDeviceId);
       this.faceInfo.setLocationNo(this.startFaceNo);
       this.faceInfo = this.pipeInfoService.getFaceInfoBean(this.faceInfo);
       FaceInfoBean oppoFaceInfo = new FaceInfoBean();
       oppoFaceInfo.setWellId(this.endDeviceId);
       oppoFaceInfo.setLocationNo(this.endFaceNo);
       oppoFaceInfo = this.pipeInfoService.getFaceInfoBean(oppoFaceInfo);
       if ((this.faceInfo.getCols().equals(oppoFaceInfo.getCols())) && (this.faceInfo.getRows().equals(oppoFaceInfo.getRows())))
         this.success = false;
       else {
         this.success = true;
       }
       return "checkFaceTube";
     } catch (Exception e) {
       log.error("PointAction.facerelation", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       e.printStackTrace(); }
     return "error";
   }
 
   public String checktubecable()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.tubeInfoBean = new TubeInfoBean();
       this.tubeInfoBean.setWellId(this.wellId.toString());
       this.tubeInfoBean.setFaceId(this.faceId);
       this.tubeInfoBean.setPipeSegmentId(this.pipeSegmentId+"");
       this.tuberesultList = this.pipeInfoService.getTubeList(this.tubeInfoBean);
       if (this.tuberesultList.size() != 0) {
         for (TubeInfoBean tubeinfo : this.tuberesultList)
           if (!(tubeinfo.getIsFather().equals("2"))) {
             this.cableRouteInfoBean = new CableRouteInfoBean();
             this.cableRouteInfoBean.setTubeId(tubeinfo.getTubeId());
             this.cableRouteList = this.pipeInfoService.getCableRouteList(this.cableRouteInfoBean);
             if (this.cableRouteList.size() != 0) {
               this.success = false;
             }
             this.success = true;
           }
           else {
             this.success = true;
           }
       }
       else {
         this.success = true;
       }
       return "checktubecable";
     } catch (Exception e) {
       log.error("PipeAction.checktubecable", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       e.printStackTrace(); }
     return "error";
   }
 
   public String updateFaceDisTube()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       String disableTubes = this.faceInfo.getDisableTubes();
       this.faceInfo = this.pipeInfoService.getFaceInfoBean1(this.faceInfo);
       this.faceInfo.setDisableTubes(disableTubes);
       List faceList = new ArrayList();
       faceList.add(this.faceInfo);
       this.success = true;
       return "updateFaceDisTube";
     } catch (Exception e) {
       log.error("PipeAction.updateFaceDisTube", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       e.printStackTrace(); }
     return "error";
   }
 
   public String getface()
     throws Exception
   {
     try
     {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       this.faceInfo = this.pipeInfoService.getFaceInfoBean1(this.faceInfo);
       return "faceInfo";
     } catch (Exception e) {
       log.error("PipeAction.getface", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       e.printStackTrace(); }
     return "error";
   }
 
   public String exportwell() throws Exception
   {
     try {
       this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
       String randomPath = "temp_" + CommonUtil.getUuid();
       String dlTempPath = ServletActionContext.getServletContext().getRealPath("/") + File.separator + "downloadtemp" + File.separator + randomPath;
       File dirfile = new File(dlTempPath);
       dirfile.mkdirs();
 
       UserInfoBean userBean = (UserInfoBean)getSession().getAttribute("userBean");
       this.pipeInfoBean = new PipeInfoBean();
 
       String ecodeString = "";
 
       if ((this.pipeids != null) || (this.pipeids != "")) {
         String[] temp = this.pipeids.split(",");
         String tempeid = "";
         String modelPath = "";
         modelPath = getServletContext().getRealPath("/") + "tmpl" + File.separator + "excel_model_well.xls";
 
         POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(modelPath));
         HSSFWorkbook wb = new HSSFWorkbook(fs);
 
         HSSFCellStyle style = wb.createCellStyle();
         style.setFillForegroundColor(IndexedColors.TAN.getIndex());
         style.setFillPattern((short) 1);
 
         HSSFFont font = wb.createFont();
         font.setFontName("宋体");
         font.setBoldweight((short) 700);
         font.setFontHeightInPoints((short) 11);
         style.setFont(font);
 
         for (int i = 0; i < temp.length; ++i) {
           HSSFSheet sheet = wb.getSheetAt(0);
           HSSFRow row = sheet.createRow(0);
           HSSFCell cell1 = row.createCell(0);
           HSSFCell cell2 = row.createCell(1);
           HSSFCell cell3 = row.createCell(2);
           HSSFCell cell4 = row.createCell(3);
           HSSFCell cell5 = row.createCell(4);
           HSSFCell cell6 = row.createCell(5);
           HSSFCell cell7 = row.createCell(6);
           HSSFCell cell8 = row.createCell(7);
           HSSFCell cell9 = row.createCell(8);
           HSSFCell cell10 = row.createCell(9);
           HSSFCell cell11 = row.createCell(10);
 
           cell1.setCellType(1);
           cell2.setCellType(1);
           cell3.setCellType(1);
           cell4.setCellType(1);
           cell5.setCellType(1);
           cell6.setCellType(1);
           cell7.setCellType(1);
           cell8.setCellType(1);
           cell9.setCellType(1);
           cell10.setCellType(1);
           cell11.setCellType(1);
 
           cell1.setCellStyle(style);
           cell2.setCellStyle(style);
           cell3.setCellStyle(style);
           cell4.setCellStyle(style);
           cell5.setCellStyle(style);
           cell6.setCellStyle(style);
           cell7.setCellStyle(style);
           cell8.setCellStyle(style);
           cell9.setCellStyle(style);
           cell10.setCellStyle(style);
           cell11.setCellStyle(style);
 
           cell1.setCellValue("编号");
           cell2.setCellValue("道路名称");
           cell3.setCellValue("人井种类");
           cell4.setCellValue("人井类型");
           cell5.setCellValue("内部净长度");
           cell6.setCellValue("内部净宽度");
           cell7.setCellValue("内部净高度");
           cell8.setCellValue("经度");
           cell9.setCellValue("纬度");
           cell10.setCellValue("管段用途");
           cell11.setCellValue("管段模板");
           if ((temp[i] != null) || (temp[i] != "")) {
             tempeid = temp[i];
             this.pipeInfoBean = new PipeInfoBean();
             this.pipeInfoBean.setPipeId(Integer.valueOf(tempeid));
             List lists = this.pipeInfoService.getWellListOfPL(this.pipeInfoBean);
             for (int j = 0; j < lists.size(); ++j) {
               WellInfoBean teBean = (WellInfoBean)lists.get(j);
               if (teBean != null) {
                 row = sheet.createRow((short)j + 1);
                 cell1 = row.createCell(0);
                 cell2 = row.createCell(1);
                 cell3 = row.createCell(2);
                 cell4 = row.createCell(3);
                 cell5 = row.createCell(4);
                 cell6 = row.createCell(5);
                 cell7 = row.createCell(6);
                 cell8 = row.createCell(7);
                 cell9 = row.createCell(8);
                 cell10 = row.createCell(9);
                 cell11 = row.createCell(10);
 
                 cell1.setCellType(1);
                 cell2.setCellType(1);
                 cell3.setCellType(1);
                 cell4.setCellType(1);
                 cell5.setCellType(1);
                 cell6.setCellType(1);
                 cell7.setCellType(1);
                 cell8.setCellType(1);
                 cell9.setCellType(1);
                 cell10.setCellType(1);
                 cell11.setCellType(1);
 
                 cell1.setCellValue(teBean.getWellNameSub());
                 String name = teBean.getWellNameSub();
                 if ((name.contains("东#")) || (name.contains("西#")) || (name.contains("南#")) || (name.contains("北#")))
                   name = name.substring(0, name.indexOf("#") - 1);
                 else {
                   name = name.substring(0, name.indexOf("#"));
                 }
                 cell2.setCellValue(name);
                 String str = teBean.getManholeShape();
                 if (str.equals("1"))
                   str = "直通型";
                 else if (str.equals("2"))
                   str = "丁字型";
                 else if (str.equals("3"))
                   str = "十字型";
                 else if (str.equals("4"))
                   str = "15度斜通型";
                 else if (str.equals("5"))
                   str = "30度斜通型";
                 else if (str.equals("6"))
                   str = "45度斜通型";
                 else if (str.equals("7"))
                   str = "60度斜通型";
                 else if (str.equals("8"))
                   str = "75度斜通型";
                 else if (str.equals("9"))
                   str = "不规则型";
                 else if (str.equals("10"))
                   str = "扇型";
                 else if (str.equals("11"))
                   str = "拐弯型(左)";
                 else if (str.equals("12"))
                   str = "拐弯型(右)";
                 else if (str.equals("13"))
                   str = "手井";
                 else if (str.equals("14"))
                   str = "方井";
                 else if (str.equals("15"))
                   str = "穿线孔";
                 else {
                   str = "";
                 }
                 cell3.setCellValue(str);
 
                 cell4.setCellValue(str);
                 if ((teBean.getLongitude() != null) && (teBean.getLatitude() != null)) {
                   String[] strlonlat = PositionUtil.latlonTran(Double.parseDouble(teBean.getLongitude()), Double.parseDouble(teBean.getLatitude()));
                   cell8.setCellValue(strlonlat[0]);
                   cell9.setCellValue(strlonlat[1]);
                 } else {
                   return "error";
                 }
 
                 cell10.setCellValue("地井管段");
                 cell11.setCellValue("HLDB1*4");
               }
             }
             if (tempeid != null) {
               ecodeString = tempeid + "_";
             }
             ecodeString = ecodeString.replace('/', '_');
             ecodeString = ecodeString.replace('\\', '_');
             ecodeString = ecodeString.replace('*', '_');
             ecodeString = ecodeString.replace('"', '_');
             ecodeString = ecodeString.replace(':', '_');
             ecodeString = ecodeString.replace('<', '_');
             ecodeString = ecodeString.replace('>', '_');
             ecodeString = ecodeString.replace('|', '_');
             ecodeString = ecodeString.replace('?', '_');
           }
           String filename = dlTempPath + File.separator + ecodeString + "井信息列表.xls";
           FileOutputStream fo = new FileOutputStream(filename);
           wb.write(fo);
           fo.close();
         }
         String sourceFilePath = dlTempPath + File.separator;
         String zipFilePath = getServletContext().getRealPath("/") + "ziptemp";
         boolean flag = FileToZip.fileToZip(sourceFilePath, zipFilePath, "井信息");
         if (flag)
           this.filepath = "ziptemp/井信息.zip";
         else
           this.filepath = "downloadtemp/" + randomPath + "/" + ecodeString + "井信息列表.xls";
       }
     }
     catch (DataAccessException e) {
       log.error("PolelineAction.exportpole", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       return "error";
     } catch (Exception e) {
       log.error("PipeAction.exportwell 获取信息异常...", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       return "error";
     }
     return "exportsuccess";
   }
 
   public String getFace() {
     try {
       this.faceInfo = this.pipeInfoService.getFace(this.faceInfo);
     } catch (Exception e) {
       log.error("PipeAction.getFace 获取信息异常...", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       return "error";
     }
     return "getFace";
   }
 
   public String getFacetube() {
     try {
       this.fatherTube = this.pipeInfoService.getFacetube(this.fatherTube);
     } catch (Exception e) {
       log.error("PipeAction.getFacetube 获取信息异常...", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       return "error";
     }
     return "getFacetube";
   }
 
   public String getLedup() {
     try {
       if (this.ledupInfoBean == null) {
         this.ledupInfoBean = new LedupInfoBean();
       }
       this.ledupInfoBean.setLimit(this.limit);
       this.ledupInfoBean.setStart(this.start);
       this.ledupInfoBean = this.pipeInfoService.getLedupInfoBean(this.ledupInfoBean);
     } catch (Exception e) {
       log.error("PipeAction.getLedup 获取信息异常...", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       return "error";
     }
     return "getLedup";
   }
 
   public String getFaceId() {
     return this.faceId; }
 
   public void setFaceId(String faceId) {
     this.faceId = faceId; }
 
   public String getFacebasic() {
     return this.facebasic; }
 
   public void setFacebasic(String facebasic) {
     this.facebasic = facebasic; }
 
   public Integer getPoleId() {
     return this.poleId; }
 
   public void setPoleId(Integer poleId) {
     this.poleId = poleId; }
 
   public String getMapstr() {
     return this.mapstr; }
 
   public void setMapstr(String mapstr) {
     this.mapstr = mapstr; }
 
   public String getHeng() {
     return this.heng; }
 
   public void setHeng(String heng) {
     this.heng = heng; }
 
   public String getShu() {
     return this.shu; }
 
   public void setShu(String shu) {
     this.shu = shu; }
 
   public UserInfoBean getUserInfoBean() {
     return this.userInfoBean;
   }
 
   public void setUserInfoBean(UserInfoBean userInfoBean)
   {
     this.userInfoBean = userInfoBean;
   }
 
   public PipeInfoBean getPipeInfoBean()
   {
     return this.pipeInfoBean;
   }
 
   public void setPipeInfoBean(PipeInfoBean pipeInfoBean)
   {
     this.pipeInfoBean = pipeInfoBean;
   }
 
   public PipeInfoService getPipeInfoService()
   {
     return this.pipeInfoService;
   }
 
   public void setPipeInfoService(PipeInfoService pipeInfoService)
   {
     this.pipeInfoService = pipeInfoService;
   }
 
   public List<PipeInfoBean> getResultList()
   {
     return this.resultList;
   }
 
   public void setResultList(List<PipeInfoBean> resultList)
   {
     this.resultList = resultList;
   }
 
   public ErrorMessage getErrorMessage()
   {
     return this.errorMessage;
   }
 
   public void setErrorMessage(ErrorMessage errorMessage)
   {
     this.errorMessage = errorMessage;
   }
 
   public static String getMODULENAME()
   {
     return "pipemanage";
   }
 
   public static Logger getLog()
   {
     return log;
   }
 
   public Integer getPipeId() {
     return this.pipeId;
   }
 
   public void setPipeId(Integer pipeId) {
     this.pipeId = pipeId; }
 
   public List<PipeSegmentInfoBean> getSegresultList() {
     return this.segresultList; }
 
   public void setSegresultList(List<PipeSegmentInfoBean> segresultList) {
     this.segresultList = segresultList; }
 
   public PipeSegmentInfoBean getPipeSegmentInfoBean() {
     return this.pipeSegmentInfoBean; }
 
   public void setPipeSegmentInfoBean(PipeSegmentInfoBean pipeSegmentInfoBean) {
     this.pipeSegmentInfoBean = pipeSegmentInfoBean; }
 
   public Integer getPipeSegmentId() {
     return this.pipeSegmentId; }
 
   public void setPipeSegmentId(Integer pipeSegmentId) {
     this.pipeSegmentId = pipeSegmentId; }
 
   public WellInfoBean getWellInfoBean() {
     return this.wellInfoBean; }
 
   public void setWellInfoBean(WellInfoBean wellInfoBean) {
     this.wellInfoBean = wellInfoBean; }
 
   public TubeInfoBean getTubeInfoBean() {
     return this.tubeInfoBean; }
 
   public void setTubeInfoBean(TubeInfoBean tubeInfoBean) {
     this.tubeInfoBean = tubeInfoBean; }
 
   public List<TubeInfoBean> getTuberesultList() {
     return this.tuberesultList; }
 
   public void setTuberesultList(List<TubeInfoBean> tuberesultList) {
     this.tuberesultList = tuberesultList; }
 
   public Integer getTubeId() {
     return this.tubeId; }
 
   public void setTubeId(Integer tubeId) {
     this.tubeId = tubeId;
   }
 
   public String getWellAddress() {
     return this.wellAddress; }
 
   public void setWellAddress(String wellAddress) {
     this.wellAddress = wellAddress; }
 
   public String getManholeTypeEnumId() {
     return this.manholeTypeEnumId; }
 
   public void setManholeTypeEnumId(String manholeTypeEnumId) {
     this.manholeTypeEnumId = manholeTypeEnumId; }
 
   public List<WellInfoBean> getWellresultList() {
     return this.wellresultList; }
 
   public void setWellresultList(List<WellInfoBean> wellresultList) {
     this.wellresultList = wellresultList; }
 
   public PoleInfoBean getPoleInfoBean() {
     return this.poleInfoBean; }
 
   public void setPoleInfoBean(PoleInfoBean poleInfoBean) {
     this.poleInfoBean = poleInfoBean; }
 
   public String getWellName() {
     return this.wellName; }
 
   public void setWellName(String wellName) {
     this.wellName = wellName;
   }
 
   public String getStartDeviceName() {
     return this.startDeviceName; }
 
   public void setStartDeviceName(String startDeviceName) {
     this.startDeviceName = startDeviceName; }
 
   public String getEndDeviceName() {
     return this.endDeviceName; }
 
   public void setEndDeviceName(String endDeviceName) {
     this.endDeviceName = endDeviceName; }
 
   public String getPoleAddress() {
     return this.poleAddress; }
 
   public void setPoleAddress(String poleAddress) {
     this.poleAddress = poleAddress; }
 
   public String getPoleName() {
     return this.poleName; }
 
   public void setPoleName(String poleName) {
     this.poleName = poleName; }
 
   public List<PoleInfoBean> getPoleresultList() {
     return this.poleresultList; }
 
   public void setPoleresultList(List<PoleInfoBean> poleresultList) {
     this.poleresultList = poleresultList; }
 
   public Integer getFatherPoreId() {
     return this.fatherPoreId; }
 
   public void setFatherPoreId(Integer fatherPoreId) {
     this.fatherPoreId = fatherPoreId; }
 
   public Integer getWellId() {
     return this.wellId; }
 
   public void setWellId(Integer wellId) {
     this.wellId = wellId; }
 
   public String getPipeSegmentType() {
     return this.pipeSegmentType; }
 
   public void setPipeSegmentType(String pipeSegmentType) {
     this.pipeSegmentType = pipeSegmentType;
   }
 
   public Integer getStartDeviceId() {
     return this.startDeviceId; }
 
   public void setStartDeviceId(Integer startDeviceId) {
     this.startDeviceId = startDeviceId; }
 
   public Integer getEndDeviceId() {
     return this.endDeviceId; }
 
   public void setEndDeviceId(Integer endDeviceId) {
     this.endDeviceId = endDeviceId; }
 
   public LedupInfoBean getLedupInfoBean() {
     return this.ledupInfoBean; }
 
   public void setLedupInfoBean(LedupInfoBean ledupInfoBean) {
     this.ledupInfoBean = ledupInfoBean; }
 
   public Integer getFatherId() {
     return this.fatherId; }
 
   public void setFatherId(Integer fatherId) {
     this.fatherId = fatherId; }
 
   public TubeInfoBean getFatherTube() {
     return this.fatherTube; }
 
   public void setFatherTube(TubeInfoBean fatherTube) {
     this.fatherTube = fatherTube; }
 
   public String getStrPipe() {
     return this.strPipe; }
 
   public void setStrPipe(String strPipe) {
     this.strPipe = strPipe; }
 
   public String getStrPipeStement() {
     return this.strPipeStement; }
 
   public void setStrPipeStement(String strPipeStement) {
     this.strPipeStement = strPipeStement; }
 
   public String getPipeSegmentName() {
     return this.pipeSegmentName; }
 
   public void setPipeSegmentName(String pipeSegmentName) {
     this.pipeSegmentName = pipeSegmentName; }
 
   public String getPipeName() {
     return this.pipeName; }
 
   public void setPipeName(String pipeName) {
     this.pipeName = pipeName; }
 
   public String getPipeLevel() {
     return this.pipeLevel; }
 
   public void setPipeLevel(String pipeLevel) {
     this.pipeLevel = pipeLevel; }
 
   public Boolean getFlag() {
     return this.flag; }
 
   public void setFlag(Boolean flag) {
     this.flag = flag; }
 
   public String getTubeName() {
     return this.tubeName; }
 
   public void setTubeName(String tubeName) {
     this.tubeName = tubeName; }
 
   public String getC_id() {
     return this.c_id; }
 
   public void setC_id(String c_id) {
     this.c_id = c_id; }
 
   public String getC_receivername() {
     return this.c_receivername; }
 
   public void setC_receivername(String c_receivername) {
     this.c_receivername = c_receivername; }
 
   public Integer getEid() {
     return this.eid; }
 
   public void setEid(Integer eid) {
     this.eid = eid; }
 
   public String getAreaname() {
     return this.areaname; }
 
   public void setAreaname(String areaname) {
     this.areaname = areaname;
   }
 
   public String[] getStartFaceArray() {
     return this.startFaceArray; }
 
   public void setStartFaceArray(String[] startFaceArray) {
     this.startFaceArray = startFaceArray; }
 
   public String[] getEndFaceArray() {
     return this.endFaceArray; }
 
   public void setEndFaceArray(String[] endFaceArray) {
     this.endFaceArray = endFaceArray; }
 
   public String getStartFaceNo() {
     return this.startFaceNo; }
 
   public void setStartFaceNo(String startFaceNo) {
     this.startFaceNo = startFaceNo; }
 
   public String getEndFaceNo() {
     return this.endFaceNo; }
 
   public void setEndFaceNo(String endFaceNo) {
     this.endFaceNo = endFaceNo; }
 
   public WellInfoBean getWellInfo() {
     return this.wellInfo; }
 
   public void setWellInfo(WellInfoBean wellInfo) {
     this.wellInfo = wellInfo; }
 
   public FaceInfoBean getFaceInfo() {
     return this.faceInfo; }
 
   public void setFaceInfo(FaceInfoBean faceInfo) {
     this.faceInfo = faceInfo;
   }
 
   public String getCname() {
     return this.cname; }
 
   public void setCname(String cname) {
     this.cname = cname; }
 
   public String getNum() {
     return this.num; }
 
   public void setNum(String num) {
     this.num = num; }
 
   public String getLocationNo() {
     return this.locationNo; }
 
   public void setLocationNo(String locationNo) {
     this.locationNo = locationNo; }
 
   public CableRouteInfoBean getCableRouteInfoBean() {
     return this.cableRouteInfoBean; }
 
   public void setCableRouteInfoBean(CableRouteInfoBean cableRouteInfoBean) {
     this.cableRouteInfoBean = cableRouteInfoBean; }
 
   public List<CableRouteInfoBean> getCableRouteList() {
     return this.cableRouteList; }
 
   public void setCableRouteList(List<CableRouteInfoBean> cableRouteList) {
     this.cableRouteList = cableRouteList; }
 
   public String getDirection() {
     return this.direction; }
 
   public void setDirection(String direction) {
     this.direction = direction; }
 
   public String getCdirection() {
     return this.cdirection; }
 
   public void setCdirection(String cdirection) {
     this.cdirection = cdirection; }
 
   public Integer getCwellNo() {
     return this.cwellNo; }
 
   public void setCwellNo(Integer cwellNo) {
     this.cwellNo = cwellNo; }
 
   public Integer getCwellSubNo() {
     return this.cwellSubNo; }
 
   public void setCwellSubNo(Integer cwellSubNo) {
     this.cwellSubNo = cwellSubNo; }
 
   public String getWellNo() {
     return this.wellNo; }
 
   public void setWellNo(String wellNo) {
     this.wellNo = wellNo; }
 
   public String getWellSubNo() {
     return this.wellSubNo; }
 
   public void setWellSubNo(String wellSubNo) {
     this.wellSubNo = wellSubNo; }
 
   public Integer getLimit() {
     return this.limit; }
 
   public void setLimit(Integer limit) {
     this.limit = limit; }
 
   public Integer getStart() {
     return this.start; }
 
   public void setStart(Integer start) {
     this.start = start; }
 
   public boolean isSuccess() {
     return this.success; }
 
   public void setSuccess(boolean success) {
     this.success = success; }
 
   public List<String> getIds() {
     return this.ids; }
 
   public void setIds(List<String> ids) {
     this.ids = ids; }
 
   public String getDeleteMsg() {
     return this.deleteMsg; }
 
   public void setDeleteMsg(String deleteMsg) {
     this.deleteMsg = deleteMsg; }
 
   public String getFaceIds() {
     return this.faceIds; }
 
   public void setFaceIds(String faceIds) {
     this.faceIds = faceIds; }
 
   public String getPipeids() {
     return this.pipeids; }
 
   public void setPipeids(String pipeids) {
     this.pipeids = pipeids; }
 
   public String getFilepath() {
     return this.filepath; }
 
   public void setFilepath(String filepath) {
     this.filepath = filepath;
   }
 }

