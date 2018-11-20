package manage.resPoint.service;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import manage.resPoint.pojo.WirelessPointPojo;
import manage.resPoint.service.impl.IwirelessService;
import base.database.DataBase;
import base.util.TextUtil;

public class WirelessService extends DataBase implements IwirelessService{

	private JdbcTemplate jdbcTemplate;

	
	/**
	 * 得到网络资源点
	 * @param obj
	 * @return
	 */
	public List<WirelessPointPojo> getWirelessGrid(WirelessPointPojo obj){
		if(TextUtil.isNotNull(obj.getWirelessName())){
			String wirelessName = obj.getWirelessName();
			if(wirelessName.contains(" ")){
				wirelessName= wirelessName.replaceAll(" +", "%");
			}
			obj.setWirelessName("%"+wirelessName+"%");
		}
		List<WirelessPointPojo> list = getObjects("resPoint.getWirelessGrid", obj);
		return list;
	}
	
	/**
	 * 得网络资源列表
	 * @param obj
	 * @return
	 */
	public List<WirelessPointPojo> getWirelessList(WirelessPointPojo obj){
		if(TextUtil.isNotNull(obj.getWirelessName())){
			String wirelessName = obj.getWirelessName();
			if(wirelessName.contains(" ")){
				wirelessName= wirelessName.replaceAll(" +", "%");
			}
			obj.setWirelessName("%"+wirelessName+"%");
		}
		List<WirelessPointPojo> list = getObjects("resPoint.getWirelessList", obj);
		return list;
	}
	
	
	/**
	 * 得到网络资源点
	 * @param obj
	 * @return
	 */
	public WirelessPointPojo getWirelessObj(WirelessPointPojo obj){
		return (WirelessPointPojo) getObject("resPoint.getWirelessObj", obj);
	}
	
	
	/**
	 * 新增网络资源点
	 * @param obj
	 * @return
	 */
	public int addWireless(WirelessPointPojo obj){
		return (Integer) this.insert("resPoint.addWireless", obj);
	}
	
	
	/**
	 * 修改网络资源点
	 * @param obj
	 */
	public void updateWireless(WirelessPointPojo obj){
		this.update("resPoint.updateWireless", obj);
	}
	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
