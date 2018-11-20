package interfaces.pdainterface.buried.action;

import java.util.List;
import manage.buried.pojo.BuriedInfoObj;
import manage.buried.service.impl.IburiedService;
import org.apache.log4j.Logger;
import base.util.TextUtil;
import base.web.InterfaceAction;

public class PDABuried extends InterfaceAction{
	private static final Logger log = Logger.getLogger(PDABuried.class);
	private IburiedService buriedService;
	
	/**
	 * 得到直埋信息
	 * @return
	 */
	public String getBuried(){
		try{
			BuriedInfoObj buried = (BuriedInfoObj) getRequestObject(BuriedInfoObj.class);
			buried.setStart(this.start);
			buried.setLimit(this.limit);
			List<BuriedInfoObj> list = buriedService.getBuriedGrid(buried);
			if(list !=null){
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			e.printStackTrace();
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDABuried.getBuried ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 更新直埋信息
	 * @return
	 */
	public String updateBuried(){
		try{
			BuriedInfoObj buried = (BuriedInfoObj) getRequestObject(BuriedInfoObj.class);
			int result;
			buried.setDataQualitier(this.longiner);
			if(TextUtil.isNotNull(buried.getBuriedId())){
				buried.setLastUpTime(this.invokTime);
				buried.setLastUpMan(this.longiner);
				buried.setMaintainer(this.longiner);
				result= this.buriedService.updateBuried(buried);
				sendResponse(Integer.valueOf(0), "修改成功。");
			}else{
				buried.setCreater(this.longiner);
				buried.setCreateTime(this.invokTime);
				result=this.buriedService.insertBuried(buried);
				buried.setBuriedId(result);
				sendResponse(Integer.valueOf(0),buried);
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDABuried.updateBuried ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 添加直埋信息
	 * @return
	 */
	public String insertBuried(){
		try{
			BuriedInfoObj buried = (BuriedInfoObj) getRequestObject(BuriedInfoObj.class);
			if(this.checkBuried(buried.getBuriedName()) > 0){
				sendResponse(Integer.valueOf(2), "资产标签被占用。");
			}else{
				buried.setCreater(this.longiner);
				buried.setCreateTime(this.invokTime);
				int result = this.buriedService.insertBuried(buried);
				buried.setBuriedId(result);
				sendResponse(Integer.valueOf(0),buried);
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDABuried.insertBuried ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Integer checkBuried(String name){
		String sql = "select count(*) from buriedInfo where deleteflag='0' and buriedName='"+name+"'";
		int size = this.getJdbcTemplate().queryForInt(sql);
		return size;
	}
	
	
	
	public IburiedService getBuriedService() {
		return buriedService;
	}
	public void setBuriedService(IburiedService buriedService) {
		this.buriedService = buriedService;
	}
	public static Logger getLog() {
		return log;
	}
}
