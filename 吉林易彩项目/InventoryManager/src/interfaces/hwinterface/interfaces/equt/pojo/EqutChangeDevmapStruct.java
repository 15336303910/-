 package interfaces.hwinterface.interfaces.equt.pojo;
 
 import interfaces.hwinterface.interfaceUtil.pojo.DataStruct;
 import java.nio.ByteBuffer;
 import java.nio.IntBuffer;
 public class EqutChangeDevmapStruct extends DataStruct
 {
   public EqutChangeDevmapStruct()
   {
     this.head = 21930;
     this.cmd = 25;
     this.rand = 1;
     this.ln = 288;
     this.crc = calCRC();
   }
 
   public EqutChangeDevmapStruct(int[][] devmap) {
     this.head = 21930;
     this.cmd = 25;
     this.rand = 1;
     this.ln = 288;
 
     ByteBuffer mbuf = ByteBuffer.allocate(288);
     for (int i = 0; i < 12; ++i)
     {
       for (int j = 0; j < 6; ++j)
       {
         mbuf.position(i * 6 * 4 + j * 4);
         mbuf.asIntBuffer().put(devmap[i][j]);
       }
     }
     this.datload = mbuf.array();
     this.crc = calCRC();
   }
 }

