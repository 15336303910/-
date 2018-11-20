package manage.V2.beijing.resource.service;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import base.database.DataBase;
import manage.V2.beijing.resource.pojo.ZSLResourcePointInfoBean;
import manage.V2.beijing.resource.service.impl.IresPortService;

public class ResPortService extends DataBase implements IresPortService{
	private JdbcTemplate jdbcTemplate;
	private static String resPortGrid ="resport.getResportGrid";
	private static String resPortCount = "resport.getResportCount";
	private static String getResPort ="resport.getResPort";
	private static String updateResPort = "resport.updateResPort";
	private static String insertResPort = "resport.insertResPort";
	private static String delResPort = "resport.delResPort";
	
	/**
	 * 得到分页数据
	 * @param object
	 * @return
	 */
	public List<ZSLResourcePointInfoBean> getResPortGrid(ZSLResourcePointInfoBean object){
		List<ZSLResourcePointInfoBean>  list = getObjects(resPortGrid, object);
		return list;
	}
	
	/**
	 * 得到所有的资源点
	 * @param obj
	 * @return
	 */
	public List<ZSLResourcePointInfoBean> getResPortList(ZSLResourcePointInfoBean obj){
		List<ZSLResourcePointInfoBean>  list = getObjects(getResPort, obj);
		return list;
	}
	
	/**
	 * 得到所有的资源点数
	 * @param obj
	 * @return
	 */
	public int getResPortCount(ZSLResourcePointInfoBean obj){
		return getCount(resPortCount, obj);
	}
	
	/**
	 * 修改资源点
	 * @param obj
	 * @return
	 */
	public int updateResPort(ZSLResourcePointInfoBean obj){
		return this.update(updateResPort, obj);
	}
	
	/**
	 * 添加资源点
	 * @param obj
	 * @return
	 */
	public int insertResPort(ZSLResourcePointInfoBean obj){
		return (Integer) this.insert(insertResPort, obj);
	}
	
	
	/**
	 * 删除资源点
	 * @param ids
	 */
	public void delResPort(String ids){
		this.delete(delResPort, ids);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
