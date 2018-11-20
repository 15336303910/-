package manage.user.service;

import base.exceptions.DataAccessException;

import java.util.List;
import java.util.Map;

import manage.domain.pojo.DomainBean;
import manage.main.pojo.MenuInfoBean;
import manage.user.pojo.MaintainGroupBean;
import manage.user.pojo.RoleInfoBean;
import manage.user.pojo.UserInfoBean;

public abstract interface UserInfoService
{
  public abstract List<UserInfoBean> getUserByPage(UserInfoBean paramUserInfoBean)
    throws DataAccessException;

  public abstract int getUserCount(UserInfoBean paramUserInfoBean)
    throws DataAccessException;

  public abstract int getVerifyUserInfo(UserInfoBean paramUserInfoBean)
    throws DataAccessException;

  public abstract List<MenuInfoBean> getPowerstrs(MenuInfoBean paramMenuInfoBean)
    throws DataAccessException;

  public abstract int insertNewUser(UserInfoBean paramUserInfoBean)
    throws DataAccessException;

  public abstract UserInfoBean getLoadUser(List<String> paramList)
    throws DataAccessException;

  public abstract UserInfoBean getUserById(UserInfoBean paramUserInfoBean)
    throws DataAccessException;

  public abstract int deleteUser(List<String> paramList)
    throws DataAccessException;

  public abstract int findUserCount(UserInfoBean paramUserInfoBean)
    throws DataAccessException;

  public abstract int updateUser(UserInfoBean paramUserInfoBean)
    throws DataAccessException;

  public abstract RoleInfoBean getRoleList(RoleInfoBean paramRoleInfoBean)
    throws DataAccessException;

  public abstract int insertNewRole(RoleInfoBean paramRoleInfoBean)
    throws DataAccessException;

  public abstract int deleteRole(List<String> paramList)
    throws DataAccessException;

  public abstract int updateRole(RoleInfoBean paramRoleInfoBean)
    throws DataAccessException;

  public abstract RoleInfoBean getLoadRole(List<String> paramList)
    throws DataAccessException;

  public abstract int getVerifyRoleInfo(RoleInfoBean paramRoleInfoBean)
    throws DataAccessException;

  public abstract List<RoleInfoBean> getRolestr(RoleInfoBean paramRoleInfoBean)
    throws DataAccessException;

  public abstract List<MenuInfoBean> getRolePowerByRoleId(RoleInfoBean paramRoleInfoBean)
    throws DataAccessException;

  public abstract List<DomainBean> getDomainBeanList(DomainBean paramDomainBean)
    throws DataAccessException;
  
  
  /**
	 * 得到班组列表
	 * @param obj
	 * @return
	 */
	public List<MaintainGroupBean> getGroupList(MaintainGroupBean obj);
	
	
	
	/**
	 * 返回班组的总条数
	 * @param obj
	 * @return
	 */
	public int getGroupCount(MaintainGroupBean obj);
	
	
	/**
	 * 得到维护公司下拉
	 * @return
	 */
	public List<Map<String, Object>> getCompList();
	
	
	
	/**
	 * 保存班组信息
	 * @param obj
	 * @return
	 */
	public int saveGroup(MaintainGroupBean obj);
	
	
	
	/**
	 * 删除班组信息
	 * @param ids
	 */
	public void delGroup(String ids);
	
	
	/**
	 * 根据domaincode得到
	 * 相应的班组信息
	 * @param domainCode
	 * @return
	 */
	public List<Map<String, Object>> getGroupList(String domainCode);
	
	/**
	 * 根据区县ID得到相应的信息
	 * @param country
	 * @return
	 */
	public List<Map<String, Object>> getGroupByCountry(String country);
	
}