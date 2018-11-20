package com.systemConfig.action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.systemConfig.dao.RoleMenuService;
import com.systemConfig.dao.RoleService;
import com.systemConfig.model.DataTableResult;
import com.systemConfig.model.Role;
import com.systemConfig.model.RoleMenu;
@Controller("com.systemConfig.action.RoleManageAction")
@RequestMapping(value="/roleManageAction")
public class RoleManageAction{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleMenuService roleMenuService;
	
	@RequestMapping("/findRoles.ilf")
	public void findRoles(@RequestParam String tableparam,@RequestParam String conditions,HttpServletResponse response)throws Exception{
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
		Integer count = roleService.getCount(conditonMap);
		List<Role> objectList = roleService.getRolesPage(conditonMap);	
		DataTableResult<Role> tableData = new DataTableResult<Role>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(objectList);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	@RequestMapping("/saveAudit.ilf")
	public void saveAudit(@RequestParam String params,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'角色配置成功.'}");
		try{
			final JSONObject thisObject = JSONObject.fromObject(params);
			if(thisObject.get("roleCode")==null || "".equals(thisObject.get("roleCode").toString())){
				Integer isExist = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM S_SYSTEM_ROLE WHERE ROLE_NAME = '"+thisObject.getString("roleNames")+"'");
				if(isExist==0){
					/*插入角色信息*/
					Role thisRole = new Role();
					thisRole.setROLE_NAME(thisObject.getString("roleNames"));
					thisRole.setIS_USING(thisObject.getString("isUsings"));
					Integer newCode = roleService.insertRole(thisRole);
			        /*插入菜单信息*/
			        String[] checkedNodes = thisObject.getString("checkedNodes").split(",");
			        for(int i=0;i<checkedNodes.length;i++){
			        	String checkedNode = checkedNodes[i];
			        	RoleMenu roleMenu = new RoleMenu();
			        	roleMenu.setROLE_ID(newCode);
			        	roleMenu.setMENU_ID(Integer.parseInt(checkedNode));
			        	roleMenuService.insertModel(roleMenu);
			        }
				}else{
					resultObject.put("success",false);
					resultObject.put("message","数据库中已存在同名的角色.");
				}						
			}else{
				Boolean needValid = true;
				Map<String,Object> roleObject = jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_ROLE WHERE ID = "+thisObject.get("roleCode").toString());
				if(thisObject.getString("roleNames").equals(roleObject.get("ROLE_NAME").toString())){
					needValid = false;
				}
				Boolean isValid = true;
				if(needValid){
					Integer isExist = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM S_SYSTEM_ROLE WHERE ROLE_NAME = '"+thisObject.getString("roleNames")+"'");
					if(isExist>0){
						isValid = false;
					}
				}
				if(isValid){
					/*修改主信息*/
					String updateSQL = "UPDATE S_SYSTEM_ROLE SET ";
					updateSQL+="ROLE_NAME = '"+thisObject.getString("roleNames")+"',";
					updateSQL+="IS_USING = '"+thisObject.getString("isUsings")+"' ";				
					updateSQL+="WHERE ID = "+thisObject.get("roleCode").toString();
					jdbcTemplate.execute(updateSQL);
					/*修改菜单配置信息*/
					jdbcTemplate.execute("DELETE FROM S_SYSTEM_ROLE_MENU WHERE ROLE_ID = "+thisObject.get("roleCode").toString());
			        String[] checkedNodes = thisObject.getString("checkedNodes").split(",");
			        for(int i=0;i<checkedNodes.length;i++){
			        	String checkedNode = checkedNodes[i];
			        	RoleMenu roleMenu = new RoleMenu();
			        	roleMenu.setROLE_ID(Integer.parseInt(thisObject.get("roleCode").toString()));
			        	roleMenu.setMENU_ID(Integer.parseInt(checkedNode));
			        	roleMenuService.insertModel(roleMenu);
			        }
				}else{
					resultObject.put("success",false);
					resultObject.put("message","数据库中已存在同名的角色名称.");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
			resultObject.put("message","系统异常.");
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}	
	}

	@RequestMapping("/findRolesTree.ilf")
	public void findRolesTree(@RequestParam String areaName,HttpServletResponse response)throws Exception{
		List<Map<String,Object>> roleObjects = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_ROLE WHERE IS_USING = 'Y'");
		if(roleObjects.size()>0){
			for(int i=0;i<roleObjects.size();i++){
				roleObjects.get(i).put("id",roleObjects.get(i).get("ID").toString());
				roleObjects.get(i).put("pId",-1);
				roleObjects.get(i).put("name",roleObjects.get(i).get("ROLE_NAME").toString());
			}
		}else{
			Map<String,Object> nullObject = new HashMap<String,Object>();
			nullObject.put("id",0);
			nullObject.put("pId",-1);
			nullObject.put("name","暂无可用的角色");
			roleObjects.add(nullObject);
		}
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONArray.fromObject(roleObjects).toString());
	} 
	
	@RequestMapping("/findOne.ilf")
	public void findOne(@RequestParam String roleCode,HttpServletResponse response)throws Exception{
		Role thisRole = roleService.getRole(Integer.parseInt(roleCode));
		JSONObject thisObject = JSONObject.fromObject(thisRole);
		if(thisObject!=null){
			thisObject.put("success",true);
		}
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(thisObject.toString());
	}
	
	@RequestMapping("/deleteRole.ilf")
	public void deleteRole(@RequestParam String roleCode,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			/*初始化当前正在使用此角色的用户信息*/
			jdbcTemplate.execute("UPDATE S_SYSTEM_USER SET ROLE_CODE = -1 WHERE ROLE_CODE = "+roleCode);			
			/*删除此角色下包含的菜单信息*/
			jdbcTemplate.execute("DELETE FROM S_SYSTEM_ROLE_MENU WHERE ROLE_ID = "+roleCode);
			/*删除角色信息*/
			jdbcTemplate.execute("DELETE FROM S_SYSTEM_ROLE WHERE ID = "+roleCode);			
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
}
