package interfaces.irmsInterface.interfaces.outLine.service;

import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import manage.approval.service.impl.IapprovalTaskService;
import manage.buriedPart.pojo.BuriedPartObj;
import manage.device.pojo.DeviceInfoBean;
import manage.leadup.pojo.HangWallPojo;
import manage.leadup.pojo.LeadupPojo;
import manage.leadup.pojo.SupportPointPojo;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.pipe.pojo.TubeInfoBean;
import manage.pipe.pojo.WellInfoBean;
import manage.poleline.pojo.PoleInfoBean;
import manage.poleline.pojo.PolelineSegmentInfoBean;
import manage.stone.pojo.StoneInfoBean;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import base.database.DataBase;
import base.util.ResUtil;
import base.util.TextUtil;
import interfaces.irmsInterface.interfaces.outLine.pojo.IrmsPoint;
import interfaces.irmsInterface.interfaces.outLine.service.impl.IirmsOutLineService;
import interfaces.irmsInterface.interfaces.pojo.IrmsPojo;
import interfaces.irmsInterface.interfaces.pojo.MoveResPojo;
import interfaces.irmsInterface.interfaces.service.impl.IirmsInterService;
import interfaces.irmsInterface.utils.InterfaceAddr;
import interfaces.irmsInterface.utils.RequestUtil;
import interfaces.irmsInterface.utils.UploadUtil;
import interfaces.pdainterface.lineSystem.pojo.LineSystemInfo;

public class IrmsOutLineService extends DataBase implements IirmsOutLineService{

	//app
	private JdbcTemplate jdbcTemplate;
	//irms
	private JdbcTemplate irmsjdbcTemplate;
	private IirmsInterService irmsInterService;
	private IapprovalTaskService approvalService;
	
	/**
	 * 
	 * @param id
	 * @param resType
	 */
	public void test(String id,String resType){
		System.out.println(id+"============"+resType);
	}
	
	
	/**
	 * 增加资源点信息
	 * @param list
	 * @return
	 */
	public String getPointStr(List<Map<String, Object>> list){
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh24:mm:ss");
		String time = sdf.format(date);
		try{
			List<Map<String, Object>> poleList = new LinkedList();
			List<Map<String, Object>> stoneList = new LinkedList();
			List<Map<String, Object>> wellList = new LinkedList();
			List<Map<String, Object>> hangList = new LinkedList();
			for(Map<String, Object> map : list){
				String resType = map.get("resEnType")+"";
				if(resType.equals("renshoujing")){
					wellList.add(map);
				}
				if(resType.equals("diangan")){
					poleList.add(map);
				}
				if(resType.equals("biaoshi")){
					stoneList.add(map);
				}
				if(resType.equals("chengdian")){
					hangList.add(map);
				}
			}
			if(TextUtil.isNotNull(list)){
				sb.append("<xmldata mode=\"SinglePointAddMode\">"
						+ "");
				if(TextUtil.isNotNull(wellList)){
					sb.append("<mc type=\"renshoujing\">");
					sb.append(this.getResStr(wellList));
					sb.append("</mc>");
				}
				if(TextUtil.isNotNull(poleList)){
					sb.append("<mc type=\"diangan\">");
					sb.append(this.getResStr(poleList));
					sb.append("</mc>");
				}
				if(TextUtil.isNotNull(stoneList)){
					sb.append("<mc type=\"biaoshi\">");
					sb.append(this.getResStr(stoneList));
					sb.append("</mc>");
				}
				sb.append("</xmldata>");
			}
			
			String jsonString = "xml="+URLEncoder.encode(sb.toString(), "UTF-8")+"&useraccount=yc";
			String outIN = RequestUtil.HttpRequest(InterfaceAddr.ADD_RESOURCE, "POST", jsonString);
			String result = "";
			if(outIN.contains("成功")){
				result = "success";
				List<Map<String, String>> resultList =interfaces.irmsInterface.utils.XmlUtil.getAddResList(outIN);
				List<Map<String, String>> poleResult = new LinkedList();
				List<Map<String, String>> stoneResult = new LinkedList();
				List<Map<String, String>> wellResult = new LinkedList();
				
				for(Map<String, String> map : resultList){
					String oldId = map.keySet().toString();
					String newId = map.get(oldId);
					if(oldId.contains("new-111")){
						poleResult.add(map);
					}
					if(oldId.contains("new-222")){
						wellResult.add(map);
					}
					if(oldId.contains("new-333")){
						stoneResult.add(map);
					}
				}
				if(TextUtil.isNotNull(stoneResult)){
					this.beatchAddRes("stone", stoneResult);
				}
				if(TextUtil.isNotNull(poleResult)){
					this.beatchAddRes("pole", poleResult);
				}
				if(TextUtil.isNotNull(wellResult)){
					this.beatchAddRes("well", wellResult);
				}
			}else{
				result = "error";
			}
			String inFile = UploadUtil.saveXml(sb.toString());
			String outFile = UploadUtil.saveXml(outIN);
			
			IrmsPojo pojo = new IrmsPojo();
			pojo.setInStr(inFile);
			pojo.setFaceType("batchAddRes");
			pojo.setOutStr(outFile);
			pojo.setFaceResult(result);
			pojo.setFaceTime(date);
			this.irmsInterService.addInterLog(pojo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * 批量更新数据
	 * @param type
	 * @param list
	 */
	public void beatchAddRes(String type,final List<Map<String, String>> list){
		String sql = "";
		if(type.equals("stone")){
			sql = "update stoneinfo "
				+ " set resNum = ?"
				+ " where stoneId = ?";
		}
		if(type.equals("well")){
			sql = "update wellinfo "
				+ " set resNum =? "
				+ " where wellId =? ";
		}
		if(type.equals("pole")){
			sql = "update poleinfo "
				+ " set resNum = ?"
				+ " where poleId =?";
		}
		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
			@Override
			public int getBatchSize() {
				return list.size();
			}
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				Map<String, String> map = list.get(i);
				String oldId = "";
				String newId = "";
				for(String key : map.keySet()){
					oldId = key.substring(7);
					newId = map.get(key);
				}
				ps.setString(1, newId);
				ps.setInt(2, Integer.parseInt(oldId));
			}
			
		});
	}
	
	/**
	 * 得到封装数据
	 */
	String getResStr(List<Map<String, Object>> list){
		StringBuffer sb = new StringBuffer();
		for(Map<String, Object> map : list){
			Map<String, String> cityMap  = this.irmsInterService.getCityCountry(map.get("region")+"");
			sb.append("<mo group=\"1\">");
			sb.append(""
					+ "<fv k=\"OWNERSHIP\" v=\"0\"/>"
					+ "<fv k=\"COUNTY_ID\" v=\""+cityMap.get("countyId")+"\"/>"
					+ "<fv k=\"PROJECT_ID\" v=\"\"/>"
					+ "<fv k=\"LATITUDE\" v=\""+map.get("latitude")+"\"/>"
					+ "<fv k=\"STATUS\" v=\"3\"/>"
					+ "<fv k=\"ZH_LABEL\" v=\""+map.get("resName")+"\"/>"
					+ "<fv k=\"CITY_ID\" v=\""+cityMap.get("cityId")+"\"/>"
					+ "<fv k=\"LONGITUDE\" v=\""+map.get("longitude")+"\"/>"
					+ "");
			if(getPropert.getValueByKey("province").equals("北京")){
				String resType = map.get("resEnType")+"";
				if(resType.equals("diangan")){
					sb.append("<fv k=\"RES_OWNER\" v=\"1\"/>"
							+ "<fv k=\"INT_ID\" v=\"new-111"+map.get("resCode")+"\"/>"
							+ "<fv k=\"IS_JIAOWEI\" v=\"0\"/>"
							+ "<fv k=\"MAINTAIN_AREA\" v=\"0\"/>"
							+ "<fv k=\"HIGHT\" v=\"0\"/>"
							+ "<fv k=\"POLE_KIND\" v=\"1\"/>"
							+ "<fv k=\"DAIWEI_ZU\" v=\"\"/>");
				}
				if(resType.equals("renshoujing")){
					sb.append("<fv k=\"DAIWEI_ZU\" v=\"\"/>"
							+ "<fv k=\"INT_ID\" v=\"new-222"+map.get("resCode")+"\"/>"
							+ "<fv k=\"IS_JIAOWEI\" v=\"1\"/>"
							+ "<fv k=\"IS_REDLINE\" v=\"0\"/>"
							+ "<fv k=\"IS_ERROR\" v=\"1\"/>"
							+ "<fv k=\"RES_OWNER\" v=\"1\"/>"
							+ "<fv k=\"MAINTAIN_AREA\" v=\"0\"/>"
							+ "<fv k=\"IS_CONN_POINT\" v=\"0\"/>"
							+ "<fv k=\"IS_CHECK\" v=\"0\"/>"
							+ "<fv k=\"COVER_STUFF\" v=\"1\"/>"
							+ "<fv k=\"IS_DANGER_POINT\" v=\"0\"/>"
							+ "");
				}
				if(resType.equals("biaoshi")){
					sb.append("<fv k=\"DEPTH\" v=\"0\"/>"
							+ "<fv k=\"INT_ID\" v=\"new-333"+map.get("resCode")+"\"/>"
							+ "<fv k=\"IS_JIAOWEI\" v=\"0\"/>"
							+ "<fv k=\"IS_CONN_POINT\" v=\"0\"/>"
							+ "<fv k=\"RES_OWNER\" v=\"1\"/>"
							+ "<fv k=\"IS_DANGER_POINT\" v=\"0\"/>"
							+ "<fv k=\"IS_KEEP_POINT\" v=\"0\"/>"
							+ "<fv k=\"DAIWEI_ZU\" v=\"\"/>"
							+ "<fv k=\"MAINTAIN_AREA\" v=\"0\"/>"
							+ "<fv k=\"STONE_TYPE\" v=\"1\"/>"
							+ "<fv k=\"IS_REDLINE\" v=\"0\"/>"
							+ "");
				}
			}
			String qualitor = map.get("qualitor")+"";
			String maintainor = map.get("maintainor")+"";
			if(TextUtil.isNotNull(qualitor) && qualitor.contains("_")){
				sb.append("<fv k=\"QUALITOR\" v=\""+qualitor.split("_")[1]+"\"/>");
			}
			if(TextUtil.isNotNull(maintainor)){
				sb.append("<fv k=\"MAINTAINOR\" v=\""+maintainor+"\"/>");
			}
			sb.append("</mo>");
		}
		return sb.toString();
	}
	
	/**
	 * 增加点接口
	 * @param obj
	 */
	public void addPoint(IrmsPoint obj){
		try{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh24:mm:ss");
			String time = sdf.format(date);
			String mode = "";
			String xml ="<xmldata mode=\"SinglePointAddMode\">"
				+ "<mc type=\""+obj.getType()+"\">"
				+ "<mo group=\"1\">"
				+ "<fv k=\"OWNERSHIP\" v=\"0\"/>"
				+ "<fv k=\"COUNTY_ID\" v=\""+obj.getCountryId()+"\"/>"
				+ "<fv k=\"PROJECT_ID\" v=\"\"/>"
				+ "<fv k=\"INT_ID\" v=\"new-"+obj.getImId()+"\"/>"
				+ "<fv k=\"LATITUDE\" v=\""+obj.getLatitude()+"\"/>"
				+ "<fv k=\"STATUS\" v=\"3\"/>"
				+ "<fv k=\"ZH_LABEL\" v=\""+obj.getResName()+"\"/>"
				+ "<fv k=\"CITY_ID\" v=\""+obj.getCityId()+"\"/>"
				+ "<fv k=\"LONGITUDE\" v=\""+obj.getLongitude()+"\"/>";
			if(getPropert.getValueByKey("province").equals("北京")){
				if(obj.getType().equals("diangan")){
					mode ="电杆";
					xml +="<fv k=\"RES_OWNER\" v=\"1\"/>"
						+ "<fv k=\"IS_JIAOWEI\" v=\"0\"/>"
						+ "<fv k=\"MAINTAIN_AREA\" v=\"0\"/>"
						+ "<fv k=\"HIGHT\" v=\"0\"/>"
						+ "<fv k=\"POLE_KIND\" v=\"1\"/>"
						+ "<fv k=\"DAIWEI_ZU\" v=\"\"/>";
				}
				if(obj.getType().equals("renshoujing")){
					mode ="人手井";
					xml +="<fv k=\"DAIWEI_ZU\" v=\"\"/>"
						+ "<fv k=\"IS_JIAOWEI\" v=\"1\"/>"
						+ "<fv k=\"IS_REDLINE\" v=\"0\"/>"
						+ "<fv k=\"IS_ERROR\" v=\"1\"/>"
						+ "<fv k=\"RES_OWNER\" v=\"1\"/>"
						+ "<fv k=\"MAINTAIN_AREA\" v=\"0\"/>"
						+ "<fv k=\"IS_CONN_POINT\" v=\"0\"/>"
						+ "<fv k=\"IS_CHECK\" v=\"0\"/>"
						+ "<fv k=\"COVER_STUFF\" v=\"1\"/>"
						+ "<fv k=\"IS_DANGER_POINT\" v=\"0\"/>";
				}
				if(obj.getType().equals("biaoshi")){
					mode ="标石";
					xml +="<fv k=\"DEPTH\" v=\"0\"/>"
						+ "<fv k=\"IS_JIAOWEI\" v=\"0\"/>"
						+ "<fv k=\"IS_CONN_POINT\" v=\"0\"/>"
						+ "<fv k=\"RES_OWNER\" v=\"1\"/>"
						+ "<fv k=\"IS_DANGER_POINT\" v=\"0\"/>"
						+ "<fv k=\"IS_KEEP_POINT\" v=\"0\"/>"
						+ "<fv k=\"DAIWEI_ZU\" v=\"\"/>"
						+ "<fv k=\"MAINTAIN_AREA\" v=\"0\"/>"
						+ "<fv k=\"STONE_TYPE\" v=\"1\"/>"
						+ "<fv k=\"IS_REDLINE\" v=\"0\"/>"
						+ "";
				}
			}
			if(TextUtil.isNotNull(obj.getQualitor()) && obj.getQualitor().contains("_")){
				xml+="<fv k=\"QUALITOR\" v=\""+obj.getQualitor().split("_")[1]+"\"/>";
			}
			if(TextUtil.isNotNull(obj.getMaintainor())){
				xml+="<fv k=\"MAINTAINOR\" v=\""+obj.getMaintainor()+"\"/>";
			}
			xml+= "</mo>"
				+ "</mc>"
				+ "</xmldata>"
				+ "";
			MoveResPojo pojo = new MoveResPojo();
			pojo.setInXml(xml);
			pojo.setAppType("add"+obj.getType());
			pojo.setResName(obj.getResName());
			pojo.setAppCnType("新增"+mode);
			pojo.setImId(obj.getImId());
			pojo.setImCnType(mode);
			pojo.setImEnType(obj.getType());
			pojo.setMaintainor(obj.getMaintainor());
			pojo.setCityId(obj.getCityId());
			pojo.setCountyId(obj.getCountryId());
			pojo.setRegion(obj.getArea());
			String id = this.irmsInterService.addRes(pojo,obj.getImId());
			if(!(id.equals("error")) && TextUtil.isNotNull(obj.getImId())){
				String sql = "update "+obj.getTableName()+" set resNum ="+id+" where "+obj.getIdCol()+"='"+obj.getImId()+"'";
				this.jdbcTemplate.execute(sql);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 移动点资源
	 * @param point
	 */
	public void movePoint(IrmsPoint point){
		try{
			if(TextUtil.isNotNull(point.getResNum())){
				String xml="<xmldata mode=\"SinglePointEditMode\">"
						+ "<mc type=\""+point.getType()+"\">"
						+ "<mo group=\"1\">"
						+ "<fv k=\"ZH_LABEL\" v=\""+point.getResName()+"\"/>"
						+ "<fv k=\"LONGITUDE\" v=\""+point.getLongitude()+"\"/>"
						+ "<fv k=\"LATITUDE\" v=\""+point.getLatitude()+"\"/>";
				//数据质量维护人
				if(TextUtil.isNotNull(point.getQualitor())){
					xml+="<fv k=\"QUALITOR\" v=\""+point.getQualitor()+"\"/>";
				}
				//一线维护人
				if(TextUtil.isNotNull(point.getMaintainor())){
					xml+="<fv k=\"MAINTAINOR\" v=\""+point.getMaintainor()+"\"/>";
				}
				xml+= "<fv k=\"INT_ID\" v=\""+point.getResNum()+"\"/>"
						+ "</mo>"
						+ "</mc>"
						+ "</xmldata>"
						+ "";
				String jsonString = "xml="+URLEncoder.encode(xml, "UTF-8")+"&useraccount=yc";
				String outIN = RequestUtil.HttpRequest(InterfaceAddr.MOVE_RESOURCE, "POST", jsonString);
				
				IrmsPojo obj = new IrmsPojo();
				obj.setInStr(xml);
				obj.setOutStr(outIN);
				obj.setFaceType("update"+point.getType());
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
	 * 删除资源点
	 * @param obj
	 */
	public void delPoint(IrmsPoint irms){
		try{
			String jsonString = "types="+URLEncoder.encode(irms.getType(), "UTF-8")+"&ids="+irms.getResNum()+"&useraccount=yc";
			String outIN = RequestUtil.HttpRequest(InterfaceAddr.DEL_RES, "POST", jsonString);
			
			IrmsPojo obj = new IrmsPojo();
			obj.setInStr(jsonString);
			obj.setOutStr(outIN);
			obj.setFaceType("del"+irms.getType());
			if(outIN.contains("loaded=\"成功\"")){
				obj.setFaceResult("success");
			}else{
				obj.setFaceResult("error");
			}
			this.addInterLog(obj);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 增加电杆
	 * @param pole
	 */
	public String addPole(PoleInfoBean pole){
		String id = "";
		try{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh24:mm:ss");
			String time = sdf.format(date);
			Map<String, String> map= this.irmsInterService.getCityCountry(pole.getRegion());
			String xml ="<xmldata mode=\"SinglePointAddMode\">"
					+ "<mc type=\"diangan\">"
					+ "<mo group=\"1\">"
					+ "<fv k=\"OWNERSHIP\" v=\""+ResUtil.getOwnerShip(pole.getMaintenanceModeEnumId()+"")+"\"/>"
					+ "<fv k=\"RES_OWNER\" v=\""+ResUtil.getResOwner(pole.getMaintenanceOrgId()+"")+"\"/>"
					+ "<fv k=\"HIGHT\" v=\""+ResUtil.getPoleKind(pole.getPoleTypeEnumId()+"")+"\"/>"
					+ "<fv k=\"COUNTY_ID\" v=\""+map.get("countyId")+"\"/>"
					+ "<fv k=\"PROJECT_ID\" v=\"\"/>"
					+ "<fv k=\"INT_ID\" v=\"new-"+pole.getPoleId()+"\"/>"
					+ "<fv k=\"LATITUDE\" v=\""+pole.getPoleLatitude()+"\"/>"
					+ "<fv k=\"STATUS\" v=\"3\"/>"
					+ "<fv k=\"ZH_LABEL\" v=\""+pole.getPoleName()+"\"/>"
					+ "<fv k=\"CITY_ID\" v=\""+map.get("cityId")+"\"/>"
					+ "<fv k=\"LONGITUDE\" v=\""+pole.getPoleLongitude()+"\"/>";
			if(getPropert.getValueByKey("province").equals("北京")){
				xml += "<fv k=\"IS_JIAOWEI\" v=\"0\"/>"
					+ "<fv k=\"MAINTAIN_AREA\" v=\"0\"/>"
					+ "<fv k=\"HIGHT\" v=\"0\"/>"
					+ "<fv k=\"EXAMINESTATUS\" v=\"2\"/>"
					+ "<fv k=\"POLE_KIND\" v=\"1\"/>"
					+ "<fv k=\"DAIWEI_ZU\" v=\"\"/>";
			}
			//数据质量责任人
			if(TextUtil.isNotNull(pole.getDataQualityPrincipal())){
				xml+="<fv k=\"QUALITOR\" v=\""+pole.getDataQualityPrincipal()+"\"/>";
			}
			//这个是新增电杆
			if(TextUtil.isNotNull(pole.getParties())){
				xml+="<fv k=\"MAINTAINOR\" v=\""+pole.getParties()+"\"/>"
					+ "<fv k=\"CREATOR\" v=\""+pole.getParties()+"\"/>";
				List<Map<String, Object>> MainList = this.getMaintainList(pole.getParties());
				if(TextUtil.isNotNull(MainList)){
					Map<String, Object> mainMap = MainList.get(0);
					xml+="<fv k=\"DAIWEI_ZU\" v=\""+mainMap.get("daiweiZu")+"\"/>"
						+ "<fv k=\"MAINTAIN_AREA\" v=\""+mainMap.get("maintainArea")+"\"/>";
				}

			}
				xml+= "</mo>"
					+ "</mc>"
					+ "</xmldata>"
					+ "";
			MoveResPojo pojo = new MoveResPojo();
			pojo.setInXml(xml);
			pojo.setAppType("addPole");
			pojo.setResType("diangan");
			pojo.setImId(pole.getPoleId()+"");
			pojo.setAppCnType("新增电杆");
			pojo.setResName(pole.getPoleName());
			pojo.setRegion(pole.getRegion());
			pojo.setQualitor(pole.getDataQualityPrincipal());
			pojo.setMaintainor(pole.getParties());
			pojo.setImEnType("pole");
			pojo.setImCnType("电杆");
			
			id = this.irmsInterService.addRes(pojo,pole.getPoleId()+"");
			if(!(id.equals("error")) && TextUtil.isNotNull(pole.getPoleId())){
				String sql = "update poleinfo"
						+ " set resNum ='"+id+"',"
						+ " poleName='"+pole.getPoleName()+"'"
						+ " where poleId ='"+pole.getPoleId()+"'";
				this.jdbcTemplate.execute(sql);
				this.irmsInterService.upYcapp("pole",pole.getPoleId()+"", id+"");
				//更新照片信息
				this.upImageStr(pole.getPoleId()+"", "pole", id);
			}
		}catch(Exception e){
			id = "errr";
			e.printStackTrace();
		}
		return id;
	}
	
	/**
	 * 修改电杆的位置
	 * @param pole
	 * @param gis
	 * @return
	 */
	public void movePole(PoleInfoBean pole){
		try{
			if(TextUtil.isNotNull(pole.getResNum())){
				Map<String, String> map= this.irmsInterService.getCityCountry(pole.getRegion());
				MoveResPojo obj = new MoveResPojo();
				obj.setAppType("movePole");
				obj.setLatitude(pole.getPoleLatitude());
				obj.setLongitude(pole.getPoleLongitude());
				obj.setResId(pole.getResNum());
				obj.setResName(pole.getPoleName());
				obj.setAppCnType("移动电杆");
				obj.setExtendXml("<fv k=\"OWNERSHIP\" v=\""+ResUtil.getOwnerShip(pole.getMaintenanceModeEnumId()+"")+"\"/>"
						+ "<fv k=\"RES_OWNER\" v=\""+ResUtil.getResOwner(pole.getMaintenanceOrgId()+"")+"\"/>"
						+ "<fv k=\"POLE_KIND\" v=\""+ResUtil.getPoleKind(pole.getPoleTypeEnumId()+"")+"\"/>"
						+ "");
				obj.setImId(pole.getPoleId()+"");
				obj.setResName(pole.getPoleName());
				obj.setRegion(pole.getRegion());
				obj.setQualitor(pole.getDataQualityPrincipal());
				obj.setCityId(map.get("cityId"));
				obj.setCountyId(map.get("countyId"));
				obj.setMaintainor(pole.getParties());
				obj.setImEnType("pole");
				obj.setImCnType("电杆");
				obj.setRegion(pole.getRegion());
				//数据质量维护人
				if(TextUtil.isNotNull(pole.getDataQualityPrincipal()) && pole.getDataQualityPrincipal().contains("_")){
					obj.setQualitor(pole.getDataQualityPrincipal().split("_")[1]);
				}
				//一线维护人
				if(TextUtil.isNotNull(pole.getParties())){
					obj.setMaintainor(pole.getParties());
					List<Map<String, Object>> MainList = this.getMaintainList(pole.getParties());
					if(TextUtil.isNotNull(MainList)){
						Map<String, Object> mainMap = MainList.get(0);
						String extendXml = obj.getExtendXml();
						extendXml+="<fv k=\"DAIWEI_ZU\" v=\""+mainMap.get("daiweiZu")+"\"/>"
							+ "<fv k=\"MAINTAIN_AREA\" v=\""+mainMap.get("maintainArea")+"\"/>";
						obj.setExtendXml(extendXml);
					}
				}
				obj.setResType("diangan");
				this.irmsInterService.moveRes(obj);
				//更新电杆的照片信息
				this.upImageStr(pole.getPoleId()+"", "pole", pole.getResNum());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 修改电杆的位置
	 * @param pole
	 * @param gis
	 * @return
	 */
	public void delPole(PoleInfoBean pole){
		try{
			if(TextUtil.isNotNull(pole.getResNum())){
				Map<String, String> map= this.irmsInterService.getCityCountry(pole.getRegion());
				MoveResPojo obj = new MoveResPojo();
				obj.setAppType("delPole");
				obj.setLatitude(pole.getPoleLatitude());
				obj.setLongitude(pole.getPoleLongitude());
				obj.setResId(pole.getResNum());
				obj.setResName(pole.getPoleName());
				obj.setAppCnType("删除电杆");
				obj.setExtendXml("<fv k=\"OWNERSHIP\" v=\""+ResUtil.getOwnerShip(pole.getPropertyNature()+"")+"\"/>"
						+ "<fv k=\"RES_OWNER\" v=\""+ResUtil.getOwnerShip(pole.getMaintenanceOrgId()+"")+"\"/>");
				obj.setImId(pole.getPoleId()+"");
				obj.setResName(pole.getPoleName());
				obj.setRegion(pole.getRegion());
				obj.setQualitor(pole.getDataQualityPrincipal());
				obj.setCityId(map.get("cityId"));
				obj.setCountyId(map.get("countyId"));
				obj.setMaintainor(pole.getParties());
				obj.setImEnType("pole");
				obj.setImCnType("电杆");
				obj.setRegion(pole.getRegion());
				//数据质量维护人
				if(TextUtil.isNotNull(pole.getDataQualityPrincipal()) && pole.getDataQualityPrincipal().contains("_")){
					obj.setQualitor(pole.getDataQualityPrincipal().split("_")[1]);
				}
				//一线维护人
				if(TextUtil.isNotNull(pole.getParties())){
					obj.setMaintainor(pole.getParties());
					List<Map<String, Object>> MainList = this.getMaintainList(pole.getParties());
					if(TextUtil.isNotNull(MainList)){
						Map<String, Object> mainMap = MainList.get(0);
						String extendXml = obj.getExtendXml();
						extendXml+="<fv k=\"DAIWEI_ZU\" v=\""+mainMap.get("daiweiZu")+"\"/>"
							+ "<fv k=\"MAINTAIN_AREA\" v=\""+mainMap.get("maintainArea")+"\"/>";
						obj.setExtendXml(extendXml);
					}
				}
				obj.setResType("diangan");
				this.irmsInterService.delRes(obj);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 修改ganlu 段资源
	 * @param poleLine
	 * @return
	 */
	public String upPoleLine(PolelineSegmentInfoBean poleLine){
		String id = "";
		try{
			Map<String, String> map= this.irmsInterService.getCityCountry(poleLine.getMaintenanceAreaName());
			Map<String, Object> start = this.getSpotMap(poleLine.getStartDeviceId()+"", "pole");
			Map<String, Object> end = this.getSpotMap(poleLine.getEndDeviceId()+"", "pole");
			Map<String, String> sysMap = new LinkedHashMap<String, String>();
			
			sysMap.put("type", "poleLine");
			sysMap.put("start", start.get("resNum")+"");
			sysMap.put("end", end.get("resNum")+"");
			sysMap.put("city", map.get("cityId")+"");
			sysMap.put("county", map.get("countyId")+"");
			sysMap.put("lineId", poleLine.getResNum());
			sysMap.put("lineName", poleLine.getPoleLineSegmentName()+"");
			sysMap.put("ownerShip",poleLine.getConstructionSharingEnumId()+"");
			sysMap.put("resOwner", poleLine.getConstructionSharingOrg()+"");
			String sysId="";
			if(TextUtil.isNotNull(poleLine.getPoleLineId())){
				sysId = this.getSystem(poleLine.getPoleLineId()+"", poleLine.getResNum(), "poleLine");
			}
			sysMap.put("sysId", sysId);//系统id
			sysMap.put("resType", "ganluduan");
			sysMap.put("startLon", start.get("longitude")+"");
			sysMap.put("startLat", start.get("latitude")+"");
			sysMap.put("startType", start.get("type")+"");
			sysMap.put("endType", end.get("type")+"");
			sysMap.put("endLon", end.get("longitude")+"");
			sysMap.put("endLat", end.get("latitude")+"");
			sysMap.put("figueLength", poleLine.getPoleLineSegmentLength());
			sysMap.put("lineLength", poleLine.getMaintainLength()+"");
			
			//数据质量维护人
			if(TextUtil.isNotNull(poleLine.getDataQualityPrincipal()) && poleLine.getDataQualityPrincipal().contains("_")){
				sysMap.put("qualitor", poleLine.getDataQualityPrincipal().split("_")[1]);
			}
			//一线维护人
			if(TextUtil.isNotNull(poleLine.getParties())){
				sysMap.put("maintainor", poleLine.getParties());
				List<Map<String, Object>> MainList = this.getMaintainList(poleLine.getParties());
				if(TextUtil.isNotNull(MainList)){
					Map<String, Object> mainMap = MainList.get(0);
					String xml="<fv k=\"DAIWEI_ZU\" v=\""+mainMap.get("daiweiZu")+"\"/>"
							+ "<fv k=\"MAINTAIN_AREA\" v=\""+mainMap.get("maintainArea")+"\"/>";
					sysMap.put("extendXml", xml);
				}
			}
			
			
			String xml = this.irmsInterService.upLineInstr(sysMap);
			MoveResPojo pojo = new MoveResPojo();
			pojo.setInXml(xml);
			pojo.setAppType("upPloeLine");
			pojo.setAppCnType("修改杆路段");
			pojo.setImId(poleLine.getPoleLineSegmentId()+"");
			pojo.setImCnType("杆路段");
			pojo.setImEnType("poleLineSeg");
			pojo.setResName(poleLine.getPoleLineSegmentName());
			pojo.setMaintainor(poleLine.getParties());
			pojo.setRegion(poleLine.getMaintenanceAreaName());
			
			id = this.irmsInterService.upRes(pojo,poleLine.getPoleLineSegmentId()+"");
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}
	
	/**
	 * 增加杆路段
	 * @param poleLine
	 */
	public String addPoleLine(PolelineSegmentInfoBean poleLine){
		String id = "";
		try{
			Map<String, String> map= this.irmsInterService.getCityCountry(poleLine.getMaintenanceAreaName());
			Map<String, Object> start = this.getSpotMap(poleLine.getStartDeviceId()+"", "pole");
			Map<String, Object> end = this.getSpotMap(poleLine.getEndDeviceId()+"", "pole");
			Map<String, String> sysMap = new LinkedHashMap<String, String>();
			sysMap.put("type", "poleLine");
			sysMap.put("start", start.get("resNum")+"");
			sysMap.put("end", end.get("resNum")+"");
			sysMap.put("city", map.get("cityId")+"");
			sysMap.put("county", map.get("countyId")+"");
			sysMap.put("lineId", poleLine.getPoleLineSegmentId()+"");
			sysMap.put("lineName", poleLine.getPoleLineSegmentName()+"");
			sysMap.put("sysName", poleLine.getPoleLineName());
			sysMap.put("ownerShip",poleLine.getConstructionSharingEnumId()+"");
			sysMap.put("resOwner", poleLine.getConstructionSharingOrg()+"");
			String sysId = "";
			if(getPropert.getValueByKey("province").equals("北京")){
				sysId = this.getBjSystem(sysMap,poleLine.getPoleLineId()+"");
			}else{
				String key =this.irmsInterService.getKeyStr(poleLine.getPoleLineSegmentName());
				sysMap.put("key", key);
				sysId = this.getSystem(sysMap);
			}
			sysMap.put("resType", "ganluduan");
			sysMap.put("startLon", start.get("longitude")+"");
			sysMap.put("startLat", start.get("latitude")+"");
			sysMap.put("startType", start.get("type")+"");
			sysMap.put("endType", end.get("type")+"");
			sysMap.put("endLon", end.get("longitude")+"");
			sysMap.put("endLat", end.get("latitude")+"");
			sysMap.put("sysId", sysId);
			sysMap.put("figueLength", poleLine.getPoleLineSegmentLength());
			sysMap.put("lineLength", poleLine.getMaintainLength()+"");
			//数据质量维护人
			if(TextUtil.isNotNull(poleLine.getDataQualityPrincipal())){
				sysMap.put("qualitor", poleLine.getDataQualityPrincipal());
			}
			//一线维护人
			if(TextUtil.isNotNull(poleLine.getParties())){
				sysMap.put("maintainor", poleLine.getParties());
				List<Map<String, Object>> MainList = this.getMaintainList(poleLine.getParties());
				if(TextUtil.isNotNull(MainList)){
					Map<String, Object> mainMap = MainList.get(0);
					String xml="<fv k=\"DAIWEI_ZU\" v=\""+mainMap.get("daiweiZu")+"\"/>"
							+ "<fv k=\"MAINTAIN_AREA\" v=\""+mainMap.get("maintainArea")+"\"/>";
					sysMap.put("extendXml", xml);
				}
			}
			String xml = this.irmsInterService.getLineInStr(sysMap);

			MoveResPojo pojo = new MoveResPojo();
			pojo.setInXml(xml);
			pojo.setAppType("addPloeLine");
			pojo.setAppCnType("增加杆路段");
			pojo.setImId(poleLine.getPoleLineSegmentId()+"");
			pojo.setImCnType("杆路段");
			pojo.setImEnType("poleLineSeg");
			pojo.setResName(poleLine.getPoleLineSegmentName());
			pojo.setMaintainor(poleLine.getParties());
			pojo.setRegion(poleLine.getMaintenanceAreaName());
			
			id = this.irmsInterService.addRes(pojo,poleLine.getPoleLineSegmentId()+"");
			if(!(id.equals("error")) && TextUtil.isNotNull(poleLine.getPoleLineSegmentId())){
				String sql = "update polelinesegmentinfo"
						+ " set resNum ='"+id+"'"
						+ " where poleLineSegmentId ='"+poleLine.getPoleLineSegmentId()+"'";
				this.jdbcTemplate.execute(sql);
				this.irmsInterService.upYcapp("poleLine",poleLine.getPoleLineSegmentId()+"", id+"");
			}
		}catch(Exception e){
			id="error";
			e.printStackTrace();
		}
		return id;
	}
	
	
	/**
	 * 增加井
	 * @param well
	 */
	public String addWell(WellInfoBean well){
		String id = "";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(date);
		String wellType = "";
		if(TextUtil.isNull(well.getWellType())) {
			wellType = "1";
		}else {
			wellType =well.getWellType();
		}
		Map<String, String> map= this.irmsInterService.getCityCountry(well.getRegion());
		String xml ="<xmldata mode=\"SinglePointAddMode\">"
				+ "<mc type=\"renshoujing\">"
				+ "<mo group=\"1\">"
				+ "<fv k=\"OWNERSHIP\" v=\""+ResUtil.getOwnerShip(well.getConstructionSharingEnumId()+"")+"\"/>"
				+ "<fv k=\"RES_OWNER\" v=\""+ResUtil.getResOwner(well.getConstructionSharingOrg()+"")+"\"/>"
				+ "<fv k=\"COUNTY_ID\" v=\""+map.get("countyId")+"\"/>"
				+ "<fv k=\"WELL_KIND\" v=\""+wellType+"\"/>"
				+ "<fv k=\"PROJECT_ID\" v=\"\"/>"
				+ "<fv k=\"INT_ID\" v=\"new-"+well.getWellId()+"\"/>"
				+ "<fv k=\"LATITUDE\" v=\""+well.getLatitude()+"\"/>"
				+ "<fv k=\"STATUS\" v=\"3\"/>"
				+ "<fv k=\"ZH_LABEL\" v=\""+well.getWellName()+"\"/>"
				+ "<fv k=\"CITY_ID\" v=\""+map.get("cityId")+"\"/>"
				+ "<fv k=\"LONGITUDE\" v=\""+well.getLongitude()+"\"/>";
		if(getPropert.getValueByKey("province").equals("北京")){
			xml +="<fv k=\"DAIWEI_ZU\" v=\"\"/>"
				+ "<fv k=\"IS_JIAOWEI\" v=\"1\"/>"
				+ "<fv k=\"IS_REDLINE\" v=\"0\"/>"
				+ "<fv k=\"IS_ERROR\" v=\"1\"/>"
				+ "<fv k=\"MAINTAIN_AREA\" v=\"0\"/>"
				+ "<fv k=\"IS_CONN_POINT\" v=\"0\"/>"
				+ "<fv k=\"IS_CHECK\" v=\"0\"/>"
				+ "<fv k=\"EXAMINESTATUS\" v=\"2\"/>"
				+ "<fv k=\"COVER_STUFF\" v=\"1\"/>"
				+ "<fv k=\"IS_DANGER_POINT\" v=\"0\"/>"
				;
		}
		//增加管井
		if(TextUtil.isNotNull(well.getDataQualityPrincipal())){
			xml+="<fv k=\"QUALITOR\" v=\""+well.getDataQualityPrincipal()+"\"/>";
		}
		if(TextUtil.isNotNull(well.getParties()) ){
			xml+="<fv k=\"MAINTAINOR\" v=\""+well.getParties()+"\"/>"
				+ "<fv k=\"CREATOR\" v=\""+well.getParties()+"\"/>";
			List<Map<String, Object>> MainList = this.getMaintainList(well.getParties());
			if(TextUtil.isNotNull(MainList)){
				Map<String, Object> mainMap = MainList.get(0);
				xml+="<fv k=\"DAIWEI_ZU\" v=\""+mainMap.get("daiweiZu")+"\"/>"
					+ "<fv k=\"MAINTAIN_AREA\" v=\""+mainMap.get("maintainArea")+"\"/>";
			}
		}
		xml+= "</mo>"
			+ "</mc>"
			+ "</xmldata>"
			+ "";
		MoveResPojo pojo = new MoveResPojo();
		pojo.setInXml(xml);
		pojo.setAppType("addWell");
		
		pojo.setResType("renshoujing");
		pojo.setImId(well.getWellId()+"");
		pojo.setAppCnType("新增人手井");
		pojo.setResName(well.getWellName());
		pojo.setRegion(well.getRegion());
		pojo.setQualitor(well.getDataQualityPrincipal());
		pojo.setMaintainor(well.getParties());
		pojo.setImEnType("well");
		pojo.setImCnType("人手井");
		
		id = this.irmsInterService.addRes(pojo,well.getWellId()+"");
		if(!(id.contains("error")) && TextUtil.isNotNull(well.getWellId()) && TextUtil.isNotNull(id)){
			String sql = "update wellinfo"
					+ " set resNum ='"+id+"', "
					+ " wellName ='"+well.getWellName()+"' "
					+ " where wellId ='"+well.getWellId()+"'";
			this.jdbcTemplate.execute(sql);
			//更新综资信息
			this.irmsInterService.upYcapp("well", well.getWellId()+"", id+"");
			//更新图片信息
			this.upImageStr(well.getWellId()+"", "well", id);
		}
		return id;
	}
	
	/**
	 * 修改管道段
	 * @param pipe
	 * @return
	 */
	public String upPipeSeg(PipeSegmentInfoBean pipe){
		String id = "";
		try{
			Map<String, String> map= this.irmsInterService.getCityCountry(pipe.getMaintenanceAreaName());
			Map<String, Object> start = this.getSpotMap(pipe.getStartDeviceId()+"", "well");
			Map<String, Object> end = this.getSpotMap(pipe.getEndDeviceId()+"", "well");
			Map<String, String> sysMap = new LinkedHashMap<String, String>();
			
			sysMap.put("start", start.get("resNum")+"");
			sysMap.put("end", end.get("resNum")+"");
			sysMap.put("city", map.get("cityId")+"");
			sysMap.put("county", map.get("countyId")+"");
			sysMap.put("lineId", pipe.getResNum()+"");
			sysMap.put("lineName",pipe.getPipeSegmentName()+"");
			sysMap.put("sysName", pipe.getPipeName());
			sysMap.put("ownerShip",pipe.getSharingTypeEnumId()+"");
			sysMap.put("resOwner", pipe.getConstructionSharingOrg()+"");
			String sysId = "";
			if(TextUtil.isNotNull(pipe.getPipeId())){
				sysId = this.getSystem(pipe.getPipeId()+"", pipe.getResNum(), "pipeLine");
			}
			sysMap.put("resType", "guandaoduan");
			sysMap.put("startLon", start.get("longitude")+"");
			sysMap.put("startLat", start.get("latitude")+"");
			sysMap.put("startType", start.get("type")+"");
			sysMap.put("endType", end.get("type")+"");
			sysMap.put("endLon", end.get("longitude")+"");
			sysMap.put("endLat", end.get("latitude")+"");
			sysMap.put("sysId", sysId);
			sysMap.put("figueLength", pipe.getPipeSegmentLength());
			sysMap.put("lineLength",pipe.getMaintainLength()+"");
			if(TextUtil.isNotNull(pipe.getDataQualityPrincipal()) && pipe.getDataQualityPrincipal().contains("_")){
				sysMap.put("qualitor", pipe.getDataQualityPrincipal().split("_")[1]);
			}
			if(TextUtil.isNotNull(pipe.getParties())){
				sysMap.put("maintainor", pipe.getParties());
			}
			String xml = this.irmsInterService.upLineInstr(sysMap);
			
			//修改管道段
			MoveResPojo pojo = new MoveResPojo();
			pojo.setInXml(xml);
			pojo.setAppType("upPipeSeg");
			pojo.setAppCnType("修改管道段");
			pojo.setImId(pipe.getPipeSegmentId()+"");
			pojo.setImCnType("管道段");
			pojo.setImEnType("pipeSeg");
			pojo.setResName(pipe.getPipeSegmentName());
			pojo.setMaintainor(pipe.getParties());
			pojo.setRegion(pipe.getMaintenanceAreaName());
			id = this.irmsInterService.upRes(pojo,pipe.getPipeSegmentId()+"");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}
	
	/**
	 * 管道段
	 * @param pipe
	 */
	public String addPipeSeg(PipeSegmentInfoBean pipe){
		String id = "";
		try{
			Map<String, String> map= this.irmsInterService.getCityCountry(pipe.getMaintenanceAreaName());
			Map<String, Object> start = this.getSpotMap(pipe.getStartDeviceId()+"", "well");
			Map<String, Object> end = this.getSpotMap(pipe.getEndDeviceId()+"", "well");
			Map<String, String> sysMap = new LinkedHashMap<String, String>();
			String startResNum = start.get("resNum")+"";
			String endResNum = end.get("resNum")+"";
			if(TextUtil.isNull(startResNum)) {
				WellInfoBean startWell = (WellInfoBean) this.approvalService.getResObject(pipe.getStartDeviceId()+"","well");
				String result = this.addWell(startWell);
				if(isNumeric(result)) {
					startResNum = result;
				}
			}
			if(TextUtil.isNull(endResNum)) {
				WellInfoBean endWell = (WellInfoBean) this.approvalService.getResObject(pipe.getEndDeviceId()+"","well");
				String result = this.addWell(endWell);
				if(isNumeric(result)) {
					endResNum = result;
				}
			}
			if(TextUtil.isNotNull(startResNum) && TextUtil.isNotNull(endResNum)) {
				sysMap.put("type", "pipeLine");
				sysMap.put("start",startResNum);
				sysMap.put("end", endResNum);
				sysMap.put("city", map.get("cityId")+"");
				sysMap.put("county", map.get("countyId")+"");
				sysMap.put("lineId", pipe.getPipeSegmentId()+"");
				sysMap.put("lineName",pipe.getPipeSegmentName()+"");
				sysMap.put("sysName", pipe.getPipeName());
				sysMap.put("ownerShip",pipe.getSharingTypeEnumId()+"");
				sysMap.put("resOwner", pipe.getConstructionSharingOrg()+"");
				String sysId  = "";
				if(getPropert.getValueByKey("province").equals("北京")){
					sysId = this.getBjSystem(sysMap,pipe.getPipeId()+"");
				}else{
					String key = this.irmsInterService.getKeyStr(pipe.getPipeSegmentName());
					sysMap.put("key", key);
					sysId = this.getSystem(sysMap);
				}
				sysMap.put("resType", "guandaoduan");
				sysMap.put("startLon", start.get("longitude")+"");
				sysMap.put("startLat", start.get("latitude")+"");
				sysMap.put("startType", start.get("type")+"");
				sysMap.put("endType", end.get("type")+"");
				sysMap.put("endLon", end.get("longitude")+"");
				sysMap.put("endLat", end.get("latitude")+"");
				sysMap.put("sysId", sysId);
				sysMap.put("figueLength", pipe.getPipeSegmentLength());
				sysMap.put("lineLength",pipe.getMaintainLength()+"");
				if(TextUtil.isNotNull(pipe.getDataQualityPrincipal())){
					sysMap.put("qualitor", pipe.getDataQualityPrincipal());
				}
				if(TextUtil.isNotNull(pipe.getParties())){
					sysMap.put("maintainor", pipe.getParties());
				}
				String xml = this.irmsInterService.getLineInStr(sysMap);
				
				MoveResPojo pojo = new MoveResPojo();
				pojo.setInXml(xml);
				pojo.setAppType("addPipeSeg");
				pojo.setAppCnType("增加管道段");
				pojo.setImId(pipe.getPipeSegmentId()+"");
				pojo.setImCnType("管道段");
				pojo.setImEnType("pipeSeg");
				pojo.setResName(pipe.getPipeSegmentName());
				pojo.setMaintainor(pipe.getParties());
				pojo.setRegion(pipe.getMaintenanceAreaName());
				id = this.irmsInterService.addRes(pojo,pipe.getPipeSegmentId()+"");
				if(!(id.contains("error")) && TextUtil.isNotNull(pipe.getPipeSegmentId())){
					String sql = "update pipesegmentinfo"
							+ " set resNum ='"+id+"',"
							+ " pipeSegmentName ='"+pipe.getPipeSegmentName()+"', "
							+ " startDeviceName ='"+pipe.getStartDeviceName()+"', "
							+ " endDeviceName ='"+pipe.getEndDeviceName()+"' "
							+ " where pipeSegmentId ='"+pipe.getPipeSegmentId()+"'";
					this.jdbcTemplate.execute(sql);
					this.irmsInterService.upYcapp("pipeLine", pipe.getPipeSegmentId()+"", id+"");
				}
			}else {
				id="error_数据格式不正确";
			}
			
		}catch(Exception e){
			id = "error";
			e.printStackTrace();
		}
		return id;
	}
	/**
	 * 移动井
	 * @param well
	 * @param gis
	 * @return
	 */
	public void moveWell(WellInfoBean well){
		try{
			if(TextUtil.isNotNull(well.getResNum())){
				Map<String, String> map= this.irmsInterService.getCityCountry(well.getRegion());
				MoveResPojo obj = new MoveResPojo();
				obj.setAppType("moveWell");
				obj.setLatitude(well.getLatitude());
				obj.setLongitude(well.getLongitude());
				obj.setResId(well.getResNum());
				obj.setResName(well.getWellName());
				obj.setResType("renshoujing");
				obj.setExtendXml("<fv k=\"OWNERSHIP\" v=\""+ResUtil.getOwnerShip(well.getConstructionSharingEnumId()+"")+"\"/>"
						+ "<fv k=\"RES_OWNER\" v=\""+ResUtil.getResOwner(well.getConstructionSharingOrg()+"")+"\"/>");
				obj.setCityId(map.get("cityId"));
				obj.setCountyId(map.get("countyId"));
				obj.setAppCnType("移动人手井");
				obj.setImId(well.getWellId()+"");
				obj.setImEnType("well");
				obj.setImCnType("人手井");
				obj.setRegion(well.getRegion());
				//数据质量维护人
				if(TextUtil.isNotNull(well.getDataQualityPrincipal()) && well.getDataQualityPrincipal().contains("_")){
					obj.setQualitor(well.getDataQualityPrincipal().split("_")[1]);
				}
				//一线维护人
				if(TextUtil.isNotNull(well.getParties()) ){
					obj.setMaintainor(well.getParties());
					List<Map<String, Object>> MainList = this.getMaintainList(well.getParties());
					if(TextUtil.isNotNull(MainList)){
						Map<String, Object> mainMap = MainList.get(0);
						String extendXml = obj.getExtendXml();
						extendXml+="<fv k=\"DAIWEI_ZU\" v=\""+mainMap.get("daiweiZu")+"\"/>"
							+ "<fv k=\"MAINTAIN_AREA\" v=\""+mainMap.get("maintainArea")+"\"/>";
						obj.setExtendXml(extendXml);
					}
				}
				this.irmsInterService.moveRes(obj);
				//更新图片信息
				this.upImageStr(well.getWellId()+"", "well", well.getResNum());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 删除人手井
	 * @param well
	 */
	public void delWell(WellInfoBean well){
		try{
			if(TextUtil.isNotNull(well.getResNum())){
				Map<String, String> map= this.irmsInterService.getCityCountry(well.getRegion());
				MoveResPojo obj = new MoveResPojo();
				obj.setAppType("delWell");
				obj.setLatitude(well.getLatitude());
				obj.setLongitude(well.getLongitude());
				obj.setResId(well.getResNum());
				obj.setResName(well.getWellName());
				obj.setResType("renshoujing");
				obj.setExtendXml("<fv k=\"OWNERSHIP\" v=\""+ResUtil.getOwnerShip(well.getConstructionSharingEnumId()+"")+"\"/>"
						+ "<fv k=\"RES_OWNER\" v=\""+ResUtil.getOwnerShip(well.getConstructionSharingOrg()+"")+"\"/>");
				obj.setCityId(map.get("cityId"));
				obj.setCountyId(map.get("countyId"));
				obj.setAppCnType("删除人手井");
				obj.setImId(well.getWellId()+"");
				obj.setImEnType("well");
				obj.setImCnType("人手井");
				obj.setRegion(well.getRegion());
				//数据质量维护人
				if(TextUtil.isNotNull(well.getDataQualityPrincipal()) && well.getDataQualityPrincipal().contains("_")){
					obj.setQualitor(well.getDataQualityPrincipal().split("_")[1]);
				}
				//一线维护人
				if(TextUtil.isNotNull(well.getParties()) ){
					obj.setMaintainor(well.getParties());
					List<Map<String, Object>> MainList = this.getMaintainList(well.getParties());
					if(TextUtil.isNotNull(MainList)){
						Map<String, Object> mainMap = MainList.get(0);
						String extendXml = obj.getExtendXml();
						extendXml+="<fv k=\"DAIWEI_ZU\" v=\""+mainMap.get("daiweiZu")+"\"/>"
							+ "<fv k=\"MAINTAIN_AREA\" v=\""+mainMap.get("maintainArea")+"\"/>";
						obj.setExtendXml(extendXml);
					}
				}
				this.irmsInterService.delRes(obj);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改直埋段
	 * @param buried
	 * @return
	 */
	public String upBuried(BuriedPartObj buried){
		String id ="";
		try{
			Map<String, String> map= this.irmsInterService.getCityCountry(buried.getBuriedPartArea());
			Map<String, Object> start = this.getSpotMap(buried.getBuriedPartStartId(), "stone");
			Map<String, Object> end = this.getSpotMap(buried.getBuriedPartEndId(), "stone");
			Map<String, String> sysMap = new LinkedHashMap<String, String>();
			sysMap.put("type", "buried");
			sysMap.put("start", start.get("resNum")+"");
			sysMap.put("end", end.get("resNum")+"");
			sysMap.put("city", map.get("cityId")+"");
			sysMap.put("county", map.get("countyId")+"");
			sysMap.put("lineId", buried.getId()+"");
			sysMap.put("lineName",buried.getBuriedPartName()+"");
			sysMap.put("sysName", buried.getBuriedStr());
			sysMap.put("ownerShip", buried.getPropertyRight()+"");
			sysMap.put("resOwner", buried.getPropertyDept()+"");
			String sysId = "";
			if(TextUtil.isNotNull(buried.getBuriedId())){
				//sysId = this.getSystem(buried.getBuriedId());
				sysId = this.getSystem(buried.getBuriedId(), buried.getResNum(), "buried");
			}
			sysMap.put("resType", "zhimaiduan");
			sysMap.put("startLon", start.get("longitude")+"");
			sysMap.put("startLat", start.get("latitude")+"");
			sysMap.put("startType", start.get("type")+"");
			sysMap.put("endType", end.get("type")+"");
			sysMap.put("endLon", end.get("longitude")+"");
			sysMap.put("endLat", end.get("latitude")+"");
			sysMap.put("sysId", sysId);
			sysMap.put("lineId", buried.getResNum());
			sysMap.put("lineName",buried.getBuriedPartName()+"");
			sysMap.put("figueLength", buried.getBuriedPartLength());
			sysMap.put("lineLength",buried.getMaintainLength()+"");
			if(TextUtil.isNotNull(buried.getDataQualitier()) && buried.getDataQualitier().contains("_")){
				sysMap.put("qualitor", buried.getDataQualitier().split("_")[1]);
			}
			if(TextUtil.isNotNull(buried.getMaintainer())){
				sysMap.put("maintainor", buried.getMaintainer());
				List<Map<String, Object>> MainList = this.getMaintainList(buried.getMaintainer());
				if(TextUtil.isNotNull(MainList)){
					Map<String, Object> mainMap = MainList.get(0);
					String xml="<fv k=\"DAIWEI_ZU\" v=\""+mainMap.get("daiweiZu")+"\"/>"
							+ "<fv k=\"MAINTAIN_AREA\" v=\""+mainMap.get("maintainArea")+"\"/>";
					sysMap.put("extendXml", xml);
				}
			}
			String xml = this.irmsInterService.upLineInstr(sysMap);
			
			MoveResPojo pojo = new MoveResPojo();
			pojo.setInXml(xml);
			pojo.setAppType("upBuried");
			pojo.setAppCnType("修改直埋");
			pojo.setImId(buried.getId()+"");
			pojo.setImCnType("直埋段");
			pojo.setImEnType("buriedSeg");
			pojo.setResName(buried.getBuriedPartName());
			pojo.setMaintainor(buried.getMaintainer());
			pojo.setRegion(buried.getBuriedPartArea());
			id = this.irmsInterService.upRes(pojo,buried.getId()+"");
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}
	
	/**
	 * 增加直埋段
	 * @param buried
	 */
	public String addBuried(BuriedPartObj buried){
		String id ="";
		try{
			Map<String, String> map= this.irmsInterService.getCityCountry(buried.getBuriedPartArea());
			Map<String, Object> start = this.getSpotMap(buried.getBuriedPartStartId(), "stone");
			Map<String, Object> end = this.getSpotMap(buried.getBuriedPartEndId(), "stone");
			Map<String, String> sysMap = new LinkedHashMap<String, String>();
			sysMap.put("type", "buried");
			sysMap.put("start", start.get("resNum")+"");
			sysMap.put("end", end.get("resNum")+"");
			sysMap.put("city", map.get("cityId")+"");
			sysMap.put("county", map.get("countyId")+"");
			sysMap.put("lineId", buried.getId()+"");
			sysMap.put("lineName",buried.getBuriedPartName()+"");
			sysMap.put("sysName", buried.getBuriedStr());
			sysMap.put("ownerShip", buried.getPropertyRight()+"");
			sysMap.put("resOwner", buried.getPropertyDept()+"");
			String sysId = "";
			if(getPropert.getValueByKey("province").equals("北京")){
				sysId = this.getBjSystem(sysMap,buried.getBuriedId());
			}else{
				String key = this.irmsInterService.getKeyStr(buried.getBuriedPartName());
				sysMap.put("key", key);
				sysId = this.getSystem(sysMap);
			}
			sysMap.put("resType", "zhimaiduan");
			sysMap.put("startLon", start.get("longitude")+"");
			sysMap.put("startLat", start.get("latitude")+"");
			sysMap.put("startType", start.get("type")+"");
			sysMap.put("endType", end.get("type")+"");
			sysMap.put("endLon", end.get("longitude")+"");
			sysMap.put("endLat", end.get("latitude")+"");
			sysMap.put("sysId", sysId);
			sysMap.put("lineName",buried.getBuriedPartName()+"");
			sysMap.put("figueLength", buried.getBuriedPartLength());
			sysMap.put("lineLength",buried.getMaintainLength()+"");
			if(TextUtil.isNotNull(buried.getDataQualitier()) && buried.getDataQualitier().contains("_")){
				sysMap.put("qualitor", buried.getDataQualitier().split("_")[1]);
			}
			if(TextUtil.isNotNull(buried.getMaintainer())){
				sysMap.put("maintainor", buried.getMaintainer());
				List<Map<String, Object>> MainList = this.getMaintainList(buried.getMaintainer());
				if(TextUtil.isNotNull(MainList)){
					Map<String, Object> mainMap = MainList.get(0);
					String xml="<fv k=\"DAIWEI_ZU\" v=\""+mainMap.get("daiweiZu")+"\"/>"
						+ "<fv k=\"MAINTAIN_AREA\" v=\""+mainMap.get("maintainArea")+"\"/>";
					sysMap.put("extendXml", xml);
				}

			}
			String xml = this.irmsInterService.getLineInStr(sysMap);
			
			MoveResPojo pojo = new MoveResPojo();
			pojo.setInXml(xml);
			pojo.setAppType("addBuried");
			pojo.setAppCnType("增加直埋");
			pojo.setImId(buried.getId()+"");
			pojo.setImCnType("直埋段");
			pojo.setImEnType("buriedSeg");
			pojo.setResName(buried.getBuriedPartName());
			pojo.setMaintainor(buried.getMaintainer());
			pojo.setRegion(buried.getBuriedPartArea());
			id = this.irmsInterService.addRes(pojo,buried.getId()+"");
			if(!(id.equals("error")) && TextUtil.isNotNull(buried.getId())){
				String sql = "update buriedpartinfo"
						+ " set resNum ='"+id+"'"
						+ " where id ='"+buried.getId()+"'";
				this.jdbcTemplate.execute(sql);
				this.irmsInterService.upYcapp("buried", buried.getId()+"", id+"");
			}
		}catch(Exception e){
			id="error";
			e.printStackTrace();
		}
		return id;
	}
	
	/**
	 * 增加标石
	 * @param stone
	 */
	public String addStone(StoneInfoBean stone){
		String id = "";
		try{
			Map<String, String> map= this.irmsInterService.getCityCountry(stone.getStoneArea());
			String xml ="<xmldata mode=\"SinglePointAddMode\">"
					+ "<mc type=\"biaoshi\">"
					+ "<mo group=\"1\">"
					+ "<fv k=\"OWNERSHIP\" v=\""+ResUtil.getOwnerShip(stone.getPropertyNature()+"")+"\"/>"
					+ "<fv k=\"RES_OWNER\" v=\""+ResUtil.getResOwner(stone.getPropertyComp()+"")+"\"/>"
					+ "<fv k=\"COUNTY_ID\" v=\""+map.get("countyId")+"\"/>"
					+ "<fv k=\"PROJECT_ID\" v=\"\"/>"
					+ "<fv k=\"INT_ID\" v=\"new-"+stone.getStoneId()+"\"/>"
					+ "<fv k=\"LATITUDE\" v=\""+stone.getLatitude()+"\"/>"
					+ "<fv k=\"STATUS\" v=\"3\"/>"
					+ "<fv k=\"ZH_LABEL\" v=\""+stone.getStoneName()+"\"/>"
					+ "<fv k=\"CITY_ID\" v=\""+ map.get("cityId")+""+"\"/>"
					+ "<fv k=\"LONGITUDE\" v=\""+stone.getLongitude()+"\"/>";
			if(getPropert.getValueByKey("province").equals("北京")){
				xml +="<fv k=\"DEPTH\" v=\"0\"/>"
					+ "<fv k=\"IS_JIAOWEI\" v=\"0\"/>"
					+ "<fv k=\"IS_CONN_POINT\" v=\"0\"/>"
					+ "<fv k=\"IS_DANGER_POINT\" v=\"0\"/>"
					+ "<fv k=\"EXAMINESTATUS\" v=\"2\"/>"
					+ "<fv k=\"IS_KEEP_POINT\" v=\"0\"/>"
					+ "<fv k=\"STONE_TYPE\" v=\"1\"/>"
					+ "<fv k=\"IS_REDLINE\" v=\"0\"/>"
					+ "";
			}
			//标石数据质量责任人
			if(TextUtil.isNotNull(stone.getDataQualitier()) && stone.getDataQualitier().contains("_")){
				xml+="<fv k=\"QUALITOR\" v=\""+stone.getDataQualitier().split("_")[1]+"\"/>";
			}
			//标石的一线维护人
			if(TextUtil.isNotNull(stone.getMaintainer())){
				xml+="<fv k=\"MAINTAINOR\" v=\""+stone.getMaintainer()+"\"/>";
				List<Map<String, Object>> MainList = this.getMaintainList(stone.getMaintainer());
				if(TextUtil.isNotNull(MainList)){
					Map<String, Object> mainMap = MainList.get(0);
					xml+="<fv k=\"DAIWEI_ZU\" v=\""+mainMap.get("daiweiZu")+"\"/>"
					  + "<fv k=\"MAINTAIN_AREA\" v=\""+mainMap.get("maintainArea")+"\"/>";
				}
			}
				xml+= "</mo>"
					+ "</mc>"
					+ "</xmldata>"
					+ "";
			MoveResPojo pojo = new MoveResPojo();
			pojo.setInXml(xml);
			pojo.setAppType("addStone");
			
			pojo.setResType("biaoshi");
			pojo.setImId(stone.getStoneId()+"");
			pojo.setAppCnType("新增标石");
			pojo.setResName(stone.getStoneName());
			pojo.setRegion(stone.getStoneArea());
			pojo.setQualitor(stone.getDataQualitier());
			pojo.setMaintainor(stone.getMaintainer());
			pojo.setImEnType("stone");
			pojo.setImCnType("标石");
			
			id = this.irmsInterService.addRes(pojo,stone.getStoneId()+"");
			if(!(id.equals("error")) && TextUtil.isNotNull(stone.getStoneId())){
				String sql = "update stoneinfo"
						+ " set resNum ='"+id+"'"
						+ " where stoneId ='"+stone.getStoneId()+"'";
				this.jdbcTemplate.execute(sql);
				this.irmsInterService.upYcapp("stone", stone.getStoneId()+"", id+"");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}
	
	/**
	 * 移动标石
	 * @param stone
	 */
	public void moveStone(StoneInfoBean stone){
		try{
			if(TextUtil.isNotNull(stone.getResNum())){
				Map<String, String> map= this.irmsInterService.getCityCountry(stone.getStoneArea());
				
				MoveResPojo obj = new MoveResPojo();
				obj.setAppType("moveStone");
				obj.setLatitude(stone.getLatitude());
				obj.setLongitude(stone.getLongitude());
				obj.setResId(stone.getResNum());
				obj.setResName(stone.getStoneName());
				obj.setResType("biaoshi");
				obj.setExtendXml("<fv k=\"OWNERSHIP\" v=\""+ResUtil.getOwnerShip(stone.getPropertyNature()+"")+"\"/>"
						+ "<fv k=\"RES_OWNER\" v=\""+ResUtil.getResOwner(stone.getPropertyComp()+"")+"\"/>");
				obj.setCityId(map.get("cityId"));
				obj.setCountyId(map.get("countyId"));
				obj.setAppCnType("移动标石");
				obj.setImId(stone.getStoneId()+"");
				obj.setImEnType("stone");
				obj.setImCnType("标石");
				obj.setRegion(stone.getStoneArea());
				//数据质量维护人
				if(TextUtil.isNotNull(stone.getDataQualitier()) && stone.getDataQualitier().contains("_")){
					obj.setQualitor(stone.getDataQualitier().split("_")[1]);
				}
				//一线维护人
				if(TextUtil.isNotNull(stone.getMaintainer())){
					obj.setMaintainor(stone.getMaintainer());
					List<Map<String, Object>> MainList = this.getMaintainList(stone.getMaintainer());
					if(TextUtil.isNotNull(MainList)){
						Map<String, Object> mainMap = MainList.get(0);
						String extendXml = obj.getExtendXml();
						extendXml+="<fv k=\"DAIWEI_ZU\" v=\""+mainMap.get("daiweiZu")+"\"/>"
							+ "<fv k=\"MAINTAIN_AREA\" v=\""+mainMap.get("maintainArea")+"\"/>";
						obj.setExtendXml(extendXml);
					}
				}
				this.irmsInterService.moveRes(obj);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 删除标石
	 * @param stone
	 */
	public void delStone(StoneInfoBean stone){
		try{
			if(TextUtil.isNotNull(stone.getResNum())){
				Map<String, String> map= this.irmsInterService.getCityCountry(stone.getStoneArea());
				
				MoveResPojo obj = new MoveResPojo();
				obj.setAppType("delStone");
				obj.setLatitude(stone.getLatitude());
				obj.setLongitude(stone.getLongitude());
				obj.setResId(stone.getResNum());
				obj.setResName(stone.getStoneName());
				obj.setResType("biaoshi");
				obj.setExtendXml("<fv k=\"OWNERSHIP\" v=\""+ResUtil.getOwnerShip(stone.getPropertyNature()+"")+"\"/>"
						+ "<fv k=\"RES_OWNER\" v=\""+ResUtil.getResOwner(stone.getPropertyComp()+"")+"\"/>");
				obj.setCityId(map.get("cityId"));
				obj.setCountyId(map.get("countyId"));
				obj.setAppCnType("删除标石");
				obj.setImId(stone.getStoneId()+"");
				obj.setImEnType("stone");
				obj.setImCnType("标石");
				obj.setRegion(stone.getStoneArea());
				//数据质量维护人
				if(TextUtil.isNotNull(stone.getDataQualitier()) && stone.getDataQualitier().contains("_")){
					obj.setQualitor(stone.getDataQualitier().split("_")[1]);
				}
				//一线维护人
				if(TextUtil.isNotNull(stone.getMaintainer())){
					obj.setMaintainor(stone.getMaintainer());
					List<Map<String, Object>> MainList = this.getMaintainList(stone.getMaintainer());
					if(TextUtil.isNotNull(MainList)){
						Map<String, Object> mainMap = MainList.get(0);
						String extendXml = obj.getExtendXml();
						extendXml+="<fv k=\"DAIWEI_ZU\" v=\""+mainMap.get("daiweiZu")+"\"/>"
							+ "<fv k=\"MAINTAIN_AREA\" v=\""+mainMap.get("maintainArea")+"\"/>";
						obj.setExtendXml(extendXml);
					}
				}
				this.irmsInterService.delRes(obj);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 增加撑点
	 * @param support
	 */
	public void addSupport(SupportPointPojo support){
		try{
			Map<String, String> map= this.irmsInterService.getCityCountry(support.getMaintArea());
			String xml ="<xmldata mode=\"SinglePointAddMode\">"
					+ "<mc type=\"chengdian\">"
					+ "<mo group=\"1\">"
					+ "<fv k=\"OWNERSHIP\" v=\"0\"/>"
					+ "<fv k=\"COUNTY_ID\" v=\""+map.get("countyId")+"\"/>"
					+ "<fv k=\"PROJECT_ID\" v=\"\"/>"
					+ "<fv k=\"INT_ID\" v=\"new-"+support.getId()+"\"/>"
					+ "<fv k=\"LATITUDE\" v=\""+support.getLatitude()+"\"/>"
					+ "<fv k=\"STATUS\" v=\"3\"/>"
					+ "<fv k=\"ZH_LABEL\" v=\""+support.getSportName()+"\"/>"
					+ "<fv k=\"CITY_ID\" v=\""+map.get("cityId")+"\"/>"
					+ "<fv k=\"LONGITUDE\" v=\""+support.getLongitude()+"\"/>"
					+ "<fv k=\"CREATOR\" v=\"YC_"+support.getMaintainer()+"\"/>"
					+ "</mo>"
					+ "</mc>"
					+ "</xmldata>"
					+ "";
			
			MoveResPojo pojo = new MoveResPojo();
			pojo.setInXml(xml);
			pojo.setAppType("addSupport");
			
			String id = this.irmsInterService.addRes(pojo,support.getId()+"");
			if(!(id.equals("error")) && TextUtil.isNotNull(support.getId())){
				String sql = "update job_support_point"
						+ " set resNum ='"+id+"'"
						+ " where id ='"+support.getId()+"'";
				this.jdbcTemplate.execute(sql);
				this.irmsInterService.upYcapp("support", support.getId()+"", id+"");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 移动撑点位置
	 * @param obj
	 */
	public void moveSupport(SupportPointPojo support){
		try{
			if(TextUtil.isNotNull(support.getResNum())){
				MoveResPojo obj = new MoveResPojo();
				obj.setAppType("moveSupport");
				obj.setLatitude(support.getLatitude());
				obj.setLongitude(support.getLongitude());
				obj.setResId(support.getResNum());
				obj.setResName(support.getSportName());
				obj.setResType("chengdian");
				this.irmsInterService.moveRes(obj);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 增加挂墙段
	 * @param hang
	 */
	public void addHangWall(HangWallPojo hang){
		try{
			Map<String, String> map= this.irmsInterService.getCityCountry(hang.getMaintArea());
			Map<String, Object> start = this.getSpotMap(hang.getStartDeviceId()+"", "support");
			Map<String, Object> end = this.getSpotMap(hang.getEndDeviceId()+"", "support");
			Map<String, String> sysMap = new LinkedHashMap<String, String>();
			sysMap.put("type", "hangwall");
			sysMap.put("start", start.get("resNum")+"");
			sysMap.put("end", end.get("resNum")+"");
			sysMap.put("city", map.get("cityId")+"");
			sysMap.put("county", map.get("countyId")+"");
			sysMap.put("lineId", hang.getId()+"");
			sysMap.put("lineName", hang.getHangWallName()+"");
			String key =this.irmsInterService.getKeyStr(hang.getHangWallName());
			sysMap.put("key", key);
			String sysId = this.getSystem(sysMap);
			sysMap.put("resType", "guaqiangduan");
			sysMap.put("startLon", start.get("longitude")+"");
			sysMap.put("startLat", start.get("latitude")+"");
			sysMap.put("startType", start.get("type")+"");
			sysMap.put("endType", end.get("type")+"");
			sysMap.put("endLon", end.get("longitude")+"");
			sysMap.put("endLat", end.get("latitude")+"");
			sysMap.put("sysId", sysId);
			sysMap.put("figueLength", hang.getHangLength()+"");
			sysMap.put("lineLength", hang.getMaintainLength()+"");
			String xml = this.irmsInterService.getLineInStr(sysMap);
			
			
			MoveResPojo pojo = new MoveResPojo();
			pojo.setInXml(xml);
			pojo.setAppType("addHangWall");
			
			String id = this.irmsInterService.addRes(pojo,hang.getId()+"");
			if(!(id.equals("error")) && TextUtil.isNotNull(hang.getId())){
				String sql = "update hang_wall"
						+ " set resNum ='"+id+"'"
						+ " where id ='"+hang.getId()+"'";
				this.jdbcTemplate.execute(sql);
				this.irmsInterService.upYcapp("hangwall",hang.getId()+"", id+"");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 增加引上段
	 * @param leadUp
	 */
	public String addLeadUp(LeadupPojo leadUp){
		String id = "";
		try{
			Map<String, String> map= this.irmsInterService.getCityCountry(leadUp.getMantainance());
			Map<String, Object> start = this.getSpotMap(leadUp.getStartId(), this.getDeviceType(leadUp.getStartType()));
			Map<String, Object> end = this.getSpotMap(leadUp.getEndId(), this.getDeviceType(leadUp.getEndType()));
			Map<String, String> sysMap = new LinkedHashMap<String, String>();
			sysMap.put("type", "ledUp");
			sysMap.put("start", start.get("resNum")+"");
			sysMap.put("end", end.get("resNum")+"");
			sysMap.put("city", map.get("cityId")+"");
			sysMap.put("county", map.get("countyId")+"");
			sysMap.put("lineId", leadUp.getId()+"");
			sysMap.put("lineName", leadUp.getLeadupName()+"");
			String key =this.irmsInterService.getKeyStr(leadUp.getLeadupName());
			sysMap.put("key", key);
			sysMap.put("ownerShip",leadUp.getPropertyNature()+"");
			sysMap.put("resOwner", leadUp.getPropertyComp()+"");
			String sysId = this.getSystem(sysMap);
			sysMap.put("resType", "yinshangduan");
			sysMap.put("startLon", start.get("longitude")+"");
			sysMap.put("startLat", start.get("latitude")+"");
			sysMap.put("startType", start.get("type")+"");
			sysMap.put("endType", end.get("type")+"");
			sysMap.put("endLon", end.get("longitude")+"");
			sysMap.put("endLat", end.get("latitude")+"");
			sysMap.put("sysId", sysId);
			sysMap.put("figueLength",leadUp.getLength()+"");
			if(TextUtil.isNull(leadUp.getMaintainLength())) {
				leadUp.setMaintainLength(leadUp.getLength());
			}
			sysMap.put("lineLength", leadUp.getMaintainLength());
			String xml = this.irmsInterService.getLineInStr(sysMap);
			System.out.println("输入参数============"+xml);
			
			MoveResPojo pojo = new MoveResPojo();
			pojo.setInXml(xml);
			pojo.setAppType("addleadup");
			pojo.setAppCnType("增加引上");
			pojo.setImId(leadUp.getId()+"");
			pojo.setImCnType("引上段");
			pojo.setImEnType("leadupSeg");
			pojo.setResName(leadUp.getLeadupName());
			pojo.setMaintainor(leadUp.getMaintainer());
			pojo.setRegion(leadUp.getMantainance());
			
			id = this.irmsInterService.addRes(pojo,leadUp.getId()+"");
			if(!(id.equals("error")) && TextUtil.isNotNull(leadUp.getId())){
				String sql = "update leadupstage"
						+ " set resNum ='"+id+"'"
						+ " where id ='"+leadUp.getId()+"'";
				this.jdbcTemplate.execute(sql);
				this.irmsInterService.upYcapp("leadup",leadUp.getId()+"", id+"");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
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
	/**
	 * 只试用引上
	 * @param type
	 * @return
	 */
	String getDeviceType(int type){
		String result ="";
		if(type == 0){
			result ="pole";
		} else if(type == 1){
			result ="stone";
		}else if(type ==2){
			result = "well";
		}else if(type ==3){
			result = "support";
		}else if(type == 4){
			result = "site";
		}else if(type == 5){
			result = "equtinfo";
		}
		return result;
	}
	
	/**
	 * 增加管孔
	 * @param tube
	 */
	public void addTube(List<TubeInfoBean> tubeList,String pipeRes){
		try{
			String xml ="<xmldata mode=\"SinglePointAddMode\">"
				+ "<mc type=\"guankong\">";
			for(int i=0;i<tubeList.size();i++){
				TubeInfoBean tube = tubeList.get(i);
				xml +="<mo group=\""+(i+1)+"\">"
					+ "<fv k=\"CONDUIT_ID\" v=\""+pipeRes+"\"/>"
					+ "<fv k=\"DUCTNUMBER\" v=\""+tube.getTubeNumber()+"\"/>"
					+ "<fv k=\"INT_ID\" v=\"new-"+tube.getTubeId()+"\"/>"
					+ "<fv k=\"ZH_LABEL\" v=\""+tube.getTubeName()+"\"/>"
					+ "<fv k=\"USER_STATUS\" v=\"2\"/>"
					+ "<fv k=\"SUB_PORE_NUM\" v=\""+tube.getSubTubeAmount()+"\"/>"
					+ "<fv k=\"STATUS\" v=\"3\"/>"
					+ "</mo>";
			}
			xml += "</mc>"
				+ "</xmldata>";
			String jsonString = "xml="+URLEncoder.encode(xml, "UTF-8");
			String outIN = RequestUtil.HttpRequest(InterfaceAddr.ADD_RESOURCE, "POST", jsonString);
			IrmsPojo obj = new IrmsPojo();
			obj.setInStr(xml);
			obj.setOutStr(outIN);
			obj.setFaceType("addTube");
			irmsInterService.addInterLog(obj);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到点的数据
	 * @param id
	 * @param type
	 * @return
	 */
	Map<String, Object> getSpotMap(String id,String type){
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		String sql ="";
		//电杆数据
		if(type.equals("pole")){
			sql ="select poleId,poleName,poleNo,"+getPropert.getValueByKey("poleType")+" as type,"
				+ " poleLongitude as longitude,poleLatitude as latitude,resNum"
				+ " from poleinfo"
				+ " where poleId ='"+id+"'";
		}
		//井数据
		if(type.equals("well")){
			sql ="select wellId ,wellName,wellNo,"+getPropert.getValueByKey("wellType")+" as type,"
				+ " longitude as longitude,latitude as latitude,region,resNum"
				+ " from wellinfo where wellId ='"+id+"'";
		}
		//标石数据
		if(type.equals("stone")){
			sql="select stoneId,stoneName,stoneNum,"+getPropert.getValueByKey("stoneType")+" as type,"
				+ " stoneArea,latitude as latitude,longitude as longitude,resNum"
				+ " from stoneinfo where stoneId ='"+id+"'";
		}
		//撑点
		if(type.equals("support")){
			sql ="select id,sportName,maintArea,"+getPropert.getValueByKey("supportType")+" as type,"
				+ " longitude,latitude,resNum"
				+ " from job_support_point where id ='"+id+"'";
		}
		//站点
		if(type.equals("site")){
			sql ="select t.stationBaseId,t.stationName,"+getPropert.getValueByKey("siteType")+" as type,"
					+ " t.region,t.lon as longitude ,t.lat as latitude,t.resNum"
					+ " from job_stationbase t where t.stationBaseId ='"+id+"'";
		}
		//光交箱
		if(type.equals("equtinfo")){
			sql =" select t.id,t.ENAME,t.EADDR,"+getPropert.getValueByKey("opticTran")+" as type, "
				+ " t.lon as longitude ,t.LAT as latitude,t.resNum"
				+ "  from job_equtinfo t where t.ETYPE = 3 and t.EID ='"+id+"'";
		}
		return this.jdbcTemplate.queryForMap(sql);
	}
	
	
	/**
	 * 
	 * @param sysId
	 * @param resNum
	 * @param type
	 * @return
	 */
	public String getSystem(String sysId,String resNum,String type){
		String sysNum ="";
		String sql = "";
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		try{
			if(TextUtil.isNotNull(sysId)){
				sql ="select lineName,lineType,lineArea,resNum"
						+ " from line_system where deleteflag = 0"
						+ " and id ='"+sysId+"'";
				list = this.jdbcTemplate.queryForList(sql);
				if(TextUtil.isNotNull(list)){
					Map<String, Object> map = list.get(0);
					sysNum = map.get("resNum")+"";
				}
			}
			if(TextUtil.isNull(sysNum) || sysNum.equals("null")){
				String table = "";
				if(type.equals("pipeLine")){
					table="RES_PIPE_SEG";
				}else if(type.equals("poleLine")){
					table ="RES_POLE_ROAD_SEG";
				}else if(type.equals("buried")){
					table ="RES_DIRE_BURY_SEG";
				}else if(type.equals("leadup")){
					table ="RES_ONTO";
				}
				if(TextUtil.isNotNull(table)){
					sql = "select RELATED_SYSTEM "
						+ " from "+table+" t"
						+ " where t.INT_ID = '"+resNum+"'";
					list  = this.irmsjdbcTemplate.queryForList(sql);
					if(TextUtil.isNotNull(list)){
						Map<String, Object> map = list.get(0);
						sysNum = map.get("RELATED_SYSTEM")+"";
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sysNum;
	}
	
	/**
	 * 得到所属的管线系统
	 * @param sysId
	 * @return
	 */
	public String getSystem(String sysId){
		String sql ="select lineName,lineType,lineArea,resNum"
				+ " from line_system where deleteflag = 0"
				+ " and id ='"+sysId+"'";
		String sysNum = "";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			Map<String, Object> map= list.get(0);
			String resNum = map.get("resNum")+"";
			if(TextUtil.isNotNull(resNum)){
				sysNum = resNum;
			}else{
				LineSystemInfo line = new LineSystemInfo();
				line.setLineName(map.get("lineName")+"");
				line.setLineType(Integer.parseInt(map.get("lineType")+""));
				line.setLineArea(map.get("lineArea")+"");
				sysNum = this.addSystem(line);
				String upSql ="update line_system set resNum ='"+sysNum+"'"
						+ " where id='"+sysId+"'"
						+ "";
				this.jdbcTemplate.update(upSql);
			}
		}
		return sysNum;
	}
	
	
	/**
	 * 新增一个外线系统
	 * @param line
	 * @return
	 */
	public String addSystem(LineSystemInfo line){
		String sysId = "";
	    Map<String, String> map= this.irmsInterService.getCityCountry(line.getLineArea());
		String type = "";
		if(line.getLineType().equals(0)){
			//杆路
			type = "poleLine";
		}else if(line.getLineType().equals(1)){
			//直埋
			type = "buried";
		}else if(line.getLineType().equals(2)){
			//管线
			type = "pipeLine";
		}
		String sysName = line.getLineName();
		String sql = "select int_id as sysId from RES_LINESEGSYSTEM where"
				+ " lineseg_type ='"+getPropert.getValueByKey(type)+"'"
				+ " and zh_label ='"+sysName+"'";
		List<Map<String,Object>> list = this.irmsjdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			Map< String, Object> result = list.get(0);
			sysId = result.get("SYSID")+"";
		}else{
			sysId = irmsInterService.addSys(sysName, map.get("cityId"), map.get("countyId"),type,line);
		}
		return sysId;
	}
	
	/**
	 * 北京的处理方式
	 * @param map
	 * @return
	 */
	String getBjSystem(Map<String, String> map,String imSys){
		String sysId = "";
		String sql = "";
		sql = "select resNum from line_system "
			+ " where id ='"+imSys+"' and resNum is not null ";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			Map<String, Object> resMap = list.get(0);
			sysId = resMap.get("resNum")+"";
		}else{
			sysId = irmsInterService.addSys(map.get("sysName"), map.get("city"), map.get("county"), map.get("type"),null);
			if(isNumeric(sysId)) {
				String upSql = " update line_system set resNum = '"+sysId+"'"
						+ " where id ='"+imSys+"' ";
				this.jdbcTemplate.execute(upSql);
			}
		}
		return sysId;
	}
	
	/**
	 * 得到系统
	 * @param map
	 * @return
	 */
	String getSystem(Map<String, String> map){
		String sysId = "";
		String sql = "";
		//先到表里面查看下是否属于某个系统
		if(map.get("type").equals("poleLine")){
			sql ="select related_system as sysId from"
				+ " RES_POLE_ROAD_SEG where a_object_id in ('"+map.get("start")+"','"+map.get("end")+"')"
				+ " and related_system is not null"
				+ " union all"
				+ " select related_system as sysId from RES_POLE_ROAD_SEG"
				+ " where z_object_id in ('"+map.get("start")+"','"+map.get("end")+"')"
				+ " and related_system is not null ";
		}else if(map.get("type").equals("buried")){
			sql ="select related_system as sysId from RES_DIRE_BURY_SEG "
				+ " where a_object_id in ('"+map.get("start")+"','"+map.get("end")+"')"
				+ " and related_system is not null "
				+ " union all"
				+ " select related_system as sysId from RES_DIRE_BURY_SEG "
				+ " where z_object_id in ('"+map.get("start")+"','"+map.get("end")+"')"
				+ " and related_system is not null"
				+ "";
		}else if(map.get("type").equals("pipeLine")){
			sql ="select related_system as sysId from RES_PIPE_SEG "
				+ " where a_object_id in ('"+map.get("start")+"','"+map.get("end")+"')"
				+ " and related_system is not null "
				+ " union all"
				+ " select related_system as sysId from RES_PIPE_SEG "
				+ " where z_object_id in ('"+map.get("start")+"','"+map.get("end")+"')"
				+ " and related_system is not null"
				+ "";
		}
		if(TextUtil.isNull(sql)) {
			LineSystemInfo info = new LineSystemInfo();
			info.setId(Integer.parseInt(map.get("lineId")+""));
			sysId = irmsInterService.addSys(map.get("lineName"), map.get("city"), map.get("county"), map.get("type"),info);
		}else {
			List<Map<String,Object>> list = this.irmsjdbcTemplate.queryForList(sql);
			//如果两个点没有保存系统
			if(TextUtil.isNull(list)){
				sql = "select int_id as sysId from RES_LINESEGSYSTEM where"
					+ " city_id ='"+map.get("city")+"' and county_id ='"+map.get("county")+"'"
					+ " and lineseg_type ='"+getPropert.getValueByKey(map.get("type"))+"'"
					+ " and zh_label like '%"+map.get("key")+"%'";
				list = this.irmsjdbcTemplate.queryForList(sql);
			}
			
			if(TextUtil.isNotNull(list)){
				Map< String, Object> result = list.get(0);
				sysId = result.get("SYSID")+"";
			}else{
				sysId = irmsInterService.addSys(map.get("lineName"), map.get("city"), map.get("county"), map.get("type"),null);
			}
		}
		
		return sysId;
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
	 * 添加接口记录
	 * @param obj
	 * @return
	 */
	public int addInterLog(IrmsPojo obj){
		String sql = "insert into irms_interface(inStr,outStr,faceType,faceTime,faceResult)"
				+ "values ("
				+ " '"+obj.getInStr()+"', "
				+ " '"+obj.getOutStr()+"', "
				+ " '"+obj.getFaceType()+"', "
				+ " now(), "
				+ " '"+obj.getFaceResult()+"'"
				+ ")";
		this.jdbcTemplate.execute(sql);
		return 1;
	}
	
	/**
	 * 判断是否是数字
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str){ 
		 Pattern pattern = Pattern.compile("[0-9]*"); 
		 Matcher isNum = pattern.matcher(str);
		 if( !isNum.matches() ){
		    return false; 
		 } 
		 return true; 
	}
	
	public IapprovalTaskService getApprovalService() {
		return approvalService;
	}
	public void setApprovalService(IapprovalTaskService approvalService) {
		this.approvalService = approvalService;
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
	public JdbcTemplate getIrmsjdbcTemplate() {
		return irmsjdbcTemplate;
	}
	public void setIrmsjdbcTemplate(JdbcTemplate irmsjdbcTemplate) {
		this.irmsjdbcTemplate = irmsjdbcTemplate;
	}
}
