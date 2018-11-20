package com.function.rules.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import com.function.rules.model.RuleJobData;
@Repository("jobDataService")
public class JobDataService{

	@Autowired
	private HibernateTemplate ht;
	
	/*
	 * 	新增Model
	 * 
	 * */
	public Integer insertModel(RuleJobData ruleJobData){
		try{
			Integer id = (Integer)ht.save(ruleJobData);
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
	public List<RuleJobData> selectModelsByHql(String hql){
		List<RuleJobData> jobDatas = ht.find(hql);
		return jobDatas;
	}
}
