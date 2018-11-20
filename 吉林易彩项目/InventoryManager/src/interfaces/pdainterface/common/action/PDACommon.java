package interfaces.pdainterface.common.action;

import java.util.List;

import interfaces.irmsInterface.interfaces.correct.irmsUtil.AssignDealerPojo;
import interfaces.irmsInterface.interfaces.correct.irmsUtil.TaskDealPojo;
import interfaces.irmsInterface.interfaces.correct.irmsUtil.TaskInfoPojo;
import interfaces.pdainterface.common.pojo.CorrectBean;
import interfaces.pdainterface.common.pojo.CorrectResBean;
import interfaces.pdainterface.common.pojo.EqutPointObj;
import interfaces.pdainterface.common.pojo.IntergradeBean;
import interfaces.pdainterface.common.pojo.OpticabLay;
import interfaces.pdainterface.common.pojo.QualitorBean;
import interfaces.pdainterface.common.service.impl.ICommonService;
import manage.equt.pojo.EqutInfoBean;
import manage.user.pojo.UserInfoBean;

import org.apache.log4j.Logger;

import com.sun.mail.iap.Literal;

import edu.emory.mathcs.backport.java.util.LinkedList;
import base.util.TextUtil;
import base.web.InterfaceAction;

public class PDACommon extends InterfaceAction{
	private static final Logger log = Logger.getLogger(PDACommon.class);
	private static final int List = 0;
	private ICommonService commonService;
	
	
	/**
	 * 得到综
	 * 合业务区数据
	 * @return
	 */
	public String getIntergrade(){
		try{
			IntergradeBean obj = (IntergradeBean)getRequestObject(IntergradeBean.class);
			obj.setStateFlag("0");
			obj.setStart(this.start);
			obj.setLimit(this.limit);
			if(TextUtil.isNotNull(super.areaName)){
				obj.setCoverArea(super.areaName);
			}
			List<IntergradeBean> list = this.commonService.getIntergradeList(obj);
			if(list != null){
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDACommon.getIntergrade ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 得到设备的id
	 * 输入     id 光缆段id
	 * 		 type a z 
	 * 输出     list
	 * 	     id   设备的id startDeciveid  enddeviceid  
	 *       name 设备名称    设备名称显示
	 *       type 设备的类型   startdeviceid  enddeviceType
	 *      
	 * @return
	 */
	public String getDeviceList(){
		try{
			EqutPointObj obj = (EqutPointObj)getRequestObject(EqutPointObj.class);
			if(obj != null){
				List<EqutPointObj> list = this.commonService.getDeviceList(obj);
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDACommon.getDeviceList ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 得到设备
	 * 下的纤芯
	 * 输入 
	 * 	   id  startdeviceid   enddeviceid
	 *     type startdeviceType  enddevicetype
	 * 输出
	 * 	   id  端子的id
	 * 	   name 端子的name 
	 *     type 端子类型
	 * 	    
	 * @return
	 */
	public String getPointList(){
		try{
			EqutPointObj obj = (EqutPointObj)getRequestObject(EqutPointObj.class);
			if(obj != null){
				List<EqutPointObj> list = this.commonService.getEpointList(obj);
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDACommon.getPointList ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		
		return "success";
	}
	
	
	/**
	 * 得到敷设
	 * @return
	 */
	public String getOpticabLayList(){
		try{
			OpticabLay obj = (OpticabLay)getRequestObject(OpticabLay.class);
			if(obj !=null ){
				List<OpticabLay> list = new LinkedList();
				if(obj.getSpanType() < 7){
					//敷设到段资源上
					 list= this.commonService.getOpticabLayList(obj);
				}else if(obj.getSpanType() == 7){
					list= this.commonService.getOpticabLayByTube(obj);
				}
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDACommon.getOpticabLayList ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 删除敷设
	 * @return
	 */
	public String delOpticabLay(){
		try{
			OpticabLay obj = (OpticabLay)getRequestObject(OpticabLay.class);
			if(obj !=null){
				this.commonService.delOpticabLay(obj);
				sendResponse(Integer.valueOf(0), "删除成功");
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDACommon.delOpticabLay ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 增加敷设
	 * @return
	 */
	public String addOpticabLay(){
		try{
			List<OpticabLay> list =(List<OpticabLay>) getRequestObject(OpticabLay.class);
			if(list !=null){
				this.commonService.beathAddOpticalLay(list);
				sendResponse(Integer.valueOf(0), "增加成功");
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDACommon.addOpticabLay ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 得到一线维护
	 * 人列表
	 * @return
	 */
	public String getQualitor(){
		try{
			QualitorBean object = (QualitorBean) getRequestObject(QualitorBean.class);
			if(object !=null){
				if(TextUtil.isNotNull(super.areaName)){
					object.setCity(super.areaName);
				}
				object.setStart(this.start);
				object.setLimit(this.limit);
				List<QualitorBean> list = this.commonService.getQualitorList(object);
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDACommon.getQualitor ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 派发勘误工单
	 * @return
	 */
	public String billCorrent(){
		try{
			UserInfoBean user = (UserInfoBean) getInterfaceSession().getAttribute("userBean");
			CorrectBean object = (CorrectBean)getRequestObject(CorrectBean.class);
			if(object !=null){
				object.setSendMan(user.getUsername());
				object.setSendManId(user.getEid()+"");
				String result = this.commonService.billCorrent(object);
				if(result .equals("success")){
					sendResponse(Integer.valueOf(0), "派发成功");
				}else{
					sendResponse(Integer.valueOf(0), result);
				}
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDACommon.billCorrent ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 得到待办工单列表
	 * @return
	 */
	public String getWaitedTask(){
		try{
			UserInfoBean user = (UserInfoBean) getInterfaceSession().getAttribute("userBean");
			CorrectBean object = (CorrectBean)getRequestObject(CorrectBean.class);
			if(object!=null){
				if(TextUtil.isNull(object.getStart())){
					object.setStart(super.start);
					object.setLimit(super.limit);
				}
				object.setDealer(user.getRealname());
				String json = this.commonService.getWaitedTaskStr(user.getEid(), null, null, object.getStart());
				sendResponse(Integer.valueOf(0), json);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDACommon.getWaitedTask ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 *获取已办处理信息
	 * @return
	 */
	public String getClaimedTask(){
		try{
			UserInfoBean user = (UserInfoBean) getInterfaceSession().getAttribute("userBean");
			CorrectBean object = (CorrectBean)getRequestObject(CorrectBean.class);
			if(object!=null){
				if(TextUtil.isNull(object.getStart())){
					object.setStart(super.start);
					object.setLimit(super.limit);
				}
				object.setDealer(user.getRealname());
				String json = this.commonService.getClaimedTask(user.getEid(),object.getStart(),object.getLimit());
				sendResponse(Integer.valueOf(0), json);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDACommon.getClaimedTask ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 得到工单详情
	 * @return
	 */
	public String getTaskInfo(){
		try{
			TaskInfoPojo object = (TaskInfoPojo) getRequestObject(TaskInfoPojo.class);
			if(object != null){
				String json = this.commonService.getTaskInfo(object.getFlowId(), "");
				sendResponse(Integer.valueOf(0), json);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDACommon.getTaskInfo ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 工单历史处理信息
	 * @return
	 */
	public String getTaskHisInfo(){
		try{
			UserInfoBean user = (UserInfoBean) getInterfaceSession().getAttribute("userBean");
			TaskInfoPojo object = (TaskInfoPojo) getRequestObject(TaskInfoPojo.class);
			if(object != null){
				String json = this.commonService.getTaskHisInfo(object.getFlowId());
				sendResponse(Integer.valueOf(0), json);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDACommon.getTaskHisInfo ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 得到工单处理界面
	 * @return
	 */
	public String getTaskDealInfo(){
		try{
			UserInfoBean user = (UserInfoBean) getInterfaceSession().getAttribute("userBean");
			TaskInfoPojo object = (TaskInfoPojo) getRequestObject(TaskInfoPojo.class);
			if(object != null){
				String inParam = "{\"flowId\":\""+object.getFlowId()+"\","
						+ " \"processDefName\":\"com.inspur.app.hn.kanwu.process.kanwuGx\","
						+ " \"activeName\":\""+object.getActiveName()+"\","
						+ " \"empid\":\""+user.getEid()+"\","
						+ " \"groupName\":\"\"}";
				
				String json = this.commonService.getTaskDealInfo(inParam);
				sendResponse(Integer.valueOf(0), json);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDACommon.getTaskDealInfo ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	
	/**
	 * 处理工单实例接口
	 * @return
	 */
	public String dealTaskInfo(){
		try{
			UserInfoBean user = (UserInfoBean) getInterfaceSession().getAttribute("userBean");
			TaskDealPojo object = (TaskDealPojo) getRequestObject(TaskDealPojo.class);
			if(object != null){
				String inParam = "{\"flowId\":\""+object.getFlowId()+"\","
						+ " \"workItemId\":\""+object.getWorkItemId()+"\","
						+ " \"isPass\":\""+object.getIsPass()+"\",";
				if(TextUtil.isNotNull(object.getEmpid())){
					inParam +=" \"empid\":\""+object.getEmpid()+"\","
							+ " \"empName\":\""+object.getEmpName()+"\",";
				}
				inParam+= " \"remark\":\""+object.getRemark()+"\","
						+ " \"ownerName\":\""+user.getUsername()+"\"}";
				
				String json = this.commonService.dealTaskInfo(inParam,object.getFlowId());
				if(json.equals("success")){
					sendResponse(Integer.valueOf(0), "处理完成");
				}else{
					sendResponse(Integer.valueOf(2), "当前登录人无权限处理该工单!");
				}
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDACommon.dealTaskInfo ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 获取分派人
	 * @return
	 */
	public String getAssignDealer(){
		try{
			UserInfoBean user = (UserInfoBean) getInterfaceSession().getAttribute("userBean");
			AssignDealerPojo object = (AssignDealerPojo) getRequestObject(AssignDealerPojo.class);
			if(object != null){
				if(TextUtil.isNull(object.getParentId())){
					object.setParentId("0");
				}
				String inParam = "{\"userId\":\""+user.getUserId()+"\","
						+ " \"objectType\":\""+object.getObjectType()+"\","
						+ "\"parentId\":\""+object.getParentId()+"\","
						+ "\"userName\":\"\"}";
				String json = this.commonService.getAssignDealer(inParam);
				sendResponse(Integer.valueOf(0),json);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDACommon.getAssignDealer ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 得到资源列表
	 * @return
	 */
	public String getCorrectRes(){
		try{
			CorrectResBean resObj = (CorrectResBean) getRequestObject(CorrectResBean.class);
			if(resObj != null){
				List<CorrectResBean> list = this.commonService.getResByTask(resObj.getTaskId());
				if(TextUtil.isNotNull(list)){
					sendResponse(Integer.valueOf(0), list);
				}else{
					sendResponse(Integer.valueOf(2), "请求参数错误。");
				}
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDACommon.getCorrectRes ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
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
