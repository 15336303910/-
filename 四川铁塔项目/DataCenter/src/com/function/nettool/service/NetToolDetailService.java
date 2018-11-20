package com.function.nettool.service;
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
import com.function.nettool.model.NetToolDetail;
@Repository("netToolDetailService")
public class NetToolDetailService{
	
	@Autowired
	private HibernateTemplate ht;
	
	/*
	 * 	新增Model
	 * 
	 * */
	public Integer insertModel(NetToolDetail netToolDetail){
		try{
			Integer id = (Integer)ht.save(netToolDetail);
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
		NetToolDetail netToolDetail = ht.get(NetToolDetail.class,id);
		if(netToolDetail!=null){
			ht.delete(netToolDetail);
		}
	}
	
	/*
	 * 	修改Model
	 * 
	 * */
	public void updateModel(NetToolDetail netToolDetail){
		ht.update(netToolDetail);
	}
	
	/*
	 * 	查询Model
	 * 
	 * */
	public NetToolDetail selectOne(Integer toolDetailId){
		return ht.get(NetToolDetail.class,toolDetailId);
	}
	
	/*
	 * 	查询Models
	 * 
	 * */
	public List<NetToolDetail> selectSome(String hql){
		return ht.find(hql);
	}
	
	/*
	 * 	根据条件查找记录总数
	 * 
	 * */
	public Integer getCount(HashMap<String, Object> conditions){
		String SQL = "select count(*) from NetToolDetail where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName) && !"".equals(columnValu)){
					SQL+=" and "+columnName+" like '%"+columnValu+"%'";
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
	public List<NetToolDetail> getDbPage(HashMap<String, Object> conditions){
		String SQL = "from NetToolDetail where 1 = 1 ";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName) && !"".equals(columnValu)){
					SQL+=" and "+columnName+" like '%"+columnValu+"%'";
				}
			}
		}		
		SQL+=" order by ID asc";
		final String finalSQL = SQL;
		final Integer iDisplayStart = Integer.parseInt(conditions.get("iDisplayStart").toString());
		final Integer iDisplayLength = Integer.parseInt(conditions.get("iDisplayLength").toString()); 
		List<NetToolDetail> dbPage = (List<NetToolDetail>)ht.executeFind(new  HibernateCallback(){
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
