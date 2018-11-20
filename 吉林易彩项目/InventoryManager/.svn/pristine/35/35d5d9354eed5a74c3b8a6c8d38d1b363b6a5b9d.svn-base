package interfaces.pdainterface.common.service;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import edu.emory.mathcs.backport.java.util.LinkedList;
import interfaces.irmsInterface.interfaces.correct.OpticalOpenWebServiceHttpBindingStub;
import interfaces.irmsInterface.interfaces.correct.OpticalOpenWebServiceLocator;
import interfaces.irmsInterface.interfaces.correct.OpticalOpenWebServicePortType;
import interfaces.irmsInterface.interfaces.correct.irmsUtil.AnalysisUtil;
import interfaces.irmsInterface.interfaces.pojo.IrmsPojo;
import interfaces.irmsInterface.interfaces.service.impl.IirmsInterService;
import interfaces.irmsInterface.utils.UploadUtil;
import interfaces.pdainterface.common.pojo.CorrectBean;
import interfaces.pdainterface.common.pojo.CorrectResBean;
import interfaces.pdainterface.common.pojo.EqutPointObj;
import interfaces.pdainterface.common.pojo.IntergradeBean;
import interfaces.pdainterface.common.pojo.OpticabLay;
import interfaces.pdainterface.common.pojo.QualitorBean;
import interfaces.pdainterface.common.service.impl.ICommonService;
import base.database.DataBase;
import base.util.BeanUtil;
import base.util.JsonUtil;
import base.util.TextUtil;

public class CommonService extends DataBase implements ICommonService{
	private JdbcTemplate jdbcTemplate;
	private IirmsInterService irmsInterService;
	
	/**
	 * 获取综合业务区
	 * @param obj
	 * @return
	 */
	public List<IntergradeBean> getIntergradeList(IntergradeBean obj){
		List<IntergradeBean> list = getObjects("imCommon.getInterGrade", obj);
		return list;
	}
	
	/**
	 * 得到列表数据
	 * @param obj
	 * @return
	 */
	public List<EqutPointObj> getDeviceList(EqutPointObj obj){
		List<EqutPointObj> list = new ArrayList<EqutPointObj>();
		String cableSql = "";
		if(obj.getType().equals("a")){
			cableSql ="select startDeviceId as id ,startDeviceType as type,startDeviceName as name"
					+ " from job_cable where cableid ='"+obj.getId()+"'";
		}else{
			cableSql ="select endDeviceId as id ,endDeviceType as type,endDeviceName as name"
					+ " from job_cable where cableid ='"+obj.getId()+"'";
		}
		List<Map<String, Object>> cableList = this.jdbcTemplate.queryForList(cableSql);
		if(TextUtil.isNotNull(cableList)){
			Map<String, Object> map = cableList.get(0);
			list = this.getEqutList(map);
		}
		return list;
	}
	
	/**
	 * 得到数据
	 * @param map
	 * @return
	 */
	List<EqutPointObj> getEqutList(Map<String, Object> map){
		List<EqutPointObj> list = new ArrayList<EqutPointObj>();
		String type = map.get("type")+"";
		String sql = "";
		//光交
		if(type.equals("1") || type.equals("9203")){
			sql = " select distinct id,name,type  from ( ";
			sql += " select id,ENAME as name,1 as type from job_equtinfo where id = '"+map.get("id")+"'"
				+ "  union all "
				+ " select id,ENAME as name,1 as type from job_equtinfo where resNum= '"+map.get("id")+"'";
			sql +=" ) b ";
		}else if(type.equals("3") || type.equals("9503")){
			//站点
			String baseSql = "select distinct generatorId from ("
					+ " select g.generatorId from job_generator g, job_stationbase s "
					+ " where g.areano = s.stationBaseId and  s.stationBaseId = '"+map.get("id")+"' "
					+ " union all "
					+ " select g.generatorId from job_generator g, job_stationbase s"
					+ " where g.areano = s.stationBaseId and  s.resNum ='"+map.get("id")+"'"
					+ " ) b"
					+ "";
			List<Map<String, Object>> baseList = this.jdbcTemplate.queryForList(baseSql);
			String gId = super.getStrList(baseList, "generatorId");
			sql= "select id as id ,ENAME as name,2 as type"
			   + " from job_equtinfo where etype =1 and gid in ("+gId+")";
		}else if(type.equals("2")){
			sql = " select distinct id,name,type  from ( ";
			sql +="select jointId as id , jointName as name ,0 as type"
				+ " from job_joint where resNum ='"+map.get("id")+"'"
				+ " union all "
				+ " select jointId as id , jointName as name ,0 as type"
				+ " from job_joint where jointId ='"+map.get("id")+"'";
			sql +=" ) b ";
		}
		
		
		/**
		 * 这个地方的sql 有可能为空
		 */
		if(TextUtil.isNotNull(sql)){
			List<Map<String, Object>> equtList = this.jdbcTemplate.queryForList(sql);
			if(TextUtil.isNotNull(equtList)){
				for(Map<String, Object> eMap : equtList){
					EqutPointObj eObj = new EqutPointObj();
					eObj.setId(Integer.parseInt(eMap.get("id")+""));
					eObj.setName(eMap.get("name")+"");
					eObj.setType(eMap.get("type")+"");
					list.add(eObj);
				}
			}
		}
		return list;
	}
	
	/**
	 * 得到所有的端口数据
	 * @param obj
	 * @return
	 */
	public List<EqutPointObj> getEpointList(EqutPointObj obj){
		List<EqutPointObj> list = new ArrayList<EqutPointObj>();
		String sql = "";
		if(obj.getType().equals("0")){
			//光接头盒   接头盒数据
			String jointSql = "select distinct jointId ,resNum from job_joint where jointId ='"+obj.getId()+"'";
			List<Map<String, Object>> jointList = this.jdbcTemplate.queryForList(jointSql);
			String gId = super.getStrList(jointList, "resNum");
			if(TextUtil.isNotNull(jointList)){
				sql = "select id as id , pos as name,type from job_pointinfo where resEID='"+gId+"'";
			}
		}
		
		if(TextUtil.isNotNull(sql)){
			List<Map<String, Object>> resultList = this.jdbcTemplate.queryForList(sql);
			if(TextUtil.isNotNull(resultList)){
				for(Map<String, Object> resultMap  : resultList){
					EqutPointObj epoint = new EqutPointObj();
					epoint.setId(Integer.parseInt(resultMap.get("id")+""));
					epoint.setName(resultMap.get("name")+"");
					epoint.setType(resultMap.get("type")+"");
					list.add(epoint);
				}
			}
		}
		return list;
	}
	
	/**
	 * 查询敷设
	 * @param obj
	 * @return
	 */
	public List<OpticabLay> getOpticabLayList(OpticabLay obj){
		List<OpticabLay> list = new ArrayList<OpticabLay>();
		String sql = "";
		
		if(obj.getSpanType().equals(Integer.parseInt(getPropert.getValueByKey("pipeLine"))) || obj.getSpanType().equals(1)){
			//管道
			sql = "select t.pipeSegmentId as id ,t.resNum as resNum from pipesegmentinfo t"
					+ " where t.pipeSegmentId = '"+obj.getSpanId()+"'";
		}else if(obj.getSpanType().equals(Integer.parseInt(getPropert.getValueByKey("poleLine"))) || obj.getSpanType().equals(2)){
			//杆路
			sql = "select t.poleLineSegmentId as id,t.resNum as resNum from polelinesegmentinfo t "
				+ " where t.poleLineSegmentId ='"+obj.getSpanId()+"'";
			
		}else if(obj.getSpanType().equals(Integer.parseInt(getPropert.getValueByKey("ledUp"))) || obj.getSpanType().equals(3)){
			//引上
			sql = "select t.id as id,t.resNum as resNum from leadupStage t "
					+ " where t.id = '"+obj.getSpanId()+"'";
		}else if(obj.getSpanType().equals(Integer.parseInt(getPropert.getValueByKey("hangwall"))) || obj.getSpanType().equals(6)){
			//挂墙
			sql = "select t.id as id,t.resNum as resNum from hang_wall t"
					+ " where t.id = '"+obj.getSpanId()+"'";
		}else if(obj.getSpanType().equals(Integer.parseInt(getPropert.getValueByKey("buried"))) || obj.getSpanType().equals(4)){
			//直埋
			sql = "select t.id as id,t.resNum as resNum from buriedpartinfo t "
					+ " where t.id = '"+obj.getSpanId()+"'";
			
		}else if(obj.getSpanType().equals(Integer.parseInt(getPropert.getValueByKey("cable"))) || obj.getSpanType().equals(5)){
			//光缆
			sql = "select t.cableid as id,t.resNum as resNum from job_cable t "
					+ " where t.cableid ='"+obj.getSpanId()+"'";
		}
		try{
			Map<String, Object> map = this.jdbcTemplate.queryForMap(sql);
			if(map!=null){
				sql = "select distinct id,zhlabel,cableId,cableName,subporeId,poreId,aid,atype,ztype,zid,spanId,spanType from ("
					+ " select id,zhlabel,cableId,cableName,subporeId,poreId,aid,atype,ztype,zid,spanId,spanType from opticab_lay t"
					+ " where spanId = '"+map.get("id")+"' and t.deleteFlag =0 "
					+ " union all"
					+ " select id,zhlabel,cableId,cableName,subporeId,poreId,aid,atype,ztype,zid,spanId,spanType from opticab_lay t"
					+ " where spanId = '"+map.get("resNum")+"' and t.deleteFlag =0 "
					+ " ) lay"
					+ "";
				List<Map<String, Object>> resultList = this.jdbcTemplate.queryForList(sql);
				for(Map<String, Object> result : resultList){
					OpticabLay opt = (OpticabLay) BeanUtil.mapToObject(result, OpticabLay.class);
					list.add(opt);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 查询敷设
	 * @param obj
	 * @return
	 */
	public List<OpticabLay> getOpticabLayByTube(OpticabLay obj){
		List<OpticabLay> list = new ArrayList<OpticabLay>();
		String sql = "";
		sql = "select t.tubeId as id ,t.resNum as resNum from tubeinfo t "
				+ " where t.tubeId ='"+obj.getSubporeId()+"'";
		Map<String, Object> map = this.jdbcTemplate.queryForMap(sql);
		try{
			if(map != null){
				sql = "select distinct id,zhlabel,cableId,cableName,subporeId,poreId,aid,atype,ztype,zid,spanId,spanType from ("
						+ " select id,zhlabel,cableId,cableName,subporeId,poreId,aid,atype,ztype,zid,spanId,spanType from opticab_lay t"
						+ " where subporeId = '"+map.get("id")+"' and t.deleteFlag =0 "
						+ " union all"
						+ " select id,zhlabel,cableId,cableName,subporeId,poreId,aid,atype,ztype,zid,spanId,spanType from opticab_lay t"
						+ " where subporeId = '"+map.get("resNum")+"' and t.deleteFlag =0 "
						+ ") lay "
						+ "";
				List<Map<String, Object>> resultList = this.jdbcTemplate.queryForList(sql);
				for(Map<String, Object> result : resultList){
					OpticabLay opt = (OpticabLay) BeanUtil.mapToObject(result, OpticabLay.class);
					list.add(opt);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 删除操作
	 * @param obj
	 * @return
	 */
	public boolean delOpticabLay(OpticabLay obj){
		boolean flag = false;
		try{
			String sql = " update opticab_lay set deleteFlag = 1 "
					+ " , lastUpTime = now()"
					+ " where id = '"+obj.getId()+"'";
			this.jdbcTemplate.execute(sql);
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 批量增加数据
	 * @param list
	 */
	public void beathAddOpticalLay(final List<OpticabLay> list){
		try{
			String sql = "insert into opticab_lay"
					+ "(zhlabel,cableId,cableName,spanId,spanType,createTime,deleteFlag,subporeId)"
					+ " values"
					+ " (?,?,?,?,?,now(),0,?)";
			this.jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					OpticabLay obj = list.get(i);
					obj = setOpticabLay(obj);
					ps.setString(1,obj.getZhlabel());
					ps.setInt(2, obj.getCableId());
					ps.setString(3, obj.getCableName());
					if(TextUtil.isNull(obj.getSpanId())){
						ps.setInt(4, -1);
					}else{
						ps.setInt(4, obj.getSpanId());
					}
					ps.setInt(5, obj.getSpanType());
					if(TextUtil.isNull(obj.getSubporeId())){
						ps.setInt(6, -1);
					}else{
						ps.setInt(6, obj.getSubporeId());
					}
				}
				@Override
				public int getBatchSize() {
					return list.size();
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	OpticabLay setOpticabLay(OpticabLay obj){
		if(TextUtil.isNull(obj.getZhlabel())){
			obj.setZhlabel(obj.getCableName()+"_敷设");
		}
		return obj;
	}
	
	/**
	 * 得到一线维护人列表
	 * @param object
	 * @return
	 */
	public List<QualitorBean> getQualitorList(QualitorBean object){
		List<QualitorBean> list = getObjects("imCommon.getQualitor", object);
		if(TextUtil.isNull(list)){
			object.setCounty(null);
			list = getObjects("imCommon.getQualitor", object);
			if(TextUtil.isNull(list)){
				object.setCity(null);
				list = getObjects("imCommon.getQualitor", object);
			}
		}
		return list;
	}
	
	
	/**
	 * 派发勘误工单
	 * @param obj
	 * @return
	 */
	public String billCorrent(CorrectBean obj){
		String result = "";
		try{
			String sql = "";
			String resTable ="";
			Date date = new Date();
			Calendar calendar = new GregorianCalendar(); 
			calendar.setTime(date);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String sendTime = sdf.format(date);
			calendar.add(calendar.DATE, 2);
			String requireTime = sdf.format(calendar.getTime());
			if(obj.getId().endsWith(",")){
				obj.setId(obj.getId().substring(0, obj.getId().length()-1));
			}
			if(obj.getType().equals("station")){
				resTable ="SITE";
				sql ="select stationName as resName,resNum as resNum,region as region"
					+ " from job_stationbase where stationBaseId ='"+obj.getId()+"'";
			}else if(obj.getType().equals("generator")){
				resTable ="EQUIPROOM";
				sql ="select generatorName as resName,resNum as resNum,region as region "
					+ " from job_generator where generatorId ='"+obj.getId()+"'";
			}else if(obj.getType().equals("equ")){
				resTable ="OPTI_TRAN_BOX";
				sql ="select ENAME as resName,resNum as resNum,EADDR as region"
						+ " from job_equtinfo where EID ='"+obj.getId()+"'";
			}else if(obj.getType().equals("pole")){
				resTable = "POLE";
				sql = "select GROUP_CONCAT(resNum) as resNum,GROUP_CONCAT(distinct region) as region "
					+ " from poleinfo where poleId in ("+obj.getId()+") ";
			}else if(obj.getType().equals("stone")){
				resTable ="MARKSTONE";
				sql = "select GROUP_CONCAT(resNum) as resNum,GROUP_CONCAT(distinct stoneArea) as region "
					+ " from stoneinfo where stoneId in ("+obj.getId()+")";
			}else if(obj.getType().equals("well")){
				resTable ="STAFF_WELL";
				sql = "select GROUP_CONCAT(resNum) as resNum,GROUP_CONCAT(distinct region) as region "
					+ " from wellinfo where wellId in ("+obj.getId()+")";
			}else if(obj.getType().equals("joint")){
				resTable ="OPTI_CONN_BOX";
				sql = "select GROUP_CONCAT(resNum) as resNum"
					+ " from job_joint where jointId in ("+obj.getId()+")";
			}else if(obj.getType().equals("terminal")){
				resTable ="OPTI_END_BOX";
				sql = "select GROUP_CONCAT(resNum) as resNum,GROUP_CONCAT(distinct terminalAddres) as region "
					+ " from optical_terminal where id in ("+obj.getId()+")";
			}else if(obj.getType().equals("support")){
				resTable ="SUPP_POINT";
				sql = "select GROUP_CONCAT(resNum) as resNum,GROUP_CONCAT(distinct maintArea) as region "
					+ " from job_support_point where id in ("+obj.getId()+")";
			}else if(obj.getType().equals("optFiber")){
				resTable ="OPTI_SFB";
				sql = "select GROUP_CONCAT(resNum) as resNum,GROUP_CONCAT(distinct maintainArea) as region "
					+ " from job_fiberbox where id in ("+obj.getId()+")";
			}else if(obj.getType().equals("polelineSeg")){
				resTable ="POLE_ROAD_SEG";
				sql = " select GROUP_CONCAT(resNum) as resNum,GROUP_CONCAT(distinct maintenanceAreaName) as region "
					+ " from polelinesegmentinfo where poleLineSegmentId in ("+obj.getId()+")";
			}else if(obj.getType().equals("pipeSeg")){
				resTable ="PIPE_SEG";
				sql = " select GROUP_CONCAT(resNum) as resNum, GROUP_CONCAT(distinct maintenanceAreaName) as region "
					+ " from pipesegmentinfo where pipeSegmentId in ("+obj.getId()+")";
			}else if(obj.getType().equals("buriedSeg")){
				resTable ="DIRE_BURY_SEG";
				sql = " select GROUP_CONCAT(resNum) as resNum,GROUP_CONCAT(distinct buriedPartArea) as region "
					+ " from buriedpartinfo where id in ("+obj.getId()+")";
			}else if(obj.getType().equals("cable")){
				resTable ="OPTI_CAB_SEG";
				sql = " select GROUP_CONCAT(resNum) as resNum,GROUP_CONCAT(distinct region) as region "
					+ " from job_cable where cableid in ("+obj.getId()+")";
			}else if(obj.getType().equals("leadup")){
				resTable ="ONTO";
				sql = " select GROUP_CONCAT(resNum) as resNum,GROUP_CONCAT(distinct mantainance) as region "
					+ " from leadupstage where id in ("+obj.getId()+")";
			}else if(obj.getType().equals("hangWall")){
				resTable ="HANG_WALL_SEG";
				sql = " select GROUP_CONCAT(resNum) as resNum,GROUP_CONCAT(distinct maintArea) as region "
						+ " from hang_wall where id in ("+obj.getId()+")";
			}
			obj.setCorrigendumEnTable(resTable);
			obj.setSendTime(sendTime);
			obj.setRequireTime(requireTime);
			obj.setCorrigendumProf("传输");
			List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
			if(TextUtil.isNotNull(list)){
				Map<String, Object> map = list.get(0);
				if(obj.getType().equals("station") || obj.getType().equals("generator") || obj.getType().equals("equ")){
					obj.setFlowTitle(map.get("resName")+"勘误工单");
					String region = map.get("region")+"";
					if(region.contains("_") && region.split("_").length >=2){
						obj.setSendCompany((map.get("region")+"").split("_")[1]);
					}
					String city = "";
					if(region.startsWith("湖南省")){
						city  = region.split("_")[1];
						if(city.endsWith("分公司")){
							city = city.replace("分公司", "");
						}
						obj.setSendCompany(city);
					}
					obj.setResId(map.get("resNum")+"");
				}else{
					if(obj.getSendCompany().contains("_") && obj.getSendCompany().split("_").length >=2){
						obj.setSendCompany(obj.getSendCompany().split("_")[1]);
					}
					String[] regions = (map.get("region")+"").split(",");
					
					if(TextUtil.isNull(obj.getSendCompany()) && regions.length > 0){
						String sendCompany = regions[0];
						if(sendCompany.contains("_") && sendCompany.split("_").length >=2){
							obj.setSendCompany(sendCompany.split("_")[1]);
						}
					}
					//批量多选数据
					obj.setResId(map.get("resNum")+"");
				}
				if(map.get("resNum") != null){
					String xml = this.getXmlStr(obj);
					OpticalOpenWebServiceLocator locator = new OpticalOpenWebServiceLocator();
					OpticalOpenWebServicePortType portType = locator.getopticalOpenWebServiceHttpPort();
					String outXml = portType.genarateKanwuFlow(xml);
					if(outXml.contains("flowNo")){
						System.out.println("=====================================完成综资派单"+outXml+"=======================");
						result = "success";
					}else{
						result = "派发工单失败，请核查数据";
					}
					this.addIrmsLog(xml, outXml, "irms_kanwu",obj);
					
				}else{
					result = "数据未同步到综资";
				}
			}else{
				result = "综资查无数据";
			}
		}catch(Exception e){
			 try {
				UploadUtil.saveXml(e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			result = "派发失败，网络不通";
		}
		return result;
	}
	
	/**
	 * 这是日志
	 * @param inXml
	 * @param outXml
	 * @param type
	 * @return
	 */
	void addIrmsLog(String inXml ,String outXml,String type,CorrectBean obj){
		Date date = new Date();
		IrmsPojo pojo = new IrmsPojo();
		pojo.setInStr(inXml);
		pojo.setOutStr(outXml);
		pojo.setFaceType(type);
		pojo.setFaceResult("success");
		pojo.setFaceTime(date);
		pojo.setFaceCnType("派发勘误");
		pojo.setImEnType("kanwu");
		pojo.setImCnType("勘误工单");
		pojo.setImName(obj.getFlowTitle());
		pojo.setCreater(obj.getSendMan());
		String dealer = this.getDealer(obj);
		String flowId = this.getFlowId(outXml);
		pojo.setResId("工单号:"+flowId+";处理人:"+dealer+".");
		obj.setCurrentState("2");
		obj.setFlowNo(flowId);
		obj.setDealer(dealer);
		obj.setFlowTask("com.inspur.app.hn.kanwu.process.kanwuGx");
		String ids = "";
		if(TextUtil.isNotNull(obj.getId())){
			ids = obj.getId();
			obj.setId(null);
		}
		this.insert("imCommon.insertCorrect", obj);
		System.out.println("++++++++++++++++++++++完成写入日志操作"+obj.getResId()+"+++++++++++++++++"+flowId+"========");
		//保存下资源详细信息
		//new addRes(obj, ids,flowId).start();
		this.addResCorrect(obj, ids, flowId);
		
		if(TextUtil.isNotNull(obj.getSendCompany()) && obj.getSendCompany().contains("_")){
			String[] regions = obj.getSendCompany().split("_");
			pojo.setCity(regions[0]+"_"+regions[regions.length-1]);
			pojo.setCounty(regions[regions.length-1]);
		}
		this.irmsInterService.addInterLog(pojo);
	}
	
	void addResCorrect(CorrectBean obj,String ids,String flowId){
		String[] resIds = ids.split(",");
		String[] resName = obj.getName().split(",");
		for(int i = 0 ;i<resIds.length;i++){
			CorrectResBean res = new CorrectResBean();
			res.setTaskId(flowId);
			res.setResId(resIds[i]);
			res.setResName(resName[i]);
			res.setResType(obj.getType());
			insert("imCommon.insertRmsRes", res);
		}
	}
	
	
	
	/**
	 * 添加资源信息
	 * @author chenqp
	 *
	 *//*
	class addRes extends Thread{
		private CorrectBean obj;
		private String ids;
		private String flowId;
		public addRes(CorrectBean obj,String ids,String flowId){
			this.obj  = obj;
			this.ids = ids;
			this.flowId = flowId;
		}
		@Override
		public void run() {
			System.out.println("++++++++++++++++++++++开始保存资源信息"+ids+"+++++++++++++++++"+flowId+"========");
			String[] resIds = ids.split(",");
			String[] resName = obj.getName().split(",");
			for(int i = 0 ;i<resIds.length;i++){
				CorrectResBean res = new CorrectResBean();
				res.setTaskId(flowId);
				res.setResId(resIds[i]);
				res.setResName(resName[i]);
				res.setResType(obj.getType());
				insert("imCommon.insertRmsRes", res);
			}
			super.run();
		}
	}*/
	
	
	
	/**
	 * 根据工单id得到
	 * 资源信息列表
	 * @param taskId
	 * @return
	 */
	public List<CorrectResBean> getResByTask(String taskId){
		List<CorrectResBean> list = new LinkedList();
		CorrectResBean correct = new CorrectResBean();
		correct.setTaskId(taskId);
		list=getObjects("imCommon.getCorrectRes", correct);
		return list;
	}
	
	/**
	 * 得到工单id
	 * @param xml
	 * @return
	 */
	String getFlowId(String xml){
		String flowId ="";
		List<Map<String, Object>> list = JsonUtil.getList4Json(xml,Map.class);
		if(TextUtil.isNotNull(list)){
			Map<String, Object> map = list.get(0);
			flowId = map.get("flowNo")+"";
		}
		return flowId;
	}
	
	
	/**
	 * 得到处理人
	 * @param obj
	 * @return
	 */
	String getDealer(CorrectBean obj){
		String dealer = "";
		String sql = "select dealer from rms_city where zhLabel like '%"+obj.getSendCompany()+"%'";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			Map<String, Object> map = list.get(0);
			dealer = map.get("dealer")+"";
		}else{
			dealer = "未查询到处理人";
		}
		return dealer;
	}
	
	/**
	 * 得到输入参数
	 * @param obj
	 * @return
	 */
	String getXmlStr(CorrectBean obj){
		String outXml = "";
		String sql = "select cityId from rms_city where zhLabel like '%"+obj.getSendCompany()+"%' ";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			Map<String, Object> map= list.get(0);
			obj.setSendCompanyId(map.get("cityId")+"");
		}else{
			obj.setSendCompanyId("478");
		}
		String sendMan = obj.getSendMan();
		String sendId = obj.getSendManId();
		if(TextUtil.isNull(sendMan)){
			sendMan = "cs_zifei";
		}
		if(TextUtil.isNull(sendId)){
			sendId = "9722";
		}
		outXml ="<?xml version=\"1.0\" encoding=\"GB2312\"?>"
				+ "<sendXml>"
				+ "<taskList>"
				+ "<TaskInfo>"
				+ "<FlowTitle>"+obj.getFlowTitle()+"</FlowTitle>"
				+ "<remark>"+obj.getRemark()+"</remark>"
				+ "<sendCompany>"+obj.getSendCompany()+"分公司</sendCompany>"
				+ "<sendCompanyId>"+obj.getSendCompanyId()+"</sendCompanyId>"
				+ "<sendMan>"+sendMan+"</sendMan>"
				+ "<sendManId>"+sendId+"</sendManId>"
				+ "<sendTime>"+obj.getSendTime()+"</sendTime>"
				+ "<requireTime>"+obj.getRequireTime()+"</requireTime>"
				+ "<CorrigendumProf>"+obj.getCorrigendumProf()+"</CorrigendumProf>"
				+ "<CorrigendumEnTable>"+obj.getCorrigendumEnTable()+"</CorrigendumEnTable>"
				+ "<CorrigendumColumns>"
				+ "<CorrigendumColumn>"
				+ "<CorrigendumColumn>int_id</CorrigendumColumn>"
				+ "<CorrColumnEnName>int_id</CorrColumnEnName>"
				+ "<CorrColumnEXValue>"+obj.getResId()+"</CorrColumnEXValue>"
				+ "</CorrigendumColumn>";
		outXml+= "</CorrigendumColumns>"
				+ "</TaskInfo>"
				+ "</taskList>"
				+ "</sendXml>";
		return outXml;
	}
	
	
	/**
	 * 得到待办接口
	 * @param inParam
	 * @return
	 */
	public String getWaitedTaskStr(String empid,String flowNo,String processInstname,int start){
		String result= "";
		try{
			String inParam ="empid:"+empid+";start:"+start+";flowNo:"+flowNo;
			OpticalOpenWebServiceLocator locator = new OpticalOpenWebServiceLocator();
			OpticalOpenWebServicePortType portType = locator.getopticalOpenWebServiceHttpPort();
			//String outXml = portType.waitedKWTaskAJAX(empid, flowNo, processInstname, start);
			String outXml = "";
			result = AnalysisUtil.getWaitStr(outXml);
			UploadUtil.saveIrms("输入信息:"+inParam+";综资返回信息:"+outXml,"waitedTask");
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 得到已办接口
	 * @param inParam
	 * @return
	 */
	public String getClaimedTask(String empid,int start,int length){
		String result= "";
		try{
			String inParam ="empid:"+empid+";start:"+start+";length:"+length;
			OpticalOpenWebServiceLocator locator = new OpticalOpenWebServiceLocator();
			OpticalOpenWebServicePortType portType = locator.getopticalOpenWebServiceHttpPort();
			String outXml = portType.showCompletedTasksAJAX(empid,start,length);
			result = AnalysisUtil.getClaimedStr(outXml);
			UploadUtil.saveIrms("输入信息:"+inParam+";综资返回信息:"+outXml,"claimedTask");
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 得到工单详情页面
	 * @param flowId
	 * @param activeId
	 * @return
	 */
	public String getTaskInfo(String flowId,String activeId){
		String result = "";
		try{
			String inParam = "{\"flowId\":\""+flowId+"\",\"activeId\":\""+activeId+"\"}";
			OpticalOpenWebServiceLocator locator = new OpticalOpenWebServiceLocator();
			OpticalOpenWebServicePortType portType = locator.getopticalOpenWebServiceHttpPort();
			String outXml = portType.queryflowTaskInfo(inParam);
			result = AnalysisUtil.getTaskInfo(outXml);
			UploadUtil.saveIrms("输入信息:"+inParam+";综资返回信息:"+outXml,flowId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 得到历史处理信息
	 * @param flowId
	 * @param activeId
	 * @return
	 */
	public String getTaskHisInfo(String flowId){
		String result = "";
		try{
			String inParam = "{\"flowId\":\""+flowId+"\"}"; 
			OpticalOpenWebServiceLocator locator = new OpticalOpenWebServiceLocator();
			OpticalOpenWebServicePortType portType = locator.getopticalOpenWebServiceHttpPort();
			String outXml = portType.getActivList(inParam);
			result = AnalysisUtil.getTaskHisInfo(outXml);
			UploadUtil.saveIrms("输入信息:"+inParam+";综资返回信息:"+outXml,flowId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 得到工单处理页面
	 * @param inParam
	 * @return
	 */
	public String getTaskDealInfo(String inParam){
		String result = "";
		try{
			OpticalOpenWebServiceLocator locator = new OpticalOpenWebServiceLocator();
			OpticalOpenWebServicePortType portType = locator.getopticalOpenWebServiceHttpPort();
			String outXml = portType.queryflowFormComponent(inParam);
			result = AnalysisUtil.getTaskDealInfo(outXml);
			UploadUtil.saveIrms("输入信息:"+inParam+";综资返回信息:"+outXml,"DealInfo");
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 提交处理信息页面
	 * @param inParam
	 * @return
	 */
	public String dealTaskInfo(String inParam,String flowId){
		String result = "";
		try{
			OpticalOpenWebServiceLocator locator = new OpticalOpenWebServiceLocator();
			OpticalOpenWebServicePortType portType = locator.getopticalOpenWebServiceHttpPort();
			String outXml = portType.finishTaskController(inParam, null);
			Map<String, Object> map = JsonUtil.getMap4Json(outXml);
			String message = map.get("message")+"";
			if(message.equals("success")){
				result = "success";
			}else{
				result = "failed";
			}
			UploadUtil.saveIrms("输入信息:"+inParam+";综资返回信息:"+outXml,flowId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取分派人列表
	 * 三级关联
	 * @param inParam
	 * @return
	 */
	public String getAssignDealer(String inParam){
		String result ="";
		try{
			OpticalOpenWebServiceLocator locator = new OpticalOpenWebServiceLocator();
			OpticalOpenWebServicePortType portType = locator.getopticalOpenWebServiceHttpPort();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String time =sdf.format(date);
			String outXml = portType.distributedObjectRequest("IRMS", "Mobile", "123456",time,inParam);
			result = AnalysisUtil.getAssignDealer(outXml);
			UploadUtil.saveIrms("输入信息:"+inParam+";综资返回信息:"+outXml,"assignDealer");
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 得到工单列表
	 * @param obj
	 * @return
	 */
	public List<CorrectBean> getCorrectList(CorrectBean obj){
		List<CorrectBean> list = getObjects("imCommon.getCorrect", obj);
		return list;
	}
	
	/**
	 * 根据资源id和资源类型
	 * 得到资源的二维码串
	 * @param resId
	 * @param resType
	 * @return
	 */
	public String getScanCode(String resId,String resType){
		String result = "";
		try{
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH )+1;
			String monthstr = month+"";
			if(monthstr.length() == 1){
				monthstr ="0"+month;
			}
			String resStr = this.getResStr(resId,null,resType);
			List<Map<String, Object>> list = this.jdbcTemplate.queryForList(resStr);
			if(TextUtil.isNotNull(list)){
				Map<String, Object> map= list.get(0);
				String scanCode = map.get("scanCode")+"";
				String resNum = map.get("resNum")+"";
				String imId = map.get("resCode")+"";
				if(TextUtil.isNull(scanCode)){
					scanCode="0409999";
				}
				if(TextUtil.isNotNull(resNum)){
					result = resType+"_res_"+year+monthstr+scanCode+resNum;
				}else{
					result = resType+"_im_"+year+monthstr+scanCode+imId;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 根据resnum得到
	 * 资源的实例id
	 * @param resNum
	 * @param resType
	 * @return
	 */
	public String getResId(String resNum,String resType){
		String result = "";
		String sqlStr = this.getResStr(null, resNum, resType);
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sqlStr);
		if(TextUtil.isNotNull(list)){
			Map<String, Object> map= list.get(0);
			result = map.get("resCode")+"";
		}
		return result;
	}
	
	/**
	 * 得到资源实例数据
	 * @param resId
	 * @param resType
	 * @return
	 */
	String getResStr(String resId,String resNum,String resType){
		StringBuffer sb = new StringBuffer();
		List<Map<String, Object>> resList =this.jdbcTemplate.queryForList("SELECT * FROM config_resource_task WHERE im_type = '"+resType+"'");
		if(TextUtil.isNotNull(resList)){
			Map<String,Object> resObject = resList.get(0);
			sb.append(" select "
					+ " '"+resType+"' as resType,"
					+ " '"+resObject.get("id_column").toString()+"' as idCol,"
					+ " '"+resObject.get("resType").toString()+"' as resEnType,"
					+ " '"+resObject.get("scan_code").toString()+"' as scanCode,"
					+ " t."+resObject.get("id_column").toString()+" as resCode,"
					+ " t."+resObject.get("name_column").toString()+" as resName,"
					+ " '"+resObject.get("res_table_name").toString()+"' as tableName,"
					+ " t."+resObject.get("longitude_column").toString()+" as longitude,"
					+ " t."+resObject.get("latitude_column").toString()+" as latitude,"
					+ " t."+resObject.get("resNum_column").toString()+" as resNum,"
					+ " t."+resObject.get("deleteFlag_column").toString()+" as deletedFlag,");
					if(null != resObject.get("res_region_column")){
						String region = resObject.get("res_region_column").toString();
						if(TextUtil.isNotNull(region) && !(region.equals("null"))){
							sb.append(" t."+resObject.get("res_region_column").toString()+" as region," );
						}
					}
				sb.append( " date_format(t."+resObject.get("createTime_column").toString()+",'%Y-%c-%d %h:%i:%s') as createTime," 
					+ " date_format(t."+resObject.get("updateTime_column").toString()+",'%Y-%c-%d %h:%i:%s') as updateTime," 
					+ " t."+resObject.get("qualitor_column").toString()+" as qualitor," 
					+ " t."+resObject.get("maintainor").toString()+" as maintainor," 
					+ " t."+resObject.get("resNum_column").toString()+" as resNum "
					+ "");
			if(resObject.get("sel_sql")!= null && !((resObject.get("sel_sql")+"").equals(""))){
				sb.append(" , "+resObject.get("sel_sql")+" ");
			}
			sb.append(" from "+ resObject.get("res_table_name").toString() +" t");
			sb.append(" where 1=1 ");
			if(TextUtil.isNotNull(resId)){
				if(resType.equals("equt")){
					sb.append(" and (t."+resObject.get("id_column").toString()+" ='"+resId+"' or t.EID ='"+resId+"')");
				}else{
					sb.append(" and t."+resObject.get("id_column").toString()+" ='"+resId+"' ");
				}
			}
			if(TextUtil.isNotNull(resNum)){
				sb.append(" and t."+resObject.get("resNum_column").toString()+" ='"+resNum+"' ");
			}
		}
		System.out.println("==="+sb.toString());
		return sb.toString();
	}
	
	public IirmsInterService getIrmsInterService() {
		return irmsInterService;
	}

	public void setIrmsInterService(IirmsInterService irmsInterService) {
		this.irmsInterService = irmsInterService;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
