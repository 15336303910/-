package interfaces.irmsInterface.interfaces.opticTran.service;

import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import manage.V2.beijing.resource.pojo.ZSLPOSInfoBean;
import manage.device.pojo.CardInfoBean;
import manage.equt.pojo.EqutInfoBean;
import manage.opticalTerminal.pojo.OpticalTerminalObj;
import manage.route.pojo.CableInfoBean;
import manage.route.pojo.FiberBoxInfoBean;
import manage.route.pojo.JointInfoBean;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;


import interfaces.irmsInterface.interfaces.opticTran.service.impl.IirmsOpticTranService;
import interfaces.irmsInterface.interfaces.pojo.IrmsPojo;
import interfaces.irmsInterface.interfaces.pojo.MoveResPojo;
import interfaces.irmsInterface.interfaces.service.impl.IirmsInterService;
import interfaces.irmsInterface.utils.InterfaceAddr;
import interfaces.irmsInterface.utils.RequestUtil;
import interfaces.irmsInterface.utils.XmlUtil;
import base.database.DataBase;
import base.util.TextUtil;

public class IrmsOpticTranService extends DataBase implements IirmsOpticTranService{

	//app
	private JdbcTemplate jdbcTemplate;
	//irms
	private JdbcTemplate irmsjdbcTemplate;
	private IirmsInterService irmsInterService;
	
	/**
	 * 增加光交箱
	 * @param tranBox
	 */
	public void addOptiTranBox(EqutInfoBean tranBox){
		try{
			if(tranBox.getLat().endsWith("0000")) {
				tranBox.setLat(tranBox.getLat().substring(0, tranBox.getLat().length()-4));
			}
			if(tranBox.getLon().endsWith("0000")) {
				tranBox.setLon(tranBox.getLon().substring(0, tranBox.getLon().length()-4));
			}
			Map<String, String> map= this.irmsInterService.getCityCountry(tranBox.getEaddr());
			String xml ="<xmldata mode=\"SinglePointAddMode\">"
					+ "<mc type=\"guangjiaojiexiang\">"
					+ "<mo group=\"1\">"
					+ "<fv k=\"ANZHUANG_LOCATION\" v=\""+tranBox.getAddress()+"\"/>"
					+ "<fv k=\"FIBERCAB_NO\" v=\""+this.getNum(tranBox.getEname())+"\"/>"
					+ "<fv k=\"SUPPLIER\" v=\"\"/>"
					+ "<fv k=\"GUANGJIAODINGWEI\" v=\"1\"/>"
					+ "<fv k=\"STRUCTURE_ID\" v=\"new-"+(tranBox.getId()+1)+"\"/>"
					+ "<fv k=\"OWNERSHIP\" v=\"1\"/>"
					+ "<fv k=\"MODEL\" v=\"\"/>"
					+ "<fv k=\"RES_OWNER\" v=\"1\"/>"
					+ "<fv k=\"STRUCTURE_ID\" v=\"new-"+tranBox.getId()+"\"/>"
					+ "<fv k=\"COUNTY_ID\" v=\""+map.get("countyId")+"\"/>"
					+ "<fv k=\"PROJECT_ID\" v=\"\"/>"
					+ "<fv k=\"INT_ID\" v=\"new-"+tranBox.getId()+"\"/>"
					+ "<fv k=\"LATITUDE\" v=\""+tranBox.getLat()+"\"/>"
					+ "<fv k=\"CAPACITY\" v=\""+tranBox.getInstallCapacity()+"\"/>"
					+ "<fv k=\"FREE_CAPACITY\" v=\""+tranBox.getFreeCapacity()+"\"/>"
					+ "<fv k=\"USED_CAPACITY\" v=\""+tranBox.getUsedCapacity()+"\"/>"
					+ "<fv k=\"DESIGN_CAPACITY\" v=\""+tranBox.getDesignCapacity()+"\"/>"
					+ "<fv k=\"STATUS\" v=\"3\"/>"
					+ "<fv k=\"EXAMINESTATUS\" v=\"2\"/>"
					+ "<fv k=\"ZH_LABEL\" v=\""+tranBox.getEname()+"\"/>"
					+ "<fv k=\"CITY_ID\" v=\""+map.get("cityId")+"\"/>"
					+ "<fv k=\"STRUCTURE_TYPE\" v=\""+getPropert.getValueByKey("outType")+"\"/>"
					+ "<fv k=\"LONGITUDE\" v=\""+tranBox.getLon()+"\"/>"
					+ "<fv k=\"FACE_COUNT\" v=\""+(tranBox.getGjxmianshu()+1)+"\"/>";
			if(TextUtil.isNotNull(tranBox.getDataQualityPrincipal()) && tranBox.getDataQualityPrincipal().contains("_")){
				xml +="<fv k=\"QUALITOR\" v=\""+tranBox.getDataQualityPrincipal().split("_")[1]+"\"/>";
			}
			if(TextUtil.isNotNull(tranBox.getParties())){
				xml +="<fv k=\"MAINTAINOR\" v=\""+tranBox.getParties()+"\"/>";
				List<Map<String, Object>> MainList = this.getMaintainList(tranBox.getParties());
				if(TextUtil.isNotNull(MainList)){
					Map<String, Object> mainMap = MainList.get(0);
					xml+="<fv k=\"DAIWEI_ZU\" v=\""+mainMap.get("daiweiZu")+"\"/>"
						+ "<fv k=\"MAINTAIN_AREA\" v=\""+mainMap.get("maintainArea")+"\"/>";
				}
			}
				xml+= "</mo>"
					+ "</mc>"
					+ "<mc type=\"ziyuandian\">" + 
					" 	<mo group=\"1\">" + 
					"      <fv k=\"PROJECT_ID\" v=\"\"/>" + 
					"      <fv k=\"STATUS\" v=\"3\"/>" + 
					"      <fv k=\"LONGITUDE\" v=\""+tranBox.getLon()+"\"/>" + 
					"      <fv k=\"IS_JIAOWEI\" v=\"1\"/>" + 
					"      <fv k=\"CITY_ID\" v=\""+map.get("cityId")+"\"/>" + 
					"      <fv k=\"COUNTY_ID\" v=\""+map.get("countyId")+"\"/>" + 
					"      <fv k=\"ZH_LABEL\" v=\""+tranBox.getEname()+"-资源点\"/>" + 
					"      <fv k=\"MAINTAIN_AREA\" v=\"1\"/>" + 
					"	   <fv k=\"EXAMINESTATUS\" v=\"2\"/>" +
					"      <fv k=\"LATITUDE\" v=\""+tranBox.getLat()+"\"/>" + 
					"      <fv k=\"INT_ID\" v=\"new-"+(tranBox.getId()+1)+"\"/>" + 
					"    </mo>" + 
					"  </mc>" + 
					""
					+ "</xmldata>"
					+ "";
			String jsonString = "xml="+URLEncoder.encode(xml, "UTF-8");
			String outIN = RequestUtil.HttpRequest(InterfaceAddr.ADD_RESOURCE, "POST", jsonString);
			
			//新增光交箱
			IrmsPojo obj = new IrmsPojo();
			obj.setInStr(xml);
			obj.setOutStr(outIN);
			obj.setFaceType("addTranBox");
			obj.setFaceCnType("新增光交");
			obj.setImId(tranBox.getEid());
			obj.setImEnType("equt");
			obj.setImCnType("光交箱");
			obj.setImName(tranBox.getEname());
			obj.setCreater(tranBox.getParties());
			if(TextUtil.isNotNull(tranBox.getEaddr()) && tranBox.getEaddr().contains("_")){
				String[] regions = tranBox.getEaddr().split("_");
				obj.setCity(regions[0]+"_"+regions[regions.length-1]);
				obj.setCounty(regions[regions.length-1]);
			}
			if(outIN.contains("loaded=\"成功\"")){
				obj.setFaceResult("success");
				String resourceId = XmlUtil.getAddRes(outIN,tranBox.getId()+"");
				String parenteid = XmlUtil.getAddRes(outIN, (tranBox.getId()+1)+"");
				obj.setResId(resourceId);
				String sql ="update job_equtinfo set resNum ='"+resourceId+"',"
						+ " parenteid='"+parenteid+"', resMotion ='Audit'"
						+ " where id ='"+tranBox.getId()+"'";
				this.jdbcTemplate.execute(sql);
				//更新图片信息
				String eid = tranBox.getEid();
				if(eid.startsWith("EIU_")) {
					eid = eid.replace("EIU_", "");
				}
				this.upImageStr(eid, "EIU", resourceId);
			}else{
				obj.setFaceResult("error");
			}
			this.irmsInterService.addInterLog(obj);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 更新照片信息
	 * @param imId
	 * @param type
	 * @param resNum
	 */
	public void upImageStr(String imId,String type,String resNum){
		try{
			String sql = "update resource_images"
					+ " set resNum ='"+resNum+"'"
					+ " where type='"+type+"'"
					+ " and resourceId = '"+imId+"'";
			this.jdbcTemplate.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据名称取数据
	 * @param name
	 * @return
	 */
	public int getNum(String name) {
		int result = 0;
		String regEx="[^0-9]";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(name);   
		if(TextUtil.isNotNull(m.replaceAll("").trim())) {
			result = Integer.parseInt(m.replaceAll("").trim());
		}
		return result;
	}
	
	/**
	 * 移动光交箱
	 * @param tranBox
	 */
	public void moveOptiTranBox(EqutInfoBean tranBox){
		try{
			if(TextUtil.isNotNull(tranBox.getResNum())){
				if(tranBox.getLat().endsWith("0000")) {
					tranBox.setLat(tranBox.getLat().substring(0, tranBox.getLat().length()-4));
				}
				if(tranBox.getLon().endsWith("0000")) {
					tranBox.setLon(tranBox.getLon().substring(0, tranBox.getLon().length()-4));
				}
				Map<String, String> map= this.irmsInterService.getCityCountry(tranBox.getEaddr());
				MoveResPojo obj = new MoveResPojo();
				obj.setAppType("moveTranbox");
				obj.setLatitude(tranBox.getLat());
				obj.setLongitude(tranBox.getLon());
				obj.setResId(tranBox.getResNum());
				obj.setResType("guangjiaojiexiang");
				obj.setResName(tranBox.getEname());
				obj.setCityId(map.get("cityId"));
				obj.setCountyId(map.get("countyId"));
				obj.setAppCnType("移动光交箱");
				obj.setImId(tranBox.getEid());
				obj.setImEnType("equt");
				obj.setImCnType("光交箱");
				obj.setExtendXml("<fv k=\"FACE_COUNT\" v=\""+(tranBox.getGjxmianshu()+1)+"\"/>"
						+ "<fv k=\"FREE_ROOM_FIBER_NUM\" v=\"\"/>"
						+ "<fv k=\"CAPACITY\" v=\""+tranBox.getInstallCapacity()+"\"/>"
						+ "<fv k=\"FREE_CAPACITY\" v=\""+tranBox.getFreeCapacity()+"\"/>"
						+ "<fv k=\"USED_CAPACITY\" v=\""+tranBox.getUsedCapacity()+"\"/>"
						+ "<fv k=\"DESIGN_CAPACITY\" v=\""+tranBox.getDesignCapacity()+"\"/>"
						+ "<fv k=\"ROOM_FIRBER_NUM\" v=\"\"/>");
				obj.setParentid(tranBox.getParenteid());
				obj.setRegion(tranBox.getEaddr());
				if(TextUtil.isNotNull(tranBox.getDataQualityPrincipal()) && tranBox.getDataQualityPrincipal().contains("_")){
					obj.setQualitor(tranBox.getDataQualityPrincipal().split("_")[1]);
				}
				if(TextUtil.isNotNull(tranBox.getParties())){
					obj.setMaintainor(tranBox.getParties());
				}
				String result = this.irmsInterService.moveOptical(obj);
				if(TextUtil.isNotNull(tranBox.getEid()) && result.equals("success")) {
					String sql = "update job_equtinfo "
							+ " set resMotion = 'Audit'"
							+ " where del =0 and EID ='"+tranBox.getEid()+"'";
					this.jdbcTemplate.execute(sql);
				}
				
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 增加光终端盒
	 * @param obj
	 */
	public void addOptTerm(OpticalTerminalObj pojo){
		try{
			Map<String, String> map= this.irmsInterService.getCityCountry(pojo.getTerminalAddres());
			String xml ="<xmldata mode=\"SinglePointAddMode\">"
					+ "<mc type=\"guangzhongduanhe\">"
					+ "<mo group=\"1\">"
					+ "<fv k=\"STRUCTURE_ID\" v=\"new-"+pojo.getId()+"\"/>"
					+ "<fv k=\"OWNERSHIP\" v=\"0\"/>"
					+ "<fv k=\"COUNTY_ID\" v=\""+map.get("countyId")+"\"/>"
					+ "<fv k=\"PROJECT_ID\" v=\"\"/>"
					+ "<fv k=\"INT_ID\" v=\"new-"+pojo.getId()+"\"/>"
					+ "<fv k=\"LATITUDE\" v=\""+pojo.getLatitude()+"\"/>"
					+ "<fv k=\"STATUS\" v=\"3\"/>"
					+ "<fv k=\"ZH_LABEL\" v=\""+pojo.getTerminalName()+"\"/>"
					+ "<fv k=\"CITY_ID\" v=\""+map.get("cityId")+"\"/>"
					+ "<fv k=\"STRUCTURE_TYPE\" v=\""+getPropert.getValueByKey("outType")+"\"/>"
					+ "<fv k=\"LONGITUDE\" v=\""+pojo.getLongitude()+"\"/>";
			if(TextUtil.isNotNull(pojo.getDataQualitier()) && pojo.getDataQualitier().contains("_")){
				xml +="<fv k=\"QUALITOR\" v=\""+pojo.getDataQualitier().split("_")[1]+"\"/>";
			}
			if(TextUtil.isNotNull(pojo.getMaintainer())){
				xml +="<fv k=\"MAINTAINOR\" v=\""+pojo.getMaintainer()+"\"/>";
			}
				xml+= "</mo>"
					+ "</mc>"
					+ "</xmldata>"
					+ "";
			String jsonString = "xml="+URLEncoder.encode(xml, "UTF-8");
			String outIN = RequestUtil.HttpRequest(InterfaceAddr.ADD_RESOURCE, "POST", jsonString);
			
			IrmsPojo obj = new IrmsPojo();
			obj.setInStr(xml);
			obj.setOutStr(outIN);
			obj.setFaceType("addOpticalTerm");
			if(outIN.contains("loaded=\"成功\"")){
				obj.setFaceResult("success");
				String resourceId = XmlUtil.getAddRes(outIN,pojo.getId()+"");
				String sql= "update optical_terminal "
						+ " set resNum ='"+resourceId+"'"
						+ " where id ='"+pojo.getId()+"'";
				this.jdbcTemplate.execute(sql);
			}else{
				obj.setFaceResult("error");
			}
			this.irmsInterService.addInterLog(obj);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/***
	 * 移动光终端盒
	 * @param obj
	 */
	public void moveOptTerm(OpticalTerminalObj obj){
		try{
			if(TextUtil.isNotNull(obj.getResNum())){
				Map<String, String> map= this.irmsInterService.getCityCountry(obj.getTerminalAddres());
				MoveResPojo pojo = new MoveResPojo();
				pojo.setAppType("moveOptTerm");
				pojo.setLatitude(obj.getLatitude());
				pojo.setLongitude(obj.getLongitude());
				pojo.setResId(obj.getResNum());
				pojo.setResType("guangzhongduanhe");
				pojo.setResName(obj.getTerminalName());
				pojo.setCityId(map.get("cityId"));
				pojo.setCountyId(map.get("countyId"));
				pojo.setAppCnType("移动光终端盒");
				pojo.setImId(obj.getId()+"");
				pojo.setImEnType("optTerminal");
				pojo.setImCnType("光终端盒");
				pojo.setRegion(obj.getTerminalAddres());
				if(TextUtil.isNotNull(obj.getDataQualitier()) && obj.getDataQualitier().contains("_")){
					pojo.setQualitor(obj.getDataQualitier().split("_")[1]);
				}
				if(TextUtil.isNotNull(obj.getMaintainer())){
					pojo.setMaintainor(obj.getMaintainer());
				}
				this.irmsInterService.moveOptical(pojo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 增加分纤箱
	 * @param fiberBox
	 */
	public void addFiberBox(FiberBoxInfoBean fiberBox){
		try{
			Map<String, String> map= this.irmsInterService.getCityCountry(fiberBox.getMaintainArea());
			String xml ="<xmldata mode=\"SinglePointAddMode\">"
					+ "<mc type=\"guangfenxianxiang\">"
					+ "<mo group=\"1\">"
					+ "<fv k=\"STRUCTURE_ID\" v=\"new-"+fiberBox.getId()+"\"/>"
					+ "<fv k=\"OWNERSHIP\" v=\"1\"/>"
					+ "<fv k=\"COUNTY_ID\" v=\""+map.get("countyId")+"\"/>"
					+ "<fv k=\"PROJECT_ID\" v=\"\"/>"
					+ "<fv k=\"INT_ID\" v=\"new-"+fiberBox.getId()+"\"/>"
					+ "<fv k=\"LATITUDE\" v=\""+fiberBox.getLatitude()+"\"/>"
					+ "<fv k=\"STATUS\" v=\"3\"/>"
					+ "<fv k=\"ZH_LABEL\" v=\""+fiberBox.getFiberboxName()+"\"/>"
					+ "<fv k=\"CITY_ID\" v=\""+map.get("cityId")+"\"/>"
					+ "<fv k=\"STRUCTURE_TYPE\" v=\""+getPropert.getValueByKey("outType")+"\"/>"
					+ "<fv k=\"LONGITUDE\" v=\""+fiberBox.getLongitude()+"\"/>";
				if(TextUtil.isNotNull(fiberBox.getDataQualitier()) && fiberBox.getDataQualitier().contains("_")){
					xml +="<fv k=\"QUALITOR\" v=\""+fiberBox.getDataQualitier().split("_")[1]+"\"/>";
				}
				if(TextUtil.isNotNull(fiberBox.getMaintainer())){
					xml +="<fv k=\"MAINTAINOR\" v=\""+fiberBox.getMaintainer()+"\"/>";
				}
				xml+= "<fv k=\"FIBERDP_NO\" v=\""+fiberBox.getFiberdpNo()+"\"/>"
					+ "<fv k=\"FREE_CAPACITY\" v=\""+fiberBox.getFreeCapacity()+"\"/>"
					+ "<fv k=\"INSTALL_CAPACITY\" v=\""+fiberBox.getInstallCapacity()+"\"/>"
					+ "<fv k=\"CREATOR\" v=\"yc\"/>"
					+ "</mo>"
					+ "</mc>"
					+ "</xmldata>"
					+ "";
			String jsonString = "xml="+URLEncoder.encode(xml, "UTF-8");
			String outIN = RequestUtil.HttpRequest(InterfaceAddr.ADD_RESOURCE, "POST", jsonString);
			
			
			IrmsPojo obj = new IrmsPojo();
			obj.setInStr(xml);
			obj.setOutStr(outIN);
			obj.setFaceType("addFiberBox");
			if(outIN.contains("loaded=\"成功\"")){
				obj.setFaceResult("success");
				String resourceId = XmlUtil.getAddRes(outIN,fiberBox.getId()+"");
				String sql= "update job_fiberbox "
						+ " set resNum ='"+resourceId+"'"
						+ " where id ='"+fiberBox.getId()+"'";
				this.jdbcTemplate.execute(sql);
			}else{
				obj.setFaceResult("error");
			}
			this.irmsInterService.addInterLog(obj);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 增加分光器
	 * @param obj
	 */
	public void addResPos(ZSLPOSInfoBean pos){
		try{
			Map<String, String> map= this.irmsInterService.getCityCountry(pos.getMaintainArea());
			String xml ="<xmldata mode=\"SinglePointAddMode\">"
					+ "<mc type=\"pos\">"
					+ "<mo group=\"1\">"
					+ "<fv k=\"STRUCTURE_ID\" v=\"new-"+pos.getId()+"\"/>"
					+ "<fv k=\"OWNERSHIP\" v=\"1\"/>"
					+ "<fv k=\"COUNTY_ID\" v=\""+map.get("countyId")+"\"/>"
					+ "<fv k=\"PROJECT_ID\" v=\"\"/>"
					+ "<fv k=\"INT_ID\" v=\"new-"+pos.getId()+"\"/>"
					+ "<fv k=\"STATUS\" v=\"3\"/>"
					+ "<fv k=\"ZH_LABEL\" v=\""+pos.getPosName()+"\"/>"
					+ "<fv k=\"CITY_ID\" v=\""+map.get("cityId")+"\"/>"
					+ "<fv k=\"STRUCTURE_TYPE\" v=\""+getPropert.getValueByKey("outType")+"\"/>"
					+ "<fv k=\"SCALE\" v=\""+this.getScale(pos.getFenGuangBi()+"")+"\"/>"
					+ "<fv k=\"QUALITOR\" v=\""+pos.getDataQualityPrincipal()+"\"/>"
					+ "<fv k=\"MAINTAINOR\" v=\""+pos.getParties()+"\"/>"
					+ "<fv k=\"CREATOR\" v=\"yc\"/>"
					+ "</mo>"
					+ "</mc>"
					+ "</xmldata>"
					+ "";
			String jsonString = "xml="+URLEncoder.encode(xml, "UTF-8");
			String outIN = RequestUtil.HttpRequest(InterfaceAddr.ADD_RESOURCE, "POST", jsonString);
			
			
			IrmsPojo obj = new IrmsPojo();
			obj.setInStr(xml);
			obj.setOutStr(outIN);
			obj.setFaceType("addPos");
			if(outIN.contains("loaded=\"成功\"")){
				obj.setFaceResult("success");
				String resourceId = XmlUtil.getAddRes(outIN,pos.getId()+"");
				String sql= "update resource_pos "
						+ " set resNum ='"+resourceId+"'"
						+ " where id ='"+pos.getId()+"'";
				this.jdbcTemplate.execute(sql);
			}else{
				obj.setFaceResult("error");
			}
			this.irmsInterService.addInterLog(obj);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 移动修改分纤箱
	 * @param fiberBox
	 */
	public void moveFiberBox(FiberBoxInfoBean fiberBox){
		try{
			if(TextUtil.isNotNull(fiberBox.getResNum())){
				Map<String, String> map= this.irmsInterService.getCityCountry(fiberBox.getMaintainArea());
				MoveResPojo pojo = new MoveResPojo();
				pojo.setAppType("moveFiberBox");
				pojo.setLatitude(fiberBox.getLatitude());
				pojo.setLongitude(fiberBox.getLongitude());
				pojo.setResId(fiberBox.getResNum());
				pojo.setResType("guangfenxianxiang");
				pojo.setResName(fiberBox.getFiberboxName());
				
				pojo.setCityId(map.get("cityId"));
				pojo.setCountyId(map.get("countyId"));
				pojo.setAppCnType("移动分纤箱");
				pojo.setImId(fiberBox.getId()+"");
				pojo.setImEnType("fiberBox");
				pojo.setImCnType("分纤箱");
				pojo.setRegion(fiberBox.getMaintainArea());
				
				if(TextUtil.isNotNull(fiberBox.getDataQualitier()) && fiberBox.getDataQualitier().contains("_")){
					pojo.setQualitor(fiberBox.getDataQualitier().split("_")[1]);
				}
				if(TextUtil.isNotNull(fiberBox.getMaintainer())){
					pojo.setMaintainor(fiberBox.getMaintainer());
				}
				this.irmsInterService.moveOptical(pojo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 增加光接头盒
	 * @param joint
	 */
	public void addOpticJoint(JointInfoBean joint){
		String sql="select "
				+ " case t.addrType "
		   		+ " when 2 then (select resNum from poleinfo where poleId = t.poleId )"
		   		+ " when 1 then (select resNum  from wellinfo where wellId = t.wellId )"
		   		+ " when 3 then (select resNum  from stoneinfo where stoneId = t.stoneId)"
		   		+ " end  as strId,"
		   		+ " case t.addrType "
		   		+ " when 2 then (select region from poleinfo where poleId = t.poleId )"
		   		+ " when 1 then (select region from wellinfo where wellId = t.wellId )"
		   		+ " when 3 then (select stoneArea from stoneinfo where stoneId = t.stoneId)"
		   		+ " end  as country,"
				+ " case t.addrType "
				+ " when 2 then '"+getPropert.getValueByKey("poleType")+"' "
				+ " when 1 then '"+getPropert.getValueByKey("wellType")+"' "
				+ " when 3 then '"+getPropert.getValueByKey("stoneType")+"' "
				+ " end as strType,"
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
		   		+ " and t.jointId ='"+joint.getJointId()+"' ";
		Map<String, Object> jointMap = this.jdbcTemplate.queryForMap(sql);
		Map<String, String> cityMap= this.irmsInterService.getCityCountry(jointMap.get("country")+"");
		
		String xml ="<xmldata mode=\"SinglePointAddMode\">"
				+ "<mc type=\"guangjietouhe\">"
				+ "<mo group=\"1\">"
				+ "<fv k=\"STRUCTURE_ID\" v=\"new-"+joint.getJointId()+"\"/>"
				+ "<fv k=\"OWNERSHIP\" v=\"0\"/>"
				+ "<fv k=\"COUNTY_ID\" v=\""+cityMap.get("countyId")+"\"/>"
				+ "<fv k=\"PROJECT_ID\" v=\"\"/>"
				+ "<fv k=\"INT_ID\" v=\"new-"+joint.getJointId()+"\"/>"
				+ "<fv k=\"LATITUDE\" v=\""+jointMap.get("lat")+"\"/>"
				+ "<fv k=\"STATUS\" v=\"3\"/>"
				+ "<fv k=\"ZH_LABEL\" v=\""+joint.getJointName()+"\"/>"
				+ "<fv k=\"CITY_ID\" v=\""+cityMap.get("cityId")+"\"/>"
				+ "<fv k=\"STRUCTURE_TYPE\" v=\""+jointMap.get("strType")+"\"/>"
				+ "<fv k=\"LONGITUDE\" v=\""+jointMap.get("lon")+"\"/>";
		if(TextUtil.isNotNull(joint.getDataQualityPrincipal()) && joint.getDataQualityPrincipal().contains("_")){
			xml +="<fv k=\"QUALITOR\" v=\""+joint.getDataQualityPrincipal().split("_")[1]+"\"/>";
		}
		if(TextUtil.isNotNull(joint.getParties())){
			xml+="<fv k=\"MAINTAINOR\" v=\""+joint.getParties()+"\"/>";
		}
		xml+= "</mo>"
				+ "</mc>"
				+ "</xmldata>"
				+ "";
		try{
			String jsonString = "xml="+URLEncoder.encode(xml, "UTF-8");
			String outIN = RequestUtil.HttpRequest(InterfaceAddr.ADD_RESOURCE, "POST", jsonString);
			
			IrmsPojo obj = new IrmsPojo();
			obj.setInStr(xml);
			obj.setOutStr(outIN);
			obj.setFaceType("addOpticJoint");
			if(outIN.contains("loaded=\"成功\"")){
				obj.setFaceResult("success");
				String resourceId = XmlUtil.getAddRes(outIN,joint.getJointId()+"");
				String upSql= "update job_joint "
						+ " set resNum ='"+resourceId+"'"
						+ " where jointId='"+joint.getJointId()+"'"
						+ "";
				this.jdbcTemplate.execute(upSql);
			}else{
				obj.setFaceResult("error");
			}
			this.irmsInterService.addInterLog(obj);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 增加光缆段
	 * @param cable
	 */
	public void addCableSeg(CableInfoBean cable){
		try{
			Map<String, String> countyMap= this.irmsInterService.getCityCountry(cable.getRegion());
			Map<String, String> start = this.getObjmsg(cable.getStartDeviceType(), cable.getStartDeviceId());
			Map<String, String> end = this.getObjmsg(cable.getEndDeviceType(), cable.getEndDeviceId());
			String sysId = this.getSysId(countyMap.get("cityId"), countyMap.get("countyId"), this.irmsInterService.getKeyStr(cable.getCablename()));
			String xml ="<xmldata mode=\"FibersegAddMode\">"
					+ "<mc type=\"guanglanduan\">"
					+ "<mo group=\"1\" ax=\""+start.get("lon")+"\" ay=\""+start.get("lat")+"\" zx=\""+end.get("lon")+"\" zy=\""+end.get("lat")+"\">"
					+ "<fv k=\"A_ROOM_ID\" v=\"\"/>"
					+ "<fv k=\"Z_ROOM_ID\" v=\"\"/>"
					+ "<fv k=\"CREATOR\" v=\"yc\"/>"
					+ "<fv k=\"FIBER_TYPE\" v=\"1\"/>"
					+ "<fv k=\"ZH_LABEL\" v=\""+cable.getCablename()+"\"/>"
					+ "<fv k=\"CITY_ID\" v=\""+countyMap.get("cityId")+"\"/>"
					+ "<fv k=\"SERVICE_LEVEL\" v=\"5\"/>"
					+ "<fv k=\"COUNTY_ID\" v=\""+countyMap.get("countyId")+"\"/>"
					+ "<fv k=\"INT_ID\" v=\"new-"+cable.getCableid()+"\"/>"
					+ "<fv k=\"Z_OBJECT_ID\" v=\""+end.get("objId")+"\"/>"
					+ "<fv k=\"A_OBJECT_ID\" v=\""+start.get("objId")+"\"/>"
					+ "<fv k=\"Z_OBJECT_TYPE\" v=\""+end.get("objType")+"\"/>"
					+ "<fv k=\"A_OBJECT_TYPE\" v=\""+start.get("objType")+"\"/>"
					+ "<fv k=\"C_LENGTH\" v=\""+cable.getLength()+"\"/>"
					+ "<fv k=\"M_LENGTH\" v=\""+cable.getMaintainLength()+"\"/>"
					+ "<fv k=\"A_EQUIP_ID\" v=\""+start.get("equtId")+"\"/>"
					+ "<fv k=\"RELATED_SYSTEM\" v=\""+sysId+"\"/>"
					+ "<fv k=\"FIBER_NUM\" v=\""+cable.getFibercount()+"\"/>"
					+ "<fv k=\"A_EQUIP_TYPE\" v=\""+start.get("equtType")+"\"/>"
					+ "<fv k=\"Z_EQUIP_ID\" v=\""+end.get("equtId")+"\"/>"
					+ "<fv k=\"STATUS\" v=\"3\"/>"
					+ "<fv k=\"Z_EQUIP_TYPE\" v=\""+end.get("equtType")+"\"/>"
					+ "<fv k=\"OPTI_CABLE_TYPE\" v=\"1\"/>";
			 if(TextUtil.isNotNull(cable.getDataQualityPrincipal()) && cable.getDataQualityPrincipal().contains("_")){
				  xml+="<fv k=\"QUALITOR\" v=\""+cable.getDataQualityPrincipal().split("_")[1]+"\"/>";;
			 }
			 if(TextUtil.isNotNull(cable.getParties())){
				 xml+="<fv k=\"MAINTAINOR\" v=\""+cable.getParties()+"\"/>";
			 }
				xml	+= "</mo>"
					+ "</mc>"
					+ "</xmldata>";
			
			MoveResPojo pojo = new MoveResPojo();
			pojo.setInXml(xml);
			pojo.setAppType("addCable");
			
			String id = this.irmsInterService.addRes(pojo,cable.getCableid()+"");
			if(!(id.equals("error")) && TextUtil.isNotNull(cable.getCableid())){
				String sql = "update job_cable"
						+ " set resNum ='"+id+"'"
						+ " where cableid ='"+cable.getCableid()+"'";
				this.jdbcTemplate.execute(sql);
				this.irmsInterService.upYcapp("cable",cable.getCableid()+"", id+"");
				//修改纤芯
				String irmSql = "select int_id as id, zh_label as name ,fiber_no as no "
						+ " from RES_FIBER_CORE where fibercableseg_id ='"+id+"' order by fiber_no asc ";
				List<Map<String, Object>> irmsList = this.irmsjdbcTemplate.queryForList(irmSql);
				String appSql = "select id,zhLabel,alias,resNum"
						+ " from job_fiber where cableId ='"+cable.getCableid()+"' order by alias asc ";
				List<Map<String, Object>> appList = this.jdbcTemplate.queryForList(appSql);
				for(int i=0;i<appList.size();i++){
					Map<String, Object> appMap = appList.get(i);
					if(irmsList.get(i) != null){
						Map<String, Object> irmsMap = irmsList.get(i);
						appMap.put("zhLabel", irmsMap.get("name"));
						appMap.put("resNum", irmsMap.get("id"));
					}
				}
				this.beatchUpSql(appList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	void beatchUpSql(final List<Map<String, Object>> list){
		String upSql = "update job_fiber"
				+ " set zhLabel =? ,resNum =? "
				+ " where id =?";
		this.jdbcTemplate.batchUpdate(upSql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Map<String, Object> map = list.get(i);
				ps.setString(1, map.get("zhLabel")+"");
				ps.setString(2, map.get("resNum")+"");
				ps.setString(3, map.get("id")+"");
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
	}
	
	/**
	 * 得到系统id
	 * @param map
	 * @return
	 */
	String getSysId(String city,String county,String key){
		String sysId="";
		String sql ="select int_id as SYSID from RES_OPTICSYSTEM where "
				+ " city_id ='"+city+"' and county_id ='"+county+"'"
				+ " and zh_label like '%"+key+"%'";
		List<Map<String, Object>> list = this.irmsjdbcTemplate.queryForList(sql);
		if(TextUtil.isNull(list)){
			sql = "select int_id from RES_OPTICSYSTEM where "
					+ " city_id ='"+city+"' and county_id ='"+county+"'";
			list = this.irmsjdbcTemplate.queryForList(sql);
		}
		if(TextUtil.isNull(list)){
			sysId ="81888669";
		}else{
			Map< String, Object> result = list.get(0);
			sysId = result.get("SYSID")+"";
		}
		return sysId;
	}
	
	
	
	/**
	 * 得到数据map
	 * @param type
	 * @param id
	 * @return
	 */
	Map<String, String> getObjmsg(Integer type,String id){
		Map<String, String> map = new HashMap<String, String>();
		String sql ="";
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if(type.equals(1)){
			//光交箱
			String appSql = ""
					+ " select resNum as equtId,'"+getPropert.getValueByKey("opticTran")+"' as equtType,"
					+ " LON as lon,LAT as lat"
					+ " from job_equtinfo where etype ='3' and EID='"+id+"'"
					+ "";
			Map<String, Object> appMap = this.jdbcTemplate.queryForMap(appSql);
			if(appMap != null){
				sql  = "select structure_type as \"objType\",structure_id as \"objId\","
						+ " "+appMap.get("equtId")+" as \"equtId\", "+appMap.get("equtType")+" as \"equtType\","
						+ " "+appMap.get("lon")+" as \"lon\", "+appMap.get("lat")+" as \"lat\" "
						+ " from RES_OPTI_TRAN_BOX where int_id ='"+appMap.get("equtId")+"'";
				list = this.irmsjdbcTemplate.queryForList(sql);
			}
		}else if(type.equals(2)){
			//光缆接头盒
			sql =" select t.resNum as equtId,'"+getPropert.getValueByKey("opticJoint")+"' as equtType,"
				+ " case t.addrType"
				+ " when 2 then (select resNum as lon from poleinfo where poleId = t.poleId )"
				+ " when 1 then (select resNum as lon from wellinfo where wellId = t.wellId )"
				+ " when 3 then (select resNum as lon from stoneinfo where stoneId = t.stoneId)"
				+ " end  as objId,"
				+ " case t.addrType"
				+ " when 2 then '"+getPropert.getValueByKey("poleType")+"'"
				+ " when 1 then '"+getPropert.getValueByKey("wellType")+"'"
				+ " when 3 then '"+getPropert.getValueByKey("stoneType")+"'"
				+ " end  as objType,"
				+ " case t.addrType"
				+ " when 2 then (select poleLongitude as lon from poleinfo where poleId = t.poleId )"
				+ " when 1 then (select longitude as lon from wellinfo where wellId = t.wellId )"
				+ " when 3 then (select longitude as lon from stoneinfo where stoneId = t.stoneId)"
				+ " end  as lon,"
				+ " case t.addrType"
				+ " when 2 then (select poleLatitude as lat from poleinfo where poleId = t.poleId )"
				+ " when 1 then (select latitude as lat from wellinfo where wellId = t.wellId )"
				+ " when 3 then (select latitude as lat from stoneinfo where stoneId = t.stoneId)"
				+ " end  as lat"
				+ " from job_joint t where t.jointId ='"+id+"'"; 
			list = this.jdbcTemplate.queryForList(sql);
		}else if(type.equals(3)){
			//站点
			sql = "select resNum as objId, '"+getPropert.getValueByKey("siteType")+"' as objType,"
					+ " resNum as equtId,'"+getPropert.getValueByKey("siteType")+"' as equtType,"
					+ " lon as lon,lat as lat "
					+ " from job_stationbase where stationBaseId ='"+id+"'"; 
			list = this.jdbcTemplate.queryForList(sql);
		}else if(type.equals(4)){
			//光终端盒
			String appSql = " select t.resNum as equtId,'"+getPropert.getValueByKey("opticTerm")+"' as equtType,"
					+ " t.longitude as lon,t.latitude as lat "
					+ " from optical_terminal t where t.id = '"+id+"'";
			Map<String, Object> appMap = this.jdbcTemplate.queryForMap(appSql);
			if(appMap != null){
				sql  = "select structure_type as \"objType\",structure_id as \"objId\","
						+ " "+appMap.get("equtId")+" as \"equtId\", "+appMap.get("equtType")+" as \"equtType\","
						+ " "+appMap.get("lon")+" as \"lon\", "+appMap.get("lat")+" as \"lat\" "
						+ " from RES_OPTI_END_BOX where int_id ='"+appMap.get("equtId")+"'";
				list = this.irmsjdbcTemplate.queryForList(sql);
			}
		}
		
		if(TextUtil.isNotNull(list)){
			Map<String, Object> result = list.get(0);
			map.put("objId", result.get("objId")+"");
			map.put("objType", result.get("objType")+"");
			map.put("equtId", result.get("equtId")+"");
			map.put("equtType", result.get("equtType")+"");
			map.put("lon", result.get("lon")+"");
			map.put("lat", result.get("lat")+"");
		}
		return map;
		
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
	 * 得到分光比
	 * @param scale
	 * @return
	 */
	String getScale(String scale){
		String result ="";
		if(scale.equals("0")){
			result = "未知";
		}else if(scale.equals("1")){
			result = "1:2";
		}else if(scale.equals("2")){
			result= "1:4";
		}else if(scale.equals("3")){
			result = "1:8";
		}else if(scale.equals("4")){
			result ="1:16";
		}else if(scale.equals("5")){
			result = "1:32";
		}else if(scale.equals("6")){
			result = "1:64";
		}
		return result;
	}
	
	/**
	 * 得到维护人的相关信息
	 * @param userName
	 * @return
	 */
	public List<Map<String, Object>> getMaintainList(String userName){
		String sql = "select g.groupName as daiweiZu,g.compRes as maintainArea"
				+ " from sys_user u,maintainGroup g"
				+ " where u.groupId = g.id and u.username ='"+userName+"'"
				+ " union all"
				+ " select g.groupName as daiweiZu,g.compRes as maintainArea"
				+ " from sys_user u,maintainGroup g"
				+ " where u.groupId = g.id and u.realname ='"+userName+"'";
		return this.jdbcTemplate.queryForList(sql);
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
