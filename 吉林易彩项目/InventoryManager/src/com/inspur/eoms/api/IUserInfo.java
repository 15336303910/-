package com.inspur.eoms.api;

import java.util.Map;

import com.langchao.comm.authority.UserinfoObj;

public interface IUserInfo {
	public String getUseraccount();

	public Map getUserInfo();

	public UserinfoObj getUserInfoObj();

	public void setUserInfo(Map userInfo);

	public void setUserInfoObj(UserinfoObj userInfoObj);
	
	public void addUserConfig(String key,Object value);
}
