package com.systemConfig.dao;
import java.sql.SQLException;
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

import com.systemConfig.interfaces.ISystemMenuInterface;
import com.systemConfig.model.SystemMenu;
@Repository("systemMenuService")
public class SystemMenuService implements ISystemMenuInterface{

	@Override
	public Integer insertModel(SystemMenu systemMenu) {
		Integer newCode = (Integer)ht.save(systemMenu); 
		return newCode;
	}
	
	@Override
	public void updateModel(SystemMenu systemMenu){
		ht.update(systemMenu);
	}
	
	@Override
	public SystemMenu findOneMenu(Integer menuCode){
		SystemMenu systemMenu = ht.get(SystemMenu.class,menuCode);
		return systemMenu;
	}

	@Override
	public Integer findCount(HashMap<String, Object> conditions) {
		String SQL = "select count(*) from SystemMenu where MENU_NAME != '根目录'";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					SQL+=" AND "+columnName+" LIKE '%"+columnValu+"%'";			
				}
			}
		}		
		final String finalSQL = SQL;
		Integer count = (Integer)ht.find(finalSQL).listIterator().next();
		return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SystemMenu> findByHql(String hql){
		List<SystemMenu> systemMenus = (List<SystemMenu>)ht.find(hql);
		return systemMenus;
	}
	
	@SuppressWarnings({"unchecked","rawtypes", "deprecation"})
	@Override
	public List<SystemMenu> findMenus(HashMap<String, Object> conditions) {
		String SQL = "from SystemMenu where MENU_NAME != '根目录'";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					SQL+=" and "+columnName+" like '%"+columnValu+"%'";			
				}
			}
		}
		final Integer iDisplayStart = Integer.parseInt(conditions.get("iDisplayStart").toString());
		final Integer iDisplayLength = Integer.parseInt(conditions.get("iDisplayLength").toString()); 
		final String finalSQL = SQL;
		List<SystemMenu> systemMenus = (List<SystemMenu>)ht.executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session)throws HibernateException{
				Query query = session.createQuery(finalSQL);
				query.setFirstResult(iDisplayStart);
				query.setMaxResults(iDisplayLength);
				List list = query.list();
				return list;
			}
		});
		return systemMenus;
	}
	
	@Autowired
	private HibernateTemplate ht;
	
	public HibernateTemplate getHt() {
		return ht;
	}
	
	public void setHt(HibernateTemplate ht) {
		this.ht = ht;
	}
}
