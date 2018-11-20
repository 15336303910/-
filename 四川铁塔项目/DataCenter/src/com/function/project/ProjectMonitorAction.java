package com.function.project;
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
import com.system.LoginUserUtil;
import com.systemConfig.model.DataTableResult;
@Controller("com.function.project.ProjectMonitorAction")
@RequestMapping(value="/projectMonitorAction")
public class ProjectMonitorAction{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	/*
	 * 	查询列表
	 * 
	 * */
	@RequestMapping("/findItems.ilf")
	public void findItems(
		@RequestParam String tableparam,
		@RequestParam String conditions,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		try{
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
			/*
			 * 	基本SQL
			 * 
			 * */
			String sql = "";
			sql+="SELECT D.*,E.NAME AS CITY_NAME,F.REGION_NAME,G.DICTNAME FROM(";
			sql+="	  SELECT A.PRJ_ID,A.SITE_ID,A.SITE_NAME,A.PRJ_TYPE,A.PRJ_YEAR,A.PROJECT_NAME,A.CITY,A.COUNTY,H.PRJ_STAGE AS PRJ_STATUS,K.DICTNAME AS PROJECT_NODE ";
			sql+="	  FROM TOWERCRNOP.PMS_MID_EXPORT_FORMS A,TOWERCRNOP.PMS_MID_PRJ_STATUS H,TOWERCRNOP.GJ_PMS_DICT K ";
			sql+="	  WHERE A.PRJ_ID = H.PRJ_ID AND H.PRJ_STAGE = K.DICTID AND K.DICTTYPEID = 'stage' ";
			if(!loginUserUtil.isProvince(request)){
				sql+="  AND A.PROJECT_NAME LIKE '%"+loginUserUtil.getBelongArea(request)+"%' ";
			}
			if(conditonMap.containsKey("PRO_NAME") && conditonMap.get("PRO_NAME")!=null && !"".equals(conditonMap.get("PRO_NAME").toString())){
				sql+="	AND A.PROJECT_NAME LIKE '%"+conditonMap.get("PRO_NAME").toString()+"%' ";
			}
			if(conditonMap.containsKey("PRO_STATUS") && conditonMap.get("PRO_STATUS")!=null && !"".equals(conditonMap.get("PRO_STATUS").toString()) && !"-".equals(conditonMap.get("PRO_STATUS").toString())){
				sql+="	AND H.PRJ_STAGE LIKE '%"+conditonMap.get("PRO_STATUS").toString()+"%' ";
			}
			sql+="	  ORDER BY A.PRJ_YEAR DESC";
			sql+=") D,TOWERCRNOP.OBP_CITY E,TOWERCRNOP.GJ_RES_SPC_REGION F,(SELECT * FROM TOWERCRNOP.GJ_PMS_DICT WHERE DICTTYPEID = 'PROJ_TYPE') G,TOWERCRNOP.PMS_MID_PRJ_STATUS H,TOWERCRNOP.GJ_PMS_DICT K WHERE D.CITY = E.CODE(+) AND D.COUNTY = F.CODE(+) AND D.PRJ_TYPE = G.DICTID(+) AND D.PRJ_ID = H.PRJ_ID AND H.PRJ_STAGE = K.DICTID AND K.DICTTYPEID = 'stage'";
			/*
			 * 	统计数量
			 * 
			 * */
			Integer count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM("+sql+")");
			/*
			 * 	分页SQL
			 * 
			 * */
			String pageSql = "";
			pageSql+="SELECT J.* FROM(";
			pageSql+="	  SELECT I.*,ROWNUM AS RN FROM (";
			pageSql+="		  "+sql;
			pageSql+="	  ) I WHERE ROWNUM <= "+(displayStart+iDisplayLength);
			pageSql+=") J WHERE J.RN > "+displayStart;
			List<Map<String,Object>> ruleJobs = jdbcTemplate.queryForList(pageSql);		
			DataTableResult<Map<String,Object>> tableData = new DataTableResult<Map<String,Object>>();
			tableData.setsEcho(sEcho);
			tableData.setAaData(ruleJobs);
			tableData.setiTotalRecords(count);
			tableData.setiTotalDisplayRecords(count);
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(JSONObject.fromObject(tableData).toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
