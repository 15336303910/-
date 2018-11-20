package interfaces.pdainterface.poleline.service.impl;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.TextUtil;
import interfaces.pdainterface.poleline.service.PDAPolelineCustomService;
import java.util.List;
import manage.poleline.pojo.PoleCustomInfoBean;

public class PDAPolelineCustomServiceImpl extends DataBase  implements PDAPolelineCustomService{

	@SuppressWarnings("unchecked")
	@Override
	public List<PoleCustomInfoBean> getPoleCustom(PoleCustomInfoBean pole) throws DataAccessException {
		if(TextUtil.isNotNull(pole.getInt_id())){
			pole.setLate(null);
			pole.setLats(null);
			pole.setLatitude(null);
			pole.setLone(null);
			pole.setLats(null);
			pole.setLongitude(null);
		}
		if(TextUtil.isNotNull(pole.getZh_label())){
			String poleName = pole.getZh_label().trim();
			if(poleName.contains(" ")){
				poleName = poleName .replaceAll(" +", "%");
			}
			pole.setZh_label("%"+poleName+"%");
		}
	    return getObjects("pdapoleline.getPoleCustom", pole);
	}

	@Override
	public Integer insertPoleCustom(PoleCustomInfoBean pole) throws DataAccessException {
		 return ((Integer)insert("pdapoleline.insertPoleCustom", pole));
	}

	@Override
	public Integer updatePoleCustom(PoleCustomInfoBean pole) throws DataAccessException {
		return Integer.valueOf(update("pdapoleline.updatePoleCustom", pole));
	}

	@Override
	public Integer deletePoleCustom(PoleCustomInfoBean pole) throws DataAccessException {
		return Integer.valueOf(update("pdapoleline.deletePoleCustom", pole));
	}
	

 
}
