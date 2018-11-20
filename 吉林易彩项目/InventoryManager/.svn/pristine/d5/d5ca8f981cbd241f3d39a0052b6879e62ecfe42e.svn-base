package manage.gd.checkConfig.action;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import net.sf.json.JSONObject;
import manage.gd.checkConfig.pojo.RmsResource;
import manage.gd.checkConfig.service.impl.IResourceConfigService;
import base.web.PaginationAction;
import com.opensymphony.xwork2.ModelDriven;
public class ResourceConfigAction extends PaginationAction implements ModelDriven<RmsResource>{

	private static final long serialVersionUID = 2920876358839052415L;
	private RmsResource resObject = new RmsResource();
	private IResourceConfigService resourceConfigService;
	private Integer start;
	private Integer limit;
	
	/*
	 * 	分页查询
	 * 
	 * */
	public void getResourcesList()throws Exception{
		this.resObject = resourceConfigService.getResourceList(resObject);
		JSONObject thisObject = new JSONObject();
		thisObject.put("total",resObject.getTotal());
		thisObject.put("items",resObject.getItems());
		this.printString(thisObject.toString(), null);
	}
	
	/*
	 * 	查询单条查询
	 * 
	 * */
	public void getResourceObject()throws Exception{
		this.resObject.setStart(null);
		this.resObject.setLimit(null);
		this.resObject = resourceConfigService.getResourceObject(resObject);
		JSONObject thisObject = new JSONObject();
		thisObject.put("success",true);
		thisObject.put("resourceObject",resObject);
		this.printString(thisObject.toString(), null);
	}
	
	/*
	 * 	更新记录
	 * 
	 * */
	public void editModel()throws Exception{		
		if(resObject.getParentType()==null || "".equals(resObject.getParentType())){
			resObject.setParentType("0");
			resObject.setRelateColumn("0");
			resObject.setResourceLevel(1);
		}else{
			resObject.setResourceLevel(2);
		}
		if(this.resObject.getId()!=null){
			try{
				resourceConfigService.updateResourceObject(resObject);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			resourceConfigService.insertModel(resObject);
		}
		this.printString("{success:true}",null);
	}
	
	/*
	 * 	删除记录
	 * 
	 * */
	public void deleteObjects()throws Exception{
		String[] deletedCodes  = this.getRequest().getParameter("deletedCodes").split(",");
		for(int i=0;i<deletedCodes.length;i++){
			resObject.setId(Integer.parseInt(deletedCodes[i]));
			resourceConfigService.deleteResourceObject(resObject);
		}
		this.printString("{success:true,deleteMsg:'已成功删除资源配置.'}",null);
	}
	
	/*
	 * 	验证资源是否已经存在
	 * 
	 * */
	public void validResExist()throws Exception{
		String resourceEnName = this.getRequest().getParameter("resourceEnName");
		String resourceChName = this.getRequest().getParameter("resourceChName");
		String resourceTable = this.getRequest().getParameter("resourceTable");
		JdbcTemplate jdbcTemplate = resourceConfigService.getTemplate();
		Integer isExist = jdbcTemplate.queryForInt("select count(1) from config_resource_table where resource_en_name = '"+resourceEnName+"' or resource_cn_name = '"+resourceChName+"' or rms_table_name = '"+resourceTable+"'");
		if(isExist>0){
			this.printString("{success:false}",null);
		}else{
			this.printString("{success:true}",null);
		}	
	}
	
	/*
	 * 	验证资源配置是否正确
	 * 
	 * */
	public void validResExpress()throws Exception{
		Boolean isValid = true;
		try{
			String resourceTable = this.getRequest().getParameter("resourceTable");
			String codeColumn = this.getRequest().getParameter("codeColumn");
			String valueColumn = this.getRequest().getParameter("valueColumn");
			String regionColumn = this.getRequest().getParameter("regionColumn");
			JdbcTemplate jdbcTemplate = resourceConfigService.getTemplate();
			List<Map<String,Object>> maps = jdbcTemplate.queryForList("select "+codeColumn+","+valueColumn+" from "+resourceTable+" where "+regionColumn+" = '-1'");
			System.out.println(maps.size());
		}catch(Exception e){
			isValid = false;
		}finally{
			this.printString("{success:"+isValid+"}",null);
		}
	}
	
	@Override
	public RmsResource getModel() {
		return resObject;
	}

	public void printString(String string, String contentType) throws Exception {
		this.getResponse().setHeader("Cache-Control", "no-cache");
		this.getResponse().setContentType(contentType);
		this.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter pw = this.getResponse().getWriter();
		pw.write(string);
		pw.close();
	}

	public RmsResource getResObject() {
		return resObject;
	}

	public void setResObject(RmsResource resObject) {
		this.resObject = resObject;
	}

	public IResourceConfigService getResourceConfigService() {
		return resourceConfigService;
	}

	public void setResourceConfigService(
			IResourceConfigService resourceConfigService) {
		this.resourceConfigService = resourceConfigService;
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
