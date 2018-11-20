/*     */ package manage.hardware.pojo;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class VersionInfoBean
/*     */ {
/*     */   private Integer id;
/*     */   private String type;
/*     */   private String version;
/*     */   private String updatelog;
/*     */   private String filename;
/*     */   private String url;
/*     */   private Date createdate;
/*  15 */   private File upgradeFile = new File("");
/*     */   private String sort;
/*     */   private String dir;
/*     */   private Integer total;
/*     */   private Integer start;
/*     */   private Integer limit;
/*     */   private List<VersionInfoBean> versions;
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  41 */     return this.id; }
/*     */ 
/*     */   public void setId(Integer id) {
/*  44 */     this.id = id; }
/*     */ 
/*     */   public String getVersion() {
/*  47 */     return this.version; }
/*     */ 
/*     */   public void setVersion(String version) {
/*  50 */     this.version = version; }
/*     */ 
/*     */   public String getUpdatelog() {
/*  53 */     return this.updatelog; }
/*     */ 
/*     */   public void setUpdatelog(String updatelog) {
/*  56 */     this.updatelog = updatelog; }
/*     */ 
/*     */   public String getUrl() {
/*  59 */     return this.url; }
/*     */ 
/*     */   public void setUrl(String url) {
/*  62 */     this.url = url; }
/*     */ 
/*     */   public Date getCreatedate() {
/*  65 */     return this.createdate; }
/*     */ 
/*     */   public void setCreatedate(Date createdate) {
/*  68 */     this.createdate = createdate; }
/*     */ 
/*     */   public String getFilename() {
/*  71 */     return this.filename; }
/*     */ 
/*     */   public void setFilename(String filename) {
/*  74 */     this.filename = filename; }
/*     */ 
/*     */   public String getSort() {
/*  77 */     return this.sort; }
/*     */ 
/*     */   public void setSort(String sort) {
/*  80 */     this.sort = sort; }
/*     */ 
/*     */   public String getDir() {
/*  83 */     return this.dir; }
/*     */ 
/*     */   public void setDir(String dir) {
/*  86 */     this.dir = dir; }
/*     */ 
/*     */   public Integer getTotal() {
/*  89 */     return this.total; }
/*     */ 
/*     */   public void setTotal(Integer total) {
/*  92 */     this.total = total; }
/*     */ 
/*     */   public Integer getStart() {
/*  95 */     return this.start; }
/*     */ 
/*     */   public void setStart(Integer start) {
/*  98 */     this.start = start; }
/*     */ 
/*     */   public Integer getLimit() {
/* 101 */     return this.limit; }
/*     */ 
/*     */   public void setLimit(Integer limit) {
/* 104 */     this.limit = limit; }
/*     */ 
/*     */   public List<VersionInfoBean> getVersions() {
/* 107 */     return this.versions; }
/*     */ 
/*     */   public void setVersions(List<VersionInfoBean> versions) {
/* 110 */     this.versions = versions; }
/*     */ 
/*     */   public File getUpgradeFile() {
/* 113 */     return this.upgradeFile; }
/*     */ 
/*     */   public void setUpgradeFile(File upgradeFile) {
/* 116 */     this.upgradeFile = upgradeFile; }
/*     */ 
/*     */   public String getType() {
/* 119 */     return this.type; }
/*     */ 
/*     */   public void setType(String type) {
/* 122 */     this.type = type;
/*     */   }
/*     */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.hardware.pojo.VersionInfoBean
 * JD-Core Version:    0.5.3
 */