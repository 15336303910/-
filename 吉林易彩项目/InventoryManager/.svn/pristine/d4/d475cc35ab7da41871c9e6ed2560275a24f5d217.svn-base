package manage.device.service;

import interfaces.pdainterface.equt.pojo.EqutRankInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import base.database.DataBase;
import base.database.impl.MyBatchUpdate;
import base.util.TextUtil;
import manage.device.pojo.PointBean;
import manage.device.pojo.PointCommon;
import manage.device.service.impl.IFiberRackService;
import manage.equt.pojo.EqutInfoBean;
import manage.point.pojo.PointInfoBean;
import manage.route.pojo.CableInfoBean;
import manage.route.pojo.FiberInfoBean;

public class FiberRackService extends DataBase implements IFiberRackService{

	/**
	 * 进行调用
	 */
	private JdbcTemplate jdbcTemplate;
	private static String GET_ODMPOINT ="pdaequt.getPoint";
	private static String GET_NEPOINT = "device.getPointObj";
	
	
	/**
	 * 根据光缆段id得到
	 * 光缆段信息
	 * @param cableId
	 * @return
	 */
	public CableInfoBean getCableById(Integer cableId){
		CableInfoBean  obj = new CableInfoBean();
		obj.setCableid(cableId);
		obj = (CableInfoBean) getObject("pdaroute.getCableObj", obj);
		return obj;
	}
	
	/**
	 * 根据端子得到端子数据
	 * @param pointId
	 * @return
	 */
	public PointInfoBean getPointInfo(Integer pointId){
		PointInfoBean point = new PointInfoBean();
		point.setId(pointId);
		point = (PointInfoBean) getObject("pdaequt.getPoint", point);
		
		return point ;
	}
	
	/**
	 * 根据端子id得到对应的数据
	 * @param pointId
	 * @return
	 */
	public EqutInfoBean getEqutByPoint(String eid){
		EqutInfoBean equt = new EqutInfoBean();
		equt.setEid(eid);
		equt = (EqutInfoBean) getObject("pdaequt.getEqut", equt);
		
		return equt;
	}
	
	/**
	 * 得到纤芯的数据
	 * @param fiberId
	 * @return
	 */
	public FiberInfoBean getFiberById(Integer fiberId){
		FiberInfoBean fiber = new FiberInfoBean();
		fiber.setId(fiberId);
		fiber = (FiberInfoBean) getObject("pdaroute.getFiber", fiber);
		return fiber;
	}
	
	
	/**
	 * ODM端子纤芯落架
	 * @param list
	 * @return
	 */
	public int batchFiberOdmPoint(List<PointInfoBean> list,EqutRankInfo equtRank){
		int num = 0;
		if(equtRank.getCableItem().equals("start")){
			num = this.startFiberRank(list, equtRank);
		}else{
			num = this.endFiberRank(list, equtRank);
		}
		return num;
	}
	
	/**
	 * 落架一个端子对应的纤芯
	 * @param cable
	 * @param equt
	 * @param fiber
	 * @return
	 */
	public String fallApartPoint(CableInfoBean cable,EqutInfoBean equt,PointInfoBean point,FiberInfoBean fiber){
		String direction ="";
		String oppsite = "";
		point.setFiberName(fiber.getZhLabel());
		if(equt.getEtype().equals("3")){
			//这个是光交箱
			if(cable.getStartDeviceId().equals(equt.getId()) || cable.getStartDeviceId().equals(equt.getResNum())){
				direction ="start";
				fiber.setStartDeviceId(equt.getId()+"");
				fiber.setStartDeviceName(equt.getEname());
				fiber.setStartPortId(point.getId());
				fiber.setStartPortName(point.getPos());
				fiber.setStartDeviceType(1);
				fiber.setStartPortType(9215);
				oppsite = fiber.getEndPortName();
			}else{
				direction = "end";
				fiber.setEndDeviceId(equt.getId()+"");
				fiber.setEndDeviceName(equt.getEname());
				fiber.setEndPortId(point.getId());
				fiber.setEndPortName(point.getPos());
				fiber.setEndDeviceType(1);
				fiber.setEndPortType(9215);
				oppsite = fiber.getStartPortName();
			}
		}else if(equt.getEtype().equals("1")){
			//这个是站点
			String sql = ""
					+ "select g.areano as deviceId,"
					+ " (select resNum from job_stationbase where stationBaseId = g.areano) as resNum "
					+ " from job_generator g "
					+ " where g.generatorId='"+equt.getGid()+"'"
					+ " ";
			List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
			Map<String, Object> map= list.get(0);
			if(cable.getStartDeviceId().equals(map.get("deviceId")) || cable.getStartDeviceId().equals(map.get("resNum"))){
				direction ="start";
				fiber.setStartDeviceId(equt.getId()+"");
				fiber.setStartDeviceName(equt.getEname());
				fiber.setStartPortId(point.getId());
				fiber.setStartPortName(point.getPos());
				fiber.setStartDeviceType(6);
				fiber.setStartPortType(9215);
				oppsite = fiber.getEndPortName();
			}else{
				direction ="end";
				fiber.setEndDeviceId(equt.getId()+"");
				fiber.setEndDeviceName(equt.getEname());
				fiber.setEndPortId(point.getId());
				fiber.setEndPortName(point.getPos());
				fiber.setEndDeviceType(6);
				fiber.setEndPortType(9215);
				oppsite = fiber.getStartPortName();
			}
		}
		point.setOppsite(oppsite);
		point.setPstat("1");
		this.update("pdaequt.updatePoint", point);
		this.update("pdaroute.updateFiber", fiber);
		return direction;
	}
	 
	/**
	 * 更新纤芯上架
	 * @param list
	 * @param equtRank
	 * @return
	 */
	int startFiberRank(List<PointInfoBean> list,EqutRankInfo equtRank){
		int num = 0;
		for(PointInfoBean point : list){
			int id = this.jdbcTemplate.queryForInt("select min(id) from job_fiber"
					+ " where cableId = '"+equtRank.getCableId()+"' and startPortId is null");
			String sql= ""
					+ "update job_fiber "
					+ " set startId = '"+equtRank.getOdmId()+"',"
					+ " startDeviceId = '"+equtRank.getEid()+"' ,"
					+ " startPortId = '"+point.getId()+"', "
					+ " startType =0,startDeviceType ='"+equtRank.getRankType()+"',startPortType = 0 "
					+ " where id = "+id
					+ "";
			this.jdbcTemplate.execute(sql);
			String pointSql = "update job_pointinfo set pstat=1 where ID="+point.getId();
			this.jdbcTemplate.execute(pointSql);
			num+=1;
		}
		return num;
	}
	
	int endFiberRank(List<PointInfoBean> list,EqutRankInfo equtRank){
		int num = 0;
		for(PointInfoBean point : list){
			int id = this.jdbcTemplate.queryForInt("select min(id) from job_fiber"
					+ " where cableId = '"+equtRank.getCableId()+"' and endPortId is null");
			String sql= ""
					+ "update job_fiber "
					+ " set endId = '"+equtRank.getOdmId()+"',"
					+ " endDeviceId = '"+equtRank.getEid()+"' ,"
					+ " endPortId = '"+point.getId()+"', "
					+ " endType =0,endDeviceType ='"+equtRank.getRankType()+"',endPortType = 0 "
					+ " where id = "+id
					+ "";
			String pointSql = "update job_pointinfo set pstat=1 where ID="+point.getId();
			this.jdbcTemplate.execute(pointSql);
			this.jdbcTemplate.execute(sql);
		}
		return num;
	}
	
	/**
	 * 解析字符串
	 * @param list
	 * @return
	 */
	String getMapStr(List<Map<String, Object>> list,String type){
		String str = "";
		for(Map<String, Object> map : list){
			str += map.get(type)+",";
		}
		if(str.endsWith(",")){
			str = str.substring(0,str.length()-1);
		}
		return str;
	}
	/**
	 * odm设备id字符串
	 * @param list
	 * @return
	 */
	String getOdmStr(List<PointInfoBean> list){
		String str = "";
		for(PointInfoBean point : list){
			str += point.getId()+",";
		}
		if(str.endsWith(",")){
			str = str.substring(0, str.length()-1);
		}
		return str;
	}
	
	/**
	 * 设备id字符串
	 * @param list
	 * @return
	 */
	String getNeStr(List<PointBean> list){
		String str = "";
		for(PointBean point : list){
			str += point.getId()+",";
		}
		if(str.endsWith(",")){
			str = str.substring(0, str.length()-1);
		}
		return str;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
