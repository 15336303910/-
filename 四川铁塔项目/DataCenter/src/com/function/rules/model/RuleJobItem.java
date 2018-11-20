package com.function.rules.model;
import java.io.Serializable;
public class RuleJobItem implements Serializable{

	private static final long serialVersionUID = -9147187682679855883L;

	private Integer ID;
	private Integer JOB_ID;
	private String CHECK_COLUMN;
	private String CHECK_EXPRESS;
	private Integer PROBLEM_COUNT;
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Integer getJOB_ID() {
		return JOB_ID;
	}
	public void setJOB_ID(Integer jOB_ID) {
		JOB_ID = jOB_ID;
	}
	public String getCHECK_COLUMN() {
		return CHECK_COLUMN;
	}
	public void setCHECK_COLUMN(String cHECK_COLUMN) {
		CHECK_COLUMN = cHECK_COLUMN;
	}
	public String getCHECK_EXPRESS() {
		return CHECK_EXPRESS;
	}
	public void setCHECK_EXPRESS(String cHECK_EXPRESS) {
		CHECK_EXPRESS = cHECK_EXPRESS;
	}
	public Integer getPROBLEM_COUNT() {
		return PROBLEM_COUNT;
	}
	public void setPROBLEM_COUNT(Integer pROBLEM_COUNT) {
		PROBLEM_COUNT = pROBLEM_COUNT;
	}
}
