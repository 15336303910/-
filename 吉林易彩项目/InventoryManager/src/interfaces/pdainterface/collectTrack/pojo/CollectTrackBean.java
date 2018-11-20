package interfaces.pdainterface.collectTrack.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 轨迹实体类
 * @author chenqp
 *
 */
public class CollectTrackBean implements Serializable{

	private Integer id;//主键
	private Date trackTime;//记录轨迹时间
	private String longitude;//经度
	private String latitude;//纬度
	private String trackName;//保存账号
	private String trackAddress;//记录上传的地方
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getTrackTime() {
		return trackTime;
	}
	public void setTrackTime(Date trackTime) {
		this.trackTime = trackTime;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	public String getTrackAddress() {
		return trackAddress;
	}
	public void setTrackAddress(String trackAddress) {
		this.trackAddress = trackAddress;
	}
	
	
}
