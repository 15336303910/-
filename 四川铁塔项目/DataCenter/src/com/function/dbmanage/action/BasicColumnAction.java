package com.function.dbmanage.action;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.function.dbmanage.model.BasicColumn;
import com.function.dbmanage.service.BasicColumnService;
import com.systemConfig.model.DataTableResult;
@Controller("com.function.dbmanage.action.BasicColumnAction")
@RequestMapping(value="/basicColumnAction")
public class BasicColumnAction{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private BasicColumnService basicColumnService;	
	
	/*
	 * 	获取一个详情
	 * 
	 * */
	@RequestMapping("/findOne.ilf")
	public void findOne(@RequestParam String columnId,HttpServletResponse response)throws Exception{
		BasicColumn basicColumn = new BasicColumn();
		try{
			basicColumn = basicColumnService.selectModel(Integer.parseInt(columnId));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(JSONObject.fromObject(basicColumn).toString());
		}		
	}
	
	/*
	 * 	获取模型包含的字段信息
	 * 
	 * */
	@RequestMapping("/findColumnsOfModel.ilf")
	public void findColumnsOfModel(@RequestParam String modelId,HttpServletResponse response)throws Exception{
		List<BasicColumn> basicColumns = null;
		try{
			basicColumns = basicColumnService.selectModelsByHql("from BasicColumn where BELONG_TABLE = "+modelId);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(JSONArray.fromObject(basicColumns).toString());
		}		
	}
	
	/*
	 * 	查询列表
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
		Integer count = basicColumnService.getCount(conditonMap);
		List<BasicColumn> basicColumns = basicColumnService.getItemPage(conditonMap);
		/*封装数据*/
		DataTableResult<BasicColumn> tableData = new DataTableResult<BasicColumn>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(basicColumns);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	查询列表[不分页]
	 * 
	 * */
	@RequestMapping("/findItemsUnPage.ilf")
	public void findItemsUnPage(@RequestParam String tableparam,@RequestParam String conditions,HttpServletResponse response)throws Exception{
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
		List<BasicColumn> basicColumns = basicColumnService.selectModelsByHql("from BasicColumn where BELONG_TABLE = "+conditonMap.get("BELONG_TABLE").toString());
		/*封装数据*/
		DataTableResult<BasicColumn> tableData = new DataTableResult<BasicColumn>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(basicColumns);
		tableData.setiTotalRecords(basicColumns.size());
		tableData.setiTotalDisplayRecords(basicColumns.size());
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	保存/修改
	 * 
	 * */
	@RequestMapping("/saveAudit.ilf")
	public void saveAudit(@RequestParam String params,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'字段配置成功.'}");
		try{
			BasicColumn basicColumn = null;
			final JSONObject thisObject = JSONObject.fromObject(params);
			if(thisObject.get("id")==null || "".equals(thisObject.get("id").toString())){
				basicColumn = new BasicColumn();
			}else{
				basicColumn = basicColumnService.selectModel(Integer.parseInt(thisObject.get("id").toString()));		
			}		
			basicColumn.setBELONG_TABLE(Integer.parseInt(thisObject.getString("belongTable")));
			basicColumn.setCOLUMN_ALIAS(thisObject.getString("aliasName"));
			basicColumn.setCOLUMN_NAME(thisObject.getString("columnName"));
			basicColumn.setDATA_TYPE(thisObject.getString("dataType"));
			basicColumn.setIS_EXPORT(thisObject.getString("isExport"));
			basicColumn.setIS_PRIMARY_KEY(thisObject.getString("isPrimary"));
			basicColumn.setIS_NULL_ABLE(thisObject.getString("isNullAble"));
			if(thisObject.get("id")==null || "".equals(thisObject.get("id").toString())){
				basicColumnService.insertModel(basicColumn);
			}else{
				basicColumnService.updateModel(basicColumn);
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
	 * 	删除
	 * 
	 * */
	@RequestMapping("/deleteItem.ilf")
	public void deleteItem(@RequestParam String itemCode,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{	
			/*删除属性*/
			basicColumnService.deleteModel(Integer.parseInt(itemCode));
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
}
