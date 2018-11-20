package manage.leadup.pojo;

import java.io.Serializable;
import java.util.Date;

public class HangWallPojo implements Serializable{
	private Integer id;//挂墙唯一主键
	private String hangWallName;//挂墙名称
	private String maintMode;//维护方式
	private String maintArea;//维护区县
	private String direction;//维护方向
	private String purpose;//用途
	private String username;//使用单位
	private String systemLevel;//级别
	private Double hangLength;//挂墙长度
	private Double maintLength;//维护长度
	private Integer startDeviceId;//起始撑点id 
	private String startDeviceName;//起始撑点名称
	private Integer endDeviceId;//终止撑点id
	private String endDeviceName;//终止撑点名称
	private String propertyNature;//产权性质
	private String propertyComp;//产权单位
	private String dataQualitier;//数据质量维护人
	private String maintainer;//一线维护人
	private Date createTime;//创建时间
	private String creater;//创建人
	private Integer deleteflag;//删除标识
	private Date lastUpTime;//最近更新时间
	private String resNum;//资源标识
	private String hangWallOptical;//敷设光缆段
	private String hangWallOpticalId;//敷设光缆段id
	private String maintainLength;//维护长度
	
	private Integer start;
	private Integer limit;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHangWallName() {
		return hangWallName;
	}
	public void setHangWallName(String hangWallName) {
		this.hangWallName = hangWallName;
	}
	public String getMaintMode() {
		return maintMode;
	}
	public void setMaintMode(String maintMode) {
		this.maintMode = maintMode;
	}
	public String getMaintArea() {
		return maintArea;
	}
	public void setMaintArea(String maintArea) {
		this.maintArea = maintArea;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMaintainLength() {
		return maintainLength;
	}
	public void setMaintainLength(String maintainLength) {
		this.maintainLength = maintainLength;
	}
	public String getSystemLevel() {
		return systemLevel;
	}
	public void setSystemLevel(String systemLevel) {
		this.systemLevel = systemLevel;
	}
	public Double getHangLength() {
		return hangLength;
	}
	public void setHangLength(Double hangLength) {
		this.hangLength = hangLength;
	}
	public Double getMaintLength() {
		return maintLength;
	}
	public void setMaintLength(Double maintLength) {
		this.maintLength = maintLength;
	}
	public Integer getStartDeviceId() {
		return startDeviceId;
	}
	public void setStartDeviceId(Integer startDeviceId) {
		this.startDeviceId = startDeviceId;
	}
	public String getStartDeviceName() {
		return startDeviceName;
	}
	public void setStartDeviceName(String startDeviceName) {
		this.startDeviceName = startDeviceName;
	}
	public Integer getEndDeviceId() {
		return endDeviceId;
	}
	public void setEndDeviceId(Integer endDeviceId) {
		this.endDeviceId = endDeviceId;
	}
	public String getEndDeviceName() {
		return endDeviceName;
	}
	public void setEndDeviceName(String endDeviceName) {
		this.endDeviceName = endDeviceName;
	}
	public String getPropertyNature() {
		return propertyNature;
	}
	public void setPropertyNature(String propertyNature) {
		this.propertyNature = propertyNature;
	}
	public String getPropertyComp() {
		return propertyComp;
	}
	public void setPropertyComp(String propertyComp) {
		this.propertyComp = propertyComp;
	}
	public String getDataQualitier() {
		return dataQualitier;
	}
	public void setDataQualitier(String dataQualitier) {
		this.dataQualitier = dataQualitier;
	}
	public String getMaintainer() {
		return maintainer;
	}
	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Integer getDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(Integer deleteflag) {
		this.deleteflag = deleteflag;
	}
	public Date getLastUpTime() {
		return lastUpTime;
	}
	public void setLastUpTime(Date lastUpTime) {
		this.lastUpTime = lastUpTime;
	}
	public String getResNum() {
		return resNum;
	}
	public void setResNum(String resNum) {
		this.resNum = resNum;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public String getHangWallOptical() {
		return hangWallOptical;
	}
	public void setHangWallOptical(String hangWallOptical) {
		this.hangWallOptical = hangWallOptical;
	}
	public String getHangWallOpticalId() {
		return hangWallOpticalId;
	}
	public void setHangWallOpticalId(String hangWallOpticalId) {
		this.hangWallOpticalId = hangWallOpticalId;
	}
	
}
