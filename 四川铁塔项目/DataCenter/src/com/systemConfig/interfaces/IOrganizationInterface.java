package com.systemConfig.interfaces;
import java.util.HashMap;
import java.util.List;
import com.systemConfig.model.SystemOrganization;
public interface IOrganizationInterface{
	public Integer insertOrganization(SystemOrganization systemOrganization);
	public boolean deleteObject(Integer id);
	public boolean updateObject(SystemOrganization systemOrganization);
	public SystemOrganization getObject(Integer id);
	public Integer getCount(HashMap<String,Object> conditions);
	public List<SystemOrganization> getOrgansByHql(String hql);
	public List<SystemOrganization> getOrgansPage(HashMap<String,Object> conditions);
}
