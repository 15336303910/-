package interfaces.pdainterface.lineSystem.service.impl;

import interfaces.pdainterface.lineSystem.pojo.LineAffiliationInfo;
import interfaces.pdainterface.lineSystem.pojo.LinePointInfo;
import interfaces.pdainterface.lineSystem.pojo.LineSegmentInfo;
import interfaces.pdainterface.lineSystem.pojo.LineSystemInfo;

import java.util.List;
import java.util.Map;

public interface IlineSystemService {

	
	/**
	 * 得到所有的列表
	 * @param info
	 * @return
	 */
	public List<LineSystemInfo> getLineSystemList(LineSystemInfo info);
	
	
	/**
	 * 得到管线系统
	 * @param info
	 * @return
	 */
	public LineSystemInfo getLineSystemObj(LineSystemInfo info);
	
	/**
	 * 修改管线
	 * @param info
	 * @return
	 */
	public int upLineSystem(LineSystemInfo info);
	
	
	/**
	 * 增加管线
	 * @param info
	 * @return
	 */
	public int insertLineSystem(LineSystemInfo info);
	
	/**
	 * 得到点的列表
	 * @param info
	 * @return
	 */
	public List<LinePointInfo> getLinePoint(LinePointInfo info);
	
	
	/**
	 * 进行核查管线系统是否存在重复数据
	 * @param info
	 * @return
	 */
	public LineSystemInfo checkLineSystemObj(LineSystemInfo info);
	
	
	/**
	 * 得到段的数据
	 * @param info
	 * @return
	 */
	public List<LineSegmentInfo> getLineSegList(LineSegmentInfo info);
	
	/**
	 * 得到地图展现
	 * 的光缆段的数据
	 * @param obj
	 * @return
	 */
	public List<LineSegmentInfo> getCableGis(LineSystemInfo obj);
	
	/**
	 * 得到最大的记录
	 * @param obj
	 * @return
	 */
	public Map<String, Object> getMaxLinePoint(LinePointInfo obj);
	
	/**
	 * 增加点数据
	 * @param obj
	 * @param map
	 * @return
	 */
	public int addLinePoint(LinePointInfo obj,Map<String, Object> map,String loginer);
	
	/**
	 * 得到地图上的数据
	 * @param obj
	 * @return
	 */
	public List<LineSegmentInfo> getLineGis(LineSystemInfo obj);
	
	
	/**
	 * 得到长度
	 * @param obj
	 * @return
	 */
	public Map<String, Object> getLineLength(LineSystemInfo obj);
	
	/**
	 * 修改点归属到
	 * 当前管线系统
	 * @param obj
	 * @param map
	 * @param loginer
	 * @return
	 */
	public int upLinePoint(LineAffiliationInfo obj,Map<String, Object> map,String loginer);
}
