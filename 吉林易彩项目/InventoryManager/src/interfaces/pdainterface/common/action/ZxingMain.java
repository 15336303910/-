package interfaces.pdainterface.common.action;

public class ZxingMain {

	public static void main(String[] args) {
		String scanCode = "pole_im_2017010409104360";
		if(scanCode.contains("im")){
			String[] codes = scanCode.split("_");
			System.out.println(codes[codes.length-1].substring(13,codes[codes.length-1].length()));
		}
	}
}
