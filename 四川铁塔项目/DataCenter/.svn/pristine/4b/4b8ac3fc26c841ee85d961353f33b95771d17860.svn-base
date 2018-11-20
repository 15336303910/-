package com.systemConfig.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import com.systemConfig.interfaces.IRoleMenuInterface;
import com.systemConfig.model.RoleMenu;
@Repository("roleMenuService")
public class RoleMenuService implements IRoleMenuInterface{
	
	@Autowired
	private HibernateTemplate ht;

	@Override
	public void insertModel(RoleMenu roleMenu) {
		ht.save(roleMenu);
	}

	public HibernateTemplate getHt() {
		return ht;
	}

	public void setHt(HibernateTemplate ht) {
		this.ht = ht;
	}
}
