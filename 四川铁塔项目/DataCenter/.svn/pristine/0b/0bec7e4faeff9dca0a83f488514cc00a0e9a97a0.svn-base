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

import com.systemConfig.dao.OrganizationService;
import com.systemConfig.dao.SystemUserService;
import com.systemConfig.model.DataTableResult;
import com.systemConfig.model.SystemOrganization;
import com.systemConfig.model.SystemUser;
@Controller("com.systemConfig.action.DepartConfigAction")
@RequestMapping(value="/departConfigAction")
public class DepartConfigAction{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private SystemUserService systemUserService;	
	
	/*
	 * 	获取一个组织详情
	 * 
	 * */
	@RequestMapping("/findOne.ilf")
	public void findOne(@RequestParam String departId,HttpServletResponse response)throws Exception{
		SystemOrganization organization = organizationService.getObject(Integer.parseInt(departId));
		if(organization!=null){
			JSONObject organObject = JSONObject.fromObject(organization);
			organObject.put("success",true);
			if(organization.getPARENT_CODE()!=0){
				SystemOrganization parentOrganization = organizationService.getObject(organization.getPARENT_CODE());
				organObject.put("PARENT_NAME",parentOrganization.getORGANIZATION_NAME());			
			}else{
				organObject.put("PARENT_NAME","");
			}
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(organObject.toString());
		}	
	}
	
	/*
	 * 	查询组织列表
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
		Integer count = organizationService.getCount(conditonMap);
		List<SystemOrganization> organizations = organizationService.getOrgansPage(conditonMap);	
		DataTableResult<SystemOrganization> tableData = new DataTableResult<SystemOrganization>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(organizations);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	保存/修改
	 * 
	 * */
	@RequestMapping("/saveAudit.ilf")
	public void saveAudit(@RequestParam String params,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'组织配置成功.'}");
		try{
			final JSONObject thisObject = JSONObject.fromObject(params);
			if(thisObject.get("departCode")==null || "".equals(thisObject.get("departCode").toString())){
				SystemOrganization organization = new SystemOrganization(thisObject,true);
				organizationService.insertOrganization(organization);
			}else{
				SystemOrganization storedOrganization = organizationService.getObject(thisObject.getInt("departCode"));
				String organLevel = storedOrganization.getORGANIZATION_LEVEL();
				if("部门".equals(organLevel)){
					jdbcTemplate.execute("UPDATE S_SYSTEM_USER SET EMPLOYEE_DEPARTNAME = '"+thisObject.getString("departName")+"' WHERE EMPLOYEE_DEPART_CODE = "+thisObject.getInt("departCode"));
				}else if("总公司".equals(organLevel) || "子公司".equals(organLevel)){
					jdbcTemplate.execute("UPDATE S_SYSTEM_USER SET EMPLOYEE_COMPANY = '"+thisObject.getString("departName")+"' WHERE EMPLOYEE_COMPANY_CODE = "+thisObject.getInt("departCode"));
				}				
				SystemOrganization organization = new SystemOrganization(thisObject,false);
				organizationService.updateObject(organization);
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
	
	@RequestMapping("/deleteItem.ilf")
	public void deleteItem(@RequestParam String organizationCode,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			List<Map<String,Object>> departList = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_ORGANIZATION WHERE ID = "+organizationCode);
			if("部门".equals(departList.get(0).get("ORGANIZATION_LEVEL").toString())){
				/*删除此部门包含的员工账号信息*/
				jdbcTemplate.execute("DELETE FROM S_SYSTEM_USER WHERE EMPLOYEE_DEPART_CODE = "+organizationCode);
				/*删除部门信息*/
				jdbcTemplate.execute("DELETE FROM S_SYSTEM_ORGANIZATION WHERE ID = "+organizationCode);
			}else if("分公司".equals(departList.get(0).get("ORGANIZATION_LEVEL").toString())){
				/*删除分公司包含的部门信息*/
				List<Map<String,Object>> departsOfCompany = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_ORGANIZATION WHERE PARENT_CODE = "+organizationCode);
				if(departsOfCompany.size()>0){
					for(int i=0;i<departsOfCompany.size();i++){
						/*删除此部门包含的员工账号信息*/
						jdbcTemplate.execute("DELETE FROM S_SYSTEM_USER WHERE EMPLOYEE_DEPART_CODE = "+departsOfCompany.get(i).get("ID").toString());
						/*删除部门信息*/
						jdbcTemplate.execute("DELETE FROM S_SYSTEM_ORGANIZATION WHERE ID = "+departsOfCompany.get(i).get("ID").toString());
					}
				}
				/*删除分公司信息*/
				jdbcTemplate.execute("DELETE FROM S_SYSTEM_USER WHERE EMPLOYEE_COMPANY_CODE = "+organizationCode);
				jdbcTemplate.execute("DELETE FROM S_SYSTEM_ORGANIZATION WHERE ID = "+organizationCode);
			}else if("总公司".equals(departList.get(0).get("ORGANIZATION_LEVEL").toString())){
				/*删除总公司包含的分公司信息*/
				List<Map<String,Object>> filiales = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_ORGANIZATION WHERE PARENT_CODE = "+organizationCode);
				if(filiales.size()>0){
					for(int i=0;i<filiales.size();i++){
						/*删除分公司包含的部门信息*/
						List<Map<String,Object>> departsOfCompany = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_ORGANIZATION WHERE PARENT_CODE = "+filiales.get(i).get("ID").toString());
						if(departsOfCompany.size()>0){
							for(int j=0;j<departsOfCompany.size();j++){
								/*删除此部门包含的员工账号信息*/
								jdbcTemplate.execute("DELETE FROM S_SYSTEM_USER WHERE EMPLOYEE_DEPART_CODE = "+departsOfCompany.get(i).get("ID").toString());
								/*删除部门信息*/
								jdbcTemplate.execute("DELETE FROM S_SYSTEM_ORGANIZATION WHERE ID = "+departsOfCompany.get(i).get("ID").toString());
							}
						}
						/*删除分公司信息*/
						jdbcTemplate.execute("DELETE FROM S_SYSTEM_USER WHERE EMPLOYEE_COMPANY_CODE = "+filiales.get(i).get("ID").toString());
						jdbcTemplate.execute("DELETE FROM S_SYSTEM_ORGANIZATION WHERE ID = "+filiales.get(i).get("ID").toString());
					}
				}
				/*删除总公司信息*/
				jdbcTemplate.execute("DELETE FROM S_SYSTEM_USER WHERE EMPLOYEE_COMPANY_CODE = "+organizationCode);
				jdbcTemplate.execute("DELETE FROM S_SYSTEM_ORGANIZATION WHERE ID = "+organizationCode);
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	获取公司列表
	 * 
	 * */
	@RequestMapping("/findCompanies.ilf")
	public void findCompanies(HttpServletResponse response)throws Exception{
		List<SystemOrganization> companys = organizationService.getOrgansByHql("from SystemOrganization where ORGANIZATION_LEVEL in ('总公司','分公司')");
		JSONArray jsonArray = new JSONArray();
		if(companys.size()>0){
			for(int j=0;j<companys.size();j++){
				SystemOrganization topDepartObject = companys.get(j);
				JSONObject topMenu = new JSONObject();
				topMenu.put("pId",0);
				topMenu.put("id",topDepartObject.getID());					
				topMenu.put("name",topDepartObject.getORGANIZATION_NAME());
				topMenu.put("checked",false);
				jsonArray.add(topMenu);
			}
		}	
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonArray.toString());
	}
	
	/*
	 * 	获取部门列表
	 * 
	 * */
	@RequestMapping("/findDeparts.ilf")
	public void findDeparts(@RequestParam String companyCode,HttpServletResponse response)throws Exception{
		List<SystemOrganization> departList = organizationService.getOrgansByHql("from SystemOrganization where PARENT_CODE = "+companyCode);
		JSONArray jsonArray = new JSONArray();
		if(departList.size()>0){
			for(int j=0;j<departList.size();j++){
				SystemOrganization secondDepartObject = departList.get(j);
				JSONObject topMenu = new JSONObject();
				topMenu.put("pId",0);
				topMenu.put("id",secondDepartObject.getID());					
				topMenu.put("name",secondDepartObject.getORGANIZATION_NAME());
				topMenu.put("checked",false);
				jsonArray.add(topMenu);
			}
		}	
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonArray.toString());
	}
	
	/*
	 * 	获取组织树
	 * 
	 * */
	@RequestMapping("/findTreeData.ilf")
	public void findTreeData(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONArray jsonArray = new JSONArray();
		if(request.getSession().getAttribute("ORGANIZATION_TREE")!=null){
			jsonArray = (JSONArray)request.getSession().getAttribute("ORGANIZATION_TREE");
		}else{
			/*公司*/
			List<SystemOrganization> companyList = organizationService.getOrgansByHql("from SystemOrganization where PARENT_CODE = 0");
			if(companyList.size()>0){
				for(int j=0;j<companyList.size();j++){
					SystemOrganization topDepartObject = companyList.get(j);
					JSONObject topMenu = new JSONObject();
					topMenu.put("pId",0);
					topMenu.put("id",topDepartObject.getID());					
					topMenu.put("name",topDepartObject.getORGANIZATION_NAME());
					topMenu.put("checked",false);
					/*部门*/
					List<SystemOrganization> secondDeparts = organizationService.getOrgansByHql("from SystemOrganization where PARENT_CODE = "+topDepartObject.getID());
					if(secondDeparts.size()>0){
						jsonArray.add(topMenu);
						for(int w=0;w<secondDeparts.size();w++){
							SystemOrganization secondObject = secondDeparts.get(w);
							JSONObject secondMenu = new JSONObject();						
							secondMenu.put("pId",topDepartObject.getID());
							secondMenu.put("id",secondObject.getID());					
							secondMenu.put("name",secondObject.getORGANIZATION_NAME());
							secondMenu.put("checked",false);
							jsonArray.add(secondMenu);
						}					
					}
				}
			}
		}
		if(request.getSession().getAttribute("ORGANIZATION_TREE")==null){
			request.getSession().setAttribute("ORGANIZATION_TREE",jsonArray);
		}
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonArray.toString());
	}
}
