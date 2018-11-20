package interfaces.pdainterface.generator.service.impl;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.TextUtil;
import interfaces.pdainterface.generator.service.PDAGeneratorService;

import java.util.List;

import manage.equt.pojo.EqutInfoBean;
import manage.generator.pojo.GeneratorInfoBean;
import manage.generator.pojo.HighFrequencySwitchingPowerSupplyInfoBean;
import manage.generator.pojo.StationBaseInfoBean;

public class PDAGeneratorServiceImpl extends DataBase
  implements PDAGeneratorService
{
  private static final String GET_GENERATOR = "pdagenerator.getGenerator";
  private static final String UPDATE_GENERATOR = "pdagenerator.updateGenerator";
  private static final String INSERT_GENERATOR = "pdagenerator.insertGenerator";
  private static final String GET_STATIONBASE = "pdagenerator.getStationBase";
  private static final String UPDATE_STATIONBASE = "pdagenerator.updateStationBase";
  private static final String INSERT_STATIONBASE = "pdagenerator.insertStationBase";
  private static final String GET_HIGHFREQUENCYSWITCHINGPOWERSUPPLY = "pdagenerator.getHighFrequencySwitchingPowerSupply";
  private static final String UPDATE_HIGHFREQUENCYSWITCHINGPOWERSUPPLY = "pdagenerator.updateHighFrequencySwitchingPowerSupply";
  private static final String INSERT_HIGHFREQUENCYSWITCHINGPOWERSUPPLY = "pdagenerator.insertHighFrequencySwitchingPowerSupply";
  private static final String GET_EQUT = "pdaequt.getEqut";

  public List<GeneratorInfoBean> getGenerator(GeneratorInfoBean generator)
    throws DataAccessException
  {
	if(TextUtil.isNotNull(generator.getGeneratorName())){
		String gName = generator.getGeneratorName().trim();
		if(gName.contains(" ")){
			gName= gName.replaceAll(" +", "%");
		}
		generator.setGeneratorName("%"+gName+"%");
	}
	
	if(TextUtil.isNotNull(generator.getRegion()) && generator.getRegion().contains("*")) {
	     String[] areas = generator.getRegion().split("\\*");
	     String sql = "";
	     for(String area : areas) {
	       sql +=" instr(region, '"+area+"') > 0 or";
	     }
	     if(TextUtil.isNotNull(sql) && sql.endsWith("or")) {
	       sql = sql.substring(0,sql.length()-2);
	       generator.setRegion(null);
	       generator.setExtendSql(sql);
	     }
	   }
    return getObjects("pdagenerator.getGenerator", generator);
  }
  
  /**
   * 根据实体对象得到数据
   * @param generator
   * @return
   */
  public GeneratorInfoBean getGeneratorObj(GeneratorInfoBean generator){
	  return (GeneratorInfoBean) getObject("pdagenerator.getGenerator", generator);
  }

  public int insertGenerator(GeneratorInfoBean generator) throws DataAccessException
  {
    return ((Integer)insert("pdagenerator.insertGenerator", generator)).intValue();
  }

  public int updateGenerator(GeneratorInfoBean generator) throws DataAccessException
  {
    return update("pdagenerator.updateGenerator", generator);
  }

  public List<StationBaseInfoBean> getStation(StationBaseInfoBean station)
    throws DataAccessException
  {
	if(TextUtil.isNotNull(station.getStationName())){
		String siteName = station.getStationName().trim();
		if(siteName.contains(" ")){
			siteName = siteName.replaceAll(" +", "%");
		}
		station.setStationName("%"+siteName+"%");
		
	}
	//站点的扩展语句
	if(TextUtil.isNotNull(station.getRegion()) && station.getRegion().contains("*")) {
	     String[] areas = station.getRegion().split("\\*");
	     String sql = "";
	     for(String area : areas) {
	       sql +=" instr(region, '"+area+"') > 0 or";
	     }
	     if(TextUtil.isNotNull(sql) && sql.endsWith("or")) {
	       sql = sql.substring(0,sql.length()-2);
	       station.setRegion(null);
	       station.setExtendsSql(sql);
	     }
	   }
    return getObjects("pdagenerator.getStationBase", station);
  }
  
  /**
   * 得到数据库中的数据
   * @param station
   * @return
   */
  public StationBaseInfoBean getStationObj(StationBaseInfoBean station){
	  return (StationBaseInfoBean) getObject("pdagenerator.getStationBase", station);
  }

  public int insertStation(StationBaseInfoBean station)
    throws DataAccessException
  {
    return ((Integer)insert("pdagenerator.insertStationBase", station)).intValue();
  }

  public int updateStation(StationBaseInfoBean station)
    throws DataAccessException
  {
    return update("pdagenerator.updateStationBase", station);
  }

  public List<HighFrequencySwitchingPowerSupplyInfoBean> getPower(HighFrequencySwitchingPowerSupplyInfoBean power)
    throws DataAccessException
  {
    return getObjects("pdagenerator.getHighFrequencySwitchingPowerSupply", power);
  }

  public int insertPower(HighFrequencySwitchingPowerSupplyInfoBean power)
    throws DataAccessException
  {
    boolean canInsert = true;
    if ((power.getFixedAssetsCode() != null) && (!(power.getFixedAssetsCode().equals("")))) {
      EqutInfoBean zcequt = new EqutInfoBean();
      zcequt = (EqutInfoBean)getObject("pdaequt.getEqut", zcequt);
      if (zcequt != null) {
        canInsert = false;
      }
      if (canInsert) {
        HighFrequencySwitchingPowerSupplyInfoBean powerch = new HighFrequencySwitchingPowerSupplyInfoBean();
        powerch.setFixedAssetsCode(power.getFixedAssetsCode());
        powerch = (HighFrequencySwitchingPowerSupplyInfoBean)getObject("pdagenerator.getHighFrequencySwitchingPowerSupply", powerch);
        if (powerch != null)
          canInsert = false;
      }
    }
    if (canInsert) {
      return ((Integer)insert("pdagenerator.insertHighFrequencySwitchingPowerSupply", power)).intValue();
    }else{
    	return -1;
    }
  }

  public int updatePower(HighFrequencySwitchingPowerSupplyInfoBean power)
    throws DataAccessException
  {
    HighFrequencySwitchingPowerSupplyInfoBean op = new HighFrequencySwitchingPowerSupplyInfoBean();
    op.setSwitchingId(power.getSwitchingId());
    op = (HighFrequencySwitchingPowerSupplyInfoBean)getObject("pdagenerator.getHighFrequencySwitchingPowerSupply", op);

    boolean canEdit = true;
    if ((power.getFixedAssetsCode() != null) && (!(power.getFixedAssetsCode().equals(""))) && (!(power.getFixedAssetsCode().equals(op.getFixedAssetsCode())))) {
      EqutInfoBean zcequt = new EqutInfoBean();
      zcequt = (EqutInfoBean)getObject("pdaequt.getEqut", zcequt);
      if (zcequt != null) {
        canEdit = false;
      }
      if (canEdit) {
        HighFrequencySwitchingPowerSupplyInfoBean powerch = new HighFrequencySwitchingPowerSupplyInfoBean();
        powerch.setFixedAssetsCode(power.getFixedAssetsCode());
        powerch = (HighFrequencySwitchingPowerSupplyInfoBean)getObject("pdagenerator.getHighFrequencySwitchingPowerSupply", powerch);
        if (powerch != null)
          canEdit = false;
      }
    }
    if (canEdit) {
      update("pdagenerator.updateHighFrequencySwitchingPowerSupply", power);
      return 0;
    }else{
    	return 1;
    }
  }
}