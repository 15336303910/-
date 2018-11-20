package interfaces.pdainterface.supportingRing.service;

import java.util.List;
import base.exceptions.DataAccessException;
import interfaces.pdainterface.supportingRing.pojo.SupportingRingInfoBean;

public interface IPDASupportingRingService {
	
	 /**
	  * 增加动环配套
	 * @param supportingRingInfoBean
	 * @return
	 * @throws DataAccessException
	 */
	public int insertSupportingRing(SupportingRingInfoBean supportingRingInfoBean) throws DataAccessException;
	 
	/**
	 * 获取动环配套信息
	 * @param ringInfoBean
	 * @return
	 * @throws DataAccessException
	 */
	public List<SupportingRingInfoBean> getSupportingRing(SupportingRingInfoBean ringInfoBean) throws DataAccessException;
	
	/**
	 * 更新动环配套信息
	 * @param ringInfoBean
	 * @return
	 * @throws DataAccessException
	 */
	public int updateSupportingRing(SupportingRingInfoBean ringInfoBean) throws DataAccessException;
	
	/**
	 * 删除动环配套信息
	 * @param ringInfoBean
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteSupportingRing(SupportingRingInfoBean ringInfoBean) throws DataAccessException;

	/**
	 * 更改删除标记状态
	 * @param ringInfoBean
	 * @return
	 * @throws DataAccessException
	 */
	public int updateDeleteFlag(SupportingRingInfoBean ringInfoBean) throws DataAccessException;

}
