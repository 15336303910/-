package com.function.index.greyList.action;

/**
 * @author fengfeng02
 * 
 */
import java.util.ArrayList;
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

import com.system.LoginUserUtil;
import com.systemConfig.model.DataTableResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("com.function.index.greylist.action.GreyListFirstPageAction")
@RequestMapping(value = "/GreyListFirstPageAction")
public class GreyListFirstPageAction {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private LoginUserUtil loginUserUtil;

	private static String resUserName = "TOWERCRNOP";

	private static String guikou = "";

	@RequestMapping("/findGreyListNumPro.ilf")
	public void findGreyListNumPro(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer userId;
		int t = 0;// 存放lista对应的ROLE_ID的数值
		int ta = 0;// 存放listc对应的ROLE_ID的数值
		int tb = 0;// 返回传回的值
		boolean flag = false;// 匹配成功的标志，开始为false
		String num = new String();// 存放lista对应的ROLE_ID经过tostring后的字符串
		String numa = new String();// 存放listc对应的ROLE_ID经过tostring后的字符串
		String rolename = new String();// 存放匹配成功的ROLE_ID经过tostring后的字符串
		String sqla = "SELECT ROLE_ID " + "FROM DB_QUALITY.S_SYSTEM_USER_ROLE " + "WHERE USER_ID =  ";// 根据登录用户的USER_ID查询出ROLE_ID
		String sqlb = new String();// 匹配成功后按照该SQL语句查询结果
		String sqlc = new String();
		String sqld = "";// 查询地市对应的区县
		sqlc = "SELECT  ROLE_ID " + "FROM DB_QUALITY.GL_TROLE";// 从权限表中查询ROLE_ID
		JSONObject jsonObject = JSONObject.fromObject("{success:true}");
		Object loginObject = request.getSession().getAttribute("LoginUserInfo");
		Boolean IS_PROVICE = false;
		String CITY_NAME = "";
		if (loginObject != null) {
			Map<String, Object> loginUser = (HashMap<String, Object>) loginObject;
			/*
			 * 判断地市跟省份
			 */

			if (loginUser.get("BELONG_AREA").toString().indexOf("四川") != -1) {
				IS_PROVICE = true;
				CITY_NAME = loginUser.get("BELONG_AREA").toString();
			} else {
				CITY_NAME = loginUser.get("BELONG_AREA").toString();
			}
			if (CITY_NAME.length() > 2) {
				CITY_NAME = CITY_NAME.substring(0, 2);
			}
			/*
			 * 缓存登录账户的用户名编号
			 * 
			 */
			userId = loginUserUtil.getLoginUserId(request);
			sqla = sqla + " '" + userId + "'";
			List<Map<String, Object>> lista = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> listb = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> listc = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> county = new ArrayList<Map<String, Object>>();
			Map<String, Object> dan = new HashMap<String, Object>();// 存放lista中取出的值
			Map<String, Object> dana = new HashMap<String, Object>();// 存放listc中取出的值
			Map<String, Object> danb = new HashMap<String, Object>();// 存放匹配上的值
			lista = jdbcTemplate.queryForList(sqla);
			listc = jdbcTemplate.queryForList(sqlc);
			for (int i = 0; i < lista.size(); i++) {
				dan = lista.get(i);// 遍历lista每次取出一个值
				num = dan.get("ROLE_ID").toString();
				t = Integer.parseInt(num);
				for (int j = 0; j < listc.size(); j++) {
					dana = listc.get(j);// 遍历listc每次取出一个值
					numa = dana.get("ROLE_ID").toString();
					ta = Integer.parseInt(numa);
					if (t == ta) {
						tb = ta;
						flag = true;// 匹配成功，找到了相等的ROLE_ID
						break;// 匹配成功退出循环
					}
					if (flag) {// 匹配成功退出大循环
						break;
					}
				}
			}
			if (tb == 0) {
				if (!IS_PROVICE) {
					rolename = "地市领导";
				} else {
					rolename = "省级领导";
				}
				request.getSession().setAttribute("ROLENAME", "");
			} else {
				// 匹配成功后按照该SQL语句查询要获取的角色的名称
				sqlb = "SELECT  ROLE_NAME " + "FROM DB_QUALITY.GL_TROLE " + "WHERE ROLE_ID =  ' " + tb + "' ";
				listb = jdbcTemplate.queryForList(sqlb);
				danb = listb.get(0);
				rolename = (String) danb.get("ROLE_NAME");
				request.getSession().setAttribute("ROLENAME", rolename);
			}
			if ("灰名单省专业管理员（维护）".equals(rolename)) {
				guikou = "维护";
			} else if ("灰名单省专业管理员（市场）".equals(rolename)) {
				guikou = "市场";
			} else if ("灰名单省专业管理员（财务）".equals(rolename)) {
				guikou = "财务";
			} else {
				guikou = "";
			}

			jsonObject.put("rolename", rolename);// 将角色名称返回到前台

			if (!IS_PROVICE) {
				sqld = "SELECT   COUNTY_NAME " + "FROM     TOWERCRNOP.RMS_COUNTY " + "WHERE 	 BELONG_CITY = 	'"
						+ CITY_NAME + "'  " + "ORDER BY ID";
				county = jdbcTemplate.queryForList(sqld);
				jsonObject.put("list_counties", county);
			}
		}
		String sql = "";
		sql = "SELECT  A.GREYLIST_NUM GREYLISTNUM,A.EXPIREGREYLIST_NUM EXPIREGREYLISTNUM,(ROUND(A.EXPIREGREYLIST_NUM/A.GREYLIST_NUM*100,2)||'%')AS EXPIRYPRO   "
				+ "FROM   "
				+ "( SELECT   COUNT(*)  GREYLIST_NUM,SUM(CASE  WHEN  GL_GREY_LIST.EXPIRE_STATUS=2  THEN 1 ELSE 0 END)  EXPIREGREYLIST_NUM "
				+ "FROM  DB_QUALITY.GL_GREY_LIST WHERE 	EXPIRE_STATUS  <> 0 ";
		if (!IS_PROVICE) {
			sql += "  And  CITY = '" + CITY_NAME + "'";
		} else {

			if (!guikou.equals("")) {
				sql += " AND ATTRIBUTION='" + guikou + "' ";
			}
		}
		sql += " ) A  ";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = jdbcTemplate.queryForList(sql);
		jsonObject.put("list", list);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonObject.toString());
	}

	/*
	 * 为图提供数据
	 * 
	 */
	@RequestMapping("/findGreyListNumProaaa.ilf")
	public void findGreyListNumProaaa(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject("{success:true}");
		Object loginObject = request.getSession().getAttribute("LoginUserInfo");
		Boolean IS_PROVICE = false;
		String CITY_NAME = "";
		if (loginObject != null) {
			Map<String, Object> loginUser = (HashMap<String, Object>) loginObject;
			/*
			 * 判断地市跟省份
			 */

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
		String sqld = "";
		String sql_ds = "";
		String sqla_ds = "";
		List<Map<String, Object>> county = new ArrayList<Map<String, Object>>();
		if (!IS_PROVICE) {
			sqld = "SELECT   COUNTY_NAME " + "FROM     TOWERCRNOP.RMS_COUNTY " + "WHERE 	 BELONG_CITY = 	'"
					+ CITY_NAME + "'  " + "ORDER BY ID";
			county = jdbcTemplate.queryForList(sqld);
			for (int i = 0; i < county.size(); i++) {
				if (i == 0) {
					sql_ds += "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
							+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '" + CITY_NAME + "' and COUNTY='"
							+ county.get(i).get("COUNTY_NAME") + "' ";
					sqla_ds += "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
							+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And CITY = '" + CITY_NAME + "' and COUNTY='"
							+ county.get(i).get("COUNTY_NAME") + "' ";
				} else {
					sql_ds += "UNION  ALL  " + "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
							+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '" + CITY_NAME + "' and COUNTY='"
							+ county.get(i).get("COUNTY_NAME") + "' ";
					sqla_ds += "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  "
							+ "FROM  DB_QUALITY.GL_GREY_LIST   " + "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And CITY = '"
							+ CITY_NAME + "' and COUNTY='" + county.get(i).get("COUNTY_NAME") + "' ";
				}
			}

		}

		String sql = "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '成都' AND ATTRIBUTION='" + guikou + "' " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '雅安' AND ATTRIBUTION='" + guikou + "' " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '凉山' AND ATTRIBUTION='" + guikou + "' " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '宜宾' AND ATTRIBUTION='" + guikou + "' " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '泸州' AND ATTRIBUTION='" + guikou + "' " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '乐山' AND ATTRIBUTION='" + guikou + "'  " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM     " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '资阳'  AND ATTRIBUTION='" + guikou + "'  " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '阿坝' AND ATTRIBUTION='" + guikou + "'  " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '德阳' AND ATTRIBUTION='" + guikou + "'  " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '攀枝花' AND ATTRIBUTION='" + guikou + "'  " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '甘孜' AND ATTRIBUTION='" + guikou + "'  " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '内江' AND ATTRIBUTION='" + guikou + "'  " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '广元' AND ATTRIBUTION='" + guikou + "'  " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '眉山' AND ATTRIBUTION='" + guikou + "'  " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '巴中' AND ATTRIBUTION='" + guikou + "'  " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '绵阳' AND ATTRIBUTION='" + guikou + "'  " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '自贡' AND ATTRIBUTION='" + guikou + "'  " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM    " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '广安' AND ATTRIBUTION='" + guikou + "'  " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM     " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '达州' AND ATTRIBUTION='" + guikou + "'  " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '南充' AND ATTRIBUTION='" + guikou + "'  " + "UNION  ALL  "
				+ "SELECT   COUNT(*)  TOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	EXPIRE_STATUS  <> 0  And CITY = '遂宁' AND ATTRIBUTION='" + guikou + "'";

		String sqla = "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And 	CITY = 	'成都' AND ATTRIBUTION='" + guikou + "' "
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE  	 GL_GREY_LIST.EXPIRE_STATUS=2  And CITY = 	'雅安' AND ATTRIBUTION='" + guikou + "' "
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And 	CITY = 	'凉山' AND ATTRIBUTION='" + guikou + "' "
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And 	CITY = 	'宜宾'  AND ATTRIBUTION='" + guikou + "'"
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And 	CITY = 	'泸州'  AND ATTRIBUTION='" + guikou + "'"
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And 	CITY = 	'乐山'  AND ATTRIBUTION='" + guikou + "'"
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And 	CITY = 	'资阳' AND ATTRIBUTION='" + guikou + "' "
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And 	CITY = 	'阿坝'  AND ATTRIBUTION='" + guikou + "'"
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And 	CITY = 	'德阳'  AND ATTRIBUTION='" + guikou + "'"
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And 	CITY = 	'攀枝花'  AND ATTRIBUTION='" + guikou + "'"
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And 	CITY = 	'甘孜'  AND ATTRIBUTION='" + guikou + "'"
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And 	CITY = 	'内江'  AND ATTRIBUTION='" + guikou + "'"
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And 	CITY = 	'广元' AND ATTRIBUTION='" + guikou + "' "
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And 	CITY = 	'眉山'  AND ATTRIBUTION='" + guikou + "'"
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And 	CITY = 	'巴中'  AND ATTRIBUTION='" + guikou + "'"
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And 	CITY = 	'绵阳'  AND ATTRIBUTION='" + guikou + "'"
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And	CITY = '自贡'  AND ATTRIBUTION='" + guikou + "'"
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And	CITY = '广安'  AND ATTRIBUTION='" + guikou + "'"
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And	CITY = '达州'  AND ATTRIBUTION='" + guikou + "'"
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And	 CITY = '南充'  AND ATTRIBUTION='" + guikou + "'"
				+ "UNION  ALL  " + "SELECT    COUNT(*)  EXPIRETOTALNUM  " + "FROM  DB_QUALITY.GL_GREY_LIST   "
				+ "WHERE 	 GL_GREY_LIST.EXPIRE_STATUS=2  And	 CITY = '遂宁' AND ATTRIBUTION='" + guikou + "'";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lista = new ArrayList<Map<String, Object>>();
		if (CITY_NAME.equals("四川")) {
			list = jdbcTemplate.queryForList(sql);
			lista = jdbcTemplate.queryForList(sqla);
		} else {
			list = jdbcTemplate.queryForList(sql_ds);
			lista = jdbcTemplate.queryForList(sqla_ds);
		}

		jsonObject.put("list", list);
		jsonObject.put("lista", lista);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonObject.toString());
	}

	/*
	 * 查询灰名单总数、到期灰名单总数和到期占比数据
	 * 
	 */
	@RequestMapping("/findGreyListNum.ilf")
	public void findGreyListNum(@RequestParam String tableparam, @RequestParam String conditions,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long sEcho = 0L;
		Integer displayStart = 0;
		Integer iDisplayLength = 0;
		JSONArray jsons = JSONArray.fromObject(tableparam);
		HashMap<String, Object> conditonMap = new HashMap<String, Object>();
		if (jsons != null && jsons.size() != 0) {
			for (int i = 0; i < jsons.size(); i++) {
				JSONObject json = JSONObject.fromObject(jsons.get(i));
				String key = json.getString("name");
				if (key.equals("sEcho")) {
					sEcho = Long.parseLong(json.getString("value"));
				} else if (key.equals("iDisplayStart")) {
					displayStart = Integer.parseInt(json.getString("value"));
					conditonMap.put("iDisplayStart", displayStart);
				} else if (key.equals("iDisplayLength")) {
					iDisplayLength = Integer.parseInt(json.getString("value"));
					conditonMap.put("iDisplayLength", iDisplayLength);
				}
			}
		}
		JSONArray condition = JSONArray.fromObject(conditions);
		if (conditions != null && condition.size() != 0) {
			for (int i = 0; i < condition.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(condition.get(i));
				if (jsonObject.get("value") != null && !"".equals(jsonObject.getString("value"))) {
					conditonMap.put(jsonObject.getString("name"), jsonObject.getString("value"));
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
		/* 检索搜索参数 */
		String searchCity = "";
		String searchDate = "";
		if (conditonMap.containsKey("CITY") && !"".equals(conditonMap.get("CITY").toString())
				&& !"--".equals(conditonMap.get("CITY").toString())
				&& !"--请选择--".equals(conditonMap.get("CITY").toString())
				&& !"四川".equals(conditonMap.get("CITY").toString())
				&& !"全省".equals(conditonMap.get("CITY").toString())) {
			searchCity = conditonMap.get("CITY").toString();
		}
		if (conditonMap.containsKey("DATE") && !"".equals(conditonMap.get("DATE").toString())) {
			searchDate = conditonMap.get("DATE").toString();
		}
		String sql = "SELECT ROWNUM AS ID,B.*,(ROUND(B.EXPIREGREYLISTNUM/B.GREYLISTNUM*100,2)||'%')  EXPIREPRO  "
				+ "FROM ( "
				+ "SELECT A.CITY,A.GLS_TIME,COUNT(*) AS GREYLISTNUM,SUM(CASE  WHEN  A.EXPIRE_STATUS=2 THEN 1 ELSE 0 END) EXPIREGREYLISTNUM  "
				+ "FROM ( " + "SELECT CITY ,ATTRIBUTION, TO_CHAR(GLS_TIME,'YYYY-MM')  GLS_TIME,EXPIRE_STATUS  "
				+ "FROM DB_QUALITY.GL_GREY_LIST WHERE EXPIRE_STATUS  <> 0 ";

		if (!IS_PROVICE) {
			sql += " and CITY='" + CITY_NAME + "'";
			if (!searchDate.equals("")) {
				sql += " and TO_CHAR(GLS_TIME,'yyyy-MM')  like'" + searchDate + "'";
			}

		} else {
			if (!searchCity.equals("") && !searchDate.equals("")) {
				sql += " and CITY='" + searchCity + "' and TO_CHAR(GLS_TIME,'yyyy-MM')  like'" + searchDate + "'";
			} else if (searchCity.equals("") && !searchDate.equals("")) {
				sql += " and TO_CHAR(GLS_TIME,'yyyy-MM')  like'" + searchDate + "'";
			} else if (!searchCity.equals("") && searchDate.equals("")) {
				sql += " and CITY='" + searchCity + "'";
			}
			if (!guikou.equals("")) {
				sql += " AND ATTRIBUTION='" + guikou + "' ";

			}
		}
		sql += " ) A  " + "GROUP BY A.CITY,A.GLS_TIME  " + "ORDER BY A.CITY,A.GLS_TIME)B   ";

		Integer count = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM(" + sql + ")");
		Integer lastIndex = displayStart + iDisplayLength;
		String pageSql = "";
		pageSql += "SELECT X.* FROM(";
		pageSql += "	SELECT V.*,ROWNUM AS RN FROM(" + sql + ") V WHERE ROWNUM <= " + lastIndex;
		pageSql += ") X WHERE X.RN > " + displayStart;
		List<Map<String, Object>> objectList = jdbcTemplate.queryForList(pageSql);
		DataTableResult<Map<String, Object>> tableData = new DataTableResult<Map<String, Object>>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(objectList);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
}