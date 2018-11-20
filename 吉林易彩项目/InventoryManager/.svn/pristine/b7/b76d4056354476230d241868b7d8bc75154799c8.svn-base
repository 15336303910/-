package manage.V2.beijing.resource.web;

import manage.V2.beijing.resource.pojo.ZSLPOSInfoBean;
import manage.V2.beijing.resource.service.impl.IresPosService;

import org.apache.log4j.Logger;

import base.web.PaginationAction;

import com.opensymphony.xwork2.ModelDriven;

public class ResPosAction extends PaginationAction implements ModelDriven{
	private int start = 0;
	private int length = 15;
	private static final Logger log = Logger.getLogger(ResPosAction.class);
	private ZSLPOSInfoBean object = new ZSLPOSInfoBean();
	private IresPosService resPosService;
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
	public ZSLPOSInfoBean getObject() {
		return object;
	}
	public void setObject(ZSLPOSInfoBean object) {
		this.object = object;
	}
	public IresPosService getResPosService() {
		return resPosService;
	}
	public void setResPosService(IresPosService resPosService) {
		this.resPosService = resPosService;
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
	public static Logger getLog() {
		return log;
	}
}
