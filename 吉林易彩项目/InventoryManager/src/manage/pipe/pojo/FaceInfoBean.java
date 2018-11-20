/*     */ package manage.pipe.pojo;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class FaceInfoBean
/*     */ {
/*     */   private Integer faceId;
/*     */   private Integer wellId;
/*     */   private Integer cols;
/*     */   private Integer rows;
/*     */   private String locationNo;
/*     */   private Integer oppoFaceId;
/*     */   private String pipeSegmentId;
/*     */   private String deletedFlag;
/*     */   private Integer faceId1;
/*     */   private Integer faceId2;
/*     */   private String tubeNames;
/*     */   private String oppoWellName;
/*     */   private Integer oppoWellId;
/*     */   private String oppolocationNo;
/*     */   private String disableTubes;
/*     */   private String isVisible;
/*     */   private String cuser;
/*     */   private String cstate;
/*     */   private List<FaceInfoBean> faces;
/*     */   private Integer logId;
/*     */   private Date logTime;
/*     */   private String logOperater;
/*     */   private String seriesNo;
/*     */ 
/*     */   public String getOppolocationNo()
/*     */   {
/*  40 */     return this.oppolocationNo; }
/*     */ 
/*     */   public void setOppolocationNo(String oppolocationNo) {
/*  43 */     this.oppolocationNo = oppolocationNo; }
/*     */ 
/*     */   public Integer getFaceId() {
/*  46 */     return this.faceId; }
/*     */ 
/*     */   public void setFaceId(Integer faceId) {
/*  49 */     this.faceId = faceId; }
/*     */ 
/*     */   public Integer getWellId() {
/*  52 */     return this.wellId; }
/*     */ 
/*     */   public void setWellId(Integer wellId) {
/*  55 */     this.wellId = wellId; }
/*     */ 
/*     */   public Integer getCols() {
/*  58 */     return this.cols; }
/*     */ 
/*     */   public void setCols(Integer cols) {
/*  61 */     this.cols = cols; }
/*     */ 
/*     */   public Integer getRows() {
/*  64 */     return this.rows; }
/*     */ 
/*     */   public void setRows(Integer rows) {
/*  67 */     this.rows = rows; }
/*     */ 
/*     */   public String getLocationNo() {
/*  70 */     return this.locationNo; }
/*     */ 
/*     */   public void setLocationNo(String locationNo) {
/*  73 */     this.locationNo = locationNo; }
/*     */ 
/*     */   public Integer getOppoFaceId() {
/*  76 */     return this.oppoFaceId; }
/*     */ 
/*     */   public void setOppoFaceId(Integer oppoFaceId) {
/*  79 */     this.oppoFaceId = oppoFaceId; }
/*     */ 
/*     */   public String getPipeSegmentId() {
/*  82 */     return this.pipeSegmentId; }
/*     */ 
/*     */   public void setPipeSegmentId(String pipeSegmentId) {
/*  85 */     this.pipeSegmentId = pipeSegmentId; }
/*     */ 
/*     */   public Integer getFaceId1() {
/*  88 */     return this.faceId1; }
/*     */ 
/*     */   public void setFaceId1(Integer faceId1) {
/*  91 */     this.faceId1 = faceId1; }
/*     */ 
/*     */   public Integer getFaceId2() {
/*  94 */     return this.faceId2; }
/*     */ 
/*     */   public void setFaceId2(Integer faceId2) {
/*  97 */     this.faceId2 = faceId2; }
/*     */ 
/*     */   public String getDeletedFlag() {
/* 100 */     return this.deletedFlag; }
/*     */ 
/*     */   public void setDeletedFlag(String deletedFlag) {
/* 103 */     this.deletedFlag = deletedFlag; }
/*     */ 
/*     */   public String getCuser() {
/* 106 */     return this.cuser; }
/*     */ 
/*     */   public void setCuser(String cuser) {
/* 109 */     this.cuser = cuser; }
/*     */ 
/*     */   public String getCstate() {
/* 112 */     return this.cstate; }
/*     */ 
/*     */   public void setCstate(String cstate) {
/* 115 */     this.cstate = cstate; }
/*     */ 
/*     */   public String getTubeNames() {
/* 118 */     return this.tubeNames; }
/*     */ 
/*     */   public void setTubeNames(String tubeNames) {
/* 121 */     this.tubeNames = tubeNames; }
/*     */ 
/*     */   public String getOppoWellName() {
/* 124 */     return this.oppoWellName; }
/*     */ 
/*     */   public void setOppoWellName(String oppoWellName) {
/* 127 */     this.oppoWellName = oppoWellName; }
/*     */ 
/*     */   public Integer getOppoWellId() {
/* 130 */     return this.oppoWellId; }
/*     */ 
/*     */   public void setOppoWellId(Integer oppoWellId) {
/* 133 */     this.oppoWellId = oppoWellId; }
/*     */ 
/*     */   public String getDisableTubes() {
/* 136 */     return this.disableTubes; }
/*     */ 
/*     */   public void setDisableTubes(String disableTubes) {
/* 139 */     this.disableTubes = disableTubes; }
/*     */ 
/*     */   public Integer getLogId() {
/* 142 */     return this.logId; }
/*     */ 
/*     */   public void setLogId(Integer logId) {
/* 145 */     this.logId = logId; }
/*     */ 
/*     */   public Date getLogTime() {
/* 148 */     return this.logTime; }
/*     */ 
/*     */   public void setLogTime(Date logTime) {
/* 151 */     this.logTime = logTime; }
/*     */ 
/*     */   public String getLogOperater() {
/* 154 */     return this.logOperater; }
/*     */ 
/*     */   public void setLogOperater(String logOperater) {
/* 157 */     this.logOperater = logOperater; }
/*     */ 
/*     */   public String getIsVisible() {
/* 160 */     return this.isVisible; }
/*     */ 
/*     */   public void setIsVisible(String isVisible) {
/* 163 */     this.isVisible = isVisible; }
/*     */ 
/*     */   public List<FaceInfoBean> getFaces() {
/* 166 */     return this.faces; }
/*     */ 
/*     */   public void setFaces(List<FaceInfoBean> faces) {
/* 169 */     this.faces = faces; }
/*     */ 
/*     */   public String getSeriesNo() {
/* 172 */     return this.seriesNo; }
/*     */ 
/*     */   public void setSeriesNo(String seriesNo) {
/* 175 */     this.seriesNo = seriesNo;
/*     */   }
/*     */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.pipe.pojo.FaceInfoBean
 * JD-Core Version:    0.5.3
 */