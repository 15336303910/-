package interfaces.pdainterface.generator.service;

import java.util.List;

import base.exceptions.DataAccessException;
import manage.generator.pojo.SiteBaseInfoBean;

public interface IPDASiteBaseService {
	
	/**
	 * 增加基站
	 * @param siteBase
	 * @return
	 * @throws DataAccessException
	 */
	public int insertSiteBase(SiteBaseInfoBean siteBase) throws DataAccessException;

	/**
	 * 更新基站
	 * @param siteBase
	 * @return
	 * @throws DataAccessException
	 */
	public int updateSiteBase(SiteBaseInfoBean siteBase) throws DataAccessException;

	/**
	 * 获取基站信息
	 * @param siteBase
	 * @return
	 * @throws DataAccessException
	 */
	public List<SiteBaseInfoBean> getSiteBase(SiteBaseInfoBean siteBase) throws DataAccessException;

	/**
	 * 删除基站
	 * @param siteBase
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteSiteBase(SiteBaseInfoBean siteBase) throws DataAccessException;

}
