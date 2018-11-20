package manage.resPoint.service.impl;

import java.util.List;

import manage.resPoint.pojo.WirelessPointPojo;

public interface IwirelessService {

	/**
	 * 得到网络资源点
	 * @param obj
	 * @return
	 */
	public List<WirelessPointPojo> getWirelessGrid(WirelessPointPojo obj);
	
	
	/**
	 * 得网络资源列表
	 * @param obj
	 * @return
	 */
	public List<WirelessPointPojo> getWirelessList(WirelessPointPojo obj);
	
	
	/**
	 * 新增网络资源点
	 * @param obj
	 * @return
	 */
	public int addWireless(WirelessPointPojo obj);
	
	
	/**
	 * 修改网络资源点
	 * @param obj
	 */
	public void updateWireless(WirelessPointPojo obj);
	
	
	/**
	 * 得到网络资源点
	 * @param obj
	 * @return
	 */
	public WirelessPointPojo getWirelessObj(WirelessPointPojo obj);
}
