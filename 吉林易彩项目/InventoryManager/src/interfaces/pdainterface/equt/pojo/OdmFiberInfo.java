package interfaces.pdainterface.equt.pojo;

import java.io.Serializable;

public class OdmFiberInfo implements Serializable{

	private Integer id;//主键序列
	private Integer odmId;//模块主键
	private String eid;//机架id
	private Integer row;//行号
	private Integer startCol;//起始列
	private Integer endCol;//终止列
	private Integer cableId;//光缆段id
	private String cableName;//光缆段名称
	private Integer startFiber;//开始纤芯
	private Integer endFiber;//结束纤芯
	private Integer deleteFlag;//删除标识
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOdmId() {
		return odmId;
	}
	public void setOdmId(Integer odmId) {
		this.odmId = odmId;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Integer getStartCol() {
		return startCol;
	}
	public void setStartCol(Integer startCol) {
		this.startCol = startCol;
	}
	public Integer getEndCol() {
		return endCol;
	}
	public void setEndCol(Integer endCol) {
		this.endCol = endCol;
	}
	public Integer getCableId() {
		return cableId;
	}
	public void setCableId(Integer cableId) {
		this.cableId = cableId;
	}
	public String getCableName() {
		return cableName;
	}
	public void setCableName(String cableName) {
		this.cableName = cableName;
	}
	public Integer getStartFiber() {
		return startFiber;
	}
	public void setStartFiber(Integer startFiber) {
		this.startFiber = startFiber;
	}
	public Integer getEndFiber() {
		return endFiber;
	}
	public void setEndFiber(Integer endFiber) {
		this.endFiber = endFiber;
	}
}
