/*    */ package interfaces.hwinterface.interfaces.equt.pojo;
/*    */ 
/*    */ import interfaces.hwinterface.interfaceUtil.pojo.DataStruct;
/*    */ import manage.point.pojo.PointInfoBean;
/*    */ 
/*    */ public class EqutTagOnLineStruct extends DataStruct
/*    */ {
/*    */   public EqutTagOnLineStruct()
/*    */   {
/*    */   }
/*    */ 
/*    */   public EqutTagOnLineStruct(short u32bdg, short u32dev, short p)
/*    */   {
/* 21 */     this.head = 21930;
/* 22 */     this.u32bdg = u32bdg;
/* 23 */     this.u32dev = u32dev;
/* 24 */     this.cmd = 10;
/* 25 */     this.p1 = p;
/* 26 */     this.crc = calCRC();
/*    */   }
/*    */ 
/*    */   public EqutTagOnLineStruct(PointInfoBean point)
/*    */   {
/* 35 */     String pid = point.getPid();
/* 36 */     this.head = 21930;
/* 37 */     this.u32bdg = Integer.parseInt(pid.substring(0, 2));
/* 38 */     this.u32dev = Integer.parseInt(pid.substring(2, 4));
/* 39 */     this.cmd = 10;
/* 40 */     this.p1 = (short)Integer.parseInt(pid.substring(4, 6));
/* 41 */     this.crc = calCRC();
/*    */   }
/*    */ 
/*    */   public Boolean hasJumpFiber()
/*    */   {
/* 46 */     return Boolean.valueOf(true);
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     interfaces.hwinterface.interfaces.equt.pojo.EqutTagOnLineStruct
 * JD-Core Version:    0.5.3
 */