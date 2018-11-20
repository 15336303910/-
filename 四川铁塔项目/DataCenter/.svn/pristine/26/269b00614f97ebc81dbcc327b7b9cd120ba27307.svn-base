package com.function.index.service;
import com.function.index.model.Location;
public class LatitudeLontitudeUtil {

	/*地球半径*/
	private static final double EARTH_RADIUS = 6371000;
	/*距离*/
	private double distance;
	/*左上角*/
	private Location left_top = null;
	/*右上角*/
	private Location right_top = null;
	/*左下角*/
	private Location left_bottom = null;
	/*右下角*/
	private Location right_bottom = null;
	
	private LatitudeLontitudeUtil(double distance) {  
        this.distance = distance;  
    }
	
	private void getRectangle4Point(double lat,double lng){
		double dlng = 2 * Math.asin(Math.sin(distance/(2*EARTH_RADIUS))/Math.cos(lat));  
        dlng = Math.toDegrees(dlng);
        double dlat = distance / EARTH_RADIUS;
        dlat = Math.toDegrees(dlat);
        left_top = new Location(lat+dlat,lng-dlng);
        left_bottom = new Location(lat-dlat,lng-dlng);
        right_top = new Location(lat+dlat,lng+dlng);
        right_bottom = new Location(lat - dlat,lng+dlng); 
	}
	
	public static double hav(double theta){  
        double s = Math.sin(theta/2);  
        return s*s;  
    }  
	
	public static double getDistance(double lat0,double lng0,double lat1,double lng1){  
		lat0 = Math.toRadians(lat0);  
        lat1 = Math.toRadians(lat1);  
        lng0 = Math.toRadians(lng0);  
        lng1 = Math.toRadians(lng1);
        double dlng = Math.abs(lng0 - lng1);  
        double dlat = Math.abs(lat0 - lat1);  
        double h = hav(dlat) + Math.cos(lat0) * Math.cos(lat1) * hav(dlng);  
        double distance = 2 * EARTH_RADIUS * Math.asin(Math.sqrt(h));
        return distance;  
	}
	
	public static Location[] getRectangle4Point(double lat,double lng,double distance){  
        LatitudeLontitudeUtil jwUtil = new LatitudeLontitudeUtil(distance);  
        jwUtil.getRectangle4Point(lat, lng);  
        Location[] locations = new Location[4];  
        locations[0] = jwUtil.left_top;  
        locations[1] = jwUtil.right_top;  
        locations[2] = jwUtil.left_bottom;  
        locations[3] = jwUtil.right_bottom;  
        return locations;  
    } 
	
	public static void main(String[] args) { 
		/*
		 * 	获取经纬度周边200米的范围.
		 * 
		 * */
        double myLatitude = 36.6621;  
        double myLongitude = 117.12941;  
        double distance = 300.0;  
        Location[] locations = LatitudeLontitudeUtil.getRectangle4Point(myLatitude,myLongitude,distance);
        Double topLine = locations[0].getLatitude();
        Double bottomLine = locations[2].getLatitude();
        Double leftLine = locations[0].getLongitude();
        Double rightLine = locations[1].getLongitude();
        if(bottomLine.toString().length()>=16){
        	bottomLine = Double.parseDouble(bottomLine.toString().substring(0,15));
        }
        if(topLine.toString().length()>=16){
        	topLine = Double.parseDouble(topLine.toString().substring(0,15));
        }
        if(leftLine.toString().length()>=16){
        	leftLine = Double.parseDouble(leftLine.toString().substring(0,15));
        }
        if(rightLine.toString().length()>=16){
        	rightLine = Double.parseDouble(rightLine.toString().substring(0,15));
        }
        System.out.println("自下而上："+bottomLine+" => "+topLine);
        System.out.println("自左而右："+leftLine+" => "+rightLine);
        /*
         * 	计算两个经纬度之间的距离.
         * 
         * */
        double targetLatitude = 36.6622;  
        double targetLongitude = 117.12942;  
        Double targetDistance = LatitudeLontitudeUtil.getDistance(myLatitude,myLongitude,targetLatitude,targetLongitude);  
        System.out.println("与指定经纬度的距离约为："+targetDistance.intValue()+"米.");  
    } 
}
