package manage.stone.service.impl;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manage.stone.pojo.StoneInfoBean;

public interface IstoneInfoService {

	/**
	 * 得到所有的标石信息
	 * @param obj
	 * @return
	 */
	public List<StoneInfoBean> getStoneGrid(StoneInfoBean obj);
	
	
	/**
	 * 更新数据
	 */
	public void upVal();
	
	
	/**
	 * 得到查询的条数
	 * @param obj
	 * @return
	 */
	public int getStoneCount(StoneInfoBean obj);
	
	/**
	 * 得到标石列表
	 * @param obj
	 * @return
	 */
	public List<StoneInfoBean> getStone(StoneInfoBean obj);
	
	/**
	 * 修改标石信息
	 * @param obj
	 * @return
	 */
	public int updateStone(StoneInfoBean obj);
	
	
	/**
	 * 添加标石信息
	 * @param obj
	 * @return
	 */
	public int insertStone(StoneInfoBean obj);
	
	
	/**
	 * 删除标石数据
	 * @param ids
	 */
	public void delStones(String ids);
	
	/**
	 * 得到标石的敷设
	 * @param stone
	 * @return
	 */
	public boolean getStoneLay(StoneInfoBean stone);
	
	/**
	 * 根据主键得到
	 * @param stoneId
	 * @return
	 */
	public StoneInfoBean getStoneById(Integer stoneId);
	
	
	/**
	 * 导出数据模板
	 * @param request
	 * @param response
	 */
	public void expTemp(HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 导出数据
	 * @param request
	 * @param response
	 */
	public void expData(StoneInfoBean obj,HttpServletRequest request,HttpServletResponse response);
	
	
	
	/**
	 * 导入数据
	 * @param file
	 * @param fileName
	 * @return
	 */
	public String impData(File file,String fileName);
	
	
	
	/**
	 * 更新直埋段数据
	 * @param stoneId
	 * @param stoneName
	 */
	public void upBuriedSeg(Integer stoneId,String stoneName);
	
	
	/**
	 * 得到具体的标石
	 * @param obj
	 * @return
	 */
	public StoneInfoBean getStoneObj(StoneInfoBean obj);
}
