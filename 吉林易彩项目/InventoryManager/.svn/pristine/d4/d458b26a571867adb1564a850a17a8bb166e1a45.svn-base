package manage.device.service.impl;

import interfaces.pdainterface.equt.pojo.EqutRankInfo;

import java.util.List;

import manage.device.pojo.PointBean;
import manage.device.pojo.PointCommon;
import manage.equt.pojo.EqutInfoBean;
import manage.point.pojo.PointInfoBean;
import manage.route.pojo.CableInfoBean;
import manage.route.pojo.FiberInfoBean;

public interface IFiberRackService {

	/**
	 * ODM端子纤芯落架
	 * @param list
	 * @return
	 */
	public int batchFiberOdmPoint(List<PointInfoBean> list,EqutRankInfo equtRank);
	
	
	/**
	 * 根据光缆段id得到
	 * 光缆段信息
	 * @param cableId
	 * @return
	 */
	public CableInfoBean getCableById(Integer cableId);
	
	
	
	/**
	 * 根据端子id得到对应的数据
	 * @param pointId
	 * @return
	 */
	public EqutInfoBean getEqutByPoint(String eid);
	
	/**
	 * 根据端子得到端子数据
	 * @param pointId
	 * @return
	 */
	public PointInfoBean getPointInfo(Integer pointId);
	
	
	/**
	 * 得到纤芯的数据
	 * @param fiberId
	 * @return
	 */
	public FiberInfoBean getFiberById(Integer fiberId);
	
	
	/**
	 * 落架一个端子对应的纤芯
	 * @param cable
	 * @param equt
	 * @param fiber
	 * @return
	 */
	public String fallApartPoint(CableInfoBean cable,EqutInfoBean equt,PointInfoBean point,FiberInfoBean fiber);
}
