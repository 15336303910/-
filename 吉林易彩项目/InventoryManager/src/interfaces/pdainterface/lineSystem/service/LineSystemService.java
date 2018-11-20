package interfaces.pdainterface.lineSystem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import manage.buriedPart.pojo.BuriedPartObj;
import manage.generator.pojo.StationBaseInfoBean;
import manage.leadup.pojo.HangWallPojo;
import manage.leadup.pojo.SupportPointPojo;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.pipe.pojo.TubeInfoBean;
import manage.pipe.pojo.WellInfoBean;
import manage.poleline.pojo.PoleInfoBean;
import manage.poleline.pojo.PolelineSegmentInfoBean;
import manage.stone.pojo.StoneInfoBean;

import org.springframework.jdbc.core.JdbcTemplate;

import edu.emory.mathcs.backport.java.util.LinkedList;
import edu.emory.mathcs.backport.java.util.concurrent.Semaphore;
import base.database.DataBase;
import base.util.BeanUtil;
import base.util.MapUtil;
import base.util.ResUtil;
import base.util.TextUtil;
import base.util.functions;
import interfaces.pdainterface.lineSystem.pojo.LineAffiliationInfo;
import interfaces.pdainterface.lineSystem.pojo.LinePointInfo;
import interfaces.pdainterface.lineSystem.pojo.LineSegmentInfo;
import interfaces.pdainterface.lineSystem.pojo.LineSystemInfo;
import interfaces.pdainterface.lineSystem.service.impl.IlineSystemService;
import interfaces.pdainterface.pipe.service.PDAPipeService;

public class LineSystemService extends DataBase implements IlineSystemService{
	private JdbcTemplate jdbcTemplate;
	private PDAPipeService pdaPipeService;	
	
	/**
	 * 得到所有的列表
	 * @param info
	 * @return
	 */
	public List<LineSystemInfo> getLineSystemList(LineSystemInfo info){
		if(TextUtil.isNotNull(info.getLineArea()) && info.getLineArea().contains("*")) {
		     String[] areas = info.getLineArea().split("\\*");
		     String sql = "";
		     for(String area : areas) {
		       sql +=" instr(lineArea, '"+area+"') > 0 or";
		     }
		     if(TextUtil.isNotNull(sql) && sql.endsWith("or")) {
		       sql = sql.substring(0,sql.length()-2);
		       info.setLineArea(null);
		       info.setExtendsSql(sql);
		     }
		   }
		List<LineSystemInfo> list = this.getObjects("lineSystem.getLineSystem", info);
		return list;
	}
	
	/**
	 * 得到管线系统
	 * @param info
	 * @return
	 */
	public LineSystemInfo getLineSystemObj(LineSystemInfo info){
		LineSystemInfo obj = (LineSystemInfo) this.getObject("lineSystem.getLineSysObj", info);
		return obj;
	}
	
	
	/**
	 * 进行核查管线系统是否存在重复数据
	 * @param info
	 * @return
	 */
	public LineSystemInfo checkLineSystemObj(LineSystemInfo info) {
		LineSystemInfo obj = (LineSystemInfo) this.getObject("lineSystem.checkLineSysObj", info);
		return obj;
	}
	
	/**
	 * 修改管线
	 * @param info
	 * @return
	 */
	public int upLineSystem(LineSystemInfo info){
		return this.update("lineSystem.upLineSystem", info);
	}
	
	/**
	 * 增加管线
	 * @param info
	 * @return
	 */
	public int insertLineSystem(LineSystemInfo info){
		return (Integer) this.insert("lineSystem.insertLineSys", info);
	}
	
	/**
	 * 得到点的列表
	 * @param info
	 * @return
	 */
	public List<LinePointInfo> getLinePoint(LinePointInfo info){
		List<LinePointInfo> list = new ArrayList<LinePointInfo>();
		try{
			info.setDeleteflag(0);
			String sql = ResUtil.getPointStr(info);
			List<Map<String, Object>> result = this.jdbcTemplate.queryForList(sql);
			for(Map<String, Object> map : result ){
				LinePointInfo obj = (LinePointInfo) BeanUtil.mapToObject(map, LinePointInfo.class);
				obj.setSysId(info.getSysId());
				obj.setSysType(info.getSysType());
				list.add(obj);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	/**
	 * 得到段的数据
	 * @param info
	 * @return
	 */
	public List<LineSegmentInfo> getLineSegList(LineSegmentInfo info){
		List<LineSegmentInfo>  list = new ArrayList<LineSegmentInfo>();
		try{
			info.setDeleteFlag(0);
			String sql = ResUtil.getSegStr(info);
			List<Map<String, Object>> result = this.jdbcTemplate.queryForList(sql);
			for(Map<String, Object> map  : result){
//				LineSegmentInfo line = (LineSegmentInfo) BeanUtil.mapToObject(map, LineSegmentInfo.class);
				LineSegmentInfo line = new LineSegmentInfo();
				line.setSegId(map.get("segId").toString());
				line.setSegName(map.get("segName").toString());
				line.setSegType(map.get("segType").toString());
				line.setStartId(map.get("startId").toString());
				line.setStartName(map.get("startName").toString());
				line.setStartType(map.get("startType").toString());
				line.setStartLon(map.get("startLon").toString());
				line.setStartLat(map.get("startLat").toString());
				line.setEndId(map.get("endId").toString());
				line.setEndName(map.get("endName").toString());
				line.setEndType(map.get("endType").toString());
				line.setEndLon(map.get("endLon").toString());
				line.setEndLat(map.get("endLat").toString());
				
				line.setSysId(info.getSysId());
				line.setSysType(info.getSysType());
				list.add(line);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 得到长度
	 * @param obj
	 * @return
	 */
	public Map<String, Object> getLineLength(LineSystemInfo obj){
		Map<String, Object> map= new HashMap<String, Object>();
		String sql= "";
		if(obj.getLineType().equals(0)){
			//电杆
			sql = "select sum(poleLineSegmentLength) as coutLength,sum(maintainLength) as maintainLength"
					+ " from polelinesegmentinfo where poleLineId ='"+obj.getId()+"' and deletedFlag ='0'";
		}
		if(obj.getLineType().equals(1)){
			//标石
			sql = "select sum(buriedPartLength) as coutLength,sum(maintainLength) as maintainLength "
					+ " from buriedpartinfo where buriedId = '"+obj.getId()+"' and deleteflag = 0";
		}
		if(obj.getLineType().equals(2)){
			//管井
			sql = "select sum(pipeSegmentLength) as coutLength, sum(maintainLength) as maintainLength"
					+ " from pipesegmentinfo  where pipeId = '"+obj.getId()+"' and deletedFlag = 0";
		}
		if(obj.getLineType().equals(4)){
			//光缆
			sql = "select sum(length) as coutLength,sum(maintainLength) as maintainLength"
					+ " from job_cable where routeid = 36 and deletedFlag= 0";
		}
		map = this.jdbcTemplate.queryForMap(sql);
		return map;
	}
	
	/**
	 * 得到最大的记录
	 * @param obj
	 * @return
	 */
	public Map<String, Object> getMaxLinePoint(LinePointInfo obj){
		String sql = "";
		if(obj.getSysType().equals(0)){
			//电杆
			sql = "select t.poleId,t.poleName,t.poleNo,t.poleLineId,t.poleTypeEnumId,t.poleLongitude,t.poleLatitude,t.region,t.direction"
				+ " from poleinfo t where  t.deletedFlag = 0 and "
				+ " t.poleNo = (select max(poleNo+0) from poleinfo where deletedFlag=0 and poleLineId='"+obj.getSysId()+"') and t.poleLineId ='"+obj.getSysId()+"'";
		}else if (obj.getSysType().equals(1)){
			//标石
			sql = "select stoneId,stoneName,stonePosition,stoneNum,stoneArea,longitude,latitude,propertyNature,propertyComp,maintainer,buriedId"
				+ " from stoneinfo t where t.deleteflag =0 and t.stoneNum =(select max(stoneNum+0) from stoneinfo where deleteflag =0 and buriedId='"+obj.getSysId()+"')"
				+ " and t.buriedId='"+obj.getSysId()+"'";
		}else if(obj.getSysType().equals(2)){
			//管井
			sql = "select t.wellId,t.wellName,t.direction,t.wellNo,t.pipeId,t.longitude,t.latitude,t.manholeShape,t.region,t.constructionSharingEnumId,t.constructionSharingOrg"
					+ " from wellinfo t where t.deletedFlag = 0 and "
					+ " t.wellNo =(select max(wellNo+0) from wellinfo WHERE deletedFlag = 0 and pipeId = '"+obj.getSysId()+"') and t.pipeId ='"+obj.getSysId()+"'";
		} else if (obj.getSysType().equals(3)) {
			//撑点
			sql = "select t.id,t.sportName,t.status,t.maintMode,t.purpose,t.maintArea,t.maintDept,t.longitude,t.latitude,t.propertyNature,t.dataQualitier,t.propertyComp,t.maintainer,t.createTime,t.creater,t.deleteflag,t.lastUpTime,t.lastUpMan,t.resNum,t.supportPointNo "
					+ " from job_support_point t where t.deleteflag = 0 and "
					+ " t.supportPointNo =(select max(supportPointNo+0) from job_support_point WHERE deleteflag = 0  and hangWallId = '"+obj.getSysId()+"') and t.hangWallId ='"+obj.getSysId()+"'";
		}
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			Map<String, Object> map = list.get(0);
			return map;
		}else {
			return null;
		}
	}
	
	
	/**
	 * 增加点数据
	 * @param obj
	 * @param map
	 * @return
	 */
	public int addLinePoint(LinePointInfo obj,Map<String, Object> map,String loginer){
		int result= 0;
		String direction = "";
		LineSystemInfo lineSys = new LineSystemInfo();
		lineSys.setId(obj.getSysId());
		lineSys = (LineSystemInfo) this.getObject("lineSystem.getLineSysObj", lineSys);
		if(TextUtil.isNotNull(lineSys.getDirection())){
			String sql = "select text as direction from dicview"
					+ " where enName ='wellDirection' and value = '"+lineSys.getDirection()+"'";
			Map<String, Object> dirMap = this.jdbcTemplate.queryForMap(sql);
			if(dirMap != null){
				direction = dirMap.get("direction")+"";
			}
		}
		try{
			if(obj.getSysType().equals(0)){
				//电杆
				PoleInfoBean pole = new PoleInfoBean();
				if(null == map){
					pole.setPoleNo("1");
					pole.setPoleName(obj.getSysName()+"_"+direction+"1号杆");
				}else {
					Integer startNum = Integer.parseInt(map.get("poleNo")+"");
					pole.setPoleNo((startNum+1)+"");
					pole.setPoleName(obj.getSysName()+"_"+direction+""+(startNum+1)+"号杆");
				}
				pole.setPoleLineId(obj.getSysId());
				pole.setPoleTypeEnumId(0);
				pole.setPoleLatitude(obj.getLatitude());
				pole.setPoleLongitude(obj.getLongitude());
				pole.setRegion(obj.getSysRegion());
				pole.setMaintenanceOrgId(lineSys.getPropertyComp()+"");
				pole.setMaintenanceModeEnumId(lineSys.getPropertyNature());
				pole.setPropertyNature(lineSys.getPropertyNature());
				pole.setDataQualityPrincipal(loginer);
				pole.setDirection(lineSys.getDirection());
				pole.setParties(loginer);
				result= (Integer) this.insert("pdapoleline.insertPole", pole);
				//增加一个段
				if(null != map){
					PolelineSegmentInfoBean poleSeg = new PolelineSegmentInfoBean();
					poleSeg.setPoleLineId(obj.getSysId());
					poleSeg.setPoleLineSegmentName(map.get("poleName")+"-"+pole.getPoleName()+"杆路段");
					poleSeg.setStartDeviceId(Integer.parseInt(map.get("poleId")+""));
					poleSeg.setEndDeviceId(result);
					poleSeg.setConstructionSharingEnumId(0);
					poleSeg.setCstate("2");
					poleSeg.setMaintenanceAreaName(obj.getSysRegion());
					poleSeg.setDataQualityPrincipal(loginer);
					poleSeg.setParties(loginer);
					poleSeg.setConstructionSharingOrg(lineSys.getPropertyComp()+"");
					poleSeg.setConstructionSharingEnumId(lineSys.getPropertyNature());
					poleSeg.setPoleLineSegmentLength(this.getDistince(obj.getLongitude(), obj.getLatitude(), map.get("poleLongitude")+"", map.get("poleLatitude")+""));
					poleSeg.setMaintainLength(poleSeg.getPoleLineSegmentLength());
					if(Double.parseDouble(poleSeg.getMaintainLength()) > 500.0) {
						result= -1;
					}else {
						this.insert("pdapoleline.insertPolelineSegment", poleSeg);
					}
				}
			}else if(obj.getSysType().equals(1)){
				//标石
				StoneInfoBean stone = new StoneInfoBean();
				if(null == map){
					stone.setStoneNum("1");
					stone.setStoneName(obj.getSysName()+"_"+direction+"1号标石");
				}else{
					Integer startNum = Integer.parseInt(map.get("stoneNum")+"");
					stone.setStoneNum((startNum+1)+"");
					stone.setStoneName(obj.getSysName()+"_"+direction+""+(startNum+1)+"号标石");
				}
				stone.setBuriedId(obj.getSysId()+"");
				stone.setStoneArea(obj.getSysRegion());
				stone.setLongitude(obj.getLongitude());
				stone.setLatitude(obj.getLatitude());
				stone.setDataQualitier(loginer);
				stone.setMaintainer(loginer);
				stone.setPropertyComp(lineSys.getPropertyComp());
				stone.setPropertyNature(lineSys.getPropertyNature());
				stone.setStonePosition(lineSys.getDirection());
				result= (Integer) this.insert("stone.insertStone", stone);
				//增加一个段
				if(null != map){
					BuriedPartObj buriedSeg = new BuriedPartObj();
					buriedSeg.setBuriedId(obj.getSysId()+"");
					buriedSeg.setBuriedPartName(map.get("stoneName")+"-"+stone.getStoneName()+"直埋段");
					buriedSeg.setBuriedPartStartId(map.get("stoneId")+"");
					buriedSeg.setBuriedPartStart(map.get("stoneName")+"");
					buriedSeg.setBuriedPartEnd(stone.getStoneName());
					buriedSeg.setBuriedPartEndId(stone.getStoneId()+"");
					buriedSeg.setBuriedPartArea(obj.getSysRegion());
					buriedSeg.setDataQualitier(loginer);
					buriedSeg.setPropertyDept(lineSys.getPropertyComp());
					buriedSeg.setPropertyRight(lineSys.getPropertyNature());
					buriedSeg.setMaintainer(loginer);
					buriedSeg.setBuriedPartLength(this.getDistince(obj.getLongitude(), obj.getLatitude(), map.get("longitude")+"", map.get("latitude")+""));
					buriedSeg.setMaintainLength(buriedSeg.getBuriedPartLength());
					if(Double.parseDouble(buriedSeg.getMaintainLength()) > 500.0) {
						result= -1;
					}else {
						this.insert("buriedPart.insertBuriedPart", buriedSeg);
					}
				}
			}else if(obj.getSysType().equals(2)){
				//管井
				WellInfoBean well = new WellInfoBean();
				if(null == map){
					well.setWellNo("1");
					well.setWellName(obj.getSysName()+"_"+direction+"1号井");
				}else{
					Integer startNum = Integer.parseInt(map.get("wellNo")+"");
					well.setWellNo((startNum+1)+"");
					well.setWellName(obj.getSysName()+"_"+direction+""+(startNum+1)+"号井");
				}
				well.setPipeId(obj.getSysId()+"");
				well.setRegion(obj.getSysRegion());
				well.setLatitude(obj.getLatitude());
				well.setLongitude(obj.getLongitude());
				well.setDataQualityPrincipal(loginer);
				well.setDirection(direction);
				well.setConstructionSharingEnumId(lineSys.getPropertyNature());
				well.setConstructionSharingOrg(lineSys.getPropertyComp()+"");
				well.setTexture(1);
				well.setRedWire(0);
				well.setParties(loginer);
				result= (Integer) this.insert("pdapipe.insertWell", well);
				if(null != map){
					PipeSegmentInfoBean seg = new PipeSegmentInfoBean();
					seg.setPipeId(obj.getSysId());
					seg.setPipeSegmentName(map.get("wellName")+"-"+well.getWellName()+"管道段");
					seg.setStartDeviceId(Integer.parseInt(map.get("wellId")+""));
					seg.setEndDeviceId(result);
					seg.setStartDeviceName(map.get("wellName")+"");
					seg.setEndDeviceName(well.getWellName());
					seg.setMaintenanceAreaName(obj.getSysRegion());
					seg.setSharingTypeEnumId(lineSys.getPropertyNature());
					seg.setConstructionSharingOrg(lineSys.getPropertyComp()+"");
					seg.setDataQualityPrincipal(loginer);
					seg.setParties(loginer);
					seg.setPipeSegmentLength(this.getDistince(well.getLongitude(), well.getLatitude(), map.get("longitude")+"", map.get("latitude")+""));
					seg.setMaintainLength(seg.getPipeSegmentLength());
					if(Double.parseDouble(seg.getMaintainLength()) > 1000.0) {
						result =-1;
					}else {
						int segId = (Integer) this.insert("pdapipe.insertPipeSegment", seg);
						//批量增加一个管孔信息
						new addTubeThread(segId,seg.getPipeId(),seg.getPipeSegmentName()).start();
					}
					
				}
			} else if (obj.getSysType().equals(3)) {
				/*//挂墙
				SupportPointPojo supportPointPojo = new SupportPointPojo();
				if(null == map){
					supportPointPojo.setSupportPointNo("1");
					supportPointPojo.setSportName(obj.getSysName()+"_1号撑点");
				}else{
					Integer startNum = Integer.parseInt(map.get("supportPointNo")+"");
					supportPointPojo.setSupportPointNo((startNum+1)+"");
					supportPointPojo.setSportName(obj.getSysName()+"_"+(startNum+1)+"号撑点");
				}
				supportPointPojo.setHangWallId(obj.getSysId()+"");
				supportPointPojo.setMaintArea(obj.getSysRegion());
				supportPointPojo.setLatitude(obj.getLatitude());
				supportPointPojo.setLongitude(obj.getLongitude());
				supportPointPojo.setDataQualitier(loginer);
				supportPointPojo.setMaintainer(loginer);
				result= (Integer) this.insert("leadup.insertSupportPoint", supportPointPojo);
				if(null != map){
					HangWallPojo hangWallPojo = new HangWallPojo();
					hangWallPojo.setHangWallId(obj.getSysId().toString());
					hangWallPojo.setHangWallName(map.get("sportName")+"-"+supportPointPojo.getSportName()+"挂墙");
					hangWallPojo.setStartDeviceId(Integer.parseInt(map.get("id")+""));//
					hangWallPojo.setEndDeviceId(result);
					hangWallPojo.setStartDeviceName(map.get("sportName").toString()); 
					hangWallPojo.setEndDeviceName(supportPointPojo.getSportName()); 
					hangWallPojo.setMaintArea(obj.getSysRegion());
					hangWallPojo.setDataQualitier(loginer);
					hangWallPojo.setMaintainer(loginer);
					this.insert("leadup.insertHanlWall", hangWallPojo);
				}*/
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 
	 * @author chenqp
	 *
	 */
	class addTubeThread extends Thread{
		private int segId;
		private int sysId;
		private String segName;
		public addTubeThread(int segId ,int sysId,String segName){
			this.segId = segId;
			this.sysId = sysId;
			this.segName = segName;
		}
		@Override
		public void run() {
			String sql = ""
					+ " select pipeSegmentId as frontId"
					+ " from pipesegmentinfo"
					+ " where pipeSegmentId <"+segId+""
					+ " and pipeId ="+sysId+" and deletedFlag =0 "
					+ " order by pipeSegmentId desc limit 1";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			if(TextUtil.isNotNull(list)){
				Map<String, Object> map = list.get(0);
				String frontId = map.get("frontId")+"";
				TubeInfoBean tube = new TubeInfoBean();
				tube.setPipeSegmentId(frontId);
				List<TubeInfoBean> oriList = pdaPipeService.getTube(tube);
				
				List<TubeInfoBean> cloneList = new LinkedList();
				for(TubeInfoBean ori: oriList) {
					TubeInfoBean clone = (TubeInfoBean) ori.clone();
					clone.setPipeSegmentId(segId+"");
					clone.setPipeSegmentName(segName);
					clone.setTubeName(segName+"("+clone.getTubeNumber()+")管孔");
					clone.setTubeId(null);
					int cloneId = pdaPipeService.insertTube(clone);
					clone.setTubeId(cloneId);
					cloneList.add(clone);
				}
				if(TextUtil.isNotNull(cloneList)) {
					pdaPipeService.beatchSubTube(cloneList,"");
				}
				
				/*String numSql = "select max(tubeNumber) as tubeNum,subTubeAmount as subNum"
						+ " from tubeinfo where pipeSegmentId = "+frontId+"";
				List<Map<String, Object>> numList = jdbcTemplate.queryForList(numSql);
				if(TextUtil.isNotNull(numList)){
					Map<String, Object> numMap = numList.get(0);
					TubeInfoBean tube = new TubeInfoBean();
					tube.setPipeSegmentId(segId+"");
					tube.setPipeSegmentName(segName);
					tube.setTubeNumber(numMap.get("tubeNum")+"");
					if(TextUtil.isNotNull(numMap.get("subNum")+"")){
						tube.setSubTubeAmount(Integer.parseInt(numMap.get("subNum")+""));
						List<TubeInfoBean> tubeList = pdaPipeService.beatchTube(tube);
						pdaPipeService.beatchSubTube(tubeList);
					}
				}*/
			}
			super.run();
		}
	}
	
	
	/**
	 * 得到两点之间的距离
	 * @param slon
	 * @param slat
	 * @param elon
	 * @param elat
	 * @return
	 */
	String getDistince(String slon,String slat,String elon ,String elat){
		String result= "0";
		if(TextUtil.isNotNull(slon) && TextUtil.isNotNull(slat) && TextUtil.isNotNull(elon) && TextUtil.isNotNull(elat)){
			result = MapUtil.Distance(Double.parseDouble(slon), Double.parseDouble(slat), Double.parseDouble(elon), Double.parseDouble(elat));
		}
		
		return result;
	}
	
	String getDistinct(Integer type,String start,String end){
		String distinct = "0";
		String sql ="";
		if(type.equals(0)){
			//杆路
			sql ="select poleLongitude as lon,poleLatitude as lat from"
			  		+ " poleinfo where poleId in ('"+start+"','"+end+"')";
		}else if(type.equals(1)){
			//直埋
			sql ="select longitude as lon,latitude as lat from"
			  		+ "  stoneinfo where stoneId in ('"+start+"','"+end+"')";
		}else if(type.equals(2)){
			//管井
			sql ="select longitude as lon,latitude as lat from"
			  		+ " wellinfo where wellId in ('"+start+"','"+end+"')";
		} else if (type.equals(3)) {
			//挂墙
			sql ="select longitude as lon,latitude as lat from"
			  		+ " job_support_point where id in ('"+start+"','"+end+"')";
		}
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		Map<String, Object> startMap = list.get(0);
		Map<String, Object> endMap = list.get(1);
		distinct = MapUtil.Distance(Double.parseDouble(startMap.get("lon")+""),
				 Double.parseDouble(startMap.get("lat")+""),
				 Double.parseDouble(endMap.get("lon")+""),
				 Double.parseDouble(endMap.get("lat")+""));
		Double dis = Double.parseDouble(distinct);
		if(dis <= 300 && dis >0){
			distinct = dis+"";
		}else{
			distinct = "0";
		}
		return distinct;
	}
	
	
	/**
	 * 得到地图展现
	 * 的光缆段的数据
	 * @param obj
	 * @return
	 */
	public List<LineSegmentInfo> getCableGis(LineSystemInfo obj){
		List<LineSegmentInfo> list = new ArrayList<LineSegmentInfo>();
		try{
			LineSegmentInfo segObj = new LineSegmentInfo();
			segObj.setSysId(obj.getId());
			segObj.setSysType(obj.getLineType()+"");
			segObj.setDeleteFlag(0);
			String segSql = ResUtil.getSegStr(segObj);
			List<Map<String, Object>> segList = this.jdbcTemplate.queryForList(segSql);
			List<LineSegmentInfo> pointList = this.getCablePoint(obj);
			if(TextUtil.isNotNull(segList)){
				for(Map<String, Object> segMap : segList){
					//LineSegmentInfo seg = (LineSegmentInfo) BeanUtil.mapToObject(segMap, LineSegmentInfo.class);
					LineSegmentInfo seg = new LineSegmentInfo();
					seg.setSegId(segMap.get("segId")+"");
					seg.setSegName(segMap.get("segName")+"");
					seg.setSegType(segMap.get("segType")+"");
					seg.setStartId(segMap.get("startId")+"");
					seg.setStartName(segMap.get("startName")+"");
					seg.setStartType(segMap.get("startType")+"");
					seg.setStartLat(segMap.get("startLat")+"");
					seg.setStartLon(segMap.get("startLon")+"");
					seg.setEndId(segMap.get("endId")+"");
					seg.setEndName(segMap.get("endName")+"");
					seg.setEndType(segMap.get("endType")+"");
					seg.setEndLat(segMap.get("endLat")+"");
					seg.setEndLon(segMap.get("endLon")+"");
					list.add(seg);
					if(TextUtil.isNotNull(pointList)){
						for(int j=0 ;j<pointList.size();j++){
							LineSegmentInfo start = pointList.get(j);
							if(start.getStartId().equals(seg.getStartId()) || start.getStartId().equals(seg.getEndId())){
								pointList.remove(j);
							}
						}
					}
				}
			}
			//将剩余的点数据加到段里面去
			if(TextUtil.isNotNull(pointList)){
				for(LineSegmentInfo point : pointList){
					list.add(point);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 根据获取点的
	 * @param obj
	 * @return
	 */
	List<LineSegmentInfo> getCablePoint(LineSystemInfo obj){
		List<LineSegmentInfo> list= new LinkedList();
		if ((obj.getLatitude() != null) && (!(obj.getLatitude().equals(""))) && 
				 (obj.getLongitude() != null) && (!(obj.getLongitude().equals("")))) {
			 double[] arr = functions.getAround(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()), 1000);
			 obj.setLats(String.valueOf(arr[0]));
			 obj.setLons(String.valueOf(arr[1]));
			 obj.setLate(String.valueOf(arr[2]));
			 obj.setLone(String.valueOf(arr[3]));
			List<Map<String, Object>> resultList = this.jdbcTemplate.queryForList(ResUtil.getCablePoint(obj));
			for(Map<String, Object> map : resultList){
				LineSegmentInfo point = new LineSegmentInfo();
				point.setStartId(map.get("id")+"");
				point.setStartName(map.get("name")+"");
				point.setStartType(map.get("type")+"");
				point.setStartLon(map.get("longitude")+"");
				point.setStartLat(map.get("latitude")+"");
				list.add(point);
			}
			return list;
		}else {
			return null;
		}
	}
	
	/**
	 * 得到地图上的数据
	 * @param obj
	 * @return
	 */
	public List<LineSegmentInfo> getLineGis(LineSystemInfo obj){
		List<LineSegmentInfo> list = new ArrayList<LineSegmentInfo>();
		try{
			//得到点的数据
			LinePointInfo point = new LinePointInfo();
			point.setSysId(obj.getId());
			point.setSysType(obj.getLineType());
			point.setDeleteflag(0);
			String pointSql = ResUtil.getPointStr(point);
			System.out.println("===="+pointSql);
			List<Map<String, Object>> pointList = this.jdbcTemplate.queryForList(pointSql);
			if(TextUtil.isNotNull(pointList)){
				String pointStr  = super.getStrList(pointList, "id");
				LineSegmentInfo segObj = new LineSegmentInfo();
				segObj.setSysId(obj.getId());
				segObj.setSysType(obj.getLineType()+"");
				segObj.setDeleteFlag(0);
				String segSql = ResUtil.getSegStr(segObj);
				List<Map<String, Object>> segList = this.jdbcTemplate.queryForList(segSql);
				if(TextUtil.isNotNull(segList)){
					for(Map<String, Object> segMap : segList){
						//LineSegmentInfo seg = (LineSegmentInfo) BeanUtil.mapToObject(segMap, LineSegmentInfo.class);
						LineSegmentInfo seg = new LineSegmentInfo();
						seg.setSegId(segMap.get("segId")+"");
						seg.setSegName(segMap.get("segName")+"");
						seg.setSegType(segMap.get("segType")+"");
						seg.setStartId(segMap.get("startId")+"");
						seg.setStartName(segMap.get("startName")+"");
						seg.setStartType(segMap.get("startType")+"");
						seg.setStartLat(segMap.get("startLat")+"");
						seg.setStartLon(segMap.get("startLon")+"");
						seg.setEndId(segMap.get("endId")+"");
						seg.setEndName(segMap.get("endName")+"");
						seg.setEndType(segMap.get("endType")+"");
						seg.setEndLat(segMap.get("endLat")+"");
						seg.setEndLon(segMap.get("endLon")+"");
						list.add(seg);
						for(int i=0;i<pointList.size();i++){
							Map<String, Object> pMap = pointList.get(i);
							if((pMap.get("id")+"").equals(seg.getStartId())  ){
								seg.setStartMaintain(pMap.get("maintain")+"");
								pointList.remove(i);
							}
							if((pMap.get("id")+"").equals(seg.getEndId())) {
								seg.setEndMaintain(pMap.get("maintain")+"");
								pointList.remove(i);
							}
						}
					}
				}
			}
			//增加孤点
			if(TextUtil.isNotNull(pointList)){
				for(Map<String, Object> map : pointList){
					LineSegmentInfo line = new LineSegmentInfo();
					line.setStartId(map.get("id")+"");
					line.setStartName(map.get("name")+"");
					line.setStartType(map.get("type")+"");
					line.setStartLon(map.get("longitude")+"");
					line.setStartLat(map.get("latitude")+"");
					line.setStartMaintain(map.get("maintain")+"");
					list.add(line);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 判断资源类型
	 * @param resNum
	 * @param lastUpDate
	 * @param del
	 * @return
	 */
	String getResType(String resNum,String lastUpDate,String del){
		String resType ="add";
		if(TextUtil.isNull(resNum)){
			resType ="add";
		}else{
			if(del.equals("1")){
				resType ="del";
			}else{
				resType ="update";
			}
		}
		return resType;
	}
	
	/**
	 * 修改点归属到
	 * 当前管线系统
	 * @param obj
	 * @param map
	 * @param loginer
	 * @return
	 */
	public int upLinePoint(LineAffiliationInfo obj,Map<String, Object> map,String loginer){
		int result = 0;
		try{
			String direction = "";
			LineSystemInfo lineSys = new LineSystemInfo();
			lineSys.setId(obj.getSysId());
			lineSys = (LineSystemInfo) this.getObject("lineSystem.getLineSysObj", lineSys);
			if(TextUtil.isNotNull(lineSys.getDirection())){
				String sql = "select text as direction from dicview"
						+ " where enName ='wellDirection' and value = '"+lineSys.getDirection()+"'";
				Map<String, Object> dirMap = this.jdbcTemplate.queryForMap(sql);
				if(dirMap != null){
					direction = dirMap.get("direction")+"";
				}
			}
			//电杆数据
			if(obj.getType().equals("pole") || obj.getType().equals(0)){
				PoleInfoBean pole = new PoleInfoBean();
				pole.setPoleId(obj.getPointId());
				pole = (PoleInfoBean) getObject("pdapoleline.getPole", pole);
				if(TextUtil.isNotNull(pole.getPoleName())){
					if(null == map){
						pole.setPoleNo("1");
						pole.setPoleName(lineSys.getLineName()+"_"+direction+"1号杆");
					}else{
						Integer startNum = Integer.parseInt(map.get("poleNo")+"");
						pole.setPoleNo((startNum+1)+"");
						pole.setPoleName(lineSys.getLineName()+"_"+direction+""+(startNum+1)+"号杆");
					}
					pole.setPoleLineId(lineSys.getId());
					pole.setMaintenanceOrgId(lineSys.getPropertyComp()+"");
					pole.setMaintenanceModeEnumId(lineSys.getPropertyNature());
					pole.setPropertyNature(lineSys.getPropertyNature());
					pole.setDataQualityPrincipal(loginer);
					pole.setDirection(lineSys.getDirection());
					pole.setParties(loginer);
					result = this.update("pdapoleline.updatePole", pole);
					//增加一个段
					if(null != map){
						PolelineSegmentInfoBean poleSeg = new PolelineSegmentInfoBean();
						poleSeg.setPoleLineId(pole.getPoleLineId());
						poleSeg.setPoleLineSegmentName(map.get("poleName")+"-"+pole.getPoleName()+"杆路");
						poleSeg.setStartDeviceId(Integer.parseInt(map.get("poleId")+""));
						poleSeg.setEndDeviceId(pole.getPoleId());
						poleSeg.setConstructionSharingEnumId(0);
						poleSeg.setCstate("2");
						poleSeg.setMaintenanceAreaName(pole.getMaintenanceAreaName());
						poleSeg.setDataQualityPrincipal(loginer);
						poleSeg.setParties(loginer);
						poleSeg.setConstructionSharingOrg(lineSys.getPropertyComp()+"");
						poleSeg.setConstructionSharingEnumId(lineSys.getPropertyNature());
						poleSeg.setPoleLineSegmentLength(this.getDistince(pole.getPoleLongitude(), pole.getPoleLatitude(), map.get("poleLongitude")+"", map.get("poleLatitude")+""));
						poleSeg.setMaintainLength(poleSeg.getPoleLineSegmentLength());
						this.insert("pdapoleline.insertPolelineSegment", poleSeg);
					}
				}
			}else if(obj.getType().equals("stone") || obj.getType().equals(1)){
				//标石
				StoneInfoBean stone = new StoneInfoBean();
				stone.setStoneId(obj.getPointId());
				stone = (StoneInfoBean) getObject("stone.getStone", stone);
				if(TextUtil.isNotNull(stone.getStoneName())){
					if(null == map){
						stone.setStoneNum("1");
						stone.setStoneName(lineSys.getLineName()+"_"+direction+"1号标石");
					}else{
						Integer startNum = Integer.parseInt(map.get("stoneNum")+"");
						stone.setStoneNum((startNum+1)+"");
						stone.setStoneName(lineSys.getLineName()+"_"+direction+""+(startNum+1)+"号标石");
					}
					stone.setBuriedId(lineSys.getId()+"");
					stone.setDataQualitier(loginer);
					stone.setMaintainer(loginer);
					stone.setPropertyComp(lineSys.getPropertyComp());
					stone.setPropertyNature(lineSys.getPropertyNature());
					stone.setStonePosition(lineSys.getDirection());
					result = this.update("stone.updateStone",stone);
					//增加一个段
					if(null != map){
						BuriedPartObj buriedSeg = new BuriedPartObj();
						buriedSeg.setBuriedId(obj.getSysId()+"");
						buriedSeg.setBuriedPartName(map.get("stoneName")+"-"+stone.getStoneName()+"直埋");
						buriedSeg.setBuriedPartStartId(map.get("stoneId")+"");
						buriedSeg.setBuriedPartStart(map.get("stoneName")+"");
						buriedSeg.setBuriedPartEnd(stone.getStoneName());
						buriedSeg.setBuriedPartEndId(stone.getStoneId()+"");
						buriedSeg.setBuriedPartArea(lineSys.getLineArea());
						buriedSeg.setDataQualitier(loginer);
						buriedSeg.setPropertyDept(lineSys.getPropertyComp());
						buriedSeg.setPropertyRight(lineSys.getPropertyNature());
						buriedSeg.setMaintainer(loginer);
						buriedSeg.setBuriedPartLength(this.getDistince(stone.getLongitude(), stone.getLatitude(), map.get("longitude")+"", map.get("latitude")+""));
						buriedSeg.setMaintainLength(buriedSeg.getBuriedPartLength());
						this.insert("buriedPart.insertBuriedPart", buriedSeg);
					}
				}
			}else if(obj.getType().equals("well") || obj.getType().equals(2)){
				//管井数据
				WellInfoBean well = new WellInfoBean();
				well.setWellId(obj.getPointId());
				well = (WellInfoBean) getObject("pdapipe.getWell", well);
				if(TextUtil.isNotNull(well.getWellName())){
					if(null == map){
						well.setWellNo("1");
						well.setWellName(lineSys.getLineName()+"_"+direction+"1号井");
					}else{
						Integer startNum = Integer.parseInt(map.get("wellNo")+"");
						well.setWellNo((startNum+1)+"");
						well.setWellName(lineSys.getLineName()+"_"+direction+""+(startNum+1)+"号井");
					}
					well.setPipeId(lineSys.getId()+"");
					well.setDataQualityPrincipal(loginer);
					well.setDirection(direction);
					well.setConstructionSharingEnumId(lineSys.getPropertyNature());
					well.setConstructionSharingOrg(lineSys.getPropertyComp()+"");
					well.setParties(loginer);
					result = this.update("pdapipe.updateWell", well);
					
					if(null != map){
						PipeSegmentInfoBean seg = new PipeSegmentInfoBean();
						seg.setPipeId(obj.getSysId());
						seg.setPipeSegmentName(map.get("wellName")+"-"+well.getWellName()+"管道");
						seg.setStartDeviceId(Integer.parseInt(map.get("wellId")+""));
						seg.setEndDeviceId(well.getWellId());
						seg.setStartDeviceName(map.get("wellName")+"");
						seg.setEndDeviceName(well.getWellName());
						seg.setMaintenanceAreaName(lineSys.getLineArea());
						seg.setSharingTypeEnumId(lineSys.getPropertyNature());
						seg.setConstructionSharingOrg(lineSys.getPropertyComp()+"");
						seg.setDataQualityPrincipal(loginer);
						seg.setParties(loginer);
						seg.setPipeSegmentLength(this.getDistince(well.getLongitude(), well.getLatitude(), map.get("longitude")+"", map.get("latitude")+""));
						seg.setMaintainLength(seg.getPipeSegmentLength());
						this.insert("pdapipe.insertPipeSegment", seg);
					}
				}
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public PDAPipeService getPdaPipeService() {
		return pdaPipeService;
	}
	public void setPdaPipeService(PDAPipeService pdaPipeService) {
		this.pdaPipeService = pdaPipeService;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
