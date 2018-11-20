package com.function.index.greyList.model;

import java.util.Date;

/**
 * 灰名单映射实体类
 */
public class GreyList {
	private Long id;// 主键
	private String city;// 地市
	private String county;// 区县
	private String saName;// 站址名称（合同）
	private String saCode;// 站址编码（合同）
	private String attribution;// 归口专业
	private String dataSource;// 数据来源
	private String tableSource;// 来源表
	private String glType;// 灰名单类型
	private String glDescribe;// 灰名单说明
	private String originator;// 发起人
	private Date launchTime;// 申请发起时间
	private String glRule;// 灰名单规则
	private String cityApprover;// 地市审批人
	private Date caTime;// 地市审批时间
	private String provinceApprover;// 省审批人
	private Date paTime;// 省审批时间
	private String rejectReason;// 驳回原因
	private Date glsTime;// 进入灰名单时间
	private Integer validTime;// 有效期
	private Date expireTime;// 到期时间
	private Date glExitTime;// 灰名单退出时间
	private Integer expireStatus;// 是否到期_状态
	private String procedureStatus;// 流程类型（流程类型（三个）申请、续期、解除）
	// 流程环节（流程环节（三个）0审批未通过 1还未审批 2地市审批完成 3省公司审批完成）
	private String procedureSegment;
	private String remarks;// 备注
	private Long lastId;// 之前所在表的id

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getSaName() {
		return saName;
	}

	public void setSaName(String saName) {
		this.saName = saName;
	}

	public String getSaCode() {
		return saCode;
	}

	public void setSaCode(String saCode) {
		this.saCode = saCode;
	}

	public String getAttribution() {
		return attribution;
	}

	public void setAttribution(String attribution) {
		this.attribution = attribution;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getTableSource() {
		return tableSource;
	}

	public void setTableSource(String tableSource) {
		this.tableSource = tableSource;
	}

	public String getGlType() {
		return glType;
	}

	public void setGlType(String glType) {
		this.glType = glType;
	}

	public String getGlDescribe() {
		return glDescribe;
	}

	public void setGlDescribe(String glDescribe) {
		this.glDescribe = glDescribe;
	}

	public String getOriginator() {
		return originator;
	}

	public void setOriginator(String originator) {
		this.originator = originator;
	}

	public Date getLaunchTime() {
		return launchTime;
	}

	public void setLaunchTime(Date launchTime) {
		this.launchTime = launchTime;
	}

	public String getGlRule() {
		return glRule;
	}

	public void setGlRule(String glRule) {
		this.glRule = glRule;
	}

	public String getCityApprover() {
		return cityApprover;
	}

	public void setCityApprover(String cityApprover) {
		this.cityApprover = cityApprover;
	}

	public Date getCaTime() {
		return caTime;
	}

	public void setCaTime(Date caTime) {
		this.caTime = caTime;
	}

	public String getProvinceApprover() {
		return provinceApprover;
	}

	public void setProvinceApprover(String provinceApprover) {
		this.provinceApprover = provinceApprover;
	}

	public Date getPaTime() {
		return paTime;
	}

	public void setPaTime(Date paTime) {
		this.paTime = paTime;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Date getGlsTime() {
		return glsTime;
	}

	public void setGlsTime(Date glsTime) {
		this.glsTime = glsTime;
	}

	public Integer getValidTime() {
		return validTime;
	}

	public void setValidTime(Integer validTime) {
		this.validTime = validTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Date getGlExitTime() {
		return glExitTime;
	}

	public void setGlExitTime(Date glExitTime) {
		this.glExitTime = glExitTime;
	}

	public Integer getExpireStatus() {
		return expireStatus;
	}

	public void setExpireStatus(Integer expireStatus) {
		this.expireStatus = expireStatus;
	}

	public String getProcedureStatus() {
		return procedureStatus;
	}

	public void setProcedureStatus(String procedureStatus) {
		this.procedureStatus = procedureStatus;
	}

	public String getProcedureSegment() {
		return procedureSegment;
	}

	public void setProcedureSegment(String procedureSegment) {
		this.procedureSegment = procedureSegment;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getLastId() {
		return lastId;
	}

	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}

	@Override
	public String toString() {
		return "GreyList [id=" + id + ", city=" + city + ", county=" + county + ", saName=" + saName + ", saCode="
				+ saCode + ", attribution=" + attribution + ", dataSource=" + dataSource + ", tableSource="
				+ tableSource + ", glType=" + glType + ", glDescribe=" + glDescribe + ", originator=" + originator
				+ ", launchTime=" + launchTime + ", glRule=" + glRule + ", cityApprover=" + cityApprover + ", caTime="
				+ caTime + ", provinceApprover=" + provinceApprover + ", paTime=" + paTime + ", rejectReason="
				+ rejectReason + ", glsTime=" + glsTime + ", validTime=" + validTime + ", expireTime=" + expireTime
				+ ", glExitTime=" + glExitTime + ", expireStatus=" + expireStatus + ", procedureStatus="
				+ procedureStatus + ", procedureSegment=" + procedureSegment + ", remarks=" + remarks + "]";
	}

}
