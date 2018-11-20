 package interfaces.pdainterface.generator.action;
 
 import base.util.MapUtil;
import base.util.TextUtil;
import base.util.functions;
import base.util.pojo.Point;
import base.web.InterfaceAction;
import interfaces.irmsInterface.interfaces.station.service.impl.IirmsQueryService;
import interfaces.irmsInterface.interfaces.station.service.impl.IirmsStationService;
import interfaces.pdainterface.generator.service.PDAGeneratorService;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import manage.generator.pojo.GeneratorInfoBean;
import manage.generator.pojo.HighFrequencySwitchingPowerSupplyInfoBean;
import manage.generator.pojo.StationBaseInfoBean;
import manage.user.pojo.UserInfoBean;

import org.apache.log4j.Logger;
 
 public class PDAGenerator extends InterfaceAction
 {
   private static final long serialVersionUID = -3486300674796123267L;
   private static final Logger log = Logger.getLogger(PDAGenerator.class);
   private PDAGeneratorService pdaGeneratorService;
   private  IirmsStationService irmsStationService;
   private IirmsQueryService irmsQueryService;
  
 /**
  * 得到机房数据  
  * @return
  */
 public String getGenerator()
 {
   try
   {
     GeneratorInfoBean generator = (GeneratorInfoBean)getRequestObject(GeneratorInfoBean.class);
     if (generator != null) {
       if(TextUtil.isNotNull(generator.getJflx())){
    	   generator.setJflx(null);
       }
       if(TextUtil.isNotNull(generator.getYwjb())){
    	   generator.setYwjb(null);
       }
       //增加地市限制
       if(TextUtil.isNotNull(super.getAreaName())){
    	   generator.setRegion(this.getAreaName());
       }
       generator.setStart(this.start);
	   generator.setLimit(this.limit);
	   List<GeneratorInfoBean>generatorList = new LinkedList<GeneratorInfoBean>();
	   //加个验证
	   if(super.fromIrms && TextUtil.isNotNull(generator.getAreano())){
		   generatorList = this.irmsQueryService.getGeneratorBySite(generator.getAreano());
	   }else{
		   if(TextUtil.isNotNull(generator.getAreano())) {
			   generator.setRegion(null);
		   }
		   generatorList = this.pdaGeneratorService.getGenerator(generator);
	   }
	   
       for(GeneratorInfoBean obj:generatorList){
    	   if(super.isWGS && TextUtil.isNotNull(obj.getLat()) && TextUtil.isNotNull(obj.getLon())){
    		   Point point = MapUtil.db_phone_encrypt(Double.parseDouble(obj.getLat()), Double.parseDouble(obj.getLon()));
    		   obj.setLat(point.getLat()+"");
    		   obj.setLon(point.getLng()+"");
    	   }
       }
       sendResponse(Integer.valueOf(0), generatorList);
     }else{
       sendResponse(Integer.valueOf(2), "请求参数错误。");	 
     }
   }
   catch (Exception e) {
     this.exception = e;
     sendResponse(Integer.valueOf(3), "应用服务器异常。");
     log.error("PDAGenerator.getGenerator ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
   }
   return "success";
 }

 /**
  * 增加机房
  * @return
  */
	public String insertGenerator() {
		try {
			GeneratorInfoBean generator = (GeneratorInfoBean) getRequestObject(GeneratorInfoBean.class);
			UserInfoBean user = (UserInfoBean) getInterfaceSession()
					.getAttribute("userBean");
			if (generator != null) {
				if(this.checkGener(generator.getGeneratorName() , null ) >0){
					sendResponse(Integer.valueOf(2), "机房名称重复。");
				}else{
					if(TextUtil.isNotNull(super.realName)){
						generator.setParties(super.realName);
					}
					if(isWGS){
						 Point point = MapUtil.phone_db_encrypt(Double.parseDouble(generator.getLat()), Double.parseDouble(generator.getLon()));
						 generator.setLat(point.getLat()+"");
						 generator.setLon(point.getLng()+"");
					}
					int id = this.pdaGeneratorService.insertGenerator(generator);
					if(isWGS){
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(generator.getLat()),Double.parseDouble(generator.getLon()));
						generator.setLat(point.getLat()+"");
						generator.setLon(point.getLng()+"");
					}
					generator.setGeneratorId(Integer.valueOf(id));
					generator.setDeleteFlag("0");
					sendResponse(Integer.valueOf(0), generator);
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAGenerator.insertGenerator ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 修改机房 
	 * @return
	 */
	public String updateGenerator() {
		try {
			GeneratorInfoBean generator = (GeneratorInfoBean) getRequestObject(GeneratorInfoBean.class);
			UserInfoBean user = (UserInfoBean) getInterfaceSession()
					.getAttribute("userBean");
			if (generator != null) {
				if(TextUtil.isNotNull(super.realName)){
					generator.setParties(super.realName);
				}
				if(isWGS){
					 Point point = MapUtil.phone_db_encrypt(Double.parseDouble(generator.getLat()), Double.parseDouble(generator.getLon()));
					 generator.setLat(point.getLat()+"");
					 generator.setLon(point.getLng()+"");
				}
				if(TextUtil.isNotNull(generator.getGeneratorId())){
					if(generator.getDeleteFlag().equals("1")){
						GeneratorInfoBean resGener = this.pdaGeneratorService.getGeneratorObj(generator);
						if(TextUtil.isNotNull(resGener.getResNum())){
							sendResponse(Integer.valueOf(1), "综资设备无删除权限!");
						}else{
							this.pdaGeneratorService.updateGenerator(generator);
							sendResponse(Integer.valueOf(0), "修改成功。");
						}
					}else{
						this.pdaGeneratorService.updateGenerator(generator);
						sendResponse(Integer.valueOf(0), "修改成功。");
					}
				}else{
					int id = this.pdaGeneratorService.insertGenerator(generator);
					generator.setGeneratorId(id);
					sendResponse(Integer.valueOf(0), "修改成功。");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAGenerator.updateGenerator ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 检查机房
	 * @param name
	 * @return
	 */
	public Integer checkGener(String name,Integer id) {
		String checkSql = "select count(*) from job_generator where deleteFlag ='0' and generatorName ='"
				+ name + "'";
		if(TextUtil.isNotNull(id)){
			checkSql += " and generatorId !='"+id+"'";
		}
		int size = this.getJdbcTemplate().queryForInt(checkSql);
		return size;
	}

	/**
	 * 获取所有站点
	 * @return
	 */
 public String getStation() {
   try {
     StationBaseInfoBean station = (StationBaseInfoBean)getRequestObject(StationBaseInfoBean.class);
     if (station != null) {
       if ((station.getLat() != null) && (!(station.getLat().equals(""))) && (station.getLon() != null) && (!(station.getLon().equals("")))) {
    	  if(isWGS){
    		Point point = MapUtil.phone_db_encrypt(Double.parseDouble(station.getLat()), Double.parseDouble(station.getLon()));
    		station.setLat(point.getLat()+"");
    		station.setLon(point.getLng()+"");
    	  }  
         double[] arr = functions.getAround(Double.parseDouble(station.getLat()), Double.parseDouble(station.getLon()), ((this.start/this.limit)+1)*Integer.parseInt(properties.getValueByKey("gisLength")));
         station.setLats(String.valueOf(arr[0]));
         station.setLons(String.valueOf(arr[1]));
         station.setLate(String.valueOf(arr[2]));
         station.setLone(String.valueOf(arr[3]));
       }else{
    	   station.setStart(this.start);
    	   station.setLimit(this.limit);
       }
       if(TextUtil.isNotNull(super.getAreaName())){
    	   station.setRegion(this.getAreaName());
       }
       List<StationBaseInfoBean> stationList = this.pdaGeneratorService.getStation(station);
       for(StationBaseInfoBean obj: stationList){
    	   if(isWGS && TextUtil.isNotNull(obj.getLat()) && TextUtil.isNotNull(obj.getLon())){
    		   Point point = MapUtil.db_phone_encrypt(Double.parseDouble(obj.getLat()), Double.parseDouble(obj.getLon()));
      		   obj.setLat(point.getLat()+"");
      		   obj.setLon(point.getLng()+"");
    	   }
       }
       sendResponse(Integer.valueOf(0), stationList); 
     }else{
        sendResponse(Integer.valueOf(2), "请求参数错误。");
     }
   }
   catch (Exception e) {
     this.exception = e;
     sendResponse(Integer.valueOf(3), "应用服务器异常。");
     log.error("PDAGenerator.getGenerator ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
   }
   return "success";
 }

 
 /**
  * 增加站点
  * @return
  */
	public String insertStation() {
		try {
			StationBaseInfoBean station = (StationBaseInfoBean) getRequestObject(StationBaseInfoBean.class);
			if (station != null) {
				if (this.checkStation(station.getStationName(),null) > 0) {
					sendResponse(Integer.valueOf(2), "站点名称重复。");
				}else{
					if(TextUtil.isNotNull(super.realName)){
						station.setParties(super.realName);
					}
					if(isWGS){
						 Point point = MapUtil.phone_db_encrypt(Double.parseDouble(station.getLat()), Double.parseDouble(station.getLon()));
						 station.setLat(point.getLat()+"");
						 station.setLon(point.getLng()+"");
					}
					int id = this.pdaGeneratorService.insertStation(station);
					station.setDeleteFlag("0");
					station.setStationBaseId(Integer.valueOf(id));
					if(isWGS){
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(station.getLat()),Double.parseDouble(station.getLon()));
						station.setLat(point.getLat()+"");
						station.setLon(point.getLng()+"");
					}
					sendResponse(Integer.valueOf(0), station);
					
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAGenerator.insertGenerator ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 修改站点
	 * @return
	 */
	public String updateStation() {
		try {
			StationBaseInfoBean station = (StationBaseInfoBean) getRequestObject(StationBaseInfoBean.class);
			/*UserInfoBean user = (UserInfoBean) getInterfaceSession()
					.getAttribute("userBean");*/
			if (station != null) {
				if(this.checkStation(station.getStationName(),station.getStationBaseId()) >0){
					sendResponse(Integer.valueOf(2), "站点名称重复。");
				}else{
					if(TextUtil.isNotNull(super.realName)){
						station.setParties(super.realName);
					}
					if(isWGS){
						 Point point = MapUtil.phone_db_encrypt(Double.parseDouble(station.getLat()), Double.parseDouble(station.getLon()));
						 station.setLat(point.getLat()+"");
						 station.setLon(point.getLng()+"");
					}
					if(TextUtil.isNotNull(station.getStationBaseId())){
						if(station.getDeleteFlag().equals("1")){
							StationBaseInfoBean resStaion = this.pdaGeneratorService.getStationObj(station);
							if(TextUtil.isNotNull(resStaion.getResNum())){
								sendResponse(Integer.valueOf(1), "综资设备无权删除!");
							}else{
								this.pdaGeneratorService.updateStation(station);
								sendResponse(Integer.valueOf(0), "修改成功");
							}
						}else{
							this.pdaGeneratorService.updateStation(station);
							sendResponse(Integer.valueOf(0), "修改成功");
							//移动站点位置
							if(super.toIrms){
								StationBaseInfoBean resStaion = this.pdaGeneratorService.getStationObj(station);
								resStaion.setLat(station.getLat());
								resStaion.setLon(station.getLon());
								new moveSite(resStaion).start();
							}
						}
					}else{
						int id = this.pdaGeneratorService.insertStation(station);
						station.setStationBaseId(id);
						sendResponse(Integer.valueOf(0), station);
					}
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAGenerator.updateGenerator ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 访问综资进程
	 * @author chenqp
	 *
	 */
	class moveSite extends Thread{
		private StationBaseInfoBean station;
		public moveSite(StationBaseInfoBean station){
			this.station = station;
		}
		@Override
		public void run() {
			irmsStationService.movStation(station);
			super.run();
		}
	}
	
	/**
	 * 检测站点
	 * @param name
	 * @return
	 */
	public Integer checkStation(String name,Integer id){
		String checkSql = "select count(*) from job_stationbase where deleteFlag ='0' and stationName='"
				+ name + "'";
		if(TextUtil.isNotNull(id)){
			checkSql += " and stationBaseId !='"+id+"'";
		}
		Integer size = getJdbcTemplate().queryForInt(checkSql);
		return size;
	}

 public String getPower() {
   try {
     HighFrequencySwitchingPowerSupplyInfoBean power = (HighFrequencySwitchingPowerSupplyInfoBean)getRequestObject(HighFrequencySwitchingPowerSupplyInfoBean.class);
     if (power != null) {
       List<HighFrequencySwitchingPowerSupplyInfoBean> powerList = this.pdaGeneratorService.getPower(power);
       String filePath = getServletContext().getRealPath("/") + "upload";
       File dir = new File(filePath);
       String[] fileList = dir.list();
       if ((fileList != null) && (powerList != null)) {
         for (HighFrequencySwitchingPowerSupplyInfoBean p : powerList) {
           String fileName = "h_" + p.getSwitchingId() + "_";
           String names = "";
           for (String name : fileList) {
             if (name.startsWith(fileName)) {
               names = names + name + ",";
             }
           }
           p.setImageNames(names);
         }
       }
       sendResponse(Integer.valueOf(0), powerList); 
     }else{
       sendResponse(Integer.valueOf(2), "请求参数错误。");
     }
   }
   catch (Exception e) {
     this.exception = e;
     sendResponse(Integer.valueOf(3), "应用服务器异常。");
     log.error("PDAGenerator.getGenerator ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
   }
   return "success";
 }

 public String insertPower() {
   try {
     HighFrequencySwitchingPowerSupplyInfoBean power = (HighFrequencySwitchingPowerSupplyInfoBean)getRequestObject(HighFrequencySwitchingPowerSupplyInfoBean.class);
     UserInfoBean user = (UserInfoBean)getInterfaceSession().getAttribute("userBean");
     if (power != null) {
       int id = this.pdaGeneratorService.insertPower(power);
       if (id != -1) {
         HighFrequencySwitchingPowerSupplyInfoBean p = new HighFrequencySwitchingPowerSupplyInfoBean();
         p.setSwitchingId(Integer.valueOf(id));
         sendResponse(Integer.valueOf(0), power); 
       }
       sendResponse(Integer.valueOf(2), "资产标签已被占用。"); 
     }else{
       sendResponse(Integer.valueOf(2), "请求参数错误。");
     }
   }
   catch (Exception e) {
     this.exception = e;
     sendResponse(Integer.valueOf(3), "应用服务器异常。");
     log.error("PDAGenerator.insertGenerator ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
   }
   return "success";
 }

 public String updatePower() {
   try {
     HighFrequencySwitchingPowerSupplyInfoBean power = (HighFrequencySwitchingPowerSupplyInfoBean)getRequestObject(HighFrequencySwitchingPowerSupplyInfoBean.class);
     UserInfoBean user = (UserInfoBean)getInterfaceSession().getAttribute("userBean");
     if (power != null) {
       int result = this.pdaGeneratorService.updatePower(power);
       if (result == 0) {
         sendResponse(Integer.valueOf(0), "修改成功。"); 
       }
       sendResponse(Integer.valueOf(2), "资产标签已被使用。"); 
     }else{
    	 sendResponse(Integer.valueOf(2), "请求参数错误。"); 
     }
   }
   catch (Exception e) {
     this.exception = e;
     sendResponse(Integer.valueOf(3), "应用服务器异常。");
     log.error("PDAGenerator.updateGenerator ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
   }
   return "success";
 }

 public PDAGeneratorService getPdaGeneratorService() {
   return this.pdaGeneratorService;
 }
 public IirmsStationService getIrmsStationService() {
	return irmsStationService;
}
public void setIrmsStationService(IirmsStationService irmsStationService) {
	this.irmsStationService = irmsStationService;
}
public IirmsQueryService getIrmsQueryService() {
	return irmsQueryService;
}
public void setIrmsQueryService(IirmsQueryService irmsQueryService) {
	this.irmsQueryService = irmsQueryService;
}
public void setPdaGeneratorService(PDAGeneratorService pdaGeneratorService) {
   this.pdaGeneratorService = pdaGeneratorService;
 }
 }