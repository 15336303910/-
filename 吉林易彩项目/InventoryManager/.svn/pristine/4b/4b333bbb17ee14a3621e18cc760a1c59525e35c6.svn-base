package base.database;

import base.database.impl.BaseDaoiBatis;
import base.exceptions.DataAccessException;
import base.util.GetProperties;
import base.util.TextUtil;

import com.ibatis.sqlmap.client.SqlMapExecutor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

public class DataBase extends BaseDaoiBatis {
	public GetProperties getPropert = new GetProperties();
	
	
	public Object getObject(String statment, Object obj)
			throws DataAccessException {
		try {
			Object object = getSqlMapClientTemplate().queryForObject(statment, obj);
			return object;
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	public List getObjects(String statement, Object obj)
			throws DataAccessException {
		try {
			return getSqlMapClientTemplate().queryForList(statement, obj);
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	public List getObjects(String statement, String obj)
			throws DataAccessException {
		try {
			return getSqlMapClientTemplate().queryForList(statement, obj);
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	public Object insert(String statment, Object obj)
			throws DataAccessException {
		try {
			Object result = getSqlMapClientTemplate().insert(statment, obj);
			Integer id = (Integer) result;
			return result;
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	public int update(String statment, Object obj) throws DataAccessException {
		int ret = 0;
		try {
			ret = getSqlMapClientTemplate().update(statment, obj);
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
		return ret;
	}

	public int delete(String statment, Object obj) throws DataAccessException {
		int ret = 0;
		try {
			ret = getSqlMapClientTemplate().delete(statment, obj);
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
		return ret;
	}

	public int getCount(String statement, Object obj)
			throws DataAccessException {
		try {
			return ((Integer) getSqlMapClientTemplate().queryForObject(
					statement, obj)).intValue();
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	public List getObjects(Object statement, Object obj)
			throws DataAccessException {
		try {
			return getSqlMapClientTemplate().queryForList((String) statement,
					obj);
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	public List getObjectsByPage(String statment, Object obj, int skipResults,
			int maxResults) throws DataAccessException {
		try {
			return getSqlMapClientTemplate().queryForList(statment, obj,
					skipResults, maxResults);
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	public void batchUpdate(final String statementName, final List list) {
		try {
			if (list != null) {
				getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
					@Override
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						int i = 0;
						for (int n = list.size(); i < n; ++i) {
							executor.update(statementName, list.get(i));
						}
						executor.executeBatch();
						return null;
					}
				});
			}

		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	/** @deprecated */
	public void batchInsert(final String statementName, final List list) {
		try {
			if (list != null)
				getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						int i = 0;
						for (int n = list.size(); i < n; ++i) {
							executor.insert(statementName, list.get(i));
						}
						executor.executeBatch();
						return null;
					}
				});
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	public List batchInsertReturnIdStr(final String statementName,
			final List list) {
		try {
			List ids = new ArrayList();
			if (list != null) {
				ids = (List) getSqlMapClientTemplate().execute(
						new SqlMapClientCallback() {
							public Object doInSqlMapClient(
									SqlMapExecutor executor)
									throws SQLException {
								List ids = new ArrayList();
								executor.startBatch();
								int i = 0;
								for (int n = list.size(); i < n; ++i) {
									int id = ((Integer) executor.insert(
											statementName, list.get(i)))
											.intValue();
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
	}

	public Object insertLogData(String statment, Object obj)
			throws DataAccessException {
		try {
			Object result = getSqlMapClientTemplate().insert(statment, obj);
			return result;
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}
	
	/**
	 * 
	 * @param city
	 * @return
	 */
	public String getAreaStr(String city){
		int loc = 0 ;
		if(city.contains("省")){
			loc = city.indexOf("省");
		}else if(city.contains("市")){
			loc = city.indexOf("市");
		}else if(city.contains("县")){
			loc = city.indexOf("县");
		}else if(city.contains("区")){
			loc = city.indexOf("区");
		}else if(city.contains("自治")){
			loc = city.indexOf("自治");
		}else if(city.contains("分")){
			loc = city.indexOf("分");
		}else if(city.contains("州")){
			loc = city.indexOf("州");
		}else if(city.contains("移动")){
			loc = city.indexOf("移动");
		}else{
			loc = city.length();
		}
		String newStr = city.substring(0, loc);
		return newStr;
	}
	
	/**
	 * 
	 * @param list
	 * @param type
	 * @return
	 */
	public String getStrList(List<Map<String, Object>> list, String type){
		String result = "";
		if(TextUtil.isNotNull(list)){
			for(Map<String, Object> map : list){
				result = result + map.get(type)+",";
			}
		}
		if(result.endsWith(",")){
			result = result.substring(0, result.length() -1);
		}
		return result;
	}

	public GetProperties getGetPropert() {
		return getPropert;
	}

	public void setGetPropert(GetProperties getPropert) {
		this.getPropert = getPropert;
	}
}
