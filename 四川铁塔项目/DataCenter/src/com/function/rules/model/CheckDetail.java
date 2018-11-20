package com.function.rules.model;
public class CheckDetail {
	
	private Boolean IS_SUPPORTED;
	private String COUNT_SQL;
	private String SQL;
	private String PROBLEM_DETAIL;
	
	public String getSQL() {
		return SQL;
	}
	
	public void setSQL(String sQL) {
		SQL = sQL;
	}
	
	public Boolean getIS_SUPPORTED() {
		return IS_SUPPORTED;
	}
	
	public void setIS_SUPPORTED(Boolean iS_SUPPORTED) {
		IS_SUPPORTED = iS_SUPPORTED;
	}
	
	public String getCOUNT_SQL() {
		return COUNT_SQL;
	}
	
	public void setCOUNT_SQL(String cOUNT_SQL) {
		COUNT_SQL = cOUNT_SQL;
	}
	
	public String getPROBLEM_DETAIL() {
		return PROBLEM_DETAIL;
	}
	
	public void setPROBLEM_DETAIL(String pROBLEM_DETAIL) {
		PROBLEM_DETAIL = pROBLEM_DETAIL;
	}
}
