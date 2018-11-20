package interfaces.pdainterface.transmissionEquipment.action;

import java.util.List;

import org.apache.log4j.Logger;

import base.exceptions.DataAccessException;
import base.util.MapUtil;
import base.util.TextUtil;
import base.util.functions;
import base.util.pojo.Point;
import base.web.InterfaceAction;
import interfaces.irmsInterface.interfaces.outLine.service.impl.IirmsOutLineService;
import manage.buriedPart.service.impl.IburiedPartService;

import manage.transmissionEquipment.pojo.TransmissionEquipmentBean;
import manage.transmissionEquipment.service.impl.ItransmissionEquipmentService;

public class PDATransmissionEquipment extends InterfaceAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4009767614645442985L;
	private static final Logger log = Logger.getLogger(PDATransmissionEquipment.class);
	private ItransmissionEquipmentService equipmentService;
	private IirmsOutLineService irmsOutLineService;
	/**
	 * 得到传输设备列表
	 * @return
	 */
	public String getEquipment(){
		try{
			TransmissionEquipmentBean obj = (TransmissionEquipmentBean) getRequestObject(TransmissionEquipmentBean.class);
			if(TextUtil.isNull(obj.getEquipmentId()) && TextUtil.isNotNull(obj.getLongitude()) && TextUtil.isNotNull(obj.getLatitude())){
				if(isWGS){
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()));
					obj.setLatitude(point.getLat() + "");
					obj.setLongitude(point.getLng() + "");
				}
				double[] arr = functions.getAround(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()), ((this.start/this.limit)+1)*Integer.parseInt(properties.getValueByKey("gisLength")));
			/*	obj.setLats(String.valueOf(arr[0]));
				obj.setLons(String.valueOf(arr[1]));
				obj.setLate(String.valueOf(arr[2]));
				obj.setLone(String.valueOf(arr[3]));*/
			}else{
				obj.setStart(this.start);
				obj.setLimit(this.limit);
			}
			/*if(TextUtil.isNull(obj.getBuriedId()) && TextUtil.isNull(obj.getStoneId()) && TextUtil.isNotNull(super.areaName)){
				obj.setStoneArea(super.areaName);
			}*/
			List<TransmissionEquipmentBean> list = equipmentService.getTransmissionEquipmentGrid(obj);
			if(list !=null){
				for(TransmissionEquipmentBean equip : list){
					if(isWGS && TextUtil.isNotNull(equip.getLatitude()) && TextUtil.isNotNull(equip.getLongitude())){
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(equip.getLatitude()), Double.parseDouble(equip.getLongitude()));
						equip.setLatitude(point.getLat()+"");
						equip.setLongitude(point.getLng()+"");
					}
				}
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("transmissionEquipment.getTransmissionEquipmentGrid ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	/**
	 * 得到传输设备信息（详情）
	 * @return
	 */
	public String getTransObj(){
		try{
			TransmissionEquipmentBean equip = (TransmissionEquipmentBean) getRequestObject(TransmissionEquipmentBean.class);
			if(equip != null){
				equip = this.equipmentService.getTransmissionEquipmentObj(equip);
				sendResponse(Integer.valueOf(0), equip);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("transmissionEquipment.getEquipment ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 修改传输设备
	 * @return
	 */
	public String updateTrans(){
		try{
			TransmissionEquipmentBean equip = (TransmissionEquipmentBean) getRequestObject(TransmissionEquipmentBean.class);
			int result;
			if(TextUtil.isNotNull(equip.getEquipmentId())){
//			equip.setLastUpTime(this.invokTime);
//				equip.setLastUpMan(this.longiner);
//				equip.setMaintainer(this.longiner);更改修改的信息
				result= this.equipmentService.updateTransmissionEquipment(equip);
				sendResponse(Integer.valueOf(0), "修改成功。");
			}else{
				//equip.setCreater(this.longiner);
				equip.setCreateTime(this.invokTime);
//				result=this.buriedService.insertBuried(buried);
//				equip.setEquipmentId(result);
				sendResponse(Integer.valueOf(0),equip);
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("transmissionEquipment.updateTrans ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 添加传输设备
	 * @return
	 */
	public String insertTransmissionEquipment(){
		try{
			TransmissionEquipmentBean obj = (TransmissionEquipmentBean) getRequestObject(TransmissionEquipmentBean.class);
/*			if(this.checkTransmission(obj.getEquipmentLable()) > 0){
				sendResponse(Integer.valueOf(2), "传输设备名称已占用。");
			}else{
				if(isWGS && TextUtil.isNotNull(obj.getLatitude()) && TextUtil.isNotNull(obj.getLongitude())){
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()));
	       			obj.setLatitude(point.getLat()+"");
	       			obj.setLongitude(point.getLng()+"");
				}
				*/
				obj.setCreator(this.longiner);
//				if(TextUtil.isNotNull(super.realName)){
//					obj.setMaintainer(super.realName);
//				}
				int result = equipmentService.insertTransmissionEquipment(obj);
//				int result = stoneService.insertStone(obj);
				obj.setEquipmentId(result);
//				if(isWGS){
//					Point point = MapUtil.db_phone_encrypt(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()));
//					obj.setLatitude(point.getLat()+"");
//					obj.setLongitude(point.getLng()+"");
//				}
				sendResponse(Integer.valueOf(0), obj);
				//增加一个标石
//				if(toIrms){
//					new addStoneThread(obj.getStoneId()).start();
//				}
//			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("transmissionEquipment.insertTransmissionEquipment ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	public ItransmissionEquipmentService getEquipmentService() {
		return equipmentService;
	}

	public void setEquipmentService(ItransmissionEquipmentService equipmentService) {
		this.equipmentService = equipmentService;
	}

	/**
	 * 检查传输设备
	 * @param name
	 * @return
	 */
	public Integer checkTransmission(String name){
		String sql = "select count(*) from transmissionEquipmentinfo where equipment_name='"+name+"' and stateflag='0'";
		int size = this.getJdbcTemplate().queryForInt(sql);
		return size;
	}
	/**
	 * 继续增加传输设备的类
	 * @author chenqp
	 *
	 */
	class addStoneThread extends Thread{
		private Integer equipmentId;
		public addStoneThread(Integer equipmentId){
			this.equipmentId = equipmentId;
		}
		public void run() {
			TransmissionEquipmentBean equipment = equipmentService.getTransmissionEquipmentById(equipmentId);
//			irmsOutLineService.addStone(equipment);//有问题？？
			super.run();
		}
	}
	
	/**
	 * 删除对应的传输设备（假删除）
	 * @param obj
	 */
//	public void deleteTrans(TransmissionEquipmentBean obj){
//		String sql ="update transmissionEquipmentinfo set stateflag ='1'"
//				+ " where  int_id ='"+obj.getEquipmentId()+"'";
//		this.getJdbcTemplate().execute(sql);
//	}
	
	/**
	 * 彻底删除
	 * @return
	 */
	public String deleteSupportingRing() {
		try {
			TransmissionEquipmentBean equip = (TransmissionEquipmentBean) getRequestObject(TransmissionEquipmentBean.class);
			if (equip != null) {
				if(TextUtil.isNotNull(equip.getEquipmentId())){
					this.equipmentService.deleteSupportingRing(equip);
					sendResponse(Integer.valueOf(0), "删除成功。");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (DataAccessException e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("transmissionEquipment.deleteSupportingRing ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 更新删除标志
	 * @return
	 */
	public String deleteTrans() {
		try {
			TransmissionEquipmentBean equip = (TransmissionEquipmentBean) getRequestObject(TransmissionEquipmentBean.class);
			if (equip != null) {
				if(TextUtil.isNotNull(equip.getEquipmentId())){
					this.equipmentService.updateDeleteFlag(equip);
					sendResponse(Integer.valueOf(0), "删除成功。");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (DataAccessException e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("transmissionEquipment.updateDeleteFlag ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	public IirmsOutLineService getIrmsOutLineService() {
		return irmsOutLineService;
	}

	public void setIrmsOutLineService(IirmsOutLineService irmsOutLineService) {
		this.irmsOutLineService = irmsOutLineService;
	}
	public static Logger getLog() {
		return log;
	}
}
