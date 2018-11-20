package manage.route.pojo;

import base.util.CommonUtil;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.dom4j.Element;

public class RouteInfoBean
  implements Serializable
{
  private static final long serialVersionUID = 8175552928730037032L;

  @SerializedName("Optical_cable_id")
  private Integer routeid;

  @SerializedName("Optical_cable_name")
  private String routename;

  @SerializedName("Optical_cable_level_enum_id")
  private String routelevel;

  @SerializedName("")
  private String areano;
  private String areaname;
  private Integer sectioncount;
  private Integer fibercount;
  private Integer inusecount;

  @SerializedName("Maintenance_status_enum_id")
  private Integer state;

  @SerializedName("Start_site_name")
  private String startsite;

  @SerializedName("End_site_name")
  private String endsite;

  @SerializedName("Optical_cable_sign")
  private String sign;
  private String did;
  private String newRoutename;
  private Integer total;
  private String dir;
  private String sort;
  private Integer start;
  private List<String> ids;
  private Integer limit;
  private List<CableInfoBean> cables;
  private List<RouteInfoBean> routes;

  @SerializedName("Last_update_date")
  private Date lastUpdateDate;
  private Date deletionDate;

  @SerializedName("deleted_flag")
  private String deletedFlag;

  @SerializedName("Creation_date")
  private Date creationDate;
  private String cuser;
  private String cstate;

  @SerializedName("Optical_cable_topology")
  private String opticalCableTopology;

  @SerializedName("START_SITE_AREANO")
  private String startsiteareano;

  @SerializedName("END_SITE_AREANO")
  private String endsiteareano;

  @SerializedName("Management_authorized_flag")
  private String managementAuthorizedFlag;

  @SerializedName("Authorized_management_org_id")
  private String authorizedManagementOrgId;

  @SerializedName("Designed_purpose")
  private String designedPurpose;
  private String maintenanceAreaName;
  private Integer logId;
  private Date logTime;
  private String logOperater;
  private String seriesNo;
  private String dataQualityPrincipal;
  private String parties;

  public String beanToXML()
  {
    String xml = "";
    xml = xml + "<OPTICAL_CABLE_ID>" + this.routeid + "</OPTICAL_CABLE_ID>";
    xml = xml + "<OPTICAL_CABLE_NAME>" + this.routename + "</OPTICAL_CABLE_NAME>";
    xml = xml + "<MAINTENANCE_STATUS_ENUM_ID>" + this.state + "</MAINTENANCE_STATUS_ENUM_ID>";
    xml = xml + "<OPTICAL_CABLE_TOPOLOGY>" + this.opticalCableTopology + "</OPTICAL_CABLE_TOPOLOGY>";
    xml = xml + "<OPTICAL_CABLE_LEVEL_ENUM_ID>" + this.routelevel + "</OPTICAL_CABLE_LEVEL_ENUM_ID>";
    xml = xml + "<START_SITE_NAME>" + this.startsite + "</START_SITE_NAME>";
    xml = xml + "<END_SITE_NAME>" + this.endsite + "</END_SITE_NAME>";
    xml = xml + "<START_SITE_AREANO>" + this.startsiteareano + "</START_SITE_AREANO>";
    xml = xml + "<END_SITE_AREANO>" + this.endsiteareano + "</END_SITE_AREANO>";
    xml = xml + "<OPTICAL_CABLE_SIGN>" + this.sign + "</OPTICAL_CABLE_SIGN>";
    xml = xml + "<MANAGEMENT_AUTHORIZED_FLAG>" + this.managementAuthorizedFlag + "</MANAGEMENT_AUTHORIZED_FLAG>";
    xml = xml + "<AUTHORIZED_MANAGEMENT_ORG_ID>" + this.authorizedManagementOrgId + "</AUTHORIZED_MANAGEMENT_ORG_ID>";
    xml = xml + "<DESIGNED_PURPOSE>" + this.designedPurpose + "</DESIGNED_PURPOSE>";
    xml = xml + "<CREATION_DATE>" + this.creationDate + "</CREATION_DATE>";
    xml = xml + "<LAST_UPDATE_DATE>" + this.lastUpdateDate + "</LAST_UPDATE_DATE>";
    xml = xml + "<DELETED_FLAG>" + this.deletedFlag + "</DELETED_FLAG>";
    xml = xml + "<DELETION_DATE>" + this.deletionDate + "</DELETION_DATE>";
    xml = xml + "<CUSER>" + this.cuser + "</CUSER>";
    xml = xml + "<CSTATE>" + this.cstate + "</CSTATE>";
    return xml;
  }

  public RouteInfoBean xmlToBean(Element el) throws ParseException {
    if (el != null) {
      this.routeid = CommonUtil.elementToIngeger(el, "OPTICAL_CABLE_ID");
      this.routename = el.elementTextTrim("OPTICAL_CABLE_NAME");
      this.state = CommonUtil.elementToIngeger(el, "MAINTENANCE_STATUS_ENUM_ID");
      this.opticalCableTopology = el.elementTextTrim("OPTICAL_CABLE_TOPOLOGY");
      this.routelevel = el.elementTextTrim("OPTICAL_CABLE_LEVEL_ENUM_ID");
      this.startsite = el.elementTextTrim("START_SITE_NAME");
      this.endsite = el.elementTextTrim("END_SITE_NAME");
      this.startsiteareano = el.elementTextTrim("START_SITE_AREANO");
      this.endsiteareano = el.elementTextTrim("END_SITE_AREANO");
      this.sign = el.elementTextTrim("OPTICAL_CABLE_SIGN");
      this.managementAuthorizedFlag = el.elementTextTrim("MANAGEMENT_AUTHORIZED_FLAG");
      this.authorizedManagementOrgId = el.elementTextTrim("AUTHORIZED_MANAGEMENT_ORG_ID");
      this.designedPurpose = el.elementTextTrim("DESIGNED_PURPOSE");
      this.deletedFlag = el.elementTextTrim("DELETED_FLAG");
      this.cuser = el.elementTextTrim("CUSER");
      this.cstate = el.elementTextTrim("CSTATE");
    }
    return this;
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

public String getOpticalCableTopology()
  {
    return this.opticalCableTopology;
  }

  public void setOpticalCableTopology(String opticalCableTopology) {
    this.opticalCableTopology = opticalCableTopology;
  }

  public String getStartsiteareano() {
    return this.startsiteareano;
  }

  public void setStartsiteareano(String startsiteareano) {
    this.startsiteareano = startsiteareano;
  }

  public String getEndsiteareano() {
    return this.endsiteareano;
  }

  public void setEndsiteareano(String endsiteareano) {
    this.endsiteareano = endsiteareano;
  }

  public String getManagementAuthorizedFlag() {
    return this.managementAuthorizedFlag;
  }

  public void setManagementAuthorizedFlag(String managementAuthorizedFlag) {
    this.managementAuthorizedFlag = managementAuthorizedFlag;
  }

  public String getAuthorizedManagementOrgId() {
    return this.authorizedManagementOrgId;
  }

  public void setAuthorizedManagementOrgId(String authorizedManagementOrgId) {
    this.authorizedManagementOrgId = authorizedManagementOrgId;
  }

  public String getDesignedPurpose() {
    return this.designedPurpose;
  }

  public void setDesignedPurpose(String designedPurpose) {
    this.designedPurpose = designedPurpose;
  }

  public String getCuser() {
    return this.cuser;
  }

  public void setCuser(String cuser) {
    this.cuser = cuser;
  }

  public String getCstate() {
    return this.cstate;
  }

  public void setCstate(String cstate) {
    this.cstate = cstate;
  }

  public Date getCreationDate() {
    return this.creationDate; }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate; }

  public String getDeletedFlag() {
    return this.deletedFlag; }

  public void setDeletedFlag(String deletedFlag) {
    this.deletedFlag = deletedFlag; }

  public Date getLastUpdateDate() {
    return this.lastUpdateDate; }

  public void setLastUpdateDate(Date lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate; }

  public Date getDeletionDate() {
    return this.deletionDate; }

  public void setDeletionDate(Date deletionDate) {
    this.deletionDate = deletionDate; }

  public String getRoutename() {
    return this.routename; }

  public void setRoutename(String routename) {
    this.routename = routename; }

  public String getRoutelevel() {
    return this.routelevel; }

  public void setRoutelevel(String routelevel) {
    this.routelevel = routelevel; }

  public String getAreano() {
    return this.areano; }

  public void setAreano(String areano) {
    this.areano = areano; }

  public String getAreaname() {
    return this.areaname; }

  public void setAreaname(String areaname) {
    this.areaname = areaname; }

  public Integer getSectioncount() {
    return this.sectioncount; }

  public void setSectioncount(Integer sectioncount) {
    this.sectioncount = sectioncount; }

  public Integer getFibercount() {
    return this.fibercount; }

  public void setFibercount(Integer fibercount) {
    this.fibercount = fibercount; }

  public Integer getInusecount() {
    return this.inusecount; }

  public void setInusecount(Integer inusecount) {
    this.inusecount = inusecount; }

  public String getDid() {
    return this.did; }

  public void setDid(String did) {
    this.did = did; }

  public String getNewRoutename() {
    return this.newRoutename; }

  public void setNewRoutename(String newRoutename) {
    this.newRoutename = newRoutename; }

  public Integer getState() {
    return this.state; }

  public void setState(Integer state) {
    this.state = state; }

  public String getStartsite() {
    return this.startsite; }

  public void setStartsite(String startsite) {
    this.startsite = startsite; }

  public String getEndsite() {
    return this.endsite; }

  public void setEndsite(String endsite) {
    this.endsite = endsite; }

  public String getSign() {
    return this.sign; }

  public void setSign(String sign) {
    this.sign = sign; }

  public List<CableInfoBean> getCables() {
    return this.cables; }

  public void setCables(List<CableInfoBean> cables) {
    this.cables = cables; }

  public Integer getRouteid() {
    return this.routeid; }

  public void setRouteid(Integer routeid) {
    this.routeid = routeid; }

  public List<RouteInfoBean> getRoutes() {
    return this.routes; }

  public void setRoutes(List<RouteInfoBean> routes) {
    this.routes = routes; }

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

  public List<String> getIds() {
    return this.ids; }

  public void setIds(List<String> ids) {
    this.ids = ids;
  }

  public String getMaintenanceAreaName() {
    return this.maintenanceAreaName;
  }

  public void setMaintenanceAreaName(String maintenanceAreaName) {
    this.maintenanceAreaName = maintenanceAreaName;
  }

  public Integer getLogId() {
    return this.logId;
  }

  public void setLogId(Integer logId) {
    this.logId = logId;
  }

  public Date getLogTime() {
    return this.logTime;
  }

  public void setLogTime(Date logTime) {
    this.logTime = logTime;
  }

  public String getLogOperater() {
    return this.logOperater;
  }

  public void setLogOperater(String logOperater) {
    this.logOperater = logOperater;
  }

  public String getSeriesNo() {
    return this.seriesNo;
  }

  public void setSeriesNo(String seriesNo) {
    this.seriesNo = seriesNo;
  }
}