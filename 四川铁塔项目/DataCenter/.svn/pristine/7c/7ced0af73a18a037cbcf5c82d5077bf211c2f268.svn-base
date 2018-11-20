package com.function.rules.model;
import java.io.Serializable;

import net.sf.json.JSONObject;
public class RuleItem implements Serializable{

	private static final long serialVersionUID = 8863700835253753933L;

	private Integer ID;
	private Integer BELONG_RULE;
	//编辑类型：普通为“基础规则核查”，连表为“关联规则核查”.
	private String AUDIT_TYPE;
	//待核查的字段编号
	private Integer COLUMN_ID;
	//是否过滤空值，Y，N
	private String IS_FILTER;
	//普通为表达式，连表状态下为“核查类型”
	private String EXPRESSION;
	//保存连表状态下需要关联的tableId以及值属性valueColumn.
	private Integer VALUE_TABLE;
	private Integer VALUE_COLUMN;
	private Integer MIN_NUMBER;
	private Integer MAX_NUMBER;
	private String COLUMN_IDS;
	private String MATCH_TYPE;
	private String DIMENSION_TYPE;
	private String EXPORT_COLUMNS;
	private String AUDIT_SQL;
	
	public RuleItem(){
		
	}
	
	public RuleItem(JSONObject jsonObject){
		/*['基础规则核查','关联规则核查']*/
		this.setAUDIT_TYPE(jsonObject.get("auditType")==null?"":jsonObject.getString("auditType"));
		if(jsonObject.get("auditType")!=null && "自定义核查".equals(jsonObject.getString("auditType"))){
			this.setEXPORT_COLUMNS(jsonObject.get("exportColumns")==null?"":jsonObject.getString("exportColumns"));
			this.setAUDIT_SQL(jsonObject.get("auditSql")==null?"":jsonObject.getString("auditSql"));
		}else{
			/*['核查目标属性']*/
			this.setCOLUMN_ID(Integer.parseInt(jsonObject.getString("columnCode")));
			/*['是否过滤空值']*/
			this.setIS_FILTER(Boolean.parseBoolean(jsonObject.getString("isFilter"))?"Y":"N");
			/*['基础表达式','核查枚举值','核查关联数据条数','多属性匹配']*/
			this.setEXPRESSION(jsonObject.get("expression")==null?"":jsonObject.getString("expression"));
			if("关联规则核查".equals(jsonObject.getString("auditType"))){
				this.setVALUE_TABLE(Integer.parseInt(jsonObject.getString("tableCode")));
				this.setVALUE_COLUMN(Integer.parseInt(jsonObject.getString("valueColumn")));
				if("核查关联数据条数".equals(jsonObject.getString("expression"))){
					this.setMIN_NUMBER(Integer.parseInt(jsonObject.getString("minNumber")));
					this.setMAX_NUMBER(Integer.parseInt(jsonObject.getString("maxNumber")));
				}else if("多属性匹配".equals(jsonObject.getString("expression"))){
					this.setCOLUMN_IDS(jsonObject.getString("checkedValues"));
					this.setMATCH_TYPE(jsonObject.getString("matchType"));
					this.setDIMENSION_TYPE(jsonObject.getString("dimension"));
				}
			}
		}
	}
	
	public Integer getID() {
		return ID;
	}
	
	public void setID(Integer iD) {
		ID = iD;
	}
	
	public Integer getMIN_NUMBER() {
		return MIN_NUMBER;
	}
	
	public String getCOLUMN_IDS() {
		return COLUMN_IDS;
	}

	public void setCOLUMN_IDS(String cOLUMN_IDS) {
		COLUMN_IDS = cOLUMN_IDS;
	}

	public String getMATCH_TYPE() {
		return MATCH_TYPE;
	}

	public void setMATCH_TYPE(String mATCH_TYPE) {
		MATCH_TYPE = mATCH_TYPE;
	}

	public String getDIMENSION_TYPE() {
		return DIMENSION_TYPE;
	}

	public void setDIMENSION_TYPE(String dIMENSION_TYPE) {
		DIMENSION_TYPE = dIMENSION_TYPE;
	}

	public void setMIN_NUMBER(Integer mIN_NUMBER) {
		MIN_NUMBER = mIN_NUMBER;
	}

	public Integer getMAX_NUMBER() {
		return MAX_NUMBER;
	}

	public void setMAX_NUMBER(Integer mAX_NUMBER) {
		MAX_NUMBER = mAX_NUMBER;
	}

	public Integer getBELONG_RULE() {
		return BELONG_RULE;
	}
	
	public void setBELONG_RULE(Integer bELONG_RULE) {
		BELONG_RULE = bELONG_RULE;
	}
	
	public String getAUDIT_TYPE() {
		return AUDIT_TYPE;
	}
	
	public void setAUDIT_TYPE(String aUDIT_TYPE) {
		AUDIT_TYPE = aUDIT_TYPE;
	}
	
	public Integer getCOLUMN_ID() {
		return COLUMN_ID;
	}
	
	public void setCOLUMN_ID(Integer cOLUMN_ID) {
		COLUMN_ID = cOLUMN_ID;
	}
	
	public String getIS_FILTER() {
		return IS_FILTER;
	}
	
	public void setIS_FILTER(String iS_FILTER) {
		IS_FILTER = iS_FILTER;
	}
	
	public String getEXPRESSION() {
		return EXPRESSION;
	}
	
	public void setEXPRESSION(String eXPRESSION) {
		EXPRESSION = eXPRESSION;
	}
	
	public Integer getVALUE_TABLE() {
		return VALUE_TABLE;
	}
	
	public void setVALUE_TABLE(Integer vALUE_TABLE) {
		VALUE_TABLE = vALUE_TABLE;
	}
	
	public Integer getVALUE_COLUMN() {
		return VALUE_COLUMN;
	}
	
	public void setVALUE_COLUMN(Integer vALUE_COLUMN) {
		VALUE_COLUMN = vALUE_COLUMN;
	}

	public String getEXPORT_COLUMNS() {
		return EXPORT_COLUMNS;
	}

	public void setEXPORT_COLUMNS(String eXPORT_COLUMNS) {
		EXPORT_COLUMNS = eXPORT_COLUMNS;
	}

	public String getAUDIT_SQL() {
		return AUDIT_SQL;
	}

	public void setAUDIT_SQL(String aUDIT_SQL) {
		AUDIT_SQL = aUDIT_SQL;
	}
}
