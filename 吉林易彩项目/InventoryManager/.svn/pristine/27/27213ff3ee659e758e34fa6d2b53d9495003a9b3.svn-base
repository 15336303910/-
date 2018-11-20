package manage.generator.service.impl;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import java.util.Date;
import java.util.List;
import manage.generator.pojo.GeneratorInfoBean;
import manage.generator.pojo.HighFrequencySwitchingPowerSupplyInfoBean;
import manage.generator.pojo.StationBaseInfoBean;
import manage.generator.service.GeneratorService;

public class GeneratorServiceImpl extends DataBase
  implements GeneratorService
{
  private static String GET_STATIONBASE = "generator.getStationBase";
  private static String GET_STATIONBASE_TOTAL = "generator.getStationBaseTotal";
  private static String UPDATE_STATIONBASE = "generator.updateStationBase";

  private static String GET_GENERATOR = "generator.getGenerator";
  private static String GET_GENERATOR_TOTAL = "generator.getGeneratorTotal";
  private static String UPDATE_GENERATOR = "generator.updateGenerator";
  
  public StationBaseInfoBean getStationBase(StationBaseInfoBean stationBase)
    throws DataAccessException
  {
    List list = getObjects(GET_STATIONBASE, stationBase);
    int total = getCount(GET_STATIONBASE_TOTAL, stationBase);
    stationBase = new StationBaseInfoBean();
    stationBase.setStations(list);
    stationBase.setTotal(total);
    return stationBase;
  }

  public GeneratorInfoBean getGenerator(GeneratorInfoBean generator) throws DataAccessException
  {
    List list = getObjects(GET_GENERATOR, generator);
    int total = getCount(GET_GENERATOR_TOTAL, generator);
    generator = new GeneratorInfoBean();
    generator.setGenerators(list);
    generator.setTotal(total);
    return generator;
  }

  public int updateStationBase(StationBaseInfoBean stationBase) throws DataAccessException
  {
    int i = update(UPDATE_STATIONBASE, stationBase);
    return i;
  }

  public int deleteStationBase(StationBaseInfoBean stationBase) throws DataAccessException
  {
    stationBase.setDeleteFlag("1");
    int i = update(UPDATE_STATIONBASE, stationBase);
    return i;
  }

  public int updateGenerator(GeneratorInfoBean generator) throws DataAccessException
  {
    int i = update(UPDATE_GENERATOR, generator);
    return i;
  }

  public int deleteGenerator(GeneratorInfoBean generator) throws DataAccessException
  {
    generator.setDeletedFlag("1");
    generator.setLastUpdateDate(new Date());
    int i = update(UPDATE_GENERATOR, generator);
    return i;
  }

}