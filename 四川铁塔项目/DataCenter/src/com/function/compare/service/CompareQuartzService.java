package com.function.compare.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import com.function.compare.model.CompareQuartz;
@Repository("compareQuartzService")
public class CompareQuartzService{
	
	@Autowired
	private HibernateTemplate ht;
	
	/*
	 * 	新增Model
	 * 
	 * */
	public Integer insertModel(CompareQuartz compareQuartz){
		try{
			Integer id = (Integer)ht.save(compareQuartz);
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
		CompareQuartz compareQuartz = ht.get(CompareQuartz.class,id);
		if(compareQuartz!=null){
			ht.delete(compareQuartz);
		}
	}

	/*
	 * 	修改Model
	 * 
	 * */
	public void updateModel(CompareQuartz compareQuartz){
		ht.update(compareQuartz);
	}
	
	/*
	 * 	根据编号查找Model
	 * 
	 * */
	public CompareQuartz selectModel(Integer id){
		CompareQuartz compareQuartz = ht.get(CompareQuartz.class,id);
		return compareQuartz;
	}
	
	/*
	 * 	根据Hql查找
	 * 	
	 * */
	@SuppressWarnings("unchecked")
	public CompareQuartz selectModelByHql(String hql){
		List<CompareQuartz> compareQuartzs = ht.find(hql);
		if(compareQuartzs.size()>0){
			return compareQuartzs.get(0);
		}else{
			return null;
		}
	}
}
