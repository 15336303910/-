package base.database.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 执行批量操作
 * @author chenqp
 *
 */
public class MyBatchUpdate{

	//执行的操作
	JdbcTemplate jdbctemplate;
	//执行语句
	String sql;
	//执行数据
	List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	//参数个数
	int size;
	
	/**
	 * 初始化数据
	 * @param jdbctemplate
	 * @param sql
	 * @param list
	 * @param size
	 */
	public MyBatchUpdate(JdbcTemplate jdbctemplate,String sql,List<Map<String, Object>> list,int size){
		this.jdbctemplate = jdbctemplate;
		this.sql = sql;
		this.list = list;
		this.size = size;
	}
	
	/**
	 * 执行
	 */
	public void execute(){
		this.jdbctemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Map<String, Object> map = list.get(i);
				for(int j=1;j<=size;j++){
					if(this.isInteger(map.get(j+"").toString())){
						ps.setInt(j, Integer.parseInt(map.get(j+"").toString()));
					}else{
						ps.setString(j, map.get(j+"").toString());
					}
				}
			}
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return list.size();
			}
			
			boolean isInteger(String value) {
				try {
					Integer.parseInt(value);
					return true;
				} catch (NumberFormatException e) {
					return false;
				}
			}
		});
	}
}
