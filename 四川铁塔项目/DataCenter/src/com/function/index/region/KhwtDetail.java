package com.function.index.region;
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
@Controller("com.function.index.region.KhwtDetail")
@RequestMapping(value="/khwtDetail")
public class KhwtDetail{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	private static String resUserName = "NEWIRMS";
	
	/*
	 * 	客户问题明细
	 * 
	 * */
	@RequestMapping("/findDatas.ilf")
	public void findDatas(@RequestParam String tableparam,@RequestParam String conditions,HttpServletRequest request,HttpServletResponse response)throws Exception{
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
		Boolean IS_PROVICE = false;
		String CITY_NAME = "";
		Object loginObject = request.getSession().getAttribute("LoginUserInfo");
		if(loginObject!=null){
			Map<String,Object> loginUser = (HashMap<String,Object>)loginObject;
			if(loginUser.get("BELONG_AREA").toString().indexOf("省")!=-1){
				IS_PROVICE = true;
				CITY_NAME = loginUser.get("BELONG_AREA").toString();
			}else{
				CITY_NAME = loginUser.get("BELONG_AREA").toString();
			}
			if(CITY_NAME.length()>2){
				CITY_NAME = CITY_NAME.substring(0,2);
			}
		}
		/*
		 * 	客户问题数
		 * 
		 * */
		String sql = "SELECT CITYID,PROBLEM_TYPE,PROBLEM_DESC,PROBLEM_LEVEL,PROBLEM_SOURCE FROM "+resUserName+".T_CUSTOMER_PROBLEM_INFO WHERE CITYID IS NOT NULL";
		if(conditonMap.containsKey("PROBLEM_DESC") && !"".equals(conditonMap.get("PROBLEM_DESC").toString())){
			sql+=" AND PROBLEM_DESC LIKE '%"+conditonMap.get("PROBLEM_DESC").toString()+"%'";
		}
		if(!IS_PROVICE){
			sql+=" AND CITYID LIKE '%"+CITY_NAME+"%'";
		}
		Integer count = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM("+sql+")");
		Integer lastIndex = displayStart+iDisplayLength;
		String pageSql = "";
		pageSql+="SELECT B.* FROM(";
		pageSql+="	SELECT A.*,ROWNUM AS RN FROM("+sql+") A WHERE ROWNUM <= "+lastIndex;
		pageSql+=") B WHERE B.RN > "+displayStart;
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