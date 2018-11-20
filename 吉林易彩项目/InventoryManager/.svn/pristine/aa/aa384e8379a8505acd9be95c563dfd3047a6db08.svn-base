package interfaces.pdainterface.V2.beijing.resource.action;

import interfaces.irmsInterface.interfaces.opticTran.service.impl.IirmsOpticTranService;
import interfaces.irmsInterface.interfaces.outLine.service.impl.IirmsOutLineService;

import java.util.List;

import oracle.net.ano.SupervisorService;

import org.apache.log4j.Logger;

import manage.V2.beijing.resource.pojo.ZSLPOSInfoBean;
import manage.V2.beijing.resource.pojo.ZSLResourcePointInfoBean;
import manage.V2.beijing.resource.service.impl.IresPortService;
import manage.V2.beijing.resource.service.impl.IresPosService;
import base.util.TextUtil;
import base.web.InterfaceAction;

public class PDAResource extends InterfaceAction{
	private static final Logger log = Logger.getLogger(PDAResource.class);
	private IresPortService resPortService;
	private IresPosService resPosService;
	private IirmsOpticTranService irmsOpticTranService;
	/**
	 * 得到资源点的列表
	 * @return
	 */
	public String getResPort(){
		try{
			ZSLResourcePointInfoBean obj = (ZSLResourcePointInfoBean) getRequestObject(ZSLResourcePointInfoBean.class);
			if(this.limit != 0){
				obj.setLimit(limit);
			}
			if(this.start != 0){
				obj.setStart(this.start);
			}
			List<ZSLResourcePointInfoBean> list = this.resPortService.getResPortGrid(obj);
			if(list !=null){
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAResource.getResPort ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 修改资源点
	 * @return
	 */
	public String updateResPort(){
		try{
			ZSLResourcePointInfoBean obj = (ZSLResourcePointInfoBean) getRequestObject(ZSLResourcePointInfoBean.class);
			if(TextUtil.isNull(obj.getId())){
				int result = resPortService.insertResPort(obj);
				obj.setId(result);
				sendResponse(Integer.valueOf(0), "修改成功。");
			}else{
				resPortService.updateResPort(obj);
				sendResponse(Integer.valueOf(0), "修改成功。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAResource.updateResPort ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 增加资源点
	 * @return
	 */
	public String insertResPort(){
		try{
			ZSLResourcePointInfoBean obj = (ZSLResourcePointInfoBean) getRequestObject(ZSLResourcePointInfoBean.class);
			if(obj == null ){
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}else{
				int result = resPortService.insertResPort(obj);
				if(result != -1){
					obj.setId(result);
					sendResponse(Integer.valueOf(0), obj);
				}else{
					sendResponse(Integer.valueOf(2), "资源点已被占用。");
				}
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAResource.insertResPort ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 得到分光器
	 * @return
	 */
	public String getResPos(){
		try{
			ZSLPOSInfoBean obj =  (ZSLPOSInfoBean) getRequestObject(ZSLPOSInfoBean.class);
			if(this.limit != 0){
				obj.setLimit(this.limit);
			}
			if(this.start != 0){
				obj.setStart(this.start);
			}
			List<ZSLPOSInfoBean> list = resPosService.getResPosGrid(obj);
			if(list !=null){
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAResource.getResPos ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 修改分光器
	 * @return
	 */
	public String updateResPos(){
		try{
			ZSLPOSInfoBean obj =  (ZSLPOSInfoBean) getRequestObject(ZSLPOSInfoBean.class);
			if(TextUtil.isNull(obj.getId())){
				int result = resPosService.insertResPos(obj);
				obj.setId(result);
				sendResponse(Integer.valueOf(0), "修改成功。");
			}else{
				this.resPosService.updateResPos(obj);
				sendResponse(Integer.valueOf(0), "修改成功。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAResource.updateResPos ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 增加分光器
	 * @return
	 */
	public String insertResPos(){
		try{
			ZSLPOSInfoBean obj =  (ZSLPOSInfoBean) getRequestObject(ZSLPOSInfoBean.class);
			if(obj == null){
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}else{
				obj.setDataQualityPrincipal(super.getLonginer());
				obj.setParties(super.getLonginer());
				int result = this.resPosService.insertResPos(obj);
				if(result != -1){
					obj.setId(result);
					//往综资同步数据
					if(toIrms){
						new addResPos(result).start();
					}
					sendResponse(Integer.valueOf(0), obj);
				}else{
					sendResponse(Integer.valueOf(2), "资源点已被占用。");
				}
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAResource.insertResPos ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 增加一个分光器
	 * @author chenqp
	 *
	 */
	class addResPos extends Thread{
		private Integer id;
		public addResPos(Integer id) {
			this.id = id;
		}
		@Override
		public void run() {
			ZSLPOSInfoBean pos = new ZSLPOSInfoBean();
			pos.setId(id);
			List<ZSLPOSInfoBean> list =resPosService.getResPosList(pos);
			if(TextUtil.isNotNull(list)){
				pos = list.get(0);
				irmsOpticTranService.addResPos(pos);
			}
			super.run();
		}
	}
	
	public IirmsOpticTranService getIrmsOpticTranService() {
		return irmsOpticTranService;
	}
	public void setIrmsOpticTranService(IirmsOpticTranService irmsOpticTranService) {
		this.irmsOpticTranService = irmsOpticTranService;
	}
	public IresPortService getResPortService() {
		return resPortService;
	}
	public void setResPortService(IresPortService resPortService) {
		this.resPortService = resPortService;
	}
	public static Logger getLog() {
		return log;
	}
	public IresPosService getResPosService() {
		return resPosService;
	}
	public void setResPosService(IresPosService resPosService) {
		this.resPosService = resPosService;
	}
}
