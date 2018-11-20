package manage.approval.pojo;

import java.io.Serializable;

public class ApprovalReportPojo implements Serializable,Cloneable{
	
	private Integer id;//分类ID
	private String belongCmp;//工单所属公司
	private String totalTask;//工单总数
	private String sendTask;//已派工单数
	private String beingTask;//处理中的工单数
	private String checkTask;//待审核的工单数
	private String endTask;//归档的工单数
	private String rejectTask;//驳回的工单数
	private String allTotalLength;//管道杆路总长度
	private String selfTotalLength;//自有管道杆路总长度
	private String rentTotalLength;//租用管道杆路总长度
	private String collectLength;//采集管道管路总长度
	private String allPassLength;//审核通过的管道杆路总长度
	private String selfPassLength;//审核通过自有管道杆路总长度
	private String rentPassLength;//审核通过租用管道杆路总长度
	private String passAllRate;//审核通过采集管道杆路完成率
	private String allEqutNum;//光交箱总数
	private String assertEqutNum;//已交维的光交箱数
	private String buildEqutNum;//未交维光交箱数量
	private String collectEqutNum;//采集光交箱数量
	private String passEqutNum;//审核通过光交箱数据量
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBelongCmp() {
		return belongCmp;
	}
	public void setBelongCmp(String belongCmp) {
		this.belongCmp = belongCmp;
	}
	public String getTotalTask() {
		return totalTask;
	}
	public void setTotalTask(String totalTask) {
		this.totalTask = totalTask;
	}
	public String getSendTask() {
		return sendTask;
	}
	public void setSendTask(String sendTask) {
		this.sendTask = sendTask;
	}
	public String getBeingTask() {
		return beingTask;
	}
	public void setBeingTask(String beingTask) {
		this.beingTask = beingTask;
	}
	public String getCheckTask() {
		return checkTask;
	}
	public void setCheckTask(String checkTask) {
		this.checkTask = checkTask;
	}
	public String getEndTask() {
		return endTask;
	}
	public void setEndTask(String endTask) {
		this.endTask = endTask;
	}
	public String getRejectTask() {
		return rejectTask;
	}
	public void setRejectTask(String rejectTask) {
		this.rejectTask = rejectTask;
	}
	public String getAllTotalLength() {
		return allTotalLength;
	}
	public void setAllTotalLength(String allTotalLength) {
		this.allTotalLength = allTotalLength;
	}
	public String getSelfTotalLength() {
		return selfTotalLength;
	}
	public void setSelfTotalLength(String selfTotalLength) {
		this.selfTotalLength = selfTotalLength;
	}
	public String getRentTotalLength() {
		return rentTotalLength;
	}
	public void setRentTotalLength(String rentTotalLength) {
		this.rentTotalLength = rentTotalLength;
	}
	public String getCollectLength() {
		return collectLength;
	}
	public void setCollectLength(String collectLength) {
		this.collectLength = collectLength;
	}
	public String getAllPassLength() {
		return allPassLength;
	}
	public void setAllPassLength(String allPassLength) {
		this.allPassLength = allPassLength;
	}
	public String getSelfPassLength() {
		return selfPassLength;
	}
	public void setSelfPassLength(String selfPassLength) {
		this.selfPassLength = selfPassLength;
	}
	public String getRentPassLength() {
		return rentPassLength;
	}
	public void setRentPassLength(String rentPassLength) {
		this.rentPassLength = rentPassLength;
	}
	public String getPassAllRate() {
		return passAllRate;
	}
	public void setPassAllRate(String passAllRate) {
		this.passAllRate = passAllRate;
	}
	public String getAllEqutNum() {
		return allEqutNum;
	}
	public void setAllEqutNum(String allEqutNum) {
		this.allEqutNum = allEqutNum;
	}
	public String getAssertEqutNum() {
		return assertEqutNum;
	}
	public void setAssertEqutNum(String assertEqutNum) {
		this.assertEqutNum = assertEqutNum;
	}
	public String getBuildEqutNum() {
		return buildEqutNum;
	}
	public void setBuildEqutNum(String buildEqutNum) {
		this.buildEqutNum = buildEqutNum;
	}
	public String getCollectEqutNum() {
		return collectEqutNum;
	}
	public void setCollectEqutNum(String collectEqutNum) {
		this.collectEqutNum = collectEqutNum;
	}
	public String getPassEqutNum() {
		return passEqutNum;
	}
	public void setPassEqutNum(String passEqutNum) {
		this.passEqutNum = passEqutNum;
	}
	
}
