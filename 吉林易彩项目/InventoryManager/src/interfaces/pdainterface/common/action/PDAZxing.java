package interfaces.pdainterface.common.action;

import interfaces.pdainterface.common.pojo.ZxingPojo;
import interfaces.pdainterface.common.service.impl.ICommonService;

import org.apache.log4j.Logger;

import base.web.InterfaceAction;


/**
 * 二维码接口实现类
 * @author chenqp
 *
 */
public class PDAZxing extends InterfaceAction{
	
	private static final Logger log = Logger.getLogger(PDAZxing.class);
	private ICommonService commonService;
	
	
	/**
	 * 二维码扫描接口
	 * @return
	 */
	public String zxScan(){
		try{
			ZxingPojo zxing = (ZxingPojo) getRequestObject(ZxingPojo.class);
			if(zxing != null){
				String province = getProperties().getValueByKey("province");
				if(province.equals("湖南")){
					zxing = this.getHnScan(zxing);
				}else{
					zxing = this.getHnScan(zxing);
				}
				sendResponse(Integer.valueOf(0), zxing);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("pdaZxing.zxScan ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 得到湖南解析
	 * 方式
	 * @param zxing
	 * @return
	 */
	ZxingPojo getHnScan(ZxingPojo zxing){
		String scanCode = zxing.getZxCode();
		System.out.println("===数据"+scanCode);
		if(scanCode.contains("_")){
			String[] codes = scanCode.split("_");
			if(scanCode.contains("im")){
				zxing.setResType(codes[0]);
				zxing.setResId(codes[codes.length-1].substring(13,codes[codes.length-1].length()));
			}else{
				zxing.setResType(codes[0]);
				String resNum = codes[codes.length-1].substring(13,codes[codes.length-1].length());
				String resId = commonService.getResId(resNum, zxing.getResType());
				zxing.setResId(resId);
			}
		}else{
			//综资已有接口2005020401400644105254
			if(scanCode.contains("0100110")){
				zxing.setResType("station");
				String resNum = scanCode.substring(13, scanCode.length());
				String resId = commonService.getResId(resNum, zxing.getResType());
				zxing.setResId(resId);
			}else if(scanCode.contains("0401400") || scanCode.contains("0401411")){
				System.out.println("===这是光交的数据数据"+scanCode);
				zxing.setResType("equt");
				String resNum = scanCode.substring(13, scanCode.length());
				String resId = commonService.getResId(resNum, zxing.getResType());
				System.out.println("===得到数据"+resId);
				zxing.setResId(resId);
			}else if(scanCode.contains("0401700")){
				zxing.setResType("cable");
				String resNum = scanCode.substring(13, scanCode.length());
				String resId = commonService.getResId(resNum, zxing.getResType());
				zxing.setResId(resId);
			}
		}
		return zxing;
	}
	
	
	public ICommonService getCommonService() {
		return commonService;
	}
	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}
	public static Logger getLog() {
		return log;
	}
}
