package com.function.rules.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import com.function.rules.model.RuleJobItem;
@Repository("jobItemService")
public class JobItemService{
	
	@Autowired
	private HibernateTemplate ht;
	
	/*
	 * 	新增Model
	 * 
	 * */
	public Integer insertModel(RuleJobItem ruleJobItem){
		try{
			Integer id = (Integer)ht.save(ruleJobItem);
			return id;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * 	根据Hql查找
	 * 	
	 * */
	@SuppressWarnings("unchecked")
	public List<RuleJobItem> selectModelsByHql(String hql){
		List<RuleJobItem> jobItems = ht.find(hql);
		return jobItems;
	}
}
