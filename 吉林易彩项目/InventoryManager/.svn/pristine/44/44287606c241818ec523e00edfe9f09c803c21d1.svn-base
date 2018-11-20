package manage.approval.web;

import interfaces.irmsInterface.interfaces.station.service.impl.ISyncSiteDataService;
import interfaces.pdainterface.lineSystem.pojo.LinePointInfo;
import interfaces.pdainterface.lineSystem.pojo.LineSegmentInfo;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import manage.approval.pojo.ApprovalListPojo;
import manage.approval.pojo.ApprovalMapPojo;
import manage.approval.pojo.ApprovalResPojo;
import manage.approval.pojo.ApprovalTaskPojo;
import manage.approval.service.impl.IapprovalTaskService;
import manage.user.pojo.UserInfoBean;

import org.apache.log4j.Logger;

import base.util.JsonUtil;
import base.util.PolygonArea;
import base.util.ResUtil;
import base.util.SmsUtil;
import base.util.TextUtil;
import base.web.PaginationAction;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 流程处理工具类
 * @author chenqp
 *
 */
public class ApprovalSendAction extends PaginationAction implements ModelDriven{
	private int length = 15;
	private static final Logger log = Logger.getLogger(ApprovalTaskAction.class);
	private ApprovalTaskPojo object = new ApprovalTaskPojo();
	private IapprovalTaskService approvalService;
	private ISyncSiteDataService syncSiteDataService;
	private String jsonString;
	private java.io.File file;
	private String fileFileName;
	
	
	/**
	 * 得到多边形面积
	 */
	public void polygonArea(){
		try{
			List<double[]> points = new ArrayList<double[]>(); 
			String str = this.getRequest().getParameter("str");
			String[] s1 = str.split(";");   
			for(String ss : s1 ) {    
				String[] temp = ss.split(",");    
				double[] point = {Double.parseDouble(temp[0]) , Double.parseDouble(temp[1])};    
				points.add(point);    
			}   
			PolygonArea tp = new PolygonArea();   
			String result = tp.calculateArea(points); 
			jsonString=result;
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 加载资源
	 */
	public void loadRes(){
		try{
			String lat = this.getRequest().getParameter("lat");
			String lon = this.getRequest().getParameter("lon");
			String resType = this.getRequest().getParameter("resType");
			LinePointInfo line = new LinePointInfo();
			line.setLongitude(lon);
			line.setLatitude(lat);
			List<LineSegmentInfo> list = new LinkedList<LineSegmentInfo>();
			if(resType.contains(",")){
				if(resType.contains("well")){
					LinePointInfo wellObj =(LinePointInfo) line.clone();
					wellObj.setSysType(2);
					List<LineSegmentInfo> wellList = this.approvalService.getPointList(wellObj);
					list.addAll(wellList);
				}
				if(resType.contains("pole")){
					LinePointInfo poleObj = (LinePointInfo)line.clone();
					poleObj.setSysType(0);
					List<LineSegmentInfo> poleList = this.approvalService.getPointList(poleObj);
					list.addAll(poleList);
				}
				if(resType.contains("stone")){
					LinePointInfo stoneObj = (LinePointInfo)line.clone();
					stoneObj.setSysType(1);
					List<LineSegmentInfo> stoneList =this.approvalService.getPointList(stoneObj);
					list.addAll(stoneList);
				}
				for(LineSegmentInfo seg : list){
					seg.setSegType("0");
				}
			}
			if(resType.contains("equt")){
				LinePointInfo equtObj = (LinePointInfo) line.clone();
				equtObj.setSysType(4);
				List<LineSegmentInfo> equtList = this.approvalService.getPointList(equtObj);
				list.addAll(equtList);
			}
			if(resType.contains("optiTermainal")){
				LinePointInfo optObj = (LinePointInfo) line.clone();
				optObj.setSysType(5);
				List<LineSegmentInfo> optList = this.approvalService.getPointList(optObj);
				list.addAll(optList);
			}
			if(resType.contains("station")){
				LinePointInfo siteObj = (LinePointInfo) line.clone();
				siteObj.setSysType(6);
				List<LineSegmentInfo> siteList = this.approvalService.getPointList(siteObj);
				list.addAll(siteList);
			}
			String jsonStr = ResUtil.getWebGisSeg(list,0,false);
			jsonString = "{info:";
			jsonString += jsonStr;
			jsonString +="}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 派发工单
	 */
	public void payoutWork(){
		try{
			String pointStr = this.getRequest().getParameter("pointList");
			String resStr =  this.getRequest().getParameter("resList");
			String siteList = this.getRequest().getParameter("siteList");
			UserInfoBean userInfoBean=(UserInfoBean)getRequest().getSession().getAttribute("userBean");
			object.setSender(userInfoBean.getRealname());
			object.setSendId(userInfoBean.getUserId()+"");
			object.setFlowName("send");
			object.setFlowId(this.getFlowId()+"");
			object.setTaskState("已派发");
			if(TextUtil.isNull(object.getGrabType())) {
				object.setGrabType("简易清查");
			}
			if(object.getResType().equals("station")){
				object.setTaskState("已派发");
				object.setResType("基站");
			}
			if(TextUtil.isNull(object.getCity())) {
				object.setCity("北京");
			}
			
			int taskId =this.approvalService.addApproval(object);
			//点区域
			if(TextUtil.isNotNull(pointStr) && !(pointStr.equals("[]"))){
				List<Map<String, Object>> pointList = JsonUtil.getList4Json(pointStr, Map.class);
				List<Map<String, Object>> resList = JsonUtil.getList4Json(resStr, Map.class);
				this.approvalService.upApprovalTask(taskId+"", pointList);
				//保存画的几个点
				new saveMap(pointList, taskId).start();
				new savePoint(resList, taskId).start();
				new delZone(taskId+"",pointList).start();
			}
			//站点资源
			if(TextUtil.isNotNull(siteList) && !(siteList.equals("[]"))){
				List<Map<String, Object>> stationList = JsonUtil.getList4Json(siteList, Map.class);
				new savePoint(stationList, taskId).start();
			}
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 得到划定的资源数据
	 */
	public void getPointNum() {
		try {
			String pointStr = this.getRequest().getParameter("pointList");
			String title = this.getRequest().getParameter("title");
			List<Map<String, Object>> taskList = approvalService.getTaskList(title);
			if(TextUtil.isNotNull(taskList)) {
				this.printString("工单标题", null);
			}else {
				int num = 0;
				if(TextUtil.isNotNull(pointStr)) {
					List<Map<String, Object>> pointList = JsonUtil.getList4Json(pointStr, Map.class);
					String pointNum =  approvalService.getPolonObj(pointList,"point");
					num =pointNum.split(",").length;
				}
				this.printString(num+"", null);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	class delZone extends Thread{
		private String taskId;
		private List<Map<String, Object>> pointList;
		public delZone(String taskId,List<Map<String, Object>> pointList){
			this.taskId = taskId;
			this.pointList = pointList;
		}
		@Override
		public void run() {
			approvalService.sendDelZone(taskId,pointList);
			//查询点
			String pointId = approvalService.getPolonObj(pointList,"point");
			String lineId = approvalService.getPolonObj(pointList,"line");
			if(TextUtil.isNotNull(pointId)) {
				String result = approvalService.sendDelZone(taskId, pointList, pointId,lineId);
			}
			super.run();
		}
	}
	
	
	
	/**
	 * 保存画的点资源信息
	 * @author chenqp
	 *
	 */
	class saveMap extends Thread{
		private List<Map<String, Object>> pList;
		private int taskId;
		public saveMap(List<Map<String, Object>> pList,int taskId){
			this.taskId = taskId;
			this.pList = pList;
		}
		@Override
		public void run() {
			List<ApprovalMapPojo> list = new LinkedList();
			for(int i=0;i<pList.size();i++){
				ApprovalMapPojo mapObj = new ApprovalMapPojo();
				Map<String,Object> startMap = pList.get(i);
				mapObj.setTaskId(taskId+"");
				mapObj.setPointNum(startMap.get("num")+"");
				mapObj.setStartLat(startMap.get("latitude")+"");
				mapObj.setStartLon(startMap.get("longitude")+"");
				Map<String,Object> endMap = new LinkedHashMap<String, Object>();
				if(i == pList.size()-1){
					endMap = pList.get(0);
				}else{
					endMap = pList.get(i+1);
				}
				mapObj.setEndLat(endMap.get("latitude")+"");
				mapObj.setEndLon(endMap.get("longitude")+"");
				list.add(mapObj);
			}
			approvalService.batchAddPoint(list);
			super.run();
		}
	}
	
	/**
	 * 保存保留的点
	 * @author chenqp
	 *
	 */
	class savePoint extends Thread{
		private List<Map<String, Object>> pList;
		private int taskId;
		public savePoint(List<Map<String, Object>> pList,int taskId){
			this.taskId = taskId;
			this.pList = pList;
		}
		@Override
		public void run() {
			List<ApprovalResPojo> list = new LinkedList();
			String generId = "";
			for(int i=0;i<pList.size();i++){
				Map<String, Object> resMap = pList.get(i);
				ApprovalResPojo res = new ApprovalResPojo();
				res.setTaskId(taskId+"");
				res.setResId(resMap.get("id")+"");
				res.setResNum(resMap.get("resNum")+"");
				res.setResType(resMap.get("resType")+"");
				res.setLon(resMap.get("lon")+"");
				res.setLat(resMap.get("lat")+"");
				res.setGenerId(resMap.get("generId")+"");
				res.setGenerName(resMap.get("generName")+"");
				res.setGenerNum(resMap.get("generNum")+"");
				res.setResState("0");
				generId = resMap.get("generId")+"";
				list.add(res);
			}
			generId ="23687";
			syncSiteDataService.updatePoint(generId);
			approvalService.batchAddRes(list);
			super.run();
		}
	}
	
	/**
	 * 加载历史资源数据
	 */
	public void loadHisMap(){
		try{
			List<Map<String, Object>> list = this.approvalService.getHisMap(object);
			if(TextUtil.isNotNull(list)){
				jsonString = JsonUtil.getJsonString4List(list);
			}
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 定位地图位置
	 */
	public void locatMap(){
		try{
			String locatArea = this.getRequest().getParameter("locatArea");
			String resType = this.getRequest().getParameter("resType");
			if(resType.equals("station")){
				List<LineSegmentInfo> list = this.approvalService.locatSite(locatArea);
				String jsonStr = ResUtil.getWebGisSeg(list,0,false);
				jsonString = "{info:";
				jsonString += jsonStr;
				jsonString +="}";
			}else{
				Map<String, String> map = this.approvalService.locatMap(locatArea);
				if(null != map && TextUtil.isNotNull(map.get("longitude"))){
					jsonString = JsonUtil.getJsonString4Map(map);
				}
			}
			
			if(TextUtil.isNull(jsonString)){
				jsonString ="";
			}
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 返回结果
	 * @param string
	 * @param contentType
	 * @throws Exception
	 */
	public void printString(String string, String contentType) throws Exception {
		this.getResponse().setHeader("Content-Type","text/html;charset=utf-8");
		this.getResponse().setHeader("Cache-Control", "no-cache");
		this.getResponse().setContentType(contentType);
		PrintWriter pw = this.getResponse().getWriter();
		pw.write(string);
		pw.close();
	}
	
	/**
	 * 得到工单流水号 通过当前时间+随机数*1000
	 * 
	 * @return
	 */
	public Long getFlowId() {
		// 当前时间
		long currentTime = System.currentTimeMillis();
		// 生成随机数
		int randD = (int) (Math.random() * 1000);
		// 相加得到流水号
		long flowId = currentTime + randD;
		return new Long(flowId);
	}
	@Override
	public Object getModel() {
		return object;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public ApprovalTaskPojo getObject() {
		return object;
	}
	public void setObject(ApprovalTaskPojo object) {
		this.object = object;
	}
	public IapprovalTaskService getApprovalService() {
		return approvalService;
	}
	public void setApprovalService(IapprovalTaskService approvalService) {
		this.approvalService = approvalService;
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
	public ISyncSiteDataService getSyncSiteDataService() {
		return syncSiteDataService;
	}
	public void setSyncSiteDataService(ISyncSiteDataService syncSiteDataService) {
		this.syncSiteDataService = syncSiteDataService;
	}

}
