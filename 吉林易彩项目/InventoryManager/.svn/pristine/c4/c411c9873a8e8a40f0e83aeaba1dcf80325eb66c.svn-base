package interfaces.pdainterface.common.action;

import java.util.List;

import org.apache.log4j.Logger;

import interfaces.irmsInterface.interfaces.pojo.IrmsPojo;
import interfaces.irmsInterface.interfaces.service.impl.IirmsInterService;
import base.web.InterfaceAction;

public class PDAInterIrms extends InterfaceAction{
	private static final Logger log = Logger.getLogger(PDAInterIrms.class);
	private IirmsInterService irmsInterService;
	
	/**
	 * 得到信息列表
	 * @return
	 */
	public String getInterIrms(){
		try{
			IrmsPojo obj = (IrmsPojo) getRequestObject(IrmsPojo.class);
			if(obj !=null){
				obj.setStart(this.start);
				obj.setLimit(this.limit);
				List<IrmsPojo> list = this.irmsInterService.getIrmsGrid(obj);
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(1), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("pdaIrms.getInterIrms ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 得到实体类
	 * @return
	 */
	public String getInterObj(){
		try{
			IrmsPojo obj = (IrmsPojo) getRequestObject(IrmsPojo.class);
			if(obj != null){
				obj = this.irmsInterService.getIrmsObj(obj);
				sendResponse(Integer.valueOf(0), obj);
			}else{
				sendResponse(Integer.valueOf(1), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("pdaIrms.getInterIrms ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	
	public IirmsInterService getIrmsInterService() {
		return irmsInterService;
	}
	public void setIrmsInterService(IirmsInterService irmsInterService) {
		this.irmsInterService = irmsInterService;
	}
	public static Logger getLog() {
		return log;
	}
}
