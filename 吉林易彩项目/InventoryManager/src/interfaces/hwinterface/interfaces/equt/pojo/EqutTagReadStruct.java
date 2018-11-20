/*     */ package interfaces.hwinterface.interfaces.equt.pojo;
/*     */ 
/*     */ import base.util.CommonUtil;
/*     */ import interfaces.hwinterface.interfaceUtil.pojo.DataStruct;
/*     */ import manage.point.pojo.PointEventInfoBean;
/*     */ import manage.point.pojo.PointInfoBean;
/*     */ 
/*     */ public class EqutTagReadStruct extends DataStruct
/*     */ {
/*     */   public EqutTagReadStruct()
/*     */   {
/*     */   }
/*     */ 
/*     */   public EqutTagReadStruct(short u32bdg, short u32dev, short p)
/*     */   {
/*  27 */     this.head = 21930;
/*  28 */     this.u32bdg = u32bdg;
/*  29 */     this.u32dev = u32dev;
/*  30 */     this.cmd = 11;
/*  31 */     this.p1 = p;
/*  32 */     this.crc = calCRC();
/*     */   }
/*     */ 
/*     */   public EqutTagReadStruct(PointInfoBean point)
/*     */   {
/*  41 */     String pid = point.getPid();
/*  42 */     this.head = 21930;
/*  43 */     this.u32bdg = Integer.parseInt(pid.substring(0, 2));
/*  44 */     this.u32dev = Integer.parseInt(pid.substring(2, 4));
/*  45 */     this.cmd = 11;
/*  46 */     this.p1 = (short)Integer.parseInt(pid.substring(4, 6));
/*  47 */     this.crc = calCRC();
/*     */   }
/*     */ 
/*     */   public EqutTagReadStruct(PointEventInfoBean point) {
/*  51 */     String pid = point.getPid();
/*  52 */     this.head = 21930;
/*  53 */     this.u32bdg = Integer.parseInt(pid.substring(0, 2));
/*  54 */     this.u32dev = Integer.parseInt(pid.substring(2, 4));
/*  55 */     this.cmd = 11;
/*  56 */     this.p1 = (short)Integer.parseInt(pid.substring(4, 6));
/*  57 */     this.crc = calCRC();
/*     */   }
/*     */ 
/*     */   public String[] getFibercode()
/*     */     throws Exception
/*     */   {
/*  65 */     String[] str = { "", "" };
/*     */ 
/*  67 */     String codeInFiber = new String(this.datload, 3, 32, "utf-8");
/*  68 */     String codeInPoint = new String(this.datload, 38, 32, "utf-8");
/*  69 */     str[0] = CommonUtil.bytesToHexString(codeInFiber.getBytes());
/*  70 */     str[1] = CommonUtil.bytesToHexString(codeInPoint.getBytes());
/*  71 */     return str;
/*     */   }
/*     */ 
/*     */   public int analyzePointAlarmState(int state) throws Exception {
/*  75 */     String tag = new String(this.datload, 3, 32, "utf-8");
/*  76 */     String point = new String(this.datload, 38, 32, "utf-8");
/*     */ 
/*  78 */     byte[] f1 = tag.getBytes();
/*  79 */     byte[] f2 = point.getBytes();
/*     */ 
/*  81 */     String[] str = { "", "" };
/*  82 */     str[0] = CommonUtil.bytesToHexString(f1);
/*  83 */     str[1] = CommonUtil.bytesToHexString(f2);
/*     */ 
/*  85 */     if (state == 2) {
/*  86 */       if ((f2 == null) || (f2[1] == 0)) {
/*  87 */         return 0;
/*     */       }
/*  89 */       return 2;
/*     */     }
/*  91 */     if (state == 4) {
/*  92 */       if ((f1 == null) || (f1[1] == 0)) {
/*  93 */         return 3;
/*     */       }
/*  95 */       if ((f2 == null) || (f2[1] == 0)) {
/*  96 */         return 4;
/*     */       }
/*  98 */       if (str[0].equals(str[1])) {
/*  99 */         return 1;
/*     */       }
/* 101 */       return 5;
/*     */     }
/*     */ 
/* 106 */     return 0;
/*     */   }
/*     */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     interfaces.hwinterface.interfaces.equt.pojo.EqutTagReadStruct
 * JD-Core Version:    0.5.3
 */