/*    */ package interfaces.hwinterface.interfaces.equt.pojo;
/*    */ 
/*    */ import base.util.CommonUtil;
/*    */ import interfaces.hwinterface.interfaceUtil.pojo.DataStruct;
/*    */ import manage.point.pojo.PointInfoBean;
/*    */ 
/*    */ public class EqutTagBundingStruct extends DataStruct
/*    */ {
/*    */   public EqutTagBundingStruct()
/*    */   {
/*    */   }
/*    */ 
/*    */   public EqutTagBundingStruct(PointInfoBean point)
/*    */   {
/* 21 */     String pid = point.getPid();
/* 22 */     this.head = 21930;
/* 23 */     this.u32bdg = Integer.parseInt(pid.substring(0, 2));
/* 24 */     this.u32dev = Integer.parseInt(pid.substring(2, 4));
/* 25 */     this.cmd = 12;
/* 26 */     this.p1 = (short)Integer.parseInt(pid.substring(4, 6));
/* 27 */     this.p2 = 0;
/* 28 */     this.p3 = 0;
/* 29 */     this.ln = 32;
/* 31 */     this.crc = calCRC();
/*    */   }
/*    */ }