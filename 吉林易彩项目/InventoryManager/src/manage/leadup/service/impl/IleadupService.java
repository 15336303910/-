package manage.leadup.service.impl;

import java.util.List;

import manage.leadup.pojo.HangWallPojo;
import manage.leadup.pojo.LeadupPojo;
import manage.leadup.pojo.SupportPointPojo;

public interface IleadupService {

	/**
	 * 得到引上信息
	 * @param obj
	 * @return
	 */
	public List<LeadupPojo> getLeadUp(LeadupPojo obj);
	
	/**
	 * 得到引上数据
	 * @param obj
	 * @return
	 */
	public int getLeadupCount(LeadupPojo obj);
	
	
	/**
	 * 修改引上段数据
	 * @param obj
	 * @return
	 */
	public int updateLeadup(LeadupPojo obj);
	
	
	
	/**
	 * 增加引上数据
	 * @param obj
	 * @return
	 */
	public int insertLeadup(LeadupPojo obj);
	
	
	/**
	 * 删除引上
	 * @param ids
	 */
	public void delLeadup(String ids);
	
	
	/**
	 * 得到引上具体对象
	 * @param leadup
	 * @return
	 */
	public LeadupPojo getLeadUpObj(LeadupPojo leadup);
	
	
	/**
	 * 得到引上的长度
	 * @param leadup
	 * @return
	 */
	public String getLeadupLength(LeadupPojo leadup);
	
	
	/**
	 * 得到所有的撑点
	 * @param spoint
	 * @return
	 */
	public List<SupportPointPojo> getsPointList(SupportPointPojo spoint);
	
	/**
	 * 得到撑点总数
	 * @param spoint
	 * @return
	 */
	public int getsPointCount(SupportPointPojo spoint);
	
	/**
	 * 修改撑点数据
	 * @param obj
	 * @return
	 */
	public int upsPoint(SupportPointPojo obj);
	
	/**
	 * 新增撑点数据
	 * @param obj
	 * @return
	 */
	public int addSupportPoint(SupportPointPojo obj);
	
	/**
	 * 批量删除撑点
	 * @param ids
	 * @return
	 */
	public int delSupportPoint(String ids);
	
	
	/**
	 * 得到是否有敷设关系
	 * @param obj
	 * @return
	 */
	public int getLeadupLayNum(LeadupPojo obj);
	
	
	/**
	 * 得到所有的挂墙段
	 * @param obj
	 * @return
	 */
	public List<HangWallPojo> getHangWallList(HangWallPojo obj);
	
	
	/**
	 * 得到所有的挂墙段数
	 * @param obj
	 * @return
	 */
	public int getHangWallCount(HangWallPojo obj);
	
	
	/**
	 * 增加挂墙段
	 * @param obj
	 * @return
	 */
	public int insertHangWall(HangWallPojo obj);
	
	/**
	 * 批量删除挂墙段
	 * @param ids
	 * @return
	 */
	public int delHangWall(String ids);
	
	/**
	 * 修改挂墙段
	 * @param obj
	 */
	public void updateHangWall(HangWallPojo obj);
	
	/**
	 * 得到计算长度
	 * @param obj
	 * @return
	 */
	public String getHangWallLength(HangWallPojo obj);
	
	
	/**
	 * 得到挂墙段详细数据
	 * @param obj
	 * @return
	 */
	public HangWallPojo getHangWallObj(HangWallPojo obj);
	
	
	/**
	 * 
	 * @param spoint
	 * @return
	 */
	public SupportPointPojo getsPointObj(SupportPointPojo spoint);
}
