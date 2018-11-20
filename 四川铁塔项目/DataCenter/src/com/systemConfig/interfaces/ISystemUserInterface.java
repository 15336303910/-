package com.systemConfig.interfaces;
import java.util.HashMap;
import java.util.List;
import com.systemConfig.model.SystemUser;
public interface ISystemUserInterface{
	public Integer insertModel(SystemUser systemUser);
	public void deleteModel(Integer userCode);
	public void updateModel(SystemUser systemUser);
	public SystemUser findOneObject(Integer userCode);
	public Integer getCount(String hql);
	public Integer findCount(HashMap<String,Object> conditions);
	public List<SystemUser> findMenus(HashMap<String,Object> conditions);
	public List<SystemUser> getUsersByHql(String hql);
}
