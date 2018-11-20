package com.function.index;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.function.index.model.Location;
import com.function.index.model.MarginLocation;
import com.function.index.service.LatitudeLontitudeUtil;
import com.system.LoginUserUtil;
import com.systemConfig.model.DataTableResult;
@Controller("com.function.index.SiteVisibleAction")
@RequestMapping(value="/sitevisibleAction")
public class SiteVisibleAction{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	
	/*
	 * 获得用户信息
	 * 
	 * */
	@RequestMapping("/getUserInfo.ilf")
	public void getUserInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			Map<String,Object> loginUser = (Map<String,Object>)request.getSession().getAttribute("LoginUserInfo");
			String area = "";
			if(null!=loginUser){
				area = loginUser.get("BELONG_AREA").toString();
			}
			resultObject.put("area",loginUserUtil.getBelongArea(request));
			/*
			 * 	地市列表.
			 * 
			 * */
			resultObject.put("CITY",jdbcTemplate.queryForList(
				"select distinct city from rmw.zg_site t where t.stateflag = '0' and substr(t.city, 0, 2) in(select substr(city_name, 0, 2) city from towercrnop.rms_city)"
			));
			/*
			 * 	站点类型.
			 * 
			 * */
			resultObject.put("SITE_TYPE",jdbcTemplate.queryForList(
				"SELECT DISTINCT(SITE_TYPE) FROM RMW.ZG_SITE WHERE STATEFLAG = '0' and SITE_TYPE IS NOT NULL"
			));
			resultObject.put("IS_PROVINCE",loginUserUtil.isProvince(request));
			/*
			 * 	停电数.
			 * 
			 * */
			String sql = "";
			sql+="SELECT SITE_CODE,SITE_NAME,'已停电' AS SITE_STATE,SITE_TYPE,STATE,LONGITUDE,LATITUDE ";
			sql+="FROM RMW.ZG_SITE WHERE LATITUDE IS NOT NULL AND LONGITUDE IS NOT NULL AND SITE_CODE IN(";
			sql+="	  SELECT DISTINCT(SITE_CODE) FROM TOWERCRNOP.YWJK_ACTIVE_ALARM WHERE ALARM_NAME = '交流输入停电告警'";
			sql+=")";
			if(area!=null && (area.indexOf("川")==-1 && area.indexOf("省")==-1)){
				sql+=" AND CITY LIKE '%"+area+"%'";
			}
			resultObject.put("STOP_ELEC_COUNT",jdbcTemplate.queryForInt("SELECT COUNT(*) FROM ("+sql+") A"));
			/*
			 * 	退服数.
			 * 
			 * */
			sql ="";
			sql+="SELECT COUNT(DISTINCT SITE_CODE) FROM TOWERCRNOP.YWJK_ACTIVE_ALARM WHERE ALARM_NAME = '一级低压脱离告警'";
			if(area!=null && (area.indexOf("川")==-1 && area.indexOf("省")==-1)){
				sql+=" AND CITY_NAME LIKE '%"+area+"%'";
			}
			resultObject.put("QUIT_SERVICE_COUNT",jdbcTemplate.queryForInt(sql));
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 获得停电站点.
	 * 
	 * */
	@RequestMapping("/getStopElecSites.ilf")
	public void getStopElecSites(
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			String sql = "";
			sql+="SELECT ZH_LABEL,SITE_CODE,SITE_NAME,SITE_TYPE,STATE,LONGITUDE,LATITUDE,LONGITUDE||','||LATITUDE AS LONGITUDE_LATITUDE,ROWNUM AS RN ";
			sql+="FROM RMW.ZG_SITE WHERE LATITUDE IS NOT NULL AND LONGITUDE IS NOT NULL AND SITE_CODE IN(";
			sql+="	  SELECT DISTINCT(SITE_CODE) FROM TOWERCRNOP.YWJK_ACTIVE_ALARM WHERE ALARM_NAME = '交流输入停电告警'";
			if(!loginUserUtil.isProvince(request)){
				sql+=" AND CITY LIKE '%"+loginUserUtil.getBelongArea(request)+"%'";
			}
			sql+=")";
			String pageSql = "";
			pageSql+="SELECT ";
			pageSql+="	  A.ZH_LABEL,A.SITE_CODE,A.SITE_NAME,A.SITE_TYPE,A.LATITUDE,A.LONGITUDE,A.LONGITUDE_LATITUDE AS 经纬度,";
			pageSql+="	  B.REGION_ID 区县,B.PROP_CHAR 原产权单位,B.BUSINESS_SCENE 业务场景,B.SHARE_UNIT 共享单位,B.TOWER 铁塔数量,B.ROOM 机房数量,B.SWITCH 开关电源数量,B.STORAGE 蓄电池组数量,B.AIR 空调数量,B.ENVIR 动环数量,";
			pageSql+="	  C.STATE 运行状态,C.ALARM_NUM 告警数量,";
			pageSql+="	  D.STATE 站址运营状态,D.TZ_ICB_TOTAL 投资合计,D.TOI_CUMULATIVE_TOTAL 经营收入,D.TOI_TOWER_RENT_Y 塔租收入,D.TOI_MAINTAIN_FEE_Y 维护费收入,D.TOI_RENTAL_COSTS_Y 场租收入,D.TOI_POWER_INTRODUCE_Y 电力引入收入,D.TOI_OIL_SERVICE_Y 油机发电收入,D.TOI_NEW_BUSINESS_Y 新业务收入,";
			pageSql+="	  D.TOC_CUMULATIVE_TOTAL 运营成本,D.TOC_MAINTAIN_Y 维护成本,D.TOC_SITE_LEASING_Y 场租成本,D.TOC_POWER_COST_Y 电力成本,D.TOC_OIL_COST_Y 油机发电成本,D.ZJ_DAD_CUMULATIVE_TOTAL 折旧摊销,D.GL_ME_CUMULATIVE_TOTAL 管理费用,D.FINANCE_COST_Y 财务费用,D.SS_GROSS_PROFIT 单站毛利    ";
			pageSql+="FROM("+sql+") A,RMW.SITE_VIS_BASICINFO B,RMW.SITE_VIS_ALARMINFO C,RMW.SITE_VIS_OUTIN D WHERE A.SITE_CODE = B.SITE_CODE(+) AND A.SITE_CODE = C.SITE_CODE(+) AND A.SITE_CODE = D.SITE_CODE(+)";
			List<Map<String,Object>> siteList = jdbcTemplate.queryForList(pageSql);
			if(siteList.size()>0){
				for(int i=0;i<siteList.size();i++){
					siteList.get(i).put("运行状态","已停电");
				}
			}
			resultObject.put("SITES",siteList);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 获得[退服]站点.
	 * 
	 * */
	@RequestMapping("/getStopServiceSites.ilf")
	public void getStopServiceSites(
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			String sql = "";
			sql+="SELECT ZH_LABEL,SITE_CODE,SITE_NAME,SITE_TYPE,STATE,LATITUDE,LONGITUDE,LONGITUDE||','||LATITUDE AS LONGITUDE_LATITUDE,ROWNUM AS RN ";
			sql+="FROM RMW.ZG_SITE WHERE LATITUDE IS NOT NULL AND LONGITUDE IS NOT NULL AND SITE_CODE IN(";
			sql+="	  SELECT DISTINCT(SITE_CODE) FROM TOWERCRNOP.YWJK_ACTIVE_ALARM WHERE ALARM_NAME = '一级低压脱离告警'";
			if(!loginUserUtil.isProvince(request)){
				sql+=" AND CITY LIKE '%"+loginUserUtil.getBelongArea(request)+"%'";
			}
			sql+=")";
			String pageSql = "";
			pageSql+="SELECT ";
			pageSql+="	  A.ZH_LABEL,A.SITE_CODE,A.SITE_NAME,A.SITE_TYPE,A.LATITUDE,A.LONGITUDE,A.LONGITUDE_LATITUDE AS 经纬度,";
			pageSql+="	  B.REGION_ID 区县,B.PROP_CHAR 原产权单位,B.BUSINESS_SCENE 业务场景,B.SHARE_UNIT 共享单位,B.TOWER 铁塔数量,B.ROOM 机房数量,B.SWITCH 开关电源数量,B.STORAGE 蓄电池组数量,B.AIR 空调数量,B.ENVIR 动环数量,";
			pageSql+="	  C.STATE 运行状态,C.ALARM_NUM 告警数量,";
			pageSql+="	  D.STATE 站址运营状态,D.TZ_ICB_TOTAL 投资合计,D.TOI_CUMULATIVE_TOTAL 经营收入,D.TOI_TOWER_RENT_Y 塔租收入,D.TOI_MAINTAIN_FEE_Y 维护费收入,D.TOI_RENTAL_COSTS_Y 场租收入,D.TOI_POWER_INTRODUCE_Y 电力引入收入,D.TOI_OIL_SERVICE_Y 油机发电收入,D.TOI_NEW_BUSINESS_Y 新业务收入,";
			pageSql+="	  D.TOC_CUMULATIVE_TOTAL 运营成本,D.TOC_MAINTAIN_Y 维护成本,D.TOC_SITE_LEASING_Y 场租成本,D.TOC_POWER_COST_Y 电力成本,D.TOC_OIL_COST_Y 油机发电成本,D.ZJ_DAD_CUMULATIVE_TOTAL 折旧摊销,D.GL_ME_CUMULATIVE_TOTAL 管理费用,D.FINANCE_COST_Y 财务费用,D.SS_GROSS_PROFIT 单站毛利    ";
			pageSql+="FROM("+sql+") A,RMW.SITE_VIS_BASICINFO B,RMW.SITE_VIS_ALARMINFO C,RMW.SITE_VIS_OUTIN D WHERE A.SITE_CODE = B.SITE_CODE(+) AND A.SITE_CODE = C.SITE_CODE(+) AND A.SITE_CODE = D.SITE_CODE(+)";
			List<Map<String,Object>> siteList = jdbcTemplate.queryForList(pageSql);
			if(siteList.size()>0){
				for(int i=0;i<siteList.size();i++){
					siteList.get(i).put("运行状态","已退服");
				}
			}
			resultObject.put("SITES",siteList);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 获得[退服]站点（单个）.
	 * 
	 * */
	@RequestMapping("/getSingleStopServiceSite.ilf")
	public void getSingleStopServiceSite(
		@RequestParam String SITE_CODE,
		@RequestParam String ALARM_TYPE,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			if("停电".equals(ALARM_TYPE)){
				ALARM_TYPE = "交流输入停电告警";
			}else if("退服".equals(ALARM_TYPE)){
				ALARM_TYPE = "一级低压脱离告警";
			}
			String pageSql = "";
			pageSql+="SELECT ";
			pageSql+="	  A.ZH_LABEL,A.SITE_CODE,A.SITE_NAME,A.SITE_TYPE,A.LATITUDE,A.LONGITUDE,A.LONGITUDE_LATITUDE AS 经纬度,";
			pageSql+="	  B.REGION_ID 区县,B.PROP_CHAR 原产权单位,B.BUSINESS_SCENE 业务场景,B.SHARE_UNIT 共享单位,B.TOWER 铁塔数量,B.ROOM 机房数量,B.SWITCH 开关电源数量,B.STORAGE 蓄电池组数量,B.AIR 空调数量,B.ENVIR 动环数量,";
			pageSql+="	  C.STATE 运行状态,C.ALARM_NUM 告警数量,";
			pageSql+="	  D.STATE 站址运营状态,D.TZ_ICB_TOTAL 投资合计,D.TOI_CUMULATIVE_TOTAL 经营收入,D.TOI_TOWER_RENT_Y 塔租收入,D.TOI_MAINTAIN_FEE_Y 维护费收入,D.TOI_RENTAL_COSTS_Y 场租收入,D.TOI_POWER_INTRODUCE_Y 电力引入收入,D.TOI_OIL_SERVICE_Y 油机发电收入,D.TOI_NEW_BUSINESS_Y 新业务收入,";
			pageSql+="	  D.TOC_CUMULATIVE_TOTAL 运营成本,D.TOC_MAINTAIN_Y 维护成本,D.TOC_SITE_LEASING_Y 场租成本,D.TOC_POWER_COST_Y 电力成本,D.TOC_OIL_COST_Y 油机发电成本,D.ZJ_DAD_CUMULATIVE_TOTAL 折旧摊销,D.GL_ME_CUMULATIVE_TOTAL 管理费用,D.FINANCE_COST_Y 财务费用,D.SS_GROSS_PROFIT 单站毛利    ";
			pageSql+="FROM(";
			pageSql+="	  SELECT ZH_LABEL,SITE_CODE,SITE_NAME,SITE_TYPE,STATE,LATITUDE,LONGITUDE,LONGITUDE||','||LATITUDE AS LONGITUDE_LATITUDE,ROWNUM AS RN ";
			pageSql+="	  FROM RMW.ZG_SITE WHERE LATITUDE IS NOT NULL AND LONGITUDE IS NOT NULL AND SITE_CODE IN(";
			pageSql+="		  SELECT DISTINCT(SITE_CODE) FROM TOWERCRNOP.YWJK_ACTIVE_ALARM WHERE ALARM_NAME = '"+ALARM_TYPE+"'";
			pageSql+="	  ) AND SITE_CODE = '"+SITE_CODE+"'";
			pageSql+=") A,RMW.SITE_VIS_BASICINFO B,RMW.SITE_VIS_ALARMINFO C,RMW.SITE_VIS_OUTIN D WHERE A.SITE_CODE = B.SITE_CODE(+) AND A.SITE_CODE = C.SITE_CODE(+) AND A.SITE_CODE = D.SITE_CODE(+)";
			List<Map<String,Object>> siteList = jdbcTemplate.queryForList(pageSql);
			if(siteList.size()>0){
				for(int i=0;i<siteList.size();i++){
					if("交流输入停电告警".equals(ALARM_TYPE)){
						siteList.get(i).put("运行状态","已停电");
					}else{
						siteList.get(i).put("运行状态","已退服");
					}
				}
			}
			resultObject.put("THIS_SITE",siteList.get(0));
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 根据地市获取区县
	 * 
	 * */
	@RequestMapping("/flushRegionOfCity.ilf")
	public void flushRegionOfCity(
		@RequestParam String cityName,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		if(cityName.length()>2){
			cityName = cityName.substring(0,2);
		}
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		resultObject.put("REGION",jdbcTemplate.queryForList("SELECT DISTINCT(REGION_ID) FROM RMW.ZG_SITE WHERE REGION_ID IS NOT NULL AND CITY LIKE '%"+cityName+"%'"));
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(resultObject.toString());
	}
	
	/*
	 * 	根据地市获得地市资源点
	 * 	根据用户地市获得站址信息：站址名  站址编码  经度   纬度
	 * 
	 * */
	@RequestMapping("/getSites.ilf")
	public void getResources(
		@RequestParam String zoomLevel,
		@RequestParam String latitude,
		@RequestParam String longitude,
		@RequestParam String siteType,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			Double circleLine = 400.0;
			if(Integer.parseInt(zoomLevel)<17){
				circleLine = 500.0;
			}
			MarginLocation location = findMarginLocation(
				Double.parseDouble(latitude),
				Double.parseDouble(longitude),
				circleLine
			);
			String sql = "";
			sql+="SELECT ZH_LABEL,SITE_CODE,SITE_NAME,SITE_TYPE,STATE,LATITUDE,LONGITUDE,LONGITUDE||','||LATITUDE AS LONGITUDE_LATITUDE,ROWNUM AS RN ";
			sql+="FROM RMW.ZG_SITE ";
			sql+="WHERE LONGITUDE IS NOT NULL AND LATITUDE IS NOT NULL AND TO_NUMBER(LATITUDE) >= "+location.getBottomBorder()+" AND TO_NUMBER(LATITUDE) <= "+location.getTopBorder()+" AND TO_NUMBER(LONGITUDE) >= "+location.getLeftBorder()+" AND TO_NUMBER(LONGITUDE) <= "+location.getRightBorder();
			if(siteType!=null && !"".equals(siteType) && !"-".equals(siteType)){
				sql+=" AND SITE_TYPE = '"+siteType+"'";
			}
			if(!loginUserUtil.isProvince(request)){
				sql+=" AND CITY LIKE '"+loginUserUtil.getBelongArea(request)+"'";
			}
			if(circleLine>400.0){
				sql =" SELECT F.* FROM("+sql+") F WHERE F.RN <= 20";
			}
			String pageSql = "";
			pageSql+="SELECT ";
			pageSql+="	  A.ZH_LABEL,A.SITE_CODE,A.SITE_NAME,A.SITE_TYPE,A.LATITUDE,A.LONGITUDE,A.LONGITUDE_LATITUDE AS 经纬度 ,";
			pageSql+="	  B.REGION_ID 区县,B.PROP_CHAR 原产权单位,B.BUSINESS_SCENE 业务场景,B.SHARE_UNIT 共享单位,B.TOWER 铁塔数量,B.ROOM 机房数量,B.SWITCH 开关电源数量,B.STORAGE 蓄电池组数量,B.AIR 空调数量,B.ENVIR 动环数量,";
			pageSql+="	  C.STATE 运行状态,C.ALARM_NUM 告警数量,";
			pageSql+="	  D.STATE 站址运营状态,D.TZ_ICB_TOTAL 投资合计,D.TOI_CUMULATIVE_TOTAL 经营收入,D.TOI_TOWER_RENT_Y 塔租收入,D.TOI_MAINTAIN_FEE_Y 维护费收入,D.TOI_RENTAL_COSTS_Y 场租收入,D.TOI_POWER_INTRODUCE_Y 电力引入收入,D.TOI_OIL_SERVICE_Y 油机发电收入,D.TOI_NEW_BUSINESS_Y 新业务收入,";
			pageSql+="	  D.TOC_CUMULATIVE_TOTAL 运营成本,D.TOC_MAINTAIN_Y 维护成本,D.TOC_SITE_LEASING_Y 场租成本,D.TOC_POWER_COST_Y 电力成本,D.TOC_OIL_COST_Y 油机发电成本,D.ZJ_DAD_CUMULATIVE_TOTAL 折旧摊销,D.GL_ME_CUMULATIVE_TOTAL 管理费用,D.FINANCE_COST_Y 财务费用,D.SS_GROSS_PROFIT 单站毛利    ";
			pageSql+="FROM("+sql+") A,RMW.SITE_VIS_BASICINFO B,RMW.SITE_VIS_ALARMINFO C,RMW.SITE_VIS_OUTIN D WHERE A.SITE_CODE = B.SITE_CODE(+) AND A.SITE_CODE = C.SITE_CODE(+) AND A.SITE_CODE = D.SITE_CODE(+)";
			List<Map<String,Object>> siteList = jdbcTemplate.queryForList(pageSql);
			if(siteList.size()>0){
				for(int i=0;i<siteList.size();i++){
					Map<String,Object> siteInfo = siteList.get(i);
					Integer isElecStop = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM TOWERCRNOP.YWJK_ACTIVE_ALARM WHERE ALARM_NAME = '交流输入停电告警' AND SITE_CODE = '"+siteInfo.get("SITE_CODE").toString()+"'");
					if(isElecStop>0){
						siteList.get(i).put("运行状态","已停电");
					}else{
						Integer isQuitService = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM TOWERCRNOP.YWJK_ACTIVE_ALARM WHERE ALARM_NAME = '一级低压脱离告警' AND SITE_CODE = '"+siteInfo.get("SITE_CODE").toString()+"'");
						if(isQuitService>0){
							siteList.get(i).put("运行状态","已退服");
						}else{
							siteList.get(i).put("运行状态","正常");
						}
					}
				}
			}
			resultObject.put("SITES",siteList);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}

	/*
	 * 	根据站址名称获取站点
	 *
	 * */
	@RequestMapping("/getSitesByName.ilf")
	public void getSitesByName(
		@RequestParam String cityName,
		@RequestParam String regionName,
		@RequestParam String runState,
		@RequestParam String siteType,
		@RequestParam String siteCode,
		@RequestParam String siteName,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			String sql = "";
			sql+="SELECT ZH_LABEL,SITE_CODE,SITE_NAME,SITE_TYPE,STATE,LATITUDE,LONGITUDE,LONGITUDE||','||LATITUDE AS LONGITUDE_LATITUDE,ROWNUM AS RN ";
			sql+="FROM RMW.ZG_SITE ";
			sql+="WHERE LONGITUDE IS NOT NULL AND LATITUDE IS NOT NULL ";
			if(siteType!=null && !"".equals(siteType) && !"-".equals(siteType)){
				sql+=" AND SITE_TYPE = '"+siteType+"'";
			}
			if(siteCode!=null && !"".equals(siteCode) && !"-".equals(siteCode)){
				sql+=" AND SITE_CODE LIKE '%"+siteCode+"%'";
			}
			if(siteName!=null && !"".equals(siteName) && !"-".equals(siteName)){
				sql+=" AND SITE_NAME LIKE '%"+siteName+"%'";
			}
			if(cityName!=null && !"".equals(cityName) && !"-".equals(cityName)){
				if(cityName.length()>2){
					cityName = cityName.substring(0,2);
				}
				sql+=" AND CITY LIKE '%"+cityName+"%'";
			}else{
				if(!loginUserUtil.isProvince(request)){
					sql+=" AND CITY LIKE '%"+loginUserUtil.getBelongArea(request)+"%'";
				}
			}
			if(regionName!=null && !"".equals(regionName) && !"-".equals(regionName)){
				sql+=" AND REGION_ID LIKE '%"+regionName+"%'";
			}
			String pageSql = "";
			pageSql+="SELECT W.*,ROWNUM AS RN FROM(";
			pageSql+="	  SELECT ";
			pageSql+="	  	  A.ZH_LABEL,A.SITE_CODE,A.SITE_NAME,A.SITE_TYPE,A.LATITUDE,A.LONGITUDE,A.LONGITUDE_LATITUDE AS 经纬度,";
			pageSql+="	  	  B.REGION_ID 区县,B.PROP_CHAR 原产权单位,B.BUSINESS_SCENE 业务场景,B.SHARE_UNIT 共享单位,B.TOWER 铁塔数量,B.ROOM 机房数量,B.SWITCH 开关电源数量,B.STORAGE 蓄电池组数量,B.AIR 空调数量,B.ENVIR 动环数量,";
			pageSql+="	  	  C.STATE 运行状态,C.ALARM_NUM 告警数量,";
			pageSql+="	  	  D.STATE 站址运营状态,D.TZ_ICB_TOTAL 投资合计,D.TOI_CUMULATIVE_TOTAL 经营收入,D.TOI_TOWER_RENT_Y 塔租收入,D.TOI_MAINTAIN_FEE_Y 维护费收入,D.TOI_RENTAL_COSTS_Y 场租收入,D.TOI_POWER_INTRODUCE_Y 电力引入收入,D.TOI_OIL_SERVICE_Y 油机发电收入,D.TOI_NEW_BUSINESS_Y 新业务收入,";
			pageSql+="	  	  D.TOC_CUMULATIVE_TOTAL 运营成本,D.TOC_MAINTAIN_Y 维护成本,D.TOC_SITE_LEASING_Y 场租成本,D.TOC_POWER_COST_Y 电力成本,D.TOC_OIL_COST_Y 油机发电成本,D.ZJ_DAD_CUMULATIVE_TOTAL 折旧摊销,D.GL_ME_CUMULATIVE_TOTAL 管理费用,D.FINANCE_COST_Y 财务费用,D.SS_GROSS_PROFIT 单站毛利    ";
			pageSql+="	  FROM("+sql+") A,RMW.SITE_VIS_BASICINFO B,RMW.SITE_VIS_ALARMINFO C,RMW.SITE_VIS_OUTIN D WHERE A.SITE_CODE = B.SITE_CODE(+) AND A.SITE_CODE = C.SITE_CODE(+) AND A.SITE_CODE = D.SITE_CODE(+)";
			pageSql+=") W WHERE 1 = 1 ";
			if(runState!=null && !"".equals(runState) && !"-".equals(runState)){
				pageSql+=" AND W.站址运营状态    = '"+runState+"'";
			}
			Integer totalCount = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM ("+pageSql+")");
			resultObject.put("COUNT",totalCount);
			String finalSql = "";
			finalSql+="SELECT F.*,CASE WHEN (SELECT COUNT(*) FROM TOWERCRNOP.YWJK_ACTIVE_ALARM V WHERE V.ALARM_NAME = '一级低压脱离告警' AND V.SITE_CODE = F.SITE_CODE)>0 THEN 1 ELSE 0 END AS IS_STOP_SERVICE FROM(";
			finalSql+="	  SELECT K.*,CASE WHEN (SELECT COUNT(*) FROM TOWERCRNOP.YWJK_ACTIVE_ALARM G WHERE G.ALARM_NAME = '交流输入停电告警' AND G.SITE_CODE = K.SITE_CODE)>0 THEN 1 ELSE 0 END AS IS_STOP_ELECTRIC FROM("+pageSql+" AND ROWNUM <= 1000) K";
			finalSql+=") F";
			Integer stopElectric = 0;
			Integer stopServices = 0;
			List<Map<String,Object>> siteList = null;

			if(totalCount>1000){
				System.out.println("=>根据查询条件共找到多于1000个站址. 即将查找前1000个.");
			}else{
				System.out.println("=>根据查询条件共找到"+totalCount+"个站址.");
				siteList = jdbcTemplate.queryForList(finalSql);
				if(siteList.size()>0){
					for(int i=0;i<siteList.size();i++){
						Map<String,Object> siteInfo = siteList.get(i);
						if(siteInfo.get("IS_STOP_ELECTRIC")!=null && "1".equals(siteInfo.get("IS_STOP_ELECTRIC").toString())){
							siteList.get(i).put("运行状态","已停电");
							stopElectric++;
						}else if(siteInfo.get("IS_STOP_SERVICE")!=null && "1".equals(siteInfo.get("IS_STOP_SERVICE").toString())){
							siteList.get(i).put("运行状态","已退服");
							stopServices++;
						}else{
							siteList.get(i).put("运行状态","正常");
						}
					}
				}
			}
			resultObject.put("SITES",siteList);
			/*
			 * 	停电.
			 *
			 * */
			/*String stopElecSql = "";
			stopElecSql+="SELECT COUNT(*) FROM (";
			stopElecSql+="	  SELECT F.*,CASE WHEN (SELECT COUNT(*) FROM TOWERCRNOP.YWJK_ACTIVE_ALARM V WHERE V.ALARM_NAME = '一级低压脱离告警' AND V.SITE_CODE = F.SITE_CODE)>0 THEN 1 ELSE 0 END AS IS_STOP_SERVICE FROM(";
			stopElecSql+="	  	  SELECT K.*,CASE WHEN (SELECT COUNT(*) FROM TOWERCRNOP.YWJK_ACTIVE_ALARM G WHERE G.ALARM_NAME = '交流输入停电告警' AND G.SITE_CODE = K.SITE_CODE)>0 THEN 1 ELSE 0 END AS IS_STOP_ELECTRIC FROM("+pageSql+") K";
			stopElecSql+="	  ) F";
			stopElecSql+=") X WHERE X.IS_STOP_ELECTRIC = 1";
			resultObject.put("STOP_ELECTRIC",jdbcTemplate.queryForInt(stopElecSql));*/
			resultObject.put("STOP_ELECTRIC",stopElectric);

			/*
			 * 	退服.
			 *
			 * */
			/*stopElecSql = "";
			stopElecSql+="SELECT COUNT(*) FROM (";
			stopElecSql+="	  SELECT F.*,CASE WHEN (SELECT COUNT(*) FROM TOWERCRNOP.YWJK_ACTIVE_ALARM V WHERE V.ALARM_NAME = '一级低压脱离告警' AND V.SITE_CODE = F.SITE_CODE)>0 THEN 1 ELSE 0 END AS IS_STOP_SERVICE FROM(";
			stopElecSql+="	  	  SELECT K.*,CASE WHEN (SELECT COUNT(*) FROM TOWERCRNOP.YWJK_ACTIVE_ALARM G WHERE G.ALARM_NAME = '交流输入停电告警' AND G.SITE_CODE = K.SITE_CODE)>0 THEN 1 ELSE 0 END AS IS_STOP_ELECTRIC FROM("+pageSql+") K";
			stopElecSql+="	  ) F";
			stopElecSql+=") X WHERE X.IS_STOP_SERVICE = 1";*/
			/*resultObject.put("STOP_SERVICES",jdbcTemplate.queryForInt(stopElecSql));*/
			resultObject.put("STOP_SERVICES",stopServices);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}

	/**
	 *  获取告警站点
	 * @param cityName  地市
	 * @param regionName 区县
	 * @param runState 运营状态
	 * @param siteType 站址类型
	 * @param siteCode 站址编码
	 * @param siteName  站址名称
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getSitesAlarm.ilf")
	public void getSitesAlarm(
		@RequestParam String cityName,
		@RequestParam String regionName,
		@RequestParam String runState,
		@RequestParam String siteType,
		@RequestParam String siteCode,
		@RequestParam String siteName,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			String sql = "";
			sql+="SELECT ";
			sql+="	  A.ZH_LABEL,A.SITE_CODE,A.SITE_NAME,A.SITE_TYPE,A.LATITUDE,A.LONGITUDE,A.LONGITUDE||','||A.LATITUDE AS 经纬度,";
			sql+="	  B.REGION_ID 区县,B.PROP_CHAR 原产权单位,B.BUSINESS_SCENE 业务场景,B.SHARE_UNIT 共享单位,B.TOWER 铁塔数量,B.ROOM 机房数量,B.SWITCH 开关电源数量,B.STORAGE 蓄电池组数量,B.AIR 空调数量,B.ENVIR 动环数量,";
			sql+="	  C.STATE 运行状态,C.ALARM_NUM 告警数量,";
			sql+="	  D.STATE 站址运营状态,D.TZ_ICB_TOTAL 投资合计,D.TOI_CUMULATIVE_TOTAL 经营收入,D.TOI_TOWER_RENT_Y 塔租收入,D.TOI_MAINTAIN_FEE_Y 维护费收入,";
			sql+="	  D.TOI_RENTAL_COSTS_Y 场租收入,D.TOI_POWER_INTRODUCE_Y 电力引入收入,D.TOI_OIL_SERVICE_Y 油机发电收入,D.TOI_NEW_BUSINESS_Y 新业务收入,";
			sql+="	  D.TOC_CUMULATIVE_TOTAL 运营成本,D.TOC_MAINTAIN_Y 维护成本,D.TOC_SITE_LEASING_Y 场租成本,D.TOC_POWER_COST_Y 电力成本,D.TOC_OIL_COST_Y 油机发电成本,";
			sql+="	  D.ZJ_DAD_CUMULATIVE_TOTAL 折旧摊销,D.GL_ME_CUMULATIVE_TOTAL 管理费用,D.FINANCE_COST_Y 财务费用,D.SS_GROSS_PROFIT 单站毛利   ";
			sql+="FROM(";
			sql+="	  SELECT ZH_LABEL,SITE_CODE,SITE_NAME,SITE_TYPE,STATE,LATITUDE,LONGITUDE,ROWNUM AS RN ";
			sql+="	  FROM RMW.ZG_SITE WHERE LONGITUDE IS NOT NULL AND LATITUDE IS NOT NULL ";
			StringBuffer strSql = new StringBuffer(sql);
			StringBuffer strSqlSotpService = new StringBuffer();

			if(siteType!=null && !"".equals(siteType) && !"-".equals(siteType)){
				strSql.append(" AND SITE_TYPE = '"+siteType+"'");
			}
			if(siteCode!=null && !"".equals(siteCode) && !"-".equals(siteCode)){
				strSql.append(" AND SITE_CODE LIKE '%"+siteCode+"%'");
			}
			if(siteName!=null && !"".equals(siteName) && !"-".equals(siteName)){
				strSql.append(" AND SITE_NAME LIKE '%"+siteName+"%'");

			}
			if(cityName!=null && !"".equals(cityName) && !"-".equals(cityName)){
				if(cityName.length()>2){
					cityName = cityName.substring(0,2);
				}
				strSql.append(" AND CITY LIKE '%"+cityName+"%'");
			}else{
				if(!loginUserUtil.isProvince(request)){
					strSql.append(" AND CITY LIKE '%"+loginUserUtil.getBelongArea(request)+"%'");
				}
			}
			if (regionName != null && !"".equals(regionName) && !"-".equals(regionName)) {
				strSql.append(" AND REGION_ID LIKE '%" + regionName + "%'");
			}
			strSqlSotpService.append(strSql.toString());//退服sql
			int stopEle = 0;
			int StopService = 0;
			//查询停电
			List<Map<String, Object>> siteList = jdbcTemplate.queryForList(strSql.append(this.getAlarmSql("交流输入停电告警", runState).toString()).toString());
			if (siteList.size() > 0) {
				for (int i = 0; i < siteList.size(); i++) {
					Map<String, Object> siteInfo = siteList.get(i);
					siteList.get(i).put("运行状态", "已停电");
					stopEle++;
				}
			}
			resultObject.put("STOP_ELECTRIC", stopEle);//告警数
			//查询退服
			List<Map<String, Object>> siteListService = jdbcTemplate.queryForList(strSqlSotpService.append(this.getAlarmSql("一级低压脱离告警", runState)).toString());
			if (siteListService.size() > 0) {
				for (int i = 0; i < siteListService.size(); i++) {
					Map<String, Object> siteInfo = siteListService.get(i);
					siteList.get(i).put("运行状态", "已退服");
					StopService++;
				}
				siteList.addAll(siteListService);
			}
			resultObject.put("STOP_SERVICES", StopService);//退服数

			resultObject.put("SITES", siteList);//告警站点数
			Integer count = (Integer) this.getSiteAll(cityName,regionName,runState,siteType,siteCode,siteName,request,true);
			resultObject.put("COUNT", count);//总数



		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	/**
	 *  条件查询后,显示正常站点
	 * @param cityName  地市
	 * @param regionName 区县
	 * @param runState 运营状态
	 * @param siteType 站址类型
	 * @param siteCode 站址编码
	 * @param siteName  站址名称
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getSitesWhereAll.ilf")
	public void getSitesWhereAll(
		@RequestParam String cityName,
		@RequestParam String regionName,
		@RequestParam String runState,
		@RequestParam String siteType,
		@RequestParam String siteCode,
		@RequestParam String siteName,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
       try {
         Integer resultCount = (Integer) this.getSiteAll(cityName,regionName,runState,siteType,siteCode,siteName,request,true);
		   resultObject.put("COUNT",resultCount);
		   if(resultCount < 4000){
			   List<Map<String,Object>> siteList = (List<Map<String,Object>>) this.getSiteAll(cityName,regionName,runState,siteType,siteCode,siteName,request,false);
			   resultObject.put("SITES",siteList.subList(0,1000));

		   }else{
		   	System.out.println("getSitesWhereAll======>正常站点大于4000个,不予显示");
		   }
	   }catch (Exception e){
		   e.printStackTrace();
		   resultObject.put("SUCCESS",false);
	   }finally {
		   response.setContentType("application/json;charset=UTF-8");
		   response.getWriter().print(resultObject.toString());
	   }




	}


	/**
	 * 获取全部站点
	 * @return
	 */
	private Object getSiteAll(
		String cityName,
		String regionName,
		String runState,
		String siteType,
		String siteCode,
		String siteName,
		HttpServletRequest request,
		boolean flag
	){
		String sql = "";
		sql+="SELECT ";
		sql+="	  A.ZH_LABEL,A.SITE_CODE,A.SITE_NAME,A.SITE_TYPE,A.LATITUDE,A.LONGITUDE,A.LONGITUDE||','||A.LATITUDE AS 经纬度,";
		sql+="	  B.REGION_ID 区县,B.PROP_CHAR 原产权单位,B.BUSINESS_SCENE 业务场景,B.SHARE_UNIT 共享单位,B.TOWER 铁塔数量,B.ROOM 机房数量,B.SWITCH 开关电源数量,B.STORAGE 蓄电池组数量,B.AIR 空调数量,B.ENVIR 动环数量,";
		sql+="	  C.STATE 运行状态,C.ALARM_NUM 告警数量,";
		sql+="	  D.STATE 站址运营状态,D.TZ_ICB_TOTAL 投资合计,D.TOI_CUMULATIVE_TOTAL 经营收入,D.TOI_TOWER_RENT_Y 塔租收入,D.TOI_MAINTAIN_FEE_Y 维护费收入,";
		sql+="	  D.TOI_RENTAL_COSTS_Y 场租收入,D.TOI_POWER_INTRODUCE_Y 电力引入收入,D.TOI_OIL_SERVICE_Y 油机发电收入,D.TOI_NEW_BUSINESS_Y 新业务收入,";
		sql+="	  D.TOC_CUMULATIVE_TOTAL 运营成本,D.TOC_MAINTAIN_Y 维护成本,D.TOC_SITE_LEASING_Y 场租成本,D.TOC_POWER_COST_Y 电力成本,D.TOC_OIL_COST_Y 油机发电成本,";
		sql+="	  D.ZJ_DAD_CUMULATIVE_TOTAL 折旧摊销,D.GL_ME_CUMULATIVE_TOTAL 管理费用,D.FINANCE_COST_Y 财务费用,D.SS_GROSS_PROFIT 单站毛利   ";
		sql+="FROM(";
		sql+="	  SELECT ZH_LABEL,SITE_CODE,SITE_NAME,SITE_TYPE,STATE,LATITUDE,LONGITUDE,ROWNUM AS RN ";
		sql+="	  FROM RMW.ZG_SITE WHERE LONGITUDE IS NOT NULL AND LATITUDE IS NOT NULL ";
		StringBuffer strSql = new StringBuffer(sql);
		StringBuffer strSqlSotpService = new StringBuffer();

		if(siteType!=null && !"".equals(siteType) && !"-".equals(siteType)){
			strSql.append(" AND SITE_TYPE = '"+siteType+"'");
		}
		if(siteCode!=null && !"".equals(siteCode) && !"-".equals(siteCode)){
			strSql.append(" AND SITE_CODE LIKE '%"+siteCode+"%'");
		}
		if(siteName!=null && !"".equals(siteName) && !"-".equals(siteName)){
			strSql.append(" AND SITE_NAME LIKE '%"+siteName+"%'");

		}
		if(cityName!=null && !"".equals(cityName) && !"-".equals(cityName)){
			if(cityName.length()>2){
				cityName = cityName.substring(0,2);
			}
			strSql.append(" AND CITY LIKE '%"+cityName+"%'");
		}else{
			if(!loginUserUtil.isProvince(request)){
				strSql.append(" AND CITY LIKE '%"+loginUserUtil.getBelongArea(request)+"%'");
			}
		}
		if(regionName!=null && !"".equals(regionName) && !"-".equals(regionName)){
			strSql.append(" AND REGION_ID LIKE '%"+regionName+"%'");
		}

		strSql.append(" AND  SITE_CODE NOT IN (SELECT DISTINCT (SITE_CODE)\n" +
				"    FROM TOWERCRNOP.YWJK_ACTIVE_ALARM\n" +
				"      WHERE ALARM_NAME = '一级低压脱离告警')   AND SITE_CODE " +
				"NOT IN (SELECT DISTINCT (SITE_CODE)\n" +
				"                               FROM TOWERCRNOP.YWJK_ACTIVE_ALARM\n" +
				"                              WHERE ALARM_NAME = '交流输入停电告警')) A,\n" +
				"       RMW.SITE_VIS_BASICINFO B,\n" +
				"       RMW.SITE_VIS_ALARMINFO C,\n" +
				"       RMW.SITE_VIS_OUTIN D\n" +
				" WHERE A.SITE_CODE = B.SITE_CODE(+)\n" +
				"   AND A.SITE_CODE = C.SITE_CODE(+)\n" +
				"   AND A.SITE_CODE = D.SITE_CODE(+)");
		if (runState != null && !"".equals(runState) && !"-".equals(runState)) {
			strSql.append(" AND D.STATE    = '" + runState + "'");
		}
		if (flag) {//true 查询count
         String sqls  = "select count(*) from ("+strSql.toString()+")";
			Integer totalCount = jdbcTemplate.queryForInt(sqls);
          return totalCount;
		} else {//false 查询数据
			List<Map<String,Object>> siteList = jdbcTemplate.queryForList(strSql.toString());
             if(siteList.size() > 0) {
				 for (int i = 0; i < siteList.size(); i++) {
					 Map<String, Object> map = siteList.get(i);
					 map.put("运行状态", "正常");
				 }
			 }
			 return  siteList;
		}
	}

	/*
	 * 	根据经纬度计算范围.
	 * 
	 * */
	public static MarginLocation findMarginLocation(Double latitude,Double longitude,Double circleLine){   
		Location[] locations = LatitudeLontitudeUtil.getRectangle4Point(latitude,longitude,circleLine);
		Double topLine = locations[0].getLatitude();
        Double bottomLine = locations[2].getLatitude();
        Double leftLine = locations[0].getLongitude();
        Double rightLine = locations[1].getLongitude();
        if(bottomLine.toString().length()>=16){
        	bottomLine = Double.parseDouble(bottomLine.toString().substring(0,15));
        }
        if(topLine.toString().length()>=16){
        	topLine = Double.parseDouble(topLine.toString().substring(0,15));
        }
        if(leftLine.toString().length()>=16){
        	leftLine = Double.parseDouble(leftLine.toString().substring(0,15));
        }
        if(rightLine.toString().length()>=16){
        	rightLine = Double.parseDouble(rightLine.toString().substring(0,15));
        }
        MarginLocation location = new MarginLocation();
        location.setTopBorder(topLine);
        location.setBottomBorder(bottomLine);
        location.setLeftBorder(leftLine);
        location.setRightBorder(rightLine);
        return location;
	}
	/**
	 * 辅助方法
	 * @return sql
	 */
	private String  getAlarmSql(String flag,String runState){
		String sql = " AND SITE_CODE IN (SELECT DISTINCT (SITE_CODE)\n" +
				"                               FROM TOWERCRNOP.YWJK_ACTIVE_ALARM\n" +
				"                              WHERE ALARM_NAME = '"+flag+"')) A,\n" +
				"       RMW.SITE_VIS_BASICINFO B,\n" +
				"       RMW.SITE_VIS_ALARMINFO C,\n" +
				"       RMW.SITE_VIS_OUTIN D\n" +
				" WHERE A.SITE_CODE = B.SITE_CODE(+)\n" +
				"   AND A.SITE_CODE = C.SITE_CODE(+)\n" +
				"   AND A.SITE_CODE = D.SITE_CODE(+)";
		if(runState!=null && !"".equals(runState) && !"-".equals(runState)){
			sql+=" AND D.STATE    = '"+runState+"'";
		}
		return sql;
	}


	/*
	 * 	查看告警明细
	 * 
	 * */
	@RequestMapping("/findAlarmDetailsPage.ilf")
	public void findAlarmDetailsPage(
		@RequestParam String tableparam,
		@RequestParam String conditions,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		Long sEcho = 0L;
		Integer displayStart = 0;
		Integer iDisplayLength = 0;
		JSONArray jsons = JSONArray.fromObject(tableparam);
		HashMap<String,Object> conditonMap = new HashMap<String,Object>();
		if(jsons!=null && jsons.size()!=0){
			for(int i=0;i<jsons.size();i++){
				JSONObject json = JSONObject.fromObject(jsons.get(i));
				String key = json.getString("name");
				if(key.equals("sEcho")){
					sEcho = Long.parseLong(json.getString("value"));
				}else if(key.equals("iDisplayStart")){
					displayStart = Integer.parseInt(json.getString("value"));
					conditonMap.put("iDisplayStart",displayStart);
				}else if(key.equals("iDisplayLength")){
					iDisplayLength = Integer.parseInt(json.getString("value"));
					conditonMap.put("iDisplayLength",iDisplayLength);
				}
			}
		}
		JSONArray condition = JSONArray.fromObject(conditions);
		if(conditions!=null && condition.size()!=0){
			for(int i=0;i<condition.size();i++){
				JSONObject jsonObject = JSONObject.fromObject(condition.get(i));
				if(jsonObject.get("value")!=null && !"".equals(jsonObject.getString("value"))){
					conditonMap.put(jsonObject.getString("name"),jsonObject.getString("value"));
				}
			}
		}
		Integer count = 0;
		List<Map<String,Object>> objectList = new ArrayList<Map<String,Object>>();
		String sql = "";
		if(conditonMap.containsKey("DATA_TYPE") && !"".equals(conditonMap.get("DATA_TYPE").toString())){
			if("停电".equals(conditonMap.get("DATA_TYPE").toString())){
				/*
				 * 	停电站点.
				 * 
				 * */
				sql+="SELECT SITE_CODE,SITE_NAME,'已停电' AS SITE_STATE,SITE_TYPE,STATE,LONGITUDE,LATITUDE ";
				sql+="FROM RMW.ZG_SITE WHERE LATITUDE IS NOT NULL AND LONGITUDE IS NOT NULL AND SITE_CODE IN(";
				sql+="	  SELECT DISTINCT(SITE_CODE) FROM TOWERCRNOP.YWJK_ACTIVE_ALARM WHERE ALARM_NAME = '交流输入停电告警'";
				sql+=")";
				if(conditonMap.containsKey("CITY") && !"".equals(conditonMap.get("CITY").toString()) && !"-".equals(conditonMap.get("CITY").toString())){
					/*
					 * 	选择了地市.
					 * 
					 * */
					sql+=" AND CITY LIKE '%"+conditonMap.get("CITY").toString()+"%'";
					if(conditonMap.containsKey("REGION") && !"".equals(conditonMap.get("REGION").toString()) && !"-".equals(conditonMap.get("REGION").toString())){
						sql+=" AND REGION_ID LIKE '%"+conditonMap.get("REGION").toString()+"%'";
					}
				}else{
					/*
					 * 	查看全省的数据,无需再判断区县.
					 * 
					 * */
				}
				if(conditonMap.containsKey("SITE_CODE_OR_NAME") && !"".equals(conditonMap.get("SITE_CODE_OR_NAME").toString())){
					sql+=" AND (SITE_CODE LIKE '%"+conditonMap.get("SITE_CODE_OR_NAME").toString()+"%' OR SITE_NAME LIKE '%"+conditonMap.get("SITE_CODE_OR_NAME").toString()+"%')";
				}
				sql+=" ORDER BY SITE_CODE ASC";
				count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM ("+sql+") A");
				String pageSql = "";
				pageSql+="SELECT C.* FROM (";
				pageSql+="	  SELECT B.*,ROWNUM AS RN FROM("+sql+") B WHERE ROWNUM <= "+(displayStart+iDisplayLength);
				pageSql+=") C WHERE C.RN > "+displayStart;
				objectList = jdbcTemplate.queryForList(pageSql);
			}else if("退服".equals(conditonMap.get("DATA_TYPE").toString())){
				/*
				 * 	退服站点.
				 * 
				 * */
				sql+="SELECT SITE_CODE,SITE_NAME,'已退服' AS SITE_STATE,SITE_TYPE,STATE,LONGITUDE,LATITUDE ";
				sql+="FROM RMW.ZG_SITE WHERE LATITUDE IS NOT NULL AND LONGITUDE IS NOT NULL AND SITE_CODE IN(";
				sql+="	  SELECT DISTINCT(SITE_CODE) FROM TOWERCRNOP.YWJK_ACTIVE_ALARM WHERE ALARM_NAME = '一级低压脱离告警'";
				sql+=")";
				if(conditonMap.containsKey("CITY") && !"".equals(conditonMap.get("CITY").toString()) && !"-".equals(conditonMap.get("CITY").toString())){
					/*
					 * 	选择了地市.
					 * 
					 * */
					sql+=" AND CITY LIKE '%"+conditonMap.get("CITY").toString()+"%'";
					if(conditonMap.containsKey("REGION") && !"".equals(conditonMap.get("REGION").toString()) && !"-".equals(conditonMap.get("REGION").toString())){
						sql+=" AND REGION_ID LIKE '%"+conditonMap.get("REGION").toString()+"%'";
					}
				}else{
					/*
					 * 	查看全省的数据,无需再判断区县.
					 * 
					 * */
				}
				if(conditonMap.containsKey("SITE_CODE_OR_NAME") && !"".equals(conditonMap.get("SITE_CODE_OR_NAME").toString())){
					sql+=" AND (SITE_CODE LIKE '%"+conditonMap.get("SITE_CODE_OR_NAME").toString()+"%' OR SITE_NAME LIKE '%"+conditonMap.get("SITE_CODE_OR_NAME").toString()+"%')";
				}
				sql+=" ORDER BY SITE_CODE ASC";
				count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM ("+sql+") A");
				String pageSql = "";
				pageSql+="SELECT C.* FROM (";
				pageSql+="	  SELECT B.*,ROWNUM AS RN FROM("+sql+") B WHERE ROWNUM <= "+(displayStart+iDisplayLength);
				pageSql+=") C WHERE C.RN > "+displayStart;
				objectList = jdbcTemplate.queryForList(pageSql);
			}
		}else{
			count = 0;
			objectList = new ArrayList<Map<String,Object>>();
		}
		DataTableResult<Map<String,Object>> tableData = new DataTableResult<Map<String,Object>>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(objectList);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
}
