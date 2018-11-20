package com.function.rules.model;
import java.io.Serializable;
import java.util.Date;
public class RuleImport implements Serializable{

	private static final long serialVersionUID = 6968975943325602341L;

	private Integer ID;
	private String FILE_NAME;
	private Date UPLOAD_DATE;
	private String RULE_NAME;
	private String ACTION_RESULT;
	private Integer RULE_ID;
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Integer getRULE_ID() {
		return RULE_ID;
	}
	public void setRULE_ID(Integer rULE_ID) {
		RULE_ID = rULE_ID;
	}
	public String getFILE_NAME() {
		return FILE_NAME;
	}
	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}
	public Date getUPLOAD_DATE() {
		return UPLOAD_DATE;
	}
	public void setUPLOAD_DATE(Date uPLOAD_DATE) {
		UPLOAD_DATE = uPLOAD_DATE;
	}
	public String getRULE_NAME() {
		return RULE_NAME;
	}
	public void setRULE_NAME(String rULE_NAME) {
		RULE_NAME = rULE_NAME;
	}
	public String getACTION_RESULT() {
		return ACTION_RESULT;
	}
	public void setACTION_RESULT(String aCTION_RESULT) {
		ACTION_RESULT = aCTION_RESULT;
	}
}
