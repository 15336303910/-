 package interfaces.pdainterface.route.service.impl;
 
 import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.BeanUtil;
import base.util.MapUtil;
import base.util.TextUtil;
import interfaces.pdainterface.equt.pojo.EqutCableInfo;
import interfaces.pdainterface.route.service.PDARouteService;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import manage.equt.pojo.EqutInfoBean;
import manage.generator.pojo.GeneratorInfoBean;
import manage.point.pojo.PointInfoBean;
import manage.route.pojo.BatchRackBean;
import manage.route.pojo.CableInfoBean;
import manage.route.pojo.FiberBoxInfoBean;
import manage.route.pojo.FiberInfoBean;
import manage.route.pojo.JointInfoBean;
import manage.route.pojo.RouteInfoBean;
 
 public class PDARouteServiceImpl extends DataBase
   implements PDARouteService
 {
   private JdbcTemplate jdbcTemplate;
   private static final String GET_ROUTE = "pdaroute.getRoute";
   private static final String GET_CABLE = "pdaroute.getCable";
   private static final String GET_CABLE_ROUTE = "pdaroute.getCableRoute";
   private static final String GET_JOINT = "pdaroute.getJoint";
   private static final String GET_FIBERBOX = "pdaroute.getFiberbox";
   private static final String GET_GENERATOR = "pdagenerator.getGenerator";
   private static final String UPDATE_ROUTE = "pdaroute.updateRoute";
   private static final String UPDATE_CABLE = "pdaroute.updateCable";
   private static final String UPDATE_CABLE_ROUTE = "pdaroute.updateCableRoute";
   private static final String UPDATE_JOINT = "pdaroute.updateJoint";
   private static final String UPDATE_FIBERBOX = "pdaroute.updateFiberbox";
   private static final String INSERT_ROUTE = "pdaroute.insertRoute";
   private static final String INSERT_CABLE = "pdaroute.insertCable";
   private static final String INSERT_CABLE_ROUTE = "pdaroute.insertCableRoute";
   private static final String INSERT_JOINT = "pdaroute.insertJoint";
   private static final String INSERT_FIBERBOX = "pdaroute.insertFiberbox";
   private static final String CHECK_ROUTE = "pdaroute.checkRouteName";
   private static final String CHECK_CABLE = "pdaroute.checkCableName";
   private static final String DELETE_ROUTE = "pdaroute.deleteRoute";
   private static final String DELETE_JOINT = "pdaroute.deleteJoint";
   private static final String DELETE_CABLE = "pdaroute.deleteCable";
   private static final String DELETE_FIBERBOX = "pdaroute.deleteFiberbox";
   private static final String CHECK_JOINT = "pdaroute.checkJointName";
   private static final String CHECK_FIBERBOX = "pdaroute.checkFiberboxName";
   private static final String GET_FIBER ="pdaroute.getFiberGrid";
   private static final String UPDATE_FIBER = "pdaroute.updateFiber";
   private static final String INSERT_FIBER = "pdaroute.insertFiber";
   private static final String CHECK_FIBER = "pdaroute.checkFiberName";
   public List<RouteInfoBean> getRoute(RouteInfoBean route)
     throws DataAccessException
   {
     return getObjects("pdaroute.getRoute", route);
   }
 
   public Integer insertRoute(RouteInfoBean route) throws DataAccessException {
     RouteInfoBean ro = new RouteInfoBean();
     ro.setRoutename(route.getRoutename());
     ro = (RouteInfoBean)getObject("pdaroute.checkRouteName", route);
     if (ro == null) {
       return ((Integer)insert("pdaroute.insertRoute", route));
     }else{
    	 return Integer.valueOf(-1);
     }
   }
 
   public Integer updateRoute(RouteInfoBean route)
     throws DataAccessException
   {
     RouteInfoBean ro = new RouteInfoBean();
     ro.setRoutename(route.getRoutename());
     ro = (RouteInfoBean)getObject("pdaroute.checkRouteName", route);
     if ((ro == null) || (ro.getRouteid().intValue() == route.getRouteid().intValue())) {
       return Integer.valueOf(update("pdaroute.updateRoute", route));
     }else{
    	 return Integer.valueOf(-1);
     }
   }
 
   public List<CableInfoBean> getCable(CableInfoBean cable)
     throws DataAccessException
   {
	 if(TextUtil.isNotNull(cable.getCablename())){
		 String cableName = cable.getCablename();
		 if(cableName.contains(" ")){
			 cableName= cableName.replaceAll(" +", "%");
		 }
		 cable.setCablename("%"+cableName+"%");
	 }
     return getObjects("pdaroute.getCable", cable);
   }
   
   
   /**
    * 得到光缆段信息
    * @param cable
    * @return
    */
   public CableInfoBean getCableObj(CableInfoBean cable){
	   CableInfoBean obj = (CableInfoBean) getObject("pdaroute.getCableObj", cable);
	   if(obj == null){
		   cable.setResNum(cable.getCableid()+"");
		   cable.setCableid(null);
		   obj = (CableInfoBean) getObject("pdaroute.getCableObj", cable);
	   }
	   return obj;
   }
   
   CableInfoBean getCableLay(CableInfoBean cable){
	   String sql = "select distinct cableId,cableName  from opticab_lay "
				+ " where spanId ='"+cable.getResNum()+"' and spanType in (9202,5) and deleteFlag ='0'"
				+ " union all "
				+ " select distinct cableId,cableName  from opticab_lay "
				+ " where spanId ='"+cable.getCableid()+"' and spanType in (9202,5) and deleteFlag ='0'";
	   List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
	   String cableName = super.getStrList(list, "cableName");
	   String cableId = super.getStrList(list, "cableId");
	   cable.setCableOptical(cableName);
	   cable.setCableOpticalId(cableId);
	   return cable;
   }
 
   /**
    * 增加光缆段
    */
   public Integer insertCable(CableInfoBean cable) throws DataAccessException {
	   CableInfoBean obj = new CableInfoBean();
	   obj.setCablename(cable.getCablename());
	   obj = (CableInfoBean)getObject("pdaroute.checkCableName", obj);
	   if(obj == null){
		   return ((Integer)insert("pdaroute.insertCable", cable));
	   }else{
		   return Integer.valueOf(-1);
	   }
   }
 
   public Integer updateCable(CableInfoBean cable) throws DataAccessException {
     CableInfoBean ca = new CableInfoBean();
     ca.setCablename(cable.getCablename());
     ca = (CableInfoBean)getObject("pdaroute.checkCableName", ca);
     if ((ca == null) || (ca.getCableid().intValue() == cable.getCableid().intValue())) {
       return Integer.valueOf(update("pdaroute.updateCable", cable));
     }else{
    	 return Integer.valueOf(-1);
     }
   }
 
   public Integer deleteCable(CableInfoBean cable) throws DataAccessException {
     update("pdaroute.deleteCable", cable);
     return Integer.valueOf(1);
   }
 
   public Integer delteRoute(RouteInfoBean route) throws DataAccessException {
     update("pdaroute.deleteRoute", route);
     return Integer.valueOf(1);
   }
 
   public List<JointInfoBean> getJoint(JointInfoBean joint)
     throws DataAccessException
   {
	 if(TextUtil.isNotNull(joint.getJointName())){
		 String jointName = joint.getJointName();
		 if(jointName.contains(" ")){
			 jointName= jointName.replaceAll(" +", "%");
		 }
		 joint.setJointName("%"+jointName+"%");
	 }
     return getObjects("pdaroute.getJoint", joint);
   }
 
   public Integer insertJoint(JointInfoBean joint) throws DataAccessException {
     /*JointInfoBean j = new JointInfoBean();
     j.setJointNameSub(joint.getJointNameSub());
     j = (JointInfoBean)getObject("pdaroute.checkJointName", j);
     if (j == null) {
       return ((Integer)insert("pdaroute.insertJoint", joint));
     }else{
    	return Integer.valueOf(-1);
     }*/
	 return ((Integer)insert("pdaroute.insertJoint", joint));
    
   }
 
   public Integer updateJoint(JointInfoBean joint) throws DataAccessException
   {
	   return Integer.valueOf(update("pdaroute.updateJoint", joint));
   }
 
   public Integer deleteJoint(JointInfoBean joint)
     throws DataAccessException
   {
     update("pdaroute.deleteJoint", joint);
     return Integer.valueOf(1);
   }
 
   public List<FiberBoxInfoBean> getFiberbox(FiberBoxInfoBean fiberbox) throws DataAccessException
   {
	 if(TextUtil.isNotNull(fiberbox.getFiberboxName())){
		 String fiberName = fiberbox.getFiberboxName();
		 if(fiberName.contains(" ")){
			 fiberName= fiberName.replaceAll(" +", "%");
		 }
		 fiberbox.setFiberboxName("%"+fiberName+"%");
	 }
     List<FiberBoxInfoBean> list = getObjects("pdaroute.getFiberbox", fiberbox);
     return list;
   }
 
   public Integer insertFiberbox(FiberBoxInfoBean fiberbox) throws DataAccessException
   {
     FiberBoxInfoBean f = new FiberBoxInfoBean();
     f.setFiberboxName(fiberbox.getFiberboxName());
     f = (FiberBoxInfoBean)getObject("pdaroute.checkFiberboxName", f);
     if (f == null) {
       return ((Integer)insert("pdaroute.insertFiberbox", fiberbox));
     }else{
    	 return Integer.valueOf(-1);
     }
   }
 
   public Integer updateFiberbox(FiberBoxInfoBean fiberbox)
     throws DataAccessException
   {
	   return Integer.valueOf(update("pdaroute.updateFiberbox", fiberbox));
   }
 
   public Integer deleteFiberbox(FiberBoxInfoBean fiberbox)
     throws DataAccessException
   {
     return Integer.valueOf(update("pdaroute.deleteFiberbox", fiberbox));
   }
   
   /**
    * 得到纤芯的数据
    * @param fiber
    * @return
    */
   public List<FiberInfoBean> getFiber(FiberInfoBean fiber){
	   List<FiberInfoBean> list = getObjects(GET_FIBER, fiber);
	   return list;
   }
   
   /**
    * 根据光缆段得
    * 到纤芯数据
    * @param fiber
    * @return
    */
   public List<FiberInfoBean> getFiberList(FiberInfoBean fiber){
	   List<FiberInfoBean> list = new LinkedList();
	   try{
		   CableInfoBean cable = new CableInfoBean();
		   cable.setCableid(Integer.parseInt(fiber.getCableId()));
		   cable = (CableInfoBean) getObject("pdaroute.getCableObj", cable);
		   StringBuffer sb = new StringBuffer();
		   sb.append("select distinct id,zhLabel,alias,status"
				   + " startType,startId,startName,"
			   		+ " endType,endId,endName,"
			   		+ " startDeviceType,startDeviceId,startDeviceName,"
			   		+ " endDeviceType,endDeviceId,endDeviceName,"
			   		+ " startPortType,startPortId,startPortName,"
			   		+ " endPortType,endPortId,endPortName,cableId,resNum "
		   		+ " from (");
		   sb.append("select t.id,t.zhLabel,t.alias,t.status,"
		   		+ " t.startType,t.startId,t.startName,"
		   		+ " t.endType,t.endId,t.endName,"
		   		+ " t.startDeviceType,t.startDeviceId,t.startDeviceName,"
		   		+ " t.endDeviceType,t.endDeviceId,t.endDeviceName,"
		   		+ " t.startPortType,t.startPortId,t.startPortName,"
		   		+ " t.endPortType,t.endPortId,t.endPortName,t.cableId,t.resNum "
		   		+ " from job_fiber t where t.deleteflag='0' "
		   		+ " and t.cableId ='"+cable.getCableid()+"'"
		   		+ "");
		   if(TextUtil.isNotNull(cable.getResNum())){
			   sb.append(" union all ");
			   sb.append("select t.id,t.zhLabel,t.alias,t.status,"
				   		+ " t.startType,t.startId,t.startName,"
				   		+ " t.endType,t.endId,t.endName,"
				   		+ " t.startDeviceType,t.startDeviceId,t.startDeviceName,"
				   		+ " t.endDeviceType,t.endDeviceId,t.endDeviceName,"
				   		+ " t.startPortType,t.startPortId,t.startPortName,"
				   		+ " t.endPortType,t.endPortId,t.endPortName,t.cableId,t.resNum "
				   		+ " from job_fiber t where t.deleteflag='0' "
				   		+ " and t.cableId ='"+cable.getResNum()+"'"
				   		+ "");
		   }
		   sb.append(") b"); 
		   List<Map<String, Object>> fiberList = this.jdbcTemplate.queryForList(sb.toString());
		   for(Map<String, Object> map : fiberList){
			   FiberInfoBean fibers = new FiberInfoBean();
			   fibers.setId(Integer.parseInt(map.get("id")+""));
			   fibers.setZhLabel(map.get("zhLabel")+"");
			   fibers.setAlias(map.get("alias")+"");
			   fibers.setStartDeviceId(map.get("startDeviceId")+"");
			   fibers.setStartDeviceName(map.get("startDeviceName")+"");
			   fibers.setEndDeviceId(map.get("endDeviceId")+"");
			   fibers.setEndDeviceName(map.get("endDeviceName")+"");
			   fibers.setStartPortName(map.get("startPortName")+"");
			   fibers.setEndPortName(map.get("endPortName")+"");
			   list.add(fibers);
			   /*FiberInfoBean fibers = (FiberInfoBean) BeanUtil.mapToObject(map, FiberInfoBean.class);
			   System.out.println(map.get("zhLabel")+"=====");
			   list.add(fibers);
			   System.out.println(fibers.getZhLabel()+"====");*/
		   }
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   return list;
   }
   
   /**
    * 修改纤芯数据
    * @param fiber
    * @return
    */
   public Integer updateFiber(FiberInfoBean fiber){
	   return Integer.valueOf(update(UPDATE_FIBER, fiber)); 
   }
   
   /**
    * 增加纤芯数据
    * @param fiber
    * @return
    */
   public Integer insertFiber(FiberInfoBean fiber){
	   return (Integer) insert(INSERT_FIBER, fiber);
   }
   
   /**
    * 得到光缆段计算距离
    * @param cable
    * @return
    */
   public String getCableLength(CableInfoBean cable){
	   String distinct = "0";
	   String startSql = this.getPointSql(cable.getStartDeviceType()+"", cable.getStartDeviceId());
	   String endSql = this.getPointSql(cable.getEndDeviceType()+"", cable.getEndDeviceId());
	   if(TextUtil.isNotNull(startSql) && TextUtil.isNotNull(endSql)){
		   List<Map<String, Object>> startList = this.jdbcTemplate.queryForList(startSql);
		   List<Map<String, Object>> endList = this.jdbcTemplate.queryForList(endSql);
		   if(TextUtil.isNotNull(startList) && TextUtil.isNotNull(endList)){
			   Map<String, Object> start = startList.get(0);
			   Map<String, Object> end = endList.get(0);
			   if(start != null && end != null && start.get("lon") != null && start.get("lat") != null && end.get("lon") != null && end.get("lat")!= null){
				   distinct = MapUtil.Distance(Double.parseDouble(start.get("lon")+""),
							 Double.parseDouble(start.get("lat")+""),
							 Double.parseDouble(end.get("lon")+""),
							 Double.parseDouble(end.get("lat")+""));
			   }
		   }
	   }
	   return distinct;
   }
   
   /**
    * 得到点的经纬度
    * 距离长度
    * @param type
    * @param id
    * @return
    */
   String getPointSql(String type,String id){
	   StringBuffer sql = new StringBuffer();
	   if(type.equals("1")){
		   //光交箱
		   sql.append("select LON AS lon,LAT as lat from job_equtinfo e where ETYPE = 3"
		   		+ " and EID = '"+id+"'");
	   }else if(type.equals("2")){
		   //接头盒
		   sql.append("select "
		   		+ " case t.addrType "
		   		+ " when 2 then (select poleLongitude as lon from poleinfo where poleId = t.poleId )"
		   		+ " when 1 then (select longitude as lon from wellinfo where wellId = t.wellId )"
		   		+ " when 3 then (select longitude as lon from stoneinfo where stoneId = t.stoneId)"
		   		+ " end  as lon,"
		   		+ " case t.addrType "
		   		+ " when 2 then (select poleLatitude as lat from poleinfo where poleId = t.poleId ) "
		   		+ " when 1 then (select latitude as lat from wellinfo where wellId = t.wellId )"
		   		+ " when 3 then (select latitude as lat from stoneinfo where stoneId = t.stoneId)"
		   		+ " end  as lat"
		   		+ " from job_joint t where 1=1 "
		   		+ " and t.jointId ='"+id+"' ");
		   
	   }else if(type.equals("3")){
		   //站点
		   sql.append("select lon,lat from job_stationbase where stationBaseId = '"+id+"'");
		   
	   }else if(type.equals("4")){
		   //光终端盒
		   sql.append("select longitude as lon , latitude as lat from optical_terminal"
		   		+ " where id = '"+id+"'");
	   }
	   return sql.toString();
   }
   
   
   /**
    * 得到敷设的光缆段
    * @param ecable
    * @return
    */
   public List<CableInfoBean> getLayCable(EqutCableInfo ecable){
	   List<CableInfoBean> list = new ArrayList<CableInfoBean>();
	   String sql = "";
	   if(ecable.getEtype().equals(3)){
		   //光交箱
		   sql="select EID as deviceId,resNum as resNum  from job_equtinfo"
					+ " where ETYPE = 3 and eid ='"+ecable.geteId()+"'";
	   }else if(ecable.getEtype().equals(1)){
		   if(TextUtil.isNotNull(ecable.getsId())){
			 //站点
			   sql = "select stationBaseId as deviceId,resNum as resNum from job_stationbase"
			   		+ " where stationBaseId='"+ecable.getsId()+"'";
		   }else if(TextUtil.isNotNull(ecable.geteId())){
			   String esql ="select g.areano from job_equtinfo e ,job_generator g "
			   		+ " where e.gid = g.generatorId and e.ETYPE = 1 and e.EID ='"+ecable.geteId()+"'";
			   int areano = this.jdbcTemplate.queryForInt(esql);
			   sql = "select stationBaseId as deviceId,resNum as resNum from job_stationbase"
				   		+ " where stationBaseId='"+areano+"'";
		   }
		   
	   }
	   List<Map<String, Object>> deviceList = this.jdbcTemplate.queryForList(sql);
	   String deviceStr = this.getStr(deviceList, "deviceId");
	   String resNum = this.getStr(deviceList, "resNum");
	   if(TextUtil.isNotNull(resNum)){
		   deviceStr =deviceStr+","+resNum;
	   }
	   
	   String cSql = "select cableId,cableName,cableType,deviceId from devicecable where "
				+ " deviceId in ("+deviceStr+")";
	   if(TextUtil.isNotNull(ecable.getCableName())) {
		   cSql +=" and cableName like '%"+ecable.getCableName()+"%'";
	   }
	   List<Map<String, Object>> cableList = this.jdbcTemplate.queryForList(cSql);
		
	   if(TextUtil.isNotNull(cableList)){
			for(Map<String,Object> map : cableList){
				CableInfoBean cable = new CableInfoBean();
				cable.setCableid(Integer.parseInt(map.get("cableId").toString()));
				cable.setCablename(map.get("cableName").toString());
				list.add(cable);
			}
		}
		
	   return list;
   }
   
   
   /**
    * 根据纤芯的主键
    * 得到纤芯封装数据
    * @param fiber
    * @return
    */
   public FiberInfoBean getFiberObj(FiberInfoBean fiber){
	   fiber = (FiberInfoBean) getObject("pdaroute.getFiber", fiber);
	   return fiber;
   }
   
   
   /**
    * 纤芯直接上架
    * @param type
    * @param cable
    */
   public void setCableRack(String type,CableInfoBean cable){
	   
   }
   
   
   
   String getStr(List<Map<String, Object>> list , String key){
		String str = "";
		if(TextUtil.isNotNull(list)){
			for(Map<String, Object> map : list){
				str += "'"+map.get(key)+"',";
			}
		}
		if(str.endsWith(",")){
			str = str.substring(0, str.length() -1);
		}
		return str;
	}
   
   /**
    * 得到纤芯数据
    * @param cableId
    * @param fiberIds
    * @return
    */
   public List<FiberInfoBean> getFiberList(BatchRackBean obj){
	   List<FiberInfoBean> fiberList = getObjects("pdaroute.getFiberList", obj);
	   return fiberList;
   }
   
   
   
   /**
    * 批量修改端子的跳纤成端信息
    * @param pointList
    */
   public void batchPutPoint(final List<PointInfoBean> pointList) {
	   String upSql ="update job_pointinfo "
	   		+ " set PSTAT = 1,fiberName =?,oppsite =?"
	   		+ " where id =?"
	   		+ "";
	   this.jdbcTemplate.batchUpdate(upSql, new BatchPreparedStatementSetter() {
		@Override
		public int getBatchSize() {
			return pointList.size();
		}
		@Override
		public void setValues(PreparedStatement ps, int i) throws SQLException {
			PointInfoBean point = pointList.get(i);
			ps.setString(1, point.getFiberName());
			ps.setString(2, point.getOppsite());
			ps.setInt(3, point.getId());
		}
	   });
   }
   
   /**
    * 批量更新
    * @param pointIds
    */
   public void batchPoint(String pointIds){
	   if((pointIds.endsWith(","))){
		   pointIds = pointIds.substring(0, pointIds.length()-1);
	   }
	   String sql = "update job_pointinfo "
	   		+ " set pstat = 1"
	   		+ " where id in ("+pointIds+")";
	   this.jdbcTemplate.execute(sql);
   }
   
   
   /**
    * 批量纤芯成端
    * @param obj
    * @param list
    */
   public List<PointInfoBean> batchFiber(BatchRackBean obj ,List<FiberInfoBean> list){
	   String rackSide = obj.getRackSide();
	   String pointId = obj.getPointIds();
	   if(pointId.endsWith(",")){
		   pointId = pointId.substring(0, pointId.length()-1);
	   }
	   String[] pointIds = pointId.split(",");
	   List<PointInfoBean> pointList = new LinkedList<PointInfoBean>();
	   for(int i=0;i<pointIds.length;i++){
		   FiberInfoBean fiber = list.get(i);
		   PointInfoBean point = new PointInfoBean();
		   point.setId(Integer.parseInt(pointIds[i]));
		   point.setFiberName(fiber.getZhLabel());
		  
		   fiber.setStatus(1);
		   fiber.setCheckSuccess(-1);//-1代表刚进行成端
		   if(rackSide.equals("0")){
			   //A端设备信息
			   fiber.setStartDeviceId(obj.getRackId());
			   fiber.setStartPortId(Integer.parseInt(pointIds[i]));
			   point.setOppsite(fiber.getEndPortName());
		   }else{
			   //Z端信息
			   fiber.setEndDeviceId(obj.getRackId());
			   fiber.setEndPortId(Integer.parseInt(pointIds[i]));
			   point.setOppsite(fiber.getStartPortName());
		   }
		   pointList.add(point);
		   this.updateFiber(fiber);
	   }
	   return pointList;
   }

   public JdbcTemplate getJdbcTemplate() {
	return jdbcTemplate;
   }

   public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
   }
   
}
