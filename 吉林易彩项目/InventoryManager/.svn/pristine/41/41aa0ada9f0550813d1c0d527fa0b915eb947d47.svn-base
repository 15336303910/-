/*
 * @(#) $Id: ILogger.java,v 1.1 2006/11/22 10:10:22 genglei Exp $
 * $Revision: 1.1 $
 * $Date: 2006/11/22 10:10:22 $
 * 
 * $Log: ILogger.java,v $
 * Revision 1.1  2006/11/22 10:10:22  genglei
 * �����µ��ۺϰ汾������ά
 *
 * 
 * ===================================
 * Electric Operation Maintenance System(EOMS)
 * 
 * Copyright (c) 2006 by INSPUR LG, Inc.
 * All rights reserved.
 */

package com.inspur.eoms.api;

import java.util.Properties;

public interface ILogger {

	/**
	 * debug message
	 * 
	 * @param message
	 */
	public void debug(Object message);
	public void debug(Object message, Exception ex);
	/**
	 * info message
	 * 
	 * @param message
	 */
	public void info(Object message);
	public void info(Object message, Exception ex);
	/**
	 * warn message
	 * 
	 * @param message
	 */
	public void warn(Object message);
	public void warn(Object message, Exception ex);

	/**
	 * error message
	 * 
	 * @param message
	 */
	public void error(Object message);
	
	public void error(Object message, Exception ex);
	
	/**
	 * fatal message
	 * 
	 * @param message
	 */
	public void fatal(Object message);

	/**
	 * operate log
	 * 
	 * @param message
	 */
	public void operate(Object message);

	/**
	 * 通过文件名称配置logger
	 * 
	 * @param name
	 */
	public void configure(String file);

	/**
	 * 通过properties配置
	 * 
	 * @param configs
	 */
	public void configure(Properties configs);

	/**
	 * 通过名称取得日志记录器
	 * 
	 * @param name
	 * @return
	 */
	public ILogger getLogger(String name);

	/**
	 * 通过Class取得日志记录器
	 * 
	 * @param name
	 * @return
	 */
	public ILogger getLogger(Class classz);
}