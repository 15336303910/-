package interfaces.pdainterface.common.pojo;

import java.io.Serializable;


/**
 * 勘误工单资源列表
 * @author chenqp
 *
 */
public class CorrectResBean implements Serializable{
	private Integer id;//资源信息主键
	private String taskId;//工单主键
	private String resNum;//资源的综资标识
	private String resName;//资源名称
	private String resId;//易采的资源唯一标识
	private String resType;//资源的类型
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
	public String getResNum() {
		return resNum;
	}
	public void setResNum(String resNum) {
		this.resNum = resNum;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	
}
