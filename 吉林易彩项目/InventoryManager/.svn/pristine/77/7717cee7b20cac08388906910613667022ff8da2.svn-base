 package base.util;
 
 import java.text.ParsePosition;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Date;
 import javax.servlet.http.HttpServletRequest;
 
 public class functions
 {
   public static String bitand(String str1, String str2)
   {
     String tmpString = "";
     String tmpString1 = "";
 
     String aString = "";
     try
     {
       int l1 = str1.length();
       int l2 = str2.length();
 
       if (l1 != l2) return null;
 
       for (int i = 0; i < l1; ++i)
       {
         tmpString = str1.substring(i, i + 1);
         tmpString1 = str2.substring(i, i + 1);
 
         int int1 = Integer.parseInt(tmpString);
         int int2 = Integer.parseInt(tmpString1);
         int1 &= int2;
         aString = aString + Integer.toBinaryString(int1);
       }
 
     }
     catch (Exception localException)
     {
     }
 
     return aString;
   }
 
   public static Boolean getPower(String powString, String listString)
   {
     Boolean renBoolean = Boolean.valueOf(false);
     try
     {
       String[] temp = listString.split(",");
       for (int i = 0; i < temp.length; ++i)
       {
         if (!(powString.equals(temp[i].trim())))
           continue;
         renBoolean = Boolean.valueOf(true);
       }
     }
     catch (Exception localException)
     {
     }
 
      return renBoolean;
   }
 
   public static String formatDateStr(Date date)
   {
     String str = "";
     try
     {
       if (date == null)
       {
         date = new Date();
       }
       str = new SimpleDateFormat("yyyyMMdd'T'hhmmss").format(date);
     }
     catch (Exception localException)
     {
     }
     return str;
   }
 
   public static Date strToDate(String str, int type)
   {
     Date datetime = null;
     try
     {
       str = str.replaceAll("T", "");
       SimpleDateFormat formatter;
       ParsePosition pos;
       if (type == 1)
       {
         formatter = new SimpleDateFormat("yyyyMMddHHmmss");
         pos = new ParsePosition(0);
         datetime = formatter.parse(str, pos);
       }
       if (type == 2) {
         formatter = new SimpleDateFormat("yyyyMMddHHmmss");
         pos = new ParsePosition(0);
         datetime = formatter.parse(str, pos);
         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         datetime = df.parse(df.format(datetime));
       }
     }
     catch (Exception e) {
       datetime = new Date();
     }
 
      return datetime;
   }
 
   public static String getIpAddr(HttpServletRequest request)
   {
     String ip = request.getHeader("x-forwarded-for");
     if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
       ip = request.getHeader("Proxy-Client-IP");
     }
     if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
       ip = request.getHeader("WL-Proxy-Client-IP");
     }
     if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
       ip = request.getRemoteAddr();
     }
     return ip;
   }
 
   public static String binary64ToHex(String binaryStr)
   {
     String ren = "";
     int pos = 0;
     String tmp = "";
     try
     {
       if (binaryStr.length() == 64)
       {
         while (pos < binaryStr.length())
         {
           tmp = binaryStr.substring(pos, pos + 4);
           Integer a = Integer.valueOf(Integer.parseInt(tmp, 2));
           tmp = Integer.toHexString(a.intValue());
           ren = ren + tmp;
           pos += 4;
         }
 
       }
 
     }
     catch (Exception localException)
     {
     }
 
     return ren;
   }
 
   public static double[] getAround(double lat, double lon, int raidus)
   {
     Double latitude = Double.valueOf(lat);
     Double longitude = Double.valueOf(lon);
 
     Double degree = Double.valueOf(111293.63611111112D);
 
     double raidusMile = raidus;
 
     Double dpmLat = Double.valueOf(1.0D / degree.doubleValue());
 
     Double radiusLat = Double.valueOf(dpmLat.doubleValue() * raidusMile);
     Double minLat = Double.valueOf(latitude.doubleValue() - radiusLat.doubleValue());
     Double maxLat = Double.valueOf(latitude.doubleValue() + radiusLat.doubleValue());
 
     Double mpdLng = Double.valueOf(degree.doubleValue() * Math.cos(latitude.doubleValue() * 0.0174532925199433D));
     Double dpmLng = Double.valueOf(1.0D / mpdLng.doubleValue());
     Double radiusLng = Double.valueOf(dpmLng.doubleValue() * raidusMile);
     Double minLng = Double.valueOf(longitude.doubleValue() - radiusLng.doubleValue());
     Double maxLng = Double.valueOf(longitude.doubleValue() + radiusLng.doubleValue());
     return new double[] { minLat.doubleValue(), minLng.doubleValue(), maxLat.doubleValue(), maxLng.doubleValue() };
   }
 
   public static Date addDate(Date date, int days)
   {
     Date newDate = null;
     try
     {
       Calendar canlendar = Calendar.getInstance();
 
       canlendar.setTime(date);
       canlendar.add(5, days);
       newDate = canlendar.getTime();
     } catch (Exception e) {
       newDate = new Date();
     }
     return newDate;
   }
 
   public static String addRed(String str)
   {
     str = "<font color=\"#ff0000\">" + str + "</font>";
     return str;
   }
 
   public static String getPstatName(String pstat)
   {
     String str = "";
     if (pstat.equals("1"))
       str = "空闲";
     else if (pstat.equals("2"))
       str = "故障";
     else if (pstat.equals("3"))
       str = "可用";
     else if (pstat.equals("4"))
       str = "占用";
     else {
       str = "未知";
     }
     return str;
   }
  }
