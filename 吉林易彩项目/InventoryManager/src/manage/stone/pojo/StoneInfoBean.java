package manage.stone.pojo;

import java.io.Serializable;

import manage.buriedPart.pojo.BuriedPartObj;

/**
 * 标石管理
 * @author Administrator
 *
 */
public class StoneInfoBean implements Serializable,Cloneable{
	private Integer stoneId;//标石id
	private String stoneName;//标石名称
	private String stoneSubName;//标石子名称
	private String buriedId;//直埋id
	private Integer stonePosition;//标石方位
	private String stonePositionStr;//翻译后标石方位
	private String stoneNum;//标石序号
	private String stoneArea;//所属区域
	private String longitude;//经度
	private String latitude;//纬度
	private Integer propertyNature;//产权性质
	private String propertyNatureStr;
	private Integer propertyComp;//产权单位
	private String propertyCompStr;
	private String dataQualitier;//数据质量责任人
	private String maintainer;//一线维护人
	private String deleteflag;//删除标识
	private String createTime;//创建时间 
	private String creater;//创建人
	private String remark;//备注 
	private String lastUpTime;//最近修改时间
	private String lastUpMan;//最近修改人
	private Integer previousStoneID;// 上一节点ID
	private String previousStoneName;// 上一节点名称
	private Integer chengzaidian_type;//承载点类型
	private String resNum;//资源标识
	private String jointName;  //接头盒名称
	private String extendsSql;//扩展语句
	private String lats;
	private String lons;
	private String late;
	private String lone;
	private Integer start;
	private Integer limit;
	private String maintenanceOrg;//维护单位
	private String maintenanceAreaId;//维护区域
	private String maintenanceLeader;//维护区域组长
	private String leaderPhone;//维护区域组长电话
	
	
	public String getLeaderPhone() {
		return leaderPhone;
	}

	public void setLeaderPhone(String leaderPhone) {
		this.leaderPhone = leaderPhone;
	}

	public String getMaintenanceLeader() {
		return maintenanceLeader;
	}

	public void setMaintenanceLeader(String maintenanceLeader) {
		this.maintenanceLeader = maintenanceLeader;
	}

	public String getMaintenanceOrg() {
		return maintenanceOrg;
	}

	public void setMaintenanceOrg(String maintenanceOrg) {
		this.maintenanceOrg = maintenanceOrg;
	}
	public String getMaintenanceAreaId() {
		return maintenanceAreaId;
	}

	public void setMaintenanceAreaId(String maintenanceAreaId) {
		this.maintenanceAreaId = maintenanceAreaId;
	}
	public Object clone(){
		Object o = null;
		try{
			o = (StoneInfoBean)super.clone();
		}catch(Exception e){
			e.printStackTrace();
		}
		return o;
		
	}
	
	public String getExtendsSql() {
		return extendsSql;
	}

	public void setExtendsSql(String extendsSql) {
		this.extendsSql = extendsSql;
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
	public String getJointName() {
		return jointName;
	}
	public void setJointName(String jointName) {
		this.jointName = jointName;
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
	public String getBuriedId() {
		return buriedId;
	}
	public void setBuriedId(String buriedId) {
		this.buriedId = buriedId;
	}
	public String getStoneSubName() {
		return stoneSubName;
	}
	public void setStoneSubName(String stoneSubName) {
		this.stoneSubName = stoneSubName;
	}
	public String getStonePositionStr() {
		return stonePositionStr;
	}
	public void setStonePositionStr(String stonePositionStr) {
		this.stonePositionStr = stonePositionStr;
	}
	public String getPropertyNatureStr() {
		return propertyNatureStr;
	}
	public void setPropertyNatureStr(String propertyNatureStr) {
		this.propertyNatureStr = propertyNatureStr;
	}
	public String getPropertyCompStr() {
		return propertyCompStr;
	}
	public void setPropertyCompStr(String propertyCompStr) {
		this.propertyCompStr = propertyCompStr;
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
	public Integer getStoneId() {
		return stoneId;
	}
	public void setStoneId(Integer stoneId) {
		this.stoneId = stoneId;
	}
	public String getStoneName() {
		return stoneName;
	}
	public void setStoneName(String stoneName) {
		this.stoneName = stoneName;
	}
	public Integer getStonePosition() {
		return stonePosition;
	}
	public void setStonePosition(Integer stonePosition) {
		this.stonePosition = stonePosition;
	}
	public String getStoneNum() {
		return stoneNum;
	}
	public void setStoneNum(String stoneNum) {
		this.stoneNum = stoneNum;
	}
	public String getStoneArea() {
		return stoneArea;
	}
	public void setStoneArea(String stoneArea) {
		this.stoneArea = stoneArea;
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
	public Integer getChengzaidian_type() {
		return chengzaidian_type;
	}
	public void setChengzaidian_type(Integer chengzaidian_type) {
		this.chengzaidian_type = chengzaidian_type;
	}
	public Integer getPreviousStoneID() {
		return previousStoneID;
	}
	public void setPreviousStoneID(Integer previousStoneID) {
		this.previousStoneID = previousStoneID;
	}
	public String getPreviousStoneName() {
		return previousStoneName;
	}
	public void setPreviousStoneName(String previousStoneName) {
		this.previousStoneName = previousStoneName;
	}
	
}
