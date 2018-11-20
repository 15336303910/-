package interfaces.pdainterface.leadup.action;

import interfaces.irmsInterface.interfaces.outLine.service.impl.IirmsOutLineService;

import java.util.List;

import manage.leadup.pojo.HangWallPojo;
import manage.leadup.pojo.LeadupPojo;
import manage.leadup.pojo.SupportPointPojo;
import manage.leadup.service.impl.IleadupService;

import org.apache.log4j.Logger;

import base.util.MapUtil;
import base.util.TextUtil;
import base.util.functions;
import base.util.pojo.Point;
import base.web.InterfaceAction;

/**
 * 引上段的接口
 * @author chenqp
 *
 */
public class PDALeadupAction extends InterfaceAction{
	private static final Logger log = Logger.getLogger(PDALeadupAction.class);
	private IleadupService leadupService;
	private IirmsOutLineService irmsOutLineService;
	
	/**
	 * 查询所有的引上段信息
	 * @return
	 */
	public String getLeadup(){
		try{
			LeadupPojo obj = (LeadupPojo) getRequestObject(LeadupPojo.class);
			obj.setStart(this.start);
			obj.setLimit(this.limit);
			List<LeadupPojo> list = this.leadupService.getLeadUp(obj);
			if(list!=null){
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("pdaLeadup.getLeadup ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 得到引上段
	 * @return
	 */
	public String getLeadupObj(){
		try{
			LeadupPojo obj = (LeadupPojo) getRequestObject(LeadupPojo.class);
			if(obj != null){
				obj = this.leadupService.getLeadUpObj(obj);
				sendResponse(Integer.valueOf(0), obj);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("pdaLeadup.getLeadupObj ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 增加引上段
	 * @return
	 */
	public String insertLeadup(){
		try{
			LeadupPojo obj = (LeadupPojo) getRequestObject(LeadupPojo.class);
			String length  = this.leadupService.getLeadupLength(obj);
			obj.setLength(length);
			obj.setMaintainer(this.realName);
			int result = this.leadupService.insertLeadup(obj);
			if(result > 0) {
				obj.setId(result);
				sendResponse(Integer.valueOf(0), obj);
				//发送到综资
				if(toIrms){
					new addLeadupThread(result).start();
				}
			}else {
				sendResponse(Integer.valueOf(1), "存在重复引上!");
			}
			
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("pdaLeadup.insertLeadup ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 
	 * @author chenqp
	 *
	 */
	class addLeadupThread extends Thread{
		private Integer id;
		public addLeadupThread(Integer id){
			this.id = id;
		}
		@Override
		public void run() {
			LeadupPojo obj = new LeadupPojo();
			obj.setId(id);
			obj = leadupService.getLeadUpObj(obj);
			irmsOutLineService.addLeadUp(obj);
			super.run();
		}
	}
	
	/**
	 * 修改引上段信息
	 * @return
	 */
	public String updateLeadup(){
		try{
			LeadupPojo obj = (LeadupPojo) getRequestObject(LeadupPojo.class);
			obj.setMaintainer(this.longiner);
			String length  = this.leadupService.getLeadupLength(obj);
			obj.setLength(length);
			if(TextUtil.isNull(obj.getId())){
				int result = this.leadupService.insertLeadup(obj);
				obj.setId(result);
				sendResponse(Integer.valueOf(0), "修改成功。");
			}else{
				if(TextUtil.isNotNull(obj.getDeleteflag()) && obj.getDeleteflag() == 1){
					int num = this.leadupService.getLeadupLayNum(obj);
					if(num >0 && !super.forceDel){
						sendResponse(Integer.valueOf(1), "存在敷设关系请勿删除。");
					}else{
						this.leadupService.updateLeadup(obj);
						sendResponse(Integer.valueOf(0), "修改成功。");
					}
				}else{
					this.leadupService.updateLeadup(obj);
					sendResponse(Integer.valueOf(0), "修改成功。");
				}
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("pdaLeadup.updateLeadup ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 得到所有的
	 * 撑点
	 * @return
	 */
	public String getSupportPoint(){
		try{
			SupportPointPojo obj =(SupportPointPojo) getRequestObject(SupportPointPojo.class);
			if(obj != null){
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
					obj.setMaintArea(super.areaName);
				}
				List<SupportPointPojo> list = this.leadupService.getsPointList(obj);
				for(SupportPointPojo bean : list){
					if(isWGS && TextUtil.isNotNull(bean.getLatitude()) && TextUtil.isNotNull(bean.getLongitude())){
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(bean.getLatitude()), Double.parseDouble(bean.getLongitude()));
						bean.setLatitude(point.getLat()+"");
						bean.setLongitude(point.getLng()+"");
					}
				}
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("pdaLeadup.getSupportPoint ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 修改撑点信息
	 * @return
	 */
	public String upSupportPoint(){
		try{
			SupportPointPojo obj =(SupportPointPojo) getRequestObject(SupportPointPojo.class);
			if(obj !=null){
				if(isWGS && TextUtil.isNotNull(obj.getLatitude()) && TextUtil.isNotNull(obj.getLongitude())){
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()));
					obj.setLatitude(point.getLat()+"");
					obj.setLongitude(point.getLng()+"");
				}
				if(TextUtil.isNotNull(this.realName)){
					obj.setMaintainer(this.realName);
				}
				this.leadupService.upsPoint(obj);
				sendResponse(Integer.valueOf(0), "修改成功。");
				//移动撑点位置
				if(toIrms){
					new moveSpoinThread(obj.getId()).start();
				}
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("pdaLeadup.upSupportPoint ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 
	 * @author chenqp
	 *
	 */
	class moveSpoinThread extends Thread{
		private Integer spointId;
		public moveSpoinThread(Integer spointId){
			this.spointId = spointId;
		}
		@Override
		public void run() {
			SupportPointPojo  obj = new SupportPointPojo();
			obj.setId(spointId);
			obj = leadupService.getsPointObj(obj);
			irmsOutLineService.moveSupport(obj);
			super.run();
		}
	}
	
	/**
	 * 增加撑点
	 * @return
	 */
	public String insertSupportPoint(){
		try{
			SupportPointPojo obj =(SupportPointPojo) getRequestObject(SupportPointPojo.class);
			if(obj !=null){
				if(isWGS && TextUtil.isNotNull(obj.getLatitude()) && TextUtil.isNotNull(obj.getLongitude())){
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()));
					obj.setLatitude(point.getLat()+"");
					obj.setLongitude(point.getLng()+"");
				}
				if(TextUtil.isNotNull(this.realName)){
					obj.setMaintainer(this.realName);
				}
				int id = this.leadupService.addSupportPoint(obj);
				obj.setId(id);
				if(isWGS && TextUtil.isNotNull(obj.getLatitude()) && TextUtil.isNotNull(obj.getLongitude())){
					Point point = MapUtil.db_phone_encrypt(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()));
					obj.setLatitude(point.getLat()+"");
					obj.setLongitude(point.getLng()+"");
				}
				sendResponse(Integer.valueOf(0), obj);
				//发送综资
				if(toIrms){
					new addSupportThread(id).start();
				}
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("pdaLeadup.insertSupportPoint ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 增加撑点
	 * @author chenqp
	 *
	 */
	class addSupportThread extends Thread{
		private Integer id;
		public addSupportThread(Integer id){
			this.id = id;
		}
		@Override
		public void run() {
			SupportPointPojo obj = new SupportPointPojo();
			obj.setId(id);
			obj = leadupService.getsPointObj(obj);
			irmsOutLineService.addSupport(obj);
			super.run();
		}
	}
	
	/**
	 * 得到挂墙
	 * @return
	 */
	public String getHangWall(){
		try{
			HangWallPojo obj = (HangWallPojo)getRequestObject(HangWallPojo.class);
			if(obj != null){
				obj.setStart(this.start);
				obj.setLimit(this.limit);
				if(TextUtil.isNotNull(super.areaName)){
					obj.setMaintArea(super.areaName);
				}
				List list = this.leadupService.getHangWallList(obj);
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("pdaLeadup.getHangWall ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 得到挂墙的详细信息
	 * @return
	 */
	public String getHangWallObj(){
		try{
			HangWallPojo obj = (HangWallPojo)getRequestObject(HangWallPojo.class);
			if(obj != null){
				obj = this.leadupService.getHangWallObj(obj);
				sendResponse(Integer.valueOf(0), obj);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("pdaLeadup.getHangWallObj ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	/**
	 * 增加挂墙段
	 * @return
	 */
	public String insertHangWall(){
		try{
			HangWallPojo obj = (HangWallPojo)getRequestObject(HangWallPojo.class);
			if(obj != null){
				obj.setMaintainer(this.longiner);
				String distinct = this.leadupService.getHangWallLength(obj);
				obj.setHangLength(Double.parseDouble(distinct));
				if(TextUtil.isNull(obj.getMaintLength())){
					obj.setMaintLength(Double.parseDouble(distinct));
				}
				if(TextUtil.isNull(obj.getMaintArea())){
					SupportPointPojo support = new SupportPointPojo();
					support.setId(obj.getStartDeviceId());
					support = this.leadupService.getsPointObj(support);
					obj.setMaintArea(support.getMaintArea());
				}
				int id = this.leadupService.insertHangWall(obj);
				obj.setId(id);
				sendResponse(Integer.valueOf(0), obj);
				//综资增加挂墙段
				if(toIrms){
					new addHangwallThread(id).start();
				}
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("pdaLeadup.insertHangWall ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 增加挂墙段
	 * @author chenqp
	 *
	 */
	class addHangwallThread extends Thread{
		private Integer id ;
		public addHangwallThread(Integer id){
			this.id = id;
		}
		@Override
		public void run() {
			HangWallPojo hangwall = new HangWallPojo();
			hangwall.setId(id);
			hangwall = leadupService.getHangWallObj(hangwall);
			irmsOutLineService.addHangWall(hangwall);
			super.run();
		}
	}
	
	/**
	 * 修改挂墙段
	 * @return
	 */
	public String updateHangWall(){
		try{
			HangWallPojo obj = (HangWallPojo)getRequestObject(HangWallPojo.class);
			if(obj != null){
				if(!(obj.getDeleteflag().equals("1"))){
					String distinct = this.leadupService.getHangWallLength(obj);
					obj.setMaintainer(this.longiner);
					obj.setHangLength(Double.parseDouble(distinct));
				}
				this.leadupService.updateHangWall(obj);
				sendResponse(Integer.valueOf(0), obj);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("pdaLeadup.updateHangWall ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	public IleadupService getLeadupService() {
		return leadupService;
	}
	public void setLeadupService(IleadupService leadupService) {
		this.leadupService = leadupService;
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
}
