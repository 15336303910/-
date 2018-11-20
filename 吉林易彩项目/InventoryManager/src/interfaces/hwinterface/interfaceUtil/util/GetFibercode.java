package interfaces.hwinterface.interfaceUtil.util;

import base.util.CommonUtil;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetFibercode
{
  private String rand = "";

  public GetFibercode()
  {
    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    this.rand = sdFormat.format(new Date());
  }

  public byte[] WF()
  {
    return m(1, (byte)1, (byte)0);
  }

  public Object[] TF()
  {
    Object[] temp = { m(2, (byte)2, (byte)0), m(2, (byte)1,(byte) 0) };
    return temp;
  }

  public Object[] FF(int size)
  {
    Object[] temp = new Object[size + 2];
    temp[0] = m(4, (byte)1, (byte)2);
    temp[1] = m(4, (byte)2, (byte)2);
    for (int i = 0; i < size; ++i)
    {
      temp[(i + 2)] = m(5, (byte)2, (byte)i);
    }
    return temp;
  }

  private byte[] m(int type, byte index, byte num)
  {
    byte[] byt = new byte[32];
    byt[0] = 1;
    for (int i = 0; i < this.rand.length(); ++i)
    {
      byt[(i + 2)] = (byte)Integer.parseInt(this.rand.charAt(i)+"");
    }
    byt[24] = 0;
    byt[25] = 0;
    byt[26] = 0;
    byt[27] = 0;
    byt[28] = 0;
    byt[29] = 0;
    byt[30] = 0;
    switch (type)
    {
    case 1:
      byt[1] = 2;
      byt[21] = 1;
      byt[22] = 1;
      byt[23] = 1;
      break;
    case 2:
      byt[1] = 1;
      byt[21] = index;
      byt[22] = 1;
      byt[23] = 1;
      break;
    case 4:
      byt[1] = 68;
      byt[21] = index;
      byt[22] = 2;
      byt[23] = num;
      break;
    case 5:
      byt[1] = -124;
      byt[21] = index;
      byt[22] = 2;
      byt[23] = num;
    case 3:
    }
    byt[31] = Crc8.calcCrc8(byt);
    return byt;
  }

  public String[] getJumpFiberCode() {
    String[] fibercodes = { "", "" };
    Object[] temp = TF();
    byte[] code1 = (byte[])temp[0];
    byte[] code2 = (byte[])temp[1];
    fibercodes[0] = CommonUtil.bytesToHexString(code1);
    fibercodes[1] = CommonUtil.bytesToHexString(code2);
    return fibercodes;
  }

  public String getTailFiberCode() {
    String fibercode = "";
    Object temp = WF();
    byte[] code = (byte[])temp;
    fibercode = CommonUtil.bytesToHexString(code);
    return fibercode;
  }

  public String[] getOsFiberCode(int size) {
    String[] fibercodes = new String[size + 2];
    Object[] temp = FF(size);
    for (int i = 0; i < size + 2; ++i) {
      fibercodes[i] = CommonUtil.bytesToHexString((byte[])temp[i]);
    }
    return fibercodes;
  }
}