package interfaces.pdainterface.equt.service;

import base.exceptions.DataAccessException;
import interfaces.pdainterface.equt.pojo.EqutCableInfo;
import interfaces.pdainterface.equt.pojo.OdmFiberInfo;

import java.util.List;
import java.util.Map;

import manage.document.pojo.DocEqutInfoBean;
import manage.equt.pojo.EqutInfoBean;
import manage.equt.pojo.ODMInfoBean;
import manage.point.pojo.PointInfoBean;
import manage.route.pojo.CableInfoBean;

public abstract interface PDAEqutInfoService {
	public abstract List<DocEqutInfoBean> getDocEqutList(
			DocEqutInfoBean paramDocEqutInfoBean) throws DataAccessException;

	public abstract List<EqutInfoBean> getEqut(EqutInfoBean paramEqutInfoBean)
			throws DataAccessException;

	public abstract int updateEqut(EqutInfoBean paramEqutInfoBean)
			throws DataAccessException;

	public abstract int insertEqut(EqutInfoBean paramEqutInfoBean)
			throws DataAccessException;

	public abstract List<ODMInfoBean> getODM(ODMInfoBean paramODMInfoBean)
			throws DataAccessException;

	public abstract void updateODM(List<ODMInfoBean> paramList)
			throws DataAccessException;

	/**;
	 * 增加odm信息
	 */
	public List<ODMInfoBean> insertODM(List<ODMInfoBean> odmList,String loginer) throws DataAccessException;

	public abstract List<PointInfoBean> getPoint(
			PointInfoBean paramPointInfoBean) throws DataAccessException;

	public abstract void updatePoint(PointInfoBean paramPointInfoBean)
			throws DataAccessException;

	public abstract void insertPoint(List<PointInfoBean> paramList)
			throws DataAccessException;
	
	/**
	 * 修改odm对象
	 * @param odm
	 */
	public void updateOdmObj(ODMInfoBean odm);
	
	
	/**
	 * 判断是否有
	 * 综合设备
	 * @param sql
	 * @return
	 */
	public int getDeviceCount(String sql);
	
	
	/**
	 * 执行语句
	 * @param sql
	 */
	public void exeSql(String sql);
	
	
	/**
	 * 得到机架所
	 * 承载的光缆段
	 * @return
	 */
	public List<EqutCableInfo> getEqutCable(EqutCableInfo eCable);
	
	
	/**
	 * 保存并修改
	 * @param odmList
	 * @throws Exception
	 */
	public void upAndSaveOdm(List<ODMInfoBean> odmList) throws Exception;
	
	
	/**
	 * 根据语句得到查询数据
	 * @param sql
	 * @return
	 */
	public List<Map<String, String>> getJdbcList(String sql);
	
	
	/**
	 * 批量更新端子
	 * @param list
	 */
	public void batchUpdatePoint(List<PointInfoBean> list);
	
	
	/**
	 * 根据对象得到唯一对象
	 * @param equt
	 * @return
	 */
	public EqutInfoBean getEqutObj(EqutInfoBean equt);
	
	/**
	 * 得到端口具体数据
	 * @param point
	 * @return
	 */
	public PointInfoBean getPointObj(PointInfoBean point);
	
	
	/**
	 * 
	 * @param point
	 * @return
	 */
	public List<PointInfoBean> getNullPoint(PointInfoBean point);
	
	
	/**
	 * 删除端子业务
	 * @param point
	 */
	public void delPointBusiness(PointInfoBean point);
	
	/**
	 * 
	 * @param odmName
	 * @return
	 */
	public String setOdmCode(String odmName);
	
	
	/**
	 * 得到Odm的列表
	 * @param obj
	 * @return
	 */
	public List<OdmFiberInfo> getOdmFiber(OdmFiberInfo obj);
	
	
	/**
	 * 增加
	 * @param obj
	 * @return
	 */
	public int insertOdmFiber(OdmFiberInfo obj);
	
	
	/**
	 * 删除业务
	 * @param obj
	 */
	public void delOdmFiber(OdmFiberInfo obj);
	
	
	/**
	 * 批量落架
	 * @param obj
	 * @return
	 */
	public String batchOdmFiber(OdmFiberInfo obj,CableInfoBean cable);
	
}
