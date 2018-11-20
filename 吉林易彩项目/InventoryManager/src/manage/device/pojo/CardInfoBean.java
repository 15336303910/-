package manage.device.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CardInfoBean implements Serializable{

	@SerializedName("id")
	private Integer id;
	
	private String version;//板卡版本
	private String model;//板卡型号
	@SerializedName("cardType")
	private String cardType;//板卡类型
	
	@SerializedName("cardName")
	private String cardName;//板卡名称
	@SerializedName("deviceId")
	private Integer deviceId;//网元id
	private String eID;//机架id
	private Date creationDate;//创建时间
	private Date lastUpdateDate;//最近修改时间
	@SerializedName("deletedFlag")
	private Integer deletedFlag;//删除标识
	private String dataQualityPrincipal;//数据质量维护人
	@SerializedName("resNum")
	private String resNum;//资源id
	private String resNe;//所属网元
	@SerializedName("points")
	private List<PointBean> points;//板卡下所有的端子
	private Integer start;
	private Integer limit;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public String geteID() {
		return eID;
	}
	public void seteID(String eID) {
		this.eID = eID;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public Integer getDeletedFlag() {
		return deletedFlag;
	}
	public void setDeletedFlag(Integer deletedFlag) {
		this.deletedFlag = deletedFlag;
	}
	public String getDataQualityPrincipal() {
		return dataQualityPrincipal;
	}
	public void setDataQualityPrincipal(String dataQualityPrincipal) {
		this.dataQualityPrincipal = dataQualityPrincipal;
	}
	public String getResNum() {
		return resNum;
	}
	public void setResNum(String resNum) {
		this.resNum = resNum;
	}
	public String getResNe() {
		return resNe;
	}
	public void setResNe(String resNe) {
		this.resNe = resNe;
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
	public List<PointBean> getPoints() {
		return points;
	}
	public void setPoints(List<PointBean> points) {
		this.points = points;
	}
}
