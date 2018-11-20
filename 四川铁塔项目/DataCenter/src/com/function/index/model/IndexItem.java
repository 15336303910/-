package com.function.index.model;
import java.io.Serializable;
import java.util.Date;
public class IndexItem implements Serializable{
	
	private static final long serialVersionUID = 8197564230001170504L;

	private Integer ID;
	private Integer INDEX_ID;
	private Integer RULE_ID;
	private Integer COUNT_RATIO;
	private Date MAINTAIN_DATE;
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Integer getCOUNT_RATIO() {
		return COUNT_RATIO;
	}
	public void setCOUNT_RATIO(Integer cOUNT_RATIO) {
		COUNT_RATIO = cOUNT_RATIO;
	}
	public Integer getINDEX_ID() {
		return INDEX_ID;
	}
	public void setINDEX_ID(Integer iNDEX_ID) {
		INDEX_ID = iNDEX_ID;
	}
	public Integer getRULE_ID() {
		return RULE_ID;
	}
	public void setRULE_ID(Integer rULE_ID) {
		RULE_ID = rULE_ID;
	}
	public Date getMAINTAIN_DATE() {
		return MAINTAIN_DATE;
	}
	public void setMAINTAIN_DATE(Date mAINTAIN_DATE) {
		MAINTAIN_DATE = mAINTAIN_DATE;
	}
}
