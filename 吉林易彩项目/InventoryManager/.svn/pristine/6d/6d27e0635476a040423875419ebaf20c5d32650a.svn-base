package interfaces.pdainterface.GIS.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import manage.buriedPart.pojo.BuriedPartObj;
import manage.equt.pojo.EqutInfoBean;
import manage.generator.pojo.StationBaseInfoBean;
import manage.opticalTerminal.pojo.OpticalTerminalObj;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.pipe.pojo.WellInfoBean;
import manage.poleline.pojo.PoleInfoBean;
import manage.poleline.pojo.PolelineSegmentInfoBean;
import manage.route.pojo.FiberBoxInfoBean;
import manage.stone.pojo.StoneInfoBean;

import org.springframework.jdbc.core.JdbcTemplate;

import base.database.DataBase;
import base.util.MapUtil;
import base.util.TextUtil;
import base.util.pojo.Point;
import interfaces.pdainterface.GIS.pojo.GisPojo;
import interfaces.pdainterface.GIS.service.impl.IPDAGisService;

public class PDAGisService extends DataBase implements IPDAGisService{
	
	private JdbcTemplate jdbcTemplate;
	private static String siteSTR = "pdagenerator.getStationBase";
	private static String equtSTR = "pdaequt.getEqut";
	
	/**
	 * 查询得到站点数据
	 * @param station
	 * @return
	 */
	public  List<StationBaseInfoBean> getStation(StationBaseInfoBean station){
		return getObjects(siteSTR, station);
	}
	
	
	/**
	 * 得到光交箱
	 * @param equt
	 * @return
	 */
	public List<EqutInfoBean> getEqut(EqutInfoBean equt){
		return getObjects(equtSTR, equt);
	}
	
	
	/**
	 * 得到光终端盒
	 * @param obj
	 * @return
	 */
	public List<OpticalTerminalObj> getOpticalTerminal(OpticalTerminalObj obj){
		return getObjects("opticalTer.getOpticalTerGrid", obj);
	}
	
	
	/**
	 * 得到分纤箱
	 * @param obj
	 * @return
	 */
	public List<FiberBoxInfoBean> getFiberBox(FiberBoxInfoBean obj){
		return getObjects("pdaroute.getFiberbox", obj);
	}
	
	/**
	 * 得到引上的列表
	 * @param pojo
	 * @return
	 */
	public List<GisPojo> getLeadUp(GisPojo pojo){
		List<GisPojo> result = new ArrayList<GisPojo>();
		String sql = this.getSpotStr(pojo);
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			for(Map<String, Object> map : list){
				GisPojo obj = new GisPojo();
				obj.setLineId(map.get("id")+"");
				obj.setLineName(map.get("leadupName")+"");
				obj.setLineType(pojo.getType());
				obj.setStartId(map.get("startId")+"");
				obj.setStartName(map.get("startName")+"");
				obj.setStartType(map.get("startType")+"");
				obj.setStartLon(map.get("startLon")+"");
				obj.setStartLat(map.get("startLat")+"");
				obj.setEndId(map.get("endId")+"");
				obj.setEndName(map.get("endName")+"");
				obj.setEndType(map.get("endType")+"");
				obj.setEndLon(map.get("endLon")+"");
				obj.setEndLat(map.get("endLat")+"");
				result.add(obj);
			}
		}
		return result;
	}
	
	
	/**
	 * 得到管线数据
	 * @param pojo
	 * @return
	 */
	public List<GisPojo> getGisLine(GisPojo pojo){
		List<GisPojo> result = new ArrayList<GisPojo>();
		StringBuffer sb = new StringBuffer();
		String sql=this.getSpotStr(pojo);
		List<Map<String, Object>> wellList = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(wellList)){
			String id = super.getStrList(wellList, "id");
			String pipeSql=this.getLineStr(id, pojo.getType());
			List<Map<String, Object>> pipeList = this.jdbcTemplate.queryForList(pipeSql);
			String startId = super.getStrList(pipeList, "startId");
			String endId = super.getStrList(pipeList, "endId");
			//分组数据
			Map<String, String> spotMap = new HashMap<String, String>();
			for(int i=0;i<wellList.size();i++){
				Map<String, Object> order = wellList.get(i);
				String isSys = order.get("isSys")+"";
				if(TextUtil.isNull(isSys)) {
					isSys="false";
				}else {
					isSys="true";
				}
				spotMap.put(order.get("id")+"",order.get("name")+"%_%"+order.get("longitude")+"%_%"+order.get("latitude")+"%_%"+isSys);
			}
			LinkedHashMap<String, String> spotLinked = new LinkedHashMap<String, String>();
			String spotType = "";
			if(pojo.getType().equals("pipe")){
				spotType ="well";
			}else if(pojo.getType().equals("poleLine")){
				spotType ="pole";
			}else if(pojo.getType().equals("buried")){
				spotType ="stone";
			}else if(pojo.getType().equals("hangWall")){
				spotType = "support";
			}
			for(int i=0;i<pipeList.size();i++){
				Map<String, Object> lineMap = pipeList.get(i);
				GisPojo obj = new GisPojo();
				obj.setLineId(lineMap.get("lineId")+"");
				obj.setLineName(lineMap.get("lineName")+"");
				obj.setLineType(pojo.getType());
				String start = spotMap.get(lineMap.get("startId")+"");
				spotLinked.put(lineMap.get("startId")+"", lineMap.get("startId")+"");
				String end = spotMap.get(lineMap.get("endId")+"");
				spotLinked.put(lineMap.get("endId")+"", lineMap.get("endId")+"");
				obj.setStartId(lineMap.get("startId")+"");
				obj.setStartName(start.split("%_%")[0]);
				obj.setStartType(spotType);
				obj.setStartLon(start.split("%_%")[1]);
				obj.setStartLat(start.split("%_%")[2]);
				obj.setStartSys(start.split("%_%")[3]);
				obj.setEndId(lineMap.get("endId")+"");
				obj.setEndName(end.split("%_%")[0]);
				obj.setEndLon(end.split("%_%")[1]);
				obj.setEndLat(end.split("%_%")[2]);
				obj.setEndSys(end.split("%_%")[3]);
				obj.setEndType(spotType);
				result.add(obj);
			}
			for(int i=0;i<wellList.size();i++){
				Map<String, Object> spotEnd = wellList.get(i);
				if(!((spotEnd.get("id")+"").equals(spotLinked.get(spotEnd.get("id")+"")))){
					GisPojo obj = new GisPojo();
					obj.setStartId(spotEnd.get("id")+"");
					obj.setStartName(spotEnd.get("name")+"");
					obj.setStartType(spotType);
					obj.setStartLon(spotEnd.get("longitude")+"");
					obj.setStartLat(spotEnd.get("latitude")+"");
					String isSys = spotEnd.get("isSys")+"";
					if(TextUtil.isNull(isSys)) {
						isSys="false";
					}else {
						isSys="";
					}
					obj.setStartSys(isSys);
					result.add(obj);
				}
			}
		}
		return result;
	}
	
	/**
	 * 得到点的数据
	 * @param pojo
	 * @return
	 */
	String getSpotStr(GisPojo pojo){
		String sql = "";
		//得到管井的数据
		if(pojo.getType().equals("pipe")){
			if(TextUtil.isNotNull(pojo.getName())){
				sql=""
					+ "select t.wellId as id,t.wellName as name,pipeId as isSys,"
					+ " t.longitude as longitude,t.latitude as latitude"
					+ " from wellinfo t"
					+ " where t.deletedFlag ='0'"
					+ " and t.wellName like '%"+pojo.getName()+"%'"
					+ " limit 100"
					+ "";
			}else{
				 sql=""
					+ "select t.wellId as id,t.wellName as name,pipeId as isSys,"
					+ " t.longitude as longitude,t.latitude as latitude"
					+ " from wellinfo t"
					+ " where t.deletedFlag ='0'"
					+ " and (t.longitude >='"+pojo.getLonl()+"' and t.longitude <= '"+pojo.getLonh()+"'"
					+ " and t.latitude >='"+pojo.getLatl()+"' and t.latitude <= '"+pojo.getLath()+"')"
					+ "";
				 /*if(TextUtil.isNotNull(pojo.getIsSys()) && pojo.getIsSys().equals("true")){
					 sql+=" and t.pipeId is not null ";
				 }*/
			}
		}
		//得到杆路数据
		if(pojo.getType().equals("poleLine")){
			if(TextUtil.isNotNull(pojo.getName())){
				sql =""
					+ " SELECT t.poleId as id ,t.poleName as name,poleLineId as isSys,"
					+ " t.poleLongitude as longitude, t.poleLatitude as latitude"
					+ " FROM poleinfo t where t.deletedFlag ='0'"
					+ " and t.poleName like '%"+pojo.getName()+"%'"
					+ " limit 100"
					+ "";
			}else{
				sql =""
					+ " SELECT t.poleId as id ,t.poleName as name,poleLineId as isSys,"
					+ " t.poleLongitude as longitude, t.poleLatitude as latitude"
					+ " FROM poleinfo t where t.deletedFlag ='0'"
					+ " and (t.poleLongitude >='"+pojo.getLonl()+"' and t.poleLongitude <= '"+pojo.getLonh()+"'"
					+ " and t.poleLatitude >='"+pojo.getLatl()+"' and t.poleLatitude <= '"+pojo.getLath()+"')"
					+ "";
				 /*if(TextUtil.isNotNull(pojo.getIsSys()) && pojo.getIsSys().equals("true")){
					sql+=" and t.poleLineId is not null ";
				}*/
			}
		}
		//得到直埋
		if(pojo.getType().equals("buried")){
			if(TextUtil.isNotNull(pojo.getName())){
				sql =""
					+ " select t.stoneId as id,t.stoneName as name, "
					+ " t.latitude as latitude,t.longitude as longitude"
					+ " from stoneinfo t "
					+ " where t.deleteflag ='0'"
					+ " and t.stoneName like '%"+pojo.getName()+"%'"
					+ " limit 100"
					+ "";
			}else{
				sql =""
					+ " select t.stoneId as id,t.stoneName as name, "
					+ " t.latitude as latitude,t.longitude as longitude"
					+ " from stoneinfo t "
					+ " where t.deleteflag ='0'"
					+ " and (t.longitude >='"+pojo.getLonl()+"' and t.longitude <= '"+pojo.getLonh()+"'"
					+ " and t.latitude >='"+pojo.getLatl()+"' and t.latitude <= '"+pojo.getLath()+"')"
					+ "";
				 /*if(TextUtil.isNotNull(pojo.getIsSys()) && pojo.getIsSys().equals("true")){
					sql +=" and t.buriedId is not null ";
				}*/
			}
		}
		//得到撑点和挂墙
		if(pojo.getType().equals("hangWall")){
			if(TextUtil.isNotNull(pojo.getName())){
				sql =""
					+ "select t.id as id ,t.sportName as name,"
					+ " t.latitude as latitude,t.longitude as longitude"
					+ " from job_support_point t"
					+ " where t.deleteflag =0 "
					+ " and t.sportName like'%"+pojo.getName()+"%' "
					+ " limit 100";
			}else{
				sql =""
					+ "select t.id as id ,t.sportName as name,"
					+ " t.latitude as latitude,t.longitude as longitude"
					+ " from job_support_point t"
					+ " where t.deleteflag =0 "
					+ " and (t.longitude >='"+pojo.getLonl()+"' and t.longitude <= '"+pojo.getLonh()+"'"
					+ " and t.latitude >='"+pojo.getLatl()+"' and t.latitude <= '"+pojo.getLath()+"')"
					+ " ";
			}
		}
		if(pojo.getType().equals("leadup")){
			sql =" select t.id ,t.leadupName,"
					+ " case t.startType "
					+ " when 0 then 'pole' "
					+ " when 1 then 'stone' "
					+ " when 2 then 'well'"
					+ " when 3 then 'support'"
					+ " when 4 then 'site'"
					+ " when 5 then 'optical'"
					+ " else '' end as startType,"
					+ " t.startLon,t.startLat,"
					+ " t.startId,t.startName,"
					+ " case t.endType"
					+ " when 0 then 'pole'"
					+ " when 1 then 'stone'"
					+ " when 2 then 'well'"
					+ " when 3 then 'support'"
					+ " when 4 then 'site'"
					+ " when 5 then 'optical'"
					+ " else '' end as endType,"
					+ " t.endId,t.endName,"
					+ " t.endLon,t.endLat "
				+ " from leadupstage t  where t.deleteflag = 0 "
				+ " and (t.longitude >='"+pojo.getLonl()+"' and t.longitude <= '"+pojo.getLonh()+"'"
				+ " and t.latitude >='"+pojo.getLatl()+"' and t.latitude <= '"+pojo.getLath()+"')"
				+ " ";
		}
		return sql;
	}
	
	
	/**
	 * 得到段的语句
	 * @param id
	 * @param type
	 * @return
	 */
	String getLineStr(String id,String type){
		String sql = "";
		if(type.equals("pipe")){
			sql ="select pipeSegmentId as lineId,"
				+ " pipeSegmentName as lineName,pipeId as isSys,"
				+ " startDeviceId as startId,endDeviceId as endId"
				+ " from pipesegmentinfo where deletedFlag ='0' and startDeviceId in ("
				+ ""+id+""
				+ ") and  endDeviceId in ("
				+ ""+id+" "
				+ ")";
		}
		if(type.equals("poleLine")){
			sql = " select poleLineSegmentId as lineId,poleLineSegmentName as lineName,"
					+ " poleLineId as isSys,"
				+ " startDeviceId as startId,endDeviceId as endId from polelinesegmentinfo"
				+ " where deletedFlag ='0' and startDeviceId in ("
				+ ""+id+""
				+ ") and  endDeviceId in ("
				+ ""+id+""
				+ ")";
		}
		if(type.equals("buried")){
			sql=" select  id as lineId,buriedPartName as lineName,"
					+ " buriedId as isSys, "
				+ " buriedPartStartId as startId,buriedPartEndId as endId"
				+ " from buriedpartinfo where deleteflag ='0' and buriedPartStartId in ("
				+ ""+id+""
				+ ") and  buriedPartEndId in ("
				+ ""+id+""
				+ ")";
		}
		/*if(type.equals("hangWall")){
			sql="select t.id as lineId,t.hangWallName as lineName,"
				+ " t.startDeviceId as startId,t.endDeviceId as endId"
				+ " from hang_wall t where deleteflag ='0' and startDeviceId in ("
				+ ""+id+""
				+ ") and  endDeviceId in ("
				+ ""+id+""
				+ ")";
		}*/
		return sql;
	}
	
	/**
	 * 管线的数据
	 * @param pojo
	 * @return
	 */
	public int getPipeCount(GisPojo pojo){
		String sql = " select count(1) ";
		if(pojo.getType().equals("pipe")){
			sql += " from pipeview t ";
		}else if(pojo.getType().equals("pole")){
			sql += " from poleview t ";
		}else if(pojo.getType().equals("buried")){
			sql += " from buriedview t ";
		}
		sql += " where 1=1 ";
		sql += this.getPipeStr(pojo);
		return this.jdbcTemplate.queryForInt(sql);
	}
	
	String getPipeStr(GisPojo pojo){
		StringBuffer sb = new StringBuffer();
		if(TextUtil.isNotNull(pojo.getLat()) && TextUtil.isNotNull(pojo.getLon())){
			sb.append(" and ((t.startLat >= "+pojo.getLatl()+" and t.startLat <= "+pojo.getLath()+""
					       + " and t.startLon >= "+pojo.getLonl()+" and t.startLon <= "+pojo.getLonh()+")"
					       + " or ("
					       + " t.endLat >= "+pojo.getLatl()+" and t.endLat <= "+pojo.getLath()+" "
					       + " and t.endLon >= "+pojo.getLonl()+" and t.endLon <= "+pojo.getLonh()+""
					       + "))");
		}
		if(TextUtil.isNotNull(pojo.getName())){
			sb.append(" and ( t.startName like '%"+pojo.getName()+"%' or t.endName like '%"+pojo.getName()+"%')");
		}
		if(TextUtil.isNotNull(pojo.getExtendSql())){
			sb.append(pojo.getExtendSql());
		}
		
		return sb.toString();
	}
	
	/**
	 * 删除资源点信息
	 * @param list
	 * @return
	 */
	public String delRes(List<GisPojo> list,String realName){
		String result = "";
		try{
			String resId = this.getResId(list);
			GisPojo gis = list.get(0);
			String sql = "";
			//电杆
			if(gis.getType().equals("pole")){
				sql = "update poleinfo "
					+ " set deletedFlag = 1,lastUpdateDate = now(),parties = '"+realName+"'"
					+ " where poleId in ("+resId+")"
					+ " and ("
					+ " parties = '"+realName+"' or parties is null "
					+ " )";
			}else if(gis.getType().equals("well")){
				sql = "update wellinfo"
					+ " set lastUpdateDate = now() ,deletedFlag = 1 ,deletionDate = now(),parties ='"+realName+"'"
					+ " where wellId in ("+resId+")"
					+ " and (parties = '"+realName+"' or parties is null)";
			}else if(gis.getType().equals("stone")){
				sql = "update stoneinfo"
					+ " set maintainer ='"+realName+"' ,deleteflag = 1 ,lastUpTime = now() "
					+ " where stoneId in("+resId+")"
					+ " and (maintainer = '"+realName+"' or maintainer is null)";
			}else if(gis.getType().equals("support")){
				sql = "update job_support_point"
					+ " set maintainer ='"+realName+"',deleteflag = 1,lastUpTime = now()"
					+ " where id in ("+resId+")"
					+ " and (maintainer is null or maintainer = '"+realName+"') ";
			}else if(gis.getType().equals("optical")){
				sql = "update job_equtinfo"
					+ " set parties= '"+realName+"',del = 1, lastUpdateDate =now()"
					+ " where EID in("+resId+")"
					+ " and (parties is null or parties = '"+realName+"')";
			}else if(gis.getType().equals("opticalTerminal")){
				sql = "update optical_terminal"
					+ " set maintainer ='"+realName+"',deleteflag =1 ,lastUpTime = now()"
					+ " where id in ("+resId+")"
					+ " and (maintainer is null or maintainer ='"+realName+"')";
			}else if(gis.getType().equals("fiberBox")){
				sql = "update job_fiberbox"
					+ " set deleteflag =1 ,maintainer ='"+realName+"',lastUpTime = now()"
					+ " where id in ("+resId+")"
					+ " and (maintainer is null or maintainer ='"+realName+"')";
			}
			//删除点信息
			this.jdbcTemplate.execute(sql);
			//删除段信息
			new delSegment(resId, gis.getType(), realName).start();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	class delSegment extends Thread{
		private String resId;
		private String type;
		private String realName;
		public delSegment(String resId,String type,String realName){
			this.resId = resId;
			this.type = type;
			this.realName = realName;
		}
		@Override
		public void run() {
			delSegment(resId, type, realName);
			super.run();
		}
	}
	
	/**
	 * 删除段信息
	 * @param resId
	 * @param type
	 * @return
	 */
	void delSegment(String resId, String type,String realName){
		String sql = "";
		if(type.equals("pole")){
			sql = "update polelinesegmentinfo "
				+ " set deletedFlag = 1, deletionDate = now() ,parties = '"+realName+"'"
				+ " where startDeviceId in ("+resId+") or endDeviceId in ("+resId+")"
				+ "";
		}else if(type.equals("well")){
			sql = "update pipesegmentinfo"
				+ " set lastUpdateDate = now(),deletedFlag = 1, deletionDate = now(),parties = '"+realName+"'"
				+ " where startDeviceId in ("+resId+") or endDeviceId in ("+resId+")";
		}else if(type.equals("stone")){
			sql ="update buriedpartinfo"
				+ " set maintainer = '"+realName+"' ,deleteflag = 1, lastUpTime = now()"
				+ " where buriedPartStartId in ("+resId+") or buriedPartEndId in ("+resId+")";
		}else if(type.equals("support")){
			sql = "update hang_wall"
				+ " set maintainer = '"+realName+"',deleteflag = 1, lastUpTime =now()"
				+ " where startDeviceId in ("+resId+") or endDeviceId in ("+resId+")"
				+ "";
		}
		if(TextUtil.isNotNull(sql)){
			this.jdbcTemplate.execute(sql);
		}
	}
	
	
	/**
	 * 截取段信息
	 * @param obj
	 * @return
	 */
	/**
	 * @param obj
	 * @return
	 */
	public String cutoffSeg(GisPojo obj){
		String result = "";
		try{
			if(obj.getLineType().equals("pipe")){
				PipeSegmentInfoBean pipe = new PipeSegmentInfoBean();
				pipe.setPipeSegmentId(Integer.parseInt(obj.getLineId()));
				pipe = (PipeSegmentInfoBean) this.getObject("pdapipe.getPipeSegment", pipe);
				result = this.cutoffPipe(pipe);
			}
			if(obj.getLineType().equals("poleLine")){
				PolelineSegmentInfoBean poleLine = new PolelineSegmentInfoBean();
				poleLine.setPoleLineSegmentId(Integer.parseInt(obj.getLineId()));
				poleLine = (PolelineSegmentInfoBean) this.getObject("pdapoleline.getPolelineSegment", poleLine);
				result = this.cutoffPole(poleLine);
			}
			if(obj.getLineType().equals("buried")){
				BuriedPartObj buried = new BuriedPartObj();
				buried.setId(Integer.parseInt(obj.getLineId()));
				buried = (BuriedPartObj) this.getObject("buriedPart.getBuriedPart", buried);
				result= this.cutoffBuried(buried);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	String cutoffBuried(BuriedPartObj buried){
		String result= "";
		String ids = buried.getBuriedPartStartId()+","+buried.getBuriedPartEndId();
		List<StoneInfoBean> list = this.getObjects("stone.getStoneByids", ids);
		if(TextUtil.isNotNull(list) && list.size() ==2){
			StoneInfoBean start = list.get(0);
			StoneInfoBean end = list.get(1);
			StoneInfoBean middle = (StoneInfoBean) start.clone();
			middle.setStoneId(null);
			middle.setResNum(null);
			if(middle.getStoneName().contains("号标石")){
				String stoneName = middle.getStoneName().replace("号标石", "-?号标石");
				middle.setStoneName(stoneName);
			}else{
				middle.setStoneName(middle.getStoneName()+"-?");
			}
			Point point = this.getMiddlePoint(start.getLongitude(), start.getLatitude(), end.getLongitude(), end.getLatitude());
			middle.setLongitude(point.getLng()+"");
			middle.setLatitude(point.getLat()+"");
			int stoneId = ((Integer)insert("stone.insertStone", middle)).intValue();
			middle.setStoneId(stoneId);
			
			BuriedPartObj startBuried = (BuriedPartObj) buried.clone();
			startBuried.setId(null);
			startBuried.setResNum(null);
			startBuried.setBuriedPartStart(start.getStoneName());
			startBuried.setBuriedPartStartId(start.getStoneId()+"");
			startBuried.setBuriedPartEnd(middle.getStoneName());
			startBuried.setBuriedPartEndId(middle.getStoneId()+"");
			startBuried.setBuriedPartName(start.getStoneName()+"-"+middle.getStoneName()+"直埋");
			startBuried.setBuriedPartLength(MapUtil.Distance(Double.parseDouble(start.getLongitude()),
					Double.parseDouble(start.getLatitude()), 
					Double.parseDouble(middle.getLongitude()),
					Double.parseDouble(middle.getLatitude())));
			if(TextUtil.isNotNull(startBuried.getMaintainLength())){
				startBuried.setMaintainLength(Math.ceil(Double.parseDouble(startBuried.getMaintainLength()) /2)+"");
			}
			BuriedPartObj endBuried =(BuriedPartObj)buried.clone();
			endBuried.setId(null);
			endBuried.setResNum(null);
			endBuried.setBuriedPartStart(middle.getStoneName());
			endBuried.setBuriedPartStartId(middle.getStoneId()+"");
			endBuried.setBuriedPartEnd(end.getStoneName());
			endBuried.setBuriedPartEndId(end.getStoneId()+"");
			endBuried.setBuriedPartName(middle.getStoneName()+"-"+end.getStoneName()+"直埋");
			endBuried.setBuriedPartLength(MapUtil.Distance(Double.parseDouble(middle.getLongitude()),
					Double.parseDouble(middle.getLatitude()), 
					Double.parseDouble(end.getLongitude()),
					Double.parseDouble(end.getLatitude())));
			if(TextUtil.isNotNull(endBuried.getMaintainLength())){
				endBuried.setMaintainLength(Math.ceil(Double.parseDouble(endBuried.getMaintainLength()) /2)+"");
			}
			buried.setDeleteflag("1");
			
			int startid = ((Integer)insert("buriedPart.insertBuriedPart", startBuried));
			int endid = ((Integer)insert("buriedPart.insertBuriedPart", endBuried));
			Integer.valueOf(update("buriedPart.updateBuriedPart", buried));
			
			result ="success";
		}
		return result;
	}
	
	/**
	 * 切割杆路
	 * @param poleLine
	 * @return
	 */
	String cutoffPole(PolelineSegmentInfoBean poleLine){
		String result = "";
		String ids = poleLine.getStartDeviceId()+","+poleLine.getEndDeviceId();
		List<PoleInfoBean> list = this.getObjects("pdapoleline.getPoleIds", ids);
		if(TextUtil.isNotNull(list) && list.size() == 2){
			PoleInfoBean start = list.get(0);
			PoleInfoBean end = list.get(1);
			PoleInfoBean middle = (PoleInfoBean) start.clone();
			middle.setPoleId(null);
			middle.setResNum(null);
			if(middle.getPoleName().contains("号杆")){
				String poleName = middle.getPoleName().replace("号杆", "-?号杆");
				middle.setPoleName(poleName);
			}else{
				middle.setPoleName(middle.getPoleName()+"-?");
			}
			Point point = this.getMiddlePoint(start.getPoleLongitude(), start.getPoleLatitude(), end.getPoleLongitude(), end.getPoleLatitude());
			middle.setPoleLongitude(point.getLng()+"");
			middle.setPoleLatitude(point.getLat()+"");
			int poleId = ((Integer)insert("pdapoleline.insertPole", middle)).intValue();
			middle.setPoleId(poleId);
			
			PolelineSegmentInfoBean startLine = (PolelineSegmentInfoBean) poleLine.clone();
			startLine.setPoleLineSegmentId(null);
			startLine.setResNum(null);
			startLine.setStartDeviceId(start.getPoleId());
			startLine.setStartDeviceName(start.getPoleName());
			startLine.setEndDeviceId(middle.getPoleId());
			startLine.setEndDeviceName(middle.getPoleName());
			startLine.setPoleLineSegmentName(start.getPoleName()+"-"+middle.getPoleName()+"杆路");
			startLine.setPoleLineSegmentLength(MapUtil.Distance(Double.parseDouble(start.getPoleLongitude()),
					Double.parseDouble(start.getPoleLatitude()), 
					Double.parseDouble(middle.getPoleLongitude()),
					Double.parseDouble(middle.getPoleLatitude())));
			if(TextUtil.isNotNull(startLine.getMaintainLength())){
				startLine.setMaintainLength(Math.ceil(Double.parseDouble(startLine.getMaintainLength()) /2)+"");
			}
			PolelineSegmentInfoBean endLine = (PolelineSegmentInfoBean) poleLine.clone();
			endLine.setPoleLineSegmentId(null);
			endLine.setResNum(null);
			endLine.setStartDeviceId(middle.getPoleId());
			endLine.setStartDeviceName(middle.getPoleName());
			endLine.setEndDeviceId(end.getPoleId());
			endLine.setEndDeviceName(end.getPoleName());
			endLine.setPoleLineSegmentName(middle.getPoleName()+"-"+end.getPoleName()+"杆路");
			endLine.setPoleLineSegmentLength(MapUtil.Distance(Double.parseDouble(middle.getPoleLongitude()),
					Double.parseDouble(middle.getPoleLatitude()), 
					Double.parseDouble(end.getPoleLongitude()),
					Double.parseDouble(end.getPoleLatitude())));
			if(TextUtil.isNotNull(endLine.getMaintainLength())){
				endLine.setMaintainLength(Math.ceil(Double.parseDouble(endLine.getMaintainLength()) /2)+"");
			}
			
			poleLine.setDeletedFlag("1");
			int startid = ((Integer)insert("pdapoleline.insertPolelineSegment", startLine));
			int endid = ((Integer)insert("pdapoleline.insertPolelineSegment", endLine));
			Integer.valueOf(update("pdapoleline.updatePolelineSegment", poleLine));
			
			result ="success";
		}
		
		return result;
	}
	
	
	/**
	 * 切分管道段
	 * @param pipe
	 */
	String cutoffPipe(PipeSegmentInfoBean pipe){
		String ids = pipe.getStartDeviceId()+","+pipe.getEndDeviceId();
		List<WellInfoBean> list = this.getObjects("pdapipe.getWellByids", ids);
		String result = "";
		if(TextUtil.isNotNull(list) && list.size() ==2){
			WellInfoBean start = list.get(0);
			WellInfoBean end = list.get(1);
			WellInfoBean middle = (WellInfoBean) start.clone();
			middle.setWellId(null);
			if(middle.getWellName().contains("号井")){
				String wellName = middle.getWellName().replace("号井", "-?号井");
				middle.setWellName(wellName);
			}else{
				middle.setWellName(middle.getWellName()+"-?");
			}
			Point point = this.getMiddlePoint(start.getLongitude(), start.getLatitude(), end.getLongitude(), end.getLatitude());
			middle.setLongitude(point.getLng()+"");
			middle.setLatitude(point.getLat()+"");
			middle.setResNum(null);
			middle.setWellNo(start.getWellNo()+"-"+end.getWellNo());
			int wellId = ((Integer)insert("pdapipe.insertWell", middle)).intValue();
			middle.setWellId(wellId);
			PipeSegmentInfoBean startPipe = (PipeSegmentInfoBean) pipe.clone();
			startPipe.setPipeSegmentId(null);
			startPipe.setResNum(null);
			startPipe.setStartDeviceId(start.getWellId());
			startPipe.setStartDeviceName(start.getWellName());
			startPipe.setEndDeviceId(middle.getWellId());
			startPipe.setEndDeviceName(middle.getWellName());
			startPipe.setPipeSegmentName(start.getWellName()+"-"+middle.getWellName()+"管道");
			startPipe.setPipeSegmentLength(MapUtil.Distance(Double.parseDouble(start.getLongitude()),
					Double.parseDouble(start.getLatitude()), 
					Double.parseDouble(middle.getLongitude()),
					Double.parseDouble(middle.getLatitude())));
			if(TextUtil.isNotNull(startPipe.getMaintainLength())){
				startPipe.setMaintainLength(((Double.parseDouble(startPipe.getMaintainLength()))/2)+"");
			}
			PipeSegmentInfoBean endPipe = (PipeSegmentInfoBean) pipe.clone();
			endPipe.setPipeSegmentId(null);
			endPipe.setResNum(null);
			endPipe.setStartDeviceId(middle.getWellId());
			endPipe.setStartDeviceName(middle.getWellName());
			endPipe.setEndDeviceId(end.getWellId());
			endPipe.setEndDeviceName(end.getWellName());
			endPipe.setPipeSegmentName(middle.getWellName()+"-"+end.getWellName()+"管道");
			endPipe.setPipeSegmentLength(MapUtil.Distance(Double.parseDouble(middle.getLongitude()),
					Double.parseDouble(middle.getLatitude()), 
					Double.parseDouble(end.getLongitude()),
					Double.parseDouble(end.getLatitude())));
			if(TextUtil.isNotNull(endPipe.getMaintainLength())){
				endPipe.setMaintainLength(((Double.parseDouble(endPipe.getMaintainLength()))/2)+"");
			}
			int startPipeid = ((Integer)insert("pdapipe.insertPipeSegment", startPipe)).intValue();
			int endPipeid = ((Integer)insert("pdapipe.insertPipeSegment", endPipe)).intValue();
			pipe.setDeletedFlag("1");
			Integer.valueOf(update("pdapipe.updatePipeSegment", pipe));
			result ="success";
		}
		return result;
	}
	
	
	
	Point getMiddlePoint(String slon ,String slat ,String elon,String elat){
		double lat = (Double.parseDouble(slat) + Double.parseDouble(elat)) / 2;
		double lon = (Double.parseDouble(slon) + Double.parseDouble(elon)) / 2;
		Point point = new Point();
		point.setLat(lat);
		point.setLng(lon);
		return point;
	}
	
	String getResId(List<GisPojo> list){
		String resId = "";
		for(GisPojo pojo : list){
			resId += "'"+pojo.getId()+"',";
		}
		if(resId.endsWith(",")){
			resId = resId.substring(0, resId.length()-1);
		}
		return resId;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
