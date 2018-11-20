package com.function.index.model;
public class Location{
	
	/*纬度*/
	private Double latitude;
	/*经度*/
	private Double longitude;
	
	public Location(double latitude, double longitude) {  
        this.latitude = latitude;  
        this.longitude = longitude;  
    }

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
