package com.function.nettool.plugin;
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
@Controller("com.function.nettool.plugin.KnowledgeMapAction")
@RequestMapping(value="/knowledgeMapAction")
public class KnowledgeMapAction{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static String userName = "HBRMW6";
	
	private static String tableName = "T_KNOWLEGEGRAPH_RELATE_PLUS";
	
	/*
	 * 	查找资源
	 * 
	 * */
	@RequestMapping("/findResources.ilf")
	public void findResources(@RequestParam String resourceName,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			JSONArray titles = new JSONArray();
			List<Map<String,Object>> resourceNames = jdbcTemplate.queryForList("SELECT DISTINCT(T.ZH_LABEL),ROWNUM AS RN FROM(SELECT DISTINCT(A.ZH_LABEL) FROM "+userName+"."+tableName+" A WHERE A.ZH_LABEL LIKE '"+resourceName+"%') T WHERE ROWNUM<= 10");
			if(resourceNames.size()>0){
				for(int i=0;i<resourceNames.size();i++){
					Map<String,Object> resourceObj = resourceNames.get(i);
					String zhLabel = resourceObj.get("ZH_LABEL").toString();
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("title",zhLabel);
					titles.add(jsonObject);
				}
			}
			resultObject.put("NAMES", titles);
		}catch(Exception e){
			e.printStackTrace();
			resultObject = JSONObject.fromObject("{SUCCESS:false}");
		}finally{
			System.out.println("==="+resultObject.toString());
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}	
	}
	
	/*
	 * 	分析两项资源的关系
	 * 
	 * */
	@RequestMapping("/doAnalysis.ilf")
	public void xytransfer(@RequestParam String params,HttpServletRequest request,HttpServletResponse response)throws Exception{
		/*结果缓存容器*/
		List<String> keyList = new ArrayList<String>();
		JSONArray keyArray = new JSONArray();
		JSONArray nodeArray = new JSONArray();
		JSONArray linkArray = new JSONArray();
		/*任务：开始解析*/
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			final JSONObject paramObject = JSONObject.fromObject(params);
			String aResName = paramObject.get("aResName").toString();
			String zResName = paramObject.get("zResName").toString();
			String grades = paramObject.get("grade").toString();
			/*查找A端资源*/
			List<Map<String,Object>> aResNames = jdbcTemplate.queryForList("SELECT DISTINCT A.MODEL_NAME,A.TABLE_NAME,A.INT_ID,A.ZH_LABEL FROM "+userName+"."+tableName+" A WHERE A.ZH_LABEL = '"+aResName+"'");
			if(aResNames.size()==0){
				resultObject.put("SUCCESS",false);
				resultObject.put("MESSAGE","根据A端关键字未找到类似的A端资源.");
			}else{
				/*查找Z端资源*/
				List<Map<String,Object>> zResNames = jdbcTemplate.queryForList("SELECT DISTINCT A.MODEL_NAME,A.TABLE_NAME,A.INT_ID,A.ZH_LABEL FROM "+userName+"."+tableName+" A WHERE A.ZH_LABEL = '"+zResName+"'");
				if(zResNames.size()==0){
					resultObject.put("SUCCESS",false);
					resultObject.put("MESSAGE","根据Z端关键字未找到类似的Z端资源.");
				}else{
					/*执行分析*/
					String aResCode = aResNames.get(0).get("INT_ID").toString();
					String zResCode = zResNames.get(0).get("INT_ID").toString();
					String randomId = findRandomID();
					jdbcTemplate.execute("CALL "+userName+".P_KNOWLEGEGRAPH_CALC_NEW('"+aResCode+"','"+zResCode+"',"+grades+",'"+randomId+"')");
					System.out.println("==="+randomId);
					/*获取分析结果：结果种类*/
					String typeSql = "";
					typeSql+="SELECT DISTINCT(T.MODEL_NAME) AS MODEL_NAME FROM(";
					typeSql+="	 SELECT DISTINCT(MODEL_NAME) AS MODEL_NAME FROM "+userName+".T_KNOWLEGEGRAPH_RELATE_OUTPUT WHERE MODEL_NAME IS NOT NULL AND SESSIONID = '"+randomId+"' ";
					typeSql+="	 UNION ";
					typeSql+="	 SELECT DISTINCT(RELATE_MODEL_NAME) AS MODEL_NAME FROM "+userName+".T_KNOWLEGEGRAPH_RELATE_OUTPUT WHERE MODEL_NAME IS NOT NULL AND SESSIONID = '"+randomId+"'";
					typeSql+=") T";
					List<Map<String,Object>> keyResults = jdbcTemplate.queryForList(typeSql);
					if(keyResults.size()==0){
						resultObject.put("SUCCESS",false);
						resultObject.put("MESSAGE","分析未得出此两项资源包含关联关系.");
					}else{
						for(int i=0;i<keyResults.size();i++){
							if(keyResults.get(i).get("MODEL_NAME")!=null){
								/*legend:data*/
								keyList.add(keyResults.get(i).get("MODEL_NAME").toString());
								/*series:categories*/
								JSONObject titleObject = new JSONObject();
								titleObject.put("name",keyResults.get(i).get("MODEL_NAME").toString());
								keyArray.add(titleObject);
							}
						}
						resultObject.put("legentData",keyList);
						resultObject.put("categories",keyArray);
						/*nodes*/
						String nodeSql = "";
						nodeSql+="SELECT DISTINCT T.MODEL_NAME,T.INT_ID,T.ZH_LABEL FROM(";
						nodeSql+="	  SELECT DISTINCT MODEL_NAME AS MODEL_NAME,INT_ID AS INT_ID,ZH_LABEL AS ZH_LABEL FROM "+userName+".T_KNOWLEGEGRAPH_RELATE_OUTPUT WHERE SESSIONID = '"+randomId+"' ";
						nodeSql+="	  UNION ";
						nodeSql+="	  SELECT DISTINCT RELATE_MODEL_NAME AS MODEL_NAME,RELATE_INT_ID AS INT_ID,RELATE_ZH_LABEL AS ZH_LABEL FROM "+userName+".T_KNOWLEGEGRAPH_RELATE_OUTPUT WHERE SESSIONID = '"+randomId+"'";
						nodeSql+=") T";
						/*依次遍历*/
						List<Map<String,Object>> analysisResults = jdbcTemplate.queryForList(nodeSql);
						for(int j=0;j<analysisResults.size();j++){
							Map<String,Object> nodeObject = analysisResults.get(j);
							/*节点基本信息*/
							JSONObject nodeObj = new JSONObject();
							nodeObj.put("name",nodeObject.get("ZH_LABEL").toString());
							nodeObj.put("value",findRandomValue());
							if(nodeObject.get("MODEL_NAME")!=null && !"".equals(nodeObject.get("MODEL_NAME").toString())){
								String modelName = nodeObject.get("MODEL_NAME").toString();
								for(int k=0;k<keyList.size();k++){
									if(keyList.get(k).equals(modelName)){
										nodeObj.put("category",k);
										break;
									}
								}
							}
							nodeArray.add(nodeObj);
						}
						resultObject.put("nodes",nodeArray);
						/*A→Z:links*/
						Map<String,String> paramMap = new HashMap<String,String>();
						for(int g=0;g<analysisResults.size();g++){
							Map<String,Object> aObject = analysisResults.get(g);
							String aModelName = aObject.get("MODEL_NAME")==null?"NULL":aObject.get("MODEL_NAME").toString();
							String aIntId = aObject.get("INT_ID").toString();
							String aZhLabel = aObject.get("ZH_LABEL").toString();
							if(paramMap.containsKey(aZhLabel)){
								continue;
							}else{
								List<Map<String,Object>> aMappings = null;
								if("NULL".equals(aModelName)){
									aMappings = jdbcTemplate.queryForList("SELECT * FROM "+userName+".T_KNOWLEGEGRAPH_RELATE_OUTPUT A WHERE A.SESSIONID = '"+randomId+"' AND A.MODEL_NAME IS NULL AND A.INT_ID = '"+aIntId+"' AND A.ZH_LABEL = '"+aZhLabel+"'");
								}else{
									aMappings = jdbcTemplate.queryForList("SELECT * FROM "+userName+".T_KNOWLEGEGRAPH_RELATE_OUTPUT A WHERE A.SESSIONID = '"+randomId+"' AND A.MODEL_NAME = '"+aModelName+"' AND A.INT_ID = '"+aIntId+"' AND A.ZH_LABEL = '"+aZhLabel+"'");
								}
								if(aMappings.size()>0){
									for(int h=0;h<aMappings.size();h++){
										Map<String,Object> zMap = aMappings.get(h);
										String zZhLabel = zMap.get("RELATE_ZH_LABEL").toString();
										for(int v=0;v<analysisResults.size();v++){
											if(analysisResults.get(v).get("ZH_LABEL").toString().equals(zZhLabel)){
												JSONObject linkObject = new JSONObject();
												linkObject.put("source",g);
												linkObject.put("target",v);
												linkArray.add(linkObject);
											}
										}
									}
								}
								paramMap.put(aZhLabel,aZhLabel);
							}
						}
						paramMap = new HashMap<String,String>();
						/*Z→A:links*/
						for(int g=0;g<analysisResults.size();g++){
							Map<String,Object> aObject = analysisResults.get(g);
							String zModelName = aObject.get("MODEL_NAME")==null?"NULL":aObject.get("MODEL_NAME").toString();
							String zIntId = aObject.get("INT_ID").toString();
							String zZhLabel = aObject.get("ZH_LABEL").toString();
							if(paramMap.containsKey(zZhLabel)){
								continue;
							}else{
								List<Map<String,Object>> zMappings = null;
								if("NULL".equals(zModelName)){
									zMappings = jdbcTemplate.queryForList("SELECT * FROM "+userName+".T_KNOWLEGEGRAPH_RELATE_OUTPUT A WHERE A.SESSIONID = '"+randomId+"' AND A.RELATE_MODEL_NAME IS NULL AND A.RELATE_INT_ID = '"+zIntId+"' AND A.RELATE_ZH_LABEL = '"+zZhLabel+"'");
								}else{
									zMappings = jdbcTemplate.queryForList("SELECT * FROM "+userName+".T_KNOWLEGEGRAPH_RELATE_OUTPUT A WHERE A.SESSIONID = '"+randomId+"' AND A.RELATE_MODEL_NAME = '"+zModelName+"' AND A.RELATE_INT_ID = '"+zIntId+"' AND A.RELATE_ZH_LABEL = '"+zZhLabel+"'");
								}
								if(zMappings.size()>0){
									for(int h=0;h<zMappings.size();h++){
										Map<String,Object> aMap = zMappings.get(h);
										String aZhLabel = aMap.get("ZH_LABEL").toString();
										for(int v=0;v<analysisResults.size();v++){
											if(analysisResults.get(v).get("ZH_LABEL").toString().equals(aZhLabel)){
												JSONObject linkObject = new JSONObject();
												linkObject.put("source",v);
												linkObject.put("target",g);
												linkArray.add(linkObject);
											}
										}
									}
								}
								paramMap.put(zZhLabel,zZhLabel);
							}
						}
						resultObject.put("links",linkArray);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject = JSONObject.fromObject("{SUCCESS:false}");
		}finally{
			System.out.println("==="+resultObject.toString());
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}	
	}
	
	public static Integer findRandomValue(){
		int randomValue = (int)(Math.random()*11);
		return randomValue;
	}
	
	public static String findRandomID(){
		List<String> strArray = new ArrayList<String>();
		strArray.add("A");
		strArray.add("B");
		strArray.add("C");
		strArray.add("D");
		strArray.add("E");
		strArray.add("F");
		strArray.add("G");
		strArray.add("H");
		strArray.add("I");
		strArray.add("J");
		strArray.add("K");
		strArray.add("L");
		strArray.add("M");
		strArray.add("N");
		strArray.add("O");
		strArray.add("P");
		strArray.add("Q");
		strArray.add("R");
		strArray.add("S");
		strArray.add("T");
		strArray.add("U");
		strArray.add("V");
		strArray.add("W");
		strArray.add("X");
		strArray.add("Y");
		strArray.add("Z");
		String randomId = "";
		while(randomId.length()<=15){
			int randomIndex = (int)(Math.random()*26);
			if(randomIndex<0){
				randomIndex = 0;
			}else if(randomIndex>25){
				randomIndex = 25;
			}
			randomId+=strArray.get(randomIndex);
		}
		return randomId;
	}
}
