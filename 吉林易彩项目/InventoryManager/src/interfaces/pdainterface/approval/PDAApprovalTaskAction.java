package interfaces.pdainterface.approval;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import edu.emory.mathcs.backport.java.util.LinkedList;
import manage.approval.pojo.ApprovalListPojo;
import manage.approval.pojo.ApprovalResRejectPojo;
import manage.approval.pojo.ApprovalTaskPojo;
import manage.approval.service.ApprovalTaskService;
import manage.approval.service.impl.IapprovalTaskService;
import manage.user.pojo.UserInfoBean;
import net.sf.ezmorph.bean.MorphDynaBean;
import base.util.JsonUtil;
import base.util.MapUtil;
import base.util.TextUtil;
import base.util.pojo.Point;
import base.web.InterfaceAction;

public class PDAApprovalTaskAction extends InterfaceAction{
	private static final Logger log = Logger.getLogger(PDAApprovalTaskAction.class);
	private IapprovalTaskService approvalService;

	
	/**
	 * 得到所有的工单列表
	 * @return
	 */
	public String getApproval(){
		try{
			ApprovalTaskPojo object = (ApprovalTaskPojo) getRequestObject(ApprovalTaskPojo.class);
			UserInfoBean user = (UserInfoBean) getInterfaceSession().getAttribute("userBean");
			if(object !=null){
				if(TextUtil.isNull(object.getSender())){
					object.setSender(user.getRealname());
					object.setExtendSql(" t.flowName is null and (t.delFlag is null or t.delFlag ='Y')");
				}
				List<ApprovalTaskPojo> list = this.approvalService.getApprovalTaskList(object);
				ApprovalTaskPojo bean = new ApprovalTaskPojo();
				bean.setApprovaler(user.getRealname());
				bean.setExtendSql(" t.flowName ='send' and t.taskState='驳回'");
				List<ApprovalTaskPojo> dealList = this.approvalService.getApprovalTaskList(bean);
				if(TextUtil.isNotNull(dealList)) {
					for(ApprovalTaskPojo deal : dealList) {
						String sender = deal.getSender();
						String approvaler = deal.getApprovaler();
						String audit = deal.getAuditer();
						deal.setSender(approvaler);
						deal.setApprovaler(audit);
						list.add(deal);
					}
				}
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAApprovalTask.getApproval ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 派发审批工单
	 * @return
	 */
	public String sendApproval(){
		try{
			ApprovalTaskPojo object = (ApprovalTaskPojo) getRequestObject(ApprovalTaskPojo.class);
			UserInfoBean user = (UserInfoBean) getInterfaceSession().getAttribute("userBean");
			if(object !=null){
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(TextUtil.isNull(object.getCity())){
					object.setCity(user.getAreaName()+"_");
				}
				if(TextUtil.isNotNull(object.getCity()) && object.getCity().contains("_")){
					if(object.getResType().endsWith(",")){
						String resType = object.getResType().substring(0,object.getResType().length()-1);
						//String resType = "外线资源";
						object.setResType(resType);
					}
					String[] areas = object.getCity().split("_");
					String city,country;
					if(areas.length>=2){
						city = areas[areas.length-2];
						country = areas[areas.length-1];
					}else{
						city=user.getAreaName();
						country="";
					}
					object.setCity(city);
					object.setCounty(country);
					object.setSender(super.realName);
					object.setSendId(user.getUsername());
					object.setTaskTitle(city+country+realName+"_"+sdf.format(date)+"_采集工单");
					object.setTaskState("处理中");
					object.setExtendSql(" and t.flowName is null  ");
				}
				List<Map<String, Object>> list = this.approvalService.getApprovalList(object);
				if(TextUtil.isNotNull(list)){
					sendResponse(Integer.valueOf(1), "在此时间段内您提交过核查工单!");
				}else{
					object.setDelFlag("Y");
					int id = this.approvalService.addApproval(object);
					object.setId(id);
					new countApprovalList(object).start();
					sendResponse(Integer.valueOf(0), "派发成功");
				}
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAApprovalTask.sendApproval ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 進行搶單操作
	 * @return
	 */
	public String getGrap() {
		try {
			ApprovalTaskPojo object = (ApprovalTaskPojo) getRequestObject(ApprovalTaskPojo.class);
			UserInfoBean user = (UserInfoBean) getInterfaceSession().getAttribute("userBean");
			if(null != object) {
				List<Map<String, Object>> grapList = this.approvalService.getGrapApproval(user.getUserId()+"");
				if(TextUtil.isNull(grapList)) {
					ApprovalTaskPojo pojo = this.approvalService.getApprovalObj(object.getId()+"");
					pojo.setApprovaler(user.getRealname());
					pojo.setApprovalerId(user.getUserId()+"");
					pojo.setTaskState("已领单");
					this.approvalService.upApprovalObj(pojo);
					sendResponse(Integer.valueOf(0), "领单成功");
				}else {
					sendResponse(Integer.valueOf(1), "请先处理已领工单");
				}
			}else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e) {
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAApprovalTask.getGrap ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 得到工单中的派发站点
	 * @return
	 */
	public String getSendSite(){
		try{
			ApprovalTaskPojo object = (ApprovalTaskPojo) getRequestObject(ApprovalTaskPojo.class);
			UserInfoBean user = (UserInfoBean) getInterfaceSession().getAttribute("userBean");
			if(null != object){
				if(TextUtil.isNotNull(user.getGroupId())) {
					object.setGroupId(user.getGroupId());
					if(TextUtil.isNull(object.getApprovalerId())) {
						object.setApprovalerId(user.getUserId()+"");
						object.setApprovaler(user.getRealname());
					}
				}
				List<Map<String, Object>> list = approvalService.getHisSite(object);
				String jsonString="[";
				for(Map<String, Object> map : list){
					jsonString+="{";
					jsonString += ""
							+ "\"id\":"+map.get("id")+","
							+ "\"taskTitle\":\""+map.get("taskTitle")+"\","
							+ "\"taskType\":\"station\","
							+ "\"points\":[";
					String mapPoint = map.get("mapPoint")+"";
					String[] points = mapPoint.split(",");
					for(String point : points){
						//需要把坐标转成百度坐标
						String[] pts = point.split("_");
						Point gcjPoint = MapUtil.google_bd_encrypt(Double.parseDouble(pts[1]),Double.parseDouble(pts[2]));
						jsonString +="{";
						jsonString +="\"siteId\":\""+pts[0]+"\","
								   + "\"siteName\":\""+pts[3]+"\","
								   + "\"latitude\":\""+gcjPoint.getLat()+"\","
								   	+ "\"longitude\":\""+gcjPoint.getLng()+"\"";
						jsonString +="},";
					}
					if(jsonString.endsWith(",")){
						jsonString = jsonString.substring(0,jsonString.length()-1);
					}
					jsonString +="]";
					jsonString +="},";
				}
				if(jsonString.endsWith(",")){
					jsonString = jsonString.substring(0,jsonString.length()-1);
				}
				jsonString +="]";
				sendResponse(Integer.valueOf(0), jsonString);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}
	
	
	/**
	 * 得到派发核查工单的历史
	 * @return
	 */
	public String getSendHis(){
		try{
			ApprovalTaskPojo object = (ApprovalTaskPojo) getRequestObject(ApprovalTaskPojo.class);
			UserInfoBean user = (UserInfoBean) getInterfaceSession().getAttribute("userBean");
			if(null != object){
				if(TextUtil.isNull(object.getCounty())) {
					object.setCounty(super.areaName);
				}
				if(TextUtil.isNotNull(user.getGroupId())) {
					object.setGroupId(user.getGroupId());
					if(TextUtil.isNull(object.getApprovalerId())) {
						object.setApprovalerId(user.getUserId()+"");
						object.setApprovaler(user.getRealname());
					}
				}
				List<Map<String, Object>> list = approvalService.getHisMap(object);
				List<Map<String, Object>> siteList = approvalService.getHisSite(object);
				String jsonString="[";
				for(Map<String, Object> map : siteList) {
					jsonString+="{";
					jsonString += ""
							+ "\"id\":"+map.get("id")+","
							+ "\"taskTitle\":\""+map.get("taskTitle")+"\","
							+ "\"approvalerId\":\""+map.get("approvalerId")+"\","
							+ "\"approvaler\":\""+map.get("approvaler")+"\","
							+ "\"sender\":\""+map.get("sender")+"\","
							+ "\"createTime\":\""+map.get("createTime")+"\","
							+ "\"taskType\":\"station\","
							+ "\"points\":[";
					String mapPoint = map.get("mapPoint")+"";
					String[] points = mapPoint.split(",");
					for(String point : points){
						//需要把坐标转成百度坐标
						String[] pts = point.split("_");
						Point gcjPoint = MapUtil.google_bd_encrypt(Double.parseDouble(pts[1]),Double.parseDouble(pts[2]));
						for(int i=0;i<4;i++) {
							jsonString +="{";
							jsonString +="\"siteId\":\""+pts[0]+"\","
									+ "\"num\":\""+(i+1)+"\","
								   + "\"siteName\":\""+pts[3]+"\","
								   + "\"latitude\":\""+gcjPoint.getLat()+"\","
								   	+ "\"longitude\":\""+gcjPoint.getLng()+"\"";
						jsonString +="},";
						}
					}
					if(jsonString.endsWith(",")){
						jsonString = jsonString.substring(0,jsonString.length()-1);
					}
					jsonString +="]";
					jsonString +="},";
				}
				if(jsonString.endsWith(",") && TextUtil.isNull(list)){
					jsonString = jsonString.substring(0,jsonString.length()-1);
				}
				for(Map<String, Object> map : list){
					String mapPoint = map.get("mapPoint")+"";
					String[] points = mapPoint.split(",");
					if(points.length <=3){
						continue;
					}
					jsonString+="{";
					jsonString += ""
							+ "\"id\":"+map.get("id")+","
							+ "\"taskTitle\":\""+map.get("taskTitle")+"\","
							+ "\"approvalerId\":\""+map.get("approvalerId")+"\","
							+ "\"approvaler\":\""+map.get("approvaler")+"\","
							+ "\"sender\":\""+map.get("sender")+"\","
							+ "\"createTime\":\""+map.get("createTime")+"\","
							+ "\"taskType\":\"outSide\","
							+ "\"points\":[";
					
					for(String point : points){
						//需要把坐标转成百度坐标
						String[] pts = point.split("_");
						if(TextUtil.isNotNull(pts[1]) && TextUtil.isNotNull(pts[2])) {
							Point gcjPoint = MapUtil.db_phone_encrypt(Double.parseDouble(pts[1]),Double.parseDouble(pts[2]));
							jsonString +="{";
							jsonString +="\"num\":\""+pts[0]+"\","
									   + "\"latitude\":\""+gcjPoint.getLat()+"\","
									   	+ "\"longitude\":\""+gcjPoint.getLng()+"\"";
							jsonString +="},";
						}
						
					}
					if(jsonString.endsWith(",")){
						jsonString = jsonString.substring(0,jsonString.length()-1);
					}
					jsonString +="]";
					jsonString +="},";
				}
				if(jsonString.endsWith(",")){
					jsonString = jsonString.substring(0,jsonString.length()-1);
				}
				jsonString +="]";
				jsonString.replaceAll("null", "");
				sendResponse(Integer.valueOf(0), jsonString);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAApprovalTask.getSendHis ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 保存信息
	 * @return
	 */
	public String saveSendHis(){
		try{
			ApprovalTaskPojo object = (ApprovalTaskPojo) getRequestObject(ApprovalTaskPojo.class);
			UserInfoBean user = (UserInfoBean) getInterfaceSession().getAttribute("userBean");
			if(null != object){
				if(object.getTaskState().equals("1")){
					object.setTaskState("处理完成");
				}
				object.setApprovaler(user.getRealname());
				this.approvalService.upApprovalObj(object);
				new statResThread(object.getId()).start();
				sendResponse(Integer.valueOf(0), "success");
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAApprovalTask.saveSendHis ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 得到
	 * @author chenqp
	 *
	 */
	class statResThread extends Thread{
		private Integer taskId;
		public statResThread(Integer taskId) {
			this.taskId = taskId;
		}
		@Override
		public void run() {
			ApprovalTaskPojo task = approvalService.getApprovalObj(taskId+"");
			List<Map<String, Object>> collectList = approvalService.getCollectList(taskId+"");
			//判断下是否存在数据
			if(TextUtil.isNull(collectList)) {
				String json = approvalService.getMapStr(task, "now");
				Integer segNum = 0;
				Integer pointNum = 0;
				String eids = "";
				List<Map<String, Object>> list =JsonUtil.getList4Json(json, Map.class);
				for(int i=0;i<list.size();i++) {
					Map<String, Object> map = list.get(i);
					if(map.get("id")!=null) {
						segNum++;
					}
					if(map.get("start")!=null) {
						MorphDynaBean start = (MorphDynaBean) map.get("start");
						if(start.get("type").equals("optical")) {
							eids +="'"+start.get("id")+"',";
						}
						pointNum++;
						
					}
					if(map.get("end")!=null) {
						MorphDynaBean end = (MorphDynaBean) map.get("end");
					}
				}
				task.setSegNum(segNum+"");
				task.setPointNum(pointNum+"");
				approvalService.upApprovalObj(task);
				approvalService.batchResCollect(list,taskId+"");
				if(TextUtil.isNotNull(eids)) {
					approvalService.batchEqutUpdate(task.getBelongCmp(), eids);
				}
			}
			super.run();
		}
		
		public String rePattern(String myString){  
	        String newString=null;  
	        Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");  
	        Matcher m = CRLF.matcher(myString);  
	        if (m.find()) {  
	          newString = m.replaceAll("<br>");  
	        }  
	        return newString;  
	    }
	}
	
	
	/**
	 * 得到工单详情页面
	 * @return
	 */
	public String getApprovalObj(){
		try{
			ApprovalTaskPojo object = (ApprovalTaskPojo) getRequestObject(ApprovalTaskPojo.class);
			if(null != object){
				object = this.approvalService.getApprovalObj(object.getId()+"");
				if(TextUtil.isNotNull(object.getFlowName()) && object.getFlowName().equals("send")) {
					String approvaler = object.getApprovaler();
					object.setApprovaler(object.getAuditer());
					object.setSender(approvaler);
				}
				if(object.getTaskState().equals("驳回")) {
					ApprovalResRejectPojo rejObj = new ApprovalResRejectPojo();
					rejObj.setTaskId(object.getId()+"");
					List<ApprovalResRejectPojo> rejList = this.approvalService.getResReject(rejObj);
					if(TextUtil.isNotNull(rejList)) {
						String rejStr = "";
						for(ApprovalResRejectPojo obj : rejList) {
							rejStr +="资源:"+obj.getResName()+";驳回原因："+obj.getRejectStr()+"!";
						}
						object.setApprovalAdvice(rejStr);
					}
				}
				sendResponse(Integer.valueOf(0), object);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAApprovalTask.getApprovalObj ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 统计下该工单下的资源数据
	 * @author chenqp
	 *
	 */
	class countApprovalList extends Thread{
		private ApprovalTaskPojo obj;
		public countApprovalList(ApprovalTaskPojo obj){
			this.obj = obj;
		}
		@Override
		public void run() {
			String[] resTypes=obj.getResType().split(",");
			int sum = 0;
			for(String resType : resTypes){
				List<Map<String, Object>> list = approvalService.getResGrid(obj, resType);
				saveApprovalList(list, resType,obj.getId());
				sum +=list.size();
			}
			obj.setResSum(sum);
			approvalService.upApprovalObj(obj);
			super.run();
		}
	}
	
	/**
	 * 保存列表
	 * @param list
	 * @param resType
	 */
	void saveApprovalList(List<Map<String, Object>> list,String resType,Integer taskId){
		Integer addNum = 0 ;
		Integer updateNum = 0;
		Integer delNum = 0;
		
		String addIds = "";
		String updateIds = "";
		String delIds = "";
		List<ApprovalListPojo> approvalList = new LinkedList();
		ApprovalListPojo addPojo = new ApprovalListPojo();
		ApprovalListPojo updatePojo = new ApprovalListPojo();
		ApprovalListPojo delPojo = new ApprovalListPojo();
		//将现有的资源进行分类梳理
		for(Map<String, Object> map : list){
			String resNum = map.get("resNum")+"";
			String deletedFlag = map.get("deletedFlag")+"";
			String updateTime = map.get("updateTime")+"";
			//判断资源
			String type = "";
			if((resNum.equals("null") || resNum.equals("")) && deletedFlag.equals("0")){
				type ="新增";
				addNum++;
				addIds +=map.get("resCode")+",";
			}
			if((!(resNum.equals("null")) && !(resNum.equals(""))) && deletedFlag.equals("0") && !(updateTime.equals("null"))){
				type ="修改";
				updateNum++;
				updateIds +=map.get("resCode")+",";
			}
			if((!(resNum.equals("null")) && !(resNum.equals(""))) && deletedFlag.equals("1") && !(updateTime.equals("null"))){
				type ="删除";
				delNum++;
				delIds +=map.get("resCode")+",";
			}
			if(TextUtil.isNull(type)){
				type = "已同步";
			}
		}
		//新增的数据
		if(TextUtil.isNotNull(addIds)){
			addPojo.setType(resType);
			addPojo.setMotion("新增");
			addPojo.setRequestId(addIds);
			addPojo.setTaskId(taskId+"");
			approvalList.add(addPojo);
		}
		//修改的数据
		if(TextUtil.isNotNull(updateIds)){
			updatePojo.setType(resType);
			updatePojo.setMotion("修改");
			updatePojo.setRequestId(updateIds);
			updatePojo.setTaskId(taskId+"");
			approvalList.add(updatePojo);
		}
		//删除的数据
		if(TextUtil.isNotNull(delIds)){
			delPojo.setType(resType);
			delPojo.setMotion("删除");
			delPojo.setRequestId(delIds);
			delPojo.setTaskId(taskId+"");
			approvalList.add(delPojo);
		}
		//将数据进行入库操作
		if(TextUtil.isNotNull(approvalList)){
			approvalService.batchAddApprovalList(approvalList);
		}
	}
	
	
	/**
	 * 得到驳回的资源
	 * @return
	 */
	public String getResReject(){
		try{
			ApprovalResRejectPojo obj = (ApprovalResRejectPojo) this.getRequestObject(ApprovalResRejectPojo.class);
			if(obj != null){
				List<ApprovalResRejectPojo> list = this.approvalService.getResReject(obj);
				sendResponse(Integer.valueOf(0),list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}
	
	public IapprovalTaskService getApprovalService() {
		return approvalService;
	}
	public void setApprovalService(IapprovalTaskService approvalService) {
		this.approvalService = approvalService;
	}
}
