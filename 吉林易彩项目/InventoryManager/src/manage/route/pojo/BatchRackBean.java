package manage.route.pojo;

import java.io.Serializable;


/**
 * 纤芯批量落架
 * @author chenqp
 *
 */
public class BatchRackBean implements Serializable{
	private String cableId;//光缆段id
	private String fiberIds;//纤芯的id序列
	private String rackSide;//承端 A 端  0；  Z端 1
	private String generatorId;//机房id
	private String rackType;//机架类型 0光接头盒   1光交箱   2ODF  3光分纤箱  4光终端盒  5综合机架'
	private String rackId;//机架id //光交箱id， //光接头盒id
	private String moduleId;//模块id
	private String pointIds;//落架的端子序列
	
	private String startFiber;//起始纤芯
	private String endFiber;//终止纤芯
	
	
	public String getRackType() {
		return rackType;
	}
	public void setRackType(String rackType) {
		this.rackType = rackType;
	}
	public String getCableId() {
		return cableId;
	}
	public void setCableId(String cableId) {
		this.cableId = cableId;
	}
	public String getFiberIds() {
		return fiberIds;
	}
	public void setFiberIds(String fiberIds) {
		this.fiberIds = fiberIds;
	}
	public String getRackSide() {
		return rackSide;
	}
	public String getStartFiber() {
		return startFiber;
	}
	public void setStartFiber(String startFiber) {
		this.startFiber = startFiber;
	}
	public String getEndFiber() {
		return endFiber;
	}
	public void setEndFiber(String endFiber) {
		this.endFiber = endFiber;
	}
	public void setRackSide(String rackSide) {
		this.rackSide = rackSide;
	}
	public String getGeneratorId() {
		return generatorId;
	}
	public void setGeneratorId(String generatorId) {
		this.generatorId = generatorId;
	}
	public String getRackId() {
		return rackId;
	}
	public void setRackId(String rackId) {
		this.rackId = rackId;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getPointIds() {
		return pointIds;
	}
	public void setPointIds(String pointIds) {
		this.pointIds = pointIds;
	}
	
}
