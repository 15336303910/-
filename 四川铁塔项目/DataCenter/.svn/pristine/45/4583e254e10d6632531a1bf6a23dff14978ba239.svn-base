package com.function.index.action;
import java.util.ArrayList;
import java.util.Date;
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

import com.function.index.model.IndexDetail;
import com.function.index.model.IndexUser;
import com.function.index.service.IndexDetailService;
import com.function.index.service.IndexUserService;
import com.systemConfig.model.DataTableResult;
@Controller("com.function.index.action.IndexUserAction")
@RequestMapping(value="/indexUserAction")
public class IndexUserAction{
	
	@Autowired
	private IndexUserService indexUserService;
	
	@Autowired
	private IndexDetailService indexDetailService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*
	 * 	获取用户信息
	 * 
	 * */
	@RequestMapping("/findUserDetail.ilf")
	public void findUserDetail(@RequestParam String userCode,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			Map<String,Object> userMap = jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_USER WHERE ID = "+userCode);
			resultObject.put("userMap",userMap);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	查询指标[未被关联；已被关联]
	 * 
	 * */
	@RequestMapping("/findItems.ilf")
	public void findItems(@RequestParam String tableparam,@RequestParam String conditions,HttpServletResponse response)throws Exception{
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
		Boolean isCount = false;
		Integer userCode = -1;
		if(conditonMap.containsKey("EXPECT_USER")){
			userCode = Integer.parseInt(conditonMap.get("EXPECT_USER").toString());
			List<Map<String,Object>> checkedIndexes = jdbcTemplate.queryForList("SELECT * FROM INDEX_USER WHERE USER_ID = "+userCode);
			if(checkedIndexes.size()>0){
				String indexCodes = "";
				for(int i=0;i<checkedIndexes.size();i++){
					String indexCode = checkedIndexes.get(i).get("INDEX_ID").toString();
					if("".equals(indexCodes)){
						indexCodes = indexCode;
					}else{
						indexCodes+= ","+indexCode;
					}
				}
				conditonMap.put("EXPECT_IDS",indexCodes);
			}
			conditonMap.remove("EXPECT_USER");
		}
		if(conditonMap.containsKey("BELONG_USER")){
			isCount = true;
			userCode = Integer.parseInt(conditonMap.get("BELONG_USER").toString());
			List<Map<String,Object>> checkedIndexes = jdbcTemplate.queryForList("SELECT * FROM INDEX_USER WHERE USER_ID = "+userCode);
			if(checkedIndexes.size()>0){
				String indexCodes = "";
				for(int i=0;i<checkedIndexes.size();i++){
					String indexCode = checkedIndexes.get(i).get("INDEX_ID").toString();
					if("".equals(indexCodes)){
						indexCodes = indexCode;
					}else{
						indexCodes+= ","+indexCode;
					}
				}
				conditonMap.put("IN_IDS",indexCodes);
			}else{
				conditonMap.put("IN_IDS",-1);
			}
			conditonMap.remove("BELONG_USER");
		}		
		Integer count = indexDetailService.getCount(conditonMap);
		List<IndexDetail> indexDetails = indexDetailService.getDbPage(conditonMap);	
		if(isCount && indexDetails.size()>0){
			for(int i=0;i<indexDetails.size();i++){
				IndexUser indexUser = indexUserService.queryOneByHql("from IndexUser where USER_ID = "+userCode+" and INDEX_ID = "+indexDetails.get(i).getID());
				if(indexUser!=null){
					indexDetails.get(i).setINDEX_LEVEL(indexUser.getCOUNT_RATIO());
				}else{
					indexDetails.get(i).setINDEX_LEVEL(0);
				}				
			}
		}	
		DataTableResult<IndexDetail> tableData = new DataTableResult<IndexDetail>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(indexDetails);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	保存指标与规则的关联
	 * 
	 * */
	
	@RequestMapping("/saveAudit.ilf")
	public void saveAudit(@RequestParam String userCode,@RequestParam String indexCodes,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'账户与指标关联成功.'}");
		try{
			String[] indexIds = indexCodes.split(",");
			if(indexIds.length>0){
				for(int i=0;i<indexIds.length;i++){
					IndexUser indexUser = new IndexUser();
					indexUser.setUSER_ID(Integer.parseInt(userCode));
					indexUser.setINDEX_ID(Integer.parseInt(indexIds[i]));
					IndexDetail indexDetail = indexDetailService.selectOne(Integer.parseInt(indexIds[i]));
					if(indexDetail.getINDEX_LEVEL()==2){
						indexUser.setCOUNT_RATIO(20);
					}else if(indexDetail.getINDEX_LEVEL()==3){
						indexUser.setCOUNT_RATIO(30);
					}else if(indexDetail.getINDEX_LEVEL()==5){
						indexUser.setCOUNT_RATIO(50);
					}			
					indexUser.setMAINTAIN_DATE(new Date());
					indexUserService.insertModel(indexUser);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject = JSONObject.fromObject("{success:false,message:'系统异常,请联系管理员.'}");
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	取消关联
	 * 
	 * */
	@RequestMapping("/deleteChecked.ilf")
	public void deleteChecked(@RequestParam String userCode,@RequestParam String indexCodes,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'配置成功.'}");
		try{
			jdbcTemplate.execute("DELETE FROM INDEX_USER WHERE USER_ID = "+userCode+" AND INDEX_ID IN("+indexCodes+")");
		}catch(Exception e){
			e.printStackTrace();
			resultObject = JSONObject.fromObject("{success:false,message:'系统异常,请联系管理员.'}");
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	修改规则权重
	 * 
	 * */
	@RequestMapping("/updateCount.ilf")
	public void updateCount(@RequestParam String userCode,@RequestParam String indexCode,@RequestParam String count,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'权重修改成功.'}");
		try{
			jdbcTemplate.execute("UPDATE INDEX_USER SET COUNT_RATIO = "+count+" WHERE USER_ID = "+userCode+" AND INDEX_ID = "+indexCode);
		}catch(Exception e){
			e.printStackTrace();
			resultObject = JSONObject.fromObject("{success:false,message:'系统异常,请联系管理员.'}");
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	指标下各规则的占比统计
	 * 
	 * */
	@RequestMapping("/accountRatio.ilf")
	public void accountRatio(@RequestParam String userCode,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'指标权重统计成功.'}");
		try{
			List<String> namesList = new ArrayList<String>();
			List<Map<String,Object>> valueList = new ArrayList<Map<String,Object>>();
			String countSql = "";
			countSql+="SELECT ID AS INDEX_ID,INDEX_NAME,INDEX_LEVEL FROM INDEX_DETAIL WHERE ID IN(";
			countSql+="	  SELECT INDEX_ID FROM INDEX_USER WHERE USER_ID = "+userCode;
			countSql+=")";
			List<Map<String,Object>> indexOfUser = jdbcTemplate.queryForList(countSql);
			for(int i=0;i<indexOfUser.size();i++){
				Map<String,Object> indexObject = indexOfUser.get(i);
				/*添加标题*/
				namesList.add(indexObject.get("INDEX_NAME").toString());
				/*添加数据*/
				Map<String,Object> valueObj = new HashMap<String,Object>();
				valueObj.put("name",indexObject.get("INDEX_NAME").toString());
				List<Map<String,Object>> relateObj = jdbcTemplate.queryForList("SELECT * FROM INDEX_USER WHERE USER_ID = "+userCode+" AND INDEX_ID = "+indexObject.get("INDEX_ID").toString());
				if(relateObj.size()>0){
					valueObj.put("value",Integer.parseInt(relateObj.get(0).get("COUNT_RATIO").toString()));
				}else{
					valueObj.put("value",0);
				}
				valueList.add(valueObj);		
			}
			resultObject.put("titles",namesList);
			resultObject.put("values",valueList);		
		}catch(Exception e){
			e.printStackTrace();
			resultObject = JSONObject.fromObject("{success:false,message:'系统异常,请联系管理员.'}");
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
}
