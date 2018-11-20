package interfaces.hwinterface.interfaceUtil.util;

public final class CRCUtil
{
  static CRC16 crc16 = new CRC16();

  static byte[] short2bytes(short s)
  {
    byte[] bytes = new byte[2];
    for (int i = 1; i >= 0; --i) {
      bytes[i] = (byte)(s % 256);
      s = (short)(s >> 8);
    }
    return bytes;
  }

  static short bytes2short(byte[] bytes) {
    short s = (short)(bytes[1] & 0xFF);
    s = (short)(s | bytes[0] << 8 & 0xFF00);
    return s;
  }

  public static byte[] crc16Bytes(byte[] data)
  {
    return short2bytes(crc16Short(data));
  }

  public static short crc16Short(byte[] data)
  {
    return crc16.getCrc(data);
  }

  static class CRC16
  {
    private short[] crcTable = new short[256];
    private int gPloy = 4129;

    public CRC16() {
      computeCrcTable();
    }

    private short getCrcOfByte(int aByte) {
      int value = aByte << 8;
      for (int count = 7; count >= 0; --count) {
        if ((value & 0x8000) != 0)
          value = value << 1 ^ this.gPloy;
        else {
          value <<= 1;
        }
      }
      value &= 65535;
      return (short)value;
    }

    private void computeCrcTable()
    {
      for (int i = 0; i < 256; ++i)
        this.crcTable[i] = getCrcOfByte(i);
    }

    public short getCrc(byte[] data)
    {
      int crc = 0;
      int length = data.length;
      for (int i = 0; i < length; ++i) {
        crc = (crc & 0xFF) << 8 ^ 
          this.crcTable[(((crc & 0xFF00) >> 8 ^ data[i]) & 0xFF)];
      }
      crc &= 65535;
      return (short)crc;
    }
  }
}