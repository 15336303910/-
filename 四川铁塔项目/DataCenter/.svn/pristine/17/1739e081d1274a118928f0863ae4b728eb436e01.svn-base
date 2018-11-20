package com.function.index.greyList.action;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.function.index.greyList.model.GreyList;
import com.function.index.greyList.service.IGreyListService;
import com.systemConfig.model.DataTableResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 灰名单处理
 * 
 */
@Controller("com.function.index.greyList.action.GreyListExpireAction")
@RequestMapping(value = "/greyListExpireAction")
public class GreyListExpireAction {
	// 日期格式化
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private IGreyListService iGreyListService;

	/**
	 * 
	 * 获取到期灰名单数据列表
	 */
	@RequestMapping(value = "/findGreyListOfExpireDate.ilf")
	public void findGreyListOfExpireDate(@RequestParam String tableparam, @RequestParam String conditions,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long sEcho = 0L;
		Integer displayStart = 0;
		Integer iDisplayLength = 0;
		String saName = "";
		String glType = "";

		// 拼装分页查询条件
		JSONArray jsons = JSONArray.fromObject(tableparam);
		if (jsons != null && jsons.size() != 0) {
			for (int i = 0; i < jsons.size(); i++) {
				JSONObject json = JSONObject.fromObject(jsons.get(i));
				String key = json.getString("name");
				if (key.equals("sEcho")) {
					sEcho = Long.parseLong(json.getString("value"));
				} else if (key.equals("iDisplayStart")) {
					displayStart = Integer.parseInt(json.getString("value"));
				} else if (key.equals("iDisplayLength")) {
					iDisplayLength = Integer.parseInt(json.getString("value"));
				}
			}
		}

		JSONArray jsonCondition = JSONArray.fromObject(conditions);
		if (conditions != null && jsonCondition.size() != 0) {
			for (int i = 0; i < jsonCondition.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonCondition.get(i));

				String keyName = jsonObject.getString("name");

				if ("saName".equals(keyName)) {
					saName = jsonObject.getString("value");
				}

				if ("glType".equals(keyName)) {
					glType = jsonObject.getString("value");
				}

			}
		}

		Boolean IS_PROVICE = false;
		String CITY_NAME = "";
		Object loginObject = request.getSession().getAttribute("LoginUserInfo");
		if (loginObject != null) {
			Map<String, Object> loginUser = (HashMap<String, Object>) loginObject;
			if (loginUser.get("BELONG_AREA").toString().indexOf("四川") != -1) {
				IS_PROVICE = true;
				CITY_NAME = loginUser.get("BELONG_AREA").toString();
			} else {
				CITY_NAME = loginUser.get("BELONG_AREA").toString();
			}
			if (CITY_NAME.length() > 2) {
				CITY_NAME = CITY_NAME.substring(0, 2);
			}
		}

		String pageHql = "from GreyList where expireStatus!=0";
		pageHql = getHqlWhere(saName, glType, pageHql);

		// 获取同一地市的数据
		if (!IS_PROVICE) {
			pageHql += " and city like '%" + CITY_NAME + "%'";
		}

		List<GreyList> dataList = iGreyListService.getGreyList(pageHql);// 全部数据

		pageHql += " order by expireTime desc";

		List<GreyList> greyList = iGreyListService.getGreyListPage(pageHql, displayStart, iDisplayLength);

		List<Map<String, Object>> objectList = new ArrayList<Map<String, Object>>();

		if (greyList != null && greyList.size() > 0) {
			// 重新筛选数据，包括数据格式切换
			for (GreyList model : greyList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", model.getId());
				map.put("city", model.getCity());
				map.put("saCode", model.getSaCode());
				map.put("glType", model.getGlType());
				map.put("glDescribe", model.getGlDescribe());
				map.put("originator", model.getOriginator());
				map.put("glsTime", model.getGlsTime() == null ? "" : formatter.format(model.getGlsTime()));
				map.put("validTime", model.getValidTime() + "个月");
				map.put("expireTime", model.getExpireTime() == null ? "" : formatter.format(model.getExpireTime()));
				map.put("expireStatus", model.getExpireStatus() == 1 ? "即将到期" : "已到期");
				objectList.add(map);
			}
		}

		DataTableResult<Map<String, Object>> tableData = new DataTableResult<Map<String, Object>>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(objectList);
		tableData.setiTotalRecords(dataList.size() + 1);
		tableData.setiTotalDisplayRecords(dataList.size() + 1);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}

	private String getHqlWhere(String saName, String glType, String hql) {
		if (StringUtils.isNotEmpty(saName)) {
			hql += " and saName like '%" + saName + "%'";
		}

		if (StringUtils.isNotEmpty(glType)) {
			hql += " and glType like '%" + glType + "%'";
		}

		// 流程已审批完成的
		hql += " and procedureSegment='3'";

		// 获取到期时间在十天以内的数据
		hql += " and expireTime between  sysdate and sysdate+10 ";
		return hql;
	}

	/**
	 * 导出到期灰名单数据
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/exportExcel.ilf")
	@ResponseBody
	public void createExcel(@RequestParam String saName, @RequestParam String glType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 总列数
		int columnCount = 9;

		// 创建HSSFWorkbook对象(excel的文档对象)
		HSSFWorkbook wb = new HSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		HSSFSheet sheet = wb.createSheet("灰名单到期确认");

		// 在sheet里创建第一行
		HSSFRow row1 = sheet.createRow(0);

		// 创建单元格
		HSSFCell cell = row1.createCell(0);

		// 1.生成字体对象
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("新宋体");

		// 2.生成样式对象
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置居中样式
		style.setFont(font); // 调用字体样式对象
		style.setWrapText(true);

		// 3.单元格应用样式
		cell.setCellStyle(style);

		// 设置单元格内容
		cell.setCellValue("灰名单到期确认");

		// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnCount));

		// 在sheet里创建第二行
		HSSFRow row2 = sheet.createRow(1);

		// 创建单元格并设置单元格内容及样式
		HSSFCell cityCell0 = row2.createCell(0);
		cityCell0.setCellStyle(style);
		cityCell0.setCellValue("序号");// 地市与月份不能从数据表获取，因此直接在此添加。

		HSSFCell dateCell1 = row2.createCell(1);
		dateCell1.setCellStyle(style);
		dateCell1.setCellValue("地市");

		HSSFCell dateCell2 = row2.createCell(2);
		dateCell2.setCellStyle(style);
		dateCell2.setCellValue("站址编号");

		HSSFCell dateCell3 = row2.createCell(3);
		dateCell3.setCellStyle(style);
		dateCell3.setCellValue("灰名单类型");

		HSSFCell dateCell4 = row2.createCell(4);
		dateCell4.setCellStyle(style);
		dateCell4.setCellValue("灰名单说明");

		HSSFCell dateCell5 = row2.createCell(5);
		dateCell5.setCellStyle(style);
		dateCell5.setCellValue("发起人");

		HSSFCell dateCell6 = row2.createCell(6);
		dateCell6.setCellStyle(style);
		dateCell6.setCellValue("进入灰名单时间");

		HSSFCell dateCell7 = row2.createCell(7);
		dateCell7.setCellStyle(style);
		dateCell7.setCellValue("灰名单有效期");

		HSSFCell dateCell8 = row2.createCell(8);
		dateCell8.setCellStyle(style);
		dateCell8.setCellValue("到期时间");

		HSSFCell dateCell9 = row2.createCell(9);
		dateCell9.setCellStyle(style);
		dateCell9.setCellValue("是否到期");

		// 宽度自适应(循环设置不生效，暂时这样写)
		sheet.autoSizeColumn((short) 0);
		sheet.autoSizeColumn((short) 1);
		sheet.autoSizeColumn((short) 2);
		sheet.autoSizeColumn((short) 3);
		sheet.autoSizeColumn((short) 4);
		sheet.autoSizeColumn((short) 5);
		sheet.autoSizeColumn((short) 6);
		sheet.autoSizeColumn((short) 7);
		sheet.autoSizeColumn((short) 8);
		sheet.autoSizeColumn((short) 9);

		Boolean IS_PROVICE = false;
		String CITY_NAME = "";
		Object loginObject = request.getSession().getAttribute("LoginUserInfo");
		if (loginObject != null) {
			Map<String, Object> loginUser = (HashMap<String, Object>) loginObject;
			if (loginUser.get("BELONG_AREA").toString().indexOf("四川") != -1) {
				IS_PROVICE = true;
				CITY_NAME = loginUser.get("BELONG_AREA").toString();
			} else {
				CITY_NAME = loginUser.get("BELONG_AREA").toString();
			}
			if (CITY_NAME.length() > 2) {
				CITY_NAME = CITY_NAME.substring(0, 2);
			}
		}

		// 查询数据
		String hql = "from GreyList where expireStatus!=0";
		hql = getHqlWhere(saName, glType, hql);

		// 获取同一地市的数据
		if (!IS_PROVICE) {
			hql += " and city like '%" + CITY_NAME + "%'";
		}

		List<GreyList> dataList = iGreyListService.getGreyList(hql);// 全部数据

		// 数据添加
		for (GreyList data : dataList) {
			HSSFRow rowx = sheet.createRow(2);// 从第三列开始添加数据

			HSSFCell cellData0 = rowx.createCell(0);
			cellData0.setCellStyle(style);
			cellData0.setCellValue(data.getId().toString());

			HSSFCell cellData1 = rowx.createCell(1);
			cellData1.setCellStyle(style);
			cellData1.setCellValue(data.getCity());

			HSSFCell cellData2 = rowx.createCell(2);
			cellData2.setCellStyle(style);
			cellData2.setCellValue(data.getSaCode());

			HSSFCell cellData3 = rowx.createCell(3);
			cellData3.setCellStyle(style);
			cellData3.setCellValue(data.getGlType());

			HSSFCell cellData4 = rowx.createCell(4);
			cellData4.setCellStyle(style);
			cellData4.setCellValue(data.getGlDescribe());

			HSSFCell cellData5 = rowx.createCell(5);
			cellData5.setCellStyle(style);
			cellData5.setCellValue(data.getOriginator());

			HSSFCell cellData6 = rowx.createCell(6);
			cellData6.setCellStyle(style);
			cellData6.setCellValue(data.getGlsTime());

			HSSFCell cellData7 = rowx.createCell(7);
			cellData7.setCellStyle(style);
			cellData7.setCellValue(data.getValidTime());

			HSSFCell cellData8 = rowx.createCell(8);
			cellData8.setCellStyle(style);
			cellData8.setCellValue(data.getExpireTime());

			HSSFCell cellData9 = rowx.createCell(9);
			cellData9.setCellStyle(style);
			cellData9.setCellValue(data.getExpireStatus() == 1 ? "即将到期" : "已到期");
		}

		// 输出Excel文件
		OutputStream output = response.getOutputStream();
		response.reset();

		String agent = request.getHeader("USER-AGENT").toLowerCase();
		response.setContentType("application/msexcel");

		String fileName = "灰名单到期确认.xls";
		String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");

		if (agent.contains("firefox")) {
			response.setCharacterEncoding("utf-8");
			response.setHeader("content-disposition",
					"attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1"));
		} else {
			response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
		}

		wb.write(output);
		output.close();
	}

	@RequestMapping(value = "/findDataDetail.ilf")
	public void findDataDetail(HttpServletRequest request, HttpServletResponse response, @RequestParam String id)
			throws Exception {
		JSONObject jsonObject = JSONObject.fromObject("{success:true}");
		String hql = "from GreyList where ID=?";
		List<GreyList> list = iGreyListService.getGreyListById(hql, new Long(id));
		jsonObject.put("list", list);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonObject.toString());
	}

	@RequestMapping(value = "/expire.ilf")
	public void expire(HttpServletRequest request, HttpServletResponse response, @RequestParam String validTime,
			@RequestParam String id) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject("{success:true}");

		Integer result = iGreyListService.dataBackup(Integer.parseInt(id), "expire");

		if (result < 1) {
			jsonObject = JSONObject.fromObject("{success:false}");
		}
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonObject.toString());
	}
}
