package com.function.index.risk;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.systemConfig.model.DataTableResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("com.function.index.risk.RiskQuestionDataListDetailsList")
@RequestMapping(value = "/riskQuestionDataListDetailsListAction")
public class RiskQuestionDataListDetailsList {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static String resUserName = "TOWERCRNOP";

	@SuppressWarnings("unchecked")
	@RequestMapping("/findRiskDataListDetailsListFirst.ilf")
	public void findRiskDataListDetailsListFirst(HttpServletRequest request, HttpServletResponse response, String qu_id)
			throws Exception {
		JSONObject jsonObject = JSONObject.fromObject("{success:true}");
		String sql = "";
		sql += "select ID,QU_TYPE,RISK_NAME from " + resUserName + ".ORC_QU_TYPE_DETAIL where ID='" + qu_id
				+ "' order by ID";
		String qu_type = (String) jdbcTemplate.queryForList(sql).get(0).get("QU_TYPE");
		jsonObject.put("QU_TYPE", qu_type);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonObject.toString());
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/findRiskDataListDetailsListTable.ilf")
	public void findRiskDataListDetailsListTable(@RequestParam String qu_id, @RequestParam String city,
			@RequestParam String mouth, @RequestParam String tableparam, @RequestParam String conditions,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long sEcho = 0L;
		Integer displayStart = 0;
		Integer iDisplayLength = 0;
		JSONArray jsons = JSONArray.fromObject(tableparam);
		HashMap<String, Object> conditionMap = new HashMap<String, Object>();
		if (jsons != null && jsons.size() != 0) {
			for (int i = 0; i < jsons.size(); i++) {
				JSONObject json = JSONObject.fromObject(jsons.get(i));
				String key = json.getString("name");
				if (key.equals("sEcho")) {
					sEcho = Long.parseLong(json.getString("value"));
				} else if (key.equals("iDisplayStart")) {
					displayStart = Integer.parseInt(json.getString("value"));
					conditionMap.put("iDisplayStart", displayStart);
				} else if (key.equals("iDisplayLength")) {
					iDisplayLength = Integer.parseInt(json.getString("value"));
					conditionMap.put("iDisplayLength", iDisplayLength);
				}
			}
		}
		JSONArray condition = JSONArray.fromObject(conditions);
		if (conditions != null && condition.size() != 0) {
			for (int i = 0; i < condition.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(condition.get(i));
				if (jsonObject.get("value") != null && !"".equals(jsonObject.getString("value"))) {
					conditionMap.put(jsonObject.getString("name"), jsonObject.getString("value"));
				}
			}
		}
		/* 检索搜索参数 */
		String searchCity = "";
		String searchDate = "";
		if (conditionMap.containsKey("CITY") && !"".equals(conditionMap.get("CITY").toString())
				&& !"--".equals(conditionMap.get("CITY").toString())
				&& !"--请选择--".equals(conditionMap.get("CITY").toString())
				&& !"四川".equals(conditionMap.get("CITY").toString())
				&& !"全省".equals(conditionMap.get("CITY").toString())) {
			searchCity = conditionMap.get("CITY").toString();
		}
		if (conditionMap.containsKey("DATE") && !"".equals(conditionMap.get("DATE").toString())) {
			searchDate = conditionMap.get("DATE").toString();
		}
		/* 获取风险点问题的集合 */
		Boolean isProvince = false;
		String belongArea = "";
		isProvince = (Boolean) request.getSession().getAttribute("IS_PROVINCE");
		belongArea = (String) request.getSession().getAttribute("BELONG_AREA");
		String sql = "";
		sql += "select ID,QU_TYPE,RISK_NAME from " + resUserName + ".ORC_QU_TYPE_DETAIL where ID='" + qu_id
				+ "' order by ID";
		String tablePreName = jdbcTemplate.queryForList(sql).get(0).get("RISK_NAME") + "";
		sql = "select ROWNUM as NUM,E.* from";
		sql += "(select A.ID,A.CITY,A.COUNTY,A.MOUTH,B.RISK_TYPE,C.RISK_NAME,D.QU_TYPE from";
		sql += "(select ID,CITY,COUNTY,to_char(MOUTH,'yyyy-MM') as MOUTH,RISK_TYPE,RISK_NAME,QU_TYPE,REASON,FEE_TIME,FEE_PEOPLE from "
				+ resUserName + "." + RiskControlTable.getValueByKey(tablePreName) + ") A";
		sql += "," + resUserName + ".ORC_RISK_TYPE_DETAIL B";
		sql += "," + resUserName + ".ORC_RISK_NAME_DETAIL C";
		sql += "," + resUserName + ".ORC_QU_TYPE_DETAIL D ";
		sql += "where A.RISK_TYPE=B.ID and A.RISK_NAME=C.ID and A.QU_TYPE=D.ID and A.QU_TYPE=" + qu_id
				+ " and (trim(A.REASON) is null or trim(A.FEE_TIME) is null or trim(A.FEE_PEOPLE) is null) ";
		// sql+="and A.CITY='"+city+"' and A.MOUTH='"+mouth+"'";

		if (!isProvince) {
			sql += "and A.CITY='" + belongArea + "'";
			if (!searchDate.equals("") && !searchDate.equals(mouth)) {
				sql += " and A.MOUTH='" + searchDate + "'";
			} else {
				sql += " and A.MOUTH='" + mouth + "'";
			}
		} else {
			if (!searchCity.equals("") && !searchDate.equals("")) {
				if (!searchCity.equals(city) && !searchDate.equals(mouth)) {
					sql += " and A.CITY='" + searchCity + "' and A.MOUTH='" + searchDate + "'";
				} else if (!searchCity.equals(city) && searchDate.equals(mouth)) {
					sql += " and A.CITY='" + searchCity + "' and A.MOUTH='" + mouth + "'";
				} else if (searchCity.equals(city) && searchDate.equals(mouth)) {
					sql += " and A.CITY='" + city + "' and A.MOUTH='" + mouth + "'";
				} else if (searchCity.equals(city) && !searchDate.equals(mouth)) {
					sql += " and A.CITY='" + city + "' and A.MOUTH='" + searchDate + "'";
				}
			} else if (searchCity.equals("") && searchDate.equals("")) {
				sql += " and A.CITY='" + city + "' and A.MOUTH='" + mouth + "'";
			} else if (!searchCity.equals("") && searchDate.equals("")) {
				sql += " and A.CITY='" + searchCity + "' and A.MOUTH='" + mouth + "'";
			} else if (searchCity.equals("") && !searchDate.equals("")) {
				sql += " and A.CITY='" + city + "' and A.MOUTH='" + searchDate + "'";
			}
		}
		sql += ")E";
		Integer count = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM(" + sql + ")");
		Integer lastIndex = displayStart + iDisplayLength;
		String pageSql = "";
		pageSql += "SELECT B.* FROM(";
		pageSql += "	SELECT A.*,ROWNUM AS RN FROM(" + sql + ") A WHERE ROWNUM <= " + lastIndex;
		pageSql += ") B WHERE B.RN > " + displayStart;
		List<Map<String, Object>> objectList = jdbcTemplate.queryForList(pageSql);
		DataTableResult<Map<String, Object>> tableData = new DataTableResult<Map<String, Object>>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(objectList);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}

	/**
	 * 详情列表导出</br>
	 * 
	 */
	@RequestMapping(value = "/exportExcel.ilf")
	@ResponseBody
	public void createExcel(@RequestParam String qu_id, @RequestParam String mouth, @RequestParam String date,
			@RequestParam String city, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// city传入的为中文，转一次码
		city = java.net.URLDecoder.decode(city, "utf-8");

		// 总列数
		int columnCount = 7;

		// 创建HSSFWorkbook对象(excel的文档对象)
		HSSFWorkbook wb = new HSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		HSSFSheet sheet = wb.createSheet("报表");

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
		cell.setCellValue("问题数据报表");

		// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnCount));

		// 在sheet里创建第二行
		HSSFRow row2 = sheet.createRow(1);

		// 创建单元格并设置单元格内容及样式
		HSSFCell cell0 = row2.createCell(0);
		cell0.setCellStyle(style);
		cell0.setCellValue("  序号        ");

		HSSFCell cell1 = row2.createCell(1);
		cell1.setCellStyle(style);
		cell1.setCellValue("地市");

		HSSFCell cell2 = row2.createCell(2);
		cell2.setCellStyle(style);
		cell2.setCellValue(" 区县  ");

		HSSFCell cell3 = row2.createCell(3);
		cell3.setCellStyle(style);
		cell3.setCellValue("  月份    ");

		HSSFCell cell4 = row2.createCell(4);
		cell4.setCellStyle(style);
		cell4.setCellValue("风险点类型");

		HSSFCell cell5 = row2.createCell(5);
		cell5.setCellStyle(style);
		cell5.setCellValue(" 风险点名称    ");

		HSSFCell cell6 = row2.createCell(6);
		cell6.setCellStyle(style);
		cell6.setCellValue("  问题类型        ");

		// 宽度自适应(循环设置不生效，暂时这样写)
		sheet.autoSizeColumn((short) 0);
		sheet.autoSizeColumn((short) 1);
		// sheet.setColumnWidth(1, 10);// 第二列日期格式为yyyy-mm，直接设定一个固定的值,无效
		sheet.autoSizeColumn((short) 2);
		sheet.autoSizeColumn((short) 3);
		sheet.autoSizeColumn((short) 4);
		sheet.autoSizeColumn((short) 5);
		sheet.autoSizeColumn((short) 6);
		sheet.autoSizeColumn((short) 7);

		// 查询数据
		List<Map<String, Object>> questionDataList = queryData(qu_id, mouth, city, date, request);

		if (questionDataList == null) {
			return;// 用户条件设置错误时可能查出空数据，这里不做抛出异常处理
		}

		// 数据填充(行填充)
		for (int i = 0; i < questionDataList.size(); i++) {
			// 从第三行开始填充数据
			HSSFRow rowx = sheet.createRow(i + 2);

			// 获取当前条目数据
			Map<String, Object> map = questionDataList.get(i);

			HSSFCell cellData0 = rowx.createCell(0);// 创建列
			cellData0.setCellStyle(style);
			cellData0.setCellValue(map.get("ID").toString());// 添加当列数据

			HSSFCell cellData1 = rowx.createCell(1);// 创建列
			cellData1.setCellStyle(style);
			cellData1.setCellValue(map.get("CITY").toString());// 添加当列数据

			HSSFCell cellData2 = rowx.createCell(2);// 创建列
			cellData2.setCellStyle(style);
			cellData2.setCellValue(map.get("COUNTY").toString());// 添加当列数据

			HSSFCell cellData3 = rowx.createCell(3);// 创建列
			cellData3.setCellStyle(style);
			cellData3.setCellValue(map.get("MOUTH").toString());// 添加当列数据

			HSSFCell cellData4 = rowx.createCell(4);// 创建列
			cellData4.setCellStyle(style);
			cellData4.setCellValue(map.get("RISK_TYPE").toString());// 添加当列数据

			HSSFCell cellData5 = rowx.createCell(5);// 创建列
			cellData5.setCellStyle(style);
			cellData5.setCellValue(map.get("RISK_NAME").toString());// 添加当列数据

			HSSFCell cellData6 = rowx.createCell(6);// 创建列
			cellData6.setCellStyle(style);
			cellData6.setCellValue(map.get("QU_TYPE").toString());// 添加当列数据
		}

		// 输出Excel文件
		OutputStream output = response.getOutputStream();
		response.reset();

		String agent = request.getHeader("USER-AGENT").toLowerCase();
		response.setContentType("application/msexcel");

		String fileName = "问题数据详情列表.xls";
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

	/**
	 * 列表数据查询
	 */
	private List<Map<String, Object>> queryData(String qu_id, String mouth, String city, String date,
			HttpServletRequest request) {
		/* 检索搜索参数 */
		String searchCity = "";
		String searchDate = date;

		if (!"--".equals(city) && !"--请选择--".equals(city) && !"四川".equals(city) && !"全省".equals(city)) {
			searchCity = city;
		}

		/* 获取风险点问题的集合 */
		Boolean isProvince = false;
		String belongArea = "";
		isProvince = (Boolean) request.getSession().getAttribute("IS_PROVINCE");
		belongArea = (String) request.getSession().getAttribute("BELONG_AREA");

		String sql = "";
		sql += "select ID,QU_TYPE,RISK_NAME from " + resUserName + ".ORC_QU_TYPE_DETAIL where ID='" + qu_id
				+ "' order by ID";
		String tablePreName = jdbcTemplate.queryForList(sql).get(0).get("RISK_NAME") + "";
		sql = "select ROWNUM as NUM,E.* from";
		sql += "(select A.ID,A.CITY,A.COUNTY,A.MOUTH,B.RISK_TYPE,C.RISK_NAME,D.QU_TYPE from";
		sql += "(select ID,CITY,COUNTY,to_char(MOUTH,'yyyy-MM') as MOUTH,RISK_TYPE,RISK_NAME,QU_TYPE,REASON,FEE_TIME,FEE_PEOPLE from "
				+ resUserName + "." + RiskControlTable.getValueByKey(tablePreName) + ") A";
		sql += "," + resUserName + ".ORC_RISK_TYPE_DETAIL B";
		sql += "," + resUserName + ".ORC_RISK_NAME_DETAIL C";
		sql += "," + resUserName + ".ORC_QU_TYPE_DETAIL D ";
		sql += "where A.RISK_TYPE=B.ID and A.RISK_NAME=C.ID and A.QU_TYPE=D.ID and A.QU_TYPE=" + qu_id
				+ " and (trim(A.REASON) is null or trim(A.FEE_TIME) is null or trim(A.FEE_PEOPLE) is null) ";
		// sql+="and A.CITY='"+city+"' and A.MOUTH='"+mouth+"'";

		if (!isProvince) {
			sql += "and A.CITY='" + belongArea + "'";
			if (!searchDate.equals("") && !searchDate.equals(mouth)) {
				sql += " and A.MOUTH='" + searchDate + "'";
			} else {
				sql += " and A.MOUTH='" + mouth + "'";
			}
		} else {
			if (!searchCity.equals("") && !searchDate.equals("")) {
				if (!searchCity.equals(city) && !searchDate.equals(mouth)) {
					sql += " and A.CITY='" + searchCity + "' and A.MOUTH='" + searchDate + "'";
				} else if (!searchCity.equals(city) && searchDate.equals(mouth)) {
					sql += " and A.CITY='" + searchCity + "' and A.MOUTH='" + mouth + "'";
				} else if (searchCity.equals(city) && searchDate.equals(mouth)) {
					sql += " and A.CITY='" + city + "' and A.MOUTH='" + mouth + "'";
				} else if (searchCity.equals(city) && !searchDate.equals(mouth)) {
					sql += " and A.CITY='" + city + "' and A.MOUTH='" + searchDate + "'";
				}
			} else if (searchCity.equals("") && searchDate.equals("")) {
				sql += " and A.CITY='" + city + "' and A.MOUTH='" + mouth + "'";
			} else if (!searchCity.equals("") && searchDate.equals("")) {
				sql += " and A.CITY='" + searchCity + "' and A.MOUTH='" + mouth + "'";
			} else if (searchCity.equals("") && !searchDate.equals("")) {
				sql += " and A.CITY='" + city + "' and A.MOUTH='" + searchDate + "'";
			}
		}
		sql += ")E";

		List<Map<String, Object>> objectList = jdbcTemplate.queryForList(sql);

		if (objectList == null || objectList.size() <= 0) {
			return null;
		}

		return objectList;
	}
}
