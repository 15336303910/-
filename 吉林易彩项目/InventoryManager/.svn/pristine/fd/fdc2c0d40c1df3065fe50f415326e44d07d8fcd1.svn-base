package interfaces.irmsInterface.interfaces.station.service.impl;

import interfaces.pdainterface.equt.pojo.EqutRankInfo;
import manage.device.pojo.JumpFiberBean;
import manage.equt.pojo.EqutInfoBean;
import manage.equt.pojo.ODMInfoBean;
import manage.generator.pojo.StationBaseInfoBean;
import manage.point.pojo.PointInfoBean;
import manage.resPoint.pojo.WirelessPointPojo;

public interface IirmsStationService {

	/**
	 * 增加站点接口
	 * @return
	 */
	public String insertStation();
	
	
	/**
	 * 移动
	 */
	public void movStation(StationBaseInfoBean pojo);
	
	
	/**
	 * 增加机架
	 * @param equt
	 * @return
	 */
	public String insertOdf(EqutInfoBean equt);
	
	
	/**
	 * 增加ODM模块
	 * @return
	 */
	public String insertOdm(ODMInfoBean odm);
	
	/**
	 * 修改机架信息
	 * @param equt
	 * @return
	 */
	public String updateRack(EqutInfoBean equt);
	
	/**
	 * 删除ODM
	 * @param odm
	 */
	public void delOdm(ODMInfoBean odm);
	
	
	/**
	 * 保存跳纤关系
	 * @param list
	 */
	public void saveJumper(JumpFiberBean jumpFier);
	
	
	/**
	 * 保存上架信息
	 * @param equtRank
	 */
	public void saveFiber(EqutRankInfo equtRank);
	
	
	/**
	 * 删除上架信息
	 * @param point
	 */
	public void delFiber(PointInfoBean point);
	
	
	/**
	 * 移动网络资源点
	 * @param obj
	 */
	public void moveResPoint(WirelessPointPojo obj);
	
	
	/**
	 * 增加机框
	 * @param equt
	 * @return
	 */
	public String addPosition(EqutInfoBean equt,String gres);
	
	
	/**
	 * 删除机架
	 * @param equt
	 * @return
	 */
	public String delRack(EqutInfoBean equt);
}
