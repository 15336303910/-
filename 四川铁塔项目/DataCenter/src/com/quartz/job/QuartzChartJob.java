package com.quartz.job;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.quartz.util.ApplicationUtil;
@Repository("quartzChartJob")
public class QuartzChartJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException{
		System.out.println("=>Chart Data Audit Start.");
		Date date = new Date();
		Integer nowYears = date.getYear()+1900;
		Integer nowMonth = date.getMonth();
		if(nowMonth==0){
			nowYears = nowYears-1;
			nowMonth = 12;
		}
		String nowYearMonth = nowYears+""+(nowMonth<10?("0"+nowMonth):nowMonth);
		System.out.println("=>Chart Data of '"+nowYearMonth+"' is counting ...");
		JdbcTemplate jdbcTool = (JdbcTemplate)ApplicationUtil.getBean("jdbcTemplate");
		try{
			/*
			 * 	单站收益排名：地市（所有地市）维度    上个月（本月是2017年10月，则统计2017年09月）
			 * 
			 * */
			jdbcTool.execute("DELETE FROM QUARTZ_INDEX_CHART WHERE DATA_TYPE = '单站收益排名'");
			String sql = "";
			sql+="SELECT W.*,ROWNUM AS RN FROM (";
			sql+="	 SELECT A.* FROM(";
			sql+="	 	 SELECT substr(T.CITY_NAME,0,2) AS REGION_ID,NVL(ROUND(SUM(SS_PROFIT)/10000,0),0) AS AVL_COUNT ";
			sql+="		 FROM TOWERCRNOP.CW_SIG_TOWER_ACCOUNT T ";
			sql+="		 WHERE T.CITY_NAME IS NOT NULL AND T.PUT_TIME = '"+nowYearMonth+"' GROUP BY T.CITY_NAME";
			sql+="	 ) A ORDER BY A.AVL_COUNT DESC";
			sql+=") W";
			List<Map<String,Object>> dzsyCount = jdbcTool.queryForList(sql);
			for(int i=0;i<dzsyCount.size();i++){
				Map<String,Object> dzsyObj = dzsyCount.get(i);
				/*
				 * 	针对每一个地市统计[区县]单站受益排名
				 * 
				 * */
				String CITY_NAME = dzsyObj.get("REGION_ID").toString();
				sql = "";
				sql+="SELECT W.*,ROWNUM AS RN FROM (";
				sql+="	 SELECT A.* FROM(";
				sql+="	 	 SELECT T.COUNTY_NAME AS COUNTRY_NAME,NVL(ROUND(SUM(SS_PROFIT)/10000,0),0) AS EARN_AVL_COUNT ";
				sql+="		 FROM TOWERCRNOP.CW_SIG_TOWER_ACCOUNT T ";
				sql+="		 WHERE T.PUT_TIME = '"+nowYearMonth+"' AND T.COUNTY_NAME IS NOT NULL AND T.CITY_NAME IS NOT NULL AND T.CITY_NAME LIKE '"+CITY_NAME+"%' GROUP BY T.COUNTY_NAME";
				sql+="	 ) A ORDER BY A.EARN_AVL_COUNT DESC";
				sql+=") W";
				List<Map<String,Object>> dzsyCountyCount = jdbcTool.queryForList(sql);
				if(dzsyCountyCount.size()>0){
					for(int j=0;j<dzsyCountyCount.size();j++){
						Map<String,Object> dzsyObjCountry = dzsyCountyCount.get(j);
						jdbcTool.execute("INSERT INTO QUARTZ_INDEX_CHART(DATA_TYPE,COUNT_DIMENSION,DIMENSION_NAME,DIMENSION_VALUE)VALUES('单站收益排名','区县','"+dzsyObjCountry.get("COUNTRY_NAME").toString()+"',"+dzsyObjCountry.get("EARN_AVL_COUNT").toString()+")");
					}
				}
				/*
				 * 	保存[区县]信息完毕，开始保存[地市]收益信息.
				 * 
				 * */
				jdbcTool.execute("INSERT INTO QUARTZ_INDEX_CHART(DATA_TYPE,COUNT_DIMENSION,DIMENSION_NAME,DIMENSION_VALUE)VALUES('单站收益排名','地市','"+dzsyObj.get("REGION_ID").toString()+"',"+dzsyObj.get("AVL_COUNT").toString()+")");
			}
			List<Map<String,Object>> cityList = jdbcTool.queryForList("SELECT DISTINCT(CITY_NAME) AS CITY_NAME FROM TOWERCRNOP.CW_SIG_TOWER_ACCOUNT WHERE CITY_NAME IS NOT NULL AND CITY_NAME != ' '");
			
			/*
			 * 	拆站数量：地市<目前只有地市维度，所以只能统计地市>.
			 * 
			 * */
			
			sql = "SELECT substr(CITY,0,2) AS CITY_NAME,UNLOAD_STATION_TOTAL FROM TOWERCRNOP.WY_UNLOADSTATION_SUM WHERE CITY != '总计' ORDER BY UNLOAD_STATION_TOTAL DESC";
			List<Map<String,Object>> unFixSorts = jdbcTool.queryForList(sql);
			if(unFixSorts.size()>0){
				/*
				 * 	清空现有数据.
				 * 
				 * */
				jdbcTool.execute("DELETE FROM QUARTZ_INDEX_CHART WHERE DATA_TYPE = '拆站数量'");
				/*
				 * 	录入最新数据.
				 * 
				 * */
				for(int i=0;i<unFixSorts.size();i++){
					Map<String,Object> unfixObj = unFixSorts.get(i);
					jdbcTool.execute("INSERT INTO QUARTZ_INDEX_CHART(DATA_TYPE,COUNT_DIMENSION,DIMENSION_NAME,DIMENSION_VALUE)VALUES('拆站数量','地市','"+unfixObj.get("CITY_NAME").toString()+"',"+unfixObj.get("UNLOAD_STATION_TOTAL").toString()+")");
				}
			}
			
			/*
			 * 	亏损站数量：<地市>
			 * 
			 * */
			sql = "";
			sql+= "SELECT substr(T.CITY_NAME,0,2) AS REGION_ID,COUNT(*) AS TOTAL_COUNT ";
			sql+= "FROM TOWERCRNOP.CW_SIG_TOWER_ACCOUNT T ";
			sql+= "WHERE T.PUT_TIME = '"+nowYearMonth+"' AND T.SS_PROFIT<0 AND T.CITY_NAME IS NOT NULL GROUP BY T.CITY_NAME ORDER BY TOTAL_COUNT DESC";
			List<Map<String,Object>> unEarnSorts = jdbcTool.queryForList(sql);
			if(unEarnSorts.size()>0){
				/*
				 * 	清空现有数据.
				 * 
				 * */
				jdbcTool.execute("DELETE FROM QUARTZ_INDEX_CHART WHERE DATA_TYPE = '亏损站数量'");
				/*
				 * 	录入最新数据.
				 * 
				 * */
				for(int i=0;i<unEarnSorts.size();i++){
					Map<String,Object> unearnObj = unEarnSorts.get(i);
					String CITY_NAME = unearnObj.get("REGION_ID").toString();
					/*
					 * 	针对每一个地市统计<区县>亏损站数量
					 * 
					 * */
					sql = "";
					sql+= "SELECT T.COUNTY_NAME AS REGION_ID,COUNT(*) AS TOTAL_COUNT ";
					sql+= "FROM TOWERCRNOP.CW_SIG_TOWER_ACCOUNT T ";
					sql+= "WHERE T.PUT_TIME = '"+nowYearMonth+"' AND T.SS_PROFIT<0 AND T.CITY_NAME IS NOT NULL AND T.CITY_NAME LIKE '"+CITY_NAME+"%' GROUP BY T.COUNTY_NAME ORDER BY TOTAL_COUNT DESC";
					List<Map<String,Object>> unEarnCountryDatas = jdbcTool.queryForList(sql);
					if(unEarnCountryDatas.size()>0){
						for(int j=0;j<unEarnCountryDatas.size();j++){
							Map<String,Object> unearnCountryObj = unEarnCountryDatas.get(j);
							if(unearnCountryObj.get("REGION_ID")!=null && unearnCountryObj.get("TOTAL_COUNT")!=null){
								jdbcTool.execute("INSERT INTO QUARTZ_INDEX_CHART(DATA_TYPE,COUNT_DIMENSION,DIMENSION_NAME,DIMENSION_VALUE)VALUES('亏损站数量','区县','"+unearnCountryObj.get("REGION_ID").toString()+"',"+unearnCountryObj.get("TOTAL_COUNT").toString()+")");
							}
						}
					}
					/*
					 * 	保存<地市>亏损数据.
					 * 
					 * */
					jdbcTool.execute("INSERT INTO QUARTZ_INDEX_CHART(DATA_TYPE,COUNT_DIMENSION,DIMENSION_NAME,DIMENSION_VALUE)VALUES('亏损站数量','地市','"+unearnObj.get("REGION_ID").toString()+"',"+unearnObj.get("TOTAL_COUNT").toString()+")");
				}
			}
			
			/*
			 * 	近六个月收入与成本分析：<全省>维度.
			 * 
			 * */
			jdbcTool.execute("DELETE FROM QUARTZ_IN_OUT_CHART");
			for(int m=(date.getMonth()-5);m<=date.getMonth();m++){
				Integer currentMonth = m;
				Integer fullYear = date.getYear()+1900;
				if(currentMonth<0){
					currentMonth = 12+(currentMonth)+1;
					fullYear = fullYear-1;
				}else if(currentMonth==0){
					currentMonth = 1;
				}
				String monthDesign = fullYear+""+(currentMonth<10?"0"+currentMonth:currentMonth);
				sql = "";
				sql+= "SELECT SUM(V.COME_IN) AS 总收入,SUM(V.COME_OUT) AS 总成本 FROM(";
				sql+= "	  SELECT A.CITY_ID CITY_NAME,A.A COME_IN,B.B COME_OUT,C.C MAINTAIN_PAYOUT,D.D RENT_PAYOUT,E.E UN_EARN_SITE_NUMBER ";
				sql+= "	  FROM ";
				sql+= "		  (SELECT CITY_ID,NVL(ROUND(SUM(TOI_APRIL_SUBTOTAL)/10000,0),0) A FROM TOWERCRNOP.CW_SIG_TOWER_ACCOUNT T WHERE T.PUT_TIME = '"+monthDesign+"' AND T.CITY_ID IS NOT NULL GROUP BY CITY_ID) A,";
				sql+= "		  (SELECT CITY_ID,NVL(ROUND(SUM(TOC_APRIL_SUBTOTAL)/10000,0),0) B FROM TOWERCRNOP.CW_SIG_TOWER_ACCOUNT T WHERE T.PUT_TIME = '"+monthDesign+"' AND T.CITY_ID IS NOT NULL GROUP BY CITY_ID) B,";
				sql+= "		  (SELECT CITY_ID,NVL(ROUND(SUM(TOC_MAINTAIN_M)/10000,0),0) C FROM TOWERCRNOP.CW_SIG_TOWER_ACCOUNT T WHERE T.PUT_TIME = '"+monthDesign+"' AND T.CITY_ID IS NOT NULL GROUP BY CITY_ID) C,";
				sql+= "		  (SELECT CITY_ID,NVL(ROUND(SUM(TOC_SITE_LEASING_M)/10000,0),0) D FROM TOWERCRNOP.CW_SIG_TOWER_ACCOUNT T WHERE T.PUT_TIME = '"+monthDesign+"' AND T.CITY_ID IS NOT NULL GROUP BY CITY_ID) D,";
				sql+= "		  (SELECT T.CITY_ID, COUNT(*) E FROM TOWERCRNOP.CW_SIG_TOWER_ACCOUNT T WHERE T.PUT_TIME = '"+monthDesign+"' AND T.SS_PROFIT < 0 AND CITY_ID IS NOT NULL GROUP BY CITY_ID) E ";
				sql+= "	  WHERE A.CITY_ID = B.CITY_ID(+) AND A.CITY_ID = C.CITY_ID(+) AND A.CITY_ID = D.CITY_ID(+) AND A.CITY_ID = E.CITY_ID(+)";
				sql+= ") V";
				Map<String,Object> countMap = jdbcTool.queryForMap(sql);
				if(countMap!=null && countMap.get("总收入")!=null && countMap.get("总成本")!=null){
					jdbcTool.execute("INSERT INTO QUARTZ_IN_OUT_CHART(DIMENSION_VALUE,COUNT_DATE,ALL_IN,ALL_OUT)VALUES('全省','"+monthDesign+"',"+countMap.get("总收入").toString()+","+countMap.get("总成本").toString()+")");
				}
			}
			/*
			 * 	近六个月收入与成本分析：<地市>维度.
			 * 
			 * */
			cityList = jdbcTool.queryForList("SELECT DISTINCT(substr(CITY_NAME,0,2)) AS CITY_NAME FROM TOWERCRNOP.CW_SIG_TOWER_ACCOUNT WHERE CITY_NAME IS NOT NULL");
			for(int i=0;i<cityList.size();i++){
				String cityName = cityList.get(i).get("CITY_NAME").toString();
				for(int m=(date.getMonth()-5);m<=date.getMonth();m++){
					Integer currentMonth = m;
					Integer fullYear = date.getYear()+1900;
					if(currentMonth<0){
						currentMonth = 12+(currentMonth)+1;
						fullYear = fullYear-1;
					}else if(currentMonth==0){
						currentMonth = 1;
					}
					String monthDesign = fullYear+""+(currentMonth<10?"0"+currentMonth:currentMonth);
					sql = "";
					sql+= "SELECT SUM(V.COME_IN) AS 总收入,SUM(V.COME_OUT) AS 总成本 FROM(";
					sql+= "	  SELECT A.CITY_ID CITY_NAME,A.A COME_IN,B.B COME_OUT,C.C MAINTAIN_PAYOUT,D.D RENT_PAYOUT,E.E UN_EARN_SITE_NUMBER ";
					sql+= "	  FROM ";
					sql+= "		  (SELECT CITY_ID,NVL(ROUND(SUM(TOI_APRIL_SUBTOTAL)/10000,0),0) A FROM TOWERCRNOP.CW_SIG_TOWER_ACCOUNT T WHERE T.PUT_TIME = "+monthDesign+" AND T.CITY_NAME LIKE '"+cityName+"%' AND T.CITY_ID IS NOT NULL GROUP BY CITY_ID) A,";
					sql+= "		  (SELECT CITY_ID,NVL(ROUND(SUM(TOC_APRIL_SUBTOTAL)/10000,0),0) B FROM TOWERCRNOP.CW_SIG_TOWER_ACCOUNT T WHERE T.PUT_TIME = "+monthDesign+" AND T.CITY_NAME LIKE '"+cityName+"%' AND T.CITY_ID IS NOT NULL GROUP BY CITY_ID) B,";
					sql+= "		  (SELECT CITY_ID,NVL(ROUND(SUM(TOC_MAINTAIN_M)/10000,0),0) C FROM TOWERCRNOP.CW_SIG_TOWER_ACCOUNT T WHERE T.PUT_TIME = "+monthDesign+" AND T.CITY_NAME LIKE '"+cityName+"%' AND T.CITY_ID IS NOT NULL GROUP BY CITY_ID) C,";
					sql+= "		  (SELECT CITY_ID,NVL(ROUND(SUM(TOC_SITE_LEASING_M)/10000,0),0) D FROM TOWERCRNOP.CW_SIG_TOWER_ACCOUNT T WHERE T.PUT_TIME = "+monthDesign+" AND T.CITY_NAME LIKE '"+cityName+"%' AND T.CITY_ID IS NOT NULL GROUP BY CITY_ID) D,";
					sql+= "		  (SELECT T.CITY_ID, COUNT(*) E FROM TOWERCRNOP.CW_SIG_TOWER_ACCOUNT T WHERE T.PUT_TIME = "+monthDesign+" AND T.CITY_NAME LIKE '"+cityName+"%' AND T.SS_PROFIT < 0 AND CITY_ID IS NOT NULL GROUP BY CITY_ID) E ";
					sql+= "	  WHERE A.CITY_ID = B.CITY_ID(+) AND A.CITY_ID = C.CITY_ID(+) AND A.CITY_ID = D.CITY_ID(+) AND A.CITY_ID = E.CITY_ID(+)";
					sql+= ") V";
					Map<String,Object> countMap = jdbcTool.queryForMap(sql);
					if(countMap!=null && countMap.get("总收入")!=null && countMap.get("总成本")!=null){
						jdbcTool.execute("INSERT INTO QUARTZ_IN_OUT_CHART(DIMENSION_VALUE,COUNT_DATE,ALL_IN,ALL_OUT)VALUES('"+cityName+"','"+monthDesign+"',"+countMap.get("总收入").toString()+","+countMap.get("总成本").toString()+")");
					}
				}
			}
			System.out.println("=>Chart Data Audit Done.");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
