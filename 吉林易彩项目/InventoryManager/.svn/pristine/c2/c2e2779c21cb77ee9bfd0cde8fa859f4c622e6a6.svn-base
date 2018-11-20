/*
 * @(#) $Id: UserSession.java,v 1.3 2006/12/13 08:35:35 genglei Exp $
 * $Revision: 1.3 $
 * $Date: 2006/12/13 08:35:35 $
 * 
 * $Log: UserSession.java,v $
 * Revision 1.3  2006/12/13 08:35:35  genglei
 * ����û����?��.
 *
 * Revision 1.2  2006/12/02 05:29:07  genglei
 * ��������û�����
 *
 * Revision 1.1  2006/11/22 10:10:25  genglei
 * �����µ��ۺϰ汾������ά
 *
 * Revision 1.15  2006/08/10 06:58:14  genglei
 * *** empty log message ***
 *
 * Revision 1.14  2006/07/31 05:55:23  mufsh
 * ���û���Ϣ
 *
 * Revision 1.13  2006/07/30 04:34:26  genglei
 * ����Ȩ��
 *
 * Revision 1.12  2006/07/27 13:05:50  genglei
 * *** empty log message ***
 *
 * Revision 1.11  2006/07/23 03:44:39  genglei
 * ���Ȩ�޶����ȡ��
 *
 * Revision 1.10  2006/07/13 08:31:22  mufsh
 * ��ӷ��� getPageMessage(HttpServletRequest request)
 *
 * Revision 1.9  2006/07/13 06:26:14  genglei
 * Ĭ�����Դ���
 *
 * Revision 1.8  2006/07/13 01:23:57  mufsh
 * ��ȡת�����ö���
 *
 * Revision 1.7  2006/07/12 07:07:12  mufsh
 * Modify Method------getPageMessage
 *
 * Revision 1.6  2006/07/12 07:04:37  genglei
 * *** empty log message ***
 *
 * Revision 1.5  2006/07/12 05:39:11  mufsh
 * ��ȡת�����ö���
 *
 * Revision 1.4  2006/07/11 14:09:28  genglei
 * ��¶ȡ������Ϣ����
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
import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.inspur.eoms.common.PageMessage;
import com.inspur.eoms.common.platform.core.Platform;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.inspur.authority.CookieManage;
import com.inspur.authority.UserFacade;
import com.inspur.eoms.api.IUserInfo;
import com.inspur.eoms.common.UserConstants;
import com.langchao.comm.authority.UserinfoObj;
import com.langchao.comm.tree.AuthorityTree;

/**
 * ��ȡ�û�session�е���用户session的工具类
 * 
 * @author Administrator
 * 
 */
public class UserSession  extends UserFacade implements Serializable {
	private static final Logger log = Logger.getLogger(UserSession.class);
	public UserSession(HttpSession aSession) {
		super(aSession);
	}

	public final static String SESSION_NAME = "userSession";

	public final static String EOMS_SYS_NAME = "EomsSession";

	public final static String AUTH_SYS_NAME = "UserinfoSen";

	private static final long serialVersionUID = 2L;
	/********
	 * 将对象压入session中
	 * @param session
	 * @param key
	 * @param value
	 */
	public static void setEomsSessionConfig(HttpSession session,String key,Object value){
		IUserInfo userInfo = getUserInfo(session);
		userInfo.addUserConfig(key,value);
	}
	/********
	 * 获取session中的对象
	 * @param session
	 * @param key
	 * @return
	 */
	public static Object getEomsSessionConfig(HttpSession session,String key){
		UserInfo userInfo = (UserInfo)session.getAttribute(UserSession.SESSION_NAME);
		if(userInfo==null||userInfo.getUserInfo()==null){
			return null;
		}
		if (userInfo.getUserInfo().get(key) == null)
			return null;
		else
			return userInfo.getUserInfo().get(key);
	}


	/**
	 * 取得登陆账号信息.
	 * 
	 * @param userInfo
	 * @return
	 */
	public static String getUserAccount(IUserInfo userInfo) {
		return userInfo.getUseraccount();
	}
	public static String getUserAccount(HttpSession session) {
		return getUserAccount(getUserInfo(session));
	}
	public static IUserInfo getUserInfo(HttpSession session){
		return (IUserInfo)session.getAttribute(SESSION_NAME);
	}
	
	public static UserinfoObj getUserInfoObj(IUserInfo userInfo){
		return userInfo.getUserInfoObj();
	}

	/**
	 * 用户注销
	 * 
	 * @param req
	 */
	public static void logout(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession(false);

		Statement sta = null;
		Connection conn = null;
		UserinfoObj userInfoObj = (UserinfoObj)session.getAttribute(AUTH_SYS_NAME);
		String sql = "update SYS_LOG set logout_time = sysdate where log_id ='"
				+ userInfoObj.getLogid() + "'";
		try {
			try {
				conn = ((JdbcTemplate) (Platform.getInstance().getApplication()
						.getBean("spareJdbcTemplate"))).getDataSource()
						.getConnection();
				sta = conn.createStatement();
				sta.executeUpdate(sql);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			log.error("UserSession 中的方法logout增加注销时间失败：" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (sta != null) {
				try {
					sta.close();
				} catch (Exception ex) {
					log.error("关闭数据库Statement失败：" + ex.getMessage());
					ex.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ex) {
					log.error("关闭数据库连接失败：" + ex.getMessage());
					ex.printStackTrace();

				}
			}
		}

		if (session != null) {
			// logoutCognos(req,res,session);
			session.invalidate();
			session = null;
		}
		// 删除cookie
		CookieManage manage = new CookieManage();
		manage.clearAccountCookie(req, res);
	}
	
	/**
	 * 取得登陆用户所属公司名
	 * 
	 * @param request
	 * @return
	 */
/*	public static String getLoginUserComp(HttpServletRequest request) {
		UserinfoObj senuserObj = (UserinfoObj) getPurView(request);
		return senuserObj.getCompname().trim();
	}*/

	/**
	 * 取得登陆用户所属公司名
	 * 
	 * @param request
	 * @return
	 */
/*	public static String getLoginUserComp(Map session) {
		UserinfoObj senuserObj = (UserinfoObj) getPurView(session);
		return senuserObj.getCompname().trim();
	}*/

	/**
	 * 取得登陆用户名
	 * 
	 * @param request
	 * @return
	 */
/*	public static String getLoginUserName(HttpServletRequest request) {
		UserinfoObj senuserObj = (UserinfoObj) getPurView(request);
		return senuserObj.getEmpname();
	}*/

	/**
	 * 取得登陆用户名
	 * 
	 * @param request
	 * @return
	 */
/*	public static String getLoginUserName(Map session) {
		UserinfoObj senuserObj = (UserinfoObj) getPurView(session);
		return senuserObj.getEmpname();
	}*/

	/**
	 * 取得登陆用户编号
	 * 
	 * @param request
	 * @return
	 */
/*	public static int getLoginUserId(HttpServletRequest request) {
		UserinfoObj senuserObj = (UserinfoObj) getPurView(request);
		return senuserObj.getUserid();
	}*/

	/**
	 * 取得登陆员工编号
	 * 
	 * @param request
	 * @return
	 */
/*	public static int getLoginEmpId(HttpServletRequest request) {
		UserinfoObj senuserObj = (UserinfoObj) getPurView(request);
		return senuserObj.getEmpid();
	}*/

	/**
	 * 取得登陆员工编号
	 * 
	 * @param request
	 * @return
	 */
/*	public static int getLoginEmpId(Map session) {
		UserinfoObj senuserObj = (UserinfoObj) session.get(AUTH_SYS_NAME);
		return senuserObj.getEmpid();
	}

	public static UserInfo getUserInfo(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		return (UserInfo) session.getAttribute(EOMS_SYS_NAME);
	}

	public static UserInfo getUserInfo(Map session) {
		return (UserInfo) session.get(EOMS_SYS_NAME);
	}*/

	/**
	 * 取得是否包含权限
	 * 
	 * @param request
	 * @param type
	 * @param parent
	 * @param funcName
	 * @return
	 */
/*	public static boolean hasFunction(HttpServletRequest request, String type,
			String parent, String funcName) {
		String allFunctions = getChildFunctions(request, type, parent);
		return TextParser.find(allFunctions, funcName);
	}*/

	/**
	 * 取得指定类型，指定模块的所有子权限
	 * 
	 * @param request
	 * @param type
	 * @param parent
	 * @return
	 */
	public static String getChildFunctions(HttpServletRequest request,
			String type, String parent) {
		IUserInfo userInfo = (IUserInfo)request.getSession().getAttribute(SESSION_NAME);
		UserinfoObj senuserObj = (UserinfoObj) getUserInfoObj(userInfo);
		AuthorityTree aTree = (AuthorityTree) senuserObj.getAuthoritytree();
		// 获取该模块下的功能。
		return aTree.getChildFunctionByName(type, parent);
	}

	/**
	 * 判断是否已经登陆。(按照权限的session的键值为依据)
	 * 
	 * @param req
	 * @return
	 */
	public static boolean isLogin(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		return isLoginBySession(session);
	}
	/**
	 * 判断是否已经登陆。(按照权限的session的键值为依据)
	 * 
	 * @param
	 * @return
	 */
	public static boolean isLoginBySession(HttpSession session) {
		if (session == null || session.getAttribute(AUTH_SYS_NAME) == null)
			return false;
		return true;
	}
	/**
	 * 取得权限对象。
	 * 
	 * @param session
	 * @return
	 */
/*	public static Object getPurView(Map session) {
		return session.get(AUTH_SYS_NAME);
	}*/

	/**
	 * 取得权限对象。
	 * 
	 * @param req
	 * @return
	 */
/*	public static Object getPurView(HttpServletRequest req) {
		return req.getSession().getAttribute(AUTH_SYS_NAME);
	}*/

	/**
	 * 读取用于的所有配置信息
	 * 
	 * @param session
	 * @return
	 */
/*	public static Map getCongfiures(Map session) {
		UserInfo userInfo = (UserInfo) session.get(EOMS_SYS_NAME);
		if (userInfo == null) {
			return null;
		} else {
			return userInfo.getUserInfo();
		}
	}*/

	/**
	 * 读取session中的配置信息
	 * 
	 * @param req
	 * @param key
	 * @return
	 */
/*	public static String getProperty(HttpServletRequest req, String key) {
		UserInfo userInfo = getUserInfo(req);
		if (userInfo.get(key) == null)
			return "";
		else
			return userInfo.get(key).toString();

	}*/

	/**
	 * 读取session中的配置信息
	 * 
	 * @param session
	 * @param key
	 * @return
	 */
/*	public static String getProperty(Map session, String key) {
		UserInfo userInfo = getUserInfo(session);
		if (userInfo.get(key) == null)
			return "";
		else
			return userInfo.get(key).toString();

	}*/

/*	public static void setProperty(Map session, String key, String value) {
		UserInfo userInfo = getUserInfo(session);
		userInfo.set(key, value);
	}*/

	/** ***********PageSize*********** */

	/**
	 * 读取页面行数信息
	 * 
	 * @param session
	 * @return
	 */
	public static int getPageSize(Map session) {
		if (session != null && session.get(SESSION_NAME) != null) {
			UserInfo userInfo = (UserInfo) session.get(SESSION_NAME);
			Object size = userInfo.getUserInfo().get(UserConstants.USERPAGESIZE);
			if(size==null || "".equals(size)){
				return UserConstants.DEFAULTPAGESIZE;
			}
			return Integer.parseInt(size.toString());
		} else {
			return UserConstants.DEFAULTPAGESIZE;
		}
	}

	/** ***********Language*********** */
	/**
	 * ȡ���û�������取得语言参考�û�����
	 * 
	 * @param req
	 * @return
	 */
	public static String getLanguagePrefernce(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		String langRef = null;
		if (session != null && session.getAttribute(SESSION_NAME) != null) {
			Object langObj = ((UserInfo) session.getAttribute(SESSION_NAME)).getUserInfo()
					.get(UserConstants.USERLANGUAGE);
			if (langObj == null) {
				langRef = defaultLanguage(req);
			} else {
				langRef = langObj.toString();
			}
		} else {
			langRef = defaultLanguage(req);
		}

		return langRef;
	}
	
	/**
	 * 取得用户的制定的样式文件，如果没有制定，将取得UserConstants.DEFAULTUSERSTYLE的值.
	 * 
	 * @param req
	 * @return
	 */
	public static String getSkinPrefernce(HttpServletRequest req){
		HttpSession session = req.getSession(false);
		String styleRef = UserConstants.DEFAULTUSERSTYLE;
		if (session != null && session.getAttribute(SESSION_NAME) != null) {
			Object styleObj = ((UserInfo) session.getAttribute(SESSION_NAME)).getUserInfo()
					.get(UserConstants.USERSTYLE);
			if (styleObj != null) {
				styleRef = styleObj.toString();
			}
		} 
		return styleRef;
	}

	/** ***********Language*********** */
	/**
	 * ȡ���û�������取得语言参考�û�����
	 * 
	 * @param
	 * @return
	 */
	public static String getLanguagePrefernce(Map session) {
		String styleRef = UserConstants.DEFAULTUSERSTYLE;
		if (session != null && session.get(SESSION_NAME) != null) {
			Object styleObj = ((UserInfo) session.get(SESSION_NAME)).getUserInfo()
					.get(UserConstants.USERSTYLE);
			if (styleObj != null) {
				styleRef = styleObj.toString();
			}
		} 
		return styleRef;
	}

	/**
	 * 取得缺省的语言，从request中读取。然后设置userinfo中的默认语言
	 * 
	 * @param req
	 * @return
	 */
	private static String defaultLanguage(HttpServletRequest req) {
		String defaultL = req.getHeader("Accept-Language");
		String lang = "en";
		if (defaultL != null && defaultL.toLowerCase().indexOf("zh") >= 0) {
			lang = "zh";
		}
		return lang;
	}

	/**
	 * 取消session
	 * 
	 * @param req
	 */
	public static void invalidate(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
			session = null;
		}
	}

	/**
	 * 返回页面提示对象
	 * 
	 * @param session
	 * @return
	 */
	public static PageMessage getPageMessage(Map session) {
		PageMessage pageMessage = new PageMessage();
		
		// 国际化
		String msgContent = "";
		String msgTitle = "";
		if ("zh".equals(getLanguagePrefernce(session))) {
			msgContent = Platform.getInstance().getGlobalConfig("msg.content_zh");
			msgTitle = Platform.getInstance().getGlobalConfig("msg.title_zh");
		} else {
			msgContent = Platform.getInstance().getGlobalConfig("msg.content_en");
			msgTitle = Platform.getInstance().getGlobalConfig("msg.title_en");
		}
		pageMessage.setMsgContent(msgContent);
		pageMessage.setMsgTitle(msgTitle);
		if ("".equals(Platform.getInstance().getGlobalConfig("msg.to_time"))
				|| null == Platform.getInstance().getGlobalConfig("msg.to_time")) {
			pageMessage.setMsgToTime(1);
		} else {
			pageMessage.setMsgToTime(new Integer(Platform.getInstance().getGlobalConfig("msg.to_time")).intValue());
		}
		// pagemessage.setMsgToURL(getProperty(session, "msg.to_url"));
		return pageMessage;
	}

	/**
	 * 返回页面提示对象
	 * 
	 * @param
	 * @return
	 */
	public static PageMessage getPageMessage(HttpServletRequest request) {
		PageMessage pageMessage = new PageMessage();
		
		// 国际化
		String msgContent = "";
		String msgTitle = "";
		if ("zh".equals(getLanguagePrefernce(request))) {
			msgContent = Platform.getInstance().getGlobalConfig("msg.content_zh");
			msgTitle = Platform.getInstance().getGlobalConfig("msg.title_zh");
		} else {
			msgContent = Platform.getInstance().getGlobalConfig("msg.content_en");
			msgTitle = Platform.getInstance().getGlobalConfig("msg.title_en");
		}
		pageMessage.setMsgContent(msgContent);
		pageMessage.setMsgTitle(msgTitle);
		if ("".equals(Platform.getInstance().getGlobalConfig("msg.to_time"))
				|| null == Platform.getInstance().getGlobalConfig("msg.to_time")) {
			pageMessage.setMsgToTime(1);
		} else {
			pageMessage.setMsgToTime(new Integer(Platform.getInstance().getGlobalConfig("msg.to_time")).intValue());
		}
		// pagemessage.setMsgToURL(getProperty(session, "msg.to_url"));
		return pageMessage;
	}
	
	
	public static void setUserConfigure(HttpSession session,String key,String value){
		UserInfo userInfo = (UserInfo)session.getAttribute(UserSession.SESSION_NAME);
		Map userMap = userInfo.getUserInfo();
		userMap.put(key,value);
	}
	/**
	 * 读取session中的配置信息
	 * 
	 * @param session
	 * @param key
	 * @return
	 */
	public static String getUserConfigure(HttpSession session, String key) {
		UserInfo userInfo = (UserInfo)session.getAttribute(UserSession.SESSION_NAME);
		if(userInfo==null||userInfo.getUserInfo()==null){
			return "";
		}
		if (userInfo.getUserInfo().get(key) == null)
			return "";
		else
			return userInfo.getUserInfo().get(key).toString();

	}
}
