package manage.statistics.web;

import base.util.ErrorMessage;
import base.web.PaginationAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import manage.domain.pojo.DomainBean;
import manage.equt.pojo.EqutInfoBean;
import manage.generator.pojo.GeneratorInfoBean;
import manage.generator.pojo.HighFrequencySwitchingPowerSupplyInfoBean;
import manage.generator.pojo.StationBaseInfoBean;
import manage.pipe.pojo.WellInfoBean;
import manage.poleline.pojo.PoleInfoBean;
import manage.statistics.pojo.StatisticsReasource;
import manage.statistics.pojo.StatisticsResource;
import manage.statistics.service.StatisticsService;
import manage.user.pojo.UserInfoBean;
import org.apache.log4j.Logger;

public class StatisticsAction extends PaginationAction
{
  private static final long serialVersionUID = 4596680992133578990L;
  private UserInfoBean userInfoBean;
  private ErrorMessage errorMessage;
  private StatisticsService statisticsService;
  private DomainBean domain;
  private PoleInfoBean poleInfoBean;
  private EqutInfoBean equtInfoBean;
  private WellInfoBean wellInfoBean;
  private GeneratorInfoBean generator;
  private StationBaseInfoBean station;
  private HighFrequencySwitchingPowerSupplyInfoBean HFS;
  private StatisticsResource sta;
  private StatisticsReasource staBack;
  private String str;
  private Integer domainId;
  private String times;
  private String timee;
  private Integer limit;
  private Integer start;

  public String statisticsReasource()
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if (this.domain == null) {
        this.domain = new DomainBean();
        this.domain.setDomainId(this.userInfoBean.getDomainid());
      }
      List <DomainBean>list = this.statisticsService.getDomain(this.domain);
      List ids = new ArrayList();
      for (DomainBean d : list) {
        this.sta = new StatisticsResource();
        this.sta.setAreano(d.getDomainCode());
        this.sta = this.statisticsService.getstatistics(this.sta);
        String s = "['" + d.getDomainName() + "'," + this.sta.getTotal() + "," + this.sta.getStationNum() + "," + this.sta.getStationPer() + "," + 
          this.sta.getGeneratorNum() + "," + this.sta.getGeneratorPer() + "," + this.sta.getWellNum() + "," + this.sta.getWellPer() + "," + this.sta.getPoleNum() + "," + this.sta.getPolePer() + "]";
        ids.add(s);
      }
      this.str = ids.toString();
    }
    catch (Exception e) {
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }

    return "statisticsReasource";
  }

  public String statisticsReasourceByUser() {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if (this.domain == null) {
        this.domain = new DomainBean();
        this.domain.setDomainId(this.userInfoBean.getDomainid());
      }
      List<UserInfoBean> list = this.statisticsService.getUser(this.domain);
      List ids = new ArrayList();
      Date times = null;
      Date timee = null;
      if ((this.times != null) && (!(this.times.equals(""))) && (!(this.times.equals("null")))) {
        times = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz", Locale.US).parse(this.times);
      }
      if ((this.timee != null) && (!(this.timee.equals(""))) && (!(this.timee.equals("null")))) {
        timee = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz", Locale.US).parse(this.timee);
        long t = timee.getTime();
        t = t + 86400000L - 1000L;
        timee = new Date(t);
      }
      for (UserInfoBean u : list) {
        this.staBack = new StatisticsReasource();
        this.staBack.setUsername(u.getUsername());
        this.staBack.setTimes(times);
        this.staBack.setTimee(timee);
        this.staBack = this.statisticsService.getstatisticsByUser(this.staBack);
        String s = "['" + u.getRealname() + "'," + this.staBack.getTotal() + "," + this.staBack.getTotalCheck() + "," + this.staBack.getTotalUnCheck() + "," + 
          this.staBack.getStationNum() + "," + this.staBack.getStationNumCheck() + "," + this.staBack.getStationNumUnCheck() + "," + this.staBack.getStationPer() + "," + 
          this.staBack.getGeneratorNum() + "," + this.staBack.getGeneratorNumCheck() + "," + this.staBack.getGeneratorNumUnCheck() + "," + this.staBack.getGeneratorPer() + "," + 
          this.staBack.getWellNum() + "," + this.staBack.getWellNumCheck() + "," + this.staBack.getWellNumUnCheck() + "," + this.staBack.getWellPer() + "," + 
          this.staBack.getPoleNum() + "," + this.staBack.getPoleNumCheck() + "," + this.staBack.getPoleNumUnCheck() + "," + this.staBack.getPolePer() + "]";
        ids.add(s);
      }
      this.str = ids.toString();
    }
    catch (Exception e) {
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }

    return "statisticsReasource";
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

  public DomainBean getDomain() {
    return this.domain;
  }

  public void setDomain(DomainBean domain) {
    this.domain = domain;
  }

  public PoleInfoBean getPoleInfoBean() {
    return this.poleInfoBean;
  }

  public void setPoleInfoBean(PoleInfoBean poleInfoBean) {
    this.poleInfoBean = poleInfoBean;
  }

  public EqutInfoBean getEqutInfoBean() {
    return this.equtInfoBean;
  }

  public void setEqutInfoBean(EqutInfoBean equtInfoBean) {
    this.equtInfoBean = equtInfoBean;
  }

  public WellInfoBean getWellInfoBean() {
    return this.wellInfoBean;
  }

  public void setWellInfoBean(WellInfoBean wellInfoBean) {
    this.wellInfoBean = wellInfoBean;
  }

  public GeneratorInfoBean getGenerator() {
    return this.generator;
  }

  public void setGenerator(GeneratorInfoBean generator) {
    this.generator = generator;
  }

  public StationBaseInfoBean getStation() {
    return this.station;
  }

  public void setStation(StationBaseInfoBean station) {
    this.station = station;
  }

  public HighFrequencySwitchingPowerSupplyInfoBean getHFS() {
    return this.HFS;
  }

  public void setHFS(HighFrequencySwitchingPowerSupplyInfoBean hFS) {
    this.HFS = hFS;
  }


  public StatisticsResource getSta() {
    return this.sta;
  }

  public void setSta(StatisticsResource sta) {
    this.sta = sta;
  }

  public String getStr() {
    return this.str;
  }

  public void setStr(String str)
  {
    this.str = str;
  }

  public StatisticsReasource getStaBack() {
    return this.staBack;
  }

  public void setStaBack(StatisticsReasource staBack) {
    this.staBack = staBack;
  }

  public Integer getDomainId() {
    return this.domainId;
  }

  public void setDomainId(Integer domainId) {
    this.domainId = domainId;
  }

  public String getTimes() {
    return this.times;
  }

  public void setTimes(String times) {
    this.times = times;
  }

  public String getTimee() {
    return this.timee;
  }

  public void setTimee(String timee) {
    this.timee = timee;
  }

  public static long getSerialversionuid() {
    return 4596680992133578990L;
  }
}