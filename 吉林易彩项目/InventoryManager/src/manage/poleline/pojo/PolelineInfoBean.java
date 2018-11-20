package manage.poleline.pojo;

import java.util.Date;
import java.util.List;

public class PolelineInfoBean
{
  private Integer poleLineId;
  private String poleLineName;
  private String poleLineCode;
  private Integer maintenanceAreaId;
  private String poleLineLevel;
  private String poleLineLength;
  private String startDeviceName;
  private String endDeviceName;
  private Integer maintenanceModeEnumId;
  private String maintenanceOrgId;
  private String maintainerId;
  private String thirdPartyMaintenanceOrg;
  private Date renewalExpirationDate;
  private Integer maintenanceTypeEnumId;
  private String purchasedMaintenanceTime;
  private String projectCode;
  private String projectName;
  private Date projectWarrantyExpireDate;
  private Integer resourceLifePeriodEnumId;
  private Date creationDate;
  private Date lastUpdateDate;
  private String deletedFlag;
  private Date deletionDate;
  private String provinceId;
  private String cuser;
  private String cstate;
  private Integer startDeviceId;
  private Integer endDeviceId;
  private String maintenanceAreaName;
  private String areano;
  private String areaname;
  private List<PolelineInfoBean> polelines;
  private Integer total;
  private Integer start;
  private Integer limit;
  private Integer logId;
  private Date logTime;
  private String logOperater;
  private String seriesNo;
  private String alias;
  private Date resourceLifePeriodEnumDate;
  private String designPurposes;
  private Integer isAuthorizationManagement;
  private String authorizationManagementUnit;
  private String note;
  private String ids;
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

public List<PolelineInfoBean> getPolelines()
  {
    return this.polelines; }

  public void setPolelines(List<PolelineInfoBean> polelines) {
    this.polelines = polelines; }

  public Integer getTotal() {
    return this.total; }

  public void setTotal(Integer total) {
    this.total = total; }

  public Integer getStart() {
    return this.start; }

  public void setStart(Integer start) {
    this.start = start; }

  public Integer getLimit() {
    return this.limit; }

  public void setLimit(Integer limit) {
    this.limit = limit; }

  public String getMaintenanceAreaName() {
    return this.maintenanceAreaName; }

  public void setMaintenanceAreaName(String maintenanceAreaName) {
    this.maintenanceAreaName = maintenanceAreaName; }

  public Integer getStartDeviceId() {
    return this.startDeviceId; }

  public void setStartDeviceId(Integer startDeviceId) {
    this.startDeviceId = startDeviceId; }

  public Integer getEndDeviceId() {
    return this.endDeviceId; }

  public void setEndDeviceId(Integer endDeviceId) {
    this.endDeviceId = endDeviceId; }

  public String getCuser() {
    return this.cuser; }

  public void setCuser(String cuser) {
    this.cuser = cuser; }

  public String getCstate() {
    return this.cstate; }

  public void setCstate(String cstate) {
    this.cstate = cstate; }

  public Integer getPoleLineId() {
    return this.poleLineId; }

  public void setPoleLineId(Integer poleLineId) {
    this.poleLineId = poleLineId; }

  public String getPoleLineName() {
    return this.poleLineName; }

  public void setPoleLineName(String poleLineName) {
    this.poleLineName = poleLineName; }

  public String getPoleLineCode() {
    return this.poleLineCode; }

  public void setPoleLineCode(String poleLineCode) {
    this.poleLineCode = poleLineCode; }

  public Integer getMaintenanceAreaId() {
    return this.maintenanceAreaId; }

  public void setMaintenanceAreaId(Integer maintenanceAreaId) {
    this.maintenanceAreaId = maintenanceAreaId; }

  public String getPoleLineLevel() {
    return this.poleLineLevel; }

  public void setPoleLineLevel(String poleLineLevel) {
    this.poleLineLevel = poleLineLevel; }

  public String getPoleLineLength() {
    return this.poleLineLength; }

  public void setPoleLineLength(String poleLineLength) {
    this.poleLineLength = poleLineLength; }

  public String getStartDeviceName() {
    return this.startDeviceName; }

  public void setStartDeviceName(String startDeviceName) {
    this.startDeviceName = startDeviceName; }

  public String getEndDeviceName() {
    return this.endDeviceName; }

  public void setEndDeviceName(String endDeviceName) {
    this.endDeviceName = endDeviceName; }

  public Integer getMaintenanceModeEnumId() {
    return this.maintenanceModeEnumId; }

  public void setMaintenanceModeEnumId(Integer maintenanceModeEnumId) {
    this.maintenanceModeEnumId = maintenanceModeEnumId; }

  public String getMaintenanceOrgId() {
    return this.maintenanceOrgId; }

  public void setMaintenanceOrgId(String maintenanceOrgId) {
    this.maintenanceOrgId = maintenanceOrgId; }

  public String getMaintainerId() {
    return this.maintainerId; }

  public void setMaintainerId(String maintainerId) {
    this.maintainerId = maintainerId; }

  public String getThirdPartyMaintenanceOrg() {
    return this.thirdPartyMaintenanceOrg; }

  public void setThirdPartyMaintenanceOrg(String thirdPartyMaintenanceOrg) {
    this.thirdPartyMaintenanceOrg = thirdPartyMaintenanceOrg; }

  public Date getRenewalExpirationDate() {
    return this.renewalExpirationDate; }

  public void setRenewalExpirationDate(Date renewalExpirationDate) {
    this.renewalExpirationDate = renewalExpirationDate; }

  public Integer getMaintenanceTypeEnumId() {
    return this.maintenanceTypeEnumId; }

  public void setMaintenanceTypeEnumId(Integer maintenanceTypeEnumId) {
    this.maintenanceTypeEnumId = maintenanceTypeEnumId; }

  public String getPurchasedMaintenanceTime() {
    return this.purchasedMaintenanceTime; }

  public void setPurchasedMaintenanceTime(String purchasedMaintenanceTime) {
    this.purchasedMaintenanceTime = purchasedMaintenanceTime; }

  public String getProjectCode() {
    return this.projectCode; }

  public void setProjectCode(String projectCode) {
    this.projectCode = projectCode; }

  public String getProjectName() {
    return this.projectName; }

  public void setProjectName(String projectName) {
    this.projectName = projectName; }

  public Date getProjectWarrantyExpireDate() {
    return this.projectWarrantyExpireDate; }

  public void setProjectWarrantyExpireDate(Date projectWarrantyExpireDate) {
    this.projectWarrantyExpireDate = projectWarrantyExpireDate; }

  public Integer getResourceLifePeriodEnumId() {
    return this.resourceLifePeriodEnumId; }

  public void setResourceLifePeriodEnumId(Integer resourceLifePeriodEnumId) {
    this.resourceLifePeriodEnumId = resourceLifePeriodEnumId; }

  public Date getCreationDate() {
    return this.creationDate; }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate; }

  public Date getLastUpdateDate() {
    return this.lastUpdateDate; }

  public void setLastUpdateDate(Date lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate; }

  public String getDeletedFlag() {
    return this.deletedFlag; }

  public void setDeletedFlag(String deletedFlag) {
    this.deletedFlag = deletedFlag; }

  public Date getDeletionDate() {
    return this.deletionDate; }

  public void setDeletionDate(Date deletionDate) {
    this.deletionDate = deletionDate; }

  public String getProvinceId() {
    return this.provinceId; }

  public void setProvinceId(String provinceId) {
    this.provinceId = provinceId; }

  public String getAreano() {
    return this.areano; }

  public void setAreano(String areano) {
    this.areano = areano; }

  public String getAreaname() {
    return this.areaname; }

  public void setAreaname(String areaname) {
    this.areaname = areaname; }

  public Integer getLogId() {
    return this.logId; }

  public void setLogId(Integer logId) {
    this.logId = logId; }

  public Date getLogTime() {
    return this.logTime; }

  public void setLogTime(Date logTime) {
    this.logTime = logTime; }

  public String getLogOperater() {
    return this.logOperater; }

  public void setLogOperater(String logOperater) {
    this.logOperater = logOperater; }

  public String getSeriesNo() {
    return this.seriesNo; }

  public void setSeriesNo(String seriesNo) {
    this.seriesNo = seriesNo; }

  public String getAlias() {
    return this.alias; }

  public void setAlias(String alias) {
    this.alias = alias; }

  public Date getResourceLifePeriodEnumDate() {
    return this.resourceLifePeriodEnumDate; }

  public void setResourceLifePeriodEnumDate(Date resourceLifePeriodEnumDate) {
    this.resourceLifePeriodEnumDate = resourceLifePeriodEnumDate; }

  public String getDesignPurposes() {
    return this.designPurposes; }

  public void setDesignPurposes(String designPurposes) {
    this.designPurposes = designPurposes; }

  public Integer getIsAuthorizationManagement() {
    return this.isAuthorizationManagement; }

  public void setIsAuthorizationManagement(Integer isAuthorizationManagement) {
    this.isAuthorizationManagement = isAuthorizationManagement; }

  public String getAuthorizationManagementUnit() {
    return this.authorizationManagementUnit; }

  public void setAuthorizationManagementUnit(String authorizationManagementUnit) {
    this.authorizationManagementUnit = authorizationManagementUnit; }

  public String getNote() {
    return this.note; }

  public void setNote(String note) {
    this.note = note; }

  public String getIds() {
    return this.ids; }

  public void setIds(String ids) {
    this.ids = ids;
  }
}