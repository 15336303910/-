package manage.approval.service;
import interfaces.irmsInterface.interfaces.outLine.pojo.IrmsPoint;
import interfaces.irmsInterface.utils.InterfaceAddr;
import interfaces.irmsInterface.utils.RequestUtil;
import interfaces.pdainterface.lineSystem.pojo.LinePointInfo;
import interfaces.pdainterface.lineSystem.pojo.LineSegmentInfo;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.LineListener;

import org.apache.poi.hmef.attribute.MAPIAttribute;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import manage.approval.pojo.ApprovalCollectPojo;
import manage.approval.pojo.ApprovalListPojo;
import manage.approval.pojo.ApprovalMapPojo;
import manage.approval.pojo.ApprovalReportPojo;
import manage.approval.pojo.ApprovalResPojo;
import manage.approval.pojo.ApprovalResRejectPojo;
import manage.approval.pojo.ApprovalTaskPojo;
import manage.approval.service.impl.IapprovalTaskService;
import manage.buriedPart.pojo.BuriedPartObj;
import manage.device.pojo.CardInfoBean;
import manage.device.pojo.DeviceInfoBean;
import manage.device.pojo.PointBean;
import manage.equt.pojo.EqutInfoBean;
import manage.equt.pojo.ODMInfoBean;
import manage.generator.pojo.GeneratorInfoBean;
import manage.generator.pojo.StationBaseInfoBean;
import manage.leadup.pojo.LeadupPojo;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.pipe.pojo.WellInfoBean;
import manage.point.pojo.PointInfoBean;
import manage.poleline.pojo.PoleInfoBean;
import manage.poleline.pojo.PolelineSegmentInfoBean;
import manage.stone.pojo.StoneInfoBean;
import net.sf.ezmorph.bean.MorphDynaBean;
import base.database.DataBase;
import base.util.ExcelUtil;
import base.util.JsonUtil;
import base.util.ResUtil;
import base.util.SmsUtil;
import base.util.TextUtil;
import base.util.functions;
public class ApprovalTaskService extends DataBase implements IapprovalTaskService{
	
	private JdbcTemplate jdbcTemplate;	
	private JdbcTemplate irmsjdbcTemplate;//访问综资数据
	
	/**
	 * 得到工单列表
	 */
	@Override
	public ApprovalTaskPojo getTasks(ApprovalTaskPojo approvalTaskPojo,String userId) {
		if(TextUtil.isNotNull(approvalTaskPojo.getCounty())){
			approvalTaskPojo.setCounty(this.getAreaStr(approvalTaskPojo.getCounty()));
		}
		boolean flag = this.getAuditRoll(userId);
		if(flag) {
			approvalTaskPojo.setCounty(null);
		}
		List<ApprovalTaskPojo> taskItems = getObjects("approval.getTaskGrid",approvalTaskPojo);
		for(ApprovalTaskPojo obj : taskItems) {
			if(obj.getResType().contains("well,pole,stone")) {
				obj.setResType("外线");
			}
			if(obj.getResType().contains("equt")) {
				obj.setResType("光交箱");
			}
		}
		int total = getCount("approval.getTaskCount",approvalTaskPojo);
		ApprovalTaskPojo thisObject = new ApprovalTaskPojo();
		thisObject.setItems(taskItems);
		thisObject.setTotal(Integer.valueOf(total));
		return thisObject;
	}
	
	/**
	 * 得到列表信息
	 * @param approvalTaskPojo
	 * @return
	 */
	public List<ApprovalTaskPojo> getAppAuditList(ApprovalTaskPojo approvalTaskPojo){
		List<ApprovalTaskPojo> list = getObjects("approval.getTaskList",approvalTaskPojo);
		return list;
	}
	
	/**
	 * 得到工单的总数
	 * @param approvalTaskPojo
	 * @return
	 */
	public int getAppAuditCount(ApprovalTaskPojo approvalTaskPojo) {
		int num = getCount("approval.getTaskListCount", approvalTaskPojo);
		return num;
	}
	
	
	/**
	 * 得到产权性质报表
	 * @param approvalTaskPojo
	 * @return
	 */
	public List<Map<String, Object>> getPropertyReport(ApprovalTaskPojo approvalTaskPojo){
		String sql= "select "
				+ " taskSum.belongCmp,"
				+ " MAX(CASE taskSum.shareType WHEN '自建' THEN FORMAT(taskSum.segLength/1000,2) ELSE 0 END ) 'selfBuild',"
				+ " MAX(CASE taskSum.shareType WHEN '自建' THEN FORMAT(taskSum.passLength/1000,2) ELSE 0 END ) 'pselfBuild',"
				+ " MAX(CASE taskSum.shareType WHEN '合建' THEN FORMAT(taskSum.segLength/1000,2) ELSE 0 END ) 'togetherBuild',"
				+ " MAX(CASE taskSum.shareType WHEN '合建' THEN FORMAT(taskSum.passLength/1000,2) ELSE 0 END ) 'ptogetherBuild',"
				+ " MAX(CASE taskSum.shareType WHEN '共建' THEN FORMAT(taskSum.segLength/1000,2) ELSE 0 END ) 'commonBuild',"
				+ " MAX(CASE taskSum.shareType WHEN '共建' THEN FORMAT(taskSum.passLength/1000,2) ELSE 0 END ) 'pcommonBuild',"
				+ " MAX(CASE taskSum.shareType WHEN '租用' THEN FORMAT(taskSum.segLength/1000,2) ELSE 0 END ) 'leaseBuild',"
				+ " MAX(CASE taskSum.shareType WHEN '租用' THEN FORMAT(taskSum.passLength/1000,2) ELSE 0 END ) 'pleaseBuild',"
				+ " MAX(CASE taskSum.shareType WHEN '购买' THEN FORMAT(taskSum.segLength/1000,2) ELSE 0 END ) 'buyBuild',"
				+ " MAX(CASE taskSum.shareType WHEN '购买' THEN FORMAT(taskSum.passLength/1000,2) ELSE 0 END ) 'pbuyBuild',"
				+ " MAX(CASE taskSum.shareType WHEN '置换' THEN FORMAT(taskSum.segLength/1000,2) ELSE 0 END ) 'replaceBuild',"
				+ " MAX(CASE taskSum.shareType WHEN '置换' THEN FORMAT(taskSum.passLength/1000,2) ELSE 0 END ) 'preplaceBuild',"
				+ " MAX(CASE taskSum.shareType WHEN '其他' THEN FORMAT(taskSum.segLength/1000,2) ELSE 0 END ) 'otherBuild',"
				+ " MAX(CASE taskSum.shareType WHEN '其他' THEN FORMAT(taskSum.passLength/1000,2) ELSE 0 END ) 'potherBuild'"
				+ " from ("
				+ " select al.belongCmp,al.shareType,al.segLength as segLength,pass.segLength as passLength from ("
				+ " select collRes.belongCmp, case collRes.shareType"
				+ " when 0 then '自建'"
				+ " when 1 then '合建'"
				+ " when 2 then '共建'"
				+ " when 3 then '租用'"
				+ " when 4 then '购买'"
				+ " when 5 then '置换'"
				+ " when 6 then '其他'"
				+ " else '自建' end as shareType ,CONVERT(sum(segLength),char) as segLength"
				+ " from pipCollectView collRes"
				+ " where belongCmp is not null and collRes.shareType is not null"
				+ " group by collRes.belongCmp,collRes.shareType"
				+ " ) al,"
				+ " (select collRes.belongCmp, case collRes.shareType"
				+ " when 0 then '自建'"
				+ " when 1 then '合建'"
				+ " when 2 then '共建'"
				+ " when 3 then '租用'"
				+ " when 4 then '购买'"
				+ " when 5 then '置换'"
				+ " when 6 then '其他'"
				+ " else '自建' end as shareType ,CONVERT(sum(segLength),char) as segLength"
				+ " from pipCollectView collRes"
				+ " where belongCmp is not null and collRes.shareType is not null and collRes.resNum is not null"
				+ " group by collRes.belongCmp,collRes.shareType) pass"
				+ " where al.belongCmp = pass.belongCmp and al.shareType = pass.shareType )taskSum"
				+ " group by taskSum.belongCmp";
		
		return this.jdbcTemplate.queryForList(sql);
	}
	/**
	 * 得到统计报表
	 * @param approvalTaskPojo
	 * @return
	 */
	public List<Map<String, Object>> getReportTask(ApprovalTaskPojo approvalTaskPojo) {
		String sql = "select al.belongCmp,al.num as totalTask,one.num as sendTask,two.num as beingTask,"
				+ " three.num as checkTask,four.num as endTask,five.num as rejectTask,"
				+ " FORMAT((leg.segLength/1000),2) as totalLength,FORMAT((pass.segLength/1000),2) as checkLength,"
				+ " eqt.equtNum as alEqut,epass.equtNum as passEqut"
				+ " from reportall al"
				+ " left join reportone one "
				+ " on al.belongCmp = one.belongCmp"
				+ " left join reporttwo two"
				+ " on al.belongCmp = two.belongCmp "
				+ " left join reportthree three"
				+ " on al.belongCmp = three.belongCmp"
				+ " left join reportfour four"
				+ " on al.belongCmp= four.belongCmp"
				+ " left join reportfive five"
				+ " on al.belongCmp = five.belongCmp"
				+ " left join reportleg leg"
				+ " on al.belongCmp = leg.belongCmp "
				+ " left join reportPass pass"
				+ " on al.belongCmp = pass.belongCmp"
				+ " left join equtView eqt"
				+ " on al.belongCmp = eqt.belongCmp"
				+ " left join equtPassView epass"
				+ " on al.belongCmp = epass.belongCmp"
				+ "";
		return this.jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 得到网元数据
	 * @param approvalTaskPojo
	 * @return
	 */
	public List<Map<String, Object>> getNetTask(ApprovalTaskPojo approvalTaskPojo){
		String sql = "select a.compName as belongCmp,sum(allNum) as totalTask,sum(oneNum) as sendTask,sum(twoNum) as beingTask,"
				+ " sum(threeNum) as checkTask,sum(fourNum) as endTask,sum(fiveNum) as rejectTask,"
				+ " FORMAT((sum(legNum)/1000),2) as totalLength,FORMAT((sum(passNum)/1000),2) as checkLength"
				+ "  from ("
				+ " select (select left(groupName,2) from maintaingroup where id=al.groupId) as compName ,"
				+ " al.num as allNum,one.num as oneNum,two.num as twoNum,three.num as threeNum,four.num as fourNum,five.num as fiveNum,"
				+ " leg.segLength as legNum,pass.segLength as passNum"
				+ " from reportCompAll al"
				+ " left join  reportCompOne one"
				+ " on al.groupId = one.groupId "
				+ " left join reportCompTwo two"
				+ " on al.groupId = two.groupId"
				+ " left join reportCompThree three"
				+ " on al.groupId = three.groupId"
				+ " left join reportCompFour four "
				+ " on al.groupId = four.groupId"
				+ " left join reportCompFive  five"
				+ " on al.groupId = five.groupId"
				+ " left join reportCompLeg leg"
				+ " on al.groupId = leg.groupId"
				+ " left join reportCompPass pass"
				+ "  on al.groupId = pass.groupId)"
				+ " a group by a.compName";
		return this.jdbcTemplate.queryForList(sql);
	}
	
	
	/**
	 * 导出每天日报
	 * @param obj
	 * @param request
	 * @param response
	 */
	public void extApprovalReport(ApprovalTaskPojo obj,HttpServletRequest request,HttpServletResponse response) {
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String caption = sdf.format(date)+"日报数据";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet=workbook.createSheet();
			workbook.setSheetName(0, "日报数据");
			sheet.setDefaultColumnWidth(10);
			
			HSSFRow row=sheet.createRow(0);
			HSSFCell cell=row.createCell(0);
			cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
			int col=0;
			String[] titles = {"所属分公司","工单总数","已派","处理中","待审核(处理完成)","归档",
					"驳回","管道杆路总长度(公里)","自有管道杆路总长度(公里)","租用管道杆路总长度(公里)",
					"采集管道/杆路总长度(公里)","审核通过管道/杆路长度(公里)","审核通过自有管道/杆路长度(公里)",
					"审核通过租用管道/杆路长度(公里)","审核通过采集管道/杆路完成率","光交接箱总数量",
					"已交维光交接箱数量","未交维光交接箱数量","采集光交箱数量","审核通过光交箱数量"};
			for(String title : titles) {
				cell=row.createCell(col++);
			    cell.setCellValue(title);
			    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
			}
			List<ApprovalReportPojo> list = this.getApprovalReport();
			for(int i=0;i<list.size();i++) {
				ApprovalReportPojo bean = list.get(i);
				HSSFRow rows=sheet.createRow(i+1);
				ExcelUtil.createCell(rows, 0, TextUtil.isNull(bean.getBelongCmp()) ? " " : bean.getBelongCmp(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 1, TextUtil.isNull(bean.getTotalTask()) ? " " : bean.getTotalTask(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 2, TextUtil.isNull(bean.getSendTask()) ? " " : bean.getSendTask(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 3, TextUtil.isNull(bean.getBeingTask()) ? " " : bean.getBeingTask(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 4, TextUtil.isNull(bean.getCheckTask()) ? " " : bean.getCheckTask(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 5, TextUtil.isNull(bean.getEndTask()) ? " " : bean.getEndTask(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 6, TextUtil.isNull(bean.getRejectTask()) ? " " : bean.getRejectTask(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 7, TextUtil.isNull(bean.getAllTotalLength()) ? " " : bean.getAllTotalLength(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 8, TextUtil.isNull(bean.getSelfTotalLength()) ? " " : bean.getSelfTotalLength(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 9, TextUtil.isNull(bean.getRentTotalLength()) ? " " : bean.getRentTotalLength(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 10, TextUtil.isNull(bean.getCollectLength()) ? " " : bean.getCollectLength(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 11, TextUtil.isNull(bean.getAllPassLength()) ? " " : bean.getAllPassLength(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 12, TextUtil.isNull(bean.getSelfPassLength()) ? " " : bean.getSelfPassLength(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 13, TextUtil.isNull(bean.getRentPassLength()) ? " " : bean.getRentPassLength(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 14, TextUtil.isNull(bean.getPassAllRate()) ? " " : bean.getPassAllRate(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 15, TextUtil.isNull(bean.getAllEqutNum()) ? " " : bean.getAllEqutNum(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 16, TextUtil.isNull(bean.getAssertEqutNum()) ? " " : bean.getAssertEqutNum(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 17, TextUtil.isNull(bean.getBuildEqutNum()) ? " " : bean.getBuildEqutNum(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 18, TextUtil.isNull(bean.getCollectEqutNum()) ? " " : bean.getCollectEqutNum(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 19, TextUtil.isNull(bean.getPassEqutNum()) ? " " : bean.getPassEqutNum(), ExcelUtil.getValueStyle(workbook));
			}
			
			ExcelUtil.downloadFile(caption, workbook, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出工单明细信息
	 * @param obj
	 * @param request
	 * @param response
	 */
	public void extTaskAuditList(ApprovalTaskPojo obj,HttpServletRequest request,HttpServletResponse response) {
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String caption = sdf.format(date)+"数据";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet=workbook.createSheet();
			workbook.setSheetName(0, "工单明细表");
			sheet.setDefaultColumnWidth(10);
			
			HSSFRow row=sheet.createRow(0);
			HSSFCell cell=row.createCell(0);
			cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
			int col=0;
			String[] titles = {"工单标题","区县","二级","创建时间","派发人","采集人",
					"处理时间","审核人","审核时间","工单状态","班组名称","采集长度","审核长度"};
			for(String title : titles) {
				cell=row.createCell(col++);
			    cell.setCellValue(title);
			    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
			}
			sheet.setColumnWidth(0, 70*256);
			List<ApprovalTaskPojo> list = this.getAppAuditList(obj);
			HSSFCellStyle style = ExcelUtil.getValueStyle(workbook);
			for(int i=0;i<list.size();i++) {
				ApprovalTaskPojo bean = list.get(i);
				HSSFRow rows=sheet.createRow(i+1);
				ExcelUtil.createCell(rows, 0, TextUtil.isNull(bean.getTaskTitle()) ? " " : bean.getTaskTitle(), style);
				ExcelUtil.createCell(rows, 1, TextUtil.isNull(bean.getCounty()) ? " " : bean.getCounty(), style);
				ExcelUtil.createCell(rows, 2, TextUtil.isNull(bean.getBelongCmp()) ? " " : bean.getBelongCmp(), style);
				ExcelUtil.createCell(rows, 3, TextUtil.isNull(bean.getCreateStr()) ? " " : bean.getCreateStr(), style);
				ExcelUtil.createCell(rows, 4, TextUtil.isNull(bean.getSender()) ? " " : bean.getSender(), style);
				ExcelUtil.createCell(rows, 5, TextUtil.isNull(bean.getApprovaler()) ? " " : bean.getApprovaler(), style);
				ExcelUtil.createCell(rows, 6, TextUtil.isNull(bean.getDealStr()) ? " " : bean.getDealStr(), style);
				ExcelUtil.createCell(rows, 7, TextUtil.isNull(bean.getAuditer()) ? " " : bean.getAuditer(), style);
				ExcelUtil.createCell(rows, 8, TextUtil.isNull(bean.getAuditStr()) ? " " : bean.getAuditStr(), style);
				ExcelUtil.createCell(rows, 9, TextUtil.isNull(bean.getTaskState()) ? " " : bean.getTaskState(), style);
				ExcelUtil.createCell(rows, 10, TextUtil.isNull(bean.getGroupName()) ? " " : bean.getGroupName(), style);
				ExcelUtil.createCell(rows, 11, TextUtil.isNull(bean.getTotalLength()) ? " " : bean.getTotalLength(), style);
				ExcelUtil.createCell(rows, 12, TextUtil.isNull(bean.getAuditLength()) ? " " : bean.getAuditLength(), style);
			}
			ExcelUtil.downloadFile(caption, workbook, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 导出产权性质
	 * @param obj
	 * @param request
	 * @param response
	 */
	public void expPropertyReport(ApprovalTaskPojo obj, HttpServletRequest request,HttpServletResponse response) {
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String caption = sdf.format(date)+"数据";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet=workbook.createSheet();
			workbook.setSheetName(0, "管线产权表");
			sheet.setDefaultColumnWidth(10);
			
			HSSFRow row=sheet.createRow(0);
			HSSFCell cell=row.createCell(0);
			cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
			int col=0;
			String[] titles = {"所属公司","自建合计","自建审批","合建合计","合建审批",
					"共建合计","共建审批","租用合计","租用审批","购买合计","购买审批","置换合计","置换审批","其他合计","其他审批"};
			for(String title : titles) {
				cell=row.createCell(col++);
			    cell.setCellValue(title);
			    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
			}
			List<Map<String, Object>> list = this.getPropertyReport(obj);
			for(int i=0;i<list.size();i++){
				Map<String, Object> map= list.get(i);
				HSSFRow rows=sheet.createRow(i+1);
				ExcelUtil.createCell(rows, 0, TextUtil.isNull(map.get("belongCmp")) ? " " : map.get("belongCmp").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 1, TextUtil.isNull(map.get("selfBuild")) ? " " : map.get("selfBuild").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 2, TextUtil.isNull(map.get("pselfBuild")) ? " " : map.get("pselfBuild").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 3, TextUtil.isNull(map.get("togetherBuild")) ? " " : map.get("togetherBuild").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 4, TextUtil.isNull(map.get("ptogetherBuild")) ? " " : map.get("ptogetherBuild").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 5, TextUtil.isNull(map.get("commonBuild")) ? " " : map.get("commonBuild").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 6, TextUtil.isNull(map.get("pcommonBuild")) ? " " : map.get("pcommonBuild").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 7, TextUtil.isNull(map.get("leaseBuild")) ? " " : map.get("leaseBuild").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 8, TextUtil.isNull(map.get("pleaseBuild")) ? " " : map.get("pleaseBuild").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 9, TextUtil.isNull(map.get("buyBuild")) ? " " : map.get("buyBuild").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 10, TextUtil.isNull(map.get("pbuyBuild")) ? " " : map.get("pbuyBuild").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 11, TextUtil.isNull(map.get("replaceBuild")) ? " " : map.get("replaceBuild").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 12, TextUtil.isNull(map.get("preplaceBuild")) ? " " : map.get("preplaceBuild").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 13, TextUtil.isNull(map.get("otherBuild")) ? " " : map.get("otherBuild").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 14, TextUtil.isNull(map.get("potherBuild")) ? " " : map.get("potherBuild").toString(), ExcelUtil.getValueStyle(workbook));

			}
			
			ExcelUtil.downloadFile(caption, workbook, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出数据
	 * @param obj
	 * @param request
	 * @param response
	 */
	public void expTaskReport(ApprovalTaskPojo obj,HttpServletRequest request,HttpServletResponse response) {
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String caption = sdf.format(date)+"数据";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet=workbook.createSheet();
			workbook.setSheetName(0, "全量统计表");
			sheet.setDefaultColumnWidth(10);
			
			HSSFSheet netSheet = workbook.createSheet();
			workbook.setSheetName(1, "网元中心统计表");
			netSheet.setDefaultColumnWidth(10);
			//全量统计表
			HSSFRow row=sheet.createRow(0);
			HSSFCell cell=row.createCell(0);
			cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
			int col=0;
			String[] titles = {"所属公司","工单总数","已派","处理中","待审核","归档","驳回","采集总长度","审核总长度","采集光交","审批光交"};
			for(String title : titles) {
				cell=row.createCell(col++);
			    cell.setCellValue(title);
			    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
			}
			List<Map<String, Object>> allList = this.getReportTask(obj);
			for(int i=0;i<allList.size();i++){
				Map<String, Object> map= allList.get(i);
				HSSFRow rows=sheet.createRow(i+1);
				ExcelUtil.createCell(rows, 0, TextUtil.isNull(map.get("belongCmp")) ? " " : map.get("belongCmp").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 1, TextUtil.isNull(map.get("totalTask")) ? " " : map.get("totalTask").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 2, TextUtil.isNull(map.get("sendTask")) ? " " : map.get("sendTask").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 3, TextUtil.isNull(map.get("beingTask")) ? " " : map.get("beingTask").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 4, TextUtil.isNull(map.get("checkTask")) ? " " : map.get("checkTask").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 5, TextUtil.isNull(map.get("endTask")) ? " " : map.get("endTask").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 6, TextUtil.isNull(map.get("rejectTask")) ? " " : map.get("rejectTask").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 7, TextUtil.isNull(map.get("totalLength")) ? " " : map.get("totalLength").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 8, TextUtil.isNull(map.get("checkLength")) ? " " : map.get("checkLength").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 9, TextUtil.isNull(map.get("alEqut")) ? " " : map.get("alEqut").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 10, TextUtil.isNull(map.get("passEqut")) ? " " : map.get("passEqut").toString(), ExcelUtil.getValueStyle(workbook));
			}
			//网元统计表
			HSSFRow netRow = netSheet.createRow(0);
			HSSFCell netCell = netRow.createCell(0);
			netCell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
			int netCol=0;
			for(String title : titles) {
				netCell=netRow.createCell(netCol++);
				netCell.setCellValue(title);
				netCell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
			}
			List<Map<String, Object>> netList = this.getNetTask(obj);
			
			for(int i=0;i<netList.size();i++){
				Map<String, Object> map= netList.get(i);
				HSSFRow rows=netSheet.createRow(i+1);
				ExcelUtil.createCell(rows, 0, TextUtil.isNull(map.get("belongCmp")) ? " " : map.get("belongCmp").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 1, TextUtil.isNull(map.get("totalTask")) ? " " : map.get("totalTask").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 2, TextUtil.isNull(map.get("sendTask")) ? " " : map.get("sendTask").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 3, TextUtil.isNull(map.get("beingTask")) ? " " : map.get("beingTask").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 4, TextUtil.isNull(map.get("checkTask")) ? " " : map.get("checkTask").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 5, TextUtil.isNull(map.get("endTask")) ? " " : map.get("endTask").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 6, TextUtil.isNull(map.get("rejectTask")) ? " " : map.get("rejectTask").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 7, TextUtil.isNull(map.get("totalLength")) ? " " : map.get("totalLength").toString(), ExcelUtil.getValueStyle(workbook));
				ExcelUtil.createCell(rows, 8, TextUtil.isNull(map.get("checkLength")) ? " " : map.get("checkLength").toString(), ExcelUtil.getValueStyle(workbook));
			}
			
			ExcelUtil.downloadFile(caption, workbook, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 根据实体对象
	 * 得到相应的列表
	 * @param object
	 * @return
	 */
	public List<ApprovalTaskPojo> getApprovalTaskList(ApprovalTaskPojo object){
		List<ApprovalTaskPojo> list = getObjects("approval.getTaskGrid",object);
		return list;
	}
	
	/**
	 * 得到日报统计数据
	 * @return
	 */
	public List<ApprovalReportPojo> getApprovalReport(){
		ApprovalReportPojo obj = new ApprovalReportPojo();
		ApprovalTaskPojo object = new ApprovalTaskPojo();
		List<ApprovalReportPojo>  list = getObjects("approval.getApprovalReport", obj);
		//得到工单的数据
		List<Map<String, Object>> taskList = this.getReportTask(object);
		//得到产权性质数据
		List<Map<String, Object>> proList = this.getPropertyReport(object);
		for(ApprovalReportPojo report : list) {
			//填充工单数据
			for(Map<String, Object> task : taskList) {
				if(report.getBelongCmp().substring(0, 2).equals((task.get("belongCmp")+"").substring(0, 2))) {
					report.setTotalTask(task.get("totalTask")+"");
					report.setSendTask(task.get("sendTask")+"");
					report.setBeingTask(task.get("beingTask")+"");
					report.setCheckTask(task.get("checkTask")+"");
					report.setEndTask(task.get("endTask")+"");
					report.setRejectTask(task.get("rejectTask")+"");
					report.setCollectLength(task.get("totalLength")+"");
					report.setAllPassLength(task.get("checkLength")+"");
					report.setCollectEqutNum(task.get("alEqut")+"");
					report.setPassEqutNum(task.get("passEqut")+"");
				}
			}
			//填充审核通过的自有管道和租用管道
			java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
			for(Map<String, Object> pro : proList) {
				if(report.getBelongCmp().substring(0, 2).equals((pro.get("belongCmp")+"").substring(0, 2))) {
					Double selfLength = Double.parseDouble((pro.get("pselfBuild")+"").replaceAll(",", ""))
										+Double.parseDouble((pro.get("ptogetherBuild")+"").replaceAll(",", ""))
										+Double.parseDouble((pro.get("pbuyBuild")+"").replaceAll(",", ""))
										+Double.parseDouble((pro.get("preplaceBuild")+"").replaceAll(",", ""))
										+Double.parseDouble((pro.get("pcommonBuild")+"").replaceAll(",", ""));
					/*Double leaseLenth = Double.parseDouble((pro.get("pleaseBuild")+"").replaceAll(",", ""))
									+Double.parseDouble((pro.get("potherBuild")+"").replaceAll(",", ""));*/
					Double checkLength = Double.parseDouble((report.getAllPassLength()).replaceAll(",", ""));
					/*if((checkLength - selfLength -leaseLenth) > 2.0) {
						leaseLenth = checkLength - selfLength;
					}*/
					Double leaseLenth = checkLength - selfLength;
					report.setSelfPassLength(df.format(selfLength)+"");
					report.setRentPassLength(df.format(leaseLenth)+"");
				}
			}
			if(TextUtil.isNotNull(report.getAllPassLength())) {
				Double rent = Double.parseDouble(report.getAllPassLength().replaceAll(",", "")) / Double.parseDouble(report.getAllTotalLength().replaceAll(",", ""));
				rent =Double.parseDouble(df.format(rent)) * 100;
				report.setPassAllRate(df.format(rent)+"%");
			}
		}
		return list;
	}
	
	
	/**
	 * 根据语句判断是
	 * 否提交过相同数据
	 * @param object
	 * @return
	 */
	public List<Map<String, Object>> getApprovalList(ApprovalTaskPojo object){
		String sql = "select id,taskTitle,createTime,sender from approval_task t where "
				+ " ((unix_timestamp(t.startTime) >= unix_timestamp('"+object.getStartTime()+" 00:00:00')"
				+ " AND unix_timestamp(t.startTime) <= unix_timestamp('"+object.getEndTime()+" 23:00:00')) OR "
				+ " (unix_timestamp(t.startTime) <= unix_timestamp('"+object.getStartTime()+" 00:00:00')"
				+ " AND unix_timestamp(t.endTime) >= unix_timestamp('"+object.getEndTime()+" 23:00:00')) OR"
				+ " (unix_timestamp(t.endTime) >= unix_timestamp('"+object.getStartTime()+" 00:00:00')"
				+ " AND unix_timestamp(t.endTime) <= unix_timestamp('"+object.getEndTime()+" 23:00:00')) )"
				+ "";
		if(TextUtil.isNotNull(object.getSendId())){
			sql +=" and t.sendId ='"+object.getSendId()+"'";
		}else{
			sql +=" and t.sender ='"+object.getSender()+"'";
		}
		if(TextUtil.isNotNull(object.getTaskTitle())){
			sql +=" and t.taskTitle ='"+object.getTaskTitle()+"'";
		}
		if(TextUtil.isNotNull(object.getExtendSql())){
			sql += " "+object.getExtendSql();
		}
		sql +=" and t.taskState !='驳回' ";
		return this.jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 根据主键得到
	 * 实体对象
	 * @param id
	 * @return
	 */
	public ApprovalTaskPojo getApprovalObj(String id ){
		ApprovalTaskPojo obj = new ApprovalTaskPojo();
		obj.setId(Integer.parseInt(id));
		obj = (ApprovalTaskPojo) this.getObject("approval.getTaskObj", obj);
		if(TextUtil.isNotNull(obj.getGroupId())) {
			String sql = "select group_concat(realname) as crewer"
					+ " from sys_user"
					+ " where groupId = '"+obj.getGroupId()+"'";
			List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
			if(TextUtil.isNotNull(list)) {
				Map<String, Object> map = list.get(0);
				String crewer = map.get("crewer")+"";
				obj.setCrewer(crewer);
			}
		}
		return obj;
	}
	
	
	/**
	 * 得到是否存在录入数据
	 * @param taskId
	 * @return
	 */
	public List<Map<String, Object>> getCollectList(String taskId){
		String sql = "select id,taskId,segId from approval_collect"
				+ " where taskId ='"+taskId+"'";
		return this.jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 得到是否有签单记录
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getGrapApproval(String userId){
		String sql = "select id,taskTitle,approvaler,approvalerId"
				+ " from approval_task where 1=1"
				+ " and taskState ='已领单'"
				+ " and approvalerId ='"+userId+"' and deleteFlag =0 ";
		return this.jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 給賬號派發短信
	 * @param userName
	 * @param smsContent
	 */
	public void sendSmsByUser(String userName,String smsContent) {
		String sql = "select phoneNumber as tel from sys_user"
				+ " where username ='"+userName+"'"
				+ " or realname ='"+userName+"'";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)) {
			Map<String, Object> map = list.get(0);
			SmsUtil.sendSms(map.get("tel")+"", smsContent);
		}
	}
	
	/**
	 * 根據sql語句得到
	 * 相應list列表
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getListBySql(String sql ){
		return this.jdbcTemplate.queryForList(sql);
	}
	
	
	/**
	 * 根據機房和采集
	 * 人得到相應的幾架信息
	 * @param gid
	 * @param parties
	 * @return
	 */
	public List<Map<String, Object>> getEqut(String gid,String parties){
		String sql = "select t.id,t.ENAME as eName,EID as eid,DATE_FORMAT(t.Mtime,'%Y-%m-%d %H:%i:%S') as createTime,"
				+ " DATE_FORMAT(t.lastUpdateDate,'%Y-%m-%d %H:%i:%S') as updateTime,"
				+ " t.parties,t.changjia ,"
				+ " case t.jijialeixing"
				+ " when 0 then 'ODF架'"
				+ " when 1 then '设备机架'"
				+ " when 2 then '综合机架'"
				+ " when 3 then 'DDF架'"
				+ " when 4 then 'MODF架'"
				+ " end as rackType,t.jijiahanghao,"
				+ " t.jijialiehao,t.equtLength,t.equtTall,"
				+ " t.equtWide,if(isnull(t.resNum),0,t.resNum) as resNum,t.del"
				+ " from job_equtinfo t where t.gid = '"+gid+"' and t.parties ='"+parties+"'";
		return this.jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 修改工单状态
	 * @param obj
	 */
	public void upApprovalObj(ApprovalTaskPojo obj){
		if(obj.getTaskState().equals("已领单")) {
			this.update("approval.updateTask", obj);
		}else if(obj.getTaskState().equals("归档") || obj.getTaskState().equals("驳回")){
			this.update("approval.gdTask", obj);
		}else if(obj.getTaskState().equals("已派发") && obj.getDeleteFlag().equals("1")){
			String sql  ="update approval_task set deleteFlag = '1' where id='"+obj.getId()+"'";
			this.jdbcTemplate.execute(sql);
		}else {
			this.update("approval.finishTask", obj);
		}
	}
	
	
	/**
	 * 根据点列表得
	 * 到杆路段
	 * @param list
	 * @return
	 */
	public List<PolelineSegmentInfoBean> getPoleSegBypole(List<IrmsPoint> list){
		String ids = "";
		for(IrmsPoint obj : list){
			ids +=obj.getImId()+",";
		}
		if(ids.endsWith(",")){
			ids = ids.substring(0,ids.length()-1);
		}
		return this.getObjects("pdapoleline.getPoleSegBypole", ids);
	}
	
	
	/**
	 * 得到直埋段数据
	 * @param list
	 * @return
	 */
	public List<BuriedPartObj> getBuriedPartByStone(List<IrmsPoint> list){
		String ids = "";
		for(IrmsPoint obj : list){
			ids +=obj.getImId()+",";
		}
		if(ids.endsWith(",")){
			ids = ids.substring(0,ids.length()-1);
		}
		return getObjects("buriedPart.getBuriedPartBysid", ids);
	}
	
	/**
	 * 得到管井数据
	 * @param list
	 * @return
	 */
	public List<PipeSegmentInfoBean> getPipeSegBywell(List<IrmsPoint> list){
		String ids = "";
		for(IrmsPoint obj : list){
			ids +=obj.getImId()+",";
		}
		if(ids.endsWith(",")){
			ids = ids.substring(0,ids.length()-1);
		}
		return getObjects("pdapipe.getpipeSegBywell", ids);
	}
	
	/**
	 * 根据站点得到机房
	 * @param gener
	 * @return
	 */
	public List<GeneratorInfoBean> getGenerInfo(GeneratorInfoBean gener){
		return this.getObjects("pdagenerator.getGenerator", gener);
	}
	
	
	/**
	 * 根据机架得到
	 * 下面的ODM模块
	 * @param equt
	 * @return
	 */
	public List<ODMInfoBean> getOdmInfo(ODMInfoBean odm){
		return this.getObjects("pdaequt.getAllODM", odm);
	}
	
	/**
	 * 得到机房下
	 * @param equt
	 * @return
	 */
	public List<EqutInfoBean> getEqutinfo(EqutInfoBean equt){
		return this.getObjects("pdaequt.getEqut", equt);
	}
	
	/**
	 * 得到光交项数据
	 * @param equt
	 * @return
	 */
	public EqutInfoBean getEqutObj(EqutInfoBean equt) {
		List<EqutInfoBean> list = this.getEqutinfo(equt);
		for(EqutInfoBean eobj : list) {
			if(eobj.getEid().equals(equt.getEid())) {
				equt = eobj;
			}
		}
		return equt;
	}
	
	/**
	 * 得到资源
	 * 的列表数据
	 * @return
	 */
	public List<Map<String, Object>> getResGrid(ApprovalTaskPojo obj,String resType){
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		String[] types = resType.split(",");
		for(String res : types){
			String sql =  this.getResSql(obj, res, "list");
			if(TextUtil.isNotNull(sql)){
				List<Map<String, Object>> subList = this.jdbcTemplate.queryForList(sql);
				list.addAll(subList);
			}
		}
		return list;
	}
	
	/**
	 * 批量增加驳回资源
	 * @param list
	 */
	public void batchRejectResList(final List<ApprovalResRejectPojo> list) {
		String sql = "insert into"
				+ " approval_resreject(resName,resId,resType,taskId,"
				+ " createTime,rejectStr,rejectUser)"
				+ " values(?,?,?,?,now(),?,?)";
		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
			@Override
			public int getBatchSize() {
				return list.size();
			}
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ApprovalResRejectPojo obj = list.get(i);
				ps.setString(1, obj.getResName());
				ps.setString(2, obj.getResId());
				ps.setString(3, obj.getResType());
				ps.setString(4, obj.getTaskId());
				ps.setString(5, obj.getRejectStr());
				ps.setString(6, obj.getRejectUser());
			}
			
		});
	}
	
	/**
	 * 更新下光交箱的所属第三方公司
	 * @param belongCmp
	 * @param eids
	 */
	public void batchEqutUpdate(String belongCmp,String eids) {
		if(eids.endsWith(",")) {
			eids = eids.substring(0, eids.length()-1);
		}
		String sql = " update job_equtinfo "
				+ " set thirdPartyMaintenanceOrg ='"+belongCmp+"'"
				+ " where EID in ("+eids+") and ETYPE =3 "
				+ "";
		this.jdbcTemplate.execute(sql);
	}
	
	/**
	 * 批量保存工单采集资源数据
	 * @param list
	 * @param taskId
	 */
	public void batchResCollect(final List<Map<String, Object>> list,final String taskId ) {
		String sql = "insert into approval_collect"
				+ " (taskId,segId,segName,segType,"
				+ " startId,startName,startLat,startLon,startState,startType,"
				+ " endId,endName,endLat,endLon,endState,endType)"
				+ " values("
				+ " ?,?,?,?,"
				+ " ?,?,?,?,?,?,"
				+ " ?,?,?,?,?,?"
				+ " )";
		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Map<String, Object> map = list.get(i);
				ps.setString(1, taskId);
				ps.setString(2, map.get("id")+"");
				ps.setString(3, map.get("name")+"");
				ps.setString(4, map.get("type")+"");
				MorphDynaBean start = (MorphDynaBean) map.get("start");
				ps.setString(5, start.get("id")+"");
				ps.setString(6, start.get("name")+"");
				ps.setString(7, start.get("lat")+"");
				ps.setString(8, start.get("lon")+"");
				ps.setString(9, "add");
				ps.setString(10, start.get("type")+"");
				if(map.get("end") != null) {
					MorphDynaBean end = (MorphDynaBean) map.get("end");
					ps.setString(11, end.get("id")+"");
					ps.setString(12, end.get("name")+"");
					ps.setString(13, end.get("lat")+"");
					ps.setString(14, end.get("lon")+"");
					ps.setString(15, "add");
					ps.setString(16, end.get("type")+"");
				}else {
					ps.setString(11, "");
					ps.setString(12, "");
					ps.setString(13, "");
					ps.setString(14, "");
					ps.setString(15, "");
					ps.setString(16, "");
				}
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
	}
	
	/**
	 * 批量增加
	 * @param list
	 */
	public void batchAddApprovalList(final List<ApprovalListPojo> list){
		String sql = "insert  into"
				+ " approval_list(type,taskId,motion,requestId)"
				+ " values (?,?,?,?)";
		
		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
			@Override
			public int getBatchSize() {
				return list.size();
			}
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ApprovalListPojo obj = list.get(i);
				ps.setString(1, obj.getType());
				ps.setString(2, obj.getTaskId());
				ps.setString(3, obj.getMotion());
				ps.setString(4, obj.getRequestId());
			}
			
		});
	}
	
	
	/**
	 * 得到资源的数据
	 * @param id
	 * @param resType
	 * @return
	 */
	public int getResCount(ApprovalTaskPojo obj,String resType){
		String sql = this.getResSql(obj, resType, "count");
		return this.jdbcTemplate.queryForInt(sql);
	}
	
	/**
	 * 根据map得到
	 * 相应的对象
	 * @param map
	 * @return
	 */
	public IrmsPoint getIrmsPoint(Map<String, Object> map){
		IrmsPoint obj = new IrmsPoint();
		obj.setImId(map.get("resCode")+"");
		obj.setLatitude(map.get("latitude")+"");
		obj.setLongitude(map.get("longitude")+"");
		obj.setResName(map.get("resName")+"");
		obj.setQualitor(map.get("qualitor")+"");
		obj.setMaintainor(map.get("maintainor")+"");
		obj.setArea(map.get("region")+"");
		obj.setType(map.get("resEnType")+"");
		obj.setResNum(map.get("resNum")+"");
		obj.setTableName(map.get("tableName")+"");
		obj.setIdCol(map.get("idCol")+"");
		obj = this.setPointArea(obj);
		return obj;
	}
	
	/**
	 * 得到地市区县id
	 * @param obj
	 * @return
	 */
	public IrmsPoint setPointArea(IrmsPoint obj){
		String sql = "";
		if(obj.getArea().contains("_")){
			String[] counts = obj.getArea().split("_");
			sql = "select resNum as countyId,resCity as cityId from rms_county"
					+ " where towerName ='"+counts[counts.length-1]+"'";
		}else{
			sql = "select resNum as countyId,resCity as cityId from rms_county"
				+ " where zhLabel ='"+obj.getArea()+"'";
		}
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			Map<String, Object> result = list.get(0);
			if(result != null){
				obj.setCityId(result.get("cityId")+"");
				obj.setCountryId(result.get("countyId")+"");
			}
		}
		return obj;
	}
	
	/**
	 * 计算各个资源的数据
	 * @param obj
	 * @return
	 */
	public List<Map<String, Object>> getCountApprovalList(ApprovalTaskPojo obj,String resType){
		List<Map<String, Object>> list = new LinkedList();
		String sql = this.getResSql(obj, resType, "list");
		list = this.jdbcTemplate.queryForList(sql);
		return list;
	}
	
	/**
	 * 得到资源的语句
	 * @param obj
	 * @param resType
	 * @return
	 */
	String getResSql(ApprovalTaskPojo obj,String resType,String type){
		StringBuffer sb = new StringBuffer();
		List<Map<String, Object>> resList =this.jdbcTemplate.queryForList("SELECT * FROM config_resource_task WHERE res_cn_name = '"+resType+"'");
		if(TextUtil.isNull(resList)){
			return "";
		}
		Map<String,Object> resObject = resList.get(0);
		if(type.equals("list")){
			sb.append(" select  * "
					+ " from (");
		}
		if(type.equals("count")){
			sb.append(" select count(1) from ("); 
		}
		sb.append(" select "
				+ " '"+resType+"' as resType,"
				+ " '"+resObject.get("id_column").toString()+"' as idCol,"
				+ " '"+resObject.get("resType").toString()+"' as resEnType,"
				+ " t."+resObject.get("id_column").toString()+" as resCode,"
				+ " t."+resObject.get("name_column").toString()+" as resName,"
				+ " '"+resObject.get("res_table_name").toString()+"' as tableName,"
				+ " t."+resObject.get("longitude_column").toString()+" as longitude,"
				+ " t."+resObject.get("latitude_column").toString()+" as latitude,"
				+ " t."+resObject.get("deleteFlag_column").toString()+" as deletedFlag,");
				if(null != resObject.get("res_region_column")){
					String region = resObject.get("res_region_column").toString();
					if(TextUtil.isNotNull(region) && !(region.equals("null"))){
						sb.append(" t."+resObject.get("res_region_column").toString()+" as region," );
					}
				}
			sb.append( " date_format(t."+resObject.get("createTime_column").toString()+",'%Y-%c-%d %h:%i:%s') as createTime," 
				+ " date_format(t."+resObject.get("updateTime_column").toString()+",'%Y-%c-%d %h:%i:%s') as updateTime," 
				+ " t."+resObject.get("qualitor_column").toString()+" as qualitor," 
				+ " t."+resObject.get("maintainor").toString()+" as maintainor," 
				+ " t."+resObject.get("resNum_column").toString()+" as resNum "
				+ "");
		if(resObject.get("sel_sql")!= null && !((resObject.get("sel_sql")+"").equals(""))){
			sb.append(" , "+resObject.get("sel_sql")+" ");
		}
		sb.append(" from "+ resObject.get("res_table_name").toString() +" t");
		sb.append(" where 1=1 ");
		if(obj.getFlowName().equals("send")) {
			if(TextUtil.isNotNull(obj.getApprovaler())){
				sb.append(" and t."+resObject.get("operator_person_column").toString()+" ='"+obj.getApprovaler()+"' ");
			}
		}else {
			if(TextUtil.isNotNull(obj.getSender())){
				sb.append(" and t."+resObject.get("operator_person_column").toString()+" ='"+obj.getSender()+"' ");
			}
		}
		
		if(TextUtil.isNotNull(obj.getStartTime()) && TextUtil.isNotNull(obj.getEndTime())){
			sb.append(" and (");
			sb.append("(unix_timestamp(t."+resObject.get("createTime_column").toString()+") >= unix_timestamp('"+obj.getStartTime()+" 00:00:00')"
					+ " and unix_timestamp(t."+resObject.get("createTime_column").toString()+") <= unix_timestamp('"+obj.getEndTime()+" 23:00:00'))");
			sb.append(" or (unix_timestamp(t."+resObject.get("updateTime_column").toString()+") >= unix_timestamp('"+obj.getStartTime()+" 00:00:00')"
					+ " and unix_timestamp(t."+resObject.get("updateTime_column").toString()+") <= unix_timestamp('"+obj.getEndTime()+" 23:00:00'))");
			sb.append(")");
		}
		if(resObject.get("where_sql") != null){
			sb.append(" and "+ resObject.get("where_sql"));
		}
		if(type.equals("count")){
			sb.append(" ) b" );
		}
		if(type.equals("list") ){
			sb.append(") b ");
			if(TextUtil.isNotNull(obj.getStart()) && TextUtil.isNotNull(obj.getLimit())){
				sb.append(" limit "+obj.getStart()+","+obj.getLimit()+" ");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 新增审批工单
	 * @param object
	 * @return
	 */
	public int addApproval(ApprovalTaskPojo object){
		return (Integer) this.insert("approval.addApproval", object);
	}
	
	/**
	 * 得到采集的数据
	 * @param obj
	 * @return
	 */
	public String getCollectStr(ApprovalTaskPojo obj) {
		String mapStr = "";
		try {
			if(obj.getTaskState().equals("处理中") || obj.getTaskState().equals("已领单")) {
				mapStr = this.getMapStr(obj, "now");
			}
			if(obj.getTaskState().equals("归档") || obj.getTaskState().equals("处理完成") || obj.getTaskState().equals("驳回")) {
				String sql = ResUtil.getCollectRes(null,obj.getId()+"");
				List<Map<String,Object>> list = this.jdbcTemplate.queryForList(sql);
				List<LineSegmentInfo> segList= new LinkedList<LineSegmentInfo>();
				if(TextUtil.isNotNull(list)) {
					for(Map<String,Object> segMap : list) {
						String segId = segMap.get("segId")+"";
						String startLon =segMap.get("startLon")+"";
						String endLon = segMap.get("endLon")+"";
						LineSegmentInfo seg = new LineSegmentInfo();
						if(TextUtil.isNotNull(segId) && TextUtil.isNotNull(endLon)) {
							seg.setSegId(segMap.get("segId")+"");
							seg.setSegName(segMap.get("segName")+"");
							seg.setSegType(segMap.get("segType")+"");
						}
						if(TextUtil.isNotNull(startLon)) {
							seg.setStartId(segMap.get("startId")+"");
							seg.setStartName(segMap.get("startName")+"");
							seg.setStartType(segMap.get("startType")+"");
							seg.setStartLat(segMap.get("startLat")+"");
							seg.setStartLon(segMap.get("startLon")+"");
							seg.setStartResNum(segMap.get("startResNum")+"");
							seg.setStartState(this.getResType(segMap.get("startResNum")+"", segMap.get("startUpdate")+"", segMap.get("startDel")+"",segMap.get("startMotion")+""));
						}
						if(TextUtil.isNotNull(endLon)) {
							seg.setEndId(segMap.get("endId")+"");
							seg.setEndName(segMap.get("endName")+"");
							seg.setEndType(segMap.get("endType")+"");
							seg.setEndLat(segMap.get("endLat")+"");
							seg.setEndLon(segMap.get("endLon")+"");
							seg.setEndResNum(segMap.get("endResNum")+"");
							seg.setEndState(this.getResType(segMap.get("endResNum")+"", segMap.get("endUpdate")+"", segMap.get("endDel")+"",segMap.get("endMotion")+""));
						}
						if(TextUtil.isNotNull(seg.getStartId())) {
							segList.add(seg);
						}
					}
				}
				List<LineSegmentInfo> leadUpList = this.getLeadupList(0, obj);
				if(TextUtil.isNotNull(leadUpList)) {
					segList.addAll(leadUpList);
				}
				if(TextUtil.isNotNull(segList)) {
					mapStr = ResUtil.getWebGisSeg(segList,0,false);
				}else {
					mapStr = this.getMapStr(obj, "now");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mapStr;
	}
	
	
	/**
	 * 得到地图数据
	 * @param obj
	 * 0 杆路  1  直埋  2  管井 3 挂墙 4 光缆
	 * @return
	 */
	public String getMapStr(ApprovalTaskPojo obj,String type){
		String mapStr = "";
		try{
			List<LineSegmentInfo> list= new LinkedList();
			Integer del =0;
			if(type.equals("original")){
				del=1;
			}
			String resType = obj.getResType();
			String jsonStr = "",pipeStr ="",plineStr = "",equtStr="";
			
			if(resType.contains("井") || resType.contains("well") || resType.contains("pipe") || resType.contains("管道")){
				List<LineSegmentInfo> pipeList = this.getLineList(del, "2", obj);
				list.addAll(pipeList);
			}
			if(resType.contains("杆") || resType.contains("pole") || resType.contains("poleLine") || resType.contains("杆路")){
				List<LineSegmentInfo> poleList = this.getLineList(del, "0", obj);
				list.addAll(poleList);
			}
			if(resType.contains("埋") || resType.contains("buried") || resType.equals("stone") || resType.contains("标石")){
				List<LineSegmentInfo> buriedList = this.getLineList(del, "1", obj);
				list.addAll(buriedList);
			}
			//添加引上段s
			if(resType.contains(",")){
				List<LineSegmentInfo> leadUpList = this.getLeadupList(del, obj);
				list.addAll(leadUpList);
			}
			if(resType.contains("光交") || resType.contains("optical") || resType.contains("equt")){
				List<LineSegmentInfo> cableList = this.getCableList(del, obj);
				list.addAll(cableList);
			}
			//查看下现在的采集数据
			if(obj.getFlowName().equals("send")) {
				String sql = "select distinct startId as resId,"
						+ " startName as resName,'optical' as resType,startLat,startLon "
						+ " from approval_collect"
						+ " where taskId = "+obj.getId()+" and startType ='optical'";
				List<Map<String, Object>> rejList = this.jdbcTemplate.queryForList(sql);
				if(TextUtil.isNotNull(rejList)) {
					for(Map<String, Object> map : rejList) {
						String rejType = map.get("resType")+"";
						String resId = map.get("resId")+"";
						LineSegmentInfo rejLine = new LineSegmentInfo();
						if(rejType.equals("optical")) {
							EqutInfoBean equt = new EqutInfoBean();
							equt.setEid(resId);
							equt = this.getEqutObj(equt);
							rejLine.setStartId(resId);
							rejLine.setStartName(map.get("resName")+"");
							rejLine.setStartType(rejType);
							rejLine.setStartLon(equt.getLon());
							rejLine.setStartLat(equt.getLat());
							String startType = "add";
							if(TextUtil.isNotNull(equt.getResNum())) {
								startType = "update";
							}
							if(TextUtil.isNotNull(equt.getResMotion()) && equt.getResMotion().equals("Audit")) {
								startType ="Audit";
							}
							rejLine.setStartState(startType);
							list.add(rejLine);
						}
					}
				}
			}
			if(obj.getTaskState().equals("驳回") || obj.getTaskState().equals("归档")) {
				String sql = "select distinct resName,resId,resType"
						+ " from approval_resreject where taskId ='"+obj.getId()+"'"
						+ "  ";
				List<Map<String, Object>> rejList = this.jdbcTemplate.queryForList(sql);
				if(TextUtil.isNotNull(rejList)) {
					for(Map<String, Object> map : rejList) {
						String rejType = map.get("resType")+"";
						String resId = map.get("resId")+"";
						LineSegmentInfo rejLine = new LineSegmentInfo();
						if(rejType.equals("optical")) {
							EqutInfoBean equt = new EqutInfoBean();
							equt.setEid(resId);
							equt = this.getEqutObj(equt);
							rejLine.setStartId(resId);
							rejLine.setStartName(map.get("resName")+"");
							rejLine.setStartType(rejType);
							rejLine.setStartLon(equt.getLon());
							rejLine.setStartLat(equt.getLat());
							String startType = "add";
							if(TextUtil.isNotNull(equt.getResNum())) {
								startType = "update";
							}
							rejLine.setStartState(startType);
							list.add(rejLine);
						}
					}
				}
			}
			mapStr = ResUtil.getWebGisSeg(list,0,false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapStr;
	}
	
	List<LineSegmentInfo> getOptList(Integer del,String LineType,ApprovalTaskPojo obj){
		List<LineSegmentInfo> list = new LinkedList();
		return list;
	}
	
	
	
	/**
	 * 得到驳回的资源
	 * @param taskId
	 * @return
	 */
	public String getReject(String taskId) {
		String jsonStr = "[";
		String sql = "select distinct resName,resId,resType,rejectStr "
				+ " from approval_resreject"
				+ " where taskId ='"+taskId+"' and deleteFlag ='0' ";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		for(int i=0;i<list.size();i++) {
			Map<String, Object> map = list.get(i);
			jsonStr+="{\"resName\":\""+map.get("resName")+"\","
					+ "\"resId\":\""+map.get("resId")+"\","
					+ "\"rejectStr\":\""+map.get("rejectStr")+"\","
					+ "\"resType\":\""+map.get("resType")+"\"},";
		}
		if(jsonStr.endsWith(",")) {
			jsonStr = jsonStr.substring(0, jsonStr.length()-1);
		}
		jsonStr +="]";
		return jsonStr;
	}
	
	/**
	 * 引上数据
	 * @param del
	 * @param obj
	 * @return
	 */
	List<LineSegmentInfo> getLeadupList(Integer del , ApprovalTaskPojo obj){
		List<LineSegmentInfo> list = new LinkedList<LineSegmentInfo>();
		LineSegmentInfo line = new LineSegmentInfo();
		if(TextUtil.isNotNull(obj.getApprovaler())) {
			line.setMaintain(obj.getApprovaler());
		}else {
			if(TextUtil.isNotNull(obj.getCrewer())) {
				line.setMaintain(obj.getCrewer());
			}else {
				line.setMaintain(obj.getSender());
			}
		}
		
		if((null != obj.getDealTime())) {
			if(null == obj.getFinishTime()) {
				obj.setFinishTime(new Date());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			line.setStartTime(sdf.format(obj.getDealTime()));
			line.setEndTime(sdf.format(obj.getFinishTime()));
		}else {
			line.setStartTime(obj.getStartTime()+"  00:00:00");
			line.setEndTime(obj.getEndTime() +"  23:59:00");
		}
		String sql = ResUtil.getLeadupStr(line);
		List<Map<String, Object>> segList = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(segList)){
			for(Map<String, Object> map : segList){
				LineSegmentInfo seg = new LineSegmentInfo();
				seg.setSegId(map.get("segId")+"");
				seg.setSegName(map.get("segName")+"");
				seg.setSegType(map.get("segType")+"");
				seg.setStartId(map.get("startId")+"");
				seg.setStartName(map.get("startName")+"");
				seg.setStartType(map.get("startType")+"");
				seg.setStartLon(map.get("startLon")+"");
				seg.setStartLat(map.get("startLat")+"");
				seg.setEndId(map.get("endId")+"");
				seg.setEndName(map.get("endName")+"");
				seg.setEndType(map.get("endType")+"");
				seg.setEndLon(map.get("endLon")+"");
				seg.setEndLat(map.get("endLat")+"");
				list.add(seg);
			}
		}
		
		return list;
	}
	
	/**
	 * 单独写的光缆段数据
	 * @param del
	 * @param obj
	 * @return
	 */
	List<LineSegmentInfo> getCableList(Integer del,ApprovalTaskPojo obj){
		String LineType = "4";
		List<LineSegmentInfo> list = new LinkedList();
		//段的数据
		LineSegmentInfo segObj = new LineSegmentInfo();
		segObj.setSysType(LineType+"");
		segObj.setSegType(LineType+"");
		if(del.equals(1)){
			segObj.setExtendSql(" and t.resNum is not null ");
		}else{
			segObj.setDeleteFlag(del);
		}
		//如果存在领单优先用领单人账号查询资源
		if(TextUtil.isNotNull(obj.getApprovaler())) {
			segObj.setMaintain(obj.getApprovaler());
		}else {
			if(TextUtil.isNotNull(obj.getCrewer())) {
				segObj.setMaintain(obj.getCrewer());
			}else {
				segObj.setMaintain(obj.getSender());
			}
		}
		if((null != obj.getDealTime()) ) {
			if(null == obj.getFinishTime()) {
				obj.setFinishTime(new Date());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			segObj.setStartTime(sdf.format(obj.getDealTime()));
			segObj.setEndTime(sdf.format(obj.getFinishTime()));
		}else {
			segObj.setStartTime(obj.getStartTime()+"  00:00:00");
			segObj.setEndTime(obj.getEndTime() +"  23:59:00");
		}
		
		String segSql = ResUtil.getSegStr(segObj);
		List<Map<String, Object>> segList = this.jdbcTemplate.queryForList(segSql);
		List<Map<String, Object>> opticalList = this.getPointList(del, "4", obj);
		List<Map<String, Object>> OpticalTerminalList = this.getPointList(del, "5", obj);
		List<Map<String, Object>> stationList = this.getPointList(del, "6", obj);
		if(TextUtil.isNotNull(segList)){
			for(Map<String, Object> segMap : segList){
				LineSegmentInfo seg = new LineSegmentInfo();
				seg.setSegId(segMap.get("segId")+"");
				seg.setSegName(segMap.get("segName")+"");
				seg.setSegType(segMap.get("segType")+"");
				seg.setStartId(segMap.get("startId")+"");
				seg.setStartName(segMap.get("startName")+"");
				seg.setStartType(segMap.get("startType")+"");
				seg.setStartLat(segMap.get("startLat")+"");
				seg.setStartLon(segMap.get("startLon")+"");
				seg.setEndId(segMap.get("endId")+"");
				seg.setEndName(segMap.get("endName")+"");
				seg.setEndType(segMap.get("endType")+"");
				seg.setEndLat(segMap.get("endLat")+"");
				seg.setEndLon(segMap.get("endLon")+"");
				list.add(seg);
				for(int i=0;i<opticalList.size();i++){
					Map<String, Object> pMap = opticalList.get(i);
					if((pMap.get("id")+"").equals(seg.getStartId()) || (pMap.get("id")+"").equals(seg.getEndId())){
						opticalList.remove(i);
					}
				}
				for(int i=0;i<OpticalTerminalList.size();i++){
					Map<String, Object> pMap = OpticalTerminalList.get(i);
					if((pMap.get("id")+"").equals(seg.getStartId()) || (pMap.get("id")+"").equals(seg.getEndId())){
						OpticalTerminalList.remove(i);
					}
				}
				for(int i=0;i<stationList.size();i++){
					Map<String, Object> pMap = stationList.get(i);
					if((pMap.get("id")+"").equals(seg.getStartId()) || (pMap.get("id")+"").equals(seg.getEndId())){
						stationList.remove(i);
					}
				}
			}
		}
		//增加光交孤点
		if(TextUtil.isNotNull(opticalList)){
			for(Map<String, Object> map : opticalList){
				LineSegmentInfo line = new LineSegmentInfo();
				line.setStartId(map.get("id")+"");
				line.setStartName(map.get("name")+"");
				line.setStartType(map.get("type")+"");
				if(TextUtil.isNull(map.get("resNum")+"")) {
					line.setStartState("add");
				}else {
					line.setStartState("update");
				}
				line.setStartLon(map.get("longitude")+"");
				line.setStartLat(map.get("latitude")+"");
				list.add(line);
			}
		}
		if(TextUtil.isNotNull(OpticalTerminalList)){
			for(Map<String, Object> map : OpticalTerminalList){
				LineSegmentInfo line = new LineSegmentInfo();
				line.setStartId(map.get("id")+"");
				line.setStartName(map.get("name")+"");
				line.setStartType(map.get("type")+"");
				line.setStartState("add");
				line.setStartLon(map.get("longitude")+"");
				line.setStartLat(map.get("latitude")+"");
				list.add(line);
			}
		}
		if(TextUtil.isNotNull(stationList)){
			for(Map<String, Object> map : stationList){
				LineSegmentInfo line = new LineSegmentInfo();
				line.setStartId(map.get("id")+"");
				line.setStartName(map.get("name")+"");
				line.setStartType(map.get("type")+"");
				line.setStartState("add");
				line.setStartLon(map.get("longitude")+"");
				line.setStartLat(map.get("latitude")+"");
				list.add(line);
			}
		}
		return list;
	}
	
	/**
	 * 得到外线的数据
	 * @param del
	 * @param type
	 * @param obj
	 * @return
	 */
	List<LineSegmentInfo> getLineList(Integer del,String LineType,ApprovalTaskPojo obj){
		List<LineSegmentInfo> list = new LinkedList();
		//段的数据
		LineSegmentInfo segObj = new LineSegmentInfo();
		segObj.setSysType(LineType+"");
		if(del.equals(1)){
			segObj.setExtendSql(" and t.resNum is not null ");
		}else{
			segObj.setDeleteFlag(del);
		}
		if(TextUtil.isNotNull(obj.getApprovaler())) {
			segObj.setMaintain(obj.getApprovaler());
		}else {
			if(TextUtil.isNotNull(obj.getCrewer())) {
				segObj.setMaintain(obj.getCrewer());
			}else {
				segObj.setMaintain(obj.getSender());
			}
		}
		if((null != obj.getDealTime())) {
			if(null == obj.getFinishTime()) {
				obj.setFinishTime(new Date());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			segObj.setStartTime(sdf.format(obj.getDealTime()));
			segObj.setEndTime(sdf.format(obj.getFinishTime()));
		}else {
			segObj.setStartTime(obj.getStartTime()+"  00:00:00");
			segObj.setEndTime(obj.getEndTime() +"  23:59:00");
		}
		
		String segSql = ResUtil.getSegStr(segObj);
		List<Map<String, Object>> segList = this.jdbcTemplate.queryForList(segSql);
		List<Map<String, Object>> pointList = this.getPointList(del, LineType, obj);
		if(TextUtil.isNotNull(segList)){
			for(Map<String, Object> segMap : segList){
				LineSegmentInfo seg = new LineSegmentInfo();
				seg.setSegId(segMap.get("segId")+"");
				seg.setSegName(segMap.get("segName")+"");
				seg.setSegType(segMap.get("segType")+"");
				seg.setStartId(segMap.get("startId")+"");
				seg.setStartName(segMap.get("startName")+"");
				seg.setStartType(segMap.get("startType")+"");
				seg.setStartLat(segMap.get("startLat")+"");
				seg.setStartLon(segMap.get("startLon")+"");
				seg.setStartResNum(segMap.get("startResNum")+"");
				seg.setStartState(this.getResType(segMap.get("startResNum")+"", segMap.get("startUpdate")+"", segMap.get("startDel")+"",""));
				seg.setEndId(segMap.get("endId")+"");
				seg.setEndName(segMap.get("endName")+"");
				seg.setEndType(segMap.get("endType")+"");
				seg.setSegType(LineType);
				seg.setEndLat(segMap.get("endLat")+"");
				seg.setEndLon(segMap.get("endLon")+"");
				seg.setEndResNum(segMap.get("endResNum")+"");
				seg.setEndState(this.getResType(segMap.get("endResNum")+"", segMap.get("endUpdate")+"", segMap.get("endDel")+"",""));
				list.add(seg);
				for(int i=0;i<pointList.size();i++){
					Map<String, Object> pMap = pointList.get(i);
					if((pMap.get("id")+"").equals(seg.getStartId()) || (pMap.get("id")+"").equals(seg.getEndId())){
						pointList.remove(i);
					}
				}
			}
		}
		//增加孤点
		if(TextUtil.isNotNull(pointList)){
			for(Map<String, Object> map : pointList){
				LineSegmentInfo line = new LineSegmentInfo();
				line.setSegType(LineType);
				line.setStartId(map.get("id")+"");
				line.setStartName(map.get("name")+"");
				line.setStartType(map.get("type")+"");
				line.setStartLon(map.get("longitude")+"");
				line.setStartLat(map.get("latitude")+"");
				line.setStartResNum(map.get("resNum")+"");
				line.setStartState(this.getResType(map.get("resNum")+"", map.get("lastUpdate")+"", map.get("del")+"",""));
				list.add(line);
			}
		}
		return list;
	}
	
	
	/**
	 * 得到资源点数据
	 * @param info
	 * @return
	 */
	public List<LineSegmentInfo> getPointList(LinePointInfo info){
		info.setDeleteflag(0);
		info.setDistance(1000);
		String pointSql= ResUtil.getPointStr(info);
		List<LineSegmentInfo> list = new LinkedList();
		List<Map<String, Object>> resultList = this.jdbcTemplate.queryForList(pointSql);
		if(TextUtil.isNotNull(resultList)){
			for(Map<String, Object> map : resultList){
				LineSegmentInfo line = new LineSegmentInfo();
				line.setStartId(map.get("id")+"");
				line.setStartName(map.get("name")+"");
				line.setStartType(map.get("type")+"");
				line.setStartLon(map.get("longitude")+"");
				line.setStartLat(map.get("latitude")+"");
				line.setStartResNum(map.get("resNum")+"");
				line.setStartState(this.getResType(map.get("resNum")+"", map.get("lastUpdate")+"", map.get("del")+"",""));
				list.add(line);
			}
		}
		return list;
	}
	
	
	/**
	 * 得到相应
	 * 的资源点信息
	 * @param info
	 * @return
	 */
	public String getPointStr(LinePointInfo info ){
		info.setDeleteflag(0);
		info.setDistance(1000);
		String pointSql= ResUtil.getPointStr(info);
		List<LineSegmentInfo> list = new LinkedList();
		List<Map<String, Object>> resultList = this.jdbcTemplate.queryForList(pointSql);
		if(TextUtil.isNotNull(resultList)){
			for(Map<String, Object> map : resultList){
				LineSegmentInfo line = new LineSegmentInfo();
				line.setStartId(map.get("id")+"");
				line.setStartName(map.get("name")+"");
				line.setStartType(map.get("type")+"");
				line.setStartLon(map.get("longitude")+"");
				line.setStartLat(map.get("latitude")+"");
				line.setStartResNum(map.get("resNum")+"");
				line.setStartState(this.getResType(map.get("resNum")+"", map.get("lastUpdate")+"", map.get("del")+"",""));
				list.add(line);
			}
		}
		String jsonStr = ResUtil.getWebGisSeg(list,0,false);
		return jsonStr;
	}
	
	/**
	 * 得到相应的点数据
	 * @param del
	 * @param type
	 * @param obj
	 * @return
	 */
	List<Map<String, Object>> getPointList(Integer del,String pointType,ApprovalTaskPojo obj){
		LinePointInfo info = new LinePointInfo();
		if(del.equals(1)){
			info.setExtendSql(" and t.resNum is not null ");
		}else{
			info.setDeleteflag(del);
		}
		info.setSysType(Integer.parseInt(pointType));
		if(TextUtil.isNotNull(obj.getApprovaler())) {
			info.setMaintain(obj.getApprovaler());
		}else {
			if(TextUtil.isNotNull(obj.getCrewer())) {
				info.setMaintain(obj.getCrewer());
			}else {
				info.setMaintain(obj.getSender());
			}
		}
		if((null != obj.getDealTime()) ) {
			if(null == obj.getFinishTime()) {
				obj.setFinishTime(new Date());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			info.setStartTime(sdf.format(obj.getDealTime()));
			info.setEndTime(sdf.format(obj.getFinishTime()));
		}else {
			info.setStartTime(obj.getStartTime()+"  00:00:00");
			info.setEndTime(obj.getEndTime() +"  23:59:00");
		}
		
		String pointSql= ResUtil.getPointStr(info);
		List<Map<String, Object>> resultList = this.jdbcTemplate.queryForList(pointSql);
		return resultList;
	}
	
	/**
	 * 判断资源类型
	 * @param resNum
	 * @param lastUpDate
	 * @param del
	 * @return
	 */
	String getResType(String resNum,String lastUpDate,String del,String motion){
		String resType ="add";
		if(TextUtil.isNull(resNum)){
			resType ="add";
		}else{
			if(del.equals("1")){
				resType ="del";
			}else{
				resType ="update";
			}
		}
		if(TextUtil.isNotNull(motion) && motion.equals("Audit")) {
			resType ="Audit";
		}
		return resType;
	}
	
	/**
	 * 根据用户ID得到
	 * 相应的审批权限
	 * @param userId
	 * @return
	 */
	public boolean getAuditRoll(String userId) {
		boolean flag = false;
		String sql = "select"
				+ " id,account,county,countyId,accountId"
				+ " from audit_roll"
				+ " where accountId ='"+userId+"'";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)) {
			flag = true;
		}
		return flag;
	}
	
	
	/**
	 * 得到资源的图片
	 * @param id
	 * @param resType
	 * @return
	 */
	public List<Map<String, Object>> getResImag(String id,String resType){
		if(resType.equals("station")){
			resType = "s";
		}else if(resType.equals("generator")){
			resType = "g";
		}else if(resType.equals("rack")){
			resType ="EQU";
		}else if(resType.equals("optical") || resType.equals("equtinfo")){
			resType ="EIU";
		}
		if(id.startsWith("EQU_")){
			id = id.split("_")[1];
		}else if(id.startsWith("EIU_")){
			id = id.split("_")[1];
		}
		List<Map<String, Object>> list = new LinkedList();
		String imagePath = getPropert.getValueByKey("imageUrl");
		String sql = "select ImageName,ImagePath from"
				+ " resource_images where type = '"+resType+"'"
				+ " and resourceId = '"+id+"'";
				//+ " resource_images where resourceId = 280";
		list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			for(Map<String, Object> map: list){
				String path = map.get("ImagePath")+"";
				map.put("ImagePath", imagePath+""+path);
			}
		}
		return list;
	}
	
	
	/**
	 * 得到资源map
	 * @param id
	 * @param resType
	 * @return
	 */
	public Map<String, Object> getResMap(String id,String resType){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		
		//管井的
		if(resType.equals("well")){
			sb.append("select wellId as imId, wellName as '资源名称', wellNo  as '编号', longitude as '经度', latitude as '纬度',"
					+ " region as '所属区县', date_format(creationDate,'%Y-%c-%d') as '创建时间',"
					+ " date_format(lastUpdateDate,'%Y-%c-%d %H:%i:%s') as '修改时间', dataQualityPrincipal as '数据质量责任人',"
					+ " parties as '一线维护人',resNum as '综资标识',deletedFlag as del,constructionSharingEnumId as '产权性质',"
					+ " constructionSharingOrg as '产权单位',remark as '备注' "
					+ " from wellinfo  where wellId ='"+id+"'"
					+ "");
		}
		//标石
		if(resType.equals("stone")){
			sb.append(" select stoneId as imId,stoneName as '资源名称',stoneNum as '编号',longitude as '经度',latitude as '纬度',"
					+ " stoneArea as '所属区县',"
					+ " date_format(createTime,'%Y-%c-%d') as '创建时间', "
					+ " date_format(lastUpTime,'%Y-%c-%d %H:%i:%s') as '修改时间',dataQualitier as '数据质量责任人',"
					+ " maintainer as '一线维护人',resNum as '综资标识',deleteflag as del,propertyNature as '产权性质',"
					+ " propertyComp as '产权单位',remark as '备注' "
					+ " from stoneinfo where stoneId ='"+id+"' ");
		}
		//电杆
		if(resType.equals("pole")){
			sb.append(" select poleId as imId, poleName as '资源名称',poleNo as '编号',poleLongitude as '经度',poleLatitude as '纬度',"
					+ " region as '所属区县',"
					+ " date_format(creationDate,'%Y-%c-%d') as '创建时间',"
					+ " date_format(lastUpdateDate,'%Y-%c-%d %H:%i:%s') as '修改时间', dataQualityPrincipal as '数据质量责任人',"
					+ " parties as '一线维护人',resNum as '综资标识',deletedFlag as del,"
					+ " maintenanceOrgId as '产权单位',maintenanceModeEnumId as '产权性质',remark as '备注' "
					+ " from poleinfo where poleId = '"+id+"'"
					+ "");
		}
		//直埋段
		if(resType.equals("buried")){
			sb.append(" select t.buriedPartName as '资源名称',"
					+ " (select lineName from line_system where id = t.buriedId) as '系统名称',"
					+ " buriedPartArea as '维护区县',buriedPartLength as '计算长度',"
					+ " maintainLength as '维护长度',buriedPartStart as 'A端设备',"
					+ " buriedPartEnd as 'Z端设备',"
					+ " date_format(createTime,'%Y-%c-%d') as '创建时间',"
					+ " date_format(lastUpTime,'%Y-%c-%d %H:%i:%s') as '修改时间',"
					+ " dataQualitier as '数据质量责任人',maintainer as '一线维护人',"
					+ " resNum as '综资标识',deleteflag as del,"
					+ " propertyDept as '产权单位', propertyRight as '产权性质' "
					+ " from buriedpartinfo t where t.id ='"+id+"'"
					+ "");
		}
		//光缆段
		if(resType.equals("cable")){
			sb.append(" select cablename as '资源名称',"
					+ " region as '维护区县',length as '计算长度',maintainLength as '维护长度', "
					+ " startDeviceName as 'A端设备',endDeviceName as 'Z端设备',fibercount as '纤芯数',"
					+ " date_format(creationDate,'%Y-%c-%d') as '创建时间',"
					+ " date_format(lastUpdateDate,'%Y-%c-%d %H:%i:%s') as '修改时间', "
					+ " resNum as '综资标识',deletedFlag as del"
					+ " from job_cable t where t.cableid ='"+id+"'");
		}
		if(resType.equals("leadup")){
			sb.append("select leadupName as '资源名称',mantainance as '维护区县',length as '计算长度',"
					+ " maintainLength as '维护长度',startName as 'A端设备',endName as 'Z端设备',"
					+ " date_format(createTime,'%Y-%c-%d %H:%i:%s') as '创建时间',"
					+ " date_format(lastUpTime,'%Y-%c-%d %H:%i:%s') as '修改时间',resNum as '综资标识',"
					+ " propertyNature as '产权性质',propertyComp as '产权单位',deleteflag as del"
					+ " from leadupstage t where id ='"+id+"'");
		}
		//杆路段
		if(resType.equals("poleLine")){
			sb.append("select  poleLineSegmentName as '资源名称',"
					+ " (select lineName from line_system where id = t.poleLineId) as '系统名称', "
					+ " maintenanceAreaName as '维护区县',poleLineSegmentLength as '计算长度',"
					+ " maintainLength as '维护长度',"
					+ " (select poleName from poleinfo where poleId=t.startDeviceId) as 'A端设备',"
					+ " (select poleName from poleinfo where poleId=t.endDeviceId) as 'Z端设备',"
					+ " date_format(creationDate,'%Y-%c-%d') as '创建时间',"
					+ " date_format(lastUpdateDate,'%Y-%c-%d %H:%i:%s') as '修改时间',"
					+ " dataQualityPrincipal as '数据质量责任人',parties as '一线维护人',resNum as '综资标识',deletedFlag as del,"
					+ " constructionSharingEnumId as '产权性质' ,constructionSharingOrg as '产权单位' "
					+ " from polelinesegmentinfo t where t.poleLineSegmentId='"+id+"'");
		}
		//管道段
		if(resType.equals("pipe")){
			sb.append(" select pipeSegmentName as '资源名称',"
					+ " (select lineName from line_system where id = t.pipeId) as '系统名称',"
					+ " maintenanceAreaName as '维护区县',pipeSegmentLength as '计算长度',"
					+ " maintainLength as '维护长度',"
					+ " (select w.wellName from wellinfo w where w.wellId = t.startDeviceId) as 'A端设备',"
					+ " (select w.wellName from wellinfo w where w.wellId = t.endDeviceId) as 'Z端设备',"
					+ " date_format(creationDate,'%Y-%c-%d') as '创建时间',"
					+ " date_format(lastUpdateDate,'%Y-%c-%d %H:%i:%s') as '修改时间',"
					+ " dataQualityPrincipal as '数据质量责任人',parties as '一线维护人',resNum as '综资标识',deletedFlag as del,"
					+ " sharingTypeEnumId as '产权性质' ,constructionSharingOrg as '产权单位' "
					+ "from pipesegmentinfo t where t.pipeSegmentId ='"+id+"'");
		}
		//光交箱
		if(resType.equals("optical") || resType.equals("equtinfo")){
			sb.append(" select t.ENAME as '资源名称',t.LON as '经度',t.LAT as '纬度',t.EADDR as '所属区县',"
					+ " date_format(t.MTIME,'%Y-%c-%d') as '创建时间',"
					+ " date_format(t.lastUpdateDate,'%Y-%c-%d %H:%i:%s') as '修改时间',t.resNum as '综资标识',"
					+ " case t.gjxmianshu when 0 then '单面' else '双面' end as '光交箱面数',"
					+ " t.address as '安装地址',t.installCapacity as '安装容量',t.usedCapacity as '使用容量',"
					+ " t.designCapacity as '设计容量',t.freeCapacity as '空闲容量',"
					+ " t.dataQualityPrincipal as '数据质量责任人',t.parties as '一线维护人',t.remark as '备注' "
					+ " from job_equtinfo t where t.EID like '%"+id+"%' and t.ETYPE =3 and t.del=0 ");
		}
		//光终端盒
		if(resType.equals("OpticalTerminal")){
			sb.append(" select t.terminalName as '资源名称',t.longitude as '经度',t.latitude as '纬度',terminalAddres as '所属区县',"
					+ " date_format(t.createTime,'%Y-%c-%d') as '创建时间',date_format(t.lastUpTime,'%Y-%c-%d %H:%i:%s') as '修改时间',"
					+ " t.attachName as '所属设备',t.dataQualitier as '数据质量责任人',t.maintainer as '一线维护人',t.resNum as '综资标识'"
					+ " from optical_terminal t where t.id = '"+id+"'"
					+ "");
			
		}
		//站点
		if(resType.equals("station")){
			sb.append(" select t.stationName as '资源名称',t.region as '所属区县',t.stationAddr as '资源地址',"
					+ " t.lon as '经度',t.lat as '纬度',"
					+ " (select text from dicview where enName ='site_type' and value = t.stationType) as '站点类型',"
					+ " (select text from dicview where enName ='serviceLevel' and value = t.stationLevel) as '业务级别',"
					+ " date_format(t.creationDate,'%Y-%c-%d') as '创建时间',"
					+ " date_format(t.lastUpdateDate,'%Y-%c-%d %H:%i:%s') as '修改时间',t.dataQualityPrincipal as '数据质量责任人',"
					+ " t.parties as '一线维护人', t.resNum as '综资标识'"
					+ " from job_stationbase t "
					+ " where t.stationBaseId  = '"+id+"'");
		}
		//机房
		if(resType.equals("generator")){
			sb.append("select t.generatorName as '资源名称',t.region as '所属区县',t.station as '所属站点',"
					+ " t.lon as '经度',t.lat as '纬度',t.generatorAddr as '资源地址',"
					+ " (select text from dicview where enName ='room_type' and value = t.jflx) as '机房类型',"
					+ " (select text from dicview where enName ='ywjb' and value = t.ywjb) as '业务级别',"
					+ " t.szlc as '所在楼层',"
					+ " date_format(t.creationDate,'%Y-%c-%d') as '创建时间',"
					+ " date_format(t.lastUpdateDate,'%Y-%c-%d %H:%i:%s') as '修改时间',"
					+ " t.resNum as '综资标识',t.dataQualityPrincipal as '数据质量责任人',"
					+ " t.parties as '一线维护人'"
					+ "  from job_generator t where t.generatorId = '"+id+"'");
		}
		//机架
		if(resType.equals("rack")){
			sb.append("select t.ENAME as '资源名称',t.changjia as '厂家',"
					+ " (select text from dicview where enName ='frame_type' and value = t.jijialeixing) as '机架类型',"
					+ " t.jijiahanghao as '机架行号',t.jijialiehao as '机架列号',t.dataQualityPrincipal as '数据质量责任人',"
					+ " t.parties as '一线维护人',"
					+ " date_format(t.MTIME,'%Y-%c-%d') as '创建时间',"
					+ " date_format(t.lastUpdateDate,'%Y-%c-%d %H:%i:%s') as '修改时间',t.resNum as '综资标识'"
					+ "  from job_equtinfo t where t.EID like '%"+id+"%'"
					/*+ " union all "
					+ " select t.ENAME as '资源名称',t.changjia as '厂家',"
					+ " (select text from dicview where enName ='frame_type' and value = t.jijialeixing) as '机架类型',"
					+ " t.jijiahanghao as '机架行号',t.jijialiehao as '机架列号',t.dataQualityPrincipal as '数据质量责任人',"
					+ " t.parties as '一线维护人',"
					+ " date_format(t.MTIME,'%Y-%c-%d %h:%i:%s') as '创建时间',"
					+ " date_format(t.lastUpdateDate,'%Y-%c-%d %h:%i:%s') as '修改时间',t.resNum as '综资标识'"
					+ "  from job_equtinfo t where t.id ='"+id+"'"*/
					+ "");
		}
		//模块
		if(resType.equals("odm")){
			sb.append(" select t.odmName as '模块名称',t.odmCode as '模块编号',"
					+ " t.terminalRowQuantity as '行数',t.terminalColumnQuantity as '列数',"
					+ " date_format(t.creationDate,'%Y-%c-%d') as '创建时间',"
					+ " date_format(t.lastUpdateDate,'%Y-%c-%d %H:%i:%s') as '修改时间',t.resNum as '综资标识' "
					+ " from job_odm t where t.odmId='"+id+"'");
		}
		//传输网元
		if(resType.equals("ne")){
			sb.append("select t.deviceName as '设备名称',t.deviceModel as '设备型号',t.deviceVender as '设备厂家',"
					+ " date_format(t.creationDate,'%Y-%c-%d') as '创建时间',"
					+ " date_format(t.lastUpdateDate,'%Y-%c-%d %H:%i:%s') as '修改时间',t.resNum as '综资标识'"
					+ "  from job_device t where t.id = '"+id+"'");
		}
		//传输板卡
		if(resType.equals("neCard")){
			sb.append(" select cardName as '板卡名称',version as '软件版本',t.model as '板卡型号',t.cardType as '板卡类型',"
					+ " t.creationDate as '创建时间',t.lastUpdateDate as '修改时间',t.resNum as '综资标识'"
					+ " from job_card t where t.id ='"+id+"'");
		}
		//odm端子
		if(resType.equals("odmPoint")){
			sb.append(" select t.pos as '资源名称',t.plineno as '列号',t.prowno as '行号',"
					+ " (select zhLabel from job_fiber where startPortId = t.ID) as aim,"
					+ " (select zhLabel from job_fiber where startPortId = t.resNum) as aresNum,"
					+ " (select zhLabel from job_fiber where endPortId = t.ID) as zim,"
					+ " (select zhLabel from job_fiber where endPortId = t.resNum) as zresNum,"
					+ " date_format(t.MTIME,'%Y-%c-%d') as '创建时间',"
					+ " date_format(t.lastUpdateDate,'%Y-%c-%d %H:%i:%s') as '修改时间',t.resNum as '综资标识'"
					+ " from job_pointinfo t where t.ID ="+id+"");
		}
		//网元端口数据
		if(resType.equals("nePoint")){
			sb.append(" select t.pointName as '资源名称',t.pointRate as '端口速率',"
					+ " (select case j.pid2Type"
					+ " when 9215 then (select pos from job_pointinfo where ID = j.pid2)"
					+ " when 9701 then (select pointName from job_point where Id = j.pid2)"
					+ " end as pName"
					+ " from job_jumpfiber j where j.pid1 = t.id) as 'z对端',"
					+ " (select case j.pid1Type"
					+ " when 9215 then (select pos from job_pointinfo where ID = j.pid1)"
					+ " when 9701 then (select pointName from job_point where Id = j.pid1)"
					+ " end as pName"
					+ " from job_jumpfiber j where j.pid1 = t.id) as 'A对端',"
					+ " date_format(t.creationDate,'%Y-%c-%d %h:%i:%s') as '创建时间',"
					+ " date_format(t.lastUpdateDate,'%Y-%c-%d %h:%i:%s') as '修改时间',t.resNum as '综资标识'"
					+ " from job_point t where t.id = "+id+"");
		}
		if(resType.equals("odmPoint")){
			List<Map<String, Object>> list= this.jdbcTemplate.queryForList(sb.toString());
			if(TextUtil.isNotNull(list)){
				Map<String,Object> resMap = list.get(0);
				map.put("资源名称", resMap.get("资源名称")+"");
				map.put("列号", resMap.get("列号")+"");
				map.put("行号", resMap.get("行号")+"");
				map.put("创建时间", resMap.get("创建时间")+"");
				map.put("修改时间", resMap.get("修改时间")+"");
				map.put("综资标识", resMap.get("综资标识")+"");
				if(TextUtil.isNotNull(resMap.get("aim")+"")){
					map.put("上架纤芯", resMap.get("aim")+"");
					map.put("状态", "占用");
				}
				if(TextUtil.isNotNull(resMap.get("aresNum")+"")){
					map.put("上架纤芯", resMap.get("aresNum")+"");
					map.put("状态", "占用");
				}
				if(TextUtil.isNotNull(resMap.get("zim")+"")){
					map.put("上架纤芯", resMap.get("zim")+"");
					map.put("状态", "占用");
				}
				if(TextUtil.isNotNull(resMap.get("zresNum")+"")){
					map.put("上架纤芯", resMap.get("zresNum")+"");
					map.put("状态", "占用");
				}
			}
		}else if(resType.equals("nePoint")){
			List<Map<String, Object>> list= this.jdbcTemplate.queryForList(sb.toString());
			if(TextUtil.isNotNull(list)){
				Map<String, Object> resMap = list.get(0);
				map.put("资源名称", resMap.get("资源名称")+"");
				map.put("创建时间", resMap.get("创建时间")+"");
				map.put("修改时间", resMap.get("修改时间")+"");
				map.put("综资标识", resMap.get("综资标识")+"");
				if(TextUtil.isNotNull(resMap.get("z对端")+"")){
					map.put("对端", resMap.get("z对端")+"");
					map.put("状态", "占用");
				}
				if(TextUtil.isNotNull(resMap.get("A对端")+"")){
					map.put("对端", resMap.get("A对端")+"");
					map.put("状态", "占用");
				}
			}
		}else{
			List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sb.toString());
			if(TextUtil.isNotNull(list)){
				map = list.get(0);
			}
		}
		
		return map;
	}
	
	
	/**
	 * 得到单个资源点
	 * @param id
	 * @param type
	 * @return
	 */
	public Object getResObject(String id, String type){
		String tableStr = "";
		if(type.equals("stone")){
			tableStr ="stone.getStone";
			StoneInfoBean stone = new StoneInfoBean();
			stone.setStoneId(Integer.parseInt(id));
			stone = (StoneInfoBean) this.getObject(tableStr, stone);
			return stone;
		}else if(type.equals("pole")){
			tableStr = "pdapoleline.getPole";
			PoleInfoBean pole = new PoleInfoBean();
			pole.setPoleId(Integer.parseInt(id));
			pole = (PoleInfoBean) this.getObject(tableStr, pole);
			return pole;
		}else if(type.equals("well")){
			tableStr = "pdapipe.getWell";
			WellInfoBean well = new WellInfoBean();
			well.setWellId(Integer.parseInt(id));
			well = (WellInfoBean) this.getObject(tableStr, well);
			return well;
		}else if(type.equals("buried")){
			tableStr ="buriedPart.getBuriedPart";
			BuriedPartObj buriedPart = new BuriedPartObj();
			buriedPart.setId(Integer.parseInt(id));
			buriedPart = (BuriedPartObj) this.getObject(tableStr, buriedPart);
			return buriedPart;
		}else if(type.equals("poleLine")){
			tableStr ="pdapoleline.checkPoleLineSeg";
			PolelineSegmentInfoBean poleSeg = new PolelineSegmentInfoBean();
			poleSeg.setPoleLineSegmentId(Integer.parseInt(id));
			poleSeg = (PolelineSegmentInfoBean) this.getObject(tableStr, poleSeg);
			return poleSeg;
		}else  if(type.equals("pipe")){
			tableStr = "pdapipe.getPipeSegment";
			PipeSegmentInfoBean pipeSeg = new PipeSegmentInfoBean();
			pipeSeg.setPipeSegmentId(Integer.parseInt(id));
			pipeSeg = (PipeSegmentInfoBean) this.getObject(tableStr, pipeSeg);
			return pipeSeg;
		}else if(type.equals("station")){
			tableStr = "pdagenerator.getStationBase";
			StationBaseInfoBean site = new StationBaseInfoBean();
			site.setStationBaseId(Integer.parseInt(id));
			site = (StationBaseInfoBean) this.getObject(tableStr, site);
			return site;
		}else if(type.equals("generator")){
			tableStr = "pdagenerator.getGenerator";
			GeneratorInfoBean gener = new GeneratorInfoBean();
			gener.setGeneratorId(Integer.parseInt(id));
			gener = (GeneratorInfoBean) this.getObject(tableStr, gener);
			return gener;
		}else if(type.equals("rack")){
			tableStr = "pdaequt.getAllEqut";
			EqutInfoBean equt = new EqutInfoBean();
			equt.setEid(id);
			equt = (EqutInfoBean) this.getObject(tableStr, equt);
			return equt;
		}else if(type.equals("leadup")){
			tableStr ="leadup.getLeadupObj";
			LeadupPojo leadUp = new LeadupPojo();
			leadUp.setId(Integer.parseInt(id));
			leadUp = (LeadupPojo) this.getObject(tableStr, leadUp);
			return leadUp;
		}else if(type.equals("optical")) {
			tableStr = "pdaequt.getAllEqut";
			EqutInfoBean equt = new EqutInfoBean();
			equt.setEid(id);
			equt = (EqutInfoBean) this.getObject(tableStr, equt);
			return equt;
		}
		else{
			return null;
		}
	}
	
	/**
	 * 根据管井得到段数据
	 * @param wellid
	 * @return
	 */
	public List<Map<String, Object>> getPipeByWell(String wellid){
		String sql = "select imOpp,resOpp,pipeId from ("
				+ " select p.startDeviceId as imOpp ,"
				+ " (select resNum from wellinfo where wellId = p.startDeviceId) as resOpp,p.pipeSegmentId as pipeId"
				+ " from pipesegmentinfo p where p.endDeviceId = '"+wellid+"' and resNum is null and deletedFlag = 0 "
				+ " union all"
				+ " select p.endDeviceId as imOpp,"
				+ " (select resNum from wellinfo where wellId = p.endDeviceId) as resOpp,p.pipeSegmentId as pipeId"
				+ " from pipesegmentinfo p where p.startDeviceId = '"+wellid+"' and resNum is null and deletedFlag = 0 "
				+ ") pipeMap where resOpp is not null ";
		return this.jdbcTemplate.queryForList(sql);
	}
	
	
	/**
	 * 得到杆路信息
	 * @param poleId
	 * @return
	 */
	public List<Map<String, Object>> getPlineByPole(String poleId){
		String sql = "select imOpp,resOpp,pipeId from ("
				+ " select p.endDeviceId as imOpp ,(select resNum from poleinfo where poleId = p.endDeviceId) as resOpp,"
				+ " p.poleLineSegmentId as pipeId"
				+ "  from polelinesegmentinfo p where p.startDeviceId = '"+poleId+"' and p.resNum is null and p.deletedFlag =0"
				+ " union all"
				+ " select p.startDeviceId as imOpp ,(select resNum from poleinfo where poleId = p.startDeviceId) as resOpp,"
				+ " p.poleLineSegmentId as pipeId"
				+ " from polelinesegmentinfo p where p.endDeviceId = '"+poleId+"' and p.resNum is null and p.deletedFlag =0"
				+ " )poleMap where resOpp is not null ";
		return this.jdbcTemplate.queryForList(sql);
	}
	
	
	/**
	 * 批量添加地
	 * 图填写的点数据
	 * @param list
	 */
	public void batchAddPoint(final List<ApprovalMapPojo> list){
		String addSql = "insert into approval_map(taskId,startLat,startLon,endLat,endLon,pointNum)"
				+ " values(?,?,?,?,?,?)";
		this.jdbcTemplate.batchUpdate(addSql, new BatchPreparedStatementSetter(){
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return list.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ApprovalMapPojo obj = list.get(i);
				ps.setString(1, obj.getTaskId());
				ps.setString(2, obj.getStartLat());
				ps.setString(3, obj.getStartLon());
				ps.setString(4, obj.getEndLat());
				ps.setString(5, obj.getEndLon());
				ps.setString(6, obj.getPointNum());
			}
		});
	}
	
	
	/**
	 * 保存资源信息
	 * @param list
	 */
	public void batchAddRes(final List<ApprovalResPojo> list){
		String sql = "insert into approval_res(taskId,resType,resId,resState,resNum,lon,lat,generId,generName,generNum)"
				+ " values(?,?,?,?,?,?,?,?,?,?)";
		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ApprovalResPojo obj = list.get(i);
				ps.setString(1, obj.getTaskId());
				ps.setString(2, obj.getResType());
				ps.setString(3, obj.getResId());
				ps.setString(4, obj.getResState());
				ps.setString(5, obj.getResNum());
				ps.setString(6, obj.getLon());
				ps.setString(7, obj.getLat());
				ps.setString(8, obj.getGenerId());
				ps.setString(9, obj.getGenerName());
				ps.setString(10, obj.getGenerNum());
			}
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
	}
	
	/**
	 * 得到光交箱数据
	 * @param eid
	 * @return
	 */
	public String getOpticalTopo(String eid) {
		Map<String, String> result = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		
		EqutInfoBean equt = new EqutInfoBean();
		equt.setEid(eid);
		equt.setEtype("3");
		equt = (EqutInfoBean) this.getObject("pdaequt.getEqut",equt);
		if(equt!=null) {
			result.put("centerId", eid);
			result.put("center", equt.getEname());
			
			sb.append(JsonUtil.getJsonString4Map(result));
		}
		return sb.toString();
	}
	
	/**
	 * 得到资源站点的拓扑图
	 * @param siteId
	 * @return
	 */
	public String getSiteTopo(String siteId){
		Map<String, String> result = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		
		StationBaseInfoBean site = new StationBaseInfoBean();
		site.setStationBaseId(Integer.parseInt(siteId));
		site = (StationBaseInfoBean) this.getObject("pdagenerator.getStationBase", site);
		if(site != null){
			result.put("centerId", siteId);
			result.put("center", site.getStationName());
			String sql = "select"
					+ " cableid,cablename,region,length,startDeviceId,startDeviceName,"
					+ " startDeviceType,endDeviceId, endDeviceName,endDeviceType"
					+ " from job_cable where startDeviceId ='"+siteId+"'"
					+ " union all "
					+ " select"
					+ " cableid,cablename,region,length,startDeviceId,startDeviceName,"
					+ " startDeviceType,endDeviceId, endDeviceName,endDeviceType"
					+ " from job_cable where  endDeviceId ='"+siteId+"'";
			List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
			if(TextUtil.isNotNull(list)){
				List<Map<String, String>> lineList = new LinkedList<Map<String,String>>();
				for(Map<String, Object> map : list){
					Map<String, String> lineMap = new HashMap<String, String>();
					String startId = map.get("startDeviceId")+"";
					String endId = map.get("endDeviceId")+"";
					lineMap.put("cableid", map.get("cableid")+"");
					lineMap.put("cablename", map.get("cablename")+"");
					lineMap.put("region", map.get("region")+"");
					if(startId.equals(siteId)){
						lineMap.put("side", "end");
						lineMap.put("sideId", map.get("endDeviceId")+"");
						lineMap.put("sideName", map.get("endDeviceName")+"");
						lineMap.put("sideType", getResType(map.get("endDeviceType")+""));
					}
					if(endId.equals(siteId)){
						lineMap.put("side", "start");
						lineMap.put("sideId", map.get("startDeviceId")+"");
						lineMap.put("sideName", map.get("startDeviceName")+"");
						lineMap.put("sideType", getResType(map.get("startDeviceType")+""));
					}
					lineList.add(lineMap);
				}
				result.put("line", JsonUtil.getJsonString4List(lineList));
			}
			
			sb.append(JsonUtil.getJsonString4Map(result));
		}
		return sb.toString();
	}
	
	/**
	 * 得到类型
	 * @param type
	 * @return
	 */
	String getResType(String type){
		String result = "";
		if(type.equals("1") || type.equals("9203")){
			result = "opticTran";
		}else if(type.equals("2") || type.equals("9205")){
			result = "opticJoint";
		}else if(type.equals("3") || type.equals("9503")){
			result = "station";
		}
		if(TextUtil.isNull(result)){
			result = type;
		}
		return result;
	}
	
	/**
	 * 得到站内资源的
	 * 级联树
	 * @param parentId
	 * @param resType
	 * @return
	 */
	public String getResStr(String parentId,String resType){
		StringBuffer sb = new StringBuffer();
		if(resType.equals("station")){
			GeneratorInfoBean generator = new GeneratorInfoBean();
			generator.setAreano(parentId);
			List<GeneratorInfoBean> list = this.getObjects("pdagenerator.getGenerators", generator);
			if(TextUtil.isNotNull(list)){
				sb.append("[");
				for(GeneratorInfoBean obj : list){
					sb.append("{");
					sb.append("id:'"+obj.getGeneratorId()+"',");
					sb.append("text:'"+obj.getGeneratorName()+"',");
					sb.append("resState:'"+this.getResCondition(obj.getResNum(), obj.getLastUpdateDate()+"", obj.getDeleteFlag())+"',");
					sb.append("resType:'generator'");
					sb.append("},");
				}
				if(sb.toString().endsWith(",")){
					sb.deleteCharAt(sb.length()-1);
				}
				sb.append("]");
			}
		}else if(resType.equals("generator")){
			EqutInfoBean equt = new EqutInfoBean();
			equt.setGid(parentId);
			List<EqutInfoBean> list = this.getObjects("pdaequt.getEqut", equt);
			if(TextUtil.isNotNull(list)){
				sb.append("[");
				for(EqutInfoBean obj : list){
					if(obj.getEname().contains("/")){
						String[] enames = obj.getEname().split("/");
						obj.setEname(enames[enames.length-1]);
					}
					sb.append("{");
					sb.append("id:'"+obj.getEid()+"',");
					sb.append("text:'"+obj.getEname()+"',");
					if(TextUtil.isNotNull(obj.getEmodel())){
						sb.append("model:'"+getNumByStr(obj.getEmodel())+"',");
					}else{
						sb.append("model:'"+getNumByStr("0")+"',");
					}
					sb.append("col:'"+obj.getJijialiehao()+"',");
					if(TextUtil.isNotNull(obj.getJijiahanghao())){
						sb.append("row:'"+getNumByStr(obj.getJijiahanghao())+"',");
					}else{
						sb.append("row:'1',");
					}
					sb.append("resType:'rack'");
					sb.append("},");
				}
				if(sb.toString().endsWith(",")){
					sb.deleteCharAt(sb.length()-1);
				}
				sb.append("]");
			}
		}else if(resType.equals("rack")){
			//机架需要获取ODM 和网元设备
			sb.append("[");
			ODMInfoBean odm = new ODMInfoBean();
			odm.setEid(parentId);
			List<ODMInfoBean> odmList = this.getObjects("pdaequt.getODM", odm);
			if(TextUtil.isNotNull(odmList)){
				for(ODMInfoBean obj : odmList){
					if(obj.getOdmName().contains("/")){
						String[] odmNames = obj.getOdmName().split("/");
						obj.setOdmName(odmNames[odmNames.length-1]);
					}
					sb.append("{");
					sb.append("id:'"+obj.getOdmId()+"',");
					sb.append("text:'"+obj.getOdmName()+"',");
					sb.append("posX:'"+obj.getPosX()+"',");
					sb.append("posY:'"+obj.getPosY()+"',");
					sb.append("resType:'odm',leaf:true");
					sb.append("},");
				}
				if(sb.toString().endsWith(",")){
					sb.deleteCharAt(sb.length()-1);
				}
			}
			
			//传输网元
			DeviceInfoBean device = new DeviceInfoBean();
			device.setEid(parentId);
			List<DeviceInfoBean> deviceList = this.getObjects("device.getDeviceGrid", device);
			if(TextUtil.isNotNull(deviceList)){
				if(TextUtil.isNotNull(odmList)){
					sb.append(",");
				}
				for(DeviceInfoBean obj : deviceList){
					String deviceName = obj.getDeviceName();
					if(TextUtil.isNotNull(obj.getAlias())){
						deviceName = obj.getAlias();
					}
					sb.append("{");
					sb.append("id:'"+obj.getId()+"',");
					sb.append("text:'"+deviceName+"',");
					sb.append("posX:'"+obj.getPosX()+"',");
					sb.append("posY:'"+obj.getPosY()+"',");
					sb.append("resType:'ne'");
					sb.append("},");
				}
				if(sb.toString().endsWith(",")){
					sb.deleteCharAt(sb.length()-1);
				}
			}
			sb.append("]");
		}else if(resType.equals("ne")){
			sb.append("[");
			CardInfoBean card = new CardInfoBean();
			card.setDeviceId(Integer.parseInt(parentId));
			List<CardInfoBean> cardList = this.getObjects("device.getCardGrid", card);
			if(TextUtil.isNotNull(cardList)){
				for(CardInfoBean obj: cardList){
					sb.append("{");
					sb.append("id:'"+obj.getId()+"',");
					sb.append("text:'"+obj.getCardName()+"',");
					sb.append("resType:'neCard',leaf:true");
					sb.append("},");
				}
				if(sb.toString().endsWith(",")){
					sb.deleteCharAt(sb.length()-1);
				}
			}
			sb.append("]");
		}else if(resType.equals("optical")) {
			sb.append("[");
			ODMInfoBean odm = new ODMInfoBean();
			odm.setEid(parentId);
			List<ODMInfoBean> odmList = this.getObjects("pdaequt.getODM", odm);
			if(TextUtil.isNotNull(odmList)){
				for(ODMInfoBean obj : odmList){
					if(obj.getOdmName().contains("/")){
						String[] odmNames = obj.getOdmName().split("/");
						obj.setOdmName(odmNames[odmNames.length-1]);
					}
					sb.append("{");
					sb.append("id:'"+obj.getOdmId()+"',");
					sb.append("text:'"+obj.getOdmName()+"',");
					sb.append("posX:'"+obj.getPosX()+"',");
					sb.append("posY:'"+obj.getPosY()+"',");
					sb.append("resType:'odm',leaf:true");
					sb.append("},");
				}
				if(sb.toString().endsWith(",")){
					sb.deleteCharAt(sb.length()-1);
				}
			}
			sb.append("]");
		}
		return sb.toString();
	}
	
	/**
	 * 将字母转成数字
	 * @param str
	 * @return
	 */
	int getNumByStr(String str){
		int num =0;
		String reg = "[a-zA-Z]";
		boolean isStr = str.matches(reg);
		if(isStr){
			str = str.toUpperCase();
			char start = 'A';
			char end = 'Z';
			for (int i = 0; i < (int) (end - start + 1); i++) {
				str = str.replaceAll(String.valueOf((char) (start + (char) i)),
						String.valueOf(i + 1));
			}
			num = Integer.parseInt(str);
		}else{
			num = Integer.parseInt(str);
		}
		return num;
	}
	
	/**
	 * 得到端子端口的返回语句
	 * @param parentId
	 * @param resType
	 * @return
	 */
	public String getPointJson(String parentId,String resType){
		StringBuffer sb = new StringBuffer();
		if(resType.equals("odm")){
			sb.append("[");
			PointInfoBean point = new PointInfoBean();
			point.setOdmId(Integer.parseInt(parentId));
			List<PointInfoBean> pointList = this.getObjects("pdaequt.getPoint", point);
			if(TextUtil.isNotNull(pointList)){
				for(PointInfoBean obj : pointList){
					sb.append("{");
					sb.append("id:'"+obj.getId()+"',");
					sb.append("text:'"+obj.getPid()+"',");
					sb.append("row:'"+obj.getProwno()+"',");//行信息
					sb.append("col:'"+obj.getPlineno()+"',");//列信息
					sb.append("state:'"+obj.getPstat()+"',");
					sb.append("resType:'odmPoint',leaf:true");
					sb.append("},");
				}
				if(sb.toString().endsWith(",")){
					sb.deleteCharAt(sb.length()-1);
				}
			}
			sb.append("]");
		}else if(resType.equals("neCard")){
			sb.append("[");
			PointBean point = new PointBean();
			point.setCardId(Integer.parseInt(parentId));
			List<PointBean> pointList = this.getObjects("device.getPointGrid", point);
			if(TextUtil.isNotNull(pointList)){
				for(PointBean obj:pointList){
					sb.append("{");
					sb.append("id:'"+obj.getId()+"',");
					sb.append("text:'"+obj.getPointName()+"',");
					sb.append("resType:'nePoint',leaf:true");
					sb.append("},");
				}
				if(sb.toString().endsWith(",")){
					sb.deleteCharAt(sb.length()-1);
				}
			}
			
			sb.append("]");
		}
		return sb.toString();
	}
	
	/**
	 * 得到资源处理类型
	 * @param resNum
	 * @param updateTime
	 * @param del
	 * @return
	 */
	String getResCondition(String resNum,String updateTime,String del){
		String resType = "";
		if(TextUtil.isNotNull(del)){
			resType = "del";
		}else{
			if(TextUtil.isNotNull(resNum) && TextUtil.isNotNull(updateTime)){
				resType = "update";
			}
			if(TextUtil.isNull(resNum)){
				resType = "add";
			}
		}
		return resType;
	}
	
	/**
	 * 得到核查站点数据
	 * @param obj
	 * @return
	 */
	public List<Map<String, Object>> getHisSite(ApprovalTaskPojo obj){
		List<Map<String, Object>> list = new LinkedList();
		String sql = "select distinct t.id,t.taskTitle,"
				+ " t.approvaler,t.approvalerId,t.sender,t.createTime,"
				+ " group_concat(r.resId,'_',r.lat,'_',r.lon,'_',"
				+ " (select stationName from job_stationbase where stationBaseId = r.resId)"
				+ "  order by r.taskId) as mapPoint"
				+ " from approval_task t , approval_res r where"
				+ " t.id = r.taskId and t.deleteFlag =0 "
				+ " and t.delFlag ='Y' and t.taskState in ('已领单','已派发')"
				+ " and  t.resType in ('station','基站') ";
		/*if(TextUtil.isNotNull(obj.getCity())){
			sql +=" and t.city ='"+obj.getCity()+"'";
		}
		if(TextUtil.isNotNull(obj.getCounty())){
			sql +=" and t.county ='"+obj.getCounty()+"'";
		}*/
		if(TextUtil.isNotNull(obj.getGroupId())) {
			sql +=" and t.groupId ='"+obj.getGroupId()+"'";
		}
		/*if(TextUtil.isNotNull(obj.getLatitude()) && TextUtil.isNotNull(obj.getLongitude())){
			double[] arr = functions.getAround(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()), 2000);
			String lats = String.valueOf(arr[0]);
			String lons = String.valueOf(arr[1]);
			String late = String.valueOf(arr[2]);
			String lone = String.valueOf(arr[3]);
			sql +=" and ("
				+ " r.lat >= "+lats+""
				+ " and r.lat <= "+late+""
				+ " and r.lon >= "+lons+""
				+ " and r.lon <= "+lone+""
				+ ")";
		}*/
		sql +=" group by r.taskId";
		list = this.jdbcTemplate.queryForList(sql);
		return list;
	}
	
	/**
	 * 返回画的点数据
	 * @param obj
	 * @return
	 */
	public List<Map<String, Object>> getResMapArea(ApprovalTaskPojo obj){
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		String sql = "select startLat,startLon,endLat,endLon"
				+ " from approval_map where taskId = '"+obj.getId()+"'"
				+ " order by pointNum ";
		list = this.jdbcTemplate.queryForList(sql);
		return list;
	}
	
	/**
	 * 得到工单及点数据
	 * @param obj
	 * @return
	 */
	public List<Map<String, Object>> getHisMap(ApprovalTaskPojo obj){
		List<Map<String, Object>> list = new LinkedList();
		String sql = "select distinct t.id,t.taskTitle,t.sender,t.createTime,"
				+ " if(isnull(t.approvalerId),'',t.approvalerId) as approvalerId,"
				+ " if(isnull(t.approvaler),'',t.approvaler) as approvaler,"
				+ " group_concat(m.pointNum,'_',m.startLat,'_',m.startLon order by m.pointNum)"
				+ " as mapPoint"
				+ " from approval_task t ,approval_map m where  t.deleteFlag =0 and t.delFlag ='Y'"
				+ " and t.taskState in ('已领单','已派发')"
				+ " and t.id = m.taskId "	
				+ "";
		if(TextUtil.isNotNull(obj.getResType())){
			sql +=" and t.resType like '%"+obj.getResType()+"%'";
		}
		if(TextUtil.isNotNull(obj.getCity())){
			sql +=" and t.city ='"+obj.getCity()+"'";
		}
		if(TextUtil.isNotNull(obj.getCounty()) && !(obj.getCounty().contains("*"))){
			sql +=" and t.county like '%"+obj.getCounty()+"%'";
		}
		if(TextUtil.isNotNull(obj.getGroupId())) {
			sql+=" and ((t.groupId ='"+obj.getGroupId()+"' and t.approvalerId is null )";
			if(TextUtil.isNotNull(obj.getApprovalerId())) {
				sql+=" or (t.approvalerId ='"+obj.getApprovalerId()+"')";
			}
			sql +=" )";
		}
		if(TextUtil.isNotNull(obj.getId())) {
			sql+=" and t.id='"+obj.getId()+"'";
		}
		
		if(TextUtil.isNotNull(obj.getLatitude()) && TextUtil.isNotNull(obj.getLongitude())){
			double[] arr = functions.getAround(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()), 2000);
			String lats = String.valueOf(arr[0]);
			String lons = String.valueOf(arr[1]);
			String late = String.valueOf(arr[2]);
			String lone = String.valueOf(arr[3]);
			sql +=" and ("
				+ " m.startLat >= "+lats+""
				+ " and m.startLat <= "+late+""
				+ " and m.startLon >= "+lons+""
				+ " and m.startLon <= "+lone+""
				+ ")";
		}
		sql +=" group by m.taskId ";
		list = this.jdbcTemplate.queryForList(sql);
		return list;
	}
	
	/**
	 * 返回加载地图的数据
	 * @param locatArea
	 * @return
	 */
	public Map<String, String> locatMap(String locatArea){
		Map<String, String> map = new LinkedHashMap<String, String>();
		String sql = "select longitude,latitude from wellinfo"
				+ "  where wellName like '%"+locatArea+"%' limit 1";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			Map<String, Object> resMap = list.get(0);
			map.put("longitude", resMap.get("longitude")+"");
			map.put("latitude", resMap.get("latitude")+"");
		}
		return map;
	}
	
	
	/**
	 * 根据关键字查询站点信息
	 * @param locatArea
	 * @return
	 */
	public List<LineSegmentInfo> locatSite(String locatArea){
		List<LineSegmentInfo> list = new LinkedList<LineSegmentInfo>();
		String sql = "select stationBaseId as id,stationName as name ,"
				+ " 'station' as type,lon as longitude,lat as latitude, "
				+ " resNum as resNum"
				+ " from job_stationbase where stationName like '%"+locatArea+"%'"
				+ " and deleteFlag =0 and lon is not null and lat is not null ";
		List<Map<String, Object>> resList = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(resList)){
			for(Map<String, Object> map : resList){
				LineSegmentInfo line = new LineSegmentInfo();
				line.setStartId(map.get("id")+"");
				line.setStartName(map.get("name")+"");
				line.setStartType(map.get("type")+"");
				line.setStartLon(map.get("longitude")+"");
				line.setStartLat(map.get("latitude")+"");
				line.setStartResNum(map.get("resNum")+"");
				line.setStartState("add");
				list.add(line);
			}
		}
		return list;
	}
	
	/**
	 * 执行更新语句
	 * @param id
	 * @param pointList
	 */
	public void upApprovalTask(String id ,List<Map<String, Object>> pointList) {
		if(TextUtil.isNotNull(pointList)) {
			String wkt = "POLYGON  ((";
			String point0 = "";
			for(int i=0;i<pointList.size();i++){
				Map<String,Object> map = pointList.get(i);
				if(i== 0){
					point0 = map.get("longitude")+" "+map.get("latitude");
				}
				wkt +=""+map.get("longitude")+" "+map.get("latitude")+",";
			}
			if(wkt.endsWith(",")){
				wkt = wkt.substring(0, wkt.length()-1);
			}
			if(TextUtil.isNotNull(point0)){
				wkt  += ","+point0;
			}
			wkt += "))";
			String sql = "update approval_task"
					+ " set polygongeo =POLYGONFROMTEXT('"+wkt+"')"
					+ " where id ='"+id+"'";
			this.jdbcTemplate.execute(sql);
		}
	}
	
	/**
	 * 派发删除实例
	 * @param id
	 */
	public void sendDelZone(String id,List<Map<String, Object>> pointList){
		ApprovalTaskPojo approval = this.getApprovalObj(id);
		ApprovalMapPojo taskMap = new ApprovalMapPojo();
		taskMap.setTaskId(id);
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00000000");
		String wkt = "";
		if(TextUtil.isNotNull(pointList)){
			wkt = "POLYGON  ((";
			String point0 = "";
			for(int i=0;i<pointList.size();i++){
				Map<String,Object> map = pointList.get(i);
				if(i== 0){
					point0 = map.get("longitude")+" "+map.get("latitude");
				}
				wkt +=""+map.get("longitude")+" "+map.get("latitude")+",";
			}
			if(wkt.endsWith(",")){
				wkt = wkt.substring(0, wkt.length()-1);
			}
			if(TextUtil.isNotNull(point0)){
				wkt  += ","+point0;
			}
			wkt += "))";
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(date);
			String num = date.getTime()+"";
			String sql = ""
					+ " INSERT INTO OSL_DELBYZONE_TASK(UUID, YC_JOBID, TASK_NAME, ZONE_WKT, TASK_STATUS, TASK_CREAT_TIME)"
					+ " VALUES("
					+ "'"+num+"',"
					+ "'"+id+"',"
					+ "'"+approval.getTaskTitle()+"',"
					+ "'"+wkt+"',"
					+ "'0',"
					+ "to_date('"+time+"','yyyy-mm-dd hh24:mi:ss')"
					+ ")";
			this.irmsjdbcTemplate.execute(sql);
		}
	}
	
	/**
	 * 得到排重的工单操作
	 * @param title
	 * @return
	 */
	public List<Map<String, Object>> getTaskList(String title){
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		try {
			String sql = "select id from approval_task where taskTitle ='"+title+"'";
			list = this.jdbcTemplate.queryForList(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 得到划定区域内的点
	 * @param pointList
	 * @return
	 */
	public String getPolonObj(List<Map<String, Object>> pointList,String type) {
		String objects = "";
		String param="where=EXAMINESTATUS+is+null&returnGeometry=false&f=json&_ts=1506580458422&returnIdsOnly=true&inSR=4326"
				+ "&geometryType=esriGeometryPolygon&spatialRel=esriSpatialRelIntersects";
		if(TextUtil.isNotNull(pointList)) {
			String geometry="geometry={\"rings\":[[";
			for(int i=0;i<pointList.size();i++) {
				Map<String,Object> map = pointList.get(i);
				geometry +="["+map.get("longitude")+","+map.get("latitude")+"],";
			}
			Map<String,Object> zeroMap =pointList.get(0);
			geometry+="["+zeroMap.get("longitude")+","+zeroMap.get("latitude")+"]";
			geometry +="]]}";
			param +="&"+geometry;
		}
		if(type.equals("point")) {
			String retStr = RequestUtil.sendGet(InterfaceAddr.POINT_QUERY, param);
			Map<String,Object> objMap =JsonUtil.getMap4Json(retStr);
			String ids = objMap.get("objectIds") +"";
			objects = ids.substring(1, ids.length()-1);
		}else {
			String retStr = RequestUtil.sendGet(InterfaceAddr.LINE_QUERY, param);
			Map<String,Object> objMap =JsonUtil.getMap4Json(retStr);
			String ids = objMap.get("objectIds") +"";
			objects = ids.substring(1, ids.length()-1);
		}
		
		return objects;
	}
	
	
	/**
	 * 发送删除区域
	 * @param id
	 * @param pointList
	 * @param objId
	 * @return
	 */
	public String sendDelZone(String id,List<Map<String, Object>> pointList,String pointId,String lineId) {
		String wkt = "";
		if(TextUtil.isNotNull(pointList)){
			wkt = "POLYGON  ((";
			String point0 = "";
			for(int i=0;i<pointList.size();i++){
				Map<String,Object> map = pointList.get(i);
				if(i== 0){
					point0 = map.get("longitude")+" "+map.get("latitude");
				}
				wkt +=""+map.get("longitude")+" "+map.get("latitude")+",";
			}
			if(wkt.endsWith(",")){
				wkt = wkt.substring(0, wkt.length()-1);
			}
			if(TextUtil.isNotNull(point0)){
				wkt  += ","+point0;
			}
			wkt += "))";
		}
		
		String json = "json={\"wkt\":\""+wkt+"\","
				+ " \"examineid\":\""+id+"\","
				+ " \"point\":\""+pointId+"\","
				+ " \"line\":\""+lineId+"\""
				+ "}";
		String outIN = RequestUtil.HttpRequest(InterfaceAddr.ADD_FRAME, "POST", json);
		
		return "";
	}
	
	
	/**
	 * 执行删除任务
	 * @param taskid
	 */
	public void delIRMStask(String taskid,String type) {
		if(TextUtil.isNotNull(taskid)) {
			String upSql = "update INSPUR_GIS.all_rectify_area"
					+ " set EXAMINESTATUS = 2 where EXAMINEID ='"+taskid+"'";
			this.irmsjdbcTemplate.execute(upSql);
		}
		
	}
	
	/**
	 * 根据工单ID得到站点信息
	 * @param taskId
	 * @return
	 */
	public String getTaskSite(String taskId){
		String result ="";
		String sql  = "select s.stationBaseId as resId,s.stationName as resName,"
				+ " 'station' as resType,s.resNum as resNum "
				+ " from approval_res r,job_stationbase s"
				+ " where  s.stationBaseId =r.resId and  r.taskId ='"+taskId+"' ";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			Map<String,Object> map = list.get(0);
			result ="{'resId':'"+map.get("resId")+"',"
					+ "'resName':'"+map.get("resName")+"',"
					+ "'resType':'"+map.get("resType")+"',"
					+ "'resNum':'"+map.get("resNum")+"'}";
		}
		return result;
	}
	/**
	 * 获取驳回的资源列表
	 * @param obj
	 * @return
	 */
	public List<ApprovalResRejectPojo> getResReject(ApprovalResRejectPojo obj){
		List<ApprovalResRejectPojo> list = this.getObjects("approval.getResReject", obj);
		return list;
	}
	
	
	/**
	 * 根据组ID得到组
	 * 下面的用户信息
	 * @param groupId
	 * @return
	 */
	public List<Map<String, Object>> getGroupUser(String groupId){
		String sql = "select username,realname,phoneNumber"
				+ " from sys_user where groupId = '"+groupId+"' ";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		return list;
	}
	
	
	/**
	 * 增加驳回资源
	 * @param obj
	 * @return
	 */
	public int addResReject(ApprovalResRejectPojo obj){
		if(TextUtil.isNull(obj.getRejectStr())) {
			obj.setRejectStr("驳回");
		}
		int id = (Integer) this.insert("approval.addResReject", obj);
		
		return id;
	}
	
	
	/**
	 * 根据资源类型得到
	 * 相应管线及引上数据
	 * 
	 * @param resType
	 * @param resId
	 * @return
	 */
	public String getRadiate(String resType,String resId) {
		String jsonStr = "{info:[";
		String sql = "";
		if(resType.equals("well")) {
			sql ="select s.id,s.name,s.type,s.startId,"
				+ " (select wellName from wellinfo where wellId = s.startId) as startName,"
				+ " (select latitude from wellinfo where wellId = s.startId) as startLat,"
				+ " (select longitude from wellinfo where wellId = s.startId) as startLon,"
				+ " 'well' as startType,"
				+ " s.endId,"
				+ " (select wellName from wellinfo where wellId = s.endId) as endName,"
				+ " (select latitude from wellinfo where wellId = s.endId) as endLat,"
				+ " (select longitude from wellinfo where wellId = s.endId) as endLon,"
				+ " 'well' as endType"
				+ "  from ("
				+ " select p.pipeSegmentId as id,p.pipeSegmentName as name,'pipe' as 'type',startDeviceId as startId,endDeviceId as endId"
				+ " from pipesegmentinfo p where p.startDeviceId ='"+resId+"'"
				+ " union all"
				+ " select p.pipeSegmentId as id,p.pipeSegmentName as name,'pipe' as 'type',startDeviceId as startId,endDeviceId as endId"
				+ " from pipesegmentinfo p where p.endDeviceId = '"+resId+"'"
				+ " ) s"
				+ "";
		}else if(resType.equals("pole")) {
			sql ="select s.id,s.name,s.type,s.startId,"
				+ " (select poleName from poleinfo where poleId = s.startId) as startName,"
				+ " (select poleLatitude from poleinfo where poleId = s.startId) as startLat,"
				+ " (select poleLongitude from poleinfo where poleId = s.startId) as startLon,"
				+ " 'pole' as startType,"
				+ " s.endId,"
				+ " (select poleName from poleinfo where poleId = s.endId) as endName,"
				+ " (select poleLatitude from poleinfo where poleId = s.endId) as endLat,"
				+ " (select poleLongitude from poleinfo where poleId = s.endId) as endLon,"
				+ " 'pole' as endType"
				+ " from ("
				+ " select p.poleLineSegmentId as id,p.poleLineSegmentName as name ,'poleLine' as type,p.startDeviceId as startId,p.endDeviceId as endId"
				+ " from polelinesegmentinfo p where p.startDeviceId ='"+resId+"'"
				+ " union all"
				+ " select p.poleLineSegmentId as id,p.poleLineSegmentName as name ,'poleLine' as type,p.startDeviceId as startId,p.endDeviceId as endId"
				+ " from polelinesegmentinfo p where p.endDeviceId ='"+resId+"'"
				+ " ) s";
		}
		if(TextUtil.isNotNull(sql)) {
			List<Map<String, Object>> resList = this.jdbcTemplate.queryForList(sql);
			for(int i=0;i<resList.size();i++) {
				Map<String, Object> map  = resList.get(i);
				jsonStr+="{"
						+ "'id':'"+map.get("id")+"','name':'"+map.get("name")+"','type':'"+map.get("type")+"',"
						+ "start:{'id':'"+map.get("startId")+"','name':'"+map.get("startName")+"','state':'update','lat':'"+map.get("startLat")+"','lon':'"+map.get("startLon")+"','type':'well'},"
						+ "end:{'id':'"+map.get("endId")+"','name':'"+map.get("endName")+"','state':'update','lat':'"+map.get("endLat")+"','lon':'"+map.get("endLon")+"','type':'well'}"
						+ "},";
			}
			if(jsonStr.endsWith(",")) {
				jsonStr = jsonStr.substring(0, jsonStr.length()-1);
			}
		}
		jsonStr +="]}";
		return jsonStr;
	}
	
	/**
	 * 执行删除驳回记录
	 * @param taskId
	 * @param resType
	 * @param resId
	 */
	public void delRejectRes(String user,String resType,String resId) {
		String sql ="update approval_resreject"
				+ " set deleteFlag ='1' "
				+ " where resType ='"+resType+"'"
				+ " and resId = '"+resId+"'";
		this.jdbcTemplate.execute(sql);
	}
	
	
	/**
	 * 得到提醒消息
	 * @param userName
	 * @return
	 */
	public Map<String, String> getWarnMsg(String userName) {
		Map<String, String> result =  new HashMap<String, String>();
		String workSql = "select taskTitle as title,id as id from approval_task"
				+ " where 1=1 and  flowName ='send' and taskState ='处理完成' "
				+ "  order by finishTime desc limit 1";
		List<Map<String, Object>> taskList = this.jdbcTemplate.queryForList(workSql);
		if(TextUtil.isNotNull(taskList)) {
			Map<String, Object> taskMap = taskList.get(0);
			result.put("taskTitle", taskMap.get("title")+"");
			result.put("taskId", taskMap.get("id")+"");
		}
		
		String rejSql = "select r.resName,r.taskId,"
				+ " (select taskTitle from approval_task where id = r.taskId) as taskTitle"
				+ " from approval_resreject r ,wellinfo w "
				+ " where r.resId = w.wellId "
				+ " and  unix_timestamp(w.lastUpdateDate) > unix_timestamp(r.createTime)"
				+ "  and r.deleteFlag ='0' and w.deletedFlag ='0'"
				+ " and r.resType ='well' and r.resId = w.wellId and rejectUser ='"+userName+"'"
				+ " order by w.lastUpdateDate desc limit 1";
		List<Map<String, Object>> rejList = this.jdbcTemplate.queryForList(rejSql);
		if(TextUtil.isNotNull(rejList)) {
			Map<String, Object> rejMap = rejList.get(0);
			result.put("rejTaskId", rejMap.get("taskId")+"");
			result.put("rejRes", rejMap.get("resName")+"");
			result.put("rejTaskTitle", rejMap.get("taskTitle")+"");
		}
		return result;
	}
	
	
	/**
	 * 得到查詢基站的信息
	 * @param siteName
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Map<String, Object>> getSeachSite(String siteName,Integer start,Integer limit){
		String sql = "select (@i:=@i+1) as id,s.stationBaseId,g.generatorId,s.stationName,"
				+ " s.region,s.lon,s.lat,s.parties,g.generatorName,g.szlc,"
				+ " s.resNum as siteNum,g.resNum as generNum"
				+ " from job_stationbase s left join job_generator g on s.stationBaseId = g.areano,"
				+ " (select @i:=0) as i"
				+ " where s.deleteFlag = 0  and s.stationName like '%"+siteName+"%'"
				+ "LIMIT 0,100  ";
		return this.jdbcTemplate.queryForList(sql);
	}
	
	
	/**
	 * 得到水文数据
	 * @return
	 */
	public List<Map<String, Object>> getHydrology(){
		String sql ="select group_concat(address) as  hy_name,group_concat(latlon) as latlon,a.hy_value from ("
				+ " select address,CONCAT(latitude,'-',longitude) as latlon,hy_value from hydrology where hy_value =4) a "
				+ " group by a.hy_value ";
		return this.jdbcTemplate.queryForList(sql);
	}
	
 	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public JdbcTemplate getIrmsjdbcTemplate() {
		return irmsjdbcTemplate;
	}
	public void setIrmsjdbcTemplate(JdbcTemplate irmsjdbcTemplate) {
		this.irmsjdbcTemplate = irmsjdbcTemplate;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	@Override
	public JdbcTemplate getTemplate() {
		return jdbcTemplate;
	}
	
}
