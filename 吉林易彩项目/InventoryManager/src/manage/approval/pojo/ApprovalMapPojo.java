package manage.approval.pojo;

import java.io.Serializable;

/**
 * 在地图上画点
 * @author chenqp
 *
 */
public class ApprovalMapPojo implements Serializable,Cloneable{

	private Integer id;//主键自增长
	private String taskId;//工单主键
	private String startLat;//点的起始纬度
	private String startLon;//点的起始经度
	private String endLat;//点的终止纬度
	private String endLon;//点的终止经度
	private String pointNum;//点序号第一个点
	/**
	 * 克隆对象
	 */
	public Object clone(){
		Object o = null;
		try{
			o = (ApprovalMapPojo)super.clone();
		}catch(Exception e){
			e.printStackTrace();
		}
		return o;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getStartLat() {
		return startLat;
	}
	public void setStartLat(String startLat) {
		this.startLat = startLat;
	}
	public String getStartLon() {
		return startLon;
	}
	public void setStartLon(String startLon) {
		this.startLon = startLon;
	}
	public String getEndLat() {
		return endLat;
	}
	public void setEndLat(String endLat) {
		this.endLat = endLat;
	}
	public String getEndLon() {
		return endLon;
	}
	public void setEndLon(String endLon) {
		this.endLon = endLon;
	}
	public String getPointNum() {
		return pointNum;
	}
	public void setPointNum(String pointNum) {
		this.pointNum = pointNum;
	}
}
