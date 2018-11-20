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
package com.inspur.eoms.common;

/**
 * 用于页面提示的信息类.
 * 
 * @author scud
 * 
 */
public class PageMessage {
	/** 标题 */
	private String msgTitle;

	/** 内容体 */
	private String msgContent;

	/** 要转向到的网址 */
	private String msgToURL;

	/** 停留时间 */
	private int msgToTime = 1;

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public int getMsgToTime() {

		return msgToTime;
	}

	public void setMsgToTime(int msgToTime) {
		this.msgToTime = msgToTime;
	}

	public String getMsgToURL() {
		return msgToURL;
	}

	public void setMsgToURL(String msgToURL) {
		this.msgToURL = msgToURL;
	}
}
