package interfaces.pdainterface.poleline.service;

import java.util.List;

import base.exceptions.DataAccessException;
import manage.poleline.pojo.PoleCustomInfoBean;
import manage.poleline.pojo.PoleSegCustomInfoBean;

public abstract interface PDAPoleSegCustomService {

	/**
	 * 查询所有杆路段
	 * @param pole
	 * @return
	 * @throws DataAccessException
	 */
	public List<PoleSegCustomInfoBean> getPoleSegCustom(PoleSegCustomInfoBean poleSeg) throws DataAccessException;

	/**
	 * 增加杆路段
	 * @param pole
	 * @return
	 * @throws DataAccessException
	 */
	public Integer insertPoleSegCustom(PoleSegCustomInfoBean poleSeg) throws DataAccessException;

	/**
	 * 更新杆路段
	 * @param pole
	 * @return
	 * @throws DataAccessException
	 */
	public Integer updatePoleSegCustom(PoleSegCustomInfoBean poleSeg) throws DataAccessException;

	/**
	 * 删除杆路段
	 * @param pole
	 * @return
	 * @throws DataAccessException
	 */
	public Integer deletePoleSegCustom(PoleSegCustomInfoBean poleSeg) throws DataAccessException;
}
