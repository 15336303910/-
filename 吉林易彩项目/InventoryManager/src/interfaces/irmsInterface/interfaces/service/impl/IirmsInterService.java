package interfaces.irmsInterface.interfaces.service.impl;

import java.util.List;
import java.util.Map;

import manage.generator.pojo.StationBaseInfoBean;
import interfaces.irmsInterface.interfaces.pojo.IrmsPojo;
import interfaces.irmsInterface.interfaces.pojo.MoveResPojo;
import interfaces.pdainterface.lineSystem.pojo.LineSystemInfo;

public interface IirmsInterService {

	
	/**
	 * 添加接口记录
	 * @param obj
	 * @return
	 */
	public int addInterLog(IrmsPojo obj);
	
	
	/**
	 * 得到地市区县的代号
	 * @param country
	 * @return
	 */
	public Map<String, String> getCityCountry(String country);
	
	
	/**
	 * 移动资源
	 * @param movePojo
	 */
	public void moveRes(MoveResPojo movePojo);
	
	
	/**
	 * 增加资源点
	 * @param pojo
	 * @return
	 */
	public String addRes(MoveResPojo pojo,String appId);
	
	
	/**
	 * 得到输入段
	 * @param map
	 * @return
	 */
	public String getLineInStr(Map< String, String> map);
	
	
	/**
	 * 更新综资ycid
	 * @param type
	 * @param appId
	 * @param resId
	 */
	public void upYcapp(String type,String appId,String resId);
	
	
	/**
	 * 增加一个系统
	 * @param zhLabel
	 * @param cityId
	 * @param countyId
	 * @param lineType
	 * @return
	 */
	public String addSys(String zhLabel,String cityId,String countyId,String lineType,LineSystemInfo line);
	
	
	/**
	 * 得到关键字
	 * @param name
	 * @return
	 */
	public String getKeyStr(String name);
	
	
	/**
	 * 得到分页数据
	 * @param obj
	 * @return
	 */
	public List<IrmsPojo> getIrmsGrid(IrmsPojo obj);
	
	
	/**
	 * 得到分页总数
	 * @param obj
	 * @return
	 */
	public int getIrmsCount(IrmsPojo obj);
	
	
	/**
	 * 得到具体对象
	 * @param obj
	 * @return
	 */
	public IrmsPojo getIrmsObj(IrmsPojo obj);
	
	
	/**
	 * 移动光电设备
	 * @param movePojo
	 */
	public String moveOptical(MoveResPojo movePojo);
	
	
	/**
	 * 移动资源
	 * @param movePojo
	 */
	public void delRes(MoveResPojo movePojo);
	
	/**
	 * 移动站点
	 * @param movePojo
	 * @param station
	 */
	public void moveSite(MoveResPojo movePojo,StationBaseInfoBean station);
	
	
	/**
	 * 更新段信息
	 * @param map
	 * @return
	 */
	public String upLineInstr(Map<String, String> map);
	
	
	/**
	 * xiugai ziyuan shuju 
	 * @param pojo
	 * @param appId
	 * @return
	 */
	public String upRes(MoveResPojo pojo,String appId);
}
