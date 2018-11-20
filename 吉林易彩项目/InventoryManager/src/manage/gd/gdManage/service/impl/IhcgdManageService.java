package manage.gd.gdManage.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import manage.gd.gdManage.pojo.GdTaskMain;
public interface IhcgdManageService{

	/*
	 * 	获取SqlMapClientTemplate
	 * 
	 * */
	public JdbcTemplate getTemplate();
	
	/*
	 * 	分页查询
	 * 
	 * */
	public GdTaskMain getTasksList(GdTaskMain gdTaskMain);
	
	/*
	 * 	资源类型
	 * 
	 * */
	public List<Map<String,Object>> getResourceTypes(HashMap<String,Object> mapObject);
	
	/*
	 * 	顶级资源类型
	 * 
	 * */
	public List<Map<String,Object>> getTopTypes(HashMap<String,Object> mapObject);
	
	/*
	 * 	资源数据
	 * 
	 * */
	public HashMap<String,Object> getResourceType(String cnName);
	
	/*
	 * 	查询地市列表
	 * 
	 * */
	public List<Map<String,Object>> getCitys(HashMap<String,Object> mapObject);
	
	/*
	 * 	查询地市所属的区县
	 * 
	 * */
	public List<Map<String,Object>> getRegionsOfCity(Integer cityId);
}
