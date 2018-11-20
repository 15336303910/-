package interfaces.pdainterface.buriedPart.action;


import interfaces.irmsInterface.interfaces.outLine.service.impl.IirmsOutLineService;

import java.util.List;

import manage.buriedPart.pojo.BuriedPartObj;
import manage.buriedPart.service.BuriedPartService;
import manage.buriedPart.service.impl.IburiedPartService;
import manage.stone.pojo.StoneInfoBean;
import manage.stone.service.impl.IstoneInfoService;

import org.apache.log4j.Logger;

import base.util.TextUtil;
import base.web.InterfaceAction;

public class PDABuriedPart extends InterfaceAction{
	private static final Logger log = Logger.getLogger(PDABuriedPart.class);
	private IstoneInfoService stoneService;
	private IburiedPartService buriedPartServie;
	private IirmsOutLineService irmsOutLineService;
	
	/**
	 * 得到所有的直埋段
	 * @return
	 */
	public String getBuriedPart(){
		try{
			BuriedPartObj obj = (BuriedPartObj) getRequestObject(BuriedPartObj.class);
			obj.setStart(this.start);
			obj.setLimit(this.limit);
			if(TextUtil.isNull(obj.getBuriedId()) && TextUtil.isNull(obj.getId()) && TextUtil.isNotNull(super.areaName)){
				obj.setBuriedPartArea(super.areaName);
			}
			List<BuriedPartObj> list = buriedPartServie.getBuriedPartGrid(obj);
			if(list != null){
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDABuriedPart.getBuriedPart ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 查询直埋段信息单
	 * 独独立出来在于敷
	 * 设信息
	 * 提供  id 
	 * @return
	 */
	public String getBuriedPartObj(){
		try{
			BuriedPartObj obj = (BuriedPartObj) getRequestObject(BuriedPartObj.class);
			if(obj != null){
				obj = buriedPartServie.getBuriedPartPojo(obj);
				sendResponse(Integer.valueOf(0), obj);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDABuriedPart.getBuriedPartObj ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 修改直埋段
	 * @return
	 */
	public String updateBuriedPart(){
		try{
			BuriedPartObj obj = (BuriedPartObj) getRequestObject(BuriedPartObj.class);
			obj.setLastUpMan(this.longiner);
			obj.setLastUpTime(this.invokTime);
			obj = this.buriedPartServie.setBuriedPartLength(obj);
			if(TextUtil.isNotNull(obj.getDeleteflag()) && obj.getDeleteflag().equals("1")){
				//删除信息
				BuriedPartObj buriedObj = this.buriedPartServie.getBuriedPartPojo(obj);
				buriedObj = this.buriedPartServie.getBuriedlay(buriedObj);
				if(TextUtil.isNotNull(buriedObj.getBuriedPartOpticalId()) && !super.forceDel){
					//存在敷设信息
					sendResponse(Integer.valueOf(1), "存在敷设请勿删除");
				}else{
					//不存在敷设信息
					if(TextUtil.isNotNull(super.realName)){
						obj.setMaintainer(super.realName);
					}
					int result = buriedPartServie.updateBuriedPart(obj);
					sendResponse(Integer.valueOf(0), "修改成功。");
					//同步综资
					if(toIrms){
						new upBuriedPardThread(obj.getId()).start();
					}
				}
			}else{
				if(TextUtil.isNotNull(super.realName)){
					obj.setMaintainer(super.realName);
				}
				int result = buriedPartServie.updateBuriedPart(obj);
				sendResponse(Integer.valueOf(0), "修改成功。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDABuriedPart.updateBuriedPart ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 修改直埋段数据
	 * @author chenqp
	 *
	 */
	class upBuriedPardThread extends Thread{
		private Integer buriedPartId;
		public upBuriedPardThread(Integer buriedPartId){
			this.buriedPartId = buriedPartId;
		}
		@Override
		public void run() {
			try{
				BuriedPartObj obj = new BuriedPartObj();
			    obj.setId(buriedPartId);
			    obj = buriedPartServie.getBuriedPartPojo(obj);
			    irmsOutLineService.upBuried(obj);    
			}catch(Exception e){
				e.printStackTrace();
			}
			super.run();
		}
	}
	
	/**
	 * 新增直埋段
	 * @return
	 */
	public String insertBuriedPart(){
		try{
			BuriedPartObj obj = (BuriedPartObj) getRequestObject(BuriedPartObj.class);
			Integer size = this.buriedPartServie.getBuriedPartCount(obj);
			if(size != 0){
				sendResponse(Integer.valueOf(2), "资产标签被占用。");
			}else{
				if(TextUtil.isNull(obj.getBuriedPartName()) || TextUtil.isNull(obj.getBuriedPartStartId()) || TextUtil.isNull(obj.getBuriedPartEndId())){
					sendResponse(Integer.valueOf(2), "请填写必填字段。");
				}else{
					obj = this.buriedPartServie.setBuriedPartLength(obj);
					obj.setCreater(this.longiner);
					obj.setCreateTime(this.invokTime);
					if(TextUtil.isNotNull(super.realName)){
						obj.setMaintainer(super.realName);
					}
					if(TextUtil.isNull(obj.getBuriedPartArea())){
						StoneInfoBean stone = new StoneInfoBean();
						stone = stoneService.getStoneById(Integer.parseInt(obj.getBuriedPartStartId()));
						obj.setBuriedPartArea(stone.getStoneArea());
					}
					int result = buriedPartServie.insertBuriedPart(obj);
					obj.setId(result);
					sendResponse(Integer.valueOf(0),obj);
					
					//是否派送到综资
					if(toIrms){
						new addBuriedPart(result).start();
					}
				}
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDABuriedPart.insertBuriedPart ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 
	 * @author chenqp
	 *
	 */
	class addBuriedPart extends Thread{
		private Integer buriedPartId;
		public addBuriedPart(Integer buriedPartId){
			this.buriedPartId = buriedPartId;
		}
		@Override
		public void run() {
			try{
				BuriedPartObj obj = new BuriedPartObj();
				obj.setId(buriedPartId);
				obj = buriedPartServie.getBuriedPartPojo(obj);
				irmsOutLineService.addBuried(obj);
			}catch(Exception e){
				e.printStackTrace();
			}
			super.run();
		}
	}
	public IburiedPartService getBuriedPartServie() {
		return buriedPartServie;
	}
	public void setBuriedPartServie(IburiedPartService buriedPartServie) {
		this.buriedPartServie = buriedPartServie;
	}
	public static Logger getLog() {
		return log;
	}
	public IirmsOutLineService getIrmsOutLineService() {
		return irmsOutLineService;
	}
	public void setIrmsOutLineService(IirmsOutLineService irmsOutLineService) {
		this.irmsOutLineService = irmsOutLineService;
	}
	public IstoneInfoService getStoneService() {
		return stoneService;
	}
	public void setStoneService(IstoneInfoService stoneService) {
		this.stoneService = stoneService;
	}
}
