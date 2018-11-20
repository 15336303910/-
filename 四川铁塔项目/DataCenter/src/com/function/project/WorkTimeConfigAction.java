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
@Controller("com.function.project.WorkTimeConfigAction")
@RequestMapping(value="/workTimeConfigAction")
public class WorkTimeConfigAction{

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
			sql+="SELECT A.DICTID,A.DICTNAME,B.WORK_CIRCLE_CONFIGED,B.CONFIG_USER,B.CONFIG_TIME,C.EMPLOYEE_NAME ";
			sql+="FROM (SELECT * FROM TOWERCRNOP.GJ_PMS_DICT WHERE DICTTYPEID = 'stage') A,PROJECT_WORK_CIRCLE_CONFIG B,S_SYSTEM_USER C ";
			sql+="WHERE A.DICTID = B.VALUE_IN_DICT(+) AND B.CONFIG_USER = C.ID(+) ORDER BY A.DICTID ASC ";
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
	
	/*
	 * 	编辑标准工期.
	 * 
	 * */
	@RequestMapping("/updateCount.ilf")
	public void updateCount(
		@RequestParam String nodeCode,
		@RequestParam String newCircleValue,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			if(jdbcTemplate.queryForInt("SELECT COUNT(*) FROM PROJECT_WORK_CIRCLE_CONFIG WHERE VALUE_IN_DICT = '"+nodeCode+"'")==0){
				String sql = "";
				sql+="INSERT INTO PROJECT_WORK_CIRCLE_CONFIG(";
				sql+="	  VALUE_IN_DICT,WORK_CIRCLE_CONFIGED,CONFIG_USER,CONFIG_TIME";
				sql+=")VALUES(";
				sql+="	  '"+nodeCode+"',"+newCircleValue+","+loginUserUtil.getLoginUserId(request)+",SYSDATE";
				sql+=")";
				jdbcTemplate.execute(sql);
			}else{
				jdbcTemplate.execute("UPDATE PROJECT_WORK_CIRCLE_CONFIG SET WORK_CIRCLE_CONFIGED = "+newCircleValue+",CONFIG_USER = "+loginUserUtil.getLoginUserId(request)+",CONFIG_TIME = SYSDATE WHERE VALUE_IN_DICT = '"+nodeCode+"'");
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject = JSONObject.fromObject("{SUCCESS:true}");
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
}
