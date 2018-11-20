package manage.V2.hebei.mainTask.service;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import manage.V2.hebei.mainTask.pojo.RouteInfoBean;

public interface MainTaskService {

	/**
	 * 巡检任务主键接口（出发）
	 * @return
	 */
	public int getRouteInfo(String uid,String jsonParams);
	
	/**
	 * 拍照上传接口
	 * @param jsonParams
	 * @return
	 */
	public String savePhoto(String uid,String jsonParams,ServletContext servletContext);
	
	/**
	 * 关联资源接口
	 * @param uid
	 * @param jsonParams
	 * @return
	 */
	public String getRalitonRes(String uid,String jsonParams);
	
	/**
	 * 隐患上传接口
	 * @param uid
	 * @param jsonParams
	 * @return
	 */
	public String saveError(String uid,String jsonParams,ServletContext servletContext);
	
	/**
	 * 巡查任务数据上传接口
	 * @param uid
	 * @param jsonParams
	 * @return
	 */
	public String saveTask(String uid,String jsonParams,ServletContext servletContext);
	
	/**
	 * 传输线路-我的
	 * @param uid
	 * @param jsonParams
	 * @return
	 */
	public String getMyRoute(String uid,String jsonParams);
	
	/**
	 * 清理垃圾路由数据
	 * @param uid
	 * @param jsonParams
	 * @return
	 */
	public String deleteRoute(String uid,String jsonParams);
	
	/**
	 * 获取路由段信息
	 * @param uid
	 * @param jsonParams
	 * @return
	 */
	public String getSegInfo(String uid,String jsonParams);
	
	
	
	/**
	 * 易采前台-我的路由
	 * @param routeInfoBean
	 * @return
	 */
	public List<RouteInfoBean> getMyRouteInfo(RouteInfoBean routeInfoBean);
	
	/**
	 * 通过routeId和userId获取路由对象信息
	 * @param uid
	 * @param jsonParams
	 * @return
	 */
	public String getRouteInfoById(String uid,String jsonParams);
}
