package interfaces.pdainterface.collectTrack.action;

import java.util.List;

import org.apache.log4j.Logger;

import interfaces.pdainterface.collectTrack.pojo.CollectTrackBean;
import interfaces.pdainterface.collectTrack.service.impl.ICollectTrackService;
import base.util.MapUtil;
import base.util.TextUtil;
import base.util.pojo.Point;
import base.web.InterfaceAction;

/**
 * 记录轨迹
 * @author chenqp
 *
 */
public class PDACollectTrack extends InterfaceAction{
	private static final Logger log = Logger.getLogger(PDACollectTrack.class);
	private ICollectTrackService collectTrackService;
	
	/**
	 * 得到某人的轨迹点
	 * @return
	 */
	public String getCollectTrack(){
		try{
			CollectTrackBean obj = (CollectTrackBean) getRequestObject(CollectTrackBean.class);
			if(TextUtil.isNotNull(obj.getLatitude()) && TextUtil.isNotNull(obj.getLongitude())){
				Point point = MapUtil.wgs_gcj_encrypts(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()));
				obj.setLatitude(point.getLat() + "");
				obj.setLongitude(point.getLng() + "");
			}
			List<CollectTrackBean>  list = this.collectTrackService.getCollectTrack(obj);
			if(TextUtil.isNotNull(list)){
				for(CollectTrackBean bean : list){
					Point point = MapUtil.wgs_gcj_encrypts(Double.parseDouble(bean.getLatitude()), Double.parseDouble(bean.getLongitude()));
					bean.setLatitude(point.getLat()+"");
					bean.setLongitude(point.getLng()+"");
				}
				sendResponse(Integer.valueOf(0), list);
			}else{
				sendResponse(Integer.valueOf(2), "查无数据。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDACollectTrack.getCollectTrack ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 增加轨迹点
	 * @return
	 */
	public String addCollectTrack(){
		try{
			CollectTrackBean obj = (CollectTrackBean) getRequestObject(CollectTrackBean.class);
			if(obj != null){
				if(TextUtil.isNotNull(obj.getLatitude()) && TextUtil.isNotNull(obj.getLongitude())){
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()));
					obj.setLatitude(point.getLat() + "");
					obj.setLongitude(point.getLng() + "");
				}
				int id = this.collectTrackService.addCollectTrack(obj);
				obj.setId(id);
				if(TextUtil.isNotNull(obj.getLatitude()) && TextUtil.isNotNull(obj.getLongitude())){
					Point point = MapUtil.db_phone_encrypt(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()));
					obj.setLatitude(point.getLat()+"");
					obj.setLongitude(point.getLng()+"");
				}
				sendResponse(Integer.valueOf(0), obj);
			}else{
				sendResponse(Integer.valueOf(1), "请求参数异常。");
			}
		}catch(Exception e){
			this.exception = e;
		    sendResponse(Integer.valueOf(3), "应用服务器异常。");
		    log.error("PDACollectTrack.addCollectTrack ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	public ICollectTrackService getCollectTrackService() {
		return collectTrackService;
	}
	public void setCollectTrackService(ICollectTrackService collectTrackService) {
		this.collectTrackService = collectTrackService;
	}
	public static Logger getLog() {
		return log;
	}
	
}
