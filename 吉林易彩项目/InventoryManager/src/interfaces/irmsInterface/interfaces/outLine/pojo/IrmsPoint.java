package interfaces.irmsInterface.interfaces.outLine.pojo;

import java.io.Serializable;

public class IrmsPoint implements Serializable{
	private String mode;//模型
	private String type;//资源类型综资的
	private String area;//资源所属区域
	private String cityId;//综资的地市id
	private String countryId;//综资的区县id
	private String imId;//易采的id用于新增
	private String latitude;//点资源纬度
	private String longitude;//点资源经度
	private String resName;//资源名称
	private String resNum;//资源编号
	private String qualitor;//数据质量维护人
	private String maintainor;//一线维护人
	private String exeSql;//执行语句
	private String tableName;//易采的表名称
	private String idCol;//主键字段 
	
	public String getIdCol() {
		return idCol;
	}
	public void setIdCol(String idCol) {
		this.idCol = idCol;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getResNum() {
		return resNum;
	}
	public void setResNum(String resNum) {
		this.resNum = resNum;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getImId() {
		return imId;
	}
	public void setImId(String imId) {
		this.imId = imId;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getQualitor() {
		return qualitor;
	}
	public void setQualitor(String qualitor) {
		this.qualitor = qualitor;
	}
	public String getExeSql() {
		return exeSql;
	}
	public void setExeSql(String exeSql) {
		this.exeSql = exeSql;
	}
	public String getMaintainor() {
		return maintainor;
	}
	public void setMaintainor(String maintainor) {
		this.maintainor = maintainor;
	}
	
}
