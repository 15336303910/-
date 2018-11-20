package interfaces.pdainterface.generator.action;

import base.exceptions.DataAccessException;
import base.util.MapUtil;
import base.util.TextUtil;
import base.util.pojo.Point;
import base.web.InterfaceAction;
import interfaces.pdainterface.generator.service.IPDAMachineRoomService;
import java.util.LinkedList;
import java.util.List;
import manage.generator.pojo.MachineRoomInfoBean;
import org.apache.log4j.Logger;

public class PDAMachineRoomAction extends InterfaceAction {
	
	private static final long serialVersionUID = -7878749011639113754L;
	private static final Logger log = Logger.getLogger(PDAMachineRoomAction.class);
	private IPDAMachineRoomService pdaMachineRoomService;
	
	/**
	 * 增加机房
	 * @return
	 */
	public String insertMachineRoom() {
		try {
			MachineRoomInfoBean machineRoom = (MachineRoomInfoBean) getRequestObject(MachineRoomInfoBean.class);
			if (machineRoom != null) {
				if (this.checkMachineRoom(machineRoom.getZh_label(), null) > 0) {
					sendResponse(Integer.valueOf(2), "机房名称重复。");
				} else {					
					if (isWGS) {
						Point point = MapUtil.phone_db_encrypt(Double.parseDouble(machineRoom.getLatitude()),
								Double.parseDouble(machineRoom.getLongitude()));
						machineRoom.setLatitude(point.getLat() + "");
						machineRoom.setLongitude(point.getLng() + "");
					}
					machineRoom.setCreator(realName);
					int id = this.pdaMachineRoomService.insertMachineRoom(machineRoom);
					machineRoom.setInt_id(id);
					machineRoom.setStateflag(0);
					sendResponse(Integer.valueOf(0), machineRoom);
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("pdagenerator.insertMachineRoom ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(),
					e);
		}
		return "success";
	}

	/**
	 * 得到机房数据
	 * @return
	 */
	public String getMachineRoom() {
		try {
			MachineRoomInfoBean machineRoom = (MachineRoomInfoBean) getRequestObject(MachineRoomInfoBean.class);
			if (machineRoom != null) {
				machineRoom.setStart(this.start);
				machineRoom.setLimit(this.limit);
				machineRoom.setStateflag(0);
				List<MachineRoomInfoBean> machineRoomList = new LinkedList<MachineRoomInfoBean>();
				machineRoomList = this.pdaMachineRoomService.getMachineRoom(machineRoom);
				for (MachineRoomInfoBean obj : machineRoomList) {
					if (isWGS && TextUtil.isNotNull(obj.getLatitude()) && TextUtil.isNotNull(obj.getLongitude())) {
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(obj.getLatitude()),
								Double.parseDouble(obj.getLongitude()));
						obj.setLatitude(point.getLat() + "");
						obj.setLongitude(point.getLng() + "");
					}
				}
				sendResponse(Integer.valueOf(0), machineRoomList);
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("pdagenerator.getMachineRoom ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 修改机房
	 * 
	 * @return
	 */
	public String updateMachineRoom() {
		try {
			MachineRoomInfoBean machineRoom = (MachineRoomInfoBean) getRequestObject(MachineRoomInfoBean.class);
			if (machineRoom != null) {			
				if (isWGS) {
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(machineRoom.getLatitude()),
							Double.parseDouble(machineRoom.getLongitude()));
					machineRoom.setLatitude(point.getLat() + "");
					machineRoom.setLongitude(point.getLng() + "");
				}
				if (TextUtil.isNotNull(machineRoom.getInt_id())) {
					machineRoom.setModifier(realName);
					this.pdaMachineRoomService.updateMachineRoom(machineRoom);
					sendResponse(Integer.valueOf(0), "修改成功。");
				} else {
					int id = this.pdaMachineRoomService.insertMachineRoom(machineRoom);
					machineRoom.setInt_id(id);
					sendResponse(Integer.valueOf(0), "修改成功。");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("pdagenerator.updateMachineRoom ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(),
					e);
		}
		return "success";
	}
	
	/**
	 * 删除机房
	 * @return
	 */
	public String deleteMachineRoom() {
		try {
			MachineRoomInfoBean machineRoom = (MachineRoomInfoBean) getRequestObject(MachineRoomInfoBean.class);
			if (machineRoom != null) {
				if(TextUtil.isNotNull(machineRoom.getInt_id())){
					this.pdaMachineRoomService.deleteMachineRoom(machineRoom);
					sendResponse(Integer.valueOf(0), "删除成功。");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (DataAccessException e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("pdagenerator.deleteMachineRoom ERROR\nJsonRequest:"
					+ getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
	}

	/**
	 * 检查机房
	 * 
	 * @param name
	 * @return
	 */
	public Integer checkMachineRoom(String name, Integer id) {
		String checkSql = "select count(*) from rms_equiproom where stateflag ='0' and zh_label ='" + name + "'";
		if (TextUtil.isNotNull(id)) {
			checkSql += " and int_id !='" + id + "'";
		}
		int size = this.getJdbcTemplate().queryForInt(checkSql);
		return size;
	}

	public IPDAMachineRoomService getPdaMachineRoomService() {
		return pdaMachineRoomService;
	}

	public void setPdaMachineRoomService(IPDAMachineRoomService pdaMachineRoomService) {
		this.pdaMachineRoomService = pdaMachineRoomService;
	}
}