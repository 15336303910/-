package com.function.index.risk;

import java.util.ArrayList;
/**
 * @author wuzidong
 * 
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.system.LoginUserUtil;

import net.sf.json.JSONObject;

@Controller("com.function.index.risk.RiskPointDetailDisplayAction")
@RequestMapping(value = "/RiskPointDetailDisplayAction")
public class RiskPointDetailDisplayAction {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private LoginUserUtil loginUserUtil;

	private static String resUserName1 = "TOWERCRNOP";

	/*
	 * 展示风险点管控列表
	 * 
	 */
	@RequestMapping("/showRiskPointControlDetail.ilf")
	public void showRiskPointControlDetail(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String city, @RequestParam String date, @RequestParam String Is_Search) throws Exception {

		JSONObject jsonObject = JSONObject.fromObject("{success:true}");

		/***
		 * 获取登录信息判断是否是省级人员或者找出地市人员的所属地市
		 */
		Boolean IS_PROVICE = false;
		String BELONG_CITY = "";
		Object loginObject = request.getSession().getAttribute("LoginUserInfo");
		if (loginObject != null) {
			Map<String, Object> loginUser = (HashMap<String, Object>) loginObject;
			if (loginUser.get("BELONG_AREA").toString().indexOf("省") != -1) {
				IS_PROVICE = true;
				BELONG_CITY = loginUser.get("BELONG_AREA").toString();
			} else {
				BELONG_CITY = loginUser.get("BELONG_AREA").toString();
			}
			if (BELONG_CITY.length() > 2) {
				BELONG_CITY = BELONG_CITY.substring(0, 2);
			}
		}

		/* 资金问题列表数据 */
		List<Map<String, Object>> fund_list = new ArrayList<Map<String, Object>>();
		String fundsql = "";
		fundsql += "select ROWNUM as ID,C.* from";
		fundsql += "(";
		fundsql += "select B.RISK_NAME,B.ID as RISK_ID,SUM(case when trim(A.REASON) is NULL or trim(A.FEE_PEOPLE) is NULL or trim(A.FEE_TIME) is NULL THEN 1 ELSE 0 END)  QUNUM,SUM(case when trim(A.REASON) is NULL and trim(A.FEE_PEOPLE) is NULL and trim(A.FEE_TIME) is NULL THEN 0 ELSE 1 END) FEEDNUM from ";
		fundsql += "(";
		fundsql += "select CITY,to_char(MOUTH,'yyyy-MM')  MOUTH,RISK_NAME,REASON,FEE_PEOPLE,FEE_TIME from "
				+ resUserName1 + ".ORC_CGI_DETAIL ";
		fundsql += " union all ";
		fundsql += "select CITY,to_char(MOUTH,'yyyy-MM')  MOUTH,RISK_NAME,REASON,FEE_PEOPLE,FEE_TIME from "
				+ resUserName1 + ".ORC_CER_DETAIL ";
		fundsql += " union all ";
		fundsql += " select CITY,to_char(MOUTH,'yyyy-MM')  MOUTH,RISK_NAME,REASON,FEE_PEOPLE,FEE_TIME from "
				+ resUserName1 + ".ORC_OSP_DETAIL ";
		fundsql += " union all ";
		fundsql += " select CITY,to_char(MOUTH,'yyyy-MM')  MOUTH,RISK_NAME,REASON,FEE_PEOPLE,FEE_TIME from "
				+ resUserName1 + ".ORC_NVOTM_DETAIL ";
		fundsql += " union all ";
		fundsql += " select CITY,to_char(MOUTH,'yyyy-MM')  MOUTH,RISK_NAME,REASON,FEE_PEOPLE,FEE_TIME from "
				+ resUserName1 + ".ORC_RPA_DETAIL ";
		fundsql += " union all ";
		fundsql += " select CITY,to_char(MOUTH,'yyyy-MM')  MOUTH,RISK_NAME,REASON,FEE_PEOPLE,FEE_TIME from "
				+ resUserName1 + ".ORC_CTP_DETAIL ";
		fundsql += " union all ";
		fundsql += " select CITY,to_char(MOUTH,'yyyy-MM')  MOUTH,RISK_NAME,REASON,FEE_PEOPLE,FEE_TIME from "
				+ resUserName1 + ".ORC_PCOND_DETAIL ";
		fundsql += " union all ";
		fundsql += " select CITY,to_char(MOUTH,'yyyy-MM')  MOUTH,RISK_NAME,REASON,FEE_PEOPLE,FEE_TIME from "
				+ resUserName1 + ".ORC_OM_DETAIL ";
		fundsql += ") A," + resUserName1 + ".ORC_RISK_NAME_DETAIL B";
		fundsql += " where A.RISK_NAME=B.ID ";

		if (!StringUtils.isEmpty(city) && !city.equals("全省") && !city.equals("--请选择--")) {
			fundsql += " and A.CITY='" + city + "'";
		}

		if (!StringUtils.isEmpty(date)) {
			fundsql += " and A.MOUTH='" + date + "'";
		}

//		/****
//		 * 如果地市管理员登录则只查看所属地市的数据
//		 */
//		if(!IS_PROVICE) {
//			fundsql+="AND A.CITY='"+BELONG_CITY+"'";
//			if("-1".equals(Is_Search))
//			{
//				if(!date.equals("")) {
//					fundsql+=" and  A.MOUTH='"+date+"'";
//				}
//			}
//			
//		}else {
//			/****
//			 * 如果省份管理员登录可查询各个地市的数据
//			 */
//			if("-1".equals(Is_Search)) {
//				
//				if(!city.equals("")  && !city.equals("全省") && !city.equals("--请选择--")  && !date.equals("")) {
//					fundsql+=" and A.CITY='"+city+"' and A.MOUTH='"+date+"'";
//				}else if((city.equals("") || city.equals("全省")  || city.equals("--请选择--") )&& !date.equals("")) {
//					fundsql+=" and A.MOUTH='"+date+"'";
//				}else if(!city.equals("") && !city.equals("全省")  && !city.equals("--请选择--") && date.equals("")) {
//					fundsql+=" and A.CITY='"+city+"'";
//				}
//			}
//			
//		}

		fundsql += " GROUP BY B.RISK_NAME,B.ID ";
		fundsql += ") C ";
		fund_list = jdbcTemplate.queryForList(fundsql);

		/* 工程类问题列表数据 */
		List<Map<String, Object>> engineering_list = new ArrayList<Map<String, Object>>();
		String engineeringsql = "";
		engineeringsql += "select ROWNUM as ID,C.* from";
		engineeringsql += "(";
		engineeringsql += "select B.RISK_NAME,B.ID as RISK_ID,SUM(case when trim(A.REASON) is NULL or trim(A.FEE_PEOPLE) is NULL or trim(A.FEE_TIME) is NULL THEN 1 ELSE 0 END)  QUNUM,SUM(case when trim(A.REASON) is NULL and trim(A.FEE_PEOPLE) is NULL and trim(A.FEE_TIME) is NULL THEN 0 ELSE 1 END) FEEDNUM from ";
		engineeringsql += "(";
		engineeringsql += "select CITY,to_char(MOUTH,'yyyy-MM')  MOUTH,RISK_NAME,REASON,FEE_PEOPLE,FEE_TIME from "
				+ resUserName1 + ".ORC_POOI_DETAIL ";
		engineeringsql += " union all ";
		engineeringsql += "select CITY,to_char(MOUTH,'yyyy-MM')  MOUTH,RISK_NAME,REASON,FEE_PEOPLE,FEE_TIME from "
				+ resUserName1 + ".ORC_NSCEA_DETAIL ";
		engineeringsql += " union all ";
		engineeringsql += " select CITY,to_char(MOUTH,'yyyy-MM')  MOUTH,RISK_NAME,REASON,FEE_PEOPLE,FEE_TIME from "
				+ resUserName1 + ".ORC_TRMCD_DETAIL ";
		engineeringsql += " union all ";
		engineeringsql += " select CITY,to_char(MOUTH,'yyyy-MM')  MOUTH,RISK_NAME,REASON,FEE_PEOPLE,FEE_TIME from "
				+ resUserName1 + ".ORC_RAOBTI_DETAIL ";
		engineeringsql += ") A," + resUserName1 + ".ORC_RISK_NAME_DETAIL B";
		engineeringsql += " where A.RISK_NAME=B.ID ";

		/****
		 * 如果地市管理员登录则只查看所属地市的数据
		 */
		if (!IS_PROVICE) {
			engineeringsql += "AND A.CITY='" + BELONG_CITY + "'";
			if ("-1".equals(Is_Search)) {
				if (!date.equals("")) {
					engineeringsql += " and  A.MOUTH='" + date + "'";
				}
			}

		} else {
			/****
			 * 如果省份管理员登录可查询各个地市的数据
			 */
			if ("-1".equals(Is_Search)) {

				if (!city.equals("") && !city.equals("全省") && !city.equals("--请选择--") && !date.equals("")) {
					engineeringsql += " and A.CITY='" + city + "' and A.MOUTH='" + date + "'";
				} else if ((city.equals("") || city.equals("全省") || city.equals("--请选择--")) && !date.equals("")) {
					engineeringsql += " and A.MOUTH='" + date + "'";
				} else if (!city.equals("") && !city.equals("全省") && !city.equals("--请选择--") && date.equals("")) {
					engineeringsql += " and A.CITY='" + city + "'";
				}
			}

		}

		engineeringsql += " GROUP BY B.RISK_NAME,B.ID ";
		engineeringsql += ") C ";
		engineering_list = jdbcTemplate.queryForList(engineeringsql);

		/* 收入问题列表数据 */
		List<Map<String, Object>> income_list = new ArrayList<Map<String, Object>>();
		String incomesql = "";
		incomesql += "select ROWNUM as ID,C.* from";
		incomesql += "(";
		incomesql += "select B.RISK_NAME,B.ID as RISK_ID,SUM(case when trim(A.REASON) is NULL or trim(A.FEE_PEOPLE) is NULL or trim(A.FEE_TIME) is NULL THEN 1 ELSE 0 END)  QUNUM,SUM(case when trim(A.REASON) is NULL and trim(A.FEE_PEOPLE) is NULL and trim(A.FEE_TIME) is NULL THEN 0 ELSE 1 END) FEEDNUM from ";
		incomesql += "(";
		incomesql += "select CITY,to_char(MOUTH,'yyyy-MM')  MOUTH,RISK_NAME,REASON,FEE_PEOPLE,FEE_TIME from "
				+ resUserName1 + ".ORC_CACDD_DETAIL ";
		incomesql += " union all ";
		incomesql += "select CITY,to_char(MOUTH,'yyyy-MM')  MOUTH,RISK_NAME,REASON,FEE_PEOPLE,FEE_TIME from "
				+ resUserName1 + ".ORC_CTNH_DETAIL ";
		incomesql += ") A," + resUserName1 + ".ORC_RISK_NAME_DETAIL B";
		incomesql += " where A.RISK_NAME=B.ID ";

		/****
		 * 如果地市管理员登录则只查看所属地市的数据
		 */
		if (!IS_PROVICE) {
			incomesql += "AND A.CITY='" + BELONG_CITY + "'";
			if ("-1".equals(Is_Search)) {
				if (!date.equals("")) {
					incomesql += " and  A.MOUTH='" + date + "'";
				}
			}

		} else {
			/****
			 * 如果省份管理员登录可查询各个地市的数据
			 */
			if ("-1".equals(Is_Search)) {

				if (!city.equals("") && !city.equals("全省") && !city.equals("--请选择--") && !date.equals("")) {
					incomesql += " and A.CITY='" + city + "' and A.MOUTH='" + date + "'";
				} else if ((city.equals("") || city.equals("全省") || city.equals("--请选择--")) && !date.equals("")) {
					incomesql += " and A.MOUTH='" + date + "'";
				} else if (!city.equals("") && !city.equals("全省") && !city.equals("--请选择--") && date.equals("")) {
					incomesql += " and A.CITY='" + city + "'";
				}
			}

		}

		incomesql += " GROUP BY B.RISK_NAME,B.ID ";
		incomesql += ") C ";
		income_list = jdbcTemplate.queryForList(incomesql);
		/* 场租费问题列表数据 */
		List<Map<String, Object>> colocation_list = new ArrayList<Map<String, Object>>();
		String colocationsql = "";
		colocationsql += "select ROWNUM as ID,C.* from";
		colocationsql += "(";
		colocationsql += "select B.RISK_NAME,B.ID as RISK_ID,SUM(case when trim(A.REASON) is NULL or trim(A.FEE_PEOPLE) is NULL or trim(A.FEE_TIME) is NULL THEN 1 ELSE 0 END)  QUNUM,SUM(case when trim(A.REASON) is NULL and trim(A.FEE_PEOPLE) is NULL and trim(A.FEE_TIME) is NULL THEN 0 ELSE 1 END) FEEDNUM from ";
		colocationsql += "(";
		colocationsql += "select CITY,to_char(MOUTH,'yyyy-MM')  MOUTH,RISK_NAME,REASON,FEE_PEOPLE,FEE_TIME from "
				+ resUserName1 + ".ORC_ZCCA_DETAIL ";
		colocationsql += " union all ";
		colocationsql += "select CITY,to_char(MOUTH,'yyyy-MM')  MOUTH,RISK_NAME,REASON,FEE_PEOPLE,FEE_TIME from "
				+ resUserName1 + ".ORC_RCA_DETAIL ";
		colocationsql += ") A," + resUserName1 + ".ORC_RISK_NAME_DETAIL B";
		colocationsql += " where A.RISK_NAME=B.ID ";
		/****
		 * 如果地市管理员登录则只查看所属地市的数据
		 */
		if (!IS_PROVICE) {
			colocationsql += "AND A.CITY='" + BELONG_CITY + "'";
			if ("-1".equals(Is_Search)) {
				if (!date.equals("")) {
					colocationsql += " and  A.MOUTH='" + date + "'";
				}
			}

		} else {
			/****
			 * 如果省份管理员登录可查询各个地市的数据
			 */
			if ("-1".equals(Is_Search)) {

				if (!city.equals("") && !city.equals("全省") && !city.equals("--请选择--") && !date.equals("")) {
					colocationsql += " and A.CITY='" + city + "' and A.MOUTH='" + date + "'";
				} else if ((city.equals("") || city.equals("全省") || city.equals("--请选择--")) && !date.equals("")) {
					colocationsql += " and A.MOUTH='" + date + "'";
				} else if (!city.equals("") && !city.equals("全省") && !city.equals("--请选择--") && date.equals("")) {
					colocationsql += " and A.CITY='" + city + "'";
				}
			}

		}
		colocationsql += " GROUP BY B.RISK_NAME,B.ID ";
		colocationsql += ") C ";
		colocation_list = jdbcTemplate.queryForList(colocationsql);
		/* 基站电费问题列表数据 */

		List<Map<String, Object>> baseelectric_list = new ArrayList<Map<String, Object>>();
		String baseelectricsql = "";
		baseelectricsql += "select ROWNUM as ID,C.* from";
		baseelectricsql += "(";
		baseelectricsql += "select B.RISK_NAME,B.ID as RISK_ID,SUM(case when trim(A.REASON) is NULL or trim(A.FEE_PEOPLE) is NULL or trim(A.FEE_TIME) is NULL THEN 1 ELSE 0 END)  QUNUM,SUM(case when trim(A.REASON) is NULL and trim(A.FEE_PEOPLE) is NULL and trim(A.FEE_TIME) is NULL THEN 0 ELSE 1 END) FEEDNUM from ";
		baseelectricsql += "(";
		baseelectricsql += "select CITY,to_char(MOUTH,'yyyy-MM')  MOUTH,RISK_NAME,REASON,FEE_PEOPLE,FEE_TIME from "
				+ resUserName1 + ".ORC_TFHE_DETAIL ";
		baseelectricsql += " union all ";
		baseelectricsql += "select CITY,to_char(MOUTH,'yyyy-MM')  MOUTH,RISK_NAME,REASON,FEE_PEOPLE,FEE_TIME from "
				+ resUserName1 + ".ORC_REMA_DETAIL ";
		baseelectricsql += ") A," + resUserName1 + ".ORC_RISK_NAME_DETAIL B";
		baseelectricsql += " where A.RISK_NAME=B.ID ";
		/****
		 * 如果地市管理员登录则只查看所属地市的数据
		 */
		if (!IS_PROVICE) {
			baseelectricsql += "AND A.CITY='" + BELONG_CITY + "'";
			if ("-1".equals(Is_Search)) {
				if (!date.equals("")) {
					baseelectricsql += " and  A.MOUTH='" + date + "'";
				}
			}

		} else {
			/****
			 * 如果省份管理员登录可查询各个地市的数据
			 */
			if ("-1".equals(Is_Search)) {

				if (!city.equals("") && !city.equals("全省") && !city.equals("--请选择--") && !date.equals("")) {
					baseelectricsql += " and A.CITY='" + city + "' and A.MOUTH='" + date + "'";
				} else if ((city.equals("") || city.equals("全省") || city.equals("--请选择--")) && !date.equals("")) {
					baseelectricsql += " and A.MOUTH='" + date + "'";
				} else if (!city.equals("") && !city.equals("全省") && !city.equals("--请选择--") && date.equals("")) {
					baseelectricsql += " and A.CITY='" + city + "'";
				}
			}

		}
		baseelectricsql += " GROUP BY B.RISK_NAME,B.ID ";
		baseelectricsql += ") C ";
		baseelectric_list = jdbcTemplate.queryForList(baseelectricsql);

		jsonObject.put("FUND_QUESTION_DETAIL", fund_list);
		jsonObject.put("ENGINERING_TYPE_DETAIL", engineering_list);
		jsonObject.put("INCOME_QUESTION_DETAIL", income_list);
		jsonObject.put("COLOCATION_CHARGE_DETAIL", colocation_list);
		jsonObject.put("BASESTATION_ELECTRIC_DETAIL", baseelectric_list);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonObject.toString());

	}

}