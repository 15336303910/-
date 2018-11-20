package base.V2.hebei.database;

import base.V2.hebei.database.impl.BaseDaoiBatis4HB;
import base.exceptions.DataAccessException;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.springframework.orm.ibatis.SqlMapClientCallback;

public class DataBase4HB extends BaseDaoiBatis4HB
{
	protected SqlMapClient sqlMapClient2;
	

	public SqlMapClient getSqlMapClient2() {
		return sqlMapClient2;
	}

	public void setSqlMapClient2(SqlMapClient sqlMapClient2) {
		this.sqlMapClient2 = sqlMapClient2;
	}

  public Object getObject(String statment, Object obj)
    throws DataAccessException
  {
    try
    {
      return sqlMapClient2.queryForObject(statment, obj);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public List getObjects(String statement, Object obj) throws DataAccessException {
    try {
      return sqlMapClient2.queryForList(statement, obj);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public List getObjects(String statement, String obj) throws DataAccessException {
    try {
      return sqlMapClient2.queryForList(statement, obj);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public Object insert(String statment, Object obj)
    throws DataAccessException
  {
    try
    {
      Object result = sqlMapClient2.insert(statment, obj);
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
      ret = sqlMapClient2.update(statment, obj);
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
      ret = sqlMapClient2.delete(statment, obj);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
    return ret;
  }

  public int getCount(String statement, Object obj) throws DataAccessException {
    try {
      return ((Integer)sqlMapClient2.queryForObject(statement, obj)).intValue();
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public List getObjects(Object statement, Object obj) throws DataAccessException {
    try {
      return sqlMapClient2.queryForList((String)statement, obj);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public List getObjectsByPage(String statment, Object obj, int skipResults, int maxResults)
    throws DataAccessException
  {
    try
    {
      return sqlMapClient2.queryForList(statment, obj, skipResults, maxResults);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  /*public void batchUpdate(final String statementName,final List list)
  {
    try
    {
      if (list != null){
    	  sqlMapClient2.execute(new SqlMapClientCallback() {
			@Override
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				executor.startBatch();
	            int i = 0; 
	            for (int n =list.size(); i < n; ++i) {
	              executor.update(statementName, list.get(i));
	            }
	            executor.executeBatch(); 
				return null;
			}
		});
      }
        
    }
    catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  *//** @deprecated *//*
  public void batchInsert(final String statementName, final List list)
  {
    try
    {
      if (list != null)
        sqlMapClient2.execute(new SqlMapClientCallback() {
          public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
            executor.startBatch();
            int i = 0; for (int n = list.size(); i < n; ++i) {
              executor.insert(statementName, list.get(i));
            }
            executor.executeBatch();
            return null;
          }
        });
    }
    catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public List batchInsertReturnIdStr(final String statementName, final List list)
  {
    try
    {
      List ids = new ArrayList();
      if (list != null) {
        ids = (List)sqlMapClient2.execute(new SqlMapClientCallback() {
          public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
            List ids = new ArrayList();
            executor.startBatch();
            int i = 0; for (int n = list.size(); i < n; ++i) {
              int id = ((Integer)executor.insert(statementName, list.get(i))).intValue();
              LogUtil.addLog(Integer.valueOf(id), list.get(i));
              ids.add(Integer.valueOf(id));
            }
            executor.executeBatch();
            return ids;
          }
        });
      }
      return ids;
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }*/

  public Object insertLogData(String statment, Object obj)
    throws DataAccessException
  {
    try
    {
      Object result = sqlMapClient2.insert(statment, obj);
      return result;
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }
}
