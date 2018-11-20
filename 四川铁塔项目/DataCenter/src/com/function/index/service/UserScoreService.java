package com.function.index.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import com.function.index.model.UserScore;
@Repository("userScoreService")
public class UserScoreService{
	
	@Autowired
	private HibernateTemplate ht;
	
	/*
	 * 	新增Model
	 * 
	 * */
	public Integer insertModel(UserScore userScore){
		try{
			Integer id = (Integer)ht.save(userScore);
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
	public void updateModel(UserScore userScore){
		ht.update(userScore);
	}
}
