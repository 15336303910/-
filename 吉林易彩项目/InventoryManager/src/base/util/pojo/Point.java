package base.util.pojo;

/**
 * 用于构建地图中的坐标点
 * @author chenqp
 *
 */
public class Point {
    
    private double lat;// 纬度
    private double lng;// 经度
     
    public Point() {
    }
     
    public Point(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point bmapPoint = (Point) obj;
            return (bmapPoint.getLng() == lng && bmapPoint.getLat() == lat) ? true : false;
        } else {
            return false;
        }
    }
     
    public double getLat() {
    	java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.0000000000");  
    	lat = Double.parseDouble(df.format(lat));
        return lat;
    }
    public void setLat(double lat) {
    	java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.0000000000");  
    	lat = Double.parseDouble(df.format(lat));
        this.lat = lat;
    }
    public double getLng() {
    	java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.0000000000");  
    	lng = Double.parseDouble(df.format(lng));
        return lng;
    }
    public void setLng(double lng) {
    	java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.0000000000");  
    	lng = Double.parseDouble(df.format(lng));
        this.lng = lng;
    }
     
    @Override
    public String toString() {
        return "Point [lat=" + lat + ", lng=" + lng + "]";
    }
     
}