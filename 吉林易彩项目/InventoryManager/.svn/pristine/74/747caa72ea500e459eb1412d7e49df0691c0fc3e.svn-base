package interfaces.pdainterface.equt.service;

import base.exceptions.DataAccessException;
import interfaces.pdainterface.equt.pojo.EqutJiLinInfoBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface PDAEqutInfoJiLinService {

	/**
	 * 光交接箱列表
	 * @param equt
	 * @return
	 * @throws DataAccessException
	 */
	public List<EqutJiLinInfoBean> getEqutList(EqutJiLinInfoBean equt) throws DataAccessException;

	/**
     * 新增光交接箱
	 * @param equt
     * @return
     * @throws DataAccessException
	 */
	public int insertEqut(EqutJiLinInfoBean equt) throws DataAccessException;

	/**
     * 更新光交接箱
	 * @param equt
     * @return
     * @throws DataAccessException
	 */
	public int updateEqut(EqutJiLinInfoBean equt) throws DataAccessException;

	/**
	 * 获取光交接箱详情
	 * @param equt
	 * @return
	 * @throws DataAccessException
	 */
	public EqutJiLinInfoBean getEqut(EqutJiLinInfoBean equt) throws DataAccessException;

	/**
	 * 删除光交接箱详情
	 * @param equt
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteEqut(EqutJiLinInfoBean equt) throws DataAccessException;

	public JdbcTemplate getJdbcTemplate();

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate);


}
