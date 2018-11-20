package com.function.dbmanage.service;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.function.dbmanage.model.BasicDb;
import com.function.dbmanage.model.BasicDbTable;
@Repository("basicDbService")
public class BasicDbService{

	@Autowired
	private HibernateTemplate ht;
	
	@Autowired
	private BasicDbTableService basicDbTableService;
	
	/*
	 * 	新增Model
	 * 
	 * */
	public Integer insertModel(BasicDb basicDb){
		try{
			Integer id = (Integer)ht.save(basicDb);
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
		BasicDb basicDb = ht.get(BasicDb.class,id);
		if(basicDb!=null){
			/*删除数据源下包含的表*/
			List<BasicDbTable> tables = basicDbTableService.selectModelsByHql("from BasicDbTable where BELONG_DB = "+id);
			if(tables.size()>0){
				for(int i=0;i<tables.size();i++){
					basicDbTableService.deleteModel(tables.get(i).getID());
				}
			}
			/*删除数据源*/
			ht.delete(basicDb);
		}
	}

	/*
	 * 	修改Model
	 * 
	 * */
	public void updateModel(BasicDb basicDb){
		ht.update(basicDb);
	}
	
	/*
	 * 	根据编号查找Model
	 * 
	 * */
	public BasicDb selectModel(Integer id){
		BasicDb basicDb = ht.get(BasicDb.class,id);
		return basicDb;
	}
	
	/*
	 * 	根据Hql查找
	 * 	
	 * */
	@SuppressWarnings("unchecked")
	public List<BasicDb> selectModelsByHql(String hql){
		List<BasicDb> dbList = ht.find(hql);
		return dbList;
	}
	
	/*
	 * 	根据条件查找记录总数
	 * 
	 * */
	public Integer getCount(HashMap<String, Object> conditions){
		String SQL = "select count(*) from BasicDb where ID != -1";
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
		final String finalSQL = SQL;
		Integer count = (Integer)ht.find(finalSQL).listIterator().next();
		return count;
	}
	
	/*
	 * 	根据条件查找记录：分页
	 * 
	 * */
	@SuppressWarnings({"unchecked","rawtypes"})
	public List<BasicDb> getDbPage(HashMap<String, Object> conditions){
		String SQL = "from BasicDb where ID != -1";
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
		List<BasicDb> dbPage = (List<BasicDb>)ht.executeFind(new  HibernateCallback(){
			public Object doInHibernate(Session session)throws HibernateException{
				Query query = session.createQuery(finalSQL);
				query.setFirstResult(iDisplayStart);
				query.setMaxResults(iDisplayLength);
				List list = query.list();
				return list;
			}
		});
		return dbPage;
	}
}
