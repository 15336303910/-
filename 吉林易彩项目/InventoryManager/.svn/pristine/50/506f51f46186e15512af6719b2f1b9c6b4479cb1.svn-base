package manage.device.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hmef.attribute.MAPIAttribute;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import manage.device.pojo.CardInfoBean;
import manage.device.pojo.DeviceInfoBean;
import manage.device.pojo.JumpFiberBean;
import manage.device.pojo.JumpPosBean;
import manage.device.pojo.PointBean;
import manage.device.pojo.PointCommon;
import manage.device.service.impl.IDeviceService;
import manage.point.pojo.PointInfoBean;
import manage.route.pojo.CableInfoBean;
import base.database.DataBase;
import base.database.impl.MyBatchUpdate;
import base.util.BeanUtil;
import base.util.TextUtil;

//设备操作
public class DeviceService extends DataBase implements IDeviceService{

	private JdbcTemplate jdbcTemplate;
	//设备管理
	private static final String GET_DEVICE = "device.getDevice";
	private static final String GET_DEVICECOUNT = "device.getDeviceCount";
	private static final String UPDATE_DEVICE = "device.updateDevice";
	private static final String GET_DEVICEGRID = "device.getDeviceGrid";
	private static final String INSERT_DEVICE = "device.insertDevice";
	//板卡管理
	private static final String GET_CARDGRID= "device.getCardGrid";
	private static final String GET_CARDCOUNT="device.getCardCount";
	private static final String GET_CARD="device.getCardObj";
	private static final String UPDATE_CARD="device.updateCard";
	private static final String INSERT_CARD="device.insertCard";
	//端口管理
	private static final String GET_PORTGRID="device.getPointGrid";
	private static final String GET_PORTCOUNT="device.getPointCount";
	private static final String GET_PORT="device.getPointObj";
	private static final String UPDATE_PORT="device.updatePoint";
	private static final String INSERT_PORT="device.insertPoint";
	
	private static final String GET_FIBER = "pdaroute.getFiber";
	private static final String GET_CABLE = "pdaroute.getCable";
	
	/**
	 * 得到所有的
	 * 网元设备信息
	 * @param obj
	 * @return
	 */
	public List<DeviceInfoBean> getDeviceGrid(DeviceInfoBean obj){
		return getObjects(GET_DEVICEGRID, obj);
	}
	
	/***
	 * 增加网元设备
	 * @param obj
	 * @return
	 */
	public int insertDevice(DeviceInfoBean obj){
		List<DeviceInfoBean> list = getObjects(GET_DEVICE, obj);
		if(TextUtil.isNotNull(list)){
			return -1;
		}else{
			Integer id = (Integer) insert(INSERT_DEVICE, obj);
			return id;
		}
	}
	
	/**
	 * 修改网元设备
	 * @param obj
	 */
	public void updateDevice(DeviceInfoBean obj){
		update(UPDATE_DEVICE, obj);
	}
	/**
	 * 批量更新数据
	 * @param obj
	 */
	public void beatchUpDevice(final List<DeviceInfoBean> list){
		String sql =" update job_device set deviceName =?,deviceModel=?,"
				+ " deviceVender=?,deviceType=?,posX=?, "
				+ " posY=?, eid=?, deleteFlag=?,"
				+ " lastUpdateDate=now() "
				+ " where id =? ";
		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
			@Override
			public int getBatchSize() {
				return list.size();
			}
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				DeviceInfoBean obj=list.get(i);
				ps.setString(1, obj.getDeviceName());
				ps.setString(2, obj.getDeviceModel());
				ps.setString(3, obj.getDeviceVender());
				ps.setString(4, obj.getDeviceType());
				ps.setString(5, obj.getPosX());
				ps.setString(6, obj.getPosY());
				ps.setString(7, obj.getEid());
				ps.setString(8, obj.getDeleteFlag());
				ps.setInt(9, obj.getId());
			}
			
		});
	}
	
	/**
	 * 得到所有的板卡信息
	 * @param obj
	 * @return
	 */
	public List<CardInfoBean> getCardGrid(CardInfoBean obj){
		return getObjects(GET_CARDGRID, obj);
	}
	
	/**
	 * 临时根据设备
	 * 生成板卡和端子
	 * @param obj
	 */
	public void tempDevice(DeviceInfoBean obj){
		try{
			//增加10个板卡
			List<CardInfoBean> list = new ArrayList<CardInfoBean>();
			int cardCount = 3;
			if(TextUtil.isNotNull(obj.getCardCount())){
				cardCount = obj.getCardCount();
			}
			for(int i=0;i<cardCount;i++){
				CardInfoBean card = new CardInfoBean();
				card.setCardName(obj.getDeviceName()+"-"+(i+1)+"号板卡");
				card.setDeviceId(obj.getId());
				card.seteID(obj.getEid());
				list.add(card);
			}
			this.batchInsertCard(list);
			//增加对应的端口
			String getCardSql = "select id ,cardName,deviceId from job_card where deviceId ='"+obj.getId()+"' ";
			List<Map<String, Object>> cardList = this.jdbcTemplate.queryForList(getCardSql);
			List<PointBean> pointList = new ArrayList<PointBean>();
			for(int i=0;i<cardList.size();i++){
				Map<String, Object> map = cardList.get(i);
				CardInfoBean card = (CardInfoBean) BeanUtil.mapToObject(map, CardInfoBean.class);
				int pointCount = 10;
				if(TextUtil.isNotNull(obj.getPointCount())){
					pointCount = obj.getPointCount();
				}
				for(int j=0;j<pointCount;j++){
					PointBean point = new PointBean();
					point.setCardId(card.getId());
					point.setPointName(card.getCardName()+"_"+(j+1)+"_号端口");
					point.setPointState(1);
					point.setDeleteFlag("0");
					point.setDeviceId(obj.getId());
					pointList.add(point);
				}
			}
			this.batchInsertPoint(pointList);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新板卡及
	 * 端口的所属机架
	 * @param list
	 */
	public void tempUpDevice(List<DeviceInfoBean> list){
		for(DeviceInfoBean device : list){
			String cardSql = "update job_card "
							+ " set eID = '"+device.getEid()+"',"
							+ " deletedFlag = '"+device.getDeleteFlag()+"'"
							+ " where deviceId = '"+device.getId()+"'";
			this.jdbcTemplate.execute(cardSql);
			String pointSql = "update job_point "
					+ " set deleteFlag ='"+device.getDeleteFlag()+"' ,"
					+ " eID ='"+device.getEid()+"' "
					+ " where deviceId ='"+device.getId()+"'";
			this.jdbcTemplate.execute(pointSql);
		}
	}
	
	/**
	 * 批量增加板卡
	 * @param list
	 */
	void batchInsertCard(final List<CardInfoBean> list){
		String sql = "insert into job_card (cardName,deviceId,eID,creationDate,deletedFlag)"
				+ " values (?,?,?,now(),0)";
		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				CardInfoBean card = list.get(i);
				ps.setString(1, card.getCardName());
				ps.setInt(2, card.getDeviceId());
				ps.setString(3, card.geteID());
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
	}
	
	/**
	 * 批量添加端子
	 * @param list
	 */
	void batchInsertPoint(List<PointBean> list){
		String sql ="insert into job_point(pointName,cardId,pointNum,pointState,creationDate,deleteFlag)"
				+ " values (?,?,?,1,now(),'0')";
		List<Map<String, Object>> pointList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			Map<String, Object> map = new HashMap<String, Object>();
			PointBean point = list.get(i);
			map.put("1", point.getPointName());
			map.put("2", point.getCardId()+"");
			map.put("3", (i+1)+"");
			pointList.add(map);
		}
		MyBatchUpdate update = new MyBatchUpdate(this.jdbcTemplate, sql, pointList, 3);
		update.execute();
	}
	
	/**
	 * 增加板卡操作
	 * @param obj
	 * @return
	 */
	public int insertCard(CardInfoBean obj){
		List<CardInfoBean> list = getObjects(GET_CARD, obj);
		if(TextUtil.isNotNull(list)){
			return -1;
		}else{
			Integer id =(Integer) insert(INSERT_CARD, obj);
			return id;
		}
	}
	
	/**
	 * 修改板卡操作
	 * @param obj
	 */
	public void updateCard(CardInfoBean obj){
		update(UPDATE_CARD, obj);
	}
	
	/**
	 * 得到所有的端口信息
	 * @param obj
	 * @return
	 */
	public List<PointBean> getPointGrid(PointBean obj){
		List<PointBean> list =getObjects(GET_PORTGRID, obj);
		//得到设备下的端子信息
		if(TextUtil.isNull(list) && TextUtil.isNotNull(obj.getDeviceId())){
			DeviceInfoBean device = new DeviceInfoBean();
			device.setId(obj.getDeviceId());
			List<DeviceInfoBean> deviceList = getObjects(GET_DEVICE, obj);
			if(TextUtil.isNotNull(deviceList)){
				DeviceInfoBean resDevice = deviceList.get(0);
				obj.setResNe(resDevice.getResNum());
				obj.setDeviceId(null);
				list = getObjects(GET_PORTGRID, obj);
			}
		}
		return list;
	}
	
	/**
	 * 得到板卡下所有
	 * 的端口信息
	 * @param obj
	 * @return
	 */
	public List<PointBean> getPointList(PointBean obj){
		List<PointBean> list = getObjects(GET_PORT, obj);
		for(PointBean point  : list){
			if(TextUtil.isNull(point.getPointState())){
				point.setPointState(1);
			}
			if(point.getPointState().equals(2)){
				/*String sql = " "
						+ "select pid1,pid2,pid1Type,pid2Type, "
						+ " case pid1Type "
						+ " when 'odm' then (select pos from job_pointinfo where ID = pid1) "
						+ " else (select pointName from job_point where id= pid1) "
						+ " END as pid1Name, "
						+ " case pid2Type "
						+ " when 'odm' then (select pos from job_pointinfo where ID = pid2) "
						+ " else (select pointName from job_point where id= pid2) "
						+ " END as pid2Name "
						+ " from job_jumpfiber where pid1 = '"+point.getId()+"' and deleteFlag='0' "
						+ " union all "
						+ " select pid1,pid2,pid1Type,pid2Type, "
						+ " case pid1Type "
						+ " when 'odm' then (select pos from job_pointinfo where ID = pid1) "
						+ " else (select pointName from job_point where id= pid1) "
						+ " END as pid1Name, "
						+ " case pid2Type "
						+ " when 'odm' then (select pos from job_pointinfo where ID = pid2) "
						+ " else (select pointName from job_point where id= pid2) "
						+ " END as pid2Name "
						+ " from job_jumpfiber where pid2 ='"+point.getId()+"' and deleteFlag='0' "
						+ " union all "
						+ " select pid1,pid2,pid1Type,pid2Type, "
						+ " case pid1Type "
						+ " when 'odm' then (select pos from job_pointinfo where resNum = pid1) "
						+ " else (select pointName from job_point where resNum= pid1) "
						+ " END as pid1Name, "
						+ " case pid2Type "
						+ " when 'odm' then (select pos from job_pointinfo where resNum = pid2) "
						+ " else (select pointName from job_point where resNum= pid2) "
						+ " END as pid2Name "
						+ " from job_jumpfiber where pid1 = '"+point.getResNum()+"'  and deleteFlag='0' "
						+ " union all "
						+ " select pid1,pid2,pid1Type,pid2Type, "
						+ " case pid1Type "
						+ " when 'odm' then (select pos from job_pointinfo where resNum = pid1) "
						+ " else (select pointName from job_point where resNum= pid1) "
						+ " END as pid1Name, "
						+ " case pid2Type "
						+ " when 'odm' then (select pos from job_pointinfo where resNum = pid2) "
						+ " else (select pointName from job_point where resNum= pid2) "
						+ " END as pid2Name "
						+ " from job_jumpfiber where pid2 ='"+point.getResNum()+"' and deleteFlag='0' "
						+ "";
				List<Map<String, Object>> resultList = this.jdbcTemplate.queryForList(sql);
				if(TextUtil.isNotNull(resultList)){
					Map<String, Object> resultMap= resultList.get(0);
					if((resultMap.get("pid1")+"").equals(point.getId()+"")){
						point.setOppoName(resultMap.get("pid2Name")+"");
					}else if((resultMap.get("pid2")+"").equals(point.getId()+"")){
						point.setOppoName(resultMap.get("pid1Name")+"");
					}
				}*/
			}
		}
		return list;
	}
	
	/**
	 * 增加端口操作
	 * @param obj
	 * @return
	 */
	public int insertPoint(PointBean obj){
		List<PointBean> list = getObjects(GET_PORT, obj);
		if(TextUtil.isNotNull(list)){
			return -1;
		}else{
			Integer id = (Integer) this.insert(INSERT_PORT, obj);
			return id;
		}
	}
	
	/**
	 * 修改端口操作
	 * @param obj
	 */
	public void updatePoint(PointBean obj){
		update(UPDATE_PORT, obj);
	}
	
	/**
	 * 批量更新端口状态
	 * @param list
	 */
	public void beachUpdatePoint(final List<PointBean> list){
		String sql = "update job_point set"
				//+ " pointName=?,"
				+ " pointState=?,"
				+ " mask =?, "
				+ " lastUpdateDate=now() "
				+ " where id =? ";
		this.jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				PointBean obj = list.get(i);
				//ps.setString(1, obj.getPointName());
				ps.setInt(1, obj.getPointState());
				ps.setString(2, obj.getMask());
				ps.setInt(3, obj.getId());
			}
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
	}
	
	
	//==============================================开始跳纤========================================
	/**
	 * 跳纤
	 * @param list
	 * @return
	 */
	public void jumpFiber(List<JumpFiberBean> list){
		this.delJumpFiber(list);
		this.addJumpFiber(list);
		List<PointInfoBean> odmList = new ArrayList<PointInfoBean>();
		List<PointBean> neList = new ArrayList<PointBean>();
		for(JumpFiberBean jumpFiber : list){
			PointCommon start = jumpFiber.getStart();
			PointCommon end = jumpFiber.getEnd();
			if(start.getPointType().equals("odm")){
				PointInfoBean point = new PointInfoBean();
				point.setId(start.getId());
				point.setPstat("2");
				point.setJumpOptical(end.getPointName() == null ? end.getId()+"_odm" : end.getPointName());
				odmList.add(point);
			}else if(start.getPointType().equals("ne")){
				PointBean point = new PointBean();
				point.setId(start.getId());
				point.setPointState(2);
				point.setMask(end.getPointName() == null ? end.getId() + "_ne" : end.getPointName());
				neList.add(point);
			}
			
			if(end.getPointType().equals("odm")){
				PointInfoBean point = new PointInfoBean();
				point.setId(end.getId());
				point.setPstat("2");
				point.setJumpOptical(start.getPointName() == null ? start.getId()+"_odm" : start.getPointName());
				odmList.add(point);
			}else if(end.getPointType().equals("ne")){
				PointBean point = new PointBean();
				point.setId(end.getId());
				point.setPointState(2);
				point.setMask(start.getPointName() == null ? start.getId()+"_ne" : start.getPointName());
				neList.add(point);
			}
		}
		//更新端子状态
		this.updatePointInfo(odmList);
		this.beachUpdatePoint(neList);
	}
	
	/**
	 * 将现在要跳纤的端子
	 * deleteFlag 置为1
	 * @param list
	 * @return
	 */
	void delJumpFiber(final List<JumpFiberBean> list){
		String sql = " update job_jumpfiber "
				   + " set deleteFlag ='1' ,updatetime = now() "
				   + " where pid1 in (?,?) or pid2 in (?,?)";
		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				JumpFiberBean jumpFiber = list.get(i);
				PointCommon start = jumpFiber.getStart();
				PointCommon end = jumpFiber.getEnd();
				ps.setString(1, start.getId()+"");
				ps.setString(2, end.getId()+"");
				ps.setString(3, start.getId()+"");
				ps.setString(4, end.getId()+"");
			}
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
	}
	
	/**
	 * 添加纤芯跳纤
	 */
	void addJumpFiber(final List<JumpFiberBean> list){
		String sql ="insert into job_jumpfiber (pid1,pid2,createtime,deleteFlag,pid1Type,pid2Type,discribe)"
				+ " values(?,?,now(),'0',?,?,?)";
		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				JumpFiberBean jumpFiber = list.get(i);
				PointCommon start = jumpFiber.getStart();
				PointCommon end = jumpFiber.getEnd();
				ps.setString(1, start.getId()+"");
				ps.setString(2, end.getId()+"");
				ps.setString(3, start.getPointType());
				ps.setString(4, end.getPointType());
				ps.setString(5, jumpFiber.getDiscribe());
			}
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return list.size();
			}
		});
	}
	
	/**
	 * 修改外线数据
	 * @param list
	 */
	void updatePointInfo(final List<PointInfoBean> list){
		String sql ="update job_pointinfo "
				+ " set pstat =? , jumpOptical =?"
				+ " where id = ? ";
		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				PointInfoBean point = list.get(i);
				ps.setString(1, point.getPstat());
				ps.setString(2, point.getJumpOptical()+"");
				ps.setString(3, point.getId()+"");
			}
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
	}
	//===========================结束跳纤==========================================
	
	/**
	 * 保存跳纤到分光器
	 * * @param pos
	 */
	public void jumpPos(JumpPosBean pos){
		//更新端口数据
		String ponSql = "update job_point set pointState = 2 where id ='"+pos.getPonId()+"'";
		this.jdbcTemplate.execute(ponSql);
		//更新分光器
		String posSql = "update resource_pos set oltId = "+pos.getOltId()+","
				+ " oltName ='"+pos.getOltName()+"', "
				+ " ponId = "+pos.getPonId()+","
				+ " ponName ='"+pos.getPonName()+"'"
				+ " where id = "+pos.getPosId()+"";
		this.jdbcTemplate.execute(posSql);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
