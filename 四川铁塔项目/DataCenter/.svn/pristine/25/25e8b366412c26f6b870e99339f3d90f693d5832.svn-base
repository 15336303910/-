package com.function.index.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.function.index.model.IndexUser;
@Repository("indexUserService")
public class IndexUserService{
	
	@Autowired
	private HibernateTemplate ht;
	
	/*
	 * 	新增Model
	 * 
	 * */
	public Integer insertModel(IndexUser indexUser){
		try{
			Integer id = (Integer)ht.save(indexUser);
			return id;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * 	根据hql查询
	 * 
	 * */
	public IndexUser queryOneByHql(String hql){
		try{
			List<IndexUser> indexUsers = ht.find(hql);
			if(indexUsers.size()>0){
				return indexUsers.get(0);
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
