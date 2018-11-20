package manage.V2.beijing.resource.pojo;
import java.io.Serializable;

/**
 * @author Dell-
 *分光器实体类
 */
public class ZSLPOSInfoBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1114448070764541615L;
	private Integer id;
	private String posName;
	private Integer belongType;// 归属点类型
	private String belongName;// 归属点名称
	private Integer factory;// 设备厂家
	private String factoryName;//设备厂家
	private Integer fenGuangBi;// 分光比
	private Integer labelState;// 标签情况
	private String installPosition;// 安装位置
	private Integer oltId;//OLTid
	private String oltName;//OLT的名称
	private Integer ponId;//pon口id 
	private String ponName;//pon口名称
	private String maintainArea;//维护区域
	private String dataQualityPrincipal;// 数据质量责任人
	private String parties;// 一线数据维护人
	private String createTime;
	private String updateTime;
	private String remark;
	private String creater;
	private int start;
	private int limit;

	public int getStart() {
		return start;
	}

	public Integer getOltId() {
		return oltId;
	}

	public void setOltId(Integer oltId) {
		this.oltId = oltId;
	}

	public String getOltName() {
		return oltName;
	}

	public void setOltName(String oltName) {
		this.oltName = oltName;
	}

	public Integer getPonId() {
		return ponId;
	}

	public void setPonId(Integer ponId) {
		this.ponId = ponId;
	}

	public String getPonName() {
		return ponName;
	}

	public void setPonName(String ponName) {
		this.ponName = ponName;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	private Integer deleteFlag = 0;// 删除标识,1代表删除

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPosName()
	{
		return posName;
	}

	public String getMaintainArea() {
		return maintainArea;
	}

	public void setMaintainArea(String maintainArea) {
		this.maintainArea = maintainArea;
	}

	public void setPosName(String posName)
	{
		this.posName = posName;
	}

	public Integer getBelongType()
	{
		return belongType;
	}

	public void setBelongType(Integer belongType)
	{
		this.belongType = belongType;
	}

	public String getBelongName()
	{
		return belongName;
	}

	public void setBelongName(String belongName)
	{
		this.belongName = belongName;
	}

	public Integer getFactory()
	{
		return factory;
	}

	public void setFactory(Integer factory)
	{
		this.factory = factory;
	}

	public Integer getFenGuangBi()
	{
		return fenGuangBi;
	}

	public void setFenGuangBi(Integer fenGuangBi)
	{
		this.fenGuangBi = fenGuangBi;
	}

	public Integer getLabelState()
	{
		return labelState;
	}

	public void setLabelState(Integer labelState)
	{
		this.labelState = labelState;
	}

	public String getInstallPosition()
	{
		return installPosition;
	}

	public void setInstallPosition(String installPosition)
	{
		this.installPosition = installPosition;
	}

	public String getDataQualityPrincipal()
	{
		return dataQualityPrincipal;
	}

	public void setDataQualityPrincipal(String dataQualityPrincipal)
	{
		this.dataQualityPrincipal = dataQualityPrincipal;
	}

	public String getParties()
	{
		return parties;
	}

	public void setParties(String parties)
	{
		this.parties = parties;
	}

	public Integer getDeleteFlag()
	{
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag)
	{
		this.deleteFlag = deleteFlag;
	}
	
	
}
