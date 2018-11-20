/*     */ package manage.document.pojo;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class DocEqutInfoBean
/*     */ {
/*     */   private Integer id;
/*     */   private String did;
/*     */   private String estat;
/*     */   private String ecode;
/*     */   private String ename;
/*     */   private String eaddr;
/*     */   private String etype;
/*     */   private String emodel;
/*     */   private String lon;
/*     */   private String lat;
/*     */   private String note;
/*     */   private Date mtime;
/*     */   private String mp;
/*     */   private String mpname;
/*     */   private String station;
/*     */   private String devmap;
/*     */   private String nomap;
/*     */   private String areano;
/*     */   private String areaname;
/*     */   private String pcount;
/*     */   private String eid;
/*     */   private String ip;
/*     */   private String prlid;
/*     */   private String mask;
/*     */   private String gateway;
/*     */   private File excle;
/*     */   private String filename;
/*     */   private String isrealtime;
/*     */   private String sort;
/*     */   private String dir;
/*     */   private Integer total;
/*     */   private Integer start;
/*     */   private Integer limit;
/*     */   private List<DocEqutInfoBean> docEquts;
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  66 */     return this.id; }
/*     */ 
/*     */   public void setId(Integer id) {
/*  69 */     this.id = id; }
/*     */ 
/*     */   public String getDid() {
/*  72 */     return this.did; }
/*     */ 
/*     */   public void setDid(String did) {
/*  75 */     this.did = did; }
/*     */ 
/*     */   public String getEstat() {
/*  78 */     return this.estat; }
/*     */ 
/*     */   public void setEstat(String estat) {
/*  81 */     this.estat = estat; }
/*     */ 
/*     */   public String getEcode() {
/*  84 */     return this.ecode; }
/*     */ 
/*     */   public void setEcode(String ecode) {
/*  87 */     this.ecode = ecode; }
/*     */ 
/*     */   public String getEname() {
/*  90 */     return this.ename; }
/*     */ 
/*     */   public void setEname(String ename) {
/*  93 */     this.ename = ename; }
/*     */ 
/*     */   public String getEaddr() {
/*  96 */     return this.eaddr; }
/*     */ 
/*     */   public void setEaddr(String eaddr) {
/*  99 */     this.eaddr = eaddr; }
/*     */ 
/*     */   public String getEtype() {
/* 102 */     return this.etype; }
/*     */ 
/*     */   public void setEtype(String etype) {
/* 105 */     this.etype = etype; }
/*     */ 
/*     */   public String getEmodel() {
/* 108 */     return this.emodel; }
/*     */ 
/*     */   public void setEmodel(String emodel) {
/* 111 */     this.emodel = emodel; }
/*     */ 
/*     */   public String getLon() {
/* 114 */     return this.lon; }
/*     */ 
/*     */   public void setLon(String lon) {
/* 117 */     this.lon = lon; }
/*     */ 
/*     */   public String getLat() {
/* 120 */     return this.lat; }
/*     */ 
/*     */   public void setLat(String lat) {
/* 123 */     this.lat = lat; }
/*     */ 
/*     */   public String getNote() {
/* 126 */     return this.note; }
/*     */ 
/*     */   public void setNote(String note) {
/* 129 */     this.note = note; }
/*     */ 
/*     */   public Date getMtime() {
/* 132 */     return this.mtime; }
/*     */ 
/*     */   public void setMtime(Date mtime) {
/* 135 */     this.mtime = mtime; }
/*     */ 
/*     */   public String getMp() {
/* 138 */     return this.mp; }
/*     */ 
/*     */   public void setMp(String mp) {
/* 141 */     this.mp = mp; }
/*     */ 
/*     */   public String getStation() {
/* 144 */     return this.station; }
/*     */ 
/*     */   public void setStation(String station) {
/* 147 */     this.station = station; }
/*     */ 
/*     */   public String getDevmap() {
/* 150 */     return this.devmap; }
/*     */ 
/*     */   public void setDevmap(String devmap) {
/* 153 */     this.devmap = devmap; }
/*     */ 
/*     */   public String getNomap() {
/* 156 */     return this.nomap; }
/*     */ 
/*     */   public void setNomap(String nomap) {
/* 159 */     this.nomap = nomap; }
/*     */ 
/*     */   public String getAreano() {
/* 162 */     return this.areano; }
/*     */ 
/*     */   public void setAreano(String areano) {
/* 165 */     this.areano = areano; }
/*     */ 
/*     */   public String getEid() {
/* 168 */     return this.eid; }
/*     */ 
/*     */   public void setEid(String eid) {
/* 171 */     this.eid = eid; }
/*     */ 
/*     */   public String getIp() {
/* 174 */     return this.ip; }
/*     */ 
/*     */   public void setIp(String ip) {
/* 177 */     this.ip = ip; }
/*     */ 
/*     */   public String getPcount() {
/* 180 */     return this.pcount; }
/*     */ 
/*     */   public void setPcount(String pcount) {
/* 183 */     this.pcount = pcount; }
/*     */ 
/*     */   public String getAreaname() {
/* 186 */     return this.areaname; }
/*     */ 
/*     */   public void setAreaname(String areaname) {
/* 189 */     this.areaname = areaname; }
/*     */ 
/*     */   public List<DocEqutInfoBean> getDocEquts() {
/* 192 */     return this.docEquts; }
/*     */ 
/*     */   public void setDocEquts(List<DocEqutInfoBean> docEquts) {
/* 195 */     this.docEquts = docEquts; }
/*     */ 
/*     */   public String getSort() {
/* 198 */     return this.sort; }
/*     */ 
/*     */   public void setSort(String sort) {
/* 201 */     this.sort = sort; }
/*     */ 
/*     */   public String getDir() {
/* 204 */     return this.dir; }
/*     */ 
/*     */   public void setDir(String dir) {
/* 207 */     this.dir = dir; }
/*     */ 
/*     */   public Integer getTotal() {
/* 210 */     return this.total; }
/*     */ 
/*     */   public void setTotal(Integer total) {
/* 213 */     this.total = total; }
/*     */ 
/*     */   public Integer getStart() {
/* 216 */     return this.start; }
/*     */ 
/*     */   public void setStart(Integer start) {
/* 219 */     this.start = start; }
/*     */ 
/*     */   public Integer getLimit() {
/* 222 */     return this.limit; }
/*     */ 
/*     */   public void setLimit(Integer limit) {
/* 225 */     this.limit = limit; }
/*     */ 
/*     */   public File getExcle() {
/* 228 */     return this.excle; }
/*     */ 
/*     */   public void setExcle(File excle) {
/* 231 */     this.excle = excle; }
/*     */ 
/*     */   public String getFilename() {
/* 234 */     return this.filename; }
/*     */ 
/*     */   public void setFilename(String filename) {
/* 237 */     this.filename = filename; }
/*     */ 
/*     */   public String getMpname() {
/* 240 */     return this.mpname; }
/*     */ 
/*     */   public void setMpname(String mpname) {
/* 243 */     this.mpname = mpname; }
/*     */ 
/*     */   public String getPrlid() {
/* 246 */     return this.prlid; }
/*     */ 
/*     */   public void setPrlid(String prlid) {
/* 249 */     this.prlid = prlid; }
/*     */ 
/*     */   public String getMask() {
/* 252 */     return this.mask; }
/*     */ 
/*     */   public void setMask(String mask) {
/* 255 */     this.mask = mask; }
/*     */ 
/*     */   public String getGateway() {
/* 258 */     return this.gateway; }
/*     */ 
/*     */   public void setGateway(String gateway) {
/* 261 */     this.gateway = gateway; }
/*     */ 
/*     */   public String getIsrealtime() {
/* 264 */     return this.isrealtime; }
/*     */ 
/*     */   public void setIsrealtime(String isrealtime) {
/* 267 */     this.isrealtime = isrealtime;
/*     */   }
/*     */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.document.pojo.DocEqutInfoBean
 * JD-Core Version:    0.5.3
 */