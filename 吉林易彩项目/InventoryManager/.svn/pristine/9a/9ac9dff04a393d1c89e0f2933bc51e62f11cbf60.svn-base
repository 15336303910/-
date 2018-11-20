 package manage.statistics.service.impl;
 
 import base.database.DataBase;
 import base.exceptions.DataAccessException;
 import java.text.DecimalFormat;
 import java.util.ArrayList;
 import java.util.List;
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
 
 public class StatisticsServiceImpl extends DataBase
   implements StatisticsService
 {
   private static final String GET_DOMAIN = "statistics.getDomain";
   private static final String GET_DOMAIN_ONLY = "statistics.getDomainOnly";
   private static final String GET_DOMAIN_OWN = "statistics.getDomainOwn";
   private static final String GET_POLE = "statistics.getPole";
   private static final String GET_WELL = "statistics.getWell";
   private static final String GET_HFS = "statistics.getHighFrequencySwitchingPowerSupply";
   private static final String GET_GENERATOR = "statistics.getGenerator";
   private static final String GET_EQUT = "statistics.getEqut";
   private static final String GET_STATION = "statistics.getStationBase";
   private static final String GET_RESOURCE_NODE = "statistics.getResourceNode";
   private static final String GET_USER = "statistics.getUser";
 
   public List<DomainBean> getDomain(DomainBean domain)
     throws DataAccessException
   {
     List list = getObjects("statistics.getDomain", domain);
     DomainBean d = (DomainBean)getObject("statistics.getDomainOwn", domain);
     list.add(0, d);
     return list;
   }
 
   public DomainBean getDomainOnly(DomainBean domain) throws DataAccessException {
     domain = (DomainBean)getObject("statistics.getDomainOnly", domain);
 
     return domain;
   }
 
   public StatisticsResource getstatistics(StatisticsResource sta) throws DataAccessException
   {
     List list = new ArrayList();
     StationBaseInfoBean station = new StationBaseInfoBean();
     GeneratorInfoBean generator = new GeneratorInfoBean();
     EqutInfoBean equt = new EqutInfoBean();
     HighFrequencySwitchingPowerSupplyInfoBean HFS = new HighFrequencySwitchingPowerSupplyInfoBean();
     WellInfoBean well = new WellInfoBean();
     PoleInfoBean pole = new PoleInfoBean();
 
     int a = getCount("statistics.getStationBase", station);
     generator.setAreano(sta.getAreano());
     int s = getCount("statistics.getGenerator", generator);
     equt.setAreano(sta.getAreano());
     int d = getCount("statistics.getEqut", equt);
     HFS.setAreano(sta.getAreano());
     int f = getCount("statistics.getHighFrequencySwitchingPowerSupply", HFS);
     well.setAreano(sta.getAreano());
     int g = getCount("statistics.getWell", well);
     pole.setAreano(sta.getAreano());
     int h = getCount("statistics.getPole", pole);
     int total = a + s + d + f + g + h;
 
     DecimalFormat df = new DecimalFormat("0.00");
 
     sta = new StatisticsResource();
     sta.setTotal(total+"");
     if (total != 0) {
       sta.setStationNum(Integer.valueOf(a));
       sta.setStationPer(df.format((a + 0.0D) / (total + 0.0D) * 100.0D));
       sta.setGeneratorNum(Integer.valueOf(s));
       sta.setGeneratorPer(df.format((s + 0.0D) / (total + 0.0D) * 100.0D));
       sta.setEqutNum(Integer.valueOf(d));
       sta.setEqutPer(df.format((d + 0.0D) / (total + 0.0D) * 100.0D));
       sta.setHFSNum(Integer.valueOf(f));
       sta.setHFSPer(df.format((f + 0.0D) / (total + 0.0D) * 100.0D));
       sta.setWellNum(Integer.valueOf(g));
       sta.setWellPer(df.format((g + 0.0D) / (total + 0.0D) * 100.0D));
       sta.setPoleNum(Integer.valueOf(h));
       sta.setPolePer(df.format((h + 0.0D) / (total + 0.0D) * 100.0D));
     } else {
       sta.setStationNum(Integer.valueOf(0));
       sta.setStationPer("0");
       sta.setGeneratorNum(Integer.valueOf(0));
       sta.setGeneratorPer("0");
       sta.setEqutNum(Integer.valueOf(0));
       sta.setEqutPer("0");
       sta.setHFSNum(Integer.valueOf(0));
       sta.setHFSPer("0");
       sta.setWellNum(Integer.valueOf(0));
       sta.setWellPer("0");
       sta.setPoleNum(Integer.valueOf(0));
       sta.setPolePer("0");
     }
     return sta;
   }
 
   public StatisticsResource getResource(StatisticsResource sta)
     throws DataAccessException
   {
     StationBaseInfoBean station = new StationBaseInfoBean();
     GeneratorInfoBean generator = new GeneratorInfoBean();
     EqutInfoBean equt = new EqutInfoBean();
     HighFrequencySwitchingPowerSupplyInfoBean HFS = new HighFrequencySwitchingPowerSupplyInfoBean();
     WellInfoBean well = new WellInfoBean();
     PoleInfoBean pole = new PoleInfoBean();
 
     int a = getCount("statistics.getStationBase", station);
     generator.setAreano(sta.getAreano());
     int s = getCount("statistics.getGenerator", generator);
     equt.setAreano(sta.getAreano());
     int d = getCount("statistics.getEqut", equt);
     HFS.setAreano(sta.getAreano());
     int f = getCount("statistics.getHighFrequencySwitchingPowerSupply", HFS);
     well.setAreano(sta.getAreano());
     int g = getCount("statistics.getWell", well);
     pole.setAreano(sta.getAreano());
     int h = getCount("statistics.getPole", pole);
 
     int total = a + s + d + f + g + h;
 
     sta = new StatisticsResource();
     sta.setTotal(total+"");
     if (total != 0) {
       sta.setStationNum(Integer.valueOf(a));
       sta.setGeneratorNum(Integer.valueOf(s));
       sta.setEqutNum(Integer.valueOf(d));
       sta.setHFSNum(Integer.valueOf(f));
       sta.setWellNum(Integer.valueOf(g));
       sta.setPoleNum(Integer.valueOf(h));
     } else {
       sta.setStationNum(Integer.valueOf(0));
       sta.setGeneratorNum(Integer.valueOf(0));
       sta.setEqutNum(Integer.valueOf(0));
       sta.setHFSNum(Integer.valueOf(0));
       sta.setWellNum(Integer.valueOf(0));
       sta.setPoleNum(Integer.valueOf(0));
     }
     return sta;
   }
 
   public StatisticsReasource getstatisticsByUser(StatisticsReasource sta) throws DataAccessException
   {
 
     return null;
   }
 
   public List<UserInfoBean> getUser(DomainBean domain) throws DataAccessException
   {
     return getObjects("statistics.getUser", domain);
   }
   }
