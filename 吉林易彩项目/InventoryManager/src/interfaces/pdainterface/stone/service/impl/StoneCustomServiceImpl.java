package interfaces.pdainterface.stone.service.impl;

import java.util.List;

import base.database.DataBase;
import base.util.TextUtil;
import interfaces.pdainterface.stone.pojo.StoneCustomInfoBean;
import interfaces.pdainterface.stone.service.IStoneCustomService;

public class StoneCustomServiceImpl extends DataBase implements IStoneCustomService{

	@Override
	public List<StoneCustomInfoBean> getStoneGridCustom(StoneCustomInfoBean obj) {
		if(TextUtil.isNotNull(obj.getInt_id())){
			obj.setLate(null);
			obj.setLats(null);
			obj.setLatitude(null);
			obj.setLone(null);
			obj.setLons(null);
			obj.setLongitude(null);
		}
		if(TextUtil.isNotNull(obj.getZh_label())){
			String stoneName = obj.getZh_label();
			if(stoneName.contains(" ")){
				stoneName= stoneName.replaceAll(" +", "%");
			}
			obj.setZh_label("%"+stoneName+"%");
		}
		
		@SuppressWarnings("unchecked")
		List<StoneCustomInfoBean> list = getObjects("stone.getStoneGridCustom", obj);
		return list;
	}

	@Override
	public int updateStoneCustom(StoneCustomInfoBean obj) {
		return update("stone.updateStoneCustom", obj);
	}

	@Override
	public int insertStoneCustom(StoneCustomInfoBean obj) {
		return (Integer) this.insert("stone.insertStoneCustom", obj);
	}

	@Override
	public int deleteStoneCustom(StoneCustomInfoBean obj) {
		return update("stone.deleteStoneCustom", obj);
	}

}
