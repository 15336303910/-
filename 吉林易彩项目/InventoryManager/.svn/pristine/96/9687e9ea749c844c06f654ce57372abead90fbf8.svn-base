package manage.generator.service;

import base.exceptions.DataAccessException;
import manage.generator.pojo.GeneratorInfoBean;
import manage.generator.pojo.HighFrequencySwitchingPowerSupplyInfoBean;
import manage.generator.pojo.StationBaseInfoBean;

public abstract interface GeneratorService
{
  public abstract StationBaseInfoBean getStationBase(StationBaseInfoBean paramStationBaseInfoBean)
    throws DataAccessException;

  public abstract int updateStationBase(StationBaseInfoBean paramStationBaseInfoBean)
    throws DataAccessException;

  public abstract int deleteStationBase(StationBaseInfoBean paramStationBaseInfoBean)
    throws DataAccessException;

  public abstract GeneratorInfoBean getGenerator(GeneratorInfoBean paramGeneratorInfoBean)
    throws DataAccessException;

  public abstract int updateGenerator(GeneratorInfoBean paramGeneratorInfoBean)
    throws DataAccessException;

  public abstract int deleteGenerator(GeneratorInfoBean paramGeneratorInfoBean)
    throws DataAccessException;
}
