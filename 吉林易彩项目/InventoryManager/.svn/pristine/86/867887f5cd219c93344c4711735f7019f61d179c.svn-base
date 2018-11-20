package manage.domain.service;

import base.exceptions.DataAccessException;
import java.util.List;
import manage.domain.pojo.DomainBean;
import manage.tree.pojo.Tree;
import manage.user.pojo.UserInfoBean;

public abstract interface DomainService
{
  public abstract List<Tree> getDomianTree(DomainBean paramDomainBean)
    throws DataAccessException;

  public abstract List<Tree> getDomianTree2(DomainBean paramDomainBean)
    throws DataAccessException;

  public abstract List<Tree> getDomainAndEquitTree(DomainBean paramDomainBean)
    throws DataAccessException;

  public abstract int getVerifyDomain(DomainBean paramDomainBean)
    throws DataAccessException;

  public abstract int insertNewDomain(DomainBean paramDomainBean, UserInfoBean paramUserInfoBean)
    throws DataAccessException;

  public abstract int insertNewdomain(DomainBean paramDomainBean)
    throws DataAccessException;

  public abstract DomainBean getLoadDomain(DomainBean paramDomainBean)
    throws DataAccessException;

  public abstract List<Integer> deleteDomain(DomainBean paramDomainBean)
    throws DataAccessException;

  public abstract UserInfoBean getResponsibleUser(DomainBean paramDomainBean)
    throws DataAccessException;

  public abstract int updateDomain(DomainBean paramDomainBean)
    throws DataAccessException;

  public abstract List<Tree> findDomianTree(DomainBean paramDomainBean)
    throws DataAccessException;

  public abstract List<Tree> findEqutDomianTree(DomainBean paramDomainBean)
    throws DataAccessException;

  public abstract DomainBean getDomain(DomainBean paramDomainBean)
    throws DataAccessException;
}

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.domain.service.DomainService
 * JD-Core Version:    0.5.3
 */