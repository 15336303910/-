package com.function.index.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.function.index.model.IndexItem;
@Repository("indexItemService")
public class IndexItemService{
	
	@Autowired
	private HibernateTemplate ht;
	
	/*
	 * 	新增Model
	 * 
	 * */
	public Integer insertModel(IndexItem indexItem){
		try{
			Integer id = (Integer)ht.save(indexItem);
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
		IndexItem indexItem = ht.get(IndexItem.class,id);
		if(indexItem!=null){
			ht.delete(indexItem);
		}
	}
	
	/*
	 * 	根据hql执行查询
	 * 
	 * */
	public IndexItem queryOneByHql(String hql){
		List<IndexItem> indexItems = ht.find(hql);
		if(indexItems.size()>0){
			return indexItems.get(0);
		}else{
			return null;
		}
	}
}
