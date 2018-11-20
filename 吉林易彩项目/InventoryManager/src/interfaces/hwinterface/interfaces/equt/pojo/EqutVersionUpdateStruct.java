/*    */ package interfaces.hwinterface.interfaces.equt.pojo;
/*    */ 
/*    */ import base.util.CommonUtil;
/*    */ import interfaces.hwinterface.interfaceUtil.pojo.DataStruct;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class EqutVersionUpdateStruct extends DataStruct
/*    */ {
/*    */   public EqutVersionUpdateStruct()
/*    */   {
/* 11 */     this.head = 21930;
/* 12 */     this.u32bdg = 1;
/* 13 */     this.u32dev = 1;
/* 14 */     this.cmd = 6;
/* 15 */     this.p1 = 270;
/* 16 */     this.p2 = 0;
/* 17 */     this.ln = 1024;
/* 18 */     this.crc = calCRC();
/*    */   }
/*    */ 
/*    */   public EqutVersionUpdateStruct(String vision) {
/* 22 */     this.head = 21930;
/* 23 */     this.u32bdg = 1;
/* 24 */     this.u32dev = 1;
/* 25 */     this.cmd = 6;
/* 26 */     this.p1 = 270;
/* 27 */     this.p2 = 0;
/*    */ 
/* 29 */     String data = vision + "," + CommonUtil.getUuid();
/* 30 */     int strLen = data.length();
/* 31 */     ByteBuffer mbuf = ByteBuffer.allocate(strLen * 2);
/* 32 */     mbuf.put(data.getBytes());
/* 33 */     this.datload = mbuf.array();
/* 34 */     this.ln = (short)this.datload.length;
/* 35 */     this.crc = calCRC();
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     interfaces.hwinterface.interfaces.equt.pojo.EqutVersionUpdateStruct
 * JD-Core Version:    0.5.3
 */