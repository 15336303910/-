/*    */ package interfaces.hwinterface.interfaces.equt.pojo;
/*    */ 
/*    */ import interfaces.hwinterface.interfaceUtil.pojo.DataStruct;
/*    */ 
/*    */ public class EqutIpConfigStruct extends DataStruct
/*    */ {
/*    */   private static final String TAG = "EqutIpConfigStruct Class";
/*    */ 
/*    */   public EqutIpConfigStruct()
/*    */   {
/* 10 */     this.head = 21930;
/* 11 */     this.u32bdg = 0;
/* 12 */     this.u32dev = 0;
/* 13 */     this.cmd = 20;
/* 14 */     this.p1 = 0;
/* 15 */     this.p2 = 0;
/* 16 */     this.crc = calCRC();
/*    */   }
/*    */ 
/*    */   public void setEqutNewIp(String newIp) {
/* 20 */     if ((newIp != null) && (!("".equals(newIp)))) {
/* 21 */       byte[] ipByte = newIp.getBytes();
/* 22 */       this.datload = ipByte;
/* 23 */       this.ln = (short)ipByte.length;
/*    */     }
/*    */   }
/*    */ 
/*    */   public Boolean isModification() {
/* 28 */     if (this.fRet == 0) {
/* 29 */       return Boolean.valueOf(true);
/*    */     }
/* 31 */     return Boolean.valueOf(false);
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     interfaces.hwinterface.interfaces.equt.pojo.EqutIpConfigStruct
 * JD-Core Version:    0.5.3
 */