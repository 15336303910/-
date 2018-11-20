package interfaces.pdainterface.common.service.impl;

import interfaces.pdainterface.common.pojo.CorrectBean;
import interfaces.pdainterface.common.pojo.CorrectResBean;
import interfaces.pdainterface.common.pojo.EqutPointObj;
import interfaces.pdainterface.common.pojo.IntergradeBean;
import interfaces.pdainterface.common.pojo.OpticabLay;
import interfaces.pdainterface.common.pojo.QualitorBean;

import java.util.List;

public interface ICommonService {

	
	/**
	 * 获取综合业务区
	 * @param obj
	 * @return
	 */
	public List<IntergradeBean> getIntergradeList(IntergradeBean obj);
	
	
	
	/**
	 * 得到列表数据
	 * @param obj
	 * @return
	 */
	public List<EqutPointObj> getDeviceList(EqutPointObj obj);
	
	
	/**
	 * 得到所有的端口数据
	 * @param obj
	 * @return
	 */
	public List<EqutPointObj> getEpointList(EqutPointObj obj);
	
	
	/**
	 * 查询敷设
	 * @param obj
	 * @return
	 */
	public List<OpticabLay> getOpticabLayList(OpticabLay obj);
	
	
	/**
	 * 查询敷设
	 * @param obj
	 * @return
	 */
	public List<OpticabLay> getOpticabLayByTube(OpticabLay obj);
	/**
	 * 删除操作
	 * @param obj
	 * @return
	 */
	public boolean delOpticabLay(OpticabLay obj);
	
	
	/**
	 * 批量增加数据
	 * @param list
	 */
	public void beathAddOpticalLay(final List<OpticabLay> list);
	
	
	/**
	 * 得到一线维护人列表
	 * @param object
	 * @return
	 */
	public List<QualitorBean> getQualitorList(QualitorBean object);
	
	/**
	 * 派发勘误工单
	 * @param obj
	 * @return
	 */
	public String billCorrent(CorrectBean obj);
	
	
	/**
	 * 根据资源id和资源类型
	 * 得到资源的二维码串
	 * @param resId
	 * @param resType
	 * @return
	 */
	public String getScanCode(String resId,String resType);
	
	
	/**
	 * 根据resnum得到
	 * 资源的实例id
	 * @param resNum
	 * @param resType
	 * @return
	 */
	public String getResId(String resNum,String resType);
	
	
	/**
	 * 根据工单id得到
	 * 资源信息列表
	 * @param taskId
	 * @return
	 */
	public List<CorrectResBean> getResByTask(String taskId);
	
	/**
	 * 得到工单列表
	 * @param obj
	 * @return
	 */
	public List<CorrectBean> getCorrectList(CorrectBean obj);
	
	
	/**
	 * 得到待办接口
	 * @param inParam
	 * @return
	 */
	public String getWaitedTaskStr(String empid,String flowNo,String processInstname,int start);
	
	/**
	 * 得到待办接口
	 * @param inParam
	 * @return
	 */
	public String getClaimedTask(String empid,int start,int length);
	
	
	/**
	 * 得到工单详情页面
	 * @param flowId
	 * @param activeId
	 * @return
	 */
	public String getTaskInfo(String flowId,String activeId);
	
	
	/**
	 * 得到历史处理信息
	 * @param flowId
	 * @param activeId
	 * @return
	 */
	public String getTaskHisInfo(String flowId);
	
	
	/**
	 * 得到工单处理页面
	 * @param inParam
	 * @return
	 */
	public String getTaskDealInfo(String inParam);
	
	
	/**
	 * 提交处理信息页面
	 * @param inParam
	 * @return
	 */
	public String dealTaskInfo(String inParam,String flowId);
	
	
	/**
	 * 获取分派人列表
	 * 三级关联
	 * @param inParam
	 * @return
	 */
	public String getAssignDealer(String inParam);
	
}
