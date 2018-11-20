package com.function.nettool.model;
import java.io.Serializable;
import java.util.Date;
public class NetToolDetail implements Serializable{

	private static final long serialVersionUID = 3886124139869895174L;

	private Integer ID;
	private String APP_ICON;
	private String APP_NAME;
	private String APP_URL;
	private Integer PUBLISH_USER_CODE;
	private Date PUBLISH_DATE;
	private String APP_VERSION;
	private String APP_DESC;
	private String APP_LANGUAGE;
	private String SEARCH_KEY;
	private String OTHER_DESC;
	private String APP_DESC_IMAGE;
	private String IS_EXPORT;
	private String IS_DOWNLOAD;
	private String TOOL_NAME;
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getIS_DOWNLOAD() {
		return IS_DOWNLOAD;
	}
	public void setIS_DOWNLOAD(String iS_DOWNLOAD) {
		IS_DOWNLOAD = iS_DOWNLOAD;
	}
	public String getTOOL_NAME() {
		return TOOL_NAME;
	}
	public void setTOOL_NAME(String tOOL_NAME) {
		TOOL_NAME = tOOL_NAME;
	}
	public String getIS_EXPORT() {
		return IS_EXPORT;
	}
	public void setIS_EXPORT(String iS_EXPORT) {
		IS_EXPORT = iS_EXPORT;
	}
	public String getAPP_ICON() {
		return APP_ICON;
	}
	public void setAPP_ICON(String aPP_ICON) {
		APP_ICON = aPP_ICON;
	}
	public String getAPP_NAME() {
		return APP_NAME;
	}
	public void setAPP_NAME(String aPP_NAME) {
		APP_NAME = aPP_NAME;
	}
	public String getAPP_URL() {
		return APP_URL;
	}
	public void setAPP_URL(String aPP_URL) {
		APP_URL = aPP_URL;
	}
	public Integer getPUBLISH_USER_CODE() {
		return PUBLISH_USER_CODE;
	}
	public void setPUBLISH_USER_CODE(Integer pUBLISH_USER_CODE) {
		PUBLISH_USER_CODE = pUBLISH_USER_CODE;
	}
	public Date getPUBLISH_DATE() {
		return PUBLISH_DATE;
	}
	public void setPUBLISH_DATE(Date pUBLISH_DATE) {
		PUBLISH_DATE = pUBLISH_DATE;
	}
	public String getAPP_VERSION() {
		return APP_VERSION;
	}
	public void setAPP_VERSION(String aPP_VERSION) {
		APP_VERSION = aPP_VERSION;
	}
	public String getAPP_DESC() {
		return APP_DESC;
	}
	public void setAPP_DESC(String aPP_DESC) {
		APP_DESC = aPP_DESC;
	}
	public String getAPP_LANGUAGE() {
		return APP_LANGUAGE;
	}
	public void setAPP_LANGUAGE(String aPP_LANGUAGE) {
		APP_LANGUAGE = aPP_LANGUAGE;
	}
	public String getSEARCH_KEY() {
		return SEARCH_KEY;
	}
	public void setSEARCH_KEY(String sEARCH_KEY) {
		SEARCH_KEY = sEARCH_KEY;
	}
	public String getOTHER_DESC() {
		return OTHER_DESC;
	}
	public void setOTHER_DESC(String oTHER_DESC) {
		OTHER_DESC = oTHER_DESC;
	}
	public String getAPP_DESC_IMAGE() {
		return APP_DESC_IMAGE;
	}
	public void setAPP_DESC_IMAGE(String aPP_DESC_IMAGE) {
		APP_DESC_IMAGE = aPP_DESC_IMAGE;
	}
}
