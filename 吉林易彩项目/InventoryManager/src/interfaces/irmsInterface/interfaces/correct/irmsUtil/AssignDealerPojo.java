package interfaces.irmsInterface.interfaces.correct.irmsUtil;

import java.io.Serializable;

public class AssignDealerPojo implements Serializable{
	
	private String objectType;//参数company  公司   dept  部门   person  人员
	private String parentId;//上级目录id
	
	private String id;//返回参数
	private String text;//内容
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
