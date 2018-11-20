package manage.pipe.pojo;

import java.util.Date;
import java.util.List;

public class TubeInfoBean
  implements Cloneable
{
  private Integer tubeId;
  private String tubeName;
  private String tubeNumber;
  private String pipeSegmentId;
  private String pipeSegmentName;
  private Integer fatherPoreId;
  private String fatherPoreName;
  private String subTubeArrangeMode;
  private Integer subTubeAmount;
  private Integer tubeStatusEnumId;
  private String tubeDiameter;
  private String tubeColor;
  private String tubeMaterial;
  private String tubeShape;
  private String rentFlag;
  private String shape;
  private String rentOrg;
  private Integer resourceLifePeriodEnumId;
  private String bearCableSegment;
  private String bearCableSegmentId;
  private String redunBearCableSegment;
  private String redunBearCableSegmentId;
  private Date creationDate;
  private Date lastUpdateDate;
  private String deletedFlag;
  private Date deletionDate;
  private String provinceId;
  private Integer maintenanceAreaId;
  private String maintenanceAreaName;
  private String cuser;
  private String cstate;
  private String duiwellId;
  private String duifaceId;
  private String duitubeNumber;
  private String duitubeName;
  private String duirfid;
  private Integer constructionSharingEnumId;
  private String constructionSharingOrg;
  private Integer sharingTypeEnumId;
  private String isFather;
  private String wellId;
  private String faceId;
  private String rfid;
  private String pipeName;
  private String wellName;
  private String faceNo;
  private String areano;
  private String areaname;
  private String tubeNames;
  private Integer limit;
  private Integer start;
  private Integer total;
  private List<TubeInfoBean> tubes;
  private Integer logId;
  private Date logTime;
  private String logOperater;
  private String seriesNo;
  private String occupysubTube;
  private String inPipesegCode;
  private String assetsownership;
  private Date resourceLifePeriodEnumDate;
  private Integer isAuthorizationManagement;
  private String authorizationManagementUnit;
  private String designPurposes;
  private String note;
  private String ids;
  private String resNum;

  public Object clone()
  {
    TubeInfoBean t = null;
    try {
      t = (TubeInfoBean)super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return t;
  }

  public String getShape() {
	return shape;
}

public void setShape(String shape) {
	this.shape = shape;
}

public String getResNum() {
	return resNum;
}

public void setResNum(String resNum) {
	this.resNum = resNum;
}

public String getCuser() {
    return this.cuser; }

  public void setCuser(String cuser) {
    this.cuser = cuser; }

  public String getCstate() {
    return this.cstate; }

  public void setCstate(String cstate) {
    this.cstate = cstate; }

  public Integer getTubeId() {
    return this.tubeId; }

  public void setTubeId(Integer tubeId) {
    this.tubeId = tubeId; }

  public String getTubeName() {
    return this.tubeName; }

  public void setTubeName(String tubeName) {
    this.tubeName = tubeName; }

  public String getTubeNumber() {
    return this.tubeNumber; }

  public void setTubeNumber(String tubeNumber) {
    this.tubeNumber = tubeNumber; }

  public String getPipeSegmentId() {
    return this.pipeSegmentId; }

  public void setPipeSegmentId(String pipeSegmentId) {
    this.pipeSegmentId = pipeSegmentId;
  }

  public Integer getFatherPoreId() {
    return this.fatherPoreId; }

  public void setFatherPoreId(Integer fatherPoreId) {
    this.fatherPoreId = fatherPoreId; }

  public String getFatherPoreName() {
    return this.fatherPoreName; }

  public void setFatherPoreName(String fatherPoreName) {
    this.fatherPoreName = fatherPoreName; }

  public String getSubTubeArrangeMode() {
    return this.subTubeArrangeMode; }

  public void setSubTubeArrangeMode(String subTubeArrangeMode) {
    this.subTubeArrangeMode = subTubeArrangeMode; }

  public Integer getTubeStatusEnumId() {
    return this.tubeStatusEnumId; }

  public void setTubeStatusEnumId(Integer tubeStatusEnumId) {
    this.tubeStatusEnumId = tubeStatusEnumId; }

  public String getTubeDiameter() {
    return this.tubeDiameter; }

  public void setTubeDiameter(String tubeDiameter) {
    this.tubeDiameter = tubeDiameter; }

  public String getTubeColor() {
    return this.tubeColor; }

  public void setTubeColor(String tubeColor) {
    this.tubeColor = tubeColor; }

  public String getTubeMaterial() {
    return this.tubeMaterial; }

  public void setTubeMaterial(String tubeMaterial) {
    this.tubeMaterial = tubeMaterial; }

  public String getTubeShape() {
    return this.tubeShape; }

  public void setTubeShape(String tubeShape) {
    this.tubeShape = tubeShape; }

  public String getRentFlag() {
    return this.rentFlag; }

  public void setRentFlag(String rentFlag) {
    this.rentFlag = rentFlag; }

  public String getRentOrg() {
    return this.rentOrg; }

  public void setRentOrg(String rentOrg) {
    this.rentOrg = rentOrg;
  }

  public Integer getResourceLifePeriodEnumId()
  {
    return this.resourceLifePeriodEnumId; }

  public void setResourceLifePeriodEnumId(Integer resourceLifePeriodEnumId) {
    this.resourceLifePeriodEnumId = resourceLifePeriodEnumId; }

  public String getBearCableSegment() {
    return this.bearCableSegment; }

  public void setBearCableSegment(String bearCableSegment) {
    this.bearCableSegment = bearCableSegment; }

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

  public Integer getMaintenanceAreaId() {
    return this.maintenanceAreaId; }

  public void setMaintenanceAreaId(Integer maintenanceAreaId) {
    this.maintenanceAreaId = maintenanceAreaId; }

  public String getIsFather() {
    return this.isFather; }

  public void setIsFather(String isFather) {
    this.isFather = isFather; }

  public String getWellId() {
    return this.wellId; }

  public void setWellId(String wellId) {
    this.wellId = wellId; }

  public String getBearCableSegmentId() {
    return this.bearCableSegmentId; }

  public void setBearCableSegmentId(String bearCableSegmentId) {
    this.bearCableSegmentId = bearCableSegmentId; }

  public String getRedunBearCableSegment() {
    return this.redunBearCableSegment; }

  public void setRedunBearCableSegment(String redunBearCableSegment) {
    this.redunBearCableSegment = redunBearCableSegment; }

  public String getRedunBearCableSegmentId() {
    return this.redunBearCableSegmentId; }

  public void setRedunBearCableSegmentId(String redunBearCableSegmentId) {
    this.redunBearCableSegmentId = redunBearCableSegmentId; }

  public String getMaintenanceAreaName() {
    return this.maintenanceAreaName; }

  public void setMaintenanceAreaName(String maintenanceAreaName) {
    this.maintenanceAreaName = maintenanceAreaName; }

  public String getPipeSegmentName() {
    return this.pipeSegmentName; }

  public void setPipeSegmentName(String pipeSegmentName) {
    this.pipeSegmentName = pipeSegmentName; }

  public String getPipeName() {
    return this.pipeName; }

  public void setPipeName(String pipeName) {
    this.pipeName = pipeName; }

  public String getWellName() {
    return this.wellName; }

  public void setWellName(String wellName) {
    this.wellName = wellName; }

  public String getFaceNo() {
    return this.faceNo; }

  public void setFaceNo(String faceNo) {
    this.faceNo = faceNo; }

  public String getAreano() {
    return this.areano; }

  public void setAreano(String areano) {
    this.areano = areano; }

  public String getAreaname() {
    return this.areaname; }

  public void setAreaname(String areaname) {
    this.areaname = areaname; }

  public Integer getSubTubeAmount() {
    return this.subTubeAmount; }

  public void setSubTubeAmount(Integer subTubeAmount) {
    this.subTubeAmount = subTubeAmount; }

  public String getFaceId() {
    return this.faceId; }

  public void setFaceId(String faceId) {
    this.faceId = faceId; }

  public String getTubeNames() {
    return this.tubeNames; }

  public void setTubeNames(String tubeNames) {
    this.tubeNames = tubeNames; }

  public Integer getLimit() {
    return this.limit; }

  public void setLimit(Integer limit) {
    this.limit = limit; }

  public Integer getStart() {
    return this.start; }

  public void setStart(Integer start) {
    this.start = start; }

  public Integer getTotal() {
    return this.total; }

  public void setTotal(Integer total) {
    this.total = total; }

  public List<TubeInfoBean> getTubes() {
    return this.tubes; }

  public void setTubes(List<TubeInfoBean> tubes) {
    this.tubes = tubes; }

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

  public String getDuiwellId() {
    return this.duiwellId; }

  public void setDuiwellId(String duiwellId) {
    this.duiwellId = duiwellId; }

  public String getDuifaceId() {
    return this.duifaceId; }

  public void setDuifaceId(String duifaceId) {
    this.duifaceId = duifaceId; }

  public String getDuitubeNumber() {
    return this.duitubeNumber; }

  public void setDuitubeNumber(String duitubeNumber) {
    this.duitubeNumber = duitubeNumber; }

  public String getRfid() {
    return this.rfid; }

  public void setRfid(String rfid) {
    this.rfid = rfid; }

  public String getSeriesNo() {
    return this.seriesNo; }

  public void setSeriesNo(String seriesNo) {
    this.seriesNo = seriesNo; }

  public Integer getConstructionSharingEnumId() {
    return this.constructionSharingEnumId; }

  public void setConstructionSharingEnumId(Integer constructionSharingEnumId) {
    this.constructionSharingEnumId = constructionSharingEnumId; }

  public String getConstructionSharingOrg() {
    return this.constructionSharingOrg; }

  public void setConstructionSharingOrg(String constructionSharingOrg) {
    this.constructionSharingOrg = constructionSharingOrg; }

  public Integer getSharingTypeEnumId() {
    return this.sharingTypeEnumId; }

  public void setSharingTypeEnumId(Integer sharingTypeEnumId) {
    this.sharingTypeEnumId = sharingTypeEnumId; }

  public String getDuitubeName() {
    return this.duitubeName; }

  public void setDuitubeName(String duitubeName) {
    this.duitubeName = duitubeName;
  }

  public String getDuirfid() {
    return this.duirfid;
  }

  public void setDuirfid(String duirfid) {
    this.duirfid = duirfid;
  }

  public String getInPipesegCode() {
    return this.inPipesegCode;
  }

  public void setInPipesegCode(String inPipesegCode) {
    this.inPipesegCode = inPipesegCode;
  }

  public String getAssetsownership() {
    return this.assetsownership;
  }

  public void setAssetsownership(String assetsownership) {
    this.assetsownership = assetsownership;
  }

  public Date getResourceLifePeriodEnumDate() {
    return this.resourceLifePeriodEnumDate;
  }

  public void setResourceLifePeriodEnumDate(Date resourceLifePeriodEnumDate) {
    this.resourceLifePeriodEnumDate = resourceLifePeriodEnumDate;
  }

  public Integer getIsAuthorizationManagement() {
    return this.isAuthorizationManagement;
  }

  public void setIsAuthorizationManagement(Integer isAuthorizationManagement) {
    this.isAuthorizationManagement = isAuthorizationManagement;
  }

  public String getAuthorizationManagementUnit() {
    return this.authorizationManagementUnit;
  }

  public void setAuthorizationManagementUnit(String authorizationManagementUnit) {
    this.authorizationManagementUnit = authorizationManagementUnit;
  }

  public String getDesignPurposes() {
    return this.designPurposes;
  }

  public void setDesignPurposes(String designPurposes) {
    this.designPurposes = designPurposes;
  }

  public String getNote() {
    return this.note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public String getIds() {
    return this.ids;
  }

  public void setIds(String ids) {
    this.ids = ids;
  }

  public String getOccupysubTube() {
    return this.occupysubTube;
  }

  public void setOccupysubTube(String occupysubTube) {
    this.occupysubTube = occupysubTube;
  }
}