/*     */ package manage.point.pojo;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class PointEventInfoBean
/*     */ {
/*     */   private Integer id;
/*     */   private Integer userId;
/*     */   private String realname;
/*     */   private Date alarmTime;
/*     */   private String eid;
/*     */   private String eip;
/*     */   private String pid;
/*     */   private String fibercode;
/*     */   private String type;
/*     */   private String state;
/*     */   private String level;
/*     */   private String areano;
/*     */   private String alarmText;
/*     */   private String isEvent;
/*     */   private String isLatest;
/*     */   private Date alarmTimeb;
/*     */   private Date alarmTimee;
/*     */   private String beanType;
/*     */   private String ename;
/*     */   private String pcode;
/*     */   private Integer total;
/*     */   private String dir;
/*     */   private String sort;
/*     */   private Integer start;
/*     */   private Integer limit;
/*     */   private List<PointEventInfoBean> events;
/*     */   private Integer happenAlarm;
/*     */   private Integer PanAlarm;
/*     */   private Integer PointAlarm;
/*     */   private Integer oneAlarm;
/*     */   private Integer twoAlarm;
/*     */   private Integer localAlarm;
/*     */   private Integer pullAlarm;
/*     */   private Integer inmatchAlarm;
/*     */   private String workorderno;
/*     */   private String workordersubno;
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  80 */     return this.id; }
/*     */ 
/*     */   public void setId(Integer id) {
/*  83 */     this.id = id; }
/*     */ 
/*     */   public Integer getUserId() {
/*  86 */     return this.userId; }
/*     */ 
/*     */   public void setUserId(Integer userId) {
/*  89 */     this.userId = userId; }
/*     */ 
/*     */   public Date getAlarmTime() {
/*  92 */     return this.alarmTime; }
/*     */ 
/*     */   public void setAlarmTime(Date alarmTime) {
/*  95 */     this.alarmTime = alarmTime; }
/*     */ 
/*     */   public String getEid() {
/*  98 */     return this.eid; }
/*     */ 
/*     */   public void setEid(String eid) {
/* 101 */     this.eid = eid; }
/*     */ 
/*     */   public String getPid() {
/* 104 */     return this.pid; }
/*     */ 
/*     */   public void setPid(String pid) {
/* 107 */     this.pid = pid; }
/*     */ 
/*     */   public String getState() {
/* 110 */     return this.state;
/*     */   }
/*     */ 
/*     */   public void setState(String state)
/*     */   {
/* 118 */     this.state = state; }
/*     */ 
/*     */   public String getAreano() {
/* 121 */     return this.areano; }
/*     */ 
/*     */   public void setAreano(String areano) {
/* 124 */     this.areano = areano; }
/*     */ 
/*     */   public String getAlarmText() {
/* 127 */     return this.alarmText; }
/*     */ 
/*     */   public void setAlarmText(String alarmText) {
/* 130 */     this.alarmText = alarmText; }
/*     */ 
/*     */   public String getFibercode() {
/* 133 */     return this.fibercode; }
/*     */ 
/*     */   public void setFibercode(String fibercode) {
/* 136 */     this.fibercode = fibercode; }
/*     */ 
/*     */   public String getType() {
/* 139 */     return this.type; }
/*     */ 
/*     */   public void setType(String type) {
/* 142 */     this.type = type; }
/*     */ 
/*     */   public String getIsEvent() {
/* 145 */     return this.isEvent; }
/*     */ 
/*     */   public void setIsEvent(String isEvent) {
/* 148 */     this.isEvent = isEvent; }
/*     */ 
/*     */   public String getIsLatest() {
/* 151 */     return this.isLatest; }
/*     */ 
/*     */   public void setIsLatest(String isLatest) {
/* 154 */     this.isLatest = isLatest; }
/*     */ 
/*     */   public String getRealname() {
/* 157 */     return this.realname; }
/*     */ 
/*     */   public void setRealname(String realname) {
/* 160 */     this.realname = realname; }
/*     */ 
/*     */   public String getEip() {
/* 163 */     return this.eip; }
/*     */ 
/*     */   public void setEip(String eip) {
/* 166 */     this.eip = eip; }
/*     */ 
/*     */   public Date getAlarmTimeb() {
/* 169 */     return this.alarmTimeb; }
/*     */ 
/*     */   public void setAlarmTimeb(Date alarmTimeb) {
/* 172 */     this.alarmTimeb = alarmTimeb; }
/*     */ 
/*     */   public Date getAlarmTimee() {
/* 175 */     return this.alarmTimee; }
/*     */ 
/*     */   public void setAlarmTimee(Date alarmTimee) {
/* 178 */     this.alarmTimee = alarmTimee; }
/*     */ 
/*     */   public String getBeanType() {
/* 181 */     return this.beanType; }
/*     */ 
/*     */   public void setBeanType(String beanType) {
/* 184 */     this.beanType = beanType; }
/*     */ 
/*     */   public Integer getTotal() {
/* 187 */     return this.total; }
/*     */ 
/*     */   public void setTotal(Integer total) {
/* 190 */     this.total = total; }
/*     */ 
/*     */   public String getDir() {
/* 193 */     return this.dir; }
/*     */ 
/*     */   public void setDir(String dir) {
/* 196 */     this.dir = dir; }
/*     */ 
/*     */   public String getSort() {
/* 199 */     return this.sort; }
/*     */ 
/*     */   public void setSort(String sort) {
/* 202 */     this.sort = sort; }
/*     */ 
/*     */   public Integer getStart() {
/* 205 */     return this.start; }
/*     */ 
/*     */   public void setStart(Integer start) {
/* 208 */     this.start = start; }
/*     */ 
/*     */   public Integer getLimit() {
/* 211 */     return this.limit; }
/*     */ 
/*     */   public void setLimit(Integer limit) {
/* 214 */     this.limit = limit; }
/*     */ 
/*     */   public List<PointEventInfoBean> getEvents() {
/* 217 */     return this.events; }
/*     */ 
/*     */   public void setEvents(List<PointEventInfoBean> events) {
/* 220 */     this.events = events; }
/*     */ 
/*     */   public String getEname() {
/* 223 */     return this.ename; }
/*     */ 
/*     */   public void setEname(String ename) {
/* 226 */     this.ename = ename; }
/*     */ 
/*     */   public String getPcode() {
/* 229 */     return this.pcode; }
/*     */ 
/*     */   public void setPcode(String pcode) {
/* 232 */     this.pcode = pcode; }
/*     */ 
/*     */   public String getLevel() {
/* 235 */     return this.level; }
/*     */ 
/*     */   public void setLevel(String level) {
/* 238 */     this.level = level; }
/*     */ 
/*     */   public Integer getHappenAlarm() {
/* 241 */     return this.happenAlarm; }
/*     */ 
/*     */   public void setHappenAlarm(Integer happenAlarm) {
/* 244 */     this.happenAlarm = happenAlarm; }
/*     */ 
/*     */   public Integer getPanAlarm() {
/* 247 */     return this.PanAlarm; }
/*     */ 
/*     */   public void setPanAlarm(Integer panAlarm) {
/* 250 */     this.PanAlarm = panAlarm; }
/*     */ 
/*     */   public Integer getPointAlarm() {
/* 253 */     return this.PointAlarm; }
/*     */ 
/*     */   public void setPointAlarm(Integer pointAlarm) {
/* 256 */     this.PointAlarm = pointAlarm; }
/*     */ 
/*     */   public Integer getOneAlarm() {
/* 259 */     return this.oneAlarm; }
/*     */ 
/*     */   public void setOneAlarm(Integer oneAlarm) {
/* 262 */     this.oneAlarm = oneAlarm; }
/*     */ 
/*     */   public Integer getTwoAlarm() {
/* 265 */     return this.twoAlarm; }
/*     */ 
/*     */   public void setTwoAlarm(Integer twoAlarm) {
/* 268 */     this.twoAlarm = twoAlarm; }
/*     */ 
/*     */   public Integer getLocalAlarm() {
/* 271 */     return this.localAlarm; }
/*     */ 
/*     */   public void setLocalAlarm(Integer localAlarm) {
/* 274 */     this.localAlarm = localAlarm; }
/*     */ 
/*     */   public Integer getPullAlarm() {
/* 277 */     return this.pullAlarm; }
/*     */ 
/*     */   public void setPullAlarm(Integer pullAlarm) {
/* 280 */     this.pullAlarm = pullAlarm; }
/*     */ 
/*     */   public Integer getInmatchAlarm() {
/* 283 */     return this.inmatchAlarm; }
/*     */ 
/*     */   public void setInmatchAlarm(Integer inmatchAlarm) {
/* 286 */     this.inmatchAlarm = inmatchAlarm; }
/*     */ 
/*     */   public String getWorkorderno() {
/* 289 */     return this.workorderno; }
/*     */ 
/*     */   public void setWorkorderno(String workorderno) {
/* 292 */     this.workorderno = workorderno; }
/*     */ 
/*     */   public String getWorkordersubno() {
/* 295 */     return this.workordersubno; }
/*     */ 
/*     */   public void setWorkordersubno(String workordersubno) {
/* 298 */     this.workordersubno = workordersubno;
/*     */   }
/*     */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.point.pojo.PointEventInfoBean
 * JD-Core Version:    0.5.3
 */