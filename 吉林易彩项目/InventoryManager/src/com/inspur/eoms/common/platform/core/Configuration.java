/*
 * @(#) $Id: Configuration.java,v 1.1 2006/11/22 10:10:25 genglei Exp $
 * $Revision: 1.1 $
 * $Date: 2006/11/22 10:10:25 $
 * 
 * $Log: Configuration.java,v $
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

import java.util.Properties;

import com.inspur.eoms.api.IConfiguration;

public class Configuration implements IConfiguration {

	private Properties config = new Properties();

	/**
	 * 取得指定key的全局配置信息。
	 * 
	 * @param key
	 * @return
	 */
	public String getConfig(String key) {
		return config.getProperty(key);
	}

	/**
	 * 取得全局配置集合.
	 * 
	 * @return
	 */
	public Properties getConfigs() {
		return config;
	}

	// single
	public static Configuration configuration;
	static {
		configuration = new Configuration();
	}

	private Configuration() {

	}

	public static IConfiguration getInstance() {
		return configuration;
	}

	public void setConfig(Properties config) {
		this.config = config;
	}

}
