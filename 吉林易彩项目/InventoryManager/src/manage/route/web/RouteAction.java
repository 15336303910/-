package manage.route.web;

import base.exceptions.DataAccessException;
import base.util.CommonUtil;
import base.util.ErrorMessage;
import base.web.PaginationAction;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import manage.equt.pojo.EqutInfoBean;
import manage.pipe.pojo.FaceInfoBean;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.pipe.pojo.TubeInfoBean;
import manage.poleline.pojo.PolelineSegmentInfoBean;
import manage.route.pojo.CableInfoBean;
import manage.route.pojo.CableRouteInfoBean;
import manage.route.pojo.FiberBoxInfoBean;
import manage.route.pojo.OppoFiberInfoBean;
import manage.route.pojo.RouteInfoBean;
import manage.route.sercice.RouteService;
import manage.user.pojo.UserInfoBean;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class RouteAction extends PaginationAction
{
  private static final long serialVersionUID = -3673614470853046445L;
  private static final Logger log = Logger.getLogger(RouteAction.class);
  private UserInfoBean userInfoBean;
  private ErrorMessage errorMessage;
  private RouteService routeService;
  private Integer total;
  private Integer start;
  private Integer limit;
  private String sort;
  private String dir;
  private FiberBoxInfoBean fiberGrid;
  private RouteInfoBean routeGrid;
  private CableInfoBean cableGrid;
  private RouteInfoBean searchRoute;
  private CableInfoBean searchCable;
  private RouteInfoBean newRoute;
  private RouteInfoBean mergeRoute;
  private CableInfoBean newCable;
  private CableInfoBean mergeCable;
  private CableInfoBean updateCable;
  private RouteInfoBean updateRoute;
  private String confirmDelete;
  private RouteInfoBean deleteRoute;
  private boolean success = false;

  private String verifyMsg = null;
  private String ids;
  private OppoFiberInfoBean oppoFiberGrid;
  private OppoFiberInfoBean searchOppoFiber;
  private List<String> idss;
  private CableRouteInfoBean cableRouteInfoBean;
  private List<CableRouteInfoBean> cableRouteList;
  private PipeSegmentInfoBean pipeSegmentInfoBean;
  private PolelineSegmentInfoBean polelineSegmentInfoBean;
  private TubeInfoBean tubeInfoBean;
  private CableRouteInfoBean data;
  private String filepath;
  private File[] excle;
  private String routeid1;
  private String routeid2;
  private String cableid1;
  private String cableid2;
  private String tubeId;
  private CableInfoBean cable1;
  private CableInfoBean cable2;
  List<CableInfoBean> cableList;
  List<CableInfoBean> cableList1;

  public String searchRoute()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));

      if (this.searchRoute == null) {
        this.searchRoute = new RouteInfoBean();
      }
      this.searchRoute.setSort(this.sort);
      this.searchRoute.setDir(this.dir);
      this.searchRoute.setStart(this.start);
      this.searchRoute.setLimit(this.limit);
      this.routeGrid = this.routeService.getRouteGridrByPage(this.searchRoute);
      return "searchRoute";
    }
    catch (Exception e) {
      log.error("RouteAction.searchRoute", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String searchCable()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));

      if (this.searchCable == null) {
        this.searchCable = new CableInfoBean();
      }
      this.searchCable.setSort(this.sort);
      this.searchCable.setDir(this.dir);
      this.searchCable.setStart(this.start);
      this.searchCable.setLimit(this.limit);
      this.cableGrid = this.routeService.getCableGridrByPage(this.searchCable);
      return "searchCable";
    }
    catch (Exception e) {
      log.error("RouteAction.searchCable", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String verifyRoute()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if ((this.newRoute == null) || (this.newRoute.getRoutename() == null) || 
        ("".equals(this.newRoute.getRoutename()))) {
        this.success = false;
        this.verifyMsg = "请输入光缆名";
      } else {
        int n = this.routeService.getVerifyRoute(this.newRoute);
        if (n == 0) {
          this.success = true;
          this.verifyMsg = "光缆名可以使用";
        } else if (n >= 1) {
          this.success = false;
          this.verifyMsg = "光缆名已存在";
        }
      }
      return "verifyRoute";
    } catch (Exception e) {
      log.error("RouteAction.verifyRoute", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String addRoute()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      int n = this.routeService.insertNewRoute(this.newRoute);
      if (n == 1) {
        this.success = true;

        StringBuffer description = new StringBuffer("用户");
        description.append(this.userInfoBean.getRealname());
        description.append("添加光缆成功");
      } else {
        this.success = false;
      }
      return "addRoute";
    } catch (Exception e) {
      log.error("RouteAction.verifyRoute", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String mergeRoute()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if ((this.ids == null) || ("".equals(this.ids))) {
        this.success = false;
        return "mergeRoute";
      }
      int n = this.routeService.stopMergeRoute(this.ids, this.mergeRoute);
      if (n == 1) {
        this.success = true;

        StringBuffer description = new StringBuffer("用户");
        description.append(this.userInfoBean.getRealname());
        description.append("合并光缆成功");
      }
      else {
        this.success = false;
      }
      return "mergeRoute";
    } catch (Exception e) {
      log.error("RouteAction.mergeRoute", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String updateRouteBean()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      int n = this.routeService.updateRouteBean(this.updateRoute);
      if (n == 1) {
        this.success = true;

        StringBuffer description = new StringBuffer("用户");
        description.append(this.userInfoBean.getRealname());
        description.append("修改光缆成功");
      }
      else {
        this.success = false;
      }
      return "updateRoute";
    }
    catch (Exception e) {
      log.error("RouteAction.updateRoute", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String addCable()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      String startid = this.newCable.getStarteid();
      String endid = this.newCable.getEndeid();
      EqutInfoBean equtInfoBean = new EqutInfoBean();
      equtInfoBean.setEid(startid);
      equtInfoBean = this.routeService.getEqutBean(equtInfoBean);
      EqutInfoBean equtInfo = new EqutInfoBean();
      equtInfo.setEid(endid);
      equtInfo = this.routeService.getEqutBean(equtInfo);
      if ((equtInfoBean != null) && (equtInfo != null))
        this.newCable.setAreanoLink("*" + equtInfoBean.getAreano() + "*" + equtInfo.getAreano() + "*");
      int n = this.routeService.insertNewCable(this.newCable);
      if (n == 1) {
        this.success = true;
      }
      else {
        this.success = false;
      }
      return "addCable";
    } catch (Exception e) {
      log.error("RouteAction.addCable", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String verifyCable()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if ((this.newCable == null) || (this.newCable.getCablename() == null) || 
        ("".equals(this.newCable.getCablename()))) {
        this.success = false;
        this.verifyMsg = "请输入光缆名";
      } else {
        int n = this.routeService.getVerifyCable(this.newCable);
        if (n == 0) {
          this.success = true;
          this.verifyMsg = "光缆名可以使用";
        } else if (n >= 1) {
          this.success = false;
          this.verifyMsg = "光缆名已存在";
        }
      }
      return "verifyCable";
    } catch (Exception e) {
      log.error("RouteAction.verifyRoute", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String mergeCable()
    throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      if ((this.ids == null) || ("".equals(this.ids))) {
        this.success = false;
        return "mergeCable";
      }
      int n = this.routeService.stopMergeCable(this.ids, this.mergeCable);
      if (n == 1) {
        this.success = true;

        StringBuffer description = new StringBuffer("用户");
        description.append(this.userInfoBean.getRealname());
        description.append("合并光缆段成功");
      }
      else {
        this.success = false;
      }
      return "mergeCable";
    }
    catch (Exception e)
    {
      log.error("RouteAction.mergeCable", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String updateCableBean()
    throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      String startid = this.updateCable.getStarteid();
      String endid = this.updateCable.getEndeid();
      EqutInfoBean equtInfoBean = new EqutInfoBean();
      equtInfoBean.setEid(startid);
      equtInfoBean = this.routeService.getEqutBean(equtInfoBean);
      EqutInfoBean equtInfo = new EqutInfoBean();
      equtInfo.setEid(endid);
      equtInfo = this.routeService.getEqutBean(equtInfo);
      this.updateCable.setAreanoLink("*" + equtInfoBean.getAreano() + "*" + equtInfo.getAreano() + "*");
      int n = this.routeService.updateCableBean(this.updateCable);
      if (n == 1) {
        this.success = true;

        StringBuffer description = new StringBuffer("用户");
        description.append(this.userInfoBean.getRealname());
        description.append("修改光缆段成功");
      }
      else {
        this.success = false;
      }
      return "updateCable";
    }
    catch (Exception e) {
      log.error("RouteAction.updateRoute", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String searchOppoFiber()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if (this.searchOppoFiber == null) {
        this.searchOppoFiber = new OppoFiberInfoBean();
      }
      this.searchOppoFiber.setSort(this.sort);
      this.searchOppoFiber.setDir(this.dir);
      this.searchOppoFiber.setStart(this.start);
      this.searchOppoFiber.setLimit(this.limit);
      this.oppoFiberGrid = this.routeService.searchOppoFiber(this.searchOppoFiber);
      return "searchOppoFiber";
    } catch (Exception e) {
      log.error("RouteAction.searchOppoFiber", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String oppoFiberGrid() throws Exception {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      return "oppoFiberGrid";
    }
    catch (Exception e) {
      log.error("RouteAction.oppoFiberList", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String editcable() throws Exception
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.updateCable = new CableInfoBean();
      this.updateCable.setCableid(Integer.valueOf(((String)this.idss.get(0)).trim()));
      this.updateCable = this.routeService.getCable(this.updateCable);
      return "editcable";
    } catch (Exception e) {
      log.error("RouteAction.editCable 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String searchcabletoroute()
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.updateCable = new CableInfoBean();
      this.updateCable.setCableid(Integer.valueOf(((String)this.idss.get(0)).trim()));
      this.updateCable = this.routeService.getTubeToCable(this.updateCable);
      if ((this.updateCable.getRouteid().equals("")) || (this.updateCable.getRouteid() == null))
        this.success = false;
      else {
        this.success = true;
      }
      return "vertifyCalToRoute";
    } catch (Exception e) {
      log.error("PointAction.searchTubeJSON", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String removeCable()
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.updateCable = new CableInfoBean();
      this.updateCable.setCableid(Integer.valueOf(((String)this.idss.get(0)).trim()));
      int n = this.routeService.updateCableRouteId(this.updateCable).intValue();
      if (n == 1)
        this.success = true;
      else {
        this.success = false;
      }
      return "removeCable";
    } catch (Exception e) {
      log.error("RouteAction.export 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String serchJbCable() throws Exception {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if (this.searchCable == null) {
        this.searchCable = new CableInfoBean();
      }
      this.cableGrid = this.routeService.getJbCableList(this.searchCable);
      return "jbCableList";
    } catch (Exception e) {
      log.error("EqutAction.addequt", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String addRouteAndCable() throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      String[] sid = this.ids.split(",");
      if ((this.mergeRoute.getRouteid() == null) || (this.mergeRoute.getRouteid().intValue() == 0)) {
        int routeid = this.routeService.insertRoute(this.mergeRoute).intValue();
        String routename = this.mergeRoute.getRoutename();
        if (sid != null) {
          for (int i = 0; i < sid.length; ++i) {
            this.updateCable = new CableInfoBean();
            this.updateCable.setRoutename(routename);
            this.updateCable.setRouteid(routeid+"");
            this.updateCable.setCableid(Integer.valueOf(Integer.parseInt(sid[i])));
            this.routeService.updateJbCable(this.updateCable);
          }
        }
      }
      else if (sid != null) {
        for (int i = 0; i < sid.length; ++i) {
          this.updateCable = new CableInfoBean();
          this.updateCable.setRoutename(this.mergeRoute.getRoutename());
          this.updateCable.setRouteid(this.mergeRoute.getRouteid().toString());
          this.updateCable.setCableid(Integer.valueOf(Integer.parseInt(sid[i])));
          this.routeService.updateJbCable(this.updateCable);
        }
      }

      this.success = true;
      return "addRouteByCable";
    } catch (Exception e) {
      log.error("RouteAction.editRoute 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String cableRoutelist() throws Exception
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if ((this.idss != null) || (this.idss.equals(""))) {
        if (this.cableRouteInfoBean != null) {
          this.cableRouteInfoBean.setOptCableId(Integer.valueOf(Integer.parseInt(((String)this.idss.get(0)).trim())));
        }
        else {
          this.cableRouteInfoBean = new CableRouteInfoBean();
          this.cableRouteInfoBean.setOptCableId(Integer.valueOf(Integer.parseInt(((String)this.idss.get(0)).trim())));
        }
        this.newCable = new CableInfoBean();
        this.newCable.setCableid(Integer.valueOf(Integer.parseInt(((String)this.idss.get(0)).trim())));
        this.newCable = this.routeService.getCable(this.newCable);
      }
      this.cableRouteList = this.routeService.getCableRouteList(this.cableRouteInfoBean);

      if (this.cableRouteList.size() != 0) {
        for (CableRouteInfoBean cableRouteInfo : this.cableRouteList) {
          cableRouteInfo.setCablename(this.newCable.getCablename());
          if (cableRouteInfo != null) {
            Integer ide = cableRouteInfo.getIdE();
            Integer tubeid = cableRouteInfo.getTubeId();
            if ((ide != null) && (ide.intValue() != 0)) {
              this.polelineSegmentInfoBean = new PolelineSegmentInfoBean();
              this.polelineSegmentInfoBean.setPoleLineSegmentId(cableRouteInfo.getIdE());
              this.polelineSegmentInfoBean = this.routeService.getPolelineSegment(this.polelineSegmentInfoBean);
              if (this.polelineSegmentInfoBean != null) {
                cableRouteInfo.setPipepolename(this.polelineSegmentInfoBean.getPoleLineSegmentName());
              }
            }
            if ((tubeid != null) && (tubeid.intValue() != 0)) {
              this.tubeInfoBean = new TubeInfoBean();
              this.tubeInfoBean.setTubeId(cableRouteInfo.getTubeId());
              this.tubeInfoBean = this.routeService.getTubeById(this.tubeInfoBean);
              if ((this.tubeInfoBean == null) || 
                (this.tubeInfoBean.getPipeSegmentId() == null) || (this.tubeInfoBean.getPipeSegmentId().equals(""))) continue;
              this.pipeSegmentInfoBean = new PipeSegmentInfoBean();
              this.pipeSegmentInfoBean.setPipeSegmentId(Integer.valueOf(Integer.parseInt(this.tubeInfoBean.getPipeSegmentId())));
              this.pipeSegmentInfoBean = this.routeService.getPipeSegmentInfoBean(this.pipeSegmentInfoBean);
              if (this.pipeSegmentInfoBean != null) {
                cableRouteInfo.setPipepolename(this.pipeSegmentInfoBean.getPipeSegmentName());
              }
            }
          }
        }

      }

      this.data = new CableRouteInfoBean();
      this.data.setCableroutes(this.cableRouteList);
      int total = this.routeService.getCableRouteCount(this.cableRouteInfoBean);
      this.data.setTotal(Integer.valueOf(total));
      return "cableRoutelist";
    } catch (DataAccessException e) {
      log.error("RouteAction.CableList 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String doaddCableRoute() throws Exception
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.newCable = new CableInfoBean();
      this.newCable.setCableid(this.cableRouteInfoBean.getOptCableId());
      int optCableId = this.cableRouteInfoBean.getOptCableId().intValue();
      String cablename = this.cableRouteInfoBean.getCablename();

      if (this.cableRouteInfoBean != null) {
        Date creationDate = new Date();
        this.cableRouteInfoBean.setCreationDate(creationDate);
        this.cableRouteInfoBean.setLastUpdateDate(creationDate);

        this.routeService.insertCableRoute(this.cableRouteInfoBean);
        if (this.cableRouteInfoBean.getTubeId() != null) {
          this.tubeInfoBean = new TubeInfoBean();
          this.tubeInfoBean.setTubeId(this.cableRouteInfoBean.getTubeId());
          this.tubeInfoBean = this.routeService.getTubeById(this.tubeInfoBean);
          String bearCableSegmentId = this.tubeInfoBean.getBearCableSegmentId();
          String bearCableSegment = this.tubeInfoBean.getBearCableSegment();
          String[] ids = bearCableSegmentId.split(",");
          boolean flags = true;
          for (String id : ids) {
            if (id.equals(optCableId)) {
              flags = false;
              break;
            }
            flags = true;
          }

          if (flags) {
            bearCableSegmentId = bearCableSegmentId + optCableId + ",";
            bearCableSegment = bearCableSegment + cablename + ",";
            this.tubeInfoBean.setBearCableSegmentId(bearCableSegmentId);
            this.tubeInfoBean.setBearCableSegment(bearCableSegment);
            this.routeService.updateTube(this.tubeInfoBean);
          }
        }
      }
      this.success = true;
      return "addCR";
    } catch (Exception e) {
      log.error("EqutAction.addequt", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String editcableroute() throws Exception {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.cableRouteInfoBean = new CableRouteInfoBean();
      this.cableRouteInfoBean.setId(Integer.valueOf(((String)this.idss.get(0)).trim()));
      this.cableRouteInfoBean = this.routeService.getCableRoute(this.cableRouteInfoBean);
      if (this.cableRouteInfoBean != null) {
        Integer tubeid = this.cableRouteInfoBean.getTubeId();
        Integer ide = this.cableRouteInfoBean.getIdE();
        if ((tubeid != null) && (tubeid.intValue() != 0)) {
          this.tubeInfoBean = new TubeInfoBean();
          this.tubeInfoBean.setTubeId(tubeid);
          this.tubeInfoBean = this.routeService.getTubeById(this.tubeInfoBean);
          this.cableRouteInfoBean.setTubeName(this.tubeInfoBean.getTubeName());
        } else if ((ide != null) && (ide.intValue() != 0)) {
          this.polelineSegmentInfoBean = new PolelineSegmentInfoBean();
          this.polelineSegmentInfoBean.setPoleLineSegmentId(ide);
          this.polelineSegmentInfoBean = this.routeService.getPolelineSegment(this.polelineSegmentInfoBean);
          this.cableRouteInfoBean.setPoleLineSegmentName(this.polelineSegmentInfoBean.getPoleLineSegmentName());
        }
        if (this.cableRouteInfoBean.getOptCableId() != null) {
          this.newCable = new CableInfoBean();
          this.newCable.setCableid(this.cableRouteInfoBean.getOptCableId());
          this.newCable = this.routeService.getCable(this.newCable);
          this.cableRouteInfoBean.setCablename(this.newCable.getCablename());
        }
      }
      return "loadCR";
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

  public String doupdateCableRoute() throws Exception
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.newCable = new CableInfoBean();
      this.newCable.setCableid(this.cableRouteInfoBean.getOptCableId());
      this.newCable = this.routeService.getCable(this.newCable);

      Date lastUpdateDate = new Date();
      this.cableRouteInfoBean.setLastUpdateDate(lastUpdateDate);
      int n = this.routeService.updatecableRoute(this.cableRouteInfoBean).intValue();
      if (n == 1)
        this.success = true;
      else {
        this.success = false;
      }
      return "modifyCR";
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

  public String dodeletcableroute() throws Exception
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.cableRouteInfoBean = new CableRouteInfoBean();
      Date deletionDate = new Date();
      this.cableRouteInfoBean.setDeletionDate(deletionDate);
      this.cableRouteInfoBean.setId(Integer.valueOf(((String)this.idss.get(0)).trim()));
      int n = this.routeService.deleteCableRoute(this.cableRouteInfoBean);
      if (n == 0)
        this.success = true;
      else {
        this.success = false;
      }
      return "deleteCR";
    } catch (DataAccessException e) {
      log.error("EqutAction.deleteequt", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String searchJSON() throws Exception
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if (this.tubeInfoBean == null) {
        this.tubeInfoBean = new TubeInfoBean();
      }
      this.tubeInfoBean.setLimit(this.limit);
      this.tubeInfoBean.setStart(this.start);
      this.tubeInfoBean = this.routeService.getTubeList(this.tubeInfoBean);
      return "tubeGrid";
    } catch (DataAccessException e) {
      log.error("RouteAction.searchJSON", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String searchPlJSON() throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if (this.polelineSegmentInfoBean == null) {
        this.polelineSegmentInfoBean = new PolelineSegmentInfoBean();
      }
      this.polelineSegmentInfoBean.setLimit(this.limit);
      this.polelineSegmentInfoBean.setStart(this.start);
      this.polelineSegmentInfoBean = this.routeService.getPolelineSegmentList(this.polelineSegmentInfoBean);
      return "plsGrid";
    } catch (DataAccessException e) {
      log.error("EqutAction.search", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String exportRoute()
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      List clist = new ArrayList();
      if ((this.ids != null) || (!(this.ids.equals("")))) {
        String[] temp = this.ids.split(",");
        String tempeid = "";
        String modelPath = getServletContext().getRealPath("/") + "tmpl" + File.separator + "excel_model_route.xls";
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(modelPath));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        int rotTemp = 2;

        for (int i = 0; i < temp.length; ++i) {
          tempeid = temp[i];
          this.newRoute = new RouteInfoBean();
          this.newRoute.setRouteid(Integer.valueOf(Integer.parseInt(tempeid.trim())));
          this.newRoute = this.routeService.getRoute(this.newRoute);
          clist = this.routeService.getCableList(this.newRoute);
          if ((clist != null) && (!(clist.isEmpty()))) {
            for (int j = 0; j < clist.size(); ++j) {
              this.newCable = ((CableInfoBean)clist.get(j));
              HSSFRow row = sheet.createRow((short)rotTemp);
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
              HSSFCell cell12 = row.createCell(11);
              HSSFCell cell13 = row.createCell(12);
              HSSFCell cell14 = row.createCell(13);

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
              cell12.setCellType(1);
              cell13.setCellType(1);
              cell14.setCellType(1);

              cell1.setCellValue((this.newRoute.getRoutename() == null) ? "" : this.newRoute.getRoutename());
              cell2.setCellValue(CommonUtil.getRlevelStr(this.newRoute.getRoutelevel()));
              String[] level = { "一级干线", "一级干线", "本地中继", "本地核心", "本地汇聚", "本地接入" };
              CellRangeAddressList regions = new CellRangeAddressList(rotTemp, rotTemp, 1, 1);

              DVConstraint constraint = DVConstraint.createExplicitListConstraint(level);

              HSSFDataValidation data_validation = new HSSFDataValidation(regions, constraint);

              sheet.addValidationData(data_validation);
              cell3.setCellValue(CommonUtil.getRstateStr(this.newRoute.getState()));
              String[] states = { "割接封锁", "正常", "不可用" };
              regions = new CellRangeAddressList(rotTemp, rotTemp, 2, 2);

              constraint = DVConstraint.createExplicitListConstraint(states);

              data_validation = new HSSFDataValidation(regions, constraint);

              sheet.addValidationData(data_validation);

              cell4.setCellValue((this.newRoute.getStartsite() == null) ? "" : this.newRoute.getStartsite());
              cell5.setCellValue((this.newRoute.getEndsite() == null) ? "" : this.newRoute.getEndsite());
              cell6.setCellValue((this.newRoute.getSign() == null) ? "" : this.newRoute.getSign());

              cell7.setCellValue((this.newCable.getCablename() == null) ? "" : this.newCable.getCablename());
              cell8.setCellValue((this.newCable.getType() == null) ? "" : this.newCable.getType());
              cell9.setCellValue(CommonUtil.getCstateStr(this.newCable.getState()));
              String[] cstates = { "割接封锁", "正常", "不可用" };
              regions = new CellRangeAddressList(rotTemp, rotTemp, 8, 8);

              constraint = 
                DVConstraint.createExplicitListConstraint(cstates);

              data_validation = new HSSFDataValidation(regions, 
                constraint);

              sheet.addValidationData(data_validation);
              cell10.setCellValue((this.newCable.getMode() == null) ? "" : this.newCable.getMode());
              cell11.setCellValue((this.newCable.getLength() == null) ? "" : this.newCable.getLength());
              cell12.setCellValue((this.newCable.getPaveWay() == null) ? "" : this.newCable.getPaveWay());
              cell13.setCellValue(CommonUtil.getSharetypeStr(this.newCable.getSharetype()));
              String[] sharetypes = { "共建", "共享(我方共享他方)", "共享(他方共享我方)", "自建自用", "自建预留" };
              regions = new CellRangeAddressList(rotTemp, rotTemp, 12, 12);

              constraint = 
                DVConstraint.createExplicitListConstraint(sharetypes);

              data_validation = new HSSFDataValidation(regions, 
                constraint);

              sheet.addValidationData(data_validation);
              cell14.setCellValue((this.newCable.getShareoperator() == null) ? "" : this.newCable.getShareoperator());
              ++rotTemp;
            }
          }
          else {
            HSSFRow row = sheet.createRow((short)rotTemp);
            HSSFCell cell1 = row.createCell(0);
            HSSFCell cell2 = row.createCell(1);
            HSSFCell cell3 = row.createCell(2);
            HSSFCell cell4 = row.createCell(3);
            HSSFCell cell5 = row.createCell(4);
            HSSFCell cell6 = row.createCell(5);

            cell1.setCellType(1);
            cell2.setCellType(1);
            cell3.setCellType(1);
            cell4.setCellType(1);
            cell5.setCellType(1);
            cell6.setCellType(1);

            cell1.setCellValue((this.newRoute.getRoutename() == null) ? "" : this.newRoute.getRoutename());
            cell2.setCellValue(CommonUtil.getRlevelStr(this.newRoute.getRoutelevel()));
            String[] level = { "一级干线", "一级干线", "本地中继", "本地核心", "本地汇聚", "本地接入" };
            CellRangeAddressList regions = new CellRangeAddressList(rotTemp, rotTemp, 1, 1);

            DVConstraint constraint = DVConstraint.createExplicitListConstraint(level);

            HSSFDataValidation data_validation = new HSSFDataValidation(regions, constraint);

            sheet.addValidationData(data_validation);
            cell3.setCellValue(CommonUtil.getRstateStr(this.newRoute.getState()));
            String[] states = { "割接封锁", "正常", "不可用" };
            regions = new CellRangeAddressList(rotTemp, rotTemp, 2, 2);

            constraint = DVConstraint.createExplicitListConstraint(states);

            data_validation = new HSSFDataValidation(regions, constraint);

            sheet.addValidationData(data_validation);

            cell4.setCellValue((this.newRoute.getStartsite() == null) ? "" : this.newRoute.getStartsite());
            cell5.setCellValue((this.newRoute.getEndsite() == null) ? "" : this.newRoute.getEndsite());
            cell6.setCellValue((this.newRoute.getSign() == null) ? "" : this.newRoute.getSign());
            ++rotTemp;
          }
        }
        String name = "";
        if (this.ids.split(",").length == 1) {
          name = this.newRoute.getRoutename();
        }
        String filename = getServletContext().getRealPath("/") + "downloadtemp" + File.separator + name + "光缆信息.xls";
        FileOutputStream fo = new FileOutputStream(filename);
        wb.write(fo);
        fo.close();
        this.filepath = "downloadtemp/" + name + "光缆信息.xls";
      }
      return "exportsuccess";
    } catch (Exception e) {
      log.error("RouteAction.exportRoute 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String betchUpdateRoute() throws Exception
  {
    try {
      int rows = 0;
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      List routelist = new ArrayList();
      List cables = new ArrayList();

      POIFSFileSystem fs = null;
      HSSFWorkbook wb = null;
      if ((this.excle != null) && (this.excle.length != 0))
      {
        for (int j = 0; j < this.excle.length; ++j) {
          File file = this.excle[j];
          if (file != null) {
            fs = new POIFSFileSystem(new FileInputStream(file));
            wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            rows = sheet.getLastRowNum();
            DecimalFormat df = new DecimalFormat("#");
            Date eventTime = new Date();
            for (int i = 2; i <= rows; ++i) {
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

              String routename = "";
              if (cell != null) {
                if (cell.getCellType() == 0)
                  routename = df.format(cell.getNumericCellValue());
                else {
                  routename = cell.getStringCellValue();
                }
              }

              String routelevel = "";
              if (cell1 != null) {
                if (cell1.getCellType() == 0) {
                  routelevel = df.format(cell1.getNumericCellValue());
                  routelevel = CommonUtil.getRlevelInt(routelevel);
                } else {
                  routelevel = cell1.getStringCellValue();
                  routelevel = CommonUtil.getRlevelInt(routelevel);
                }
              }

              Integer rState = null;
              String rStateStr = "";
              if (cell2 != null) {
                if (cell2.getCellType() == 0) {
                  rStateStr = df.format(cell2.getNumericCellValue());
                  rState = Integer.valueOf(CommonUtil.getRstateInt(rStateStr));
                } else {
                  rStateStr = cell2.getStringCellValue();
                  rState = Integer.valueOf(CommonUtil.getRstateInt(rStateStr));
                }
              }

              String startsite = "";
              if (cell3 != null) {
                if (cell3.getCellType() == 0)
                  startsite = df.format(cell3.getNumericCellValue());
                else {
                  startsite = cell3.getStringCellValue();
                }
              }

              String endsite = "";
              if (cell4 != null) {
                if (cell4.getCellType() == 0)
                  endsite = df.format(cell4.getNumericCellValue());
                else {
                  endsite = cell4.getStringCellValue();
                }
              }

              String sign = "";
              if (cell5 != null) {
                if (cell5.getCellType() == 0)
                  sign = df.format(cell5.getNumericCellValue());
                else {
                  sign = cell5.getStringCellValue();
                }
              }

              String cablename = "";
              if (cell6 != null) {
                if (cell6.getCellType() == 0)
                  cablename = df.format(cell6.getNumericCellValue());
                else {
                  cablename = cell6.getStringCellValue();
                }
              }

              String type = "";
              if (cell7 != null) {
                if (cell7.getCellType() == 0)
                  type = df.format(cell7.getNumericCellValue());
                else {
                  type = cell7.getStringCellValue();
                }
              }

              Integer cState = null;
              String cStateStr = "";
              if (cell8 != null) {
                if (cell8.getCellType() == 0) {
                  cStateStr = df.format(cell8.getNumericCellValue());
                  cState = Integer.valueOf(CommonUtil.getRstateInt(cStateStr));
                } else {
                  cStateStr = cell8.getStringCellValue();
                  cState = Integer.valueOf(CommonUtil.getRstateInt(cStateStr));
                }
              }

              String mode = "";
              if (cell9 != null) {
                if (cell9.getCellType() == 0)
                  mode = df.format(cell9.getNumericCellValue());
                else {
                  mode = cell9.getStringCellValue();
                }
              }

              String length = "";
              if (cell10 != null) {
                if (cell10.getCellType() == 0)
                  length = df.format(cell10.getNumericCellValue());
                else {
                  length = cell10.getStringCellValue();
                }
              }

              String paveWay = "";
              if (cell11 != null) {
                if (cell11.getCellType() == 0)
                  paveWay = df.format(cell11.getNumericCellValue());
                else {
                  paveWay = cell11.getStringCellValue();
                }
              }

              String sharetypeStr = "";
              Integer sharetype = null;
              if (cell12 != null) {
                if (cell12.getCellType() == 0) {
                  sharetypeStr = df.format(cell12.getNumericCellValue());
                  sharetype = Integer.valueOf(CommonUtil.getSharetypeInt(sharetypeStr));
                } else {
                  sharetypeStr = cell12.getStringCellValue();
                  sharetype = Integer.valueOf(CommonUtil.getSharetypeInt(sharetypeStr));
                }
              }

              String shareoperator = "";
              if (cell13 != null) {
                if (cell13.getCellType() == 0)
                  shareoperator = df.format(cell13.getNumericCellValue());
                else {
                  shareoperator = cell13.getStringCellValue();
                }
              }
              RouteInfoBean route = new RouteInfoBean();
              CableInfoBean cable = new CableInfoBean();
              route.setRoutename(routename);
              route.setRoutelevel(routelevel);
              route.setState(rState);
              route.setStartsite(startsite);
              route.setEndsite(endsite);
              route.setSign(sign);
              cable.setCablename(cablename);
              cable.setType(type);
              cable.setState(cState);
              cable.setMode(mode);
              cable.setLength(length);
              cable.setPaveWay(paveWay);
              cable.setSharetype(sharetype);
              cable.setShareoperator(shareoperator);
              if (routelist.isEmpty()) {
                if (route.getCables() == null) {
                  cables = new ArrayList();
                  cables.add(cable);
                  route.setCables(cables);
                } else {
                  route.getCables().add(cable);
                }

                routelist.add(route);
              } else {
                RouteInfoBean lastRoute = (RouteInfoBean)routelist.get(routelist.size() - 1);
                if (lastRoute.getRoutename().equals(route.getRoutename())) {
                  lastRoute.getCables().add(cable);
                } else {
                  route.getCables().add(cable);
                  routelist.add(route);
                }
              }
            }
          }
        }
      }
      this.routeService.saveRouteDoc(routelist);
      this.success = true;
      return "butchRoute";
    } catch (Exception e) {
      log.error("RouteAction.betchUpdateRoute 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String cablerepeatlist() throws Exception
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.tubeInfoBean = new TubeInfoBean();
      this.tubeInfoBean = this.routeService.getRepeatTubeList(this.tubeInfoBean);
      return "cablerepeatlist";
    } catch (Exception e) {
      log.error("RouteAction.cablerepeatlist 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String showcablelist() throws Exception
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.tubeInfoBean = this.routeService.getTubeById(this.tubeInfoBean);
      String bearCableSegmentId = this.tubeInfoBean.getBearCableSegmentId();
      String redunBearCableSegmentId = this.tubeInfoBean.getRedunBearCableSegmentId();
      String[] ids = bearCableSegmentId.split(",");
      String[] idrs = redunBearCableSegmentId.split(",");
      this.cableList = new ArrayList();
      CableInfoBean cableInfo;
      for (int i = 0; i < ids.length; ++i) {
        cableInfo = new CableInfoBean();
        cableInfo.setCableid(Integer.valueOf(Integer.parseInt(ids[i])));
        cableInfo = this.routeService.getCable(cableInfo);
        this.cableList.add(cableInfo);
      }
      this.cableList1 = new ArrayList();
      for (int i = 0; i < idrs.length; ++i) {
        cableInfo = new CableInfoBean();
        cableInfo.setCableid(Integer.valueOf(Integer.parseInt(idrs[i])));
        cableInfo = this.routeService.getCable(cableInfo);
        this.cableList1.add(cableInfo);
      }
      this.newCable = new CableInfoBean();
      this.newCable.setCables(this.cableList);
      this.newCable.setCables1(this.cableList1);
      return "cablelist1";
    } catch (Exception e) {
      log.error("RouteAction.showcablelist 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String searchTubeJSON() throws Exception
  {
    try {
      boolean flag1 = true;
      boolean flag2 = true;
      this.newCable = new CableInfoBean();
      this.newCable.setCableid(Integer.valueOf(this.cableid1));
      this.newCable = this.routeService.getTubeToCable(this.newCable);
      this.routeid1 = this.newCable.getRouteid();
      if ((this.routeid1 == null) || (this.routeid1.equals("")))
        flag1 = false;
      else {
        flag1 = true;
      }
      this.updateCable = new CableInfoBean();
      this.updateCable.setCableid(Integer.valueOf(this.cableid2));
      this.updateCable = this.routeService.getTubeToCable(this.updateCable);
      this.routeid2 = this.updateCable.getRouteid();
      if ((this.routeid2 == null) || (this.routeid2.equals("")))
        flag2 = false;
      else {
        flag2 = true;
      }
      if ((flag2) && (flag1) && (this.routeid2.equals(this.routeid1))) {
        flag1 = false;
        flag2 = false;
      }
      if ((flag1) || (flag2))
        this.success = true;
      else {
        this.success = false;
      }
      return "vetifyTubeRoute";
    } catch (Exception e) {
      log.error("PointAction.searchTubeJSON", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String showroute() throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      this.tubeInfoBean = new TubeInfoBean();
      this.tubeInfoBean.setTubeId(Integer.valueOf(this.tubeId));
      this.tubeInfoBean = this.routeService.getTubeById(this.tubeInfoBean);

      this.newCable = new CableInfoBean();
      this.newRoute = new RouteInfoBean();
      if (this.cableid1 != null) {
        this.newCable.setCableid(Integer.valueOf(this.cableid1));
        this.newCable = this.routeService.getTubeToCable(this.newCable);
        if ((this.newCable.getRouteid() != null) && (!(this.newCable.getRouteid().equals("")))) {
          this.newRoute.setRouteid(Integer.valueOf(this.newCable.getRouteid()));
          this.newRoute = this.routeService.getRoute(this.newRoute);
        }
      }

      this.updateCable = new CableInfoBean();
      this.updateRoute = new RouteInfoBean();
      if (this.cableid2 != null) {
        this.updateCable.setCableid(Integer.valueOf(this.cableid2));
        this.updateCable = this.routeService.getTubeToCable(this.updateCable);
        if ((this.updateCable.getRouteid() != null) && (!(this.updateCable.getRouteid().equals("")))) {
          this.updateRoute.setRouteid(Integer.valueOf(this.updateCable.getRouteid()));
          this.updateRoute = this.routeService.getRoute(this.updateRoute);
        }
      }
      return "routecombine";
    } catch (Exception e) {
      log.error("RouteAction.showroute 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String showcable() throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      this.tubeInfoBean = new TubeInfoBean();
      this.tubeInfoBean.setTubeId(Integer.valueOf(this.tubeId));
      this.tubeInfoBean = this.routeService.getTubeById(this.tubeInfoBean);
      this.cable1 = new CableInfoBean();
      if (this.cableid1 != null) {
        this.cable1.setCableid(Integer.valueOf(this.cableid1));
        this.cable1 = this.routeService.getTubeToCable(this.cable1);
      }
      this.cable2 = new CableInfoBean();
      if (this.cableid2 != null) {
        this.cable2.setCableid(Integer.valueOf(this.cableid2));
        this.cable2 = this.routeService.getTubeToCable(this.cable2);
      }
      return "cablecombine";
    } catch (Exception e) {
      log.error("RouteAction.showcable 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String insertnewroute() throws Exception
  {
    Date date = new Date();
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    this.tubeInfoBean = new TubeInfoBean();
    this.tubeInfoBean.setTubeId(Integer.valueOf(this.tubeId));
    this.tubeInfoBean = this.routeService.getTubeById(this.tubeInfoBean);
    CableInfoBean cable1 = new CableInfoBean();
    cable1.setCableid(Integer.valueOf(this.cableid1));
    cable1 = this.routeService.getTubeToCable(cable1);
    CableInfoBean cable2 = new CableInfoBean();
    cable2.setCableid(Integer.valueOf(this.cableid2));
    cable2 = this.routeService.getTubeToCable(cable2);
    try
    {
      RouteInfoBean routeInfoBean;
      if ((this.routeid1 != null) && (!(this.routeid1.equals("")))) {
        routeInfoBean = new RouteInfoBean();
        routeInfoBean.setDeletionDate(date);
        routeInfoBean.setRouteid(Integer.valueOf(this.routeid1));
        this.routeService.updateRoute(routeInfoBean);
      }
      if ((this.routeid2 != null) && (!(this.routeid2.equals(""))) && (!(this.routeid2.equals(this.routeid1)))) {
        routeInfoBean = new RouteInfoBean();
        routeInfoBean.setDeletionDate(date);
        routeInfoBean.setRouteid(Integer.valueOf(this.routeid2));
        this.routeService.updateRoute(routeInfoBean);
      }
      Integer id = this.routeService.insertRoute(this.newRoute);
      RouteInfoBean routeInfo = new RouteInfoBean();
      routeInfo.setRouteid(id);
      routeInfo = this.routeService.getRoute(routeInfo);
      CableInfoBean cableInfoBean;
      List cableList;
      Iterator localIterator;
      CableInfoBean cableInfo;
      if ((this.routeid1 != null) && (!(this.routeid1.equals("")))) {
        cableInfoBean = new CableInfoBean();
        cableInfoBean.setRouteid(this.routeid1);
        cableList = this.routeService.getcablelist(cableInfoBean);
        if (cableList.size() != 0)
          for (localIterator = cableList.iterator(); localIterator.hasNext(); ) { cableInfo = (CableInfoBean)localIterator.next();
            cableInfo.setRouteid(routeInfo.getRouteid().toString());
            cableInfo.setRoutename(routeInfo.getRoutename());
            this.routeService.updateCableInfo(cableInfo);
          }
      }
      else {
        cable1.setRouteid(routeInfo.getRouteid().toString());
        cable1.setRoutename(routeInfo.getRoutename());
        this.routeService.updateCableInfo(cable1);
      }

      if ((this.routeid2 != null) && (!(this.routeid2.equals("")))) {
        cableInfoBean = new CableInfoBean();
        cableInfoBean.setRouteid(this.routeid2);
        cableList = this.routeService.getcablelist(cableInfoBean);
        if (cableList.size() != 0)
          for (localIterator = cableList.iterator(); localIterator.hasNext(); ) { cableInfo = (CableInfoBean)localIterator.next();
            cableInfo.setRouteid(routeInfo.getRouteid().toString());
            cableInfo.setRoutename(routeInfo.getRoutename());
            this.routeService.updateCableInfo(cableInfo);
          }
      }
      else {
        cable2.setRouteid(routeInfo.getRouteid().toString());
        cable2.setRoutename(routeInfo.getRoutename());
        this.routeService.updateCableInfo(cable2);
      }
      return "qcroute";
    } catch (Exception e) {
      log.error("RouteAction.insertnewroute 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String qcCable() throws Exception
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      Date date = new Date();
      this.tubeInfoBean = this.routeService.getTubeById(this.tubeInfoBean);
      this.cable1 = this.routeService.getCable(this.cable1);
      this.cable2 = this.routeService.getCable(this.cable2);
      this.cable1.setDeletionDate(date);
      this.cable2.setDeletionDate(date);
      this.routeService.deleteCable(this.cable1);
      this.routeService.deleteCable(this.cable2);

      int newCableid = this.routeService.insertCable(this.newCable);
      CableInfoBean cableInfoBean = new CableInfoBean();
      cableInfoBean.setCableid(Integer.valueOf(newCableid));
      cableInfoBean = this.routeService.getCable(cableInfoBean);

      this.cableRouteInfoBean = new CableRouteInfoBean();
      this.cableRouteInfoBean.setOptCableId(this.cable1.getCableid());
      this.cableRouteList = this.routeService.getCR(this.cableRouteInfoBean);
      for (int i = 0; i < this.cableRouteList.size(); ++i) {
        CableRouteInfoBean cr = (CableRouteInfoBean)this.cableRouteList.get(i);
        cr.setOptCableId(Integer.valueOf(newCableid));
        this.routeService.updateCableRoute(cr);
      }

      FaceInfoBean faceIn = new FaceInfoBean();
      faceIn.setFaceId1(Integer.valueOf(this.tubeInfoBean.getFaceId()));
      faceIn = this.routeService.getFaceRelation(faceIn);
      TubeInfoBean tubes = new TubeInfoBean();
      if (faceIn != null) {
        tubes = new TubeInfoBean();
        tubes.setFaceId(faceIn.getFaceId2().toString());
        tubes = this.routeService.getTubeBean(tubes);
      } else {
        faceIn = new FaceInfoBean();
        faceIn.setFaceId2(Integer.valueOf(this.tubeInfoBean.getFaceId()));
        faceIn = this.routeService.getFaceRelation(faceIn);
        if (faceIn != null) {
          tubes = new TubeInfoBean();
          tubes.setFaceId(faceIn.getFaceId1().toString());
          tubes = this.routeService.getTubeBean(tubes);
        }
      }
      this.cableRouteInfoBean = new CableRouteInfoBean();
      this.cableRouteInfoBean.setOptCableId(this.cable2.getCableid());
      this.cableRouteList = this.routeService.getCR(this.cableRouteInfoBean);
      CableRouteInfoBean cr;
      for (int i = 0; i < this.cableRouteList.size(); ++i) {
        cr = (CableRouteInfoBean)this.cableRouteList.get(i);
        if ((tubes != null) && 
          (cr.getTubeId() != null) && (cr.getTubeId().equals(tubes.getTubeId()))) {
          cr.setDeletionDate(date);
          this.routeService.deletecableroute(cr);
        }
        else if ((cr.getTubeId() != null) && (cr.getTubeId().equals(this.tubeInfoBean.getTubeId()))) {
          cr.setDeletionDate(date);
          this.routeService.deletecableroute(cr);
        } else {
          cr.setOptCableId(Integer.valueOf(newCableid));
          this.routeService.updateCableRoute(cr);
        }

      }

      this.cableRouteInfoBean = new CableRouteInfoBean();
      this.cableRouteInfoBean.setOptCableId(Integer.valueOf(newCableid));
      this.cableRouteList = this.routeService.getCR(this.cableRouteInfoBean);
      if (this.cableRouteList.size() != 0) {
        for (CableRouteInfoBean cableRouteInfo : this.cableRouteList) {
          if ((cableRouteInfo.getTubeId() != null) && (!(cableRouteInfo.getTubeId().equals("")))) {
            int tubeid = cableRouteInfo.getTubeId().intValue();
            TubeInfoBean tubeInfo = new TubeInfoBean();
            tubeInfo.setTubeId(Integer.valueOf(tubeid));
            tubeInfo = this.routeService.getTubeById(tubeInfo);

            String bearCableSegmentId = "," + tubeInfo.getBearCableSegmentId();
            bearCableSegmentId = bearCableSegmentId.replace("," + this.cable1.getCableid().toString() + ",", "," + newCableid + ",");
            bearCableSegmentId = bearCableSegmentId.substring(1, bearCableSegmentId.length());
            String bearCableSegmentName = "," + tubeInfo.getBearCableSegment();
            bearCableSegmentName = bearCableSegmentName.replace("," + this.cable1.getCablename() + ",", "," + cableInfoBean.getCablename() + ",");
            bearCableSegmentName = bearCableSegmentName.substring(1, bearCableSegmentName.length());
            tubeInfo.setBearCableSegmentId(bearCableSegmentId);
            tubeInfo.setBearCableSegment(bearCableSegmentName);
            this.routeService.updateTube(tubeInfo);

            String redunbearcablesegmentid = tubeInfo.getRedunBearCableSegmentId();
            String redunbearcablesegmentname = tubeInfo.getRedunBearCableSegment();
            String[] idrs = redunbearcablesegmentid.split(",");
            for (int i = 0; i < idrs.length; ++i) {
              if (idrs[i].equals(this.cable1.getCableid().toString())) {
                redunbearcablesegmentid = "," + redunbearcablesegmentid;
                redunbearcablesegmentname = "," + redunbearcablesegmentname;
                redunbearcablesegmentid = redunbearcablesegmentid.replace("," + this.cable1.getCableid().toString() + ",", "," + newCableid + ",");
                redunbearcablesegmentname = redunbearcablesegmentname.replace("," + this.cable1.getCablename() + ",", "," + cableInfoBean.getCablename() + ",");
                redunbearcablesegmentid = redunbearcablesegmentid.substring(1, redunbearcablesegmentid.length());
                redunbearcablesegmentname = redunbearcablesegmentname.substring(1, redunbearcablesegmentname.length());
                tubeInfo.setRedunBearCableSegmentId(redunbearcablesegmentid);
                tubeInfo.setRedunBearCableSegment(redunbearcablesegmentname);
              }
              this.routeService.updateTube(tubeInfo);
            }
            String bearcablesegmentId = tubeInfo.getBearCableSegmentId();
            String bearcablesegmentname = tubeInfo.getBearCableSegment();
            if ((tubeInfo.getBearCableSegmentId() != null) && (tubeInfo.getBearCableSegmentId() != "") && 
              (tubeInfo.getBearCableSegment() != null) && (tubeInfo.getBearCableSegment() != "")) {
              bearcablesegmentId = "," + tubeInfo.getBearCableSegmentId();
              bearcablesegmentId = bearcablesegmentId.replace("," + this.cable2.getCableid().toString() + ",", "," + newCableid + ",");
              bearcablesegmentname = "," + tubeInfo.getBearCableSegment();
              bearcablesegmentname = bearcablesegmentname.replace("," + this.cable2.getCablename() + ",", "," + cableInfoBean.getCablename() + ",");
              bearcablesegmentId = bearcablesegmentId.substring(1, bearcablesegmentId.length());
              bearcablesegmentname = bearcablesegmentname.substring(1, bearcablesegmentname.length());
              tubeInfo.setBearCableSegmentId(bearcablesegmentId);
              tubeInfo.setBearCableSegment(bearcablesegmentname);
              this.routeService.updateTube(tubeInfo);
            }

            if ((tubeInfo.getRedunBearCableSegmentId() != null) && (tubeInfo.getRedunBearCableSegmentId() != "") && 
              (tubeInfo.getRedunBearCableSegment() != null) && (tubeInfo.getRedunBearCableSegment() != "")) {
              String redunBearCableSegmentId = tubeInfo.getRedunBearCableSegmentId();
              String redunBearCableSegmentName = tubeInfo.getRedunBearCableSegment();
              String[] ids = redunBearCableSegmentId.split(",");
              for (int i = 0; i < ids.length; ++i)
              {
                if ((idrs[i].equals(this.cable2.getCableid().toString())) && (tubeInfo.getTubeId().equals(this.tubeInfoBean.getTubeId()))) {
                  redunBearCableSegmentId = "," + redunBearCableSegmentId;
                  redunBearCableSegmentName = "," + redunBearCableSegmentName;
                  redunBearCableSegmentId = redunBearCableSegmentId.replace("," + this.cable2.getCableid().toString() + ",", ",");
                  redunBearCableSegmentName = redunBearCableSegmentName.replace("," + this.cable2.getCablename() + ",", ",");
                  if (redunBearCableSegmentId.length() > 1)
                    redunBearCableSegmentId = redunBearCableSegmentId.substring(1, redunBearCableSegmentId.length());
                  else {
                    redunBearCableSegmentId = "";
                  }
                  if (redunBearCableSegmentName.length() > 1)
                    redunBearCableSegmentName = redunBearCableSegmentName.substring(1, redunBearCableSegmentName.length());
                  else {
                    redunBearCableSegmentName = "";
                  }
                  tubeInfo.setRedunBearCableSegmentId(redunBearCableSegmentId);
                  tubeInfo.setRedunBearCableSegment(redunBearCableSegmentName);
                  this.routeService.updateTube(tubeInfo);

                  FaceInfoBean faceInfoBean = new FaceInfoBean();
                  faceInfoBean.setFaceId1(Integer.valueOf(tubeInfo.getFaceId()));
                  faceInfoBean = this.routeService.getFaceRelation(faceInfoBean);
                  TubeInfoBean tube = new TubeInfoBean();
                  if (faceInfoBean != null) {
                    tube = new TubeInfoBean();
                    tube.setFaceId(faceInfoBean.getFaceId2().toString());
                    tube = this.routeService.getTubeBean(tube);
                  } else {
                    faceInfoBean = new FaceInfoBean();
                    faceInfoBean.setFaceId2(Integer.valueOf(tubeInfo.getFaceId()));
                    faceInfoBean = this.routeService.getFaceRelation(faceInfoBean);
                    if (faceInfoBean != null) {
                      tube = new TubeInfoBean();
                      tube.setFaceId(faceInfoBean.getFaceId1().toString());
                      tube = this.routeService.getTubeBean(tube);
                    }
                  }
                  if (tube != null) {
                    redunBearCableSegmentId = "," + tube.getRedunBearCableSegmentId();
                    redunBearCableSegmentName = "," + tube.getRedunBearCableSegment();
                    redunBearCableSegmentId = redunBearCableSegmentId.replace("," + this.cable2.getCableid().toString() + ",", ",");
                    redunBearCableSegmentName = redunBearCableSegmentName.replace("," + this.cable2.getCablename() + ",", ",");
                    if (redunBearCableSegmentId.length() > 1)
                      redunBearCableSegmentId = redunBearCableSegmentId.substring(1, redunBearCableSegmentId.length());
                    else {
                      redunBearCableSegmentId = "";
                    }
                    if (redunBearCableSegmentName.length() > 1)
                      redunBearCableSegmentName = redunBearCableSegmentName.substring(1, redunBearCableSegmentName.length());
                    else {
                      redunBearCableSegmentName = "";
                    }
                    tube.setRedunBearCableSegmentId(redunBearCableSegmentId);
                    tube.setRedunBearCableSegment(redunBearCableSegmentName);
                    this.routeService.updateTube(tube);
                  }

                }
                else if ((ids[i].equals(this.cable2.getCableid().toString())) && (!(tubeInfo.getTubeId().equals(this.tubeInfoBean.getTubeId())))) {
                  redunBearCableSegmentId = "," + redunBearCableSegmentId;
                  redunBearCableSegmentName = "," + redunBearCableSegmentName;
                  redunBearCableSegmentId = redunBearCableSegmentId.replace("," + this.cable2.getCableid().toString() + ",", "," + newCableid + ",");
                  redunBearCableSegmentName = redunBearCableSegmentName.replace("," + this.cable2.getCablename() + ",", "," + cableInfoBean.getCablename() + ",");
                  redunBearCableSegmentId = redunBearCableSegmentId.substring(1, redunBearCableSegmentId.length());
                  redunBearCableSegmentName = redunBearCableSegmentName.substring(1, redunBearCableSegmentName.length());
                  tubeInfo.setRedunBearCableSegmentId(redunBearCableSegmentId);
                  tubeInfo.setRedunBearCableSegment(redunBearCableSegmentName);
                  this.routeService.updateTube(tubeInfo);
                }
              }
            }
            if ((tubeInfo.getBearCableSegmentId() == null) || (tubeInfo.getBearCableSegmentId() == "") || 
              (tubeInfo.getBearCableSegment() == null) || (tubeInfo.getBearCableSegment() == "") || 
              (tubeInfo.getRedunBearCableSegment() == null) || (tubeInfo.getRedunBearCableSegment() == "") || 
              (tubeInfo.getRedunBearCableSegmentId() == null) || (tubeInfo.getRedunBearCableSegmentId() == "")) continue;
            bearcablesegmentId = tubeInfo.getBearCableSegmentId();
            bearcablesegmentname = tubeInfo.getBearCableSegment();
            String redunBearCableSegmentId = tubeInfo.getRedunBearCableSegmentId();
            String redunBearCableSegmentName = tubeInfo.getRedunBearCableSegment();
            String[] ids = redunBearCableSegmentId.split(",");
            idrs = bearcablesegmentId.split(",");
            for (int i = 0; i < idrs.length; ++i) {
              for (int j = 0; j < ids.length; ++j) {
                if (idrs[i].equals(ids[j])) {
                  redunBearCableSegmentId = "," + redunBearCableSegmentId;
                  redunBearCableSegmentName = "," + redunBearCableSegmentName;
                  redunBearCableSegmentId = redunBearCableSegmentId.replace("," + ids[j] + ",", ",");
                  redunBearCableSegmentName = redunBearCableSegmentName.replace("," + cableInfoBean.getCablename() + ",", ",");
                  if (redunBearCableSegmentId.length() > 1)
                    redunBearCableSegmentId = redunBearCableSegmentId.substring(1, redunBearCableSegmentId.length());
                  else {
                    redunBearCableSegmentId = "";
                  }
                  if (redunBearCableSegmentName.length() > 1)
                    redunBearCableSegmentName = redunBearCableSegmentName.substring(1, redunBearCableSegmentName.length());
                  else {
                    redunBearCableSegmentName = "";
                  }
                  tubeInfo.setRedunBearCableSegmentId(redunBearCableSegmentId);
                  tubeInfo.setRedunBearCableSegment(redunBearCableSegmentName);
                  this.routeService.updateTube(tubeInfo);
                }
              }
            }
          }
        }
      }

      this.success = true;
      return "qccable";
    } catch (Exception e) {
      log.error("RouteAction.qcCable 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String getfiberlist() throws Exception
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if (this.fiberGrid == null) {
        this.fiberGrid = new FiberBoxInfoBean();
      }
      this.fiberGrid.setStart(this.start);
      this.fiberGrid.setLimit(this.limit);
      this.fiberGrid = this.routeService.getFiberlist(this.fiberGrid);
      return "fiberGrid";
    } catch (Exception e) {
      log.error("routeAction.getfiberlist 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String getRouteid1()
  {
    return this.routeid1;
  }

  public void setRouteid1(String routeid1) {
    this.routeid1 = routeid1;
  }

  public String getRouteid2() {
    return this.routeid2;
  }

  public void setRouteid2(String routeid2) {
    this.routeid2 = routeid2;
  }

  public String getCableid1() {
    return this.cableid1;
  }

  public void setCableid1(String cableid1) {
    this.cableid1 = cableid1;
  }

  public String getCableid2() {
    return this.cableid2;
  }

  public void setCableid2(String cableid2) {
    this.cableid2 = cableid2;
  }

  public String getTubeId() {
    return this.tubeId;
  }

  public void setTubeId(String tubeId) {
    this.tubeId = tubeId;
  }

  public CableInfoBean getCable1() {
    return this.cable1;
  }

  public void setCable1(CableInfoBean cable1) {
    this.cable1 = cable1;
  }

  public CableInfoBean getCable2() {
    return this.cable2;
  }

  public void setCable2(CableInfoBean cable2) {
    this.cable2 = cable2;
  }

  public List<CableInfoBean> getCableList() {
    return this.cableList;
  }

  public void setCableList(List<CableInfoBean> cableList) {
    this.cableList = cableList;
  }

  public List<CableInfoBean> getCableList1() {
    return this.cableList1;
  }

  public void setCableList1(List<CableInfoBean> cableList1) {
    this.cableList1 = cableList1;
  }

  public File[] getExcle() {
    return this.excle;
  }

  public void setExcle(File[] excle) {
    this.excle = excle;
  }

  public String getFilepath() {
    return this.filepath;
  }

  public void setFilepath(String filepath) {
    this.filepath = filepath;
  }

  public List<String> getIdss() {
    return this.idss;
  }

  public void setIdss(List<String> idss) {
    this.idss = idss;
  }

  public ErrorMessage getErrorMessage() {
    return this.errorMessage; }

  public void setErrorMessage(ErrorMessage errorMessage) {
    this.errorMessage = errorMessage; }

  public RouteService getRouteService() {
    return this.routeService; }

  public void setRouteService(RouteService routeService) {
    this.routeService = routeService;
  }

  public Integer getTotal() {
    return this.total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public Integer getStart() {
    return this.start;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  public Integer getLimit()
  {
    return this.limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public RouteInfoBean getRouteGrid()
  {
    return this.routeGrid;
  }

  public void setRouteGrid(RouteInfoBean routeGrid) {
    this.routeGrid = routeGrid;
  }

  public RouteInfoBean getSearchRoute()
  {
    return this.searchRoute;
  }

  public void setSearchRoute(RouteInfoBean searchRoute) {
    this.searchRoute = searchRoute;
  }

  public String getSort() {
    return this.sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public String getDir() {
    return this.dir;
  }

  public void setDir(String dir)
  {
    this.dir = dir; }

  public CableInfoBean getCableGrid() {
    return this.cableGrid; }

  public void setCableGrid(CableInfoBean cableGrid) {
    this.cableGrid = cableGrid; }

  public CableInfoBean getSearchCable() {
    return this.searchCable; }

  public void setSearchCable(CableInfoBean searchCable) {
    this.searchCable = searchCable; }

  public RouteInfoBean getNewRoute() {
    return this.newRoute; }

  public void setNewRoute(RouteInfoBean newRoute) {
    this.newRoute = newRoute; }

  public boolean isSuccess() {
    return this.success; }

  public void setSuccess(boolean success) {
    this.success = success; }

  public String getVerifyMsg() {
    return this.verifyMsg; }

  public void setVerifyMsg(String verifyMsg) {
    this.verifyMsg = verifyMsg; }

  public RouteInfoBean getMergeRoute() {
    return this.mergeRoute; }

  public void setMergeRoute(RouteInfoBean mergeRoute) {
    this.mergeRoute = mergeRoute; }

  public String getIds() {
    return this.ids; }

  public void setIds(String ids) {
    this.ids = ids;
  }

  public RouteInfoBean getUpdateRoute() {
    return this.updateRoute; }

  public void setUpdateRoute(RouteInfoBean updateRoute) {
    this.updateRoute = updateRoute; }

  public CableInfoBean getNewCable() {
    return this.newCable; }

  public void setNewCable(CableInfoBean newCable) {
    this.newCable = newCable; }

  public RouteInfoBean getDeleteRoute() {
    return this.deleteRoute; }

  public void setDeleteRoute(RouteInfoBean deleteRoute) {
    this.deleteRoute = deleteRoute; }

  public CableInfoBean getMergeCable() {
    return this.mergeCable; }

  public void setMergeCable(CableInfoBean mergeCable) {
    this.mergeCable = mergeCable; }

  public String getConfirmDelete() {
    return this.confirmDelete; }

  public void setConfirmDelete(String confirmDelete) {
    this.confirmDelete = confirmDelete; }

  public CableInfoBean getUpdateCable() {
    return this.updateCable; }

  public void setUpdateCable(CableInfoBean updateCable) {
    this.updateCable = updateCable;
  }

  public OppoFiberInfoBean getOppoFiberGrid() {
    return this.oppoFiberGrid;
  }

  public void setOppoFiberGrid(OppoFiberInfoBean oppoFiberGrid) {
    this.oppoFiberGrid = oppoFiberGrid;
  }

  public OppoFiberInfoBean getSearchOppoFiber() {
    return this.searchOppoFiber;
  }

  public void setSearchOppoFiber(OppoFiberInfoBean searchOppoFiber) {
    this.searchOppoFiber = searchOppoFiber;
  }

  public UserInfoBean getUserInfoBean() {
    return this.userInfoBean;
  }

  public void setUserInfoBean(UserInfoBean userInfoBean) {
    this.userInfoBean = userInfoBean;
  }

  public CableRouteInfoBean getCableRouteInfoBean() {
    return this.cableRouteInfoBean;
  }

  public void setCableRouteInfoBean(CableRouteInfoBean cableRouteInfoBean) {
    this.cableRouteInfoBean = cableRouteInfoBean;
  }

  public List<CableRouteInfoBean> getCableRouteList() {
    return this.cableRouteList;
  }

  public void setCableRouteList(List<CableRouteInfoBean> cableRouteList) {
    this.cableRouteList = cableRouteList;
  }

  public PipeSegmentInfoBean getPipeSegmentInfoBean() {
    return this.pipeSegmentInfoBean;
  }

  public void setPipeSegmentInfoBean(PipeSegmentInfoBean pipeSegmentInfoBean) {
    this.pipeSegmentInfoBean = pipeSegmentInfoBean;
  }

  public PolelineSegmentInfoBean getPolelineSegmentInfoBean() {
    return this.polelineSegmentInfoBean;
  }

  public void setPolelineSegmentInfoBean(PolelineSegmentInfoBean polelineSegmentInfoBean)
  {
    this.polelineSegmentInfoBean = polelineSegmentInfoBean;
  }

  public TubeInfoBean getTubeInfoBean() {
    return this.tubeInfoBean;
  }

  public void setTubeInfoBean(TubeInfoBean tubeInfoBean) {
    this.tubeInfoBean = tubeInfoBean;
  }

  public CableRouteInfoBean getData() {
    return this.data;
  }

  public void setData(CableRouteInfoBean data) {
    this.data = data;
  }

  public FiberBoxInfoBean getFiberGrid() {
    return this.fiberGrid;
  }

  public void setFiberGrid(FiberBoxInfoBean fiberGrid) {
    this.fiberGrid = fiberGrid;
  }
}
