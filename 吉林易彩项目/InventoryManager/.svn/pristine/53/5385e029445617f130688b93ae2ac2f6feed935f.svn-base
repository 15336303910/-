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

import org.springframework.context.ApplicationContext;

public class Platform {

	private ApplicationContext application;

	public void init(Properties properties) {
		long tStart = System.currentTimeMillis();
		System.out.println("Platform -> platform start ...");
		PlatformManager manager = PlatformManager.getInstance();
		manager.init(properties);
		long tEnd = System.currentTimeMillis();
		System.out.println("Platform -> platform start finished. it lasted "+ getTimeDiff(tStart, tEnd));
	}
	
	//API
/*	public PLLogger getPLLogger(){
		return PlatformManager.getInstance().
	}
	*/
	
	public String getGlobalConfig(String key){
		return PlatformManager.getInstance().getConfig().getConfig(key);
	}
	
	

	private String getTimeDiff(long tStart, long tEnd) {
		long sec = 1000;
		long min = sec * 60;
		long hour = min * 60;
		long day = hour * 24;
		long month = day * 30;
		long year = 365 * day;

		// UTC is temporary realized to hold the time in miliss passed from ..
		// 1970
		long diffInMills = tEnd - tStart;
		if (diffInMills < min) {
			return String.valueOf(diffInMills / sec) + " [s]";
		} else if (diffInMills < hour) {
			long lmin = diffInMills / min;
			long lsec = (diffInMills - lmin * min) / sec;
			return String.valueOf(lmin) + " [min] " + String.valueOf(lsec) + " [s]";
		} else if (diffInMills < day) {
			long lhour = diffInMills / hour;
			long lmin = (diffInMills - lhour * hour) / min;
			long lsec = (diffInMills - lhour * hour - lmin * min) / sec;
			return String.valueOf(lhour) + " [h] " + String.valueOf(lmin) + " [min] "
					+ String.valueOf(lsec) + " [s]";
		} else if (diffInMills < month) {
			long lday = diffInMills / day;
			long lhour = (diffInMills - lday * day) / hour;
			long lmin = (diffInMills - lday * day - lhour * hour) / min;
			long lsec = (diffInMills - lday * day - lhour * hour - lmin * min) / sec;
			return String.valueOf(lday) + " [d] " + String.valueOf(lhour) + " [h] "
					+ String.valueOf(lmin) + " [min] " + String.valueOf(lsec)
					+ " [s]";
		} else if (diffInMills < year) {
			long mn = diffInMills / month;
			long lday = (diffInMills - mn * month) / day;
			long lhour = (diffInMills - mn * month - lday * day) / hour;
			long lmin = (diffInMills - mn * month - lday * day - lhour * hour) / min;
			long lsec = (diffInMills - mn * month - lday * day - lhour * hour - lmin * min)
					/ sec;
			return String.valueOf(mn) + " [m] " + String.valueOf(lday) + " [d] "
					+ String.valueOf(lhour) + " [h] " + String.valueOf(lmin)
					+ " [min] " + String.valueOf(lsec) + " [s]";
		} else { // if (diffInMills>=year)
			long lyear = diffInMills / year;
			long mn = (diffInMills - lyear * year) / month;
			long lday = (diffInMills - lyear * year - mn * month) / day;
			long lhour = (diffInMills - lyear * year - mn * month - lday * day) / hour;
			long lmin = (diffInMills - lyear * year - mn * month - lday * day - lhour * hour)
					/ min;
			long lsec = (diffInMills - lyear * year - mn * month - lday * day - lhour * hour - lmin
					* min)
					/ sec;
			return String.valueOf(lyear) + " [y] " + String.valueOf(mn) + " [m] "
					+ String.valueOf(lday) + " [d] " + String.valueOf(lhour) + " [h] "
					+ String.valueOf(lmin) + " [min] " + String.valueOf(lsec)
					+ " [s]";
		}
	}

	// /////////////////single
	private static Platform platform;
	static {
		platform = new Platform();
	}

	private Platform() {
	}

	public static Platform getInstance() {
		return platform;
	}

	public ApplicationContext getApplication() {
		return application;
	}

	public void setApplication(ApplicationContext application) {
		this.application = application;
	}
}

