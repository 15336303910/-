package manage.pipe.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PipeSegmentInfoBean implements Serializable,Cloneable
{
  private Integer pipeSegmentId;
  private String pipeSegmentName;
  private String pipeSegmentCode;
  private Integer maintenanceAreaId;//维护区域
  private String maintenanceAreaName;//维护区域
  private Integer pipeId;
  private String pipeName;
  private String roadName;
  private String pipeSegmentLength;
  private String pipeSegmentType;
  private String buriedDepth;
  private String startNestTopElevation;
  private String startNestBottomElevation;
  private Integer startDeviceId;
  private String startDeviceType;
  private String startDeviceName;
  private Integer endDeviceId;
  private String endDeviceType;
  private String endDeviceName;
  private String endNestTopElevation;
  private String endNestBottomElevation;
  private Integer pipeLineLayingEnumId;
  private String holeQuantity;
  private Integer constructionSharingEnumId;
  private String constructionSharingOrg;//产权单位
  private Integer sharingTypeEnumId;//产权性质
  private Integer rentFlag;
  private String buildAndShareNum;
  private String occupiedSharingHoleQuantity;
  private Date rentStartDate;
  private Date rentEndDate;
  private String rentOrg;
  private String rentChargingCode;
  private Integer resourceLifePeriodEnumId;
  private Date creationDate;
  private Date lastUpdateDate;
  private String deletedFlag;
  private Date deletionDate;
  private String provinceId;
  private String cuser;
  private String cstate;
  private String areano;
  private String areaname;
  private String startFaceLocation;
  private String endFaceLocation;
  private List<PipeSegmentInfoBean> pipesegments;
  private Integer total;
  private Integer limit;
  private Integer start;
  private Integer logId;
  private Date logTime;
  private String logOperater;
  private String seriesNo;
  private String assetsownership;
  private String occupyHoleQuantity;
  private String reserveHoleQuantity;
  private String fixedAssetsCode;
  private Date resourceLifePeriodEnumDate;
  private Integer isAuthorizationManagement;
  private String authorizationManagementUnit;
  private String designPurposes;
  private String note;
  private String ids;
  private String dataQualityPrincipal;
  private String parties;
  private String resNum;
  private String pipeSegOptical;//承载光缆段
  private String pipeSegOpticalId;//承载光缆段id
  private String maintainLength;//维护长度
  
  
  
  private double startLon; // 开始位置经度
  private double startLat; // 开始位置纬度
  private double endLon; // 终止位置经度
  private double endLat; // 终止位置纬度
  private String direction; // 方向
  private String extendsSql;//扩展语句
  private String maintenanceOrg;//维护单位
//  private String maintenanceArea;//维护区域
  private String maintenanceLeader;//维护区域组长
  private String leaderPhone;//维护区域组长电话
  private String remark;//备注 
  
  public String getMaintenanceOrg() {
	return maintenanceOrg;
}

public void setMaintenanceOrg(String maintenanceOrg) {
	this.maintenanceOrg = maintenanceOrg;
}

//public String getMaintenanceArea() {
//	return maintenanceArea;
//}
//
//public void setMaintenanceArea(String maintenanceArea) {
//	this.maintenanceArea = maintenanceArea;
//}

public String getMaintenanceLeader() {
	return maintenanceLeader;
}

public void setMaintenanceLeader(String maintenanceLeader) {
	this.maintenanceLeader = maintenanceLeader;
}

public String getLeaderPhone() {
	return leaderPhone;
}

public void setLeaderPhone(String leaderPhone) {
	this.leaderPhone = leaderPhone;
}

public String getRemark() {
	return remark;
}

public void setRemark(String remark) {
	this.remark = remark;
}

public Object clone(){
		Object o = null;
		try{
			o = (PipeSegmentInfoBean)super.clone();
		}catch(Exception e){
			e.printStackTrace();
		}
		return o;
		
	}
  
  public String getPipeSegOptical() {
	return pipeSegOptical;
  }

  public String getMaintainLength() {
	return maintainLength;
}

public void setMaintainLength(String maintainLength) {
	this.maintainLength = maintainLength;
}

public String getExtendsSql() {
	return extendsSql;
}

public void setExtendsSql(String extendsSql) {
	this.extendsSql = extendsSql;
}

public void setPipeSegOptical(String pipeSegOptical) {
	this.pipeSegOptical = pipeSegOptical;
  }

  public double getStartLon() {
	return startLon;
}

public void setStartLon(double startLon) {
	this.startLon = startLon;
}

public double getStartLat() {
	return startLat;
}

public void setStartLat(double startLat) {
	this.startLat = startLat;
}

public double getEndLon() {
	return endLon;
}

public void setEndLon(double endLon) {
	this.endLon = endLon;
}

public double getEndLat() {
	return endLat;
}

public void setEndLat(double endLat) {
	this.endLat = endLat;
}

public String getDirection() {
	return direction;
}

public void setDirection(String direction) {
	this.direction = direction;
}

public String getPipeSegOpticalId() {
	return pipeSegOpticalId;
}

public void setPipeSegOpticalId(String pipeSegOpticalId) {
	this.pipeSegOpticalId = pipeSegOpticalId;
}

public String getDataQualityPrincipal() {
	return dataQualityPrincipal;
  }

  public String getResNum() {
	return resNum;
}

public void setResNum(String resNum) {
	this.resNum = resNum;
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

public List<PipeSegmentInfoBean> getPipesegments()
  {
    return this.pipesegments; }

  public void setPipesegments(List<PipeSegmentInfoBean> pipesegments) {
    this.pipesegments = pipesegments; }

  public Integer getTotal() {
    return this.total; }

  public void setTotal(Integer total) {
    this.total = total; }

  public Integer getLimit() {
    return this.limit; }

  public void setLimit(Integer limit) {
    this.limit = limit; }

  public Integer getStart() {
    return this.start; }

  public void setStart(Integer start) {
    this.start = start; }

  public String getAreano() {
    return this.areano; }

  public void setAreano(String areano) {
    this.areano = areano; }

  public String getAreaname() {
    return this.areaname; }

  public void setAreaname(String areaname) {
    this.areaname = areaname; }

  public String getCuser() {
    return this.cuser; }

  public void setCuser(String cuser) {
    this.cuser = cuser; }

  public String getCstate() {
    return this.cstate; }

  public void setCstate(String cstate) {
    this.cstate = cstate; }

  public Integer getPipeSegmentId() {
    return this.pipeSegmentId; }

  public void setPipeSegmentId(Integer pipeSegmentId) {
    this.pipeSegmentId = pipeSegmentId; }

  public String getPipeSegmentName() {
    return this.pipeSegmentName; }

  public void setPipeSegmentName(String pipeSegmentName) {
    this.pipeSegmentName = pipeSegmentName; }

  public String getPipeSegmentCode() {
    return this.pipeSegmentCode; }

  public void setPipeSegmentCode(String pipeSegmentCode) {
    this.pipeSegmentCode = pipeSegmentCode; }

  public Integer getMaintenanceAreaId() {
    return this.maintenanceAreaId; }

  public void setMaintenanceAreaId(Integer maintenanceAreaId) {
    this.maintenanceAreaId = maintenanceAreaId; }

  public Integer getPipeId() {
    return this.pipeId; }

  public void setPipeId(Integer pipeId) {
    this.pipeId = pipeId; }

  public String getRoadName() {
    return this.roadName; }

  public void setRoadName(String roadName) {
    this.roadName = roadName; }

  public String getPipeSegmentLength() {
    return this.pipeSegmentLength; }

  public void setPipeSegmentLength(String pipeSegmentLength) {
    this.pipeSegmentLength = pipeSegmentLength; }

  public String getPipeSegmentType() {
    return this.pipeSegmentType; }

  public void setPipeSegmentType(String pipeSegmentType) {
    this.pipeSegmentType = pipeSegmentType; }

  public String getBuriedDepth() {
    return this.buriedDepth; }

  public void setBuriedDepth(String buriedDepth) {
    this.buriedDepth = buriedDepth; }

  public String getStartNestTopElevation() {
    return this.startNestTopElevation; }

  public void setStartNestTopElevation(String startNestTopElevation) {
    this.startNestTopElevation = startNestTopElevation; }

  public String getStartNestBottomElevation() {
    return this.startNestBottomElevation; }

  public void setStartNestBottomElevation(String startNestBottomElevation) {
    this.startNestBottomElevation = startNestBottomElevation;
  }

  public String getStartDeviceType() {
    return this.startDeviceType; }

  public void setStartDeviceType(String startDeviceType) {
    this.startDeviceType = startDeviceType;
  }

  public Integer getStartDeviceId() {
    return this.startDeviceId; }

  public void setStartDeviceId(Integer startDeviceId) {
    this.startDeviceId = startDeviceId; }

  public Integer getEndDeviceId() {
    return this.endDeviceId; }

  public void setEndDeviceId(Integer endDeviceId) {
    this.endDeviceId = endDeviceId; }

  public String getEndDeviceType() {
    return this.endDeviceType; }

  public void setEndDeviceType(String endDeviceType) {
    this.endDeviceType = endDeviceType; }

  public String getEndNestTopElevation() {
    return this.endNestTopElevation; }

  public void setEndNestTopElevation(String endNestTopElevation) {
    this.endNestTopElevation = endNestTopElevation; }

  public String getEndNestBottomElevation() {
    return this.endNestBottomElevation; }

  public void setEndNestBottomElevation(String endNestBottomElevation) {
    this.endNestBottomElevation = endNestBottomElevation; }

  public Integer getPipeLineLayingEnumId() {
    return this.pipeLineLayingEnumId; }

  public void setPipeLineLayingEnumId(Integer pipeLineLayingEnumId) {
    this.pipeLineLayingEnumId = pipeLineLayingEnumId;
  }

  public String getHoleQuantity() {
    return this.holeQuantity; }

  public void setHoleQuantity(String holeQuantity) {
    this.holeQuantity = holeQuantity; }

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
    this.sharingTypeEnumId = sharingTypeEnumId;
  }

  public String getBuildAndShareNum() {
    return this.buildAndShareNum; }

  public void setBuildAndShareNum(String buildAndShareNum) {
    this.buildAndShareNum = buildAndShareNum; }

  public String getOccupiedSharingHoleQuantity() {
    return this.occupiedSharingHoleQuantity; }

  public void setOccupiedSharingHoleQuantity(String occupiedSharingHoleQuantity) {
    this.occupiedSharingHoleQuantity = occupiedSharingHoleQuantity; }

  public Integer getRentFlag() {
    return this.rentFlag; }

  public void setRentFlag(Integer rentFlag) {
    this.rentFlag = rentFlag; }

  public Date getRentStartDate() {
    return this.rentStartDate; }

  public void setRentStartDate(Date rentStartDate) {
    this.rentStartDate = rentStartDate; }

  public Date getRentEndDate() {
    return this.rentEndDate; }

  public void setRentEndDate(Date rentEndDate) {
    this.rentEndDate = rentEndDate; }

  public String getRentOrg() {
    return this.rentOrg; }

  public void setRentOrg(String rentOrg) {
    this.rentOrg = rentOrg; }

  public String getRentChargingCode() {
    return this.rentChargingCode; }

  public void setRentChargingCode(String rentChargingCode) {
    this.rentChargingCode = rentChargingCode;
  }

  public Integer getResourceLifePeriodEnumId()
  {
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

  public String getStartDeviceName() {
    return this.startDeviceName; }

  public void setStartDeviceName(String startDeviceName) {
    this.startDeviceName = startDeviceName; }

  public String getEndDeviceName() {
    return this.endDeviceName; }

  public void setEndDeviceName(String endDeviceName) {
    this.endDeviceName = endDeviceName; }

  public String getMaintenanceAreaName() {
    return this.maintenanceAreaName; }

  public void setMaintenanceAreaName(String maintenanceAreaName) {
    this.maintenanceAreaName = maintenanceAreaName; }

  public String getPipeName() {
    return this.pipeName; }

  public void setPipeName(String pipeName) {
    this.pipeName = pipeName;
  }

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

  public String getStartFaceLocation() {
    return this.startFaceLocation; }

  public void setStartFaceLocation(String startFaceLocation) {
    this.startFaceLocation = startFaceLocation; }

  public String getEndFaceLocation() {
    return this.endFaceLocation; }

  public void setEndFaceLocation(String endFaceLocation) {
    this.endFaceLocation = endFaceLocation; }

  public String getSeriesNo() {
    return this.seriesNo; }

  public void setSeriesNo(String seriesNo) {
    this.seriesNo = seriesNo; }

  public String getAssetsownership() {
    return this.assetsownership; }

  public void setAssetsownership(String assetsownership) {
    this.assetsownership = assetsownership; }

  public String getOccupyHoleQuantity() {
    return this.occupyHoleQuantity; }

  public void setOccupyHoleQuantity(String occupyHoleQuantity) {
    this.occupyHoleQuantity = occupyHoleQuantity; }

  public String getReserveHoleQuantity() {
    return this.reserveHoleQuantity; }

  public void setReserveHoleQuantity(String reserveHoleQuantity) {
    this.reserveHoleQuantity = reserveHoleQuantity; }

  public String getFixedAssetsCode() {
    return this.fixedAssetsCode; }

  public void setFixedAssetsCode(String fixedAssetsCode) {
    this.fixedAssetsCode = fixedAssetsCode; }

  public Date getResourceLifePeriodEnumDate() {
    return this.resourceLifePeriodEnumDate; }

  public void setResourceLifePeriodEnumDate(Date resourceLifePeriodEnumDate) {
    this.resourceLifePeriodEnumDate = resourceLifePeriodEnumDate; }

  public Integer getIsAuthorizationManagement() {
    return this.isAuthorizationManagement; }

  public void setIsAuthorizationManagement(Integer isAuthorizationManagement) {
    this.isAuthorizationManagement = isAuthorizationManagement; }

  public String getAuthorizationManagementUnit() {
    return this.authorizationManagementUnit; }

  public void setAuthorizationManagementUnit(String authorizationManagementUnit) {
    this.authorizationManagementUnit = authorizationManagementUnit; }

  public String getDesignPurposes() {
    return this.designPurposes; }

  public void setDesignPurposes(String designPurposes) {
    this.designPurposes = designPurposes; }

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