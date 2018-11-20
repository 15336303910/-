package com.function.rules.model;
import java.io.Serializable;
import java.util.Date;
public class RuleJob implements Serializable{

	private static final long serialVersionUID = -803505328571050774L;

	private Integer ID;
	private Integer RULE_ID;
	private String RULE_NAME;
	private Date START_TIME;
	private Date END_TIME;
	private String IS_FINISHED;
	private String TOKEN;
	private Integer RECORD_TOTAL;
	private Integer FATUAL_TOTAL;
	private Double FATUAL_RATIO;
	private Double NORMAL_RATIO;
	
	public RuleJob(){
		
	}
	
	public RuleJob(Integer ruleId,String jobToken){
		this.setTOKEN("正常");
		this.setRULE_ID(ruleId);
		this.setSTART_TIME(new Date());
		this.setIS_FINISHED("N");
	}
	
	public Integer getID() {
		return ID;
	}
	
	public void setID(Integer iD) {
		ID = iD;
	}
	
	public Double getNORMAL_RATIO() {
		return NORMAL_RATIO;
	}

	public void setNORMAL_RATIO(Double nORMAL_RATIO) {
		NORMAL_RATIO = nORMAL_RATIO;
	}

	public Integer getRECORD_TOTAL() {
		return RECORD_TOTAL;
	}

	public void setRECORD_TOTAL(Integer rECORD_TOTAL) {
		RECORD_TOTAL = rECORD_TOTAL;
	}

	public Integer getFATUAL_TOTAL() {
		return FATUAL_TOTAL;
	}

	public void setFATUAL_TOTAL(Integer fATUAL_TOTAL) {
		FATUAL_TOTAL = fATUAL_TOTAL;
	}

	public Double getFATUAL_RATIO() {
		return FATUAL_RATIO;
	}

	public void setFATUAL_RATIO(Double fATUAL_RATIO) {
		FATUAL_RATIO = fATUAL_RATIO;
	}

	public String getRULE_NAME() {
		return RULE_NAME;
	}

	public void setRULE_NAME(String rULE_NAME) {
		RULE_NAME = rULE_NAME;
	}

	public String getTOKEN() {
		return TOKEN;
	}

	public void setTOKEN(String tOKEN) {
		TOKEN = tOKEN;
	}

	public Integer getRULE_ID() {
		return RULE_ID;
	}
	
	public void setRULE_ID(Integer rULE_ID) {
		RULE_ID = rULE_ID;
	}
	
	public Date getSTART_TIME() {
		return START_TIME;
	}
	
	public void setSTART_TIME(Date sTART_TIME) {
		START_TIME = sTART_TIME;
	}
	
	public Date getEND_TIME() {
		return END_TIME;
	}
	
	public void setEND_TIME(Date eND_TIME) {
		END_TIME = eND_TIME;
	}
	
	public String getIS_FINISHED() {
		return IS_FINISHED;
	}
	
	public void setIS_FINISHED(String iS_FINISHED) {
		IS_FINISHED = iS_FINISHED;
	}
}
