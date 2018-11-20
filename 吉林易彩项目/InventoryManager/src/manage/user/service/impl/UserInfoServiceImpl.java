package manage.user.service.impl;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.CommonUtil;
import base.util.JsonUtil;
import base.util.TextUtil;
import base.util.md5;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import manage.domain.pojo.DomainBean;
import manage.main.pojo.MenuInfoBean;
import manage.user.pojo.MaintainGroupBean;
import manage.user.pojo.RoleInfoBean;
import manage.user.pojo.UserCkBean;
import manage.user.pojo.UserInfoBean;
import manage.user.service.UserInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UserInfoServiceImpl extends DataBase implements UserInfoService {
	private static String GET_USER = "user.getUserList";
	private static String GET_USER_COUNT = "user.getUserCount";
	private static String VERIFY_USER_COUNT = "user.verifyUserCount";
	private static String GET_POWERSTR = "user.getPowerstrs";
	private static String INSERT_NEW_USER = "user.insertNewUwer";
	private static String UPDATE_USER = "user.updateUser";
	private static String UPDATE_USER_ByRoleId = "user.updateUserByRoleId";
	private static String GET_DOMAIN_NODE = "user.getDomainNode";
	private static String INSERT_USER_POWERS = "user.insertUserPowers";
	private static String DELETE_USER = "user.deleteUser";
	private static String GET_USER_POWERS = "user.getUserPowers";
	private static String DELETE_USER_POWERS = "user.deleteUserPowers";
	private static final String GET_ALL_CHILDREN_DOMAIN = "user.getAllChildrenDomain";
	private static final String GET_ALL_CHILDREN_EQUT = "user.getAllChildrenEqut";
	private static final String GET_DOMAIN_BY_USER = "user.getDomainByUser";
	private static final String GET_USERCK = "user.getUserck";
	private static final String GET_USERCK_LIST = "user.getUserckList";
	private static final String INSERT_USERCK = "user.insertUserck";
	private static final String GET_EQUIT_BY_USERCK = "user.getEquitByUserck";
	private static final String DELETE_USERCK = "user.deleteUserck";
	private static final String GET_PARENT_MENU = "user.getParentMenu";
	private static final String GetRoleListStatment = "user.getRoleList";
	private static final String GetRoleCountStatment = "user.getRoleCount";
	private static final String INSERT_NEW_ROLE = "user.insertNewRole";
	private static final String UPDATE_ROLE = "user.updateRole";
	private static final String DomainListStatment = "user.getDomainList";
	private static final String DELETE_ROLE = "user.deleteRole";
	private static final String INSERT_ROLE_POWERS = "user.insertRolePowers";
	private static final String DELETE_ROLE_POWERS = "user.deleteRolePowers";
	private static final String GET_ROLE_POWERS = "user.getRolePowers";
	private static final String VERIFY_ROLE_COUNT = "user.verifyRoleCount";
	private static final String GET_DOMAIN = "user.getDomain";
	private static final String GET_ROLESTR = "user.getRolestr";
	private static final String GET_ROLEPOWERBYROLEID = "user.getRolePowersByRoleId";
	private static final String GET_POWERS = "user.getPowers";
	private static final String GET_EQUTS = "user.getEquts";
	private static final String UPDATE_DOMAIN = "user.updateEqut";
	private static final String UPDATE_DOMAIN_REID = "user.updateDomainReid";
	private static final String UPDATE_EQUT = "user.updateEqutByU";
	private JdbcTemplate jdbcTemplate;

	public List<UserInfoBean> getUserByPage(UserInfoBean user)
			throws DataAccessException {
		List list = getObjects(GET_USER, user);
		String type = "";
		if (list != null) {
			for (int i = 0; i < list.size(); ++i) {
				UserInfoBean thisuser = (UserInfoBean) list.get(i);
				Integer responsibleid = ((UserInfoBean) list.get(i))
						.getResponsibleid();
				if ((responsibleid == null)
						|| (thisuser.getUserId().intValue() != responsibleid
								.intValue()))
					continue;
				thisuser.setIsResponsible(Boolean.valueOf(true));
			}

		}

		return list;
	}

	public int getUserCount(UserInfoBean user) throws DataAccessException {
		return getCount(GET_USER_COUNT, user);
	}

	public int getVerifyUserInfo(UserInfoBean user) throws DataAccessException {
		return getCount(VERIFY_USER_COUNT, user);
	}

	public List<MenuInfoBean> getPowerstrs(MenuInfoBean power)
			throws DataAccessException {
		List list = getObjects(GET_POWERSTR, power);
		return list;
	}

	/**
	 * 增加一个新用户
	 */
	public int insertNewUser(UserInfoBean newUser) throws DataAccessException {
		if (newUser == null) {
			return 0;
		}
		String getRole = "select roleId from sys_role where areano = '"+newUser.getAreano()+"'";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(getRole);
		if(TextUtil.isNotNull(list)) {
			Map<String, Object> map = list.get(0);
			Integer roleId = Integer.parseInt(map.get("roleId")+"");
			newUser.setRoleId(roleId);
		}
		newUser.setPassword(md5.strToMD5(newUser.getPassword()));
		newUser.setModulestr("0100000000000000000000000000000011000000000000000000000000000000");
		int uid = ((Integer) insert(INSERT_NEW_USER, newUser)).intValue();
		return 1;
	}

	public UserInfoBean getUserById(UserInfoBean user)
			throws DataAccessException {
		return ((UserInfoBean) getObject(GET_USER, user));
	}

	public UserInfoBean getLoadUser(List<String> ids)
			throws DataAccessException {
		UserInfoBean user = new UserInfoBean();
		user.setUserId(Integer.valueOf(Integer.parseInt(((String) ids.get(0))
				.trim())));
		user = (UserInfoBean) getObject(GET_USER, user);

		String type = "";
		if (user != null) {
			String modulestr = user.getModulestr();

			MenuInfoBean power = new MenuInfoBean();
			power.setUserId(user.getUserId());
			List powers = getObjects(GET_USER_POWERS, power);
			if ((powers != null) && (!(powers.isEmpty()))) {
				user.setPowerstrs(powers);
			}
		}
		return user;
	}

	public int deleteUser(List<String> ids) throws DataAccessException {
		int n = 0;
		if ((ids != null) && (!(ids.isEmpty()))) {
			for (int i = 0; i < ids.size(); ++i) {
				UserInfoBean user = new UserInfoBean();
				user.setUserId(Integer.valueOf(Integer.parseInt((String) ids
						.get(i))));
				int x = delete(DELETE_USER, user);
				n += x;
			}
		}

		return n;
	}

	public int findUserCount(UserInfoBean user) throws DataAccessException {
		int n = getCount(GET_USER_COUNT, user);
		return n;
	}

	public int updateUser(UserInfoBean updateUser) throws DataAccessException {
		if (updateUser.getUpdatePassword().booleanValue())
			updateUser.setPassword(md5.strToMD5(updateUser.getPassword()));
		else {
			updateUser.setPassword(null);
		}
		boolean hasWorkOrderManageRight = false;

		MenuInfoBean power = new MenuInfoBean();
		MenuInfoBean parent = new MenuInfoBean();
		power.setUserId(updateUser.getUserId());
		delete(DELETE_USER_POWERS, power);

		String[] powers = updateUser.getPowerstr().split(",");
		List menus = new ArrayList();
		for (int i = 0; i < powers.length; ++i) {
			power = new MenuInfoBean();
			power.setUserId(updateUser.getUserId());

			power.setCode(powers[i].trim());
			menus.add(power);
			parent = new MenuInfoBean();
			parent = (MenuInfoBean) getObject("user.getParentMenu", power);
			if (CommonUtil.noHasMenu(menus, parent)) {
				parent.setUserId(updateUser.getUserId());
				menus.add(parent);
				if (Integer.parseInt(parent.getCode()) > 10) {
					power = new MenuInfoBean();
					power.setCode(parent.getCode());
					parent = new MenuInfoBean();
					parent = (MenuInfoBean) getObject("user.getParentMenu",
							power);
					if (CommonUtil.noHasMenu(menus, parent)) {
						parent.setUserId(updateUser.getUserId());
						menus.add(parent);
					}
				}
			}
		}
		batchInsert(INSERT_USER_POWERS, menus);

		RoleInfoBean role = new RoleInfoBean();
		role.setRoleId(updateUser.getRoleId());
		role = (RoleInfoBean) getObject("user.getRoleList", role);
		updateUser.setModulestr(role.getModulestr());
		int n = update(UPDATE_USER, updateUser);

		return 1;
	}

	private Boolean isInUserCk(DomainBean odomainmain, List<UserCkBean> ck) {
		String domainCode = odomainmain.getDomainCode();
		for (UserCkBean uck : ck) {
			String ckAreano = uck.getAreano();
			if (ckAreano.startsWith(domainCode)) {
				return Boolean.valueOf(true);
			}
		}
		return Boolean.valueOf(false);
	}

	public int updateUserCk(String usercks, String userid)
			throws DataAccessException {
		UserCkBean removeCk = new UserCkBean();
		removeCk.setUserid(userid);
		List usercklist = getObjects("user.getUserckList", removeCk);
		delete("user.deleteUserck", removeCk);

		JSONArray jsons = JSONArray.fromObject(usercks);

		boolean flag = true;
		for (int i = 0; i < usercklist.size(); ++i) {
			flag = true;
			if (jsons.size() == 0)
				flag = false;
			else {
				for (int j = 0; j < jsons.size(); ++j) {
					JSONObject json = jsons.getJSONObject(j);
					UserCkBean ck = (UserCkBean) JsonUtil.JsonToObject(json,
							UserCkBean.class);
					if (((UserCkBean) usercklist.get(i)).getEid().equals(
							ck.getEid())) {
						flag = true;
						break;
					}
					flag = false;
				}
			}
		}

		return 1;
	}

	public RoleInfoBean getRoleList(RoleInfoBean roleInfoBean)
			throws DataAccessException {
		List list = getObjects("user.getRoleList", roleInfoBean);
		int total = getCount("user.getRoleCount", roleInfoBean);
		RoleInfoBean role = new RoleInfoBean();
		role.setRoles(list);
		role.setTotal(Integer.valueOf(total));
		return role;
	}

	public int insertNewRole(RoleInfoBean newRole) throws DataAccessException {
		if (newRole == null) {
			return 0;
		}
		int uid = ((Integer) insert("user.insertNewRole", newRole)).intValue();

		String[] powers = newRole.getPowerstr().split(",");
		List menus = new ArrayList();
		for (int i = 0; i < powers.length; ++i) {
			MenuInfoBean power = new MenuInfoBean();
			power.setRoleId(Integer.valueOf(uid));
			power.setCode(powers[i].trim());
			menus.add(power);
			MenuInfoBean parent = (MenuInfoBean) getObject(
					"user.getParentMenu", power);
			if (CommonUtil.noHasMenu(menus, parent)) {
				parent.setRoleId(Integer.valueOf(uid));
				menus.add(parent);
			}
		}
		batchInsert("user.insertRolePowers", menus);

		String modulestr = "0000000000000000000000000000000000000000000000000000000000000000";
		char[] modulechar = modulestr.toCharArray();
		String[] types = newRole.getRoletype().split(",");
		String n = "10";
		for (int i = 0; i < types.length; ++i) {
			if (types[i].trim().equals("5")) {
				n = types[i].trim();
			} else if (types[i].trim().equals("6")) {
				modulechar[32] = '1';
			} else if (types[i].trim().equals("7")) {
				modulechar[33] = '1';
			} else if ((Integer.valueOf(types[i].trim()).intValue() < Integer
					.valueOf(n).intValue()) && (!(n.equals("5")))) {
				n = types[i].trim();
			}
		}
		if (n.equals("2"))
			modulechar[1] = '1';
		else if (n.equals("3"))
			modulechar[2] = '1';
		else if (n.equals("4"))
			modulechar[3] = '1';
		else if (n.equals("5")) {
			modulechar[4] = '1';
		}
		newRole.setModulestr(new String(modulechar));
		newRole.setRoleId(Integer.valueOf(uid));
		newRole.setUpdateDate(new Date());
		update("user.updateRole", newRole);
		return 1;
	}

	public int deleteRole(List<String> ids) throws DataAccessException {
		int n = 0;
		if ((ids != null) && (!(ids.isEmpty()))) {
			UserInfoBean user = new UserInfoBean();
			user.setRoleId(Integer.valueOf(Integer.parseInt((String) ids.get(0))));
			List list = getObjects(GET_USER, user);
			if (list.size() > 0) {
				return 0;
			}
			RoleInfoBean role = new RoleInfoBean();
			role.setRoleId(Integer.valueOf(Integer.parseInt((String) ids.get(0))));
			n = delete("user.deleteRole", role);
		}

		return n;
	}

	public int updateRole(RoleInfoBean updateRole) throws DataAccessException {
		boolean hasWorkOrderManageRight = false;

		MenuInfoBean power = new MenuInfoBean();
		power.setRoleId(updateRole.getRoleId());
		delete("user.deleteRolePowers", power);

		String[] powers = updateRole.getPowerstr().split(",");
		List menus = new ArrayList();
		for (int i = 0; i < powers.length; ++i) {
			power = new MenuInfoBean();
			power.setRoleId(updateRole.getRoleId());
			power.setCode(powers[i].trim());
			menus.add(power);
			MenuInfoBean parent = (MenuInfoBean) getObject(
					"user.getParentMenu", power);
			if (CommonUtil.noHasMenu(menus, parent)) {
				parent.setRoleId(updateRole.getRoleId());
				menus.add(parent);
			}
		}
		batchInsert("user.insertRolePowers", menus);

		String[] oldpowers = updateRole.getStrs().split(",");
		UserInfoBean user;
		List list;
		Iterator localIterator;
		UserInfoBean u;
		for (int i = 0; i < powers.length; ++i) {
			for (int j = 0; j < oldpowers.length; ++j) {
				if (powers[i].trim().equals(oldpowers[j].trim()))
					break;
				if ((powers[i].trim().equals(oldpowers[j].trim()))
						|| (j != oldpowers.length - 1))
					continue;
				user = new UserInfoBean();
				user.setRoleId(updateRole.getRoleId());
				list = getObjects(GET_USER, user);
				for (localIterator = list.iterator(); localIterator.hasNext();) {
					u = (UserInfoBean) localIterator.next();
					power = new MenuInfoBean();
					power.setUserId(u.getUserId());
					power.setCode(powers[i].trim());
					power = (MenuInfoBean) getObject("user.getPowers", power);
					if (power != null) {
						continue;
					}
					power = new MenuInfoBean();
					menus = new ArrayList();
					power.setUserId(u.getUserId());
					power.setCode(powers[i].trim());
					menus.add(power);
					MenuInfoBean parent = (MenuInfoBean) getObject(
							"user.getParentMenu", power);
					if (CommonUtil.noHasMenu(menus, parent)) {
						parent.setUserId(u.getUserId());
						menus.add(parent);
					}
					batchInsert(INSERT_USER_POWERS, menus);
				}
			}

		}

		for (int j = 0; j < oldpowers.length; ++j) {
			for (int i = 0; i < powers.length; ++i) {
				if (powers[i].trim().equals(oldpowers[j].trim()))
					break;
				if ((powers[i].trim().equals(oldpowers[j].trim()))
						|| (i != powers.length - 1))
					continue;
				user = new UserInfoBean();
				user.setRoleId(updateRole.getRoleId());
				list = getObjects(GET_USER, user);
				for (localIterator = list.iterator(); localIterator.hasNext();) {
					u = (UserInfoBean) localIterator.next();
					power = new MenuInfoBean();
					power.setUserId(u.getUserId());
					power.setCode(oldpowers[j].trim());
					delete(DELETE_USER_POWERS, power);
				}
			}

		}

		String modulestr = "0000000000000000000000000000000000000000000000000000000000000000";
		char[] modulechar = modulestr.toCharArray();
		String[] types = updateRole.getRoletype().split(",");
		String n = "10";
		for (int i = 0; i < types.length; ++i) {
			if (types[i].trim().equals("5")) {
				n = types[i].trim();
			} else if (types[i].trim().equals("6")) {
				modulechar[32] = '1';
			} else if (types[i].trim().equals("7")) {
				modulechar[33] = '1';
			} else if ((Integer.valueOf(types[i].trim()).intValue() < Integer
					.valueOf(n).intValue()) && (!(n.equals("5")))) {
				n = types[i].trim();
			}
		}
		if (n.equals("2"))
			modulechar[1] = '1';
		else if (n.equals("3"))
			modulechar[2] = '1';
		else if (n.equals("4"))
			modulechar[3] = '1';
		else if (n.equals("5")) {
			modulechar[4] = '1';
		}
		updateRole.setModulestr(new String(modulechar));
		updateRole.setUpdateDate(new Date());
		int m = update("user.updateRole", updateRole);

		UserInfoBean user1 = new UserInfoBean();
		user1.setRoleId(updateRole.getRoleId());
		user1.setModulestr(new String(modulechar));
		update(UPDATE_USER_ByRoleId, user1);
		return 1;
	}

	public RoleInfoBean getLoadRole(List<String> ids)
			throws DataAccessException {
		RoleInfoBean role = new RoleInfoBean();
		role.setRoleId(Integer.valueOf(Integer.parseInt(((String) ids.get(0))
				.trim())));
		role = (RoleInfoBean) getObject("user.getRoleList", role);
		DomainBean domain = new DomainBean();
		domain.setDomainCode(role.getAreano());
		domain = (DomainBean) getObject("user.getDomain", domain);
		role.setAreaname(domain.getDomainName());

		String type = "";
		List types = new ArrayList();
		if (role != null) {
			String modulestr = role.getModulestr();
			if ((modulestr != null) && (!("".equals(modulestr)))) {
				char[] powers = modulestr.toCharArray();
				if (powers != null) {
					for (int j = 0; j < powers.length; ++j) {
						if (powers[j] == '1') {
							if (j == 1) {
								types.add("2");
								types.add("3");
								types.add("4");
							} else if (j == 2) {
								types.add("3");
								types.add("4");
							} else if (j == 3) {
								types.add("4");
							} else if (j == 4) {
								types.add("5");
								types.add("3");
								types.add("4");
							} else if (j == 32) {
								types.add("6");
							} else if (j == 33) {
								types.add("7");
							}
						}
					}
					role.setRoletypes(types);
				}
			}

			MenuInfoBean power = new MenuInfoBean();
			power.setRoleId(role.getRoleId());
			List powers = getObjects("user.getRolePowers", power);
			if ((powers != null) && (!(powers.isEmpty()))) {
				role.setPowerstrs(powers);
			}
		}
		return role;
	}

	public int getVerifyRoleInfo(RoleInfoBean newRole)
			throws DataAccessException {
		return getCount("user.verifyRoleCount", newRole);
	}

	public List<RoleInfoBean> getRolestr(RoleInfoBean roleInfoBean)
			throws DataAccessException {
		List list = getObjects("user.getRolestr", roleInfoBean);
		return list;
	}

	public List<MenuInfoBean> getRolePowerByRoleId(RoleInfoBean roleInfoBean)
			throws DataAccessException {
		List list = getObjects("user.getRolePowersByRoleId", roleInfoBean);
		return list;
	}

	public List<DomainBean> getDomainBeanList(DomainBean domain)
			throws DataAccessException {
		return getObjects("user.getDomainList", domain);
	}
	
	
	/**
	 * 得到班组列表
	 * @param obj
	 * @return
	 */
	public List<MaintainGroupBean> getGroupList(MaintainGroupBean obj){
		List<MaintainGroupBean>  list = new LinkedList<MaintainGroupBean>();
		list = this.getObjects("user.getMaintainGroup", obj);
		return list;
	}
	
	
	/**
	 * 得到维护公司下拉
	 * @return
	 */
	public List<Map<String, Object>> getCompList(){
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		String sql ="select t.compName as name,t.compRes as id "
				+ " from maintainComp t where 1=1 ";
		list = this.jdbcTemplate.queryForList(sql);
		return list;
	}
	
	/**
	 * 根据domaincode得到
	 * 相应的班组信息
	 * @param domainCode
	 * @return
	 */
	public List<Map<String, Object>> getGroupList(String domainCode){
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		String sql ="select groupName as name ,id as id"
				+ " from maintaingroup where domainId like '%"+domainCode+"'";
		list = this.jdbcTemplate.queryForList(sql);
		return list;
	}
	
	
	/**
	 * 根据区县ID得到相应的信息
	 * @param country
	 * @return
	 */
	public List<Map<String, Object>> getGroupByCountry(String country){
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		String sql =" select g.groupName as name,g.id as id from maintaingroup g,sys_domain d,rms_county c"
				+ " where g.domainId = d.domainCode and d.domainName = c.towerName "
				+ " and c.resNum ='"+country+"'";
		list = this.jdbcTemplate.queryForList(sql);
		return list;
	}
	
	/**
	 * 保存班组信息
	 * @param obj
	 * @return
	 */
	public int saveGroup(MaintainGroupBean obj){
		int id = (Integer) this.insert("user.addMaintainGroup", obj);
		return id;
	}
	
	/**
	 * 返回班组的总条数
	 * @param obj
	 * @return
	 */
	public int getGroupCount(MaintainGroupBean obj){
		int num = this.getCount("user.getMaintainGroupNum", obj);
		return num;
	}
	
	/**
	 * 删除班组信息
	 * @param ids
	 */
	public void delGroup(String ids){
		this.delete("user.delMainGroup", ids);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public void addRes() {

	}
}
