package interfaces.pdainterface.poleline.action;

import java.util.List;

import org.apache.log4j.Logger;
import base.web.InterfaceAction;
import interfaces.pdainterface.poleline.service.PDAPoleSegCustomService;
import manage.poleline.pojo.PoleSegCustomInfoBean;

public class PDAPoleSegCustomAction extends InterfaceAction {
	private static final long serialVersionUID = -2978337211712279460L;
	private static final Logger log = Logger.getLogger(PDAPoleSegCustomAction.class);
	private PDAPoleSegCustomService  pdaPoleSegCustomService;

	/**
	 * 得到所有的杆路段
	 * @return
	 */
	public String getPoleSegCustom() {
		try {
			PoleSegCustomInfoBean poleSeg = (PoleSegCustomInfoBean) getRequestObject(PoleSegCustomInfoBean.class);
			if (poleSeg != null) {
				poleSeg.setStart(this.start);
				poleSeg.setLimit(this.limit);
				poleSeg.setStateflag(0);
				List<PoleSegCustomInfoBean> list = this.pdaPoleSegCustomService.getPoleSegCustom(poleSeg);
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
	 * 增加杆路段
	 * @return
	 */
	public String insertPoleSegCustom() {
		try {
			PoleSegCustomInfoBean poleSeg = (PoleSegCustomInfoBean) getRequestObject(PoleSegCustomInfoBean.class);

			if(poleSeg.getZh_label().contains("#")) {
				String poleName = poleSeg.getZh_label().replaceAll("#", "号");
				poleSeg.setZh_label(poleName);
			}
			poleSeg.setCreator(realName);
			int i = this.pdaPoleSegCustomService.insertPoleSegCustom(poleSeg).intValue();
			poleSeg.setInt_id(Integer.valueOf(i));
			if (i > 0) {

				sendResponse(Integer.valueOf(0), poleSeg);				
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
	 * 修改杆路段
	 * @return
	 */
	public String updatePoleSegCustom() {
		try {
			PoleSegCustomInfoBean poleSeg = (PoleSegCustomInfoBean) getRequestObject(PoleSegCustomInfoBean.class);
			if (poleSeg != null) {

				if(poleSeg.getZh_label().contains("#")) {
					String poleName = poleSeg.getZh_label().replaceAll("#", "号");
					poleSeg.setZh_label(poleName);
				}
				poleSeg.setModifier(realName);
				int i = this.pdaPoleSegCustomService.updatePoleSegCustom(poleSeg).intValue();
				if (i > 0) {
					if(isWGS){

					}
					sendResponse(Integer.valueOf(0), poleSeg);
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
	 * 删除杆路段
	 * @return
	 */
	public String deletePoleSegCustom() {
		try {
			PoleSegCustomInfoBean poleSeg = (PoleSegCustomInfoBean) getRequestObject(PoleSegCustomInfoBean.class);
			if (poleSeg != null) {
				poleSeg.setModifier(realName);
				this.pdaPoleSegCustomService.deletePoleSegCustom(poleSeg);
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


	public PDAPoleSegCustomService getPdaPoleSegCustomService() {
		return pdaPoleSegCustomService;
	}


	public void setPdaPoleSegCustomService(PDAPoleSegCustomService pdaPoleSegCustomService) {
		this.pdaPoleSegCustomService = pdaPoleSegCustomService;
	}


	
}