package interfaces.irmsInterface.interfaces.station.service.impl;

import java.util.List;

import manage.equt.pojo.EqutInfoBean;
import manage.generator.pojo.GeneratorInfoBean;

public interface IirmsQueryService {

	
	/**
	 * 根据站点ID得到机房信息
	 * @param siteId
	 * @return
	 */
	public List<GeneratorInfoBean> getGeneratorBySite(String siteId);
	
	
	/**
	 * 得到机架信息
	 * @param generId
	 * @return
	 */
	public List<EqutInfoBean> getEqutList(String generId);
}
