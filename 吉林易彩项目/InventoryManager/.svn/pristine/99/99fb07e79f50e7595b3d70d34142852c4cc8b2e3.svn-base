package manage.user.web;

import base.util.ErrorMessage;
import base.web.PaginationAction;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpSession;

import manage.main.pojo.MenuInfoBean;
import manage.tree.pojo.Tree;
import manage.user.pojo.RoleInfoBean;
import manage.user.pojo.UserInfoBean;
import manage.user.service.UserInfoService;

import org.apache.log4j.Logger;

public class UserAction extends PaginationAction {
	private static final long serialVersionUID = -6699065407352971909L;
	private static final Logger log = Logger.getLogger(UserAction.class);
	private UserInfoBean userInfoBean;
	private UserInfoBean user;
	private UserInfoBean newUser;
	private UserInfoBean updateUser;
	private String updateps;
	private UserInfoBean data;
	private List<UserInfoBean> userList;
	private UserInfoService userInfoService;
	private ErrorMessage errorMessage;
	private String deleteMsg;
	private String sort;
	private String dir;
	private Integer total;
	private boolean success = false;

	private String verifyMsg = null;
	private MenuInfoBean power;
	private List<MenuInfoBean> powers;
	private List<String> ids;
	private List<Tree> userCkList;
	private String powerstr;
	private String strs;
	private Integer start;
	private Integer limit;
	private Integer countResult;
	private String node;
	private String userid;
	private String usercks;
	private RoleInfoBean roleBean;
	private RoleInfoBean newRole;
	private RoleInfoBean updateRole;
	private List<RoleInfoBean> roles;
	private Integer roleId;
	private Integer id;

	public String searchUser() throws Exception {
		try {
			this.userInfoBean = ((UserInfoBean) getSession().getAttribute(
					"userBean"));
			if (this.user == null) {
				this.user = new UserInfoBean();
			}
			this.user.setSort(this.sort);
			this.user.setDir(this.dir);
			this.user.setStart(this.start);
			this.user.setLimit(this.limit);
			this.user.setAreano(this.user.getAreano().trim());
			this.total = Integer.valueOf(this.userInfoService
					.getUserCount(this.user));

			this.userList = this.userInfoService.getUserByPage(this.user);
			this.user = new UserInfoBean();
			this.user.setUsers(this.userList);
			this.user.setTotal(this.total);
			return "searchUser";
		} catch (Exception e) {
			log.error("UserAction.searchUser", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
		}
		return "error";
	}

	public String verifyUser() throws Exception {
		try {
			if ((this.newUser == null) || (this.newUser.getUsername() == null)
					|| ("".equals(this.newUser.getUsername()))) {
				this.success = false;
				this.verifyMsg = "请输入用户名";
			} else {
				int n = this.userInfoService.getVerifyUserInfo(this.newUser);

				if (n == 0) {
					this.success = true;
					this.verifyMsg = "用户名可以使用";
				} else if (n >= 1) {
					this.success = false;
					this.verifyMsg = "用户名已存在";
				}
			}
			return "verifyUserInfo";
		} catch (Exception e) {
			log.error("UserAction.verifyUser", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
		}
		return "error";
	}

	public String searchPowerstr() throws Exception {
		try {
			this.userInfoBean = ((UserInfoBean) getSession().getAttribute(
					"userBean"));
			this.powers = this.userInfoService.getPowerstrs(this.power);
			return "searchPowerstr";
		} catch (Exception e) {
			log.error("UserAction.searchPowerstr", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
		}
		return "error";
	}

	/**
	 * 增加用户
	 * @return
	 * @throws Exception
	 */
	public String addUser() throws Exception {
		this.userInfoBean = ((UserInfoBean) getSession().getAttribute("userBean"));
		try {
			String groupId = this.getRequest().getParameter("groupId");
			String groupName = this.getRequest().getParameter("groupName");
			this.newUser.setGroupId(groupId);
			this.newUser.setGroupName(groupName);
			int n = this.userInfoService.insertNewUser(this.newUser);
			if (n == 1) {
				this.success = true;
			}
			this.success = false;
			String jsonString="{success:true}";
			this.printString(jsonString, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "addUser";
	}

	public String loadUser() throws Exception {
		try {
			this.userInfoBean = ((UserInfoBean) getSession().getAttribute(
					"userBean"));
			if ((this.ids == null) || (this.ids.isEmpty())) {
				this.success = false;
			}
			this.data = this.userInfoService.getLoadUser(this.ids);
			if (this.data != null)
				this.success = true;
			else {
				this.success = false;
			}
			return "loadUser";
		} catch (Exception e) {
			log.error("UserAction.loadUser", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
		}
		return "error";
	}

	public String deleteUser() throws Exception {
		this.userInfoBean = ((UserInfoBean) getSession().getAttribute(
				"userBean"));
		try {
			int n = this.userInfoService.deleteUser(this.ids);
			if (n != 0) {
				this.success = true;
				this.deleteMsg = "您成功删除了&nbsp;" + n + "&nbsp;条用户信息!";
			} else {
				this.success = false;
				this.deleteMsg = "删除用户信息失败!";
			}
			return "deleteUser";
		} catch (Exception e) {
			log.error("UserAction.deleteUser", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
		}
		return "error";
	}

	public String updateUser() throws Exception {
		this.userInfoBean = ((UserInfoBean) getSession().getAttribute(
				"userBean"));
		try {
			if (this.updateUser != null) {
				if ((this.updateps != null) && ("yes".equals(this.updateps))) {
					this.updateUser.setUpdatePassword(Boolean.valueOf(true));
				}

				this.updateUser.setPowerstr(this.powerstr);
				this.updateUser.setRoleId(this.roleId);
			}
			int n = this.userInfoService.updateUser(this.updateUser);
			if (n == 1)
				this.success = true;
			else {
				this.success = false;
			}
			return "updateUser";
		} catch (Exception e) {
			log.error("UserAction.updateUser", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
		}
		return "error";
	}

	public String roleList() throws Exception {
		try {
			this.userInfoBean = ((UserInfoBean) getSession().getAttribute(
					"userBean"));
			if (this.roleBean == null) {
				this.roleBean = new RoleInfoBean();
			}
			this.roleBean.setStart(this.start);
			this.roleBean.setLimit(this.limit);
			this.roleBean = this.userInfoService.getRoleList(this.roleBean);
			return "searchRole";
		} catch (Exception e) {
			log.error("UserAction.searchUser", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
		}
		return "error";
	}

	public String addRole() throws Exception {
		this.userInfoBean = ((UserInfoBean) getSession().getAttribute(
				"userBean"));
		try {
			this.newRole.setPowerstr(this.powerstr);
			int n = this.userInfoService.insertNewRole(this.newRole);
			if (n == 1) {
				this.success = true;
			}

			this.success = false;
		} catch (Exception e) {
			log.error("UserAction.addRole", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
			this.success = false;
		}
		return "addRole";
	}

	public String deleteRole() throws Exception {
		this.userInfoBean = ((UserInfoBean) getSession().getAttribute(
				"userBean"));
		try {
			int n = this.userInfoService.deleteRole(this.ids);
			if (n != 0) {
				this.success = true;
				this.deleteMsg = "您成功删除了&nbsp;" + n + "&nbsp;条角色信息!";
			} else if (n == 0) {
				this.success = false;
				this.deleteMsg = "有用户使用该角色不能进行删除!";
			} else {
				this.success = false;
				this.deleteMsg = "删除角色信息失败!";
			}
			return "deleteRole";
		} catch (Exception e) {
			log.error("UserAction.deleteRole", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
		}
		return "error";
	}

	public String updateRole() throws Exception {
		this.userInfoBean = ((UserInfoBean) getSession().getAttribute(
				"userBean"));
		try {
			if (this.updateRole != null) {
				this.updateRole.setPowerstr(this.powerstr);
			}
			int n = this.userInfoService.updateRole(this.updateRole);
			if (n == 1)
				this.success = true;
			else {
				this.success = false;
			}
			return "updateRole";
		} catch (Exception e) {
			log.error("UserAction.updateRole", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
		}
		return "error";
	}

	public String loadRole() throws Exception {
		try {
			this.userInfoBean = ((UserInfoBean) getSession().getAttribute(
					"userBean"));
			if ((this.ids == null) || (this.ids.isEmpty())) {
				this.success = false;
			}
			this.roleBean = this.userInfoService.getLoadRole(this.ids);
			if (this.roleBean != null)
				this.success = true;
			else {
				this.success = false;
			}
			return "loadRole";
		} catch (Exception e) {
			log.error("UserAction.loadRole", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
		}
		return "error";
	}

	public String verifyRole() throws Exception {
		try {
			if ((this.newRole == null) || (this.newRole.getRoleName() == null)
					|| ("".equals(this.newRole.getRoleName()))) {
				this.success = false;
				this.verifyMsg = "请输入角色名";
			} else {
				int n = this.userInfoService.getVerifyRoleInfo(this.newRole);

				if (n == 0) {
					this.success = true;
					this.verifyMsg = "角色名可以使用";
				} else if (n >= 1) {
					this.success = false;
					this.verifyMsg = "角色名已存在";
				}
			}
			return "verifyRoleInfo";
		} catch (Exception e) {
			log.error("UserAction.verifyRole", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
		}
		return "error";
	}

	public String searchRolestragain() throws Exception {
		try {
			this.userInfoBean = ((UserInfoBean) getSession().getAttribute(
					"userBean"));
			this.roles = this.userInfoService.getRolestr(this.roleBean);
			return "searchRolestragain";
		} catch (Exception e) {
			log.error("UserAction.searchRolestr", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
		}
		return "error";
	}

	public String searchRolestr() throws Exception {
		try {
			this.userInfoBean = ((UserInfoBean) getSession().getAttribute(
					"userBean"));
			this.roles = this.userInfoService.getRolestr(this.roleBean);
			return "searchRolestr";
		} catch (Exception e) {
			log.error("UserAction.searchRolestr", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
		}
		return "error";
	}

	public String searchRolePowerstr() throws Exception {
		try {
			this.userInfoBean = ((UserInfoBean) getSession().getAttribute(
					"userBean"));
			this.powers = this.userInfoService
					.getRolePowerByRoleId(this.roleBean);
			return "searchRolePowerstr";
		} catch (Exception e) {
			log.error("UserAction.searchRolestr", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
		}
		return "error";
	}
	
	/**
	 * 向前台输出数据
	 * @param string
	 * @param contentType
	 * @throws Exception
	 */
	public void printString(String string, String contentType) throws Exception {
		this.getResponse().setHeader("Cache-Control", "no-cache");
		this.getResponse().setContentType(contentType);
		this.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter pw = this.getResponse().getWriter();
		pw.write(string);
		pw.close();
	}

	public RoleInfoBean getUpdateRole() {
		return this.updateRole;
	}

	public void setUpdateRole(RoleInfoBean updateRole) {
		this.updateRole = updateRole;
	}

	public RoleInfoBean getNewRole() {
		return this.newRole;
	}

	public void setNewRole(RoleInfoBean newRole) {
		this.newRole = newRole;
	}

	public String findUserCount() {
		this.countResult = Integer.valueOf(this.userInfoService
				.findUserCount(this.user));
		return "findUserCount";
	}

	public UserInfoBean getUserInfoBean() {
		return this.userInfoBean;
	}

	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}

	public UserInfoService getUserInfoService() {
		return this.userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public ErrorMessage getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getDir() {
		return this.dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public UserInfoBean getUser() {
		return this.user;
	}

	public void setUser(UserInfoBean user) {
		this.user = user;
	}

	public List<UserInfoBean> getUserList() {
		return this.userList;
	}

	public void setUserList(List<UserInfoBean> userList) {
		this.userList = userList;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getVerifyMsg() {
		return this.verifyMsg;
	}

	public void setVerifyMsg(String verifyMsg) {
		this.verifyMsg = verifyMsg;
	}

	public UserInfoBean getNewUser() {
		return this.newUser;
	}

	public void setNewUser(UserInfoBean newUser) {
		this.newUser = newUser;
	}

	public List<String> getIds() {
		return this.ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public UserInfoBean getData() {
		return this.data;
	}

	public void setData(UserInfoBean data) {
		this.data = data;
	}

	public String getPowerstr() {
		return this.powerstr;
	}

	public void setPowerstr(String powerstr) {
		this.powerstr = powerstr;
	}

	public String getDeleteMsg() {
		return this.deleteMsg;
	}

	public void setDeleteMsg(String deleteMsg) {
		this.deleteMsg = deleteMsg;
	}

	public Integer getTotal() {
		return this.total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getStart() {
		return this.start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return this.limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getCountResult() {
		return this.countResult;
	}

	public void setCountResult(Integer countResult) {
		this.countResult = countResult;
	}

	public UserInfoBean getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(UserInfoBean updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateps() {
		return this.updateps;
	}

	public void setUpdateps(String updateps) {
		this.updateps = updateps;
	}

	public List<Tree> getUserCkList() {
		return this.userCkList;
	}

	public void setUserCkList(List<Tree> userCkList) {
		this.userCkList = userCkList;
	}

	public String getNode() {
		return this.node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsercks() {
		return this.usercks;
	}

	public void setUsercks(String usercks) {
		this.usercks = usercks;
	}

	public List<MenuInfoBean> getPowers() {
		return this.powers;
	}

	public void setPowers(List<MenuInfoBean> powers) {
		this.powers = powers;
	}

	public static long getSerialVersionUID() {
		return -6699065407352971909L;
	}

	public static Logger getLog() {
		return log;
	}

	public MenuInfoBean getPower() {
		return this.power;
	}

	public void setPower(MenuInfoBean power) {
		this.power = power;
	}

	public RoleInfoBean getRoleBean() {
		return this.roleBean;
	}

	public void setRoleBean(RoleInfoBean roleBean) {
		this.roleBean = roleBean;
	}

	public List<RoleInfoBean> getRoles() {
		return this.roles;
	}

	public void setRoles(List<RoleInfoBean> roles) {
		this.roles = roles;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getStrs() {
		return this.strs;
	}

	public void setStrs(String strs) {
		this.strs = strs;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
