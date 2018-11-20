package interfaces.pdainterface.resPoint.action;

import interfaces.irmsInterface.interfaces.station.service.impl.IirmsStationService;

import java.util.List;

import manage.resPoint.pojo.WirelessPointPojo;
import manage.resPoint.service.impl.IwirelessService;

import org.apache.log4j.Logger;

import base.util.MapUtil;
import base.util.TextUtil;
import base.util.functions;
import base.util.pojo.Point;
import base.web.InterfaceAction;

/**
 * pdaWirelessPoint!
 * @author chenqp
 *
 */
public class PDAWirelessPoint extends InterfaceAction{

	private static final Logger log = Logger.getLogger(PDAWirelessPoint.class);
	private IwirelessService wirelessService;
	private IirmsStationService irmsStationService;
	
	/**
	 * 得到所属区域内
	 * 的无线网络资源点
	 * @return
	 */
	public String getWirelessPoint(){
		try{
			WirelessPointPojo obj = (WirelessPointPojo) getRequestObject(WirelessPointPojo.class);
			if(TextUtil.isNotNull(obj.getLongitude()) && TextUtil.isNotNull(obj.getLatitude())){
				if(isWGS){
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()));
					obj.setLatitude(point.getLat() + "");
					obj.setLongitude(point.getLng() + "");
				}
				double[] arr = functions.getAround(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()), ((this.start/this.limit)+1)*Integer.parseInt(properties.getValueByKey("gisLength")));
				obj.setLats(String.valueOf(arr[0]));
				obj.setLons(String.valueOf(arr[1]));
				obj.setLate(String.valueOf(arr[2]));
				obj.setLone(String.valueOf(arr[3]));
			}else{
				obj.setStart(this.start);
				obj.setLimit(this.limit);
			}
			if(TextUtil.isNotNull(super.areaName)){
				if(super.areaName.contains(",")){
					obj.setWirelessArea(super.areaName.split(",")[0]);
				}else{
					obj.setWirelessArea(super.areaName);
				}
			}
			List<WirelessPointPojo> list = wirelessService.getWirelessGrid(obj);
			if(TextUtil.isNotNull(list)){
				for(WirelessPointPojo pojo : list){
					if(isWGS && TextUtil.isNotNull(pojo.getLatitude()) && TextUtil.isNotNull(pojo.getLongitude())){
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(pojo.getLatitude()), Double.parseDouble(pojo.getLongitude()));
						pojo.setLatitude(point.getLat()+"");
						pojo.setLongitude(point.getLng()+"");
					}
				}
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(0), "查无数据。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("pdaWirelessPoint.getWirelessPoint ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 新增网络资源点
	 * @return
	 */
	public String addWirelessPoint(){
		try{
			WirelessPointPojo obj = (WirelessPointPojo) getRequestObject(WirelessPointPojo.class);
			if(TextUtil.isNotNull(obj.getLongitude()) && TextUtil.isNotNull(obj.getLatitude())){
				if(isWGS){
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()));
					obj.setLatitude(point.getLat() + "");
					obj.setLongitude(point.getLng() + "");
				}
			}
			obj.setCreater(this.longiner);
			if(TextUtil.isNotNull(super.realName)){
				obj.setMaintainer(super.realName);
			}
			int result = this.wirelessService.addWireless(obj);
			obj.setWirelessId(result);
			if(isWGS){
				Point point = MapUtil.db_phone_encrypt(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()));
				obj.setLatitude(point.getLat()+"");
				obj.setLongitude(point.getLng()+"");
			}
			sendResponse(Integer.valueOf(0), obj);
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("pdaWirelessPoint.addWirelessPoint ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	
	/**
	 * 删除网络资源点
	 * @return
	 */
	public String delWirelessPoint(){
		try{
			WirelessPointPojo obj = (WirelessPointPojo) getRequestObject(WirelessPointPojo.class);
			if(TextUtil.isNotNull(obj.getLongitude()) && TextUtil.isNotNull(obj.getLatitude())){
				if(isWGS){
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()));
					obj.setLatitude(point.getLat() + "");
					obj.setLongitude(point.getLng() + "");
				}
			}
			if(TextUtil.isNotNull(super.realName)){
				obj.setMaintainer(super.realName);
			}
			obj.setDeleteflag("1");
			this.wirelessService.updateWireless(obj);
			sendResponse(Integer.valueOf(0), "删除成功。");
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("pdaWirelessPoint.delWirelessPoint ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 修改网络资源点
	 * @return
	 */
	public String updateWirelessPoint(){
		try{
			WirelessPointPojo obj = (WirelessPointPojo) getRequestObject(WirelessPointPojo.class);
			if(TextUtil.isNotNull(obj.getLongitude()) && TextUtil.isNotNull(obj.getLatitude())){
				if(isWGS){
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()));
					obj.setLatitude(point.getLat() + "");
					obj.setLongitude(point.getLng() + "");
				}
			}
			if(TextUtil.isNotNull(super.realName)){
				obj.setMaintainer(super.realName);
			}
			if(TextUtil.isNull(obj.getWirelessId())){
				int result = this.wirelessService.addWireless(obj);
				obj.setWirelessId(result);
				sendResponse(Integer.valueOf(0), "修改成功。");
				
			}else{
				this.wirelessService.updateWireless(obj);
				sendResponse(Integer.valueOf(0), "修改成功。");
				new UpresPoint(obj.getWirelessId()).start();
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("pdaWirelessPoint.updateWirelessPoint ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 更改网络资源点
	 * @author chenqp
	 *
	 */
	class UpresPoint extends Thread{
		private Integer wirelessId;
		public UpresPoint (Integer wirelessId){
			this.wirelessId = wirelessId;
		}
		@Override
		public void run() {
			if(toIrms){
				WirelessPointPojo obj = new WirelessPointPojo();
				obj.setWirelessId(wirelessId);
				obj = wirelessService.getWirelessObj(obj);
				//移动网络资源点
				irmsStationService.moveResPoint(obj);
			}
			super.run();
		}
	}
	public IwirelessService getWirelessService() {
		return wirelessService;
	}
	public IirmsStationService getIrmsStationService() {
		return irmsStationService;
	}
	public void setIrmsStationService(IirmsStationService irmsStationService) {
		this.irmsStationService = irmsStationService;
	}
	public void setWirelessService(IwirelessService wirelessService) {
		this.wirelessService = wirelessService;
	}
}
