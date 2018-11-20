package manage.transmissionEquipment.service.impl;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.exceptions.DataAccessException;
import interfaces.pdainterface.supportingRing.pojo.SupportingRingInfoBean;
import manage.stone.pojo.StoneInfoBean;
import manage.transmissionEquipment.pojo.TransmissionEquipmentBean;

public interface ItransmissionEquipmentService {
	/**
	 * 得到所有的传输设备
	 * @param obj
	 * @return
	 */
	public List<TransmissionEquipmentBean> getTransmissionEquipmentGrid(TransmissionEquipmentBean obj);
	
	/**
	 * 得到查询的条数
	 * @param obj
	 * @return
	 */
	public int getTransmissionEquipmentCount(TransmissionEquipmentBean obj);
	
	/**
	 * 得到传输设备(列表)
	 * @param obj
	 * @return
	 */
	
	public List<TransmissionEquipmentBean> getTransmissionEquipmentList(TransmissionEquipmentBean obj);
	
	/**
	 * 得到具体的传输设备(详情)
	 * @param obj
	 * @return
	 */
	public TransmissionEquipmentBean getTransmissionEquipmentObj(TransmissionEquipmentBean obj);
	
	/**
	 * 修改传输设备
	 * @param obj
	 * @return
	 */
	public int updateTransmissionEquipment(TransmissionEquipmentBean obj);
	
	
	/**
	 * 添加传输设备
	 * @param obj
	 * @return
	 */
	public int insertTransmissionEquipment(TransmissionEquipmentBean obj);
	
	/**
	 * 根据主键得到
	 * @param stoneId
	 * @return
	 */
	public TransmissionEquipmentBean getTransmissionEquipmentById(Integer transmissionEquipmentId);
	

	/**
	 * 真删除
	 * @throws DataAccessException
	 */
	public int deleteSupportingRing(TransmissionEquipmentBean equip) throws DataAccessException;

	/**
	 * 更改删除标记状态
	 * @throws DataAccessException
	 */
	public int updateDeleteFlag(TransmissionEquipmentBean equip) throws DataAccessException;

}

	
