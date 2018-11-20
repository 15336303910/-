package manage.gd.gdManage.pojo;

import java.util.List;

public class GdTaskMain{
	
	private Integer id;
	private String gdCode;
	private String resourceType;
	private String resourceCode;
	private String resourceName;
	private String taskSubject;
	private String taskDescribe;
	private String taskState;
	private String receiveRegionCode;
	private String receiveRegionName;
	private Integer currentUserCode;
	private String currentUserName;
	private Integer taskYear;
	private Integer taskMonth;
	private Integer taskDate;
	private Integer taskOrder;
	private String finishDate;
	private String createDate;
	private Integer createUserCode;
	private String createUserName;
	private String auditContents;
	
	
	private Integer total;
	private List<GdTaskMain> items;
	private Integer start;
	private Integer limit;
	private Integer resultNumber;
	private Boolean success;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAuditContents() {
		return auditContents;
	}
	public void setAuditContents(String auditContents) {
		this.auditContents = auditContents;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<GdTaskMain> getItems() {
		return items;
	}
	public void setItems(List<GdTaskMain> items) {
		this.items = items;
	}
	public String getGdCode() {
		return gdCode;
	}
	public void setGdCode(String gdCode) {
		this.gdCode = gdCode;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getResourceCode() {
		return resourceCode;
	}
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getTaskSubject() {
		return taskSubject;
	}
	public void setTaskSubject(String taskSubject) {
		this.taskSubject = taskSubject;
	}
	public String getTaskDescribe() {
		return taskDescribe;
	}
	public void setTaskDescribe(String taskDescribe) {
		this.taskDescribe = taskDescribe;
	}
	public String getTaskState() {
		return taskState;
	}
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	public String getReceiveRegionCode() {
		return receiveRegionCode;
	}
	public void setReceiveRegionCode(String receiveRegionCode) {
		this.receiveRegionCode = receiveRegionCode;
	}
	public String getReceiveRegionName() {
		return receiveRegionName;
	}
	public void setReceiveRegionName(String receiveRegionName) {
		this.receiveRegionName = receiveRegionName;
	}
	public Integer getCurrentUserCode() {
		return currentUserCode;
	}
	public void setCurrentUserCode(Integer currentUserCode) {
		this.currentUserCode = currentUserCode;
	}
	public String getCurrentUserName() {
		return currentUserName;
	}
	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}
	public Integer getTaskYear() {
		return taskYear;
	}
	public void setTaskYear(Integer taskYear) {
		this.taskYear = taskYear;
	}
	public Integer getTaskMonth() {
		return taskMonth;
	}
	public void setTaskMonth(Integer taskMonth) {
		this.taskMonth = taskMonth;
	}
	public Integer getTaskDate() {
		return taskDate;
	}
	public void setTaskDate(Integer taskDate) {
		this.taskDate = taskDate;
	}
	public Integer getTaskOrder() {
		return taskOrder;
	}
	public void setTaskOrder(Integer taskOrder) {
		this.taskOrder = taskOrder;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Integer getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(Integer createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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
	public Integer getResultNumber() {
		return resultNumber;
	}
	public void setResultNumber(Integer resultNumber) {
		this.resultNumber = resultNumber;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
}
