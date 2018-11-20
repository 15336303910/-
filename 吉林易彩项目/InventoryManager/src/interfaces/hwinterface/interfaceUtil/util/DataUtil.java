package interfaces.hwinterface.interfaceUtil.util;

import base.util.CommonUtil;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class DataUtil
{
  public static int[][] forMACList(byte[] bytes)
  {
    int[][] temp = new int[12][6];
    ByteBuffer byteb = ByteBuffer.wrap(bytes);

    for (int i = 0; i < 12; ++i)
    {
      for (int j = 0; j < 6; ++j)
      {
        byteb.position(48 + i * 6 * 4 + j * 4);
        temp[i][j] = byteb.asIntBuffer().get();
      }
    }
    byteb.position(336);
    byte[] tempByte = new byte[144];
    System.arraycopy(byteb.array(), 336, tempByte, 0, 144);
    String tempStr = ValueUtil.bytesToHexString(tempByte);

    for (int i = 0; i < 12; ++i)
    {
      for (int j = 0; j < 6; ++j)
      {
        if (temp[i][j] == 0)
          continue;
        String temps = tempStr.substring((i * 6 + j) * 4, (i * 6 + j) * 4 + 4);

        String ta = temps.substring(0, 2);
        String tb = temps.substring(2, 4);
        temps = tb + ta;
        int tempType = Integer.parseInt(temps.toLowerCase(), 16);
        int sun = 1;

        for (int q = 0; q < 12; ++q)
        {
          int state = tempType & (int)Math.pow(2.0D, q);

          if (state == 0) {
            ++sun;
          }
          else {
            ++sun;
          }
        }
      }
    }
    return temp;
  }

  public static boolean matchFibercode(String fibercode1, String fibercode2) {
    boolean match = true;
    boolean bothFiber = (CommonUtil.getFibercodeType(fibercode1) == 1) && (CommonUtil.getFibercodeType(fibercode2) == 1);
    if (!(bothFiber)) {
      return false;
    }
    byte[] code1 = CommonUtil.hexStringToByte(fibercode1);
    byte[] code2 = CommonUtil.hexStringToByte(fibercode2);
    for (int i = 2; i < 18; ++i) {
      if (code1[i] != code2[i])
        match = false;
    }
    return match;
  }
}