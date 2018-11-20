package com.systemConfig.dao;
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
import com.systemConfig.interfaces.ISystemUserInterface;
import com.systemConfig.model.SystemUser;
@Repository("systemUserService")
public class SystemUserService implements ISystemUserInterface{

	@Override
	public Integer insertModel(SystemUser systemUser){
		Integer newCode = (Integer)ht.save(systemUser); 
		return newCode;
	}
	
	@Override
	public void deleteModel(Integer userCode){
		SystemUser systemUser = ht.get(SystemUser.class,userCode);
		if(systemUser!=null){
			ht.delete(systemUser);
		}
	}
	
	@Override
	public void updateModel(SystemUser systemUser){
		ht.update(systemUser);
	}

	@Override
	public SystemUser findOneObject(Integer userCode){
		SystemUser systemUser = ht.get(SystemUser.class,userCode);
		return systemUser;
	}

	@Override
	public Integer getCount(String hql){
		List<SystemUser> systemUsers = (List<SystemUser>)ht.find(hql);
		return systemUsers.size();
	}
	
	@Override
	public List<SystemUser> getUsersByHql(String hql){
		List<SystemUser> systemUsers = (List<SystemUser>)ht.find(hql);
		return systemUsers;
	}
	
	@Override
	public Integer findCount(HashMap<String, Object> conditions){
		String SQL = "select count(*) from SystemUser where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					if(columnName.equals("USER_NAME")){
						SQL+=" AND (USER_NAME like '%"+columnValu+"%' or EMPLOYEE_NAME like '%"+columnValu+"%')";	
					}else{
						SQL+=" AND "+columnName+" LIKE '%"+columnValu+"%'";
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
	public List<SystemUser> findMenus(HashMap<String, Object> conditions){
		String SQL = "from SystemUser where ID != -1";
		if(conditions!=null && conditions.size()>0){
			Iterator<String> iterator = conditions.keySet().iterator();
			while(iterator.hasNext()){
				String columnName = iterator.next();
				String columnValu = conditions.get(columnName).toString();
				if(!"iDisplayStart".equals(columnName) && !"iDisplayLength".equals(columnName)){
					if(columnName.equals("USER_NAME")){
						SQL+=" AND (USER_NAME like '%"+columnValu+"%' or EMPLOYEE_NAME like '%"+columnValu+"%')";	
					}else{
						SQL+=" AND "+columnName+" LIKE '%"+columnValu+"%'";
					}		
				}
			}
		}
		final Integer iDisplayStart = Integer.parseInt(conditions.get("iDisplayStart").toString());
		final Integer iDisplayLength = Integer.parseInt(conditions.get("iDisplayLength").toString()); 
		final String finalSQL = SQL;
		List<SystemUser> systemUsers = (List<SystemUser>)ht.executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session)throws HibernateException{
				Query query = session.createQuery(finalSQL);
				query.setFirstResult(iDisplayStart);
				query.setMaxResults(iDisplayLength);
				List list = query.list();
				return list;
			}
		});
		return systemUsers;
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
