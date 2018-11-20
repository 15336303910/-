package com.systemConfig.model;
import java.io.Serializable;
import net.sf.json.JSONObject;
public class SystemMenu implements Serializable{

	private static final long serialVersionUID = 4756087010918598311L;
	private Integer ID;
	private String MENU_NAME;
	private String IS_LEAF;
	private String MENU_URL;
	private String IS_USING;
	private Integer MENU_PARENT;
	private Integer MENU_SORT;
	private String ICON_NAME;
	private String MENU_TOKEN;
	private String IS_EXPORT;
	
	public SystemMenu(){
		
	}
	
	public SystemMenu(JSONObject thisObject,Boolean isCreate){
		if(!isCreate){
			this.setID(Integer.parseInt(thisObject.get("menuCode").toString()));
		}
		this.setMENU_NAME(thisObject.getString("menuName"));
		this.setIS_LEAF(thisObject.getString("isLeaf"));
		this.setMENU_URL(thisObject.getString("menuUrl"));
		this.setIS_USING(thisObject.getString("isUsing"));
		this.setMENU_PARENT(Integer.parseInt(thisObject.getString("parentCode")));
		this.setIS_EXPORT(thisObject.getString("isExport"));
	}
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getIS_EXPORT() {
		return IS_EXPORT;
	}

	public void setIS_EXPORT(String iS_EXPORT) {
		IS_EXPORT = iS_EXPORT;
	}

	public String getMENU_NAME() {
		return MENU_NAME;
	}
	public void setMENU_NAME(String mENU_NAME) {
		MENU_NAME = mENU_NAME;
	}
	public String getIS_LEAF() {
		return IS_LEAF;
	}
	public void setIS_LEAF(String iS_LEAF) {
		IS_LEAF = iS_LEAF;
	}
	public String getMENU_URL() {
		return MENU_URL;
	}
	public void setMENU_URL(String mENU_URL) {
		MENU_URL = mENU_URL;
	}
	public String getIS_USING() {
		return IS_USING;
	}
	public void setIS_USING(String iS_USING) {
		IS_USING = iS_USING;
	}
	public Integer getMENU_PARENT() {
		return MENU_PARENT;
	}
	public void setMENU_PARENT(Integer mENU_PARENT) {
		MENU_PARENT = mENU_PARENT;
	}
	public Integer getMENU_SORT() {
		return MENU_SORT;
	}
	public void setMENU_SORT(Integer mENU_SORT) {
		MENU_SORT = mENU_SORT;
	}
	public String getICON_NAME() {
		return ICON_NAME;
	}
	public void setICON_NAME(String iCON_NAME) {
		ICON_NAME = iCON_NAME;
	}
	public String getMENU_TOKEN() {
		return MENU_TOKEN;
	}
	public void setMENU_TOKEN(String mENU_TOKEN) {
		MENU_TOKEN = mENU_TOKEN;
	}	
}
