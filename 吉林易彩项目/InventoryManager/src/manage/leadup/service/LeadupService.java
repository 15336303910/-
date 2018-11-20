package manage.leadup.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import manage.dictionary.service.impl.IdictService;
import manage.leadup.pojo.HangWallPojo;
import manage.leadup.pojo.LeadupPojo;
import manage.leadup.pojo.SupportPointPojo;
import manage.leadup.service.impl.IleadupService;
import base.database.DataBase;
import base.util.MapUtil;
import base.util.TextUtil;

public class LeadupService extends DataBase implements IleadupService{
	private IdictService dictService;
	private JdbcTemplate jdbcTemplate;
	private static String getLeadup = "leadup.getLeadup";
	private static String getLeadupCount = "leadup.getLeadupCount";
	private static String upLeadup = "leadup.upLeadup";
	private static String insertLeadup = "leadup.insertLeadup";
	private static String delLeadup = "leadup.delLeadup";
	private static String getSupportPoint ="leadup.getSupportPoint";
	private static String getSupportPointObj = "leadup.getSupportPointObj";
	private static String getSupportPointCount = "leadup.getSupportPointCount";
	private static String upSupportPoint = "leadup.upSupportPoint";
	private static String addSupportPoint = "leadup.insertSupportPoint";
	/**
	 * 得到引上信息
	 * @param obj
	 * @return
	 */
	public List<LeadupPojo> getLeadUp(LeadupPojo obj){
		if(TextUtil.isNotNull(obj.getLeadupName())){
			String leadName = obj.getLeadupName();
			if(leadName.contains(" ")){
				leadName= leadName.replaceAll(" +", "%");
			}
			obj.setLeadupName("%"+leadName+"%");
		}
		List<LeadupPojo> list = getObjects(getLeadup, obj);
		return list;
	}
	
	/**
	 * 得到引上数据
	 * @param obj
	 * @return
	 */
	public int getLeadupCount(LeadupPojo obj){
		return getCount(getLeadupCount, obj);
	}
	
	/**
	 * 修改引上段数据
	 * @param obj
	 * @return
	 */
	public int updateLeadup(LeadupPojo obj){
		return this.update(upLeadup, obj);
	}
	
	/**
	 * 增加引上数据
	 * @param obj
	 * @return
	 */
	public int insertLeadup(LeadupPojo obj){
		String sql = "select id,leadupName from  leadupstage"
				+ " where deleteflag =0 and startType ="+obj.getStartType()+" "
				+ " and startId ='"+obj.getStartId()+"'"
				+ " and endType ="+obj.getEndType()+" and endId ='"+obj.getEndId()+"'";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNull(list)) {
			Map<String, Object> start = this.getLeadLatLon(obj.getStartType()+"", obj.getStartId());
			Map<String, Object> end = this.getLeadLatLon(obj.getEndType()+"", obj.getEndId());
			String longitude = ((Double.parseDouble(start.get("longitude")+"") + Double.parseDouble(end.get("longitude")+""))/2)+"";
			String latitude =((Double.parseDouble(start.get("latitude")+"") + Double.parseDouble(end.get("latitude")+""))/2)+"";
			String region = end.get("region")+"";
			obj.setLongitude(longitude);
			obj.setLatitude(latitude);
			obj.setStartLon(start.get("longitude")+"");
			obj.setStartLat(start.get("latitude")+"");
			obj.setEndLon(end.get("longitude")+"");
			obj.setEndLat(end.get("latitude")+"");
			if(TextUtil.isNull(obj.getMantainance())) {
				obj.setMantainance(region);
			}
			obj.setMantainance(region);
			return (Integer) this.insert(insertLeadup, obj);
		}else {
			return -1;
		}
		
	}
	
	
	/**
	 * 得到引上的
	 * 经纬度信息
	 * @param type
	 * @param id
	 * @return
	 */
	Map<String,Object> getLeadLatLon(String type,String id){
		String sql = "";
		//电杆
		if(type.equals("0")){
			sql = "select poleLongitude as longitude,poleLatitude as latitude,region as region "
					+ " from poleinfo where poleId = "+id;
		}
		//标石
		if(type.equals("1")){
			sql = "select longitude as longitude,latitude as latitude, stoneArea as region "
					+ " from stoneinfo where stoneId = "+id;
		}
		//管井
		if(type.equals("2")){
			sql ="select longitude as longitude ,latitude as latitude,region as region "
					+ " from wellinfo where wellId = "+id;
		}
		//撑点
		if(type.equals("3")){
			sql = "select longitude as longitude ,latitude as latitude,maintArea as region "
					+ "  from job_support_point where id = "+id;
		}
		//站点
		if(type.equals("4")){
			sql="select lon as longitude ,lat as latitude,region as region "
					+ " from job_stationbase where stationBaseId = "+id;
		}
		//光交
		if(type.equals("5")){
			sql ="select LON as longitude ,LAT as latitude ,EADDR as region "
					+ " from job_equtinfo where EID ='"+id+"'";
		}
		if(TextUtil.isNotNull(id)){
			List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
			if(TextUtil.isNotNull(list)){
				return list.get(0);
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	/**
	 * 删除引上
	 * @param ids
	 */
	public void delLeadup(String ids){
		this.delete(delLeadup, ids);
	}
	
	
	/**
	 * 批量删除撑点
	 * 
	 */
	public int delSupportPoint(String ids) {
		return this.delete("leadup.delSupportPoint", ids);
	}
	
	/**
	 * 得到引上具体对象
	 * @param leadup
	 * @return
	 */
	public LeadupPojo getLeadUpObj(LeadupPojo leadup){
		LeadupPojo obj = (LeadupPojo) getObject("leadup.getLeadupObj", leadup);
		/*obj = this.getLeadupLay(obj);*/
		return obj;
	}
	
	LeadupPojo getLeadupLay(LeadupPojo obj){
		String sql = "select distinct cableId,cableName  from opticab_lay "
					+ " where spanId ='"+obj.getResNum()+"' and spanType in (3,9108) and deleteFlag ='0'"
					+ " union all "
					+ " select distinct cableId,cableName  from opticab_lay "
					+ " where spanId ='"+obj.getId()+"' and spanType in (3,9108) and deleteFlag ='0'";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		String cableName = super.getStrList(list, "cableName");
		String cableId = super.getStrList(list, "cableId");
		obj.setLeadupOptical(cableName);
		obj.setLeadupOpticalId(cableId);
		return obj;
	}
	
	/**
	 * 得到是否有敷设关系
	 * @param obj
	 * @return
	 */
	public int getLeadupLayNum(LeadupPojo obj){
		LeadupPojo lead = new LeadupPojo();
		lead.setId(obj.getId());
		lead = (LeadupPojo) getObject("leadup.getLeadupObj", lead);
		String sql = "select count(1) from (";
		if(TextUtil.isNotNull(lead.getResNum())){
			sql +="select id from opticab_lay "
					+ " where spanId ='"+lead.getResNum()+"' and spanType in (3,9108,9109) and deleteFlag ='0'"
					+ " union all ";
		}
		sql += " select id from opticab_lay "
				+ " where spanId ='"+lead.getId()+"' and spanType in (3,9108,9109) and deleteFlag ='0'"
				+ ") s ";
		int num = this.jdbcTemplate.queryForInt(sql);
		return num;
	}
	
	/**
	 * 得到引上的长度
	 * @param leadup
	 * @return
	 */
	public String getLeadupLength(LeadupPojo leadup){
		String distinct ="";
		String startSql = this.getTypeSql(leadup.getStartType()+"", leadup.getStartId()+"");
		String endSql = this.getTypeSql(leadup.getEndType()+"", leadup.getEndId()+"");
		if(TextUtil.isNotNull(startSql) && TextUtil.isNotNull(endSql)){
			String sql = startSql +""
					+ " union all "
					+ endSql;
			List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
			if(list.size() ==2){
				Map<String, Object> start = list.get(0);
				Map<String, Object> end = list.get(1);
				distinct = MapUtil.Distance(Double.parseDouble(start.get("lon")+""),
						Double.parseDouble(start.get("lat")+""),
						Double.parseDouble(end.get("lon")+""),
						Double.parseDouble(end.get("lat")+""));
			}
		}
		return distinct;
	}
	
	
	String getTypeSql(String type,String id){
		StringBuffer sql = new StringBuffer();
		if(type.equals("0")){
			sql.append("select poleLongitude as lon,poleLatitude as lat"
					+ " from poleinfo where poleId ='"+id+"'  "
					+ " ");
		}else if(type.equals("1")){
			sql.append("select longitude as lon ,latitude as lat "
					+ " from stoneinfo where  stoneId ='"+id+"'");
		}else if(type.equals("2")){
			sql.append("select longitude as lon ,latitude as lat from wellinfo"
					+ " where wellId ='"+id+"' ");
		}
		return sql.toString();
	}
	
	
	/**
	 * 得到所有的撑点
	 * @param spoint
	 * @return
	 */
	public List<SupportPointPojo> getsPointList(SupportPointPojo spoint){
		if(TextUtil.isNotNull(spoint.getSportName())){
			String portName = spoint.getSportName();
			if(portName.contains(" ")){
				portName= portName.replaceAll(" +", "%");
			}
			spoint.setSportName("%"+portName+"%");
		}
		List<SupportPointPojo> list = getObjects(getSupportPoint, spoint);
		return list;
	}
	
	/**
	 * 
	 * @param spoint
	 * @return
	 */
	public SupportPointPojo getsPointObj(SupportPointPojo spoint){
		return (SupportPointPojo) getObject(getSupportPoint, spoint);
	}
	
	/**
	 * 得到撑点总数
	 * @param spoint
	 * @return
	 */
	public int getsPointCount(SupportPointPojo spoint){
		Integer num = (Integer) getCount(getSupportPointCount, spoint);
		return num;
	}
	
	/**
	 * 修改撑点数据
	 * @param obj
	 * @return
	 */
	public int upsPoint(SupportPointPojo obj){
		return this.update(upSupportPoint, obj);
	}
	
	/**
	 * 新增撑点数据
	 * @param obj
	 * @return
	 */
	public int addSupportPoint(SupportPointPojo obj){
		return (Integer) this.insert(addSupportPoint, obj);
	}
	
	/**
	 *根据主键得到
	 *撑点数据对象
	 * @param id
	 * @return
	 */
	public SupportPointPojo getsPointObj(Integer id){
		SupportPointPojo obj = (SupportPointPojo) this.getObject(getSupportPointObj, id);
		return obj;
	}
	
	/**
	 * 得到所有的挂墙段
	 * @param obj
	 * @return
	 */
	public List<HangWallPojo> getHangWallList(HangWallPojo obj){
		if(TextUtil.isNotNull(obj.getHangWallName())){
			String hwName = obj.getHangWallName();
			if(hwName.contains(" ")){
				hwName= hwName.replaceAll(" +", "%");
			}
			obj.setHangWallName("%"+hwName+"%");
		}
		List<HangWallPojo> list = getObjects("leadup.getHangWall", obj);
		return list;
	}
	
	
	/**
	 * 得到挂墙段详细数据
	 * @param obj
	 * @return
	 */
	public HangWallPojo getHangWallObj(HangWallPojo obj){
		obj = (HangWallPojo) getObject("leadup.getHangWall", obj);
		obj = this.getHangWallLay(obj);
		return obj;
	}
	
	/**
	 * 得到所有的挂墙段数
	 * @param obj
	 * @return
	 */
	public int getHangWallCount(HangWallPojo obj){
		int num = getCount("leadup.getHanlWallCount", obj);
		return num;
	}
	/**
	 * 批量删除挂墙段
	 */
	public int delHangWall(String ids) {
		return this.delete("leadup.delHangWall", ids);
	}
	/**
	 * 增加挂墙段
	 * @param obj
	 * @return
	 */
	public int insertHangWall(HangWallPojo obj){
		int id= (Integer) this.insert("leadup.insertHanlWall", obj);
		return id;
	}
	
	/**
	 * 修改挂墙段
	 * @param obj
	 */
	public void updateHangWall(HangWallPojo obj){
		this.update("leadup.upHanlWall", obj);
	}
	
	/**
	 * 得到计算长度
	 * @param obj
	 * @return
	 */
	public String getHangWallLength(HangWallPojo obj){
		String distinct = "0";
		String startSql= this.getSql(obj.getStartDeviceId());
		String endSql = this.getSql(obj.getEndDeviceId());
		if(TextUtil.isNotNull(startSql) && TextUtil.isNotNull(endSql)){
			List<Map<String, Object>> startList = this.jdbcTemplate.queryForList(startSql);
			List<Map<String, Object>> endList = this.jdbcTemplate.queryForList(endSql);
			if(TextUtil.isNotNull(startList) && TextUtil.isNotNull(endList)){
				Map<String, Object> start = startList.get(0);
				Map<String, Object> end = endList.get(0);
				distinct = MapUtil.Distance(Double.parseDouble(start.get("lon")+""),
						Double.parseDouble(start.get("lat")+""),
						Double.parseDouble(end.get("lon")+""),
						Double.parseDouble(end.get("lat")+""));
			}
		}
		return distinct;
	}
	
	String getSql(Integer id){
		String sql = "select longitude as lon,latitude lat from job_support_point s where id ='"+id+"'";
		return sql;
	}
	
	
	HangWallPojo getHangWallLay(HangWallPojo obj){
		String sql = "select distinct cableId,cableName  from opticab_lay "
				+ " where spanId ='"+obj.getResNum()+"' and spanType in (3,9109) and deleteFlag ='0'"
				+ " union all "
				+ " select distinct cableId,cableName  from opticab_lay "
				+ " where spanId ='"+obj.getId()+"' and spanType in (3,9109) and deleteFlag ='0'";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		String cableName = super.getStrList(list, "cableName");
		String cableId = super.getStrList(list, "cableId");
		obj.setHangWallOptical(cableName);
		obj.setHangWallOpticalId(cableId);
		return obj;
	}
	
	
	public IdictService getDictService() {
		return dictService;
	}
	public void setDictService(IdictService dictService) {
		this.dictService = dictService;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
