/*    */ package interfaces.hwinterface.interfaces.equt.pojo;
/*    */ 
/*    */ import interfaces.hwinterface.interfaceUtil.pojo.DataStruct;
/*    */ import manage.point.pojo.PointInfoBean;
/*    */ 
/*    */ public class EqutTagUnBundingStruct extends DataStruct
/*    */ {
/*    */   public EqutTagUnBundingStruct()
/*    */   {
/*    */   }
/*    */ 
/*    */   public EqutTagUnBundingStruct(PointInfoBean point)
/*    */   {
/* 16 */     String pid = point.getPid();
/* 17 */     this.head = 21930;
/* 18 */     this.u32bdg = Integer.parseInt(pid.substring(0, 2));
/* 19 */     this.u32dev = Integer.parseInt(pid.substring(2, 4));
/* 20 */     this.cmd = 12;
/* 21 */     this.p1 = (short)Integer.parseInt(pid.substring(4, 6));
/* 22 */     this.p2 = 2;
/* 23 */     this.p3 = 1;
/* 24 */     this.ln = 0;
/* 25 */     this.crc = calCRC();
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     interfaces.hwinterface.interfaces.equt.pojo.EqutTagUnBundingStruct
 * JD-Core Version:    0.5.3
 */