package interfaces.pdainterface.lineSystem.pojo;

import java.io.Serializable;
import java.util.Date;

public class LineSystemInfo implements Serializable{
	private Integer id;//管线系统id
	private String lineName;//管线系统名称
	private String lineArea;//所属维护区县
	private String lineModel;//维护方式
	private Integer lineType;//管线类型  0 杆路， 1 直埋，2 管道,3挂墙，4光缆
	private String lineComp;//维护单位
	private String serviceLevel;//服务级别
	private String countLength;//计算长度
	private String maintainLength;//维护长度
	private Integer direction;//方向
	private Integer deleteflag;//删除标识
	private Date createTime;//创建时间
	private String creater;//创建人
	private String remark;//备注
	private Date lastUpTime;//修改时间
	private String lastUpMan;//修改人
	private String resNum;//综资标识
	
	private String longitude;//经度
	private String latitude;//纬度
	
	private Integer propertyNature;//产权性质
	private Integer propertyComp;//产权单位
	
	private String lats;//经度纬度的差值
	private String lons;//经度纬度的差值
	private String late;//经度纬度的差值
	private String lone;//经度纬度的差值
	
	private String extendsSql;//扩展语句
	
	private Integer start;
	private Integer limit;
	
	public String getExtendsSql() {
		return extendsSql;
	}
	public void setExtendsSql(String extendsSql) {
		this.extendsSql = extendsSql;
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
	public Integer getDirection() {
		return direction;
	}
	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	public String getCountLength() {
		return countLength;
	}
	public void setCountLength(String countLength) {
		this.countLength = countLength;
	}
	public Integer getStart() {
		return start;
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
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getLineArea() {
		return lineArea;
	}
	public void setLineArea(String lineArea) {
		this.lineArea = lineArea;
	}
	public String getLineModel() {
		return lineModel;
	}
	public void setLineModel(String lineModel) {
		this.lineModel = lineModel;
	}
	public Integer getLineType() {
		return lineType;
	}
	public void setLineType(Integer lineType) {
		this.lineType = lineType;
	}
	public String getLineComp() {
		return lineComp;
	}
	public void setLineComp(String lineComp) {
		this.lineComp = lineComp;
	}
	public String getServiceLevel() {
		return serviceLevel;
	}
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	public String getMaintainLength() {
		return maintainLength;
	}
	public void setMaintainLength(String maintainLength) {
		this.maintainLength = maintainLength;
	}
	public Integer getDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(Integer deleteflag) {
		this.deleteflag = deleteflag;
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
	public Date getLastUpTime() {
		return lastUpTime;
	}
	public void setLastUpTime(Date lastUpTime) {
		this.lastUpTime = lastUpTime;
	}
	public String getLastUpMan() {
		return lastUpMan;
	}
	public void setLastUpMan(String lastUpMan) {
		this.lastUpMan = lastUpMan;
	}
	public String getResNum() {
		return resNum;
	}
	public void setResNum(String resNum) {
		this.resNum = resNum;
	}
	
	
}
