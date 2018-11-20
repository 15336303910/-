package manage.leadup.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupportPointPojo implements Serializable{
	private Integer id;//撑点id
	private String sportName;//撑点名称
	private Integer status;//生命周期状态
	private Integer maintMode;//维护方式
	private String purpose;//用途
	private String maintArea;//维护区县
	private String maintDept;//维护单位
	private String longitude;//经度
	private String latitude;//纬度
	private Integer propertyComp;//产权单位
	private Integer propertyNature;//产权性质
	private String dataQualitier;//数据质量维护人
	private String maintainer;//一线维护人
	private Date createTime;//创建时间
	private String creater;//创建人
	private Integer deleteflag;//删除标识
	private Date lastUpTime;//最近更新时间
	private String lastUpMan;//最近修改人
	private String resNum;//资源标识
	private Integer start;
	private Integer limit;
	private String lats;
	private String lons;
	private String late;
	private String lone;
	private List<SupportPointPojo> supportPointPojoList;
	
	private int total;
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void addSupportPointPojo(SupportPointPojo supportPointPojo) {
		if (supportPointPojoList == null) {
			supportPointPojoList = new ArrayList<SupportPointPojo>();
		}
		
		supportPointPojoList.add(supportPointPojo);
	}
	
	public List<SupportPointPojo> getSupportPointPojoList() {
		return supportPointPojoList;
	}
	public void setSupportPointPojoList(List<SupportPointPojo> supportPointPojoList) {
		this.supportPointPojoList = supportPointPojoList;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSportName() {
		return sportName;
	}
	public void setSportName(String sportName) {
		this.sportName = sportName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPurpose() {
		return purpose;
	}
	public Integer getMaintMode() {
		return maintMode;
	}
	public void setMaintMode(Integer maintMode) {
		this.maintMode = maintMode;
	}
	public Integer getPropertyComp() {
		return propertyComp;
	}
	public void setPropertyComp(Integer propertyComp) {
		this.propertyComp = propertyComp;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getMaintArea() {
		return maintArea;
	}
	public void setMaintArea(String maintArea) {
		this.maintArea = maintArea;
	}
	public String getMaintDept() {
		return maintDept;
	}
	public void setMaintDept(String maintDept) {
		this.maintDept = maintDept;
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
	public Integer getPropertyNature() {
		return propertyNature;
	}
	public void setPropertyNature(Integer propertyNature) {
		this.propertyNature = propertyNature;
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
