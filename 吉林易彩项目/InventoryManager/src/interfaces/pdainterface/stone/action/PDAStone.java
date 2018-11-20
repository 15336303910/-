package interfaces.pdainterface.stone.action;

import interfaces.irmsInterface.interfaces.outLine.service.IrmsOutLineService;
import interfaces.irmsInterface.interfaces.outLine.service.impl.IirmsOutLineService;

import java.util.List;
import java.util.Map;

import manage.buriedPart.pojo.BuriedPartObj;
import manage.buriedPart.service.impl.IburiedPartService;
import manage.stone.pojo.StoneInfoBean;
import manage.stone.service.StoneInfoService;
import manage.stone.service.impl.IstoneInfoService;

import org.apache.log4j.Logger;

import base.util.MapUtil;
import base.util.TextUtil;
import base.util.functions;
import base.util.pojo.Point;
import base.web.InterfaceAction;

public class PDAStone extends InterfaceAction{
	private static final Logger log = Logger.getLogger(PDAStone.class);
	private IstoneInfoService stoneService;
	private IburiedPartService buriedPartServie;
	private IirmsOutLineService irmsOutLineService;
	/**
	 * 得到标石列表
	 * @return
	 */
	public String getStone(){
		try{
			StoneInfoBean obj = (StoneInfoBean) getRequestObject(StoneInfoBean.class);
			if(TextUtil.isNull(obj.getBuriedId()) && TextUtil.isNotNull(obj.getLongitude()) && TextUtil.isNotNull(obj.getLatitude())){
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
			if(TextUtil.isNull(obj.getBuriedId()) && TextUtil.isNull(obj.getStoneId()) && TextUtil.isNotNull(super.areaName)){
				obj.setStoneArea(super.areaName);
			}
			List<StoneInfoBean> list = stoneService.getStoneGrid(obj);
			if(list !=null){
				for(StoneInfoBean ston : list){
					if(isWGS && TextUtil.isNotNull(ston.getLatitude()) && TextUtil.isNotNull(ston.getLongitude())){
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(ston.getLatitude()), Double.parseDouble(ston.getLongitude()));
						ston.setLatitude(point.getLat()+"");
						ston.setLongitude(point.getLng()+"");
					}
				}
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAStone.getStone ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 修改标石
	 * @return
	 */
	public String updateStone(){
		try{
			StoneInfoBean obj = (StoneInfoBean) getRequestObject(StoneInfoBean.class);
			if(isWGS && TextUtil.isNotNull(obj.getLatitude()) && TextUtil.isNotNull(obj.getLongitude())){
				Point point = MapUtil.phone_db_encrypt(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()));
       			obj.setLatitude(point.getLat()+"");
       			obj.setLongitude(point.getLng()+"");
			}
			obj.setLastUpMan(this.longiner);
			if(TextUtil.isNotNull(super.realName)){
				obj.setMaintainer(super.realName);
			}
			if(TextUtil.isNull(obj.getStoneId())){
				int result = stoneService.insertStone(obj);
				obj.setStoneId(result);
				this.InsertBuriedPart(obj);
				sendResponse(Integer.valueOf(0), "修改成功。");
			}else{
				if(TextUtil.isNotNull(obj.getDeleteflag()) && obj.getDeleteflag().equals("1")){
					StoneInfoBean stone = this.stoneService.getStoneById(obj.getStoneId());
					//强制删除forcedel
					if(this.stoneService.getStoneLay(stone) && !super.forceDel){
						sendResponse(Integer.valueOf(1), "存在敷设关系,请勿删除!");
					}else{
						//不存在敷设关系
						this.delBuriedPart(obj);
						stoneService.updateStone(obj);
						sendResponse(Integer.valueOf(0), "修改成功。");
					}
				}else{
					stoneService.updateStone(obj);
					sendResponse(Integer.valueOf(0), "修改成功。");
					//进行修改相应的直埋段
					new upBuriedSegThread(obj.getStoneId(), obj.getStoneName()).start();
					//同步综资
					if(toIrms){
						new moveStone(obj.getStoneId()).start();
					}
				}
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAStone.updateStone ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 更新数据
	 * @author chenqp
	 *
	 */
	class upBuriedSegThread extends Thread{
		private Integer stoneId;
		private String stoneName;
		
		public upBuriedSegThread(Integer stoneId,String stoneName){
			this.stoneId = stoneId;
			this.stoneName = stoneName;
		}

		@Override
		public void run() {
			stoneService.upBuriedSeg(stoneId, stoneName);
			super.run();
		}
	}
	
	
	/**
	 * 移动标石
	 * @author chenqp
	 *
	 */
	class moveStone extends Thread{
		private Integer stoneId;
		public moveStone(Integer stoneId){
			this.stoneId = stoneId;
		}
		@Override
		public void run() {
			StoneInfoBean stone = stoneService.getStoneById(this.stoneId);
			if(TextUtil.isNotNull(stone.getResNum())){
				irmsOutLineService.moveStone(stone);
			}
			super.run();
		}
	}
	
	/**
	 * 添加标石
	 * @return
	 */
	public String insertStone(){
		try{
			StoneInfoBean obj = (StoneInfoBean) getRequestObject(StoneInfoBean.class);
			if(this.checkStone(obj.getStoneName()) > 0){
				sendResponse(Integer.valueOf(2), "标石名称已占用。");
			}else{
				if(isWGS && TextUtil.isNotNull(obj.getLatitude()) && TextUtil.isNotNull(obj.getLongitude())){
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()));
	       			obj.setLatitude(point.getLat()+"");
	       			obj.setLongitude(point.getLng()+"");
				}
				obj.setCreater(this.longiner);
				if(TextUtil.isNotNull(super.realName)){
					obj.setMaintainer(super.realName);
				}
				int result = stoneService.insertStone(obj);
				obj.setStoneId(result);
				if(isWGS){
					Point point = MapUtil.db_phone_encrypt(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()));
					obj.setLatitude(point.getLat()+"");
					obj.setLongitude(point.getLng()+"");
				}
				sendResponse(Integer.valueOf(0), obj);
				//增加一个标石
				if(toIrms){
					new addStoneThread(obj.getStoneId()).start();
				}
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDAStone.insertStone ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 增加标石的
	 * @author chenqp
	 *
	 */
	class addStoneThread extends Thread{
		private Integer stoneId;
		public addStoneThread(Integer stoneId){
			this.stoneId = stoneId;
		}
		@Override
		public void run() {
			StoneInfoBean stone = stoneService.getStoneById(stoneId);
			irmsOutLineService.addStone(stone);
			super.run();
		}
	}
	
	/**
	 * 增加直埋段
	 * @param obj
	 */
	public void InsertBuriedPart(StoneInfoBean obj){
		if(TextUtil.isNotNull(obj.getPreviousStoneID()) && TextUtil.isNotNull(obj.getPreviousStoneName())){
			BuriedPartObj buriedPart = new BuriedPartObj();
			buriedPart.setBuriedId(obj.getBuriedId());
			buriedPart.setCreater(this.longiner);
			buriedPart.setBuriedPartName(obj.getPreviousStoneName()+"-"+obj.getStoneName()+"直埋段");
			buriedPart.setBuriedPartStart(obj.getPreviousStoneName());
			buriedPart.setBuriedPartStartId(obj.getPreviousStoneID()+"");
			buriedPart.setBuriedPartEnd(obj.getStoneName());
			buriedPart.setBuriedPartEndId(obj.getStoneId()+"");
			buriedPart.setDeleteflag("0");
			buriedPart.setBuriedPartArea(obj.getStoneArea());
			this.buriedPartServie.insertBuriedPart(buriedPart);
		}
	}
	
	/**
	 * 删除对应的直埋段
	 * @param obj
	 */
	public void delBuriedPart(StoneInfoBean obj){
		String sql ="update buriedpartinfo set deleteflag ='1'"
				+ " where buriedPartStartId='"+obj.getStoneId()+"'"
				+ " or buriedPartEndId ='"+obj.getStoneId()+"'";
		this.getJdbcTemplate().execute(sql);
		if(properties.getValueByKey("province").equals("北京")){
			new delStoneSeg(obj.getStoneId()).start();
		}
	}
	
	/**
	 * 删除对应的
	 * @author chenqp
	 *
	 */
	class delStoneSeg extends Thread{
		private Integer stoneId;
		public delStoneSeg(Integer stoneId){
			this.stoneId = stoneId;
		}
		@Override
		public void run() {
			String sql = "select  stoneNum , buriedId from stoneinfo where stoneId ='"+stoneId+"'";
			Map<String, Object> map = getJdbcTemplate().queryForMap(sql);
			if(map!= null){
				Integer stoneNum = Integer.parseInt(map.get("stoneNum")+"");
				Integer buriedId = Integer.parseInt(map.get("buriedId")+"");
				if(TextUtil.isNotNull(stoneNum) && TextUtil.isNotNull(buriedId)){
					StoneInfoBean start = new StoneInfoBean();
					start.setStoneNum((stoneNum-1)+"");
					start.setBuriedId(buriedId+"");
					start = stoneService.getStoneObj(start);
					
					StoneInfoBean end = new StoneInfoBean();
					end.setStoneNum((stoneNum +1)+"");
					end.setBuriedId(buriedId+"");
					end = stoneService.getStoneObj(end);
					
					BuriedPartObj stoneSeg = new BuriedPartObj();
					stoneSeg.setBuriedPartStart(start.getStoneName());
					stoneSeg.setBuriedPartStartId(start.getStoneId()+"");
					stoneSeg.setBuriedPartEnd(end.getStoneName());
					stoneSeg.setBuriedPartEndId(end.getStoneId()+"");
					stoneSeg.setBuriedId(buriedId+"");
					stoneSeg.setBuriedPartArea(end.getStoneArea());
					stoneSeg.setDataQualitier(end.getDataQualitier());
					stoneSeg.setMaintainer(end.getMaintainer());
					stoneSeg.setBuriedPartName(start.getStoneName()+"-"+end.getStoneName()+"直埋");
					stoneSeg.setPropertyDept(end.getPropertyComp());
					stoneSeg.setPropertyRight(end.getPropertyNature());
					stoneSeg.setBuriedPartLength(MapUtil.Distance(Double.parseDouble(start.getLongitude()),
							Double.parseDouble(start.getLatitude()), 
							Double.parseDouble(end.getLongitude()),
							Double.parseDouble(end.getLatitude())));
					stoneSeg.setMaintainLength(stoneSeg.getBuriedPartLength());
					buriedPartServie.insertBuriedPart(stoneSeg);
				}
			}
			super.run();
		}
	}
	
	/**
	 * 检查直埋信息
	 * @param name
	 * @return
	 */
	public Integer checkStone(String name){
		String sql = "select count(*) from stoneinfo where stoneName='"+name+"' and deleteflag='0'";
		int size = this.getJdbcTemplate().queryForInt(sql);
		return size;
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
	public static Logger getLog() {
		return log;
	}
	public IburiedPartService getBuriedPartServie() {
		return buriedPartServie;
	}
	public void setBuriedPartServie(IburiedPartService buriedPartServie) {
		this.buriedPartServie = buriedPartServie;
	}
}
