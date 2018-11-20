package base.V2.beijing;

public class TestMain {

	public static void main(String[] args) {
		double minx;double miny;double maxx;double maxy;
		double dMeter = 1;
		System.out.println("-=====");
		minx = 115.3d;miny=39.4d;maxx=117.6d;maxy=41.1d;
		GCJ02Tester tester = new GCJ02Tester(minx, miny, maxx, maxy);
		int count = 0;
		for (int i=0; i<1000; i++){
//			System.out.println(tester.doRandomTest());
			double d = Double.parseDouble(tester.doRandomTest2());
			System.out.println("距离"+d+"===================");
			if(d<dMeter){
				count++;
			}
		}

		System.out.println("满足在"+dMeter+"米范围内的点个数："+count);
	}
}
