package base.database;

import java.util.List;

import base.database.impl.BaseIrmsDaoiBatis;
import base.exceptions.DataAccessException;

import com.ibatis.sqlmap.client.SqlMapClient;

public class IrmsDataBase extends BaseIrmsDaoiBatis{
protected SqlMapClient irmsSqlMapClient;
	

  public SqlMapClient getIrmsSqlMapClient() {
	return irmsSqlMapClient;
  }

  public void setIrmsSqlMapClient(SqlMapClient irmsSqlMapClient) {
	this.irmsSqlMapClient = irmsSqlMapClient;
  }

public Object getObject(String statment, Object obj)
    throws DataAccessException
  {
    try
    {
      return irmsSqlMapClient.queryForObject(statment, obj);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public List getObjects(String statement, Object obj) throws DataAccessException {
    try {
      return irmsSqlMapClient.queryForList(statement, obj);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public List getObjects(String statement, String obj) throws DataAccessException {
    try {
      return irmsSqlMapClient.queryForList(statement, obj);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public Object insert(String statment, Object obj)
    throws DataAccessException
  {
    try
    {
      Object result = irmsSqlMapClient.insert(statment, obj);
      Integer id = (Integer)result;
      return result;
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public int update(String statment, Object obj)
    throws DataAccessException
  {
    int ret = 0;
    try {
      ret = irmsSqlMapClient.update(statment, obj);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
    return ret;
  }

  public int delete(String statment, Object obj)
    throws DataAccessException
  {
    int ret = 0;
    try {
      ret = irmsSqlMapClient.delete(statment, obj);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
    return ret;
  }

  public int getCount(String statement, Object obj) throws DataAccessException {
    try {
      return ((Integer)irmsSqlMapClient.queryForObject(statement, obj)).intValue();
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public List getObjects(Object statement, Object obj) throws DataAccessException {
    try {
      return irmsSqlMapClient.queryForList((String)statement, obj);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public List getObjectsByPage(String statment, Object obj, int skipResults, int maxResults)
    throws DataAccessException
  {
    try
    {
      return irmsSqlMapClient.queryForList(statment, obj, skipResults, maxResults);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }


  public Object insertLogData(String statment, Object obj)
    throws DataAccessException
  {
    try
    {
      Object result = irmsSqlMapClient.insert(statment, obj);
      return result;
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }
}
