package manage.point.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PointInfoBean
{
  private Integer id;//主键
  private String plineno;//行线
  private String prowno;//列线
  @SerializedName("PID")
  private String pid;//序号
  @SerializedName("EID")
  private String eid;//机架id
  @SerializedName("PSTAT")
  private String pstat;//端子状态
  @SerializedName("PTYPE")
  private String ptype;//端子类型
  @SerializedName("MFLAG")
  private Integer mflag;
  private String mflagstr;
  @SerializedName("DIRETION")
  private String direction;
  @SerializedName("MTIME")
  private Date mtime;
  //落架光缆段
  @SerializedName("CABLENAME")
  private String cablename;
  @SerializedName("ODMCODE")
  private String odmCode;
  private Integer total;
  private String dir;
  private String sort;
  private Integer start;
  private Integer limit;
  private List<PointInfoBean> points = new ArrayList();
  private Integer cableid;//光缆id
  @SerializedName("ODMID")
  private Integer odmId;
  private Integer isfb;
  private Integer fibno;//纤芯序号
  @SerializedName("FIBERNAME")
  private String fiberName;//纤芯名称
  @SerializedName("POS")
  private String pos;
  private String co;
  private Integer logId;
  private Date logTime;
  private String logOperater;
  private String seriesNo;
  private String resNum;
  private String del;
  private String oppsite;//成端的对端
  private String jumpOptical;//跳纤的对端
  
public String getOppsite() {
	return oppsite;
}
public void setOppsite(String oppsite) {
	this.oppsite = oppsite;
}
public String getJumpOptical() {
	return jumpOptical;
}
public void setJumpOptical(String jumpOptical) {
	this.jumpOptical = jumpOptical;
}
public Integer getId() {
	return id;
}
public String getDel() {
	return del;
}
public void setDel(String del) {
	this.del = del;
}
public void setId(Integer id) {
	this.id = id;
}
public String getPlineno() {
	return plineno;
}
public void setPlineno(String plineno) {
	this.plineno = plineno;
}
public String getProwno() {
	return prowno;
}
public void setProwno(String prowno) {
	this.prowno = prowno;
}
public String getPid() {
	return pid;
}
public void setPid(String pid) {
	this.pid = pid;
}
public String getEid() {
	return eid;
}
public void setEid(String eid) {
	this.eid = eid;
}
public String getPstat() {
	return pstat;
}
public void setPstat(String pstat) {
	this.pstat = pstat;
}
public String getPtype() {
	return ptype;
}
public void setPtype(String ptype) {
	this.ptype = ptype;
}
public Integer getMflag() {
	return mflag;
}
public void setMflag(Integer mflag) {
	this.mflag = mflag;
}
public String getMflagstr() {
	return mflagstr;
}
public void setMflagstr(String mflagstr) {
	this.mflagstr = mflagstr;
}
public String getDirection() {
	return direction;
}
public void setDirection(String direction) {
	this.direction = direction;
}
public Date getMtime() {
	return mtime;
}
public void setMtime(Date mtime) {
	this.mtime = mtime;
}
public String getCablename() {
	return cablename;
}
public void setCablename(String cablename) {
	this.cablename = cablename;
}
public String getOdmCode() {
	return odmCode;
}
public void setOdmCode(String odmCode) {
	this.odmCode = odmCode;
}
public Integer getTotal() {
	return total;
}
public void setTotal(Integer total) {
	this.total = total;
}
public String getDir() {
	return dir;
}
public void setDir(String dir) {
	this.dir = dir;
}
public String getSort() {
	return sort;
}
public void setSort(String sort) {
	this.sort = sort;
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
public List<PointInfoBean> getPoints() {
	return points;
}
public void setPoints(List<PointInfoBean> points) {
	this.points = points;
}
public Integer getCableid() {
	return cableid;
}
public void setCableid(Integer cableid) {
	this.cableid = cableid;
}
public Integer getOdmId() {
	return odmId;
}
public void setOdmId(Integer odmId) {
	this.odmId = odmId;
}
public Integer getIsfb() {
	return isfb;
}
public void setIsfb(Integer isfb) {
	this.isfb = isfb;
}
public Integer getFibno() {
	return fibno;
}
public void setFibno(Integer fibno) {
	this.fibno = fibno;
}
public String getFiberName() {
	return fiberName;
}
public void setFiberName(String fiberName) {
	this.fiberName = fiberName;
}
public String getPos() {
	return pos;
}
public void setPos(String pos) {
	this.pos = pos;
}
public String getCo() {
	return co;
}
public void setCo(String co) {
	this.co = co;
}
public Integer getLogId() {
	return logId;
}
public void setLogId(Integer logId) {
	this.logId = logId;
}
public Date getLogTime() {
	return logTime;
}
public void setLogTime(Date logTime) {
	this.logTime = logTime;
}
public String getLogOperater() {
	return logOperater;
}
public void setLogOperater(String logOperater) {
	this.logOperater = logOperater;
}
public String getSeriesNo() {
	return seriesNo;
}
public void setSeriesNo(String seriesNo) {
	this.seriesNo = seriesNo;
}
public String getResNum() {
	return resNum;
}
public void setResNum(String resNum) {
	this.resNum = resNum;
}

}