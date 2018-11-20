package manage.gd.gdManage.action;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.opensymphony.xwork2.ModelDriven;
import manage.gd.gdManage.pojo.GdTaskMain;
import manage.gd.gdManage.service.impl.IhcgdManageService;
import manage.user.pojo.UserInfoBean;
import base.web.PaginationAction;
public class GdManageAction extends PaginationAction implements ModelDriven<GdTaskMain>{
	
	private GdTaskMain gdTaskMain = new GdTaskMain();	
	private IhcgdManageService hcgdManageService;
	
	/*
	 * 	获取基本信息
	 * 
	 * */
	public void findDetail()throws Exception{
		JSONObject thisObject = new JSONObject();
		thisObject.put("success",true);
		JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
		String itemCode = this.getRequest().getParameter("itemCode");
		List<Map<String,Object>> taskItems = jdbcTemplate.queryForList("select * from gd_main where id = "+itemCode);
		if(taskItems.size()>0){
			thisObject.put("gdDetail",taskItems.get(0));
		}else{
			thisObject.put("success",false);
		}	
		this.printString(thisObject.toString(), null);
	}
	
	/*
	 * 	获取任务核查详情
	 * 
	 * */
	public void getCheckResult()throws Exception{
		JSONObject thisObject = new JSONObject();
		thisObject.put("success",true);
		JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
		String itemCode = this.getRequest().getParameter("itemCode");
		String sql = "select a.res_dimension,a.resource_name,b.check_item,b.is_checked,b.is_ok,b.new_value,b.contents ";
		sql+="from gd_main_resource a,gd_main_check b  ";
		sql+="where a.belong_gd = "+itemCode+" and a.id = b.belong_resource order by a.res_dimension asc,b.is_checked desc";
		List<Map<String,Object>> checkItems = jdbcTemplate.queryForList(sql);
		thisObject.put("total",checkItems.size());
		thisObject.put("items",checkItems);		
		this.printString(thisObject.toString(), null);
	}
	
	/*
	 * 	获取存在问题的核查详情
	 * 
	 * */
	public void getFatalChecks()throws Exception{
		JSONObject thisObject = new JSONObject();
		thisObject.put("success",true);
		JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
		String itemCode = this.getRequest().getParameter("itemCode");
		String sql = "select a.res_dimension,a.resource_name,b.check_item,b.is_checked,b.is_ok,b.new_value,b.contents ";
		sql+="from gd_main_resource a,gd_main_check b  ";
		sql+="where b.is_checked = 'Y' and b.is_ok = 'N' and a.belong_gd = "+itemCode+" and a.id = b.belong_resource order by a.res_dimension asc,b.is_checked desc";
		List<Map<String,Object>> checkItems = jdbcTemplate.queryForList(sql);
		thisObject.put("total",checkItems.size());
		thisObject.put("items",checkItems);		
		this.printString(thisObject.toString(), null);
	}
	
	/*
	 * 	保存审核
	 * 
	 * */
	public void saveCheckAudit()throws Exception{
		JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
		String newState = "已完成";
		if(this.getRequest().getParameter("auditResult")!=null && "驳回".equals(this.getRequest().getParameter("auditResult"))){
			newState = "已驳回";
		}
		String sql = "update gd_main set task_state = '"+newState+"',audit_contents = '"+this.getRequest().getParameter("auditDesc")+"' where id = "+this.getRequest().getParameter("taskCode");
		jdbcTemplate.execute(sql);
		this.printString("{success:true}",null);
	}
	
	/*
	 * 	保存编辑
	 * 
	 * */
	public void saveAudits()throws Exception{
		JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
		/*保存工单概要信息*/
		JSONObject thisObject = JSONObject.fromObject(this.getRequest().getParameter("thisObject"));
		Integer newCode = saveJobDetail(thisObject);
		thisObject.put("newCode",newCode);
		/*更新核查工单顶级资源*/
		saveTopLevelResource(thisObject);
		/*遍历获取次级资源*/
		String currentCode = newCode.toString();
		String currentType = thisObject.getString("resourceType");
		while(isHasSubchild(currentType)){
			Map<String,Object> childRms = findChildren(currentType);
			List<Map<String,Object>> checks = jdbcTemplate.queryForList("select * from config_checks where belong_dimension = '"+childRms.get("resource_en_name").toString()+"'");
			if(checks.size()>0){
				List<Map<String,Object>> resourceList = jdbcTemplate.queryForList("select * from gd_main_resource where belong_gd = "+currentCode+" and res_dimension = '"+currentType+"'");
				for(int i=0;i<resourceList.size();i++){
					String resourceCode = resourceList.get(i).get("resource_code").toString();
					List<Map<String,Object>> subResources = jdbcTemplate.queryForList("select "+childRms.get("code_column").toString()+" as resourceCode,"+childRms.get("value_column").toString()+" as resourceName from "+childRms.get("rms_table_name").toString()+" where "+childRms.get("relate_key").toString()+" = '"+resourceCode+"'");
					if(subResources.size()>0){
						for(int j=0;j<subResources.size();j++){					
							String resesSql = "";
							resesSql+="insert into gd_main_resource(";
								resesSql+="belong_gd,resource_code,resource_name,res_dimension,parent_code,is_checked";
							resesSql+=")values(";
								resesSql+=currentCode+",'"+subResources.get(j).get("resourceCode").toString()+"','"+subResources.get(j).get("resourceName").toString()+"','"+childRms.get("resource_cn_name").toString()+"',"+resourceList.get(i).get("id").toString()+",'N'";
							resesSql+=")";
							final String finalSql = resesSql;	
							KeyHolder thisHolder = new GeneratedKeyHolder();
							jdbcTemplate.update(
								new PreparedStatementCreator(){
									public PreparedStatement createPreparedStatement(Connection connection)throws SQLException{
										PreparedStatement preparedStatement = connection.prepareStatement(finalSql,Statement.RETURN_GENERATED_KEYS); 
										preparedStatement = connection.prepareStatement(finalSql,Statement.RETURN_GENERATED_KEYS);    
										return preparedStatement;
									}
								},thisHolder
							);
							final Integer thisCode = thisHolder.getKey().intValue();
							/*保存核查项*/
							saveChecksOfResource(thisCode,checks);							
						}
					}
				}	
				currentType = childRms.get("resource_cn_name").toString();
			}else{
				currentType = "-1";
			}
		}
		this.printString("{success:true}", null);
	}
	
	/*保存核查项*/
	public void saveChecksOfResource(Integer belongCode,List<Map<String,Object>> checks)throws Exception{
		if(checks.size()>0){
			JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
        	for(int s=0;s<checks.size();s++){
        		Map<String,Object> checkObject = checks.get(s);
        		String checkSQL = "insert into gd_main_check(";
        			checkSQL+="belong_resource,check_item,is_ok,is_checked,is_consider,consider_column,edit_type,is_need_check,res_column";
        		checkSQL+=")values(";
        			checkSQL+=belongCode+",'"+checkObject.get("item_desc").toString()+"','Y','N','"+checkObject.get("is_consider").toString()+"','"+checkObject.get("consider_column").toString()+"','"+checkObject.get("edit_type").toString()+"','"+checkObject.get("is_using").toString()+"','"+(checkObject.get("res_consider_column")==null?"":checkObject.get("res_consider_column").toString())+"'";
        		checkSQL+=")";
        		jdbcTemplate.execute(checkSQL);
        	}
        }
	}
	
	/*保存工单概要信息*/
	public Integer saveJobDetail(JSONObject thisObject)throws Exception{
		JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();		
		GdTaskMain thisMain = new GdTaskMain();			
		Date nowDay = new Date();
		Integer nowYears = nowDay.getYear()+1900;
		Integer nowMonth = nowDay.getMonth()+1;
		Integer nowDates = nowDay.getDate();
		Integer maxOrder = jdbcTemplate.queryForInt("select max(task_order) from gd_main where task_year = "+nowYears+" and task_month = "+nowMonth+" and task_date = "+nowDates);
		if(maxOrder==null || maxOrder==0){
			maxOrder = 1;
		}else{
			maxOrder = ++maxOrder;
		}
		thisMain.setTaskOrder(maxOrder);
		String tailOrder = maxOrder.toString();
		while(tailOrder.length()<5){
			tailOrder = "0"+tailOrder;
		}
		String gdCode = "GD-"+nowYears+""+(nowMonth<10?"0"+nowMonth:nowMonth)+""+(nowDates<10?"0"+nowDates:nowDates)+""+tailOrder;
		Integer createUserCode = 0;
		String createUserName = " ";
		UserInfoBean loginUser = (UserInfoBean)this.getRequest().getSession().getAttribute("userBean");
		if(loginUser!=null){
			createUserCode = loginUser.getUserId();
			createUserName = loginUser.getUsername();
		}
		String sql = "";
		sql+="insert into gd_main(";
			sql+="gd_code,resource_type,task_subject,task_describe,task_state,receive_region_code,receive_region_name,task_year,task_month,task_date,task_order,finish_datetime,create_datetime,create_user_code,create_user_name";
		sql+=")values(";
			sql+="'"+gdCode+"',";
			sql+="'"+thisObject.getString("resourceType")+"',";
			sql+="'"+thisObject.getString("taskSubject")+"',";
			sql+="'"+thisObject.getString("taskDescribe")+"',";
			sql+="'未接单',";
			sql+="'"+thisObject.getString("receiveRegionCode")+"',";
			sql+="'"+thisObject.getString("receiveRegionName")+"',"+nowYears+","+nowMonth+","+nowDates+","+maxOrder+",";
			sql+="('"+thisObject.getString("finishDate")+"'),now(),"+createUserCode+",'"+createUserName+"'";				
		sql+=")";
		final String insertSQL = sql;
		KeyHolder keyHolder = new GeneratedKeyHolder();        
        jdbcTemplate.update(
        	new PreparedStatementCreator(){
                   public PreparedStatement createPreparedStatement(Connection connection)throws SQLException{
                	   PreparedStatement preparedStatement = connection.prepareStatement(insertSQL,Statement.RETURN_GENERATED_KEYS); 
                	   preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);    
                	   return preparedStatement;
                   }
        	},
        	keyHolder
        );    
        final Integer newCode = keyHolder.getKey().intValue();
		return newCode;		
	}
	
	/*更新核查工单顶级资源*/
	public void saveTopLevelResource(JSONObject thisObject)throws Exception{
		JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
		/*获取顶级类型的核查项*/
		String resourceType = thisObject.getString("resourceType");
		List<Map<String,Object>> checkItems = jdbcTemplate.queryForList("select * from config_checks where belong_dimension in(select resource_en_name from config_resource_table where resource_cn_name = '"+resourceType+"')");
		/*保存已选定的顶级资源*/
		String[] resCodes = thisObject.getString("resourceCode").split(",");
		String[] resNames = thisObject.getString("resourceName").split(",");
		for(int w=0;w<resCodes.length;w++){
			String resCode = resCodes[w];
			String resName = resNames[w];
			String resourceSql = "";
			resourceSql+="insert into gd_main_resource(";
				resourceSql+="belong_gd,resource_code,resource_name,res_dimension,parent_code,is_checked";
			resourceSql+=")values(";
				resourceSql+=thisObject.getString("newCode")+",'"+resCode+"','"+resName+"','"+thisObject.getString("resourceType")+"',0,'N'";
			resourceSql+=")";
			final String resSql = resourceSql;	
			KeyHolder newHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(
				new PreparedStatementCreator(){
					public PreparedStatement createPreparedStatement(Connection connection)throws SQLException{
						PreparedStatement preparedStatement = connection.prepareStatement(resSql,Statement.RETURN_GENERATED_KEYS); 
						preparedStatement = connection.prepareStatement(resSql,Statement.RETURN_GENERATED_KEYS);    
						return preparedStatement;
					}
				},newHolder
			);    
			final Integer newResCode = newHolder.getKey().intValue();
			/*保存核查项*/
			saveChecksOfResource(newResCode,checkItems);
		}
	}
	
	public Boolean isHasSubchild(String parentTypeName){
		JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
		Integer isExist = jdbcTemplate.queryForInt("select count(1) from config_resource_table where parent_type = '"+parentTypeName+"'");
		if(isExist>0){
			return true;
		}else{
			return false;
		}
	}
	
	public Map<String,Object> findChildren(String parentTypeName){
		JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
		List<Map<String,Object>> childNodes = jdbcTemplate.queryForList("select * from config_resource_table where parent_type = '"+parentTypeName+"'");
		return childNodes.get(0);
	}

	/*
	 * 	分页查询
	 * 
	 * */
	public void getTaskList()throws Exception{		
		Object thisUser = this.getRequest().getSession().getAttribute("userBean");
		if(thisUser!=null){
			UserInfoBean loginUser = (UserInfoBean)thisUser;
			this.gdTaskMain.setCreateUserCode(loginUser.getUserId());
		}	
		this.gdTaskMain = hcgdManageService.getTasksList(this.gdTaskMain);
		JSONObject thisObject = new JSONObject();
		thisObject.put("total",gdTaskMain.getTotal());
		thisObject.put("items",gdTaskMain.getItems());
		this.printString(thisObject.toString(), null);
	}
	
	/*
	 * 	查询资源类型数据
	 * 
	 * */
	public void getResourceTypes()throws Exception{
		List<Map<String,Object>> items = hcgdManageService.getResourceTypes(new HashMap<String, Object>());
		this.printString(JSONArray.fromObject(items).toString(), null);
	}
	
	/*
	 * 	查询顶级类型数据
	 * 
	 * */
	public void getTopResTypes()throws Exception{
		List<Map<String,Object>> items = hcgdManageService.getTopTypes(new HashMap<String, Object>());
		this.printString(JSONArray.fromObject(items).toString(), null);
	}
	
	/*
	 * 	查询资源数据
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public void getResourceData()throws Exception{
		JSONObject thisObject = new JSONObject();
		HashMap<String,Object> mapObject = hcgdManageService.getResourceType(gdTaskMain.getResourceType());
		if(mapObject!=null){
			JdbcTemplate template = hcgdManageService.getTemplate();
			String sql = "select "+mapObject.get("code_column").toString()+" as resource_code,"+mapObject.get("value_column").toString()+" as resource_name from "+mapObject.get("rms_table_name").toString()+" where 1 = 1 ";
			if(gdTaskMain.getResourceName()!=null && !"".equals(gdTaskMain.getResourceName())){
				sql+=" and "+mapObject.get("value_column").toString()+" like '%"+gdTaskMain.getResourceName()+"%'";
			}	
			if(gdTaskMain.getReceiveRegionName()!=null && !"".equals(gdTaskMain.getReceiveRegionName())){
				sql+=" and "+mapObject.get("region_column").toString()+" like '%"+gdTaskMain.getReceiveRegionName()+"%'";
			}
			Integer totalCount = template.queryForInt("select count(1) from ("+sql+")t");
			if(totalCount>50){
				sql+=" limit 0,50";
			}	
			List<Map<String,Object>> results = template.queryForList(sql);
			thisObject.put("total",results.size());
			thisObject.put("items",results);
		}	
		this.printString(thisObject.toString(),null);
	}
	
	/*
	 * 	查询核查项
	 * 
	 * */
	public void getChecks()throws Exception{
		JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();	
		JSONArray nodeArray = new JSONArray();	
		if(this.getRequest().getParameter("resourceType")==null || "".equals(this.getRequest().getParameter("resourceType"))){
			JSONObject uniqueNode = new JSONObject();
			uniqueNode.put("id",-1);
			uniqueNode.put("text","尚未配置此资源所属的核查项");
			uniqueNode.put("leaf",true);
			nodeArray.add(uniqueNode);
		}else{
			String resourceType = this.getRequest().getParameter("resourceType");
			List<Map<String,Object>> typeObjects = jdbcTemplate.queryForList("select * from config_resource_table where resource_cn_name = '"+resourceType+"'");
			if(typeObjects.size()>0){
				Map<String,Object> typeObject = typeObjects.get(0);
				String resourceEnName = typeObject.get("resource_en_name").toString();
				/*查询核查项*/
				List<Map<String,Object>> checks = jdbcTemplate.queryForList("select * from config_checks where belong_dimension = '"+resourceEnName+"'");
				if(checks.size()>0){
					for(int j=0;j<checks.size();j++){
						Map<String,Object> checkObject = checks.get(j);
						JSONObject checkNode = new JSONObject();
						checkNode.put("id",checkObject.get("id").toString());
						checkNode.put("text",checkObject.get("item_desc").toString());
						checkNode.put("leaf",true);
						checkNode.put("checked",true);
						nodeArray.add(checkNode);
					}										
				}		
			}
		}	
		this.printString(nodeArray.toString(),null);
	}
	
	/*获取资源树*/
	public void getResourceTree()throws Exception{
		JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
		JSONArray nodeArray = new JSONArray();
		List<Map<String,Object>> topNodes = jdbcTemplate.queryForList("select resource_en_name as resourceType,resource_cn_name as resourceName from config_resource_table where parent_type = '0'");
		for(int a=0;a<topNodes.size();a++){
			Map<String,Object> topNode = topNodes.get(a);	
			JSONObject jsonNode = new JSONObject();
			jsonNode.put("id",topNode.get("resourceType").toString());
			jsonNode.put("text",topNode.get("resourceName").toString());
			/*循环获取次级资源*/
			Boolean isLeaf = true;
			String $typeName = topNode.get("resourceName").toString();
			while(isHaveSubs($typeName)){
				List<Map<String,Object>> subTypes = findSubNodes($typeName);
				for(int j=0;j<subTypes.size();j++){
					Map<String,Object> subType = subTypes.get(j);
					$typeName = subType.get("resourceName").toString();				
				}		
				isLeaf = false;
			}
			jsonNode.put("leaf",isLeaf);			
			nodeArray.add(jsonNode);
		}	
		this.printString(nodeArray.toString(),null);
	}
	
	public List<Map<String,Object>> findSubNodes(String parentType){
		JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
		List<Map<String,Object>> subNodes = jdbcTemplate.queryForList("select resource_en_name as resourceType,resource_cn_name as resourceName from config_resource_table where parent_type = '"+parentType+"'");
		return subNodes;
	}
	
	public Boolean isHaveSubs(String parentType){
		JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
		List<Map<String,Object>> subNodes = jdbcTemplate.queryForList("select resource_en_name as resourceType,resource_cn_name as resourceName from config_resource_table where parent_type = '"+parentType+"'");
		if(subNodes.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	
	/*
	 * 	查询地市区县树
	 * 
	 * */
	public void getOrganizations()throws Exception{
		JSONArray nodeArray = new JSONArray();		
		List<Map<String,Object>> cityList = hcgdManageService.getCitys(new HashMap());
		if(cityList.size()>0){
			for(int i=0;i<cityList.size();i++){
				Map<String,Object> cityObject = cityList.get(i);
				JSONObject cityNode = new JSONObject();
				cityNode.put("id",cityObject.get("resNum").toString());
				cityNode.put("text",cityObject.get("zhLabel").toString());
				List<Map<String,Object>> regionList = hcgdManageService.getRegionsOfCity(Integer.parseInt(cityObject.get("resNum").toString()));
				if(regionList.size()>0){
					JSONArray regionArray = new JSONArray();
					for(int j=0;j<regionList.size();j++){
						Map<String,Object> regionObject = regionList.get(j);
						JSONObject regionNode = new JSONObject();
						regionNode.put("id",regionObject.get("resNum").toString());
						regionNode.put("text",regionObject.get("zhLabel").toString());
						regionNode.put("leaf",true);
						regionArray.add(regionNode);						
					}
					cityNode.put("children",regionArray);					
				}else{
					cityNode.put("leaf",true);
				}
				nodeArray.add(cityNode);
			}
		}
		this.printString(nodeArray.toString(),null);
	}
	
	/*-----------------------------------------------------------------------------------------------------*/
	
	@Override
	public GdTaskMain getModel(){
		return gdTaskMain;
	}
	
	public void printString(String string, String contentType) throws Exception {
		this.getResponse().setHeader("Cache-Control", "no-cache");
		this.getResponse().setContentType(contentType);
		this.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter pw = this.getResponse().getWriter();
		pw.write(string);
		pw.close();
	}	
	
	private Integer start;
	
	private Integer limit;
	
	public IhcgdManageService getHcgdManageService() {
		return hcgdManageService;
	}

	public void setHcgdManageService(IhcgdManageService hcgdManageService) {
		this.hcgdManageService = hcgdManageService;
	}

	public GdTaskMain getGdTaskMain() {
		return gdTaskMain;
	}

	public void setGdTaskMain(GdTaskMain gdTaskMain) {
		this.gdTaskMain = gdTaskMain;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}
	
	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
