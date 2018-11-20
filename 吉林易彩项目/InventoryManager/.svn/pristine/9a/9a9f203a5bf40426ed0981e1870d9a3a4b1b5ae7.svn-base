package interfaces.irmsInterface.interfaces.service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import manage.generator.pojo.StationBaseInfoBean;

import org.springframework.jdbc.core.JdbcTemplate;

import interfaces.irmsInterface.interfaces.pojo.IrmsPojo;
import interfaces.irmsInterface.interfaces.pojo.MoveResPojo;
import interfaces.irmsInterface.interfaces.service.impl.IirmsInterService;
import interfaces.irmsInterface.utils.InterfaceAddr;
import interfaces.irmsInterface.utils.RequestUtil;
import interfaces.irmsInterface.utils.XmlUtil;
import interfaces.pdainterface.lineSystem.pojo.LineSystemInfo;
import base.database.DataBase;
import base.util.ResUtil;
import base.util.TextUtil;

public class IrmsInterService extends DataBase implements IirmsInterService{

	//app数据库连接
	private JdbcTemplate jdbcTemplate;
	//irms
	private JdbcTemplate irmsjdbcTemplate;
	
	
	/**
	 * 添加接口记录
	 * @param obj
	 * @return
	 */
	public int addInterLog(IrmsPojo obj){
		
		String sql = "insert into irms_interface(inStr,outStr,faceType,faceTime,"
				+ " faceResult,creater,faceCnType,imId,imEnType,imCnType,imName,"
				+ " resId,city,county)"
				+ "values ("
				+ " '"+obj.getInStr()+"', "
				+ " '"+obj.getOutStr()+"', "
				+ " '"+obj.getFaceType()+"', "
				+ " now(), "
				+ " '"+obj.getFaceResult()+"',"
				+" '"+obj.getCreater()+"',"
				+ " '"+obj.getFaceCnType()+"',"
				+ " '"+obj.getImId()+"',"
				+ " '"+obj.getImEnType()+"',"
				+ " '"+obj.getImCnType()+"',"
				+ " '"+obj.getImName()+"',"
				+ " '"+obj.getResId()+"',"
				+ " '"+obj.getCity()+"',"
				+ " '"+obj.getCounty()+"'"
				+ ")";
		this.jdbcTemplate.execute(sql);
		return 1;
	}
	
	/**
	 * 得到分页数据
	 * @param obj
	 * @return
	 */
	public List<IrmsPojo> getIrmsGrid(IrmsPojo obj){
		List<IrmsPojo> list = this.getObjects("interIrms.getInterIrms", obj);
		return list;
	}
	
	
	/**
	 * 得到具体对象
	 * @param obj
	 * @return
	 */
	public IrmsPojo getIrmsObj(IrmsPojo obj){
		IrmsPojo pojo = (IrmsPojo) this.getObject("interIrms.getInterIrms", obj);
		return pojo;
	}
	
	
	/**
	 * 得到分页总数
	 * @param obj
	 * @return
	 */
	public int getIrmsCount(IrmsPojo obj){
		return getCount("interIrms.getInterCount", obj);
	}
	
	/**
	 * 得到地市区县的代号
	 * @param country
	 * @return
	 */
	public Map<String, String> getCityCountry(String country){
		Map<String, String> map= new HashMap<String, String>();
		String towerName ="";
		if(country.contains("_")){
			String[] counts = country.split("_");
			towerName=counts[counts.length-1];
		}else if(country.startsWith("中国北京北京")){
			towerName = country.replace("中国北京北京", "");
		}
		String sql = "select resNum as countyId,resCity as cityId from rms_county"
				+ " where towerName ='"+towerName+"'";
		Map<String, Object> result = this.jdbcTemplate.queryForMap(sql);
		if(result != null){
			map.put("countyId", result.get("countyId")+"");
			map.put("cityId", result.get("cityId")+"");
		}
		return map;
	}
	
	/**
	 * xiugai ziyuan shuju 
	 * @param pojo
	 * @param appId
	 * @return
	 */
	public String upRes(MoveResPojo pojo,String appId){
		String result ="";
		try{
			String jsonString = "xml="+URLEncoder.encode(pojo.getInXml(), "UTF-8")+"&useraccount=yc";
			String outIN = RequestUtil.HttpRequest(InterfaceAddr.MOVE_RESOURCE, "POST", jsonString);
			IrmsPojo obj = new IrmsPojo();
			obj.setInStr(pojo.getInXml());
			obj.setOutStr(outIN);
			obj.setFaceType(pojo.getAppType());
			obj.setFaceCnType(pojo.getAppCnType());
			obj.setImId(pojo.getImId());
			obj.setImEnType(pojo.getImEnType());
			obj.setImCnType(pojo.getImCnType());
			obj.setImName(pojo.getResName());
			obj.setCreater(pojo.getMaintainor());
			if(TextUtil.isNotNull(pojo.getRegion()) && pojo.getRegion().contains("_")){
				String[] regions = pojo.getRegion().split("_");
				obj.setCity(regions[0]+"_"+regions[regions.length-1]);
				obj.setCounty(regions[regions.length-1]);
			}
			if(outIN.contains("loaded=\"成功\"")){
				obj.setFaceResult("success");
				result = "success";
				
			}else{
				result ="error";
				obj.setFaceResult("error");
			}
			this.addInterLog(obj);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 增加资源点
	 * @param pojo
	 * @return
	 */
	public String addRes(MoveResPojo pojo,String appId){
		String result ="";
		try{
			String jsonString = "xml="+URLEncoder.encode(pojo.getInXml(), "UTF-8")+"&useraccount=yc";
			String outIN = RequestUtil.HttpRequest(InterfaceAddr.ADD_RESOURCE, "POST", jsonString);
			//String outIN="<?xml version=\"1.0\" encoding=\"UTF-8\"?><check loaded=\"错误\"><errors><error mc=\"管道段\" type=\"error\" id=\"1510848164\" name=\"北京市西城区珠市口西大街29号北侧租用中国联通管道_北2号井-北京市西城区珠市口西大街29号北侧租用中国联通管道_北3号井\" info=\"两个承载点之间只能建一条承载段\"/></errors></check>";
			IrmsPojo obj = new IrmsPojo();
			obj.setInStr(pojo.getInXml());
			obj.setOutStr(outIN);
			obj.setFaceType(pojo.getAppType());
			obj.setFaceCnType(pojo.getAppCnType());
			obj.setImId(pojo.getImId());
			obj.setImEnType(pojo.getImEnType());
			obj.setImCnType(pojo.getImCnType());
			obj.setImName(pojo.getResName());
			obj.setCreater(pojo.getMaintainor());
			if(TextUtil.isNotNull(pojo.getRegion()) && pojo.getRegion().contains("_")){
				String[] regions = pojo.getRegion().split("_");
				obj.setCity(regions[0]+"_"+regions[regions.length-1]);
				obj.setCounty(regions[regions.length-1]);
			}
			if(outIN.contains("loaded=\"成功\"")){
				obj.setFaceResult("success");
				result = XmlUtil.getAddRes(outIN,appId);
				obj.setResId(result);
			}else{
				//解析数据获取
				if(TextUtil.isNotNull(outIN) && outIN.contains("errors")) {
					result = XmlUtil.getErrorId(outIN);
					if(result.contains("error_")) {
						obj.setFaceResult("error");
					}else {
						obj.setFaceResult("success");
						obj.setResId(result);
					}
				}else {
					obj.setFaceResult("error");
				}
			}
			this.addInterLog(obj);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 移动光电设备
	 * @param movePojo
	 */
	public String moveOptical(MoveResPojo movePojo){
		String result ="";
		try{
			if(TextUtil.isNotNull(movePojo.getResId())){
				String parentid = movePojo.getResId();
				if(TextUtil.isNotNull(movePojo.getParentid())) {
					parentid = movePojo.getParentid();
				}else {
					parentid = (Long.parseLong(parentid)+1)+"";
				}
				String xml="<xmldata mode=\"OptoeleEditMode\">"
						+ "<mc type=\""+movePojo.getResType()+"\">"
						+ "<mo group=\"1\">"
						+ "<fv k=\"STRUCTURE_TYPE\" v=\"9115\"/>"
						+ "<fv k=\"STRUCTURE_ID\" v=\""+parentid+"\"/>"
						+ "<fv k=\"INT_ID\" v=\""+movePojo.getResId()+"\"/>"
						+ "<fv k=\"ZH_LABEL\" v=\""+movePojo.getResName()+"\"/>"
						+ "<fv k=\"LONGITUDE\" v=\""+movePojo.getLongitude()+"\"/>"
						+ "<fv k=\"LATITUDE\" v=\""+movePojo.getLatitude()+"\"/>"
						+ "<fv k=\"STATUS\" v=\"3\"/>"
						+ "<fv k=\"CITY_ID\" v=\""+movePojo.getCityId()+"\"/>"
						+ "<fv k=\"COUNTY_ID\" v=\""+movePojo.getCountyId()+"\"/>";
				if(TextUtil.isNotNull(movePojo.getExtendXml())){
					xml +=movePojo.getExtendXml();
				}
				//数据质量维护人
				if(TextUtil.isNotNull(movePojo.getQualitor())){
					xml+="<fv k=\"QUALITOR\" v=\""+movePojo.getQualitor()+"\"/>";
				}
				//一线维护人
				if(TextUtil.isNotNull(movePojo.getMaintainor())){
					xml+="<fv k=\"MAINTAINOR\" v=\""+movePojo.getMaintainor()+"\"/>";
				}
				xml+= "<fv k=\"INT_ID\" v=\""+movePojo.getResId()+"\"/>"
						+ "</mo>"
						+ "</mc>"
						+ "<mc type=\"ziyuandian\">"
						+ "<mo group=\"1\">"
						+ "<fv k=\"LATITUDE\" v=\""+movePojo.getLatitude()+"\"/>"
						+ "<fv k=\"LONGITUDE\" v=\""+movePojo.getLongitude()+"\"/>"
						+ "<fv k=\"INT_ID\" v=\""+parentid+"\"/>"
						+ "</mo>"
						+ "</mc>"
						+ "</xmldata>"
						+ "";
				String jsonString = "xml="+URLEncoder.encode(xml, "UTF-8")+"&useraccount=yc";
				String outIN = RequestUtil.HttpRequest(InterfaceAddr.MOVE_RESOURCE, "POST", jsonString);
				IrmsPojo obj = new IrmsPojo();
				obj.setInStr(xml);
				obj.setOutStr(outIN);
				obj.setFaceType(movePojo.getAppType());
				obj.setImId(movePojo.getImId());
				obj.setImEnType(movePojo.getImEnType());
				obj.setImCnType(movePojo.getImCnType());
				obj.setImName(movePojo.getResName());
				obj.setCreater(movePojo.getMaintainor());
				obj.setResId(movePojo.getResId());
				obj.setFaceCnType(movePojo.getAppCnType());
				if(TextUtil.isNotNull(movePojo.getRegion()) && movePojo.getRegion().contains("_")){
					String[] regions = movePojo.getRegion().split("_");
					obj.setCity(regions[0]+"_"+regions[regions.length-1]);
					obj.setCounty(regions[regions.length-1]);
				}
				if(outIN.contains("loaded=\"成功\"")){
					result ="success";
					obj.setFaceResult("success");
				}else{
					result ="error";
					obj.setFaceResult("error");
				}
				this.addInterLog(obj);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 移动站点
	 * @param movePojo
	 * @param station
	 */
	public void moveSite(MoveResPojo movePojo,StationBaseInfoBean station){
		try{
			if(TextUtil.isNotNull(movePojo.getResId())){
				String xml ="<data model=\"site\">"
						+ "<attr key=\"city_id\" value=\""+movePojo.getCityId()+"\"/>"
						+ "<attr key=\"county_id\" value=\""+movePojo.getCountyId()+"\"/>"
						+ "<attr key=\"zh_label\" value=\""+station.getStationName()+"\"/>"
						+ "<attr key=\"location\" value=\""+station.getStationAddr()+"\"/>"
						+ "<attr key=\"ownership\" value=\"1\"/>"
						+ "<attr key=\"status\" value=\"3\"/>"
						+ "<attr key=\"longitude\" value=\""+station.getLon()+"\"/>"
						+ "<attr key=\"latitude\" value=\""+station.getLat()+"\"/>"
						+ "<attr key=\"int_id\" value=\""+station.getResNum()+"\"/>"
						+ "</data>";
				String jsonString = "params="+URLEncoder.encode(xml, "UTF-8")+"&useraccount=yc";
				String outIN = RequestUtil.HttpRequest(InterfaceAddr.MOVE_SITE, "POST", jsonString);
				IrmsPojo obj = new IrmsPojo();
				obj.setInStr(xml);
				obj.setOutStr(outIN);
				obj.setFaceType(movePojo.getAppType());
				obj.setImId(movePojo.getImId());
				obj.setImEnType(movePojo.getImEnType());
				obj.setImCnType(movePojo.getImCnType());
				obj.setImName(movePojo.getResName());
				obj.setCreater(movePojo.getMaintainor());
				obj.setResId(movePojo.getResId());
				obj.setFaceCnType(movePojo.getAppCnType());
				if(TextUtil.isNotNull(movePojo.getRegion()) && movePojo.getRegion().contains("_")){
					String[] regions = movePojo.getRegion().split("_");
					obj.setCity(regions[0]+"_"+regions[regions.length-1]);
					obj.setCounty(regions[regions.length-1]);
				}
				if(outIN.contains("success=\"true\"")){
					obj.setFaceResult("success");
				}else{
					obj.setFaceResult("error");
				}
				this.addInterLog(obj);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 移动资源
	 * @param movePojo
	 */
	public void moveRes(MoveResPojo movePojo){
		try{
			if(TextUtil.isNotNull(movePojo.getResId())){
				String xml="<xmldata mode=\"SinglePointEditMode\">"
						+ "<mc type=\""+movePojo.getResType()+"\">"
						+ "<mo group=\"1\">"
						+ "<fv k=\"ZH_LABEL\" v=\""+movePojo.getResName()+"\"/>"
						+ "<fv k=\"LONGITUDE\" v=\""+movePojo.getLongitude()+"\"/>"
						+ "<fv k=\"LATITUDE\" v=\""+movePojo.getLatitude()+"\"/>"
						+ "<fv k=\"STATUS\" v=\"3\"/>"
						+ "<fv k=\"CITY_ID\" v=\""+movePojo.getCityId()+"\"/>"
						+ "<fv k=\"COUNTY_ID\" v=\""+movePojo.getCountyId()+"\"/>";
				//数据质量维护人
				if(TextUtil.isNotNull(movePojo.getQualitor())){
					xml+="<fv k=\"QUALITOR\" v=\""+movePojo.getQualitor()+"\"/>";
				}
				if(TextUtil.isNotNull(movePojo.getExtendXml())){
					xml +=movePojo.getExtendXml();
				}
				//一线维护人
				if(TextUtil.isNotNull(movePojo.getMaintainor())){
					xml+="<fv k=\"MAINTAINOR\" v=\""+movePojo.getMaintainor()+"\"/>";
				}
				xml+= "<fv k=\"INT_ID\" v=\""+movePojo.getResId()+"\"/>"
						+ "</mo>"
						+ "</mc>"
						+ "</xmldata>"
						+ "";
				String jsonString = "xml="+URLEncoder.encode(xml, "UTF-8")+"&useraccount=yc";
				String outIN = RequestUtil.HttpRequest(InterfaceAddr.MOVE_RESOURCE, "POST", jsonString);
				IrmsPojo obj = new IrmsPojo();
				obj.setInStr(xml);
				obj.setOutStr(outIN);
				obj.setFaceType(movePojo.getAppType());
				obj.setImId(movePojo.getImId());
				obj.setImEnType(movePojo.getImEnType());
				obj.setImCnType(movePojo.getImCnType());
				obj.setImName(movePojo.getResName());
				obj.setCreater(movePojo.getMaintainor());
				obj.setResId(movePojo.getResId());
				obj.setFaceCnType(movePojo.getAppCnType());
				if(TextUtil.isNotNull(movePojo.getRegion()) && movePojo.getRegion().contains("_")){
					String[] regions = movePojo.getRegion().split("_");
					obj.setCity(regions[0]+"_"+regions[regions.length-1]);
					obj.setCounty(regions[regions.length-1]);
				}
				if(outIN.contains("loaded=\"成功\"")){
					obj.setFaceResult("success");
				}else{
					obj.setFaceResult("error");
				}
				this.addInterLog(obj);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 移动资源
	 * @param movePojo
	 */
	public void delRes(MoveResPojo movePojo){
		try{
			if(TextUtil.isNotNull(movePojo.getResId())){
				String xml="<xmldata mode=\"SinglePointEditMode\">"
						+ "<mc type=\""+movePojo.getResType()+"\">"
						+ "<mo group=\"1\">"
						+ "<fv k=\"ZH_LABEL\" v=\""+movePojo.getResName()+"\"/>"
						+ "<fv k=\"LONGITUDE\" v=\""+movePojo.getLongitude()+"\"/>"
						+ "<fv k=\"LATITUDE\" v=\""+movePojo.getLatitude()+"\"/>"
						+ "<fv k=\"STATUS\" v=\"0\"/>"
						+ "<fv k=\"CITY_ID\" v=\""+movePojo.getCityId()+"\"/>"
						+ "<fv k=\"COUNTY_ID\" v=\""+movePojo.getCountyId()+"\"/>";
				//数据质量维护人
				if(TextUtil.isNotNull(movePojo.getQualitor())){
					xml+="<fv k=\"QUALITOR\" v=\""+movePojo.getQualitor()+"\"/>";
				}
				if(TextUtil.isNotNull(movePojo.getExtendXml())){
					xml +=movePojo.getExtendXml();
				}
				//一线维护人
				if(TextUtil.isNotNull(movePojo.getMaintainor())){
					xml+="<fv k=\"MAINTAINOR\" v=\""+movePojo.getMaintainor()+"\"/>";
				}
				xml+= "<fv k=\"INT_ID\" v=\""+movePojo.getResId()+"\"/>"
						+ "</mo>"
						+ "</mc>"
						+ "</xmldata>"
						+ "";
				String jsonString = "xml="+URLEncoder.encode(xml, "UTF-8")+"&useraccount=yc";
				String outIN = RequestUtil.HttpRequest(InterfaceAddr.MOVE_RESOURCE, "POST", jsonString);
				IrmsPojo obj = new IrmsPojo();
				obj.setInStr(xml);
				obj.setOutStr(outIN);
				obj.setFaceType(movePojo.getAppType());
				obj.setImId(movePojo.getImId());
				obj.setImEnType(movePojo.getImEnType());
				obj.setImCnType(movePojo.getImCnType());
				obj.setImName(movePojo.getResName());
				obj.setCreater(movePojo.getMaintainor());
				obj.setResId(movePojo.getResId());
				obj.setFaceCnType(movePojo.getAppCnType());
				if(TextUtil.isNotNull(movePojo.getRegion()) && movePojo.getRegion().contains("_")){
					String[] regions = movePojo.getRegion().split("_");
					obj.setCity(regions[0]+"_"+regions[regions.length-1]);
					obj.setCounty(regions[regions.length-1]);
				}
				if(outIN.contains("loaded=\"成功\"")){
					obj.setFaceResult("success");
				}else{
					obj.setFaceResult("error");
				}
				this.addInterLog(obj);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 增加一个系统
	 * @param zhLabel
	 * @param cityId
	 * @param countyId
	 * @param lineType
	 * @return
	 */
	public String addSys(String zhLabel,String cityId,String countyId,String lineType,LineSystemInfo line){
		String resId = "";
		try{
			String mode = "add";
			String int_id = "new-"+line.getId();
			if(TextUtil.isNotNull(line.getResNum())) {
				mode ="update";
				int_id = line.getResNum();
			}
			String mLength = "1.0";
			if(TextUtil.isNotNull(line.getMaintainLength())) {
				mLength = line.getMaintainLength();
			}
			String xml="<response mode=\""+mode+"\">"
					+ "<mc type=\"xitong\">"
					+ "<mo group=\"1\">"
					+ "<fv k=\"LINESEG_TYPE\" v=\""+getPropert.getValueByKey(lineType)+"\"/>"
					+ "<fv k=\"ZH_LABEL\" v=\""+zhLabel+"\"/>"
					+ "<fv k=\"INT_ID\" v=\""+int_id+"\"/>"
					+ "<fv k=\"SYSTEM_LEVEL\" v=\"3\"/>"
					+ "<fv k=\"OWNERSHIP\" v=\"1\"/>"
					+ "<fv k=\"COUNTY_ID\" v=\""+countyId+"\"/>"
					+ "<fv k=\"CITY_ID\" v=\""+cityId+"\"/>"
					+ "<fv k=\"M_LENGTH\" v=\""+mLength+"\"/>"
					+ "<fv k=\"STATUS\" v=\"3\"/>"
					+ "<fv k=\"BUILDER\" v=\"yc\"/>"
					+ "<fv k=\"DESIGNER\" v=\"yc\"/>"
					+ "<fv k=\"IS_JIAOWEI\" v=\"1\"/>"
					+ "</mo>"
					+ "</mc>"
					+ "</response>";
			String jsonString = "xml="+URLEncoder.encode(xml, "UTF-8");
			String outIN = RequestUtil.HttpRequest(InterfaceAddr.ADD_SYS, "POST", jsonString);
			
			IrmsPojo obj = new IrmsPojo();
			obj.setInStr(xml);
			obj.setOutStr(outIN);
			if(mode.equals("add")) {
				obj.setFaceType("addXitong");
			}else {
				obj.setFaceType("updateXitong");
			}
			if(outIN.contains("保存成功")){
				resId = XmlUtil.getAddRes(outIN,line.getId()+"");
				obj.setFaceResult("success");
			}else{
				obj.setFaceResult("error");
			}
			this.addInterLog(obj);
		}catch(Exception e){
			e.printStackTrace();
		}
		return resId;
	}
	
	
	
	/**
	 * 修改资源
	 * @param map
	 * @return
	 */
	public String getUpLineStr(Map<String, String> map){
		String xml ="";
		
		
		return xml;
	}
	
	/**
	 * 更新段信息
	 * @param map
	 * @return
	 */
	public String upLineInstr(Map<String, String> map){
		String xml ="<xmldata mode=\"PipeLineEditMode\">"
				+ "<mc type=\""+map.get("resType")+"\" >"
				+ "<mo group=\"1\"  ax=\""+map.get("startLon")+"\" ay=\""+map.get("startLat")+"\" zx=\""+map.get("endLon")+"\" zy=\""+map.get("endLat")+"\">"
				+ "<fv k=\"OWNERSHIP\" v=\""+ResUtil.getOwnerShip(map.get("ownerShip"))+"\"/>"
				+ "<fv k=\"RES_OWNER\" v=\""+ResUtil.getResOwner(map.get("resOwner"))+"\"/>"
				+ "<fv k=\"COUNTY_ID\" v=\""+map.get("county")+"\"/>"
				+"<fv k=\"RELATED_SYSTEM\" v=\""+map.get("sysId")+"\"/>"
				/*+ "<fv k=\"SYSTEM_LEVEL\" v=\"\"/>"
				+"<fv k=\"RELATED_SYSTEM\" v=\""+map.get("sysId")+"\"/>"
				+ "<fv k=\"RELATED_BRANCH\" v=\""+(Long.parseLong(map.get("sysId"))+1)+"\"/>"
				+ "<fv k=\"PROJECT_ID\" v=\"-1\"/>"
				+ "<fv k=\"IS_JIAOWEI\" v=\"1\"/>"
				+ "<fv k=\"PROJECT_ID\" v=\"-1\"/>"*/
				+ "<fv k=\"INT_ID\" v=\""+map.get("lineId")+"\"/>"
				+ "<fv k=\"STATUS\" v=\"3\"/>"
				+ "<fv k=\"ZH_LABEL\" v=\""+map.get("lineName")+"\"/>"
				+ "<fv k=\"CITY_ID\" v=\""+map.get("city")+"\"/>"
				+ "<fv k=\"C_LENGTH\" v=\""+map.get("figueLength")+"\"/>"
				+ "<fv k=\"M_LENGTH\" v=\""+map.get("lineLength")+"\"/>"
				+ "<fv k=\"A_OBJECT_TYPE\" v=\""+map.get("startType")+"\"/>"
				+ "<fv k=\"A_OBJECT_ID\" v=\""+map.get("start")+"\"/>"
				+ "<fv k=\"Z_OBJECT_TYPE\" v=\""+map.get("endType")+"\"/>"
				+ "<fv k=\"Z_OBJECT_ID\" v=\""+map.get("end")+"\"/>"
				+ "<fv k=\"SYSTEM_LEVEL\" v=\"5\"/>"
				+ "<fv k=\"PURPOSE\" v=\"0\"/>"
				+ "<fv k=\"MAINT_MODE\" v=\"1\"/>"
				+ "";
		if(map.get("qualitor")!=null){
			xml +="<fv k=\"QUALITOR\" v=\""+map.get("qualitor")+"\"/>";
		}
		if(map.get("maintainor")!=null){
			xml +="<fv k=\"MAINTAINOR\" v=\""+map.get("maintainor")+"\"/>";
		}
		if(map.get("extendXml")!=null){
			xml += map.get("extendXml");
		}
		xml+="</mo>"
		   + "</mc>"
		   + "</xmldata>";
		return xml;
	}
	
	/**
	 * 得到新增输入段
	 * @param map
	 * @return
	 */
	public String getLineInStr(Map< String, String> map){
		String mLength =map.get("lineLength")+"";
		String cLength = map.get("figueLength")+"";
		if(TextUtil.isNull(mLength) || mLength.startsWith(".0") || mLength.startsWith("0.0")) {
			mLength = "1.0";
			cLength ="1.0";
		}
		String xml ="<xmldata mode=\"PipeLineAddMode\">"
				+ "<mc type=\""+map.get("resType")+"\" >"
				+ "<mo group=\"1\"  ax=\""+map.get("startLon")+"\" ay=\""+map.get("startLat")+"\" zx=\""+map.get("endLon")+"\" zy=\""+map.get("endLat")+"\">"
				+ "<fv k=\"OWNERSHIP\" v=\""+ResUtil.getOwnerShip(map.get("ownerShip"))+"\"/>"
				+ "<fv k=\"RES_OWNER\" v=\""+ResUtil.getResOwner(map.get("resOwner"))+"\"/>"
				+ "<fv k=\"COUNTY_ID\" v=\""+map.get("county")+"\"/>"
				+ "<fv k=\"RELATED_BRANCH\" v=\"new-17047600\"/>"
				+ "<fv k=\"SYSTEM_LEVEL\" v=\"\"/>"
				+"<fv k=\"RELATED_SYSTEM\" v=\""+map.get("sysId")+"\"/>"
				+ "<fv k=\"PROJECT_ID\" v=\"-1\"/>"
				+ "<fv k=\"INT_ID\" v=\"new-"+map.get("lineId")+"\"/>"
				+ "<fv k=\"STATUS\" v=\"3\"/>"
				+ "<fv k=\"ZH_LABEL\" v=\""+map.get("lineName")+"\"/>"
				+ "<fv k=\"CITY_ID\" v=\""+map.get("city")+"\"/>"
				+ "<fv k=\"C_LENGTH\" v=\""+cLength+"\"/>"
				+ "<fv k=\"M_LENGTH\" v=\""+mLength+"\"/>"
				+ "<fv k=\"A_OBJECT_TYPE\" v=\""+map.get("startType")+"\"/>"
				+ "<fv k=\"A_OBJECT_ID\" v=\""+map.get("start")+"\"/>"
				+ "<fv k=\"Z_OBJECT_TYPE\" v=\""+map.get("endType")+"\"/>"
				+ "<fv k=\"Z_OBJECT_ID\" v=\""+map.get("end")+"\"/>"
				+ "<fv k=\"EXAMINESTATUS\" v=\"2\"/>";
			if(map.get("qualitor")!=null){
				xml +="<fv k=\"QUALITOR\" v=\""+map.get("qualitor")+"\"/>";
			}
			if(map.get("maintainor")!=null){
				xml +="<fv k=\"MAINTAINOR\" v=\""+map.get("maintainor")+"\"/>"
					+ "<fv k=\"CREATOR\" v=\""+map.get("maintainor")+"\"/>";
			}
			if(map.get("extendXml")!=null){
				xml += map.get("extendXml");
			}
			xml+= "</mo>"
				+ "</mc>"
				/*+ "<mc type=\"zixitong\">"
				+ "<mo group=\"1\">"
				+ "<fv k=\"COUNTY_ID\" v=\""+map.get("county")+"\"/>"
				+ "<fv k=\"LINESEG_TYPE\" v=\""+getPropert.getValueByKey(map.get("type"))+"\"/>"
				+ "<fv k=\"STATUS\" v=\"3\"/>"
				+ "<fv k=\"RELATED_SYSTEM\" v=\""+map.get("sysId")+"\"/>"
				+ "<fv k=\"INT_ID\" v=\"new-61416662\"/>"
				+ "<fv k=\"CITY_ID\" v=\""+map.get("city")+"\"/>"
				+ "<fv k=\"ZH_LABEL\" v=\""+map.get("lineName")+"\"/>"
				+ "<fv k=\"PRO_TASK_ID\" v=\"\"/>"
				+ "</mo>"
				+ "</mc>"*/
				+ "</xmldata>"
				+ "";
		
		return xml;
	}
	
	/**
	 * 更新综资ycid
	 * @param type
	 * @param appId
	 * @param resId
	 */
	public void upYcapp(String type,String appId,String resId){
		String table ="";
	    if(type.equals("pole")){
	    	table ="RES_POLE";
	    }else if(type.equals("well")){
	    	table ="RES_STAFF_WELL";
	    }else if(type.equals("stone")){
	    	table="RES_MARKSTONE";
	    }else if(type.equals("pipeLine")){
	    	table="RES_PIPE_SEG";
	    }else if(type.equals("poleLine")){
	    	table="RES_POLE_ROAD_SEG";
	    }else if(type.equals("buried")){
	    	table ="RES_DIRE_BURY_SEG";
	    }else if(type.equals("support")){
	    	table ="RES_SUPP_POINT";
	    }else if(type.equals("cable")){
	    	table ="RES_OPTI_CAB_SEG";
	    }else if(type.equals("hangwall")){
	    	table ="RES_HANG_WALL_SEG";
	    }else if(type.equals("leadup")){
	    	table ="RES_ONTO";
	    }
	    if(TextUtil.isNotNull(table) && TextUtil.isNotNull(appId) && TextUtil.isNotNull(resId)){
	    	String sql = "update "+table+""
	    			+ ""
	    			+ " set YC_ID ='1'"
	    			+ " where INT_ID ='"+resId+"'";
	    	//暂时屏蔽
	    	this.irmsjdbcTemplate.execute(sql);
	    }
	}
	
	/**
	 * 得到关键字
	 * @param name
	 * @return
	 */
	public String getKeyStr(String name){
		String key ="";
		if(name.contains("路")){
			int addr = name.indexOf("路");
			key = name.substring(0, addr+1);
		}else{
			if(name.length()>5){
				key = name.substring(0, 5);
			}else {
				key =name;
			}
		}
		return key;
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
	
}
