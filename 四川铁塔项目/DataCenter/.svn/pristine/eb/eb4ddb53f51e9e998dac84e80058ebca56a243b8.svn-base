package com.function.index;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.function.compare.model.CompareColumn;
import com.function.compare.model.CompareDetail;
import com.function.compare.model.CompareQuartz;
import com.system.LoginUserUtil;
@Controller("com.function.index.IndexAction")
@RequestMapping(value="/indexAction")
public class IndexAction{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	/*
	 * 	获取得分倒数前五名
	 * 
	 * */
	@SuppressWarnings("static-access")
	@RequestMapping("/findMostTrouble.ilf")
	public void findMostTrouble(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			Integer loginUserCode = loginUserUtil.getLoginUserId(request);
			resultObject.put("loginUserIcon",loginUserUtil.getUserIcon(request));
			resultObject.put("loginUserCode",loginUserUtil.getEmployeeName(request));
			resultObject.put("loginUserCompany",loginUserUtil.getUserCompanyName(request));
			/*
			 * 	共核查了[N]项.
			 * 
			 * */
			Integer checkedCount = jdbcTemplate.queryForInt("SELECT COUNT(1) AS TOTAL_COUNT FROM INDEX_USER WHERE USER_ID = "+loginUserCode);
			resultObject.put("checkedCount",checkedCount);
			/*
			 * 	根据已执行完毕的规则得分计算最终得分
			 * 
			 * */
			BigDecimal bigDecimal = new BigDecimal(Double.parseDouble("0.0"));		
			List<Map<String,Object>> scoreObjects = jdbcTemplate.queryForList("SELECT * FROM USER_SCORE WHERE USER_ID = "+loginUserCode);
			if(scoreObjects.size()>0){
				Map<String,Object> scoreObject = scoreObjects.get(0);
				bigDecimal = new BigDecimal(Double.parseDouble(
					scoreObject.get("FINAL_SCORE")==null?"0.0":scoreObject.get("FINAL_SCORE").toString()
				));
			}
			resultObject.put("finalScore",bigDecimal.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue());
			/*
			 * 错误率最高的五项指标.
			 * 
			 * */
			String sql = "";
			sql+="SELECT G.INDEX_ID,M.INDEX_NAME,G.FINAL_SCORE FROM (";
			sql+="	SELECT A.*,ROWNUM AS RN FROM (";
			sql+="		SELECT * FROM INDEX_SCORE WHERE FINAL_SCORE IS NOT NULL AND INDEX_ID IN (";
			sql+="			SELECT INDEX_ID FROM INDEX_USER WHERE USER_ID = "+loginUserCode;
			sql+="		) ORDER BY FINAL_SCORE ASC";
			sql+="	) A WHERE A.FINAL_SCORE < 60 AND ROWNUM <= 5";
			sql+=") G,INDEX_DETAIL M WHERE G.INDEX_ID = M.ID ORDER BY G.FINAL_SCORE ASC";
			List<Map<String,Object>> items = jdbcTemplate.queryForList(sql);
			if(items.size()>0){
				for(int i=0;i<items.size();i++){
					Map<String,Object> kpiItem = items.get(i);
					String ruleSql = "";
					ruleSql+="SELECT G.RULE_ID,H.RULE_NAME,G.NORMAL_RATIO,H.RESOLVE_METHOD FROM RULE_DETAIL H,(";
					ruleSql+="	  SELECT A.RULE_ID,A.NORMAL_RATIO,ROWNUM AS RN FROM (";
					ruleSql+="	  	  SELECT * FROM RULE_JOB WHERE RULE_ID IN(";
					ruleSql+="			  SELECT RULE_ID FROM INDEX_ITEMS WHERE INDEX_ID = "+kpiItem.get("INDEX_ID").toString();
					ruleSql+="		  ) ORDER BY NORMAL_RATIO ASC";
					ruleSql+="	  ) A WHERE A.NORMAL_RATIO <= 60 AND ROWNUM <= 2";
					ruleSql+=") G WHERE H.ID = G.RULE_ID";
					List<Map<String,Object>> ruleItems = jdbcTemplate.queryForList(ruleSql);
					items.get(i).put("LEAST_RULE",ruleItems);
					
				}
			}
			resultObject.put("items",items);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	获取得分前五名[用户]
	 * 
	 * */
	@RequestMapping("/findPerfectUsers.ilf")
	public void findPerfectUsers(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			String sql = "";
			sql+="SELECT B.*,ROWNUM AS RN FROM(";
			sql+="	  SELECT S.ID,S.USER_NAME,S.EMPLOYEE_NAME,S.EMPLOYEE_COMPANY,A.FINAL_SCORE,S.USER_ICON,A.OH_MY_GOD";
			sql+="	  FROM USER_SCORE A,S_SYSTEM_USER S ";
			sql+="	  WHERE A.IS_OK = 'Y' AND A.USER_ID = S.ID ORDER BY A.FINAL_SCORE DESC";
			sql+=") B WHERE ROWNUM <= 5";
			List<Map<String,Object>> items = jdbcTemplate.queryForList(sql);
			resultObject.put("ITEMS",items);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	点赞...
	 * 
	 * */
	@RequestMapping("/doFuck.ilf")
	public void doFuck(@RequestParam String userCode,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			Map<String,Object> scoreMap = jdbcTemplate.queryForMap("SELECT * FROM USER_SCORE WHERE USER_ID = "+userCode);
			Integer newNumber = Integer.parseInt(scoreMap.get("OH_MY_GOD").toString())+1;
			resultObject.put("NEW_VALUE",newNumber);
			jdbcTemplate.execute("UPDATE USER_SCORE SET OH_MY_GOD = "+newNumber+" WHERE USER_ID = "+userCode);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	获取得分前五名[指标]
	 * 
	 * */
	@RequestMapping("/findPerfects.ilf")
	public void findPerfects(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			Integer loginUserCode = loginUserUtil.getLoginUserId(request);
			String sql = "";
			sql+="SELECT G.INDEX_ID,M.INDEX_NAME,G.FINAL_SCORE FROM (";
			sql+="	  SELECT A.*,ROWNUM AS RN FROM (";
			sql+="		  SELECT * FROM INDEX_SCORE WHERE FINAL_SCORE IS NOT NULL AND INDEX_ID IN (";
			sql+="			  SELECT INDEX_ID FROM INDEX_USER WHERE USER_ID = "+loginUserCode;
			sql+="		  ) ORDER BY FINAL_SCORE DESC";
			sql+="	  ) A WHERE A.FINAL_SCORE > 60 AND ROWNUM <= 5";
			sql+=") G,INDEX_DETAIL M WHERE G.INDEX_ID = M.ID ORDER BY G.FINAL_SCORE DESC";
			List<Map<String,Object>> items = jdbcTemplate.queryForList(sql);
			resultObject.put("items",items);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	获取最新发布的二十条规则
	 * 
	 * */
	@RequestMapping("/findNews.ilf")
	public void findNews(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			String sql = "";
			sql+="SELECT A.ID,A.RULE_NAME,A.RULE_VERSION,A.CREATE_TIME,ROWNUM AS RN FROM (";
			sql+="	  SELECT * FROM RULE_DETAIL ORDER BY CREATE_TIME DESC";
			sql+=") A WHERE ROWNUM <= 20";	
			List<Map<String,Object>> items = jdbcTemplate.queryForList(sql);
			resultObject.put("items",items);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	获取工具
	 * 
	 * */
	@RequestMapping("/findTools.ilf")
	public void findTools(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			List<Map<String,Object>> items = jdbcTemplate.queryForList("SELECT A.*,ROWNUM AS RN FROM(SELECT * FROM NET_TOOL_DETAIL ORDER BY PUBLISH_DATE DESC) A WHERE ROWNUM <= 4");
			resultObject.put("items",items);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	初始化雷达图
	 * 
	 * */
	@RequestMapping("/initDadarData.ilf")
	public void initDadarData(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			String sql = "";
			sql+="SELECT A.RULE_NAME,B.FATUAL_RATIO FROM RULE_DETAIL A,(";
			sql+="	SELECT T.*,ROWNUM AS RN FROM (";
			sql+="		SELECT ID,RULE_ID,FATUAL_RATIO FROM RULE_JOB WHERE IS_FINISHED = 'Y' ORDER BY FATUAL_RATIO ASC";
			sql+="	) T WHERE ROWNUM <= 5";
			sql+=") B WHERE A.ID = B.RULE_ID ORDER BY B.FATUAL_RATIO ASC";
			List<Map<String,Object>> items = jdbcTemplate.queryForList(sql);
			JSONArray titleArray = new JSONArray();
			List<Double> valueArray = new ArrayList<Double>();
			if(items.size()>0){
				for(int i=0;i<items.size();i++){
					Map<String,Object> itemMap = items.get(i);
					String ruleName = itemMap.get("RULE_NAME").toString();
					if(ruleName.length()>10){
						ruleName = ruleName.substring(0,10)+"..";
					}
					/*规则*/
					JSONObject title = new JSONObject();
					title.put("text",ruleName);
					title.put("max",100);
					titleArray.add(title);
					/*得分*/
					BigDecimal bigDecimal = new BigDecimal(100-Double.parseDouble(itemMap.get("FATUAL_RATIO").toString()));					
					valueArray.add(bigDecimal.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue());
				}
			}		
			resultObject.put("titles",titleArray);
			resultObject.put("values",valueArray);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	获取作业情况.
	 * 
	 * */
	@RequestMapping("/findJobRunDetails.ilf")
	public void findJobRunDetails(
		@RequestParam String designedDate,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		List<List<String>> dataList = new ArrayList<List<String>>();
		try{
			String designYear = designedDate.split("-")[0];
			String designMonth = designedDate.split("-")[1];
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Integer totalDay = 30;
			if(Integer.parseInt(designMonth)==1 || Integer.parseInt(designMonth)==3 || Integer.parseInt(designMonth)==5 || Integer.parseInt(designMonth)==7 || Integer.parseInt(designMonth)==8 || Integer.parseInt(designMonth)==10 || Integer.parseInt(designMonth)==12){
				totalDay = 31;
			}else if(Integer.parseInt(designMonth)==2){
				if((Integer.parseInt(designYear)%4==0 && Integer.parseInt(designYear)%100!=0) || (Integer.parseInt(designYear)%400==0)){
					totalDay = 29;
				}else{
					totalDay = 28;
				}
			}
			for(int a=1;a<=3;a++){
				List<String> rowData = new ArrayList<String>();
				if(a==1){
					rowData.add("采集情况");
				}else if(a==2){
					rowData.add("标准枚举");
				}else if(a==3){
					rowData.add("S标准表");
				}
				String currentType = rowData.get(0);
				String keyPool = "";
				for(int b=1;b<=totalDay;b++){
					String day = b<10?"0"+b:b+"";
					String allDate = designedDate+"-"+day;
					if("".equals(keyPool)){
						keyPool = "'"+allDate+"'";
					}else{
						keyPool+= ",'"+allDate+"'";
					}
				}
				List<Map<String,Object>> runDatas = jdbcTemplate.queryForList("SELECT * FROM RUN_JOB_MONITOR WHERE JOB_TYPE = '"+currentType+"' AND RUN_DATE IN ("+keyPool+") ORDER BY RUN_DATE ASC");
				Date nowDate = new Date();
				for(int b=1;b<=totalDay;b++){
					String day = b<10?"0"+b:b+"";
					String allDate = designedDate+"-"+day;
					Date checkingDate = dateFormat.parse(allDate);
					if(runDatas.size()==0){
						if(checkingDate.before(nowDate)){
							rowData.add("N");
						}else{
							rowData.add("-");
						}
					}else{
						Integer dataIndex = -1;
						for(int c=0;c<runDatas.size();c++){
							if(runDatas.get(c).get("RUN_DATE")!=null && allDate.equals(runDatas.get(c).get("RUN_DATE").toString())){
								dataIndex = c;
								break;
							}
						}
						if(dataIndex==-1){
							if(checkingDate.before(nowDate)){
								rowData.add("N");
							}else{
								rowData.add("-");
							}
						}else{
							Map<String,Object> runData = runDatas.get(dataIndex);
							if(runData.get("IS_RUN_AFTER")!=null && "Y".equals(runData.get("IS_RUN_AFTER").toString())){
								rowData.add("B");
							}else{
								rowData.add(runData.get("IS_RUN").toString());
							}
						}
					}
				}
				dataList.add(rowData);
			}
			resultObject.put("DATAS",dataList);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	获取[SC]详情情况.
	 * 
	 * */
	@RequestMapping("/findSCDetailOfDate.ilf")
	public void findSCDetailOfDate(
		@RequestParam String designedDate,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			String sql = "";
			sql+="SELECT A.*,B.IS_SURE,B.CHECKING_DATE,B.SURE_USER,B.CONTENTS ";
			sql+="FROM RUN_DATA_TEMPLATE_SC A,(SELECT * FROM RUN_JOB_DATA WHERE JOB_TYPE = 'SC' AND CHECKING_DATE = '"+designedDate+"') B ";
			sql+="WHERE A.ID = B.BIND_JOB_ID(+) ORDER BY A.ID ASC";
			resultObject.put("DATAS",jdbcTemplate.queryForList(sql));
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	获取[枚举]详情情况.
	 * 
	 * */
	@RequestMapping("/findEnumDetailOfDate.ilf")
	public void findEnumDetailOfDate(
		@RequestParam String designedDate,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			String sql = "";
			sql+="SELECT A.*,B.IS_SURE,B.CHECKING_DATE,B.SURE_USER,B.CONTENTS ";
			sql+="FROM RUN_DATA_TEMPLATE_ENUM A,(SELECT * FROM RUN_JOB_DATA WHERE JOB_TYPE = 'ENUM' AND CHECKING_DATE = '"+designedDate+"') B ";
			sql+="WHERE A.ID = B.BIND_JOB_ID(+) ORDER BY A.ID ASC";
			resultObject.put("DATAS",jdbcTemplate.queryForList(sql));
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	获取[采集]详情情况.
	 * 
	 * */
	@RequestMapping("/findPickDetailOfDate.ilf")
	public void findPickDetailOfDate(
		@RequestParam String designedDate,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			String sql = "";
			sql+="SELECT A.*,B.IS_SURE,B.CHECKING_DATE,B.SURE_USER,B.CONTENTS ";
			sql+="FROM RUN_DATA_TEMPLATE_PICK A,(SELECT * FROM RUN_JOB_DATA WHERE JOB_TYPE = 'PICK' AND CHECKING_DATE = '"+designedDate+"') B ";
			sql+="WHERE A.ID = B.BIND_JOB_ID(+) ORDER BY A.ID ASC";
			resultObject.put("DATAS",jdbcTemplate.queryForList(sql));
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	保存/修改
	 * 
	 * */
	@RequestMapping("/saveAudit.ilf")
	public void saveAudit(
		@RequestParam String params,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			final JSONObject jsonObject = JSONObject.fromObject(params);
			String JOB_TYPE = jsonObject.get("JOB_TYPE").toString();
			String CHECKING_DATE = jsonObject.get("CHECKING_DATE").toString();
			jdbcTemplate.execute("DELETE FROM RUN_JOB_DATA WHERE JOB_TYPE = '"+JOB_TYPE+"' AND CHECKING_DATE = '"+CHECKING_DATE+"'");
			JSONArray jsonArray = jsonObject.getJSONArray("DATAS");
			String sql = "";
			sql+="insert all ";
			for(int i=0;i<jsonArray.size();i++){
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				Integer templateId = Integer.parseInt(jsonObj.get("ID").toString());
				sql+="INTO RUN_JOB_DATA(";
				sql+="	  JOB_TYPE,BIND_JOB_ID,IS_SURE,CHECKING_DATE,SURE_USER,CONTENTS";
				sql+=")VALUES(";
				sql+="	  '"+JOB_TYPE+"',"+templateId+",'Y','"+CHECKING_DATE+"','"+loginUserUtil.getEmployeeName(request)+"','"+jsonObj.get("EDIT_CONTENT").toString()+"'";
				sql+=") ";
				if(i==(jsonArray.size()-1)){
					sql+="SELECT 1 FROM DUAL";
				}
			}
			jdbcTemplate.execute(sql);
			Date date = new Date();
			String nowDateString = (date.getYear()+1900)+"-"+((date.getMonth()+1)<10?"0"+(date.getMonth()+1):(date.getMonth()+1))+"-"+(date.getDate()<10?"0"+date.getDate():date.getDate());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Boolean isAfter = true;
			if(nowDateString.equals(CHECKING_DATE)){
				isAfter = false;
			}else{
				Date dateA = dateFormat.parse(CHECKING_DATE);
				Date dateB = dateFormat.parse(nowDateString);
				if(dateA.before(dateB)){
					isAfter = true;
				}else{
					isAfter = false;
				}
			}
			String jobType = "";
			if("SC".equals(JOB_TYPE)){
				jobType = "S标准表";
			}else if("PICK".equals(JOB_TYPE)){
				jobType = "采集情况";
			}else if("ENUM".equals(JOB_TYPE)){
				jobType = "标准枚举";
			}
			jdbcTemplate.execute("DELETE FROM RUN_JOB_MONITOR WHERE JOB_TYPE = '"+jobType+"' AND RUN_DATE = '"+CHECKING_DATE+"'");
			if(isAfter){
				jdbcTemplate.execute("INSERT INTO RUN_JOB_MONITOR(JOB_TYPE,RUN_DATE,IS_RUN,IS_RUN_AFTER)VALUES('"+jobType+"','"+CHECKING_DATE+"','Y','Y')");
			}else{
				jdbcTemplate.execute("INSERT INTO RUN_JOB_MONITOR(JOB_TYPE,RUN_DATE,IS_RUN)VALUES('"+jobType+"','"+CHECKING_DATE+"','Y')");
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}	
	}
	
	/*
	 * 	是否[作业管理员].
	 * 
	 * */
	@RequestMapping("/isJobManager.ilf")
	public void isJobManager(
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			Boolean IS_JOB_MANAGER = false;
			String USER_ACCOUNT = loginUserUtil.getUserAccount(request);
			if(USER_ACCOUNT.toUpperCase().indexOf("ROOT")!=-1){
				IS_JOB_MANAGER = true;
			}else{
				String BELONG_AREA = loginUserUtil.getBelongArea(request);
				if(BELONG_AREA.indexOf("省")!=-1){
					IS_JOB_MANAGER = true;
				}else{
					String sql = "";
					sql+="SELECT COUNT(1) FROM S_SYSTEM_ROLE WHERE ID IN(";
					sql+="	  SELECT ROLE_ID FROM S_SYSTEM_USER_ROLE WHERE USER_ID IN(";
					sql+="		  SELECT ID FROM S_SYSTEM_USER WHERE USER_NAME = '"+USER_ACCOUNT+"'";
					sql+="	  )";
					sql+=") AND ROLE_NAME LIKE '%作业管理员%'";
					Integer IS_MANAGER = jdbcTemplate.queryForInt(sql);
					if(IS_MANAGER>0){
						IS_JOB_MANAGER = true;
					}
				}
			}
			resultObject.put("IS_JOB_MANAGER",IS_JOB_MANAGER);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	获取菜单列表.
	 * 
	 * */
	@RequestMapping("/findMySecondAndLeafsOfTop.ilf")
	public void findMySecondAndLeafsOfTop(
		@RequestParam String secondIds,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			String sql = "";
			if("ROOT".equals(loginUserUtil.getUserAccount(request).toUpperCase())){
				sql+="SELECT ID FROM S_SYSTEM_MENU ";
			}else{
				sql+="SELECT ID FROM S_SYSTEM_MENU WHERE BIND_FUNC_ID IN(";
				sql+="	  SELECT DISTINCT(FUNCID) FROM S_SYSTEM_ROLE_FUNC WHERE ROLEID IN(";
				sql+="		  SELECT ROLE_ID FROM S_SYSTEM_GROUP_ROLE WHERE GROUP_ID IN(";
				sql+="			  SELECT GROUP_ID FROM S_SYSTEM_GROUP_USER WHERE USER_ID IN(";
				sql+="				  SELECT ID AS USERID FROM S_SYSTEM_USER WHERE USER_NAME = '"+loginUserUtil.getUserAccount(request)+"'";
				sql+="			  )";
				sql+="		  )";
				sql+="		  UNION ";
				sql+="		  SELECT ROLE_ID FROM S_SYSTEM_USER_ROLE WHERE USER_ID IN(";
				sql+="			  SELECT ID AS USERID FROM S_SYSTEM_USER WHERE USER_NAME = '"+loginUserUtil.getUserAccount(request)+"'";
				sql+="		  )";
				sql+="	  )";
				sql+=") AND IS_USING = 'Y'";
			}
			List<Map<String,Object>> secondMenus = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_MENU WHERE IS_USING = 'Y' AND ID IN("+secondIds+") AND ID IN("+sql+") ORDER BY MENU_SORT ASC");
			if(secondMenus.size()>0){
				for(int i=0;i<secondMenus.size();i++){
					Map<String,Object> sMap = secondMenus.get(i);
					String secondMenuCode = sMap.get("ID").toString();
					List<Map<String,Object>> leafMenus = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_MENU WHERE MENU_PARENT = "+secondMenuCode+" AND IS_USING = 'Y' AND ID IN("+sql+") ORDER BY MENU_SORT ASC");
					if(leafMenus.size()>0){
						secondMenus.get(i).put("HAS_CHILD","Y");
						secondMenus.get(i).put("CHILDREN",leafMenus);
					}else{
						secondMenus.get(i).put("HAS_CHILD","N");
					}
				}
			}
			resultObject.put("MENUS",secondMenus);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
}
