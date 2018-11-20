/*     */ package manage.statistics.pojo;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class WorkOrderStatisticsBean
/*     */ {
/*     */   private Integer type1;
/*     */   private Integer type2;
/*     */   private Integer type3;
/*     */   private Integer type4;
/*     */   private Integer type5;
/*     */   private Integer type6;
/*     */   private Integer type7;
/*     */   private Integer s0;
/*     */   private Integer s1;
/*     */   private Integer s2;
/*     */   private Integer s3;
/*     */   private Integer s4;
/*     */   private Integer s5;
/*     */   private Integer s6;
/*     */   private Integer s7;
/*     */   private Integer overtime;
/*     */   private Integer point;
/*     */   private Integer equt;
/*     */   private Integer fiber;
/*     */   private Integer s12;
/*     */   private Integer domainid;
/*     */   private String areaname;
/*     */   private String areano;
/*     */   private String username;
/*     */   private String realname;
/*     */   private String deptname;
/*     */   private List<WorkOrderStatisticsBean> orderlist;
/*     */   private Integer total;
/*     */   private Date btime;
/*     */   private Date etime;
/*     */ 
/*     */   public WorkOrderStatisticsBean()
/*     */   {
/*  43 */     this.type1 = Integer.valueOf(0);
/*  44 */     this.type2 = Integer.valueOf(0);
/*  45 */     this.type3 = Integer.valueOf(0);
/*  46 */     this.type4 = Integer.valueOf(0);
/*  47 */     this.type5 = Integer.valueOf(0);
/*  48 */     this.type6 = Integer.valueOf(0);
/*  49 */     this.type7 = Integer.valueOf(0);
/*  50 */     this.s0 = Integer.valueOf(0);
/*  51 */     this.s1 = Integer.valueOf(0);
/*  52 */     this.s2 = Integer.valueOf(0);
/*  53 */     this.s3 = Integer.valueOf(0);
/*  54 */     this.s4 = Integer.valueOf(0);
/*  55 */     this.s5 = Integer.valueOf(0);
/*  56 */     this.s6 = Integer.valueOf(0);
/*  57 */     this.s7 = Integer.valueOf(0);
/*  58 */     this.s12 = Integer.valueOf(0);
/*     */ 
/*  62 */     this.overtime = Integer.valueOf(0);
/*     */   }
/*     */ 
/*     */   public WorkOrderStatisticsBean addToTotal(WorkOrderStatisticsBean workOrderStatisticsBean) {
/*  66 */     this.type1 = Integer.valueOf(this.type1.intValue() + workOrderStatisticsBean.getType1().intValue());
/*  67 */     this.type2 = Integer.valueOf(this.type2.intValue() + workOrderStatisticsBean.getType2().intValue());
/*  68 */     this.type3 = Integer.valueOf(this.type3.intValue() + workOrderStatisticsBean.getType3().intValue());
/*  69 */     this.type4 = Integer.valueOf(this.type4.intValue() + workOrderStatisticsBean.getType4().intValue());
/*  70 */     this.type5 = Integer.valueOf(this.type5.intValue() + workOrderStatisticsBean.getType5().intValue());
/*  71 */     this.type6 = Integer.valueOf(this.type6.intValue() + workOrderStatisticsBean.getType6().intValue());
/*  72 */     this.type7 = Integer.valueOf(this.type7.intValue() + workOrderStatisticsBean.getType7().intValue());
/*  73 */     this.s0 = Integer.valueOf(this.s0.intValue() + workOrderStatisticsBean.getS0().intValue());
/*  74 */     this.s1 = Integer.valueOf(this.s1.intValue() + workOrderStatisticsBean.getS1().intValue());
/*  75 */     this.s2 = Integer.valueOf(this.s2.intValue() + workOrderStatisticsBean.getS2().intValue());
/*  76 */     this.s3 = Integer.valueOf(this.s3.intValue() + workOrderStatisticsBean.getS3().intValue());
/*  77 */     this.s4 = Integer.valueOf(this.s4.intValue() + workOrderStatisticsBean.getS4().intValue());
/*  78 */     this.s5 = Integer.valueOf(this.s5.intValue() + workOrderStatisticsBean.getS5().intValue());
/*  79 */     this.s6 = Integer.valueOf(this.s6.intValue() + workOrderStatisticsBean.getS6().intValue());
/*  80 */     this.s7 = Integer.valueOf(this.s7.intValue() + workOrderStatisticsBean.getS7().intValue());
/*  81 */     this.s12 = Integer.valueOf(this.s12.intValue() + workOrderStatisticsBean.getS12().intValue());
/*     */ 
/*  85 */     this.overtime = Integer.valueOf(this.overtime.intValue() + workOrderStatisticsBean.getOvertime().intValue());
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getS12()
/*     */   {
/*  91 */     return this.s12;
/*     */   }
/*     */ 
/*     */   public void setS12(Integer s12) {
/*  95 */     this.s12 = s12;
/*     */   }
/*     */ 
/*     */   public List<WorkOrderStatisticsBean> getOrderlist() {
/*  99 */     return this.orderlist;
/*     */   }
/*     */ 
/*     */   public void setOrderlist(List<WorkOrderStatisticsBean> orderlist) {
/* 103 */     this.orderlist = orderlist;
/*     */   }
/*     */ 
/*     */   public Integer getTotal() {
/* 107 */     return this.total;
/*     */   }
/*     */ 
/*     */   public void setTotal(Integer total) {
/* 111 */     this.total = total;
/*     */   }
/*     */ 
/*     */   public Integer getType5() {
/* 115 */     return this.type5;
/*     */   }
/*     */ 
/*     */   public void setType5(Integer type5) {
/* 119 */     this.type5 = type5;
/*     */   }
/*     */ 
/*     */   public Integer getType6() {
/* 123 */     return this.type6;
/*     */   }
/*     */ 
/*     */   public void setType6(Integer type6) {
/* 127 */     this.type6 = type6;
/*     */   }
/*     */ 
/*     */   public Integer getType7() {
/* 131 */     return this.type7;
/*     */   }
/*     */ 
/*     */   public void setType7(Integer type7) {
/* 135 */     this.type7 = type7;
/*     */   }
/*     */ 
/*     */   public Integer getType1() {
/* 139 */     return this.type1; }
/*     */ 
/*     */   public void setType1(Integer type1) {
/* 142 */     this.type1 = type1; }
/*     */ 
/*     */   public Integer getType2() {
/* 145 */     return this.type2; }
/*     */ 
/*     */   public void setType2(Integer type2) {
/* 148 */     this.type2 = type2; }
/*     */ 
/*     */   public Integer getType3() {
/* 151 */     return this.type3; }
/*     */ 
/*     */   public void setType3(Integer type3) {
/* 154 */     this.type3 = type3; }
/*     */ 
/*     */   public Integer getType4() {
/* 157 */     return this.type4; }
/*     */ 
/*     */   public void setType4(Integer type4) {
/* 160 */     this.type4 = type4; }
/*     */ 
/*     */   public Integer getS0() {
/* 163 */     return this.s0; }
/*     */ 
/*     */   public void setS0(Integer s0) {
/* 166 */     this.s0 = s0; }
/*     */ 
/*     */   public Integer getS1() {
/* 169 */     return this.s1; }
/*     */ 
/*     */   public void setS1(Integer s1) {
/* 172 */     this.s1 = s1; }
/*     */ 
/*     */   public Integer getS2() {
/* 175 */     return this.s2; }
/*     */ 
/*     */   public void setS2(Integer s2) {
/* 178 */     this.s2 = s2; }
/*     */ 
/*     */   public Integer getS3() {
/* 181 */     return this.s3; }
/*     */ 
/*     */   public void setS3(Integer s3) {
/* 184 */     this.s3 = s3; }
/*     */ 
/*     */   public Integer getS4() {
/* 187 */     return this.s4; }
/*     */ 
/*     */   public void setS4(Integer s4) {
/* 190 */     this.s4 = s4; }
/*     */ 
/*     */   public Integer getS5() {
/* 193 */     return this.s5; }
/*     */ 
/*     */   public void setS5(Integer s5) {
/* 196 */     this.s5 = s5; }
/*     */ 
/*     */   public Integer getS6() {
/* 199 */     return this.s6; }
/*     */ 
/*     */   public void setS6(Integer s6) {
/* 202 */     this.s6 = s6; }
/*     */ 
/*     */   public Integer getS7() {
/* 205 */     return this.s7; }
/*     */ 
/*     */   public void setS7(Integer s7) {
/* 208 */     this.s7 = s7; }
/*     */ 
/*     */   public Integer getDomainid() {
/* 211 */     return this.domainid; }
/*     */ 
/*     */   public void setDomainid(Integer domainid) {
/* 214 */     this.domainid = domainid; }
/*     */ 
/*     */   public String getAreaname() {
/* 217 */     return this.areaname; }
/*     */ 
/*     */   public void setAreaname(String areaname) {
/* 220 */     this.areaname = areaname; }
/*     */ 
/*     */   public Integer getOvertime() {
/* 223 */     return this.overtime; }
/*     */ 
/*     */   public void setOvertime(Integer overtime) {
/* 226 */     this.overtime = overtime; }
/*     */ 
/*     */   public Integer getPoint() {
/* 229 */     return this.point; }
/*     */ 
/*     */   public void setPoint(Integer point) {
/* 232 */     this.point = point; }
/*     */ 
/*     */   public Integer getEqut() {
/* 235 */     return this.equt; }
/*     */ 
/*     */   public void setEqut(Integer equt) {
/* 238 */     this.equt = equt; }
/*     */ 
/*     */   public Integer getFiber() {
/* 241 */     return this.fiber; }
/*     */ 
/*     */   public void setFiber(Integer fiber) {
/* 244 */     this.fiber = fiber; }
/*     */ 
/*     */   public String getAreano() {
/* 247 */     return this.areano; }
/*     */ 
/*     */   public void setAreano(String areano) {
/* 250 */     this.areano = areano;
/*     */   }
/*     */ 
/*     */   public Date getBtime() {
/* 254 */     return this.btime;
/*     */   }
/*     */ 
/*     */   public void setBtime(Date btime) {
/* 258 */     this.btime = btime;
/*     */   }
/*     */ 
/*     */   public Date getEtime() {
/* 262 */     return this.etime;
/*     */   }
/*     */ 
/*     */   public void setEtime(Date etime) {
/* 266 */     this.etime = etime;
/*     */   }
/*     */ 
/*     */   public String getUsername() {
/* 270 */     return this.username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String username) {
/* 274 */     this.username = username;
/*     */   }
/*     */ 
/*     */   public String getRealname() {
/* 278 */     return this.realname;
/*     */   }
/*     */ 
/*     */   public void setRealname(String realname) {
/* 282 */     this.realname = realname;
/*     */   }
/*     */ 
/*     */   public String getDeptname() {
/* 286 */     return this.deptname;
/*     */   }
/*     */ 
/*     */   public void setDeptname(String deptname) {
/* 290 */     this.deptname = deptname;
/*     */   }
/*     */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.statistics.pojo.WorkOrderStatisticsBean
 * JD-Core Version:    0.5.3
 */