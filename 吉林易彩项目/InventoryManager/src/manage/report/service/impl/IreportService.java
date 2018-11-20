package manage.report.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manage.report.pojo.ReportBean;

public interface IreportService {

	
	/**
	 * 得到各地市
	 * 的热点数据
	 * @return
	 */
	public List<Map<String, Object>> getCityHot();
	
	
	/**
	 * 得到资源数据
	 * @param object
	 * @return
	 */
	public List<Map<String, Object>> getresList(ReportBean object);
	
	
	/**
	 * 得到资源总数
	 * @param object
	 * @return
	 */
	public Integer getResCount(ReportBean object);
	
	
	/**
	 * 导出数据
	 * @param request
	 * @param response
	 */
	public void expData(ReportBean report,HttpServletRequest request,HttpServletResponse response);
	
	
	/**
	 * 导出所有的数据
	 * @param report
	 * @param request
	 * @param response
	 */
	public void expAllData(ReportBean report,HttpServletRequest request,HttpServletResponse response);
	
	
	/**
	 * 导出经纬度
	 * @param request
	 * @param response
	 */
	public void expCoord(HttpServletRequest request,HttpServletResponse response);
	
	
	
}
