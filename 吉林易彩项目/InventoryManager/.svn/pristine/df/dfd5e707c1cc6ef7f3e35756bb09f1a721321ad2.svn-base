package base.V2.beijing;
import java.math.BigDecimal;
import java.util.Random;

import base.util.Gps;
import base.util.MapUtil;
import base.util.pojo.Point;


/**
 * 测试由GCJ02坐标转GPS坐标再转回去之后，坐标的偏差距离
 * 
 * @author Jackie
 *
 */
public class GCJ02Tester {

	public double getMinx() {
		return minx;
	}

	public void setMinx(double minx) {
		this.minx = minx;
	}

	public double getMiny() {
		return miny;
	}

	public void setMiny(double miny) {
		this.miny = miny;
	}

	public double getMaxx() {
		return maxx;
	}

	public void setMaxx(double maxx) {
		this.maxx = maxx;
	}

	public double getMaxy() {
		return maxy;
	}

	public void setMaxy(double maxy) {
		this.maxy = maxy;
	}

	double minx;
	double miny;
	double maxx;
	double maxy;

	public GCJ02Tester(double minx, double miny, double maxx, double maxy) {
		this.minx = minx;
		this.miny = miny;
		this.maxx = maxx;
		this.maxy = maxy;
	}

	/*public double doRandomTest() {
		Gps g = this.createRandomGPS();
		Gps g2 = PositionUtil.gcj_To_Gps84(g.getWgLat(), g.getWgLon());
		Gps g3 = PositionUtil.gps84_To_Gcj02(g2.getWgLat(), g2.getWgLon());
		
		return this.getClength(g.getWgLon(), g.getWgLat(), g3.getWgLon(), g3.getWgLat());
	}*/
	
	public String doRandomTest2() {
		Gps g = this.createRandomGPS();
		Point g2 = MapUtil.wgs_gcj_encrypts(g.getWgLat(), g.getWgLon());
		Point g3 = MapUtil.gcj_wgs_encrypts(g2.getLng(),g2.getLat());
		
		return MapUtil.Distance(g.getWgLon(), g.getWgLat(), g3.getLng(), g3.getLat());
	}
	
	

	/**
	 * 计算距离，单位米
	 * 
	 * @param alon
	 * @param alat
	 * @param zlon
	 * @param zlat
	 * @return
	 */
	private double getClength(double alon, double alat, double zlon, double zlat) {
		Long length = null;

		int Ri = 6371;
		Double d = (Ri * Math.acos(
				Math.sin(alat * (Math.PI / 180)) * Math.sin(zlat * (Math.PI / 180)) + Math.cos(alat * (Math.PI / 180))
						* Math.cos(zlat * (Math.PI / 180)) * Math.cos((alon - zlon) * (Math.PI / 180))));
//		length = Math.round(d * 1000);
		return d * 1000;
	}

	private Gps createRandomGPS(){
		
            BigDecimal dbx = new BigDecimal(Math.random() * (this.maxx - this.minx) + this.minx);  
            double ran_x = Double.parseDouble(dbx.setScale(8, BigDecimal.ROUND_HALF_UP).toString());  
            BigDecimal dby = new BigDecimal(Math.random() * (this.maxy - this.miny) + this.miny);  
            double ran_y = Double.parseDouble(dby.setScale(8, BigDecimal.ROUND_HALF_UP).toString()); 
            System.out.println(ran_x+"===================="+ran_y);
		Gps g = new Gps(ran_y, ran_x);
		
		return g;
	}
}
