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

import com.systemConfig.interfaces.IRoleInterface;
import com.systemConfig.model.Role;
@Repository("roleService")
public class RoleService implements IRoleInterface{

	@Autowired
	private HibernateTemplate ht;
	
	public HibernateTemplate getHt() {
		return ht;
	}

	public void setHt(HibernateTemplate ht) {
		this.ht = ht;
	}

	@Override
	public Integer insertRole(Role role) {
		Integer id = (Integer)ht.save(role);
		return id;
	}

	@Override
	public boolean deleteRole(Integer id){
		Role thisRole = (Role)ht.get(Role.class,id);
		if(thisRole!=null){
			ht.delete(thisRole);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean updateRole(Role role){
		if(role!=null){
			ht.update(role);
			return true;
		}else{
			return false;
		}		
	}

	@Override
	public Role getRole(Integer id) {
		Role thisRole = (Role)ht.get(Role.class,id);
		return thisRole;
	}
	
	@Override
	public Integer getCount(HashMap<String, Object> conditions) {
		String SQL = "select count(*) from Role";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					if(SQL.indexOf("where")==-1){
						SQL+=" where "+columnName+" like '%"+columnValu+"%'";
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

	@SuppressWarnings({"unchecked","rawtypes", "deprecation"})
	@Override
	public List<Role> getRolesPage(HashMap<String, Object> conditions){
		String SQL = "from Role";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					if(SQL.indexOf("where")==-1){
						SQL+=" where "+columnName+" like '%"+columnValu+"%'";
					}else{
						SQL+=" and "+columnName+" like '%"+columnValu+"%'";
					}			
				}
			}
		}		
		final Integer iDisplayStart = Integer.parseInt(conditions.get("iDisplayStart").toString());
		final Integer iDisplayLength = Integer.parseInt(conditions.get("iDisplayLength").toString()); 
		final String finalSQL = SQL;
		List<Role> roles = (List<Role>)ht.executeFind(new  HibernateCallback(){
			public Object doInHibernate(Session session)throws HibernateException{
				Query query = session.createQuery(finalSQL);
				query.setFirstResult(iDisplayStart);
				query.setMaxResults(iDisplayLength);
				List list = query.list();
				return list;
			}
		});
		return roles;
	}
}
