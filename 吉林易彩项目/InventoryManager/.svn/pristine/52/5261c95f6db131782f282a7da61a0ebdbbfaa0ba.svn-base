/*    */ package interfaces.hwinterface.interfaces.equt.pojo;
/*    */ 
/*    */ import interfaces.hwinterface.interfaceUtil.pojo.DataStruct;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ 
/*    */ public class EqutCurrentDevmapStruct extends DataStruct
/*    */ {
/*    */   public EqutCurrentDevmapStruct()
/*    */   {
/* 11 */     this.head = 21930;
/* 12 */     this.cmd = 3;
/* 13 */     this.rand = 0;
/* 14 */     this.ln = 0;
/* 15 */     this.crc = calCRC();
/*    */   }
/*    */ 
/*    */   public int[][] parseDevmap() {
/* 19 */     int[][] temp = new int[12][6];
/* 20 */     ByteBuffer data = ByteBuffer.wrap(this.datload);
/* 21 */     for (int i = 0; i < 12; ++i)
/*    */     {
/* 23 */       for (int j = 0; j < 6; ++j)
/*    */       {
/* 25 */         data.position(48 + i * 6 * 4 + j * 4);
/* 26 */         temp[i][j] = data.asIntBuffer().get();
/*    */       }
/*    */     }
/* 29 */     return temp;
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     interfaces.hwinterface.interfaces.equt.pojo.EqutCurrentDevmapStruct
 * JD-Core Version:    0.5.3
 */