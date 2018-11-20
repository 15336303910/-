package interfaces.pdainterface.supportingRing.service.impl;

import java.util.List;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.TextUtil;
import interfaces.pdainterface.supportingRing.pojo.SupportingRingInfoBean;
import interfaces.pdainterface.supportingRing.service.IPDASupportingRingService;

public class PDASupportingRingServiceImpl extends DataBase implements IPDASupportingRingService{
	private static final String INSERT_RING = "pdaSupportingRing.insertSupportingRing";
	private static final String GET_RING = "pdaSupportingRing.getSupportingRing";
	private static final String UPDATE_RING = "pdaSupportingRing.updateSupportingRing";
	private static final String DELETE_RING = "pdaSupportingRing.deleteSupportingRing";
	private static final String UPDATE_DELETE_FLAG = "pdaSupportingRing.updateDeleteFlag";

	@Override
	public int insertSupportingRing(SupportingRingInfoBean supportingRingInfoBean) throws DataAccessException {
	    return ((Integer)insert(INSERT_RING, supportingRingInfoBean)).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SupportingRingInfoBean> getSupportingRing(SupportingRingInfoBean ringInfoBean)
			throws DataAccessException {
		if(TextUtil.isNotNull(ringInfoBean.getZh_label())){
			String ringName = ringInfoBean.getZh_label().trim();
			if(ringName.contains(" ")){
				ringName= ringName.replaceAll(" +", "%");
			}
			ringInfoBean.setZh_label("%"+ringName+"%");
		}
	    return getObjects(GET_RING, ringInfoBean);
		
	}

	@Override
	public int updateSupportingRing(SupportingRingInfoBean ringInfoBean) throws DataAccessException {
		return update(UPDATE_RING, ringInfoBean);
	}

	@Override
	public int deleteSupportingRing(SupportingRingInfoBean ringInfoBean) throws DataAccessException {
		return delete(DELETE_RING, ringInfoBean);
	}

	@Override
	public int updateDeleteFlag(SupportingRingInfoBean ringInfoBean) throws DataAccessException {
		return update(UPDATE_DELETE_FLAG, ringInfoBean);
	}

}
