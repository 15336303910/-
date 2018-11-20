package manage.device.service.impl;


import java.util.List;

import manage.device.pojo.CardInfoBean;
import manage.device.pojo.DeviceInfoBean;
import manage.device.pojo.JumpFiberBean;
import manage.device.pojo.JumpPosBean;
import manage.device.pojo.PointBean;
import manage.device.pojo.PointCommon;
import manage.route.pojo.CableInfoBean;

public interface IDeviceService {

	/**
	 * 得到所有的
	 * 网元设备信息
	 * @param obj
	 * @return
	 */
	public List<DeviceInfoBean> getDeviceGrid(DeviceInfoBean obj);
	
	
	/***
	 * 增加网元设备
	 * @param obj
	 * @return
	 */
	public int insertDevice(DeviceInfoBean obj);
	
	/**
	 * 修改网元设备
	 * @param obj
	 */
	public void updateDevice(DeviceInfoBean obj);
	
	
	/**
	 * 批量更新数据
	 * @param obj
	 */
	public void beatchUpDevice(List<DeviceInfoBean> list);
	
	/**
	 * 得到所有的板卡信息
	 * @param obj
	 * @return
	 */
	public List<CardInfoBean> getCardGrid(CardInfoBean obj);
	
	/**
	 * 临时根据设备
	 * 生成板卡和端子
	 * @param obj
	 */
	public void tempDevice(DeviceInfoBean obj);
	
	/**
	 * 增加板卡操作
	 * @param obj
	 * @return
	 */
	public int insertCard(CardInfoBean obj);
	
	
	/**
	 * 修改板卡操作
	 * @param obj
	 */
	public void updateCard(CardInfoBean obj);
	
	
	/**
	 * 得到所有的端口信息
	 * @param obj
	 * @return
	 */
	public List<PointBean> getPointGrid(PointBean obj);
	
	/**
	 * 增加端口操作
	 * @param obj
	 * @return
	 */
	public int insertPoint(PointBean obj);
	
	
	/**
	 * 修改端口操作
	 * @param obj
	 */
	public void updatePoint(PointBean obj);
	
	/**
	 * 批量更新端口状态
	 * @param list
	 */
	public void beachUpdatePoint(final List<PointBean> list);
	
	
	/**
	 * 得到板卡下所有
	 * 的端口信息
	 * @param obj
	 * @return
	 */
	public List<PointBean> getPointList(PointBean obj);
	
	
	/**
	 * 跳纤
	 * @param list
	 * @return
	 */
	public void jumpFiber(List<JumpFiberBean> list);
	
	
	/**
	 * 保存跳纤到分光器
	 * * @param pos
	 */
	public void jumpPos(JumpPosBean pos);
	
	
	/**
	 * 更新板卡及
	 * 端口的所属机架
	 * @param list
	 */
	public void tempUpDevice(List<DeviceInfoBean> list);
}
