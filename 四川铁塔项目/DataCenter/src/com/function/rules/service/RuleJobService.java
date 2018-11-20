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
import com.function.rules.model.RuleJob;
@Repository("ruleJobService")
public class RuleJobService{

	@Autowired
	private HibernateTemplate ht;
	
	/*
	 * 	新增Model
	 * 
	 * */
	public Integer insertModel(RuleJob ruleJob){
		try{
			Integer id = (Integer)ht.save(ruleJob);
			return id;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * 	修改Model
	 * 
	 * */
	public void updateModel(RuleJob ruleJob){
		ht.update(ruleJob);
	}
	
	/*
	 * 	根据id查找
	 * 	
	 * */
	public RuleJob selectModel(Integer id){
		RuleJob ruleJob = ht.get(RuleJob.class,id);
		return ruleJob;
	}
	
	/*
	 * 	根据Hql查找
	 * 	
	 * */
	@SuppressWarnings("unchecked")
	public List<RuleJob> selectModelsByHql(String hql){
		List<RuleJob> ruleJobs = ht.find(hql);
		return ruleJobs;
	}
	
	/*
	 * 	根据Hql查找
	 * 	
	 * */
	@SuppressWarnings("unchecked")
	public RuleJob selectModelByHql(String hql){
		List<RuleJob> ruleJobs = ht.find(hql);
		if(ruleJobs.size()>0){
			return ruleJobs.get(0);
		}else{
			return null;
		}
	}
	
	/*
	 * 	根据Hql删除
	 * 	
	 * */
	@SuppressWarnings("unchecked")
	public void deleteFromHql(String hql){
		List<RuleJob> ruleJobs = ht.find(hql);
		if(ruleJobs.size()>0){
			for(int i=0;i<ruleJobs.size();i++){
				ht.delete(ruleJobs.get(i));
			}
		}
	}	
	
	/*
	 * 	根据条件查找记录总数
	 * 
	 * */
	public Integer getCount(HashMap<String, Object> conditions){
		String SQL = "select count(*) from RuleJob where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					SQL+=" and "+columnName+" = '"+columnValu+"'";
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
	public List<RuleJob> getDbPage(HashMap<String, Object> conditions){
		String SQL = "from RuleJob where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					SQL+=" and "+columnName+" = '"+columnValu+"'";
				}
			}
		}
		final Integer iDisplayStart = Integer.parseInt(conditions.get("iDisplayStart").toString());
		final Integer iDisplayLength = Integer.parseInt(conditions.get("iDisplayLength").toString()); 
		final String finalSQL = SQL+" order by IS_FINISHED asc,START_TIME desc";
		List<RuleJob> dbPage = (List<RuleJob>)ht.executeFind(new  HibernateCallback(){
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
