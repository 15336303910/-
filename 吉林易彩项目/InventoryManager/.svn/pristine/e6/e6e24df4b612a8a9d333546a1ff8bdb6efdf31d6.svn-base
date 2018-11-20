package manage.opticalTerminal.service.impl;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manage.opticalTerminal.pojo.OpticalTerminalObj;

public interface IopticalTerminalService {

	/**
	 * 得到查询
	 * @param obj
	 * @param start
	 * @param length
	 * @return
	 */
	public List<OpticalTerminalObj> getOpticalTerGrid(OpticalTerminalObj obj);
	
	
	/**
	 * 得到数据
	 * @param obj
	 * @return
	 */
	public int getOpticalTerCount(OpticalTerminalObj obj);
	
	
	/**
	 * 得到光终端盒查询
	 * @param obj
	 * @return
	 */
	public List<OpticalTerminalObj> getOpticalTer(OpticalTerminalObj obj);
	
	/**
	 * 修改光终端盒
	 * @param obj
	 * @return
	 */
	public int updateOpticalTer(OpticalTerminalObj obj);
	
	
	/**
	 * 插入光终端盒
	 * @param obj
	 * @return
	 */
	public int insertOpticalTer(OpticalTerminalObj obj);
	
	
	/**
	 * 删除光终端盒
	 * @param ids
	 */
	public void delOpticalTer(String ids);
	
	
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
	public void expData(OpticalTerminalObj obj,HttpServletRequest request,HttpServletResponse response);
	
	
	/**
	 * 导入数据
	 * @param file
	 * @param fileName
	 * @return
	 */
	public String impData(File file,String fileName);
	
	
	/**
	 * 得到光终端盒实例
	 * @param obj
	 * @return
	 */
	public OpticalTerminalObj getOptTerObj(OpticalTerminalObj obj);
}
