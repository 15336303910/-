package manage.V2.hebei.mainTask.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class PointlikeResourceInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4664831111269038699L;
	
	private Integer ID;
	private Integer routeID;// 所属路由ID
	
	private Integer resourceID;// 资源ID
	private String resourceType;// 资源类型
	private String resourceName;// 资源名称
	private Double latitude;// 资源纬度
	private Double longitude;// 资源经度
	private ArrayList<PhotoInfoBean> files;// 资源点照片
	
	private Date createTime;
	private Integer type;//资源点类型(0:起点,1:终点)
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Integer getRouteID() {
		return routeID;
	}
	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}
	public Integer getResourceID() {
		return resourceID;
	}
	public void setResourceID(Integer resourceID) {
		this.resourceID = resourceID;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public ArrayList<PhotoInfoBean> getFiles() {
		return files;
	}
	public void setFiles(ArrayList<PhotoInfoBean> files) {
		this.files = files;
	}

}
