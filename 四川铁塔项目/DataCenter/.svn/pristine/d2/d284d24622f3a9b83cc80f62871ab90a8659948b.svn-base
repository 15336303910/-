package com.function.index;
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
import com.systemConfig.model.DataTableResult;
@Controller("com.function.index.JobPlanManageAction")
@RequestMapping(value="/jobPlanManageAction")
public class JobPlanManageAction{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*
	 * 	查看数据采集.同步.应用说明
	 * 
	 * */
	@RequestMapping("/findJobExplain.ilf")
	public void findJobExplain(
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
		String sql = "";
		sql+="SELECT ";
		sql+="	  T.DATA_NAME,T.IS_REFERENCED,T.MODULAR_NAME,T.BUSINESS_SYSTEM,";
		sql+="	  T.BELONG_DEPARTMENT,T.ACQUISITION_MODE,T.COLLECTION_CYCLE,T.CHECK_CYCLE,";
		sql+="	  T.TABLE_INFORMATION_COMB,T.IS_KEEP,T.TABLE_DERIVED_TABLE,T.NOTE ";
		sql+="FROM TOWERCRNOP.RES_COL_INSTRTABLE T ";
		sql+="WHERE T.IS_DISPLAY = 1 ";
		if(conditonMap.containsKey("PICK_TABLE_NAME") && !"".equals(conditonMap.get("PICK_TABLE_NAME").toString())){
			sql+=" AND T.DATA_NAME LIKE '%"+conditonMap.get("PICK_TABLE_NAME").toString()+"%'";
		}
		if(conditonMap.containsKey("MAPPING_SYSTEM") && !"".equals(conditonMap.get("MAPPING_SYSTEM").toString()) && !"-".equals(conditonMap.get("MAPPING_SYSTEM").toString())){
			sql+=" AND T.BUSINESS_SYSTEM = '"+conditonMap.get("MAPPING_SYSTEM").toString()+"'";
		}
		if(conditonMap.containsKey("MODULE_DATA_APPLY") && !"".equals(conditonMap.get("MODULE_DATA_APPLY").toString())){
			sql+=" AND T.MODULAR_NAME LIKE '%"+conditonMap.get("MODULE_DATA_APPLY").toString()+"%'";
		}
		if(conditonMap.containsKey("COLLECTION_CYCLE") && !"".equals(conditonMap.get("COLLECTION_CYCLE").toString()) && !"-".equals(conditonMap.get("COLLECTION_CYCLE").toString())){
			sql+=" AND T.COLLECTION_CYCLE = '"+conditonMap.get("COLLECTION_CYCLE").toString()+"'";
		}
		Integer count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM ("+sql+") A");
		String pageSql = "";
		pageSql+="SELECT C.* FROM (";
		pageSql+="	  SELECT B.*,ROWNUM AS RN FROM(";
		pageSql+="	  	  SELECT A.* FROM("+sql+") A ORDER BY A.DATA_NAME ASC";
		pageSql+="	  ) B WHERE ROWNUM <= "+(displayStart+iDisplayLength);
		pageSql+=") C WHERE C.RN > "+displayStart;
		List<Map<String,Object>> objectList = jdbcTemplate.queryForList(pageSql);
		DataTableResult<Map<String,Object>> tableData = new DataTableResult<Map<String,Object>>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(objectList);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
}
