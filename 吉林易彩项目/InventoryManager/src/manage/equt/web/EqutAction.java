package manage.equt.web;

import base.exceptions.DataAccessException;
import base.util.CommonUtil;
import base.util.ErrorMessage;
import base.web.PaginationAction;
import com.google.gson.Gson;
import interfaces.hwinterface.interfaces.equt.sender.EqutSender;
import interfaces.pdainterface.point.service.PDAPointInfoService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import manage.domain.pojo.DomainBean;
import manage.domain.service.DomainService;
import manage.equt.pojo.ApplyInfoBean;
import manage.equt.pojo.CodeIndexInfoBean;
import manage.equt.pojo.EqutInfoBean;
import manage.equt.pojo.ReportBean;
import manage.equt.service.EqutInfoService;
import manage.point.pojo.PointInfoBean;
import manage.point.service.PointInfoService;
import manage.route.pojo.CableInfoBean;
import manage.route.pojo.RouteInfoBean;
import manage.tree.pojo.Tree;
import manage.user.pojo.UserInfoBean;
import manage.user.service.UserInfoService;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.struts2.ServletActionContext;

public class EqutAction extends PaginationAction
{
  private static final long serialVersionUID = -8663153003164851927L;
  private static final Logger log = Logger.getLogger(EqutAction.class);
  private List<EqutInfoBean> resultList;
  private List<CableInfoBean> cableList;
  private EqutInfoService equtInfoService;
  private PointInfoService pointInfoService;
  private PDAPointInfoService pdaPointInfoService;
  private UserInfoBean userInfoBean;
  private UserInfoBean user;
  private EqutInfoBean equtInfoBean;
  private PointInfoBean point;
  private DomainBean domainBean;
  private DomainService domainService;
  private UserInfoService userInfoService;
  private List<Tree> treeList;
  private boolean success = false;
  private String node;
  private Integer id;
  private String eid;
  private String eaddr;
  private String ecode;
  private String estat;
  private String etype;
  private String ename;
  private String onu_sn;
  private String lon;
  private String lat;
  private String note;
  private String mbtime;
  private String metime;
  private String mp;
  private String latl;
  private String lath;
  private String lonl;
  private String lonh;
  private String isfb;
  private String selectValue;
  private String[] temp;
  public static final String MODULENAME = "equtmanage";
  public static final String MODULENAME1 = "constructionmonitor";
  private int[] selectUserVlaue;
  private String areano;
  private String paction;
  private String eids;
  private String filepath;
  private String userids;
  private String selectuser;
  private String userstr;
  private String areanostr;
  private ErrorMessage errorMessage;
  private String equtAreano;
  private String emodel;
  private String station;
  private CableInfoBean cableInfoBean;
  private String state;
  private File excle;
  private String verifyMsg;
  private String domainName;
  private List<EqutInfoBean> resultList2;
  private Integer start;
  private Integer limit;
  private String modelName;
  private String strnum;
  private ApplyInfoBean applyBean;
  private String json;

  public String getEqutTopoView()
    throws Exception
  {
    UserInfoBean userInfoBean = (UserInfoBean)getSession().getAttribute("userBean");
    try {
      int picHeight = 120;
      int picWidth = 110;
      int distanceWidth = 200;
      int distanceHeight = 30;
      int topLen = 50;
      int left = 100;

      EqutInfoBean equt = this.equtInfoService.getEqutInfoBean(this.equtInfoBean);

      userInfoBean.setLasteid(this.equtInfoBean.getEid());
      this.equtInfoService.saveLastEid(userInfoBean);

      getSession().setAttribute("userBean", userInfoBean);

      List upList = this.equtInfoService.getUpEqutList(this.equtInfoBean);
      List downList = this.equtInfoService.getDownEqutList(this.equtInfoBean);

      int upLength = upList.size();
      int downLength = downList.size();

      int hasUp = (upLength == 0) ? 0 : 1;

      int maxLength = (upLength > downLength) ? upLength : downLength;

      topLen = (topLen * 2 + (maxLength - 1) * distanceHeight + maxLength * picHeight) / 2 - (picHeight / 2);

      this.cableList = new ArrayList();
      int thisTop;
      int i;
      EqutInfoBean e;
      if (downLength > 0)
      {
        thisTop = topLen + picHeight / 2 - (((downLength - 1) * distanceHeight + downLength * picHeight) / 2);
        for (i = 0; i < downLength; ++i)
        {
          e = (EqutInfoBean)downList.get(i);

          CableInfoBean cToDown = new CableInfoBean();
          cToDown.setStarteid(equt.getEid());
          cToDown.setEndeid(e.getEid());

          this.cableList.add(cToDown);
        }
      }

      if (upLength > 0)
      {
        thisTop = topLen + picHeight / 2 - (((upLength - 1) * distanceHeight + upLength * picHeight) / 2);
        for (i = 0; i < upLength; ++i)
        {
          e = (EqutInfoBean)upList.get(i);

          CableInfoBean cToUp = new CableInfoBean();
          cToUp.setStarteid(e.getEid());
          cToUp.setEndeid(equt.getEid());

          this.cableList.add(cToUp);
        }

      }

      this.resultList = new ArrayList();
      this.resultList.add(equt);
      this.resultList.addAll(upList);
      this.resultList.addAll(downList);
      return "topoView";
    } catch (Exception e) {
      userInfoBean.setLasteid("");
      this.equtInfoService.saveLastEid(userInfoBean);
      getSession().setAttribute("userBean", null); }
    return "reLogin";
  }

  public String getEqutInfo()
    throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      if (this.equtInfoBean != null) {
        this.equtInfoBean = this.equtInfoService.getEqutInfoBean(this.equtInfoBean);
        if (this.equtInfoBean != null) {
          this.domainBean = new DomainBean();
          this.domainBean.setDomainCode(this.equtInfoBean.getAreano());
          this.domainBean = this.domainService.getDomain(this.domainBean);
        }
      }
    } catch (Exception e) {
      log.error("EqutAction.getEqutInfo 获取信息异常...", e);
      e.printStackTrace();
      return "error";
    }
    return "equtInfo";
  }

  public String getEqutState() throws Exception {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      this.equtInfoBean = this.equtInfoService.getEqutInfoBean(this.equtInfoBean);
      if (this.equtInfoBean != null) {
    	  this.state = "1";
      }
      else {
        this.state = "0";
      }
      return "equtState";
    } catch (Exception e) {
      this.state = "0"; }
    return "equtState";
  }

  public void equtReset() throws Exception
  {
    this.equtInfoBean = this.equtInfoService.getEqutInfoBean(this.equtInfoBean);
    EqutSender.equtReboot(this.equtInfoBean);
  }

  public String getEqutPrlUserList()
    throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      this.equtInfoBean = this.equtInfoService.getEqutInfoBean(this.equtInfoBean);
      List userList = this.equtInfoService.getPrlUserList(this.equtInfoBean);
      this.user = new UserInfoBean();
      this.user.setUsers(userList);
    } catch (Exception e) {
      log.error("EqutAction.getEqutPrlUserList 获取信息异常...", e);
      e.printStackTrace();
      return "error";
    }
    return "prlUserList";
  }

  public String getEqutTree() throws Exception {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      DomainBean domain = new DomainBean();
      if (this.node.equals("rootNode")) {
        this.userInfoBean = new UserInfoBean();
        domain.setDomainId(Integer.valueOf(1));
        this.treeList = this.equtInfoService.getDomainAndEqutTree(domain); 
      }
      domain.setDomainId(Integer.valueOf(Integer.parseInt(this.node)));
      this.treeList = this.equtInfoService.getDomainAndEqutTree(domain);
    }
    catch (Exception e) {
      e.printStackTrace();
      log.error("EqutAction.getEqutTree 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    label146: return "equtTree";
  }

  public void updateEqutInfo() throws Exception {
    UserInfoBean userInfoBean = (UserInfoBean)getSession().getAttribute("userBean");
    try {
      this.equtInfoBean.setMtime(new Date());
      this.equtInfoBean.setMflag(Integer.valueOf(2));
      this.equtInfoService.updateEqutInfo(this.equtInfoBean);
    } catch (Exception e) {
      log.error("EqutAction.updateEqutInfo 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
    }
  }

  public String searchEqutInfo() throws Exception {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      this.treeList = this.equtInfoService.getEqutInfoTreeList(this.equtInfoBean);
    } catch (Exception e) {
      log.error("EqutAction.searchEqutInfo 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    return "searchEqutInfo";
  }

  public String exportequt()
    throws Exception
  {
    
    return "exportsuccess";
  }

  public String showTopo()
    throws Exception
  {
    UserInfoBean userInfoBean = (UserInfoBean)getSession().getAttribute("userBean");
    try {
      if ((!("".equals(this.areano))) && (this.areano != null))
      {
        this.cableInfoBean = new CableInfoBean();
        this.cableInfoBean.setAreanoLink("*" + this.areano + "*");
        List<CableInfoBean> cableListAll = this.equtInfoService.getCableList(this.cableInfoBean);

        this.resultList = new ArrayList();
        this.equtInfoBean = new EqutInfoBean();
        this.equtInfoBean.setAreano(this.areano);
        this.resultList = this.equtInfoService.getEqutByAreano(this.equtInfoBean);
        this.resultList2 = this.equtInfoService.getNoLatLonEqut(this.equtInfoBean);
        int alarmCount;
        if ((this.resultList != null) && (this.resultList.size() != 0)) {
          for (EqutInfoBean e : this.resultList) {
            alarmCount = this.equtInfoService.getCableAlarmCount(e);
          }
        }
        this.cableList = new ArrayList();
        if ((cableListAll != null) && (cableListAll.size() != 0)) {
          for (CableInfoBean c : cableListAll) {
            this.equtInfoBean = new EqutInfoBean();
            if ((c.getStarteid() != null) && (!("".equals(c.getStarteid())))) {
              this.equtInfoBean.setEid(c.getStarteid());
              this.equtInfoBean = this.equtInfoService.getEqutInfoBean(this.equtInfoBean);
              if (this.equtInfoBean != null) {
                if ((this.equtInfoBean.getLat() != null) && (!("".equals(this.equtInfoBean.getLat())))) {
                  c.setStartLat(this.equtInfoBean.getLat());
                }
                if ((this.equtInfoBean.getLon() != null) && (!("".equals(this.equtInfoBean.getLon())))) {
                  c.setStartLon(this.equtInfoBean.getLon());
                }

                alarmCount = this.equtInfoService.getCableAlarmCount(this.equtInfoBean);
                this.resultList.add(this.equtInfoBean);
              }
            }
            this.equtInfoBean = new EqutInfoBean();
            if ((c.getEndeid() != null) && (!("".equals(c.getEndeid())))) {
              this.equtInfoBean.setEid(c.getEndeid());
              this.equtInfoBean = this.equtInfoService.getEqutInfoBean(this.equtInfoBean);
              if (this.equtInfoBean != null) {
                if ((this.equtInfoBean.getLat() != null) && (!("".equals(this.equtInfoBean.getLat())))) {
                  c.setEndLat(this.equtInfoBean.getLat());
                }
                if ((this.equtInfoBean.getLon() != null) && (!("".equals(this.equtInfoBean.getLon())))) {
                  c.setEndLon(this.equtInfoBean.getLon());
                }
                alarmCount = this.equtInfoService.getCableAlarmCount(this.equtInfoBean);
                this.resultList.add(this.equtInfoBean);
              }
            }
            if ((c.getEndLat() != null) && (c.getEndLon() != null) && (c.getStartLat() != null) && (c.getStartLon() != null)) {
              this.cableList.add(c);
            }
          }
        }
      }
      return "showTopo";
    } catch (Exception e) {
      log.error("EqutAction.showToPo 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String loadDomainTree()
    throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try
    {
      if (this.domainBean == null) {
        this.domainBean = new DomainBean();
      }
      this.domainBean.setDomainCode(this.userInfoBean.getAreano());
      this.treeList = this.domainService.getDomainAndEquitTree(this.domainBean);
      return "loadDomainTree";
    }
    catch (Exception e) {
      log.error("DomainAction.loadDomainTree", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String searchDomainTree() throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try
    {
      this.domainBean.setDomainCode(this.userInfoBean.getAreano());
      this.treeList = this.domainService.findEqutDomianTree(this.domainBean);
      if ((this.treeList != null) && (!(this.treeList.isEmpty())))
        this.success = true;
      else {
        this.success = false;
      }
      return "searchDomainTree";
    }
    catch (Exception e) {
      log.error("DomainAction.searchDomainTree", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String importButchUpdate()
    throws Exception
  {
    int rows = 0;
    int upPointCount = 0;
    String areano = "";
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try
    {
      if (this.excle != null)
      {
        POIFSFileSystem fs = null;
        HSSFWorkbook wb = null;
        fs = new POIFSFileSystem(new FileInputStream(this.excle));
        wb = new HSSFWorkbook(fs);
        updateEqut(wb, this.userInfoBean);
        updatePoint(wb, this.userInfoBean);

        this.success = true;
      }
    } catch (IOException e) {
      log.error("PointAction.betchUpdatePoint", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    catch (DataAccessException e) {
      log.error("PointAction.betchUpdatePoint", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    catch (IllegalArgumentException e) {
      this.success = false;
      this.verifyMsg = "文件数量上传不全！";
      return "list";
    }
    catch (Exception e) {
      log.error("PointAction.betchUpdatePoint", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    return "list";
  }

  private void updateEqut(HSSFWorkbook wb, UserInfoBean userBean)
    throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    int rows = 0;
    HSSFSheet sheet = wb.getSheetAt(0);
    rows = sheet.getLastRowNum();
    DecimalFormat df = new DecimalFormat("#");
    Date eventTime = new Date();

    for (int i = 1; i <= rows; ++i) {
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

      String eid = null;
      if (cell == null) return;
      if (cell.getCellType() == 0)
        eid = df.format(cell.getNumericCellValue());
      else {
        eid = cell.getStringCellValue();
      }

      if (eid == null) return; if ("".equals(eid))
      {
        return;
      }

      String ename = null;
      if (cell1 != null) {
        if (cell1.getCellType() == 0)
          ename = df.format(cell1.getNumericCellValue());
        else {
          ename = cell1.getStringCellValue();
        }

      }

      String ecode = null;
      if (cell2 != null) {
        if (cell2.getCellType() == 0)
          ecode = df.format(cell2.getNumericCellValue());
        else {
          ecode = cell2.getStringCellValue();
        }

      }

      String etype = null;
      if (cell3 != null) {
        if (cell3.getCellType() == 0) {
          etype = df.format(cell3.getNumericCellValue());
        } else {
          etype = cell3.getStringCellValue();
          etype = CommonUtil.getEtypeInt(etype);
        }

      }

      String emodel = "";
      if (cell4 != null) {
        if (cell4.getCellType() == 0) {
          emodel = df.format(cell4.getNumericCellValue());
        }
        else {
          emodel = cell4.getStringCellValue();
        }

      }

      String estat = "";
      if (cell5 != null) {
        if (cell5.getCellType() == 0) {
          estat = df.format(cell5.getNumericCellValue());
        } else {
          estat = cell5.getStringCellValue();
          estat = CommonUtil.getEststInt(estat);
        }

      }

      String station = "";
      if (cell6 != null) {
        if (cell6.getCellType() == 0) {
          station = df.format(cell6.getNumericCellValue());
        }
        else {
          station = cell6.getStringCellValue();
        }

      }

      String eaddr = "";
      if (cell7 != null) {
        if (cell7.getCellType() == 0)
          eaddr = df.format(cell7.getNumericCellValue());
        else {
          eaddr = cell7.getStringCellValue();
        }

      }

      String lon = "";
      if (cell8 != null) {
        if (cell8.getCellType() == 0)
          lon = df.format(cell8.getNumericCellValue());
        else {
          lon = cell8.getStringCellValue();
        }
      }

      String lat = "";
      if (cell9 != null) {
        if (cell9.getCellType() == 0)
          lat = df.format(cell9.getNumericCellValue());
        else {
          lat = cell9.getStringCellValue();
        }

      }

      String note = "";
      if (cell10 != null) {
        if (cell10.getCellType() == 0)
          note = df.format(cell10.getNumericCellValue());
        else {
          note = cell10.getStringCellValue();
        }
      }

      this.equtInfoBean = new EqutInfoBean();
      this.equtInfoBean.setEid(eid);
      this.equtInfoBean.setEname(ename);
      this.equtInfoBean.setEcode(ecode);
      this.equtInfoBean.setEtype(etype);
      this.equtInfoBean.setEmodel(emodel);
      this.equtInfoBean.setEstat(estat);
      this.equtInfoBean.setEaddr(eaddr);
      this.equtInfoBean.setLon(lon);
      this.equtInfoBean.setLat(lat);
      this.equtInfoBean.setNote(note);
      this.equtInfoBean.setMflag(Integer.valueOf(2));
      this.equtInfoBean.setMtime(eventTime);
      this.equtInfoService.updateEqutInfo(this.equtInfoBean);
    }
  }

  private void updatePoint(HSSFWorkbook wb, UserInfoBean userBean)
    throws Exception
  {
    int rows = 0;
    HSSFSheet sheet = wb.getSheetAt(1);
    rows = sheet.getLastRowNum();
    DecimalFormat df = new DecimalFormat("#");
    Date eventTime = new Date();
    List<RouteInfoBean> routeList = new ArrayList();
    List<CableInfoBean> cableList = new ArrayList();
    List pointList = new ArrayList();
    for (int i = 1; i <= rows; ++i) {
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

      String pid = "";
      if (cell == null) break;
      if (cell.getCellType() == 0)
        pid = df.format(cell.getNumericCellValue());
      else {
        pid = cell.getStringCellValue();
      }
      if (pid == null) break; if ("".equals(pid)) {
        break;
      }
      pid = "0" + pid;
      pid = pid.substring(pid.length() - 6, pid.length());

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

      String pcode = "";
      if (cell4 != null) {
        if (cell4.getCellType() == 0)
          pcode = df.format(cell4.getNumericCellValue());
        else {
          pcode = cell4.getStringCellValue();
        }

      }

      String pstat = "";
      if (cell5 != null) {
        if (cell5.getCellType() == 0) {
          pstat = df.format(cell5.getNumericCellValue());
        } else {
          pstat = cell5.getStringCellValue();
          pstat = CommonUtil.getPstatInt(pstat);
        }
      }

      String direction = "";
      if (cell6 != null) {
        if (cell6.getCellType() == 0) {
          direction = df.format(cell6.getNumericCellValue());
        } else {
          direction = cell6.getStringCellValue();
          direction = CommonUtil.getDirectionInt(direction);
        }
      }

      String ptype = "";
      if (cell7 != null) {
        if (cell7.getCellType() == 0) {
          ptype = df.format(cell7.getNumericCellValue());
        } else {
          ptype = cell7.getStringCellValue();
          ptype = CommonUtil.getPtypeInt(ptype);
        }
      }

      String servno = null;
      if (cell8 != null) {
        if (cell8.getCellType() == 0)
          servno = df.format(cell8.getNumericCellValue());
        else {
          servno = cell8.getStringCellValue();
        }
      }

      String servtype = null;
      if (cell9 != null) {
        if (cell9.getCellType() == 0)
          servtype = df.format(cell9.getNumericCellValue());
        else {
          servtype = cell9.getStringCellValue();
        }
      }

      String pserv = "";
      if (cell10 != null) {
        if (cell10.getCellType() == 0)
          pserv = df.format(cell10.getNumericCellValue());
        else {
          pserv = cell10.getStringCellValue();
        }

      }

      String fiberstationname = "";
      if (cell11 != null) {
        if (cell11.getCellType() == 0)
          fiberstationname = df.format(cell11.getNumericCellValue());
        else {
          fiberstationname = cell11.getStringCellValue();
        }

      }

      String fibname = "";
      if (cell12 != null) {
        if (cell12.getCellType() == 0)
          fibname = df.format(cell12.getNumericCellValue());
        else {
          fibname = cell12.getStringCellValue();
        }

      }

      String cablename = "";
      if (cell13 != null)
      {
        if (cell13.getCellType() == 0)
          cablename = df.format(cell13.getNumericCellValue());
        else {
          cablename = cell13.getStringCellValue();
        }

      }

      String routename = "";
      if (cell14 != null) {
        if (cell14.getCellType() == 0)
          routename = df.format(cell14.getNumericCellValue());
        else {
          routename = cell14.getStringCellValue();
        }
      }

      String ofpcode = "";
      if (cell15 != null) {
        if (cell15.getCellType() == 0)
          ofpcode = df.format(cell15.getNumericCellValue());
        else {
          ofpcode = cell15.getStringCellValue();
        }

      }

      String ofpname = "";
      if (cell16 != null) {
        if (cell16.getCellType() == 0)
          ofpname = df.format(cell16.getNumericCellValue());
        else {
          ofpname = cell16.getStringCellValue();
        }

      }

      String oppo_ename = "";
      if (cell17 != null) {
        if (cell17.getCellType() == 0)
          oppo_ename = df.format(cell17.getNumericCellValue());
        else {
          oppo_ename = cell17.getStringCellValue();
        }

      }

      String oppo_ecode = "";
      if (cell18 != null) {
        if (cell18.getCellType() == 0)
          oppo_ecode = df.format(cell18.getNumericCellValue());
        else {
          oppo_ecode = cell18.getStringCellValue();
        }

      }

      String oppo_pcode = "";
      if (cell19 != null) {
        if (cell19.getCellType() == 0)
          oppo_pcode = df.format(cell19.getNumericCellValue());
        else {
          oppo_pcode = cell19.getStringCellValue();
        }
      }

      String note = "";
      if (cell20 != null) {
        if (cell20.getCellType() == 0)
          note = df.format(cell20.getNumericCellValue());
        else {
          note = cell20.getStringCellValue();
        }

      }

      this.point = new PointInfoBean();

      pointList.add(this.point);

      boolean insertRoute = false;
      boolean insertCable = false;
      if ((routename == null) || (cablename == null) || (routename.equals("")) || 
        (cablename.equals("")))
        continue;
      int hn;
      if (routeList.size() < 1)
      {
        RouteInfoBean hsr = new RouteInfoBean();
        hsr.setRoutename(routename);
        hn = this.pointInfoService.getRouteCountByName(hsr);
        if (hn == 0)
          insertRoute = true;
        else
          insertRoute = false;
      }
      else {
        for (RouteInfoBean route : routeList) {
          insertRoute = true;
          if (route.getRoutename().equals(routename)) {
            insertRoute = false;
            break;
          }

          RouteInfoBean hsr2 = new RouteInfoBean();
          hsr2.setRoutename(routename);
          hn = this.pointInfoService.getRouteCountByName(hsr2);
          if (hn == 0)
            insertRoute = true;
          else {
            insertRoute = false;
          }
        }
      }

      if (insertRoute) {
        RouteInfoBean r = new RouteInfoBean();
        r.setRoutename(routename);

        r.setAreano(userBean.getAreano());
        routeList.add(r);
      }
      int hn2;
      if (cableList.size() < 1)
      {
        CableInfoBean hsc = new CableInfoBean();
        hsc.setCablename(cablename);
        hsc.setRoutename(routename);

        hn2 = this.pointInfoService.getCableCountByName(hsc);
        if (hn2 == 0)
          insertCable = true;
        else
          insertCable = false;
      }
      else {
        for (CableInfoBean cable : cableList) {
          insertCable = true;
          if ((cable.getCablename().equals(cablename)) && (cable.getRoutename().equals(routename))) {
            insertCable = false;
            break;
          }
          CableInfoBean hsc = new CableInfoBean();
          hsc.setCablename(cablename);
          hsc.setRoutename(routename);
          hn2 = this.pointInfoService.getCableCountByName(hsc);
          if (hn2 == 0)
            insertCable = true;
          else {
            insertCable = false;
          }
        }
      }

      if (insertCable) {
        CableInfoBean c = new CableInfoBean();
        c.setCablename(cablename);
        c.setRoutename(routename);

        c.setAreano(userBean.getAreano());
        cableList.add(c);
      }
    }

    this.pointInfoService.updatePointInfo2(pointList);

    this.pointInfoService.batchInsertRoute(routeList);
    this.pointInfoService.batchInsertCable(cableList);
  }

  public String updateDomain() throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    int u = this.equtInfoService.updateEqutDomain(this.equtInfoBean);
    if (u != 0) {
      this.success = true;
    }
    return "updateDomain";
  }

  public String getSonEqut() throws Exception {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try
    {
      this.resultList = this.equtInfoService.getSonList(this.equtInfoBean);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      log.error("EqutAction.getSonEqut 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    return "sonList";
  }

  public String showEqutPoint() throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      this.equtInfoBean.setStart(this.start);
      this.equtInfoBean.setLimit(this.limit);
      this.point = this.equtInfoService.getEqutPoint(this.equtInfoBean);
    }
    catch (Exception e) {
      e.printStackTrace();
      log.error("EqutAction.showEqutPoint 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    return "showEqutPoint";
  }

  public String pigtailCodeExport() throws Exception
  {
    File csvFile = null;
    BufferedWriter csvFileOutputStream = null;
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try
    {
      CodeIndexInfoBean ci = new CodeIndexInfoBean();
      int oldindex = this.equtInfoService.getIndex(ci);
      this.start.intValue();

      csvFile = new File(getServletContext().getRealPath("/") + "downloadtemp" + File.separator + "尾纤二维码生成导出列表.txt");
      File parent = csvFile.getParentFile();
      if ((parent != null) && (!(parent.exists()))) {
        parent.mkdirs();
      }
      if (csvFile.exists()) {
        csvFile.delete();
      }
      csvFile.createNewFile();
      csvFileOutputStream = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);
      csvFileOutputStream.write("尾纤二维码");
      csvFileOutputStream.write("\r\n");
      for (int i = 1; i <= this.start.intValue(); ++i) {
        String str = "Y0";
        String six = Integer.toHexString(oldindex + i);
        for (int j = 0; j < 8 - six.length(); ++j) {
          str = str + "0";
        }
        str = str + six;
        csvFileOutputStream.write(str + "\r\n");
      }
      this.filepath = "downloadtemp/尾纤二维码生成导出列表.txt";
      ci.setCodenum(Integer.valueOf(this.start.intValue() + oldindex));
      this.equtInfoService.insertIndex(ci);
    }
    catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        csvFileOutputStream.flush();
        csvFileOutputStream.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
    return "exportcode";
  }

  public String equtCodeExport() throws Exception
  {
    File csvFile = null;
    BufferedWriter csvFileOutputStream = null;
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      String[] mnstr = this.modelName.split(",");
      String[] num = this.strnum.split(",");

      csvFile = new File(getServletContext().getRealPath("/") + "downloadtemp" + File.separator + "设备二维码生成导出列表.txt");
      File parent = csvFile.getParentFile();
      if ((parent != null) && (!(parent.exists()))) {
        parent.mkdirs();
      }
      if (csvFile.exists()) {
        csvFile.delete();
      }
      csvFile.createNewFile();
      csvFileOutputStream = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);
      csvFileOutputStream.write("设备二维码");
      csvFileOutputStream.write("\r\n");
      ArrayList list = new ArrayList();
      for (int i = 0; i < mnstr.length; ++i) {
        for (int j = 0; j < Integer.valueOf(num[i]).intValue(); ++j) {
          boolean flag = true;
          Random rand = new Random();
          int randNum = rand.nextInt(2147483647) + 1;

          EqutInfoBean e = new EqutInfoBean();
          e.setEid(randNum+"");
          e = this.equtInfoService.getEqutInfoBean(e);
          if (e != null) {
            --j;
            flag = false;
          }
          else {
            for (int k = 0; k < list.size(); ++k) {
              if (list.get(k).equals(Integer.valueOf(randNum))) {
                --j;
                flag = false;
                break;
              }
            }
          }
          if (flag) {
            list.add(Integer.valueOf(randNum));
            csvFileOutputStream.write(randNum + "," + mnstr[i] + "\r\n");
          }
        }
      }
      this.filepath = "downloadtemp/设备二维码生成导出列表.txt";
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        csvFileOutputStream.flush();
        csvFileOutputStream.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
    return "exportcode";
  }

  public String getApplyGrid() throws Exception {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      if (this.applyBean == null) {
        this.applyBean = new ApplyInfoBean();
      }
      this.applyBean = this.equtInfoService.applyGrid(this.applyBean);
      return "applyList";
    } catch (Exception e) {
      log.error("EqutAction.getApplyGrid 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String delApply() throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      int n = 0;
      if (this.applyBean != null) {
        n = this.equtInfoService.delApplyBean(this.applyBean);
      }
      if (n == 1)
        this.success = true;
      else {
        this.success = false;
      }
      return "applyDel";
    } catch (Exception e) {
      log.error("EqutAction.delApply 获取信息异常...", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String updateEqutIP() throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    int u = this.equtInfoService.updateEqutIP(this.equtInfoBean);
    if (u != 0) {
      this.success = true;
    }
    return "updateDomain";
  }

  public String getEqutGrid()
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      if (this.equtInfoBean != null)
        this.equtInfoBean = this.equtInfoService.getEqutGrid(this.equtInfoBean);
    }
    catch (Exception e) {
      log.error("EqutAction.getEqutInfo 获取信息异常...", e);
      e.printStackTrace();
      return "error";
    }
    return "getEqutGrid";
  }

  public String seeReport() {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    return ((String)"seeReport");
  }

  public List<EqutInfoBean> getResultList()
  {
    return this.resultList;
  }

  public void setResultList(List<EqutInfoBean> resultList) {
    this.resultList = resultList;
  }

  public EqutInfoService getEqutInfoService() {
    return this.equtInfoService;
  }

  public void setEqutInfoService(EqutInfoService equtInfoService) {
    this.equtInfoService = equtInfoService;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getEid() {
    return this.eid;
  }

  public void setEid(String eid) {
    this.eid = eid;
  }

  public String getEaddr() {
    return this.eaddr;
  }

  public void setEaddr(String eaddr) {
    this.eaddr = eaddr;
  }

  public String getEcode() {
    return this.ecode;
  }

  public void setEcode(String ecode) {
    this.ecode = ecode;
  }

  public String getEstat() {
    return this.estat;
  }

  public void setEstat(String estat) {
    this.estat = estat;
  }

  public String getEtype() {
    return this.etype;
  }

  public void setEtype(String etype) {
    this.etype = etype;
  }

  public String getEname() {
    return this.ename;
  }

  public void setEname(String ename) {
    this.ename = ename;
  }

  public String getOnu_sn() {
    return this.onu_sn;
  }

  public void setOnu_sn(String onu_sn) {
    this.onu_sn = onu_sn;
  }

  public String getLon() {
    return this.lon;
  }

  public void setLon(String lon) {
    this.lon = lon;
  }

  public String getLat() {
    return this.lat;
  }

  public void setLat(String lat) {
    this.lat = lat;
  }

  public String getNote() {
    return this.note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public String getMbtime() {
    return this.mbtime;
  }

  public void setMbtime(String mbtime) {
    this.mbtime = mbtime;
  }

  public String getMetime() {
    return this.metime;
  }

  public void setMetime(String metime) {
    this.metime = metime;
  }

  public String getMp() {
    return this.mp;
  }

  public void setMp(String mp) {
    this.mp = mp;
  }

  public String getLatl() {
    return this.latl;
  }

  public void setLatl(String latl) {
    this.latl = latl;
  }

  public String getLath() {
    return this.lath;
  }

  public void setLath(String lath) {
    this.lath = lath;
  }

  public String getLonl() {
    return this.lonl;
  }

  public void setLonl(String lonl) {
    this.lonl = lonl;
  }

  public String getLonh() {
    return this.lonh;
  }

  public void setLonh(String lonh) {
    this.lonh = lonh;
  }

  public String getIsfb() {
    return this.isfb;
  }

  public void setIsfb(String isfb) {
    this.isfb = isfb;
  }

  public String getSelectValue() {
    return this.selectValue;
  }

  public void setSelectValue(String selectValue) {
    this.selectValue = selectValue;
  }

  public String[] getTemp() {
    return this.temp;
  }

  public void setTemp(String[] temp) {
    this.temp = temp;
  }

  public int[] getSelectUserVlaue() {
    return this.selectUserVlaue;
  }

  public void setSelectUserVlaue(int[] selectUserVlaue) {
    this.selectUserVlaue = selectUserVlaue;
  }

  public String getAreano() {
    return this.areano;
  }

  public void setAreano(String areano) {
    this.areano = areano;
  }

  public String getPaction() {
    return this.paction;
  }

  public void setPaction(String paction) {
    this.paction = paction;
  }

  public String getEids() {
    return this.eids;
  }

  public void setEids(String eids) {
    this.eids = eids;
  }

  public String getFilepath() {
    return this.filepath;
  }

  public void setFilepath(String filepath) {
    this.filepath = filepath;
  }

  public String getUserids() {
    return this.userids;
  }

  public void setUserids(String userids) {
    this.userids = userids;
  }

  public String getSelectuser() {
    return this.selectuser;
  }

  public void setSelectuser(String selectuser) {
    this.selectuser = selectuser;
  }

  public String getUserstr() {
    return this.userstr;
  }

  public void setUserstr(String userstr) {
    this.userstr = userstr;
  }

  public String getAreanostr() {
    return this.areanostr;
  }

  public void setAreanostr(String areanostr) {
    this.areanostr = areanostr;
  }

  public ErrorMessage getErrorMessage() {
    return this.errorMessage;
  }

  public void setErrorMessage(ErrorMessage errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getEqutAreano() {
    return this.equtAreano;
  }

  public void setEqutAreano(String equtAreano) {
    this.equtAreano = equtAreano;
  }

  public String getEmodel() {
    return this.emodel;
  }

  public void setEmodel(String emodel) {
    this.emodel = emodel;
  }

  public String getStation() {
    return this.station;
  }

  public void setStation(String station) {
    this.station = station;
  }

  public static long getSerialVersionUID() {
    return -8663153003164851927L;
  }

  public static Logger getLog() {
    return log;
  }

  public static String getMODULENAME() {
    return "equtmanage";
  }

  public static String getMODULENAME1() {
    return "constructionmonitor";
  }

  public EqutInfoBean getEqutInfoBean()
  {
    return this.equtInfoBean;
  }

  public void setEqutInfoBean(EqutInfoBean equtInfoBean)
  {
    this.equtInfoBean = equtInfoBean;
  }

  public List<CableInfoBean> getCableList()
  {
    return this.cableList;
  }

  public void setCableList(List<CableInfoBean> cableList)
  {
    this.cableList = cableList;
  }

  public String getNode() {
    return this.node;
  }

  public void setNode(String node) {
    this.node = node;
  }

  public UserInfoBean getUserInfoBean() {
    return this.userInfoBean;
  }

  public void setUserInfoBean(UserInfoBean userInfoBean) {
    this.userInfoBean = userInfoBean;
  }

  public DomainBean getDomainBean() {
    return this.domainBean;
  }

  public void setDomainBean(DomainBean domainBean) {
    this.domainBean = domainBean;
  }

  public DomainService getDomainService() {
    return this.domainService;
  }

  public void setDomainService(DomainService domainService) {
    this.domainService = domainService;
  }

  public List<Tree> getTreeList() {
    return this.treeList;
  }

  public void setTreeList(List<Tree> treeList) {
    this.treeList = treeList;
  }

  public PointInfoService getPointInfoService() {
    return this.pointInfoService;
  }

  public void setPointInfoService(PointInfoService pointInfoService) {
    this.pointInfoService = pointInfoService; }

  public UserInfoBean getUser() {
    return this.user;
  }

  public void setUser(UserInfoBean user) {
    this.user = user;
  }

  public String getState() {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public CableInfoBean getCableInfoBean() {
    return this.cableInfoBean;
  }

  public void setCableInfoBean(CableInfoBean cableInfoBean) {
    this.cableInfoBean = cableInfoBean;
  }

  public boolean isSuccess() {
    return this.success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }


  public UserInfoService getUserInfoService() {
    return this.userInfoService;
  }

  public void setUserInfoService(UserInfoService userInfoService) {
    this.userInfoService = userInfoService;
  }

  public PointInfoBean getPoint() {
    return this.point;
  }

  public void setPoint(PointInfoBean point) {
    this.point = point;
  }

  public PDAPointInfoService getPdaPointInfoService() {
    return this.pdaPointInfoService;
  }

  public void setPdaPointInfoService(PDAPointInfoService pdaPointInfoService) {
    this.pdaPointInfoService = pdaPointInfoService;
  }

  public File getExcle() {
    return this.excle;
  }

  public void setExcle(File excle) {
    this.excle = excle;
  }

  public String getVerifyMsg() {
    return this.verifyMsg;
  }

  public void setVerifyMsg(String verifyMsg) {
    this.verifyMsg = verifyMsg; }

  public String getDomainName() {
    return this.domainName;
  }

  public void setDomainName(String domainName) {
    this.domainName = domainName;
  }

  public List<EqutInfoBean> getResultList2() {
    return this.resultList2;
  }

  public void setResultList2(List<EqutInfoBean> resultList2) {
    this.resultList2 = resultList2;
  }

  public Integer getStart() {
    return this.start;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  public Integer getLimit() {
    return this.limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public String getModelName() {
    return this.modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public String getStrnum() {
    return this.strnum;
  }

  public void setStrnum(String strnum) {
    this.strnum = strnum;
  }

  public ApplyInfoBean getApplyBean() {
    return this.applyBean;
  }

  public void setApplyBean(ApplyInfoBean applyBean) {
    this.applyBean = applyBean;
  }

  public String getJson() {
    return this.json;
  }

  public void setJson(String json) {
    this.json = json;
  }
}