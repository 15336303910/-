 package interfaces.pdainterface.pipe.service.impl;
 
 import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.CommonUtil;
import base.util.DirectionJudge;
import base.util.MapUtil;
import base.util.TextUtil;
import interfaces.pdainterface.pipe.service.PDAPipeService;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import edu.emory.mathcs.backport.java.util.LinkedList;
import manage.device.pojo.DeviceInfoBean;
import manage.pipe.pojo.FaceInfoBean;
import manage.pipe.pojo.LedupInfoBean;
import manage.pipe.pojo.PipeInfoBean;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.pipe.pojo.TubeInfoBean;
import manage.pipe.pojo.WellInfoBean;
import manage.poleline.pojo.PoleInfoBean;
 
 public class PDAPipeServiceImpl extends DataBase
   implements PDAPipeService
 {
	 private JdbcTemplate jdbcTemplate;
   private static final String GET_PIPE = "pdapipe.getPipe";
   private static final String GET_PIPE_SEGMENT = "pdapipe.getPipeSegment";
   private static final String GET_WELL = "pdapipe.getWell";
   private static final String GET_TUBE = "pdapipe.getTube";
   private static final String GET_FACE = "pdapipe.getFace";
   private static final String GET_FACE_RELATION = "pdapipe.getFaceRelation";
   private static final String GET_LEDUP = "pdapipe.getLedup";
   private static final String UPDATE_PIPE = "pdapipe.updatePipe";
   private static final String UPDATE_PIPE_SEGMENT = "pdapipe.updatePipeSegment";
   private static final String UPDATE_WELL = "pdapipe.updateWell";
   private static final String UPDATE_TUBE = "pdapipe.updateTube";
   private static final String UPDATE_FACE = "pdapipe.updateFace";
   private static final String UPDATE_LEDUP = "pdapipe.updateLedup";
   private static final String DELETE_FACE_RELATION = "pdapipe.deleteFaceRelation";
   private static final String INSERT_PIPE = "pdapipe.insertPipe";
   private static final String INSERT_PIPE_SEGMENT = "pdapipe.insertPipeSegment";
   private static final String INSERT_WELL = "pdapipe.insertWell";
   private static final String INSERT_TUBE = "pdapipe.insertTube";
   private static final String INSERT_FACE = "pdapipe.insertFace";
   private static final String INSERT_FACE_RELATION = "pdapipe.insertFaceRelation";
   private static final String INSERT_LEDUP = "pdapipe.insertLedup";
   private static final String DELETE_WELL = "pdapipe.deleteWell";
   private static final String DELETE_PIPE = "pdapipe.deletePipe";
   private static final String DELETE_PIPESEG = "pdapipe.deletePipeseg";
   private static final String DELETE_FACE = "pdapipe.deleteFace";
   private static final String DELETE_TUBE = "pdapipe.deleteTube";
   private static final String DELETE_LEDUP = "pdapipe.deleteLedup";
   private static final String CHECK_WELL = "pdapipe.checkWellName";
   private static final String CHECK_PIPE = "pdapipe.checkPipeName";
   private static final String CHECK_PIPESEG = "pdapipe.checkPipesegName";
   private static final String GET_POLE = "pdapoleline.getPole";
 
   public List<WellInfoBean> getWell(WellInfoBean well)
     throws DataAccessException
   {
	 if(TextUtil.isNotNull(well.getPipeId())){
		 well.setLone(null);
		 well.setLate(null);
		 well.setLongitude(null);
		 well.setLons(null);
		 well.setLatitude(null);
		 well.setLats(null);
	 }
	 if(TextUtil.isNotNull(well.getWellNameSub())){
		 well.setWellName(well.getWellNameSub());
		 well.setWellNameSub(null);
	 }
	 
	 if(TextUtil.isNotNull(well.getWellName())){
		 String wellName = well.getWellName();
		 if(wellName.contains(" ")){
			 wellName= wellName.replaceAll(" +", "%");
		 }
		 well.setWellName("%"+wellName+"%");
	 }
	 //进行跨区域
	 if( TextUtil.isNotNull(well.getRegion()) && well.getRegion().contains("*")) {
		 String[] areas = well.getRegion().split("\\*");
		 String sql = "";
		 for(String area : areas) {
			 sql +=" instr(region, '"+area+"') > 0 or";
		 }
		 if(TextUtil.isNotNull(sql) && sql.endsWith("or")) {
			 sql = sql.substring(0,sql.length()-2);
			 well.setRegion(null);
			 well.setExtendsSql(sql);
		 }
	 }
     return getObjects("pdapipe.getWell", well); 
   }
   
   /**
    * 
    * @param well
    * @return
    */
   public WellInfoBean getWellObj(WellInfoBean well){
	   well = (WellInfoBean) getObject("pdapipe.getWell", well); 
	   return well;
   }
   
   /**
    * 根据id得到井对象
    * @param wellId
    * @return
    */
   public WellInfoBean getWellByid(Integer wellId){
	   WellInfoBean well = new WellInfoBean();
	   well.setWellId(wellId);
	   return (WellInfoBean) getObject("pdapipe.getWell", well); 
   }
   
   /**
    * 得到井的敷设
    * @param well
    * @return
    */
   public boolean getWellLay(WellInfoBean well){
	   StringBuffer sql = new StringBuffer();
		  sql.append("select id from opticab_lay where atype in (9101,3) and aid in (");
		  sql.append(well.getWellId());
		  if(TextUtil.isNotNull(well.getResNum())){
			  sql.append(","+well.getResNum());
		  }
		  sql.append(") and deleteFlag = '0' ");
		  sql.append(" union all ");
		  sql.append(" select id from opticab_lay where ztype in (9101,3)  and zid in ( ");
		  sql.append(well.getWellId());
		  if(TextUtil.isNotNull(well.getResNum())){
			  sql.append(","+well.getResNum());
		  }
		  sql.append(") and deleteFlag = '0' ");
		  List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString());
		  if(TextUtil.isNotNull(list)){
			  //存在敷设
			  return true;
		  }else{
			  return false;
		  }
   }
 
   public Integer insertWell(WellInfoBean well) throws DataAccessException {
     WellInfoBean we = new WellInfoBean();
     we.setWellName(well.getWellName());
     we = (WellInfoBean)getObject("pdapipe.checkWellName", we);
     if (we == null) {
       int wellId = ((Integer)insert("pdapipe.insertWell", well)).intValue();
       List mian = new ArrayList();
       mian.add("西北"); mian.add("北"); mian.add("东北"); mian.add("东");
       mian.add("东南"); mian.add("南"); mian.add("西南"); mian.add("西");
       int i = 0; 
       while (true) { FaceInfoBean face = new FaceInfoBean();
         face.setCols(Integer.valueOf(1));
         face.setRows(Integer.valueOf(1));
         face.setWellId(Integer.valueOf(wellId));
         face.setLocationNo(mian.get(i).toString());
         insert("pdapipe.insertFace", face);
 
         ++i; if (i >= mian.size())
         {
            return Integer.valueOf(wellId); } }
     }else{
    	 return Integer.valueOf(-1);
     }
   }
 
   /**
    * 修改 井信息
    */
   public Integer updateWell(WellInfoBean well) throws DataAccessException
   {
	 
     WellInfoBean we = new WellInfoBean();
     we.setWellName(well.getWellName());
     we = (WellInfoBean)getObject("pdapipe.checkWellName", we);
     //代表修改当前井
     if(we == null ||  we.getWellId().equals(well.getWellId())) {
    	 return Integer.valueOf(update("pdapipe.updateWell", well));
     }else {
    	 //代表存在重复
    	 return Integer.valueOf(-1);
     }
   }
 
   public Integer deleteWell(WellInfoBean well) throws DataAccessException {
     update("pdapipe.deleteWell", well);
     return null;
   }
   
   
   /**
    * 执行下段的删除
    * @param wellId
    */
   public void delPipeSeg(String wellId){
	   String startSql = "update pipesegmentinfo t "
	   		+ " set t.deletedFlag =1"
	   		+ " where startDeviceId = '"+wellId+"'";
	   this.jdbcTemplate.execute(startSql);
	   String endSql = "update pipesegmentinfo t "
		   	+ " set t.deletedFlag =1"
		   	+ " where endDeviceId = '"+wellId+"'";
	   this.jdbcTemplate.execute(endSql);
	   if(getPropert.getValueByKey("province").equals("北京")){
		   new delWellSeg(wellId).start();
	   }
   }
   
   
   /**
    * 删除相应的
    * 引上段数据
    * @param wellId
    */
   public void delLeadupStage(String wellId) {
	   String endSql = "update leadupstage"
	   		+ " set deleteflag = 1"
	   		+ " where endType = 2 "
	   		+ " and endId ='"+wellId+"'"
	   		+ " and deleteflag = 0";
	   this.jdbcTemplate.execute(endSql);
	   
	   String startSql = " update leadupstage"
	   		+ " set deleteflag = 1"
	   		+ " where startType = 2 "
	   		+ " and startId ='"+wellId+"'"
	   		+ " and deleteflag = 0 ";
	   this.jdbcTemplate.execute(startSql);
   }
   
   /**
    * 修改井后更改相应的段信息
    * @param wellId
    * @param wellName
    */
   public void upPipeSeg(Integer wellId,String wellName){
	   String startSql ="update pipesegmentinfo"
	   		+ " set startDeviceName ='"+wellName+"',"
	   		+ " pipeSegmentName = concat('"+wellName+"','-',endDeviceName)"
	   		+ " where startDeviceId ='"+wellId+"' and deletedFlag ='0'";
	   this.jdbcTemplate.execute(startSql);
	   String endSql = "update pipesegmentinfo"
	   		+ " set endDeviceName ='"+wellName+"',"
	   		+ " pipeSegmentName = concat( startDeviceName,'-','"+wellName+"')"
	   		+ " where endDeviceId = '"+wellId+"' and deletedFlag ='0'"
	   		+ "";
	   this.jdbcTemplate.execute(endSql);
   }
   
   
   /**
    * 将删除后的附件两个点连接起来
    * @author chenqp
    *
    */
   class delWellSeg extends Thread{
	   private String resId;
	   public  delWellSeg(String resId) {
		   this.resId = resId;
	   }
	   public void run(){
		   String sql = "select wellNo,pipeId from wellinfo where wellId ='"+resId+"' and deletedFlag='0'";
		   Map<String, Object> map = jdbcTemplate.queryForMap(sql);
		   if(map != null){
			   String wellNum = map.get("wellNo")+"";
			   String startNo,endNo;
			   if(wellNum.contains("-")) {
				   String[] nums = wellNum.split("-");
				   startNo = nums[0];
				   endNo = nums[1];
			   }else {
				   startNo =(Integer.parseInt(wellNum)-1)+"";
				   endNo = (Integer.parseInt(wellNum)+1)+"";
			   }
			   //Integer wellNo = Integer.parseInt(map.get("wellNo")+"");
			   Integer pipeId = Integer.parseInt(map.get("pipeId")+"");
			   if(TextUtil.isNotNull(wellNum) && TextUtil.isNotNull(pipeId)){
				   WellInfoBean startWell = new WellInfoBean();
				   startWell.setWellNo(startNo);
				   startWell.setPipeId(pipeId+"");
				   startWell = (WellInfoBean) getObject("pdapipe.getWell", startWell);
				   
				   WellInfoBean endWell = new WellInfoBean();
				   endWell.setWellNo(endNo);
				   endWell.setPipeId(pipeId+"");
				   endWell = (WellInfoBean) getObject("pdapipe.getWell", endWell);
				   
				   if(startWell != null && endWell != null && TextUtil.isNotNull(startWell.getWellName()) && TextUtil.isNotNull(endWell.getWellName())){
					   PipeSegmentInfoBean segPipe = new PipeSegmentInfoBean();
					   segPipe.setStartDeviceId(startWell.getWellId());
					   segPipe.setStartDeviceName(startWell.getWellName());
					   segPipe.setEndDeviceId(endWell.getWellId());
					   segPipe.setEndDeviceName(endWell.getWellName());
					   segPipe.setPipeId(pipeId);
					   segPipe.setPipeSegmentName(startWell.getWellName()+"-"+endWell.getWellName()+"管道");
					   segPipe.setDataQualityPrincipal(endWell.getDataQualityPrincipal());
					   segPipe.setMaintenanceAreaName(endWell.getRegion());
					   segPipe.setParties(endWell.getParties());
					   segPipe.setConstructionSharingOrg(endWell.getConstructionSharingOrg());
					   segPipe.setSharingTypeEnumId(endWell.getConstructionSharingEnumId());
					   segPipe.setPipeSegmentLength(MapUtil.Distance(Double.parseDouble(startWell.getLongitude()),
								Double.parseDouble(startWell.getLatitude()), 
								Double.parseDouble(endWell.getLongitude()),
								Double.parseDouble(endWell.getLatitude())));
						if(TextUtil.isNotNull(segPipe.getMaintainLength())){
							segPipe.setMaintainLength(segPipe.getPipeSegmentLength());
						}
						insert("pdapipe.insertPipeSegment", segPipe);
				   }
			   }
		   }
	   };
   }
 
   public List<PipeInfoBean> getPipe(PipeInfoBean pipe) throws DataAccessException
   {
     return getObjects("pdapipe.getPipe", pipe); 
   }
 
   public Integer insertPipe(PipeInfoBean pipe) throws DataAccessException {
     PipeInfoBean pi = new PipeInfoBean();
     pi.setPipeName(pipe.getPipeName());
     pi = (PipeInfoBean)getObject("pdapipe.checkPipeName", pi);
     if (pi == null) {
       return ((Integer)insert("pdapipe.insertPipe", pipe));
     }else{
       return Integer.valueOf(-1);
     }
    
   }
 
   public Integer updatePipe(PipeInfoBean pipe) throws DataAccessException {
     PipeInfoBean pi = new PipeInfoBean();
     pi.setPipeName(pipe.getPipeName());
     pi = (PipeInfoBean)getObject("pdapipe.checkPipeName", pi);
     if ((pi == null) || (pipe.getPipeId().intValue() == pi.getPipeId().intValue())) {
       return Integer.valueOf(update("pdapipe.updatePipe", pipe));
     }else{
    	 return Integer.valueOf(-1);
     }
   }
 
   public Integer deletePipe(PipeInfoBean pipe) throws DataAccessException {
     update("pdapipe.deletePipe", pipe);
 
     return null;
   }
 
   public List<PipeSegmentInfoBean> getPipeseg(PipeSegmentInfoBean pipeseg) throws DataAccessException
   {
	 if(TextUtil.isNotNull(pipeseg.getPipeSegmentName())){
		 String segName = pipeseg.getPipeSegmentName();
		 if(segName.contains(" ")){
			 segName= segName.replaceAll(" +", "%");
		 }
		 pipeseg.setPipeSegmentName("%"+segName+"%");
	 }
	 
		if (pipeseg.getStartDeviceId() != null&& pipeseg.getEndDeviceId() != null) {
			if (pipeseg.getStartDeviceId().equals(pipeseg.getEndDeviceId())) {
				List<PipeSegmentInfoBean> list1 = getObjects("pdapipe.getPipeSegmentDir", pipeseg);
				for (PipeSegmentInfoBean bean : list1) {
					if (bean.getStartDeviceId().equals(pipeseg.getStartDeviceId())) {
						bean.setDirection(DirectionJudge.getDirection(
								bean.getStartLat(), bean.getStartLon(),
								bean.getEndLat(), bean.getEndLon()));
					} else if (bean.getEndDeviceId().equals(pipeseg.getEndDeviceId())) {
						bean.setDirection(DirectionJudge.getDirection(
								bean.getEndLat(), bean.getEndLon(),
								bean.getStartLat(), bean.getStartLon()));
					}
				}
				return list1;
			}
		}
		
		if(TextUtil.isNotNull(pipeseg.getMaintenanceAreaName()) && pipeseg.getMaintenanceAreaName().contains("*")) {
			 String[] areas = pipeseg.getMaintenanceAreaName().split("\\*");
		     String sql = "";
		     for(String area : areas) {
		       sql +=" instr(maintenanceAreaName, '"+area+"') > 0 or";
		     }
		     if(TextUtil.isNotNull(sql) && sql.endsWith("or")) {
		       sql = sql.substring(0,sql.length()-2);
		       pipeseg.setMaintenanceAreaName(null);
		       pipeseg.setExtendsSql(sql);
		     }
		}
	 
     return getObjects("pdapipe.getPipeSegment", pipeseg);
   }
   
   /**
    * 得到实体对象
    * @param pipeseg
    * @return
    * @throws Exception
    */
   public PipeSegmentInfoBean getPipeSegObj(PipeSegmentInfoBean pipeseg) throws Exception{
	   PipeSegmentInfoBean obj = (PipeSegmentInfoBean) getObject("pdapipe.getPipeSegment", pipeseg);
	   return obj;
   }
   
   PipeSegmentInfoBean getPipeSegLay(PipeSegmentInfoBean obj){
	   String sql = "select distinct cableId,cableName  from opticab_lay "
				+ " where spanId ='"+obj.getResNum()+"' and spanType in (1,9102) and deleteFlag ='0'"
				+ " union all "
				+ " select distinct cableId,cableName  from opticab_lay "
				+ " where spanId ='"+obj.getPipeSegmentId()+"' and spanType in (1,9102) and deleteFlag ='0'";
		
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		String cableName = super.getStrList(list, "cableName");
		String cableId = super.getStrList(list, "cableId");
		obj.setPipeSegOptical(cableName);
		obj.setPipeSegOpticalId(cableId);
		return obj;
   }
 
   /**
    * 增加管道段
    */
   public Integer insertPipeseg(PipeSegmentInfoBean pipeseg) throws DataAccessException {
     PipeSegmentInfoBean ps = new PipeSegmentInfoBean();
     ps.setPipeSegmentName(pipeseg.getPipeSegmentName());
     ps = (PipeSegmentInfoBean)getObject("pdapipe.checkPipesegName", ps);
     if (ps == null) {
       if(TextUtil.isNull(pipeseg.getPipeSegmentName())) {
    	   WellInfoBean start = this.getWellByid(pipeseg.getStartDeviceId());
    	   WellInfoBean end = this.getWellByid(pipeseg.getEndDeviceId());
    	   pipeseg.setPipeSegmentName(start.getWellName()+"-"+end.getWellName()+"管道段");
    	   pipeseg.setStartDeviceName(start.getWellName());
    	   pipeseg.setEndDeviceName(end.getWellName());
       }
       int id = ((Integer)insert("pdapipe.insertPipeSegment", pipeseg)).intValue();
       return Integer.valueOf(id);
     }else{
    	 return Integer.valueOf(-1);
     }
   }
   
 
   public Integer updatePipeseg(PipeSegmentInfoBean pipeseg) throws DataAccessException
   {
     PipeSegmentInfoBean ps = new PipeSegmentInfoBean();
     ps.setPipeSegmentName(pipeseg.getPipeSegmentName());
     ps = (PipeSegmentInfoBean)getObject("pdapipe.checkPipesegName", ps);
     if ((ps == null) || (ps.getPipeSegmentId().intValue() == pipeseg.getPipeSegmentId().intValue())) {
       return Integer.valueOf(update("pdapipe.updatePipeSegment", pipeseg));
     }else{
    	 return Integer.valueOf(-1);
     }
   }
   
   /**
    * 得到管道段长度
    * @param pipeseg
    * @return
    */
   public PipeSegmentInfoBean setPipeSegLength(PipeSegmentInfoBean pipeseg){
	   String sql ="select longitude as lon,latitude as lat from"
		  		+ " wellinfo where wellId in ('"+pipeseg.getStartDeviceId()+"','"+pipeseg.getEndDeviceId()+"')";
	   List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
	   Map<String, Object> start = list.get(0);
	   Map<String, Object> end = list.get(1);
	   String distinct = MapUtil.Distance(Double.parseDouble(start.get("lon")+""),
				  	Double.parseDouble(start.get("lat")+""),
				  	Double.parseDouble(end.get("lon")+""),
				  	Double.parseDouble(end.get("lat")+""));
		pipeseg.setPipeSegmentLength(distinct);
	   return  pipeseg;
   }
 
   public Integer deletePipeseg(PipeSegmentInfoBean pipeseg) throws DataAccessException
   {
     update("pdapipe.deletePipeseg", pipeseg);
     return null;
   }
 
   public List<FaceInfoBean> getFace(FaceInfoBean face) throws DataAccessException
   {
     return getObjects("pdapipe.getFace", face);
   }
 
   public Integer insertFace(FaceInfoBean face) throws DataAccessException {
     return ((Integer)insert("pdapipe.insertFace", face)); }
 
   public Integer updateFace(FaceInfoBean face) throws DataAccessException {
     update("pdapipe.updateFace", face);
     return Integer.valueOf(1);
   }
 
   public Integer deleteFace(FaceInfoBean face) throws DataAccessException {
     update("pdapipe.deleteFace", face);
     return null;
   }
 
   /**
    * 得到所有的空信息
    */
   public List<TubeInfoBean> getTube(TubeInfoBean tube) throws DataAccessException {
     List list = getObjects("pdapipe.getTube", tube);
     return list; 
    }
   
   /**
    * 得到敷设关系
    * @param tube
    * @return
    */
   public TubeInfoBean getTubeObj(TubeInfoBean tube){
	   List<TubeInfoBean> list = getObjects("pdapipe.getTube", tube);
	   if(TextUtil.isNotNull(list)){
		   tube = list.get(Integer.parseInt(tube.getTubeNumber()) -1);
	   }
	   
	   return tube;
   }
   
   TubeInfoBean getTubeLay(TubeInfoBean tube){
	   String sql = "";
	   if(TextUtil.isNotNull(tube.getResNum())){
		   sql = "select distinct cableId,cableName  from opticab_lay "
				+ " where subporeId ='"+tube.getResNum()+"' and deleteFlag = '0'"
				+ " union all ";
	   }
	   sql+= "select distinct cableId,cableName  from opticab_lay "
				+ " where subporeId ='"+tube.getTubeId()+"' and deleteFlag ='0'";
	   List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
	   String cableName = super.getStrList(list, "cableName");
	   String cableId = super.getStrList(list, "cableId");
	   tube.setBearCableSegment(cableName);
	   tube.setBearCableSegmentId(cableId);
	   return tube;
   }
 
   /**
    * 新增管孔
    */
   public Integer insertTube(TubeInfoBean tube) throws DataAccessException {
	  Integer id =  (Integer) insert(INSERT_TUBE, tube);
	  return id;
   }
   
   /**
    * 根据井得到管孔
    * @param wellId
    * @return
    */
   public List<TubeInfoBean> getTubeBywell(String wellId){
	   List<TubeInfoBean> list = new ArrayList<TubeInfoBean>();
	   String pipeSql = "select pipeSegmentId from pipesegmentinfo"
	   		+ " where startDeviceId ='"+wellId+"'"
	   		+ " union all"
	   		+ " select pipeSegmentId from pipesegmentinfo"
	   		+ " where endDeviceId = '"+wellId+"'";
	   List<Map<String, Object>> pipeList = this.jdbcTemplate.queryForList(pipeSql);
	   String pipe = "";
	   for(Map<String, Object> map : pipeList){
		   if(map.get("pipeSegmentId") != null ){
			   pipe = pipe +map.get("pipeSegmentId")+",";
		   }
	   }
	   if(TextUtil.isNotNull(pipe) && pipe.endsWith(",")){
		   pipe = pipe.substring(0, pipe.length()-1);
		   String sql = "select tubeId,tubeName,tubeNumber,"
		   		+ " pipeSegmentId,isfather,subTubeAmount,creationDate,"
		   		+ " lastUpdateDate,deletedFlag,cstate,resNum,rentFlag "
		   		+ " from tubeinfo where deletedFlag='0'"
		   		+ " and pipeSegmentId in ("+pipe+")";
		   List<Map<String, Object>> tubeList = this.jdbcTemplate.queryForList(sql);
		   for(Map<String, Object> map : tubeList){
			   TubeInfoBean tube =new TubeInfoBean();
			   tube.setTubeId(Integer.parseInt(map.get("tubeId")+""));
			   tube.setTubeName(map.get("tubeName")+"");
			   tube.setTubeNumber(map.get("tubeNumber")+"");
			   tube.setPipeSegmentId(map.get("pipeSegmentId")+"");
			   tube.setIsFather(map.get("isfather")+"");
			   tube.setResNum(map.get("resNum")+"");
			   tube.setRentFlag(map.get("rentFlag")+"");
			   list.add(tube);
		   }
	   }
	   return list;
   }
   
   /**
    * 根据管道段得到管孔
    * @param segId
    * @return
    */
   public List<TubeInfoBean> getTubeByPipe(String segId){
	   List<TubeInfoBean> list = new LinkedList();
	   String sql = "select tubeId,tubeName,tubeNumber,"
		   		+ " pipeSegmentId,isfather,subTubeAmount,creationDate,"
		   		+ " lastUpdateDate,deletedFlag,cstate,resNum,rentFlag,rentOrg "
		   		+ " from tubeinfo where deletedFlag='0'"
		   		+ " and pipeSegmentId ='"+segId+"'";
	   List<Map<String, Object>> tubeList = this.jdbcTemplate.queryForList(sql);
	   for(Map<String, Object> map : tubeList){
		   TubeInfoBean tube =new TubeInfoBean();
		   tube.setTubeId(Integer.parseInt(map.get("tubeId")+""));
		   
		   tube.setTubeName(map.get("tubeName")+"");
		   tube.setTubeNumber(map.get("tubeNumber")+"");
		   tube.setPipeSegmentId(map.get("pipeSegmentId")+"");
		   tube.setIsFather(map.get("isfather")+"");
		   tube.setResNum(map.get("resNum")+"");
		   tube.setShape(map.get("rentFlag")+"");
		   tube.setRentFlag(map.get("rentOrg")+"");
		   if(null != map.get("subTubeAmount")){
			   tube.setSubTubeAmount(Integer.parseInt(map.get("subTubeAmount")+""));
		   }
		   list.add(tube);
	   }
	   return list;
   }
   
   /**
    * 批量增加管孔
    * @param tube
    * @return
    */
   public List<TubeInfoBean> beatchTube(TubeInfoBean tube){
	   List<TubeInfoBean> list = new ArrayList<TubeInfoBean>();
	   List<TubeInfoBean> subList = new ArrayList<TubeInfoBean>();
	   if(TextUtil.isNotNull(tube.getPipeSegmentId()) && Integer.parseInt(tube.getTubeNumber()) > 0){
		   String numSql = "select count(*) from tubeinfo where isfather =1 and pipeSegmentId ='"+tube.getPipeSegmentId()+"'";
		   Integer num = this.jdbcTemplate.queryForInt(numSql);
		   int tubeNum = Integer.parseInt(tube.getTubeNumber());
		   for(int i=0;i<tubeNum;i++){
			   TubeInfoBean obj = new TubeInfoBean();
			   obj.setTubeName(tube.getPipeSegmentName()+"("+(i+1+num)+")管孔");
			   obj.setIsFather("1");
			   obj.setSubTubeAmount(tube.getSubTubeAmount());
			   obj.setDeletedFlag("0");
			   obj.setTubeNumber((i+1+num)+"");
			   obj.setRentFlag(tube.getShape());
			   obj.setRentOrg(tube.getRentFlag());
			   obj.setPipeSegmentId(tube.getPipeSegmentId());
			   obj.setWellId(tube.getWellId());
			   int id = this.insertTube(obj);
			   obj.setTubeId(id);
			   list.add(obj);
		   }
	   }
	   return list;
   }
   
   /**
    * 批量增加子孔
    * @param list
    */
   public void beatchSubTube(List<TubeInfoBean> list,String rentFlag){
	   List<TubeInfoBean> subList = new ArrayList<TubeInfoBean>();
	   for(int i = 0;i<list.size();i++){
		   TubeInfoBean tube = list.get(i);
		   for(int j=0;j<tube.getSubTubeAmount();j++){
			   TubeInfoBean obj = new TubeInfoBean();
			   obj.setTubeName(tube.getTubeName()+(j+1)+"号子孔");
			   obj.setTubeNumber((j+1)+"");
			   obj.setIsFather("2");
			   obj.setFatherPoreId(tube.getTubeId());
			   if(TextUtil.isNotNull(rentFlag)) {
				   obj.setRentFlag(rentFlag);
			   }else {
				   obj.setRentFlag(tube.getRentFlag());
			   }
			   obj.setDeletedFlag("0");
			   obj.setPipeSegmentId(tube.getPipeSegmentId());
			   obj.setWellId(tube.getWellId());
			   subList.add(obj);
		   }
	   }
	   this.beatchTubeList(subList);
   }
   
   /**
    * 利用语句批量
    * 增加子孔
    * @param list
    */
   void beatchTubeList(final List<TubeInfoBean> list){
	   String sql = "insert into tubeinfo (tubeName,tubeNumber,"
	   		+ " isfather,fatherPoreId,"
	   		+ " creationDate,deletedFlag,wellId,rentFlag)"
	   		+ " values(?,?,?,?,now(),?,?,?)";
	   this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
			@Override
			public int getBatchSize() {
				return list.size();
			}
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				TubeInfoBean obj = list.get(i);
				ps.setString(1, obj.getTubeName());
				ps.setString(2, obj.getTubeNumber());
				ps.setString(3, obj.getIsFather());
				ps.setInt(4, obj.getFatherPoreId());
				ps.setString(5, obj.getDeletedFlag());
				ps.setString(6, obj.getWellId());
				ps.setString(7, obj.getRentFlag());
			}
			
		});
   }
 
   
   /**
    * 修改管孔
    */
   public Integer updateTube(TubeInfoBean tube) throws DataAccessException {
     return Integer.valueOf(update("pdapipe.updateTube", tube)); 
   }
 
   /**
    * 删除管孔
    */
   public Integer deleteTube(TubeInfoBean tube) throws DataAccessException {
     update("pdapipe.deleteTube", tube);
     String upSubSql= "update tubeinfo set deletedFlag='1',deletionDate=now() where fatherPoreId ='"+tube.getTubeId()+"'";
     this.jdbcTemplate.execute(upSubSql);
     return null;
   }
   
   /**
    * 根据语句得到
    * 管孔信息
    * @param id
    * @param isFather
    * @return
    */
   public List<TubeInfoBean> getTubeList(String id,String isFather){
	   List<TubeInfoBean> resultList = new ArrayList<TubeInfoBean>();
	   String sql = "select tubeName,tubeId,tubeNumber,pipeSegmentId,subTubeAmount"
	   		+ " from tubeinfo  where tubeId in ("+id+")"
	   		+ " and resNum is null and isfather = '"+isFather+"'"
	   		+ " and deletedFlag ='0'";
	   List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
	   for(int i=0;i<list.size();i++){
		   TubeInfoBean tube = new TubeInfoBean();
		   Map<String, Object> map= list.get(i);
		   tube.setTubeId(Integer.parseInt(map.get("tubeId")+""));
		   tube.setTubeName(map.get("tubeName")+""); 
		   tube.setTubeNumber(map.get("tubeNumber")+"");
		   tube.setPipeSegmentId(map.get("pipeSegmentId")+"");
		   tube.setSubTubeAmount(Integer.parseInt(map.get("subTubeAmount")+""));
		   resultList.add(tube);
	   }
	   return resultList;
   }
 
   public List<LedupInfoBean> getLedup(LedupInfoBean ledup) throws DataAccessException {
     List <LedupInfoBean> ledupList = getObjects("pdapipe.getLedup", ledup);
     for (LedupInfoBean l : ledupList) {
       Integer wellId = l.getWellId();
       if ((wellId != null) && (wellId.intValue() != 0)) {
         WellInfoBean well = new WellInfoBean();
         well.setWellId(wellId);
         well = (WellInfoBean)getObject("pdapipe.getWell", well);
         if (well != null) {
           l.setWellName(well.getWellNameSub());
         }
       }
 
       Integer poleId = l.getPoleId();
       if ((poleId != null) && (poleId.intValue() != 0)) {
         PoleInfoBean pole = new PoleInfoBean();
         pole.setPoleId(poleId);
         pole = (PoleInfoBean)getObject("pdapoleline.getPole", pole);
         if (pole != null) {
           l.setPoleName(pole.getPoleNameSub());
         }
       }
     }
     return ledupList;
   }
 
   public JdbcTemplate getJdbcTemplate() {
	return jdbcTemplate;
   }

   public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
   }

public Integer insertLedup(LedupInfoBean ledup) throws DataAccessException {
     return ((Integer)insert("pdapipe.insertLedup", ledup));
   }
 
   public Integer updateLedup(LedupInfoBean ledup) throws DataAccessException {
     return Integer.valueOf(update("pdapipe.updateLedup", ledup));
   }
 
   public Integer deleteLedup(LedupInfoBean ledup) throws DataAccessException {
     return Integer.valueOf(update("pdapipe.deleteLedup", ledup));
   }
 }

