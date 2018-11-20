package manage.statistics.service;

import base.exceptions.DataAccessException;
import java.util.List;
import manage.domain.pojo.DomainBean;
import manage.statistics.pojo.StatisticsReasource;
import manage.statistics.pojo.StatisticsResource;
import manage.user.pojo.UserInfoBean;

public abstract interface StatisticsService
{
  public abstract List<DomainBean> getDomain(DomainBean paramDomainBean)
    throws DataAccessException;

  public abstract DomainBean getDomainOnly(DomainBean paramDomainBean)
    throws DataAccessException;

  public abstract StatisticsResource getstatistics(StatisticsResource paramStatisticsResource)
    throws DataAccessException;

  public abstract StatisticsResource getResource(StatisticsResource paramStatisticsResource)
    throws DataAccessException;

  public abstract StatisticsReasource getstatisticsByUser(StatisticsReasource paramStatisticsReasource)
    throws DataAccessException;

  public abstract List<UserInfoBean> getUser(DomainBean paramDomainBean)
    throws DataAccessException;
}

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.statistics.service.StatisticsService
 * JD-Core Version:    0.5.3
 */