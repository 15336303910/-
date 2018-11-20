/*
 * @(#) $Id: ScriptTag.java,v 1.2 2006/12/15 12:54:01 genglei Exp $
 * $Revision: 1.2 $
 * $Date: 2006/12/15 12:54:01 $
 * 
 * $Log: ScriptTag.java,v $
 * Revision 1.2  2006/12/15 12:54:01  genglei
 * �޸Ľű���֧�ֹ�ʻ�
 *
 * Revision 1.1  2006/11/22 10:10:24  genglei
 * �����µ��ۺϰ汾������ά
 *
 * 
 * ===================================
 * Electric Operation Maintenance System(EOMS)
 * 
 * Copyright (c) 2006 by INSPUR LG, Inc.
 * All rights reserved.
 */
package com.inspur.eoms.common.tablibs;

import com.inspur.eoms.common.user.UserSession;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ScriptTag extends TagSupport {
	private static final long serialVersionUID = 2L;

	private String id="";
	private String lang="";
	private String language="javascript";
	private String file="";
	private String skin="";
	private String inner="jslib";
	private String dir="common-skins";
	private String extend="";
	//国际化参考,取值：default活着实际参考值.
	private String location="";
	/*
	 * 判断权限，如果没有权限，跳出body。
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		String userStyle = UserSession.getSkinPrefernce(request);
		StringBuffer sb = new StringBuffer();
		sb.append("<script");
		if(!"".equals(file)){
			sb.append(" src=\"").append(request.getContextPath());
			sb.append("/").append(dir);
			if(userStyle==null || "".equals(userStyle)){
				userStyle = "default";
			}
			if(!"".equals(skin)){
				userStyle = skin;
			}
			sb.append("/").append(userStyle);
			if(!"".equals(inner)){
				sb.append("/").append(inner);
			}
			
			//根据需要组合国际化语言名称.
			if(!"".equals(location)){
				if("default".equals(location.toLowerCase())){
					file = getLocationFileName(file,UserSession.getLanguagePrefernce(request));
				}else{
					file = getLocationFileName(file,location);
				}
			}
			sb.append("/").append(file);
			sb.append("\""); 
		}
		//
		if(!"".equals(id)){
			sb.append(" id='").append(id).append("'");
		}
		if(!"".equals(lang)){
			sb.append(" lang='").append(lang).append("'");
		}
		if(!"".equals(language)){
			sb.append(" language='").append(language).append("'");
		}
		//
		if(!"".equals(extend)){
			sb.append(" ").append(extend);
		}
		sb.append("></script>");
		try {
			this.pageContext.getOut().print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	
	private String getLocationFileName(String file,String append){
		if(file==null){
			return "";
		}
		int lastIndex = file.lastIndexOf(".");
		String fileName = file.substring(0,lastIndex);
		String fileBack = file.substring(lastIndex);
		return fileName+ "-" + append  +fileBack;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getInner() {
		return inner;
	}
	public void setInner(String inner) {
		this.inner = inner;
	}
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}