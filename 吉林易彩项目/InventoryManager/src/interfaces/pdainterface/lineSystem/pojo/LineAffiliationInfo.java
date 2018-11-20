package interfaces.pdainterface.lineSystem.pojo;

import java.io.Serializable;

/**
 * 归属
 * @author chenqp
 *
 */
public class LineAffiliationInfo implements Serializable{

	private Integer sysId;//管线系统id
	private Integer segId;//段的唯一标识
	private Integer pointId;//点的唯一标识
	private String type;//操作的类型    0 电杆， 1 标石，2 管井
	public Integer getSysId() {
		return sysId;
	}
	public void setSysId(Integer sysId) {
		this.sysId = sysId;
	}
	public Integer getSegId() {
		return segId;
	}
	public void setSegId(Integer segId) {
		this.segId = segId;
	}
	public Integer getPointId() {
		return pointId;
	}
	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
