package base.util;


import base.util.pojo.Point;

/**
 * 地图偏移量
 * @author chenqp
 *
 */
public class MapUtil {
	private static final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    
    private static final double pi = 3.14159265358979324;
    private static final double a = 6378245.0;
    private static final double ee = 0.00669342162296594323;
    private  static String gpsType ="gcj";//北京为gcj 贵州为wgs
    
    /**
     * wgs转换百度地图
     * @param lat
     * @param lon
     * @return
     */
    public static Point wgs_bd_encrypt(double lat,double lon){
    	Point point=new Point();
    	//WGS-GCG
    	point = MapUtil.wgs_gcj_encrypts(lat, lon);
    	//GCG-BD09
    	point = MapUtil.google_bd_encrypt(point.getLat(), point.getLng());
    	return point;
    }
    
    /**
     * 百度地图转wgs
     * @param lat
     * @param lon
     * @return
     */
    public static Point bd_wgs_encrypt(double lat,double lon){
    	Point point = new Point();
    	//百度地图转 gcg
    	point = MapUtil.bd_google_encrypt(lat, lon);
    	//gcg 转 wgs
    	point = MapUtil.gcj_wgs_encrypts(point.getLng(), point.getLat());
    	return point;
    }
    
    /**
     * 将手机端地图转
     * 换成数据库需要
     * 的地图
     * @param lat
     * @param lon
     * @return
     */
    public static Point phone_db_encrypt(double lat,double lon){
    	Point point = new Point();
    /*	GetProperties properties = new GetProperties();
    	if(gpsType.equals("wgs")){
    		point.setLat(lat);
    		point.setLng(lon);
    		
    	}else if(gpsType.equals("gcj")){
    	point = MapUtil.gcj_wgs_encrypts(lat, lon);
    
    		
    	}else{
    		point.setLat(lat);
    		point.setLng(lon);
    	}*/
    	
    	point = MapUtil.gcj_wgs_encrypts(lat, lon);
    	return point ;
    }
    
    /**
     * 网页地图转成数据库
     * @param lat
     * @param lon
     * @return
     */
    public static Point web_db_encrypt(double lat,double lon){
    	Point point = new Point();
    	if(gpsType.equals("jcg")){
    		point = MapUtil.gcj_wgs_encrypts(lon, lat);
    	}else{
    		point.setLat(lat);
    		point.setLng(lon);
    	}
    	return point;
    }
    
    /**
     * 将数据库信息
     * 转换成百度地图
     * @param lat
     * @param lon
     * @return
     */
    public static Point db_phone_encrypt(double lat,double lon){
    	Point point = new Point();
    	/*if(gpsType.equals("wgs")){
    		point = MapUtil.wgs_gcj_encrypts(lat, lon);
    	}else if(gpsType.equals("gcj")){
    	//	point = MapUtil.google_bd_encrypt(lat, lon);
    		point.setLat(lat);
    		point.setLng(lon);
    		
    	}else{
    		point.setLat(lat);
    		point.setLng(lon);
    	}*/
    	point = MapUtil.wgs_gcj_encrypts(lat, lon);
    	return point;
    }
    
    
    /**
     * 将数据库数据
     * 转成网页地图
     * @param lat
     * @param lon
     * @return
     */
    public static Point db_web_encrypt(double lat,double lon){
    	Point point = new Point();
    	if(gpsType.equals("wgs")){
    		point = MapUtil.wgs_gcj_encrypts(lat, lon);
    	}else{
    		point.setLat(lat);
    		point.setLng(lon);
    	}
    	return point;
    }
     
    /**
     * gg_lat 纬度
     * gg_lon 经度
     * GCJ-02转换BD-09
     * Google地图经纬度转百度地图经纬度
     * */
    public static Point google_bd_encrypt(double gg_lat, double gg_lon){
        Point point=new Point();
        double x = gg_lon, y = gg_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi); 
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        point.setLat(bd_lat);
        point.setLng(bd_lon);
        return point;
    }
     
    /**
     * wgLat 纬度
     * wgLon 经度
     * BD-09转换GCJ-02
     * 百度转google
     * */
    public static Point bd_google_encrypt(double bd_lat, double bd_lon){
        Point point=new Point();
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;  
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);  
        double theta =Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);  
        double gg_lon = z * Math.cos(theta);  
        double gg_lat = z * Math.sin(theta);  
        point.setLat(gg_lat);
        point.setLng(gg_lon);
       gpsType="gcj";
        return point;
    }
     
     
     
    /**
     * wgLat 纬度
     * wgLon 经度
     * WGS-84 到 GCJ-02 的转换（即 GPS 加偏）
     * */
    public static Point wgs_gcj_encrypts(double wgLat, double wgLon) {
        Point point=new Point();
  
        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double lat = wgLat + dLat;
        double lon = wgLon + dLon;
        point.setLat(lat);
        point.setLng(lon);
        gpsType="gcj";
        return point;
      
    }
    

    
    
    /**
     * gcj转wgs
     * @param lng
     * @param lat
     * @return
     */
    public static Point gcj_wgs_encrypts(double lat,double lng){
    	Point point = new Point();
		double dlat = transformLat(lng - 105.0, lat - 35.0);
	    double dlng = transformLon(lng - 105.0, lat - 35.0);
	    double radlat = lat / 180.0 * pi;
	    double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * pi);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * pi);
        double mglat = lat + dlat;
        double mglng = lng + dlng;
        point.setLat(lat*2-mglat);
        point.setLng(lng *2 - mglng);
        
        gpsType="wgs";
    	return point;
    }
     
     
    public static void transform(double wgLat, double wgLon, double[] latlng) {
        if (outOfChina(wgLat, wgLon)) {
            latlng[0] = wgLat;
            latlng[1] = wgLon;
            return;
        }
        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        latlng[0] = wgLat + dLat;
        latlng[1] = wgLon + dLon;
    }
 
    private static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347){
        	 return true;
        }else if(lat < 0.8293 || lat > 55.8271){
        	return true;
        }else{
        	return false;
        }
    }
 
    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }
 
    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }
    
    
    /** 
     * 计算地球上任意两点(经纬度)距离 
     *  
     * @param long1 
     *            第一点经度 
     * @param lat1 
     *            第一点纬度 
     * @param long2 
     *            第二点经度 
     * @param lat2 
     *            第二点纬度 
     * @return 返回距离 单位：米 
     */  
    public static String Distance(double long1, double lat1, double long2,  
            double lat2) {  
        double a, b, R;  
        R = 6378137; // 地球半径  
        lat1 = lat1 * Math.PI / 180.0;  
        lat2 = lat2 * Math.PI / 180.0;  
        a = lat1 - lat2;  
        b = (long1 - long2) * Math.PI / 180.0;  
        double d;  
        double sa2, sb2;  
        sa2 = Math.sin(a / 2.0);  
        sb2 = Math.sin(b / 2.0);  
        d = 2  
                * R  
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)  
                        * Math.cos(lat2) * sb2 * sb2));  
        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
        return df.format(d);  
    }  
    
    /**
     * 求三个点之间形成的夹角
     * @param pa 第一个点
     * @param pb 中间点
     * @param pc 第三个点
     * @return
     */
    public static double getAngle(Point pa,Point pb,Point pc){
    	java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
    	double dsx ,dsy,dex ,dey ;
		double angle;
		dsx = pa.getLng()- pb.getLng(); 
		dsy = pa.getLat() - pb.getLat(); 
		dex = pc.getLng()- pb.getLng(); 
		dey = pc.getLat() - pb.getLat(); 
	 
		double cosfi = dsx * dex + dsy * dey;
		double norm = (dsx * dsx + dsy * dsy) * (dex * dex + dey * dey);
        cosfi /= Math.sqrt(norm);
        
        if (cosfi >= 1.0) {
        	angle = 0;
        }
        if(cosfi <= -1.0){
        	angle = Math.PI;
        }
	    double fi = Math.acos(cosfi);
	    if(180 * fi /Math.PI <180){
	    	angle = 180 * fi /Math.PI;
	    }else{
	    	angle = 360 - 180 * fi /Math.PI;
	    }
	    return Double.parseDouble(df.format(angle));
    }
    
    /**
     * 求两个点之间的斜率
     * @param a
     * @param b
     * @return
     */
    public static double[] getDegree (Point a, Point b )
    {
        double k = Math.abs (( b.getLat()- a.getLat() ) / ( b.getLng() - a.getLng() ));
        return new double[] { Math.toDegrees (Math.atan (k)), 360 - Math.toDegrees (Math.atan (k)) };
    }
    
    public static void main(String[] args) {
    	//102.743218	25.094005
		Point point = MapUtil.bd_wgs_encrypt(26.0160170,105.6887800);
		System.out.println(point.getLng());
		System.out.println(point.getLat());
    }
}
