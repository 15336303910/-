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
import com.function.rules.model.RuleImport;
@Repository("ruleImportService")
public class RuleImportService{
	
	@Autowired
	private HibernateTemplate ht;
	
	/*
	 * 	新增Model
	 * 
	 * */
	public Integer insertModel(RuleImport ruleImport){
		try{
			Integer id = (Integer)ht.save(ruleImport);
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
		RuleImport ruleImport = ht.get(RuleImport.class,id);
		if(ruleImport!=null){
			ht.delete(ruleImport);
		}
	}

	/*
	 * 	修改Model
	 * 
	 * */
	public void updateModel(RuleImport ruleImport){
		ht.update(ruleImport);
	}
	
	/*
	 * 	根据编号查找Model
	 * 
	 * */
	public RuleImport selectModel(Integer id){
		RuleImport ruleImport = ht.get(RuleImport.class,id);
		return ruleImport;
	}
	
	/*
	 * 	根据Hql查找
	 * 	
	 * */
	@SuppressWarnings("unchecked")
	public List<RuleImport> selectModelsByHql(String hql){
		List<RuleImport> dbList = ht.find(hql);
		return dbList;
	}
	
	/*
	 * 	根据条件查找记录总数
	 * 
	 * */
	public Integer getCount(HashMap<String, Object> conditions){
		String SQL = "select count(*) from RuleImport where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName) && !"".equals(columnValu)){
					if("RULE_NAME".equals(columnName)){
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
	public List<RuleImport> getDbPage(HashMap<String, Object> conditions){
		String SQL = "from RuleImport where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName) && !"".equals(columnValu)){
					if("RULE_NAME".equals(columnName)){
						SQL+=" and "+columnName+" like '%"+columnValu+"%'";
					}
				}
			}
		}
		final Integer iDisplayStart = Integer.parseInt(conditions.get("iDisplayStart").toString());
		final Integer iDisplayLength = Integer.parseInt(conditions.get("iDisplayLength").toString()); 
		final String finalSQL = SQL+" order by ID asc";
		List<RuleImport> dbPage = (List<RuleImport>)ht.executeFind(new  HibernateCallback(){
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
