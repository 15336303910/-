package interfaces.hwinterface.interfaceUtil.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ValueUtil
{
  public static byte[] hexStringToByte(String hex)
  {
    int len = hex.length() / 2;
    byte[] result = new byte[len];
    char[] achar = hex.toUpperCase().toCharArray();
    for (int i = 0; i < len; ++i) {
      int pos = i * 2;
      result[i] = (byte)(toByte(achar[pos]) << 4 | toByte(achar[(pos + 1)]));
    }
    return result;
  }

  private static byte toByte(char c) {
    byte b = (byte)"0123456789ABCDEF".indexOf(c);
    return b;
  }

  public static final String bytesToHexString(byte[] bArray)
  {
    if (bArray == null)
      return "";
    StringBuffer sb = new StringBuffer(bArray.length);

    for (int i = 0; i < bArray.length; ++i) {
      String sTemp = Integer.toHexString(0xFF & bArray[i]);
      if (sTemp.length() < 2)
        sb.append(0);
      sb.append(sTemp.toUpperCase());
    }
    return sb.toString();
  }

  public static final String shortToHexString(short ss)
  {
    ByteBuffer mb = ByteBuffer.allocate(2);
    mb.asShortBuffer().put(ss);
    return bytesToHexString(mb.array());
  }

  public static final Object bytesToObject(byte[] bytes)
    throws IOException, ClassNotFoundException
  {
    ByteArrayInputStream in = new ByteArrayInputStream(bytes);
    ObjectInputStream oi = new ObjectInputStream(in);
    Object o = oi.readObject();
    oi.close();
    return o;
  }

  public static final byte[] objectToBytes(Serializable s)
    throws IOException
  {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ObjectOutputStream ot = new ObjectOutputStream(out);
    ot.writeObject(s);
    ot.flush();
    ot.close();
    return out.toByteArray();
  }

  public static final String objectToHexString(Serializable s)
    throws IOException
  {
    return bytesToHexString(objectToBytes(s));
  }

  public static final Object hexStringToObject(String hex)
    throws IOException, ClassNotFoundException
  {
    return bytesToObject(hexStringToByte(hex));
  }

  public static String bcd2Str(byte[] bytes)
  {
    StringBuffer temp = new StringBuffer(bytes.length * 2);

    for (int i = 0; i < bytes.length; ++i) {
      temp.append((byte)((bytes[i] & 0xF0) >>> 4));
      temp.append((byte)(bytes[i] & 0xF));
    }
    return ((temp.toString().substring(0, 1).equalsIgnoreCase("0")) ? temp
      .toString().substring(1) : temp.toString());
  }

  public static byte[] str2Bcd(String asc)
  {
    int len = asc.length();
    int mod = len % 2;

    if (mod != 0) {
      asc = "0" + asc;
      len = asc.length();
    }

    byte[] abt = new byte[len];
    if (len >= 2) {
      len /= 2;
    }

    byte[] bbt = new byte[len];
    abt = asc.getBytes();

    for (int p = 0; p < asc.length() / 2; ++p)
    {
      int j;
      if ((abt[(2 * p)] >= 48) && (abt[(2 * p)] <= 57))
        j = abt[(2 * p)] - 48;
      else if ((abt[(2 * p)] >= 97) && (abt[(2 * p)] <= 122))
        j = abt[(2 * p)] - 97 + 10;
      else
        j = abt[(2 * p)] - 65 + 10;
      int k;
      if ((abt[(2 * p + 1)] >= 48) && (abt[(2 * p + 1)] <= 57))
        k = abt[(2 * p + 1)] - 48;
      else if ((abt[(2 * p + 1)] >= 97) && (abt[(2 * p + 1)] <= 122))
        k = abt[(2 * p + 1)] - 97 + 10;
      else {
        k = abt[(2 * p + 1)] - 65 + 10;
      }

      int a = (j << 4) + k;
      byte b = (byte)a;
      bbt[p] = b;
    }
    return bbt;
  }

  public static String MD5EncodeToHex(String origin)
  {
    return bytesToHexString(MD5Encode(origin));
  }

  public static byte[] MD5Encode(String origin)
  {
    return MD5Encode(origin.getBytes());
  }

  public static byte[] MD5Encode(byte[] bytes)
  {
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("MD5");
      return md.digest(bytes);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace(); }
    return new byte[0];
  }

  public static String transBinToHex(String str)
  {
    int len = str.length() / 4;
    String res = "";
    for (int i = 0; i < len; ++i) {
      char[] ch = new char[4];
      str.getChars(i * 4, i * 4 + 4, ch, 0);
      res = res + switchS(trans1(ch));
    }
    return res;
  }

  public static int trans1(char[] ch) {
    int sum = 0;
    for (int i = 0; i < 4; ++i) {
      int y = 8;
      if (ch[i] == '1') {
        for (int j = 1; j <= i; ++j) {
          y /= 2;
        }
        sum += y;
      }
    }
    return sum;
  }

  public static String switchS(int i) {
    String s = "";
    switch (i)
    {
    case 10:
      s = "a";
      break;
    case 11:
      s = "b";
      break;
    case 12:
      s = "c";
      break;
    case 13:
      s = "d";
      break;
    case 14:
      s = "e";
      break;
    case 15:
      s = "f";
      break;
    default:
      s = i+"";
    }
    return s;
  }

  public static String hexString2binaryString(String hexString)
  {
    if ((hexString == null) || (hexString.length() % 2 != 0))
      return null;
    String bString = "";
    for (int i = 0; i < hexString.length(); ++i) {
      String tmp = "0000" + 
        Integer.toBinaryString(Integer.parseInt(
        hexString.substring(i, i + 1), 16));
      bString = bString + tmp.substring(tmp.length() - 4);
    }
    return bString;
  }

  public static void copyValueToBean(Object obj, Object toObj)
  {
    if ((obj == null) || (toObj == null))
      return;
    Class classz = obj.getClass();
    Class classTo = toObj.getClass();

    Field[] fields = classz.getDeclaredFields();
    for (Field field : fields)
      try
      {
        String name = field.getName();
        Type type = field.getGenericType();

        Field[] fieldsTo = classTo.getDeclaredFields();
        for (Field fieldTo : fieldsTo) {
          String nameTo = fieldTo.getName();
          Type typeTo = fieldTo.getGenericType();

          if ((!(name.equals(nameTo))) || (!(type.toString().equals(typeTo.toString())))) {
            continue;
          }
          Method m = classz.getMethod("get" + change(name), new Class[0]);
          Object value = m.invoke(obj, new Object[0]);
          Method mtd;
          if (type.toString().equals("class java.util.Date"))
          {
            mtd = classTo.getMethod("set" + change(nameTo), new Class[] { Date.class });
            mtd.invoke(toObj, new Object[] { value });
          } else if (type.toString().equals("int"))
          {
            mtd = classTo.getMethod("set" + change(nameTo), new Class[] { Integer.TYPE });
            mtd.invoke(toObj, new Object[] { value });
          } else if (type.toString().equals("class java.util.List"))
          {
            mtd = classTo.getMethod("set" + change(nameTo), new Class[] { List.class });
            mtd.invoke(toObj, new Object[] { value });
          } else if (type.toString().equals("class java.util.ArrayList"))
          {
            mtd = classTo.getMethod("set" + change(nameTo), new Class[] { ArrayList.class });
            mtd.invoke(toObj, new Object[] { value });
          }
          else {
            mtd = classTo.getMethod("set" + change(nameTo), new Class[] { String.class });
            mtd.invoke(toObj, new Object[] { value });
          }

        }

      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
  }

  public static String change(String src)
  {
    if (src != null) {
      StringBuffer sb = new StringBuffer(src);
      sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
      return sb.toString();
    }
    return null;
  }
}