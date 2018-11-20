package interfaces.pdainterface.generator.service;

import base.exceptions.DataAccessException;
import java.util.List;
import manage.generator.pojo.GeneratorInfoBean;
import manage.generator.pojo.HighFrequencySwitchingPowerSupplyInfoBean;
import manage.generator.pojo.StationBaseInfoBean;

public abstract interface PDAGeneratorService
{
  public abstract List<GeneratorInfoBean> getGenerator(GeneratorInfoBean paramGeneratorInfoBean)
    throws DataAccessException;

  public abstract int updateGenerator(GeneratorInfoBean paramGeneratorInfoBean)
    throws DataAccessException;

  public abstract int insertGenerator(GeneratorInfoBean paramGeneratorInfoBean)
    throws DataAccessException;

  public abstract List<StationBaseInfoBean> getStation(StationBaseInfoBean paramStationBaseInfoBean)
    throws DataAccessException;

  public abstract int insertStation(StationBaseInfoBean paramStationBaseInfoBean)
    throws DataAccessException;

  public abstract int updateStation(StationBaseInfoBean paramStationBaseInfoBean)
    throws DataAccessException;

  public abstract List<HighFrequencySwitchingPowerSupplyInfoBean> getPower(HighFrequencySwitchingPowerSupplyInfoBean paramHighFrequencySwitchingPowerSupplyInfoBean)
    throws DataAccessException;

  public abstract int insertPower(HighFrequencySwitchingPowerSupplyInfoBean paramHighFrequencySwitchingPowerSupplyInfoBean)
    throws DataAccessException;
  /**
   * 根据实体对象得到数据
   * @param generator
   * @return
   */
  public GeneratorInfoBean getGeneratorObj(GeneratorInfoBean generator);
  
  /**
   * 得到数据库中的数据
   * @param station
   * @return
   */
  public StationBaseInfoBean getStationObj(StationBaseInfoBean station);
  
  public abstract int updatePower(HighFrequencySwitchingPowerSupplyInfoBean paramHighFrequencySwitchingPowerSupplyInfoBean)
    throws DataAccessException;
}