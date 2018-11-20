package interfaces.pdainterface.images.action;

import java.util.List;

import manage.images.pojo.ResourceImage;
import manage.images.service.ResourceImageService;

import org.apache.log4j.Logger;

import base.util.TextUtil;
import base.web.InterfaceAction;

public class ResImageInterFace extends InterfaceAction {

	private static final Logger log = Logger.getLogger(ResImageInterFace.class);
	private ResourceImageService resourceImageService;
	
	/**
	 * 得到图片列表
	 * @return
	 */
	public String getImage(){
		try{
			ResourceImage image = (ResourceImage) getRequestObject(ResourceImage.class);
			if(image !=null){
				if(image.getResourceId().contains("_")){
					image.setResourceId(image.getResourceId().split("_")[image.getResourceId().split("_").length-1]);
				}
				if(TextUtil.isNotNull(image.getResNum())){
					image.setResourceId(null);
				}
				if(image.getResNum().equals("null")){
					image.setResNum(null);
				}
				List<ResourceImage> list=resourceImageService.getResImage(image);
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			e.printStackTrace();
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAimage.getImage ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 删除图片
	 * @return
	 */
	public String delImage() {
		try{
			ResourceImage image = (ResourceImage) getRequestObject(ResourceImage.class);
			if(image != null) {
				this.resourceImageService.delResImage(image);
				sendResponse(0, "删除完成");
			}else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e) {
			e.printStackTrace();
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAimage.delImage ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	
	public ResourceImageService getResourceImageService() {
		return resourceImageService;
	}
	public void setResourceImageService(ResourceImageService resourceImageService) {
		this.resourceImageService = resourceImageService;
	}
	public static Logger getLog() {
		return log;
	}
}
