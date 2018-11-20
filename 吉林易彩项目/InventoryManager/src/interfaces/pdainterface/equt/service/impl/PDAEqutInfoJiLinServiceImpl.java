package interfaces.pdainterface.equt.service.impl;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import interfaces.pdainterface.equt.pojo.EqutJiLinInfoBean;
import interfaces.pdainterface.equt.service.PDAEqutInfoJiLinService;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PDAEqutInfoJiLinServiceImpl extends DataBase implements PDAEqutInfoJiLinService {

	private JdbcTemplate jdbcTemplate;

	/**
	 * 光交接箱列表
	 * @param equt
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<EqutJiLinInfoBean> getEqutList(EqutJiLinInfoBean equt) throws DataAccessException {
		return getObjects("pdaequt.getEqutListJiLin", equt);
	}

	/**
	 * 新增光交接箱
	 * @param equt
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public int insertEqut(EqutJiLinInfoBean equt) throws DataAccessException {
		return ((Integer) insert("pdaequt.insertEqutJiLin", equt)).intValue();
	}

	/**
	 * 获取光交接箱详情
	 * @param equt
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public EqutJiLinInfoBean getEqut(EqutJiLinInfoBean equt) throws DataAccessException {
		return (EqutJiLinInfoBean)getObject("pdaequt.getEqutJiLin", equt);
	}

	/**
	 * 删除光交接箱
	 * @param equt
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public int deleteEqut(EqutJiLinInfoBean equt) throws DataAccessException {
		delete("pdaequt.deleteEqutJiLin", equt);
		return 0;
	}

	/**
	 * 更新光交接箱
	 * @param equt
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public int updateEqut(EqutJiLinInfoBean equt) throws DataAccessException {
		update("pdaequt.updateEqutJiLin", equt);
		return 0;
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	

}
