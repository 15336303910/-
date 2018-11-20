package manage.equt.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import manage.user.pojo.UserCkBean;

public class EqutInfoBean
{
  private Integer id;//主键
  @SerializedName("EID")
  private String eid;//资源编号

  @SerializedName("ESTAT")
  private String estat;//状态

  @SerializedName("ECODE")
  private String ecode;//序号

  @SerializedName("ENAME")
  private String ename;//模块名称

  @SerializedName("EADDR")
  private String eaddr;//光交箱地址

  @SerializedName("ETYPE")
  private String etype;//类型

  @SerializedName("EMODEL")
  private String emodel;//机架正反面

  @SerializedName("LON")
  private String lon;//经度

  @SerializedName("LAT")
  private String lat;//纬度

  @SerializedName("NOTE")
  private String note;

  @SerializedName("MTIME")
  private Date mtime;
  @SerializedName("HASALLOW")
  private Integer hasallow;

  @SerializedName("MFLAG")
  private Integer mflag;
  @SerializedName("AREANO")
  private String areano;
  @SerializedName("GID")
  private String gid;
  @SerializedName("isPartFiber")
  private Integer isPartFiber;//是否分纤点
  @SerializedName("partFiberRank")
  private Integer partFiberRank;//分纤点级别
  private String generatorName;
  private String parenteid;//资源点编号
  private String posX;
  private String posY;
  private String latl;
  private String lath;
  private String lonl;
  private String lonh;
  private Integer total;
  private String dir;
  private String sort;
  private Integer start;
  private Integer limit;
  private Integer del;
  private List<EqutInfoBean> equts;
  @SerializedName("address")
  private String address;
  @SerializedName("dataQualityPrincipal")
  private String dataQualityPrincipal;//数据质量维护人
  @SerializedName("parties")
  private String parties;//一线数据维护人
  @SerializedName("changjia")
  private String changjia;//所属厂家
  @SerializedName("jijialeixing")
  private Integer jijialeixing;//机架类型
  @SerializedName("jijiahanghao")
  private String jijiahanghao;//机架行号
  @SerializedName("jijialiehao")
  private String jijialiehao;//机架列号
  @SerializedName("gjxmianshu")
  private Integer gjxmianshu;//光交箱面数
  @SerializedName("gjxhangshu")
  private Integer gjxhangshu;//光交箱行数
  @SerializedName("gjxlieshu")
  private Integer gjxlieshu;//光交箱列数
  @SerializedName("genPartOptical")
  private String genPartOptical;//承载光缆段中文名
  @SerializedName("genPartOpticalId")
  private String genPartOpticalId;//承载光缆段id
  @SerializedName("resNum")
  private String resNum;
  @SerializedName("freeCapacity")
  private String freeCapacity;//空闲容量
  @SerializedName("installCapacity")
  private String installCapacity;//安装容量
  @SerializedName("designCapacity")
  private String designCapacity;//设计容量
  @SerializedName("usedCapacity")
  private String usedCapacity;//使用容量
  @SerializedName("synthBusiness")
  private String synthBusiness;//综合业务区
  @SerializedName("synthBusinessName")
  private String synthBusinessName;//综合业务区
  @SerializedName("equtLength")
  private Double equtLength;//长
  @SerializedName("equtTall")
  private Double equtTall;//高
  @SerializedName("equtWide")
  private Double equtWide;//宽
  @SerializedName("assetsNo")
  private String assetsNo;//资产编号
  @SerializedName("remark")
  private String remark;//光交备注
  private String extendsSql;//扩展语句
  @SerializedName("sharingTypeEnumId")
  private String sharingTypeEnumId;//产权性质
  @SerializedName("constructionSharingOrg")
  private String constructionSharingOrg;//产权单位
  
  private String resMotion;//保存光交箱状态
  public String getResMotion() {
	return resMotion;
}
public void setResMotion(String resMotion) {
	this.resMotion = resMotion;
}
public String getSharingTypeEnumId() {
	return sharingTypeEnumId;
}
public void setSharingTypeEnumId(String sharingTypeEnumId) {
	this.sharingTypeEnumId = sharingTypeEnumId;
}
public String getConstructionSharingOrg() {
	return constructionSharingOrg;
}
public void setConstructionSharingOrg(String constructionSharingOrg) {
	this.constructionSharingOrg = constructionSharingOrg;
}
public String getResNum() {
	return resNum;
}
public String getParenteid() {
	return parenteid;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
public void setParenteid(String parenteid) {
	this.parenteid = parenteid;
}
public Integer getDel() {
	return del;
}
public String getExtendsSql() {
	return extendsSql;
}
public void setExtendsSql(String extendsSql) {
	this.extendsSql = extendsSql;
}
public void setDel(Integer del) {
	this.del = del;
}
public String getSynthBusinessName() {
	return synthBusinessName;
}
public void setSynthBusinessName(String synthBusinessName) {
	this.synthBusinessName = synthBusinessName;
}
public Integer getIsPartFiber() {
	return isPartFiber;
}
public String getSynthBusiness() {
	return synthBusiness;
}
public Double getEqutLength() {
	return equtLength;
}
public void setEqutLength(Double equtLength) {
	this.equtLength = equtLength;
}
public Double getEqutTall() {
	return equtTall;
}
public void setEqutTall(Double equtTall) {
	this.equtTall = equtTall;
}
public Double getEqutWide() {
	return equtWide;
}
public void setEqutWide(Double equtWide) {
	this.equtWide = equtWide;
}
public String getAssetsNo() {
	return assetsNo;
}
public void setAssetsNo(String assetsNo) {
	this.assetsNo = assetsNo;
}
public void setSynthBusiness(String synthBusiness) {
	this.synthBusiness = synthBusiness;
}
public String getFreeCapacity() {
	return freeCapacity;
}
public void setFreeCapacity(String freeCapacity) {
	this.freeCapacity = freeCapacity;
}
public String getInstallCapacity() {
	return installCapacity;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public void setInstallCapacity(String installCapacity) {
	this.installCapacity = installCapacity;
}
public String getDesignCapacity() {
	return designCapacity;
}
public void setDesignCapacity(String designCapacity) {
	this.designCapacity = designCapacity;
}


public String getUsedCapacity() {
	return usedCapacity;
}


public void setUsedCapacity(String usedCapacity) {
	this.usedCapacity = usedCapacity;
}


public void setIsPartFiber(Integer isPartFiber) {
	this.isPartFiber = isPartFiber;
}


public Integer getPartFiberRank() {
	return partFiberRank;
}


public void setPartFiberRank(Integer partFiberRank) {
	this.partFiberRank = partFiberRank;
}


public void setResNum(String resNum) {
	this.resNum = resNum;
}

public String getGenPartOptical() {
	return genPartOptical;
  }

  public void setGenPartOptical(String genPartOptical) {
	this.genPartOptical = genPartOptical;
  }
public String getGenPartOpticalId() {
	return genPartOpticalId;
}

public void setGenPartOpticalId(String genPartOpticalId) {
	this.genPartOpticalId = genPartOpticalId;
}

public String getDataQualityPrincipal() {
	return dataQualityPrincipal;
}

public String getJijiahanghao() {
	return jijiahanghao;
}

public void setJijiahanghao(String jijiahanghao) {
	this.jijiahanghao = jijiahanghao;
}

public String getJijialiehao() {
	return jijialiehao;
}

public void setJijialiehao(String jijialiehao) {
	this.jijialiehao = jijialiehao;
}

public void setDataQualityPrincipal(String dataQualityPrincipal) {
	this.dataQualityPrincipal = dataQualityPrincipal;
}

public String getParties() {
	return parties;
}

public void setParties(String parties) {
	this.parties = parties;
}

public String getChangjia() {
	return changjia;
}

public void setChangjia(String changjia) {
	this.changjia = changjia;
}

public Integer getJijialeixing() {
	return jijialeixing;
}

public void setJijialeixing(Integer jijialeixing) {
	this.jijialeixing = jijialeixing;
}

public Integer getGjxmianshu() {
	return gjxmianshu;
}

public void setGjxmianshu(Integer gjxmianshu) {
	this.gjxmianshu = gjxmianshu;
}

public Integer getGjxhangshu() {
	return gjxhangshu;
}

public void setGjxhangshu(Integer gjxhangshu) {
	this.gjxhangshu = gjxhangshu;
}

public Integer getGjxlieshu() {
	return gjxlieshu;
}

public void setGjxlieshu(Integer gjxlieshu) {
	this.gjxlieshu = gjxlieshu;
}


  public Integer getId() {
    return this.id; }

  public void setId(Integer id) {
    this.id = id; }

  public String getEid() {
    return this.eid; }

  public void setEid(String eid) {
    this.eid = eid; }

  public String getEstat() {
    return this.estat; }

  public void setEstat(String estat) {
    this.estat = estat; }

  public String getEcode() {
    return this.ecode; }

  public void setEcode(String ecode) {
    this.ecode = ecode; }

  public String getEname() {
    return this.ename; }

  public void setEname(String ename) {
    this.ename = ename; }

  public String getEaddr() {
    return this.eaddr; }

  public void setEaddr(String eaddr) {
    this.eaddr = eaddr; }

  public String getEtype() {
    return this.etype; }

  public void setEtype(String etype) {
    this.etype = etype; }

  public String getEmodel() {
    return this.emodel; }

  public void setEmodel(String emodel) {
    this.emodel = emodel; }

  public String getLon() {
    return this.lon; }

  public void setLon(String lon) {
    this.lon = lon; }

  public String getLat() {
    return this.lat; }

  public void setLat(String lat) {
    this.lat = lat; }

  public String getNote() {
    return this.note; }

  public void setNote(String note) {
    this.note = note; }

  public Date getMtime() {
    return this.mtime; }

  public void setMtime(Date mtime) {
    this.mtime = mtime; }
  public Integer getHasallow() {
    return this.hasallow; }

  public void setHasallow(Integer hasallow) {
    this.hasallow = hasallow; }

  public Integer getMflag() {
    return this.mflag; }

  public void setMflag(Integer mflag) {
    this.mflag = mflag; }

  public String getAreano() {
    return this.areano; }

  public void setAreano(String areano) {
    this.areano = areano; }

  public String getLatl() {
    return this.latl; }

  public void setLatl(String latl) {
    this.latl = latl; }

  public String getLath() {
    return this.lath; }

  public void setLath(String lath) {
    this.lath = lath; }

  public String getLonl() {
    return this.lonl; }

  public void setLonl(String lonl) {
    this.lonl = lonl; }

  public String getLonh() {
    return this.lonh; }

  public void setLonh(String lonh) {
    this.lonh = lonh; }


  public Integer getTotal() {
    return this.total; }

  public void setTotal(Integer total) {
    this.total = total; }

  public String getDir() {
    return this.dir; }

  public void setDir(String dir) {
    this.dir = dir; }

  public String getSort() {
    return this.sort; }

  public void setSort(String sort) {
    this.sort = sort; }

  public Integer getStart() {
    return this.start; }

  public void setStart(Integer start) {
    this.start = start; }

  public Integer getLimit() {
    return this.limit; }

  public void setLimit(Integer limit) {
    this.limit = limit; }

  public List<EqutInfoBean> getEquts() {
    return this.equts; }

  public void setEquts(List<EqutInfoBean> equts) {
    this.equts = equts; }


  public int hashCode() {
    int prime = 31;
    int result = 1;
    return result;
  }

  public boolean equals(Object obj) {
    if (this == obj){
    	return true;
    }
    if (obj == null){
    	return false;
    }
    if (super.getClass() != obj.getClass()){
    	 return false;
    }
    return true;
  }


  public String getGid() {
    return this.gid;
  }

  public void setGid(String gid) {
    this.gid = gid;
  }

  public String getPosX() {
    return this.posX;
  }

  public void setPosX(String posX) {
    this.posX = posX;
  }

  public String getPosY() {
    return this.posY;
  }

  public void setPosY(String posY) {
    this.posY = posY;
  }


  public String getGeneratorName() {
    return this.generatorName;
  }

  public void setGeneratorName(String generatorName) {
    this.generatorName = generatorName;
  }
}
