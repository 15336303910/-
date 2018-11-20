/*
 * @(#) 所有版权归浪潮乐金信息系统有限公司所有.
 * 
 * $Id$
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
import com.inspur.eoms.api.ILogger;


public class PlatformManager {

	private IConfiguration config;

	private ILogger logger = null;
	

	public void init(Properties properties) {
		config = Configuration.getInstance();
		this.config.setConfig(properties);
		
		// log
		String logClass = ObjectFactory.getConfig().getConfig(PlatformConstants.LOG_CLASS_NAME);
		try {
			logger = (ILogger) Class.forName(logClass).newInstance();
			logger.configure(ObjectFactory.getConfig().getConfigs());
			logger = ObjectFactory.getLogger(PlatformManager.class);
		} catch (Throwable e) {
			String mess = "PlatformManager -> Problems instantiating core managers -";
			if (logger == null) {
				mess += " Can't create " + logClass + " class.";
			}
			System.err.println(mess);
		}
		logger.debug("PlatformManager -> init log finished.");
		//
	}

	// single
	private static PlatformManager manager;
	static {
		manager = new PlatformManager();
	}

	private PlatformManager() {
	}

	public static PlatformManager getInstance() {
		return manager;
	}

	//
	public ILogger getLogger() {
		return logger;
	}

	public void setLogger(ILogger pfLogger) {
		this.logger = pfLogger;
	}

	public IConfiguration getConfig() {
		return config;
	}

	public void setConfig(IConfiguration config) {
		this.config = config;
	}
}
