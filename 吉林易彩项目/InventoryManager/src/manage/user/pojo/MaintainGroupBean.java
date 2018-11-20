package manage.user.pojo;

import java.io.Serializable;

/**
 * 代维班组管理实体类
 * @author chenqp
 *
 */
public class MaintainGroupBean implements Serializable{

	private Integer id;//班組的id
	private String groupName;//班組名稱
	private String compRes;//公司综资ID
	private String compName;//公司名称
	private String domainId;//区域id
	private String domainName;//区域名称
	
	private Integer start;
	private Integer limit;
	
	
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getCompRes() {
		return compRes;
	}
	public void setCompRes(String compRes) {
		this.compRes = compRes;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
