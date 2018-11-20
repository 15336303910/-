package interfaces.irmsInterface.interfaces.station.service;

import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import manage.device.pojo.DeviceInfoBean;
import manage.device.pojo.JumpFiberBean;
import manage.device.pojo.PointCommon;
import manage.equt.pojo.EqutInfoBean;
import manage.equt.pojo.ODMInfoBean;
import manage.generator.pojo.StationBaseInfoBean;
import manage.point.pojo.PointInfoBean;
import manage.resPoint.pojo.WirelessPointPojo;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import interfaces.irmsInterface.interfaces.pojo.IrmsPojo;
import interfaces.irmsInterface.interfaces.pojo.MoveResPojo;
import interfaces.irmsInterface.interfaces.service.impl.IirmsInterService;
import interfaces.irmsInterface.interfaces.station.service.impl.IirmsStationService;
import interfaces.irmsInterface.utils.InterfaceAddr;
import interfaces.irmsInterface.utils.RequestUtil;
import interfaces.irmsInterface.utils.UploadUtil;
import interfaces.irmsInterface.utils.XmlUtil;
import interfaces.pdainterface.equt.pojo.EqutRankInfo;
import base.database.DataBase;
import base.util.TextUtil;

public class IrmsStationService extends DataBase implements IirmsStationService{
	//app数据库连接
	private JdbcTemplate jdbcTemplate;
	//
	private JdbcTemplate irmsjdbcTemplate;
	private IirmsInterService irmsInterService;

	
	/**
	 * 增加站点接口
	 * @return
	 */
	public String insertStation(){
		return "1212";
	}
	
	
	/**
	 * 移动
	 */
	public void movStation(StationBaseInfoBean pojo){
		try{
			if(TextUtil.isNotNull(pojo.getResNum())){
				Map<String, String> map= this.irmsInterService.getCityCountry(pojo.getRegion());
				MoveResPojo obj = new MoveResPojo();
				obj.setResId(pojo.getResNum());
				obj.setResName(pojo.getStationName());
				obj.setResType("zhandian");
				obj.setAppType("moveStation");
				obj.setLatitude(pojo.getLat());
				obj.setLongitude(pojo.getLon());
				
				
				obj.setAppCnType("移动站点");
				obj.setImId(pojo.getStationBaseId()+"");
				obj.setResName(pojo.getStationName());
				obj.setRegion(pojo.getRegion());
				obj.setQualitor(pojo.getDataQualityPrincipal());
				obj.setCityId(map.get("cityId"));
				obj.setCountyId(map.get("countyId"));
				obj.setMaintainor(pojo.getParties());
				obj.setImEnType("station");
				obj.setImCnType("站点");
				obj.setRegion(pojo.getRegion());
				this.irmsInterService.moveSite(obj, pojo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 增加机架
	 * @param equt
	 * @return
	 */
	public String insertOdf(EqutInfoBean equt){
		String result ="";
		Map<String, Object> map = this.getStationBygid(Integer.parseInt(equt.getGid()));
		Map<String, String> cityMap = this.irmsInterService.getCityCountry(map.get("siteRegion")+"");
		//String position = this.addPosition(equt, map.get("siteRes")+"");
		String county = map.get("siteRegion")+"";
		try{
			if(map.get("siteRes") != null && map.get("gres") != null && cityMap !=null){
				String rackSurface = equt.getEmodel();
				if(TextUtil.isNotNull(rackSurface) && rackSurface.equals("1")){
					rackSurface="B";
				}else{
					rackSurface="A";
				}
				String odfType ="1";
				String model = "odf";
				if(equt.getJijialeixing().equals(4)){
					odfType = "2";
					model="modf";
				}
				String params ="<data model=\""+model+"\">"
						+ "<attr key=\"vendor\" value=\"3\"/>"
						+ "<attr key=\"zh_label\" value=\""+equt.getEname()+"\"/>"
						+ "<attr key=\"shelf_capacity\" value=\"10\"/>/"
						+ "<attr key=\"related_rack_position\" value=\"\"/>"
						+ "<attr key=\"rack_surface\" value=\""+rackSurface+"\"/>"
						+ "<attr key=\"structure_type\" value=\"9503\"/>"
						+ "<attr key=\"structure_id\" value=\""+map.get("siteRes")+"\"/>"
						+ "<attr key=\"room_id\" value=\""+map.get("gres")+"\"/>"
						+ "<attr key=\"related_equiproom\" value=\""+map.get("gres")+"\"/>"
						+ "<attr key=\"city_id\" value=\""+cityMap.get("cityId")+"\"/>"
						+ "<attr key=\"county_id\" value=\""+cityMap.get("countyId")+"\"/>"
						+ "<attr key=\"status\" value=\"3\"/>"
						+ "<attr key=\"odf_type\" value=\""+odfType+"\"/>"
						+ "</data>";
				String lifeParams= "<params>"
						+ "<param key=\"pro_task_id\" value=\"\"/>"
						+ "<param key=\"status\" value=\"3\"/>"
						+ "<param key=\"photo\" value=\"\"/>"
						+ "</params>";
				String jsonString = "params="+URLEncoder.encode(params, "UTF-8")+"&lifeparams="+lifeParams;
				String outIN = RequestUtil.HttpRequest(InterfaceAddr.ADD_RACK, "POST", jsonString);
				
				IrmsPojo pojo = new IrmsPojo();
				pojo.setInStr(params);
				pojo.setOutStr(outIN);
				pojo.setFaceType("equt");
				pojo.setCreater(equt.getParties());
				pojo.setFaceCnType("新增机架");
				pojo.setImId(equt.getEid());
				pojo.setImEnType("equt");
				pojo.setImCnType("机架");
				pojo.setImName(equt.getEname());
				pojo.setCity(county);
				pojo.setCounty(county.split("_")[county.split("_").length-1]);
				if(outIN.contains("success=\"true\"")){
					pojo.setFaceResult("success");
					Map<String, String> resultMap = XmlUtil.getMapXml(outIN);
					String int_id = resultMap.get("int_id").toString();
					String upSql = "update job_equtinfo "
							+ " set resNum = '"+int_id+"' "
							+ " where id = '"+equt.getId()+"'";
					this.jdbcTemplate.execute(upSql);
				}else{
					pojo.setFaceResult("error");
				}
				this.irmsInterService.addInterLog(pojo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 修改机架信息
	 * @param equt
	 * @return
	 */
	public String updateRack(EqutInfoBean equt) {
		String result ="";
		Map<String, Object> map = this.getStationBygid(Integer.parseInt(equt.getGid()));
		Map<String, String> cityMap = this.irmsInterService.getCityCountry(map.get("siteRegion")+"");
		String county = map.get("siteRegion")+"";
		try {
			if(map.get("siteRes") != null && map.get("gres") != null){
				String lifeParams= "<params>"
						+ "<param key=\"pro_task_id\" value=\"\"/>"
						+ "<param key=\"status\" value=\"3\"/>"
						+ "<param key=\"photo\" value=\"\"/>"
						+ "</params>";
				String param = "<params model=\"rack\">"
						+ "<obj user=\"root\" zh_label=\""+equt.getEname()+"\" "
						+ "shelf_capacity=\"10\" "
						+ "device_level=\"2\" "
						+ "length=\""+equt.getEqutLength()+"\" "
						+ "width=\""+equt.getEqutWide()+"\" "
						+ "height=\""+equt.getEqutTall()+"\" "
						+ "ownership=\"1\" "
						+ "maint_mode=\"1\" "
						+ "service_state=\"0\" "
						+ "is_splitting=\"0\" "
						+ "int_id=\""+equt.getResNum()+"\" "
						+ "modifier=\""+equt.getParties()+"\" "
						+ "modify_time=\""+equt.getMtime()+"\" "
						+ "related_rack_position=\"\" "
						+ "related_equiproom=\""+map.get("gres")+"\" "
						+ "related_site=\""+map.get("siteRes")+"\" "
						+ "city_id=\""+cityMap.get("cityId")+"\" "
						+ "county_id=\""+cityMap.get("countyId")+"\"/>"
						+ "</params>";
				String jsonString = "params="+URLEncoder.encode(param, "UTF-8")+"&lifeparams="+lifeParams;
				String outIN = RequestUtil.HttpRequest(InterfaceAddr.UPDATE_RACK, "POST", jsonString);
				
				IrmsPojo pojo = new IrmsPojo();
				pojo.setInStr(param);
				pojo.setOutStr(outIN);
				pojo.setFaceType("equt");
				pojo.setCreater(equt.getParties());
				pojo.setFaceCnType("修改机架");
				pojo.setImId(equt.getEid());
				pojo.setImEnType("equt");
				pojo.setImCnType("机架");
				pojo.setImName(equt.getEname());
				pojo.setResId(equt.getResNum());
				pojo.setCity(county);
				pojo.setCounty(county.split("_")[county.split("_").length-1]);
				if(outIN.contains("success=\"true\"")){
					pojo.setFaceResult("success");
					String upSql = "update job_equtinfo "
							+ " set resMotion = '修改机架' "
							+ " where id = '"+equt.getId()+"'";
					this.jdbcTemplate.execute(upSql);
				}else{
					pojo.setFaceResult("error");
				}
				this.irmsInterService.addInterLog(pojo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 删除机架
	 * @param equt
	 * @return
	 */
	public String delRack(EqutInfoBean equt) {
		String result ="";
		try {
			Map<String, Object> map = this.getStationBygid(Integer.parseInt(equt.getGid()));
			Map<String, String> cityMap = this.irmsInterService.getCityCountry(map.get("siteRegion")+"");
			String county = map.get("siteRegion")+"";
			
			if(map.get("siteRes") != null && map.get("gres") != null){
				String lifeParams= "<params>"
						+ "<param key=\"pro_task_id\" value=\"\"/>"
						+ "<param key=\"status\" value=\"3\"/>"
						+ "<param key=\"photo\" value=\"\"/>"
						+ "</params>";
				
				String rackId = equt.getResNum();
				String roomId = map.get("gres")+"";
				String siteId = map.get("siteRes")+"";
				String param = "<params> <param key=\"type\" value=\"rack\"/>"
						+ "<param key=\"id\" value=\""+rackId+"\"/>"
						+ "<param key=\"examineid\" value=\"\"/>"
						+ "<param key=\"room_id\" value=\""+roomId+"\"/>"
						+ "<param key=\"site_id\" value=\""+siteId+"\"/>"
						+ "</params>";
				String jsonString = "params="+URLEncoder.encode(param, "UTF-8")+"&lifeparams="+lifeParams;
				String outIN = RequestUtil.HttpRequest(InterfaceAddr.DEL_RACK, "POST", jsonString);
				
				
				IrmsPojo pojo = new IrmsPojo();
				pojo.setInStr(param);
				pojo.setOutStr(outIN);
				pojo.setFaceType("equt");
				pojo.setCreater(equt.getParties());
				pojo.setFaceCnType("删除机架");
				pojo.setImId(equt.getEid());
				pojo.setImEnType("equt");
				pojo.setImCnType("机架");
				pojo.setImName(equt.getEname());
				pojo.setResId(equt.getResNum());
				pojo.setCity(county);
				pojo.setCounty(county.split("_")[county.split("_").length-1]);
				if(outIN.contains("success=\"true\"")){
					pojo.setFaceResult("success");
					String upSql = "update job_equtinfo "
							+ " set resMotion = '删除机架' "
							+ " where id = '"+equt.getId()+"'";
					this.jdbcTemplate.execute(upSql);
				}else{
					pojo.setFaceResult("error");
				}
				this.irmsInterService.addInterLog(pojo);
			}
			
				
				
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result; 
	}
	
	/**
	 * 
	 * @param equt
	 * @return
	 */
	public String addPosition(EqutInfoBean equt,String gres){
		String result = "";
		try{
			String params ="<params>"
					+ " <param room_id=\""+gres+"\" "
					+ " row=\""+equt.getJijiahanghao()+"\" col=\""+equt.getJijialiehao()+"\""
					+ " row_show=\""+equt.getJijiahanghao()+"\" col_show=\""+equt.getJijialiehao()+"\"/>"
					+ "</params>";
			String lifeParams= "<params>"
					+ "<param key=\"pro_task_id\" value=\"\"/>"
					+ "<param key=\"status\" value=\"3\"/>"
					+ "<param key=\"photo\" value=\"\"/>"
					+ "</params>";
			String jsonString = "empname=root&user_account=root&params="+URLEncoder.encode(params, "UTF-8")+"&lifeparams="+lifeParams;
			String outIN = RequestUtil.HttpRequest(InterfaceAddr.ADD_POSITION, "POST", jsonString);
			System.out.println(outIN);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 增加ODM模块
	 * @return
	 */
	public String insertOdm(ODMInfoBean odm){
		String result = "";
		Map<String, Object> eMap = this.getEqutByOdm(odm.getOdmId());
		String eid = eMap.get("eResNum").toString();
		Map<String, Object> sMap = new HashMap<String, Object>();
		String stype ="";
		String rackType = "";
		if(odm.getEid().startsWith("EQU")){
			//机房id
			String gid = eMap.get("gid").toString();
			//机架类型
			String eType = eMap.get("jijialeixing").toString();
			//机架id
			stype = getPropert.getValueByKey("stype");
			rackType = getPropert.getValueByKey("odfType");
			sMap = this.getStationBygid(Integer.parseInt(gid));
		}else if(odm.getEid().startsWith("EIU")){
			stype= getPropert.getValueByKey("outType");
			rackType =getPropert.getValueByKey("opticTran");
			String sql="select structure_type as type,structure_id as siteRes "
					+ " from RES_OPTI_TRAN_BOX where int_id ='"+eid+"'";
			sMap = this.getIrmsMap(sql);
		}
		int rowNo = Integer.parseInt(odm.getOdmCode())+1;
		try{
			if(eid != null && sMap.get("siteRes") != null){
				String params =""
						+ "<params>"
						+ "<odm id=\"\" rowflag=\"+\" rownum=\""+odm.getTerminalRowQuantity()+"\""
						+ " colflag=\"+\" colnum=\""+odm.getTerminalColumnQuantity()+"\">"
						+ "<attribute "
						+ " module_rowno=\""+rowNo+"\" "
						+ " rowno=\""+odm.getTerminalRowQuantity()+"\" "
						+ " columnno=\""+odm.getTerminalColumnQuantity()+"\" "
						+ " terminal_arr=\"4\" "
						+ " status=\"3\" "
						+ " loc_in_rack=\""+rowNo+"\""
						+ " county_id=\"0\" "
						+ " city_id=\"0\" "
						+ " structure_id=\""+sMap.get("siteRes")+"\""
						+ " structure_type=\""+stype+"\" "
						+ " related_rack=\""+eid+"\" "
						+ " related_type=\""+rackType+"\""
						+ " model=\"odm\""
						+ " /> "
						+ "</odm>"
						+ "</params>";
				String lifeParams= "<params>"
						+ "<param key=\"pro_task_id\" value=\"\"/>"
						+ "<param key=\"status\" value=\"3\"/>"
						+ "<param key=\"photo\" value=\"0\"/>"
						+ "</params>";
				String jsonString = "params="+URLEncoder.encode(params, "UTF-8")+"&lifeparams="+lifeParams;
				String outIN = RequestUtil.HttpRequest(InterfaceAddr.ADD_ODM, "POST", jsonString);
				//因为返回的报文太长所以存一下吧
				IrmsPojo pojo = new IrmsPojo();
				pojo.setInStr(params);
				pojo.setFaceType("odm");
				if(outIN.contains("success=\"true\"")){
					//保存模块的综资id
					String odmId = XmlUtil.getOdmId(outIN);
					String upSql ="update job_odm set resNum='"+odmId+"'"
							+ " where odmId ='"+odm.getOdmId()+"'"
							+ "";
					this.jdbcTemplate.execute(upSql);
					pojo.setFaceResult("success");
					String filePath = UploadUtil.saveXml(outIN);
					pojo.setOutStr(filePath);
					List<Map<String, String>> resulist = XmlUtil.getPointList(outIN);
					this.beatchPonint(resulist, odm);
				}else{
					pojo.setOutStr(outIN);
					pojo.setFaceResult("error");
				}
				pojo.setCreater(odm.getCuser());
				pojo.setFaceCnType("新增模块");
				pojo.setImId(odm.getOdmId()+"");
				pojo.setImEnType("odm");
				pojo.setImCnType("模块");
				pojo.setImName(odm.getOdmName());
				this.irmsInterService.addInterLog(pojo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 删除ODM
	 * @param odm
	 */
	public void delOdm(ODMInfoBean odm){
		try{
			String sql = "select resNum from job_odm where odmId ='"+odm.getOdmId()+"'";
			List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
			if(TextUtil.isNotNull(list)){
				Map<String, Object> map = list.get(0);
				if(map.get("resNum") !=null){
					String params = "<params>"
							+ "<param key=\"type\" value=\"odm\"/>"
							+ "<param key=\"id\" value=\""+map.get("resNum")+"\"/>"
							+ "</params>";
					String jsonString = "params="+URLEncoder.encode(params, "UTF-8");
					String outIN = RequestUtil.HttpRequest(InterfaceAddr.DEL_ODM, "POST", jsonString);
					IrmsPojo pojo = new IrmsPojo();
					pojo.setInStr(params);
					pojo.setFaceType("delOdm");
					if(outIN.contains("success=\"true\"")){
						pojo.setOutStr(outIN);
						pojo.setFaceResult("success");
					}else{
						pojo.setOutStr(outIN);
						pojo.setFaceResult("error");
					}
					this.irmsInterService.addInterLog(pojo);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	Map<String, Object> getIrmsMap(String sql){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = this.irmsjdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			map = list.get(0);
		}
		return map;
	}
	
	/**
	 * 保存跳纤关系
	 * @param list
	 */
	public void saveJumper(JumpFiberBean jumpFier){
		try{
			PointCommon start = jumpFier.getStart();
			PointCommon end = jumpFier.getEnd();
			Map<String, String> startMap = this.getPointMsg(start);
			Map<String, String> endMap = this.getPointMsg(end);
			if(startMap != null && endMap !=null ){
				String params = "<params>"
						+ "<param"
						+ " a_site_type=\""+startMap.get("siteType")+"\""
						+ " a_site_id= =\""+startMap.get("siteId")+"\" "
						+ " a_equ_type=\""+startMap.get("equType")+"\" "
						+ " a_equ_id=\""+startMap.get("equType")+"\" "
						+ " a_port_type =\""+startMap.get("pointType")+"\" "
						+ " a_port_id=\""+startMap.get("pointId")+"\""
						+ " z_site_type=\""+endMap.get("siteType")+"\""
						+ " z_site_id= =\""+endMap.get("siteId")+"\" "
						+ " z_equ_type=\""+endMap.get("equType")+"\" "
						+ " z_equ_id=\""+endMap.get("equType")+"\" "
						+ " z_port_type =\""+endMap.get("pointType")+"\" "
						+ " z_port_id=\""+endMap.get("pointId")+"\""
						+ ""
						+ "/>"
						+ "</params>";
				String lifeParam = "<params>"
						+ "<param key=\"pro_task_id\" value=\"\"/>"
						+ "<param key=\"status\" value=\"\"/>"
						+ "<param key=\"photo\" value=\"\"/>"
						+ "</params>"
						+ "";
				String jsonString = "params="+URLEncoder.encode(params, "UTF-8")+"&lifeparams="+URLEncoder.encode(lifeParam, "UTF-8");
				String outIN = RequestUtil.HttpRequest(InterfaceAddr.SAVE_JUMPER, "POST", jsonString);
				IrmsPojo pojo = new IrmsPojo();
				pojo.setInStr(params);
				pojo.setFaceType("saveJump");
				if(outIN.contains("success=\"true\"")){
					pojo.setOutStr(outIN);
					pojo.setFaceResult("success");
				}else{
					pojo.setOutStr(outIN);
					pojo.setFaceResult("error");
				}
				this.irmsInterService.addInterLog(pojo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 保存上架信息
	 * @param equtRank
	 */
	public void saveFiber(EqutRankInfo equtRank){
		try{
			String params = "";
			Map<String, Object> fiberMap= this.getFiberByid(equtRank.getFiberId()+"");
			Map<String, Object> odmpoint = this.getOdfPoint(equtRank.getPointId());
			Map<String, Object> equtMap = this.getEqutByOdm(Integer.parseInt(odmpoint.get("odmId")+""));
			String equtType ="odf";
			if((odmpoint.get("eid")+"").startsWith("EQU")){
				equtType ="odf";
			}else if((odmpoint.get("eid")+"").startsWith("EIU")){
				equtType ="gjjx";
			}
			if(equtRank.getCableItem().equals("start")){
				params +="<params>"
						+ "<param"
						+ " fiber_id=\""+fiberMap.get("resNum")+"\""
						+ " a_equ_id=\""+equtMap.get("eResNum")+"\""
						+ " a_port_id=\""+odmpoint.get("resNum")+"\""
						+ " a_equ_type=\""+equtType+"\""
						+ " z_equ_id=\"\""
						+ " z_port_id=\"\""
						+ " z_equ_type=\""+equtType+"\""
						+ "/>"
						+ "</params>";
			}else{
				params +="<params>"
						+ "<param"
						+ " fiber_id=\""+fiberMap.get("resNum")+"\""
						+ " a_equ_id=\"\""
						+ " a_port_id=\"\""
						+ " a_equ_type=\""+equtType+"\""
						+ " z_equ_id=\""+equtMap.get("eResNum")+"\""
						+ " z_port_id=\""+odmpoint.get("resNum")+"\""
						+ " z_equ_type=\""+equtType+"\""
						+ "/>"
						+ "</params>";
			}
			String lifeParam = "<params>"
					+ "<param key=\"pro_task_id\" value=\"\"/>"
					+ "<param key=\"status\" value=\"\"/>"
					+ "<param key=\"photo\" value=\"\"/>"
					+ "</params>"
					+ "";
			String jsonString = "params="+URLEncoder.encode(params, "UTF-8")+"&lifeparams="+URLEncoder.encode(lifeParam, "UTF-8");
			String outIN = RequestUtil.HttpRequest(InterfaceAddr.SAVE_FIBER, "POST", jsonString);
			IrmsPojo pojo = new IrmsPojo();
			pojo.setInStr(params);
			pojo.setFaceType("saveFiber");
			if(outIN.contains("success=\"true\"")){
				pojo.setOutStr(outIN);
				pojo.setFaceResult("success");
			}else{
				pojo.setOutStr(outIN);
				pojo.setFaceResult("error");
			}
			this.irmsInterService.addInterLog(pojo);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除上架信息
	 * @param point
	 */
	public void delFiber(PointInfoBean point){
		try{
			Map<String, String> map = this.getFiberByPoint(point);
			String params =""
					+ "<params>"
					+ "<param fiber_id=\""+map.get("resNum")+"\" port_id=\""+point.getResNum()+"\"/>"
					+ "<params>";
			String lifeParam = "<params>"
					+ "<param key=\"pro_task_id\" value=\"\"/>"
					+ "<param key=\"status\" value=\"\"/>"
					+ "<param key=\"photo\" value=\"\"/>"
					+ "</params>"
					+ "";
			String jsonString = "params="+URLEncoder.encode(params, "UTF-8")+"&lifeparams="+URLEncoder.encode(lifeParam, "UTF-8");
			String outIN = RequestUtil.HttpRequest(InterfaceAddr.DEL_FIBER, "POST", jsonString);
			IrmsPojo pojo = new IrmsPojo();
			pojo.setInStr(params);
			pojo.setFaceType("delFiber");
			if(outIN.contains("success=\"true\"")){
				pojo.setOutStr(outIN);
				pojo.setFaceResult("success");
			}else{
				pojo.setOutStr(outIN);
				pojo.setFaceResult("error");
			}
			this.irmsInterService.addInterLog(pojo);
					
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据端口类型得到
	 * 端口相应数据
	 * @param point
	 * @return
	 */
	Map<String, String> getPointMsg(PointCommon point){
		Map<String, String> map= new HashMap<String, String>();
		if(point.getPointType().equals("odm")){
			//得到ODM上的端子信息
			Map<String, Object> odmpoint = this.getOdfPoint(point.getId());
			map.put("pointType", "terminal");
			map.put("pointId", odmpoint.get("resNum")+"");
			//得到对应的机架或公交信息
			Map<String, Object> equtPoint = this.getEqutBypoint(odmpoint.get("eid")+"");
			if((odmpoint.get("eid")+"").startsWith("EQU")){
				//机架信息
				map.put("equType", "odf");
				map.put("equId", equtPoint.get("resNum")+"");
				//根据机架得到站点信息
				Map<String, Object> siteMsg = this.getStationBygid(Integer.parseInt(equtPoint.get("gid")+""));
				map.put("siteType", "9503");
				map.put("siteId", siteMsg.get("siteRes")+"");
			}else if((odmpoint.get("eid")+"").startsWith("EIU")){
				//光交箱数据
				map.put("equType", "gjjx");
				map.put("equId", equtPoint.get("resNum")+"");
				
				
				map.put("siteType", "");
				map.put("siteId", "");
			}
		}else if(point.getPointType().equals("ne")){
			//得到传输网元端口信息
			Map<String, Object> nePoint = this.getNePoint(point.getId());
			map.put("pointType", "terminal");
			map.put("pointId", nePoint.get("resNum")+"");
			//得到传输网元信息
			Map<String, Object> neDevice = this.getNeDevice(Integer.parseInt(nePoint.get("appDevice")+""));
			map.put("equType", "transne");
			map.put("equId", neDevice.get("resNum")+"");
			//根据传输网元机房得到站点信息
			Map<String, Object> neSite = this.getStationBygid(Integer.parseInt(neDevice.get("roomId")+""));
			map.put("siteType", "9503");
			map.put("siteId", neSite.get("siteRes")+"");
		}
		return map;
	}
	
	/**
	 * 批量更改端子
	 * @param list
	 * @param odm
	 */
	void beatchPonint(final List<Map<String, String>> list,final ODMInfoBean odm){
		String odmCode = odm.getOdmCode();
		if(odmCode.length() == 1){
			odmCode = "0"+odmCode;
		}
		final String pid = odmCode;
		String sql ="update job_pointinfo "
				+ " set resNum =? ,pos =?"
				+ " where EID=? and PID=? "
				+ "";
		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
			@Override
			public int getBatchSize() {
				return list.size();
			}
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				Map<String, String> map= list.get(i);
				ps.setString(1, map.get("resNum")+"");
				ps.setString(2, map.get("name")+"");
				ps.setString(3, odm.getEid());
				ps.setString(4, pid +map.get("row")+map.get("col"));
			}
			
		});
	}
	
	
	/**
	 * 根据odm得到机架的信息
	 * @param odmId
	 * @return
	 */
	Map<String, Object> getEqutByOdm(Integer odmId){
		String sql =" select e.resNum as eResNum,e.jijialeixing,e.gid"
				+ "  from job_odm o,job_equtinfo e "
				+ " where o.eid = e.EID and o.odmId ='"+odmId+"' ";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		Map<String, Object> map = new HashMap<String, Object>();
		if(TextUtil.isNotNull(list)){
			map = list.get(0);
		}
		return map;
	}
	
	
	/**
	 * 根据机房信息得到站点信息
	 * @param gid
	 * @return
	 */
	Map<String, Object> getStationBygid(Integer gid){
		String sql = ""
				+ "select g.generatorId as gid,g.resNum as gres,s.stationBaseId as siteId,"
				+ " s.stationName as siteName,s.region as siteRegion,s.stationAddr as siteAddr,s.resNum as siteRes"
				+ " from job_generator g,job_stationbase s "
				+ " where g.areano = s.stationBaseId "
				+ " and g.generatorId = '"+gid+"' ";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		Map<String, Object> map = new HashMap<String, Object>();
		if(TextUtil.isNotNull(list)){
			map= list.get(0);
		}
		return map;
	}
	
	
	/**
	 * 得到传输网元
	 * 的基本信息
	 * @param id
	 * @return
	 */
	Map<String, Object> getNePoint(Integer id){
		Map<String, Object> map = new HashMap<String, Object>();
		String sql =" select id as id , pointName as zh_label,cardId as appCard,"
				+ " deviceId as appDevice,resNum as resNum,resCard ,resNe"
				+ "  from job_point where id = '"+id+"' ";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			map = list.get(0);
		}
		return map;
	}
	
	/**
	 * 得到传输
	 * 网元的基本信息
	 * @param deviceId
	 * @return
	 */
	Map<String, Object> getNeDevice(Integer deviceId){
		Map<String, Object> map= new HashMap<String, Object>();
		String sql = " select id,deviceName,roomId,resNum"
				+ " from job_device where id = '"+deviceId+"'";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			map= list.get(0);
		}
		return map;
	}
	
	
	/**
	 * 得到ODF下的端口基本信息
	 * @param pointId
	 * @return
	 */
	Map<String, Object> getOdfPoint(Integer pointId){
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select id,EID as eid,odmId,resNum"
				+ " from job_pointinfo  where id ='"+pointId+"' ";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			map= list.get(0);
		}
		return map;
	}
	
	/**
	 * 根据端口得
	 * 到机架信息
	 * @param eid
	 * @return
	 */
	Map<String, Object> getEqutBypoint(String eid){
		Map<String, Object> map = new HashMap<String, Object>();
		String sql ="select id,EID as eid,ENAME as ename,resNum,gid"
				+ " from job_equtinfo where ETYPE =1 and EID= '"+eid+"'";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			map = list.get(0);
		}
		return map;
	}
	
	/**
	 * 得到纤芯数据
	 * @param id
	 * @return
	 */
	Map<String, Object> getFiberByid(String id){
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select id,zhLabel,startPortType ,startPortId, "
				+ " endPortType, endPortId,cableId,resNum"
				+ " from job_fiber where id ='"+id+"'";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			map = list.get(0);
		}
		return map;
	}
	
	/**
	 * 根据端子得到纤芯
	 * @param point
	 * @return
	 */
	Map<String, String> getFiberByPoint(PointInfoBean point){
		Map<String, String> map = new HashMap<String, String>();
		String sql ="select resNum from job_fiber where startPortId ='"+point.getId()+"' and deleteflag ='0'"
				+ " union all "
				+ " select resNum from job_fiber where startPortId ='"+point.getResNum()+"' and deleteflag ='0'"
				+ " union all"
				+ " select resNum from job_fiber where endPortId  ='"+point.getId()+"' and deleteflag ='0' "
				+ " union all"
				+ " select resNum from job_fiber where endPortId  ='"+point.getResNum()+"' and deleteflag ='0' ";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			map.put("resNum", list.get(0).get("resNum")+"");
		}
		return map;
	}
	
	/**
	 * 根据端子端口得到
	 * 跳纤数据
	 * @param id
	 * @return
	 */
	Map<String, Object> getJumperByid(String id){
		Map<String , Object> map= new HashMap<String, Object>();
		String sql ="select jumpfiberId,pid1,pid2,pid1Type,pid2Type,resNum from job_jumpfiber where pid1 ='"+id+"'"
				+ " union all "
				+ " select jumpfiberId,pid1,pid2,pid1Type,pid2Type,resNum from job_jumpfiber where pid2 ='"+id+"'";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			map = list.get(0);
		}
		return map;
	}
	
	/**
	 * 移动网络资源点
	 * @param obj
	 */
	public void moveResPoint(WirelessPointPojo pojo){
		try{
			if(TextUtil.isNotNull(pojo.getResNum())){
				Map<String, String> map= this.irmsInterService.getCityCountry(pojo.getWirelessCounty());
				String xml = "<xmldata mode=\"SinglePointEditMode\">"
						+ "<mc type=\"wangluoziyuandian\">"
						+ "<mo group=\"1\">"
						+ "<fv k=\"ZH_LABEL\" v=\""+pojo.getWirelessName()+"\"/>"
						+ "<fv k=\"INT_ID\" v=\""+pojo.getResNum()+"\"/>"
						+ "<fv k=\"LONGITUDE\" v=\""+pojo.getLongitude()+"\"/>"
						+ "<fv k=\"LATITUDE\" v=\""+pojo.getLatitude()+"\"/>";
				xml+="</mo>"
					+ "</mc>"
					+ "</xmldata>"
					+ "";
				String jsonString = "xml="+URLEncoder.encode(xml, "UTF-8");
				String outIN = RequestUtil.HttpRequest(InterfaceAddr.MOVE_LONLAT, "POST", jsonString);
				IrmsPojo obj = new IrmsPojo();
				obj.setInStr(jsonString);
				obj.setOutStr(outIN);
				obj.setFaceType("moveResPoint");
				obj.setImId(pojo.getWirelessId()+"");
				obj.setImEnType("resPoint");
				obj.setImCnType("资源点");
				obj.setImName(pojo.getWirelessName());
				obj.setCreater(pojo.getMaintainer());
				obj.setResId(pojo.getResNum());
				obj.setFaceCnType("移动资源点");
				if(TextUtil.isNotNull(pojo.getWirelessCounty()) && pojo.getWirelessCounty().contains("_")){
					String[] regions = pojo.getWirelessCounty().split("_");
					obj.setCity(regions[0]+"_"+regions[regions.length-1]);
					obj.setCounty(regions[regions.length-1]);
				}
				if(outIN.contains("loaded=\"成功\"")){
					obj.setFaceResult("success");
				}else{
					obj.setFaceResult("error");
				}
				irmsInterService.addInterLog(obj);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public JdbcTemplate getIrmsjdbcTemplate() {
		return irmsjdbcTemplate;
	}
	public void setIrmsjdbcTemplate(JdbcTemplate irmsjdbcTemplate) {
		this.irmsjdbcTemplate = irmsjdbcTemplate;
	}
	public IirmsInterService getIrmsInterService() {
		return irmsInterService;
	}
	public void setIrmsInterService(IirmsInterService irmsInterService) {
		this.irmsInterService = irmsInterService;
	}
	
}
