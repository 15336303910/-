package com.function.rules.model;
import java.io.Serializable;

import net.sf.json.JSONObject;
public class RuleItemConnect implements Serializable{

	private static final long serialVersionUID = -2091785673162806824L;

	private Integer ID;
	private Integer BELONG_ITEM;
	private Integer CHECK_COLUMN_ID;
	private Integer GLASS_COLUMN_ID;
	private String BACK_COLOR;
	
	public RuleItemConnect(){
		
	}
	
	public RuleItemConnect(JSONObject jsonObject){
		this.setCHECK_COLUMN_ID(Integer.parseInt(jsonObject.getString("keyOne")));
		this.setGLASS_COLUMN_ID(Integer.parseInt(jsonObject.getString("keyTwo")));
		this.setBACK_COLOR(jsonObject.getString("backColor"));
	}
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Integer getBELONG_ITEM() {
		return BELONG_ITEM;
	}
	public void setBELONG_ITEM(Integer bELONG_ITEM) {
		BELONG_ITEM = bELONG_ITEM;
	}
	public Integer getCHECK_COLUMN_ID() {
		return CHECK_COLUMN_ID;
	}
	public void setCHECK_COLUMN_ID(Integer cHECK_COLUMN_ID) {
		CHECK_COLUMN_ID = cHECK_COLUMN_ID;
	}
	public Integer getGLASS_COLUMN_ID() {
		return GLASS_COLUMN_ID;
	}
	public void setGLASS_COLUMN_ID(Integer gLASS_COLUMN_ID) {
		GLASS_COLUMN_ID = gLASS_COLUMN_ID;
	}
	public String getBACK_COLOR() {
		return BACK_COLOR;
	}
	public void setBACK_COLOR(String bACK_COLOR) {
		BACK_COLOR = bACK_COLOR;
	}
}
