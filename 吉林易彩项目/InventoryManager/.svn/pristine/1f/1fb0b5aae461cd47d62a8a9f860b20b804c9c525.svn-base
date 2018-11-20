package manage.V2.beijing.resource.web;

import manage.V2.beijing.resource.pojo.ZSLResourcePointInfoBean;
import manage.V2.beijing.resource.service.impl.IresPortService;

import org.apache.log4j.Logger;

import base.web.PaginationAction;

import com.opensymphony.xwork2.ModelDriven;

public class ResPortAction extends PaginationAction implements ModelDriven{
	private int start = 0;
	private int length = 15;
	private static final Logger log = Logger.getLogger(ResPortAction.class);
	private ZSLResourcePointInfoBean object = new ZSLResourcePointInfoBean();
	private IresPortService resPortService;
	private String jsonString;
	private java.io.File file;
	private String fileFileName;
	
	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return object;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public ZSLResourcePointInfoBean getObject() {
		return object;
	}
	public void setObject(ZSLResourcePointInfoBean object) {
		this.object = object;
	}
	public IresPortService getResPortService() {
		return resPortService;
	}
	public void setResPortService(IresPortService resPortService) {
		this.resPortService = resPortService;
	}
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	public java.io.File getFile() {
		return file;
	}
	public void setFile(java.io.File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
}
