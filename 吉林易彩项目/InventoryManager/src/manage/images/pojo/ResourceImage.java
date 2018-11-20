package manage.images.pojo;

import java.util.List;

public class ResourceImage {
	
	private Integer Id;//图片id
	private String type;//图片类型
	private String resourceId;//所属资源id
	private String ImageName;//图片名称
	private String ImagePath;//图片路径加系统路径可显示图片
	private String resNum;//综资id 
	
	public String getResNum() {
		return resNum;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public void setResNum(String resNum) {
		this.resNum = resNum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getImageName() {
		return ImageName;
	}
	public void setImageName(String imageName) {
		ImageName = imageName;
	}
	public String getImagePath() {
		return ImagePath;
	}
	public void setImagePath(String imagePath) {
		ImagePath = imagePath;
	}
	
}
