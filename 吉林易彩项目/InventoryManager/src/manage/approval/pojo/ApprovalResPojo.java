package manage.approval.pojo;

import java.io.Serializable;

public class ApprovalResPojo implements Serializable,Cloneable{
	
	private Integer id;
	private String taskId;//工单id
	private String resType;//资源类型
	private String resId;//资源id
	private String resState;//资源状态
	private String resNum;//资源的综资标识
	private String lon;
	private String lat;
	private String generId;//机房ID
	private String generName;//机房名称
	private String generNum;//机房综资标识
	
	public String getGenerId() {
		return generId;
	}

	public void setGenerId(String generId) {
		this.generId = generId;
	}

	public String getGenerName() {
		return generName;
	}

	public void setGenerName(String generName) {
		this.generName = generName;
	}

	public String getGenerNum() {
		return generNum;
	}

	public void setGenerNum(String generNum) {
		this.generNum = generNum;
	}

	/**
	 * 克隆对象
	 */
	public Object clone(){
		Object o = null;
		try{
			o = (ApprovalResPojo)super.clone();
		}catch(Exception e){
			e.printStackTrace();
		}
		return o;
	}

	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getResNum() {
		return resNum;
	}
	public void setResNum(String resNum) {
		this.resNum = resNum;
	}
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getResState() {
		return resState;
	}

	public void setResState(String resState) {
		this.resState = resState;
	}
}
