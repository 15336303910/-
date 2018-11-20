package manage.version.pojo;

public class VersionControl {
	private int id;
	private double version_num;
	private String update_time;
	private String version_name;
	private String version_path;
	private String version_comment;
	private String version_must;
	
	public String getVersion_must() {
		return version_must;
	}
	public void setVersion_must(String version_must) {
		this.version_must = version_must;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getVersion_num() {
		return version_num;
	}
	public void setVersion_num(double version_num) {
		this.version_num = version_num;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getVersion_name() {
		return version_name;
	}
	public void setVersion_name(String version_name) {
		this.version_name = version_name;
	}
	public String getVersion_path() {
		return version_path;
	}
	public void setVersion_path(String version_path) {
		this.version_path = version_path;
	}
	public String getVersion_comment() {
		return version_comment;
	}
	public void setVersion_comment(String version_comment) {
		this.version_comment = version_comment;
	}
	
}
