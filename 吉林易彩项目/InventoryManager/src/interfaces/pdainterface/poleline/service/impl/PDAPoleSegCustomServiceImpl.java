package interfaces.pdainterface.poleline.service.impl;

import java.util.List;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.TextUtil;
import interfaces.pdainterface.poleline.service.PDAPoleSegCustomService;
import manage.poleline.pojo.PoleSegCustomInfoBean;

public class PDAPoleSegCustomServiceImpl extends DataBase  implements PDAPoleSegCustomService{

	@SuppressWarnings("unchecked")
	public List<PoleSegCustomInfoBean> getPoleSegCustom(PoleSegCustomInfoBean poleSeg) throws DataAccessException {
		if(TextUtil.isNotNull(poleSeg.getInt_id())){
//			pole.setLate(null);
//			pole.setLats(null);
//			pole.setLatitude(null);
//			pole.setLone(null);
//			pole.setLats(null);
//			pole.setLongitude(null);
		}
		if(TextUtil.isNotNull(poleSeg.getZh_label())){
			String poleName = poleSeg.getZh_label().trim();
			if(poleName.contains(" ")){
				poleName = poleName .replaceAll(" +", "%");
			}
			poleSeg.setZh_label("%"+poleName+"%");
		}
	    return getObjects("pdapoleSeg.getPoleSegCustom", poleSeg);
	}

	@Override
	public Integer insertPoleSegCustom(PoleSegCustomInfoBean poleSeg) throws DataAccessException {
		 return ((Integer)insert("pdapoleSeg.insertPoleSegCustom", poleSeg));
	}

	@Override
	public Integer updatePoleSegCustom(PoleSegCustomInfoBean poleSeg) throws DataAccessException {
		return Integer.valueOf(update("pdapoleSeg.updatePoleSegCustom", poleSeg));
	}

	@Override
	public Integer deletePoleSegCustom(PoleSegCustomInfoBean poleSeg) throws DataAccessException {
		return Integer.valueOf(update("pdapoleSeg.deletePoleSegCustom", poleSeg));
	}

	

 
}
