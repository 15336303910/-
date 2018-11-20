package manage.generator.pojo;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class GeneratorInfoBean
{
  private Integer generatorId;//机房id
  private String generatorName;//机房名称
  private String region;//所属区县
  private String station;//站点名称
  private String lon;//经度
  private String lat;//纬度
  private String generatorAddr;//机房地址
  private Date creationDate;//创建时间
  private Date lastUpdateDate;//修改时间
  private String deleteFlag;//修改标识
  private List<GeneratorInfoBean> generators;
  private int total;
  private String areano;//站点id
  private String dir;//排序
  private String sort;//分组
  private Integer start;
  private Integer limit;
  private String OperationType;
  private Integer jflx;//机房类型
  private Integer ywjb;//业务级别
  private String szlc;//所在楼层
  private String dataQualityPrincipal;
  private String parties;//一线维护人
  private String genPartOptical;//承载光缆段中文名
  private String genPartOpticalId;//承载光缆段id
  private String resNum;
  private String synthBusiness;//综合业务区
  private String synthBusinessName;//综合业务区名称
  
  private String extendSql;
  public String getExtendSql() {
	return extendSql;
}
public void setExtendSql(String extendSql) {
	this.extendSql = extendSql;
}
public String getSynthBusinessName() {
	return synthBusinessName;
}
public void setSynthBusinessName(String synthBusinessName) {
	this.synthBusinessName = synthBusinessName;
}
public String getResNum() {
	return resNum;
}
public String getSynthBusiness() {
	return synthBusiness;
}
public void setSynthBusiness(String synthBusiness) {
	this.synthBusiness = synthBusiness;
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
  public void setDataQualityPrincipal(String dataQualityPrincipal) {
	this.dataQualityPrincipal = dataQualityPrincipal;
  }
  public String getParties() {
	return parties;
  }
  public void setParties(String parties) {
	this.parties = parties;
  }
  public Integer getJflx() {
	return jflx;
}
public void setJflx(Integer jflx) {
	this.jflx = jflx;
}
public Integer getYwjb() {
	return ywjb;
}
public void setYwjb(Integer ywjb) {
	this.ywjb = ywjb;
}


public String getSzlc() {
	return szlc;
}
public void setSzlc(String szlc) {
	this.szlc = szlc;
}
public String getGeneratorName()
  {
    return this.generatorName; }

  public void setGeneratorName(String generatorName) {
    this.generatorName = generatorName; }

  public String getRegion() {
    return this.region; }

  public void setRegion(String region) {
    this.region = region; }

  public String getStation() {
    return this.station; }

  public void setStation(String station) {
    this.station = station; }

  public String getLon() {
    return this.lon; }

  public void setLon(String lon) {
    this.lon = lon; }

  public String getLat() {
    return this.lat; }

  public void setLat(String lat) {
    this.lat = lat; }

  public String getGeneratorAddr() {
    return this.generatorAddr; }

  public void setGeneratorAddr(String generatorAddr) {
    this.generatorAddr = generatorAddr; }

  public Integer getGeneratorId() {
    return this.generatorId; }

  public void setGeneratorId(Integer generatorId) {
    this.generatorId = generatorId; }

  public Date getCreationDate() {
    return this.creationDate; }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate; }

  public Date getLastUpdateDate() {
    return this.lastUpdateDate; }

  public void setLastUpdateDate(Date lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate; }

  public String getDeleteFlag() {
    return this.deleteFlag; }

  public void setDeletedFlag(String deleteFlag) {
    this.deleteFlag = deleteFlag; }


  public void setDeleteFlag(String deleteFlag) {
    this.deleteFlag = deleteFlag; }

  public List<GeneratorInfoBean> getGenerators() {
    return this.generators; }

  public void setGenerators(List<GeneratorInfoBean> generators) {
    this.generators = generators; }

  public int getTotal() {
    return this.total; }

  public void setTotal(int total) {
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

  public String getAreano() {
    return this.areano; }

  public void setAreano(String areano) {
    this.areano = areano; }


  public String getOperationType() {
    return this.OperationType; }

  public void setOperationType(String operationType) {
    this.OperationType = operationType; }

}