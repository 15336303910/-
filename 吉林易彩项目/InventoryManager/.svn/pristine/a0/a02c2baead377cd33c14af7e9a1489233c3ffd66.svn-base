package manage.point.web;

import base.exceptions.DataAccessException;
import base.util.ErrorMessage;
import base.web.PaginationAction;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import manage.equt.pojo.EqutInfoBean;
import manage.equt.service.EqutInfoService;
import manage.fiber.pojo.JumpFiberInfoBean;
import manage.point.pojo.PointEventInfoBean;
import manage.point.pojo.PointInfoBean;
import manage.point.service.PointInfoService;
import manage.user.pojo.UserInfoBean;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

public class PointAction extends PaginationAction
{
  private static final long serialVersionUID = -3252133367073605263L;
  private static final Logger log = Logger.getLogger(PointAction.class);
  private List<PointInfoBean> resultList;
  private Map<String, PointInfoBean> resultMap;
  private EqutInfoBean equtInfoBean;
  private List<PointInfoBean> equtList;
  private PointInfoService pointInfoService;
  private UserInfoBean userInfoBean;
  private EqutInfoService equtInfoService;
  private PointInfoBean pointInfoBean;
  private PointEventInfoBean pointEventInfoBean;
  private ErrorMessage errorMessage;
  private List<PointEventInfoBean> eventList;
  public static final String MODULENAME = "equtmanage";
  public static final String MODULENAME1 = "batchmanage";
  private String col;
  private String map;
  private String panelplace;
  private Integer limit;
  private Integer start;
  private String sort;
  private String dir;
  private String searchname;
  private Boolean success;
  private String verifyMsg;
  private String eaddr;
  private String idstr;
  private String startid;
  private String endid;
  private String mustEqutid;
  private String mustEqutid2;
  private String timeSep;
  private List<EqutInfoBean> nolist;
  private List<EqutInfoBean> uselist;
  private List<List<EqutInfoBean>> lists = new ArrayList();
  private String startpid;
  private String endpid;
  private List<List<PointInfoBean>> pointlists = new ArrayList();
  private String startpid1;
  private String endpid1;
  private List<List<PointInfoBean>> pointlists2 = new ArrayList();
  private PointInfoBean pjf;
  private List<PointInfoBean> outlist;
  private List<EqutInfoBean> equtlist;
  private PointInfoBean pointjumpBean;

  public String getPointFromEqut()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      long t1 = System.currentTimeMillis();
      this.resultList = this.pointInfoService.getPointInfoList(this.pointInfoBean);

      this.resultMap = new HashMap();
      for (PointInfoBean p : this.resultList) {
        this.resultMap.put(p.getPid(), p);
      }

    }
    catch (DataAccessException e)
    {
      log.error("PointAction.getPointFromEqut", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    return "pointFromEqut";
  }

  public String getEqutSetting() throws Exception {
    return "equtSetting";
  }

  public String getPointEvent() {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if ((this.pointEventInfoBean.getType() == null) || (!(this.pointEventInfoBean.getType().equals("1")))) {
        Date now = new Date();
        Date btime = new Date(now.getTime() - 5000L);
        Date etime = new Date(now.getTime() + 5000L);
        this.pointEventInfoBean.setAlarmTimeb(btime);
        this.pointEventInfoBean.setAlarmTimee(etime);
      }
      this.eventList = this.pointInfoService.getEventList(this.pointEventInfoBean);
    } catch (Exception e) {
      log.error("PointAction.getPointEvent", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    return "pointEvents";
  }

  public String getPointAlarm() {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.eventList = this.pointInfoService.getAlarmList(this.pointEventInfoBean);
    } catch (Exception e) {
      log.error("PointAction.getPointEvent", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    return "pointEvents";
  }

  public String intoTerminalPanel() throws Exception {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.equtInfoBean = this.equtInfoService.getEqutInfoBean(this.equtInfoBean);
    } catch (Exception e) {
      log.error("PointAction.intoTerminalPanel", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    return "intoTermianlPanel";
  }

  public String getPointInfo() throws Exception {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.pointInfoBean = this.pointInfoService.getPointInfoBean(this.pointInfoBean);
      return "getPointInfo"; 
    } catch (Exception e) {
      log.error("PointAction.getPointInfo", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
   }

	public String getOppoPointInfo() throws Exception {
		try {
			return "getPointInfo";
		} catch (Exception e) {
			log.error("PointAction.getPointInfo", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
			return "error";
		}
	}

  public String getFiberPointInfo() throws Exception {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.pointInfoBean = this.pointInfoService.getPointInfoBean(this.pointInfoBean);
      this.outlist = new ArrayList();
      JumpFiberInfoBean fiber = new JumpFiberInfoBean();
      fiber.setEid1(this.pointInfoBean.getEid());
      fiber.setPid1(this.pointInfoBean.getPid());
      List<JumpFiberInfoBean> jumplist = this.pointInfoService.getJumpList(fiber);
      if (jumplist.size() != 0)
        for (JumpFiberInfoBean j : jumplist) {
          PointInfoBean pp = new PointInfoBean();
          if (j.getPos().equals("1")) {
            pp.setEid(j.getEid2());
            pp.setPid(j.getPid2());
          } else {
            pp.setEid(j.getEid1());
            pp.setPid(j.getPid1());
          }
          pp = this.pointInfoService.getPointInfoBean(pp);
          this.outlist.add(pp);
        }
    }
    catch (Exception e) {
      log.error("PointAction.getPointInfo", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    return "getPointList";
  }

  public void updatePointInfo() throws Exception {
    UserInfoBean userInfoBean = (UserInfoBean)getSession().getAttribute("userBean");
    try {
      this.pointInfoBean.setMflag(Integer.valueOf(2));
      this.pointInfoService.updatePointInfo(this.pointInfoBean);
      if (this.pointjumpBean != null) {
        this.pointjumpBean.setMflag(Integer.valueOf(2));
        this.pointInfoService.updatePointInfo(this.pointjumpBean);
      }
    } catch (Exception e) {
      log.error("PointAction.updatePointInfo", e);
      e.printStackTrace();
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
    }
  }

  public String ofplist() throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      return "pointInfoBean";
    } catch (Exception e) {
      log.error("PointAction.ofplist", e);
      e.printStackTrace();
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String subofplist() throws Exception {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      return "subpointInfoBean";
    } catch (Exception e) {
      log.error("PointAction.ofplist", e);
      e.printStackTrace();
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String searchOfpOfCode() throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    try {
      return "pointInfoBean";
    } catch (Exception e) {
      log.error("PointAction.ofplist", e);
      e.printStackTrace();
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String verifyOfpcode() throws Exception
  {
    try {
      return "verifyOfpcode";
    } catch (Exception e) {
      log.error("RouteAction.verifyRoute", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String checkOfpNode() throws Exception
  {
    try {
      return "checkOfpNode";
    } catch (Exception e) {
      log.error("PointAction.checkOfpNode", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String getOfpNodeList() throws Exception
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.pointInfoBean = this.pointInfoService.getOfpNodeSort(this.pointInfoBean);
      if (this.pointInfoBean != null)
        this.success = Boolean.valueOf(true);
      else {
        this.success = Boolean.valueOf(false);
      }
      return "loadOfpNode";
    } catch (Exception e) {
      log.error("PointAction.getOfpNodeList", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String checkpointFaultList() throws Exception
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.idstr = this.idstr;
      return "faultOfpNode";
    } catch (Exception e) {
      log.error("PointAction.checkOfpNode", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String pointFaultList() throws Exception
  {
    try {
      return "loadOfpNode";
    } catch (Exception e) {
      log.error("PointAction.pointFaultList", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String pointDelDomList() throws Exception
  {
    try {
      return "loadOfpNode";
    } catch (Exception e) {
      log.error("PointAction.pointDelDomList", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String getAutomaticOfp() throws Exception
  {
    return ((String)"error");
  }

  public boolean getJumpFiber(PointInfoBean p)
  {

    return false;
  }

  public List<EqutInfoBean> oppoeid(List<EqutInfoBean> list, String startidd, String endidd, String mustEqutid, String mustEqutid2)
    throws Exception
  {
    PointInfoBean pointst = new PointInfoBean();
    return list;
  }

  public String getPointLog()
    throws Exception
  {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    if (this.pointInfoBean == null) {
      this.pointInfoBean = new PointInfoBean();
    }
    this.pointInfoBean.setStart(this.start);
    this.pointInfoBean.setLimit(this.limit);
    this.pointInfoBean.setSort(this.sort);
    this.pointInfoBean.setDir(this.dir);
    this.pointInfoBean = this.pointInfoService.getPointLogList(this.pointInfoBean);
    return "pointInfoBean";
  }

  public String getPointLogs() throws Exception {
    this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
    if (this.pointInfoBean == null) {
      this.pointInfoBean = new PointInfoBean();
    }
    this.pointInfoBean.setStart(this.start);
    this.pointInfoBean.setLimit(this.limit);
    this.pointInfoBean.setSort(this.sort);
    this.pointInfoBean.setDir(this.dir);
    this.pointInfoBean = this.pointInfoService.getPointLogList(this.pointInfoBean);
    return "pointInfos";
  }

  public String ofpOfEqut() throws Exception {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      this.equtlist = new ArrayList();
      if (this.pointInfoBean != null) {
        this.pointInfoBean = this.pointInfoService.getOfpNodeSort(this.pointInfoBean);
      }
      if (this.pointInfoBean != null) {
        List points = this.pointInfoBean.getPoints();
        PointInfoBean p = new PointInfoBean();
        PointInfoBean p1 = new PointInfoBean();
        for (int i = 0; i < points.size(); ++i) {
          if (i != points.size() - 1) {
            p = (PointInfoBean)points.get(i);
            p1 = (PointInfoBean)points.get(i + 1);
            if (!(p.getEid().equals(p1.getEid()))) {
              this.equtInfoBean = new EqutInfoBean();
              this.equtInfoBean.setEid(p.getEid());
              this.equtInfoBean = this.pointInfoService.getEqutInfo(this.equtInfoBean);
              this.equtlist.add(this.equtInfoBean);
            }
          } else {
            p = (PointInfoBean)points.get(i);
            this.equtInfoBean = new EqutInfoBean();
            this.equtInfoBean.setEid(p.getEid());
            this.equtInfoBean = this.pointInfoService.getEqutInfo(this.equtInfoBean);
            this.equtlist.add(this.equtInfoBean);
          }
        }
      }
      return "ofptopo";
    } catch (Exception e) {
      log.error("PointAction.ofpOfEqut", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public void seePoint() {
    this.map = "[{'boxorder':'01,02,03,04,05,06','inboxorder':'01,02,03,04,05,06,07,08,09,10,11,12','type':1,'intype':0,'inpointtype':0,'boxn':'01-Arjfkrk','rownum':1,'vernum':6,'inrownum':12,'invernum':1},{'boxorder':'01,02,03,04,05,06','inboxorder':'01,02,03,04,05,06','type':0,'intype':1,'inpointtype':1,'boxn':'02-A','rownum':2,'vernum':3,'inrownum':2,'invernum':3}]";

    HttpServletResponse response = ServletActionContext.getResponse();
    response.setContentType("text/json");
    response.setCharacterEncoding("utf-8");
    try {
      response.getWriter().write(this.map.toString());
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    try {
      response.getWriter().flush();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getDir()
  {
    return this.dir; }

  public void setDir(String dir) {
    this.dir = dir; }

  public String getSort() {
    return this.sort; }

  public void setSort(String sort) {
    this.sort = sort; }

  public String getIdstr() {
    return this.idstr;
  }

  public void setIdstr(String idstr) {
    this.idstr = idstr; }

  public UserInfoBean getUserInfoBean() {
    return this.userInfoBean;
  }

  public void setUserInfoBean(UserInfoBean userInfoBean) {
    this.userInfoBean = userInfoBean;
  }

  public String getVerifyMsg() {
    return this.verifyMsg;
  }

  public void setVerifyMsg(String verifyMsg) {
    this.verifyMsg = verifyMsg;
  }

  public Boolean getSuccess() {
    return this.success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public Integer getLimit() {
    return this.limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public Integer getStart() {
    return this.start;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  public List<PointInfoBean> getResultList() {
    return this.resultList;
  }

  public void setResultList(List<PointInfoBean> resultList) {
    this.resultList = resultList;
  }

  public List<PointInfoBean> getEqutList() {
    return this.equtList;
  }

  public void setEqutList(List<PointInfoBean> equtList) {
    this.equtList = equtList; }

  @JSON(serialize=false)
  public PointInfoService getPointInfoService() {
    return this.pointInfoService; }

  @JSON(serialize=false)
  public void setPointInfoService(PointInfoService pointInfoService) {
    this.pointInfoService = pointInfoService;
  }

  public PointInfoBean getPointInfoBean() {
    return this.pointInfoBean;
  }

  public void setPointInfoBean(PointInfoBean pointInfoBean) {
    this.pointInfoBean = pointInfoBean;
  }

  public ErrorMessage getErrorMessage() {
    return this.errorMessage;
  }

  public void setErrorMessage(ErrorMessage errorMessage) {
    this.errorMessage = errorMessage;
  }

  public static long getSerialVersionUID() {
    return -3252133367073605263L;
  }

  public static Logger getLog() {
    return log;
  }

  public static String getMODULENAME() {
    return "equtmanage";
  }

  public static String getMODULENAME1() {
    return "batchmanage"; }

  public String getCol() {
    return this.col; }

  public void setCol(String col) {
    this.col = col; }

  public String getMap() {
    return this.map; }

  public void setMap(String map) {
    this.map = map;
  }

  public List<PointEventInfoBean> getEventList() {
    return this.eventList;
  }

  public void setEventList(List<PointEventInfoBean> eventList) {
    this.eventList = eventList;
  }

  public EqutInfoBean getEqutInfoBean() {
    return this.equtInfoBean;
  }

  public void setEqutInfoBean(EqutInfoBean equtInfoBean) {
    this.equtInfoBean = equtInfoBean;
  }

  public String getSearchname() {
    return this.searchname; }

  public void setSearchname(String searchname) {
    this.searchname = searchname;
  }

  public EqutInfoService getEqutInfoService() {
    return this.equtInfoService;
  }

  public void setEqutInfoService(EqutInfoService equtInfoService) {
    this.equtInfoService = equtInfoService;
  }

  public PointEventInfoBean getPointEventInfoBean() {
    return this.pointEventInfoBean;
  }

  public void setPointEventInfoBean(PointEventInfoBean pointEventInfoBean) {
    this.pointEventInfoBean = pointEventInfoBean;
  }

  public String getEaddr() {
    return this.eaddr;
  }

  public void setEaddr(String eaddr) {
    this.eaddr = eaddr;
  }

  public String getStartid() {
    return this.startid;
  }

  public void setStartid(String startid) {
    this.startid = startid;
  }

  public String getEndid() {
    return this.endid;
  }

  public void setEndid(String endid) {
    this.endid = endid;
  }

  public List<EqutInfoBean> getNolist() {
    return this.nolist;
  }

  public void setNolist(List<EqutInfoBean> nolist) {
    this.nolist = nolist;
  }

  public List<EqutInfoBean> getUselist() {
    return this.uselist;
  }

  public void setUselist(List<EqutInfoBean> uselist) {
    this.uselist = uselist;
  }

  public List<List<EqutInfoBean>> getLists() {
    return this.lists;
  }

  public void setLists(List<List<EqutInfoBean>> lists) {
    this.lists = lists;
  }

  public String getMustEqutid() {
    return this.mustEqutid;
  }

  public void setMustEqutid(String mustEqutid) {
    this.mustEqutid = mustEqutid;
  }

  public String getMustEqutid2() {
    return this.mustEqutid2;
  }

  public void setMustEqutid2(String mustEqutid2) {
    this.mustEqutid2 = mustEqutid2;
  }

  public String getTimeSep() {
    return this.timeSep;
  }

  public void setTimeSep(String timeSep) {
    this.timeSep = timeSep;
  }

  public String getPanelplace() {
    return this.panelplace;
  }

  public void setPanelplace(String panelplace) {
    this.panelplace = panelplace;
  }

  public String getStartpid() {
    return this.startpid;
  }

  public void setStartpid(String startpid) {
    this.startpid = startpid;
  }

  public String getEndpid() {
    return this.endpid;
  }

  public void setEndpid(String endpid) {
    this.endpid = endpid;
  }

  public List<List<PointInfoBean>> getPointlists() {
    return this.pointlists;
  }

  public void setPointlists(List<List<PointInfoBean>> pointlists) {
    this.pointlists = pointlists;
  }

  public String getStartpid1() {
    return this.startpid1;
  }

  public void setStartpid1(String startpid1) {
    this.startpid1 = startpid1;
  }

  public String getEndpid1() {
    return this.endpid1;
  }

  public void setEndpid1(String endpid1) {
    this.endpid1 = endpid1;
  }

  public PointInfoBean getPjf() {
    return this.pjf;
  }

  public void setPjf(PointInfoBean pjf) {
    this.pjf = pjf;
  }

  public List<PointInfoBean> getOutlist() {
    return this.outlist;
  }

  public void setOutlist(List<PointInfoBean> outlist) {
    this.outlist = outlist;
  }

  public List<List<PointInfoBean>> getPointlists2() {
    return this.pointlists2;
  }

  public void setPointlists2(List<List<PointInfoBean>> pointlists2) {
    this.pointlists2 = pointlists2;
  }

  public List<EqutInfoBean> getEqutlist() {
    return this.equtlist;
  }

  public void setEqutlist(List<EqutInfoBean> equtlist) {
    this.equtlist = equtlist;
  }

  public Map<String, PointInfoBean> getResultMap() {
    return this.resultMap;
  }

  public void setResultMap(Map<String, PointInfoBean> resultMap) {
    this.resultMap = resultMap;
  }

  public PointInfoBean getPointjumpBean() {
    return this.pointjumpBean;
  }

  public void setPointjumpBean(PointInfoBean pointjumpBean) {
    this.pointjumpBean = pointjumpBean;
  }
}
