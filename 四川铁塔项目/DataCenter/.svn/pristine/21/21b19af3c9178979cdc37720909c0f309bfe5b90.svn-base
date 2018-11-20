package com.function.compare.service;
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
import com.function.compare.model.CompareColumn;
@Repository("compareColumnService")
public class CompareColumnService{
	
	@Autowired
	private HibernateTemplate ht;
	
	/*
	 * 	新增Model
	 * 
	 * */
	public Integer insertModel(CompareColumn compareColumn){
		try{
			Integer id = (Integer)ht.save(compareColumn);
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
		CompareColumn compareColumn = ht.get(CompareColumn.class,id);
		if(compareColumn!=null){
			ht.delete(compareColumn);
		}
	}
	
	/*
	 * 	修改Model
	 * 
	 * */
	public void updateModel(CompareColumn compareColumn){
		ht.update(compareColumn);
	}
	
	/*
	 * 	查询Model
	 * 
	 * */
	public CompareColumn selectOne(Integer columnId){
		return ht.get(CompareColumn.class,columnId);
	}
	
	/*
	 * 	查询Models
	 * 
	 * */
	public List<CompareColumn> selectSome(String hql){
		return ht.find(hql);
	}
	
	/*
	 * 	根据条件查找记录总数
	 * 
	 * */
	public Integer getCount(HashMap<String, Object> conditions){
		String SQL = "select count(*) from CompareColumn where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					SQL+=" and "+columnName+" = "+columnValu;
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
	public List<CompareColumn> getDbPage(HashMap<String, Object> conditions){
		String SQL = "from CompareColumn where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					SQL+=" and "+columnName+" = "+columnValu;
				}
			}
		}
		final Integer iDisplayStart = Integer.parseInt(conditions.get("iDisplayStart").toString());
		final Integer iDisplayLength = Integer.parseInt(conditions.get("iDisplayLength").toString()); 
		final String finalSQL = SQL+" order by ID asc";
		List<CompareColumn> dbPage = (List<CompareColumn>)ht.executeFind(new  HibernateCallback(){
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
