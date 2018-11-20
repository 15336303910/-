package com.function.dbmanage.service;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.function.dbmanage.model.BasicColumn;
@Repository("basicColumnService")
public class BasicColumnService{

	@Autowired
	private HibernateTemplate ht;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*
	 * 	新增Model
	 * 
	 * */
	public Integer insertModel(BasicColumn basicColumn){
		try{
			Integer id = (Integer)ht.save(basicColumn);
			return id;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * 	删除Column
	 * 
	 * */
	public void deleteModel(Integer id){
		/*删除用于核查此Column的规则*/
		jdbcTemplate.execute("DELETE FROM RULE_ITEMS_CONNECT WHERE BELONG_ITEM IN(SELECT ID FROM RULE_ITEMS WHERE COLUMN_ID = "+id+")");
		jdbcTemplate.execute("DELETE FROM RULE_ITEMS WHERE COLUMN_ID = "+id);	
		/*删除此Column*/
		BasicColumn basicColumn = ht.get(BasicColumn.class,id);
		if(basicColumn!=null){
			ht.delete(basicColumn);
		}
	}
	
	/*
	 * 	根据hql删除Models
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public void deleteModelsFromHql(String hql){
		List<BasicColumn> basicColumns = ht.find(hql);
		if(basicColumns!=null){
			for(int i=0;i<basicColumns.size();i++){
				BasicColumn basicColumn = basicColumns.get(i);
				ht.delete(basicColumn);
			}
		}
	}

	/*
	 * 	修改Model
	 * 
	 * */
	public void updateModel(BasicColumn basicColumn){
		ht.update(basicColumn);
	}
	
	/*
	 * 	根据编号查找Model
	 * 
	 * */
	public BasicColumn selectModel(Integer id){
		BasicColumn basicColumn = ht.get(BasicColumn.class,id);
		return basicColumn;
	}
	
	/*
	 * 	根据Hql查找
	 * 	
	 * */
	@SuppressWarnings("unchecked")
	public List<BasicColumn> selectModelsByHql(String hql){
		List<BasicColumn> objList = ht.find(hql);
		return objList;
	}
	
	/*
	 * 	根据条件查找记录总数
	 * 
	 * */
	public Integer getCount(HashMap<String, Object> conditions){
		String SQL = "select count(*) from BasicColumn where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					if("BELONG_TABLE".equals(columnName) || "ID".equals(columnName)){
						SQL+=" and "+columnName+" IN ("+columnValu+")";
					}else if("COLUMN_ALIAS".equals(columnName)){
						SQL+=" and ";
						String[] values = columnValu.split(";");
						if(values.length>0){
							for(int i=0;i<values.length;i++){
								String $value = values[i];
								if(values.length==1){
									SQL+="(COLUMN_ALIAS like '%"+columnValu+"%' or COLUMN_NAME like '%"+$value+"%')";
								}else if(values.length>1){
									if(i==0){
										SQL+="((COLUMN_ALIAS like '%"+columnValu+"%' or COLUMN_NAME like '%"+$value+"%')";
									}else if(i==(values.length-1)){
										SQL+=" OR (COLUMN_ALIAS like '%"+columnValu+"%' or COLUMN_NAME like '%"+$value+"%'))";
									}else{
										SQL+=" OR (COLUMN_ALIAS like '%"+columnValu+"%' or COLUMN_NAME like '%"+$value+"%')";
									}						
								}						
							}
						}
					}else{
						SQL+=" and "+columnName+" like '%"+columnValu+"%'";
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
	public List<BasicColumn> getItemPage(HashMap<String, Object> conditions){
		String SQL = "from BasicColumn where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					if("BELONG_TABLE".equals(columnName) || "ID".equals(columnName)){
						SQL+=" and "+columnName+" IN ("+columnValu+")";
					}else if("COLUMN_ALIAS".equals(columnName)){
						SQL+=" and ";
						String[] values = columnValu.split(";");
						if(values.length>0){
							for(int i=0;i<values.length;i++){
								String $value = values[i];
								if(values.length==1){
									SQL+="(COLUMN_ALIAS like '%"+columnValu+"%' or COLUMN_NAME like '%"+$value+"%')";
								}else if(values.length>1){
									if(i==0){
										SQL+="((COLUMN_ALIAS like '%"+columnValu+"%' or COLUMN_NAME like '%"+$value+"%')";
									}else if(i==(values.length-1)){
										SQL+=" OR (COLUMN_ALIAS like '%"+columnValu+"%' or COLUMN_NAME like '%"+$value+"%'))";
									}else{
										SQL+=" OR (COLUMN_ALIAS like '%"+columnValu+"%' or COLUMN_NAME like '%"+$value+"%')";
									}						
								}						
							}
						}
					}else{
						SQL+=" and "+columnName+" like '%"+columnValu+"%'";
					}
				}
			}
		}
		final Integer iDisplayStart = Integer.parseInt(conditions.get("iDisplayStart").toString());
		final Integer iDisplayLength = Integer.parseInt(conditions.get("iDisplayLength").toString()); 
		final String finalSQL = SQL+" order by IS_EXPORT desc,ID asc";
		List<BasicColumn> dataPage = (List<BasicColumn>)ht.executeFind(new  HibernateCallback(){
			public Object doInHibernate(Session session)throws HibernateException{
				Query query = session.createQuery(finalSQL);
				query.setFirstResult(iDisplayStart);
				query.setMaxResults(iDisplayLength);
				List list = query.list();
				return list;
			}
		});
		return dataPage;
	}
}
