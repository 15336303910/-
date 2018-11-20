package interfaces.pdainterface.device.action;


import java.text.DecimalFormat;
import java.util.List;

import manage.device.pojo.CardInfoBean;
import manage.device.pojo.DeviceInfoBean;
import manage.device.pojo.JumpFiberBean;
import manage.device.pojo.JumpPosBean;
import manage.device.pojo.PointBean;
import manage.device.pojo.PointCommon;
import manage.device.service.impl.IDeviceService;
import manage.device.service.impl.IFiberRackService;

import org.apache.log4j.Logger;

import base.util.TextUtil;
import base.web.InterfaceAction;

public class PDADevice extends InterfaceAction{
	 private static final Logger log = Logger.getLogger(PDADevice.class);
	 private IDeviceService deviceService;
	 private IFiberRackService fiberRackService;
	
	 /**
	   * 得到所有的
	   * 网元设备列表信息
	   * @return
	   */
	  public String getDevice(){
		  try{
			  DeviceInfoBean device = (DeviceInfoBean) getRequestObject(DeviceInfoBean.class);
			  if(device !=null){
				  device.setStart(this.start);
				  device.setLimit(this.limit);
				  List<DeviceInfoBean> list = this.deviceService.getDeviceGrid(device);
				  for(DeviceInfoBean obj : list){
					  if(TextUtil.isNull(obj.getPosX()) || TextUtil.isNull(obj.getPosY())){
						  obj.setPosX(this.getNonce());
						  obj.setPosY(this.getNonce());
					  }
					  if(TextUtil.isNotNull(obj.geteName())){
						  obj.setDeviceName(obj.geteName()+"_"+obj.getDeviceName());
					  }else if(TextUtil.isNull(obj.geteName()) && TextUtil.isNotNull(obj.getRoomName())){
						  obj.setDeviceName(obj.getRoomName()+"_"+obj.getDeviceName());
					  }
				  }
				  sendResponse(Integer.valueOf(0), list);
			  }else{
				  sendResponse(Integer.valueOf(2), "请求参数错误。");  
			  }
		  }catch(Exception e){
			  this.exception = e;
		      sendResponse(Integer.valueOf(3), "应用服务器异常。");
		      log.error("PDADevice.getDevice ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		  }
		  return "success";
	  }
	  
	  /**
	   * 增加网元设备
	   * @return
	   */
	  public String insertDevice(){
		  try{
			  DeviceInfoBean device = (DeviceInfoBean) getRequestObject(DeviceInfoBean.class);
			  if(device !=null){
				  device.setEid(null);
				  int id = this.deviceService.insertDevice(device);
				  if(id <0){
					  sendResponse(Integer.valueOf(1), "机架下存在命名重复设备。");  
				  }else{
					  device.setId(id);
					  //先临时生成板卡和端口信息
					  deviceService.tempDevice(device);
					  sendResponse(Integer.valueOf(0), device);
				  }
			  }else{
				  sendResponse(Integer.valueOf(2), "请求参数错误。");  
			  }
		  }catch(Exception e){
			  this.exception = e;
		      sendResponse(Integer.valueOf(3), "应用服务器异常。");
		      log.error("PDADevice.insertDevice ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		  }
		  return "success";
	  }
	  
	  String getNonce(){
		  double num = Math.random();
		  DecimalFormat  df=new DecimalFormat("#.####");
		  return df.format(num);
	  }
	  
	  /**
	   * 修改网元设备
	   * @return
	   */
	  public String updateDevice(){
		  try{
			  List<DeviceInfoBean> list = (List<DeviceInfoBean>) getRequestObject(DeviceInfoBean.class);
			  if(list !=null){
				  new upDevice(list).start();
				  sendResponse(Integer.valueOf(0), "修改成功");
			  }else{
				  sendResponse(Integer.valueOf(2), "请求参数错误。");  
			  }
		  }catch(Exception e){
			  this.exception = e;
		      sendResponse(Integer.valueOf(3), "应用服务器异常。");
		      log.error("PDADevice.updateDevice ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		  }
		  return "success";
	  }
	  
	  
	  /**
	   * 执行批量落架操作
	   * @author chenqp
	   *
	   */
	  class upDevice extends Thread{
		  private List<DeviceInfoBean> list;
		  public upDevice(List<DeviceInfoBean> list){
			  this.list = list;
		  }
		  public void run() {
			  deviceService.beatchUpDevice(list);
		  };
	  }
	  
	  /**
	   * 得到板卡信息
	   * @return
	   */
	  public String getCard(){
		  try{
			  CardInfoBean card = (CardInfoBean) getRequestObject(CardInfoBean.class);
			  if(card !=null){
				  card.setStart(super.start);
				  card.setLimit(super.limit);
				  List<CardInfoBean> list = this.deviceService.getCardGrid(card);
				  for(CardInfoBean obj : list){
					  PointBean point = new PointBean();
					  point.setCardId(obj.getId());
					  List<PointBean> pointList = deviceService.getPointList(point);
					  obj.setPoints(pointList);
				  }
				  sendResponse(Integer.valueOf(0), list);
			  }else{
				  sendResponse(Integer.valueOf(2), "请求参数错误。");  
			  }
		  }catch(Exception e){
			  this.exception = e;
		      sendResponse(Integer.valueOf(3), "应用服务器异常。");
		      log.error("PDADevice.getCard ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		  }
		  return "success";
	  }
	  
	  /**
	   * 获取传输网元下
	   * 的板卡信息
	   * @return
	   */
	  public String getCardNe(){
		  try{
			  CardInfoBean card = (CardInfoBean) getRequestObject(CardInfoBean.class);
			  if(card !=null){
				  card.setStart(super.start);
				  card.setLimit(super.limit);
				  List<CardInfoBean> list = this.deviceService.getCardGrid(card);
				  sendResponse(Integer.valueOf(0), list);
			  }else{
				  sendResponse(Integer.valueOf(2), "请求参数错误。");  
			  }
		  }catch(Exception e){
			  this.exception = e;
		      sendResponse(Integer.valueOf(3), "应用服务器异常。");
		      log.error("PDADevice.getCard ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		  }
		  return "success";
	  }
	  
	  /**
	   * 增加板卡
	   * @return
	   */
	  public String insertCard(){
		  try{
			  CardInfoBean card = (CardInfoBean) getRequestObject(CardInfoBean.class);
			  if(card !=null){
				  int id = this.deviceService.insertCard(card);
				  if(id <0){
					  sendResponse(Integer.valueOf(1), "板卡名称重复。");  
				  }else{
					  card.setId(id);
					  sendResponse(Integer.valueOf(0), card);
				  }
			  }else{
				  sendResponse(Integer.valueOf(2), "请求参数错误。");  
			  }
		  }catch(Exception e){
			  this.exception = e;
		      sendResponse(Integer.valueOf(3), "应用服务器异常。");
		      log.error("PDADevice.insertCard ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		  }
		  return "success";
	  }
	  
	  /**
	   * 修改板卡
	   * @return
	   */
	  public String updateCard(){
		  try{
			  CardInfoBean card = (CardInfoBean) getRequestObject(CardInfoBean.class);
			  if(card !=null){
				  this.deviceService.updateCard(card);
			  }else{
				  sendResponse(Integer.valueOf(2), "请求参数错误。");  
			  }
		  }catch(Exception e){
			  this.exception = e;
		      sendResponse(Integer.valueOf(3), "应用服务器异常。");
		      log.error("PDADevice.updateCard ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		  }
		  return "success";
	  }
	  
	  /**
	   * 得到端口数据集
	   * @return
	   */
	  public String getPointGrid(){
		  try{
			 PointBean point = (PointBean) getRequestObject(PointBean.class);
			 if(point !=null){
				/* point.setStart(super.start);
				 point.setLimit(super.limit);*/
				 List<PointBean> list = this.deviceService.getPointGrid(point);
				 for(PointBean obj : list){
					 if(TextUtil.isNull(obj.getPointState())){
						 obj.setPointState(1);
					 }
				 }
				 sendResponse(Integer.valueOf(0), list);
			 }else{
				 sendResponse(Integer.valueOf(2), "请求参数错误。");  
			 }
		  }catch(Exception e){
			  this.exception = e;
		      sendResponse(Integer.valueOf(3), "应用服务器异常。");
		      log.error("PDADevice.getPointGrid ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		  }
		  return "success";
	  }
	  
	  /**
	   * 获取端口数据
	   * @return
	   */
	  public String getPoint(){
		  try{
			  PointBean point = (PointBean) getRequestObject(PointBean.class);
			  if(point !=null){
				 List<PointBean> list =  this.deviceService.getPointList(point);
				 if(TextUtil.isNotNull(list)){
					 PointBean obj = list.get(0);
					 sendResponse(Integer.valueOf(0), obj);
				 }else{
					 sendResponse(Integer.valueOf(1), "查无数据。");  
				 }
			  }else{
				  sendResponse(Integer.valueOf(2), "请求参数错误。");  
			  }
		  }catch(Exception e){
			  this.exception = e;
		      sendResponse(Integer.valueOf(3), "应用服务器异常。");
		      log.error("PDADevice.getPoint ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		  }
		  return "success";
	  }
	  
	  /**
	   * 增加端口信息
	   * @return
	   */
	  public String insertPoint(){
		  try{
			  PointBean point = (PointBean) getRequestObject(PointBean.class);
			  if(point != null){
				  int id = this.deviceService.insertPoint(point);
				  if(id <0){
					  sendResponse(Integer.valueOf(1), "存在相同端口");  
				  }else{
					  point.setId(id);
					  sendResponse(Integer.valueOf(0), point);  
				  }
			  }else{
				  sendResponse(Integer.valueOf(2), "请求参数错误。");  
			  }
		  }catch(Exception e){
			  this.exception = e;
		      sendResponse(Integer.valueOf(3), "应用服务器异常。");
		      log.error("PDADevice.insertPoint ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		  }
		  return "success";
	  }
	  
	  
	  /**
	   *  修改端子
	   * @return
	   */
	  public String updatePoint(){
		  try{
			 List<PointBean> list = (List<PointBean>) getRequestObject(PointBean.class);
			 if(list!=null){
				 
			 }else{
				 sendResponse(Integer.valueOf(2), "请求参数错误。"); 
			 }
		  }catch(Exception e){
			  this.exception = e;
		      sendResponse(Integer.valueOf(3), "应用服务器异常。");
		      log.error("PDADevice.updatePoint ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		  }
		  return "success";
	  }
	  
	  /**
	   * 纤芯上架
	   * @return
	   */
	  public String fiberRack(){
		  try{
			  List<PointCommon> list = (List<PointCommon>) getRequestObject(PointCommon.class);
			  if(TextUtil.isNotNull(list)){
				 /* int num = this.fiberRackService.batchFiberRack(list);
				 if(num <0){
					  sendResponse(Integer.valueOf(1), "落架失败。"); 
				  }else{
					  sendResponse(Integer.valueOf(0), "共落架"+num+"根纤芯,"+num+"个端口!"); 
				  }*/
			  }else{
				  sendResponse(Integer.valueOf(2), "请求参数错误。"); 
			  }
		  }catch(Exception e){
			  this.exception = e;
		      sendResponse(Integer.valueOf(3), "应用服务器异常。");
		      log.error("PDADevice.fiberRack ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		  }
		  return "success";
	  }
	  
	  /**
	   * 端子跳纤
	   * @return
	   */
	  public String jumpFiber(){
		  try{
			  List<JumpFiberBean> list = (List<JumpFiberBean>) getRequestObject(JumpFiberBean.class);
			  if(TextUtil.isNotNull(list)){
				  sendResponse(Integer.valueOf(0), "跳纤完成。"); 
				  this.deviceService.jumpFiber(list);
			  }else{
				  sendResponse(Integer.valueOf(2), "请求参数错误。"); 
			  }
		  }catch(Exception e){
			  this.exception = e;
		      sendResponse(Integer.valueOf(3), "应用服务器异常。");
		      log.error("PDADevice.jumpFiber ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		  }
		  return "success";
	  }
	  
	  /**
	   * 跳到分光器
	   * @return
	   */
	  public String jumpPos(){
		  try{
			  JumpPosBean obj = (JumpPosBean) getRequestObject(JumpPosBean.class);
			  if(null != obj){
				  this.deviceService.jumpPos(obj);
				  sendResponse(Integer.valueOf(0), "跳纤完成。");
			  }else{
				  sendResponse(Integer.valueOf(2), "请求参数错误。"); 
			  }
		  }catch(Exception e){
			  this.exception = e;
		      sendResponse(Integer.valueOf(3), "应用服务器异常。");
		      log.error("PDADevice.jumpPos ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		  }
		  return "success";
	  }

	public IDeviceService getDeviceService() {
		return deviceService;
	}
	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}
	public static Logger getLog() {
		return log;
	}
	public IFiberRackService getFiberRackService() {
		return fiberRackService;
	}
	public void setFiberRackService(IFiberRackService fiberRackService) {
		this.fiberRackService = fiberRackService;
	}
}
