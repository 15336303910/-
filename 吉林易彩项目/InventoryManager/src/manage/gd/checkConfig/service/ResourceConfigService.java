package manage.gd.checkConfig.service;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import manage.gd.checkConfig.pojo.RmsResource;
import manage.gd.checkConfig.service.impl.IResourceConfigService;
import base.database.DataBase;
public class ResourceConfigService extends DataBase implements IResourceConfigService{
	
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("rawtypes")
	@Override
	public RmsResource getResourceList(RmsResource rmsResource){
		List configItems = getObjects("resourceConfig.getResConfigs",rmsResource);
		int total = getCount("resourceConfig.getResourcesCount",rmsResource);
		RmsResource thisResource = new RmsResource();
		thisResource.setTotal(total);
		thisResource.setItems(configItems);
		return thisResource;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public RmsResource getResourceObject(RmsResource rmsResource){
		List configItems = getObjects("resourceConfig.getResConfigs",rmsResource);
		if(configItems.size()>0){
			return (RmsResource)configItems.get(0);
		}else{
			return null;
		}
	}

	@Override
	public int insertModel(RmsResource rmsResource){
		return ((Integer)insert("resourceConfig.insertItem",rmsResource)).intValue();
	}

	@Override
	public int updateResourceObject(RmsResource rmsResource){
		List<Map<String,Object>> rmsItems = jdbcTemplate.queryForList("select * from config_resource_table where id = "+rmsResource.getId());
		if(rmsItems.size()>0){
			Map<String,Object> originObject = rmsItems.get(0);
			/*资源中文标识及关联*/
			String originChName = originObject.get("resource_cn_name").toString();
			jdbcTemplate.execute("update config_resource_table set parent_type = '"+rmsResource.getParentType()+"' where parent_type = '"+originChName+"'");
			/*资源英文标识及核查字段关联*/
			String originEnName = originObject.get("resource_en_name").toString();
			jdbcTemplate.execute("update config_checks set belong_dimension = '"+rmsResource.getResourceEnName()+"' where belong_dimension = '"+originEnName+"'");
		}		
		int rows = update("resourceConfig.updateItem",rmsResource);
		return rows;
	}

	@Override
	public void deleteResourceObject(RmsResource rmsResource){
		Integer rmsCode = rmsResource.getId();		
		delete("resourceConfig.deleteItem",rmsResource);
		/*删除资源下已配置的核查项*/
		List<Map<String,Object>> rmsItems = jdbcTemplate.queryForList("select * from config_resource_table where id = "+rmsCode);
		if(rmsItems.size()>0){
			String resEnName = rmsItems.get(0).get("resource_en_name").toString();
			jdbcTemplate.execute("delete from config_checks where belong_dimension = '"+resEnName+"'");
		}
	}
	
	@Override
	public JdbcTemplate getTemplate(){
		return this.jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
