package interfaces.pdainterface.poleline.action;

import base.util.MapUtil;
import base.util.TextUtil;
import base.util.functions;
import base.util.pojo.Point;
import base.web.InterfaceAction;
import interfaces.pdainterface.poleline.service.PDAPolelineCustomService;
import java.util.List;
import manage.poleline.pojo.PoleCustomInfoBean;
import org.apache.log4j.Logger;

public class PDAPolelineCustomAction extends InterfaceAction {
	private static final long serialVersionUID = -2978337211712279460L;
	private static final Logger log = Logger.getLogger(PDAPolelineCustomAction.class);
	private PDAPolelineCustomService  pdaPolelineCustomService;

	/**
	 * 得到所有的电杆
	 * @return
	 */
	public String getPoleCustom() {
		try {
			PoleCustomInfoBean pole = (PoleCustomInfoBean) getRequestObject(PoleCustomInfoBean.class);
			if (pole != null) {
				if(TextUtil.isNull(pole.getInt_id()) && TextUtil.isNotNull(pole.getLatitude()) && TextUtil.isNotNull(pole.getLongitude())){
					if(isWGS){
						Point point = MapUtil.phone_db_encrypt(Double.parseDouble(pole.getLatitude()),Double.parseDouble(pole.getLongitude()));
						pole.setLatitude(point.getLat()+"");
						pole.setLongitude(point.getLng()+"");
					}
					double[] arr = functions.getAround(Double.parseDouble(pole.getLatitude()),Double.parseDouble(pole.getLongitude()), ((this.start/this.limit)+1)*Integer.parseInt(properties.getValueByKey("gisLength")));
					pole.setLats(String.valueOf(arr[0]));
					pole.setLons(String.valueOf(arr[1]));
					pole.setLate(String.valueOf(arr[2]));
					pole.setLone(String.valueOf(arr[3]));
				}else{
					pole.setStart(this.start);
					pole.setLimit(this.limit);
				}	
				pole.setStateflag(0);
				List<PoleCustomInfoBean> list = this.pdaPolelineCustomService.getPoleCustom(pole);
				for (PoleCustomInfoBean obj : list) {
					if(isWGS){
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
			log.error("PDAPoleline.getPoleCustom ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	
	/**
	 * 增加电杆
	 * @return
	 */
	public String insertPoleCustom() {
		try {
			PoleCustomInfoBean pole = (PoleCustomInfoBean) getRequestObject(PoleCustomInfoBean.class);
			if(isWGS){
				Point point = MapUtil.phone_db_encrypt(Double.parseDouble(pole.getLatitude()),Double.parseDouble(pole.getLongitude()));
				pole.setLatitude(point.getLat() + "");
				pole.setLongitude(point.getLng() + "");
			}
			if(pole.getZh_label().contains("#")) {
				String poleName = pole.getZh_label().replaceAll("#", "号");
				pole.setZh_label(poleName);
			}
			pole.setCreator(realName);
			int i = this.pdaPolelineCustomService.insertPoleCustom(pole).intValue();
			pole.setInt_id(Integer.valueOf(i));
			if (i > 0) {
				if(isWGS){
					Point point = MapUtil.db_phone_encrypt(Double.parseDouble(pole.getLatitude()),Double.parseDouble(pole.getLongitude()));
					pole.setLatitude(point.getLat() + "");
					pole.setLongitude(point.getLng() + "");
				}
				sendResponse(Integer.valueOf(0), pole);				
			} else {
				sendResponse(Integer.valueOf(4), "名称重复");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPoleline.insertPoleCustom ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 修改电杆
	 * @return
	 */
	public String updatePoleCustom() {
		try {
			PoleCustomInfoBean pole = (PoleCustomInfoBean) getRequestObject(PoleCustomInfoBean.class);
			if (pole != null) {
				if(isWGS){
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(pole.getLatitude()),Double.parseDouble(pole.getLongitude()));
					pole.setLatitude(point.getLat() + "");
					pole.setLongitude(point.getLng() + "");
				}
				if(pole.getZh_label() != null && pole.getZh_label().contains("#")) {
					String poleName = pole.getZh_label().replaceAll("#", "号");
					pole.setZh_label(poleName);
				}
				pole.setModifier(realName);
				int i = this.pdaPolelineCustomService.updatePoleCustom(pole).intValue();
				if (i > 0) {
					if(isWGS){
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(pole.getLatitude()),Double.parseDouble(pole.getLongitude()));
						pole.setLatitude(point.getLat() + "");
						pole.setLongitude(point.getLng() + "");
					}
					sendResponse(Integer.valueOf(0), pole);
					//new upPoleLineThread(pole.getPoleId(), pole.getPoleName()).start();
				} else {	
					sendResponse(Integer.valueOf(4), "名称重复");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * 删除电杆
	 * @return
	 */
	public String deletePoleCustom() {
		try {
			PoleCustomInfoBean pole = (PoleCustomInfoBean) getRequestObject(PoleCustomInfoBean.class);
			if (pole != null) {
				pole.setModifier(realName);
				this.pdaPolelineCustomService.deletePoleCustom(pole);
				sendResponse(Integer.valueOf(0), "删除成功");
				//this.pdaPolelineService.upPloeLineSeg(pole.getInt_id()+"");				
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPolelineCustom.deletePoleCustom ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	public PDAPolelineCustomService getPdaPolelineCustomService() {
		return pdaPolelineCustomService;
	}


	public void setPdaPolelineCustomService(PDAPolelineCustomService pdaPolelineCustomService) {
		this.pdaPolelineCustomService = pdaPolelineCustomService;
	}
	
	
}