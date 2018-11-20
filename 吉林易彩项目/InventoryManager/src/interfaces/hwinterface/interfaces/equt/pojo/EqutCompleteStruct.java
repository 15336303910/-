/*    */ package interfaces.hwinterface.interfaces.equt.pojo;
/*    */ 
/*    */ import interfaces.hwinterface.interfaceUtil.pojo.DataStruct;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ 
/*    */ public class EqutCompleteStruct extends DataStruct
/*    */ {
/*    */   public EqutCompleteStruct()
/*    */   {
/*  9 */     this.head = 21930;
/* 10 */     this.cmd = 25;
/* 11 */     this.ln = 0;
/* 12 */     this.crc = calCRC();
/*    */   }
/*    */ 
/*    */   public int[][] parseDevmap() {
/* 16 */     int[][] temp = new int[12][6];
/* 17 */     ByteBuffer data = ByteBuffer.wrap(this.datload);
/* 18 */     for (int i = 0; i < 12; ++i)
/*    */     {
/* 20 */       for (int j = 0; j < 6; ++j)
/*    */       {
/* 22 */         data.position(i * 6 * 4 + j * 4);
/* 23 */         temp[i][j] = data.asIntBuffer().get();
/*    */       }
/*    */     }
/* 26 */     return temp;
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     interfaces.hwinterface.interfaces.equt.pojo.EqutCompleteStruct
 * JD-Core Version:    0.5.3
 */