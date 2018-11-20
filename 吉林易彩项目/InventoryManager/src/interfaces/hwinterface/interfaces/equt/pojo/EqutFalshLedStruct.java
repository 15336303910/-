/*    */ package interfaces.hwinterface.interfaces.equt.pojo;
/*    */ 
/*    */ import interfaces.hwinterface.interfaceUtil.pojo.DataStruct;
/*    */ import manage.point.pojo.PointInfoBean;
/*    */ 
/*    */ public class EqutFalshLedStruct extends DataStruct
/*    */ {
/*    */   private static final String TAG = "EqutFalshLedStruct Class";
/*    */ 
/*    */   public EqutFalshLedStruct()
/*    */   {
/*    */   }
/*    */ 
/*    */   public EqutFalshLedStruct(short u32bdg, short u32dev, short LED)
/*    */   {
/* 19 */     this.head = 21930;
/* 20 */     this.u32bdg = u32bdg;
/* 21 */     this.u32dev = u32dev;
/* 22 */     this.cmd = 1;
/* 23 */     this.p1 = 0;
/* 24 */     this.p2 = LED;
/* 25 */     this.crc = calCRC();
/*    */   }
/*    */ 
/*    */   public EqutFalshLedStruct(PointInfoBean point, short LED)
/*    */   {
/* 34 */     String pid = point.getPid();
/* 35 */     this.head = 21930;
/* 36 */     this.u32bdg = Integer.parseInt(pid.substring(0, 2));
/* 37 */     this.u32dev = Integer.parseInt(pid.substring(2, 4));
/* 38 */     this.cmd = 1;
/* 39 */     this.p1 = (short)Integer.parseInt(pid.substring(4, 6));
/* 40 */     this.p2 = LED;
/* 41 */     this.crc = calCRC();
/*    */   }
/*    */ 
/*    */   public EqutFalshLedStruct(String pid, short LED) {
/* 45 */     this.head = 21930;
/* 46 */     this.u32bdg = Integer.parseInt(pid.substring(0, 2));
/* 47 */     this.u32dev = Integer.parseInt(pid.substring(2, 4));
/* 48 */     this.cmd = 1;
/* 49 */     this.p1 = (short)Integer.parseInt(pid.substring(4, 6));
/* 50 */     this.p2 = LED;
/* 51 */     this.crc = calCRC();
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     interfaces.hwinterface.interfaces.equt.pojo.EqutFalshLedStruct
 * JD-Core Version:    0.5.3
 */