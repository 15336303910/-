package com.function.compare.model;
import java.io.Serializable;
import java.util.Date;
public class CompareMonitor implements Serializable{

	private static final long serialVersionUID = 1507179960501520695L;

	private Integer ID;
	private Integer BELONG_COMPARE;
	private Date START_DATE;
	private String IS_FINISHED;
	private Date FINISH_DATE;
	private Integer A_ONLY;
	private Integer Z_ONLY;
	private Integer NOT_UNIFORM;
	private Integer A_FATUAL;
	private Integer Z_FATUAL;
	private String IS_FATAL;
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getIS_FATAL() {
		return IS_FATAL;
	}
	public void setIS_FATAL(String iS_FATAL) {
		IS_FATAL = iS_FATAL;
	}
	public Integer getBELONG_COMPARE() {
		return BELONG_COMPARE;
	}
	public void setBELONG_COMPARE(Integer bELONG_COMPARE) {
		BELONG_COMPARE = bELONG_COMPARE;
	}
	public Date getSTART_DATE() {
		return START_DATE;
	}
	public void setSTART_DATE(Date sTART_DATE) {
		START_DATE = sTART_DATE;
	}
	public String getIS_FINISHED() {
		return IS_FINISHED;
	}
	public void setIS_FINISHED(String iS_FINISHED) {
		IS_FINISHED = iS_FINISHED;
	}
	public Date getFINISH_DATE() {
		return FINISH_DATE;
	}
	public void setFINISH_DATE(Date fINISH_DATE) {
		FINISH_DATE = fINISH_DATE;
	}
	public Integer getA_ONLY() {
		return A_ONLY;
	}
	public void setA_ONLY(Integer a_ONLY) {
		A_ONLY = a_ONLY;
	}
	public Integer getZ_ONLY() {
		return Z_ONLY;
	}
	public void setZ_ONLY(Integer z_ONLY) {
		Z_ONLY = z_ONLY;
	}
	public Integer getNOT_UNIFORM() {
		return NOT_UNIFORM;
	}
	public void setNOT_UNIFORM(Integer nOT_UNIFORM) {
		NOT_UNIFORM = nOT_UNIFORM;
	}
	public Integer getA_FATUAL() {
		return A_FATUAL;
	}
	public void setA_FATUAL(Integer a_FATUAL) {
		A_FATUAL = a_FATUAL;
	}
	public Integer getZ_FATUAL() {
		return Z_FATUAL;
	}
	public void setZ_FATUAL(Integer z_FATUAL) {
		Z_FATUAL = z_FATUAL;
	}
}
