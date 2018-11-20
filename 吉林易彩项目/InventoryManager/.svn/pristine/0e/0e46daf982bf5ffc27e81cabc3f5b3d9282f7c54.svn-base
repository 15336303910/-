package interfaces.pdainterface.pipe.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import base.util.MapUtil;
import base.util.TextUtil;
import base.util.functions;
import base.util.pojo.Point;
import base.web.InterfaceAction;
import interfaces.irmsInterface.interfaces.outLine.service.impl.IirmsOutLineService;
import interfaces.pdainterface.pipe.service.PDAPipeService;
import manage.pipe.pojo.FaceInfoBean;
import manage.pipe.pojo.LedupInfoBean;
import manage.pipe.pojo.PipeInfoBean;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.pipe.pojo.TubeInfoBean;
import manage.pipe.pojo.WellInfoBean;
import manage.user.pojo.UserInfoBean;

public class PDAPipe extends InterfaceAction {
	private static final long serialVersionUID = -3486300674796123267L;
	private static final Logger log = Logger.getLogger(PDAPipe.class);
	private PDAPipeService pdaPipeService;	
	private IirmsOutLineService irmsOutLineService;
	/**
	 * 查询井信息
	 * 
	 * @return
	 */
	public String getWell() {
		try {
			WellInfoBean well = (WellInfoBean) getRequestObject(WellInfoBean.class);
			if (well != null) {
				if (TextUtil.isNull(well.getPipeId()) && (well.getLatitude() != null)&& (!(well.getLatitude().equals("")))&& (well.getLongitude() != null)&& (!(well.getLongitude().equals("")))) {
					if(isWGS){
						Point point = MapUtil.phone_db_encrypt(Double.parseDouble(well.getLatitude()),Double.parseDouble(well.getLongitude()));
						well.setLatitude(point.getLat() + "");
						well.setLongitude(point.getLng() + "");
					}
					double[] arr = functions.getAround(Double.parseDouble(well.getLatitude()),Double.parseDouble(well.getLongitude()), ((this.start/this.limit)+1)*Integer.parseInt(properties.getValueByKey("gisLength")));
					well.setLats(String.valueOf(arr[0]));
					well.setLons(String.valueOf(arr[1]));
					well.setLate(String.valueOf(arr[2]));
					well.setLone(String.valueOf(arr[3]));
				}else{
					well.setStart(this.start);
					well.setLimit(this.limit);
				}
				if(TextUtil.isNull(well.getPipeId()) && TextUtil.isNull(well.getWellId()) && TextUtil.isNotNull(super.areaName)){
					well.setRegion(super.areaName);
				}
				List<WellInfoBean> list = this.pdaPipeService.getWell(well);
				for (WellInfoBean obj : list) {
					if(isWGS){
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()));
						obj.setLatitude(point.getLat() + "");
						obj.setLongitude(point.getLng() + "");
					}
				}
				sendResponse(Integer.valueOf(0), list);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}

		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.getWell ERROR\nJsonRequest:" + getJsonRequest()
					+ "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 增加井
	 * 
	 * @return
	 */
	public String insertWell() {
		try {
			WellInfoBean well = (WellInfoBean) getRequestObject(WellInfoBean.class);
			if(isWGS){
				Point point = MapUtil.phone_db_encrypt(Double.parseDouble(well.getLatitude()),Double.parseDouble(well.getLongitude()));
				well.setLatitude(point.getLat() + "");
				well.setLongitude(point.getLng() + "");
			}
			if(TextUtil.isNotNull(super.realName)){
				well.setParties(super.realName);
			}
			int i = this.pdaPipeService.insertWell(well).intValue();
			if (i > 0) {
				well.setWellId(Integer.valueOf(i));
				if(isWGS){
					Point point = MapUtil.db_phone_encrypt(Double.parseDouble(well.getLatitude()), Double.parseDouble(well.getLongitude()));
					well.setLatitude(point.getLat()+"");
					well.setLongitude(point.getLng()+"");
				}
				sendResponse(Integer.valueOf(0), well);
				//同步到综资系统
				if(toIrms){
					new addWellThread(well.getWellId()).start();
				}
			} else {
				sendResponse(Integer.valueOf(4), "名称重复");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.insertWell ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 增加井
	 * @author chenqp
	 *
	 */
	class addWellThread extends Thread{
		private Integer wellId;
		public addWellThread(Integer wellId){
			this.wellId = wellId;
		}
		@Override
		public void run() {
			WellInfoBean well= pdaPipeService.getWellByid(wellId);
			irmsOutLineService.addWell(well);
			super.run();
		}
	}

	/**
	 * 修改井信息
	 * 
	 * @return
	 */
	public String updateWell() {
		try {
			WellInfoBean well = (WellInfoBean) getRequestObject(WellInfoBean.class);
			UserInfoBean user = (UserInfoBean) getInterfaceSession().getAttribute("userBean");
			if (well != null) {
				if(isWGS){
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(well.getLatitude()),Double.parseDouble(well.getLongitude()));
					well.setLatitude(point.getLat() + "");
					well.setLongitude(point.getLng() + "");
				}
				int i = this.pdaPipeService.updateWell(well).intValue();
				if (i > 0) {
					if(isWGS){
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(well.getLatitude()),Double.parseDouble(well.getLongitude()));
						well.setLatitude(point.getLat() + "");
						well.setLongitude(point.getLng() + "");
					}
					sendResponse(Integer.valueOf(0), well);
					//更新段信息
					new upSegThread(well.getWellId(),well.getWellName()).start();
					//同步综资
					if(super.toIrms){
						new moveWell(well.getWellId()).start();
					}
				} else {
					sendResponse(Integer.valueOf(1), "修改失败");
				}
				/*if(well.getParties().equals(super.realName)) {
					
				}else {
					sendResponse(Integer.valueOf(1), "您无权进行修改!");
				}*/
				
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.updateWell ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 更新相应的段信息
	 * @author chenqp
	 *
	 */
	class upSegThread extends Thread{
		private Integer wellId;
		private String wellName;
		public upSegThread(Integer wellId,String wellName){
			this.wellId = wellId;
			this.wellName = wellName;
		}
		@Override
		public void run() {
			pdaPipeService.upPipeSeg(wellId, wellName);
			super.run();
		}
	}
	
	/**
	 * 
	 * @author chenqp
	 *
	 */
	class moveWell extends Thread{
		private Integer wellId;
		public moveWell(Integer wellId){
			this.wellId = wellId;
		}
		@Override
		public void run() {
			WellInfoBean  well = pdaPipeService.getWellByid(wellId);
			if(TextUtil.isNotNull(well.getResNum())){
				irmsOutLineService.moveWell(well);
			}
			super.run();
		}
	}

	/**
	 * 删除井
	 * 
	 * @return
	 */
	public String deleteWell() {
		try {
			WellInfoBean well = (WellInfoBean) getRequestObject(WellInfoBean.class);
			if (well != null) {
				WellInfoBean obj = this.pdaPipeService.getWellByid(well.getWellId());
				if(TextUtil.isNotNull(obj.getParties()) && !(obj.getParties().equals(super.realName))){
					sendResponse(Integer.valueOf(1), "无权删除该资源点!");
				}else{
					if(TextUtil.isNotNull(super.realName)){
						well.setParties(super.realName);
					}
					this.pdaPipeService.deleteWell(well);
					sendResponse(Integer.valueOf(0), "删除成功");
					//删除相应管道数据
					this.pdaPipeService.delPipeSeg(well.getWellId()+"");
					//删除相应引上数据
					this.pdaPipeService.delLeadupStage(well.getWellId()+"");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.deleteWell ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 得到管道
	 * 
	 * @return
	 */
	public String getPipe() {
		try {
			PipeInfoBean pipe = (PipeInfoBean) getRequestObject(PipeInfoBean.class);
			if (pipe != null) {
				pipe.setStart(this.start);
				pipe.setLimit(this.limit);
				List list = this.pdaPipeService.getPipe(pipe);
				sendResponse(Integer.valueOf(0), list);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.getPipe ERROR\nJsonRequest:" + getJsonRequest()
					+ "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 增加管道
	 * 
	 * @return
	 */
	public String insertPipe() {
		try {
			PipeInfoBean pipe = (PipeInfoBean) getRequestObject(PipeInfoBean.class);
			pipe.setParties(this.longiner);
			int i = this.pdaPipeService.insertPipe(pipe).intValue();
			pipe.setPipeId(Integer.valueOf(i));
			if (i > 0) {
				sendResponse(Integer.valueOf(0), pipe);
			} else {
				sendResponse(Integer.valueOf(4), "名称重复");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.insertPipe ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 修改管道
	 * 
	 * @return
	 */
	public String updatePipe() {
		try {
			PipeInfoBean pipe = (PipeInfoBean) getRequestObject(PipeInfoBean.class);
			if (pipe != null) {
				pipe.setParties(this.longiner);
				int i = this.pdaPipeService.updatePipe(pipe).intValue();
				if (i > 0) {
					sendResponse(Integer.valueOf(0), pipe);
				} else {
					sendResponse(Integer.valueOf(4), "名称重复");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.updatePipe ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	
	/**
	 * 删除管道
	 * @return
	 */
	public String deletePipe() {
		try {
			PipeInfoBean pipe = (PipeInfoBean) getRequestObject(PipeInfoBean.class);
			if (pipe != null) {
				pipe.setParties(this.longiner);
				this.pdaPipeService.deletePipe(pipe);
				sendResponse(Integer.valueOf(0), "删除成功");
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.deletePipe ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 得到管道段
	 * 
	 * @return
	 */
	public String getPipeseg() {
		try {
			PipeSegmentInfoBean pipeseg = (PipeSegmentInfoBean) getRequestObject(PipeSegmentInfoBean.class);
			if (pipeseg != null) {
				pipeseg.setStart(start);
				pipeseg.setLimit(this.limit);
				if(TextUtil.isNull(pipeseg.getPipeId()) && TextUtil.isNull(pipeseg.getPipeSegmentId()) && TextUtil.isNotNull(super.areaName)) {
					pipeseg.setMaintenanceAreaName(super.areaName);
				}
				List list = this.pdaPipeService.getPipeseg(pipeseg);
				sendResponse(Integer.valueOf(0), list);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.getPipeseg ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 得到管道段
	 * 的基本信息
	 * @return
	 */
	public String getPipeSegObj(){
		try{
			PipeSegmentInfoBean pipeseg = (PipeSegmentInfoBean) getRequestObject(PipeSegmentInfoBean.class);
			if(pipeseg != null){
				pipeseg = this.pdaPipeService.getPipeSegObj(pipeseg);
				if(TextUtil.isNull(pipeseg.getConstructionSharingOrg())){
					pipeseg.setConstructionSharingOrg("0");
				}
				sendResponse(Integer.valueOf(0), pipeseg);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		}catch(Exception e){
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.getPipeSegObj ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 增加管道段
	 * 
	 * @return
	 */
	public String insertPipeseg() {
		try {
			PipeSegmentInfoBean pipeseg = (PipeSegmentInfoBean) getRequestObject(PipeSegmentInfoBean.class);
			if (pipeseg != null) {
				if(TextUtil.isNull(pipeseg.getPipeSegmentName()) || TextUtil.isNull(pipeseg.getStartDeviceId()) || TextUtil.isNull(pipeseg.getEndDeviceId())){
					sendResponse(Integer.valueOf(1), "请填写必填字段!");
				}else{
					if(TextUtil.isNotNull(super.realName)){
						pipeseg.setParties(super.realName);
					}
					pipeseg = this.pdaPipeService.setPipeSegLength(pipeseg);
					if(TextUtil.isNull(pipeseg.getMaintenanceAreaName())){
						WellInfoBean well =  new WellInfoBean();
						well = this.pdaPipeService.getWellByid(pipeseg.getStartDeviceId());
						pipeseg.setMaintenanceAreaName(well.getRegion());
					}
					if(TextUtil.isNull(pipeseg.getPipeId())) {
						WellInfoBean start = this.pdaPipeService.getWellByid(pipeseg.getStartDeviceId());
						WellInfoBean end = this.pdaPipeService.getWellByid(pipeseg.getEndDeviceId());
						pipeseg.setStartDeviceName(start.getWellName());
						pipeseg.setEndDeviceName(end.getWellName());
						pipeseg.setConstructionSharingOrg(end.getConstructionSharingOrg());
						pipeseg.setDataQualityPrincipal(end.getDataQualityPrincipal());
						pipeseg.setPipeId(Integer.parseInt(end.getPipeId()));
					}
					if(Double.parseDouble(pipeseg.getPipeSegmentLength()) >1000.0) {
						sendResponse(Integer.valueOf(1), "管线长度限制为1000米,已超限");
					}else {
						int i = this.pdaPipeService.insertPipeseg(pipeseg).intValue();
						pipeseg.setPipeSegmentId(Integer.valueOf(i));
						sendResponse(Integer.valueOf(0), pipeseg);
						//判断是否送到综资
						if(toIrms){
							new addPipeSegThread(i).start();
						}
					}
				}
				
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.insertPipeseg ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 发送到综资
	 * @author chenqp
	 *
	 */
	class addPipeSegThread extends Thread{
		private Integer id;
		public addPipeSegThread(Integer id){
			this.id = id;
		}
		@Override
		public void run() {
			try{
				PipeSegmentInfoBean  pipeSeg = new PipeSegmentInfoBean();
				pipeSeg.setPipeSegmentId(id);
				pipeSeg = pdaPipeService.getPipeSegObj(pipeSeg);
				irmsOutLineService.addPipeSeg(pipeSeg);
			}catch(Exception e){
				e.printStackTrace();
			}
			super.run();
		}
	}

	/**
	 * 修改管道段
	 * 
	 * @return
	 */
	public String updatePipeseg() {
		try {
			PipeSegmentInfoBean pipeseg = (PipeSegmentInfoBean) getRequestObject(PipeSegmentInfoBean.class);
			if(TextUtil.isNotNull(super.realName)){
				pipeseg.setParties(super.realName);
			}
			if (pipeseg != null) {
				pipeseg = this.pdaPipeService.setPipeSegLength(pipeseg);
				int i = this.pdaPipeService.updatePipeseg(pipeseg).intValue();
				if (i > 0) {
					sendResponse(Integer.valueOf(0), pipeseg);
					//同步综资数据
					if(toIrms){
						new upPipeSegThread(pipeseg.getPipeSegmentId()).start();;
					}
				} else {
					sendResponse(Integer.valueOf(4), "名称重复");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.updatePipeseg ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 修改管道段
	 * @author chenqp
	 *
	 */
	class upPipeSegThread extends Thread{
		private Integer id;
		public upPipeSegThread(Integer id){
			this.id = id;
		}
		@Override
		public void run() {
			try{
				PipeSegmentInfoBean  pipeSeg = new PipeSegmentInfoBean();
				pipeSeg.setPipeSegmentId(id);
				pipeSeg = pdaPipeService.getPipeSegObj(pipeSeg);
				irmsOutLineService.upPipeSeg(pipeSeg);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			super.run();
		}
	}

	/**
	 * 删除管道段
	 * 
	 * @return
	 */
	public String deletePipeseg() {
		try {
			PipeSegmentInfoBean pipeseg = (PipeSegmentInfoBean) getRequestObject(PipeSegmentInfoBean.class);
			if (pipeseg != null) {
				PipeSegmentInfoBean obj = new PipeSegmentInfoBean();
				obj.setPipeSegmentId(pipeseg.getPipeSegmentId());
				obj = this.pdaPipeService.getPipeSegObj(obj);
				if(TextUtil.isNotNull(obj.getPipeSegOpticalId()) && !super.forceDel){
					sendResponse(Integer.valueOf(1), "存在光缆敷设请勿删除!");
				}else{
					if(TextUtil.isNotNull(super.realName)){
						pipeseg.setParties(super.realName);
					}
					this.pdaPipeService.deletePipeseg(pipeseg);
					sendResponse(Integer.valueOf(0), "删除成功");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.deletePipeseg ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 井面的信息
	 * 
	 * @return
	 */
	public String getFace() {
		try {
			FaceInfoBean face = (FaceInfoBean) getRequestObject(FaceInfoBean.class);
			if (face != null) {
				List list = this.pdaPipeService.getFace(face);
				sendResponse(Integer.valueOf(0), list);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.getFace ERROR\nJsonRequest:" + getJsonRequest()
					+ "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 增加面信息
	 * 
	 * @return
	 */
	public String insertFace() {
		try {
			FaceInfoBean face = (FaceInfoBean) getRequestObject(FaceInfoBean.class);
			if (face != null) {
				int i = this.pdaPipeService.insertFace(face).intValue();
				face.setFaceId(Integer.valueOf(i));
				sendResponse(Integer.valueOf(0), face);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.insertFace ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 修改面的信息
	 * 
	 * @return
	 */
	public String updateFace() {
		try {
			FaceInfoBean face = (FaceInfoBean) getRequestObject(FaceInfoBean.class);
			if (face != null) {
				int i = this.pdaPipeService.updateFace(face).intValue();
				if (i == -1) {
					sendResponse(Integer.valueOf(0), "该面已经关联管道段，请勿取消显示。");
				} else {
					sendResponse(Integer.valueOf(0), face);
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.updateFace ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	
	/**
	 * 删除面的信息
	 * @return
	 */
	public String deleteFace() {
		try {
			FaceInfoBean face = (FaceInfoBean) getRequestObject(FaceInfoBean.class);
			if (face != null) {
				this.pdaPipeService.deleteFace(face);
				sendResponse(Integer.valueOf(0), "删除成功");
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.deleteFace ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 得到tubeinfo
	 * @return
	 */
	public String getTube() {
		try {
			TubeInfoBean tube = (TubeInfoBean) getRequestObject(TubeInfoBean.class);
			if (tube != null) {
				List<TubeInfoBean> list = new ArrayList();
				if(TextUtil.isNotNull(tube.getWellId())){
					list = this.pdaPipeService.getTubeBywell(tube.getWellId());
				}
				if(TextUtil.isNotNull(tube.getPipeSegmentId())){
					list = this.pdaPipeService.getTubeByPipe(tube.getPipeSegmentId());
				}
				sendResponse(Integer.valueOf(0), list);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.getTube ERROR\nJsonRequest:" + getJsonRequest()
					+ "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 得到实体对象
	 * @return
	 */
	public String getTubeSub(){
		try{
			TubeInfoBean tube = (TubeInfoBean) getRequestObject(TubeInfoBean.class);
			if (tube != null) {
				tube = this.pdaPipeService.getTubeObj(tube);
				sendResponse(Integer.valueOf(0), tube);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		}catch(Exception e){
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.getTubeSub ERROR\nJsonRequest:" + getJsonRequest()
					+ "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 增加空的信息
	 * @return
	 */
	public String insertTube() {
		try {
			TubeInfoBean tube = (TubeInfoBean) getRequestObject(TubeInfoBean.class);
			if (tube != null) {
				List<TubeInfoBean> list = this.pdaPipeService.beatchTube(tube);
				if(TextUtil.isNotNull(list)){
					sendResponse(Integer.valueOf(0), "添加成功");
					this.pdaPipeService.beatchSubTube(list,tube.getRentFlag());
					//综资添加管孔数据
					if(toIrms){
						String tubeId = "";
						for(TubeInfoBean obj : list){
							tubeId +=obj.getTubeId()+",";
						}
						if(tubeId.endsWith(",")){
							tubeId = tubeId.substring(0, tubeId.length()-1);
						}
						//new addTubeThread(tubeId).start();
					}
				}else{
					sendResponse(Integer.valueOf(1), "该RFID标签已使用，请更换RFID标签后再试。");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.insertTube ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	
	/**
	 * 综资增加管孔
	 * @author chenqp
	 *
	 */
	class addTubeThread extends Thread{
		private String tubeId;
		public addTubeThread(String tubeId){
			this.tubeId = tubeId;
		}
		@Override
		public void run() {
			try{
				List<TubeInfoBean> tubeList = pdaPipeService.getTubeList(tubeId, "1");
				if(TextUtil.isNotNull(tubeList)){
					TubeInfoBean tube = tubeList.get(0);
					PipeSegmentInfoBean pipeSeg =new PipeSegmentInfoBean();
					pipeSeg.setPipeSegmentId(Integer.parseInt(tube.getPipeSegmentId()));
					pipeSeg = pdaPipeService.getPipeSegObj(pipeSeg);
					irmsOutLineService.addTube(tubeList, pipeSeg.getResNum());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			super.run();
		}
	}
	

	/**
	 * 
	 * @return
	 */
	public String updateTube() {
		try {
			TubeInfoBean tube = (TubeInfoBean) getRequestObject(TubeInfoBean.class);
			TubeInfoBean tube1 = (TubeInfoBean) tube.clone();
			if (tube != null) {
				int i = this.pdaPipeService.updateTube(tube).intValue();
				if (i == -1) {
					sendResponse(Integer.valueOf(1), "该RFID标签已使用，请更换RFID标签后再试。");
				} else {
					sendResponse(Integer.valueOf(0), tube1);
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.updateTube ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 删除管孔
	 * @return
	 */
	public String deleteTube() {
		try {
			TubeInfoBean tube = (TubeInfoBean) getRequestObject(TubeInfoBean.class);
			if (tube != null) {
				this.pdaPipeService.deleteTube(tube);
				sendResponse(Integer.valueOf(0), "删除成功");
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.deleteTube ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	public String getLedup() {
		try {
			LedupInfoBean ledup = (LedupInfoBean) getRequestObject(LedupInfoBean.class);
			if (ledup != null) {
				List list = this.pdaPipeService.getLedup(ledup);
				sendResponse(Integer.valueOf(0), list);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.getLedup ERROR\nJsonRequest:" + getJsonRequest()
					+ "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	public String insertLedup() {
		try {
			LedupInfoBean ledup = (LedupInfoBean) getRequestObject(LedupInfoBean.class);
			if (ledup != null) {
				int i = this.pdaPipeService.insertLedup(ledup).intValue();
				ledup.setLedupPointId(Integer.valueOf(i));
				sendResponse(Integer.valueOf(0), ledup);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.insertLedup ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	public String updateLedup() {
		try {
			LedupInfoBean ledup = (LedupInfoBean) getRequestObject(LedupInfoBean.class);
			if (ledup != null) {
				this.pdaPipeService.updateLedup(ledup);
				sendResponse(Integer.valueOf(0), ledup);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.updateLedup ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	public String deleteLedup() {
		try {
			LedupInfoBean ledup = (LedupInfoBean) getRequestObject(LedupInfoBean.class);
			if (ledup != null) {
				this.pdaPipeService.deleteLedup(ledup);
				sendResponse(Integer.valueOf(0), "删除成功");
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误");
			}

		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDAPipe.deleteLedup ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	public PDAPipeService getPdaPipeService() {
		return this.pdaPipeService;
	}

	public IirmsOutLineService getIrmsOutLineService() {
		return irmsOutLineService;
	}

	public void setIrmsOutLineService(IirmsOutLineService irmsOutLineService) {
		this.irmsOutLineService = irmsOutLineService;
	}

	public void setPdaPipeService(PDAPipeService pdaPipeService) {
		this.pdaPipeService = pdaPipeService;
	}
}