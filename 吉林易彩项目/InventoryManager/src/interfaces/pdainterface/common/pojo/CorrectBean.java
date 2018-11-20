package interfaces.pdainterface.common.pojo;

import java.io.Serializable;

/**
 * 派发勘误工单
 * @author chenqp
 *
 */
public class CorrectBean implements Serializable{

	private String id;//资源主键id
	private String name;//资源名称
	private String type;//资源类型
	
	
	private String FlowTitle;//派发的标题
	private String sendCompany;//派发地市
	private String sendCompanyId;//派发地市id
	private String sendMan;//派发人名
	private String sendManId;//派发人id
	private String sendTime;//派发时间
	private String requireTime;//相应时间
	private String CorrigendumProf;//资源类型
	private String CorrigendumEnTable;//模型类型
	private String resId;//资源id
	private String currentState;//工单状态
	private String dealer;//工单处理人
	private String flowTask;//工单类型
	private String flowNo;//工单编号
	private String remark;//描述信息
	
	private Integer start;//起始
	private Integer limit;//条数
	
	/**
	 * 
	 * <sendXml>
		<taskList>
		<TaskInfo>
		<FlowTitle>测试1231231</FlowTitle>
		<sendCompany>长沙分公司</sendCompany>
		<sendCompanyId>478</sendCompanyId>
		<sendMan>cs_zifei</sendMan>
		<sendManId>9722</sendManId>
		<sendTime>2016-10-28</sendTime>
		<requireTime>2016-10-30</requireTime>
		<CorrigendumProf>传输</CorrigendumProf>
		<CorrigendumEnTable>POLE</CorrigendumEnTable>
		<CorrigendumColumns><CorrigendumColumn>
		<CorrigendumColumn>int_id</CorrigendumColumn>
		<CorrColumnEnName>int_id</CorrColumnEnName>
		<CorrColumnEXValue>-712758102</CorrColumnEXValue>
		</CorrigendumColumn>
		</CorrigendumColumns>
		</TaskInfo>
		</taskList>
		</sendXml>
	 */
	
	public String getId() {
		return id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getCurrentState() {
		return currentState;
	}
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	public String getFlowTask() {
		return flowTask;
	}
	public void setFlowTask(String flowTask) {
		this.flowTask = flowTask;
	}
	public String getFlowNo() {
		return flowNo;
	}
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFlowTitle() {
		return FlowTitle;
	}
	public void setFlowTitle(String flowTitle) {
		FlowTitle = flowTitle;
	}
	public String getSendCompany() {
		return sendCompany;
	}
	public void setSendCompany(String sendCompany) {
		this.sendCompany = sendCompany;
	}
	public String getSendCompanyId() {
		return sendCompanyId;
	}
	public void setSendCompanyId(String sendCompanyId) {
		this.sendCompanyId = sendCompanyId;
	}
	public String getSendMan() {
		return sendMan;
	}
	public void setSendMan(String sendMan) {
		this.sendMan = sendMan;
	}
	public String getSendManId() {
		return sendManId;
	}
	public void setSendManId(String sendManId) {
		this.sendManId = sendManId;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getRequireTime() {
		return requireTime;
	}
	public void setRequireTime(String requireTime) {
		this.requireTime = requireTime;
	}
	public String getCorrigendumProf() {
		return CorrigendumProf;
	}
	public void setCorrigendumProf(String corrigendumProf) {
		CorrigendumProf = corrigendumProf;
	}
	public String getCorrigendumEnTable() {
		return CorrigendumEnTable;
	}
	public void setCorrigendumEnTable(String corrigendumEnTable) {
		CorrigendumEnTable = corrigendumEnTable;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
}
