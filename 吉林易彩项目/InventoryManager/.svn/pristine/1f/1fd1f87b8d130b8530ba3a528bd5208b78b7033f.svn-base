package manage.buriedPart.service.impl;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manage.buriedPart.pojo.BuriedPartObj;

public interface IburiedPartService {
	
	/**
	 * 分页查询
	 * @param object
	 * @param start
	 * @param length
	 * @return
	 */
	public List<BuriedPartObj> getBuriedPartGrid(BuriedPartObj object);
	
	
	/**
	 * 得到数据条数
	 * @param object
	 * @return
	 */
	public int getBuriedPartCount(BuriedPartObj object);
	
	
	/**
	 * 得到直埋段信息
	 * @param obj
	 * @return
	 */
	public List<BuriedPartObj> getBuriedPart(BuriedPartObj obj);
	
	
	/**
	 * 更改直埋段信息
	 * @param obj
	 * @return
	 */
	public int updateBuriedPart(BuriedPartObj obj);
	
	
	/**
	 * 增加直埋段信息
	 * @param obj
	 * @return
	 */
	public int insertBuriedPart(BuriedPartObj obj);
	
	/**
	 * 删除直埋段
	 * @param ids
	 */
	public void delBuriedPart(String ids);
	
	
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
	public void expData(BuriedPartObj obj,HttpServletRequest request,HttpServletResponse response);
	
	
	/**
	 * 导入数据
	 * @param file
	 * @param fileName
	 * @return
	 */
	public String impData(File file,String fileName);
	
	
	/**
	 * 得到直埋段
	 * @param obj
	 * @return
	 */
	public BuriedPartObj getBuriedPartPojo(BuriedPartObj obj);
	
	
	/**
	 * 查询敷设信息
	 * @param obj
	 * @return
	 */
	public BuriedPartObj getBuriedlay(BuriedPartObj obj);
	
	
	/**
	 * 设置光缆段计算长度
	 * @param obj
	 * @return
	 */
	public BuriedPartObj setBuriedPartLength(BuriedPartObj obj);
}
