package manage.images.web;

import manage.images.pojo.ResourceImage;
import manage.images.service.ResourceImageService;

import org.apache.log4j.Logger;

import base.web.PaginationAction;

public class ResourceImageAction extends PaginationAction{
	
	private static final Logger log = Logger.getLogger(ResourceImageAction.class);
	
	private ResourceImageService resourceImageService;
	private ResourceImage imageObj;
	public ResourceImageService getResourceImageService() {
		return resourceImageService;
	}
	public void setResourceImageService(ResourceImageService resourceImageService) {
		this.resourceImageService = resourceImageService;
	}
	public ResourceImage getImageObj() {
		return imageObj;
	}
	public void setImageObj(ResourceImage imageObj) {
		this.imageObj = imageObj;
	}
	public static Logger getLog() {
		return log;
	}
	
}
