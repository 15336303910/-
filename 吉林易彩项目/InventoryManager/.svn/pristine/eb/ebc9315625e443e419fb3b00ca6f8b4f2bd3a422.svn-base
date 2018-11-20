package manage.poleline.web;

import base.exceptions.DataAccessException;
import base.util.CommonUtil;
import base.util.ErrorMessage;
import base.util.FileToZip;
import base.util.PositionUtil;
import base.web.PaginationAction;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import manage.pipe.pojo.PipeInfoBean;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.pipe.service.PipeInfoService;
import manage.poleline.pojo.PoleInfoBean;
import manage.poleline.pojo.PolelineInfoBean;
import manage.poleline.pojo.PolelineSegmentInfoBean;
import manage.poleline.pojo.SupportInfoBean;
import manage.poleline.pojo.SuspensionWireInfoBean;
import manage.poleline.pojo.SuspensionWireSegInfoBean;
import manage.poleline.service.PolelineInfoService;
import manage.route.pojo.CableRouteInfoBean;
import manage.user.pojo.UserInfoBean;
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

public class PolelineAction extends PaginationAction
{
  private static final Logger log = Logger.getLogger(PolelineAction.class);
  private List<PolelineInfoBean> resultList;
  private UserInfoBean userInfoBean;
  private PipeInfoService pipeInfoService;
  private PolelineInfoService polelineInfoService;
  private PolelineInfoBean polelineInfoBean;
  private PolelineSegmentInfoBean polelineSegmentInfoBean;
  private List<PolelineSegmentInfoBean> resultList1;
  private PoleInfoBean poleInfoBean;
  private List<PoleInfoBean> resultList2;
  private PipeInfoBean pipeInfoBean;
  private List<PipeInfoBean> resultPipeList;
  private PipeSegmentInfoBean pipeSegmentInfoBean;
  private List<PipeSegmentInfoBean> resultPipeSegmentList;
  private SuspensionWireInfoBean suspension;
  private SuspensionWireSegInfoBean suspensionseg;
  private SupportInfoBean support;
  private Integer poleLineId;
  private String poleLineName;
  private String poleLineCode;
  private Integer maintenanceAreaId;
  private String poleLineLevel;
  private String poleLineLength;
  private String startDeviceName;
  private String endDeviceName;
  private Integer maintenanceModeEnumId;
  private String maintenanceOrgId;
  private String maintainerId;
  private String thirdPartyMaintenanceOrg;
  private Date renewalExpirationDate;
  private Integer maintenanceTypeEnumId;
  private String purchasedMaintenanceTime;
  private String projectCode;
  private String projectName;
  private Date projectWarrantyExpireDate;
  private Integer resourceLifePeriodEnumId;
  private Date creationDate;
  private Date lastUpdateDate;
  private String deletedFlag;
  private Date deletionDate;
  private String provinceId;
  private String areano;
  private Date creationDateB;
  private Date creationDateE;
  private Date lastUpdateDateB;
  private Date lastUpdateDateE;
  private Integer poleLineSegmentId;
  private String poleLineSegmentName;
  private String poleLineSegmentCode;
  private String poleLineSegmentLength;
  private String status;
  private Integer startDeviceId;
  private Integer endDeviceId;
  private Integer constructionSharingEnumId;
  private String constructionSharingOrg;
  private Integer sharingTypeEnumId;
  private Integer poleId;
  private String poleName;
  private String poleCode;
  private Integer poleTypeEnumId;
  private String poleMaterial;
  private String poleLength;
  private String buriedDepth;
  private String poleRadius;
  private String poleAddress;
  private String poleLongitude;
  private String poleLatitude;
  private String areaname;
  private Boolean flag;
  private String polename;
  private String poleNo;
  private String poleSubNo;
  private String poleNameSub;
  private String cpoleNo;
  private String cpoleSubNo;
  private Integer eid;
  private CableRouteInfoBean cableRouteInfoBean;
  private List<CableRouteInfoBean> cableRouteList;
  public static final String MODULENAME = "polelinemanage";
  private ErrorMessage errorMessage;
  private Integer start;
  private Integer limit;
  private boolean success = false;
  private List<String> ids;
  private String deleteMsg;
  private String plids;
  private String filepath;
  private File upFile;

  public String list()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if (this.polelineInfoBean == null) {
        this.polelineInfoBean = new PolelineInfoBean();
      }
      this.polelineInfoBean.setStart(this.start);
      this.polelineInfoBean.setLimit(this.limit);
      this.polelineInfoBean = this.polelineInfoService.getPolelineList(this.polelineInfoBean);
      return "polelineInfoBean";
    } catch (Exception e) {
      log.error("PolelineAction.list 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String dodelete()
    throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      this.polelineSegmentInfoBean = new PolelineSegmentInfoBean();
      this.polelineSegmentInfoBean.setPoleLineId(Integer.valueOf(((String)this.ids.get(0)).trim()));
      this.polelineInfoBean = new PolelineInfoBean();
      this.polelineInfoBean.setPoleLineId(Integer.valueOf(((String)this.ids.get(0)).trim()));
      this.polelineInfoBean = this.polelineInfoService.getPolelineInfoBean(this.polelineInfoBean);
      Date deletionDate = new Date();
      this.polelineInfoBean.setDeletionDate(deletionDate);
      int n = this.polelineInfoService.deletePolelineInfoBean(this.polelineInfoBean);
      if (n != 0) {
        this.polelineInfoBean.setLogTime(deletionDate);
        this.polelineInfoBean.setLogOperater(this.userInfoBean.getUsername());
        this.polelineInfoBean.setDeletedFlag("1");
        this.polelineInfoService.polelineLog(this.polelineInfoBean);
        this.success = true;
        this.deleteMsg = "您成功删除了&nbsp;" + n + "&nbsp;条杆路信息!";

        StringBuffer description = new StringBuffer("用户");
        description.append(this.userInfoBean.getRealname());
        description.append("删除杆路成功");
      }
      else {
        this.success = false;
        this.deleteMsg = "删除杆路信息失败!";
      }
      return "deletePoleLine";
    } catch (Exception e) {
      log.error("PolelineAction.dodelete 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String editpoleline()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if ((this.ids == null) || (this.ids.isEmpty())) {
        this.success = false;
      }
      this.polelineInfoBean = new PolelineInfoBean();
      this.polelineInfoBean.setPoleLineId(Integer.valueOf(((String)this.ids.get(0)).trim()));
      this.polelineInfoBean = this.polelineInfoService.getPolelineInfoBean(this.polelineInfoBean);
      if (this.polelineInfoBean != null)
        this.success = true;
      else {
        this.success = false;
      }
      return "loadPoleLine";
    } catch (Exception e) {
      log.error("PolelineAction.editpoleline 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String doedit()
    throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      this.startDeviceId = this.polelineInfoBean.getStartDeviceId();
      this.endDeviceId = this.polelineInfoBean.getEndDeviceId();
      this.poleLineId = this.polelineInfoBean.getPoleLineId();
      Date date = new Date();
      this.polelineInfoBean.setLastUpdateDate(date);
      int n = this.polelineInfoService.updatePolelineInfoBean(this.polelineInfoBean);
      if (n == 1) {
        this.polelineInfoBean.setLogTime(date);
        this.polelineInfoBean.setLogOperater(this.userInfoBean.getUsername());
        this.polelineInfoService.polelineLog(this.polelineInfoBean);
        this.success = true;

        StringBuffer description = new StringBuffer("用户");
        description.append(this.userInfoBean.getRealname());
        description.append("修改杆路成功");
      }
      else {
        this.success = false;
      }
      if ((this.startDeviceId != null) && (this.startDeviceId.intValue() != 0)) {
        this.poleInfoBean = new PoleInfoBean();
        this.poleInfoBean.setPoleId(this.startDeviceId);
        this.poleInfoBean = this.polelineInfoService.getPoleInfoBean(this.poleInfoBean);
        if (this.poleInfoBean != null) {
          this.poleInfoBean.setPoleLineId(this.poleLineId);
          this.polelineInfoService.updatePoleInfoBean(this.poleInfoBean);
        }
      }

      if ((this.endDeviceId != null) && (this.endDeviceId.intValue() != 0)) {
        this.poleInfoBean = new PoleInfoBean();
        this.poleInfoBean.setPoleId(this.endDeviceId);
        this.poleInfoBean = this.polelineInfoService.getPoleInfoBean(this.poleInfoBean);
        if (this.poleInfoBean != null) {
          this.poleInfoBean.setPoleLineId(this.poleLineId);
          this.polelineInfoService.updatePoleInfoBean(this.poleInfoBean);
        }
      }
      return "updatePoleLine";
    } catch (Exception e) {
      log.error("PolelineAction.doedit 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  /**
   * 添加杆路信息
   * @return
   * @throws Exception
   */
  public String doadd()
    throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      this.startDeviceId = this.polelineInfoBean.getStartDeviceId();
      this.endDeviceId = this.polelineInfoBean.getEndDeviceId();
      Date date = new Date();
      this.polelineInfoBean.setCreationDate(date);
      this.polelineInfoBean.setLastUpdateDate(date);
      this.poleLineId = Integer.valueOf(this.polelineInfoService.insertPolelineInfoBean(this.polelineInfoBean));
      if ((this.poleLineId != null) && (this.poleLineId.intValue() != 0)) {
        this.polelineInfoBean.setLogTime(date);
        this.polelineInfoBean.setLogOperater(this.userInfoBean.getUsername());
        this.polelineInfoBean.setPoleLineId(this.poleLineId);
        this.polelineInfoService.polelineLog(this.polelineInfoBean);
        this.success = true;
      }
      else {
        this.success = false;
      }
      if ((this.startDeviceId != null) && (this.startDeviceId.intValue() != 0)) {
        this.poleInfoBean = new PoleInfoBean();
        this.poleInfoBean.setPoleId(this.startDeviceId);
        this.poleInfoBean = this.polelineInfoService.getPoleInfoBean(this.poleInfoBean);
        if (this.poleInfoBean != null) {
          this.poleInfoBean.setPoleLineId(this.poleLineId);
          this.polelineInfoService.updatePoleInfoBean(this.poleInfoBean);
        }
      }
      if ((this.endDeviceId != null) && (this.endDeviceId.intValue() != 0)) {
        this.poleInfoBean = new PoleInfoBean();
        this.poleInfoBean.setPoleId(this.endDeviceId);
        this.poleInfoBean = this.polelineInfoService.getPoleInfoBean(this.poleInfoBean);
        if (this.poleInfoBean != null) {
          this.poleInfoBean.setPoleLineId(this.poleLineId);
          this.polelineInfoService.updatePoleInfoBean(this.poleInfoBean);
        }
      }
      return "addPoleLine";
    } catch (Exception e) {
	  e.printStackTrace();
	  return "error";
    }
  }

  public String polelslist()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if (this.polelineSegmentInfoBean == null) {
        this.polelineSegmentInfoBean = new PolelineSegmentInfoBean();
      }
      this.polelineSegmentInfoBean.setStart(this.start);
      this.polelineSegmentInfoBean.setLimit(this.limit);
      this.polelineSegmentInfoBean = this.polelineInfoService.getPolelineSegmentList(this.polelineSegmentInfoBean);
      return "polelineSegmentInfoBean";
    } catch (Exception e) {
      log.error("PolelineAction.polelslist 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String editpolelinesegment()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if ((this.ids == null) || (this.ids.isEmpty())) {
        this.success = false;
      }
      this.polelineSegmentInfoBean = new PolelineSegmentInfoBean();
      this.polelineSegmentInfoBean.setPoleLineSegmentId(Integer.valueOf(((String)this.ids.get(0)).trim()));
      this.polelineSegmentInfoBean = this.polelineInfoService.getPolelineSegmentInfoBean(this.polelineSegmentInfoBean);
      if (this.polelineSegmentInfoBean != null) {
        this.success = true;
        this.poleLineId = this.polelineSegmentInfoBean.getPoleLineId();
        if ((this.poleLineId != null) && (this.poleLineId.intValue() != 0)) {
          this.polelineInfoBean = new PolelineInfoBean();
          this.polelineInfoBean.setPoleLineId(this.poleLineId);
          this.polelineInfoBean = this.polelineInfoService.getPolelineInfoBean(this.polelineInfoBean);
          this.polelineSegmentInfoBean.setPoleLineName(this.polelineInfoBean.getPoleLineName());
        }
      } else {
        this.success = false;
      }
      return "loadPoleLS";
    } catch (Exception e) {
      log.error("PolelineAction.editpolelinesegment 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String doeditpls()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.startDeviceId = this.polelineSegmentInfoBean.getStartDeviceId();
      this.endDeviceId = this.polelineSegmentInfoBean.getEndDeviceId();
      this.poleLineId = this.polelineSegmentInfoBean.getPoleLineId();
      Date date = new Date();
      this.polelineSegmentInfoBean.setLastUpdateDate(date);
      int n = this.polelineInfoService.updatePolelineSegmentInfoBean(this.polelineSegmentInfoBean);

      if ((this.startDeviceId != null) && (this.startDeviceId.intValue() != 0)) {
        this.poleInfoBean = new PoleInfoBean();
        this.poleInfoBean.setPoleId(this.startDeviceId);
        this.poleInfoBean = this.polelineInfoService.getPoleInfoBean(this.poleInfoBean);
        if (this.poleInfoBean != null) {
          this.poleInfoBean.setPoleLineId(this.poleLineId);
          this.polelineInfoService.updatePoleInfoBean(this.poleInfoBean);
          this.poleInfoBean.setLogTime(date);
          this.poleInfoBean.setLogOperater(this.userInfoBean.getUsername());
          this.polelineInfoService.poleLog(this.poleInfoBean);
        }
      }
      if ((this.endDeviceId != null) && (this.endDeviceId.intValue() != 0)) {
        this.poleInfoBean = new PoleInfoBean();
        this.poleInfoBean.setPoleId(this.endDeviceId);
        this.poleInfoBean = this.polelineInfoService.getPoleInfoBean(this.poleInfoBean);
        if (this.poleInfoBean != null) {
          this.poleInfoBean.setPoleLineId(this.poleLineId);
          this.polelineInfoService.updatePoleInfoBean(this.poleInfoBean);
          this.poleInfoBean.setLogTime(date);
          this.poleInfoBean.setLogOperater(this.userInfoBean.getUsername());
          this.polelineInfoService.poleLog(this.poleInfoBean);
        }
      }
      if (n == 1) {
        this.polelineSegmentInfoBean.setLogTime(date);
        this.polelineSegmentInfoBean.setLogOperater(this.userInfoBean.getUsername());
        this.polelineInfoService.PolelineSegmentLog(this.polelineSegmentInfoBean);
        this.success = true;
      }
      else
      {
        this.success = false;
      }
      return "updatePoleLS";
    } catch (Exception e) {
      log.error("PolelineAction.doeditpls 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String dodeletepls()
    throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      this.polelineSegmentInfoBean = new PolelineSegmentInfoBean();
      this.polelineSegmentInfoBean.setPoleLineSegmentId(Integer.valueOf(((String)this.ids.get(0)).trim()));
      this.polelineSegmentInfoBean = this.polelineInfoService.getPolelineSegmentInfoBean(this.polelineSegmentInfoBean);
      Date deletionDate = new Date();
      this.polelineSegmentInfoBean.setDeletionDate(deletionDate);
      int n = this.polelineInfoService.deletePolelineSegmentInfoBean(this.polelineSegmentInfoBean);
      if (n != 0) {
        this.polelineSegmentInfoBean.setLogTime(deletionDate);
        this.polelineSegmentInfoBean.setLogOperater(this.userInfoBean.getUsername());
        this.polelineSegmentInfoBean.setDeletedFlag("1");
        this.polelineInfoService.PolelineSegmentLog(this.polelineSegmentInfoBean);
        this.success = true;
        this.deleteMsg = "您成功删除了&nbsp;" + n + "&nbsp;条杆路段信息!";
      }
      else {
        this.success = false;
        this.deleteMsg = "删除杆路段信息失败!";
      }
      return "deletePoleLS";
    } catch (Exception e) {
      log.error("PolelineAction.dodeletepls 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String doaddpls()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.startDeviceId = this.polelineSegmentInfoBean.getStartDeviceId();
      this.endDeviceId = this.polelineSegmentInfoBean.getEndDeviceId();
      this.poleLineId = this.polelineSegmentInfoBean.getPoleLineId();
      Date date = new Date();
      this.polelineSegmentInfoBean.setCreationDate(date);
      this.polelineSegmentInfoBean.setLastUpdateDate(date);
      Integer n = Integer.valueOf(this.polelineInfoService.insertPolelineSegmentInfoBean(this.polelineSegmentInfoBean));
      if (n != null) {
        this.polelineSegmentInfoBean.setLogTime(date);
        this.polelineSegmentInfoBean.setLogOperater(this.userInfoBean.getUsername());
        this.polelineSegmentInfoBean.setPoleLineSegmentId(n);
        this.polelineInfoService.PolelineSegmentLog(this.polelineSegmentInfoBean);
        this.success = true;
      }
      else
      {
        this.success = false;
      }
      if ((this.startDeviceId != null) && (this.startDeviceId.intValue() != 0)) {
        this.poleInfoBean = new PoleInfoBean();
        this.poleInfoBean.setPoleId(this.startDeviceId);
        this.poleInfoBean = this.polelineInfoService.getPoleInfoBean(this.poleInfoBean);
        if (this.poleInfoBean != null) {
          this.poleInfoBean.setPoleLineId(this.poleLineId);
          this.polelineInfoService.updatePoleInfoBean(this.poleInfoBean);
          this.poleInfoBean.setLogTime(date);
          this.poleInfoBean.setLogOperater(this.userInfoBean.getUsername());
          this.polelineInfoService.poleLog(this.poleInfoBean);
        }
      }

      if ((this.endDeviceId != null) && (this.endDeviceId.intValue() != 0)) {
        this.poleInfoBean = new PoleInfoBean();
        this.poleInfoBean.setPoleId(this.endDeviceId);
        this.poleInfoBean = this.polelineInfoService.getPoleInfoBean(this.poleInfoBean);
        if (this.poleInfoBean != null) {
          this.poleInfoBean.setPoleLineId(this.poleLineId);
          this.polelineInfoService.updatePoleInfoBean(this.poleInfoBean);
          this.poleInfoBean.setLogTime(date);
          this.poleInfoBean.setLogOperater(this.userInfoBean.getUsername());
          this.polelineInfoService.poleLog(this.poleInfoBean);
        }
      }

      return "addPoleLineSegment";
    } catch (Exception e) {
      log.error("PolelineAction.doaddpls 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String polelist()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if (this.poleInfoBean == null) {
        this.poleInfoBean = new PoleInfoBean();
      }
      this.poleInfoBean.setStart(this.start);
      this.poleInfoBean.setLimit(this.limit);
      this.poleInfoBean = this.polelineInfoService.getPoleList(this.poleInfoBean, this.start.intValue(), this.limit.intValue());
      return "poleInfoBean";
    } catch (Exception e) {
      log.error("PolelineAction.polelist 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String editpole()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if ((this.ids == null) || (this.ids.isEmpty())) {
        this.success = false;
      }
      this.poleInfoBean = new PoleInfoBean();
      this.poleInfoBean.setPoleId(Integer.valueOf(((String)this.ids.get(0)).trim()));
      this.poleInfoBean = this.polelineInfoService.getPoleInfoBean(this.poleInfoBean);
      if (this.poleInfoBean != null)
        this.success = true;
      else {
        this.success = false;
      }
      return "loadPole";
    } catch (Exception e) {
      log.error("PolelineAction.editpole 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String doeditpole()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      Date date = new Date();
      this.poleInfoBean.setLastUpdateDate(date);
      String polename = this.poleInfoBean.getPoleName();
      String poleno = this.poleInfoBean.getPoleNo();
      String polesubno = this.poleInfoBean.getPoleSubNo();
      if ((polesubno != null) && (!(polesubno.equals(""))))
        this.poleNameSub = polename + "P" + poleno + "_" + polesubno;
      else {
        this.poleNameSub = polename + "P" + poleno;
      }
      this.poleInfoBean.setPoleNameSub(this.poleNameSub);
      int n = this.polelineInfoService.updatePoleInfoBean(this.poleInfoBean);
      if (n == 1) {
        this.poleInfoBean.setLogTime(date);
        this.poleInfoBean.setLogOperater(this.userInfoBean.getUsername());
        this.polelineInfoService.poleLog(this.poleInfoBean);
        this.success = true;
      }
      else {
        this.success = false;
      }

      if ((!(this.poleName.equals(polename))) || (!(this.poleNo.equals(poleno))) || (!(this.poleSubNo.equals(polesubno)))) {
        this.polelineInfoBean = new PolelineInfoBean();
        this.polelineInfoBean.setStartDeviceId(this.poleInfoBean.getPoleId());
        this.polelineInfoBean.setStartDeviceName(this.poleNameSub);
        this.polelineInfoService.updatePolelineInfoBean11(this.polelineInfoBean);

        this.polelineInfoBean = new PolelineInfoBean();
        this.polelineInfoBean.setEndDeviceId(this.poleInfoBean.getPoleId());
        this.polelineInfoBean.setEndDeviceName(this.poleNameSub);
        this.polelineInfoService.updatePolelineInfoBean22(this.polelineInfoBean);
      }
      Iterator localIterator;
      if ((!(this.poleName.equals(polename))) || (!(this.poleNo.equals(poleno))) || (!(this.poleSubNo.equals(polesubno)))) {
        this.polelineSegmentInfoBean = new PolelineSegmentInfoBean();
        this.polelineSegmentInfoBean.setStartDeviceId(this.poleInfoBean.getPoleId());
        this.resultList1 = this.polelineInfoService.getPolelineSegmentListAll(this.polelineSegmentInfoBean);
        PolelineSegmentInfoBean polelineSegmentInfo;
        PoleInfoBean pole;
        for (localIterator = this.resultList1.iterator(); localIterator.hasNext(); ) { polelineSegmentInfo = (PolelineSegmentInfoBean)localIterator.next();
          Integer enddeviceid = polelineSegmentInfo.getEndDeviceId();
          if ((enddeviceid == null) || (enddeviceid.intValue() == 0)) {
            if ((polelineSegmentInfo.getPoleLineId() != null) && (polelineSegmentInfo.getPoleLineId().intValue() != 0)) {
              this.polelineInfoBean = new PolelineInfoBean();
              this.polelineInfoBean.setPoleLineId(polelineSegmentInfo.getPoleLineId());
              this.polelineInfoBean = this.polelineInfoService.getPolelineInfoBean(this.polelineInfoBean);
              if (this.polelineInfoBean != null) {
                this.poleLineSegmentName = this.polelineInfoBean.getPoleLineName() + "(" + this.poleNameSub + "_" + ")";
                polelineSegmentInfo.setPoleLineSegmentName(this.poleLineSegmentName);
                polelineSegmentInfo.setStartDeviceName(this.poleNameSub);
                this.polelineInfoService.updatePolelineSegmentInfoBean(polelineSegmentInfo);
              } else {
                this.poleLineSegmentName = this.poleNameSub + "_";
                polelineSegmentInfo.setPoleLineSegmentName(this.poleLineSegmentName);
                polelineSegmentInfo.setStartDeviceName(this.poleNameSub);
                this.polelineInfoService.updatePolelineSegmentInfoBean(polelineSegmentInfo);
              }
            } else {
              this.poleLineSegmentName = this.poleNameSub + "_";
              polelineSegmentInfo.setPoleLineSegmentName(this.poleLineSegmentName);
              polelineSegmentInfo.setStartDeviceName(this.poleNameSub);
              this.polelineInfoService.updatePolelineSegmentInfoBean(polelineSegmentInfo);
            }
          } else {
            pole = new PoleInfoBean();
            pole.setPoleId(enddeviceid);
            pole = this.polelineInfoService.getPoleInfoBean(pole);
            if (pole != null) {
              if ((polelineSegmentInfo.getPoleLineId() != null) && (polelineSegmentInfo.getPoleLineId().intValue() != 0)) {
                this.polelineInfoBean = new PolelineInfoBean();
                this.polelineInfoBean.setPoleLineId(polelineSegmentInfo.getPoleLineId());
                this.polelineInfoBean = this.polelineInfoService.getPolelineInfoBean(this.polelineInfoBean);
                if (this.polelineInfoBean != null) {
                  this.poleLineSegmentName = this.polelineInfoBean.getPoleLineName() + "(" + this.poleNameSub + "_" + pole.getPoleNameSub() + ")";
                  polelineSegmentInfo.setPoleLineSegmentName(this.poleLineSegmentName);
                  polelineSegmentInfo.setStartDeviceName(this.poleNameSub);
                  this.polelineInfoService.updatePolelineSegmentInfoBean(polelineSegmentInfo);
                } else {
                  this.poleLineSegmentName = this.poleNameSub + "_" + pole.getPoleNameSub();
                  polelineSegmentInfo.setPoleLineSegmentName(this.poleLineSegmentName);
                  polelineSegmentInfo.setStartDeviceName(this.poleNameSub);
                  this.polelineInfoService.updatePolelineSegmentInfoBean(polelineSegmentInfo);
                }
              } else {
                this.poleLineSegmentName = this.poleNameSub + "_" + pole.getPoleNameSub();
                polelineSegmentInfo.setPoleLineSegmentName(this.poleLineSegmentName);
                polelineSegmentInfo.setStartDeviceName(this.poleNameSub);
                this.polelineInfoService.updatePolelineSegmentInfoBean(polelineSegmentInfo);
              }
            }
            else if ((polelineSegmentInfo.getPoleLineId() != null) && (polelineSegmentInfo.getPoleLineId().intValue() != 0)) {
              this.polelineInfoBean = new PolelineInfoBean();
              this.polelineInfoBean.setPoleLineId(polelineSegmentInfo.getPoleLineId());
              this.polelineInfoBean = this.polelineInfoService.getPolelineInfoBean(this.polelineInfoBean);
              if (this.polelineInfoBean != null) {
                this.poleLineSegmentName = this.polelineInfoBean.getPoleLineName() + "(" + this.poleNameSub + "_" + ")";
                polelineSegmentInfo.setPoleLineSegmentName(this.poleLineSegmentName);
                polelineSegmentInfo.setStartDeviceName(this.poleNameSub);
                this.polelineInfoService.updatePolelineSegmentInfoBean(polelineSegmentInfo);
              } else {
                this.poleLineSegmentName = this.poleNameSub + "_";
                polelineSegmentInfo.setPoleLineSegmentName(this.poleLineSegmentName);
                polelineSegmentInfo.setStartDeviceName(this.poleNameSub);
                this.polelineInfoService.updatePolelineSegmentInfoBean(polelineSegmentInfo);
              }
            } else {
              this.poleLineSegmentName = this.poleNameSub + "_";
              polelineSegmentInfo.setPoleLineSegmentName(this.poleLineSegmentName);
              polelineSegmentInfo.setStartDeviceName(this.poleNameSub);
              this.polelineInfoService.updatePolelineSegmentInfoBean(polelineSegmentInfo);
            }
          }

        }

        this.polelineSegmentInfoBean = new PolelineSegmentInfoBean();
        this.polelineSegmentInfoBean.setEndDeviceId(this.poleInfoBean.getPoleId());
        this.resultList1 = this.polelineInfoService.getPolelineSegmentListAll(this.polelineSegmentInfoBean);
        for (localIterator = this.resultList1.iterator(); localIterator.hasNext(); ) { polelineSegmentInfo = (PolelineSegmentInfoBean)localIterator.next();
          Integer startdeviceid = polelineSegmentInfo.getStartDeviceId();
          if ((startdeviceid == null) || (startdeviceid.intValue() == 0)) {
            if ((polelineSegmentInfo.getPoleLineId() != null) && (polelineSegmentInfo.getPoleLineId().intValue() != 0)) {
              this.polelineInfoBean = new PolelineInfoBean();
              this.polelineInfoBean.setPoleLineId(polelineSegmentInfo.getPoleLineId());
              this.polelineInfoBean = this.polelineInfoService.getPolelineInfoBean(this.polelineInfoBean);
              if (this.polelineInfoBean != null) {
                this.poleLineSegmentName = this.polelineInfoBean.getPoleLineName() + "(" + "_" + this.poleNameSub + ")";
                polelineSegmentInfo.setPoleLineSegmentName(this.poleLineSegmentName);
                polelineSegmentInfo.setEndDeviceName(this.poleNameSub);
                this.polelineInfoService.updatePolelineSegmentInfoBean(polelineSegmentInfo);
              } else {
                this.poleLineSegmentName = "_" + this.poleNameSub;
                polelineSegmentInfo.setPoleLineSegmentName(this.poleLineSegmentName);
                polelineSegmentInfo.setEndDeviceName(this.poleNameSub);
                this.polelineInfoService.updatePolelineSegmentInfoBean(polelineSegmentInfo);
              }
            } else {
              this.poleLineSegmentName = "_" + this.poleNameSub;
              polelineSegmentInfo.setPoleLineSegmentName(this.poleLineSegmentName);
              polelineSegmentInfo.setEndDeviceName(this.poleNameSub);
              this.polelineInfoService.updatePolelineSegmentInfoBean(polelineSegmentInfo);
            }
          } else {
            pole = new PoleInfoBean();
            pole.setPoleId(startdeviceid);
            pole = this.polelineInfoService.getPoleInfoBean(pole);

            if (pole != null) {
              if ((polelineSegmentInfo.getPoleLineId() != null) && (polelineSegmentInfo.getPoleLineId().intValue() != 0)) {
                this.polelineInfoBean = new PolelineInfoBean();
                this.polelineInfoBean.setPoleLineId(polelineSegmentInfo.getPoleLineId());
                this.polelineInfoBean = this.polelineInfoService.getPolelineInfoBean(this.polelineInfoBean);
                if (this.polelineInfoBean != null) {
                  this.poleLineSegmentName = this.polelineInfoBean.getPoleLineName() + "(" + pole.getPoleNameSub() + "_" + this.poleNameSub + ")";
                  polelineSegmentInfo.setPoleLineSegmentName(this.poleLineSegmentName);
                  polelineSegmentInfo.setEndDeviceName(this.poleNameSub);
                  this.polelineInfoService.updatePolelineSegmentInfoBean(polelineSegmentInfo);
                } else {
                  this.poleLineSegmentName = pole.getPoleNameSub() + "_" + this.poleNameSub;
                  polelineSegmentInfo.setPoleLineSegmentName(this.poleLineSegmentName);
                  polelineSegmentInfo.setEndDeviceName(this.poleNameSub);
                  this.polelineInfoService.updatePolelineSegmentInfoBean(polelineSegmentInfo);
                }
              } else {
                this.poleLineSegmentName = pole.getPoleNameSub() + "_" + this.poleNameSub;
                polelineSegmentInfo.setPoleLineSegmentName(this.poleLineSegmentName);
                polelineSegmentInfo.setEndDeviceName(this.poleNameSub);
                this.polelineInfoService.updatePolelineSegmentInfoBean(polelineSegmentInfo);
              }
            }
            else if ((polelineSegmentInfo.getPoleLineId() != null) && (polelineSegmentInfo.getPoleLineId().intValue() != 0)) {
              this.polelineInfoBean = new PolelineInfoBean();
              this.polelineInfoBean.setPoleLineId(polelineSegmentInfo.getPoleLineId());
              this.polelineInfoBean = this.polelineInfoService.getPolelineInfoBean(this.polelineInfoBean);
              if (this.polelineInfoBean != null) {
                this.poleLineSegmentName = this.polelineInfoBean.getPoleLineName() + "(" + "_" + this.poleNameSub + ")";
                polelineSegmentInfo.setPoleLineSegmentName(this.poleLineSegmentName);
                polelineSegmentInfo.setEndDeviceName(this.poleNameSub);
                this.polelineInfoService.updatePolelineSegmentInfoBean(polelineSegmentInfo);
              } else {
                this.poleLineSegmentName = "_" + this.poleNameSub;
                polelineSegmentInfo.setPoleLineSegmentName(this.poleLineSegmentName);
                polelineSegmentInfo.setEndDeviceName(this.poleNameSub);
                this.polelineInfoService.updatePolelineSegmentInfoBean(polelineSegmentInfo);
              }
            } else {
              this.poleLineSegmentName = "_" + this.poleNameSub;
              polelineSegmentInfo.setPoleLineSegmentName(this.poleLineSegmentName);
              polelineSegmentInfo.setEndDeviceName(this.poleNameSub);
              this.polelineInfoService.updatePolelineSegmentInfoBean(polelineSegmentInfo);
            }
          }

        }

      }


      if ((!(this.poleName.equals(polename))) || (!(this.poleNo.equals(poleno))) || (!(this.poleSubNo.equals(polesubno)))) {
        this.pipeSegmentInfoBean = new PipeSegmentInfoBean();
        this.pipeSegmentInfoBean.setStartDeviceId(this.poleInfoBean.getPoleId());
        this.pipeSegmentInfoBean.setStartDeviceType("2");
        this.resultPipeSegmentList = this.polelineInfoService.getPipeSegmentList(this.pipeSegmentInfoBean);
        PipeSegmentInfoBean pipeSegmentInfo;
        String pipesname;
        for (localIterator = this.resultPipeSegmentList.iterator(); localIterator.hasNext(); ) { pipeSegmentInfo = (PipeSegmentInfoBean)localIterator.next();
          if ((pipeSegmentInfo.getPipeId() != null) && (pipeSegmentInfo.getPipeId().intValue() != 0)) {
            this.pipeInfoBean = new PipeInfoBean();
            this.pipeInfoBean.setPipeId(pipeSegmentInfo.getPipeId());
            this.pipeInfoBean = this.pipeInfoService.getPipeInfoBean(this.pipeInfoBean);
            if (this.pipeInfoBean != null) {
              pipesname = this.pipeInfoBean.getPipeName() + "(" + this.poleNameSub + "_" + pipeSegmentInfo.getEndDeviceName() + ")";
              pipeSegmentInfo.setPipeSegmentName(pipesname);
              pipeSegmentInfo.setStartDeviceName(this.poleNameSub);
              this.polelineInfoService.updatePipeSegmentInfoBean(pipeSegmentInfo);
            } else {
              pipesname = this.poleNameSub + "_" + pipeSegmentInfo.getEndDeviceName();
              pipeSegmentInfo.setPipeSegmentName(pipesname);
              pipeSegmentInfo.setStartDeviceName(this.poleNameSub);
              this.polelineInfoService.updatePipeSegmentInfoBean(pipeSegmentInfo);
            }
          } else {
            pipesname = this.poleNameSub + "_" + pipeSegmentInfo.getEndDeviceName();
            pipeSegmentInfo.setPipeSegmentName(pipesname);
            pipeSegmentInfo.setStartDeviceName(this.poleNameSub);
            this.polelineInfoService.updatePipeSegmentInfoBean(pipeSegmentInfo);
          }

        }

        this.pipeSegmentInfoBean = new PipeSegmentInfoBean();
        this.pipeSegmentInfoBean.setEndDeviceId(this.poleInfoBean.getPoleId());
        this.pipeSegmentInfoBean.setEndDeviceType("2");
        this.resultPipeSegmentList = this.polelineInfoService.getPipeSegmentList(this.pipeSegmentInfoBean);
        for (localIterator = this.resultPipeSegmentList.iterator(); localIterator.hasNext(); ) { pipeSegmentInfo = (PipeSegmentInfoBean)localIterator.next();
          if ((pipeSegmentInfo.getPipeId() != null) && (pipeSegmentInfo.getPipeId().intValue() != 0)) {
            this.pipeInfoBean = new PipeInfoBean();
            this.pipeInfoBean.setPipeId(pipeSegmentInfo.getPipeId());
            this.pipeInfoBean = this.pipeInfoService.getPipeInfoBean(this.pipeInfoBean);
            if (this.pipeInfoBean != null) {
              pipesname = this.pipeInfoBean.getPipeName() + "(" + pipeSegmentInfo.getStartDeviceName() + "_" + this.poleNameSub + ")";
              pipeSegmentInfo.setPipeSegmentName(pipesname);
              pipeSegmentInfo.setEndDeviceName(this.poleNameSub);
              this.polelineInfoService.updatePipeSegmentInfoBean(pipeSegmentInfo);
            } else {
              pipesname = pipeSegmentInfo.getStartDeviceName() + "_" + this.poleNameSub;
              pipeSegmentInfo.setPipeSegmentName(pipesname);
              pipeSegmentInfo.setEndDeviceName(this.poleNameSub);
              this.polelineInfoService.updatePipeSegmentInfoBean(pipeSegmentInfo);
            }
          } else {
            pipesname = pipeSegmentInfo.getStartDeviceName() + "_" + this.poleNameSub;
            pipeSegmentInfo.setPipeSegmentName(pipesname);
            pipeSegmentInfo.setEndDeviceName(this.poleNameSub);
            this.polelineInfoService.updatePipeSegmentInfoBean(pipeSegmentInfo);
          }
        }
      }
      return "updatePole";
    } catch (Exception e) {
      log.error("PolelineAction.doeditpole 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String dodeletepole()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.poleId = Integer.valueOf(((String)this.ids.get(0)).trim());
      this.poleInfoBean = new PoleInfoBean();
      this.poleInfoBean.setPoleId(Integer.valueOf(((String)this.ids.get(0)).trim()));

      Date deletionDate = new Date();
      //this.poleInfoBean = ((PoleInfoBean)this.poleInfoBean.getPoles().get(0));
      this.poleInfoBean.setDeletionDate(deletionDate);
      int n = this.polelineInfoService.deletePoleInfoBean(this.poleInfoBean);
      if (n != 0) {
        this.poleInfoBean.setLogTime(deletionDate);
        this.poleInfoBean.setLogOperater(this.userInfoBean.getUsername());
        this.poleInfoBean.setDeletedFlag("1");
        this.polelineInfoService.poleLog(this.poleInfoBean);
        this.success = true;
        this.deleteMsg = "您成功删除了&nbsp;" + n + "&nbsp;条电杆信息!";

        StringBuffer description = new StringBuffer("用户");
        description.append(this.userInfoBean.getRealname());
        description.append("删除电杆成功");
      }
      else {
        this.success = false;
        this.deleteMsg = "删除用户信息失败!";
      }

      return "deletePole";
    } catch (Exception e) {
      log.error("PolelineAction.dodeletepole 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String doaddpole()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      Date date = new Date();
      this.poleInfoBean.setCreationDate(date);
      this.poleInfoBean.setLastUpdateDate(date);
      this.poleSubNo = this.poleInfoBean.getPoleSubNo();
      if ((this.poleSubNo != null) && (!(this.poleSubNo.equals(""))))
        this.poleNameSub = this.poleInfoBean.getPoleName() + "P" + this.poleInfoBean.getPoleNo() + "_" + this.poleInfoBean.getPoleSubNo();
      else {
        this.poleNameSub = this.poleInfoBean.getPoleName() + "P" + this.poleInfoBean.getPoleNo();
      }
      this.poleInfoBean.setPoleNameSub(this.poleNameSub);

      Integer n = Integer.valueOf(this.polelineInfoService.insertPoleInfoBean(this.poleInfoBean));
      if (n != null) {
        this.poleInfoBean.setPoleId(n);
        this.poleInfoBean.setLogTime(date);
        this.poleInfoBean.setLogOperater(this.userInfoBean.getUsername());
        this.polelineInfoService.poleLog(this.poleInfoBean);
        this.success = true;
      } else {
        this.success = false;
      }
      return "addPole";
    } catch (Exception e) {
      log.error("PolelineAction.doaddpole 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String checkJSON()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.poleInfoBean = new PoleInfoBean();
      this.poleInfoBean.setPoleName(CommonUtil.setNullForString(this.poleName));
      this.poleInfoBean.setPoleNo(CommonUtil.setNullForString(this.poleNo));
      this.poleInfoBean.setPoleSubNo(CommonUtil.setNullForString(this.poleSubNo));
      this.poleInfoBean = this.polelineInfoService.getPoleInfoBean(this.poleInfoBean);
      if (this.poleInfoBean != null)
        this.success = false;
      else {
        this.success = true;
      }
      return "vetifyPoleName";
    } catch (Exception e) {
      log.error("PointAction.checkJSON", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  /**
   * 检查杆路信息是否已存在
   * @return
   * @throws Exception
   */
  public String checkpolelineJSON()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.polelineInfoBean = new PolelineInfoBean();
      this.polelineInfoBean.setPoleLineName(CommonUtil.setNullForString(this.poleLineName));
      this.polelineInfoBean = this.polelineInfoService.getPolelineInfoBean(this.polelineInfoBean);
      if (this.polelineInfoBean != null)
        this.success = false;
      else {
        this.success = true;
      }
      return "vetifyPoleLineName";
    } catch (Exception e) {
      log.error("PointAction.checkpolelineJSON", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String checkpolelinesegmentJSON()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.polelineSegmentInfoBean = new PolelineSegmentInfoBean();
      this.polelineSegmentInfoBean.setPoleLineSegmentName(CommonUtil.setNullForString(this.poleLineSegmentName));
      this.polelineSegmentInfoBean = this.polelineInfoService.getPolelineSegmentInfoBean(this.polelineSegmentInfoBean);
      if (this.polelineSegmentInfoBean != null)
        this.success = false;
      else {
        this.success = true;
      }
      return "vetifyPoleLineSegmentName";
    } catch (Exception e) {
      log.error("PointAction.checkpolelinesegmentJSON", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      e.printStackTrace(); }
    return "error";
  }

  public void poleDW()
    throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    String lon = "0";
    String lat = "0";
    String addr = "";
    String pn = "";
    String an = "";
    String pt = "";

    String pm = "";
    String pl = "";
    String bd = "";
    String pr = "";
    String pc = "";
    try
    {
      this.poleInfoBean = new PoleInfoBean();
      this.poleInfoBean.setPoleId(this.eid);

      this.poleInfoBean = this.polelineInfoService.getPoleInfoBean(this.poleInfoBean);

      lon = this.poleInfoBean.getPoleLongitude();
      lat = this.poleInfoBean.getPoleLatitude();
      addr = URLEncoder.encode(this.poleInfoBean.getPoleAddress(), "utf-8");
      pn = URLEncoder.encode(this.poleInfoBean.getPoleName(), "utf-8");
      an = URLEncoder.encode(this.poleInfoBean.getAreaname(), "utf-8");
      pt = this.poleInfoBean.getPoleTypeEnumId()+"";
      pm = this.poleInfoBean.getPoleMaterial();
      pl = this.poleInfoBean.getPoleLength();
      bd = this.poleInfoBean.getBuriedDepth();
      pr = this.poleInfoBean.getPoleRadius();
      pc = this.poleInfoBean.getPoleCode();
      if ((pt != null) && (!("".equals(pt)))) {
        String[] manList = { "未知", "普通杆", "单接杆", "双接杆", "H型杆", "A型杆", "L型杆", "三角杆", "井型杆", "引上杆", "终端杆", "角杆", "分歧杆", "T型杆", "跨路杆" };
        pt = manList[Integer.parseInt(pt)];
      } else {
        pt = "未知";
      }

      if ((pm != null) && (!("".equals(pm)))) {
        String[] pmList = { "未知", "钢筋混凝土电杆", "木质电杆", "铁质电杆" };
        pm = pmList[Integer.parseInt(pm)];
      } else {
        pm = "未知";
      }

      if ((pl != null) && (!("".equals(pl)))) {
        String[] plList = { "未知", "7.5m", "8m", "9m", "10m", "12m" };
        pl = plList[Integer.parseInt(pl)];
      } else {
        pl = "未知";
      }

      if ((bd == null) || (!("".equals(bd)))) {
        bd = "未知";
      }
      if ((pr == null) || (!("".equals(pr)))) {
        pr = "未知";
      }
      pt = URLEncoder.encode(pt, "utf-8");
      pm = URLEncoder.encode(pm, "utf-8");
      pl = URLEncoder.encode(pl, "utf-8");
      bd = URLEncoder.encode(bd, "utf-8");
      pr = URLEncoder.encode(pr, "utf-8");
    }
    catch (Exception e)
    {
      log.error("PointAction.checkJSON", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
    }

    HttpServletResponse response = ServletActionContext.getResponse();
    response.setContentType("text/plain");
    response.setHeader("Charset", "UTF-8");
    PrintWriter out = response.getWriter();
    out.println("{\"lon\":\"" + lon + "\",\"lat\":\"" + lat + "\",\"addr\":\"" + addr + "\",\"pn\":\"" + pn + "\",\"an\":\"" + an + "\",\"pt\":\"" + pt + "\",\"pm\":\"" + pm + "\",\"pl\":\"" + pl + "\",\"bd\":\"" + bd + "\",\"pr\":\"" + pr + "\",\"pc\":\"" + pc + "\"}");
    out.flush();
    out.close();
  }

  public String exportpole() throws Exception {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      String randomPath = "temp_" + CommonUtil.getUuid();
      String dlTempPath = ServletActionContext.getServletContext().getRealPath("/") + File.separator + "downloadtemp" + File.separator + randomPath;
      File dirfile = new File(dlTempPath);
      dirfile.mkdirs();

      UserInfoBean userBean = (UserInfoBean)getSession().getAttribute("userBean");
      this.polelineInfoBean = new PolelineInfoBean();

      String ecodeString = "";

      if ((this.plids != null) || (this.plids != "")) {
        String[] temp = this.plids.split(",");
        String tempeid = "";
        String modelPath = "";
        modelPath = getServletContext().getRealPath("/") + "tmpl" + File.separator + "excel_model_pole.xls";

        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(modelPath));
        HSSFWorkbook wb = new HSSFWorkbook(fs);

        HSSFCellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.TAN.getIndex());
        style.setFillPattern((short)1);

        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setBoldweight((short)700);
        font.setFontHeightInPoints((short)11);
        style.setFont(font);
        String lastname = "";
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

          cell1.setCellValue("编号");
          cell2.setCellValue("所属工程");
          cell3.setCellValue("道路名称");
          cell4.setCellValue("电杆类型");
          cell5.setCellValue("电杆材质");
          cell6.setCellValue("电杆长度");
          cell7.setCellValue("经度");
          cell8.setCellValue("纬度");
          cell9.setCellValue("埋深");
          cell10.setCellValue("吊线程式");

          if ((temp[i] != null) || (temp[i] != "")) {
            tempeid = temp[i];
            this.polelineInfoBean = new PolelineInfoBean();
            this.polelineInfoBean.setPoleLineId(Integer.valueOf(tempeid));
            List lists = this.polelineInfoService.getPoleListOfPL(this.polelineInfoBean);

            Collections.sort(lists);
            this.polelineInfoBean = this.polelineInfoService.getPolelineInfoBean(this.polelineInfoBean);
            lastname = this.polelineInfoBean.getPoleLineName();
            for (int j = 0; j < lists.size(); ++j) {
              PoleInfoBean teBean = (PoleInfoBean)lists.get(j);
              if (teBean == null)
                continue;
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

              cell1.setCellValue(teBean.getPoleNameSub());
              cell2.setCellValue("");
              String name = teBean.getPoleNameSub();
              if ((name.contains("东P")) || (name.contains("西P")) || (name.contains("南P")) || (name.contains("北P")))
                name = name.substring(0, name.indexOf("P") - 1);
              else {
                name = name.substring(0, name.indexOf("P"));
              }
              cell3.setCellValue(name);
              int num = 0;
              if (teBean.getPoleTypeEnumId() != null) {
                num = teBean.getPoleTypeEnumId().intValue();
              }
              String str = "";
              if (num == 1)
                str = "分支杆";
              else if (num == 2)
                str = "角杆";
              else if (num == 3)
                str = "十字杆";
              else if (num == 4)
                str = "引上杆";
              else if (num == 5)
                str = "中间杆";
              else if (num == 6)
                str = "终端杆";
              else {
                str = "";
              }
              cell4.setCellValue(str);
              str = teBean.getPoleMaterial();
              if (str.equals("1"))
                str = "水泥杆";
              else if (str.equals("2"))
                str = "素杆";
              else if (str.equals("3"))
                str = "铁杆";
              else if (str.equals("4"))
                str = "铁塔";
              else if (str.equals("5"))
                str = "油杆";
              else {
                str = "";
              }
              cell5.setCellValue(str);
              cell6.setCellValue(teBean.getPoleLength());
              if ((teBean.getPoleLongitude() != null) && (!(teBean.getPoleLongitude().equals(""))) && (teBean.getPoleLatitude() != null) && (!(teBean.getPoleLatitude().equals("")))) {
                String[] strlonlat = PositionUtil.latlonTran(Double.parseDouble(teBean.getPoleLongitude()), Double.parseDouble(teBean.getPoleLatitude()));
                cell7.setCellValue(strlonlat[0]);
                cell8.setCellValue(strlonlat[1]);
              }

              cell9.setCellValue(teBean.getBuriedDepth());
              str = teBean.getPoleWireForm();
              if (str == null)
                str = "";
              else if (str.equals("1"))
                str = "2*2+3*3+3*3";
              else if (str.equals("2"))
                str = "1800*18";
              else if (str.equals("3"))
                str = "800*20";
              else if (str.equals("4"))
                str = "800*27";
              else if (str.equals("5"))
                str = "800*23";
              else if (str.equals("6"))
                str = "800*24";
              else if (str.equals("7"))
                str = "800*25";
              else if (str.equals("8"))
                str = "800*26";
              else if (str.equals("9"))
                str = "700*13";
              else if (str.equals("10"))
                str = "700*15";
              else if (str.equals("11"))
                str = "600*12";
              else if (str.equals("12"))
                str = "600*15";
              else if (str.equals("13"))
                str = "大号";
              else if (str.equals("14"))
                str = "小号";
              else if (str.equals("15"))
                str = "900*15";
              else if (str.equals("16"))
                str = "800*13";
              else if (str.equals("17"))
                str = "750*15";
              else if (str.equals("18"))
                str = "13*750";
              else if (str.equals("19"))
                str = "750*13";
              else if (str.equals("20"))
                str = "800*15";
              else if (str.equals("21"))
                str = "1000*15";
              else if (str.equals("22"))
                str = "1000*18";
              else if (str.equals("23"))
                str = "1200*20";
              else {
                str = "";
              }
              cell10.setCellValue(str);
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
          String filename = dlTempPath + File.separator + lastname + "_" + "电杆信息列表.xls";
          FileOutputStream fo = new FileOutputStream(filename);
          wb.write(fo);
          fo.close();
        }
        String sourceFilePath = dlTempPath + File.separator;
        String zipFilePath = getServletContext().getRealPath("/") + "ziptemp";
        boolean flag = FileToZip.fileToZip(sourceFilePath, zipFilePath, "电杆信息");
        if (flag)
          this.filepath = "ziptemp/电杆信息.zip";
        else
          this.filepath = "downloadtemp/" + randomPath + "/" + lastname + "_" + "电杆信息列表.xls";
      }
    }
    catch (DataAccessException e) {
      log.error("PolelineAction.exportpole", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    } catch (Exception e) {
      log.error("PolelineAction.exportpole", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    return "exportsuccess";
  }

  public String doexportpole() throws Exception
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      String randomPath = "temp_" + CommonUtil.getUuid();
      String dlTempPath = ServletActionContext.getServletContext().getRealPath("/") + "downloadtemp" + File.separator + randomPath;
      File dirfile = new File(dlTempPath);
      dirfile.mkdirs();

      String ecodeString = "";
      if ((this.areano != null) && (!(this.areano.equals("")))) {
        String modelPath = "";
        modelPath = getServletContext().getRealPath("/") + "tmpl" + File.separator + "excel_model_pole.xls";

        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(modelPath));
        HSSFWorkbook wb = new HSSFWorkbook(fs);

        HSSFCellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.TAN.getIndex());
        style.setFillPattern((short)1);

        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setBoldweight((short)700);
        font.setFontHeightInPoints((short)11);
        style.setFont(font);

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

        cell1.setCellValue("行政区");
        cell2.setCellValue("编号");
        cell3.setCellValue("所属工程");
        cell4.setCellValue("道路名称");
        cell5.setCellValue("电杆类型");
        cell6.setCellValue("电杆材质");
        cell7.setCellValue("电杆长度");
        cell8.setCellValue("经度");
        cell9.setCellValue("纬度");
        cell10.setCellValue("埋深");
        cell11.setCellValue("吊线程式");

        this.poleInfoBean = new PoleInfoBean();
        this.poleInfoBean.setAreano(this.areano);
        List lists = this.polelineInfoService.getPoleLists(this.poleInfoBean);
        for (int j = 0; j < lists.size(); ++j) {
          PoleInfoBean teBean = (PoleInfoBean)lists.get(j);
          if (teBean == null)
            continue;
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

          String names = teBean.getPoleNameSub();
          if (names.indexOf("区") != -1) {
            cell1.setCellValue(names.substring(0, names.indexOf("区") + 1));
            cell2.setCellValue(names.substring(names.indexOf("区") + 1, names.length()));
          } else if (names.indexOf("县") != -1) {
            cell1.setCellValue(names.substring(0, names.indexOf("县") + 1));
            cell2.setCellValue(names.substring(names.indexOf("县") + 1, names.length()));
          } else if (names.lastIndexOf("市") != -1) {
            cell1.setCellValue(names.substring(0, names.lastIndexOf("市") + 1));
            cell2.setCellValue(names.substring(names.lastIndexOf("市") + 1, names.length()));
          } else {
            cell1.setCellValue("");
            cell2.setCellValue(names);
          }

          cell3.setCellValue("");
          String name = teBean.getPoleNameSub();
          if ((name.contains("东P")) || (name.contains("西P")) || (name.contains("南P")) || (name.contains("北P")))
            name = name.substring(0, name.indexOf("P") - 1);
          else {
            name = name.substring(0, name.indexOf("P"));
          }
          cell4.setCellValue(name);
          int num = 0;
          if (teBean.getPoleTypeEnumId() != null) {
            num = teBean.getPoleTypeEnumId().intValue();
          }
          String str = "";
          if (num == 1)
            str = "分支杆";
          else if (num == 2)
            str = "角杆";
          else if (num == 3)
            str = "十字杆";
          else if (num == 4)
            str = "引上杆";
          else if (num == 5)
            str = "中间杆";
          else if (num == 6)
            str = "终端杆";
          else {
            str = "";
          }
          cell5.setCellValue(str);
          str = teBean.getPoleMaterial();
          if (str.equals("1"))
            str = "水泥杆";
          else if (str.equals("2"))
            str = "素杆";
          else if (str.equals("3"))
            str = "铁杆";
          else if (str.equals("4"))
            str = "铁塔";
          else if (str.equals("5"))
            str = "油杆";
          else {
            str = "";
          }
          cell6.setCellValue(str);
          cell7.setCellValue(teBean.getPoleLength());
          if ((teBean.getPoleLongitude() != null) && (!(teBean.getPoleLongitude().equals(""))) && (teBean.getPoleLatitude() != null) && (!(teBean.getPoleLatitude().equals("")))) {
            String[] strlonlat = PositionUtil.latlonTran(Double.parseDouble(teBean.getPoleLongitude()), Double.parseDouble(teBean.getPoleLatitude()));
            cell8.setCellValue(strlonlat[0]);
            cell9.setCellValue(strlonlat[1]);
          }

          cell10.setCellValue(teBean.getBuriedDepth());
          str = teBean.getPoleWireForm();
          if (str == null)
            str = "";
          else if (str.equals("1"))
            str = "2*2+3*3+3*3";
          else if (str.equals("2"))
            str = "1800*18";
          else if (str.equals("3"))
            str = "800*20";
          else if (str.equals("4"))
            str = "800*27";
          else if (str.equals("5"))
            str = "800*23";
          else if (str.equals("6"))
            str = "800*24";
          else if (str.equals("7"))
            str = "800*25";
          else if (str.equals("8"))
            str = "800*26";
          else if (str.equals("9"))
            str = "700*13";
          else if (str.equals("10"))
            str = "700*15";
          else if (str.equals("11"))
            str = "600*12";
          else if (str.equals("12"))
            str = "600*15";
          else if (str.equals("13"))
            str = "大号";
          else if (str.equals("14"))
            str = "小号";
          else if (str.equals("15"))
            str = "900*15";
          else if (str.equals("16"))
            str = "800*13";
          else if (str.equals("17"))
            str = "750*15";
          else if (str.equals("18"))
            str = "13*750";
          else if (str.equals("19"))
            str = "750*13";
          else if (str.equals("20"))
            str = "800*15";
          else if (str.equals("21"))
            str = "1000*15";
          else if (str.equals("22"))
            str = "1000*18";
          else if (str.equals("23"))
            str = "1200*20";
          else {
            str = "";
          }
          cell11.setCellValue(str);
        }

        String filename = dlTempPath + File.separator + this.areaname + "_" + "电杆信息列表.xls";
        FileOutputStream fo = new FileOutputStream(filename);
        wb.write(fo);
        fo.close();

        String sourceFilePath = dlTempPath + File.separator;
        this.filepath = "downloadtemp" + File.separator + randomPath + File.separator + this.areaname + "_" + "电杆信息列表.xls";
      }
      return "exportsuccess";
    } catch (Exception e) {
      log.error("DocumentAction.doexportpole", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String importPole() throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if (this.upFile != null) {
        POIFSFileSystem fs = null;
        HSSFWorkbook wb = null;
        fs = new POIFSFileSystem(new FileInputStream(this.upFile));
        wb = new HSSFWorkbook(fs);
        String str = importPoles(wb);
        if (str.equals("list2")) {
          this.success = true;
        }
        return str;
      }
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("对不起，请您选择一个文件！");
      return "error";
    }
    catch (DataAccessException e) {
      log.error("PolelineAction.importPole", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    } catch (Exception e) {
      log.error("PolelineAction.importPole", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String importPoles(HSSFWorkbook wb) throws Exception
  {
    int rows = 0;
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    this.areano = this.userInfoBean.getAreano();
    String eventUser = this.userInfoBean.getUsername();
    Date eventtime = new Date();
    List polelist = new ArrayList();
    PoleInfoBean p = new PoleInfoBean();
    String namestr = "";
    try {
      HSSFSheet sheet = wb.getSheetAt(0);
      rows = sheet.getLastRowNum();
      if (rows > 0) {
        System.out.print("$$$$$$$$$$" + rows + "###########");
      }
      DecimalFormat df = new DecimalFormat("#");

      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
      Date eventTime = new Date();

      String importareaname = "";
      String importpolecode = "";
      for (int i = 3; i <= rows; ++i) {
        p = new PoleInfoBean();
        HSSFRow row = sheet.getRow(i);
        HSSFCell cell = row.getCell(0);
        HSSFCell cell1 = row.getCell(1);
        HSSFCell cell2 = row.getCell(2);
        HSSFCell cell3 = row.getCell(3);
        HSSFCell cell4 = row.getCell(4);
        HSSFCell cell5 = row.getCell(5);
        HSSFCell cell6 = row.getCell(6);
        HSSFCell cell7 = row.getCell(7);
        HSSFCell cell8 = row.getCell(8);
        HSSFCell cell9 = row.getCell(9);
        HSSFCell cell10 = row.getCell(10);
        HSSFCell cell11 = row.getCell(11);
        HSSFCell cell12 = row.getCell(12);
        HSSFCell cell13 = row.getCell(13);
        HSSFCell cell14 = row.getCell(14);
        HSSFCell cell15 = row.getCell(15);
        HSSFCell cell16 = row.getCell(16);
        HSSFCell cell17 = row.getCell(17);
        HSSFCell cell18 = row.getCell(18);
        HSSFCell cell19 = row.getCell(19);
        HSSFCell cell20 = row.getCell(20);
        HSSFCell cell21 = row.getCell(21);
        HSSFCell cell22 = row.getCell(22);
        HSSFCell cell23 = row.getCell(23);
        HSSFCell cell24 = row.getCell(24);
        HSSFCell cell25 = row.getCell(25);
        HSSFCell cell26 = row.getCell(26);
        HSSFCell cell27 = row.getCell(27);
        HSSFCell cell28 = row.getCell(28);
        HSSFCell cell29 = row.getCell(29);
        HSSFCell cell30 = row.getCell(30);

        String areaname = null;
        if (cell != null) {
          if (cell.getCellType() == 0)
            areaname = df.format(cell.getNumericCellValue());
          else
            areaname = cell.getStringCellValue();
        }
        else {
          importareaname = importareaname + (i + 1) + ",";
        }

        String pboardno = null;
        if (cell1 != null) {
          if (cell1.getCellType() == 0)
            pboardno = df.format(cell1.getNumericCellValue());
          else {
            pboardno = cell1.getStringCellValue();
          }

        }

        String plineno = null;
        if (cell2 != null) {
          if (cell2.getCellType() == 0)
            plineno = df.format(cell2.getNumericCellValue());
          else {
            plineno = cell2.getStringCellValue();
          }
        }

        String prowno = null;
        if (cell3 != null) {
          if (cell3.getCellType() == 0)
            prowno = df.format(cell3.getNumericCellValue());
          else {
            prowno = cell3.getStringCellValue();
          }
        }

        String poleCode = null;
        if (cell4 != null) {
          if (cell4.getCellType() == 0)
            poleCode = df.format(cell4.getNumericCellValue());
          else
            poleCode = cell4.getStringCellValue();
        }
        else {
          importpolecode = importpolecode + (i + 1) + ",";
        }

        String poleName = null;
        if (cell5 != null) {
          if (cell5.getCellType() == 0)
            poleName = df.format(cell5.getNumericCellValue());
          else {
            poleName = cell5.getStringCellValue();
          }
        }

        String direction = null;
        if (cell6 != null) {
          if (cell6.getCellType() == 0)
            direction = df.format(cell6.getNumericCellValue());
          else {
            direction = cell6.getStringCellValue();
          }
        }

        String poleStd = null;
        if (cell7 != null) {
          if (cell7.getCellType() == 0) {
            poleStd = df.format(cell7.getNumericCellValue());
          } else {
            poleStd = cell7.getStringCellValue();
            if ((poleStd.equals("2*2+3*3+3*3")) || (poleStd.equals(" 2*2+3*3+3*3")))
              poleStd = "1";
            else if ((poleStd.equals("1800*18")) || (poleStd.equals(" 1800*18")))
              poleStd = "2";
            else if ((poleStd.equals("800*20")) || (poleStd.equals(" 800*20")))
              poleStd = "3";
            else if ((poleStd.equals("800*27")) || (poleStd.equals(" 800*27")))
              poleStd = "4";
            else if ((poleStd.equals("800*23")) || (poleStd.equals(" 800*23")))
              poleStd = "5";
            else if ((poleStd.equals("800*24")) || (poleStd.equals(" 800*24")))
              poleStd = "6";
            else if ((poleStd.equals("800*25")) || (poleStd.equals(" 800*25")))
              poleStd = "7";
            else if ((poleStd.equals("800*26")) || (poleStd.equals(" 800*26")))
              poleStd = "8";
            else if ((poleStd.equals("700*13")) || (poleStd.equals(" 700*13")))
              poleStd = "9";
            else if ((poleStd.equals("700*15")) || (poleStd.equals(" 700*15")))
              poleStd = "10";
            else if ((poleStd.equals("600*12")) || (poleStd.equals(" 600*12")))
              poleStd = "11";
            else if ((poleStd.equals("600*15")) || (poleStd.equals(" 600*15")))
              poleStd = "12";
            else if ((poleStd.equals("大号")) || (poleStd.equals(" 大号")))
              poleStd = "13";
            else if ((poleStd.equals("小号")) || (poleStd.equals(" 小号")))
              poleStd = "14";
            else if ((poleStd.equals("900*15")) || (poleStd.equals(" 900*15")))
              poleStd = "15";
            else if ((poleStd.equals("800*13")) || (poleStd.equals(" 800*13")))
              poleStd = "16";
            else if ((poleStd.equals("750*15")) || (poleStd.equals(" 750*15")))
              poleStd = "17";
            else if ((poleStd.equals("13*750")) || (poleStd.equals(" 13*750")))
              poleStd = "18";
            else if ((poleStd.equals("750*13")) || (poleStd.equals(" 750*13")))
              poleStd = "19";
            else if ((poleStd.equals("800*15")) || (poleStd.equals(" 800*15")))
              poleStd = "20";
            else if ((poleStd.equals("1000*15")) || (poleStd.equals(" 1000*15")))
              poleStd = "21";
            else if ((poleStd.equals("1000*18")) || (poleStd.equals(" 1000*18")))
              poleStd = "22";
            else if ((poleStd.equals("1200*20")) || (poleStd.equals(" 1200*20")))
              poleStd = "23";
          }
        }
        else {
          poleStd = "0";
        }

        String poleTypeEnumId = null;
        if (cell8 != null) {
          if (cell8.getCellType() == 0) {
            poleTypeEnumId = df.format(cell8.getNumericCellValue());
          } else {
            poleTypeEnumId = cell8.getStringCellValue();
            if ((poleTypeEnumId.equals("引上杆")) || (poleTypeEnumId.equals(" 引上杆")))
              poleTypeEnumId = "4";
            else if ((poleTypeEnumId.equals("中间杆")) || (poleTypeEnumId.equals(" 中间杆")))
              poleTypeEnumId = "5";
            else if ((poleTypeEnumId.equals("终端杆")) || (poleTypeEnumId.equals(" 终端杆")))
              poleTypeEnumId = "6";
            else if ((poleTypeEnumId.equals("角杆")) || (poleTypeEnumId.equals(" 角杆")))
              poleTypeEnumId = "2";
            else if ((poleTypeEnumId.equals("十字杆")) || (poleTypeEnumId.equals(" 十字杆")))
              poleTypeEnumId = "3";
            else if ((poleTypeEnumId.equals("分支杆")) || (poleTypeEnumId.equals(" 分支杆")))
              poleTypeEnumId = "1";
          }
        }
        else {
          poleTypeEnumId = "1";
        }

        String poleMaterial = null;
        if (cell9 != null) {
          if (cell9.getCellType() == 0) {
            poleMaterial = df.format(cell9.getNumericCellValue());
          } else {
            poleMaterial = cell9.getStringCellValue();
            if ((poleMaterial.equals("铁杆")) || (poleMaterial.equals(" 铁杆")))
              poleMaterial = "3";
            else if ((poleMaterial.equals("油杆")) || (poleMaterial.equals(" 油杆")))
              poleMaterial = "5";
            else if ((poleMaterial.equals("素杆")) || (poleMaterial.equals(" 素杆")))
              poleMaterial = "2";
            else if ((poleMaterial.equals("水泥杆")) || (poleMaterial.equals(" 水泥杆")))
              poleMaterial = "1";
            else if ((poleMaterial.equals("铁塔")) || (poleMaterial.equals(" 铁塔")))
              poleMaterial = "4";
          }
        }
        else {
          poleMaterial = "1";
        }

        String buriedDepth = null;
        if (cell10 != null) {
          if (cell10.getCellType() == 0)
            buriedDepth = df.format(cell10.getNumericCellValue());
          else {
            buriedDepth = cell10.getStringCellValue();
          }
        }

        String pserv = null;
        if (cell11 != null) {
          if (cell11.getCellType() == 0)
            pserv = df.format(cell11.getNumericCellValue());
          else {
            pserv = cell11.getStringCellValue();
          }

        }

        String fiberstationname = null;
        if (cell12 != null) {
          if (cell12.getCellType() == 0)
            fiberstationname = df.format(cell12.getNumericCellValue());
          else {
            fiberstationname = cell12.getStringCellValue();
          }
        }

        String cablename = null;
        if (cell13 != null) {
          if (cell13.getCellType() == 0)
            cablename = df.format(cell13.getNumericCellValue());
          else {
            cablename = cell13.getStringCellValue();
          }
        }

        String routename = null;
        if (cell14 != null) {
          if (cell14.getCellType() == 0)
            routename = df.format(cell14.getNumericCellValue());
          else {
            routename = cell14.getStringCellValue();
          }
        }

        String ofpcode = null;
        if (cell15 != null) {
          if (cell15.getCellType() == 0)
            ofpcode = df.format(cell15.getNumericCellValue());
          else {
            ofpcode = cell15.getStringCellValue();
          }
        }

        String ofpname = null;
        if (cell16 != null) {
          if (cell16.getCellType() == 0)
            ofpname = df.format(cell16.getNumericCellValue());
          else {
            ofpname = cell16.getStringCellValue();
          }
        }

        String createdate = "";
        if (cell17 != null) {
          if (cell17.getCellType() == 0)
            createdate = df.format(cell17.getNumericCellValue());
          else {
            createdate = cell17.getStringCellValue();
          }
        }

        String poleLength = null;
        if (cell18 != null) {
          if (cell18.getCellType() == 0)
            poleLength = df.format(cell18.getNumericCellValue());
          else {
            poleLength = cell18.getStringCellValue();
          }
        }

        String updatedate = null;
        if (cell19 != null) {
          if (cell19.getCellType() == 0)
            updatedate = df.format(cell19.getNumericCellValue());
          else {
            updatedate = cell19.getStringCellValue();
          }
        }

        String protectArea = null;
        if (cell20 != null) {
          if (cell20.getCellType() == 0)
            protectArea = df.format(cell20.getNumericCellValue());
          else {
            protectArea = cell20.getStringCellValue();
          }
        }

        String madetype = null;
        if (cell21 != null) {
          if (cell21.getCellType() == 0)
            madetype = df.format(cell21.getNumericCellValue());
          else {
            madetype = cell21.getStringCellValue();
          }
        }

        String poleface = null;
        if (cell22 != null) {
          if (cell22.getCellType() == 0)
            poleface = df.format(cell22.getNumericCellValue());
          else {
            poleface = cell22.getStringCellValue();
          }
        }

        String poleuse = null;
        if (cell23 != null) {
          if (cell23.getCellType() == 0)
            poleuse = df.format(cell23.getNumericCellValue());
          else {
            poleuse = cell23.getStringCellValue();
          }
        }

        String rentState = null;
        if (cell24 != null) {
          if (cell24.getCellType() == 0)
            rentState = df.format(cell24.getNumericCellValue());
          else {
            rentState = cell24.getStringCellValue();
          }
        }

        String productionDate = null;
        if (cell25 != null) {
          if (cell25.getCellType() == 0)
            productionDate = df.format(cell25.getNumericCellValue());
          else {
            productionDate = cell25.getStringCellValue();
          }
        }

        String enteringDate = null;
        if (cell26 != null) {
          if (cell26.getCellType() == 0)
            enteringDate = df.format(cell26.getNumericCellValue());
          else {
            enteringDate = cell26.getStringCellValue();
          }
        }

        String position = null;
        if (cell27 != null) {
          if (cell27.getCellType() == 0)
            position = df.format(cell27.getNumericCellValue());
          else {
            position = cell27.getStringCellValue();
          }
        }

        String linename = null;
        if (cell28 != null) {
          if (cell28.getCellType() == 0)
            linename = df.format(cell28.getNumericCellValue());
          else {
            linename = cell28.getStringCellValue();
          }
        }

        String renovate = null;
        if (cell29 != null) {
          if (cell29.getCellType() == 0)
            renovate = df.format(cell29.getNumericCellValue());
          else {
            renovate = cell29.getStringCellValue();
          }
        }

        String renovateDate = null;
        if (cell30 != null) {
          if (cell30.getCellType() == 0)
            renovateDate = df.format(cell30.getNumericCellValue());
          else {
            renovateDate = cell30.getStringCellValue();
          }
        }
        if (!(importareaname.equals(""))) continue; if (!(importpolecode.equals(""))) {
          continue;
        }
        eventTime = new Date();

        if (!(poleCode.contains("P")))
        {
          int n = 0;
          if ((poleCode.contains("）")) || (poleCode.contains(")"))) {
            n = (poleCode.contains("）")) ? poleCode.indexOf("）") : poleCode.indexOf(")");
            ++n;
          }
          for (int k = n; k < poleCode.length(); ++k) {
            if ((poleCode.charAt(k) >= '0') && (poleCode.charAt(k) <= '9'))
            {
              poleCode = poleCode.substring(0, k) + "P" + poleCode.substring(k, poleCode.length());
              break;
            }
            if (k != poleCode.length() - 1)
              continue;
            poleCode = "";
          }

          if (poleCode.equals("")) {
            continue;
          }
        }
        p.setPoleCode(poleCode);
        String name = poleCode.substring(0, poleCode.indexOf("P"));
        p.setPoleName(name);
        if (namestr.equals("")) {
          namestr = namestr + name;
        }
        else if (!(vetifyName(namestr, name))) {
          namestr = namestr + "," + name;
        }

        String no = "";
        String subno = "";
        if (poleCode.indexOf("P") + 1 == poleCode.length()) {
          no = "";
        } else {
          String str = poleCode.substring(poleCode.indexOf("P") + 1, poleCode.length()).trim();
          for (int k = 0; k < str.length(); ++k) {
            if ((str.charAt(k) >= '0') && (str.charAt(k) <= '9')) {
              no = no + str.charAt(k);
            } else {
              subno = str.substring(k + 1, str.length());
              break;
            }
          }
        }
        p.setPoleNo(no);
        p.setPoleSubNo(subno);
        p.setPoleNameSub(poleCode);
        p.setBuriedDepth(buriedDepth);
        p.setPoleLength(poleLength);
        p.setPoleTypeEnumId(Integer.valueOf(Integer.parseInt(poleTypeEnumId)));
        p.setPoleMaterial(poleMaterial);
        p.setPoleStd(poleStd);
        p.setLogOperater(this.userInfoBean.getUsername());
        if ((createdate != null) && (!(createdate.equals(""))))
          p.setCreationDate(sdf1.parse(createdate));
        else {
          p.setCreationDate(eventTime);
        }
        if ((updatedate != null) && (!(updatedate.equals(""))))
          p.setLastUpdateDate(sdf1.parse(updatedate));
        else {
          p.setLastUpdateDate(eventTime);
        }

        polelist.add(p);
      }
      if ((!(importareaname.equals(""))) || (!(importpolecode.equals("")))) {
        if ((!(importareaname.equals(""))) && (!(importpolecode.equals("")))) {
          this.errorMessage = new ErrorMessage();
          this.errorMessage.setMessage("第" + importareaname.substring(0, importareaname.length() - 1) + "行" + "'所属区域'列是必填项，但现在为空；" + "第" + importpolecode.substring(0, importpolecode.length() - 1) + "行'编号'是必填项，但现在为空，" + "请先填写好后再导入数据！");
          return "error"; }
        if (!(importareaname.equals(""))) {
          this.errorMessage = new ErrorMessage();
          this.errorMessage.setMessage("第" + importareaname.substring(0, importareaname.length() - 1) + "行" + "'所属区域'列是必填项，但现在为空，请先填写好后再导入数据！");
          return "error"; }
        if (!(importpolecode.equals(""))) {
          this.errorMessage = new ErrorMessage();
          this.errorMessage.setMessage("第" + importpolecode.substring(0, importpolecode.length() - 1) + "行" + "'编号'列是必填项，但现在为空，请先填写好后再导入数据！");
          return "error";
        }
      }

      String[] strs = namestr.split(",");
      List plist = new ArrayList();
      List pllist = new ArrayList();
      List plslist = new ArrayList();
      PolelineInfoBean pl = new PolelineInfoBean();
      PolelineSegmentInfoBean pls = new PolelineSegmentInfoBean();
      for (int i = 0; i < strs.length; ++i) {
        plist = new ArrayList();
        pl = new PolelineInfoBean();
        String name = strs[i];
        for (int j = polelist.size() - 1; j >= 0; --j) {
          p = (PoleInfoBean)polelist.get(j);
          if (name.equals(p.getPoleName())) {
            plist.add(p);
            polelist.remove(p);
          }
        }

        if (plist.size() != 1) {
          plist = sortPOLE(plist);
        }

        this.polelineInfoService.insertPoleList(plist);

        for (int j = 0; j < plist.size() - 1; ++j) {
          pls = new PolelineSegmentInfoBean();
          List<PoleInfoBean> pslist = new ArrayList();
          List pelist = new ArrayList();
          PoleInfoBean polestart;
          int m;
          PoleInfoBean pole;
          PoleInfoBean poleend;
          if ((j == 0) && (plist.size() > 1))
          {
            polestart = new PoleInfoBean();
            polestart.setPoleNameSub(((PoleInfoBean)plist.get(j)).getPoleNameSub());
            pslist = this.polelineInfoService.getPoleInfoBeanImport(polestart);
            polestart = (PoleInfoBean)pslist.get(0);
            if (pslist.size() > 1) {
              for (m = 1; m < pslist.size(); ++m) {
                pole = (PoleInfoBean)pslist.get(m);
                this.polelineInfoService.deletePoleInfoBean(pole);
                pole.setLogOperater(this.userInfoBean.getUsername());
                pole.setLogTime(new Date());
                pole.setDeletedFlag("1");
                this.polelineInfoService.poleLog(pole);
              }
            }
            poleend = new PoleInfoBean();
            poleend.setPoleNameSub(((PoleInfoBean)plist.get(plist.size() - 1)).getPoleNameSub());
            pelist = this.polelineInfoService.getPoleInfoBeanImport(poleend);
            poleend = (PoleInfoBean)pelist.get(0);
            if (pelist.size() > 1) {
              for (m = 1; m < pelist.size(); ++m) {
                pole = (PoleInfoBean)pslist.get(m);
                this.polelineInfoService.deletePoleInfoBean(pole);
                pole.setLogOperater(this.userInfoBean.getUsername());
                pole.setLogTime(new Date());
                pole.setDeletedFlag("1");
                this.polelineInfoService.poleLog(pole);
              }
            }
            pl.setEndDeviceId(poleend.getPoleId());
            pl.setEndDeviceName(poleend.getPoleNameSub());
            pl.setStartDeviceId(polestart.getPoleId());
            pl.setStartDeviceName(polestart.getPoleNameSub());
            pl.setPoleLineName(polestart.getPoleName());
            pl.setAreano(polestart.getAreano());
            pl.setAreaname(polestart.getAreaname());
            pl.setCreationDate(eventTime);
            pl.setLastUpdateDate(eventTime);
            pl.setPoleLineLevel("3");
            int plid = this.polelineInfoService.insertPolelineInfoBean(pl);
            pl.setLogOperater(this.userInfoBean.getUsername());
            pl.setLogTime(new Date());
            pl.setPoleLineId(Integer.valueOf(plid));
            this.polelineInfoService.polelineLog(pl);

            poleend = new PoleInfoBean();
            poleend.setPoleNameSub(((PoleInfoBean)plist.get(j + 1)).getPoleNameSub());
            pelist = this.polelineInfoService.getPoleInfoBeanImport(poleend);
            poleend = (PoleInfoBean)pelist.get(0);
            if (pelist.size() > 1) {
              for (m = 1; m < pelist.size(); ++m) {
                pole = (PoleInfoBean)pslist.get(m);
                this.polelineInfoService.deletePoleInfoBean(pole);
                pole.setLogOperater(this.userInfoBean.getUsername());
                pole.setLogTime(new Date());
                pole.setDeletedFlag("1");
                this.polelineInfoService.poleLog(pole);
              }
            }
            pls.setEndDeviceId(poleend.getPoleId());
            pls.setEndDeviceName(poleend.getPoleNameSub());
            pls.setStartDeviceId(polestart.getPoleId());
            pls.setStartDeviceName(polestart.getPoleNameSub());
            pls.setPoleLineId(pl.getPoleLineId());
            pls.setPoleLineSegmentName(pl.getPoleLineName() + "(" + polestart.getPoleNameSub() + "_" + poleend.getPoleNameSub() + ")");
            pls.setAreano(pl.getAreano());
            pls.setAreaname(pl.getAreaname());
            pls.setCreationDate(eventTime);
            pls.setLastUpdateDate(eventTime);
            plslist.add(pls);
          } else if (plist.size() > 1) {
            polestart = new PoleInfoBean();
            polestart.setPoleNameSub(((PoleInfoBean)plist.get(j)).getPoleNameSub());
            pslist = this.polelineInfoService.getPoleInfoBeanImport(polestart);
            polestart = (PoleInfoBean)pslist.get(0);
            if (pslist.size() > 1) {
              for ( i = 1; i < pslist.size(); ++i) {
            	 PoleInfoBean ms = (PoleInfoBean)pslist.get(i);
                this.polelineInfoService.deletePoleInfoBean(ms);
                ms.setLogOperater(this.userInfoBean.getUsername());
                ms.setLogTime(new Date());
                ms.setDeletedFlag("1");
                this.polelineInfoService.poleLog(ms);
              }
            }
            poleend = new PoleInfoBean();
            poleend.setPoleNameSub(((PoleInfoBean)plist.get(j + 1)).getPoleNameSub());
            pelist = this.polelineInfoService.getPoleInfoBeanImport(poleend);
            poleend = (PoleInfoBean)pelist.get(0);
            if (pelist.size() > 1) {
              for (m = 1; m < pelist.size(); ++m) {
                pole = (PoleInfoBean)pslist.get(m);
                this.polelineInfoService.deletePoleInfoBean(pole);
                pole.setLogOperater(this.userInfoBean.getUsername());
                pole.setLogTime(new Date());
                pole.setDeletedFlag("1");
                this.polelineInfoService.poleLog(pole);
              }
            }
            pls.setEndDeviceId(poleend.getPoleId());
            pls.setEndDeviceName(poleend.getPoleNameSub());
            pls.setStartDeviceId(polestart.getPoleId());
            pls.setStartDeviceName(polestart.getPoleNameSub());
            pls.setPoleLineId(pl.getPoleLineId());
            pls.setPoleLineSegmentName(pl.getPoleLineName() + "(" + polestart.getPoleNameSub() + "_" + poleend.getPoleNameSub() + ")");
            pls.setAreano(pl.getAreano());
            pls.setAreaname(pl.getAreaname());
            pls.setCreationDate(eventTime);
            pls.setLastUpdateDate(eventTime);
            pls.setLogOperater(this.userInfoBean.getUsername());
            plslist.add(pls);
          }
          if (plist.size() - 2 == j) {
            p = new PoleInfoBean();
            p.setPoleName(name);
            p.setPoleLineId(pl.getPoleLineId());
            this.polelineInfoService.updatePoleImport(p);
            p.setLogOperater(this.userInfoBean.getUsername());
            p.setLogTime(new Date());
            this.polelineInfoService.poleLog(p);
          }
        }
      }
      this.polelineInfoService.insertPolelineSegments(plslist);
      return "list2";
    } catch (Exception e) {
      log.error("DocumentAction.importEqut", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("档案设备信息格式错误，请仔细检查档案。"); }
    return "error";
  }

  public boolean vetifyName(String namestr, String name)
  {
    String[] strs = namestr.split(",");
    boolean flag = false;
    for (int i = 0; i < strs.length; ++i) {
      if (strs[i].equals(name)) {
        flag = true;
      }
    }
    return flag;
  }

  public List<PoleInfoBean> sortPOLE(List<PoleInfoBean> list)
  {
    /*Collections.sort(list, new Comparator() {
      public int compare(PoleInfoBean arg0, PoleInfoBean arg1) {
        String name1 = arg0.getPoleNameSub();
        String name2 = arg1.getPoleNameSub();
        if (arg0.getPoleNo().length() != arg1.getPoleNo().length()) {
          return Integer.valueOf(arg0.getPoleNo().length()).compareTo(Integer.valueOf(arg1.getPoleNo().length()));
        }
        if (arg0.getPoleNo().equals(arg1.getPoleNo())) {
          if ((arg0.getPoleSubNo() != null) && (!(arg0.getPoleSubNo().equals(""))) && (arg1.getPoleSubNo() != null) && (!(arg1.getPoleSubNo().equals(""))) && 
            (arg0.getPoleSubNo().length() != arg1.getPoleSubNo().length())) {
            return Integer.valueOf(arg0.getPoleSubNo().length()).compareTo(Integer.valueOf(arg1.getPoleSubNo().length()));
          }

          return name1.substring(name1.indexOf("P") + 1, name1.length()).compareTo(name2.substring(name2.indexOf("P") + 1, name2.length()));
        }
        return name1.substring(name1.indexOf("P") + 1, name1.length()).compareTo(name2.substring(name2.indexOf("P") + 1, name2.length()));
      }
    });*/
    return list;
  }

  public String polelinesegmentlistDW() throws Exception {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));

    PoleInfoBean poleInfoBean1 = new PoleInfoBean();
    PoleInfoBean poleInfoBean2 = new PoleInfoBean();
    this.resultList2 = new ArrayList();
    try
    {
      this.polelineSegmentInfoBean = new PolelineSegmentInfoBean();
      this.polelineSegmentInfoBean.setPoleLineSegmentId(Integer.valueOf(((String)this.ids.get(0)).trim()));
      this.polelineSegmentInfoBean = this.polelineInfoService.getPolelineSegmentInfoBean(this.polelineSegmentInfoBean);
      Integer sd = this.polelineSegmentInfoBean.getStartDeviceId();
      Integer ed = this.polelineSegmentInfoBean.getEndDeviceId();
      if ((sd != null) && (sd.intValue() != 0)) {
        poleInfoBean1.setPoleId(sd);
        poleInfoBean1 = this.polelineInfoService.getPoleInfoBean(poleInfoBean1);
        this.resultList2.add(poleInfoBean1);
      }
      if ((ed != null) && (ed.intValue() != 0)) {
        poleInfoBean2.setPoleId(ed);
        poleInfoBean2 = this.polelineInfoService.getPoleInfoBean(poleInfoBean2);
        this.resultList2.add(poleInfoBean2);
      }
      return "polelinesegmentlistdw";
    }
    catch (Exception e) {
      log.error("PolelineAction.addpole 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String polelineDW()
    throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    this.resultList2 = new ArrayList();
    try {
      this.polelineInfoBean = new PolelineInfoBean();
      this.polelineInfoBean.setPoleLineId(Integer.valueOf(((String)this.ids.get(0)).trim()));
      this.polelineInfoBean = this.polelineInfoService.getPolelineInfoBean(this.polelineInfoBean);

      if (this.polelineInfoBean != null) {
        Integer sd = this.polelineInfoBean.getStartDeviceId();
        Integer ed = this.polelineInfoBean.getEndDeviceId();
        PoleInfoBean poleInfoBean1;
        if ((sd != null) && (sd.intValue() != 0)) {
          poleInfoBean1 = new PoleInfoBean();
          poleInfoBean1.setPoleId(sd);
          poleInfoBean1 = this.polelineInfoService.getPoleInfoBean(poleInfoBean1);
          this.resultList2.add(poleInfoBean1);
        }
        PoleInfoBean poleInfoBean2;
        if ((ed != null) && (ed.intValue() != 0)) {
          poleInfoBean2 = new PoleInfoBean();
          poleInfoBean2.setPoleId(ed);
          poleInfoBean2 = this.polelineInfoService.getPoleInfoBean(poleInfoBean2);
          this.resultList2.add(poleInfoBean2);
        }

        this.polelineSegmentInfoBean = new PolelineSegmentInfoBean();
        this.polelineSegmentInfoBean.setPoleLineId(Integer.valueOf(((String)this.ids.get(0)).trim()));
        this.resultList1 = this.polelineInfoService.getPolelineSegmentListAll(this.polelineSegmentInfoBean);
        if (this.resultList1 != null) {
          for (int i = 0; i < this.resultList1.size(); ++i) {
            PolelineSegmentInfoBean polelineSegmentInfo = (PolelineSegmentInfoBean)this.resultList1.get(i);
            Integer sd1 = polelineSegmentInfo.getStartDeviceId();
            Integer ed1 = polelineSegmentInfo.getEndDeviceId();
            if ((sd1 != null) && (sd1.intValue() != 0)) {
              poleInfoBean1 = new PoleInfoBean();
              poleInfoBean1.setPoleId(sd1);
              poleInfoBean1 = this.polelineInfoService.getPoleInfoBean(poleInfoBean1);
              this.resultList2.add(poleInfoBean1);
            }
            if ((ed1 != null) && (ed1.intValue() != 0)) {
              poleInfoBean2 = new PoleInfoBean();
              poleInfoBean2.setPoleId(ed1);
              poleInfoBean2 = this.polelineInfoService.getPoleInfoBean(poleInfoBean2);
              this.resultList2.add(poleInfoBean2);
            }
          }
        }
      }
      return "polelinelistdw";
    }
    catch (Exception e) {
      log.error("PolelineAction.addpole 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String searchPlsToCable()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.cableRouteInfoBean = new CableRouteInfoBean();
      this.cableRouteInfoBean.setIdE(Integer.valueOf(((String)this.ids.get(0)).trim()));
      this.cableRouteList = this.polelineInfoService.getCableRouteList(this.cableRouteInfoBean);
      if (this.cableRouteList.size() != 0)
        this.success = false;
      else {
        this.success = true;
      }
      return "vetifyCableToPoleLineSegment";
    } catch (Exception e) {
      log.error("PointAction.checkpolelinesegmentJSON", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      e.printStackTrace(); }
    return "error";
  }

  public String addPoleLS()
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.poleInfoBean.setLimit(this.limit);
      this.poleInfoBean.setStart(this.start);
      this.poleInfoBean = this.polelineInfoService.addPoleLS(this.poleInfoBean);
      return "addPoleLS";
    } catch (Exception e) {
      log.error("PointAction.addPoleLS", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      e.printStackTrace(); }
    return "error";
  }

  public String poleIdList()
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      this.poleInfoBean.setLogTime(new Date());
      this.poleInfoBean.setLogOperater(this.userInfoBean.getUsername());
      int i = this.polelineInfoService.getPoleIdList(this.poleInfoBean);
      if (i == 1)
        this.success = true;
      else {
        this.success = false;
      }
      return "poleIdList";
    } catch (Exception e) {
      log.error("PointAction.poleIdList", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      e.printStackTrace(); }
    return "error";
  }

  public String getSuspensionlist()
  {
    try
    {
      if (this.suspension == null) {
        this.suspension = new SuspensionWireInfoBean();
      }
      this.suspension.setLimit(this.limit);
      this.suspension.setStart(this.start);
      this.suspension = this.polelineInfoService.getSuspension(this.suspension);
      return "getSuspensionlist";
    } catch (Exception e) {
      log.error("PolelineAction.getSuspensionlist", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      e.printStackTrace(); }
    return "error";
  }

  public String getSuspensionseglist()
  {
    try {
      if (this.suspensionseg == null) {
        this.suspensionseg = new SuspensionWireSegInfoBean();
      }
      this.suspensionseg.setLimit(this.limit);
      this.suspensionseg.setStart(this.start);
      this.suspensionseg = this.polelineInfoService.getSuspensionseg(this.suspensionseg);
      return "getSuspensionseglist";
    } catch (Exception e) {
      log.error("PolelineAction.getSuspensionseglist", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      e.printStackTrace(); }
    return "error";
  }

  public String getSupportlist()
  {
    try {
      if (this.support == null) {
        this.support = new SupportInfoBean();
      }
      this.support.setLimit(this.limit);
      this.support.setStart(this.start);
      this.support = this.polelineInfoService.getSupport(this.support);
      return "getSupportlist";
    } catch (Exception e) {
      log.error("PolelineAction.getSupportlist", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      e.printStackTrace(); }
    return "error";
  }

  public PipeInfoBean getPipeInfoBean()
  {
    return this.pipeInfoBean; }

  public void setPipeInfoBean(PipeInfoBean pipeInfoBean) {
    this.pipeInfoBean = pipeInfoBean; }

  public List<PipeInfoBean> getResultPipeList() {
    return this.resultPipeList; }

  public void setResultPipeList(List<PipeInfoBean> resultPipeList) {
    this.resultPipeList = resultPipeList; }

  public PipeSegmentInfoBean getPipeSegmentInfoBean() {
    return this.pipeSegmentInfoBean; }

  public void setPipeSegmentInfoBean(PipeSegmentInfoBean pipeSegmentInfoBean) {
    this.pipeSegmentInfoBean = pipeSegmentInfoBean; }

  public List<PipeSegmentInfoBean> getResultPipeSegmentList() {
    return this.resultPipeSegmentList;
  }

  public void setResultPipeSegmentList(List<PipeSegmentInfoBean> resultPipeSegmentList) {
    this.resultPipeSegmentList = resultPipeSegmentList;
  }

  public String getPolename() {
    return this.polename; }

  public void setPolename(String polename) {
    this.polename = polename; }

  public String getAreaname() {
    return this.areaname; }

  public void setAreaname(String areaname) {
    this.areaname = areaname; }

  public Boolean getFlag() {
    return this.flag; }

  public void setFlag(Boolean flag) {
    this.flag = flag; }

  public Date getCreationDateB() {
    return this.creationDateB; }

  public void setCreationDateB(Date creationDateB) {
    this.creationDateB = creationDateB; }

  public Date getCreationDateE() {
    return this.creationDateE; }

  public void setCreationDateE(Date creationDateE) {
    this.creationDateE = creationDateE; }

  public Date getLastUpdateDateB() {
    return this.lastUpdateDateB; }

  public void setLastUpdateDateB(Date lastUpdateDateB) {
    this.lastUpdateDateB = lastUpdateDateB; }

  public Date getLastUpdateDateE() {
    return this.lastUpdateDateE; }

  public void setLastUpdateDateE(Date lastUpdateDateE) {
    this.lastUpdateDateE = lastUpdateDateE; }

  public List<PoleInfoBean> getResultList2() {
    return this.resultList2; }

  public void setResultList2(List<PoleInfoBean> resultList2) {
    this.resultList2 = resultList2; }

  public Integer getPoleId() {
    return this.poleId; }

  public void setPoleId(Integer poleId) {
    this.poleId = poleId; }

  public String getPoleName() {
    return this.poleName; }

  public void setPoleName(String poleName) {
    this.poleName = poleName; }

  public String getPoleCode() {
    return this.poleCode; }

  public void setPoleCode(String poleCode) {
    this.poleCode = poleCode; }

  public Integer getPoleTypeEnumId() {
    return this.poleTypeEnumId; }

  public void setPoleTypeEnumId(Integer poleTypeEnumId) {
    this.poleTypeEnumId = poleTypeEnumId; }

  public String getPoleMaterial() {
    return this.poleMaterial; }

  public void setPoleMaterial(String poleMaterial) {
    this.poleMaterial = poleMaterial; }

  public String getPoleLength() {
    return this.poleLength; }

  public void setPoleLength(String poleLength) {
    this.poleLength = poleLength; }

  public String getBuriedDepth() {
    return this.buriedDepth; }

  public void setBuriedDepth(String buriedDepth) {
    this.buriedDepth = buriedDepth; }

  public String getPoleRadius() {
    return this.poleRadius; }

  public void setPoleRadius(String poleRadius) {
    this.poleRadius = poleRadius; }

  public String getPoleAddress() {
    return this.poleAddress; }

  public void setPoleAddress(String poleAddress) {
    this.poleAddress = poleAddress; }

  public String getPoleLongitude() {
    return this.poleLongitude; }

  public void setPoleLongitude(String poleLongitude) {
    this.poleLongitude = poleLongitude; }

  public String getPoleLatitude() {
    return this.poleLatitude; }

  public void setPoleLatitude(String poleLatitude) {
    this.poleLatitude = poleLatitude; }

  public PoleInfoBean getPoleInfoBean() {
    return this.poleInfoBean; }

  public void setPoleInfoBean(PoleInfoBean poleInfoBean) {
    this.poleInfoBean = poleInfoBean; }

  public List<PolelineSegmentInfoBean> getResultList1() {
    return this.resultList1; }

  public void setResultList1(List<PolelineSegmentInfoBean> resultList1) {
    this.resultList1 = resultList1; }

  public Integer getPoleLineSegmentId() {
    return this.poleLineSegmentId; }

  public void setPoleLineSegmentId(Integer poleLineSegmentId) {
    this.poleLineSegmentId = poleLineSegmentId; }

  public String getPoleLineSegmentName() {
    return this.poleLineSegmentName; }

  public void setPoleLineSegmentName(String poleLineSegmentName) {
    this.poleLineSegmentName = poleLineSegmentName; }

  public String getPoleLineSegmentCode() {
    return this.poleLineSegmentCode; }

  public void setPoleLineSegmentCode(String poleLineSegmentCode) {
    this.poleLineSegmentCode = poleLineSegmentCode; }

  public String getPoleLineSegmentLength() {
    return this.poleLineSegmentLength; }

  public void setPoleLineSegmentLength(String poleLineSegmentLength) {
    this.poleLineSegmentLength = poleLineSegmentLength; }

  public String getStatus() {
    return this.status; }

  public void setStatus(String status) {
    this.status = status; }

  public Integer getStartDeviceId() {
    return this.startDeviceId; }

  public void setStartDeviceId(Integer startDeviceId) {
    this.startDeviceId = startDeviceId; }

  public Integer getEndDeviceId() {
    return this.endDeviceId; }

  public void setEndDeviceId(Integer endDeviceId) {
    this.endDeviceId = endDeviceId; }

  public Integer getConstructionSharingEnumId() {
    return this.constructionSharingEnumId; }

  public void setConstructionSharingEnumId(Integer constructionSharingEnumId) {
    this.constructionSharingEnumId = constructionSharingEnumId; }

  public String getConstructionSharingOrg() {
    return this.constructionSharingOrg; }

  public void setConstructionSharingOrg(String constructionSharingOrg) {
    this.constructionSharingOrg = constructionSharingOrg; }

  public Integer getSharingTypeEnumId() {
    return this.sharingTypeEnumId; }

  public void setSharingTypeEnumId(Integer sharingTypeEnumId) {
    this.sharingTypeEnumId = sharingTypeEnumId; }

  public PolelineSegmentInfoBean getPolelineSegmentInfoBean() {
    return this.polelineSegmentInfoBean;
  }

  public void setPolelineSegmentInfoBean(PolelineSegmentInfoBean polelineSegmentInfoBean) {
    this.polelineSegmentInfoBean = polelineSegmentInfoBean; }

  public List<PolelineInfoBean> getResultList() {
    return this.resultList;
  }

  public void setResultList(List<PolelineInfoBean> resultList) {
    this.resultList = resultList;
  }

  public PolelineInfoBean getPolelineInfoBean()
  {
    return this.polelineInfoBean;
  }

  public void setPolelineInfoBean(PolelineInfoBean polelineInfoBean) {
    this.polelineInfoBean = polelineInfoBean;
  }

  public Integer getPoleLineId() {
    return this.poleLineId; }

  public void setPoleLineId(Integer poleLineId) {
    this.poleLineId = poleLineId; }

  public String getPoleLineName() {
    return this.poleLineName; }

  public void setPoleLineName(String poleLineName) {
    this.poleLineName = poleLineName;
  }

  public String getPoleLineCode() {
    return this.poleLineCode;
  }

  public void setPoleLineCode(String poleLineCode) {
    this.poleLineCode = poleLineCode;
  }

  public Integer getMaintenanceAreaId() {
    return this.maintenanceAreaId;
  }

  public void setMaintenanceAreaId(Integer maintenanceAreaId) {
    this.maintenanceAreaId = maintenanceAreaId;
  }

  public String getPoleLineLevel() {
    return this.poleLineLevel;
  }

  public void setPoleLineLevel(String poleLineLevel) {
    this.poleLineLevel = poleLineLevel;
  }

  public String getPoleLineLength() {
    return this.poleLineLength;
  }

  public void setPoleLineLength(String poleLineLength) {
    this.poleLineLength = poleLineLength;
  }

  public String getStartDeviceName() {
    return this.startDeviceName;
  }

  public void setStartDeviceName(String startDeviceName) {
    this.startDeviceName = startDeviceName;
  }

  public String getEndDeviceName() {
    return this.endDeviceName;
  }

  public void setEndDeviceName(String endDeviceName) {
    this.endDeviceName = endDeviceName;
  }

  public Integer getMaintenanceModeEnumId() {
    return this.maintenanceModeEnumId;
  }

  public void setMaintenanceModeEnumId(Integer maintenanceModeEnumId) {
    this.maintenanceModeEnumId = maintenanceModeEnumId;
  }

  public String getMaintenanceOrgId() {
    return this.maintenanceOrgId;
  }

  public void setMaintenanceOrgId(String maintenanceOrgId) {
    this.maintenanceOrgId = maintenanceOrgId;
  }

  public String getMaintainerId() {
    return this.maintainerId;
  }

  public void setMaintainerId(String maintainerId) {
    this.maintainerId = maintainerId;
  }

  public String getThirdPartyMaintenanceOrg() {
    return this.thirdPartyMaintenanceOrg;
  }

  public void setThirdPartyMaintenanceOrg(String thirdPartyMaintenanceOrg) {
    this.thirdPartyMaintenanceOrg = thirdPartyMaintenanceOrg;
  }

  public Date getRenewalExpirationDate() {
    return this.renewalExpirationDate;
  }

  public void setRenewalExpirationDate(Date renewalExpirationDate) {
    this.renewalExpirationDate = renewalExpirationDate;
  }

  public Integer getMaintenanceTypeEnumId() {
    return this.maintenanceTypeEnumId;
  }

  public void setMaintenanceTypeEnumId(Integer maintenanceTypeEnumId) {
    this.maintenanceTypeEnumId = maintenanceTypeEnumId;
  }

  public String getPurchasedMaintenanceTime() {
    return this.purchasedMaintenanceTime;
  }

  public void setPurchasedMaintenanceTime(String purchasedMaintenanceTime) {
    this.purchasedMaintenanceTime = purchasedMaintenanceTime;
  }

  public String getProjectCode() {
    return this.projectCode;
  }

  public void setProjectCode(String projectCode) {
    this.projectCode = projectCode;
  }

  public String getProjectName() {
    return this.projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public Date getProjectWarrantyExpireDate() {
    return this.projectWarrantyExpireDate;
  }

  public void setProjectWarrantyExpireDate(Date projectWarrantyExpireDate) {
    this.projectWarrantyExpireDate = projectWarrantyExpireDate;
  }

  public Integer getResourceLifePeriodEnumId() {
    return this.resourceLifePeriodEnumId;
  }

  public void setResourceLifePeriodEnumId(Integer resourceLifePeriodEnumId) {
    this.resourceLifePeriodEnumId = resourceLifePeriodEnumId;
  }

  public Date getCreationDate() {
    return this.creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public Date getLastUpdateDate() {
    return this.lastUpdateDate;
  }

  public void setLastUpdateDate(Date lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate;
  }

  public String getDeletedFlag() {
    return this.deletedFlag;
  }

  public void setDeletedFlag(String deletedFlag) {
    this.deletedFlag = deletedFlag;
  }

  public Date getDeletionDate() {
    return this.deletionDate;
  }

  public void setDeletionDate(Date deletionDate) {
    this.deletionDate = deletionDate;
  }

  public String getProvinceId() {
    return this.provinceId;
  }

  public void setProvinceId(String provinceId) {
    this.provinceId = provinceId;
  }

  public String getAreano() {
    return this.areano;
  }

  public void setAreano(String areano) {
    this.areano = areano;
  }

  public ErrorMessage getErrorMessage() {
    return this.errorMessage;
  }

  public void setErrorMessage(ErrorMessage errorMessage) {
    this.errorMessage = errorMessage;
  }

  public static Logger getLog() {
    return log;
  }

  public static String getMODULENAME() {
    return "polelinemanage";
  }

  public PolelineInfoService getPolelineInfoService() {
    return this.polelineInfoService;
  }

  public void setPolelineInfoService(PolelineInfoService polelineInfoService) {
    this.polelineInfoService = polelineInfoService; }

  public Integer getEid() {
    return this.eid; }

  public void setEid(Integer eid) {
    this.eid = eid; }

  public CableRouteInfoBean getCableRouteInfoBean() {
    return this.cableRouteInfoBean; }

  public void setCableRouteInfoBean(CableRouteInfoBean cableRouteInfoBean) {
    this.cableRouteInfoBean = cableRouteInfoBean; }

  public List<CableRouteInfoBean> getCableRouteList() {
    return this.cableRouteList; }

  public void setCableRouteList(List<CableRouteInfoBean> cableRouteList) {
    this.cableRouteList = cableRouteList; }

  public PipeInfoService getPipeInfoService() {
    return this.pipeInfoService; }

  public void setPipeInfoService(PipeInfoService pipeInfoService) {
    this.pipeInfoService = pipeInfoService; }

  public String getPoleNo() {
    return this.poleNo; }

  public void setPoleNo(String poleNo) {
    this.poleNo = poleNo; }

  public String getPoleSubNo() {
    return this.poleSubNo; }

  public void setPoleSubNo(String poleSubNo) {
    this.poleSubNo = poleSubNo; }

  public String getPoleNameSub() {
    return this.poleNameSub; }

  public void setPoleNameSub(String poleNameSub) {
    this.poleNameSub = poleNameSub; }

  public String getCpoleNo() {
    return this.cpoleNo; }

  public void setCpoleNo(String cpoleNo) {
    this.cpoleNo = cpoleNo; }

  public String getCpoleSubNo() {
    return this.cpoleSubNo; }

  public void setCpoleSubNo(String cpoleSubNo) {
    this.cpoleSubNo = cpoleSubNo; }

  public UserInfoBean getUserInfoBean() {
    return this.userInfoBean; }

  public void setUserInfoBean(UserInfoBean userInfoBean) {
    this.userInfoBean = userInfoBean; }

  public Integer getStart() {
    return this.start; }

  public void setStart(Integer start) {
    this.start = start; }

  public Integer getLimit() {
    return this.limit; }

  public void setLimit(Integer limit) {
    this.limit = limit; }

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
    this.deleteMsg = deleteMsg;
  }

  public String getPlids() {
    return this.plids;
  }

  public void setPlids(String plids) {
    this.plids = plids;
  }

  public String getFilepath() {
    return this.filepath;
  }

  public void setFilepath(String filepath) {
    this.filepath = filepath;
  }

  public File getUpFile() {
    return this.upFile;
  }

  public void setUpFile(File upFile) {
    this.upFile = upFile;
  }

  public SuspensionWireInfoBean getSuspension() {
    return this.suspension;
  }

  public void setSuspension(SuspensionWireInfoBean suspension) {
    this.suspension = suspension;
  }

  public SuspensionWireSegInfoBean getSuspensionseg() {
    return this.suspensionseg;
  }

  public void setSuspensionseg(SuspensionWireSegInfoBean suspensionseg) {
    this.suspensionseg = suspensionseg;
  }

  public SupportInfoBean getSupport() {
    return this.support;
  }

  public void setSupport(SupportInfoBean support) {
    this.support = support;
  }
}