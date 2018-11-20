 package manage.resource.web;
 
 import base.util.ErrorMessage;
 import com.google.gson.Gson;
 import java.util.ArrayList;
 import java.util.List;
 import manage.domain.pojo.DomainBean;
 import manage.equt.pojo.ReportBean;
 import manage.statistics.pojo.StatisticsResource;
 import manage.statistics.service.StatisticsService;
 import manage.user.pojo.UserInfoBean;
 import org.apache.log4j.Logger;
 
 public class ResourceReportAction
 {
   private static final Logger log = Logger.getLogger(ResourceReportAction.class);
   private UserInfoBean userInfoBean;
   private ErrorMessage errorMessage;
   private StatisticsService statisticsService;
   private StatisticsResource sta;
   private DomainBean domain;
   private String username;
   private String areano;
   private String json;
   private String json1;
 
   public String getResourceReport()
   {
     try
     {
       if (this.username != null) {
         if (this.userInfoBean == null) {
           this.userInfoBean = new UserInfoBean();
         }
         this.userInfoBean.setUsername(this.username);
         if (this.sta == null) {
           this.sta = new StatisticsResource();
         }
         if (this.domain == null) {
           this.domain = new DomainBean();
         }
         if ((this.areano != null) && (this.areano.equals(""))) {
           this.sta.setAreano(((UserInfoBean)this.userInfoBean.getUsers().get(0)).getAreano());
           this.domain.setDomainCode(((UserInfoBean)this.userInfoBean.getUsers().get(0)).getAreano());
         } else {
           this.sta.setAreano(this.areano);
           this.domain.setDomainCode(this.areano);
         }
         this.sta = this.statisticsService.getResource(this.sta);
         this.domain = this.statisticsService.getDomainOnly(this.domain);
         this.sta.setDomianName(this.domain.getDomainName());
         this.json = "[{type:'局站','总数':" + this.sta.getStationNum() + "}," + 
           "{type:'机房','总数':" + this.sta.getGeneratorNum() + "}," + 
           "{type:'设备','总数':" + this.sta.getEqutNum() + "}," + 
           "{type:'电源','总数':" + this.sta.getHFSNum() + "}," + 
           "{type:'井','总数':" + this.sta.getWellNum() + "}," + 
           "{type:'电杆','总数':" + this.sta.getPoleNum() + "}]";
 
         ReportBean report = new ReportBean();
         List list = new ArrayList();
         if (this.sta.getTotal().equals("0")) {
           report.setName("无");
           report.setNum(Integer.valueOf(1));
           list.add(report);
         } else {
           if (this.sta.getStationNum().intValue() != 0) {
             report.setName("局站");
             report.setNum(this.sta.getStationNum());
             list.add(report);
             report = new ReportBean();
           }
           if (this.sta.getGeneratorNum().intValue() != 0) {
             report.setName("机房");
             report.setNum(this.sta.getGeneratorNum());
             list.add(report);
             report = new ReportBean();
           }
           if (this.sta.getEqutNum().intValue() != 0) {
             report.setName("设备");
             report.setNum(this.sta.getEqutNum());
             list.add(report);
             report = new ReportBean();
           }
           if (this.sta.getHFSNum().intValue() != 0) {
             report.setName("电源");
             report.setNum(this.sta.getHFSNum());
             list.add(report);
             report = new ReportBean();
           }
           if (this.sta.getWellNum().intValue() != 0) {
             report.setName("井");
             report.setNum(this.sta.getWellNum());
             list.add(report);
             report = new ReportBean();
           }
           if (this.sta.getPoleNum().intValue() != 0) {
             report.setName("电杆");
             report.setNum(this.sta.getPoleNum());
             list.add(report);
             report = new ReportBean();
           }
         }
         Gson gson = new Gson();
         this.json1 = gson.toJson(list);
       }
       return "error";
     }
     catch (Exception e) {
       log.error("WorkOrderAction.loadJumPoint", e);
       this.errorMessage = new ErrorMessage();
       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
       return "error";
     }
   }
 
   public UserInfoBean getUserInfoBean()
   {
     return this.userInfoBean;
   }
 
   public void setUserInfoBean(UserInfoBean userInfoBean) {
     this.userInfoBean = userInfoBean;
   }
 
   public ErrorMessage getErrorMessage() {
     return this.errorMessage;
   }
 
   public void setErrorMessage(ErrorMessage errorMessage) {
     this.errorMessage = errorMessage;
   }
 
   public StatisticsService getStatisticsService() {
     return this.statisticsService;
   }
 
   public void setStatisticsService(StatisticsService statisticsService) {
     this.statisticsService = statisticsService;
   }
 
 
   public StatisticsResource getSta() {
     return this.sta;
   }
 
   public void setSta(StatisticsResource sta) {
     this.sta = sta;
   }
 
   public String getUsername() {
     return this.username;
   }
 
   public void setUsername(String username) {
     this.username = username;
   }
 
   public String getJson() {
     return this.json;
   }
 
   public void setJson(String json) {
     this.json = json;
   }
 
   public String getJson1() {
     return this.json1;
   }
 
   public void setJson1(String json1) {
     this.json1 = json1;
   }
 
   public DomainBean getDomain() {
     return this.domain;
   }
 
   public void setDomain(DomainBean domain) {
     this.domain = domain;
   }
 
   public String getAreano() {
     return this.areano;
   }
 
   public void setAreano(String areano) {
     this.areano = areano;
   }
 }

