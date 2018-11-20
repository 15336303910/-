package interfaces.irmsInterface.interfaces.pojo;

import java.io.Serializable;


public class MoveResPojo implements Serializable {
	
	private String resName;//资源名称
	private String imId;//易采id
	private String resId;//资源id
	private String longitude;//经度
	private String latitude;//纬度
	private String appType;//APP的动作
	private String appCnType;//app中文动作
	private String resType;//资源类型
	private String inXml;//输入xml
	private String qualitor;//数据质量维护人
	private String maintainor;//维护人
	private String region;//地市区县
	private String cityId;//地市id
	private String countyId;//区县id
	private String imEnType;//易采的英文类型
	private String imCnType;//易采的中文类型
	private String extendXml;//额外增加的字段
	private String parentid;//额外的资源点ID
	
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getExtendXml() {
		return extendXml;
	}
	public void setExtendXml(String extendXml) {
		this.extendXml = extendXml;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCountyId() {
		return countyId;
	}
	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}
	public String getImEnType() {
		return imEnType;
	}
	public void setImEnType(String imEnType) {
		this.imEnType = imEnType;
	}
	public String getImCnType() {
		return imCnType;
	}
	public void setImCnType(String imCnType) {
		this.imCnType = imCnType;
	}
	public String getImId() {
		return imId;
	}
	public void setImId(String imId) {
		this.imId = imId;
	}
	public String getAppCnType() {
		return appCnType;
	}
	public void setAppCnType(String appCnType) {
		this.appCnType = appCnType;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getMaintainor() {
		return maintainor;
	}
	public void setMaintainor(String maintainor) {
		this.maintainor = maintainor;
	}
	public String getQualitor() {
		return qualitor;
	}
	public void setQualitor(String qualitor) {
		this.qualitor = qualitor;
	}
	public String getInXml() {
		return inXml;
	}
	public void setInXml(String inXml) {
		this.inXml = inXml;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
}
