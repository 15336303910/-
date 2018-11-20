package com.systemConfig.model;
import java.io.Serializable;

import net.sf.json.JSONObject;
public class SystemOrganization implements Serializable{

	private static final long serialVersionUID = 8164379098224935866L;

	private Integer ID;
	private String ORGANIZATION_NAME;
	private String ORGANIZATION_DESC;
	private String ORGANIZATION_LEVEL;
	private Integer PARENT_CODE;
	
	public SystemOrganization(){
		
	}
	
	public SystemOrganization(JSONObject jsonObject,Boolean isCreate){
		if(!isCreate){
			this.setID(Integer.parseInt(jsonObject.get("departCode").toString()));
		}
		this.setORGANIZATION_NAME(jsonObject.getString("departName"));
		this.setORGANIZATION_DESC(jsonObject.getString("departDescribe"));
		this.setORGANIZATION_LEVEL(jsonObject.getString("departLevel"));
		this.setPARENT_CODE(Integer.parseInt(jsonObject.getString("parentDepart")));
	}
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getORGANIZATION_NAME() {
		return ORGANIZATION_NAME;
	}
	public void setORGANIZATION_NAME(String oRGANIZATION_NAME) {
		ORGANIZATION_NAME = oRGANIZATION_NAME;
	}
	public String getORGANIZATION_DESC() {
		return ORGANIZATION_DESC;
	}
	public void setORGANIZATION_DESC(String oRGANIZATION_DESC) {
		ORGANIZATION_DESC = oRGANIZATION_DESC;
	}
	public String getORGANIZATION_LEVEL() {
		return ORGANIZATION_LEVEL;
	}
	public void setORGANIZATION_LEVEL(String oRGANIZATION_LEVEL) {
		ORGANIZATION_LEVEL = oRGANIZATION_LEVEL;
	}
	public Integer getPARENT_CODE() {
		return PARENT_CODE;
	}
	public void setPARENT_CODE(Integer pARENT_CODE) {
		PARENT_CODE = pARENT_CODE;
	}
}
