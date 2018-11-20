package manage.buried.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manage.buried.pojo.BuriedInfoObj;

public interface IburiedService {

	
	
	/**
	 * 得到分页数据
	 * @param object
	 * @param start
	 * @param length
	 * @return
	 */
	public List<BuriedInfoObj> getBuriedGrid(BuriedInfoObj object);
	
	/**
	 * 得到直埋列表
	 * @param object
	 * @return
	 */
	public List<BuriedInfoObj> getBuried(BuriedInfoObj object);
	
	
	/**
	 * 修改直埋信息
	 * @param object
	 * @return
	 */
	public int updateBuried(BuriedInfoObj object);
	
	
	/**
	 * 新建直埋信息
	 * @param object
	 * @return
	 */
	public int insertBuried(BuriedInfoObj object);
	
	/**
	 * 得到分页总数
	 * @param object
	 * @return
	 */
	public int getBuriedCount(BuriedInfoObj object);
	
	
	/**
	 * 删除标石数据
	 * @param ids
	 */
	public void delBurieds(String ids);
	
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
	public void expData(BuriedInfoObj object,HttpServletRequest request,HttpServletResponse response);
	
	
	/**
	 * 导入数据
	 * @param file
	 * @param fileName
	 * @return
	 */
	public String impData(File file,String fileName);
	
	/**
	 * 得到直埋map
	 * @param obj
	 * @return
	 */
	public Map<String, String> getBuriedMap(BuriedInfoObj obj);
}
