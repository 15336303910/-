package com.function.index.model;
import java.io.Serializable;
import java.util.Date;
public class IndexDetail implements Serializable{

	private static final long serialVersionUID = -6976029246269166089L;

	private Integer ID;
	private String INDEX_NAME;
	private Date CREATE_DATE;
	private String IS_USING;
	private Integer INDEX_LEVEL;
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Integer getINDEX_LEVEL() {
		return INDEX_LEVEL;
	}
	public void setINDEX_LEVEL(Integer iNDEX_LEVEL) {
		INDEX_LEVEL = iNDEX_LEVEL;
	}
	public String getINDEX_NAME() {
		return INDEX_NAME;
	}
	public void setINDEX_NAME(String iNDEX_NAME) {
		INDEX_NAME = iNDEX_NAME;
	}
	public Date getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(Date cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	public String getIS_USING() {
		return IS_USING;
	}
	public void setIS_USING(String iS_USING) {
		IS_USING = iS_USING;
	}
}
