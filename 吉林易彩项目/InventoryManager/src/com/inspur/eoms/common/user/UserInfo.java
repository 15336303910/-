/*
 * @(#) $Id: UserInfo.java,v 1.1 2006/11/22 10:10:25 genglei Exp $
 * $Revision: 1.1 $
 * $Date: 2006/11/22 10:10:25 $
 * 
 * $Log: UserInfo.java,v $
 * Revision 1.1  2006/11/22 10:10:25  genglei
 * �����µ��ۺϰ汾������ά
 *
 * Revision 1.5  2006/07/13 06:26:14  genglei
 * Ĭ�����Դ���
 *
 * Revision 1.4  2006/07/12 07:04:37  genglei
 * *** empty log message ***
 *
 * Revision 1.3  2006/07/11 08:52:01  genglei
 * *** empty log message ***
 *
 * Revision 1.2  2006/07/11 02:47:22  genglei
 * ���ҳ���С�����ļ���ȡ
 *
 * Revision 1.1  2006/07/05 08:51:44  genglei
 * eclipse�������⣬���ݹ���
 *
 * 
 * ===================================
 * Electric Operation Maintenance System(EOMS)
 * 
 * Copyright (c) 2006 by INSPUR LG, Inc.
 * All rights reserved.
 */
package com.inspur.eoms.common.user;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.inspur.eoms.api.IUserInfo;
import com.langchao.comm.authority.UserinfoObj;

public class UserInfo implements IUserInfo, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map userInfo = new HashMap();
	
	private UserinfoObj userInfoObj;


	/* 取得登陆账号.
	 * @see com.inspur.eoms.api.IUserInfo#getUseraccount()
	 */
	public String getUseraccount() {
		if(userInfoObj==null)
			return "";
		return userInfoObj.getUseraccount();
	}

	public UserinfoObj getUserInfoObj() {
		return userInfoObj;
	}

	public void setUserInfoObj(UserinfoObj userInfoObj) {
		this.userInfoObj = userInfoObj;
	}

	public Map getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(Map userInfo) {
		this.userInfo = userInfo;
	}
	
	public void addUserConfig(String key,Object value){
		userInfo.put(key,value);
	}

}
