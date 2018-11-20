package interfaces.pdainterface.lineSystem.action;


import java.util.List;
import java.util.Map;

import manage.user.pojo.UserInfoBean;

import org.apache.log4j.Logger;

import edu.emory.mathcs.backport.java.util.LinkedList;
import interfaces.irmsInterface.interfaces.outLine.service.impl.IirmsOutLineService;
import interfaces.irmsInterface.interfaces.service.impl.IirmsInterService;
import interfaces.pdainterface.lineSystem.pojo.LineAffiliationInfo;
import interfaces.pdainterface.lineSystem.pojo.LinePointInfo;
import interfaces.pdainterface.lineSystem.pojo.LineSegmentInfo;
import interfaces.pdainterface.lineSystem.pojo.LineSystemInfo;
import interfaces.pdainterface.lineSystem.service.impl.IlineSystemService;
import base.util.MapUtil;
import base.util.ResUtil;
import base.util.TextUtil;
import base.util.pojo.Point;
import base.web.InterfaceAction;

public class PDALineSystem extends InterfaceAction{
	 private static final Logger log = Logger.getLogger(PDALineSystem.class);
	//管线服务
	private IlineSystemService lineSystemService;
	private IirmsOutLineService irmsOutLineService;
	
	
	/**
	 * 得到管系统
	 * @return
	 */
	public String getLineSystem(){
		try{
			LineSystemInfo obj = (LineSystemInfo)getRequestObject(LineSystemInfo.class);
			UserInfoBean user = (UserInfoBean) getInterfaceSession().getAttribute("userBean");
			if(obj != null){
				obj.setStart(super.start);
				obj.setLimit(300);
				String areaName = "";
				if(TextUtil.isNotNull(super.areaName)) {
					areaName = super.areaName;
				}else if(TextUtil.isNotNull(user.getAreaName())) {
					areaName = super.getCityStr(user.getAreaName());
				}
				if(TextUtil.isNotNull(areaName)) {
					obj.setLineArea(areaName);
				}
				obj.setCreater(user.getUsername());
				List<LineSystemInfo> list = this.lineSystemService.getLineSystemList(obj);
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			 this.exception = e;
		     sendResponse(Integer.valueOf(3), "应用服务器异常。");
		     log.error("PDALineSystem.getLineSystem ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 得到实体类
	 * @return
	 */
	public String getLineSystemObj(){
		try{
			LineSystemInfo obj = (LineSystemInfo)getRequestObject(LineSystemInfo.class);
			if(obj != null){
				obj = this.lineSystemService.getLineSystemObj(obj);
				Map<String, Object> map= this.lineSystemService.getLineLength(obj);
				if(map != null){
					java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
					String coutLength = map.get("coutLength")+"";
					String maintainLength  = map.get("maintainLength")+"";
					if(TextUtil.isNotNull(coutLength)){
						obj.setCountLength(df.format(Double.parseDouble(coutLength)));
					}
					if(TextUtil.isNotNull(maintainLength)){
						obj.setMaintainLength(df.format(Double.parseDouble(maintainLength)));
					}
				}
				sendResponse(Integer.valueOf(0), obj);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDALineSystem.getLineSystemObj ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 更新
	 * @return
	 */
	public String upLineSystem(){
		try{
			LineSystemInfo obj = (LineSystemInfo)getRequestObject(LineSystemInfo.class);
			if(obj != null){
				LineSystemInfo obj1 = new LineSystemInfo();
				obj1.setLineName(obj.getLineName());
				obj1.setLineType(obj.getLineType());
				obj1.setId(obj.getId());
				LineSystemInfo obj2 = this.lineSystemService.checkLineSystemObj(obj1);
				if(obj2 == null) {
					int result = this.lineSystemService.upLineSystem(obj);
					//更新系统
					new addLineSys(obj.getId()).start();
					sendResponse(Integer.valueOf(0), "更新成功");
				}else {
					sendResponse(Integer.valueOf(1), "命名重复");
				}
				
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDALineSystem.getLineSystemObj ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 增加
	 * @return
	 */
	public String insertLineSystem(){
		try{
			LineSystemInfo obj = (LineSystemInfo)getRequestObject(LineSystemInfo.class);
			if(obj != null){
				LineSystemInfo obj1 = new LineSystemInfo();
				obj1.setLineName(obj.getLineName());
				obj1.setLineType(obj.getLineType());
				LineSystemInfo obj2 = this.lineSystemService.getLineSystemObj(obj1);
				if (obj2 == null) {
					obj.setCreater(this.longiner);
					if(obj.getLineArea().startsWith("北京市_北京市_")) {
						String[] areas = obj.getLineArea().split("_");
						obj.setLineArea("北京_"+areas[areas.length-1]+"_");
					}
					int id = this.lineSystemService.insertLineSystem(obj);
					obj.setId(id);
					//直接更新下数据
					new addLineSys(id).start();
					sendResponse(Integer.valueOf(0), obj);
				} else {
					sendResponse(Integer.valueOf(2), "名称重复");
				}
				
			}else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDALineSystem.insertLineSystem ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 新增管线系统
	 * @author chenqp
	 *
	 */
	class addLineSys extends Thread{
		private Integer sysId;
		public addLineSys(Integer sysId){
			this.sysId= sysId;
		}
		@Override
		public void run() {
			LineSystemInfo line = new LineSystemInfo();
			line.setId(sysId);
			line = lineSystemService.getLineSystemObj(line);
			String sysId = irmsOutLineService.addSystem(line);
			line.setResNum(sysId);
			lineSystemService.upLineSystem(line);
			super.run();
		}
	}
	
	
	/**
	 * 得到点的资源数据
	 * @return
	 */
	public String getLinePoint(){
		try{
			LinePointInfo obj = (LinePointInfo) getRequestObject(LinePointInfo.class);
			if(obj != null){
				obj.setStart(this.start);
				obj.setLimit(this.limit);
				List<LinePointInfo> list = this.lineSystemService.getLinePoint(obj);
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDALineSystem.getLinePoint ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 得到
	 * @return
	 */
	public String getLineSeg(){
		try{
			LineSegmentInfo obj = (LineSegmentInfo) getRequestObject(LineSegmentInfo.class);
			if(obj != null){
				obj.setStart(this.start);
				obj.setLimit(this.limit);
				List<LineSegmentInfo> list = this.lineSystemService.getLineSegList(obj);
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDALineSystem.getLineSeg ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 得到GIS地图
	 * @return
	 */
	public String getLineGis(){
		try{
			LineSystemInfo obj = (LineSystemInfo) getRequestObject(LineSystemInfo.class);
			if(obj != null){
				List<LineSegmentInfo> list = new LinkedList();
				if(obj.getLineType().equals(4)){
					if(TextUtil.isNotNull(obj.getLatitude()) && TextUtil.isNotNull(obj.getLongitude())){
						Point point = MapUtil.phone_db_encrypt(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()));
						obj.setLatitude(point.getLat()+"");
						obj.setLongitude(point.getLng()+"");
					}
					list = this.lineSystemService.getCableGis(obj);
				}else{
					list = this.lineSystemService.getLineGis(obj);
				}
				String jsonStr = ResUtil.getPhoneGisSeg(list,obj.getLineType(),true);
				sendResponse(Integer.valueOf(0), jsonStr);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDALineSystem.getLineGis ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 增加点
	 * @return
	 */
	public String addLinePoint(){
		try{
			LinePointInfo obj = (LinePointInfo) getRequestObject(LinePointInfo.class);
			UserInfoBean user = (UserInfoBean) getInterfaceSession().getAttribute("userBean");
			if(obj != null){
				if(TextUtil.isNotNull(obj.getLatitude()) && TextUtil.isNotNull(obj.getLongitude())){
					Point point =  MapUtil.phone_db_encrypt(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()));
					obj.setLatitude(point.getLat()+"");
					obj.setLongitude(point.getLng()+"");
				}
				Map<String, Object> map = this.lineSystemService.getMaxLinePoint(obj);
				int result ;
				if(null == map){
					//说明是一个新的系统
					result = this.lineSystemService.addLinePoint(obj, null,user.getRealname());
				}else{
					//说明是一个老的系统
					result = this.lineSystemService.addLinePoint(obj, map,user.getRealname());
				}
				if(result > 0){
					sendResponse(Integer.valueOf(0), "添加成功");
				}else{
					sendResponse(Integer.valueOf(1), "距离超限!管道<1000,杆路<500,引上<500");
				}
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDALineSystem.addLinePoint ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 将点段资源归属
	 * 到当前管线系统
	 * @return
	 */
	public String lineAffiliation(){
		try{
			LineAffiliationInfo obj = (LineAffiliationInfo) getRequestObject(LineAffiliationInfo.class);
			UserInfoBean user = (UserInfoBean) getInterfaceSession().getAttribute("userBean");
			if(obj != null){
				Integer sysType = 0;
				if(obj.getType().equals("pole")){
					sysType =0;
				}else if(obj.getType().equals("stone")){
					sysType =1;
				}else if(obj.getType().equals("well")){
					sysType = 2;
				}
				LinePointInfo line = new LinePointInfo();
				line.setSysId(obj.getSysId());
				line.setSysType(sysType);
				Map<String, Object> map = this.lineSystemService.getMaxLinePoint(line);
				int result ;
				result = this.lineSystemService.upLinePoint(obj, map, user.getRealname());
				sendResponse(Integer.valueOf(0), "更改成功。");
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDALineSystem.lineAffiliation ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	public IirmsOutLineService getIrmsOutLineService() {
		return irmsOutLineService;
	}
	public void setIrmsOutLineService(IirmsOutLineService irmsOutLineService) {
		this.irmsOutLineService = irmsOutLineService;
	}
	public IlineSystemService getLineSystemService() {
		return lineSystemService;
	}
	public void setLineSystemService(IlineSystemService lineSystemService) {
		this.lineSystemService = lineSystemService;
	}
	
}
