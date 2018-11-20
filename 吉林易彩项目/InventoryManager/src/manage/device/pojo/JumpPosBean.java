package manage.device.pojo;

import java.io.Serializable;

/**
 * 跳纤分光器
 * @author chenqp
 *
 */
public class JumpPosBean implements Serializable{
	private Integer roomId;//机房id
	private String roomName;//机房名称
	private Integer oltId;//olt设备id
	private String oltName;//olt设备名称
	private Integer ponId;//pon口id
	private String ponName;//pon口名称
	private Integer posId;//分光器id
	private String posName ;//分光器名称
	public Integer getRoomId() {
		return roomId;
	}
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
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
	public Integer getPosId() {
		return posId;
	}
	public void setPosId(Integer posId) {
		this.posId = posId;
	}
	public String getPosName() {
		return posName;
	}
	public void setPosName(String posName) {
		this.posName = posName;
	}
	
}
