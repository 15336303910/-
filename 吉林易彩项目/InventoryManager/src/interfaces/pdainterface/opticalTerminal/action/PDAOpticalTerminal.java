package interfaces.pdainterface.opticalTerminal.action;

import interfaces.irmsInterface.interfaces.opticTran.service.impl.IirmsOpticTranService;
import interfaces.pdainterface.equt.service.PDAEqutInfoService;

import java.util.ArrayList;
import java.util.List;

import manage.equt.pojo.ODMInfoBean;
import manage.opticalTerminal.pojo.OpticalTerminalObj;
import manage.opticalTerminal.service.impl.IopticalTerminalService;

import org.apache.log4j.Logger;

import base.util.MapUtil;
import base.util.TextUtil;
import base.util.functions;
import base.util.pojo.Point;
import base.web.InterfaceAction;

public class PDAOpticalTerminal extends InterfaceAction{
	private static final Logger log = Logger.getLogger(PDAOpticalTerminal.class);
	private IopticalTerminalService opticalTerminalService;
	//用于更新端子信息和面板信息
	private PDAEqutInfoService pdaEqutInfoService;
	private IirmsOpticTranService irmsOpticTranService;
	/**
	 * 得到光终端盒
	 * @return
	 */
	public String getOpticalTer(){
		try{
			OpticalTerminalObj  obj = (OpticalTerminalObj) getRequestObject(OpticalTerminalObj.class);
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
				obj.setTerminalAddres(super.areaName);
			}
			List<OpticalTerminalObj> list = opticalTerminalService.getOpticalTerGrid(obj);
			if(list !=null ){
				for(OpticalTerminalObj object :list){
					if(isWGS && TextUtil.isNotNull(object.getLatitude()) && TextUtil.isNotNull(object.getLongitude())){
						 Point point = MapUtil.db_phone_encrypt(Double.parseDouble(object.getLatitude()), Double.parseDouble(object.getLongitude()));
				    	 object.setLatitude(point.getLat()+"");
				    	 object.setLongitude(point.getLng()+"");
					}
				}
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAOpticalTerminal.getOpticalTer ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 修改光终端盒
	 * @return
	 */
	public String updateOpticalTer(){
		try{
			OpticalTerminalObj  obj = (OpticalTerminalObj) getRequestObject(OpticalTerminalObj.class);
			if(isWGS && TextUtil.isNotNull(obj.getLatitude())  && TextUtil.isNotNull(obj.getLongitude())){
				 Point point = MapUtil.phone_db_encrypt(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()));
				 obj.setLatitude(point.getLat()+"");
				 obj.setLongitude(point.getLng()+"");
			}
			if(TextUtil.isNotNull(super.realName)){
				obj.setMaintainer(super.realName);
			}
			if(TextUtil.isNull(obj.getId())){
				int result = opticalTerminalService.insertOpticalTer(obj);
				obj.setId(result);
				this.insertOdm(obj);
				sendResponse(Integer.valueOf(0), "修改成功。");
			}else{
				int result = opticalTerminalService.updateOpticalTer(obj);
				if(obj.getDeleteflag().equals("1")){
					this.delOdm(obj);
				}else{
					this.updateOdm(obj);
					if(toIrms){
						new upOptTerm(obj.getId()).start();
					}
				}
				sendResponse(Integer.valueOf(0), "修改成功。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAOpticalTerminal.updateOpticalTer ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 修改光终端盒
	 * @author chenqp
	 *
	 */
	class upOptTerm extends Thread{
		private Integer id;
		public upOptTerm(Integer id){
			this.id = id;
		}
		@Override
		public void run() {
			OpticalTerminalObj obj = new OpticalTerminalObj();
			obj.setId(id);
			obj = opticalTerminalService.getOptTerObj(obj);
			irmsOpticTranService.moveOptTerm(obj);
			super.run();
		}
	}
	
	
	/**
	 * 增加光终端盒
	 * @return
	 */
	public String insertOpticalTer(){
		try{
			OpticalTerminalObj  obj = (OpticalTerminalObj) getRequestObject(OpticalTerminalObj.class);
			if(this.checkOpterm(obj.getTerminalName()) > 0){
				sendResponse(Integer.valueOf(2), "资产标签被占用。");
			}else{
				if(isWGS){
					 Point point = MapUtil.phone_db_encrypt(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()));
					 obj.setLatitude(point.getLat()+"");
					 obj.setLongitude(point.getLng()+"");
				}
				if(TextUtil.isNotNull(super.realName)){
					obj.setMaintainer(super.realName);
				}
				int result = opticalTerminalService.insertOpticalTer(obj);
				if(isWGS){
					 Point point = MapUtil.db_phone_encrypt(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()));
					 obj.setLatitude(point.getLat()+"");
					 obj.setLongitude(point.getLng()+"");
				}
				obj.setId(result);
				//this.insertOdm(obj);
				sendResponse(Integer.valueOf(0), obj);
				//增加光终端盒
				if(toIrms){
					new addOpticalTerminal(obj.getId());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAOpticalTerminal.insertOpticalTer ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 增加光终端盒
	 * 同步到综资
	 * @author chenqp
	 *
	 */
	class addOpticalTerminal extends Thread{
		private Integer id;
		public addOpticalTerminal(Integer id){
			this.id = id;
		}
		@Override
		public void run() {
			OpticalTerminalObj pojo = new OpticalTerminalObj();
			pojo.setId(id);
			pojo = opticalTerminalService.getOptTerObj(pojo);
			irmsOpticTranService.addOptTerm(pojo);
			super.run();
		}
	}
	
	/**
	 * 增加ODM信息
	 * @param obj
	 */
	public void insertOdm(OpticalTerminalObj obj){
		ODMInfoBean odm = new ODMInfoBean();
		odm.setOdmName(obj.getTerminalName()+"-"+"01");
		odm.setOdmCode("1");
		odm.setTerminalRowQuantity(obj.getRowNum()+"");
		odm.setTerminalColumnQuantity(obj.getColNum()+"");
		odm.setEid("GZDH_"+obj.getId());
		odm.setPosX("0.5");
		odm.setPosY("0.5");
		List<ODMInfoBean> list = new ArrayList<ODMInfoBean>();
		list.add(odm);
		this.pdaEqutInfoService.insertODM(list,this.longiner);
	}
	
	/**
	 * 删除odm信息
	 * @param obj
	 */
	public void delOdm(OpticalTerminalObj obj){
		String upOdm = "update job_odm set deleteFlag='1' where eid='GZDH_"+obj.getId()+"'";
		//String upPoint ="update job_pointinfo set del=1 where EID='GZDH_"+obj.getId()+"'";
		this.getJdbcTemplate().execute(upOdm);
		//this.getJdbcTemplate().execute(upPoint);
	}
	
	
	/**
	 * 修改odm
	 * @param obj
	 */
	public void updateOdm(OpticalTerminalObj obj){
		ODMInfoBean odm = new ODMInfoBean();
		odm.setEid("GZDH_"+obj.getId());
		List<ODMInfoBean> odmList = this.pdaEqutInfoService.getODM(odm);
		if(TextUtil.isNotNull(odmList)){
			for(ODMInfoBean odmObj : odmList){
				odmObj.setTerminalRowQuantity(obj.getRowNum()+"");
				odmObj.setTerminalColumnQuantity(obj.getColNum()+"");
			}
			this.pdaEqutInfoService.updateODM(odmList);
		}else{
			odm.setOdmCode("1");
			odm.setTerminalRowQuantity(obj.getRowNum()+"");
			odm.setTerminalColumnQuantity(obj.getColNum()+"");
			odm.setPosX("0.5");
			odm.setPosY("0.5");
			List<ODMInfoBean> list = new ArrayList<ODMInfoBean>();
			list.add(odm);
			this.pdaEqutInfoService.insertODM(list,this.longiner);
		}
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public Integer checkOpterm(String name){
		String sql ="select count(*) from optical_terminal where deleteflag='0' and terminalName='"+name+"'";
		int size = this.getJdbcTemplate().queryForInt(sql);
		return size;
	}
	
	
	public IopticalTerminalService getOpticalTerminalService() {
		return opticalTerminalService;
	}
	public void setOpticalTerminalService(
			IopticalTerminalService opticalTerminalService) {
		this.opticalTerminalService = opticalTerminalService;
	}
	public static Logger getLog() {
		return log;
	}
	public PDAEqutInfoService getPdaEqutInfoService() {
		return pdaEqutInfoService;
	}
	public void setPdaEqutInfoService(PDAEqutInfoService pdaEqutInfoService) {
		this.pdaEqutInfoService = pdaEqutInfoService;
	}

	public IirmsOpticTranService getIrmsOpticTranService() {
		return irmsOpticTranService;
	}

	public void setIrmsOpticTranService(IirmsOpticTranService irmsOpticTranService) {
		this.irmsOpticTranService = irmsOpticTranService;
	}
	
}
