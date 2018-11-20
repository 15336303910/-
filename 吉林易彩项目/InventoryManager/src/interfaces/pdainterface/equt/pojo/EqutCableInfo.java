package interfaces.pdainterface.equt.pojo;

import java.io.Serializable;

public class EqutCableInfo implements Serializable{
	//手机端传入信息
	private String sId;//站点id
	private String gId;//机房id
	private String eId;//光交箱，光终端盒，机架的eid  字段
	private Integer etype;// 0机架，1站点，2光终端盒，3光交箱，

	//返回信息
	private String cableName;//光缆段名称
	private Integer cableId;//光缆段id
	private Integer fiberNum;//剩余纤芯
	private String cableItem;//光缆端  start  end 
	
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	public String getCableItem() {
		return cableItem;
	}
	public void setCableItem(String cableItem) {
		this.cableItem = cableItem;
	}
	public String geteId() {
		return eId;
	}
	public void seteId(String eId) {
		this.eId = eId;
	}
	public String getCableName() {
		return cableName;
	}
	public void setCableName(String cableName) {
		this.cableName = cableName;
	}
	public Integer getEtype() {
		return etype;
	}
	public void setEtype(Integer etype) {
		this.etype = etype;
	}
	public Integer getCableId() {
		return cableId;
	}
	public void setCableId(Integer cableId) {
		this.cableId = cableId;
	}
	public Integer getFiberNum() {
		return fiberNum;
	}
	public void setFiberNum(Integer fiberNum) {
		this.fiberNum = fiberNum;
	}
	public String getgId() {
		return gId;
	}
	public void setgId(String gId) {
		this.gId = gId;
	}
}

