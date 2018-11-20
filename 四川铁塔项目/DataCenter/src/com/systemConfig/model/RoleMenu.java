package com.systemConfig.model;
import java.io.Serializable;
public class RoleMenu implements Serializable{

	private static final long serialVersionUID = 6036459265327339455L;
	private Integer ID;
	private Integer ROLE_ID;
	private Integer MENU_ID;
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Integer getROLE_ID() {
		return ROLE_ID;
	}
	public void setROLE_ID(Integer rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}
	public Integer getMENU_ID() {
		return MENU_ID;
	}
	public void setMENU_ID(Integer mENU_ID) {
		MENU_ID = mENU_ID;
	}	
}
