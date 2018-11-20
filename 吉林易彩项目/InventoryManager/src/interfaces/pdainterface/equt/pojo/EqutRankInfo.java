package interfaces.pdainterface.equt.pojo;

import java.io.Serializable;

public class EqutRankInfo implements Serializable{
	private Integer cableId;//光缆段ID
	private Integer fiberNum;//纤芯数
	private Integer fiberId;//纤芯id
	private String eid;//机架id或光交的id
	private Integer rankType;//落架类型  0 接头盒  2 odm  5 综合机架   
							 //4 光终端盒 3 光分纤盒
							//0光接头盒  1 光交箱
	private Integer pointId;//端子id
	private Integer odmId;//模块id
	private Integer  gid;//机房id
	private String cableItem;//光缆端  start  end
	
	public Integer getFiberId() {
		return fiberId;
	}
	public void setFiberId(Integer fiberId) {
		this.fiberId = fiberId;
	}
	public Integer getPointId() {
		return pointId;
	}
	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}
	public Integer getCableId() {
		return cableId;
	}
	public void setCableId(Integer cableId) {
		this.cableId = cableId;
	}
	public Integer getRankType() {
		return rankType;
	}
	public void setRankType(Integer rankType) {
		this.rankType = rankType;
	}
	public Integer getFiberNum() {
		return fiberNum;
	}
	public void setFiberNum(Integer fiberNum) {
		this.fiberNum = fiberNum;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public Integer getOdmId() {
		return odmId;
	}
	public void setOdmId(Integer odmId) {
		this.odmId = odmId;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getCableItem() {
		return cableItem;
	}
	public void setCableItem(String cableItem) {
		this.cableItem = cableItem;
	}
	
}
