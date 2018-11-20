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
import com.function.rules.model.RuleDetail;
@Repository("ruleDetailService")
public class RuleDetailService{

	@Autowired
	private HibernateTemplate ht;
	
	/*
	 * 	新增Model
	 * 
	 * */
	public Integer insertModel(RuleDetail ruleDetail){
		try{
			Integer id = (Integer)ht.save(ruleDetail);
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
		RuleDetail ruleDetail = ht.get(RuleDetail.class,id);
		if(ruleDetail!=null){
			ht.delete(ruleDetail);
		}
	}

	/*
	 * 	修改Model
	 * 
	 * */
	public void updateModel(RuleDetail ruleDetail){
		ht.update(ruleDetail);
	}
	
	/*
	 * 	根据编号查找Model
	 * 
	 * */
	public RuleDetail selectModel(Integer id){
		RuleDetail ruleDetail = ht.get(RuleDetail.class,id);
		return ruleDetail;
	}
	
	/*
	 * 	根据Hql查找
	 * 	
	 * */
	@SuppressWarnings("unchecked")
	public List<RuleDetail> selectModelsByHql(String hql){
		List<RuleDetail> dbList = ht.find(hql);
		return dbList;
	}
	
	/*
	 * 	根据条件查找记录总数
	 * 
	 * */
	public Integer getCount(HashMap<String, Object> conditions){
		String SQL = "select count(*) from RuleDetail where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					if("BIND_TABLE".equals(columnName) || "CREATE_USER".equals(columnName)){
						SQL+=" and "+columnName+" = "+columnValu;
					}else if("EXPECT_IDS".equals(columnName)){
						SQL+=" and ID not in ("+columnValu+")";
					}else if("BELONG_INDEX".equals(columnName)){
						SQL+=" and ID in ("+columnValu+")";
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
	public List<RuleDetail> getDbPage(HashMap<String, Object> conditions){
		String SQL = "from RuleDetail where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					if("BIND_TABLE".equals(columnName) || "CREATE_USER".equals(columnName)){
						SQL+=" and "+columnName+" = "+columnValu;
					}else if("EXPECT_IDS".equals(columnName)){
						SQL+=" and ID not in ("+columnValu+")";
					}else if("BELONG_INDEX".equals(columnName)){
						SQL+=" and ID in ("+columnValu+")";
					}else{
						SQL+=" and "+columnName+" like '%"+columnValu+"%'";
					}
				}
			}
		}
		final Integer iDisplayStart = Integer.parseInt(conditions.get("iDisplayStart").toString());
		final Integer iDisplayLength = Integer.parseInt(conditions.get("iDisplayLength").toString()); 
		final String finalSQL = SQL+" order by ID asc";
		List<RuleDetail> dbPage = (List<RuleDetail>)ht.executeFind(new  HibernateCallback(){
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
