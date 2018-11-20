package manage.pipe.pojo;

import java.util.Date;
import java.util.List;

public class PipeInfoBean {
	private Integer pipeId;// 管道系统主键
	private String pipeName;// 管道系统名称
	private String alias;// 序号
	private Integer maintenanceAreaId;// 维护区域id
	private String maintenanceAreaName;// 维护区域名称
	private String pipeLevel;// 管道级别
	private String pipeSegmentType;// 管线类型
	private Integer maintenanceModeEnumId;
	private String maintenanceOrgId;
	private String maintenanceOrgName;
	private String maintainerId;
	private String maintainerName;
	private Date creationDate;
	private Date lastUpdateDate;
	private String deletedFlag;
	private String cstate;
	private String areano;
	private String areaname;
	private List<PipeInfoBean> pipes;
	private Integer total;
	private Integer limit;
	private Integer start;
	private String dataQualityPrincipal;
	private String parties;

	public String getDataQualityPrincipal() {
		return dataQualityPrincipal;
	}

	public void setDataQualityPrincipal(String dataQualityPrincipal) {
		this.dataQualityPrincipal = dataQualityPrincipal;
	}

	public String getParties() {
		return parties;
	}

	public void setParties(String parties) {
		this.parties = parties;
	}

	public List<PipeInfoBean> getPipes() {
		return this.pipes;
	}

	public void setPipes(List<PipeInfoBean> pipes) {
		this.pipes = pipes;
	}

	public Integer getTotal() {
		return this.total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getLimit() {
		return this.limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getStart() {
		return this.start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public String getCstate() {
		return this.cstate;
	}

	public void setCstate(String cstate) {
		this.cstate = cstate;
	}

	public Integer getPipeId() {
		return this.pipeId;
	}

	public void setPipeId(Integer pipeId) {
		this.pipeId = pipeId;
	}

	public String getPipeName() {
		return this.pipeName;
	}

	public void setPipeName(String pipeName) {
		this.pipeName = pipeName;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getMaintenanceAreaId() {
		return this.maintenanceAreaId;
	}

	public void setMaintenanceAreaId(Integer maintenanceAreaId) {
		this.maintenanceAreaId = maintenanceAreaId;
	}

	public String getPipeLevel() {
		return this.pipeLevel;
	}

	public void setPipeLevel(String pipeLevel) {
		this.pipeLevel = pipeLevel;
	}

	public Integer getMaintenanceModeEnumId() {
		return this.maintenanceModeEnumId;
	}

	public void setMaintenanceModeEnumId(Integer maintenanceModeEnumId) {
		this.maintenanceModeEnumId = maintenanceModeEnumId;
	}

	public String getMaintenanceOrgId() {
		return this.maintenanceOrgId;
	}

	public void setMaintenanceOrgId(String maintenanceOrgId) {
		this.maintenanceOrgId = maintenanceOrgId;
	}

	public String getMaintainerId() {
		return this.maintainerId;
	}

	public void setMaintainerId(String maintainerId) {
		this.maintainerId = maintainerId;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getDeletedFlag() {
		return this.deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public String getMaintenanceAreaName() {
		return this.maintenanceAreaName;
	}

	public void setMaintenanceAreaName(String maintenanceAreaName) {
		this.maintenanceAreaName = maintenanceAreaName;
	}

	public String getAreano() {
		return this.areano;
	}

	public void setAreano(String areano) {
		this.areano = areano;
	}

	public String getAreaname() {
		return this.areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getPipeSegmentType() {
		return this.pipeSegmentType;
	}

	public void setPipeSegmentType(String pipeSegmentType) {
		this.pipeSegmentType = pipeSegmentType;
	}

	public String getMaintainerName() {
		return this.maintainerName;
	}

	public void setMaintainerName(String maintainerName) {
		this.maintainerName = maintainerName;
	}

	public String getMaintenanceOrgName() {
		return this.maintenanceOrgName;
	}

	public void setMaintenanceOrgName(String maintenanceOrgName) {
		this.maintenanceOrgName = maintenanceOrgName;
	}
}