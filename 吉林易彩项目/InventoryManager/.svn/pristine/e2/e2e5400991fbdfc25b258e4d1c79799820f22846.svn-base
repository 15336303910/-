package manage.gd.gdManage.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import manage.gd.gdManage.pojo.GdTaskMain;
import manage.gd.gdManage.service.impl.IhcgdManageService;
import base.database.DataBase;
public class HcgdManageService extends DataBase implements IhcgdManageService {
	
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public GdTaskMain getTasksList(GdTaskMain gdTaskMain) {
		 List taskItems = getObjects("hcgdManage.getTasks",gdTaskMain);
	     int total = getCount("hcgdManage.getTaskCount",gdTaskMain);
	     GdTaskMain thisObject = new GdTaskMain();
	     thisObject.setItems(taskItems);
	     thisObject.setTotal(Integer.valueOf(total));
	     return thisObject;
	}
	
	@Override
	public List<Map<String,Object>> getResourceTypes(HashMap<String,Object> mapObject){
		List typeItems = getObjects("hcgdManage.getResourceTypes",mapObject);
		return typeItems;
	}
	
	@Override
	public List<Map<String,Object>> getTopTypes(HashMap<String,Object> mapObject){
		List typeItems = getObjects("hcgdManage.getTopTypes",mapObject);
		return typeItems;
	}
	
	@Override
	public HashMap<String,Object> getResourceType(String cnName){
		List resourceItems = getObjects("hcgdManage.getResourceTypeObject",cnName);
		if(resourceItems.size()>0){
			return (HashMap<String,Object>)resourceItems.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public JdbcTemplate getTemplate(){
		return this.jdbcTemplate;
	}
	
	@Override
	public List<Map<String,Object>> getCitys(HashMap<String,Object> mapObject){
		List cityItems = getObjects("hcgdManage.getCitys",mapObject);
		return cityItems;
	}
	
	@Override
	public List<Map<String,Object>> getRegionsOfCity(Integer cityId){
		List regionItems = getObjects("hcgdManage.getRegionsOfCity",cityId);
		return regionItems;
	}
}
