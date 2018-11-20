package manage.opticalTerminal.pojo;

import java.io.Serializable;

/**
 * 光终端盒
 * @author Administrator
 *
 */
public class OpticalTerminalObj implements Serializable{
	
	private Integer id;//主键标识
	private String terminalName;//光终端盒名称
	private Integer terminalNum;//光终端盒序号
	private String longitude;//经度
	private String latitude;//纬度
	private Integer rowNum;//行数
	private Integer colNum;//列数
	private String attachName;//归属点名称
	private String attachId;//归属点id
	private Integer attachType;//归属点类型
	private String attachTypeStr;//归属点
	private String terminalAddres;//设备地址
	private String dataQualitier;//数据质量责任人
	private String maintainer;//一线维护人
	private String deleteflag;//删除标识
	private String createTime;//创建时间
	private String creater;//创建人
	private String remark;//备注
	private String lastUpTime;//最近修改时间
	private String lastUpMan;//最近修改人
	private Integer start;
	private Integer limit;
	private String resNum;
	private String lats;
	private String lons;
	private String late;
	private String lone;
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
	public String getResNum() {
		return resNum;
	}
	public void setResNum(String resNum) {
		this.resNum = resNum;
	}
	public String getAttachId() {
		return attachId;
	}
	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}
	public String getAttachName() {
		return attachName;
	}
	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	public String getAttachTypeStr() {
		return attachTypeStr;
	}
	public void setAttachTypeStr(String attachTypeStr) {
		this.attachTypeStr = attachTypeStr;
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
	public String getLastUpTime() {
		return lastUpTime;
	}
	public void setLastUpTime(String lastUpTime) {
		this.lastUpTime = lastUpTime;
	}
	public String getLastUpMan() {
		return lastUpMan;
	}
	public void setLastUpMan(String lastUpMan) {
		this.lastUpMan = lastUpMan;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTerminalName() {
		return terminalName;
	}
	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
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
	public Integer getRowNum() {
		return rowNum;
	}
	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}
	public Integer getColNum() {
		return colNum;
	}
	public void setColNum(Integer colNum) {
		this.colNum = colNum;
	}
	public Integer getAttachType() {
		return attachType;
	}
	public void setAttachType(Integer attachType) {
		this.attachType = attachType;
	}
	public String getTerminalAddres() {
		return terminalAddres;
	}
	public void setTerminalAddres(String terminalAddres) {
		this.terminalAddres = terminalAddres;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
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
	public Integer getTerminalNum() {
		return terminalNum;
	}
	public void setTerminalNum(Integer terminalNum) {
		this.terminalNum = terminalNum;
	}
	
}
