package manage.user.pojo;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class UserInfoBean
{
  private Integer userId;
  private String username;
  private String password;
  private String offLinePwd;
  private String modulestr;
  private String usertype;
  private String powerstr;
  private String equtstr;
  private String areanostr;
  private String areano;
  private String realname;
  private String rolename;
  private String phoneNumber;
  private Date lasttime;
  private String lastip;
  private String lasteid;
  private String code;
  private String areaName;
  private Integer level;
  private Integer fatherid;
  private String isParent;
  private Integer domainid;
  private String areaflag;
  private String eid;
  private String beantype;
  private List<String> userCkBeans;
  private List<String> powerstrs;
  private Boolean updatePassword = Boolean.valueOf(false);
  private Boolean isResponsible;
  private Integer responsibleid;
  private Integer roleId;
  private String url;
  private Integer total;
  private String dir;
  private String sort;
  private Integer start;
  private Integer limit;
  private List<UserInfoBean> users;
  private String groupId;//组ID
  private String groupName;//组名称
  private Integer versionCode;
  private String authCode;//验证码
  private String maintenArea;//维护区域
  
  

  public String getMaintenArea() {
	return maintenArea;
}
public void setMaintenArea(String maintenArea) {
	this.maintenArea = maintenArea;
}
public String getAuthCode() {
	return authCode;
}
public void setAuthCode(String authCode) {
	this.authCode = authCode;
}

public Integer getVersionCode() {
	return versionCode;
  }

  public void setVersionCode(Integer versionCode) {
	this.versionCode = versionCode;
  }
  public String getGroupId() {
	return groupId;
  }
  public void setGroupId(String groupId) {
	this.groupId = groupId;
  }

public String getGroupName() {
	return groupName;
}

public void setGroupName(String groupName) {
	this.groupName = groupName;
}

public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getRoleId() {
    return this.roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  public String getDir() {
    return this.dir;
  }

  public void setDir(String dir) {
    this.dir = dir;
  }

  public UserInfoBean() {
    this.beantype = "user";
  }

  public Integer getLevel() {
    return this.level; }

  public void setLevel(Integer level) {
    this.level = level; }

  public void setUsername(String username) {
    this.username = username; }

  public String getUsername() {
    return this.username; }

  public void setPassword(String password) {
    this.password = password; }

  public String getPassword() {
    return this.password;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public Integer getUserId() {
    return this.userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public void setModulestr(String modulestr) {
    this.modulestr = modulestr; }

  public String getModulestr() {
    return this.modulestr; }

  public String getCode() {
    return this.code; }

  public void setCode(String code) {
    this.code = code; }

  public void setPowerstr(String powerstr) {
    this.powerstr = powerstr; }

  public String getPowerstr() {
    return this.powerstr; }

  public void setRealname(String realname) {
    this.realname = realname; }

  public String getRealname() {
    return this.realname; }

  public void setLastip(String lastip) {
    this.lastip = lastip; }

  public String getLastip() {
    return this.lastip; }

  public void setLasttime(Date lasttime) throws ParseException {
    this.lasttime = lasttime; }

  public Date getLasttime() {
    return this.lasttime;
  }

  public void setLasteid(String lasteid) {
    this.lasteid = lasteid; }

  public String getLasteid() {
    return this.lasteid; }

  public void setEid(String eid) {
    this.eid = eid; }

  public String getEid() {
    return this.eid; }

  public void setRolename(String rolename) {
    this.rolename = rolename; }

  public String getRolename() {
    return this.rolename; }

  public String getIsParent() {
    return this.isParent; }

  public void setIsParent(String isParent) {
    this.isParent = isParent; }

  public Integer getFatherid() {
    return this.fatherid; }

  public void setFatherid(Integer fatherid) {
    this.fatherid = fatherid;
  }

  public String getBeantype() {
    return this.beantype;
  }

  public void setBeantype(String beantype) {
    this.beantype = beantype;
  }

  public String getAreano() {
    return this.areano;
  }

  public void setAreano(String areano) {
    this.areano = areano;
  }

  public String getEqutstr() {
    return this.equtstr;
  }

  public void setEqutstr(String equtstr) {
    this.equtstr = equtstr;
  }

  public String getUsertype() {
    return this.usertype;
  }

  public void setUsertype(String usertype) {
    this.usertype = usertype;
  }

  public List<String> getUserCkBeans() {
    return this.userCkBeans;
  }

  public void setUserCkBeans(List<String> userCkBeans) {
    this.userCkBeans = userCkBeans;
  }

  public String getAreanostr()
  {
    return this.areanostr;
  }

  public void setAreanostr(String areanostr) {
    this.areanostr = areanostr;
  }

  public Integer getDomainid() {
    return this.domainid;
  }

  public void setDomainid(Integer domainid) {
    this.domainid = domainid;
  }

  public String getAreaflag() {
    return this.areaflag;
  }

  public void setAreaflag(String areaflag) {
    this.areaflag = areaflag;
  }

  public String getSort()
  {
    return this.sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public String getAreaName() {
    return this.areaName;
  }

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

  public Integer getTotal() {
    return this.total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public List<UserInfoBean> getUsers() {
    return this.users;
  }

  public void setUsers(List<UserInfoBean> users) {
    this.users = users;
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

  public List<String> getPowerstrs() {
    return this.powerstrs;
  }

  public void setPowerstrs(List<String> powerstrs) {
    this.powerstrs = powerstrs;
  }

  public Boolean getUpdatePassword() {
    return this.updatePassword;
  }

  public void setUpdatePassword(Boolean updatePassword) {
    this.updatePassword = updatePassword;
  }

  public Boolean getIsResponsible() {
    return this.isResponsible;
  }

  public void setIsResponsible(Boolean isResponsible) {
    this.isResponsible = isResponsible;
  }

  public Integer getResponsibleid() {
    return this.responsibleid;
  }

  public void setResponsibleid(Integer responsibleid) {
    this.responsibleid = responsibleid;
  }

  public String getOffLinePwd() {
    return this.offLinePwd;
  }

  public void setOffLinePwd(String offLinePwd) {
    this.offLinePwd = offLinePwd;
  }
}