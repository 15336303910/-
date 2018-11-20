package manage.gd.checkConfig.action;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ModelDriven;

import manage.gd.checkConfig.pojo.CheckItem;
import manage.gd.checkConfig.service.impl.IcheckItemService;
import base.web.PaginationAction;
public class CheckConfigAction extends PaginationAction implements ModelDriven<CheckItem>{
	
	private CheckItem checkItem = new CheckItem();	
	private IcheckItemService  checkItemConfigService;
	private Integer start;
	private Integer limit;

	/*
	 * 	分页查询
	 * 
	 * */
	public void getChecksList()throws Exception{
		if(checkItem.getBelongDimension()!=null && !"".equals(checkItem.getBelongDimension())){
			String dimensionName = checkItem.getBelongDimension();
			JdbcTemplate jdbcTemplate = checkItemConfigService.getTemplate();
			List<Map<String,Object>> reses = jdbcTemplate.queryForList("select * from config_resource_table where resource_cn_name like '%"+dimensionName+"%'");
			if(reses.size()==0){
				checkItem.setBelongDimension(null);
			}else{
				checkItem.setBelongDimension(reses.get(0).get("resource_en_name").toString());
			}		
		}	
		this.checkItem = checkItemConfigService.getChecksList(this.checkItem);
		JSONObject thisObject = new JSONObject();
		thisObject.put("total",checkItem.getTotal());
		thisObject.put("checkItems",checkItem.getCheckItems());
		this.printString(thisObject.toString(), null);
	}
	
	/*
	 * 	查询单条查询
	 * 
	 * */
	public void getCheckObject()throws Exception{
		this.checkItem.setStart(null);
		this.checkItem.setLimit(null);
		this.checkItem = checkItemConfigService.getChecksList(this.checkItem);
		JSONObject thisObject = new JSONObject();
		thisObject.put("success",true);
		if(this.checkItem.getCheckItems().size()>0){
			thisObject.put("checkObject",checkItem.getCheckItems().get(0));
		}
		this.printString(thisObject.toString(), null);
	}
	
	/*
	 * 	插入记录
	 * 
	 * */
	public void insertModel()throws Exception{
		if(this.checkItem.getId()!=null){
			try{
				checkItemConfigService.updateCheckObject(this.checkItem);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			checkItemConfigService.insertModel(this.checkItem);
		}
		this.printString("{success:true}",null);
	}
	
	/*
	 * 	更新记录
	 * 
	 * */
	public CheckItem updateCheckObject(){
		int rows = checkItemConfigService.updateCheckObject(this.checkItem);
		checkItem.setResultNumber(rows);
		return checkItem;
	}
	
	/*
	 * 	删除记录
	 * 
	 * */
	public void deleteCheckObject()throws Exception{
		checkItemConfigService.deleteCheckObject(this.checkItem);
		this.printString("{success:true,deleteMsg:'已成功删除核查信息.'}",null);
	}
	
	/*
	 * 	获取编辑类型
	 * 
	 * */
	public void findEditTypes()throws Exception{
		JdbcTemplate jdbcTemplate = checkItemConfigService.getTemplate();
		List<Map<String,Object>> editTypes = jdbcTemplate.queryForList("select distinct(data_type) as editCode,data_type as editName from config_edit_type");
		this.printString(JSONArray.fromObject(editTypes).toString(),null);
	}

	@Override
	public CheckItem getModel(){
		return checkItem;
	}

	public CheckItem getCheckItem() {
		return checkItem;
	}

	public void setCheckItem(CheckItem checkItem) {
		this.checkItem = checkItem;
	}
	
	public IcheckItemService getCheckItemConfigService() {
		return checkItemConfigService;
	}
	
	public void setCheckItemConfigService(IcheckItemService checkItemConfigService) {
		this.checkItemConfigService = checkItemConfigService;
	}
	
	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}
	
	public void printString(String string, String contentType) throws Exception {
		this.getResponse().setHeader("Cache-Control", "no-cache");
		this.getResponse().setContentType(contentType);
		this.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter pw = this.getResponse().getWriter();
		pw.write(string);
		pw.close();
	}
	
	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
