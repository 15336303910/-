package com.function.index.greyList.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.function.index.greyList.model.GreyList;
import com.function.index.greyList.service.GreyListService;
import com.function.index.greyList.util.GreyListRule;
import com.function.index.risk.RiskControlTable;
import com.system.LoginUserUtil;
import com.systemConfig.model.DataTableResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("com.function.index.greyList.action.GreyListApplyAction")
@RequestMapping(value = "/greyListApplyAction")
public class GreyListApplyAction {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private LoginUserUtil loginUserUtil;
	@Autowired
	private GreyListService greyListService;
	private static String resUserName = "TOWERCRNOP";

	/**
	 * 获取数据来源后的下拉菜单
	 */
	@RequestMapping(value = "/findSelectOptionsFirst.ilf")
	public void findSelectOptionsFirst(@RequestParam String value, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject("{success:true}");
		String sql = "";
		try {
			if (value.equals("1")) {
				sql = "";
				sql += "SELECT A.ID,A.DATA ";
				sql += "FROM COMPARE_SUMMARY2 A ";
				sql += "where A.CITY_NAME='宜宾' and A.MONTH='2018-08-01' ";
				sql += "group by A.ID,A.DATA order by TO_NUMBER(A.ID) ASC ";
				List<Map<String, Object>> list01 = jdbcTemplate.queryForList(sql);
				jsonObject.put("list", list01);
			} else if (value.equals("2")) {
				sql = "";
				sql += "select ID,RISK_NAME,RISK_TYPE from " + resUserName
						+ ".ORC_RISK_NAME_DETAIL order by RISK_TYPE,ID";
				List<Map<String, Object>> list02 = jdbcTemplate.queryForList(sql);
				jsonObject.put("list", list02);
			}
		} catch (Exception e) {
			jsonObject = JSONObject.fromObject("{success:false}");
			e.printStackTrace();
		} finally {
			System.out.println(jsonObject.toString());
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(jsonObject.toString());
		}

	}

	@RequestMapping(value = "/findSelectOptionsSecond.ilf")
	public void findSelectOptionsSecond(@RequestParam String value, @RequestParam String type,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject("{success:true}");
		String sql = "";
		if (type.equals("在线风控")) {
			sql = "select ID,QU_TYPE from " + resUserName + ".ORC_QU_TYPE_DETAIL where RISK_NAME='" + value + "'";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			jsonObject.put("list", list);
		} else if (type.equals("SC")) {

		}
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonObject.toString());
	}

	@RequestMapping("/findTableData.ilf")
	public void findTableData(@RequestParam String tableparam, @RequestParam String conditions,
			@RequestParam String select01, @RequestParam String select02, @RequestParam String select03,
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
		String searchDate = "";
		if (conditionMap.containsKey("DATE") && !"".equals(conditionMap.get("DATE").toString())) {
			searchDate = conditionMap.get("DATE").toString();
		}
		/* 获取问题集合 */
		Boolean isProvince = false;
		String belongArea = "";
		Object loginObject = request.getSession().getAttribute("LoginUserInfo");
		if (loginObject != null) {
			Map<String, Object> loginUser = (HashMap<String, Object>) loginObject;
			if (loginUser.get("BELONG_AREA").toString().indexOf("四川") != -1) {
				isProvince = true;
				belongArea = loginUser.get("BELONG_AREA").toString();
			} else {
				belongArea = loginUser.get("BELONG_AREA").toString();
			}
			if (belongArea.length() > 2) {
				belongArea = belongArea.substring(0, 2);
			}
		}
		String sql = "";
		if (select01.equals("1")) {
			sql = "SELECT C.* FROM COMPARE_DATA C WHERE C.BELONG_MONITOR IN(";
			sql += " SELECT ID AS MONITOR_ID FROM COMPARE_MONITOR WHERE ID IN(";
			sql += " SELECT MONITOR_ID FROM COMPARE_SUMMARY1 WHERE DATA = '" + select02 + "'";
			if (!isProvince) {
				sql += " and CITY_NAME='" + belongArea + "' ";
				if (!searchDate.equals("")) {
					sql += " and C_DATE like '%" + searchDate + "%' ";
				}
			}
			sql += ")";
			sql += ") ";
			if (!isProvince) {
				sql += "AND C.DATA_CITY = '" + belongArea + "' ";
			}
			sql += " AND C.PROBLEM_TYPE = '" + select03 + "' ";
			sql += " AND (select count(*) from GL_GREY_LIST where LAST_ID=C.ID)=0";
		} else if (select01.equals("2")) {
			if (select02.equals("60")) {
				sql = "select ID,to_char(MOUTH,'yyyy-MM') as MOUTH,CITY,CON_NUMBER as NUMBER1,CON_NAME as NAME1 from "
						+ resUserName + ".ORC_CGI_DETAIL where QU_TYPE=" + select03 + " and GL_STATUS is null";
			} else if (select02.equals("678871")) {
				sql = "select ID,to_char(MOUTH,'yyyy-MM') as MOUTH,CITY,COUNTY,PRO_NUMBER as NUMBER1,PRO_NAME as NAME1 from "
						+ resUserName + ".ORC_POOI_DETAIL where QU_TYPE=" + select03 + " and GL_STATUS is null";
			} else {
				sql = "select ID,to_char(MOUTH,'yyyy-MM') as MOUTH,CITY,STA_NUMBER as NUMBER1,STA_NAME as NAME1 from "
						+ resUserName + "." + RiskControlTable.getValueByKey(select02) + " where QU_TYPE=" + select03
						+ " and GL_STATUS is null";
			}
			if (!isProvince) {
				sql += " and CITY='" + belongArea + "'";
				if (!searchDate.equals("")) {
					sql += " and MOUTH='" + searchDate + "'";
				}
			}
		}

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

	@RequestMapping(value = "/findDataDetail.ilf")
	public void findDataDetail(HttpServletRequest request, HttpServletResponse response, @RequestParam String param01,
			@RequestParam String param02, @RequestParam String param03, @RequestParam String id) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject("{success:true}");
		Boolean isProvince = false;
		String belongArea = "";
		Object loginObject = request.getSession().getAttribute("LoginUserInfo");
		if (loginObject != null) {
			Map<String, Object> loginUser = (HashMap<String, Object>) loginObject;
			if (loginUser.get("BELONG_AREA").toString().indexOf("四川") != -1) {
				isProvince = true;
				belongArea = loginUser.get("BELONG_AREA").toString();
			} else {
				belongArea = loginUser.get("BELONG_AREA").toString();
			}
			if (belongArea.length() > 2) {
				belongArea = belongArea.substring(0, 2);
			}
		}
		String sql = "";
		if (param01.equals("1")) {
			sql = "SELECT A.* FROM COMPARE_DATA A WHERE A.BELONG_MONITOR IN(";
			sql += " SELECT ID AS MONITOR_ID FROM COMPARE_MONITOR WHERE ID IN(";
			sql += " SELECT MONITOR_ID FROM COMPARE_SUMMARY1 WHERE DATA = '" + param02 + "'";
			if (!isProvince) {
				sql += " and CITY_NAME='" + belongArea + "' ";
			}
			sql += ")";
			sql += ") ";
			if (!isProvince) {
				sql += "AND A.DATA_CITY = '" + belongArea + "' ";
			}
			sql += "AND A.PROBLEM_TYPE = '" + param03 + "' ";
			sql += "AND A.ID =" + id + "";
			jsonObject.put("table_source", "COMPARE_DATA");
		} else if (param01.equals("2")) {
			sql = "select RISK_NAME from " + resUserName + ".ORC_RISK_NAME_DETAIL where ID=" + param02;
			jsonObject.put("gl_type", jdbcTemplate.queryForList(sql).get(0).get("RISK_NAME"));
			sql = "select QU_TYPE from " + resUserName + ".ORC_QU_TYPE_DETAIL where ID=" + param03;
			jsonObject.put("gl_describe", jdbcTemplate.queryForList(sql).get(0).get("QU_TYPE"));
			if (param02.equals("60")) {
				sql = "select ID,to_char(MOUTH,'yyyy-MM') as MOUTH,CITY,COUNTY,CON_NUMBER as NUMBER1,CON_NAME as NAME1 from "
						+ resUserName + ".ORC_CGI_DETAIL where QU_TYPE=" + param03 + " and GL_STATUS is null and ID="
						+ id;
			} else if (param02.equals("678871")) {
				sql = "select ID,to_char(MOUTH,'yyyy-MM') as MOUTH,CITY,COUNTY,PRO_NUMBER as NUMBER1,PRO_NAME as NAME1 from "
						+ resUserName + ".ORC_POOI_DETAIL where QU_TYPE=" + param03 + " and GL_STATUS is null and ID="
						+ id;
			} else {
				sql = "select ID,to_char(MOUTH,'yyyy-MM') as MOUTH,CITY,COUNTY,STA_NUMBER as NUMBER1,STA_NAME as NAME1 from "
						+ resUserName + "." + RiskControlTable.getValueByKey(param02) + " where QU_TYPE=" + param03
						+ " and GL_STATUS is null and ID=" + id;
			}
			if (!isProvince) {
				sql += " and CITY='" + belongArea + "'";
			}
			jsonObject.put("table_source", RiskControlTable.getValueByKey(param02));
		}
		jsonObject.put("list", jdbcTemplate.queryForList(sql));
		// System.out.println(jsonObject.toString());
		// System.out.println(GreyListRule.toList().toString());
		jsonObject.put("greyList_Rule", GreyListRule.toList());
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonObject.toString());
	}

	@RequestMapping(value = "/findWriteData.ilf")
	public void findWriteData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject("{success:true}");
		jsonObject.put("table_source", "手工填写");
		jsonObject.put("greyList_Rule", GreyListRule.toList());
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonObject.toString());
	}

	@RequestMapping(value = "/apply.ilf")
	public void apply(HttpServletRequest request, HttpServletResponse response, @RequestParam String city,
			@RequestParam String county, @RequestParam String saName, @RequestParam String saCode,
			@RequestParam String glType, @RequestParam String glDescribe, @RequestParam String glRule,
			@RequestParam String attribution, @RequestParam String dataSource, @RequestParam String validTime,
			@RequestParam String lastId, @RequestParam String tableSource) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject("{success:true}");
		GreyList g = new GreyList();
		/* 归口专业 */
		g.setAttribution(attribution);
		/* 地市 */
		g.setCity(city);
		/* 区县 */
		g.setCounty(county);
		/* 数据来源 */
		g.setDataSource(dataSource);
		/* 灰名单说明 */
		g.setGlDescribe(glDescribe);
		/* 灰名单规则 */
		g.setGlRule(glRule);
		/* 灰名单类型 */
		g.setGlType(glType);
		/* 资源编码 */
		g.setSaCode(saCode);
		/* 资源名称 */
		g.setSaName(saName);
		/* 来源表 */
		g.setTableSource(tableSource);
		/* 有效期 */
		g.setValidTime(new Integer(validTime));
		/* 之前所在表中ID */
		g.setLastId(new Long(lastId));
		/* 发起人 */
		g.setOriginator(loginUserUtil.getEmployeeName(request));
		/* 申请发起时间 */
		g.setLaunchTime(new Date());
		/* 是否到期 (发起设置为0 默认不使用) */
		g.setExpireStatus(0);
		/* 流程环节 */
		g.setProcedureSegment("1");
		/* 流程类型 */
		g.setProcedureStatus("apply");

		try {
			greyListService.insertGreyOrder(g);
			if (tableSource != null && !tableSource.equals("") && !tableSource.equals("手工填写")
					&& tableSource.startsWith("ORC")) {
				String sql = "update " + resUserName + "." + tableSource + " set GL_STATUS=1 where ID=" + lastId;
				jdbcTemplate.update(sql);
			}
		} catch (Exception e) {
			jsonObject = JSONObject.fromObject("{success:false}");
			e.printStackTrace();
		}
		System.out.println(jsonObject.toString());
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonObject.toString());
	}

}
