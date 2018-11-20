package com.function.rules.service;
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
import com.function.rules.model.RuleItemConnect;
@Repository("ruleConnectService")
public class RuleConnectService{

	@Autowired
	private HibernateTemplate ht;
	
	/*
	 * 	新增Model
	 * 
	 * */
	public Integer insertModel(RuleItemConnect ruleItemConnect){
		try{
			Integer id = (Integer)ht.save(ruleItemConnect);
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
		RuleItemConnect ruleItemConnect = ht.get(RuleItemConnect.class,id);
		if(ruleItemConnect!=null){
			ht.delete(ruleItemConnect);
		}
	}

	/*
	 * 	修改Model
	 * 
	 * */
	public void updateModel(RuleItemConnect ruleItemConnect){
		ht.update(ruleItemConnect);
	}
	
	/*
	 * 	根据编号查找Model
	 * 
	 * */
	public RuleItemConnect selectModel(Integer id){
		RuleItemConnect ruleItemConnect = ht.get(RuleItemConnect.class,id);
		return ruleItemConnect;
	}
	
	/*
	 * 	根据Hql查找
	 * 	
	 * */
	@SuppressWarnings("unchecked")
	public List<RuleItemConnect> selectModelsByHql(String hql){
		List<RuleItemConnect> dbList = ht.find(hql);
		return dbList;
	}
	
	/*
	 * 	根据条件查找记录总数
	 * 
	 * */
	public Integer getCount(HashMap<String, Object> conditions){
		String SQL = "select count(*) from RuleItemConnect where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					if("BELONG_ITEM".equals(columnName)){
						SQL+=" and "+columnName+" = "+columnValu;
					}else{
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
	public List<RuleItemConnect> getDbPage(HashMap<String, Object> conditions){
		String SQL = "from RuleItemConnect where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					if("BELONG_ITEM".equals(columnName)){
						SQL+=" and "+columnName+" = "+columnValu;
					}else{
						SQL+=" and "+columnName+" like '%"+columnValu+"%'";
					}
				}
			}
		}
		final Integer iDisplayStart = Integer.parseInt(conditions.get("iDisplayStart").toString());
		final Integer iDisplayLength = Integer.parseInt(conditions.get("iDisplayLength").toString()); 
		final String finalSQL = SQL;
		List<RuleItemConnect> dbPage = (List<RuleItemConnect>)ht.executeFind(new  HibernateCallback(){
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
