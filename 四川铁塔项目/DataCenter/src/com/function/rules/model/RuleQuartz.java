package com.function.rules.model;
import java.io.Serializable;

import net.sf.json.JSONObject;
public class RuleQuartz implements Serializable{

	private static final long serialVersionUID = 8019731402747376163L;

	private Integer ID;
	private Integer BELONG_RULE;
	private String CIRCLE_TYPE;
	private String DAY_OF_WEEK;
	private String DAY_OF_MONTH;
	private String HOUR_VAR;
	private String MINUTE_VAR;
	private String SECOND_VAR;
	
	public RuleQuartz(){}
	
	public RuleQuartz(Integer ruleId,JSONObject obj){
		//1.所属规则
		this.setBELONG_RULE(ruleId);
		//2.粒度
		this.setCIRCLE_TYPE(obj.get("CIRCLE_TYPE")==null?null:obj.getString("CIRCLE_TYPE"));
		if(obj.get("CIRCLE_TYPE")!=null && !"无".equals(obj.getString("CIRCLE_TYPE"))){
			//3.小时
			this.setHOUR_VAR(obj.get("HOUR_VAR")==null?null:obj.getString("HOUR_VAR"));
			//4.分钟
			this.setMINUTE_VAR(obj.get("MINUTE_VAR")==null?null:obj.getString("MINUTE_VAR"));
			//5.秒
			this.setSECOND_VAR(obj.get("SECOND_VAR")==null?null:obj.getString("SECOND_VAR"));
			if("周".equals(obj.getString("CIRCLE_TYPE"))){
				//6.1.周
				this.setDAY_OF_WEEK(obj.get("DAY_OF_WEEK")==null?null:obj.getString("DAY_OF_WEEK"));
			}else if("月".equals(obj.getString("CIRCLE_TYPE"))){
				//6.2.月
				this.setDAY_OF_MONTH(obj.get("DAY_OF_MONTH")==null?null:obj.getString("DAY_OF_MONTH"));
			}
		}
	}
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Integer getBELONG_RULE() {
		return BELONG_RULE;
	}
	public void setBELONG_RULE(Integer bELONG_RULE) {
		BELONG_RULE = bELONG_RULE;
	}
	public String getCIRCLE_TYPE() {
		return CIRCLE_TYPE;
	}
	public void setCIRCLE_TYPE(String cIRCLE_TYPE) {
		CIRCLE_TYPE = cIRCLE_TYPE;
	}
	public String getDAY_OF_WEEK() {
		return DAY_OF_WEEK;
	}
	public void setDAY_OF_WEEK(String dAY_OF_WEEK) {
		DAY_OF_WEEK = dAY_OF_WEEK;
	}
	public String getDAY_OF_MONTH() {
		return DAY_OF_MONTH;
	}
	public void setDAY_OF_MONTH(String dAY_OF_MONTH) {
		DAY_OF_MONTH = dAY_OF_MONTH;
	}
	public String getHOUR_VAR() {
		return HOUR_VAR;
	}
	public void setHOUR_VAR(String hOUR_VAR) {
		HOUR_VAR = hOUR_VAR;
	}
	public String getMINUTE_VAR() {
		return MINUTE_VAR;
	}
	public void setMINUTE_VAR(String mINUTE_VAR) {
		MINUTE_VAR = mINUTE_VAR;
	}
	public String getSECOND_VAR() {
		return SECOND_VAR;
	}
	public void setSECOND_VAR(String sECOND_VAR) {
		SECOND_VAR = sECOND_VAR;
	}
}
