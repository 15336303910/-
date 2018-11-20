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

import com.function.dbmanage.service.BasicDbTableService;
import com.function.index.model.IndexDetail;
import com.function.index.model.IndexItem;
import com.function.index.service.IndexDetailService;
import com.function.index.service.IndexItemService;
import com.function.rules.model.RuleDetail;
import com.function.rules.service.RuleDetailService;
import com.systemConfig.model.DataTableResult;
@Controller("com.function.index.action.IndexDetailAction")
@RequestMapping(value="/indexDetailAction")
public class IndexDetailAction{
	
	@Autowired
	private RuleDetailService ruleDetailService;
	
	@Autowired
	private BasicDbTableService basicDbTableService;
	
	@Autowired
	private IndexDetailService indexDetailService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*
	 * 	获取单个指标
	 * 
	 * */
	@RequestMapping("/findIndexDetail.ilf")
	public void findIndexDetail(@RequestParam String indexCode,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			IndexDetail indexDetail = indexDetailService.selectOne(Integer.parseInt(indexCode));
			resultObject.put("thisOne",indexDetail);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	查询指标列表
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
		Integer count = indexDetailService.getCount(conditonMap);
		List<IndexDetail> indexDetails = indexDetailService.getDbPage(conditonMap);	
		DataTableResult<IndexDetail> tableData = new DataTableResult<IndexDetail>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(indexDetails);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	查询规则[规则池、已关联规则]
	 * 
	 * */
	@RequestMapping("/findRuleUnchecked.ilf")
	public void findRuleUnchecked(@RequestParam String tableparam,@RequestParam String conditions,HttpServletResponse response)throws Exception{
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
		try{
			Boolean isCount = false;
			Integer indexCode = -1;
			JSONArray condition = JSONArray.fromObject(conditions);
			if(conditions!=null && condition.size()!=0){
				for(int i=0;i<condition.size();i++){
					JSONObject jsonObject = JSONObject.fromObject(condition.get(i));
					if(jsonObject.get("value")!=null && !"".equals(jsonObject.getString("value"))){
						conditonMap.put(jsonObject.getString("name"),jsonObject.getString("value"));
					}
				}
			}
			if(conditonMap.containsKey("EXPECT_INDEX")){
				indexCode = Integer.parseInt(conditonMap.get("EXPECT_INDEX").toString());
				List<Map<String,Object>> checkedRules = jdbcTemplate.queryForList("SELECT * FROM INDEX_ITEMS WHERE INDEX_ID = "+indexCode);
				if(checkedRules.size()>0){
					String ruleCodes = "";
					for(int i=0;i<checkedRules.size();i++){
						String ruleCode = checkedRules.get(i).get("RULE_ID").toString();
						if("".equals(ruleCodes)){
							ruleCodes = ruleCode;
						}else{
							ruleCodes+= ","+ruleCode;
						}
					}
					conditonMap.put("EXPECT_IDS",ruleCodes);
				}
				conditonMap.remove("EXPECT_INDEX");
			}
			if(conditonMap.containsKey("BELONG_INDEX")){
				isCount = true;
				indexCode = Integer.parseInt(conditonMap.get("BELONG_INDEX").toString());
				List<Map<String,Object>> checkedRules = jdbcTemplate.queryForList("SELECT * FROM INDEX_ITEMS WHERE INDEX_ID = "+indexCode);
				if(checkedRules.size()>0){
					String ruleCodes = "";
					for(int i=0;i<checkedRules.size();i++){
						String ruleCode = checkedRules.get(i).get("RULE_ID").toString();
						if("".equals(ruleCodes)){
							ruleCodes = ruleCode;
						}else{
							ruleCodes+= ","+ruleCode;
						}
					}
					conditonMap.put("BELONG_INDEX",ruleCodes);
				}else{
					conditonMap.put("BELONG_INDEX",-1);
				}
			}
			Integer count = ruleDetailService.getCount(conditonMap);
			List<RuleDetail> ruleDetails = ruleDetailService.getDbPage(conditonMap);	
			if(ruleDetails.size()>0){
				for(int i=0;i<ruleDetails.size();i++){
					ruleDetails.get(i).setTABLE_NAME(basicDbTableService.selectModel(ruleDetails.get(i).getBIND_TABLE()).getTABLE_ALIAS());
					if(isCount){
						IndexItem indexItem = indexItemService.queryOneByHql("from IndexItem where INDEX_ID = "+indexCode+" and RULE_ID = "+ruleDetails.get(i).getID());
						if(indexItem!=null){
							ruleDetails.get(i).setCOUNT_RATIO(indexItem.getCOUNT_RATIO());
						}
					}
				}
			}
			DataTableResult<RuleDetail> tableData = new DataTableResult<RuleDetail>();
			tableData.setsEcho(sEcho);
			tableData.setAaData(ruleDetails);
			tableData.setiTotalRecords(count);
			tableData.setiTotalDisplayRecords(count);
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(JSONObject.fromObject(tableData).toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Autowired
	private IndexItemService indexItemService;
	
	/*
	 * 	保存指标与规则的关联
	 * 
	 * */	
	@RequestMapping("/saveAudit.ilf")
	public void saveAudit(@RequestParam String indexCode,@RequestParam String ruleCodes,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'指标与规则关联成功.'}");
		try{
			String[] ruleIds = ruleCodes.split(",");
			if(ruleIds.length>0){
				for(int i=0;i<ruleIds.length;i++){
					IndexItem indexItem = new IndexItem();
					indexItem.setINDEX_ID(Integer.parseInt(indexCode));
					indexItem.setRULE_ID(Integer.parseInt(ruleIds[i]));
					Integer ruleLevel = ruleDetailService.selectModel(Integer.parseInt(ruleIds[i])).getRULE_GRADE();
					if(ruleLevel==2){
						indexItem.setCOUNT_RATIO(20);
					}else if(ruleLevel==3){
						indexItem.setCOUNT_RATIO(30);
					}else if(ruleLevel==5){
						indexItem.setCOUNT_RATIO(50);
					}
					indexItem.setMAINTAIN_DATE(new Date());
					indexItemService.insertModel(indexItem);
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
	 * 	保存/修改指标基本信息
	 * 
	 * */
	@RequestMapping("/saveBasic.ilf")
	public void saveBasic(@RequestParam String params,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'配置成功.'}");
		try{
			JSONObject jsonObject = JSONObject.fromObject(params);
			Integer newCode = Integer.parseInt(jsonObject.get("indexCode").toString());
			if(jsonObject.get("indexCode")!=null && !"-1".equals(jsonObject.get("indexCode").toString())){
				IndexDetail indexDetail = indexDetailService.selectOne(Integer.parseInt(jsonObject.get("indexCode").toString()));
				indexDetail.setINDEX_NAME(jsonObject.getString("indexName"));
				indexDetail.setINDEX_LEVEL(Integer.parseInt(jsonObject.getString("indexLevel")));
				indexDetail.setIS_USING(jsonObject.getString("isUsing"));
				indexDetailService.updateModel(indexDetail);
			}else{
				IndexDetail indexDetail = new IndexDetail();
				indexDetail.setCREATE_DATE(new Date());
				indexDetail.setINDEX_NAME(jsonObject.getString("indexName"));
				indexDetail.setINDEX_LEVEL(Integer.parseInt(jsonObject.getString("indexLevel")));
				indexDetail.setIS_USING(jsonObject.getString("isUsing"));
				newCode = indexDetailService.insertModel(indexDetail);
			}
			resultObject.put("newCode",newCode);
		}catch(Exception e){
			e.printStackTrace();
			resultObject = JSONObject.fromObject("{success:false,message:'系统异常,请联系管理员.'}");
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	删除指标下包含的规则
	 * 
	 * */
	@RequestMapping("/deleteChecked.ilf")
	public void deleteChecked(@RequestParam String indexCode,@RequestParam String ruleCodes,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'配置成功.'}");
		try{
			jdbcTemplate.execute("DELETE FROM INDEX_ITEMS WHERE INDEX_ID = "+indexCode+" AND RULE_ID IN("+ruleCodes+")");
		}catch(Exception e){
			e.printStackTrace();
			resultObject = JSONObject.fromObject("{success:false,message:'系统异常,请联系管理员.'}");
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	删除指标
	 * 
	 * */
	@RequestMapping("/deleteIndex.ilf")
	public void deleteIndex(@RequestParam String indexCode,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'指标删除成功.'}");
		try{
			jdbcTemplate.execute("DELETE FROM INDEX_ITEMS WHERE INDEX_ID = "+indexCode);
			jdbcTemplate.execute("DELETE FROM INDEX_DETAIL WHERE ID = "+indexCode);
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
	public void updateCount(@RequestParam String indexCode,@RequestParam String ruleCode,@RequestParam String count,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'权重修改成功.'}");
		try{
			jdbcTemplate.execute("UPDATE INDEX_ITEMS SET COUNT_RATIO = "+count+" WHERE INDEX_ID = "+indexCode+" AND RULE_ID = "+ruleCode);
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
	public void accountRatio(@RequestParam String indexCode,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'规则统计完毕.'}");
		try{
			List<String> namesList = new ArrayList<String>();
			List<Map<String,Object>> valueList = new ArrayList<Map<String,Object>>();
			/*
			 * 	统计各份额占比	
			 * 
			 * */
			String countSql = "";
			countSql+="SELECT ID AS RULE_ID,RULE_NAME,RULE_GRADE AS RULE_LEVEL FROM RULE_DETAIL WHERE ID IN(";
			countSql+="	  SELECT RULE_ID FROM INDEX_ITEMS WHERE INDEX_ID = "+indexCode;
			countSql+=")";
			List<Map<String,Object>> rulesOfIndex = jdbcTemplate.queryForList(countSql);
			for(int i=0;i<rulesOfIndex.size();i++){
				Map<String,Object> ruleObjct = rulesOfIndex.get(i);
				/*添加标题*/
				namesList.add(ruleObjct.get("RULE_NAME").toString());
				/*添加数据*/
				Map<String,Object> valueObj = new HashMap<String,Object>();
				valueObj.put("name",ruleObjct.get("RULE_NAME").toString());
				List<Map<String,Object>> countList = jdbcTemplate.queryForList("SELECT * FROM INDEX_ITEMS WHERE INDEX_ID = "+indexCode+" AND RULE_ID = "+ruleObjct.get("RULE_ID").toString());
				if(countList.size()>0){
					valueObj.put("value",Integer.parseInt(countList.get(0).get("COUNT_RATIO").toString()));
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
