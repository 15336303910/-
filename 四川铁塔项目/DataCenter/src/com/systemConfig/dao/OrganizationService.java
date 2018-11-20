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
import com.systemConfig.interfaces.IOrganizationInterface;
import com.systemConfig.model.SystemOrganization;
@Repository("organizationService")
public class OrganizationService implements IOrganizationInterface{

	@Override
	public Integer insertOrganization(SystemOrganization systemOrganization){
		Integer id = (Integer)ht.save(systemOrganization);
		return id;
	}

	@Override
	public boolean deleteObject(Integer id){
		SystemOrganization thisOrgan = (SystemOrganization)ht.get(SystemOrganization.class,id);
		if(thisOrgan!=null){
			ht.delete(thisOrgan);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean updateObject(SystemOrganization systemOrganization){
		if(systemOrganization!=null){
			ht.update(systemOrganization);
			return true;
		}else{
			return false;
		}	
	}

	@Override
	public SystemOrganization getObject(Integer id){
		SystemOrganization thisOrgan = (SystemOrganization)ht.get(SystemOrganization.class,id);
		return thisOrgan;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SystemOrganization> getOrgansByHql(String hql){
		List<SystemOrganization> organs = (List<SystemOrganization>)ht.find(hql);
		return organs;
	}

	@Override
	public Integer getCount(HashMap<String, Object> conditions){
		String SQL = "select count(*) from SystemOrganization";
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
	public List<SystemOrganization> getOrgansPage(HashMap<String, Object> conditions){
		String SQL = "from SystemOrganization";
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
		List<SystemOrganization> organizations = (List<SystemOrganization>)ht.executeFind(new  HibernateCallback(){
			public Object doInHibernate(Session session)throws HibernateException{
				Query query = session.createQuery(finalSQL);
				query.setFirstResult(iDisplayStart);
				query.setMaxResults(iDisplayLength);
				List list = query.list();
				return list;
			}
		});
		return organizations;
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
