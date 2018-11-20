package manage.gd.checkConfig.service.impl;
import org.springframework.jdbc.core.JdbcTemplate;

import manage.gd.checkConfig.pojo.RmsResource;
public interface IResourceConfigService{
	
	/*获取SqlMapClientTemplate*/
	public JdbcTemplate getTemplate();

	/*分页查询*/
	public RmsResource getResourceList(RmsResource rmsResource);
	
	/*单条记录查询*/
	public RmsResource getResourceObject(RmsResource rmsResource);
	
	/*插入记录*/
	public int insertModel(RmsResource rmsResource);
	
	/*修改记录*/
	public int updateResourceObject(RmsResource rmsResource);
	
	/*删除记录*/
	public void deleteResourceObject(RmsResource rmsResource);
}
