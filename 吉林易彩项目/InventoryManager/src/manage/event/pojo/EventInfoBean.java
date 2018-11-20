/*     */ package manage.event.pojo;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class EventInfoBean
/*     */ {
/*     */   private Integer eventId;
/*     */   private String seriesNo;
/*     */   private String type;
/*     */   private String username;
/*     */   private String ip;
/*     */   private String mac;
/*     */   private String lon;
/*     */   private String lat;
/*     */   private Date eventDate;
/*     */   private String module;
/*     */   private String method;
/*     */   private int result;
/*     */   private String requestStr;
/*     */   private String responseStr;
/*     */   private String areano;
/*     */   private List<EventInfoBean> events;
/*     */   private Date startTime;
/*     */   private Date endTime;
/*     */   private Integer start;
/*     */   private Integer limit;
/*     */   private Integer total;
/*     */ 
/*     */   public Integer getEventId()
/*     */   {
/*  31 */     return this.eventId; }
/*     */ 
/*     */   public void setEventId(Integer eventId) {
/*  34 */     this.eventId = eventId; }
/*     */ 
/*     */   public String getType() {
/*  37 */     return this.type; }
/*     */ 
/*     */   public void setType(String type) {
/*  40 */     this.type = type; }
/*     */ 
/*     */   public String getUsername() {
/*  43 */     return this.username; }
/*     */ 
/*     */   public void setUsername(String username) {
/*  46 */     this.username = username; }
/*     */ 
/*     */   public String getIp() {
/*  49 */     return this.ip; }
/*     */ 
/*     */   public void setIp(String ip) {
/*  52 */     this.ip = ip; }
/*     */ 
/*     */   public String getMac() {
/*  55 */     return this.mac; }
/*     */ 
/*     */   public void setMac(String mac) {
/*  58 */     this.mac = mac; }
/*     */ 
/*     */   public String getLon() {
/*  61 */     return this.lon; }
/*     */ 
/*     */   public void setLon(String lon) {
/*  64 */     this.lon = lon; }
/*     */ 
/*     */   public String getLat() {
/*  67 */     return this.lat; }
/*     */ 
/*     */   public void setLat(String lat) {
/*  70 */     this.lat = lat; }
/*     */ 
/*     */   public Date getEventDate() {
/*  73 */     return this.eventDate; }
/*     */ 
/*     */   public void setEventDate(Date eventDate) {
/*  76 */     this.eventDate = eventDate; }
/*     */ 
/*     */   public String getModule() {
/*  79 */     return this.module; }
/*     */ 
/*     */   public void setModule(String module) {
/*  82 */     this.module = module; }
/*     */ 
/*     */   public String getMethod() {
/*  85 */     return this.method; }
/*     */ 
/*     */   public void setMethod(String method) {
/*  88 */     this.method = method;
/*     */   }
/*     */ 
/*     */   public String getRequestStr() {
/*  92 */     return this.requestStr; }
/*     */ 
/*     */   public void setRequestStr(String requestStr) {
/*  95 */     this.requestStr = requestStr; }
/*     */ 
/*     */   public String getResponseStr() {
/*  98 */     return this.responseStr; }
/*     */ 
/*     */   public void setResponseStr(String responseStr) {
/* 101 */     this.responseStr = responseStr; }
/*     */ 
/*     */   public String getSeriesNo() {
/* 104 */     return this.seriesNo; }
/*     */ 
/*     */   public void setSeriesNo(String seriesNo) {
/* 107 */     this.seriesNo = seriesNo; }
/*     */ 
/*     */   public String getAreano() {
/* 110 */     return this.areano; }
/*     */ 
/*     */   public void setAreano(String areano) {
/* 113 */     this.areano = areano; }
/*     */ 
/*     */   public int getResult() {
/* 116 */     return this.result; }
/*     */ 
/*     */   public void setResult(int result) {
/* 119 */     this.result = result; }
/*     */ 
/*     */   public List<EventInfoBean> getEvents() {
/* 122 */     return this.events; }
/*     */ 
/*     */   public void setEvents(List<EventInfoBean> events) {
/* 125 */     this.events = events; }
/*     */ 
/*     */   public Date getStartTime() {
/* 128 */     return this.startTime; }
/*     */ 
/*     */   public void setStartTime(Date startTime) {
/* 131 */     this.startTime = startTime; }
/*     */ 
/*     */   public Date getEndTime() {
/* 134 */     return this.endTime; }
/*     */ 
/*     */   public void setEndTime(Date endTime) {
/* 137 */     this.endTime = endTime; }
/*     */ 
/*     */   public Integer getStart() {
/* 140 */     return this.start; }
/*     */ 
/*     */   public void setStart(Integer start) {
/* 143 */     this.start = start; }
/*     */ 
/*     */   public Integer getLimit() {
/* 146 */     return this.limit; }
/*     */ 
/*     */   public void setLimit(Integer limit) {
/* 149 */     this.limit = limit; }
/*     */ 
/*     */   public Integer getTotal() {
/* 152 */     return this.total; }
/*     */ 
/*     */   public void setTotal(Integer total) {
/* 155 */     this.total = total;
/*     */   }
/*     */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.event.pojo.EventInfoBean
 * JD-Core Version:    0.5.3
 */