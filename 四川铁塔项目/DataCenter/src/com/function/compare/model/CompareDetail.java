package com.function.compare.model;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;
public class CompareDetail implements Serializable{

	private static final long serialVersionUID = -5873416300015104512L;

	private Integer ID;
	private String RULE_CODE;
	private String RULE_NAME;
	private String RULE_DESC;
	private Date CREATE_DATE;
	private Date LAST_ACTION_DATE;
	private Integer A_TABLE_ID;
	private Integer Z_TABLE_ID;
	private String IS_UNIFORM;
	private String IS_A_ONLY;
	private String IS_Z_ONLY;
	private String IS_A_FATUAL;
	private String IS_Z_FATUAL;
	private String IS_QUARTZ;
	private String QUARTZ_EXPRESS;
	private String IS_USING;
	private String IS_INCREASE;
	private List<CompareColumn> compareColumns;
	
	public CompareDetail(){
		
	}
	
	public CompareDetail(JSONObject jsonObject){
		this.setRULE_NAME(jsonObject.get("RULE_NAME")==null?"":jsonObject.getString("RULE_NAME"));
		this.setRULE_DESC(jsonObject.get("RULE_DESC")==null?"":jsonObject.getString("RULE_DESC"));
		this.setCREATE_DATE(new Date());
		this.setA_TABLE_ID(Integer.parseInt(jsonObject.getString("A_TABLE_ID")));
		this.setZ_TABLE_ID(Integer.parseInt(jsonObject.getString("Z_TABLE_ID")));
		this.setIS_UNIFORM(jsonObject.get("IS_UNIFORM")==null?"":jsonObject.getString("IS_UNIFORM"));
		this.setIS_A_ONLY(jsonObject.get("IS_A_ONLY")==null?"":jsonObject.getString("IS_A_ONLY"));
		this.setIS_Z_ONLY(jsonObject.get("IS_Z_ONLY")==null?"":jsonObject.getString("IS_Z_ONLY"));
		this.setIS_A_FATUAL(jsonObject.get("IS_A_FATUAL")==null?"":jsonObject.getString("IS_A_FATUAL"));
		this.setIS_Z_FATUAL(jsonObject.get("IS_Z_FATUAL")==null?"":jsonObject.getString("IS_Z_FATUAL"));
		if(jsonObject.get("QUARTZ_EXPRESS")!=null && !"-".equals(jsonObject.getString("QUARTZ_EXPRESS"))){
			this.setIS_QUARTZ("Y");
			this.setQUARTZ_EXPRESS(jsonObject.get("QUARTZ_EXPRESS")==null?"":jsonObject.getString("QUARTZ_EXPRESS"));
		}else{
			this.setIS_QUARTZ("N");
			this.setQUARTZ_EXPRESS(null);
		}
		this.setIS_USING(jsonObject.get("IS_USING")==null?"":jsonObject.getString("IS_USING"));
		this.setIS_INCREASE(jsonObject.get("IS_INCREASE")==null?"":jsonObject.getString("IS_INCREASE"));
	}
	
	public Integer getID() {
		return ID;
	}
	
	public List<CompareColumn> getCompareColumns() {
		return compareColumns;
	}

	public void setCompareColumns(List<CompareColumn> compareColumns) {
		this.compareColumns = compareColumns;
	}

	public void setID(Integer iD) {
		ID = iD;
	}
	
	public String getIS_A_FATUAL() {
		return IS_A_FATUAL;
	}

	public void setIS_A_FATUAL(String iS_A_FATUAL) {
		IS_A_FATUAL = iS_A_FATUAL;
	}

	public String getIS_Z_FATUAL() {
		return IS_Z_FATUAL;
	}

	public void setIS_Z_FATUAL(String iS_Z_FATUAL) {
		IS_Z_FATUAL = iS_Z_FATUAL;
	}

	public String getIS_INCREASE() {
		return IS_INCREASE;
	}
	public void setIS_INCREASE(String iS_INCREASE) {
		IS_INCREASE = iS_INCREASE;
	}
	public String getRULE_CODE() {
		return RULE_CODE;
	}
	public void setRULE_CODE(String rULE_CODE) {
		RULE_CODE = rULE_CODE;
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
	public Date getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(Date cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	public Date getLAST_ACTION_DATE() {
		return LAST_ACTION_DATE;
	}
	public void setLAST_ACTION_DATE(Date lAST_ACTION_DATE) {
		LAST_ACTION_DATE = lAST_ACTION_DATE;
	}
	public Integer getA_TABLE_ID() {
		return A_TABLE_ID;
	}
	public void setA_TABLE_ID(Integer a_TABLE_ID) {
		A_TABLE_ID = a_TABLE_ID;
	}
	public Integer getZ_TABLE_ID() {
		return Z_TABLE_ID;
	}
	public void setZ_TABLE_ID(Integer z_TABLE_ID) {
		Z_TABLE_ID = z_TABLE_ID;
	}
	public String getIS_A_ONLY() {
		return IS_A_ONLY;
	}
	public void setIS_A_ONLY(String iS_A_ONLY) {
		IS_A_ONLY = iS_A_ONLY;
	}
	public String getIS_Z_ONLY() {
		return IS_Z_ONLY;
	}
	public void setIS_Z_ONLY(String iS_Z_ONLY) {
		IS_Z_ONLY = iS_Z_ONLY;
	}
	public String getIS_QUARTZ() {
		return IS_QUARTZ;
	}
	public void setIS_QUARTZ(String iS_QUARTZ) {
		IS_QUARTZ = iS_QUARTZ;
	}
	public String getQUARTZ_EXPRESS() {
		return QUARTZ_EXPRESS;
	}
	public void setQUARTZ_EXPRESS(String qUARTZ_EXPRESS) {
		QUARTZ_EXPRESS = qUARTZ_EXPRESS;
	}
	public String getIS_USING() {
		return IS_USING;
	}
	public void setIS_USING(String iS_USING) {
		IS_USING = iS_USING;
	}
	public String getIS_UNIFORM() {
		return IS_UNIFORM;
	}
	public void setIS_UNIFORM(String iS_UNIFORM) {
		IS_UNIFORM = iS_UNIFORM;
	}
}
