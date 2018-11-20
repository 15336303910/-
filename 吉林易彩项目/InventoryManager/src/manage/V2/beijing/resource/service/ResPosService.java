package manage.V2.beijing.resource.service;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import manage.V2.beijing.resource.pojo.ZSLPOSInfoBean;
import manage.V2.beijing.resource.service.impl.IresPosService;
import base.database.DataBase;

public class ResPosService extends DataBase implements IresPosService{
	private JdbcTemplate jdbcTemplate;
	private static String getResposGrid ="respos.getResposGrid";
	private static String getResposCount ="respos.getResposCount";
	private static String getResPos = "respos.getResPos";
	private static String updateRespos = "respos.updateRespos";
	private static String insertResPos = "respos.insertResPos";
	private static String delResPos = "respos.delResPos";
	
	
	/**
	 * 得到分页数据
	 * @param obj
	 * @return
	 */
	public List<ZSLPOSInfoBean> getResPosGrid(ZSLPOSInfoBean obj){
		List<ZSLPOSInfoBean> list = getObjects(getResposGrid, obj);
		return list;
	}
	
	/**
	 * 得到符合要求的分光器
	 * @param obj
	 * @return
	 */
	public List<ZSLPOSInfoBean> getResPosList(ZSLPOSInfoBean obj){
		List<ZSLPOSInfoBean> list = getObjects(getResPos, obj);
		return list;
	}
	
	/**
	 * 修改分光器
	 * @param obj
	 * @return
	 */
	public int updateResPos(ZSLPOSInfoBean obj){
		return this.update(updateRespos, obj);
	}
	
	/**
	 * 添加分光器
	 * @param obj
	 * @return
	 */
	public int insertResPos(ZSLPOSInfoBean obj){
		return (Integer) this.insert(insertResPos, obj);
	}
	
	/**
	 * 删除分光器
	 * @param obj
	 */
	public void delResPos(ZSLPOSInfoBean obj){
		this.delete(delResPos, obj);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
