/*     */ package manage.route.pojo;
/*     */ 
/*     */ import base.util.CommonUtil;
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class CableRouteInfoBean
/*     */ {
/*     */   private Integer id;
/*     */   private Integer optCableId;
/*     */   private String cablename;
/*     */   private String routeSeqNum;
/*     */   private Integer tubeId;
/*     */   private String tubeName;
/*     */   private Integer idE;
/*     */   private String poleLineSegmentName;
/*     */   private Date creationDate;
/*     */   private Date lastUpdateDate;
/*     */   private String deletedFlag;
/*     */   private Date deletionDate;
/*     */   private String provinceId;
/*     */   private String cuser;
/*     */   private String cstate;
/*     */   private String pipepolename;
/*     */   private String startFacilityName;
/*     */   private String endFacilityName;
/*     */   private String startFaceNo;
/*     */   private String endFaceNo;
/*     */   private String startTubeName;
/*     */   private String endTubeName;
/*     */   private List<CableRouteInfoBean> cableroutes;
/*     */   private Integer total;
/*     */   private Integer limit;
/*     */   private Integer start;
/*     */ 
/*     */   public String beanToXml()
/*     */   {
/*  40 */     String xml = "";
/*  41 */     xml = xml + "<ID>" + this.id + "</ID>";
/*  42 */     xml = xml + "<OPT_CABLE_ID>" + this.optCableId + "</OPT_CABLE_ID>";
/*  43 */     xml = xml + "<ROUTE_SEQ_NUM>" + this.routeSeqNum + "</ROUTE_SEQ_NUM>";
/*  44 */     xml = xml + "<TUBE_ID>" + this.tubeId + "</TUBE_ID>";
/*  45 */     xml = xml + "<ID_E>" + this.idE + "</ID_E>";
/*  46 */     xml = xml + "<CREATION_DATE>" + this.creationDate + "</CREATION_DATE>";
/*  47 */     xml = xml + "<LAST_UPDATE_DATE>" + this.lastUpdateDate + "</LAST_UPDATE_DATE>";
/*  48 */     xml = xml + "<DELETED_FLAG>" + this.deletedFlag + "</DELETED_FLAG>";
/*  49 */     xml = xml + "<DELETION_DATE>" + this.deletionDate + "</DELETION_DATE>";
/*  50 */     xml = xml + "<PROVINCE_ID>" + this.provinceId + "</PROVINCE_ID>";
/*  51 */     xml = xml + "<PIPEPOLENAME>" + this.pipepolename + "</PIPEPOLENAME>";
/*  52 */     xml = xml + "<CUSER>" + this.cuser + "</CUSER>";
/*  53 */     xml = xml + "<CSTATE>" + this.cstate + "</CSTATE>";
/*  54 */     xml = xml + "<START_FACILITY_NAME>" + this.startFacilityName + "</START_FACILITY_NAME>";
/*  55 */     xml = xml + "<END_FACILITY_NAME>" + this.endFacilityName + "</END_FACILITY_NAME>";
/*  56 */     xml = xml + "<STARTFACENO>" + this.startFaceNo + "</STARTFACENO>";
/*  57 */     xml = xml + "<ENDFACENO>" + this.endFaceNo + "</ENDFACENO>";
/*  58 */     xml = xml + "<STARTTUBENAME>" + this.startTubeName + "</STARTTUBENAME>";
/*  59 */     xml = xml + "<ENDTUBENAME>" + this.endTubeName + "</ENDTUBENAME>";
/*  60 */     return xml;
/*     */   }
/*     */ 
/*     */   public CableRouteInfoBean xmlToBean(Element el) throws ParseException {
/*  64 */     if (el != null) {
/*  65 */       this.id = CommonUtil.elementToIngeger(el, "ID");
/*  66 */       this.optCableId = CommonUtil.elementToIngeger(el, "OPT_CABLE_ID");
/*  67 */       this.routeSeqNum = el.elementTextTrim("ROUTE_SEQ_NUM");
/*  68 */       this.tubeId = CommonUtil.elementToIngeger(el, "TUBE_ID");
/*  69 */       this.idE = CommonUtil.elementToIngeger(el, "ID_E");
/*  70 */       this.deletedFlag = el.elementTextTrim("DELETED_FLAG");
/*  71 */       this.provinceId = el.elementTextTrim("PROVINCE_ID");
/*  72 */       this.cuser = el.elementTextTrim("CUSER");
/*  73 */       this.cstate = el.elementTextTrim("CSTATE");
/*     */     }
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   public String getCuser() {
/*  79 */     return this.cuser;
/*     */   }
/*     */ 
/*     */   public void setCuser(String cuser) {
/*  83 */     this.cuser = cuser;
/*     */   }
/*     */ 
/*     */   public String getCstate() {
/*  87 */     return this.cstate;
/*     */   }
/*     */ 
/*     */   public void setCstate(String cstate) {
/*  91 */     this.cstate = cstate;
/*     */   }
/*     */ 
/*     */   public Integer getOptCableId() {
/*  95 */     return this.optCableId; }
/*     */ 
/*     */   public void setOptCableId(Integer optCableId) {
/*  98 */     this.optCableId = optCableId; }
/*     */ 
/*     */   public String getRouteSeqNum() {
/* 101 */     return this.routeSeqNum; }
/*     */ 
/*     */   public void setRouteSeqNum(String routeSeqNum) {
/* 104 */     this.routeSeqNum = routeSeqNum; }
/*     */ 
/*     */   public Integer getTubeId() {
/* 107 */     return this.tubeId; }
/*     */ 
/*     */   public void setTubeId(Integer tubeId) {
/* 110 */     this.tubeId = tubeId; }
/*     */ 
/*     */   public Integer getIdE() {
/* 113 */     return this.idE; }
/*     */ 
/*     */   public void setIdE(Integer idE) {
/* 116 */     this.idE = idE; }
/*     */ 
/*     */   public Date getCreationDate() {
/* 119 */     return this.creationDate; }
/*     */ 
/*     */   public void setCreationDate(Date creationDate) {
/* 122 */     this.creationDate = creationDate; }
/*     */ 
/*     */   public Date getLastUpdateDate() {
/* 125 */     return this.lastUpdateDate; }
/*     */ 
/*     */   public void setLastUpdateDate(Date lastUpdateDate) {
/* 128 */     this.lastUpdateDate = lastUpdateDate; }
/*     */ 
/*     */   public String getDeletedFlag() {
/* 131 */     return this.deletedFlag; }
/*     */ 
/*     */   public void setDeletedFlag(String deletedFlag) {
/* 134 */     this.deletedFlag = deletedFlag; }
/*     */ 
/*     */   public Date getDeletionDate() {
/* 137 */     return this.deletionDate; }
/*     */ 
/*     */   public void setDeletionDate(Date deletionDate) {
/* 140 */     this.deletionDate = deletionDate; }
/*     */ 
/*     */   public String getProvinceId() {
/* 143 */     return this.provinceId; }
/*     */ 
/*     */   public void setProvinceId(String provinceId) {
/* 146 */     this.provinceId = provinceId;
/*     */   }
/*     */ 
/*     */   public Integer getId() {
/* 150 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id) {
/* 154 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getCablename() {
/* 158 */     return this.cablename;
/*     */   }
/*     */ 
/*     */   public void setCablename(String cablename) {
/* 162 */     this.cablename = cablename;
/*     */   }
/*     */ 
/*     */   public String getTubeName() {
/* 166 */     return this.tubeName;
/*     */   }
/*     */ 
/*     */   public void setTubeName(String tubeName) {
/* 170 */     this.tubeName = tubeName;
/*     */   }
/*     */ 
/*     */   public String getPoleLineSegmentName() {
/* 174 */     return this.poleLineSegmentName;
/*     */   }
/*     */ 
/*     */   public void setPoleLineSegmentName(String poleLineSegmentName) {
/* 178 */     this.poleLineSegmentName = poleLineSegmentName;
/*     */   }
/*     */ 
/*     */   public String getPipepolename() {
/* 182 */     return this.pipepolename;
/*     */   }
/*     */ 
/*     */   public void setPipepolename(String pipepolename) {
/* 186 */     this.pipepolename = pipepolename;
/*     */   }
/*     */ 
/*     */   public String getStartFacilityName() {
/* 190 */     return this.startFacilityName;
/*     */   }
/*     */ 
/*     */   public void setStartFacilityName(String startFacilityName) {
/* 194 */     this.startFacilityName = startFacilityName;
/*     */   }
/*     */ 
/*     */   public String getEndFacilityName() {
/* 198 */     return this.endFacilityName;
/*     */   }
/*     */ 
/*     */   public void setEndFacilityName(String endFacilityName) {
/* 202 */     this.endFacilityName = endFacilityName;
/*     */   }
/*     */ 
/*     */   public String getStartFaceNo() {
/* 206 */     return this.startFaceNo;
/*     */   }
/*     */ 
/*     */   public void setStartFaceNo(String startFaceNo) {
/* 210 */     this.startFaceNo = startFaceNo;
/*     */   }
/*     */ 
/*     */   public String getEndFaceNo() {
/* 214 */     return this.endFaceNo;
/*     */   }
/*     */ 
/*     */   public void setEndFaceNo(String endFaceNo) {
/* 218 */     this.endFaceNo = endFaceNo;
/*     */   }
/*     */ 
/*     */   public String getStartTubeName() {
/* 222 */     return this.startTubeName;
/*     */   }
/*     */ 
/*     */   public void setStartTubeName(String startTubeName) {
/* 226 */     this.startTubeName = startTubeName;
/*     */   }
/*     */ 
/*     */   public String getEndTubeName() {
/* 230 */     return this.endTubeName;
/*     */   }
/*     */ 
/*     */   public void setEndTubeName(String endTubeName) {
/* 234 */     this.endTubeName = endTubeName;
/*     */   }
/*     */ 
/*     */   public List<CableRouteInfoBean> getCableroutes() {
/* 238 */     return this.cableroutes;
/*     */   }
/*     */ 
/*     */   public void setCableroutes(List<CableRouteInfoBean> cableroutes) {
/* 242 */     this.cableroutes = cableroutes;
/*     */   }
/*     */ 
/*     */   public Integer getTotal() {
/* 246 */     return this.total;
/*     */   }
/*     */ 
/*     */   public void setTotal(Integer total) {
/* 250 */     this.total = total;
/*     */   }
/*     */ 
/*     */   public Integer getLimit() {
/* 254 */     return this.limit;
/*     */   }
/*     */ 
/*     */   public void setLimit(Integer limit) {
/* 258 */     this.limit = limit;
/*     */   }
/*     */ 
/*     */   public Integer getStart() {
/* 262 */     return this.start;
/*     */   }
/*     */ 
/*     */   public void setStart(Integer start) {
/* 266 */     this.start = start;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 271 */     if (this == obj)
/* 272 */       return true;
/* 273 */     if (obj == null)
/* 274 */       return false;
/* 275 */     if (super.getClass() != obj.getClass())
/* 276 */       return false;
/* 277 */     CableRouteInfoBean other = (CableRouteInfoBean)obj;
/* 278 */     if ((((this.endTubeName == null) || (this.endTubeName.equals("")))) && 
/* 279 */       (((this.endFaceNo == null) || (this.endFaceNo.equals("")))) && 
/* 280 */       (((this.endFacilityName == null) || (this.endFacilityName.equals("")))) && 
/* 281 */       (((this.startFacilityName == null) || (this.startFacilityName.equals("")))) && 
/* 282 */       (((this.startTubeName == null) || (this.startTubeName.equals("")))) && ((
/* 283 */       (this.endFaceNo == null) || (this.endFaceNo.equals(""))))) {
/* 284 */       return false;
/*     */     }
/* 286 */     if (this.optCableId == null)
/* 287 */       if (other.optCableId != null)
/* 288 */         return false;
/* 289 */     else if (!(this.optCableId.equals(other.optCableId)))
/* 290 */       return false;
/* 291 */     if (this.endFaceNo == null)
/* 292 */       if (other.endFaceNo != null)
/* 293 */         return false;
/* 294 */     else if (!(this.endFaceNo.equals(other.endFaceNo)))
/* 295 */       return false;
/* 296 */     if (this.endFacilityName == null)
/* 297 */       if (other.endFacilityName != null)
/* 298 */         return false;
/* 299 */     else if (!(this.endFacilityName.equals(other.endFacilityName)))
/* 300 */       return false;
/* 301 */     if (this.endTubeName == null)
/* 302 */       if (other.endTubeName != null)
/* 303 */         return false;
/* 304 */     else if (!(this.endTubeName.equals(other.endTubeName)))
/* 305 */       return false;
/* 306 */     if (this.startFaceNo == null)
/* 307 */       if (other.startFaceNo != null)
/* 308 */         return false;
/* 309 */     else if (!(this.startFaceNo.equals(other.startFaceNo)))
/* 310 */       return false;
/* 311 */     if (this.startFacilityName == null)
/* 312 */       if (other.startFacilityName != null)
/* 313 */         return false;
/* 314 */     else if (!(this.startFacilityName.equals(other.startFacilityName)))
/* 315 */       return false;
/* 316 */     if (this.startTubeName == null)
/* 317 */       if (other.startTubeName != null)
/* 318 */         return false;
/* 319 */     else if (!(this.startTubeName.equals(other.startTubeName)))
/* 320 */       return false;
/* 321 */     return true;
/*     */   }
/*     */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.route.pojo.CableRouteInfoBean
 * JD-Core Version:    0.5.3
 */