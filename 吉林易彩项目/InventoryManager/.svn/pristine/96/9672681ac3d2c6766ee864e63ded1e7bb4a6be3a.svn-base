package interfaces.pdainterface.lineSystem.pojo;

import java.io.Serializable;
public class LinePointInfo implements Serializable,Cloneable{

	private Integer id;//id
	private String name;//名称
	private String type;//类型0 电杆， 1 标石，2 管井    如果是光缆   1 光交箱  2接口盒   3 站点   4  光终端盒
	private String longitude;//经度
	private String latitude;//纬度
	private Integer sysId;//系统id
	private String sysName;//系统名称
	private Integer sysType;//系统类型0 杆路， 1 直埋，2 管道
	private String sysRegion;//系统维护区县
	private Integer deleteflag;
	private String extendSql;//扩展语句

	private Integer distance;//距离
	private String lats;
	private String lons;
	private String late;
	private String lone;
	
	private String maintain;//维护人
	private String startTime;//记录采集开始时间
	private String endTime;//记录采集结束时间
	private Integer start;
	private Integer limit;
	
	
	public Object clone(){
		Object o = null;
		try{
			o = (LinePointInfo)super.clone();
		}catch(Exception e){
			e.printStackTrace();
		}
		return o;
		
	}
	
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	public String getMaintain() {
		return maintain;
	}
	public void setMaintain(String maintain) {
		this.maintain = maintain;
	}
	public String getStartTime() {
		return startTime;
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
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getExtendSql() {
		return extendSql;
	}
	public void setExtendSql(String extendSql) {
		this.extendSql = extendSql;
	}
	public Integer getStart() {
		return start;
	}
	public Integer getDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(Integer deleteflag) {
		this.deleteflag = deleteflag;
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
	public String getSysRegion() {
		return sysRegion;
	}
	public void setSysRegion(String sysRegion) {
		this.sysRegion = sysRegion;
	}
	public Integer getId() {
		return id;
	}
	public Integer getSysId() {
		return sysId;
	}
	public void setSysId(Integer sysId) {
		this.sysId = sysId;
	}
	public String getSysName() {
		return sysName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSysType() {
		return sysType;
	}
	public void setSysType(Integer sysType) {
		this.sysType = sysType;
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
	
}
