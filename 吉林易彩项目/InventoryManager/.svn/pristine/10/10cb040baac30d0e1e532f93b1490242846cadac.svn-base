 package interfaces.pdainterface.route.action;
 
 import base.util.MapUtil;
import base.util.TextUtil;
import base.util.functions;
import base.util.pojo.Point;
import base.web.InterfaceAction;
import interfaces.irmsInterface.interfaces.opticTran.service.IrmsOpticTranService;
import interfaces.irmsInterface.interfaces.opticTran.service.impl.IirmsOpticTranService;
import interfaces.pdainterface.equt.pojo.EqutCableInfo;
import interfaces.pdainterface.route.service.PDARouteService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import manage.device.service.impl.IDeviceService;
import manage.device.service.impl.IFiberRackService;
import manage.point.pojo.PointInfoBean;
import manage.route.pojo.BatchRackBean;
import manage.route.pojo.CableInfoBean;
import manage.route.pojo.FiberBoxInfoBean;
import manage.route.pojo.FiberInfoBean;
import manage.route.pojo.JointInfoBean;
import manage.route.pojo.RouteInfoBean;

import org.apache.log4j.Logger;
 
 public class PDARoute extends InterfaceAction
 {
   private static final Logger log = Logger.getLogger(PDARoute.class);
   private PDARouteService pdaRouteService;
   //设备及跳纤
   private IDeviceService deviceService;
   //落架
   private IFiberRackService fiberRackService;
   private IirmsOpticTranService irmsOpticTranService;
   /**
    * 得到所有的光缆数据
    * @return
    */
   public String getRoute()
   {
     try
     {
       RouteInfoBean route = (RouteInfoBean)getRequestObject(RouteInfoBean.class);
       if (route != null) {
    	 if(TextUtil.isNull(route.getRoutename())){
    		 route.setStart(this.start);
    		 route.setLimit(this.limit);
    	 }
         List list = this.pdaRouteService.getRoute(route);
         sendResponse(Integer.valueOf(0), list);
       }else{
    	 sendResponse(Integer.valueOf(2), "请求参数错误。");
       }
     }
     catch (Exception e) {
       this.exception = e;
       sendResponse(Integer.valueOf(3), "应用服务器异常。");
       log.error("PDARoute.getRoute ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
     }
     return "success";
   }
 
   /**
    * 增加光缆
    * @return
    */
   public String insertRoute() {
     try {
       RouteInfoBean route = (RouteInfoBean)getRequestObject(RouteInfoBean.class);
       int i = this.pdaRouteService.insertRoute(route).intValue();
       if (i > 0) {
         route.setRouteid(Integer.valueOf(i));
         sendResponse(Integer.valueOf(0), route);
       }else{
      	 sendResponse(Integer.valueOf(4), "名称重复");
       }
     }
     catch (Exception e) {
       this.exception = e;
       sendResponse(Integer.valueOf(3), "应用服务器异常。");
       log.error("PDARoute.insertRoute ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
     }
     return "success";
   }
 
   
   /**
    * 修改光缆数据
    * @return
    */
   public String updateRoute() {
     try {
       RouteInfoBean route = (RouteInfoBean)getRequestObject(RouteInfoBean.class);
       if (route != null) {
         int i = this.pdaRouteService.updateRoute(route).intValue();
         if (i > 0) {
           sendResponse(Integer.valueOf(0), route); 
         }else{
        	sendResponse(Integer.valueOf(4), "名称重复"); 
         }
       }else{
    	   sendResponse(Integer.valueOf(2), "请求参数错误。");
       }
     }
     catch (Exception e) {
       this.exception = e;
       sendResponse(Integer.valueOf(3), "应用服务器异常。");
       log.error("PDARoute.updateRoute ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
     }
    return "success";
   }
 
   /**
    * 删除光缆数据
    * @return
    */
   public String deleteRoute() {
     try {
       RouteInfoBean route = (RouteInfoBean)getRequestObject(RouteInfoBean.class);
       if (route != null) {
         this.pdaRouteService.delteRoute(route);
         sendResponse(Integer.valueOf(0), "删除成功"); 
       }else{
    	 sendResponse(Integer.valueOf(2), "请求参数错误。"); 
       }
       
     }
     catch (Exception e) {
       this.exception = e;
       sendResponse(Integer.valueOf(3), "应用服务器异常。");
       log.error("PDARoute.updateRoute ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
     }
     return "success";
   }
 
   
   /**
    * 得到所有的光缆段数据
    * @return
    */
   public String getCable() {
     try {
       CableInfoBean cable = (CableInfoBean)getRequestObject(CableInfoBean.class);
       if (cable != null) {
    	 cable.setStart(this.start);
  		 cable.setLimit(this.limit);
    	 if(TextUtil.isNotNull(cable.getOptical_cable_name())){
    		 cable.setCablename(cable.getOptical_cable_name());
    	 }
    	 if(TextUtil.isNull(cable.getCableid()) && TextUtil.isNotNull(super.areaName)){
    		 cable.setRegion(super.areaName);
    	 }
         List<CableInfoBean> list = this.pdaRouteService.getCable(cable);
         for(CableInfoBean obj : list){
        	 if(TextUtil.isNull(obj.getLength())){
        		 obj.setLength("0");
        	 }
         }
         sendResponse(Integer.valueOf(0), list);
       }else{
    	 sendResponse(Integer.valueOf(2), "请求参数错误。"); 
       }
     }
     catch (Exception e) {
       this.exception = e;
       sendResponse(Integer.valueOf(3), "应用服务器异常。");
       log.error("PDARoute.getCable ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
     }
     return "success";
   }
   
   /**
    * 得到敷设光缆
    * @return
    */
   public String getLayCable(){
	   try{
		   EqutCableInfo ecable = (EqutCableInfo)getRequestObject(EqutCableInfo.class);
		   if(ecable != null){
			   List<CableInfoBean> list = pdaRouteService.getLayCable(ecable);
			   sendResponse(Integer.valueOf(0), list);
		   }else{
			   sendResponse(Integer.valueOf(2), "请求参数错误。"); 
	       }
	   }catch(Exception e){
		   this.exception = e;
	       sendResponse(Integer.valueOf(3), "应用服务器异常。");
	       log.error("PDARoute.getLayCable ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
	   }
	   return "success";
   }
   
   /**
    * 得到光缆段信息
    * @return
    */
   public String getCableObj(){
	   try{
		   CableInfoBean cable = (CableInfoBean)getRequestObject(CableInfoBean.class);
		   if(cable != null){
			   //用在二维码上的
			   if(TextUtil.isNull(cable.getCableid()) && TextUtil.isNotNull(cable.getCableOpticalId())){
				   cable.setCableid(Integer.parseInt(cable.getCableOpticalId()));
			   }
			   cable = this.pdaRouteService.getCableObj(cable);
			   sendResponse(Integer.valueOf(0), cable);
		   }else{
			   sendResponse(Integer.valueOf(2), "请求参数错误。"); 
		   }
	   }catch(Exception e){
		   this.exception = e;
	       sendResponse(Integer.valueOf(3), "应用服务器异常。");
	       log.error("PDARoute.getCableObj ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
	   }
	   return "success";
   }
 
   
   /**
    * 增加光缆段
    * @return
    */
   public String insertCable() {
     try {
       CableInfoBean cable = (CableInfoBean)getRequestObject(CableInfoBean.class);
       if (cable != null) {
    	  if(TextUtil.isNotNull(cable.getStartDeviceId()) && TextUtil.isNotNull(cable.getEndDeviceId())){
    		  cable.setLength(this.pdaRouteService.getCableLength(cable));
    		  if(TextUtil.isNull(cable.getMaintainLength())){
    			  cable.setMaintainLength(cable.getLength());
    		  }
    	  }
    	 if(TextUtil.isNotNull(super.realName)){
    		 cable.setParties(super.realName);
    	 }
         int i = this.pdaRouteService.insertCable(cable).intValue();
         if (i > 0) {
           cable.setCableid(Integer.valueOf(i));
           sendResponse(Integer.valueOf(0), cable);
           new addCableThread(i).start();
         }else{
           sendResponse(Integer.valueOf(4), "名称重复");
         }
       }else{
    	   sendResponse(Integer.valueOf(2), "请求参数错误。");
       }
     }
     catch (Exception e) {
    	 e.printStackTrace();
     }
     return "success";
   }
   
   
   /**
    * 调用增加光缆段的接口
    * @author chenqp
    *
    */
   class addCableThread extends Thread{
	   private Integer cableId;
	   public addCableThread(Integer cableId){
		   this.cableId = cableId;
	   }
	   @Override
	   public void run() {
		   CableInfoBean cable = new CableInfoBean();
		   cable.setCableid(cableId);
		   cable = pdaRouteService.getCableObj(cable);
		   //增加纤芯
		   addFiber(cable);
		   //是否发送到综资
		   irmsOpticTranService.addCableSeg(cable);
		   /*if(toIrms){
			  //是否发送到综资
			  irmsOpticTranService.addCableSeg(cable);
		   }*/
		   super.run();
	   }
   }
 
   
   /**
    * 修改光缆段
    * @return
    */
	public String updateCable() {
		try {
			CableInfoBean cable = (CableInfoBean) getRequestObject(CableInfoBean.class);
			if (cable != null) {
				if(TextUtil.isNotNull(cable.getEndDeviceId())){
					cable.setLength(this.pdaRouteService.getCableLength(cable));
				}
				if(TextUtil.isNotNull(super.realName)){
					cable.setParties(super.realName);
				}
				int i = this.pdaRouteService.updateCable(cable).intValue();
				if (i > 0) {
					sendResponse(Integer.valueOf(0), cable);
				} else {
					sendResponse(Integer.valueOf(4), "名称重复");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDARoute.updateCable ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
 
   /**
    * 删除光缆段
    * @return
    */
	public String deleteCable() {
		try {
			CableInfoBean cable = (CableInfoBean) getRequestObject(CableInfoBean.class);
			if (cable != null) {
				if(TextUtil.isNotNull(super.realName)){
					cable.setParties(super.realName);
				}
				this.pdaRouteService.deleteCable(cable);
				sendResponse(Integer.valueOf(0), "删除成功");
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}

		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDARoute.deleteCable ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
 
	/**
	 * 得到接头盒
	 * @return
	 */
	public String getJoint() {
		try {
			JointInfoBean joint = (JointInfoBean) getRequestObject(JointInfoBean.class);
			if (joint != null) {
				if(joint.getLongitude() != null && joint.getLatitude() != null){
					if(isWGS && TextUtil.isNotNull(joint.getLatitude()) && TextUtil.isNotNull(joint.getLongitude())){
						Point point = MapUtil.phone_db_encrypt(Double.parseDouble(joint.getLatitude()),Double.parseDouble(joint.getLongitude()));
						joint.setLatitude(point.getLat() + "");
						joint.setLongitude(point.getLng() + "");
					}
					double[] arr = functions.getAround(Double.parseDouble(joint.getLatitude()),Double.parseDouble(joint.getLongitude()), ((this.start/this.limit)+1)*Integer.parseInt(properties.getValueByKey("gisLength")));
					joint.setLats(String.valueOf(arr[0]));
					joint.setLons(String.valueOf(arr[1]));
					joint.setLate(String.valueOf(arr[2]));
					joint.setLone(String.valueOf(arr[3]));
				}else{
					joint.setStart(this.start);
					joint.setLimit(this.limit);
				}
				List<JointInfoBean> list = this.pdaRouteService.getJoint(joint);
				for(JointInfoBean obj : list){
					if(isWGS && TextUtil.isNotNull(obj.getLatitude()) && TextUtil.isNotNull(obj.getLongitude())){
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()));
						obj.setLatitude(point.getLat() + "");
						obj.setLongitude(point.getLng() + "");
					}
				}
				sendResponse(Integer.valueOf(0), list);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDARoute.getJoint ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
 
	/**
	 * 新增接头盒
	 * @return
	 */
	public String insertJoint() {
		try {
			JointInfoBean joint = (JointInfoBean) getRequestObject(JointInfoBean.class);
			if (joint != null) {
				if(isWGS && TextUtil.isNotNull(joint.getLatitude()) && TextUtil.isNotNull(joint.getLongitude())){
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(joint.getLatitude()),Double.parseDouble(joint.getLongitude()));
					joint.setLatitude(point.getLat() + "");
					joint.setLongitude(point.getLng() + "");
				}
				if(TextUtil.isNotNull(super.realName)){
					joint.setParties(super.realName);
				}
				int i = this.pdaRouteService.insertJoint(joint).intValue();
				if (i > 0) {
					joint.setJointId(Integer.valueOf(i));
					if(isWGS && TextUtil.isNotNull(joint.getLatitude()) && TextUtil.isNotNull(joint.getLongitude())){
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(joint.getLatitude()),Double.parseDouble(joint.getLongitude()));
						joint.setLatitude(point.getLat() + "");
						joint.setLongitude(point.getLng() + "");
					}
					sendResponse(Integer.valueOf(0), joint);
					//是否送到综资
					if(toIrms){
						new addOpticJoint(joint.getJointId()).start();
					}
				} else {
					sendResponse(Integer.valueOf(4), "名称重复");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDARoute.insertJoint ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 增加光接头盒
	 * @author chenqp
	 *
	 */
	class addOpticJoint extends Thread{
		
		private Integer id;
		public addOpticJoint(Integer id){
			this.id = id;
		}
		@Override
		public void run() {
			JointInfoBean joint = new JointInfoBean();
			joint.setJointId(id);
			List<JointInfoBean> list = pdaRouteService.getJoint(joint);
			if(TextUtil.isNotNull(list)){
				irmsOpticTranService.addOpticJoint(list.get(0));
			}
			super.run();
		}
	}
 
	/**
	 * 修改接头盒
	 * @return
	 */
	public String updateJoint() {
		try {
			JointInfoBean joint = (JointInfoBean) getRequestObject(JointInfoBean.class);
			if (joint != null) {
				if(isWGS && TextUtil.isNotNull(joint.getLatitude()) && TextUtil.isNotNull(joint.getLongitude())){
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(joint.getLatitude()),Double.parseDouble(joint.getLongitude()));
					joint.setLatitude(point.getLat() + "");
					joint.setLongitude(point.getLng() + "");
				}
				if(TextUtil.isNotNull(super.realName)){
					joint.setParties(super.realName);
				}
				int i = this.pdaRouteService.updateJoint(joint).intValue();
				if (i > 0) {
					if(isWGS && TextUtil.isNotNull(joint.getLatitude()) && TextUtil.isNotNull(joint.getLongitude())){
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(joint.getLatitude()),Double.parseDouble(joint.getLongitude()));
						joint.setLatitude(point.getLat() + "");
						joint.setLongitude(point.getLng() + "");
					}
					sendResponse(Integer.valueOf(0), joint);
				} else {
					sendResponse(Integer.valueOf(4), "名称重复");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDARoute.updateJoint ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
 
	/**
	 * 删除接头盒
	 * @return
	 */
	public String deleteJoint() {
		try {
			JointInfoBean joint = (JointInfoBean) getRequestObject(JointInfoBean.class);
			if (joint != null) {
				if(TextUtil.isNotNull(super.realName)){
					joint.setParties(super.realName);
				}
				this.pdaRouteService.deleteJoint(joint);
				sendResponse(Integer.valueOf(0), "删除成功");
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDARoute.deleteJoint ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
 
	/**
	 * 得到分纤箱
	 * @return
	 */
	public String getFiberbox() {
		try {
			FiberBoxInfoBean fiberbox = (FiberBoxInfoBean) getRequestObject(FiberBoxInfoBean.class);
			if (fiberbox != null) {
				if(TextUtil.isNotNull(fiberbox.getLongitude()) && TextUtil.isNotNull(fiberbox.getLatitude())){
					if(isWGS){
						Point point = MapUtil.phone_db_encrypt(Double.parseDouble(fiberbox.getLatitude()),Double.parseDouble(fiberbox.getLongitude()));
						fiberbox.setLatitude(point.getLat() + "");
						fiberbox.setLongitude(point.getLng() + "");
					}
					double[] arr = functions.getAround(Double.parseDouble(fiberbox.getLatitude()),Double.parseDouble(fiberbox.getLongitude()), ((this.start/this.limit)+1)*Integer.parseInt(properties.getValueByKey("gisLength")));
					fiberbox.setLats(String.valueOf(arr[0]));
					fiberbox.setLons(String.valueOf(arr[1]));
					fiberbox.setLate(String.valueOf(arr[2]));
					fiberbox.setLone(String.valueOf(arr[3]));
				}else{
					fiberbox.setStart(this.start);
					fiberbox.setLimit(this.limit);
				}
				if(TextUtil.isNotNull(super.areaName)){
					fiberbox.setMaintainArea(super.areaName);
				}
				List<FiberBoxInfoBean> list = this.pdaRouteService.getFiberbox(fiberbox);
				for(FiberBoxInfoBean obj: list){
					if(isWGS && TextUtil.isNotNull(obj.getLongitude()) && TextUtil.isNotNull(obj.getLatitude())){
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()));
						obj.setLatitude(point.getLat()+"");
						obj.setLongitude(point.getLng()+"");
					}
				}
				sendResponse(Integer.valueOf(0), list);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDARoute.getFiberbox ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 新增分纤箱
	 * @return
	 */
	public String insertFiberbox() {
		try {
			FiberBoxInfoBean fiberbox = (FiberBoxInfoBean) getRequestObject(FiberBoxInfoBean.class);
			if (fiberbox != null) {
				if(TextUtil.isNotNull(super.realName)){
					fiberbox.setMaintainer(super.realName);
				}
				if(isWGS && TextUtil.isNotNull(fiberbox.getLatitude()) && TextUtil.isNotNull(fiberbox.getLongitude())){
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(fiberbox.getLatitude()), Double.parseDouble(fiberbox.getLongitude()));
					fiberbox.setLatitude(point.getLat()+"");
					fiberbox.setLongitude(point.getLng()+"");
				}
				int i = this.pdaRouteService.insertFiberbox(fiberbox).intValue();
				if (i > 0) {
					fiberbox.setId(Integer.valueOf(i));
					//综资新增分纤箱
					if(toIrms){
						new addFiberBox(i).start();
					}
					if(isWGS && TextUtil.isNotNull(fiberbox.getLatitude()) && TextUtil.isNotNull(fiberbox.getLongitude())){
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(fiberbox.getLatitude()), Double.parseDouble(fiberbox.getLongitude()));
						fiberbox.setLatitude(point.getLat()+"");
						fiberbox.setLongitude(point.getLng()+"");
					}
					sendResponse(Integer.valueOf(0), fiberbox);
				} else {
					sendResponse(Integer.valueOf(4), "名称重复");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDARoute.insertFiberbox ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 增加分纤箱
	 * @author chenqp
	 *
	 */
	class addFiberBox extends Thread{
		private Integer id;
		public addFiberBox(Integer id){
			this.id = id;
		}
		@Override
		public void run() {
			FiberBoxInfoBean fiber = new FiberBoxInfoBean();
			fiber.setId(id);
			List<FiberBoxInfoBean>  list = pdaRouteService.getFiberbox(fiber);
			if(TextUtil.isNotNull(list)){
				irmsOpticTranService.addFiberBox(list.get(0));
			}
			super.run();
		}
	}
 
	
	/**
	 * 修改分纤箱
	 * @return
	 */
	public String updateFiberbox() {
		try {
			FiberBoxInfoBean fiberbox = (FiberBoxInfoBean) getRequestObject(FiberBoxInfoBean.class);
			if (fiberbox != null) {
				if(TextUtil.isNotNull(super.realName)){
					fiberbox.setMaintainer(super.realName);
				}
				if(isWGS && TextUtil.isNotNull(fiberbox.getLatitude()) && TextUtil.isNotNull(fiberbox.getLongitude())){
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(fiberbox.getLatitude()), Double.parseDouble(fiberbox.getLongitude()));
					fiberbox.setLatitude(point.getLat()+"");
					fiberbox.setLongitude(point.getLng()+"");
				}
				int i = this.pdaRouteService.updateFiberbox(fiberbox).intValue();
				if (i > 0) {
					if(isWGS && TextUtil.isNotNull(fiberbox.getLatitude()) && TextUtil.isNotNull(fiberbox.getLongitude())){
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(fiberbox.getLatitude()), Double.parseDouble(fiberbox.getLongitude()));
						fiberbox.setLatitude(point.getLat()+"");
						fiberbox.setLongitude(point.getLng()+"");
					}
					sendResponse(Integer.valueOf(0), fiberbox);
				} else {
					sendResponse(Integer.valueOf(4), "名称重复");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDARoute.updateFiberbox ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
 
	/**
	 * 删除分纤箱
	 * @return
	 */
	public String deleteFiberbox() {
		try {
			FiberBoxInfoBean fiberbox = (FiberBoxInfoBean) getRequestObject(FiberBoxInfoBean.class);
			if (fiberbox != null) {
				if(TextUtil.isNotNull(super.realName)){
					fiberbox.setMaintainer(super.realName);
				}
				this.pdaRouteService.deleteFiberbox(fiberbox);
				sendResponse(Integer.valueOf(0), "删除成功。");
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDARoute.deleteFiberbox ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
   
   /**
    * 增加纤芯
    * @param cable
    */
   void addFiber(CableInfoBean cable){
	   Date fiberDate = new Date();
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   try{
		   if(TextUtil.isNotNull(cable.getFibercount())){
			   for(int i=0;i<cable.getFibercount();i++){
				   FiberInfoBean fiber = new FiberInfoBean();
				   fiber.setCableId(cable.getCableid()+"");
				   fiber.setCableName(cable.getCablename());
				   int num =i+1;
				   if((num+"").length()==1){
					   fiber.setZhLabel(cable.getCablename()+"_0"+(i+1)+"芯");
				   }else{
					   fiber.setZhLabel(cable.getCablename()+"_"+(i+1)+"芯");
				   }
				   fiber.setStatus(0);
				   fiber.setAlias((i+1)+"");
				   fiber.setDeleteflag("0");
				   fiber.setCreateTime(sdf.format(fiberDate));
				   this.pdaRouteService.insertFiber(fiber);
			   }
		   }
	   }catch(Exception e){
		   e.printStackTrace();
	   }
   }
   
   
   /**
    * 将光缆段自动
    * 上架到接头盒
    */
   void cableRack(CableInfoBean cable){
	   try{
		   //起始端为接头盒
		   if(cable.getStartDeviceType() .equals(2)){
			   
		   }
		   //终端为接头盒
		   if(cable.getEndDeviceType().equals(2)){
			   
		   }
	   }catch(Exception e){
		   e.printStackTrace();
	   }
   }
   
   /**
    * 得到所有的纤芯数据
    * @return
    */
   public String getFiber(){
	   try{
		   FiberInfoBean fiber = (FiberInfoBean) getRequestObject(FiberInfoBean.class);
		   if(fiber !=null){
			   fiber.setStart(this.start);
			   fiber.setLimit(this.limit);
			   List<FiberInfoBean> list = pdaRouteService.getFiberList(fiber);
			   sendResponse(Integer.valueOf(0), list);
		   }else{
			   sendResponse(Integer.valueOf(2), "请求参数错误。");
		   }
	   }catch(Exception e){
		   this.exception = e;
	       sendResponse(Integer.valueOf(3), "应用服务器异常。");
	       log.error("PDARoute.getFiber ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
	   }
	   return "success";
   }
   
   
   
   /**
    * 得到所有的纤芯数据
    * @return
    */
   public String getListFiber(){
	   try{
		   FiberInfoBean fiber = (FiberInfoBean) getRequestObject(FiberInfoBean.class);
		   if(fiber !=null){
			   List<FiberInfoBean> list = pdaRouteService.getFiber(fiber);
			   sendResponse(Integer.valueOf(0), list);
		   }else{
			   sendResponse(Integer.valueOf(2), "请求参数错误。");
		   }
	   }catch(Exception e){
		   this.exception = e;
	       sendResponse(Integer.valueOf(3), "应用服务器异常。");
	       log.error("PDARoute.getFiber ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
	   }
	   return "success";
   }
   
   /**
    * 得到纤芯详情
    * @return
    */
   public String getFiberObj(){
	   try{
		   FiberInfoBean obj = (FiberInfoBean)getRequestObject(FiberInfoBean.class);
		   if(obj != null ){
			   obj = this.pdaRouteService.getFiberObj(obj);
			   sendResponse(Integer.valueOf(0), obj);
		   }else{
			   sendResponse(Integer.valueOf(2), "请求参数错误。");
		   }
	   }catch(Exception e){
		   this.exception = e;
	       sendResponse(Integer.valueOf(3), "应用服务器异常。");
	       log.error("PDARoute.getFiberObj ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
	   }
	   return "success";
   }
   
   /**
    * 修改纤芯数据，主要用于纤芯落架
    * @return
    */
   public String updateFiber(){
	   try{
		   FiberInfoBean fiber = (FiberInfoBean) getRequestObject(FiberInfoBean.class);
		   if(fiber != null){
			   Integer result = this.pdaRouteService.updateFiber(fiber);
			   if(result >0){
				   sendResponse(Integer.valueOf(0), fiber); 
			   }else{
				   sendResponse(Integer.valueOf(4), "名称重复"); 
			   }
		   }else {
			   sendResponse(Integer.valueOf(2), "请求参数错误。");
		   }
	   }catch(Exception e){
		   this.exception = e;
	       sendResponse(Integer.valueOf(3), "应用服务器异常。");
	       log.error("PDARoute.updateFiber ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
	   }
	   return "success";
   }
   
   
   /**
    * 解除纤芯成端
    * @return
    */
   public String relieveRack(){
	   try{
		   String fibers = (String) getRequestObject(String.class);
		   if(fibers != null){
			   sendResponse(Integer.valueOf(0), "解除成端成功。");
		   }else{
			   sendResponse(Integer.valueOf(2), "请求参数错误。");
		   }
	   }catch(Exception e){
		   this.exception = e;
	       sendResponse(Integer.valueOf(3), "应用服务器异常。");
	       log.error("PDARoute.relieveRack ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
	   }
	   return "success";
   }
   
   
   /**
    * 批量上架
    * @return
    */
   public String batchRack(){
	   try{
		   BatchRackBean object = (BatchRackBean) getRequestObject(BatchRackBean.class);
		   if(object != null){
			   String[] fiberIds = object.getFiberIds().split(",");
			   String[] pointIds = object.getPointIds().split(",");
			   object.setStartFiber(fiberIds[0]);
			   object.setEndFiber(fiberIds[1]);
			   List<FiberInfoBean> list = this.pdaRouteService.getFiberList(object);
			   //这个地方需要注意，原来为list.size==fiberids.length为查询失败，很奇怪
			   if(TextUtil.isNotNull(list) && list.size() != fiberIds.length){
				   sendResponse(Integer.valueOf(1), "纤芯数据查询失败，请确认!。");
			   }else{
				   sendResponse(Integer.valueOf(0), "批量成端完成。");
				   new BatchRackThread(object, list).start();
			   }
		   }else{
			   sendResponse(Integer.valueOf(2), "请求参数错误。");
		   }
	   }catch(Exception e){
		   this.exception = e;
	       sendResponse(Integer.valueOf(3), "应用服务器异常。");
	       log.error("PDARoute.batchRack ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
	   }
	   return "success";
   }
   
   /**
    * 批量承端信息
    * @author chenqp
    *
    */
   class BatchRackThread extends Thread{
	   private BatchRackBean object;
	   private List<FiberInfoBean> list;
	   public BatchRackThread(BatchRackBean object,List<FiberInfoBean> list){
		   this.object = object;
		   this.list = list;
	   }
	   @Override
	   public void run() {
		   //批量更改纤芯的数据
		   List<PointInfoBean> pointList = pdaRouteService.batchFiber(object, list);
		   //批量更改端子的信息
		   if(TextUtil.isNotNull(pointList)) {
			   pdaRouteService.batchPutPoint(pointList);
		   }
		   super.run();
	   }
   }
   public PDARouteService getPdaRouteService() {
     return this.pdaRouteService;
   }
   public IDeviceService getDeviceService() {
	return deviceService;
   }
   public void setDeviceService(IDeviceService deviceService) {
	this.deviceService = deviceService;
   }
   public void setPdaRouteService(PDARouteService pdaRouteService) {
     this.pdaRouteService = pdaRouteService;
   }

   public IFiberRackService getFiberRackService() {
	return fiberRackService;
   }
   public IirmsOpticTranService getIrmsOpticTranService() {
	   return irmsOpticTranService;
   }
   public void setIrmsOpticTranService(IirmsOpticTranService irmsOpticTranService) {
	   this.irmsOpticTranService = irmsOpticTranService;
   }
   public void setFiberRackService(IFiberRackService fiberRackService) {
	   this.fiberRackService = fiberRackService;
   }
}

