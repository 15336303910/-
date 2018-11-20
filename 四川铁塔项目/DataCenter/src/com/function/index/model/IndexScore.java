package com.function.index.model;
import java.io.Serializable;
import java.util.Date;
public class IndexScore implements Serializable{
	
	private static final long serialVersionUID = 7188802036657805392L;

	private Integer ID;
	private Integer INDEX_ID;
	private Date LAST_AUDIT_DATE;
	private String IS_OK;
	private String ACTION_DESCRIBE;
	private Double FINAL_SCORE;
	
	public Integer getID() {
		return ID;
	}
	
	public void setID(Integer iD) {
		ID = iD;
	}
	
	public Integer getINDEX_ID() {
		return INDEX_ID;
	}
	
	public void setINDEX_ID(Integer iNDEX_ID) {
		INDEX_ID = iNDEX_ID;
	}
	
	public Date getLAST_AUDIT_DATE() {
		return LAST_AUDIT_DATE;
	}
	
	public void setLAST_AUDIT_DATE(Date lAST_AUDIT_DATE) {
		LAST_AUDIT_DATE = lAST_AUDIT_DATE;
	}
	
	public String getIS_OK() {
		return IS_OK;
	}
	
	public void setIS_OK(String iS_OK) {
		IS_OK = iS_OK;
	}
	
	public String getACTION_DESCRIBE() {
		return ACTION_DESCRIBE;
	}
	
	public void setACTION_DESCRIBE(String aCTION_DESCRIBE) {
		ACTION_DESCRIBE = aCTION_DESCRIBE;
	}
	
	public Double getFINAL_SCORE() {
		return FINAL_SCORE;
	}
	
	public void setFINAL_SCORE(Double fINAL_SCORE) {
		FINAL_SCORE = fINAL_SCORE;
	}
}
