package manage.resPoint.pojo;

import java.io.Serializable;
import java.util.Date;

public class WirelessPointPojo implements Serializable{

	private Integer wirelessId;//主键
	private String wirelessNo;//资源别名
	private String wirelessName;//资源点名称
	private String longitude;//经度
	private String latitude;//纬度
	private String wirelessArea;//地址详细地址
	private String wirelessCounty;//所属区县
	private String synthBusiness;//所属综合业务区
	
	private Integer propertyNature;//产权性质
	private Integer propertyComp;//产权单位
	private String dataQualitier;//数据质量责任人
	private String maintainer;//一线维护人
	private String deleteflag;//删除标识
	private Date lastUpTime;//最近修改时间
	private Date createTime;//创建时间 
	private String creater;//创建人
	private String remark;//备注 
	private String resNum;//资源标识
	private String building;//所属楼宇信息
	
	
	private String lats;
	private String lons;
	private String late;
	private String lone;
	private Integer start;
	private Integer limit;
	
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getSynthBusiness() {
		return synthBusiness;
	}
	public void setSynthBusiness(String synthBusiness) {
		this.synthBusiness = synthBusiness;
	}
	public Integer getWirelessId() {
		return wirelessId;
	}
	public void setWirelessId(Integer wirelessId) {
		this.wirelessId = wirelessId;
	}
	public String getWirelessNo() {
		return wirelessNo;
	}
	public void setWirelessNo(String wirelessNo) {
		this.wirelessNo = wirelessNo;
	}
	public String getWirelessName() {
		return wirelessName;
	}
	public void setWirelessName(String wirelessName) {
		this.wirelessName = wirelessName;
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
	public String getWirelessArea() {
		return wirelessArea;
	}
	public void setWirelessArea(String wirelessArea) {
		this.wirelessArea = wirelessArea;
	}
	public String getWirelessCounty() {
		return wirelessCounty;
	}
	public void setWirelessCounty(String wirelessCounty) {
		this.wirelessCounty = wirelessCounty;
	}
	public Integer getPropertyNature() {
		return propertyNature;
	}
	public void setPropertyNature(Integer propertyNature) {
		this.propertyNature = propertyNature;
	}
	public Integer getPropertyComp() {
		return propertyComp;
	}
	public void setPropertyComp(Integer propertyComp) {
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
	public String getDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}
	public Date getLastUpTime() {
		return lastUpTime;
	}
	public void setLastUpTime(Date lastUpTime) {
		this.lastUpTime = lastUpTime;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getResNum() {
		return resNum;
	}
	public void setResNum(String resNum) {
		this.resNum = resNum;
	}
	public String getLats() {
		return lats;
	}
	public void setLats(String lats) {
		this.lats = lats;
	}
	public String getLons() {
		return lons;
	}
	public void setLons(String lons) {
		this.lons = lons;
	}
	public String getLate() {
		return late;
	}
	public void setLate(String late) {
		this.late = late;
	}
	public String getLone() {
		return lone;
	}
	public void setLone(String lone) {
		this.lone = lone;
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
	
}
