package manage.transmissionEquipment.service;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.TextUtil;
import interfaces.pdainterface.supportingRing.pojo.SupportingRingInfoBean;
import manage.buriedPart.pojo.BuriedPartObj;
import manage.poleline.pojo.PolelineSegmentInfoBean;
import manage.stone.pojo.StoneInfoBean;
import manage.transmissionEquipment.pojo.TransmissionEquipmentBean;
import manage.transmissionEquipment.service.impl.ItransmissionEquipmentService;

public class TransmissionEquipmentService extends DataBase implements ItransmissionEquipmentService {
	private ItransmissionEquipmentService equipmentService;
	private JdbcTemplate jdbcTemplate;
	private static String transmissionEquipmentGrid ="transmissionEquipment.getTransmissionEquipmentGrid";
	private static String getEquipment ="transmissionEquipment.getStone";
	
	public ItransmissionEquipmentService getEquipmentService() {
		return equipmentService;
	}

	public void setEquipmentService(ItransmissionEquipmentService equipmentService) {
		this.equipmentService = equipmentService;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 分页查询
	 */
	public List<TransmissionEquipmentBean> getTransmissionEquipmentGrid(TransmissionEquipmentBean obj) {
		if(TextUtil.isNotNull(obj.getEquipmentLable())){
			String equipmentlable = obj.getEquipmentLable();
			if(equipmentlable.contains(" ")){
				equipmentlable= equipmentlable.replaceAll(" +", "%");
			}
			obj.setEquipmentLable("%"+equipmentlable+"%");
		}
		List<TransmissionEquipmentBean> list = getObjects(transmissionEquipmentGrid, obj);
		return list;
	}

	/**
	 * 查询总的记录数
	 */
	public int getTransmissionEquipmentCount(TransmissionEquipmentBean obj) {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * 得到传输设备(列表)
	 */
		
	public List<TransmissionEquipmentBean> getTransmissionEquipmentList(TransmissionEquipmentBean obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 详情
	 */
	public TransmissionEquipmentBean getTransmissionEquipmentObj(TransmissionEquipmentBean obj) {
		TransmissionEquipmentBean equip = (TransmissionEquipmentBean) getObject("transmissionEquipment.getEquipment", obj);
		  return equip;
	}
	/**
	 * 修改
	 */
	public int updateTransmissionEquipment(TransmissionEquipmentBean obj) {
		
		return this.update("transmissionEquipment.updateTrans", obj);
	}
	/**
	 * 增加
	 */
	public int insertTransmissionEquipment(TransmissionEquipmentBean obj) {
		
		return (Integer) this.insert("transmissionEquipment.insertTransmissionEquipment", obj);
	}
	
	@Override
	public int deleteSupportingRing(TransmissionEquipmentBean equip) throws DataAccessException {
		return delete("transmissionEquipment.deleteSupportingRing", equip);
	}

	@Override
	public int updateDeleteFlag(TransmissionEquipmentBean equip) throws DataAccessException {
		return update("transmissionEquipment.updateDeleteFlag", equip);
	}

	/**
	 * 根据id查询
	 */
	public TransmissionEquipmentBean getTransmissionEquipmentById(Integer transmissionEquipmentId) {
		TransmissionEquipmentBean equipment = new TransmissionEquipmentBean();
		equipment.setEquipmentId(transmissionEquipmentId);
		return (TransmissionEquipmentBean)getObject("transmissionEquipment.getStone", equipment);
	}
	}


