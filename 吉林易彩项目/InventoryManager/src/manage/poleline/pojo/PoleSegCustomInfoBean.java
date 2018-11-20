package manage.poleline.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PoleSegCustomInfoBean implements Serializable,Cloneable
{
  private Integer city_id;
  private String zh_label;//杆路段名称
  private Date time_stamp;
  private Integer stateflag;
  private Integer int_id ;
  private String creator;
  private Date creat_time;
  private String  modifier;
  private Date modify_time;
  private Integer  county_id ;
  private String start_pole_id;
  private String end_pole_id;
  private String ownership;
  private String purpose;
  private String line_length ;
  private String maintain_company;
  private String maintain_area;
  private String maintain_manager;
  private String maintain_manager_phone ;
  private String remark;
  private String extendsSql;//扩展语句
  private Integer start;
  private Integer limit;
  private String start_polename;
  private String end_polename;
  private List<PoleSegCustomInfoBean> polelinesegments;
   


  
	

	public String getStart_polename() {
	return start_polename;
}
public void setStart_polename(String start_polename) {
	this.start_polename = start_polename;
}
public String getEnd_polename() {
	return end_polename;
}
public void setEnd_polename(String end_polename) {
	this.end_polename = end_polename;
}
	public Integer getCity_id() {
		return city_id;
	}
	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}
	public String getZh_label() {
		return zh_label;
	}
	public void setZh_label(String zh_label) {
		this.zh_label = zh_label;
	}
	public Date getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(Date time_stamp) {
		this.time_stamp = time_stamp;
	}
	public Integer getStateflag() {
		return stateflag;
	}
	public void setStateflag(Integer stateflag) {
		this.stateflag = stateflag;
	}
	public Integer getInt_id() {
		return int_id;
	}
	public void setInt_id(Integer int_id) {
		this.int_id = int_id;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreat_time() {
		return creat_time;
	}
	public void setCreat_time(Date creat_time) {
		this.creat_time = creat_time;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Date getModify_time() {
		return modify_time;
	}
	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}
	public Integer getCounty_id() {
		return county_id;
	}
	public void setCounty_id(Integer county_id) {
		this.county_id = county_id;
	}
	public String getStart_pole_id() {
		return start_pole_id;
	}
	public void setStart_pole_id(String start_pole_id) {
		this.start_pole_id = start_pole_id;
	}
	public String getEnd_pole_id() {
		return end_pole_id;
	}
	public void setEnd_pole_id(String end_pole_id) {
		this.end_pole_id = end_pole_id;
	}
	public String getOwnership() {
		return ownership;
	}
	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getLine_length() {
		return line_length;
	}
	public void setLine_length(String line_length) {
		this.line_length = line_length;
	}
	public String getMaintain_company() {
		return maintain_company;
	}
	public void setMaintain_company(String maintain_company) {
		this.maintain_company = maintain_company;
	}
	public String getMaintain_area() {
		return maintain_area;
	}
	public void setMaintain_area(String maintain_area) {
		this.maintain_area = maintain_area;
	}
	public String getMaintain_manager() {
		return maintain_manager;
	}
	public void setMaintain_manager(String maintain_manager) {
		this.maintain_manager = maintain_manager;
	}
	public String getMaintain_manager_phone() {
		return maintain_manager_phone;
	}
	public void setMaintain_manager_phone(String maintain_manager_phone) {
		this.maintain_manager_phone = maintain_manager_phone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getExtendsSql() {
		return extendsSql;
	}
	public void setExtendsSql(String extendsSql) {
		this.extendsSql = extendsSql;
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
	public Object clone(){
			Object o = null;
			try{
				o = (PoleSegCustomInfoBean)super.clone();
			}catch(Exception e){
				e.printStackTrace();
			}
			return o;
			
		}
	  public List<PoleSegCustomInfoBean> getPolelinesegments() {
	    return this.polelinesegments; }
	
	  public void setPolelinesegments(List<PoleSegCustomInfoBean> polelinesegments) {
	    this.polelinesegments = polelinesegments; }

  
}