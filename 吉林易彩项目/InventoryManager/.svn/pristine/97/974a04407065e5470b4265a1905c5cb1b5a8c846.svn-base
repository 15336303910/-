package interfaces.pdainterface.poleline.service.impl;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.MapUtil;
import base.util.TextUtil;
import base.util.functions;
import interfaces.pdainterface.poleline.service.PDAPolelineService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import manage.poleline.pojo.PoleInfoBean;
import manage.poleline.pojo.PolelineInfoBean;
import manage.poleline.pojo.PolelineSegmentInfoBean;
import manage.poleline.pojo.SupportInfoBean;
import manage.poleline.pojo.SusLocationBean;
import manage.poleline.pojo.SuspensionWireInfoBean;
import manage.poleline.pojo.SuspensionWireSegInfoBean;
import manage.route.pojo.CableInfoBean;

public class PDAPolelineServiceImpl extends DataBase
  implements PDAPolelineService
{
	private JdbcTemplate jdbcTemplate;
  private static final String GET_POLELINE = "pdapoleline.getPoleline";
  private static final String GET_POLELINE_SEGMENT = "pdapoleline.getPolelineSegment";
  private static final String GET_POLE = "pdapoleline.getPole";
  private static final String GET_CABLE = "pdapoleline.getCable";
  private static final String UPDATE_POLELINE = "pdapoleline.updatePoleline";
  private static final String UPDATE_POLELINE_SEGMENT = "pdapoleline.updatePolelineSegment";
  private static final String UPDATE_POLE = "pdapoleline.updatePole";
  private static final String INSERT_POLE_HAS_ID = "pdapoleline.insertPoleHasId";
  private static final String INSERT_POLELINE = "pdapoleline.insertPoleline";
  private static final String INSERT_POLELINE_SEGMENT = "pdapoleline.insertPolelineSegment";
  private static final String INSERT_POLE = "pdapoleline.insertPole";
  private static final String DELETE_POLE = "pdapoleline.deletePole";
  private static final String DELETE_POLELINE = "pdapoleline.deletePoleline";
  private static final String DELETE_POLELINE_SEG = "pdapoleline.deletePolelineSeg";
  private static final String GET_SUPPORT = "pdapoleline.getSupport";
  private static final String INSERT_SUPPORT = "pdapoleline.insertSupport";
  private static final String UPDATE_SUPPORT = "pdapoleline.updateSupport";
  private static final String DELETE_SUPPORT = "pdapoleline.deleteSupport";
  private static final String GET_SUSPENSION = "pdapoleline.getSuspension";
  private static final String INSERT_SUSPENSION = "pdapoleline.insertSuspension";
  private static final String UPDATE_SUSPENSION = "pdapoleline.updateSuspension";
  private static final String DELETE_SUSPENSION = "pdapoleline.deleteSuspension";
  private static final String GET_SUSPENSIONSEG = "pdapoleline.getSuspensionseg";
  private static final String INSERT_SUSPENSIONSEG = "pdapoleline.insertSuspensionseg";
  private static final String UPDATE_SUSPENSIONSEG = "pdapoleline.updateSuspensionseg";
  private static final String DELETE_SUSPENSIONSEG = "pdapoleline.deleteSuspensionseg";

  public List<PoleInfoBean> getPoleByIds(String ids)
    throws DataAccessException
  {
    String[] idArr = ids.split(",");
    List pList = new ArrayList();
    for (String id : idArr) {
      PoleInfoBean p = new PoleInfoBean();
      p.setPoleId(Integer.valueOf(Integer.parseInt(id)));
      p = (PoleInfoBean)getObject("pdapoleline.getPole", p);
      pList.add(p);
    }
    return pList;
  }

  public void updatePole(List<PoleInfoBean> poleList) throws DataAccessException
  {
    for (PoleInfoBean p : poleList)
      if (p.getDeletedFlag().equals("1")) {
        int count = update("pdapoleline.updatePole", p);
      }
      else if (p.getPoleId().intValue() < 0) {
        insert("pdapoleline.insertPoleHasId", p);
      } else {
        update("pdapoleline.updatePole", p);
      }
  }

  public Integer updatePole(PoleInfoBean pole)
    throws DataAccessException
  {
	  return Integer.valueOf(update("pdapoleline.updatePole", pole));
  }
  
  /**
   * 计算两个点的位置
   * @param obj
   * @return
   */
  public PolelineSegmentInfoBean setPoleSegDistince(PolelineSegmentInfoBean obj){
	  String sql ="select poleLongitude as lon,poleLatitude as lat from"
	  		+ " poleinfo where poleId in ('"+obj.getStartDeviceId()+"','"+obj.getEndDeviceId()+"')";
	  List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
	  Map<String, Object> start = list.get(0);
	  Map<String, Object> end = list.get(1);
	  String distinct = MapUtil.Distance(Double.parseDouble(start.get("lon")+""),
			  	Double.parseDouble(start.get("lat")+""),
			  	Double.parseDouble(end.get("lon")+""),
			  	Double.parseDouble(end.get("lat")+""));
	  obj.setPoleLineSegmentLength(distinct);
	  return obj;
  }

  public Integer insertPole(PoleInfoBean pole) throws DataAccessException
  {
	  return ((Integer)insert("pdapoleline.insertPole", pole));
    
  }

  public List<PoleInfoBean> getPole(PoleInfoBean pole)
    throws DataAccessException
  {
	if(TextUtil.isNotNull(pole.getPoleId())){
		pole.setLate(null);
		pole.setLats(null);
		pole.setPoleLatitude(null);
		pole.setLone(null);
		pole.setLats(null);
		pole.setPoleLongitude(null);
	}
	if(TextUtil.isNotNull(pole.getPoleName())){
		String poleName = pole.getPoleName().trim();
		if(poleName.contains(" ")){
			poleName = poleName .replaceAll(" +", "%");
		}
		pole.setPoleName("%"+poleName+"%");
	}
	//电杆
	if(TextUtil.isNotNull(pole.getRegion()) && pole.getRegion().contains("*")) {
	     String[] areas = pole.getRegion().split("\\*");
	     String sql = "";
	     for(String area : areas) {
	       sql +=" instr(region, '"+area+"') > 0 or";
	     }
	     if(TextUtil.isNotNull(sql) && sql.endsWith("or")) {
	       sql = sql.substring(0,sql.length()-2);
	       pole.setRegion(null);
	       pole.setExtendsSql(sql);
	     }
	   }
    return getObjects("pdapoleline.getPole", pole);
  }
  
  /**
   * 得到电杆数据
   * @param pole
   * @return
   */
  public PoleInfoBean getPoleObj(Integer id){
	  PoleInfoBean obj = new PoleInfoBean();
	  obj.setPoleId(id);
	  obj = (PoleInfoBean) this.getObject("pdapoleline.getPole", obj);
	  return obj;
  }
  
  
  /**
   * 更新段信息
   * @param poleId
   * @param poleName
   */
  public void upPoleSeg(Integer poleId,String poleName){
	  String startSql = "update polelinesegmentinfo s,poleinfo p"
	  		+ " set s.poleLineSegmentName =concat('"+poleName+"','-',p.poleName)"
	  		+ " where s.endDeviceId = p.poleId and  s.startDeviceId = '"+poleId+"'"
	  		+ " and s.deletedFlag ='0'";
	  this.jdbcTemplate.execute(startSql);
	  String endSql = " update polelinesegmentinfo s,poleinfo p "
	  		+ " set s.poleLineSegmentName = concat(p.poleName,'-','"+poleName+"')"
	  		+ " where s.startDeviceId = p.poleId and s.endDeviceId = '"+poleId+"'"
	  		+ " and s.deletedFlag ='0'";
	  this.jdbcTemplate.execute(endSql);
  }
  
  
  /**
   * 得到电杆是否
   * 存在敷设信息
   * @param obj
   * @return
   */
  public boolean getPoleLay(PoleInfoBean obj){
	  if(obj == null){
		  return false;
	  }
	  StringBuffer sql = new StringBuffer();
	  sql.append("select id from opticab_lay where atype in (9104,1) and aid in (");
	  sql.append(obj.getPoleId());
	  if(TextUtil.isNotNull(obj.getResNum())){
		  sql.append(","+obj.getResNum());
	  }
	  sql.append(") and deleteFlag = '0' ");
	  sql.append(" union all ");
	  sql.append(" select id from opticab_lay where ztype in (9104,1)  and zid in ( ");
	  sql.append(obj.getPoleId());
	  if(TextUtil.isNotNull(obj.getResNum())){
		  sql.append(","+obj.getResNum());
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

  public List<PoleInfoBean> getPoleline(PolelineInfoBean poleline) throws DataAccessException
  {
    return getObjects("pdapoleline.getPoleline", poleline);
  }

  public Integer insertPoleline(PolelineInfoBean poleline) throws DataAccessException
  {
    return ((Integer)insert("pdapoleline.insertPoleline", poleline));
  }

  public Integer updatePoleline(PolelineInfoBean poleline) throws DataAccessException
  {
    return Integer.valueOf(update("pdapoleline.updatePoleline", poleline));
  }

  public Integer deletePole(PoleInfoBean pole) throws DataAccessException {
    update("pdapoleline.deletePole", pole);
    return null;
  }

  public Integer deletePolelineSeg(PolelineSegmentInfoBean polelineSeg) throws DataAccessException
  {
    return Integer.valueOf(update("pdapoleline.deletePolelineSeg", polelineSeg));
  }

  public List<PolelineSegmentInfoBean> getPolelineSeg(PolelineSegmentInfoBean polelineSeg) throws DataAccessException
  {
	if(TextUtil.isNotNull(polelineSeg.getPoleLineSegmentName())){
		String segName = polelineSeg.getPoleLineSegmentName();
		if(segName.contains(" ")){
			segName= segName.replaceAll(" +", "%");
		}
		polelineSeg.setPoleLineSegmentName("%"+segName+"%");
	}
	//进行扩展语句查询
	if(TextUtil.isNotNull(polelineSeg.getMaintenanceAreaName()) && polelineSeg.getMaintenanceAreaName().contains("*")) {
	     String[] areas = polelineSeg.getMaintenanceAreaName().split("\\*");
	     String sql = "";
	     for(String area : areas) {
	       sql +=" instr(maintenanceAreaName, '"+area+"') > 0 or";
	     }
	     if(TextUtil.isNotNull(sql) && sql.endsWith("or")) {
	       sql = sql.substring(0,sql.length()-2);
	       polelineSeg.setMaintenanceAreaName(null);
	       polelineSeg.setExtendsSql(sql);
	     }
	   }
    List <PolelineSegmentInfoBean>list = getObjects("pdapoleline.getPolelineSegment", polelineSeg);
    return list;
  }
  
  /**
   * 得到实体对象
   * @param polelineSeg
   * @return
   * @throws Exception
   */
  public PolelineSegmentInfoBean getPolelineSegObj(PolelineSegmentInfoBean polelineSeg) throws Exception{
	  polelineSeg = (PolelineSegmentInfoBean) getObject("pdapoleline.checkPoleLineSeg", polelineSeg);
	  return polelineSeg;
  }
  
  /**
   * 得到敷设信息
   * @param obj
   * @return
   * @throws Exception
   */
  public PolelineSegmentInfoBean getPolelineLay(PolelineSegmentInfoBean obj) throws Exception{
	  String sql = "select distinct cableId,cableName  from opticab_lay "
				+ " where spanId ='"+obj.getResNum()+"' and spanType in (2,9105) and deleteFlag = '0'"
				+ " union all "
				+ "select distinct cableId,cableName  from opticab_lay "
				+ " where spanId ='"+obj.getPoleLineSegmentId()+"' and spanType in (2,9105) and deleteFlag ='0'";
		
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		String cableName = super.getStrList(list, "cableName");
		String cableId = super.getStrList(list, "cableId");
		obj.setBearCableSegment(cableName);
		obj.setBearCableSegmentId(cableId);
	  return obj;
  }
  
  /**
   * 修改段的数据
   * @param poleId
   */
  public void upPloeLineSeg(String poleId){
	  String startSql = "update polelinesegmentinfo t "
	  		+ " set t.deletedFlag =1"
	  		+ " where startDeviceId = '"+poleId+"'";
	  this.jdbcTemplate.execute(startSql);
	  String endSql = "update polelinesegmentinfo t "
		  		+ " set t.deletedFlag =1"
		  		+ " where endDeviceId ='"+poleId+"'";
	  this.jdbcTemplate.execute(endSql);
	  if(getPropert.getValueByKey("province").equals("北京")){
		 new delPoleSeg(poleId).start();
	  }
	  
  }

  
  /**
   * 将现有点做下设计
   * @author chenqp
   *
   */
  class delPoleSeg extends Thread{
	  private String resId;
	  public delPoleSeg(String resId){
		  this.resId = resId;
	  }
	  @Override
	  public void run() {
		  String noSql = "select poleNo,poleLineId from poleinfo  where poleId ='"+resId+"' ";
		 Map<String, Object> map = jdbcTemplate.queryForMap(noSql);
		 if(map != null){
			Integer poleNo = Integer.parseInt(map.get("poleNo")+"");
			Integer sysId = Integer.parseInt(map.get("poleLineId")+"");
			if(TextUtil.isNotNull(poleNo) &&  TextUtil.isNotNull(sysId)){
				PoleInfoBean startPole = new PoleInfoBean();
				startPole.setPoleNo((poleNo-1)+"");
				startPole.setPoleLineId(sysId);
				startPole = (PoleInfoBean) getObject("pdapoleline.getPoleObj", startPole);
					
				PoleInfoBean endPole = new PoleInfoBean();
				endPole.setPoleNo((poleNo+1)+"");
				endPole.setPoleLineId(sysId);
				endPole = (PoleInfoBean) getObject("pdapoleline.getPoleObj", endPole);
					
				if(TextUtil.isNotNull(startPole.getPoleName()) && TextUtil.isNotNull(endPole.getPoleName())){
					PolelineSegmentInfoBean segLine = new PolelineSegmentInfoBean();
					segLine.setStartDeviceId(startPole.getPoleId());
					segLine.setStartDeviceName(startPole.getPoleName());
					segLine.setEndDeviceId(endPole.getPoleId());
					segLine.setEndDeviceName(endPole.getPoleName());
					segLine.setPoleLineId(startPole.getPoleLineId());
					segLine.setPoleLineSegmentName(startPole.getPoleName()+"-"+endPole.getPoleName()+"杆路");
					segLine.setDataQualityPrincipal(endPole.getDataQualityPrincipal());
					segLine.setParties(endPole.getParties());
					segLine.setConstructionSharingOrg(endPole.getMaintenanceOrgId());
					segLine.setMaintenanceAreaName(endPole.getMaintenanceAreaName());
					segLine.setConstructionSharingEnumId(endPole.getMaintenanceTypeEnumId());
					segLine.setPoleLineSegmentLength(MapUtil.Distance(Double.parseDouble(startPole.getPoleLongitude()),
							Double.parseDouble(startPole.getPoleLatitude()), 
							Double.parseDouble(endPole.getPoleLongitude()),
							Double.parseDouble(endPole.getPoleLatitude())));
					if(TextUtil.isNotNull(segLine.getMaintainLength())){
						segLine.setMaintainLength(segLine.getPoleLineSegmentLength());
					}
					insert("pdapoleline.insertPolelineSegment", segLine);
				}
			}
		}
		super.run();
	  }
  }
  public Integer insertPolelineSeg(PolelineSegmentInfoBean polelineSeg) throws DataAccessException
  {
	if(TextUtil.isNull(polelineSeg.getPoleLineSegmentName())) {
		PoleInfoBean start = this.getPoleObj(polelineSeg.getStartDeviceId());
		PoleInfoBean end = this.getPoleObj(polelineSeg.getEndDeviceId());
		polelineSeg.setPoleLineSegmentName(start.getPoleName()+"-"+end.getPoleName()+"杆路段");
	}
    return ((Integer)insert("pdapoleline.insertPolelineSegment", polelineSeg));
  }

  public Integer updatePolelineSeg(PolelineSegmentInfoBean polelineSeg) throws DataAccessException
  {
    return Integer.valueOf(update("pdapoleline.updatePolelineSegment", polelineSeg));
  }

  public Integer deletePoleline(PolelineInfoBean poleline)
    throws DataAccessException
  {
    update("pdapoleline.deletePoleline", poleline);
    return null;
  }

  public List<SupportInfoBean> getSupport(SupportInfoBean support) throws DataAccessException
  {
    return getObjects("pdapoleline.getSupport", support);
  }

  public Integer insertSupport(SupportInfoBean support)
    throws DataAccessException
  {
    return ((Integer)insert("pdapoleline.insertSupport", support));
  }

  public Integer updateSupport(SupportInfoBean support)
    throws DataAccessException
  {
    return Integer.valueOf(update("pdapoleline.updateSupport", support));
  }

  public Integer deleteSupport(SupportInfoBean support)
    throws DataAccessException
  {
    return Integer.valueOf(update("pdapoleline.deleteSupport", support));
  }

  public List<SuspensionWireInfoBean> getSuspensionWire(SuspensionWireInfoBean SuspensionWire)
    throws DataAccessException
  {
    return getObjects("pdapoleline.getSuspension", SuspensionWire);
  }



  public Integer insertSuspensionWire(SuspensionWireInfoBean SuspensionWire)
    throws DataAccessException
  {
    return ((Integer)insert("pdapoleline.insertSuspension", SuspensionWire));
  }

  public Integer updateSuspensionWire(SuspensionWireInfoBean SuspensionWire)
    throws DataAccessException
  {
    return Integer.valueOf(update("pdapoleline.updateSuspension", SuspensionWire));
  }

  public Integer deleteSuspensionWire(SuspensionWireInfoBean SuspensionWire)
    throws DataAccessException
  {
    return Integer.valueOf(update("pdapoleline.deleteSuspension", SuspensionWire));
  }

  public List<SuspensionWireSegInfoBean> getSuspensionseg(SuspensionWireSegInfoBean suspensionseg) throws DataAccessException
  {
    List<SuspensionWireSegInfoBean> list = getObjects("pdapoleline.getSuspensionseg", suspensionseg);
    return list;
  }

  public Integer insertSuspensionseg(SuspensionWireSegInfoBean suspensionseg)
    throws DataAccessException
  {
    return ((Integer)insert("pdapoleline.insertSuspensionseg", suspensionseg));
  }

  public JdbcTemplate getJdbcTemplate() {
	return jdbcTemplate;
}

public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
}

public Integer updateSuspensionseg(SuspensionWireSegInfoBean suspensionseg)
    throws DataAccessException
  {
    return Integer.valueOf(update("pdapoleline.updateSuspensionseg", suspensionseg));
  }

  public Integer deleteSuspensionseg(SuspensionWireSegInfoBean suspensionseg)
    throws DataAccessException
  {
    return Integer.valueOf(update("pdapoleline.deleteSuspensionseg", suspensionseg));
  }
}
