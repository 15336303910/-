package interfaces.irmsInterface.interfaces.station.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import base.database.DataBase;
import base.util.TextUtil;
import interfaces.irmsInterface.interfaces.station.service.impl.ISyncSiteDataService;
import manage.point.pojo.PointInfoBean;

/**
 * 主要作用在派发工单后将
 * 机房下的数据进行同步
 * @author chenqp
 *
 */
public class SyncSiteDataService extends DataBase implements ISyncSiteDataService{
	//app数据库连接
	private JdbcTemplate jdbcTemplate;
	//综资数据库
	private JdbcTemplate irmsjdbcTemplate;
	
	/**
	 * 根据机房ID更新
	 * 机房下的端子信息
	 * 模拟机房23687
	 * @param gid
	 */
	public void updatePoint(String gid) {
		String sql = "select GROUP_CONCAT(p.resNum) as gresNum "
				+ " from job_equtinfo e"
				+ " left join job_odm o "
				+ " on e.EID = o.eid "
				+ " left join job_pointinfo p"
				+ " on o.odmId = p.odmId"
				+ " where e.gid = '"+gid+"'"
				+ " and p.ID is not null "
				+ " and p.resNum is not null"
				+ " and p.PSTAT =0 ";
		List<Map<String, Object>> pList = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(pList)) {
			Map<String, Object> pmap = pList.get(0);
			String fsql = "select f.zhLabel,f.endPortName as opptic,f.startPortId as portId from job_fiber f"
					+ " where f.startPortId in ("+pmap.get("gresNum")+") and deleteflag = 0"
					+ " union "
					+ " select f.zhLabel,f.startPortName as opptic,f.endPortId as portId from job_fiber f"
					+ " where f.endPortId in ("+pmap.get("gresNum")+") and deleteflag = 0";
			List<Map<String, Object>> fiberList = this.jdbcTemplate.queryForList(fsql);
			batchPoint(fiberList);
		}
	}
	
	/**
	 * 更新端子信息
	 * @param fiberList
	 */
	public void batchPoint(final List<Map<String, Object>> fiberList) {
		String sql = "update job_pointinfo"
				+ " set PSTAT ='1',fiberName =?,oppsite =?"
				+ " where resNum =?";
		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
			@Override
			public int getBatchSize() {
				return fiberList.size();
			}
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Map<String, Object> map = fiberList.get(i);
				ps.setString(1, map.get("zhLabel")+"");
				ps.setString(2, map.get("opptic")+"");
				ps.setString(3, map.get("portId")+"");
			}
			
		});
	}
	
	
	/**
	 * 补录数据
	 * @param odmId
	 * @param pointStr
	 * @return
	 */
	public List<PointInfoBean> recordPoint(String odmId,String pointStr) {
		List<PointInfoBean> pointList = new LinkedList<PointInfoBean>();
		String sql = "select a.int_id,"
				+ " a.zh_label,"
				+ " to_char(a.row_arry, 'fm909') as prowno,"
				+ " to_char(a.col_arry, 'fm909') as plineno,"
				+ " '00' || to_char(a.row_arry, 'fm909') || to_char(a.col_arry, 'fm909') as pid,"
				+ " a.equ_int_id,"
				+ " a.type_name,"
				+ " a.stateflag,"
				+ " a.deviceshelf_id"
				+ " from RES_DEV_TERMINAL a "
				+ " where a.STATEFLAG = 0"
				+ " and a.deviceshelf_id ='"+odmId+"'"
				+ " and int_id not in ("+pointStr+")";
		List<Map<String, Object>> resPlist= this.irmsjdbcTemplate.queryForList(sql);
		for(Map<String, Object> res : resPlist) {
			PointInfoBean point = new PointInfoBean();
			point.setResNum(res.get("INT_ID")+"");
			point.setPos(res.get("ZH_LABEL")+"");
			point.setProwno(res.get("PROWNO")+"");
			point.setPlineno(res.get("PLINENO")+"");
			point.setPid(res.get("PID")+"");
			point.setDel("0");
			point.setPstat("0");
			point.setId(Integer.parseInt(res.get("INT_ID")+""));
			point.setOdmId(Integer.parseInt(odmId));
			pointList.add(point);
		}
		return pointList;
	}
	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public JdbcTemplate getIrmsjdbcTemplate() {
		return irmsjdbcTemplate;
	}
	public void setIrmsjdbcTemplate(JdbcTemplate irmsjdbcTemplate) {
		this.irmsjdbcTemplate = irmsjdbcTemplate;
	}
	
	
}
