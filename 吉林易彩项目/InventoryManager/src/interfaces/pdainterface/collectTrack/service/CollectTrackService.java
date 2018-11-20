package interfaces.pdainterface.collectTrack.service;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import base.database.DataBase;
import interfaces.pdainterface.collectTrack.pojo.CollectTrackBean;
import interfaces.pdainterface.collectTrack.service.impl.ICollectTrackService;


/**
 * 轨迹service
 * @author chenqp
 *
 */
public class CollectTrackService extends DataBase implements ICollectTrackService{

	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 得到轨迹列表
	 * @return
	 */
	public List<CollectTrackBean> getCollectTrack(CollectTrackBean obj){
		List<CollectTrackBean> list = getObjects("collectTrack.getCollectTrack",obj);
		return list;
	}
	
	/**
	 * 增加轨迹点
	 * @param obj
	 */
	public int addCollectTrack(CollectTrackBean obj){
		Integer id = (Integer) this.insert("collectTrack.addCollectTrack", obj);
		return id;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
