package interfaces.pdainterface.GIS.service.impl;

import interfaces.pdainterface.GIS.pojo.GisPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import manage.equt.pojo.EqutInfoBean;
import manage.generator.pojo.StationBaseInfoBean;
import manage.opticalTerminal.pojo.OpticalTerminalObj;
import manage.route.pojo.FiberBoxInfoBean;

public interface IPDAGisService {

	
	/**
	 * 查询得到站点数据
	 * @param station
	 * @return
	 */
	public  List<StationBaseInfoBean> getStation(StationBaseInfoBean station);
	
	
	/**
	 * 得到引上的列表
	 * @param pojo
	 * @return
	 */
	public List<GisPojo> getLeadUp(GisPojo pojo);
	
	/**
	 * 得到光交箱
	 * @param equt
	 * @return
	 */
	public List<EqutInfoBean> getEqut(EqutInfoBean equt);
	
	
	/**
	 * 得到光终端盒
	 * @param obj
	 * @return
	 */
	public List<OpticalTerminalObj> getOpticalTerminal(OpticalTerminalObj obj);
	
	
	
	/**
	 * 得到GIS数据
	 * @param pojo
	 * @return
	 */
	public List<GisPojo> getGisLine(GisPojo pojo);
	
	
	
	/**
	 * 得到管道的数据
	 * @param pojo
	 * @return
	 */
	public int getPipeCount(GisPojo pojo);
	
	/**
	 * 得到分纤箱
	 * @param obj
	 * @return
	 */
	public List<FiberBoxInfoBean> getFiberBox(FiberBoxInfoBean obj);
	
	
	/**
	 * 删除资源点信息
	 * @param list
	 * @return
	 */
	public String delRes(List<GisPojo> list,String realName);
	
	
	/**
	 * 截取段信息
	 * @param obj
	 * @return
	 */
	/**
	 * @param obj
	 * @return
	 */
	public String cutoffSeg(GisPojo obj);
}
