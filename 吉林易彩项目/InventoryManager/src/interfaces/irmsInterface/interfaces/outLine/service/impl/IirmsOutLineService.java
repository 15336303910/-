package interfaces.irmsInterface.interfaces.outLine.service.impl;

import interfaces.irmsInterface.interfaces.outLine.pojo.IrmsPoint;
import interfaces.pdainterface.lineSystem.pojo.LineSystemInfo;

import java.util.List;
import java.util.Map;

import manage.buriedPart.pojo.BuriedPartObj;
import manage.leadup.pojo.HangWallPojo;
import manage.leadup.pojo.LeadupPojo;
import manage.leadup.pojo.SupportPointPojo;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.pipe.pojo.TubeInfoBean;
import manage.pipe.pojo.WellInfoBean;
import manage.poleline.pojo.PoleInfoBean;
import manage.poleline.pojo.PolelineSegmentInfoBean;
import manage.stone.pojo.StoneInfoBean;

public interface IirmsOutLineService {
	
	
	/**
	 * 增加资源点信息
	 * @param list
	 * @return
	 */
	public String getPointStr(List<Map<String, Object>> list);
	
	/**
	 * 增加杆路段
	 * @param poleLine
	 */
	public String addPoleLine(PolelineSegmentInfoBean poleLine);
	
	/**
	 * 增加电杆
	 * @param pole
	 */
	public String addPole(PoleInfoBean pole);

	/**
	 * 修改电杆的位置
	 * @param pole
	 * @param gis
	 * @return
	 */
	public void movePole(PoleInfoBean pole);
	
	/**
	 * 增加井
	 * @param well
	 */
	public String addWell(WellInfoBean well);
	
	/**
	 * 移动井
	 * @param well
	 * @param gis
	 * @return
	 */
	public void moveWell(WellInfoBean well);
	
	/**
	 * 管道段
	 * @param pipe
	 */
	public String addPipeSeg(PipeSegmentInfoBean pipe);
	
	/**
	 * 增加标石
	 * @param stone
	 */
	public String addStone(StoneInfoBean stone);
	
	/**
	 * 移动标石
	 * @param stone
	 */
	public void moveStone(StoneInfoBean stone);
	
	/**
	 * 增加直埋段
	 * @param buried
	 */
	public String addBuried(BuriedPartObj buried);
	
	/**
	 * 增加管孔
	 * @param tube
	 */
	public void addTube(List<TubeInfoBean> tubeList,String pipeRes);
	
	/**
	 * 增加撑点
	 * @param support
	 */
	public void addSupport(SupportPointPojo support);
	
	/**
	 * 移动撑点位置
	 * @param obj
	 */
	public void moveSupport(SupportPointPojo support);
	
	/**
	 * 增加挂墙段
	 * @param hang
	 */
	public void addHangWall(HangWallPojo hang);
	
	
	/**
	 * 增加引上段
	 * @param leadUp
	 */
	public String addLeadUp(LeadupPojo leadUp);
	
	/**
	 * 
	 * @param id
	 * @param resType
	 */
	public void test(String id,String resType);
	
	
	/**
	 * 增加点接口
	 * @param obj
	 */
	public void addPoint(IrmsPoint obj);
	
	
	/**
	 * 移动点资源
	 * @param point
	 */
	public void movePoint(IrmsPoint point);
	
	/**
	 * 删除资源点
	 * @param obj
	 */
	public void delPoint(IrmsPoint irms);
	
	
	/**
	 * 删除标石
	 * @param stone
	 */
	public void delStone(StoneInfoBean stone);
	
	
	/**
	 * 删除人手井
	 * @param well
	 */
	public void delWell(WellInfoBean well);
	
	
	/**
	 * 修改电杆的位置
	 * @param pole
	 * @param gis
	 * @return
	 */
	public void delPole(PoleInfoBean pole);
	
	/**
	 * 新增一个外线系统
	 * @param line
	 * @return
	 */
	public String addSystem(LineSystemInfo line);
	
	
	/**
	 * 判断是否是数字
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str);
	
	/**
	 * 修改外线杆路段资源
	 * @param poleLine
	 * @return
	 */
	public String upPoleLine(PolelineSegmentInfoBean poleLine);
	
	
	/**
	 * 得到所属的管线系统
	 * @param sysId
	 * @return
	 */
	public String getSystem(String sysId);
	
	
	/**
	 * 修改管道段
	 * @param pipe
	 * @return
	 */
	public String upPipeSeg(PipeSegmentInfoBean pipe);
	
	
	/**
	 * 修改直埋段
	 * @param buried
	 * @return
	 */
	public String upBuried(BuriedPartObj buried);
	
	
	
	/**
	 * 更新照片信息
	 * @param imId
	 * @param type
	 * @param resNum
	 */
	public void upImageStr(String imId,String type,String resNum);
	
	
	/**
	 * 得到维护人的相关信息
	 * @param userName
	 * @return
	 */
	public List<Map<String, Object>> getMaintainList(String userName);
}
