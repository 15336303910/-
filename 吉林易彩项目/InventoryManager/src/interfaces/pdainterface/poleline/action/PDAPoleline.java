package interfaces.pdainterface.poleline.action;

import base.util.MapUtil;
import base.util.TextUtil;
import base.util.functions;
import base.util.pojo.Point;
import base.web.InterfaceAction;
import interfaces.irmsInterface.interfaces.outLine.service.impl.IirmsOutLineService;
import interfaces.pdainterface.poleline.service.PDAPolelineService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import manage.poleline.pojo.PoleInfoBean;
import manage.poleline.pojo.PolelineInfoBean;
import manage.poleline.pojo.PolelineSegmentInfoBean;
import manage.poleline.pojo.SupportInfoBean;
import manage.poleline.pojo.SusLocationBean;
import manage.poleline.pojo.SuspensionWireInfoBean;
import manage.poleline.pojo.SuspensionWireSegInfoBean;

import org.apache.log4j.Logger;

public class PDAPoleline extends InterfaceAction {
	private static final long serialVersionUID = -3486300674796123267L;
	private static final Logger log = Logger.getLogger(PDAPoleline.class);
	private PDAPolelineService pdaPolelineService;
	private IirmsOutLineService irmsOutLineService;
	

	/**
	 * 得到所有的电杆
	 * @return
	 */
	public String getPole() {
		try {
			PoleInfoBean pole = (PoleInfoBean) getRequestObject(PoleInfoBean.class);
			if (pole != null) {
				if(TextUtil.isNull(pole.getPoleLineId()) && TextUtil.isNotNull(pole.getPoleLatitude()) && TextUtil.isNotNull(pole.getPoleLongitude())){
					if(isWGS){
						Point point = MapUtil.phone_db_encrypt(Double.parseDouble(pole.getPoleLatitude()),Double.parseDouble(pole.getPoleLongitude()));
						pole.setPoleLatitude(point.getLat()+"");
						pole.setPoleLongitude(point.getLng()+"");
					}
					double[] arr = functions.getAround(Double.parseDouble(pole.getPoleLatitude()),Double.parseDouble(pole.getPoleLongitude()), ((this.start/this.limit)+1)*Integer.parseInt(properties.getValueByKey("gisLength")));
					pole.setLats(String.valueOf(arr[0]));
					pole.setLons(String.valueOf(arr[1]));
					pole.setLate(String.valueOf(arr[2]));
					pole.setLone(String.valueOf(arr[3]));
				}else{
					pole.setStart(this.start);
					pole.setLimit(this.limit);
				}
				if(TextUtil.isNull(pole.getPoleLineId()) && TextUtil.isNull(pole.getPoleId()) && TextUtil.isNotNull(super.areaName)){
					pole.setRegion(super.areaName);
				}
				List<PoleInfoBean> list = this.pdaPolelineService.getPole(pole);
				for (PoleInfoBean obj : list) {
					if(isWGS){
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(obj.getPoleLatitude()),Double.parseDouble(obj.getPoleLongitude()));
						obj.setPoleLatitude(point.getLat() + "");
						obj.setPoleLongitude(point.getLng() + "");
					}
				}
				sendResponse(Integer.valueOf(0), list);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPoleline.getPole ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	
	/**
	 * 增加电杆
	 * @return
	 */
	public String insertPole() {
		try {
			PoleInfoBean pole = (PoleInfoBean) getRequestObject(PoleInfoBean.class);
			if(isWGS){
				Point point = MapUtil.phone_db_encrypt(Double.parseDouble(pole.getPoleLatitude()),Double.parseDouble(pole.getPoleLongitude()));
				pole.setPoleLatitude(point.getLat() + "");
				pole.setPoleLongitude(point.getLng() + "");
			}
			if(TextUtil.isNotNull(super.realName)){
				pole.setParties(super.realName);
			}
			if(pole.getPoleName().contains("#")) {
				String poleName = pole.getPoleName().replaceAll("#", "号");
				pole.setPoleName(poleName);
			}
			int i = this.pdaPolelineService.insertPole(pole).intValue();
			pole.setPoleId(Integer.valueOf(i));
			if (i > 0) {
				if(isWGS){
					Point point = MapUtil.db_phone_encrypt(Double.parseDouble(pole.getPoleLatitude()),Double.parseDouble(pole.getPoleLongitude()));
					pole.setPoleLatitude(point.getLat() + "");
					pole.setPoleLongitude(point.getLng() + "");
				}
				sendResponse(Integer.valueOf(0), pole);
				//录入综资数据
				if(toIrms){
					new addPoleRun(pole.getPoleId()).start();
				}
			} else {
				sendResponse(Integer.valueOf(4), "名称重复");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPoleline.insertPole ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 增加电杆
	 * @author chenqp
	 *
	 */
	class addPoleRun extends Thread{
		private Integer poleId;
		public addPoleRun(Integer poleId){
			this.poleId = poleId;
		}
		@Override
		public void run() {
			PoleInfoBean pole = pdaPolelineService.getPoleObj(poleId);
			irmsOutLineService.addPole(pole);
			super.run();
		}
	}

	
	/**
	 * 修改电杆
	 * @return
	 */
	public String updatePole() {
		try {
			PoleInfoBean pole = (PoleInfoBean) getRequestObject(PoleInfoBean.class);
			if (pole != null) {
				if(isWGS){
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(pole.getPoleLatitude()),Double.parseDouble(pole.getPoleLongitude()));
					pole.setPoleLatitude(point.getLat() + "");
					pole.setPoleLongitude(point.getLng() + "");
				}
				if(TextUtil.isNotNull(super.realName)){
					pole.setParties(super.realName);
				}
				if(pole.getPoleName().contains("#")) {
					String poleName = pole.getPoleName().replaceAll("#", "号");
					pole.setPoleName(poleName);
				}
				int i = this.pdaPolelineService.updatePole(pole).intValue();
				if (i > 0) {
					if(isWGS){
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(pole.getPoleLatitude()),Double.parseDouble(pole.getPoleLongitude()));
						pole.setPoleLatitude(point.getLat() + "");
						pole.setPoleLongitude(point.getLng() + "");
					}
					sendResponse(Integer.valueOf(0), pole);
					new upPoleLineThread(pole.getPoleId(), pole.getPoleName()).start();
					//综资移动位置
					if(toIrms){
						new movePole(pole).start();
					}
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
	 * 修改段的信息
	 * @author chenqp
	 *
	 */
	class upPoleLineThread extends Thread{
		private Integer poleId;
		private String poleName;
		public upPoleLineThread(Integer poleId,String poleName){
			this.poleId = poleId;
			this.poleName = poleName;
		}
		@Override
		public void run() {
			pdaPolelineService.upPoleSeg(poleId, poleName);
			super.run();
		}
	}
	
	/**
	 *移动电杆 
	 * @author chenqp
	 *
	 */
	class movePole extends Thread{
		private PoleInfoBean pole;
		public movePole(PoleInfoBean pole){
			this.pole= pole;
		}
		@Override
		public void run() {
			PoleInfoBean resPole = pdaPolelineService.getPoleObj(pole.getPoleId());
			irmsOutLineService.movePole(resPole);
			super.run();
		}
	}

	
	/**
	 * 删除电杆
	 * @return
	 */
	public String deletePole() {
		try {
			PoleInfoBean pole = (PoleInfoBean) getRequestObject(PoleInfoBean.class);
			if (pole != null) {
				PoleInfoBean obj = this.pdaPolelineService.getPoleObj(pole.getPoleId());
				if(this.pdaPolelineService.getPoleLay(obj)  && !super.forceDel){
					//存在敷设
					sendResponse(Integer.valueOf(1), "存在敷设关系禁止删除!");
				}else{
					if(TextUtil.isNotNull(super.realName)){
						pole.setParties(super.realName);
					}
					this.pdaPolelineService.deletePole(pole);
					sendResponse(Integer.valueOf(0), "删除成功");
					this.pdaPolelineService.upPloeLineSeg(pole.getPoleId()+"");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPoleline.deletePole ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	
	/**
	 * 得到杆路
	 * @return
	 */
	public String getPoleline() {
		try {
			PolelineInfoBean poleline = (PolelineInfoBean) getRequestObject(PolelineInfoBean.class);
			if (poleline != null) {
				poleline.setStart(this.start);
				poleline.setLimit(this.limit);
				List list = this.pdaPolelineService.getPoleline(poleline);
				sendResponse(Integer.valueOf(0), list);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPoleline.getPoleline ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 增加杆路
	 * 
	 * @return
	 */
	public String insertPoleline() {
		try {
			PolelineInfoBean poleline = (PolelineInfoBean) getRequestObject(PolelineInfoBean.class);
			List poleLineList = pdaPolelineService.getPoleline(poleline);
			if (TextUtil.isNotNull(poleLineList)) {
				sendResponse(Integer.valueOf(2), "存在相同杆路");
			} else {
				if(TextUtil.isNotNull(super.realName)){
					poleline.setParties(super.realName);
				}
				int i = this.pdaPolelineService.insertPoleline(poleline).intValue();
				poleline.setPoleLineId(Integer.valueOf(i));
				sendResponse(Integer.valueOf(0), poleline);
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPoleline.insertPoleline ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 修改杆路
	 * @return
	 */
	public String updatePoleline() {
		try {
			PolelineInfoBean poleline = (PolelineInfoBean) getRequestObject(PolelineInfoBean.class);
			if (poleline != null) {
				poleline.setDataQualityPrincipal(this.longiner);
				this.pdaPolelineService.updatePoleline(poleline);
				sendResponse(Integer.valueOf(0), poleline);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPoleline.updatePoleline ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 删除杆路
	 * @return
	 */
	public String deletePoleline() {
		try {
			PolelineInfoBean poleline = (PolelineInfoBean) getRequestObject(PolelineInfoBean.class);
			if (poleline != null) {
				this.pdaPolelineService.deletePoleline(poleline);
				sendResponse(Integer.valueOf(0), "删除成功");
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPoleline.deletePoleline ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	
	/**
	 * 得到杆路段
	 * @return
	 */
	public String getPolelineSeg() {
		try {
			PolelineSegmentInfoBean polelineSeg = (PolelineSegmentInfoBean) getRequestObject(PolelineSegmentInfoBean.class);
			if (polelineSeg != null) {
				/*polelineSeg.setStart(this.start);
				polelineSeg.setLimit(this.limit);*/
				if(TextUtil.isNull(polelineSeg.getPoleLineId()) && TextUtil.isNull(polelineSeg.getPoleLineSegmentId()) && TextUtil.isNotNull(super.areaName)){
					polelineSeg.setMaintenanceAreaName(super.areaName);
				}
				List<PolelineSegmentInfoBean> list = this.pdaPolelineService.getPolelineSeg(polelineSeg);
				sendResponse(Integer.valueOf(0), list);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}

		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPoleline.getPolelineSeg ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 得到杆路段信息
	 * @return
	 */
	public String getPolelineSegObj(){
		try{
			PolelineSegmentInfoBean polelineSeg = (PolelineSegmentInfoBean) getRequestObject(PolelineSegmentInfoBean.class);
			if(polelineSeg != null){
				polelineSeg = this.pdaPolelineService.getPolelineSegObj(polelineSeg);
				sendResponse(Integer.valueOf(0), polelineSeg);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPoleline.getPolelineSegObj ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	

	/**
	 * 修改杆路段
	 * @return
	 */
	public String updatePolelineSeg() {
		try {
			PolelineSegmentInfoBean polelineSeg = (PolelineSegmentInfoBean) getRequestObject(PolelineSegmentInfoBean.class);
			polelineSeg = this.pdaPolelineService.setPoleSegDistince(polelineSeg);
			if (polelineSeg != null) {
				if(TextUtil.isNotNull(super.realName)){
					polelineSeg.setParties(super.realName);
				}
				this.pdaPolelineService.updatePolelineSeg(polelineSeg);
				//直接同步到综资数据
				if(toIrms){
					new upPoleSeg(polelineSeg.getPoleLineSegmentId()).start();
				}
				sendResponse(Integer.valueOf(0), "修改成功");
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPoleline.updatePolelineSeg ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 修改杆路段
	 * @author chenqp
	 *
	 */
	class upPoleSeg extends Thread{
		private Integer poleLineSegId;
		public upPoleSeg(Integer poleLineSegId){
			this.poleLineSegId = poleLineSegId;
		}
		@Override
		public void run() {
			try{
				PolelineSegmentInfoBean poleLine =new PolelineSegmentInfoBean();
			    poleLine.setPoleLineSegmentId(poleLineSegId);
			    poleLine=pdaPolelineService.getPolelineSegObj(poleLine);
			    irmsOutLineService.upPoleLine(poleLine);
			}catch(Exception e){
				e.printStackTrace();
			}
			super.run();
		}
	}
	
	
	/**
	 * 删除杆路段
	 * @return
	 */
	public String deletePolelineSeg() {
		try {
			PolelineSegmentInfoBean polelineSeg = (PolelineSegmentInfoBean) getRequestObject(PolelineSegmentInfoBean.class);
			if (polelineSeg != null) {
				polelineSeg = this.pdaPolelineService.getPolelineSegObj(polelineSeg);
				if(TextUtil.isNotNull(polelineSeg.getBearCableSegmentId()) && !super.forceDel){
					sendResponse(Integer.valueOf(1), "存在敷设请勿删除!");
				}else{
					if(TextUtil.isNotNull(super.realName)){
						polelineSeg.setParties(super.realName);
					}
					this.pdaPolelineService.deletePolelineSeg(polelineSeg);
					sendResponse(Integer.valueOf(0), "删除成功");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}

		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAResourceCheck.updateResourceNode ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 添加杆路段
	 * 
	 * @return
	 */
	public String insertPolelineSeg() {
		try {
			PolelineSegmentInfoBean polelineSeg = (PolelineSegmentInfoBean) getRequestObject(PolelineSegmentInfoBean.class);
			String poleSegName = polelineSeg.getPoleLineSegmentName();
			List segList = this.pdaPolelineService.getPolelineSeg(polelineSeg);
			if (TextUtil.isNotNull(segList)) {
				sendResponse(Integer.valueOf(2), "杆路段重复。");
			} else {
				if(TextUtil.isNull(polelineSeg.getPoleLineSegmentName()) || TextUtil.isNull(polelineSeg.getStartDeviceId()) || TextUtil.isNull(polelineSeg.getEndDeviceId())){
					sendResponse(Integer.valueOf(1), "请填写必填项。");
				}else{
					polelineSeg = this.pdaPolelineService.setPoleSegDistince(polelineSeg);
					if(TextUtil.isNotNull(super.realName)){
						polelineSeg.setParties(super.realName);
					}
					if(TextUtil.isNull(polelineSeg.getMaintenanceAreaName())){
						PoleInfoBean start = this.pdaPolelineService.getPoleObj(polelineSeg.getStartDeviceId());
						polelineSeg.setMaintenanceAreaName(start.getRegion());
					}
					if(polelineSeg.getPoleLineSegmentName().startsWith("%") || polelineSeg.getPoleLineSegmentName().endsWith("%")){
						polelineSeg.setPoleLineSegmentName(poleSegName);
					}
					if(Double.parseDouble(polelineSeg.getPoleLineSegmentLength()) > 5000.0) {
						sendResponse(Integer.valueOf(1), "杆路限制500米,已超限!");
					}else {
						int id = this.pdaPolelineService.insertPolelineSeg(polelineSeg).intValue();
						polelineSeg.setPoleLineSegmentId(Integer.valueOf(id));
						sendResponse(Integer.valueOf(0), polelineSeg);
						//往综资推送数据
						if(toIrms){
							new addPoleSeg(id).start();
						}
					}
					
				}
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAResourceCheck.updateResourceNode ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 
	 * @author chenqp
	 *
	 */
	class addPoleSeg extends Thread{
		private Integer poleLineId;
		public addPoleSeg(Integer poleLineId){
			this.poleLineId = poleLineId;
		}
		@Override
		public void run() {
			try{
				PolelineSegmentInfoBean poleLine =new PolelineSegmentInfoBean();
				poleLine.setPoleLineSegmentId(poleLineId);
				poleLine=pdaPolelineService.getPolelineSegObj(poleLine);
				irmsOutLineService.addPoleLine(poleLine);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			super.run();
		}
	}

	
	/**
	 * 获取电杆支撑杆
	 * @return
	 */
	public String getSupport() {
		try {
			SupportInfoBean support = (SupportInfoBean) getRequestObject(SupportInfoBean.class);
			if (support != null) {
				List list = this.pdaPolelineService.getSupport(support);
				sendResponse(Integer.valueOf(0), list);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAResourceCheck.getSupport ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 增加电杆支撑杆
	 * @return
	 */
	public String insertSupport() {
		try {
			SupportInfoBean support = (SupportInfoBean) getRequestObject(SupportInfoBean.class);
			if (support != null) {
				int i = this.pdaPolelineService.insertSupport(support)
						.intValue();
				support.setSupportId(Integer.valueOf(i));
				sendResponse(Integer.valueOf(0), support);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAResourceCheck.insertSupport ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 修改电杆支撑
	 * @return
	 */
	public String updateSupport() {
		try {
			SupportInfoBean support = (SupportInfoBean) getRequestObject(SupportInfoBean.class);
			if (support != null) {
				this.pdaPolelineService.updateSupport(support);
				sendResponse(Integer.valueOf(0), support);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAResourceCheck.updateSupport ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	public String deleteSupport() {
		try {
			SupportInfoBean support = (SupportInfoBean) getRequestObject(SupportInfoBean.class);
			if (support != null) {
				this.pdaPolelineService.deleteSupport(support);
				sendResponse(Integer.valueOf(0), "删除成功。");
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}

		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAResourceCheck.deleteSupport ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 得到所有的电杆吊线
	 * @return
	 */
	public String getSuspension() {
		try {
			SuspensionWireInfoBean suspension = (SuspensionWireInfoBean) getRequestObject(SuspensionWireInfoBean.class);
			if (suspension != null) {
				List list = this.pdaPolelineService
						.getSuspensionWire(suspension);
				sendResponse(Integer.valueOf(0), list);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAResourceCheck.getSuspension ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 本地吊线
	 * @return
	 */
	public String getSuspensionLocation() {
		try {
			SusLocationBean suspension = (SusLocationBean) getRequestObject(SusLocationBean.class);
			if (suspension != null) {
				SuspensionWireInfoBean sus = new SuspensionWireInfoBean();
				sus.setSuspensionWireId(suspension.getSusId());
				/*SusLocationBean location = this.pdaPolelineService
						.getSuspensionWireWithSeg(sus);*/
				//sendResponse(Integer.valueOf(0), location);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}

		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAResourceCheck.getSuspension ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 增加吊线
	 * @return
	 */
	public String insertSuspension() {
		try {
			SuspensionWireInfoBean suspension = (SuspensionWireInfoBean) getRequestObject(SuspensionWireInfoBean.class);
			if (suspension != null) {
				int i = this.pdaPolelineService
						.insertSuspensionWire(suspension).intValue();
				suspension.setSuspensionWireId(Integer.valueOf(i));
				sendResponse(Integer.valueOf(0), suspension);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAResourceCheck.insertSuspension ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	public String updateSuspension() {
		try {
			SuspensionWireInfoBean suspension = (SuspensionWireInfoBean) getRequestObject(SuspensionWireInfoBean.class);
			if (suspension != null) {
				this.pdaPolelineService.updateSuspensionWire(suspension);
				sendResponse(Integer.valueOf(0), suspension);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}

		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAResourceCheck.updateSuspension ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	public String deleteSuspension() {
		try {
			SuspensionWireInfoBean suspension = (SuspensionWireInfoBean) getRequestObject(SuspensionWireInfoBean.class);
			if (suspension != null) {
				this.pdaPolelineService.deleteSuspensionWire(suspension);
				sendResponse(Integer.valueOf(0), "删除成功。");
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAResourceCheck.delectSuspension ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	public String getSuspensionseg() {
		try {
			SuspensionWireSegInfoBean suspensionseg = (SuspensionWireSegInfoBean) getRequestObject(SuspensionWireSegInfoBean.class);
			if (suspensionseg != null) {
				List list = this.pdaPolelineService
						.getSuspensionseg(suspensionseg);
				sendResponse(Integer.valueOf(0), list);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}

		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAResourceCheck.getSuspensionseg ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}


	public String insertSuspensionseg() {
		try {
			SuspensionWireSegInfoBean suspensionseg = (SuspensionWireSegInfoBean) getRequestObject(SuspensionWireSegInfoBean.class);
			if (suspensionseg != null) {
				int i = this.pdaPolelineService.insertSuspensionseg(
						suspensionseg).intValue();
				suspensionseg.setSuspensionWireSegId(Integer.valueOf(i));
				sendResponse(Integer.valueOf(0), suspensionseg);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAResourceCheck.insertSuspension ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	public String updateSuspensionseg() {
		try {
			SuspensionWireSegInfoBean suspensionseg = (SuspensionWireSegInfoBean) getRequestObject(SuspensionWireSegInfoBean.class);
			if (suspensionseg != null) {
				this.pdaPolelineService.updateSuspensionseg(suspensionseg);
				sendResponse(Integer.valueOf(0), suspensionseg);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAResourceCheck.updateSuspension ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	public String deleteSuspensionseg() {
		try {
			SuspensionWireSegInfoBean suspensionseg = (SuspensionWireSegInfoBean) getRequestObject(SuspensionWireSegInfoBean.class);
			if (suspensionseg != null) {
				this.pdaPolelineService.deleteSuspensionseg(suspensionseg);
				sendResponse(Integer.valueOf(0), "删除成功。");
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAResourceCheck.delectSuspension ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	public PDAPolelineService getPdaPolelineService() {
		return this.pdaPolelineService;
	}

	public IirmsOutLineService getIrmsOutLineService() {
		return irmsOutLineService;
	}


	public void setIrmsOutLineService(IirmsOutLineService irmsOutLineService) {
		this.irmsOutLineService = irmsOutLineService;
	}


	public void setPdaPolelineService(PDAPolelineService pdaPolelineService) {
		this.pdaPolelineService = pdaPolelineService;
	}
}