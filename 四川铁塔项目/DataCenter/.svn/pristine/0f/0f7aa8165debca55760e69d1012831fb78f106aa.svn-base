package com.function.rules.service;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.function.dbmanage.model.BasicColumn;
import com.function.rules.model.RuleHistory;
@Repository("ruleHistoryService")
public class RuleHistoryService{

	@Autowired
	private HibernateTemplate ht;
	
	/*
	 * 	新增Model
	 * 
	 * */
	public Integer insertModel(RuleHistory ruleHistory){
		try{
			Integer id = (Integer)ht.save(ruleHistory);
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
	@SuppressWarnings("unchecked")
	public void deleteFromHql(String hql){
		List<RuleHistory> ruleHistorys = ht.find(hql);
		if(ruleHistorys.size()>0){
			for(int i=0;i<ruleHistorys.size();i++){
				ht.delete(ruleHistorys.get(i));
			}
		}
	}
	
	/*
	 * 	根据Hql查找
	 * 	
	 * */
	@SuppressWarnings("unchecked")
	public List<RuleHistory> selectModelsByHql(String hql){
		List<RuleHistory> dataList = ht.find(hql);
		return dataList;
	}
	
	/*
	 * 	根据Hql查找
	 * 	
	 * */
	@SuppressWarnings("unchecked")
	public List<RuleHistory> selectModelsPage(final String hql,final Integer start,final Integer limit){		
		List<RuleHistory> dataList = (List<RuleHistory>)ht.executeFind(new  HibernateCallback(){
			@SuppressWarnings("rawtypes")
			public Object doInHibernate(Session session)throws HibernateException{
				Query query = session.createQuery(hql);
				query.setFirstResult(start);
				query.setMaxResults(limit);
				List list = query.list();
				return list;
			}
		});
		
		
		return dataList;
	}
}
