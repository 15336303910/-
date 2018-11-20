package com.function.index.region.yfChart;
import java.util.ArrayList;
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
@Controller("com.function.index.region.yfChart.CzByMajorService")
@RequestMapping(value="/czByMajorService")
public class CzByMajorService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*
	 * 	各地市出账信息
	 * 
	 * */
	@RequestMapping("/czByCity.ilf")
	public void czByCity(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject chartInfo = JSONObject.fromObject("{SUCCESS:true}");
		List<String> xList = new ArrayList<String>();
		List<Double> aList = new ArrayList<Double>();
		List<Double> bList = new ArrayList<Double>();
		List<Double> cList = new ArrayList<Double>();
		List<Double> dList = new ArrayList<Double>();
		try{
			/*
			 * 	塔类
			 * 
			 * */
			List<Map<String,Object>> tlClasses = jdbcTemplate.queryForList("SELECT ORGNAME AS 地市,SUM(CZE) AS 塔类 FROM TOWERCRNOP.CZ_REPORT_TL WHERE ORGNAME<>'四川省' GROUP BY ORGNAME ORDER BY ORGNAME ASC");
			for(int a=0;a<tlClasses.size();a++){
				xList.add(tlClasses.get(a).get("地市").toString());
				aList.add(Double.parseDouble(tlClasses.get(a).get("塔类").toString()));
			}
			/*
			 * 	室分
			 * 
			 * */
			List<Map<String,Object>> SfClasses = jdbcTemplate.queryForList("SELECT ORGNAME AS 地市,SUM(CZE) AS 室分 FROM TOWERCRNOP.CZ_REPORT_SF WHERE ORGNAME<>'四川省' GROUP BY ORGNAME ORDER BY ORGNAME ASC");
			for(int a=0;a<SfClasses.size();a++){
				bList.add(Double.parseDouble(SfClasses.get(a).get("室分").toString()));
			}
			/*
			 * 	传输
			 * 
			 * */
			List<Map<String,Object>> csClasses = jdbcTemplate.queryForList("SELECT ORGNAME AS 地市,SUM(CZE) AS 传输 FROM TOWERCRNOP.CZ_REPORT_CS WHERE ORGNAME<>'四川省' GROUP BY ORGNAME ORDER BY ORGNAME ASC");
			for(int a=0;a<csClasses.size();a++){
				cList.add(Double.parseDouble(csClasses.get(a).get("传输").toString()));
			}
			/*
			 * 	微站
			 * 
			 * */
			List<Map<String,Object>> wzClasses = jdbcTemplate.queryForList("SELECT ORGNAME AS 地市,SUM(CZE) AS 微站 FROM TOWERCRNOP.CZ_REPORT_WZ WHERE ORGNAME<>'四川省' GROUP BY ORGNAME ORDER BY ORGNAME ASC");
			for(int a=0;a<wzClasses.size();a++){
				dList.add(Double.parseDouble(wzClasses.get(a).get("微站").toString()));
			}
			JSONObject chartConfig = new JSONObject();
			chartConfig.put("xAxis",xList);
			chartConfig.put("tlData",aList);
			chartConfig.put("sfData",bList);
			chartConfig.put("csData",cList);
			chartConfig.put("wzData",dList);
			chartInfo.put("CHART_CONFIG",chartConfig);
		}catch(Exception e){
			e.printStackTrace();
			chartInfo.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(chartInfo.toString());
		}
	}
	
	/*
	 * 	地市：各月份出账信息
	 * 
	 * */
	@RequestMapping("/czByCityMonth.ilf")
	public void czByCityMonth(
		@RequestParam String cityName,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject chartInfo = JSONObject.fromObject("{SUCCESS:true}");
		List<String> xList = new ArrayList<String>();
		List<Double> aList = new ArrayList<Double>();
		List<Double> bList = new ArrayList<Double>();
		List<Double> cList = new ArrayList<Double>();
		List<Double> dList = new ArrayList<Double>();
		try{
			/*
			 * 	月份、塔类.
			 * 
			 * */
			List<Map<String,Object>> tlClasses = jdbcTemplate.queryForList("SELECT MONTH,SUM(CZE) CZE FROM TOWERCRNOP.CZ_REPORT_TL WHERE ORGNAME='"+cityName+"' GROUP BY MONTH ORDER BY MONTH ASC");
			for(int a=0;a<tlClasses.size();a++){
				xList.add(tlClasses.get(a).get("MONTH").toString());
				aList.add(Double.parseDouble(tlClasses.get(a).get("CZE").toString()));
			}
			/*
			 * 	室分
			 * 
			 * */
			List<Map<String,Object>> SfClasses = jdbcTemplate.queryForList("SELECT MONTH,SUM(CZE) CZE FROM TOWERCRNOP.CZ_REPORT_SF WHERE ORGNAME='"+cityName+"' GROUP BY MONTH ORDER BY MONTH ASC");
			for(int a=0;a<SfClasses.size();a++){
				bList.add(Double.parseDouble(SfClasses.get(a).get("CZE").toString()));
			}
			/*
			 * 	传输
			 * 
			 * */
			List<Map<String,Object>> csClasses = jdbcTemplate.queryForList("SELECT MONTH,SUM(CZE) CZE FROM TOWERCRNOP.CZ_REPORT_CS WHERE ORGNAME='"+cityName+"' GROUP BY MONTH ORDER BY MONTH ASC");
			for(int a=0;a<csClasses.size();a++){
				cList.add(Double.parseDouble(csClasses.get(a).get("CZE").toString()));
			}
			/*
			 * 	微站
			 * 
			 * */
			List<Map<String,Object>> wzClasses = jdbcTemplate.queryForList("SELECT MONTH,SUM(CZE) CZE FROM TOWERCRNOP.CZ_REPORT_WZ WHERE ORGNAME='"+cityName+"' GROUP BY MONTH ORDER BY MONTH ASC");
			for(int a=0;a<wzClasses.size();a++){
				dList.add(Double.parseDouble(wzClasses.get(a).get("CZE").toString()));
			}
			JSONObject chartConfig = new JSONObject();
			chartConfig.put("xAxis",xList);
			chartConfig.put("tlData",aList);
			chartConfig.put("sfData",bList);
			chartConfig.put("csData",cList);
			chartConfig.put("wzData",dList);
			chartInfo.put("CHART_CONFIG",chartConfig);
		}catch(Exception e){
			e.printStackTrace();
			chartInfo.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(chartInfo.toString());
		}
	}
}