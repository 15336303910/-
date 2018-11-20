package com.function.dbmanage.service;
import java.util.*;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.function.dbmanage.model.BasicColumn;
import com.function.dbmanage.model.BasicDbTable;
@Repository("basicDbTableService")
public class BasicDbTableService{

	@Autowired
	private HibernateTemplate ht;
	
	@Autowired
	private BasicColumnService basicColumnService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*
	 * 	新增Model
	 * 
	 * */
	public Integer insertModel(BasicDbTable basicDbTable){
		try{
			Integer id = (Integer)ht.save(basicDbTable);
			return id;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * 	删除Model
	 * 
	 * */
	public void deleteModel(Integer id){
		BasicDbTable basicDbTable = ht.get(BasicDbTable.class,id);
		if(basicDbTable!=null){
			/*删除Column以及对应的Rule*/
			List<BasicColumn> basicColumns = basicColumnService.selectModelsByHql("from BasicColumn where BELONG_TABLE = "+id);
			if(basicColumns.size()>0){
				for(int i=0;i<basicColumns.size();i++){
					basicColumnService.deleteModel(basicColumns.get(i).getID());
				}
			}
			/*删除历史、调度信息、概览*/
			jdbcTemplate.execute("DELETE FROM RULE_HISTORY WHERE RULE_ID IN(SELECT ID FROM RULE_DETAIL WHERE BIND_TABLE = "+id+")");
			jdbcTemplate.execute("DELETE FROM RULE_EXEC_QUARTZ WHERE BELONG_RULE IN(SELECT ID FROM RULE_DETAIL WHERE BIND_TABLE = "+id+")");
			jdbcTemplate.execute("DELETE FROM RULE_DETAIL WHERE BIND_TABLE = "+id);
			/*删除数据表*/		
			ht.delete(basicDbTable);
		}
	}
	
	/*
	 * 	根据hql删除Models
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public void deleteModelsFromHql(String hql){
		List<BasicDbTable> basicDbTables = ht.find(hql);
		if(basicDbTables!=null && basicDbTables.size()>0){
			for(int i=0;i<basicDbTables.size();i++){
				BasicDbTable basicDbTable = basicDbTables.get(i);
				/*删除数据表包含的字段*/
				basicColumnService.deleteModelsFromHql("from BasicColumn where BELONG_TABLE = "+basicDbTable.getID());
				/*删除数据表*/
				ht.delete(basicDbTable);
			}
		}
	}

	/*
	 * 	修改Model
	 * 
	 * */
	public void updateModel(BasicDbTable basicDbTable){
		ht.update(basicDbTable);
	}
	
	/*
	 * 	根据编号查找Model
	 * 
	 * */
	public BasicDbTable selectModel(Integer id){
		BasicDbTable basicDbTable = ht.get(BasicDbTable.class,id);
		return basicDbTable;
	}
	
	/*
	 * 	根据Hql查找
	 * 	
	 * */
	@SuppressWarnings("unchecked")
	public List<BasicDbTable> selectModelsByHql(String hql){
		List<BasicDbTable> basicDbTables = ht.find(hql);
		return basicDbTables;
	}
	
	/*
	 * 	根据条件查找记录总数
	 * 
	 * */
	public Integer getCount(HashMap<String, Object> conditions){
		String SQL = "select count(*) from BasicDbTable where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					if("BELONG_DB".equals(columnName)){
						SQL+=" and "+columnName+" = "+columnValu;
					}else{
						if(!"".equals(columnValu)){
							SQL+=" and "+columnName+" like '%"+columnValu+"%'";
						}
					}
				}
			}
		}		
		final String finalSQL = SQL;
		Integer count = (Integer)ht.find(finalSQL).listIterator().next();
		return count;
	}
	
	/*
	 * 	根据条件查找记录：不分页
	 * 
	 * */
	@SuppressWarnings({"unchecked"})
	public List<BasicDbTable> getTableUnPage(HashMap<String, Object> conditions){
		String SQL = "from BasicDbTable where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					if("BELONG_DB".equals(columnName)){
						SQL+=" and "+columnName+" = "+columnValu;
					}else{
						if(!"".equals(columnValu)){
							SQL+=" and "+columnName+" like '%"+columnValu+"%'";
						}
					}
				}
			}
		}
		List<BasicDbTable> tablePage = ht.find(SQL);
		return tablePage;
	}
	
	/*
	 * 	根据条件查找记录：分页
	 * 
	 * */
	@SuppressWarnings({"unchecked","rawtypes"})
	public List<BasicDbTable> getTablePage(HashMap<String, Object> conditions){
		String SQL = "from BasicDbTable where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					SQL+=" and "+columnName+" like '%"+columnValu+"%'";
				}
			}
		}
		final Integer iDisplayStart = Integer.parseInt(conditions.get("iDisplayStart").toString());
		final Integer iDisplayLength = Integer.parseInt(conditions.get("iDisplayLength").toString()); 
		final String finalSQL = SQL;
		List<BasicDbTable> tablePage = (List<BasicDbTable>)ht.executeFind(new  HibernateCallback(){
			public Object doInHibernate(Session session)throws HibernateException{
				Query query = session.createQuery(finalSQL);
				query.setFirstResult(iDisplayStart);
				query.setMaxResults(iDisplayLength);
				List list = query.list();
				return list;
			}
		});
		return tablePage;
	}
}
