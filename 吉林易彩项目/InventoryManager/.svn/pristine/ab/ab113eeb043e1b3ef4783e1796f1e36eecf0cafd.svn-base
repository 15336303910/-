package interfaces.pdainterface.equt.service.impl;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.TextUtil;
import interfaces.pdainterface.equt.pojo.EqutCableInfo;
import interfaces.pdainterface.equt.pojo.OdmFiberInfo;
import interfaces.pdainterface.equt.service.PDAEqutInfoService;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;


import manage.device.pojo.DeviceInfoBean;
import manage.document.pojo.DocEqutInfoBean;
import manage.equt.pojo.EqutInfoBean;
import manage.equt.pojo.ODMInfoBean;
import manage.generator.pojo.GeneratorInfoBean;
import manage.generator.pojo.HighFrequencySwitchingPowerSupplyInfoBean;
import manage.opticalTerminal.pojo.OpticalTerminalObj;
import manage.point.pojo.PointInfoBean;
import manage.route.pojo.CableInfoBean;
import manage.route.pojo.FiberInfoBean;

public class PDAEqutInfoServiceImpl extends DataBase implements
		PDAEqutInfoService {
	private JdbcTemplate jdbcTemplate;
	private static final String GET_DOC_EQUT = "pdaequt.getDocEqut";
	private static final String GET_EQUT = "pdaequt.getEqut";
	private static final String UPDATE_EQUT = "pdaequt.updateEqut";
	private static final String INSERT_EQUT = "pdaequt.insertEqut";
	private static final String GET_ODM = "pdaequt.getODM";
	private static final String UPDATE_ODM = "pdaequt.updateODM";
	private static final String INSERT_ODM = "pdaequt.insertODM";
	private static final String GET_Point = "pdaequt.getPoint";
	private static final String UPDATE_Point = "pdaequt.updatePoint";
	private static final String INSERT_Point = "pdaequt.insertPoint";
	private static final String GET_PROPERTY = "pdaproperty.getProperty";
	private static final String GET_POWER = "pdagenerator.getHighFrequencySwitchingPowerSupply";
	private static final String GET_GENERATOR = "pdagenerator.getGenerator";

	public List<DocEqutInfoBean> getDocEqutList(DocEqutInfoBean docEqut)
			throws DataAccessException {
		return getObjects("pdaequt.getDocEqut", docEqut);
	}
	
	/**
	 * 判断是否有
	 * 综合设备
	 * @param sql
	 * @return
	 */
	public int getDeviceCount(String sql){
		return this.jdbcTemplate.queryForInt(sql);
	}
	
	/**
	 * 根据语句得到查询数据
	 * @param sql
	 * @return
	 */
	public List<Map<String, String>> getJdbcList(String sql){
		return this.jdbcTemplate.queryForList(sql);
	}
	
	
	/**
	 * 执行语句
	 * @param sql
	 */
	public void exeSql(String sql){
		this.jdbcTemplate.execute(sql);
	}

	public List<EqutInfoBean> getEqut(EqutInfoBean equt)
			throws DataAccessException {
		if(TextUtil.isNotNull(equt.getEname())){
			String ename = equt.getEname().trim();
			if(ename.contains(" ")){
				ename= ename.replaceAll(" +", "%");
			}
			equt.setEname("%"+ename+"%");
		}
		//判斷光交箱
		if(equt.getEtype().equals("3") && TextUtil.isNotNull(equt.getEaddr())
				&& equt.getEaddr().contains("*")) {
			String[] areas =equt.getEaddr().split("\\*");
		     String sql = "";
		     for(String area : areas) {
		       sql +=" instr(eaddr, '"+area+"') > 0 or";
		     }
		     if(TextUtil.isNotNull(sql) && sql.endsWith("or")) {
		       sql = sql.substring(0,sql.length()-2);
		       equt.setEaddr(null);
		       equt.setExtendsSql(sql);
		     }
		   }
		List<EqutInfoBean> equtlist = getObjects("pdaequt.getEqut", equt);
		for (EqutInfoBean e : equtlist) {
			if ((e.getGid() != null) && (!(e.getGid().equals("")))&& (!(e.getGid().equals("0")))) {
				GeneratorInfoBean g = new GeneratorInfoBean();
				g.setGeneratorId(Integer.valueOf(Integer.parseInt(e.getGid())));
				g = (GeneratorInfoBean) getObject("pdagenerator.getGenerator",g);
				if (g != null) {
					e.setGeneratorName(g.getGeneratorName());
				}
			}
		}
		return equtlist;
	}
	
	public int getEqutCount(EqutInfoBean equt) {
		return this.getCount("", equt);
	}
	
	/**
	 * 根据对象得到唯一对象
	 * @param equt
	 * @return
	 */
	public EqutInfoBean getEqutObj(EqutInfoBean equt){
		String eid = equt.getEid();
		EqutInfoBean obj = new EqutInfoBean();
		List<EqutInfoBean> equtList = getObjects("pdaequt.getEqut", equt);
		if(TextUtil.isNotNull(equtList)) {
			for(int i=0;i<equtList.size();i++) {
				EqutInfoBean equObj = equtList .get(i);
				if(equObj.getEid().equals(eid)) {
					obj = equObj;
				}
			}
		}
		return obj;
	}
	public int insertEqut(EqutInfoBean equt) throws DataAccessException {
		return ((Integer) insert("pdaequt.insertEqut", equt)).intValue();
	}

	public int updateEqut(EqutInfoBean equt) throws DataAccessException {
		update("pdaequt.updateEqut", equt);
		return 0;
	}

	public List<ODMInfoBean> getODM(ODMInfoBean odm) throws DataAccessException {
		return getObjects("pdaequt.getODM", odm);
	}

	/**
	 * 增加odm信息
	 */
	public List<ODMInfoBean> insertODM(List<ODMInfoBean> odmList,String loginer) throws DataAccessException {
		String odmName = "";
		ODMInfoBean first = odmList.get(0);
		if(first.getEid().startsWith("E")){
			EqutInfoBean equt = new EqutInfoBean();
			equt.setEid(first.getEid());
			equt = this.getEqutObj(equt);
			odmName = equt.getEname();
		}else if(first.getEid().startsWith("gzdh") || first.getEid().startsWith("GZDH")){
			OpticalTerminalObj opt = new OpticalTerminalObj();
			opt.setId(Integer.parseInt(first.getEid().split("_")[1]));
			opt = (OpticalTerminalObj) this.getObject("opticalTer.getOpticalTer", opt);
			odmName =  opt.getTerminalName();
		}
		List<PointInfoBean> pointList = new ArrayList();
		for(ODMInfoBean o: odmList){
			o.setOdmName(odmName+"-"+o.getOdmCode());
			o.setCuser(loginer);
			int id  = (Integer) this.insert("pdaequt.insertODM", o);
			o.setOdmId(id);
			int row = Integer.parseInt(o.getTerminalRowQuantity());
			int col = Integer.parseInt(o.getTerminalColumnQuantity());
			
			for (int i = 0; i < row; ++i) {
				for (int j = 0; j < col; ++j) {
					PointInfoBean p = new PointInfoBean();
					p.setEid(o.getEid());
					String odmCode = o.getOdmCode();
					p.setOdmCode(o.getOdmCode());
					String pid = ((odmCode.length() == 1) ? "0" + odmCode
							: odmCode)
							+ ((i + 1 < 10) ? "0" + (i + 1)
									: new StringBuilder().append(i + 1)
											.toString())
							+ ((j + 1 < 10) ? "0" + (j + 1)
									: new StringBuilder().append(j + 1)
											.toString());
					if(TextUtil.isNotNull(o.getOdmName())){
						p.setPos(o.getOdmName()+"-"+pid);
					}
					p.setPlineno((j+1)+"");
					p.setProwno((i+1)+"");
					p.setPid(pid);
					p.setPstat("0");
					p.setDirection("1");
					p.setPtype("1");
					p.setDel("0");
					//将端子和odm关联起来
					p.setOdmId(id);
					p.setMflag(Integer.valueOf(1));
					pointList.add(p);
				}
			}
		}
		//batchInsert("pdaequt.insertPoint", pointList);
		this.batchInsertPoint(pointList);
		return odmList;
	}
	
	public void batchInsertPoint(final List<PointInfoBean> pointList){
		String sql =" insert into job_pointinfo "
				+ " ( plineno,prowno,eid,odmCode,pid,pstat,ptype,direction,mflag,"
				+ "  mtime,del,"
				+ " odmId,pos)"
				+ " values"
				+ " (?,?,?,?,?,?,?,?,?,now(),'0',?,?)"
				+ "";
		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
			@Override
			public int getBatchSize() {
				return pointList.size();
			}
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				PointInfoBean obj = pointList.get(i);
				ps.setString(1, obj.getPlineno());
				ps.setString(2, obj.getProwno());
				ps.setString(3, obj.getEid());
				ps.setString(4, obj.getOdmCode());
				ps.setString(5, obj.getPid());
				ps.setString(6, obj.getPstat());
				ps.setString(7, obj.getPtype());
				ps.setString(8, obj.getDirection());
				ps.setInt(9, obj.getMflag());
				ps.setInt(10, obj.getOdmId());
				ps.setString(11, obj.getPos());
			}
			
		});
	}
	
	/**
	 * 修改odm对象
	 * @param odm
	 */
	public void updateOdmObj(ODMInfoBean odm){
		try{
			update("pdaequt.updateODM", odm);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存并修改
	 * @param odmList
	 * @throws Exception
	 */
	public void upAndSaveOdm(List<ODMInfoBean> odmList) throws Exception{
		String eid = ((ODMInfoBean) odmList.get(0)).getEid();
		ODMInfoBean odm = new ODMInfoBean();
		odm.setEid(eid);
		List oldList = getObjects("pdaequt.getODM", odm);
		int oldSize = 0;
		if (oldList != null)
			oldSize = oldList.size();
		ODMInfoBean o;
		// 只是修改一条数据
		
		 
		for (int k = 0; k < odmList.size(); ++k) {
			o = (ODMInfoBean) odmList.get(k);
			int index = k + 1;
			if (index <= oldSize) {
				update("pdaequt.updateODM", o);
				upPoint(o);
			} else {
				insert("pdaequt.insertODM", o);
				upPoint(o);
			}
		}
	}

	/**
	 * 修改odm 信息
	 */
	public void updateODM(List<ODMInfoBean> odmList) throws DataAccessException {
		for(ODMInfoBean odm : odmList){
			update("pdaequt.updateODM", odm);
		}
	}

	private void upPoint(ODMInfoBean o) {
		// 删除原有的端子信息
		delete("pdaequt.deletePoint", o);
		int row = Integer.parseInt(o.getTerminalRowQuantity());
		int col = Integer.parseInt(o.getTerminalColumnQuantity());
		List pointList = new ArrayList();
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				PointInfoBean p = new PointInfoBean();
				p.setEid(o.getEid());
				String odmCode = o.getOdmCode();
				p.setOdmCode(o.getOdmCode());
				String pid = ((odmCode.length() == 1) ? "0" + odmCode : odmCode)
						+ ((i + 1 < 10) ? "0" + (i + 1) : new StringBuilder()
								.append(i + 1).toString())
						+ ((j + 1 < 10) ? "0" + (j + 1) : new StringBuilder()
								.append(j + 1).toString());
				p.setPid(pid);
				p.setPstat("1");
				p.setDirection("1");
				p.setPtype("1");
				p.setDel("0");
				p.setMflag(Integer.valueOf(1));
				pointList.add(p);
			}
		}
		batchInsert("pdaequt.insertPoint", pointList);
	}
	
	/**
	 * 得到机架所
	 * 承载的光缆段
	 * @return
	 */
	public List<EqutCableInfo> getEqutCable(EqutCableInfo eCable){
		List<EqutCableInfo> list = new ArrayList<EqutCableInfo>();
		String sql = "";
		//站点更改
		if(eCable.getEtype().equals(0)){
			sql = ""
					+ "select g.areano as deviceId,"
					+ " (select resNum from job_stationbase where stationBaseId = g.areano) as resNum "
					+ " from job_generator g "
					+ " where g.generatorId='"+eCable.getgId()+"'"
					+ " ";
		}else if(eCable.getEtype().equals(1)){
			//光交箱
			sql="select EID as deviceId,resNum as resNum  from job_equtinfo"
					+ " where ETYPE = 3 and eid ='"+eCable.geteId()+"'";
		}
		List<Map<String, Object>> deviceList = this.jdbcTemplate.queryForList(sql);
		String deviceStr = this.getStr(deviceList, "deviceId");
		String resNum = this.getStr(deviceList, "resNum");
		if(TextUtil.isNotNull(resNum)){
			deviceStr =deviceStr+","+resNum;
		}
		if(TextUtil.isNotNull(deviceStr)){
			list = this.getDeviceCable(deviceStr);
		}
		return list;
	}
	
	
	
	List<EqutCableInfo> getDeviceCable(String deviceId){
		List<EqutCableInfo> list = new ArrayList<EqutCableInfo>();
		String sql = "select cableId,cableName,cableType,deviceId from devicecable where "
				+ " deviceId in ("+deviceId+")";
		List<Map<String, Object>> cableList = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(cableList)){
			for(Map<String,Object> map : cableList){
				EqutCableInfo obj = new EqutCableInfo();
				obj.setCableId(Integer.parseInt(map.get("cableId").toString()));
				obj.setCableName(map.get("cableName").toString());
				obj.setCableItem(map.get("cableType").toString());
				list.add(obj);
			}
		}
		return list;
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

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 得到跳纤关系和上架关系
	 */
	public List<PointInfoBean> getPoint(PointInfoBean point)
			throws DataAccessException {
		ODMInfoBean odm = new ODMInfoBean();
		odm.setOdmId(point.getOdmId());
		if(TextUtil.isNotNull(point.getEid())){
			odm.setEid(point.getEid());
		}
		if(TextUtil.isNotNull(point.getOdmCode())){
			odm.setOdmCode(point.getOdmCode());
		}
		odm = (ODMInfoBean) getObject(GET_ODM, odm);
		
		List<PointInfoBean> list = getObjects("pdaequt.getPoint", point);
		
		if(TextUtil.isNull(list)){
			PointInfoBean pointRes = new PointInfoBean();
			pointRes.setOdmId(Integer.parseInt(odm.getResNum()));
			list = getObjects("pdaequt.getPoint", pointRes);
		}
		
		if(TextUtil.isNull(odm.getOdmCode()) || (!this.isNumeric(odm.getOdmCode()))){
			odm.setOdmCode(this.setOdmCode(odm.getOdmName()));
		}
		
		for(PointInfoBean obj : list){
			if(TextUtil.isNull(obj.getPstat())){
				obj.setPstat("0");
			}
			if(TextUtil.isNull(obj.getOdmCode())){
				obj.setOdmCode(odm.getOdmCode());
				if(odm.getOdmCode().length() == 1){
					obj.setPid("0"+odm.getOdmCode()+obj.getPid());
				}else{
					obj.setPid(this.getOdmCode(odm.getOdmName())+obj.getPid());
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 
	 * @param odmName
	 * @return
	 */
	public String setOdmCode(String odmName){
		String[] odmNames = odmName.split("-");
		String result = "00";
		for(int i=0;i<odmNames.length;i++){
			boolean flag = this.isNumeric(odmNames[i]);
			if(flag){
				result = odmNames[i];
				break;
			}
		}
		return result;
	}
	public  boolean isNumeric(String str){ 
		Pattern pattern = Pattern.compile("[0-9]*"); 
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
		    return false; 
		} 
		return true; 
	}
	
	/**
	 * 
	 * @param point
	 * @return
	 */
	public List<PointInfoBean> getNullPoint(PointInfoBean point){
		List<PointInfoBean> list =  new ArrayList<PointInfoBean>();
		String sql = "SELECT ID,EID,PSTAT,odmId  FROM job_pointinfo "
				+ " where odmId='"+point.getOdmId()+"' and del = 0  and pstat='"+point.getPstat()+"'";
		if(point.getPstat().equals("0")){
			sql += " union all "
			+ "SELECT ID,EID,PSTAT,odmId  FROM job_pointinfo "
			+ " where odmId='"+point.getOdmId()+"' and del = 0  and pstat is null";
		}
		List<Map<String, Object>> resultList = this.jdbcTemplate.queryForList(sql);
		for(int i=0;i<resultList.size();i++){
			Map<String, Object> map = resultList.get(i);
			PointInfoBean obj = new PointInfoBean();
			obj.setId(Integer.parseInt(map.get("ID")+""));
			obj.setEid(map.get("EID")+"");
			if(map.get("PSTAT") != null){
				obj.setPstat(map.get("PSTAT")+"");
			}else{
				obj.setPstat("0");
			}
			obj.setOdmId(Integer.parseInt(map.get("odmId")+""));
			list.add(obj);
		}
		return list;
	}
	
	/**
	 * 得到端口具体数据
	 * @param point
	 * @return
	 */
	public PointInfoBean getPointObj(PointInfoBean point){
		String state = point.getPstat();
		point.setPstat(null);
		PointInfoBean obj = (PointInfoBean) getObject("pdaequt.getPoint", point);
		//得到落架关系
		Map<String, String> map = this.getOppoRanke(obj.getId()+"",obj.getResNum());
		obj.setCablename(map.get("cableName"));
		obj.setFiberName(map.get("fiberName"));
		obj.setPstat(state);
		return obj;
		
	}
	
	
	String getOdmCode(String odmName){
		String odmCode = "";
		String[] odms = odmName.split("-");
		Pattern pattern = Pattern.compile("[0-9]*"); 
		Matcher isNum = pattern.matcher(odms[odms.length-1]);
		if( !isNum.matches() ){
		     return "00";
		}else{
			if(odms[odms.length -1].length() ==1){
				return "0"+odms[odms.length -1];
			}else{
				return odms[odms.length -1];
			}
		}
	}
	/**
	 * 得到跳纤的
	 * @param id
	 * @return
	 */
	Map<String, String> getOppoRanke(String id,String resNum){
		Map<String, String> map= new HashMap<String, String>();
		String sql = "select "
				+ " t.startPortId,t.endPortId, "
				/*+ " (select pos from job_pointinfo where ID=t.startPortId) as startName,"
				+ " (select pos from job_pointinfo where ID=t.endPortId) as endName,"*/
				+ " t.zhLabel, "
				+ " t.cableName "
				+ " from job_fiber t where (t.startPortId ="+id+" ) and t.deleteflag = '0' "
				+ " union all"
				+ " select "
				+ " t.startPortId,t.endPortId, "
				/*+ " (select pos from job_pointinfo where ID=t.startPortId) as startName,"
				+ " (select pos from job_pointinfo where ID=t.endPortId) as endName,"*/
				+ " t.zhLabel, "
				+ " t.cableName "
				+ "  from job_fiber t where (t.endPortId = "+id+") and t.deleteflag = '0' "
				+ "";
		if(TextUtil.isNotNull(resNum)){
			sql+=" union all"
				+ " select "
				+ " t.startPortId,t.endPortId, "
				/*+ " (select pos from job_pointinfo where resNum=t.startPortId) as startName,"
				+ " (select pos from job_pointinfo where resNum=t.endPortId) as endName,"*/
				+ " t.zhLabel, "
				+ " t.cableName "
				+ "  from job_fiber t where (t.startPortId ="+resNum+" ) and t.deleteflag = '0' "
				+ " union all"
				+" select "
				+ " t.startPortId,t.endPortId, "
				/*+ " (select pos from job_pointinfo where resNum=t.startPortId) as startName,"
				+ " (select pos from job_pointinfo where resNum=t.endPortId) as endName,"*/
				+ " t.zhLabel, "
				+ " t.cableName "
				+ "  from job_fiber t where (t.endPortId = "+resNum+") and t.deleteflag = '0' "
				+ "";
		}
		System.out.println("-----");
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		for(Map<String, Object> result  : list){
			/*if(id .equals(result.get("startPortId")+"")){
				map.put("oppoName", result.get("endName")+"");
			}else if(id .equals(result.get("endPortId")+"")){
				map.put("oppoName", result.get("startName")+"");
			}*/
			map.put("cableName", result.get("cableName")+"");
			map.put("fiberName", result.get("zhLabel")+"");
		}
		return map;
	}
	
	/**
	 * 得到跳纤的
	 * 对端是被名称
	 * @param id
	 */
	String getoppoJump(String id,String resNum){
		String result = "";
		String sql ="";
		sql = " "
				+ "select pid1,pid2,pid1Type,pid2Type "
				/*+ " case pid1Type "
				+ " when 'odm' then (select pos from job_pointinfo where ID = pid1) "
				+ " else (select pointName from job_point where id= pid1) "
				+ " END as pid1Name, "
				+ " case pid2Type "
				+ " when 'odm' then (select pos from job_pointinfo where ID = pid2) "
				+ " else (select pointName from job_point where id= pid2) "
				+ " END as pid2Name "*/
				+ " from job_jumpfiber where pid1 = '"+id+"' and deleteFlag='0'"
				+ " union all "
				+ " select pid1,pid2,pid1Type,pid2Type "
				/*+ " case pid1Type "
				+ " when 'odm' then (select pos from job_pointinfo where ID = pid1) "
				+ " else (select pointName from job_point where id= pid1) "
				+ " END as pid1Name, "
				+ " case pid2Type "
				+ " when 'odm' then (select pos from job_pointinfo where ID = pid2) "
				+ " else (select pointName from job_point where id= pid2) "
				+ " END as pid2Name "*/
				+ " from job_jumpfiber where pid2 = '"+id+"' and deleteFlag='0'"
				+ "";
		if(TextUtil.isNotNull(resNum)){
			sql +=" union all "
				+ " select pid1,pid2,pid1Type,pid2Type "
				/*+ " case pid1Type "
				+ " when 'odm' then (select pos from job_pointinfo where resNum = pid1) "
				+ " else (select pointName from job_point where resNum= pid1) "
				+ " END as pid1Name, "
				+ " case pid2Type "
				+ " when 'odm' then (select pos from job_pointinfo where resNum = pid2) "
				+ " else (select pointName from job_point where resNum= pid2) "
				+ " END as pid2Name "*/
				+ " from job_jumpfiber where pid1 = '"+resNum+"' and deleteFlag='0' "
				+ " union all "
				+ " select pid1,pid2,pid1Type,pid2Type "
				/*+ " case pid1Type "
				+ " when 'odm' then (select pos from job_pointinfo where resNum = pid1) "
				+ " else (select pointName from job_point where resNum= pid1) "
				+ " END as pid1Name, "
				+ " case pid2Type "
				+ " when 'odm' then (select pos from job_pointinfo where resNum = pid2) "
				+ " else (select pointName from job_point where resNum= pid2) "
				+ " END as pid2Name "*/
				+ " from job_jumpfiber where pid2 = '"+resNum+"' and deleteFlag='0' "
				+ "";
		}
		List<Map<String, Object>> resultList = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(resultList)){
			Map<String, Object> resultMap= resultList.get(0);
			if((resultMap.get("pid1")+"").equals(id)){
				result = resultMap.get("pid2Name")+"";
			}else if((resultMap.get("pid2")+"").equals(id)){
				result = resultMap.get("pid1Name")+"";
			}
		}
		return result;
	}
	
	

	public void insertPoint(List<PointInfoBean> pointList)
			throws DataAccessException {
		batchInsert("pdaequt.insertPoint", pointList);
	}

	public void updatePoint(PointInfoBean point) throws DataAccessException {
		update("pdaequt.updatePoint", point);
	}
	
	/**
	 * 批量更新端子
	 * @param list
	 */
	public void batchUpdatePoint(List<PointInfoBean> list){
		batchUpdate("pdaequt.updatePoint", list);
	}
	
	/**
	 * 删除端子业务
	 * @param point
	 */
	public void delPointBusiness(PointInfoBean point){
		String selSql = "select id as id,startPortId as portId,startPortType as portType,'start' as type from job_fiber where startPortId in ("
				+ "'"+point.getId()+"'";
		if(TextUtil.isNotNull(point.getResNum())){
			selSql +=",'"+point.getResNum()+"'";
		}
		selSql +=") union all"
				+ " select id as id,endPortId as portId,endPortType as portType,'end' as type from job_fiber where endPortId in ("
				+ "'"+point.getId()+"' ";
		if(TextUtil.isNotNull(point.getResNum())){
			selSql +=",'"+point.getResNum()+"'";
		}
		selSql+=")";
		
		List<Map<String,Object>> list = this.jdbcTemplate.queryForList(selSql);
		if(TextUtil.isNotNull(list)){
			for(int i=0;i<list.size();i++){
				Map<String, Object> map= list.get(i);
				String upSql = "update job_fiber ";
				if(map.get("type").equals("start")){
					upSql +=" set startPortId = null,startPortType =null ";
				}else{
					upSql +=" set endPortId = null,endPortType =null ";
				}
				upSql +="where id ='"+map.get("id")+"'";
				System.out.println(upSql);
				this.jdbcTemplate.update(upSql);
			}
		}
	}
	
	
	/**
	 * 得到Odm的列表
	 * @param obj
	 * @return
	 */
	public List<OdmFiberInfo> getOdmFiber(OdmFiberInfo obj){
		List<OdmFiberInfo> list = new ArrayList<OdmFiberInfo>();
		try{
			list = this.getObjects("pdaequt.getOdmFiber", obj);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public int insertOdmFiber(OdmFiberInfo obj){
		int num = (Integer) this.insert("pdaequt.insertOdmFiber", obj);
		return num;
	}
	
	/**
	 * 删除业务
	 * @param obj
	 */
	public void delOdmFiber(OdmFiberInfo obj){
		this.update("pdaequt.delOdmFiber", obj);
		//将纤芯数据重置
		String upFiber = " update job_fiber set startDeviceId = null , startDeviceType = null ,"
				+ " startPortType = null ,startPortId = null"
				+ " where startDeviceId = '"+obj.getEid()+"' and alias >="+obj.getStartFiber()+" and alias <= "+obj.getEndFiber()+"";
		this.jdbcTemplate.execute(upFiber);
		upFiber =" update job_fiber set endDeviceId = null , endDeviceType = null ,"
				+ " endPortType = null ,endPortId = null"
				+ " where endDeviceId = '"+obj.getEid()+"' and alias >="+obj.getStartFiber()+" and alias <= "+obj.getEndFiber()+"";
		this.jdbcTemplate.execute(upFiber);
		//更新端子
		String upPoint = "update job_pointinfo"
				+ " set PSTAT=0,cablename=null,cableid=null,fibno=null "
				+ " where odmId = '"+obj.getOdmId()+"'"
				+ " and prowno ="+obj.getRow()+"  and plineno >="+obj.getStartCol()+" and  plineno <="+obj.getEndCol()+"";
		this.jdbcTemplate.execute(upPoint); 
	}
	
	/**
	 * 批量落架
	 * @param obj
	 * @return
	 */
	public String batchOdmFiber(OdmFiberInfo obj,CableInfoBean cable){
		String deviceId = "";
		String sql = "";
		String direction = "";
		String result = "";
		Integer deviceType = null ;
		if(obj.getEid().startsWith("EQU")){
			//站点
			sql = "select g.areano as deviceId from job_generator g, job_equtinfo e"
					+ " where g.generatorId = e.gid and e.eid ='"+obj.getEid()+"'";
			List<Map<String, Object>> deviceList = this.jdbcTemplate.queryForList(sql);
			deviceId = super.getStrList(deviceList, "deviceId");
			deviceType = 2;
		}else if(obj.getEid().startsWith("EIU")){
			deviceId = obj.getEid();
			deviceType = 1;
		}
		//判断落光缆的哪端
		if(TextUtil.isNotNull(deviceId)){
			if(cable.getStartDeviceId().equals(deviceId)){
				direction ="start";
			}else{
				direction ="end";
			}
			
			try{
				List<PointInfoBean> pointResult = new ArrayList<PointInfoBean>();
				List<PointInfoBean> pointList = this.getPointList(obj);
				List<FiberInfoBean> fiberResult = new ArrayList<FiberInfoBean>();
				List<FiberInfoBean> fiberList = this.getFiberList(obj);
				if(pointList.size() == fiberList.size()){
					for(int i=0;i<pointList.size();i++){
						PointInfoBean point = pointList.get(i);
						Integer line =Integer.parseInt(point.getPlineno());
						for(int j= 0;j<fiberList.size();j++){
							FiberInfoBean fiber = fiberList.get(j);
							Integer alias = line + (obj.getStartFiber() - obj.getStartCol());
							if(alias.equals(Integer.parseInt(fiber.getAlias()))){
								point.setCableid(obj.getCableId());
								point.setCablename(obj.getCableName());
								point.setPstat("1");
								point.setFibno(Integer.parseInt(fiber.getAlias()));
								if(direction.equals("start")){
									fiber.setStartDeviceType(deviceType);
									fiber.setStartDeviceId(obj.getEid());
									fiber.setStartPortType(0);
									fiber.setStartPortId(point.getId());
								}else{
									fiber.setEndDeviceType(deviceType);
									fiber.setEndDeviceId(obj.getEid());
									fiber.setEndPortType(0);
									fiber.setEndPortId(point.getId());
								}
								pointResult.add(point);
								fiberResult.add(fiber);
							}else{
								continue;
							}
						}
					}
					this.batchPoint(pointResult);
					this.batchFiber(direction, fiberResult);
					result ="true";
				}else{
					result ="false";
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return result;
	}
	
	
	void batchFiber(final String type,final List<FiberInfoBean> list){
		String sql = "";
		if(type.endsWith("start")){
			sql ="update job_fiber t"
				+ " set t.startDeviceType =?,t.startDeviceId = ?,"
				+ " t.startPortType = ?,t.startPortId =?"
				+ " where t.id = ?";
		}else{
			sql ="update job_fiber t"
					+ " set t.endDeviceType =?,t.endDeviceId = ?,"
					+ " t.endPortType = ?,t.endPortId =?"
					+ " where t.id = ?";
		}
		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				FiberInfoBean fiber = list.get(i);
				if(type.equals("start")){
					ps.setInt(1, fiber.getStartDeviceType());
					ps.setString(2, fiber.getStartDeviceId());
					ps.setInt(3, fiber.getStartPortType());
					ps.setInt(4, fiber.getStartPortId());
					ps.setInt(5, fiber.getId());
				}else{
					ps.setInt(1, fiber.getEndDeviceType());
					ps.setString(2, fiber.getEndDeviceId());
					ps.setInt(3, fiber.getEndPortType());
					ps.setInt(4, fiber.getEndPortId());
					ps.setInt(5, fiber.getId());
				}
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return list.size();
			}
		});
	}
	
	void batchPoint(final List<PointInfoBean> list){
		String sql = "update  "
				+ " job_pointinfo"
				+ " set pstat =?,cablename =?,cableid =?,fibno = ? "
				+ " where ID =?";
		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				PointInfoBean point = list.get(i);
				System.out.println(point.getId()+"=================="+point.getCableid()+"===============");
				ps.setString(1, point.getPstat());
				ps.setString(2, point.getCablename());
				ps.setInt(3, point.getCableid());
				ps.setInt(4, point.getFibno());
				ps.setInt(5, point.getId());
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
	}
	
	/**
	 * 得到端子列表
	 * @param odmFiber
	 * @return
	 */
	List<PointInfoBean> getPointList(OdmFiberInfo odmFiber){
		List<PointInfoBean> list = new ArrayList<PointInfoBean>();
		String sql = "select t.id ,t.plineno,t.prowno,t.EID as eid,odmCode as odmCode,"
				+ " t.PID as pid,t.PSTAT as pstat"
				+ " from job_pointinfo t where odmId ='"+odmFiber.getOdmId()+"'"
				+ " and prowno ='"+odmFiber.getRow()+"' and (plineno >="+odmFiber.getStartCol()+""
				+ " and plineno <= "+odmFiber.getEndCol()+") order by prowno desc ";
		List<Map<String, Object>> resultList = this.jdbcTemplate.queryForList(sql);
		for(Map<String, Object> map : resultList){
			PointInfoBean point = new PointInfoBean();
			point.setId(Integer.parseInt(map.get("id")+""));
			point.setPlineno(map.get("plineno")+"");
			list.add(point);
		}
		return list;
	}
	
	
	/**
	 * 得到纤芯列表
	 * @param odmFiber
	 * @return
	 */
	List<FiberInfoBean> getFiberList(OdmFiberInfo odmFiber){
		List<FiberInfoBean> list = new ArrayList<FiberInfoBean>();
		String sql = "select id,alias from job_fiber t where"
				+ " t.cableId = "+odmFiber.getCableId()+" and"
				+ " t.alias >= "+odmFiber.getStartFiber()+" and"
				+ " t.alias <="+odmFiber.getEndFiber()+" order by alias desc ";
		List<Map<String, Object>> resultList = this.jdbcTemplate.queryForList(sql);
		for(Map<String, Object> map : resultList){
			FiberInfoBean fiber = new FiberInfoBean();
			fiber.setId(Integer.parseInt(map.get("id")+""));
			fiber.setAlias(map.get("alias")+"");
			list.add(fiber);
		}
		return list;
	}
}
