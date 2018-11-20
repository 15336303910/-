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

import com.system.LoginUserUtil;
import com.systemConfig.dao.RoleService;
import com.systemConfig.dao.SystemUserService;
import com.systemConfig.model.DataTableResult;
import com.systemConfig.model.Role;
import com.systemConfig.model.SystemUser;
@Controller("com.systemConfig.action.UserManageAction")
@RequestMapping(value="/userManageAction")
public class UserManageAction{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SystemUserService systemUserService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	@RequestMapping("/findMyIcon.ilf")
	public void findMyIcon(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			String iconName = loginUserUtil.getUserIcon(request);
			resultObject.put("iconName",iconName);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	@RequestMapping("/updateMyIcon.ilf")
	public void updateMyIcon(@RequestParam String newIcon,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			Integer userCode = loginUserUtil.getLoginUserId(request);
			jdbcTemplate.execute("UPDATE S_SYSTEM_USER SET USER_ICON = '"+newIcon+"' WHERE ID = "+userCode);
			if(loginUserUtil.isLogined(request)){
				Map<String,Object> loginUser = (Map<String,Object>)request.getSession().getAttribute("LoginUserInfo");
				loginUser.put("USER_ICON",newIcon);
				request.getSession().setAttribute("LoginUserInfo",loginUser);
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	@RequestMapping("/findOne.ilf")
	public void findOne(@RequestParam String userCode,HttpServletResponse response)throws Exception{
		SystemUser systemUser = systemUserService.findOneObject(Integer.parseInt(userCode));
		if(systemUser!=null){
			JSONObject jsonObject = JSONObject.fromObject(systemUser);
			if(jsonObject!=null){
				if(systemUser.getROLE_CODE()!=null && systemUser.getROLE_CODE()!=-1){
					Role role = roleService.getRole(systemUser.getROLE_CODE());
					if(role!=null){
						jsonObject.put("ROLE_NAME",role.getROLE_NAME());				
					}else{
						jsonObject.put("ROLE_NAME","");
					}
				}else{
					jsonObject.put("ROLE_NAME","");
				}
				jsonObject.put("success",true);
				response.setContentType("application/json; charset=UTF-8");
				response.getWriter().print(jsonObject.toString());
			}
		}
	}
	
	@RequestMapping("/findUseraccounts.ilf")
	public void findUseraccounts(
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
		if(conditonMap.containsKey("IS_FILTER_REGION") && "Y".equals(conditonMap.get("IS_FILTER_REGION").toString())){
			/*
			 * 	如果过滤地市信息.
			 * 
			 * */
			if(!loginUserUtil.isProvince(request)){
				conditonMap.put("BELONG_AREA",loginUserUtil.getBelongArea(request).length()<=2?loginUserUtil.getBelongArea(request):loginUserUtil.getBelongArea(request).substring(0,2));
			}
			conditonMap.remove("IS_FILTER_REGION");
		}
		Integer count = systemUserService.findCount(conditonMap);
		List<SystemUser> objectList = systemUserService.findMenus(conditonMap);
		DataTableResult<SystemUser> tableData = new DataTableResult<SystemUser>();
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
			if(thisObject.get("userCode")==null || "".equals(thisObject.get("userCode").toString())){
				/*新增用户操作*/
				Integer isExist =  systemUserService.getCount("from SystemUser where USER_NAME = '"+thisObject.getString("userAccount")+"'");
				if(isExist==0){
					SystemUser thisUser = new SystemUser(thisObject,true);
					systemUserService.insertModel(thisUser);
				}else{
					resultObject.put("success",false);
					resultObject.put("message","系统中已存在同名的账户.");
				}		
			}else{
				Boolean needValid = true;
				SystemUser storedUser = systemUserService.findOneObject(Integer.parseInt(thisObject.get("userCode").toString()));
				if(storedUser.getUSER_NAME().equals(thisObject.getString("userAccount"))){
					needValid = false;
				}
				Boolean isValid = true;
				if(needValid){
					Integer isExist = systemUserService.getCount("from SystemUser where USER_NAME = '"+thisObject.getString("userAccount")+"'");
					if(isExist>0){
						isValid = false;
					}
				}
				if(isValid){
					thisObject.put("userPassword",storedUser.getUSER_PASS());
					SystemUser thisUser = new SystemUser(thisObject,false);
					systemUserService.updateModel(thisUser);
				}else{
					resultObject.put("success",false);
					resultObject.put("message","系统中已存在同名的账户.");
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

	/*	
	 * 	删除账户
	 * 
	 * */
	@RequestMapping("/deleteUserAccount.ilf")
	public void deleteUserAccount(@RequestParam String userCode,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			systemUserService.deleteModel(Integer.parseInt(userCode));
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	锁定/解锁账户
	 * 
	 * */
	@RequestMapping("/lockUser.ilf")
	public void lockUser(@RequestParam String userCode,@RequestParam String userState,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			SystemUser systemUser = systemUserService.findOneObject(Integer.parseInt(userCode));
			if(systemUser!=null){
				systemUser.setIS_LOCKED(userState);
				systemUserService.updateModel(systemUser);
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
