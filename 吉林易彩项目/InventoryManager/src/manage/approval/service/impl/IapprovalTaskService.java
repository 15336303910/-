package manage.approval.service.impl;
import interfaces.irmsInterface.interfaces.outLine.pojo.IrmsPoint;
import interfaces.pdainterface.lineSystem.pojo.LinePointInfo;
import interfaces.pdainterface.lineSystem.pojo.LineSegmentInfo;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manage.approval.pojo.ApprovalListPojo;
import manage.approval.pojo.ApprovalMapPojo;
import manage.approval.pojo.ApprovalReportPojo;
import manage.approval.pojo.ApprovalResPojo;
import manage.approval.pojo.ApprovalResRejectPojo;
import manage.approval.pojo.ApprovalTaskPojo;
import manage.buriedPart.pojo.BuriedPartObj;
import manage.equt.pojo.EqutInfoBean;
import manage.equt.pojo.ODMInfoBean;
import manage.generator.pojo.GeneratorInfoBean;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.poleline.pojo.PolelineSegmentInfoBean;

import org.springframework.jdbc.core.JdbcTemplate;
public interface IapprovalTaskService{
	public JdbcTemplate getTemplate();
	public ApprovalTaskPojo getTasks(ApprovalTaskPojo approvalTaskPojo,String userId);
	
	/**
	 * 根据主键得到
	 * 实体对象 
	 * @param id
	 * @return
	 */
	public ApprovalTaskPojo getApprovalObj(String id );
	
	
	/**
	 * 根据实体对象
	 * 得到相应的列表
	 * @param object
	 * @return
	 */
	public List<ApprovalTaskPojo> getApprovalTaskList(ApprovalTaskPojo object);
	
	/**
	 * 新增审批工单
	 * @param object
	 * @return
	 */
	public int addApproval(ApprovalTaskPojo object);
	
	
	/**
	 * 給賬號派發短信
	 * @param userName
	 * @param smsContent
	 */
	public void sendSmsByUser(String userName,String smsContent);
	
	/**
	 * 得到地图数据
	 * @param obj
	 * @return
	 */
	public String getMapStr(ApprovalTaskPojo obj,String type);
	
	
	/**
	 * 得到采集的数据
	 * @param obj
	 * @return
	 */
	public String getCollectStr(ApprovalTaskPojo obj);
	
	/**
	 * 得到统计报表
	 * @param approvalTaskPojo
	 * @return
	 */
	public List<Map<String, Object>> getReportTask(ApprovalTaskPojo approvalTaskPojo);
	
	
	/**
	 * 得到日报统计数据
	 * @return
	 */
	public List<ApprovalReportPojo> getApprovalReport();
	
	
	/**
	 * 更新下光交箱的所属第三方公司
	 * @param belongCmp
	 * @param eids
	 */
	public void batchEqutUpdate(String belongCmp,String eids);
	
	/**
	 * 得到是否存在录入数据
	 * @param taskId
	 * @return
	 */
	public List<Map<String, Object>> getCollectList(String taskId);
	
	/**
	 * 导出数据
	 * @param obj
	 * @param request
	 * @param response
	 */
	public void expTaskReport(ApprovalTaskPojo obj,HttpServletRequest request,HttpServletResponse response);
	
	
	/**
	 * 得到产权性质报表
	 * @param approvalTaskPojo
	 * @return
	 */
	public List<Map<String, Object>> getPropertyReport(ApprovalTaskPojo approvalTaskPojo);
	
	/**
	 * 得到排重的工单操作
	 * @param title
	 * @return
	 */
	public List<Map<String, Object>> getTaskList(String title);
	
	
	/**
	 * 导出每天日报
	 * @param obj
	 * @param request
	 * @param response
	 */
	public void extApprovalReport(ApprovalTaskPojo obj,HttpServletRequest request,HttpServletResponse response) ;
	
	/**
	 * 得到网元数据
	 * @param approvalTaskPojo
	 * @return
	 */
	public List<Map<String, Object>> getNetTask(ApprovalTaskPojo approvalTaskPojo);
	/**
	 * 批量增加驳回资源
	 * @param list
	 */
	public void batchRejectResList(final List<ApprovalResRejectPojo> list);
	/**
	 * 根据map得到
	 * 相应的对象
	 * @param map
	 * @return
	 */
	public IrmsPoint getIrmsPoint(Map<String, Object> map);
	
	
	/**
	 * 得到是否有签单记录
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getGrapApproval(String userId);
	
	
	/**
	 * 得到杆路信息
	 * @param poleId
	 * @return
	 */
	public List<Map<String, Object>> getPlineByPole(String poleId);
	
	/**
	 * 得到资源
	 * 的列表数据
	 * @return
	 */
	public List<Map<String, Object>> getResGrid(ApprovalTaskPojo obj,String resType);
	
	/**
	 * 得到资源的数据
	 * @param id
	 * @param resType
	 * @return
	 */
	public int getResCount(ApprovalTaskPojo obj,String resType);
	
	
	/**
	 * 执行删除任务
	 * @param taskid
	 */
	public void delIRMStask(String taskid,String type);
	
	/**
	 * 修改工单状态
	 * @param obj
	 */
	public void upApprovalObj(ApprovalTaskPojo obj);
	
	
	/**
	 * 根据点列表得
	 * 到杆路段
	 * @param list
	 * @return
	 */
	public List<PolelineSegmentInfoBean> getPoleSegBypole(List<IrmsPoint> list);
	
	
	/**
	 * 根据管井得到段数据
	 * @param wellid
	 * @return
	 */
	public List<Map<String, Object>> getPipeByWell(String wellid);
	
	/**
	 * 得到划定区域内的点
	 * @param pointList
	 * @return
	 */
	public String getPolonObj(List<Map<String, Object>> pointList,String type) ;
	
	
	/**
	 * 得到列表信息
	 * @param approvalTaskPojo
	 * @return
	 */
	public List<ApprovalTaskPojo> getAppAuditList(ApprovalTaskPojo approvalTaskPojo);
	
	
	/**
	 * 导出工单明细信息
	 * @param obj
	 * @param request
	 * @param response
	 */
	public void extTaskAuditList(ApprovalTaskPojo obj,HttpServletRequest request,HttpServletResponse response);
	
	
	
	/**
	 * 导出产权性质
	 * @param obj
	 * @param request
	 * @param response
	 */
	public void expPropertyReport(ApprovalTaskPojo obj, HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 得到工单的总数
	 * @param approvalTaskPojo
	 * @return
	 */
	public int getAppAuditCount(ApprovalTaskPojo approvalTaskPojo);
	
	/**
	 * 得到核查站点数据
	 * @param obj
	 * @return
	 */
	public List<Map<String, Object>> getHisSite(ApprovalTaskPojo obj);
	
	/**
	 * 发送删除区域
	 * @param id
	 * @param pointList
	 * @param objId
	 * @return
	 */
	public String sendDelZone(String id,List<Map<String, Object>> pointList,String pointId,String lineId) ;
	
	
	
	/**
	 * 得到直埋段数据
	 * @param list
	 * @return
	 */
	public List<BuriedPartObj> getBuriedPartByStone(List<IrmsPoint> list);
	
	
	/**
	 * 得到端子端口的返回语句
	 * @param parentId
	 * @param resType
	 * @return
	 */
	public String getPointJson(String parentId,String resType);
	
	/**
	 * 得到管井数据
	 * @param list
	 * @return
	 */
	public List<PipeSegmentInfoBean> getPipeSegBywell(List<IrmsPoint> list);
	
	/**
	 * 得到资源map
	 * @param id
	 * @param resType
	 * @return
	 */
	public Map<String, Object> getResMap(String id,String resType);
	
	
	/**
	 * 得到单个资源点
	 * @param id
	 * @param type
	 * @return
	 */
	public Object getResObject(String id, String type);
	
	
	/**
	 * 得到资源的图片
	 * @param id
	 * @param resType
	 * @return
	 */
	public List<Map<String, Object>> getResImag(String id,String resType);
	
	
	/**
	 * 得到站内资源的
	 * 级联树
	 * @param parentId
	 * @param resType
	 * @return
	 */
	public String getResStr(String parentId,String resType);
	
	
	/**
	 * 根据站点得到机房
	 * @param gener
	 * @return
	 */
	public List<GeneratorInfoBean> getGenerInfo(GeneratorInfoBean gener);
	
	
	/**
	 * 得到机房下
	 * @param equt
	 * @return
	 */
	public List<EqutInfoBean> getEqutinfo(EqutInfoBean equt);
	
	
	/**
	 * 根据机架得到
	 * 下面的ODM模块
	 * @param equt
	 * @return
	 */
	public List<ODMInfoBean> getOdmInfo(ODMInfoBean odm);
	
	
	/**
	 * 批量增加
	 * @param list
	 */
	public void batchAddApprovalList(final List<ApprovalListPojo> list);
	
	/**
	 * 得到相应
	 * 的资源点信息
	 * @param info
	 * @return
	 */
	public String getPointStr(LinePointInfo info );
	
	
	/**
	 * 批量添加地
	 * 图填写的点数据
	 * @param list
	 */
	public void batchAddPoint(final List<ApprovalMapPojo> list);
	
	
	/**
	 * 返回画的点数据
	 * @param obj
	 * @return
	 */
	public List<Map<String, Object>> getResMapArea(ApprovalTaskPojo obj);
	
	/**
	 * 保存资源信息
	 * @param list
	 */
	public void batchAddRes(final List<ApprovalResPojo> list);
	
	/**
	 * 得到工单及点数据
	 * @param obj
	 * @return
	 */
	public List<Map<String, Object>> getHisMap(ApprovalTaskPojo obj);
	
	
	/**
	 * 返回加载地图的数据
	 * @param locatArea
	 * @return
	 */
	public Map<String, String> locatMap(String locatArea);
	
	/**
	 * 派发删除实例
	 * @param id
	 */
	public void sendDelZone(String id,List<Map<String, Object>> pointList);
	
	
	/**
	 * 根据语句判断是
	 * 否提交过相同数据
	 * @param object
	 * @return
	 */
	public List<Map<String, Object>> getApprovalList(ApprovalTaskPojo object);
	
	
	/**
	 * 得到资源站点的拓扑图
	 * @param siteId
	 * @return
	 */
	public String getSiteTopo(String siteId);
	
	
	/**
	 * 得到资源点数据
	 * @param info
	 * @return
	 */
	public List<LineSegmentInfo> getPointList(LinePointInfo info);
	
	
	/**
	 * 根据关键字查询站点信息
	 * @param locatArea
	 * @return
	 */
	public List<LineSegmentInfo> locatSite(String locatArea);
	
	
	/**
	 * 根据工单ID得到站点信息
	 * @param taskId
	 * @return
	 */
	public String getTaskSite(String taskId);
	
	
	/**
	 * 获取驳回的资源列表
	 * @param obj
	 * @return
	 */
	public List<ApprovalResRejectPojo> getResReject(ApprovalResRejectPojo obj);
	
	
	/**
	 * 执行更新语句
	 * @param id
	 * @param pointList
	 */
	public void upApprovalTask(String id ,List<Map<String, Object>> pointList);
	
	/**
	 * 增加驳回资源
	 * @param obj
	 * @return
	 */
	public int addResReject(ApprovalResRejectPojo obj);
	
	
	/**
	 * 根据组ID得到组
	 * 下面的用户信息
	 * @param groupId
	 * @return
	 */
	public List<Map<String, Object>> getGroupUser(String groupId);
	
	
	/**
	 * 得到光交箱数据
	 * @param eid
	 * @return
	 */
	public String getOpticalTopo(String eid);
	
	
	/**
	 * 根据用户ID得到
	 * 相应的审批权限
	 * @param userId
	 * @return
	 */
	public boolean getAuditRoll(String userId);
	
	
	/**
	 * 得到驳回的资源
	 * @param taskId
	 * @return
	 */
	public String getReject(String taskId);
	
	
	/**
	 * 批量保存工单采集资源数据
	 * @param list
	 * @param taskId
	 */
	public void batchResCollect(final List<Map<String, Object>> list,final String taskId );
	
	/**
	 * 执行删除驳回记录
	 * @param taskId
	 * @param resType
	 * @param resId
	 */
	public void delRejectRes(String user,String resType,String resId);
	
	
	/**
	 * 根据资源类型得到
	 * 相应管线及引上数据
	 * 
	 * @param resType
	 * @param resId
	 * @return
	 */
	public String getRadiate(String resType,String resId);
	
	
	/**
	 * 得到查詢基站的信息
	 * @param siteName
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Map<String, Object>> getSeachSite(String siteName,Integer start,Integer limit);
	
	
	
	/**
	 * 根據sql語句得到
	 * 相應list列表
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getListBySql(String sql );
	
	
	/**
	 * 根據機房和采集
	 * 人得到相應的幾架信息
	 * @param gid
	 * @param parties
	 * @return
	 */
	public List<Map<String, Object>> getEqut(String gid,String parties);
	
	/**
	 * 得到提醒消息
	 * @param userName
	 * @return
	 */
	public Map<String, String> getWarnMsg(String userName);
	
	/**
	 * 得到水文数据
	 * @return
	 */
	public List<Map<String, Object>> getHydrology();
	
}
