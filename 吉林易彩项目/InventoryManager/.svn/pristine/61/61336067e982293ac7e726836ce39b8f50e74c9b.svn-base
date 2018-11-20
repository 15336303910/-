 package base.util;
 
 public class PositionUtil
 {
   public static final String BAIDU_LBS_TYPE = "bd09ll";
   public static double pi = 3.141592653589793D;
   public static double a = 6378245.0D;
   public static double ee = 0.006693421622965943D;
 
   public static Gps gps84_To_Gcj02(double lat, double lon)
   {
     if (outOfChina(lat, lon)) {
       return null;
     }
     double dLat = transformLat(lon - 105.0D, lat - 35.0D);
     double dLon = transformLon(lon - 105.0D, lat - 35.0D);
     double radLat = lat / 180.0D * pi;
     double magic = Math.sin(radLat);
     magic = 1.0D - (ee * magic * magic);
     double sqrtMagic = Math.sqrt(magic);
     dLat = dLat * 180.0D / a * (1.0D - ee) / magic * sqrtMagic * pi;
     dLon = dLon * 180.0D / a / sqrtMagic * Math.cos(radLat) * pi;
     double mgLat = lat + dLat;
     double mgLon = lon + dLon;
     return new Gps(mgLat, mgLon);
   }
 
   public static Gps gcj_To_Gps84(double lat, double lon)
   {
     Gps gps = transform(lat, lon);
     double lontitude = lon * 2.0D - gps.getWgLon();
     double latitude = lat * 2.0D - gps.getWgLat();
     return new Gps(latitude, lontitude);
   }
 
   public static Gps gcj02_To_Bd09(double gg_lat, double gg_lon)
   {
     double x = gg_lon; double y = gg_lat;
     double z = Math.sqrt(x * x + y * y) + 2.E-005D * Math.sin(y * pi);
     double theta = Math.atan2(y, x) + 3.E-006D * Math.cos(x * pi);
     double bd_lon = z * Math.cos(theta) + 0.0065D;
     double bd_lat = z * Math.sin(theta) + 0.006D;
     return new Gps(bd_lat, bd_lon);
   }
 
   public static Gps bd09_To_Gcj02(double bd_lat, double bd_lon)
   {
     double x = bd_lon - 0.0065D; double y = bd_lat - 0.006D;
     double z = Math.sqrt(x * x + y * y) - (2.E-005D * Math.sin(y * pi));
     double theta = Math.atan2(y, x) - (3.E-006D * Math.cos(x * pi));
     double gg_lon = z * Math.cos(theta);
     double gg_lat = z * Math.sin(theta);
     return new Gps(gg_lat, gg_lon);
   }
 
   public static Gps mercator_To_Gps84(double mercator_x, double mercator_y) {
     double x = mercator_x / 20037508.34D * 180.0D;
     double y = mercator_y / 20037508.34D * 180.0D;
     y = 180.0D / pi * (2.0D * Math.atan(Math.exp(y * pi / 180.0D)) - (pi / 2.0D));
     return new Gps(x, y);
   }
 
   public static Gps Gps84_To_mercator(double lat, double lon) {
     double x = lat * 20037508.34D / 180.0D;
     double y = Math.log(Math.tan((90.0D + lon) * pi / 360.0D)) / pi / 180.0D;
     y = y * 20037508.34D / 180.0D;
     return new Gps(x, y);
   }
 
   public static Gps bd09_To_Gps84(double bd_lat, double bd_lon)
   {
     Gps gcj02 = bd09_To_Gcj02(bd_lat, bd_lon);
     Gps map84 = gcj_To_Gps84(gcj02.getWgLat(), 
       gcj02.getWgLon());
     return map84;
   }
 
   public static boolean outOfChina(double lat, double lon)
   {
     if ((lon < 72.004000000000005D) || (lon > 137.8347D)) {
       return true;
     }
     return ((lat < 0.8293D) || (lat > 55.827100000000002D));
   }
 
   public static Gps transform(double lat, double lon)
   {
     if (outOfChina(lat, lon)) {
       return new Gps(lat, lon);
     }
     double dLat = transformLat(lon - 105.0D, lat - 35.0D);
     double dLon = transformLon(lon - 105.0D, lat - 35.0D);
     double radLat = lat / 180.0D * pi;
     double magic = Math.sin(radLat);
     magic = 1.0D - (ee * magic * magic);
     double sqrtMagic = Math.sqrt(magic);
     dLat = dLat * 180.0D / a * (1.0D - ee) / magic * sqrtMagic * pi;
     dLon = dLon * 180.0D / a / sqrtMagic * Math.cos(radLat) * pi;
     double mgLat = lat + dLat;
     double mgLon = lon + dLon;
     return new Gps(mgLat, mgLon);
   }
 
   public static double transformLat(double x, double y) {
     double ret = -100.0D + 2.0D * x + 3.0D * y + 0.2D * y * y + 0.1D * x * y + 
       0.2D * Math.sqrt(Math.abs(x));
     ret += (20.0D * Math.sin(6.0D * x * pi) + 20.0D * Math.sin(2.0D * x * pi)) * 2.0D / 3.0D;
     ret += (20.0D * Math.sin(y * pi) + 40.0D * Math.sin(y / 3.0D * pi)) * 2.0D / 3.0D;
     ret += (160.0D * Math.sin(y / 12.0D * pi) + 320.0D * Math.sin(y * pi / 30.0D)) * 2.0D / 3.0D;
     return ret;
   }
 
   public static double transformLon(double x, double y) {
     double ret = 300.0D + x + 2.0D * y + 0.1D * x * x + 0.1D * x * y + 0.1D * 
       Math.sqrt(Math.abs(x));
     ret += (20.0D * Math.sin(6.0D * x * pi) + 20.0D * Math.sin(2.0D * x * pi)) * 2.0D / 3.0D;
     ret += (20.0D * Math.sin(x * pi) + 40.0D * Math.sin(x / 3.0D * pi)) * 2.0D / 3.0D;
 
     ret = ret + (150.0D * Math.sin(x / 12.0D * pi) + 300.0D * Math.sin(x / 30.0D * 
       pi)) * 
       2.0D / 
       3.0D;
     return ret;
   }
 
   public static String[] latlonTran(double d121, double d38) {
     Gps gps = new Gps(d38, d121);
     gps = bd09_To_Gps84(gps.getWgLat(), gps.getWgLon());
     double[] dou = { 0.0D, 0.0D };
     dou[0] = (gps.getWgLon() - 0.000274D + 0.00015D);
     dou[1] = (gps.getWgLat() + 0.000464D - 8.7E-005D);
     String lon = dou[0]+"";
     lon = lon.substring(0, lon.indexOf(46) + 7);
 
     String lat = dou[1]+"";
     lat = lat.substring(0, lat.indexOf(46) + 7);
     String[] str = { lon, lat };
     return str;
   }
   }
