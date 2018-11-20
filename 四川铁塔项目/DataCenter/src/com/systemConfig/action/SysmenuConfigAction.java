package com.systemConfig.action;
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
import com.systemConfig.dao.RoleService;
import com.systemConfig.dao.SystemMenuService;
import com.systemConfig.model.DataTableResult;
import com.systemConfig.model.SystemMenu;
@Controller("com.systemConfig.action.SysmenuConfigAction")
@RequestMapping(value="/sysmenuConfigAction")
public class SysmenuConfigAction{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SystemMenuService systemMenuService;
	
	@Autowired
	private RoleService roleService;
	
	/*
	 * 	查询某个菜单
	 * 
	 * */
	@SuppressWarnings("unused")
	@RequestMapping("/findOne.ilf")
	public void findOne(@RequestParam String menuCode,HttpServletResponse response)throws Exception{
		SystemMenu systemMenu = systemMenuService.findOneMenu(Integer.parseInt(menuCode));
		JSONObject menuObject = JSONObject.fromObject(systemMenu);
		menuObject.put("PARENT_NAME",systemMenuService.findOneMenu(systemMenu.getMENU_PARENT()).getMENU_NAME());
		if(menuObject!=null){
			menuObject.put("success",true);
		}else{
			menuObject = new JSONObject();
			menuObject.put("success",false);
		}
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(menuObject.toString());
	}
	
	/*
	 * 	查询菜单列表
	 * 
	 * */
	@RequestMapping("/findMenus.ilf")
	public void findMenus(@RequestParam String tableparam,@RequestParam String conditions,HttpServletResponse response)throws Exception{
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
		Integer count = systemMenuService.findCount(conditonMap);		
		List<SystemMenu> objectList = systemMenuService.findMenus(conditonMap);	
		DataTableResult<SystemMenu> tableData = new DataTableResult<SystemMenu>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(objectList);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	获取二级菜单及其附属末梢菜单 
	 * 
	 * */
	@RequestMapping("/findMenusOfTop.ilf")
	public void findMenusOfTop(@RequestParam String topMenuCode,HttpServletResponse response)throws Exception{
		List<SystemMenu> secondMenus = systemMenuService.findByHql("from SystemMenu where MENU_PARENT = "+topMenuCode+" order by MENU_SORT asc");
		JSONArray nodeArray = JSONArray.fromObject(secondMenus);
		if(nodeArray.size()>0){
			for(int i=0;i<nodeArray.size();i++){		
				JSONObject secondMenu = nodeArray.getJSONObject(i);
				List<SystemMenu> leafMenus = systemMenuService.findByHql("from SystemMenu where MENU_PARENT = "+secondMenu.get("ID").toString()+" order by MENU_SORT asc");
				JSONArray leafArray = JSONArray.fromObject(leafMenus);
				if(leafArray.size()>0){
					nodeArray.getJSONObject(i).put("HAS_CHILD","Y");
					nodeArray.getJSONObject(i).put("CHILDREN",leafArray);
				}else{
					nodeArray.getJSONObject(i).put("HAS_CHILD","N");
				}				
			}					
		}else{
			secondMenus =  systemMenuService.findByHql("from SystemMenu where ID = "+topMenuCode);
			nodeArray = JSONArray.fromObject(secondMenus);
		}
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONArray.fromObject(nodeArray).toString());
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/findIndexMenus.ilf")
	public void findIndexMenus(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			JSONObject menusObject = new JSONObject();
		
			if(request.getSession().getAttribute("LOGIN_INFO")!=null){
				menusObject = (JSONObject)request.getSession().getAttribute("LOGIN_INFO");
			}else{
				Object loginObject = request.getSession().getAttribute("LoginUserInfo");
				if(loginObject==null){
					menusObject.put("IS_LOGINED",false);
				}else{
					menusObject.put("IS_LOGINED",true);
					Map<String,Object> loginUser = (HashMap<String,Object>)loginObject;
					menusObject.put("LOGIN_USER",loginUser);		
					menusObject.put("TIPS","信息：暂无待办任务需要处理.");	
					/*查看用户下是否绑定角色*/
					String roleBindedSql = "";
					roleBindedSql+="SELECT COUNT(1) FROM (";
					roleBindedSql+="	SELECT ROLE_ID FROM S_SYSTEM_GROUP_ROLE WHERE GROUP_ID IN(";
					roleBindedSql+="		SELECT GROUP_ID FROM S_SYSTEM_GROUP_USER WHERE USER_ID IN(";
					roleBindedSql+="			SELECT ID AS USERID FROM S_SYSTEM_USER WHERE USER_NAME = '"+loginUser.get("USER_NAME").toString()+"'";
					roleBindedSql+="		)";
					roleBindedSql+="	)";
					roleBindedSql+="	UNION ";
					roleBindedSql+="	SELECT ROLE_ID FROM S_SYSTEM_USER_ROLE WHERE USER_ID IN(";
					roleBindedSql+="		SELECT ID AS USERID FROM S_SYSTEM_USER WHERE USER_NAME = '"+loginUser.get("USER_NAME").toString()+"'";
					roleBindedSql+="	)";
					roleBindedSql+=")";
					Integer isBinded = jdbcTemplate.queryForInt(roleBindedSql);
					if(isBinded>0 || "root".equals(loginUser.get("USER_NAME").toString())){
						menusObject.put("IS_BINDED_ROLE",true);
						/*查看角色在此系统下是否有菜单*/
						String isHaveMenu = "";
						isHaveMenu+="SELECT COUNT(1) FROM S_SYSTEM_ROLE_FUNC WHERE ROLEID IN(";
						isHaveMenu+="	SELECT ROLE_ID FROM S_SYSTEM_GROUP_ROLE WHERE GROUP_ID IN(";
						isHaveMenu+="		SELECT GROUP_ID FROM S_SYSTEM_GROUP_USER WHERE USER_ID IN(";
						isHaveMenu+="			SELECT ID AS USERID FROM S_SYSTEM_USER WHERE USER_NAME = '"+loginUser.get("USER_NAME").toString()+"'";
						isHaveMenu+="		)";
						isHaveMenu+="	)";
						isHaveMenu+="	UNION ";
						isHaveMenu+="	SELECT ROLE_ID FROM S_SYSTEM_USER_ROLE WHERE USER_ID IN(";
						isHaveMenu+="		SELECT ID AS USERID FROM S_SYSTEM_USER WHERE USER_NAME = '"+loginUser.get("USER_NAME").toString()+"'";
						isHaveMenu+="	)";
						isHaveMenu+=") AND FUNCID IN (SELECT BIND_FUNC_ID FROM S_SYSTEM_MENU)";
						Integer isRoleMenued = jdbcTemplate.queryForInt(isHaveMenu);
						if(isRoleMenued>0 || "ROOT".equals(loginUser.get("USER_NAME").toString().toUpperCase())){
							menusObject.put("IS_ROLE_MENUED",true);
							/*验证是否有可查看的菜单*/
							Boolean isHaveLeaf = true;
							String menuPool = "";
							if("ROOT".equals(loginUser.get("USER_NAME").toString().toUpperCase())){
								menuPool+="SELECT ID AS MENU_ID FROM S_SYSTEM_MENU";
							}else{
								menuPool+="SELECT ID AS MENU_ID FROM S_SYSTEM_MENU WHERE BIND_FUNC_ID IN(";
								menuPool+="	  SELECT DISTINCT(FUNCID) FROM S_SYSTEM_ROLE_FUNC WHERE ROLEID IN(";
								menuPool+="		  SELECT ROLE_ID FROM S_SYSTEM_GROUP_ROLE WHERE GROUP_ID IN(";
								menuPool+="			  SELECT GROUP_ID FROM S_SYSTEM_GROUP_USER WHERE USER_ID IN(";
								menuPool+="				  SELECT ID AS USERID FROM S_SYSTEM_USER WHERE USER_NAME = '"+loginUser.get("USER_NAME").toString()+"'";
								menuPool+="			  )";
								menuPool+="		  )";
								menuPool+="		  UNION ";
								menuPool+="		  SELECT ROLE_ID FROM S_SYSTEM_USER_ROLE WHERE USER_ID IN(";
								menuPool+="			  SELECT ID AS USERID FROM S_SYSTEM_USER WHERE USER_NAME = '"+loginUser.get("USER_NAME").toString()+"'";
								menuPool+="		  )";
								menuPool+="	  )";
								menuPool+=")";
							}
							/*Integer isTopHaveLeaf = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM S_SYSTEM_MENU WHERE MENU_PARENT = 1 AND IS_USING = 'Y' AND IS_LEAF = 'Y' AND ID IN("+menuPool+")");
							if(isTopHaveLeaf>0){
								isHaveLeaf = true;
							}else{
								Integer isSecondHaveLeaf = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM S_SYSTEM_MENU WHERE MENU_PARENT IN(SELECT ID FROM S_SYSTEM_MENU WHERE ID IN("+menuPool+") AND IS_USING = 'Y' AND MENU_PARENT = 1) AND IS_USING = 'Y' AND IS_LEAF = 'Y'");
								if(isSecondHaveLeaf>0){
									isHaveLeaf = true;
								}else{
									String searchSQL = "";
									searchSQL += "SELECT COUNT(1) FROM S_SYSTEM_MENU WHERE ID IN ("+menuPool+") AND MENU_PARENT IN(";
									searchSQL += "	SELECT ID FROM S_SYSTEM_MENU WHERE ID IN ("+menuPool+") AND MENU_PARENT IN(";
									searchSQL += "		SELECT ID FROM S_SYSTEM_MENU WHERE ID IN("+menuPool+") AND MENU_PARENT = 1 IS_USING = 'Y'";
									searchSQL += "	) AND IS_USING = 'Y'";
									searchSQL += ") AND IS_USING = 'Y' AND IS_LEAF = 'Y'";
									Integer isThirdHaveLeaf = jdbcTemplate.queryForInt(searchSQL);
									if(isThirdHaveLeaf>0){
										isHaveLeaf = true;
									}
								}
							}*/
							if(isHaveLeaf){
								menusObject.put("IS_HAVE_LEAF","Y");
							
								List<Map<String,Object>> topMenus = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_MENU WHERE ID IN("+menuPool+") AND IS_USING = 'Y' AND MENU_PARENT = 1 ORDER BY MENU_SORT ASC");
					
								
								/*if(topMenus.size()>0){
									for(int a=(topMenus.size()-1);a>=0;a--){
										Map<String,Object> $top = topMenus.get(a);
										if($top.containsKey("IS_LEAF") && "N".equals($top.get("IS_LEAF").toString())){
											String topMenuID = $top.get("ID").toString();
											Integer isThirdHaveLeaf = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM S_SYSTEM_MENU WHERE ID IN ("+menuPool+") AND MENU_PARENT IN(SELECT ID FROM S_SYSTEM_MENU WHERE MENU_PARENT = "+topMenuID+") AND IS_LEAF = 'Y' AND IS_USING = 'Y'");
											if(isThirdHaveLeaf>0){
												continue;
											}else{
												Integer isSecondHaveLeaf = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM S_SYSTEM_MENU WHERE ID IN ("+menuPool+") AND MENU_PARENT = "+topMenuID+" AND IS_LEAF = 'Y' AND IS_USING = 'Y'");
												if(isSecondHaveLeaf>0){
													continue;
												}else{
													topMenus.remove(a);
												}								
											}					
										}else if($top.containsKey("IS_LEAF") && "Y".equals($top.get("IS_LEAF").toString())){
											continue;
										}
									}
								}*/
								if(topMenus.size()>0){
									for(int i=0;i<topMenus.size();i++){
										Map<String,Object> topMenuObject = topMenus.get(i);
										List<Map<String,Object>> secondMenus = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_MENU WHERE ID IN("+menuPool+") AND IS_USING = 'Y' AND MENU_PARENT = "+topMenuObject.get("ID").toString()+"ORDER BY MENU_SORT ASC");
										/*if(secondMenus.size()>0){
											for(int b=(secondMenus.size()-1);b>=0;b--){
												Map<String,Object> $second = secondMenus.get(b);
												if($second.containsKey("IS_LEAF") && "N".equals($second.get("IS_LEAF").toString())){
													String secondMenuID = $second.get("ID").toString();
													Integer isSecondHaveLeaf = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM S_SYSTEM_MENU WHERE ID IN ("+menuPool+") AND MENU_PARENT = "+secondMenuID+" AND IS_LEAF = 'Y'");
													if(isSecondHaveLeaf>0){
														continue;
													}else{
														secondMenus.remove(b);
													}								
																		
												}else if($second.containsKey("IS_LEAF") && "Y".equals($second.get("IS_LEAF").toString())){
													continue;
												}
											}
										}*/			
										if(secondMenus.size()>0){
											topMenus.get(i).put("HAS_CHILD","Y");
											for(int j=0;j<secondMenus.size();j++){
												Map<String,Object> secondMenuObject = secondMenus.get(j);
												List<Map<String,Object>> thirdMenus = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_MENU WHERE ID IN("+menuPool+") AND IS_USING = 'Y' AND MENU_PARENT = "+secondMenuObject.get("ID").toString()+" ORDER BY MENU_SORT ASC");
												if(thirdMenus.size()>0){
													for(int z=0;z<thirdMenus.size();z++){
														Map<String,Object> leafObject = thirdMenus.get(z);
														List<Map<String,Object>> chartMenus = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_MENU WHERE ID IN("+menuPool+") AND IS_USING = 'Y' AND MENU_PARENT = "+leafObject.get("ID").toString()+" ORDER BY MENU_SORT ASC");
														if(chartMenus.size()>0){
															thirdMenus.get(z).put("HAS_CHILD","Y");
															thirdMenus.get(z).put("CHILDREN",chartMenus);
														}else{
															thirdMenus.get(z).put("HAS_CHILD","N");
														}
													}
													secondMenus.get(j).put("HAS_CHILD","Y");
													secondMenus.get(j).put("CHILDREN",thirdMenus);
												}else{
													secondMenus.get(j).put("HAS_CHILD","N");
												}
											}
											topMenus.get(i).put("CHILDREN",secondMenus);
										}else{
											topMenus.get(i).put("HAS_CHILD","N");
										}
									}
									menusObject.put("MENUS",topMenus);
								}
							}else{
								menusObject.put("IS_HAVE_LEAF","N");
							}
						}else{
							menusObject.put("IS_ROLE_MENUED",false);
						}
					}else{
						menusObject.put("IS_BINDED_ROLE",false);
					}
				}
				request.getSession().setAttribute("LOGIN_INFO",menusObject);
			}
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(menusObject.toString());

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/findMenuTreeUnChecks.ilf")
	public void findMenuTreeUnChecks(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String roleCode = request.getParameter("roleCode");
		/*读取角色关联的菜单信息*/
		Map<Integer,Integer> acheObject = new HashMap<Integer,Integer>();
		List<Map<String,Object>> roleMenus = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_ROLE_MENU WHERE ROLE_ID = "+roleCode);		
		if(roleMenus.size()>0){
			for(int n=0;n<roleMenus.size();n++){
				Map<String,Object> roleObject = roleMenus.get(n);
				acheObject.put(Integer.parseInt(roleObject.get("MENU_ID").toString()),Integer.parseInt(roleObject.get("MENU_ID").toString()));
			}
		}
		
		JSONArray jsonArray = new JSONArray();
		List<Map<String,Object>> rootMenus = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_MENU WHERE MENU_PARENT = 0");
		/*根目录*/
		JSONObject rootMenu = new JSONObject();
		Map<String,Object> rootMenuObject = rootMenus.get(0);
		String thisCode = rootMenuObject.get("ID").toString();
		rootMenu.put("pId","0");
		rootMenu.put("id",thisCode);
		rootMenu.put("name",rootMenuObject.get("MENU_NAME").toString());
		rootMenu.put("open",true);
		if(acheObject.containsKey(Integer.parseInt(thisCode))){
			rootMenu.put("checked",true);
		}else{
			rootMenu.put("checked",false);
		}
		jsonArray.add(rootMenu);
		/*一级菜单*/
		List<Map<String,Object>> topMenus = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_MENU WHERE MENU_PARENT = "+thisCode+" ORDER BY MENU_SORT ASC");
		if(topMenus.size()>0){
			for(int j=0;j<topMenus.size();j++){
				Map<String,Object> topMenuObject = topMenus.get(j);
				JSONObject topMenu = new JSONObject();
				topMenu.put("pId",thisCode);
				topMenu.put("id",Integer.parseInt(topMenuObject.get("ID").toString()));					
				topMenu.put("name",topMenuObject.get("MENU_NAME").toString());
				if(acheObject.containsKey(Integer.parseInt(topMenuObject.get("ID").toString()))){
					topMenu.put("checked",true);
				}else{
					topMenu.put("checked",false);
				}
				jsonArray.add(topMenu);
				/*二级菜单*/
				List<Map<String,Object>> secondMenus = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_MENU WHERE MENU_PARENT = "+topMenuObject.get("ID").toString()+" ORDER BY MENU_SORT ASC");
				if(secondMenus.size()>0){
					for(int w=0;w<secondMenus.size();w++){
						Map<String,Object> secondObject = secondMenus.get(w);
						JSONObject secondMenu = new JSONObject();						
						secondMenu.put("pId",Integer.parseInt(topMenuObject.get("ID").toString()));
						secondMenu.put("id",Integer.parseInt(secondObject.get("ID").toString()));					
						secondMenu.put("name",secondObject.get("MENU_NAME").toString());
						if(acheObject.containsKey(Integer.parseInt(secondObject.get("ID").toString()))){
							secondMenu.put("checked",true);
						}else{
							secondMenu.put("checked",false);
						}
						jsonArray.add(secondMenu);
						/*末梢菜单*/
						List<Map<String,Object>> thirdMenus = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_MENU WHERE MENU_PARENT = "+secondObject.get("ID").toString()+" ORDER BY MENU_SORT ASC");
						if(thirdMenus.size()>0){
							for(int k=0;k<thirdMenus.size();k++){
								Map<String,Object> thirdObject = thirdMenus.get(k);
								JSONObject thirdMenu = new JSONObject();						
								thirdMenu.put("pId",Integer.parseInt(secondObject.get("ID").toString()));
								thirdMenu.put("id",Integer.parseInt(thirdObject.get("ID").toString()));					
								thirdMenu.put("name",thirdObject.get("MENU_NAME").toString());
								if(acheObject.containsKey(Integer.parseInt(thirdObject.get("ID").toString()))){
									thirdMenu.put("checked",true);
								}else{
									thirdMenu.put("checked",false);
								}
								jsonArray.add(thirdMenu);
							}
						}
					}					
				}
			}
		}
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonArray.toString());
	}
	
	public Integer findCurrentOrder(Integer parentCode){
		Integer currentCount = jdbcTemplate.queryForInt("SELECT MAX(MENU_SORT) FROM S_SYSTEM_MENU WHERE MENU_PARENT = "+parentCode);
		if(currentCount==null || currentCount==0){
			currentCount = 1;
		}else{
			if(currentCount==1){
				currentCount++;
			}else if(currentCount>1){
				Boolean needIncrease = true;
				for(int i=1;i<currentCount;i++){
					Integer isExist = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM S_SYSTEM_MENU WHERE MENU_PARENT = "+parentCode+" AND MENU_SORT = "+i);
					if(isExist==0){
						needIncrease = false;
						currentCount = i;
						break;
					}
				}
				if(needIncrease){
					currentCount++;
				}
			}
		}
		return currentCount;
	}
	
	public String getRandomToken(){
		String finalString = "";
		String[] chartContainer = new String[]{"A","S","D","F","G","H","J","K","L","Y"};
		Double randomNumber = Math.random()*10;
		Integer indexNumber = randomNumber.intValue();
		while(finalString.length()<=10){
			finalString+=chartContainer[indexNumber];
			randomNumber = Math.random()*10;
			indexNumber = randomNumber.intValue();
		}	
		return finalString;
	}
	
	public String getRandomIcon(){
		String[] chartContainer = new String[]{"system_log","custom","channel","app","cloud","syetem_management","source","statistics"};
		Double randomNumber = Math.random()*8;
		Integer indexNumber = randomNumber.intValue();
		if(indexNumber>7){
			indexNumber = 7;
		}			
		return chartContainer[indexNumber];
	}
	
	@RequestMapping("/saveAudit.ilf")
	public void saveAudit(@RequestParam String params,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			final JSONObject thisObject = JSONObject.fromObject(params);
			if(thisObject.get("menuCode")==null || "".equals(thisObject.get("menuCode").toString())){
				/*菜单排序算法*/
				SystemMenu systemMenu = new SystemMenu(thisObject,true);
				systemMenu.setMENU_TOKEN(getRandomToken());
				systemMenu.setMENU_SORT(findCurrentOrder(Integer.parseInt(thisObject.getString("parentCode"))));
				systemMenu.setICON_NAME(getRandomIcon());
				systemMenuService.insertModel(systemMenu);				
			}else{
				SystemMenu systemMenu = systemMenuService.findOneMenu(Integer.parseInt(thisObject.get("menuCode").toString()));
				Integer parentCode = systemMenu.getMENU_PARENT();
				systemMenu = transferInformation(systemMenu,thisObject);		
				if(Integer.parseInt(thisObject.get("parentCode").toString())!=parentCode){
					/*如果数据库中此菜单的父菜单编号与修改后的相同，则表示没有修改归属的父级菜单，直接保存即可.*/
					parentCode = findCurrentOrder(Integer.parseInt(thisObject.getString("parentCode")));
				}
				systemMenuService.updateModel(systemMenu);
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}	
	}
	
	public SystemMenu transferInformation(SystemMenu systemMenu,JSONObject thisObject){
		systemMenu.setMENU_NAME(thisObject.getString("menuName"));
		systemMenu.setIS_LEAF(thisObject.getString("isLeaf"));
		systemMenu.setMENU_URL(thisObject.getString("menuUrl"));
		systemMenu.setIS_USING(thisObject.getString("isUsing"));
		systemMenu.setMENU_PARENT(Integer.parseInt(thisObject.getString("parentCode")));
		systemMenu.setIS_EXPORT(thisObject.getString("isExport"));
		return systemMenu;
	}
	
	@RequestMapping("/deleteMenu.ilf")
	public void deleteMenu(@RequestParam String menuCode,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			Integer parentCode = Integer.parseInt(jdbcTemplate.queryForMap("SELECT MENU_PARENT AS PARENT_CODE FROM S_SYSTEM_MENU WHERE ID = "+menuCode).get("PARENT_CODE").toString());
			if(parentCode!=1){
				/*删除角色所属菜单中的数据*/
				jdbcTemplate.execute("DELETE FROM S_SYSTEM_ROLE_MENU WHERE MENU_ID = "+menuCode+" OR MENU_ID IN(SELECT ID FROM S_SYSTEM_MENU WHERE MENU_PARENT = "+menuCode+")");
				/*如果不是顶级菜单，则向下钻取一层即可*/
				jdbcTemplate.execute("DELETE FROM S_SYSTEM_MENU WHERE MENU_PARENT = "+menuCode+" OR ID = "+menuCode);
			}else{
				/*删除角色所属菜单中的数据*/
				String deleteSql = "";
				deleteSql+="DELETE FROM S_SYSTEM_ROLE_MENU WHERE MENU_ID IN(";
				deleteSql+="	SELECT ID FROM S_SYSTEM_MENU WHERE MENU_PARENT IN(";
				deleteSql+="		SELECT ID FROM S_SYSTEM_MENU WHERE MENU_PARENT = "+menuCode;
				deleteSql+="	) OR MENU_PARENT = "+menuCode+" OR ID = "+menuCode;
				deleteSql+=")";			
				jdbcTemplate.execute(deleteSql);
				/*如果是顶级菜单，则向下钻取两层*/
				List<Map<String,Object>> secondMenus = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_MENU WHERE MENU_PARENT = "+menuCode);
				if(secondMenus.size()>0){
					for(int i=0;i<secondMenus.size();i++){
						jdbcTemplate.execute("DELETE FROM S_SYSTEM_MENU WHERE MENU_PARENT = "+secondMenus.get(i).get("ID").toString());
					}
				}
				jdbcTemplate.execute("DELETE FROM S_SYSTEM_MENU WHERE MENU_PARENT = "+menuCode+" OR ID = "+menuCode);
			}	
			
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
}
