package com.function.index.service;
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
import com.function.index.model.IndexDetail;
@Repository("indexDetailService")
public class IndexDetailService{
	
	@Autowired
	private HibernateTemplate ht;
	
	/*
	 * 	新增Model
	 * 
	 * */
	public Integer insertModel(IndexDetail indexDetail){
		try{
			Integer id = (Integer)ht.save(indexDetail);
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
		IndexDetail indexDetail = ht.get(IndexDetail.class,id);
		if(indexDetail!=null){
			ht.delete(indexDetail);
		}
	}
	
	/*
	 * 	修改Model
	 * 
	 * */
	public void updateModel(IndexDetail indexDetail){
		ht.update(indexDetail);
	}
	
	/*
	 * 	查询Model
	 * 
	 * */
	public IndexDetail selectOne(Integer indexId){
		return ht.get(IndexDetail.class,indexId);
	}
	
	/*
	 * 	根据条件查找记录总数
	 * 
	 * */
	public Integer getCount(HashMap<String, Object> conditions){
		String SQL = "select count(*) from IndexDetail where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					if("INDEX_NAME".equals(columnName)){
						SQL+=" and "+columnName+" like '%"+columnValu+"%'";
					}else if("EXPECT_IDS".equals(columnName)){
						SQL+=" and ID not in ("+columnValu+")";					
					}else if("IN_IDS".equals(columnName)){
						SQL+=" and ID in ("+columnValu+")";					
					}else{
						SQL+=" and "+columnName+" = "+columnValu;
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
	public List<IndexDetail> getDbPage(HashMap<String, Object> conditions){
		String SQL = "from IndexDetail where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					if("INDEX_NAME".equals(columnName)){
						SQL+=" and "+columnName+" like '%"+columnValu+"%'";
					}else if("EXPECT_IDS".equals(columnName)){
						SQL+=" and ID not in ("+columnValu+")";					
					}else if("IN_IDS".equals(columnName)){
						SQL+=" and ID in ("+columnValu+")";					
					}else{
						SQL+=" and "+columnName+" = "+columnValu;
					}
				}
			}
		}
		final Integer iDisplayStart = Integer.parseInt(conditions.get("iDisplayStart").toString());
		final Integer iDisplayLength = Integer.parseInt(conditions.get("iDisplayLength").toString()); 
		final String finalSQL = SQL+" order by ID asc";
		List<IndexDetail> dbPage = (List<IndexDetail>)ht.executeFind(new  HibernateCallback(){
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
