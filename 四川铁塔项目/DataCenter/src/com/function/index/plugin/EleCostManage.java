package com.function.index.plugin;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.system.LoginUserUtil;
@Controller("com.function.index.plugin.EleCostManage")
@RequestMapping(value="/eleCostManage")
public class EleCostManage{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	/*
	 * 	运营商类型
	 * 
	 * */
	@RequestMapping("/findTypes.ilf")
	public void findTypess(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			resultObject.put("OPTIONS",jdbcTemplate.queryForList("SELECT DISTINCT(OPER) FROM RMW.WY_ELECTRI_FEE_TOTAL WHERE OPER IS NOT NULL"));
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	电费情况报表
	 * 
	 * */
	@RequestMapping("/findDatas.ilf")
	public void findDatas(
		@RequestParam String RUN_TYPE,
		@RequestParam String COUNT_DATE,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			Boolean IS_PROVINCE = loginUserUtil.isProvince(request);
			String CITY_NAME = "";
			if(!IS_PROVINCE){
				CITY_NAME = loginUserUtil.getBelongArea(request);
				if(CITY_NAME.length()>2){
					CITY_NAME = CITY_NAME.substring(0,2);
				}
			}
			if(RUN_TYPE==null || "".equals(RUN_TYPE) || "-".equals(RUN_TYPE)){
				RUN_TYPE = "";
			}
			if(COUNT_DATE==null || "".equals(COUNT_DATE)){
				Date date = new Date();
				COUNT_DATE = date.getYear()+1900+"年"+date.getMonth()+"月";
			}
			String sql = "";
			if(IS_PROVINCE){
				sql+="SELECT A.CITY AS 地区,NVL(A.SITECOUNT, 0) AS 站址数,NVL(B.YQRZ, 0) AS 已确认电费站数,NVL(C.YQRWZF, 0) AS 已确认未支付站数,NVL(D.WQRZ, 0) AS 未确认站数,NVL(E.WQRYZF, 0) AS 未确认已支付站数,NVL(F.YQRYZF, 0) AS 已确认已支付站数,ROUND(NVL(F.YQRYZF, 0) / NVL(A.SITECOUNT, 0), 2) * 100 || '%' AS 未确认已支付占比  ";
				sql+="FROM ";
				sql+="	 (SELECT COUNT(*) AS SITECOUNT, CITY FROM RMW.ZG_SITE WHERE IS_VALID = '是' GROUP BY CITY) A,";
				sql+="	 (SELECT COUNT(*) AS YQRZ, CITY FROM RMW.WY_ELECTRI_FEE_TOTAL WHERE ELECTRI_FEE='"+COUNT_DATE+"' ";if(RUN_TYPE!=null && !"".equals(RUN_TYPE)){sql+="AND OPER='"+RUN_TYPE+"'";};sql+=" AND MOVE_QRSTATUS = '0' AND TELECOM_QRSTATUS = '0' AND UNICOM_QRSTATUS = '0' GROUP BY CITY) B,";
				sql+="	 (SELECT COUNT(*) AS YQRWZF, CITY FROM RMW.WY_ELECTRI_FEE_TOTAL WHERE ELECTRI_FEE='"+COUNT_DATE+"' ";if(RUN_TYPE!=null && !"".equals(RUN_TYPE)){sql+="AND OPER='"+RUN_TYPE+"'";};sql+=" AND (MOVE_QRSTATUS = '0' AND TELECOM_QRSTATUS = '0' AND UNICOM_QRSTATUS = '0') AND (MOVE_PAYQR = '1' OR TELECOM_PAYQR = '1' OR UNICOM_PAYQR = '1') GROUP BY CITY) C,";
				sql+="	 (SELECT COUNT(*) AS WQRZ, CITY FROM RMW.WY_ELECTRI_FEE_TOTAL WHERE ELECTRI_FEE='"+COUNT_DATE+"' ";if(RUN_TYPE!=null && !"".equals(RUN_TYPE)){sql+="AND OPER='"+RUN_TYPE+"'";};sql+=" AND (MOVE_QRSTATUS = '1' AND TELECOM_QRSTATUS = '1' AND UNICOM_QRSTATUS = '1') GROUP BY CITY, AREA) D,";
				sql+="	 (SELECT COUNT(*) AS WQRYZF, CITY FROM RMW.WY_ELECTRI_FEE_TOTAL WHERE ELECTRI_FEE='"+COUNT_DATE+"' ";if(RUN_TYPE!=null && !"".equals(RUN_TYPE)){sql+="AND OPER='"+RUN_TYPE+"'";};sql+=" AND (MOVE_QRSTATUS = '1' OR TELECOM_QRSTATUS = '1' OR UNICOM_QRSTATUS = '1') AND (MOVE_PAYQR = '0' AND TELECOM_PAYQR = '0' AND UNICOM_PAYQR = '0') GROUP BY CITY) E,";
				sql+="	 (SELECT COUNT(*) AS YQRYZF, CITY FROM RMW.WY_ELECTRI_FEE_TOTAL WHERE ELECTRI_FEE='"+COUNT_DATE+"' ";if(RUN_TYPE!=null && !"".equals(RUN_TYPE)){sql+="AND OPER='"+RUN_TYPE+"'";};sql+=" AND (MOVE_QRSTATUS = '0' AND TELECOM_QRSTATUS = '0' AND UNICOM_QRSTATUS = '0') AND (MOVE_PAYQR = '0' AND TELECOM_PAYQR = '0' AND UNICOM_PAYQR = '0') GROUP BY CITY) F ";
				sql+="WHERE A.CITY = B.CITY (+) AND A.CITY = C.CITY (+) AND A.CITY = D.CITY (+) AND A.CITY = E.CITY (+) AND A.CITY = F.CITY (+)";
				List<Map<String,Object>> proviceView = jdbcTemplate.queryForList(sql);
				resultObject.put("DATAS",proviceView);
			}else{
				sql+="SELECT A.CITY AS 地区,NVL(A.SITECOUNT, 0) AS 站址数,NVL(B.YQRZ, 0) AS 已确认电费站数,NVL(C.YQRWZF, 0) AS 已确认未支付站数,NVL(D.WQRZ, 0) AS 未确认站数,NVL(E.WQRYZF, 0) AS 未确认已支付站数,NVL(F.YQRYZF, 0) AS 已确认已支付站数,ROUND(NVL(F.YQRYZF, 0) / NVL(A.SITECOUNT, 0), 2) * 100 || '%' AS 未确认已支付占比  ";
				sql+="FROM ";
				sql+="	 (SELECT COUNT(*) AS SITECOUNT, CITY FROM RMW.ZG_SITE WHERE IS_VALID = '是' GROUP BY CITY) A,";
				sql+="	 (SELECT COUNT(*) AS YQRZ, CITY FROM RMW.WY_ELECTRI_FEE_TOTAL WHERE ELECTRI_FEE='"+COUNT_DATE+"' ";if(RUN_TYPE!=null && !"".equals(RUN_TYPE)){sql+="AND OPER='"+RUN_TYPE+"'";};sql+=" AND MOVE_QRSTATUS = '0' AND TELECOM_QRSTATUS = '0' AND UNICOM_QRSTATUS = '0' GROUP BY CITY) B,";
				sql+="	 (SELECT COUNT(*) AS YQRWZF, CITY FROM RMW.WY_ELECTRI_FEE_TOTAL WHERE ELECTRI_FEE='"+COUNT_DATE+"' ";if(RUN_TYPE!=null && !"".equals(RUN_TYPE)){sql+="AND OPER='"+RUN_TYPE+"'";};sql+=" AND (MOVE_QRSTATUS = '0' AND TELECOM_QRSTATUS = '0' AND UNICOM_QRSTATUS = '0') AND (MOVE_PAYQR = '1' OR TELECOM_PAYQR = '1' OR UNICOM_PAYQR = '1') GROUP BY CITY) C,";
				sql+="	 (SELECT COUNT(*) AS WQRZ, CITY FROM RMW.WY_ELECTRI_FEE_TOTAL WHERE ELECTRI_FEE='"+COUNT_DATE+"' ";if(RUN_TYPE!=null && !"".equals(RUN_TYPE)){sql+="AND OPER='"+RUN_TYPE+"'";};sql+=" AND (MOVE_QRSTATUS = '1' AND TELECOM_QRSTATUS = '1' AND UNICOM_QRSTATUS = '1') GROUP BY CITY, AREA) D,";
				sql+="	 (SELECT COUNT(*) AS WQRYZF, CITY FROM RMW.WY_ELECTRI_FEE_TOTAL WHERE ELECTRI_FEE='"+COUNT_DATE+"' ";if(RUN_TYPE!=null && !"".equals(RUN_TYPE)){sql+="AND OPER='"+RUN_TYPE+"'";};sql+=" AND (MOVE_QRSTATUS = '1' OR TELECOM_QRSTATUS = '1' OR UNICOM_QRSTATUS = '1') AND (MOVE_PAYQR = '0' AND TELECOM_PAYQR = '0' AND UNICOM_PAYQR = '0') GROUP BY CITY) E,";
				sql+="	 (SELECT COUNT(*) AS YQRYZF, CITY FROM RMW.WY_ELECTRI_FEE_TOTAL WHERE ELECTRI_FEE='"+COUNT_DATE+"' ";if(RUN_TYPE!=null && !"".equals(RUN_TYPE)){sql+="AND OPER='"+RUN_TYPE+"'";};sql+=" AND (MOVE_QRSTATUS = '0' AND TELECOM_QRSTATUS = '0' AND UNICOM_QRSTATUS = '0') AND (MOVE_PAYQR = '0' AND TELECOM_PAYQR = '0' AND UNICOM_PAYQR = '0') GROUP BY CITY) F ";
				sql+="WHERE A.CITY LIKE '%"+CITY_NAME+"%' AND A.CITY = B.CITY (+) AND A.CITY = C.CITY (+) AND A.CITY = D.CITY (+) AND A.CITY = E.CITY (+) AND A.CITY = F.CITY (+)";
				List<Map<String,Object>> proviceView = jdbcTemplate.queryForList(sql);
				resultObject.put("DATAS",proviceView);
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
}
