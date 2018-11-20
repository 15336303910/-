package com.function.rules.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import com.function.rules.model.RuleQuartz;
@Repository("ruleQuartzService")
public class RuleQuartzService{
	
	@Autowired
	private HibernateTemplate ht;
	
	/*
	 * 	新增Model
	 * 
	 * */
	public Integer insertModel(RuleQuartz ruleQuartz){
		try{
			Integer id = (Integer)ht.save(ruleQuartz);
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
		RuleQuartz ruleQuartz = ht.get(RuleQuartz.class,id);
		if(ruleQuartz!=null){
			ht.delete(ruleQuartz);
		}
	}

	/*
	 * 	修改Model
	 * 
	 * */
	public void updateModel(RuleQuartz ruleQuartz){
		ht.update(ruleQuartz);
	}
	
	/*
	 * 	根据编号查找Model
	 * 
	 * */
	public RuleQuartz selectModel(Integer id){
		RuleQuartz ruleQuartz = ht.get(RuleQuartz.class,id);
		return ruleQuartz;
	}
	
	/*
	 * 	根据Hql查找
	 * 	
	 * */
	@SuppressWarnings("unchecked")
	public RuleQuartz selectModelByHql(String hql){
		List<RuleQuartz> quartzList = ht.find(hql);
		if(quartzList.size()>0){
			return quartzList.get(0);
		}else{
			return null;
		}
	}
}
