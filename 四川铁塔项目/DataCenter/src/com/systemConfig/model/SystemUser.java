package com.systemConfig.model;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;
public class SystemUser implements Serializable{

	private static final long serialVersionUID = -6129978249697590501L;

	private Integer ID;
	private String USER_NAME;
	private String USER_PASS;
	private String IS_LENGTHY_USE;
	private Date START_DATE;
	private Date LIMIT_DATE;
	private String IS_LOCKED;
	private String IS_HAVE_INVITE_ABLE;
	private Date LAST_LOGIN_DATETIME;
	private String EMPLOYEE_NAME;
	private String EMPLOYEE_SEX;
	private String EMPLOYEE_ID_CARD;
	private String EMPLOYEE_COMPANY;
	private String EMPLOYEE_DEPARTNAME;
	private String EMPLOYEE_PHONE;
	private String BELONG_AREA;
	private Integer ROLE_CODE;
	private Integer EMPLOYEE_COMPANY_CODE;
	private Integer EMPLOYEE_DEPART_CODE;
	private String USER_ICON;
	
	public SystemUser(){
		
	}
	
	public SystemUser(JSONObject jsonObject,Boolean isCreate)throws Exception{
		if(!isCreate){
			this.setID(Integer.parseInt(jsonObject.getString("userCode")));
		}
		this.setUSER_NAME(jsonObject.get("userAccount")==null?"":jsonObject.get("userAccount").toString());
		if(isCreate){
			this.setUSER_PASS("123456");
			this.setUSER_ICON("1004.png");
		}else{
			this.setUSER_PASS(jsonObject.getString("userPassword"));
		}
		this.setIS_LENGTHY_USE(jsonObject.getString("isLengthyUse"));
		if(jsonObject.getString("isLengthyUse")!=null && "N".equals(jsonObject.getString("isLengthyUse"))){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if(jsonObject.get("beginDate")!=null && !"".equals(jsonObject.getString("beginDate"))){
				this.setSTART_DATE(dateFormat.parse(jsonObject.getString("beginDate")));
			}
			if(jsonObject.get("limitDate")!=null && !"".equals(jsonObject.getString("limitDate"))){
				this.setLIMIT_DATE(dateFormat.parse(jsonObject.getString("limitDate")));
			}
		}
		this.setIS_LOCKED(jsonObject.getString("isLocked"));
		this.setIS_HAVE_INVITE_ABLE(jsonObject.getString("isInvited"));
		this.setLAST_LOGIN_DATETIME(new Date());
		this.setEMPLOYEE_NAME(jsonObject.getString("employeeName"));
		this.setEMPLOYEE_SEX(jsonObject.getString("employeeSex"));
		this.setEMPLOYEE_PHONE(jsonObject.getString("employeePhone"));
		this.setBELONG_AREA(jsonObject.getString("belongArea"));
		this.setROLE_CODE(Integer.parseInt(jsonObject.getString("userRoleCode")));
		this.setEMPLOYEE_COMPANY_CODE(Integer.parseInt(jsonObject.getString("companyCode")));
		this.setEMPLOYEE_COMPANY(jsonObject.getString("companyName"));
		this.setEMPLOYEE_DEPART_CODE(Integer.parseInt(jsonObject.getString("departCode")));
		this.setEMPLOYEE_DEPARTNAME(jsonObject.getString("departName"));
		this.setEMPLOYEE_ID_CARD(jsonObject.getString("employeeId"));
	}
	
	public Integer getID() {
		return ID;
	}
	
	public void setID(Integer iD) {
		ID = iD;
	}
	
	public String getUSER_ICON() {
		return USER_ICON;
	}

	public void setUSER_ICON(String uSER_ICON) {
		USER_ICON = uSER_ICON;
	}

	public String getUSER_NAME() {
		return USER_NAME;
	}
	
	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}
	
	public String getUSER_PASS() {
		return USER_PASS;
	}
	
	public void setUSER_PASS(String uSER_PASS) {
		USER_PASS = uSER_PASS;
	}
	
	public String getIS_LENGTHY_USE() {
		return IS_LENGTHY_USE;
	}
	
	public void setIS_LENGTHY_USE(String iS_LENGTHY_USE) {
		IS_LENGTHY_USE = iS_LENGTHY_USE;
	}
	
	public Date getSTART_DATE() {
		return START_DATE;
	}
	
	public void setSTART_DATE(Date sTART_DATE) {
		START_DATE = sTART_DATE;
	}
	
	public Date getLIMIT_DATE() {
		return LIMIT_DATE;
	}
	
	public void setLIMIT_DATE(Date lIMIT_DATE) {
		LIMIT_DATE = lIMIT_DATE;
	}
	
	public String getIS_LOCKED() {
		return IS_LOCKED;
	}
	
	public void setIS_LOCKED(String iS_LOCKED) {
		IS_LOCKED = iS_LOCKED;
	}
	
	public String getIS_HAVE_INVITE_ABLE() {
		return IS_HAVE_INVITE_ABLE;
	}
	
	public void setIS_HAVE_INVITE_ABLE(String iS_HAVE_INVITE_ABLE) {
		IS_HAVE_INVITE_ABLE = iS_HAVE_INVITE_ABLE;
	}
	
	public Date getLAST_LOGIN_DATETIME() {
		return LAST_LOGIN_DATETIME;
	}
	
	public void setLAST_LOGIN_DATETIME(Date lAST_LOGIN_DATETIME) {
		LAST_LOGIN_DATETIME = lAST_LOGIN_DATETIME;
	}
	
	public String getEMPLOYEE_NAME() {
		return EMPLOYEE_NAME;
	}
	
	public void setEMPLOYEE_NAME(String eMPLOYEE_NAME) {
		EMPLOYEE_NAME = eMPLOYEE_NAME;
	}
	
	public String getEMPLOYEE_SEX() {
		return EMPLOYEE_SEX;
	}
	
	public void setEMPLOYEE_SEX(String eMPLOYEE_SEX) {
		EMPLOYEE_SEX = eMPLOYEE_SEX;
	}
	
	public String getEMPLOYEE_ID_CARD() {
		return EMPLOYEE_ID_CARD;
	}
	
	public void setEMPLOYEE_ID_CARD(String eMPLOYEE_ID_CARD) {
		EMPLOYEE_ID_CARD = eMPLOYEE_ID_CARD;
	}
	
	public String getEMPLOYEE_COMPANY() {
		return EMPLOYEE_COMPANY;
	}
	
	public void setEMPLOYEE_COMPANY(String eMPLOYEE_COMPANY) {
		EMPLOYEE_COMPANY = eMPLOYEE_COMPANY;
	}
	
	public String getEMPLOYEE_DEPARTNAME() {
		return EMPLOYEE_DEPARTNAME;
	}
	
	public void setEMPLOYEE_DEPARTNAME(String eMPLOYEE_DEPARTNAME) {
		EMPLOYEE_DEPARTNAME = eMPLOYEE_DEPARTNAME;
	}
	
	public String getEMPLOYEE_PHONE() {
		return EMPLOYEE_PHONE;
	}
	
	public void setEMPLOYEE_PHONE(String eMPLOYEE_PHONE) {
		EMPLOYEE_PHONE = eMPLOYEE_PHONE;
	}
	
	public String getBELONG_AREA() {
		return BELONG_AREA;
	}
	
	public void setBELONG_AREA(String bELONG_AREA) {
		BELONG_AREA = bELONG_AREA;
	}
	
	public Integer getROLE_CODE() {
		return ROLE_CODE;
	}
	
	public void setROLE_CODE(Integer rOLE_CODE) {
		ROLE_CODE = rOLE_CODE;
	}
	
	public Integer getEMPLOYEE_COMPANY_CODE() {
		return EMPLOYEE_COMPANY_CODE;
	}
	
	public void setEMPLOYEE_COMPANY_CODE(Integer eMPLOYEE_COMPANY_CODE) {
		EMPLOYEE_COMPANY_CODE = eMPLOYEE_COMPANY_CODE;
	}
	
	public Integer getEMPLOYEE_DEPART_CODE() {
		return EMPLOYEE_DEPART_CODE;
	}
	public void setEMPLOYEE_DEPART_CODE(Integer eMPLOYEE_DEPART_CODE) {
		EMPLOYEE_DEPART_CODE = eMPLOYEE_DEPART_CODE;
	}
}
