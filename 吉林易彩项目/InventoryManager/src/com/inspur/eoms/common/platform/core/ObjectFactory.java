/*
 * @(#) $Id: ObjectFactory.java,v 1.1 2006/11/22 10:10:25 genglei Exp $
 * $Revision: 1.1 $
 * $Date: 2006/11/22 10:10:25 $
 * 
 * $Log: ObjectFactory.java,v $
 * Revision 1.1  2006/11/22 10:10:25  genglei
 * �����µ��ۺϰ汾������ά
 *
 * 
 * ===================================
 * Electric Operation Maintenance System(EOMS)
 * 
 * Copyright (c) 2006 by INSPUR LG, Inc.
 * All rights reserved.
 */
package com.inspur.eoms.common.platform.core;

import org.springframework.context.ApplicationContext;

import com.inspur.eoms.api.IConfiguration;
import com.inspur.eoms.api.ILogger;

/**
 * 平台对象工程
 * 
 * @author Administrator
 * 
 */
public class ObjectFactory {

	/**
	 * 取得日志对象
	 * 
	 * @param name
	 * @return
	 */
	public static ILogger getLogger(String name) {
		return PlatformManager.getInstance().getLogger().getLogger(name);
	}

	/**
	 * 取得日志对象
	 * 
	 * @param classz
	 * @return
	 */
	public static ILogger getLogger(Class classz) {
		return PlatformManager.getInstance().getLogger().getLogger(classz);
	}

	/**
	 * 取得全局配置对象
	 * 
	 * @return 配置对象
	 */
	public static IConfiguration getConfig() {
		return PlatformManager.getInstance().getConfig();
	}

	/**
	 * 取得spring的applicationContext.
	 * 
	 * @return ApplicationContext 
	 */
	public static ApplicationContext getApplicationContext() {
		return Platform.getInstance().getApplication();
	}

}
