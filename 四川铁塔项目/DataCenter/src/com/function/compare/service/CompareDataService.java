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
import com.function.compare.model.CompareData;
@Repository("compareDataService")
public class CompareDataService{
	
	@Autowired
	private HibernateTemplate ht;
	
	/*
	 * 	根据条件查找记录总数
	 * 
	 * */
	public Integer getCount(HashMap<String, Object> conditions){
		String SQL = "select count(*) from CompareData where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					if("BELONG_MONITOR".equals(columnName)){
						SQL+=" and "+columnName+" = "+columnValu;
					}else if("PROBLEM_DESC".equals(columnName)){
						SQL+=" and "+columnName+" = '"+columnValu+"'";
					}else if("PROBLEM_TYPE".equals(columnName) || "RESOURCE_NAME".equals(columnName) || "DATA_CITY".equals(columnName)){
						SQL+=" and "+columnName+" like '%"+columnValu+"%'";
					}
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
	public List<CompareData> getDbPage(HashMap<String, Object> conditions){
		String SQL = "from CompareData where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					if("BELONG_MONITOR".equals(columnName)){
						SQL+=" and "+columnName+" = "+columnValu;
					}else if("PROBLEM_DESC".equals(columnName)){
						SQL+=" and "+columnName+" = '"+columnValu+"'";
					}else if("PROBLEM_TYPE".equals(columnName) || "RESOURCE_NAME".equals(columnName) || "DATA_CITY".equals(columnName)){
						SQL+=" and "+columnName+" like '%"+columnValu+"%'";
					}
				}
			}
		}
		final Integer iDisplayStart = Integer.parseInt(conditions.get("iDisplayStart").toString());
		final Integer iDisplayLength = Integer.parseInt(conditions.get("iDisplayLength").toString()); 
		final String finalSQL = SQL+" order by ID asc";
		List<CompareData> dbPage = (List<CompareData>)ht.executeFind(new  HibernateCallback(){
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
