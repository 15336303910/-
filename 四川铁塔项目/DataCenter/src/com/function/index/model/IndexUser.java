package com.function.index.model;
import java.io.Serializable;
import java.util.Date;
public class IndexUser implements Serializable{

	private static final long serialVersionUID = -5173208003070257811L;

	private Integer ID;
	private Integer USER_ID;
	private Integer INDEX_ID;
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
	public Integer getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(Integer uSER_ID) {
		USER_ID = uSER_ID;
	}
	public Integer getINDEX_ID() {
		return INDEX_ID;
	}
	public void setINDEX_ID(Integer iNDEX_ID) {
		INDEX_ID = iNDEX_ID;
	}
	public Date getMAINTAIN_DATE() {
		return MAINTAIN_DATE;
	}
	public void setMAINTAIN_DATE(Date mAINTAIN_DATE) {
		MAINTAIN_DATE = mAINTAIN_DATE;
	}
}
