package interfaces.pdainterface.V2.hebei.mainTask.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jfree.util.Log;

import manage.V2.hebei.mainTask.service.MainTaskService;
import base.web.InterfaceAction;

public class PDAMaintask extends InterfaceAction{
	
	private static final Logger log = Logger.getLogger(PDAMaintask.class);

	//http://localhost:8080/InventoryManager/pdaBuriedPart!getMainTask.interface
	private MainTaskService mainTaskService;
		
	public MainTaskService getMainTaskService() {
		return mainTaskService;
	}

	public void setMainTaskService(MainTaskService mainTaskService) {
		this.mainTaskService = mainTaskService;
	}

	/**
	 * 巡检任务主键接口（出发）
	 * 返回路由ID主键
	 */
	public String getRouteInfo() {
		String uid = getUID();
		String jsonParams = getJsonRequest();
		//sendResponse(Integer.valueOf(0),this.mainTaskService.getRouteInfo(uid,jsonParams));
		return "success";
	}
	
	/**
	 * 拍照上传接口
	 * 返回业务数据
	 */
	/*public String upLoadPhoto() {
		String uid = getUID();
		String jsonParams = getJsonRequest();
		HttpServletRequest request = getRequest();
		sendResponse(Integer.valueOf(0), this.mainTaskService.upLoadPhoto(uid,jsonParams,request));
		return "success";
	}*/
	
	/**
	 * 关联资源接口
	 * 返回对应数据
	 */
	public String getRalitonRes(){
		String uid = getUID();
		String jsonParams = getJsonRequest();
		String mess = this.mainTaskService.getRalitonRes(uid, jsonParams);
		if("没有查询到资源信息".equals(mess)){
			sendResponse(Integer.valueOf(1),mess);
		}else{
			sendResponse(Integer.valueOf(0),mess);
		}
		return "success";
	}
	
	/**
	 * 隐患上传接口
	 * 返回业务数据
	 * @return
	 */
	/*public String upLoadError(){
		String uid = getUID();
		String jsonParams = getJsonRequest();
		sendResponse(Integer.valueOf(0), this.mainTaskService.upLoadError(uid, jsonParams));
		return "success";
	}*/
	
	/**
	 * 巡查任务数据上传接口
	 * 返回业务数据
	 */
	/*public String upLoadTask(){
		String uid = getUID();
		String jsonParams = getJsonRequest();
		sendResponse(Integer.valueOf(0), this.mainTaskService.upLoadTask(uid, jsonParams));
		return "success";
	}*/
	
	/**
	 * 传输线路-我的
	 * @return
	 */
	public String getMyRoute(){
		String uid = getUID();
		String jsonParams = getJsonRequest();
		//sendResponse(Integer.valueOf(0),this.mainTaskService.getMyRoute(uid, jsonParams));
		return "success";
	}
	
	/**
	 * 清理垃圾路由数据
	 * @return
	 */
	public String clearRoute(){
		String uid = getUID();
		String jsonParams = getJsonRequest();
		String mess="";
		//String mess = this.mainTaskService.deleteRoute(uid, jsonParams);
		if("routeId为空!".equals(mess)){
			sendResponse(Integer.valueOf(1), mess);
		}else{
			sendResponse(Integer.valueOf(0), mess);
		}
		return "success";
	}
	
	/**
	 * 获取路由段信息
	 * @return
	 */
	public String getSegInfo(){
		String uid = getUID();
		String jsonParams = getJsonRequest();
		String mess = "";
		log.info("mess========================="+mess);
		if("没有查询到资源信息".equals(mess) || "没有查询到段信息".equals(mess)){
			sendResponse(Integer.valueOf(1), mess);
		}else{
			sendResponse(Integer.valueOf(0), mess);
		}
		return "success";
	}
	
	/**
	 * 通过routeId和userId获取路由信息
	 * @return
	 */
	public String getRouteInfoById(){
		String uid = getUID();
		String jsonParams = getJsonRequest();
		//sendResponse(Integer.valueOf(0),  this.mainTaskService.getRouteInfoById(uid, jsonParams));
		return "success";
	}
	
	public String editRouteInfo(){
		String uid = getUID();
		String jsonParams = getJsonRequest();
		String infoStr = "";
		log.debug("infoStr===="+infoStr);
		if(StringUtils.isNotBlank(infoStr)){
			if(infoStr.length()>30){//长度大于30 说明返回的是正确格式实体类的json串
				sendResponse(Integer.valueOf(0),  infoStr);
			}else{
				sendResponse(Integer.valueOf(1),  infoStr);
			}
		}
		return "success";
	}
		
}
