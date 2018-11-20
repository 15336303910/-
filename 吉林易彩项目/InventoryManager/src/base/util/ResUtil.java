package base.util;
import java.util.List;

import base.util.pojo.Point;
import interfaces.pdainterface.lineSystem.pojo.LinePointInfo;
import interfaces.pdainterface.lineSystem.pojo.LineSegmentInfo;
import interfaces.pdainterface.lineSystem.pojo.LineSystemInfo;

public class ResUtil {
	public static GetProperties properties = new GetProperties();
	public static boolean isWGS;
	public static String province;
	static {
		try {
			String gpsType = properties.getValueByKey("gpsType");
			province = properties.getValueByKey("province");
			if (gpsType.equals("bd09")) {
				isWGS = false;
			} else {
				isWGS = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 得到相应的点坐标
	 * @param info
	 * @return
	 */
	public static String getPointStr(LinePointInfo info){
		StringBuffer sb = new StringBuffer();
		Integer type = info.getSysType();
		if(TextUtil.isNotNull(info.getStart()) && TextUtil.isNotNull(info.getLimit())){
			sb.append("select id,name,type,longitude,latitude,resNum,lastUpdate,del,maintain from (");
		}
		//地图点，获取web地图专用
		if(TextUtil.isNotNull(info.getLatitude()) && TextUtil.isNotNull(info.getLongitude())){
			Point point = MapUtil.web_db_encrypt(Double.parseDouble(info.getLatitude()),Double.parseDouble(info.getLongitude()));
			int distinct = 1000;
			if(TextUtil.isNotNull(info.getDistance())){
				distinct = info.getDistance();
			}
			double[] arr = functions.getAround(point.getLat(),point.getLng(),distinct);
			info.setLats(String.valueOf(arr[0]));
			info.setLons(String.valueOf(arr[1]));
			info.setLate(String.valueOf(arr[2]));
			info.setLone(String.valueOf(arr[3]));
		}
		if(type.equals(0)){
			//电杆的数据
			sb.append(" select distinct poleId as id ,poleName as name,"
					+ " '0' as type,poleLongitude as longitude,"
					+ " poleLatitude as latitude,resNum as resNum,"
					+ " lastUpdateDate as lastUpdate , deletedFlag as del,"
					+ " parties as maintain "
					+ " from poleinfo t"
					+ " where 1=1 ");
			if(TextUtil.isNotNull(info.getDeleteflag())){
				sb.append(" and t.deletedFlag ='"+info.getDeleteflag()+"'");
			}
			if(TextUtil.isNotNull(info.getSysId())){
				sb.append(" and t.poleLineId ='"+info.getSysId()+"'");
			}
			if(TextUtil.isNotNull(info.getSysName())){
				sb.append(" and t.poleName like '%"+info.getSysName()+"%'");
			}
			if(TextUtil.isNotNull(info.getMaintain())){
				if(info.getMaintain().contains(",")) {
					sb.append(" and t.parties in ("+ResUtil.getSqlStr(info.getMaintain())+")");
				}else {
					sb.append(" and t.parties ='"+info.getMaintain()+"'");
				}
			}
			if(TextUtil.isNotNull(info.getStartTime()) && TextUtil.isNotNull(info.getEndTime())){
				sb.append(" and (");
				sb.append("(unix_timestamp(t.creationDate) >= unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.creationDate) <= unix_timestamp('"+info.getEndTime()+"'))");
				sb.append(" or (unix_timestamp(t.lastUpdateDate) >= unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.lastUpdateDate) <= unix_timestamp('"+info.getEndTime()+"'))");
				sb.append(")");
			}
			if(TextUtil.isNotNull(info.getLatitude()) && TextUtil.isNotNull(info.getLongitude())){
				sb.append(" and ("
						+ " t.poleLatitude >= '"+info.getLats()+"' "
						+ " and t.poleLatitude <= '"+info.getLate()+"'"
						+ " and t.poleLongitude >= '"+info.getLons()+"'"
						+ " and t.poleLongitude <= '"+info.getLone()+"'"
						+ ")");
			}
		}else if(type.equals(1)){
			//标石数据
			sb.append("select distinct t.stoneId as id , t.stoneName as name ,'1' as type,"
					+ " t.longitude as longitude,t.latitude as latitude,"
					+ " t.lastUpTime as lastUpdate,t.deleteflag as del,t.resNum as resNum,"
					+ " maintainer as maintain"
					+ " from stoneinfo t where 1=1 ");
			if(TextUtil.isNotNull(info.getDeleteflag())){
				sb.append(" and t.deleteflag ='"+info.getDeleteflag()+"'");
			}
			if(TextUtil.isNotNull(info.getSysId())){
				sb.append(" and t.buriedId ='"+info.getSysId()+"'");
			}
			if(TextUtil.isNotNull(info.getSysName())){
				sb.append(" and t.stoneName like '%"+info.getSysName()+"%'");
			}
			if(TextUtil.isNotNull(info.getMaintain())){
				if(info.getMaintain().contains(",")) {
					sb.append(" and t.maintainer in ("+ResUtil.getSqlStr(info.getMaintain())+")");
				}else {
					sb.append(" and t.maintainer ='"+info.getMaintain()+"'");
				}
			}
			if(TextUtil.isNotNull(info.getStartTime()) && TextUtil.isNotNull(info.getEndTime())){
				sb.append(" and (");
				sb.append(" (unix_timestamp(t.createTime) >=unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.createTime) <=unix_timestamp('"+info.getEndTime()+"'))"
						+ " or"
						+ " (unix_timestamp(t.lastUpTime) >=unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.lastUpTime) <=unix_timestamp('"+info.getEndTime()+"'))"
						+ "");
				sb.append(")");
			}
			if(TextUtil.isNotNull(info.getLatitude()) && TextUtil.isNotNull(info.getLongitude())){
				sb.append(" and ("
						+ " t.latitude >= '"+info.getLats()+"' "
						+ " and t.latitude <= '"+info.getLate()+"'"
						+ " and t.longitude >= '"+info.getLons()+"'"
						+ " and t.longitude <= '"+info.getLone()+"'"
						+ ")");
			}
		}else if(type.equals(2)){
			//井数据
			sb.append("select wellId as id , wellName as name , '2' as type,"
					+ " longitude as longitude , latitude as latitude,"
					+ " deletedFlag as del,lastUpdateDate as lastUpdate,resNum as resNum,"
					+ " parties as maintain "
					+ " from wellinfo t where 1=1 ");
			if(TextUtil.isNotNull(info.getDeleteflag())){
				sb.append(" and t.deletedFlag ='"+info.getDeleteflag()+"'");
			}
			if(TextUtil.isNotNull(info.getSysId())){
				sb.append(" and t.pipeId ='"+info.getSysId()+"'");
			}
			if(TextUtil.isNotNull(info.getSysName())){
				sb.append(" and t.wellName like '%"+info.getSysName()+"%'");
			}
			if(TextUtil.isNotNull(info.getMaintain())){
				if(info.getMaintain().contains(",")) {
					sb.append(" and t.parties in ("+ResUtil.getSqlStr(info.getMaintain())+")");
				}else {
					sb.append(" and t.parties ='"+info.getMaintain()+"'");
				}
			}
			if(TextUtil.isNotNull(info.getStartTime()) && TextUtil.isNotNull(info.getEndTime())){
				sb.append(" and ( ");
				sb.append(" (unix_timestamp(t.creationDate) >= unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.creationDate) <= unix_timestamp('"+info.getEndTime()+"'))");
				sb.append(" or ");
				sb.append(" (unix_timestamp(t.lastUpdateDate) >= unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.lastUpdateDate) <= unix_timestamp('"+info.getEndTime()+"'))");
				sb.append(" ) ");
			}
			if(TextUtil.isNotNull(info.getLatitude()) && TextUtil.isNotNull(info.getLongitude())){
				sb.append(" and ("
						+ " t.latitude >= '"+info.getLats()+"' "
						+ " and t.latitude <= '"+info.getLate()+"'"
						+ " and t.longitude >= '"+info.getLons()+"'"
						+ " and t.longitude <= '"+info.getLone()+"'"
						+ ")");
			}
		} else if (type.equals(3)) {
			//撑点数据
			sb.append("select id as id , sportName as name , '3' as type,"
					+ " longitude as longitude , latitude as latitude,"
					+ " lastUpTime as lastUpdate,deleteflag as del,resNum as resNum,"
					+ " maintainer as maintain "
					+ " from job_support_point t where 1=1 ");
			if(TextUtil.isNotNull(info.getDeleteflag())){
				sb.append(" and t.deleteflag ='"+info.getDeleteflag()+"'");
			}
			if(TextUtil.isNotNull(info.getSysId())){
				sb.append(" and t.hangWallId ='"+info.getSysId()+"'");
			}
			if(TextUtil.isNotNull(info.getSysName())){
				sb.append(" and t.sportName like '%"+info.getSysName()+"%'");
			}
			if(TextUtil.isNotNull(info.getMaintain())){
				if(info.getMaintain().contains(",")) {
					sb.append(" and t.maintainer in ("+ResUtil.getSqlStr(info.getMaintain())+")");
				}else {
					sb.append(" and t.maintainer ='"+info.getMaintain()+"'");
				}
			}
			if(TextUtil.isNotNull(info.getStartTime()) && TextUtil.isNotNull(info.getEndTime())){
				sb.append(" and ( ");
				sb.append(" (unix_timestamp(t.createTime) >= unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.createTime) <= unix_timestamp('"+info.getEndTime()+"'))");
				sb.append(" or ");
				sb.append(" (unix_timestamp(t.lastUpTime) >= unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.lastUpTime) <= unix_timestamp('"+info.getEndTime()+"'))");
				sb.append(" ) ");
			}
			if(TextUtil.isNotNull(info.getLatitude()) && TextUtil.isNotNull(info.getLongitude())){
				sb.append(" and ("
						+ " t.latitude >= '"+info.getLats()+"' "
						+ " and t.latitude <= '"+info.getLate()+"'"
						+ " and t.longitude >= '"+info.getLons()+"'"
						+ " and t.longitude <= '"+info.getLone()+"'"
						+ ")");
			}
		}else if(type.equals(4)){
			//添加一个光交箱数据
			sb.append("select  t.EID as id ,t.ENAME as name ,'optical' as type,t.LON as longitude,t.LAT as latitude,"
					+ " t.lastUpdateDate as lastUpdate,t.del as del,t.resNum as resNum,parties as maintain "
					+ " from job_equtinfo t where t.ETYPE = 3   ");
			if(TextUtil.isNotNull(info.getDeleteflag())){
				sb.append(" and t.del ='"+info.getDeleteflag()+"'");
			}
			if(TextUtil.isNotNull(info.getSysName())){
				sb.append(" and t.ENAME like '%"+info.getSysName()+"%' ");
			}
			if(TextUtil.isNotNull(info.getMaintain())){
				sb.append(" and t.parties ='"+info.getMaintain()+"'");
			}
			if(TextUtil.isNotNull(info.getStartTime()) && TextUtil.isNotNull(info.getEndTime())){
				sb.append(" and ( ");
				sb.append(" (unix_timestamp(t.MTIME) >= unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.MTIME) <= unix_timestamp('"+info.getEndTime()+"'))");
				sb.append(" or ");
				sb.append(" (unix_timestamp(t.lastUpdateDate) >= unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.lastUpdateDate) <= unix_timestamp('"+info.getEndTime()+"'))");
				sb.append(" ) ");
			}
			if(TextUtil.isNotNull(info.getLatitude()) && TextUtil.isNotNull(info.getLongitude())){
				sb.append(" and ("
						+ " t.LAT >= '"+info.getLats()+"' "
						+ " and t.LAT <= '"+info.getLate()+"'"
						+ " and t.LON >= '"+info.getLons()+"'"
						+ " and t.LON <= '"+info.getLone()+"'"
						+ ")");
			}
		}else if(type.equals(5)){
			sb.append(" select t.id as id ,t.terminalName as name ,'OpticalTerminal' as type,"
					+ " t.longitude as longitude,t.latitude as latitude,t.deleteflag as del,"
					+ " t.lastUpTime as lastUpdate,t.resNum as resNum,maintainer as maintain "
					+ " from optical_terminal t where 1=1 ");
			if(TextUtil.isNotNull(info.getDeleteflag())){
				sb.append(" and t.deleteflag = '"+info.getDeleteflag()+"'");
			}
			if(TextUtil.isNotNull(info.getSysName())){
				sb.append(" and t.terminalName like '%"+info.getSysName()+"%'");
			}
			if(TextUtil.isNotNull(info.getMaintain())){
				if(info.getMaintain().contains(",")) {
					sb.append(" and t.maintainer in ("+ResUtil.getSqlStr(info.getMaintain())+")");
				}else {
					sb.append(" and t.maintainer ='"+info.getMaintain()+"'");
				}
			}
			if(TextUtil.isNotNull(info.getStartTime()) && TextUtil.isNotNull(info.getEndTime())){
				sb.append(" and ( ");
				sb.append(" (unix_timestamp(t.createTime) >= unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.createTime) <= unix_timestamp('"+info.getEndTime()+"'))");
				sb.append(" or ");
				sb.append(" (unix_timestamp(t.lastUpTime) >= unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.lastUpTime) <= unix_timestamp('"+info.getEndTime()+"'))");
				sb.append(" ) ");
			}
			if(TextUtil.isNotNull(info.getLatitude()) && TextUtil.isNotNull(info.getLongitude())){
				sb.append(" and ("
						+ " t.latitude >= '"+info.getLats()+"' "
						+ " and t.latitude <= '"+info.getLate()+"'"
						+ " and t.longitude >= '"+info.getLons()+"'"
						+ " and t.longitude <= '"+info.getLone()+"'"
						+ ")");
			}
		}else if(type.equals(6)){
			sb.append("select t.stationBaseId as id ,t.stationName as name ,'station' as type,"
					+ " t.lon as longitude,t.lat as latitude,t.deleteFlag as del,"
					+ " t.lastUpdateDate as lastUpdate ,t.resNum as resNum,parties as maintain "
					+ " from job_stationbase t where 1=1  ");
			if(TextUtil.isNotNull(info.getDeleteflag())){
				sb.append(" and t.deleteFlag = '"+info.getDeleteflag()+"'");
			}
			if(TextUtil.isNotNull(info.getSysName())){
				sb.append(" and t.stationName like '%"+info.getSysName()+"%'");
			}
			if(TextUtil.isNotNull(info.getMaintain())){
				if(info.getMaintain().contains(",")) {
					sb.append(" and t.parties in ("+ResUtil.getSqlStr(info.getMaintain())+")");
				}else {
					sb.append(" and t.parties ='"+info.getMaintain()+"'");
				}
			}
			if(TextUtil.isNotNull(info.getStartTime()) && TextUtil.isNotNull(info.getEndTime())){
				sb.append(" and ( ");
				sb.append(" (unix_timestamp(t.creationDate) >= unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.creationDate) <= unix_timestamp('"+info.getEndTime()+"'))");
				sb.append(" or ");
				sb.append(" (unix_timestamp(t.lastUpdateDate) >= unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.lastUpdateDate) <= unix_timestamp('"+info.getEndTime()+"'))");
				sb.append(" ) ");
			}
			if(TextUtil.isNotNull(info.getLatitude()) && TextUtil.isNotNull(info.getLongitude())){
				sb.append(" and ("
						+ " t.lat >= '"+info.getLats()+"' "
						+ " and t.lat <= '"+info.getLate()+"'"
						+ " and t.lon >= '"+info.getLons()+"'"
						+ " and t.lon <= '"+info.getLone()+"'"
						+ ")");
			}
		}
		if(TextUtil.isNotNull(info.getExtendSql())){
			sb.append(info.getExtendSql());
		}
		if(TextUtil.isNotNull(info.getStart()) && TextUtil.isNotNull(info.getLimit())){
			sb.append(") b limit "+info.getStart()+","+info.getLimit()+"");
		}
		return sb.toString();
	}
	
	
	
	/**
	 * 根据获取点的
	 * @param obj
	 * @return
	 */
	public static String getCablePoint(LineSystemInfo obj){
		StringBuffer sb = new StringBuffer();
		sb.append(""
				+ "select stationBaseId as id , stationName as name ,'3' as type, "
				+ " FORMAT(lon,8) as longitude,FORMAT(lat,8) as latitude "
				+ " from job_stationbase where deleteFlag =0"
				+ " and lat >= '"+obj.getLats()+"'"
				+ " and lat <='"+obj.getLate()+"'"
				+ " and lon >= '"+obj.getLons()+"'"
				+ " and lon <= '"+obj.getLone()+"'"
				+ "");
		sb.append(" union all ");
		sb.append(""
				+ " select EID as id , ENAME as name ,"
				+ " '1' as type, FORMAT(LON,8) as longitude,FORMAT(LAT,8) as latitude"
				+ " from job_equtinfo where del =0 AND etype = '3'"
				+ " and LAT >= '"+obj.getLats()+"'"
				+ " and LAT <='"+obj.getLate()+"'"
				+ " and LON >= '"+obj.getLons()+"'"
				+ " and LON <= '"+obj.getLone()+"'"
				+ "");
		sb.append(" union all ");
		sb.append(" select jointId as id , jointName as name ,"
				+ " '2' as type,FORMAT(longitude,8) as longitude ,"
				+ " FORMAT(latitude,8) as latitude "
				+ " from job_joint where deletedFlag =0"
				+ " and latitude >= '"+obj.getLats()+"'"
				+ " and latitude <='"+obj.getLate()+"'"
				+ " and longitude >= '"+obj.getLons()+"'"
				+ " and longitude <= '"+obj.getLone()+"'"
				+ "");
		sb.append(" union all ");
		sb.append(" select id as id ,terminalName as name ,"
				+ " '4' as type, FORMAT(longitude,8) as longitude,FORMAT(latitude,8) as latitude "
				+ " from optical_terminal where deleteflag =0"
				+ " and latitude >= '"+obj.getLats()+"'"
				+ " and latitude <='"+obj.getLate()+"'"
				+ " and longitude >= '"+obj.getLons()+"'"
				+ " and longitude <= '"+obj.getLone()+"'"
				+ "");
		return sb.toString();
	}
	
	
	/**
	 * 引上段数据
	 * @param info
	 * @return
	 */
	public static String getLeadupStr(LineSegmentInfo info){
		StringBuffer sb = new StringBuffer();
		sb.append(" select"
				+ " t.id as segId,t.leadupName as segName,'leadup' as segType,"
				+ " t.startId as startId,t.startName as startName,"
				+ " case startType"
				+ " when 0 then 'pole'"
				+ " when 1 then 'stone'"
				+ " when 2 then 'well'"
				+ " when 3 then 'support'"
				+ " when 4 then 'site'"
				+ " when 5 then 'equtinfo'"
				+ "  end as  startType,startLon,startLat,"
				+ " t.endId as endId,t.endName as endName,"
				+ " case endType"
				+ " when 0 then 'pole'"
				+ " when 1 then 'stone'"
				+ " when 2 then 'well'"
				+ " when 3 then 'support'"
				+ " when 4 then 'site'"
				+ " when 5 then 'equtinfo'"
				+ " end as  endType,endLon,endLat,resNum "
				+ " from leadupstage t where 1=1 and t.deleteflag =0 and t.startLon is not null and t.endLon is not null "
				+ "");
		if(TextUtil.isNotNull(info.getMaintain())){
			sb.append(" and t.maintainer ='"+info.getMaintain()+"'");
		}
		if(TextUtil.isNotNull(info.getStartTime()) && TextUtil.isNotNull(info.getEndTime())){
			sb.append(" and(");
			sb.append(" (unix_timestamp(t.createTime) >= unix_timestamp('"+info.getStartTime()+"')");
			sb.append(" and unix_timestamp(t.createTime) <= unix_timestamp('"+info.getEndTime()+"'))");
			sb.append(" or");
			sb.append(" (unix_timestamp(t.lastUpTime) >= unix_timestamp('"+info.getStartTime()+"')");
			sb.append(" and unix_timestamp(t.lastUpTime) <= unix_timestamp('"+info.getEndTime()+"'))");
			sb.append(")");
		}
		if(TextUtil.isNotNull(info.getExtendSql())){
			sb.append( info.getExtendSql());
		}
		return sb.toString();
	}
	
	/**
	 * 段的数据
	 * @param info
	 * @return
	 */
	public static String getSegStr(LineSegmentInfo info){
		StringBuffer sb = new StringBuffer();
		Integer type = Integer.parseInt(info.getSysType());
		if(TextUtil.isNotNull(info.getStart()) && TextUtil.isNotNull(info.getLimit())){
			sb.append("select segId,segName,segType,segMaintain,"
					+ " startId,startName,startType,startLon,startLat,startResNum,startUpdate,startDel,"
					+ " endId,endName,endType,endLon,endLat,endResNum,endUpdate,endDel "
					+ " from (");
		}
		if(type.equals(0)){
			//电杆数据
			sb.append(" select t.poleLineSegmentId as segId,t.poleLineSegmentName as segName,'0' as segType,"
					+ " t.parties as segMaintain, "
					+ " t.startDeviceId as startId,"
					+ " (select poleName from poleinfo where poleId =t.startDeviceId) as startName,"
					+ " '0' as startType,"
					+ " (select FORMAT(poleLongitude,8) from poleinfo where poleId =t.startDeviceId) as startLon,"
					+ " (select FORMAT(poleLatitude,8) from poleinfo where poleId =t.startDeviceId) as startLat,"
					+ " (select resNum from poleinfo where poleId =t.startDeviceId) as startResNum,"
					+ " (select lastUpdateDate from poleinfo where poleId =t.startDeviceId) as startUpdate,"
					+ " (select deletedFlag from poleinfo where poleId =t.startDeviceId) as startDel,"
					+ " t.endDeviceId as endId, "
					+ " (select poleName from poleinfo where poleId =t.endDeviceId) as endName,"
					+ " '0' as endType,"
					+ " (select FORMAT(poleLongitude,8) from poleinfo where poleId =t.endDeviceId) as endLon,"
					+ " (select FORMAT(poleLatitude,8) from poleinfo where poleId =t.endDeviceId) as endLat,"
					+ " (select resNum from poleinfo where poleId =t.endDeviceId) as endResNum,"
					+ " (select lastUpdateDate from poleinfo where poleId =t.endDeviceId) as endUpdate,"
					+ " (select deletedFlag from poleinfo where poleId =t.endDeviceId) as endDel"
					+ " from polelinesegmentinfo t where 1=1 ");
			if(TextUtil.isNotNull(info.getDeleteFlag())){
				sb.append(" and t.deletedFlag ='"+info.getDeleteFlag()+"'");
			}
			if(TextUtil.isNotNull(info.getMaintain())){
				if(info.getMaintain().contains(",")) {
					sb.append(" and t.parties in ("+ResUtil.getSqlStr(info.getMaintain())+")");
				}else {
					sb.append(" and t.parties ='"+info.getMaintain()+"'");
				}
			}
			if(TextUtil.isNotNull(info.getSysId())){
				sb.append(" and t.poleLineId ='"+info.getSysId()+"'");
			}
			if(TextUtil.isNotNull(info.getStartTime()) && TextUtil.isNotNull(info.getEndTime())){
				sb.append(" and(");
				sb.append(" (unix_timestamp(t.creationDate) >= unix_timestamp('"+info.getStartTime()+"')");
				sb.append(" and unix_timestamp(t.creationDate) <= unix_timestamp('"+info.getEndTime()+"'))");
				sb.append(" or");
				sb.append(" (unix_timestamp(t.lastUpdateDate) >= unix_timestamp('"+info.getStartTime()+"')");
				sb.append(" and unix_timestamp(t.lastUpdateDate) <= unix_timestamp('"+info.getEndTime()+"'))");
				sb.append(")");
			}
			if(TextUtil.isNotNull(info.getExtendSql())){
				sb.append( info.getExtendSql());
			}
		}else if(type.equals(1)){
			//标石
			sb.append(" select t.id as segId,t.buriedPartName as segName,'1' as segType, "
					+ " t.maintainer as segMaintain,"
					+ " t.buriedPartStartId as startId,"
					+ " (select stoneName from stoneinfo where stoneId = t.buriedPartStartId) as startName,"
					+ " '1' as startType,"
					+ " (select FORMAT(longitude,8) from stoneinfo where stoneId = t.buriedPartStartId) as startLon,"
					+ " (select FORMAT(latitude,8) from stoneinfo where stoneId = t.buriedPartStartId) as startLat,"
					+ " (select resNum from stoneinfo where stoneId = t.buriedPartStartId) as startResNum,"
					+ " (select lastUpTime from stoneinfo where stoneId = t.buriedPartStartId) as startUpdate,"
					+ " (select deleteflag from stoneinfo where stoneId = t.buriedPartStartId) as startDel,"
					+ " t.buriedPartEndId as endId,"
					+ " (select stoneName from stoneinfo where stoneId = t.buriedPartEndId) as endName,"
					+ " '1' as endType,"
					+ " (select FORMAT(longitude,8) from stoneinfo where stoneId = t.buriedPartEndId) as endLon,"
					+ " (select FORMAT(latitude,8) from stoneinfo where stoneId = t.buriedPartEndId) as endLat,"
					+ " (select resNum from stoneinfo where stoneId = t.buriedPartEndId) as endResNum,"
					+ " (select lastUpTime from stoneinfo where stoneId = t.buriedPartEndId) as endUpdate,"
					+ " (select deleteflag from stoneinfo where stoneId = t.buriedPartEndId) as endDel "
					+ "  from buriedpartinfo t where 1=1 ");
			if(TextUtil.isNotNull(info.getDeleteFlag())){
				sb.append(" and t.deleteflag ='"+info.getDeleteFlag()+"'");
			}
			if(TextUtil.isNotNull(info.getMaintain())){
				if(info.getMaintain().contains(",")) {
					sb.append(" and t.maintainer in ("+ResUtil.getSqlStr(info.getMaintain())+")");
				}else {
					sb.append(" and t.maintainer ='"+info.getMaintain()+"'");
				}
			}
			if(TextUtil.isNotNull(info.getSysId())){
				sb.append(" and t.buriedId ='"+info.getSysId()+"'");
			}
			if(TextUtil.isNotNull(info.getStartTime()) && TextUtil.isNotNull(info.getEndTime())){
				sb.append(" and (");
				sb.append(" (unix_timestamp(t.createTime) >= unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.createTime) <= unix_timestamp('"+info.getEndTime()+"'))"
						+ " or "
						+ " (unix_timestamp(t.lastUpTime) >= unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.lastUpTime) <= unix_timestamp('"+info.getEndTime()+"'))");
				sb.append(" )");
			}
			if(TextUtil.isNotNull(info.getExtendSql())){
				sb.append( info.getExtendSql());
			}
		}else if(type.equals(2)){
			//管井
			sb.append(" select t.pipeSegmentId as segId,t.pipeSegmentName as segName,'2' as segType,"
					+ " t.parties as segMaintain,"
					+ " t.startDeviceId as startId,"
					+ " '2' as startType,"
					+ " (select wellName from wellinfo where wellId = t.startDeviceId) as startName,"
					+ " (select FORMAT(longitude,8) from wellinfo where wellId = t.startDeviceId) as startLon,"
					+ " (select FORMAT(latitude,8) from wellinfo where wellId = t.startDeviceId) as startLat,"
					+ " (select resNum from wellinfo where wellId = t.startDeviceId) as startResNum,"
					+ " (select lastUpdateDate from wellinfo where wellId = t.startDeviceId) as startUpdate,"
					+ " (select deletedFlag from wellinfo where wellId = t.startDeviceId) as startDel,"
					+ " t.endDeviceId as endId,"
					+ " '2' as endType,"
					+ " (select wellName from wellinfo where wellId = t.endDeviceId) as endName,"
					+ " (select FORMAT(longitude,8) from wellinfo where wellId = t.endDeviceId) as endLon,"
					+ " (select FORMAT(latitude,8) from wellinfo where wellId = t.endDeviceId) as endLat,"
					+ " (select resNum from wellinfo where wellId = t.endDeviceId) as endResNum,"
					+ " (select lastUpdateDate from wellinfo where wellId = t.endDeviceId) as endUpdate,"
					+ " (select deletedFlag from wellinfo where wellId = t.endDeviceId) as endDel "
					+ "  from pipesegmentinfo t where 1=1 ");
			if(TextUtil.isNotNull(info.getDeleteFlag())){
				sb.append(" and t.deletedFlag ='"+info.getDeleteFlag()+"'");
			}
			if(TextUtil.isNotNull(info.getMaintain())){
				if(info.getMaintain().contains(",")) {
					sb.append(" and t.parties in ("+ResUtil.getSqlStr(info.getMaintain())+")");
				}else {
					sb.append(" and t.parties ='"+info.getMaintain()+"'");
				}
			}
			if(TextUtil.isNotNull(info.getSysId())){
				sb.append(" and t.pipeId ='"+info.getSysId()+"'");
			}
			if(TextUtil.isNotNull(info.getStartTime()) && TextUtil.isNotNull(info.getEndTime())){
				sb.append(" and (");
				sb.append(" (unix_timestamp(t.creationDate) >= unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.creationDate) <= unix_timestamp('"+info.getEndTime()+"'))"
						+ " or"
						+ " (unix_timestamp(t.lastUpdateDate) >= unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.lastUpdateDate) <= unix_timestamp('"+info.getEndTime()+"'))");
				sb.append(" )");
			}
			if(TextUtil.isNotNull(info.getExtendSql())){
				sb.append( info.getExtendSql());
			}
		} else if (type.equals(3)) {
			//挂墙
			sb.append(" select t.id as segId,t.hangWallName as segName,'3' as segType,"
					+ " t.maintainer as segMaintain,"
					+ " t.startDeviceId as startId,"
					+ " '3' as startType,"
					+ " (select hangWallName from job_support_point where id = t.startDeviceId) as startName,"
					+ " (select FORMAT(longitude,8) from job_support_point where id = t.startDeviceId) as startLon,"
					+ " (select FORMAT(latitude,8) from job_support_point where id = t.startDeviceId) as startLat,"
					+ " (select resNum from job_support_point where id = t.startDeviceId) as startResNum,"
					+ " (select lastUpTime from job_support_point where id = t.startDeviceId) as startUpdate,"
					+ " (select deleteflag from job_support_point where id = t.startDeviceId) as startDel,"
					+ " t.endDeviceId as endId,"
					+ " '3' as endType,"
					+ " (select hangWallName from job_support_point where id = t.endDeviceId) as endName,"
					+ " (select FORMAT(longitude,8) from job_support_point where id = t.endDeviceId) as endLon,"
					+ " (select FORMAT(latitude,8) from job_support_point where id = t.endDeviceId) as endLat,"
					+ " (select resNum from job_support_point where id = t.endDeviceId) as endResNum,"
					+ " (select lastUpTime from job_support_point where id = t.endDeviceId) as endUpdate,"
					+ " (select deleteflag from job_support_point where id = t.endDeviceId) as endDel "
					+ "  from hang_wall t where 1=1 ");
			if(TextUtil.isNotNull(info.getDeleteFlag())){
				sb.append(" and t.deleteflag ='"+info.getDeleteFlag()+"'");
			}
			if(TextUtil.isNotNull(info.getSysId())){
				sb.append(" and t.hangWallId ='"+info.getSysId()+"'");
			}
			if(TextUtil.isNotNull(info.getExtendSql())){
				sb.append( info.getExtendSql());
			}
		}else if(type.equals(4)){
			//光缆段 
			sb.append("select"
					+ " t.cableid as segId,t.cablename as segName,'4' as segType,"
					+ " t.parties as segMaintain,"
					+ " t.startDeviceId as startId, t.startDeviceType as startType,"
					+ " case t.startDeviceType "
					+ " when '1' then (select ENAME FROM job_equtinfo where EID = t.startDeviceId)"
					+ " when '2' then (select jointName from job_joint where jointId= t.startDeviceId)"
					+ " when '3' then (select stationName from job_stationbase where stationBaseId = t.startDeviceId)"
					+ " when '4' then (select terminalName from optical_terminal where id= t.startDeviceId)"
					+ " else ''"
					+ " end as startName,"
					+ " case t.startDeviceType "
					+ " when '1' then (select FORMAT(LON,8) FROM job_equtinfo where EID = t.startDeviceId)"
					+ " when '2' then (select FORMAT(longitude,8) from job_joint where jointId= t.startDeviceId)"
					+ " when '3' then (select FORMAT(lon,8) from job_stationbase where stationBaseId = t.startDeviceId)"
					+ " when '4' then (select FORMAT(longitude,8) from optical_terminal where id= t.startDeviceId)"
					+ " else ''"
					+ " end as startLon,"
					+ " case t.startDeviceType "
					+ " when '1' then (select FORMAT(LAT,8) FROM job_equtinfo where EID = t.startDeviceId)"
					+ " when '2' then (select FORMAT(latitude,8) from job_joint where jointId= t.startDeviceId)"
					+ " when '3' then (select FORMAT(lat,8) from job_stationbase where stationBaseId = t.startDeviceId)"
					+ " when '4' then (select FORMAT(latitude,8) from optical_terminal where id= t.startDeviceId)"
					+ " else ''"
					+ " end as startLat,"
					+ " case t.startDeviceType "
					+ " when '1' then (select resNum FROM job_equtinfo where EID = t.startDeviceId)"
					+ " when '2' then (select resNum from job_joint where jointId= t.startDeviceId)"
					+ " when '3' then (select resNum from job_stationbase where stationBaseId = t.startDeviceId)"
					+ " when '4' then (select resNum from optical_terminal where id= t.startDeviceId)"
					+ " else ''"
					+ " end as startResNum,"
					+ " case t.startDeviceType "
					+ " when '1' then (select lastUpdateDate FROM job_equtinfo where EID = t.startDeviceId)"
					+ " when '2' then (select lastUpdateDate from job_joint where jointId= t.startDeviceId)"
					+ " when '3' then (select lastUpdateDate from job_stationbase where stationBaseId = t.startDeviceId)"
					+ " when '4' then (select lastUpTime from optical_terminal where id= t.startDeviceId)"
					+ " else ''"
					+ " end as startUpdate,"
					+ " case t.startDeviceType "
					+ " when '1' then (select del FROM job_equtinfo where EID = t.startDeviceId)"
					+ " when '2' then (select deletedFlag from job_joint where jointId= t.startDeviceId)"
					+ " when '3' then (select deleteFlag from job_stationbase where stationBaseId = t.startDeviceId)"
					+ " when '4' then (select deleteflag from optical_terminal where id= t.startDeviceId)"
					+ " else ''"
					+ " end as startDel,"
					+ " t.endDeviceId as endId,t.endDeviceType as endType,"
					+ " case t.endDeviceType "
					+ " when '1' then (select ENAME FROM job_equtinfo where EID = t.endDeviceId)"
					+ " when '2' then (select jointName from job_joint where jointId= t.endDeviceId)"
					+ " when '3' then (select stationName from job_stationbase where stationBaseId = t.endDeviceId)"
					+ " when '4' then (select terminalName from optical_terminal where id= t.endDeviceId)"
					+ " else ''"
					+ " end as endName,"
					+ " case t.endDeviceType "
					+ " when '1' then (select FORMAT(LON,8) FROM job_equtinfo where EID = t.endDeviceId)"
					+ " when '2' then (select FORMAT(longitude,8) from job_joint where jointId= t.endDeviceId)"
					+ " when '3' then (select FORMAT(lon,8) from job_stationbase where stationBaseId = t.endDeviceId)"
					+ " when '4' then (select FORMAT(longitude,8) from optical_terminal where id= t.endDeviceId)"
					+ " else ''"
					+ " end as endLon,"
					+ " case t.endDeviceType "
					+ " when '1' then (select FORMAT(LAT,8) FROM job_equtinfo where EID = t.endDeviceId)"
					+ " when '2' then (select FORMAT(latitude,8) from job_joint where jointId= t.endDeviceId)"
					+ " when '3' then (select FORMAT(lat,8) from job_stationbase where stationBaseId = t.endDeviceId)"
					+ " when '4' then (select FORMAT(latitude,8) from optical_terminal where id= t.endDeviceId)"
					+ " else ''"
					+ " end as endLat,"
					+ " case t.endDeviceType "
					+ " when '1' then (select resNum FROM job_equtinfo where EID = t.endDeviceId)"
					+ " when '2' then (select resNum from job_joint where jointId= t.endDeviceId)"
					+ " when '3' then (select resNum from job_stationbase where stationBaseId = t.endDeviceId)"
					+ " when '4' then (select resNum from optical_terminal where id= t.endDeviceId)"
					+ " else ''"
					+ " end as startResNum,"
					+ " case t.endDeviceType "
					+ " when '1' then (select lastUpdateDate FROM job_equtinfo where EID = t.endDeviceId)"
					+ " when '2' then (select lastUpdateDate from job_joint where jointId= t.endDeviceId)"
					+ " when '3' then (select lastUpdateDate from job_stationbase where stationBaseId = t.endDeviceId)"
					+ " when '4' then (select lastUpTime from optical_terminal where id= t.endDeviceId)"
					+ " else ''"
					+ " end as endUpdate,"
					+ " case t.endDeviceType "
					+ " when '1' then (select del FROM job_equtinfo where EID = t.endDeviceId)"
					+ " when '2' then (select deletedFlag from job_joint where jointId= t.endDeviceId)"
					+ " when '3' then (select deleteFlag from job_stationbase where stationBaseId = t.endDeviceId)"
					+ " when '4' then (select deleteflag from optical_terminal where id= t.endDeviceId)"
					+ " else ''"
					+ " end as endDel "
					+ " from job_cable t where 1=1 "
					+ " "
					
					+ "");
			if(TextUtil.isNotNull(info.getDeleteFlag())){
				sb.append(" and t.deletedFlag ='"+info.getDeleteFlag()+"'");
			}
			if(TextUtil.isNotNull(info.getMaintain())){
				if(info.getMaintain().contains(",")) {
					sb.append(" and t.parties in ("+ResUtil.getSqlStr(info.getMaintain())+")");
				}else {
					sb.append(" and t.parties ='"+info.getMaintain()+"'");
				}
			}
			if(TextUtil.isNotNull(info.getSysId())){
				sb.append(" and t.routeid ='"+info.getSysId()+"'");
			}
			if(TextUtil.isNotNull(info.getStartTime()) && TextUtil.isNotNull(info.getEndTime())){
				sb.append(" and (");
				sb.append(" (unix_timestamp(t.creationDate) >= unix_timestamp('"+info.getStartTime()+"') "
						+ " and unix_timestamp(t.creationDate) <= unix_timestamp('"+info.getEndTime()+"'))"
						+ " or "
						+ " (unix_timestamp(t.lastUpdateDate) >= unix_timestamp('"+info.getStartTime()+"')"
						+ " and unix_timestamp(t.lastUpdateDate) <= unix_timestamp('"+info.getEndTime()+"'))");
				sb.append(" )");
			}
			if(TextUtil.isNotNull(info.getExtendSql())){
				sb.append( info.getExtendSql());
			}
			sb.append(" order by t.cableid desc");
		}
		if(TextUtil.isNotNull(info.getStart()) && TextUtil.isNotNull(info.getLimit())){
			sb.append(") b limit "+info.getStart()+","+info.getLimit()+"");
		}
		
		return sb.toString();
	}
	
	/**
	 * 得到线路数据
	 * @param list
	 * @param segType
	 * @return
	 */
	public static String getWebGisSeg(List<LineSegmentInfo> list,Integer segType,boolean mapFlag){
		StringBuffer jsonStr = new StringBuffer();
		
		jsonStr .append("[");
		for(LineSegmentInfo seg : list){
			String lineType = "";
			//一个很烂的代码体验是从找不到原因只能做兼容开始的
			if(TextUtil.isNull(seg.getSegType()) || seg.getSegType().equals("null") || seg.getSegType().equals("4")){
				segType = 4;
			}
			//手机端是true ,WEB端是false
			if(mapFlag){
				Point startPoint =  MapUtil.db_phone_encrypt(Double.parseDouble(seg.getStartLat()), Double.parseDouble(seg.getStartLon()));
				seg.setStartLat(startPoint.getLat()+"");
				seg.setStartLon(startPoint.getLng()+"");
				if(TextUtil.isNotNull(seg.getEndId()) && TextUtil.isNotNull(seg.getEndLat()) && TextUtil.isNotNull(seg.getEndLon())){
					Point endPoint = MapUtil.db_phone_encrypt(Double.parseDouble(seg.getEndLat()), Double.parseDouble(seg.getEndLon()));
					seg.setEndLat(endPoint.getLat()+"");
					seg.setEndLon(endPoint.getLng()+"");
				}
			}else{
				if(TextUtil.isNotNull(seg.getStartLon()) || TextUtil.isNotNull(seg.getStartLat())){
					Point startPoint =  MapUtil.db_web_encrypt(Double.parseDouble(seg.getStartLat()), Double.parseDouble(seg.getStartLon()));
					seg.setStartLat(startPoint.getLat()+"");
					seg.setStartLon(startPoint.getLng()+"");
				}
				if(TextUtil.isNotNull(seg.getEndId())){
					if(TextUtil.isNotNull(seg.getEndLat()) && TextUtil.isNotNull(seg.getEndLon()) && !(seg.getEndLat().equals("null")) && !(seg.getEndLon().equals("null"))){
						Point endPoint = MapUtil.db_web_encrypt(Double.parseDouble(seg.getEndLat()), Double.parseDouble(seg.getEndLon()));
						seg.setEndLat(endPoint.getLat()+"");
						seg.setEndLon(endPoint.getLng()+"");
					}
				}
			}
			jsonStr.append("{");
			if(TextUtil.isNotNull(seg.getSegId()) && TextUtil.isNotNull(seg.getEndId()) && TextUtil.isNotNull(seg.getStartId())){
				jsonStr.append("\"id\":\""+(seg.getSegId() == null ? "" :seg.getSegId())+"\",");
				jsonStr.append(" \"name\":\""+(seg.getSegName() == null ? "" :seg.getSegName())+"\",");
				
				if(seg.getSegType().equals("0")){
					lineType ="poleLine";
				}else if(seg.getSegType().equals("1")){
					lineType = "buried";
				}else if(seg.getSegType().equals("2")){
					lineType = "pipe";
				} else if (seg.getSegType().equals("3")) {
					lineType = "hangwall";
				} else if(seg.getSegType().equals("4")) {
					lineType = "cable";
				}else {
					lineType = seg.getSegType();
				}
				jsonStr.append("\"type\":\""+(lineType == null ? "" :lineType)+"\",");
			}
			jsonStr.append("\"start\":{\"id\":\"" + seg.getStartId()+"\",");
			jsonStr.append("\"name\":\"" +seg.getStartName() + "\",");
			//System.out.println(seg.getSegId()+"==="+seg.getStartId()+"==="+seg.getStartType()+"==="+seg.getStartLat());
			jsonStr.append("\"lat\":\"" + Double.parseDouble(seg.getStartLat())+ "\",");
			jsonStr.append("\"lon\":\"" + Double.parseDouble(seg.getStartLon()) + "\",");
			if(TextUtil.isNotNull(seg.getStartState())){
				jsonStr.append("\"state\":\"" +seg.getStartState() + "\",");
			}
			if(TextUtil.isNotNull(seg.getStartResNum())){
				jsonStr.append("\"resNum\":\"" +seg.getStartResNum() + "\",");
			}
			if(TextUtil.isNotNull(seg.getSegType()) && seg.getSegType().equals("leadup")){
				jsonStr.append("\"type\":\"" + seg.getStartType()+ "\"}");
			}else{
				jsonStr.append("\"type\":\"" + ResUtil.getPointMold(segType,seg.getStartType())+ "\"}");
			}
			if(TextUtil.isNotNull(seg.getEndId()) && !(seg.getEndLat().equals("null")) && !(seg.getEndLon().equals("null"))){
				jsonStr.append(",\"end\":{");
				jsonStr.append("\"id\":\"" + seg.getEndId() + "\",");
				jsonStr.append("\"name\":\"" + seg.getEndName() + "\",");
				jsonStr.append("\"lat\":\"" + Double.parseDouble(seg.getEndLat())+ "\",");
				jsonStr.append("\"lon\":\"" + Double.parseDouble(seg.getEndLon()) + "\",");
				if(TextUtil.isNotNull(seg.getEndState())){
					jsonStr.append("\"state\":\"" +seg.getEndState() + "\",");
				}
				if(TextUtil.isNotNull(seg.getEndResNum())){
					jsonStr.append("\"resNum\":\"" +seg.getEndResNum() + "\",");
				}
				if(TextUtil.isNotNull(seg.getSegType()) && seg.getSegType().equals("leadup")){
					jsonStr.append("\"type\":\"" + seg.getEndType()+ "\"}");
				}else{
					jsonStr.append("\"type\":\"" + ResUtil.getPointMold(segType, seg.getEndType())+ "\"}");
				}
			}
			jsonStr.append("},");
		}
		if (jsonStr.toString().endsWith(",")) {
			jsonStr.delete(jsonStr.length() - 1, jsonStr.length());
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}
	
	
	/**
	 * 得到线路数据
	 * @param list
	 * @param segType
	 * @return
	 */
	public static String getPhoneGisSeg(List<LineSegmentInfo> list,Integer segType,boolean mapFlag){
		StringBuffer jsonStr = new StringBuffer();
		
		jsonStr .append("[");
		for(LineSegmentInfo seg : list){
			String lineType = "";
			//手机端是true ,WEB端是false
			
			
			Point startPoint =  MapUtil.db_phone_encrypt(Double.parseDouble(seg.getStartLat()), Double.parseDouble(seg.getStartLon()));
			seg.setStartLat(startPoint.getLat()+"");
			seg.setStartLon(startPoint.getLng()+"");
			if(TextUtil.isNotNull(seg.getEndId()) && TextUtil.isNotNull(seg.getEndLat()) && TextUtil.isNotNull(seg.getEndLon())){
				Point endPoint = MapUtil.db_phone_encrypt(Double.parseDouble(seg.getEndLat()), Double.parseDouble(seg.getEndLon()));
				seg.setEndLat(endPoint.getLat()+"");
				seg.setEndLon(endPoint.getLng()+"");
			}
			
			
			jsonStr.append("{");
			if(TextUtil.isNotNull(seg.getSegId())){
				jsonStr.append("'id':'"+(seg.getSegId() == null ? "" :seg.getSegId())+"',");
				jsonStr.append(" 'name':'"+(seg.getSegName() == null ? "" :seg.getSegName())+"',");
				
				if(seg.getSegType().equals("0")){
					lineType ="poleLine";
				}else if(seg.getSegType().equals("1")){
					lineType = "buried";
				}else if(seg.getSegType().equals("2")){
					lineType = "pipe";
				} else if (seg.getSegType().equals("3")) {
					lineType = "hangwall";
				} else if(seg.getSegType().equals("4")) {
					lineType = "cable";
				}
				jsonStr.append("'type':'"+(lineType == null ? "" :lineType)+"',");
			}
			jsonStr.append("start:{'id':'" + seg.getStartId()+"',");
			jsonStr.append("'name':'" +seg.getStartName() + "',");
			jsonStr.append("'lat':" + Double.parseDouble(seg.getStartLat())+ ",");
			jsonStr.append("'lon':" + Double.parseDouble(seg.getStartLon()) + ",");
			if(TextUtil.isNotNull(seg.getStartState())){
				jsonStr.append("'state':'" +seg.getStartState() + "',");
			}
			if(TextUtil.isNotNull(seg.getStartMaintain())) {
				jsonStr.append("'maintain':'" +seg.getStartMaintain() + "',");
			}
			if(TextUtil.isNotNull(seg.getStartResNum())){
				jsonStr.append("'resNum':'" +seg.getStartResNum() + "',");
			}
			jsonStr.append("'type':'" + ResUtil.getPointMold(segType,seg.getStartType())+ "'}");
			if(TextUtil.isNotNull(seg.getEndId()) && !(seg.getEndLat().equals("null")) && !(seg.getEndLon().equals("null"))){
				jsonStr.append(",end:{");
				jsonStr.append("'id':'" + seg.getEndId() + "',");
				jsonStr.append("'name':'" + seg.getEndName() + "',");
				jsonStr.append("'lat':" + Double.parseDouble(seg.getEndLat())+ ",");
				jsonStr.append("'lon':" + Double.parseDouble(seg.getEndLon()) + ",");
				if(TextUtil.isNotNull(seg.getEndState())){
					jsonStr.append("'state':'" +seg.getEndState() + "',");
				}
				if(TextUtil.isNotNull(seg.getEndMaintain())) {
					jsonStr.append("'maintain':'" +seg.getEndMaintain() + "',");
				}
				if(TextUtil.isNotNull(seg.getEndResNum())){
					jsonStr.append("'resNum':'" +seg.getEndResNum() + "',");
				}
				jsonStr.append("'type':'" + ResUtil.getPointMold(segType, seg.getEndType())+ "'}");
			}
			jsonStr.append("},");
		}
		if (jsonStr.toString().endsWith(",")) {
			jsonStr.delete(jsonStr.length() - 1, jsonStr.length());
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}
	
	public static String getPointMold(Integer segType, String point){
		String ptype= "";
		if(segType == 4){
			if(point.equals("1")){
				ptype = "optical";
			}else if(point.equals("2")){
				ptype = "joint";
			}else if(point.equals("3")){
				ptype ="site";
			} else if (point.equals("4")) {
				ptype = "opticalTerminal";
			}else{
				ptype =point;
			}
		}else{
			if(point.equals("0")){
				ptype = "pole";
			}else if(point.equals("1")){
				ptype = "stone";
			}else if(point.equals("2")){
				ptype ="well";
			} else if (point.equals("3")) {
				ptype = "support";
			}else{
				ptype =point;
			}
		}
		return ptype;
	}
	
	/**
	 * 得到产权性质
	 * 根据手机端的产权性
	 * 质得到综资的产权性质
	 * @param owerShip
	 * @return
	 */
	public static String getOwnerShip(String ownerShip){
		String result = "";
		if(province.equals("北京")){
			if(ownerShip.equals("0")){
				result ="1";//自建
			}else if(ownerShip.equals("1")){
				result ="2";//合建
			}else if(ownerShip.equals("2")){
				result ="7";//共建联建
			}else if(ownerShip.equals("3")){
				result = "3";//租用
			}else if(ownerShip.equals("4")){
				result = "4";//购买
			}else if(ownerShip.equals("5")){
				result = "5";//置换
			}else{
				result = "6";//其他
			}
		}else if(province.equals("贵州") || province.equals("湖南")){
			if(ownerShip.equals("0")){
				result ="1";//自建
			}else if(ownerShip.equals("1")){
				result ="3";//合建
			}else if(ownerShip.equals("2")){
				result ="2";//共建联建
			}else if(ownerShip.equals("3")){
				result = "5";//租用
			}else if(ownerShip.equals("4")){
				result = "6";//购买
			}else if(ownerShip.equals("5")){
				result = "7";//置换
			}else{
				result = "0";//其他
			}
		}
		
		return result;
	}
	
	
	
	/**
	 * 得到产权单位
	 * @param resOwner
	 * @return
	 */
	public static String getResOwner(String resOwner){
		String result ="";
		if(province.equals("北京")){
			if(resOwner.equals("0")){
				result = "1";//中国移动
			}else if(resOwner.equals("1")){
				result = "2";//中国联通
			}else if(resOwner.equals("2")){
				result = "3";//中国电信
			}else if(resOwner.equals("3")){
				result = "4";//中国铁通
			}else if(resOwner.equals("4")){
				result = "5";//中国广电
			}else if(resOwner.equals("5")){
				result = "6";//电力
			}else if(resOwner.equals("6")){
				result = "7";//业主
			}else if(resOwner.equals("8")){
				result = "9";//北信基础
			}else if(resOwner.equals("9")){
				result = "10";//歌华
			}else if(resOwner.equals("10")){
				result = "11";//天童
			}else if(resOwner.equals("11")){
				result = "12";//亦庄博大
			}else if(resOwner.equals("12")){
				result = "13";//公联
			}else if(resOwner.equals("13")){
				result = "14";//路灯
			}else if(resOwner.equals("14")){
				result = "15";//污水
			}else{
				result ="8";
			}
		}else if(province.equals("贵州") || province.equals("湖南")){
			if(resOwner.equals("0")){
				result = "中国移动";//中国移动
			}else if(resOwner.equals("1")){
				result = "中国联通";//中国联通
			}else if(resOwner.equals("2")){
				result = "中国电信";//中国电信
			}else if(resOwner.equals("3")){
				result = "中国铁通";//中国铁通
			}else if(resOwner.equals("4")){
				result = "广电";//中国广电
			}else if(resOwner.equals("5")){
				result = "电力";//电力
			}else if(resOwner.equals("6")){
				result = "业主";//业主
			}else{
				result ="其他";
			}
		}
		
		return result;
	}
	
	/**
	 * 得到
	 * @param str
	 * @return
	 */
	static String getSqlStr(String str) {
		str = "'"+str+"'";
		str = str.replace(",", "','");
		return str;
	}
	
	
	/**
	 * 得到采集到的资源数据
	 * @param extendSql
	 * @param taskId
	 * @return
	 */
	public static String getCollectRes(String extendSql,String taskId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select segId,segName,segType,segDel,startId,startName,startType,startLon,startLat,startResNum,startDel,startMotion,endId,"
				+ " endName,endType,endLon,endLat,endResNum,endDel,endMotion "
				+ " from ("
				+ " select c.segId,"
				+ " case c.segType "
				+ " when 'pipe' then (select pipeSegmentName from pipesegmentinfo where pipeSegmentId = c.segId)"
				+ " when 'poleLine' then (select poleLineSegmentName from polelinesegmentinfo where poleLineSegmentId = c.segId)"
				+ " when 'leadup' then (select leadupName from leadupstage where id = c.segId)"
				+ " when 'cable' then (select cablename from job_cable where cableid = c.segId)"
				+ " else '' end as segName,"
				+ " c.segType,"
				+ " case c.segType " + 
				" when 'pipe' then (select deletedFlag from pipesegmentinfo where pipeSegmentId = c.segId)" + 
				" when 'poleLine' then (select deletedFlag from polelinesegmentinfo where poleLineSegmentId = c.segId)" + 
				" when 'leadup' then (select deleteflag from leadupstage where id = c.segId)" + 
				" when 'cable' then (select deletedFlag from job_cable where cableid = c.segId)" + 
				" else 0 end as segDel,"
				+ " c.startId,c.startName,"
				+ " c.startType,"
				+ " case c.startType"
				+ " when 'equtinfo' then (select LON from job_equtinfo where del =0 and EID = c.startId) "
				+ " when 'optical' then (select LON from job_equtinfo where del =0 and EID = c.startId) "
				+ " when 'well' then (select longitude from wellinfo where wellId = c.startId)"
				+ " when 'pole' then (select poleLongitude from poleinfo where poleId = c.startId)"
				+ " when 'station' then (select lon from job_stationbase where stationBaseId = c.startId)"
				+ " when 'site' then (select lon from job_stationbase where stationBaseId = c.startId)"
				+ " else ''"
				+ " end as startLon,"
				+ " case c.startType"
				+ " when 'equtinfo' then (select LAT from job_equtinfo where del =0 and EID = c.startId) "
				+ " when 'optical' then (select LAT from job_equtinfo where del =0 and EID = c.startId) "
				+ " when 'well' then (select latitude from wellinfo where wellId = c.startId)"
				+ " when 'pole' then (select poleLatitude from poleinfo where poleId = c.startId)"
				+ " when 'station' then (select lat from job_stationbase where stationBaseId = c.startId)"
				+ " when 'site' then (select lat from job_stationbase where stationBaseId = c.startId)"
				+ " else ''"
				+ " end as startLat,"
				+ " case c.startType"
				+ " when 'equtinfo'  then (select resNum from job_equtinfo where del =0 and EID = c.startId) "
				+ " when 'optical' then (select resNum from job_equtinfo where del =0 and EID = c.startId)"
				+ " when 'well' then (select resNum from wellinfo where wellId = c.startId)"
				+ " when 'pole' then (select resNum from poleinfo where poleId = c.startId)"
				+ " when 'station'  then (select resNum from job_stationbase where stationBaseId = c.startId)"
				+ " when  'site' then (select resNum from job_stationbase where stationBaseId = c.startId)"
				+ " else ''"
				+ " end as startResNum,"
				+ " case c.startType"
				+ " when 'equtinfo' then (select del from job_equtinfo where del =0 and EID = c.startId) "
				+ " when 'optical' then (select del from job_equtinfo where del =0 and EID = c.startId) "
				+ " when 'well' then (select deletedFlag from wellinfo where wellId = c.startId)"
				+ " when 'pole' then (select deletedFlag from poleinfo where poleId = c.startId)"
				+ " when 'station'  then (select deleteFlag from job_stationbase where stationBaseId = c.startId)"
				+ " when 'site'  then (select deleteFlag from job_stationbase where stationBaseId = c.startId)"
				+ " else ''"
				+ " end as startDel,"
				+ " c.endId,c.endName,c.endType,"
				+ " case c.endType"
				+ " when 'equtinfo' then (select LON from job_equtinfo where del =0 and EID = c.endId) "
				+ " when 'optical' then (select LON from job_equtinfo where del =0 and EID = c.endId) "
				+ " when 'well' then (select longitude from wellinfo where wellId = c.endId)"
				+ " when 'pole' then (select poleLongitude from poleinfo where poleId = c.endId)"
				+ " when 'station' then (select lon from job_stationbase where stationBaseId = c.endId)"
				+ " when 'site' then (select lon from job_stationbase where stationBaseId = c.endId)"
				+ " else ''"
				+ " end as endLon,"
				+ " case c.endType"
				+ " when 'equtinfo' then (select LAT from job_equtinfo where del =0 and EID = c.endId) "
				+ " when 'optical' then (select LAT from job_equtinfo where del =0 and EID = c.endId) " + 
				" when 'well' then (select latitude from wellinfo where wellId = c.endId)" + 
				" when 'pole' then (select poleLatitude from poleinfo where poleId = c.endId)" + 
				" when 'station' then (select lat from job_stationbase where stationBaseId = c.endId)" + 
				" when 'site' then (select lat from job_stationbase where stationBaseId = c.endId)" + 
				" else ''" + 
				" end as endLat," + 
				" case c.endType" + 
				" when 'equtinfo'  then (select resNum from job_equtinfo where del =0 and EID = c.endId) " + 
				" when 'optical' then (select resNum from job_equtinfo where del =0 and EID = c.endId) " + 
				" when 'well' then (select resNum from wellinfo where wellId = c.endId)" + 
				" when 'pole' then (select resNum from poleinfo where poleId = c.endId)" + 
				" when 'station'  then (select resNum from job_stationbase where stationBaseId = c.endId)" + 
				" when  'site' then (select resNum from job_stationbase where stationBaseId = c.endId)" + 
				" else ''" + 
				" end as endResNum," + 
				" case c.endType" + 
				" when 'equtinfo' then (select del from job_equtinfo where del =0 and EID = c.endId) " + 
				" when 'optical' then (select del from job_equtinfo where del =0 and EID = c.endId) " + 
				" when 'well' then (select deletedFlag from wellinfo where wellId = c.endId)" + 
				" when 'pole' then (select deletedFlag from poleinfo where poleId = c.endId)" + 
				" when 'station'  then (select deleteFlag from job_stationbase where stationBaseId = c.endId)" + 
				" when 'site'  then (select deleteFlag from job_stationbase where stationBaseId = c.endId)" + 
				" else ''" + 
				" end as endDel,"
				+ " case c.startType" + 
				" when 'equtinfo'  then (select resMotion from job_equtinfo where del =0 and EID = c.startId) " + 
				" when 'optical' then (select resMotion from job_equtinfo where del =0 and EID = c.startId) " + 
				" when 'well' then (select resMotion from wellinfo where wellId = c.startId)" + 
				" when 'pole' then (select resMotion from poleinfo where poleId = c.startId)" + 
				" else ''" + 
				" end as startMotion," + 
				" case c.endType" + 
				" when 'equtinfo'  then (select resMotion from job_equtinfo where del =0 and EID = c.endId) " + 
				" when 'optical' then (select resMotion from job_equtinfo where del =0 and EID = c.endId) " + 
				" when 'well' then (select resMotion from wellinfo where wellId = c.endId)" + 
				" when 'pole' then (select resMotion from poleinfo where poleId = c.endId)" + 
				" else ''" + 
				" end as endMotion" + 
				" from approval_collect c where c.segType !='leadup' and c.taskId ='"+taskId+"'");
		if(TextUtil.isNotNull(extendSql)) {
			sql.append(extendSql);
		}
		sql.append(") a where a.segDel= 0"
				+ " and (a.startResNum is not null or (a.startResNum is null and a.startDel ='0')) "
				+ " and (a.endResNum is not null or (a.endResNum is null and a.endDel ='0')) ");
		return sql.toString();
	}
	
	/**
	 * 得到电杆高度
	 * @param poleType
	 * @return
	 */
	public static String getPoleKind(String poleType){
		String result = "0";
		if(poleType.equals("1")){
			result ="七米杆";
		}else if(poleType.equals("2")){
			result = "八米杆";
		}else if(poleType.equals("3")){
			result = "九米杆";
		}else if(poleType.equals("4")){
			result = "十二米杆";
		}
		return result;
	}
}



