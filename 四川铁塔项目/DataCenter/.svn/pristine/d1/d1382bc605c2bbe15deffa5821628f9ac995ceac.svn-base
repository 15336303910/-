package com.function.rules.model;
import java.io.Serializable;
import java.util.Date;
import net.sf.json.JSONObject;
public class RuleDetail implements Serializable{

	private static final long serialVersionUID = 8418121429078497013L;

	private Integer ID;
	private Integer BIND_TABLE;
	private String CLASS_NAME;
	private String RULE_NAME;
	private String RULE_DESC;
	private String RESOLVE_METHOD;
	private String CON_EXPRESSION;
	private String RULE_STATE;
	private Integer CREATE_USER;
	private String CREATE_USER_NAME;
	private Date CREATE_TIME;
	private Date LAST_ACTION_TIME;
	private RuleQuartz ruleQuartz;
	private String FILE_REPORT;
	private String RULE_TYPE;
	private String PROBLEM_TOTAL;
	private Integer RULE_GRADE;
	private String TABLE_NAME;
	private String RULE_VERSION;
	private Integer COUNT_RATIO;
	
	public RuleDetail(){
		
	}
	
	public RuleDetail(JSONObject jsonObject){
		this.setRULE_TYPE(jsonObject.get("ruleType")==null?"":jsonObject.getString("ruleType"));
		this.setBIND_TABLE(Integer.parseInt(jsonObject.getString("tableId")));
		this.setRULE_NAME(jsonObject.get("ruleName")==null?"":jsonObject.getString("ruleName"));
		this.setRULE_DESC(jsonObject.get("ruleDesc")==null?"":jsonObject.getString("ruleDesc"));
		this.setRESOLVE_METHOD(jsonObject.get("recovery")==null?"":jsonObject.getString("recovery"));
		this.setCON_EXPRESSION(jsonObject.get("conExpress")==null?"":jsonObject.getString("conExpress"));
		this.setCREATE_TIME(new Date());
		this.setRULE_STATE(jsonObject.get("isUsing")==null?"":jsonObject.getString("isUsing"));
		this.setCLASS_NAME(jsonObject.get("className")==null?"":jsonObject.getString("className"));
		this.setRULE_GRADE(Integer.parseInt(jsonObject.getString("ruleGradeLevel")));
		this.setRULE_VERSION(jsonObject.get("ruleVersion")==null?"1.0":jsonObject.getString("ruleVersion"));
	}
	
	public Integer getID() {
		return ID;
	}
	
	public void setID(Integer iD) {
		ID = iD;
	}
	
	public String getRULE_VERSION() {
		return RULE_VERSION;
	}

	public void setRULE_VERSION(String rULE_VERSION) {
		RULE_VERSION = rULE_VERSION;
	}

	public Integer getCOUNT_RATIO() {
		return COUNT_RATIO;
	}
	public void setCOUNT_RATIO(Integer cOUNT_RATIO) {
		COUNT_RATIO = cOUNT_RATIO;
	}

	public String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public void setTABLE_NAME(String tABLE_NAME) {
		TABLE_NAME = tABLE_NAME;
	}

	public Integer getRULE_GRADE() {
		return RULE_GRADE;
	}

	public void setRULE_GRADE(Integer rULE_GRADE) {
		RULE_GRADE = rULE_GRADE;
	}

	public String getPROBLEM_TOTAL() {
		return PROBLEM_TOTAL;
	}

	public void setPROBLEM_TOTAL(String pROBLEM_TOTAL) {
		PROBLEM_TOTAL = pROBLEM_TOTAL;
	}

	public String getRULE_TYPE() {
		return RULE_TYPE;
	}

	public void setRULE_TYPE(String rULE_TYPE) {
		RULE_TYPE = rULE_TYPE;
	}

	public String getCLASS_NAME() {
		return CLASS_NAME;
	}

	public void setCLASS_NAME(String cLASS_NAME) {
		CLASS_NAME = cLASS_NAME;
	}

	public RuleQuartz getRuleQuartz() {
		return ruleQuartz;
	}

	public void setRuleQuartz(RuleQuartz ruleQuartz) {
		this.ruleQuartz = ruleQuartz;
	}
	public String getFILE_REPORT() {
		return FILE_REPORT;
	}

	public void setFILE_REPORT(String fILE_REPORT) {
		FILE_REPORT = fILE_REPORT;
	}

	public String getCREATE_USER_NAME() {
		return CREATE_USER_NAME;
	}
	
	public void setCREATE_USER_NAME(String cREATE_USER_NAME) {
		CREATE_USER_NAME = cREATE_USER_NAME;
	}
	
	public String getRESOLVE_METHOD() {
		return RESOLVE_METHOD;
	}
	
	public void setRESOLVE_METHOD(String rESOLVE_METHOD) {
		RESOLVE_METHOD = rESOLVE_METHOD;
	}
	
	public String getCON_EXPRESSION() {
		return CON_EXPRESSION;
	}
	
	public void setCON_EXPRESSION(String cON_EXPRESSION) {
		CON_EXPRESSION = cON_EXPRESSION;
	}
	
	public Integer getBIND_TABLE() {
		return BIND_TABLE;
	}
	
	public void setBIND_TABLE(Integer bIND_TABLE) {
		BIND_TABLE = bIND_TABLE;
	}
	
	public String getRULE_NAME() {
		return RULE_NAME;
	}
	
	public void setRULE_NAME(String rULE_NAME) {
		RULE_NAME = rULE_NAME;
	}
	
	public String getRULE_DESC() {
		return RULE_DESC;
	}
	
	public void setRULE_DESC(String rULE_DESC) {
		RULE_DESC = rULE_DESC;
	}
	
	public Integer getCREATE_USER() {
		return CREATE_USER;
	}
	
	public void setCREATE_USER(Integer cREATE_USER) {
		CREATE_USER = cREATE_USER;
	}
	
	public Date getCREATE_TIME() {
		return CREATE_TIME;
	}
	
	public void setCREATE_TIME(Date cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}
	
	public Date getLAST_ACTION_TIME() {
		return LAST_ACTION_TIME;
	}
	
	public void setLAST_ACTION_TIME(Date lAST_ACTION_TIME) {
		LAST_ACTION_TIME = lAST_ACTION_TIME;
	}
	
	public String getRULE_STATE() {
		return RULE_STATE;
	}
	
	public void setRULE_STATE(String rULE_STATE) {
		RULE_STATE = rULE_STATE;
	}
}
