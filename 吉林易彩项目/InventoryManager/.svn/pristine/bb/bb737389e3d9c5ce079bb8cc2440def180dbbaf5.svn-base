package interfaces.pdainterface.generator.service.impl;

import java.util.List;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.TextUtil;
import interfaces.pdainterface.generator.service.IPDASiteBaseService;
import manage.generator.pojo.SiteBaseInfoBean;

public class PDASiteBaseServiceImpl extends DataBase implements IPDASiteBaseService{
	private static final String INSERT_SITEBASE = "pdagenerator.insertSiteBase";
	private static final String GET_SITEBASE = "pdagenerator.getSiteBase";
	private static final String UPDATE_SITEBASE = "pdagenerator.updateSiteBase";
	private static final String DELETE_SITEBASE = "pdagenerator.deleteSiteBase";
	
	@Override
	public int insertSiteBase(SiteBaseInfoBean siteBase) throws DataAccessException {
	    return ((Integer)insert(INSERT_SITEBASE, siteBase)).intValue();

	}

	@Override
	public int updateSiteBase(SiteBaseInfoBean siteBase) throws DataAccessException {
		return update(UPDATE_SITEBASE, siteBase);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SiteBaseInfoBean> getSiteBase(SiteBaseInfoBean siteBase) throws DataAccessException {
		if(TextUtil.isNotNull(siteBase.getZh_label())){
			String siteName = siteBase.getZh_label().trim();
			if(siteName.contains(" ")){
				siteName= siteName.replaceAll(" +", "%");
			}
			siteBase.setZh_label("%"+siteName+"%");
		}
		return getObjects(GET_SITEBASE, siteBase);
	}

	@Override
	public int deleteSiteBase(SiteBaseInfoBean siteBase) throws DataAccessException {
		return update(DELETE_SITEBASE, siteBase);
	}

}
