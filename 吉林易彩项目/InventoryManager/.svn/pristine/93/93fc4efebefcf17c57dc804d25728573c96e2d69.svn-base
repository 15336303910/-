package manage.V2.hebei.mainTask.service.impl;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import javax.servlet.ServletContext;

import manage.V2.hebei.mainTask.pojo.ErrorInfoBean;
import manage.V2.hebei.mainTask.pojo.LocusPoint;
import manage.V2.hebei.mainTask.pojo.PhotoInfoBean;
import manage.V2.hebei.mainTask.pojo.PointlikeResourceInfoBean;
import manage.V2.hebei.mainTask.pojo.ResourceInfoBean;
import manage.V2.hebei.mainTask.pojo.ResourceLineBean;
import manage.V2.hebei.mainTask.pojo.ResourceRelationBean;
import manage.V2.hebei.mainTask.pojo.RouteInfoBean;
import manage.V2.hebei.mainTask.pojo.SegInfoBean;
import manage.V2.hebei.mainTask.pojo.SegInfoBeanAZ;
import manage.V2.hebei.mainTask.service.MainTaskService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import base.V2.hebei.database.DataBase4HB;
public class MainTaskServiceImpl extends DataBase4HB implements MainTaskService {
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	private static final Logger log = Logger.getLogger(MainTaskServiceImpl.class);
	private static final String GET_ROUTE = "mainTask.getRouteInfo";
	private static final String GET_RESOURCE_RELATION = "mainTask.getResourceRelation";
	private static final String GET_ROOM_INFO = "mainTask.getRoomInfo";
	private static final String GET_PIPE_INFO = "mainTask.getPipeInfo";
	private static final String GET_POLE_INFO = "mainTask.getPoleInfo";
	private static final String GET_STONE_INFO = "mainTask.getStoneInfo";
	private static final String GET_LIGHT_INFO = "mainTask.getLightInfo";
	private static final String GET_SUPPLY_INFO = "mainTask.getSupplyInfo";

	private Double PI = 3.14159265358979324;
	
	/**
	 * 巡检任务主键接口（出发）
	 * 返回路由ID主键
	 */
	public int getRouteInfo(String uid,String jsonParams) {
		Integer routeId = 1;
		try {
			RouteInfoBean rInfoBean = new RouteInfoBean();
			rInfoBean.setUserId(uid);
			/*rInfoBean = (RouteInfoBean) getObject(GET_ROUTE, rInfoBean);
			System.out.println("rInfoBean==="+rInfoBean);*/
			//将routeId入库
			insert("mainTask.getRouteID",rInfoBean);
			routeId = rInfoBean.getID();
		} catch (Exception e) {
			routeId = null;
		}
		return routeId;
	}

	/**
	 * 拍照上传接口
	 * 返回业务数据
	 */
	public String savePhoto(String uid,String jsonParams,ServletContext servletContext) {
		String returnStr = "";
		try {
			String path = "";
			JSONArray jsonArray = JSONArray.fromObject(jsonParams);
			for(int i=0;i<jsonArray.size();i++){
				JSONObject jso = jsonArray.getJSONObject(i);
				PhotoInfoBean photoInfoBean = (PhotoInfoBean) jso.toBean(jso, PhotoInfoBean.class);
				String type = photoInfoBean.getPhotoType();//照片类型
				photoInfoBean.setCreateTime(new Date());//创建时间
				if("start".equals(type.toLowerCase())){//起点
					path = servletContext.getRealPath("/")+"uploadPhoto/startPointPhoto/";
					photoInfoBean.setPath(path);
					insert("mainTask.savePhotoInfo",photoInfoBean);
				}else if("end".equals(type.toLowerCase())){//终点
					path = servletContext.getRealPath("/")+"uploadPhoto/endPointPhoto/";
					photoInfoBean.setPath(path);
					insert("mainTask.savePhotoInfo",photoInfoBean);
				}else if("way".equals(type.toLowerCase())){//过程
					path = servletContext.getRealPath("/")+"uploadPhoto/wayPhoto/";
					photoInfoBean.setPath(path);
					insert("mainTask.savePhotoInfo",photoInfoBean);
				}else if("error".equals(type.toLowerCase())){//隐患
					path = servletContext.getRealPath("/")+"uploadPhoto/errorPhoto/";
					photoInfoBean.setPath(path);
					insert("mainTask.savePhotoInfo",photoInfoBean);
				}
				
				//封装返回文件名集合
				returnStr += photoInfoBean.getPhotoName()+",";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(StringUtils.isNotBlank(returnStr)){
			returnStr = returnStr.substring(0, returnStr.length()-1);
		}
		return returnStr;
	}

	/**
	 * 关联资源接口
	 * 返回对应数据
	 */
	public String getRalitonRes(String uid, String jsonParams) {
		String mess = "";
		List<PointlikeResourceInfoBean> finalList = new ArrayList<PointlikeResourceInfoBean>();
		JSONObject jso = JSONObject.fromObject(jsonParams);
		PointlikeResourceInfoBean pBean = (PointlikeResourceInfoBean) jso.toBean(jso, PointlikeResourceInfoBean.class);
		String resourceType = pBean.getResourceType();
		Double lati = pBean.getLatitude();
		Double longi = pBean.getLongitude();
		//转换坐标
		/*try {
			String s = transGCJ02ToWGS84(longi, lati);
			String[] aa = s.split(",");
			longi = Double.parseDouble(aa[0]);
			lati = Double.parseDouble(aa[1]);
			log.debug("转换后的坐标：Longi==="+longi+",Lati==="+lati);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		Double xMax = longi+(100.0/33/3600);
		Double xMin = longi-(100.0/33/3600);
		Double yMax = lati+(100.0/33/3600);
		Double yMin = lati-(100.0/33/3600);
		log.debug("xMax==="+xMax+",xMin==="+xMin+",yMax==="+yMax+",yMin==="+yMin);
		ResourceRelationBean tempBean = new ResourceRelationBean();
		tempBean.setResourceType(resourceType);
		List<ResourceRelationBean> rrBeanList = getObjects(GET_RESOURCE_RELATION,tempBean);
		log.debug("rrBeanList===="+rrBeanList);
		for(int i=0;null!=rrBeanList && i<rrBeanList.size();i++){
			ResourceRelationBean rrBean = rrBeanList.get(i);
			log.debug("reType==="+rrBean.getResourceType()+",,,,bTable==="+rrBean.getBelongTable());
			String belongTable = rrBean.getBelongTable();
			ResourceRelationBean returnBean = new ResourceRelationBean();
			returnBean.setBelongTable(belongTable);
			returnBean.setxMax(xMax);
			returnBean.setxMin(xMin);
			returnBean.setyMax(yMax);
			returnBean.setyMin(yMin);
			List<ResourceRelationBean> returnBeanList = null;
			if("rms_site".equals(belongTable)){//机房
				returnBeanList = getObjects(GET_ROOM_INFO,returnBean);
			}else if("res_markstone".equals(belongTable)){//标石
				returnBeanList = getObjects(GET_STONE_INFO,returnBean);
			}else if("res_opti_tran_box".equals(belongTable)){//光交 (替换为资源点)
				returnBeanList = getObjects(GET_LIGHT_INFO,returnBean);
			}else if("res_pole".equals(belongTable)){//电杆
				returnBeanList = getObjects(GET_POLE_INFO,returnBean);
			}else if("res_staff_well".equals(belongTable)){//管井
				returnBeanList = getObjects(GET_PIPE_INFO,returnBean);
			}else if("res_supp_point".equals(belongTable)){//撑点
				returnBeanList = getObjects(GET_SUPPLY_INFO,returnBean);
			}
			if(returnBeanList==null){
				mess = "没有查询到资源信息";
				return mess;
			}
			for(int j=0;null!=returnBeanList && j<returnBeanList.size();j++){
				ResourceRelationBean rBean = returnBeanList.get(j);
				PointlikeResourceInfoBean fBean = new PointlikeResourceInfoBean();
				fBean.setResourceID(rBean.getInt_id());
				fBean.setResourceName(rBean.getZh_label());
				fBean.setResourceType(resourceType);
				fBean.setLongitude(rBean.getLongitude());
				fBean.setLatitude(rBean.getLatitude());
				if("rms_site".equals(belongTable)){//机房
					fBean.setResourceType("机房");
				}else if("res_markstone".equals(belongTable)){//标石
					fBean.setResourceType("标石");
				}else if("res_opti_tran_box".equals(belongTable)){//光交(资源点)
					fBean.setResourceType("资源点");
				}else if("res_pole".equals(belongTable)){//电杆
					fBean.setResourceType("电杆");
				}else if("res_staff_well".equals(belongTable)){//管井
					fBean.setResourceType("管井");
				}else if("res_supp_point".equals(belongTable)){//撑点
					fBean.setResourceType("撑点");
				}

				finalList.add(fBean);
			}
		}
		JSONArray jArray = JSONArray.fromObject(finalList);
		mess = jArray.toString();
		
		return mess;
	}

	/**
	 * 隐患上传接口
	 * 返回业务数据
	 */
	public String saveError(String uid, String jsonParams,ServletContext servletContext) {
		JSONObject jsonObject = JSONObject.fromObject(jsonParams);
		ErrorInfoBean errorInfoBean = (ErrorInfoBean) jsonObject.toBean(jsonObject, ErrorInfoBean.class);
		errorInfoBean.setCreateTime(new Date());
		//存储隐患信息
		insert("mainTask.saveErrorInfo",errorInfoBean);
		
		//处理照片信息
		JSONArray errorPhotoJsonArray = (JSONArray) jsonObject.get("files");
		if(null!=errorPhotoJsonArray && errorPhotoJsonArray.size()!=0){//存在照片信息
			for(int j=0;null!=errorPhotoJsonArray && j<errorPhotoJsonArray.size();j++){
				JSONObject photoJso = errorPhotoJsonArray.getJSONObject(j);
				PhotoInfoBean photoBean = (PhotoInfoBean) photoJso.toBean(photoJso,PhotoInfoBean.class);
				photoBean.setPath(servletContext.getRealPath("uploadPhoto/errorPhoto/"));
				photoBean.setCreateTime(new Date());
				//存储隐患照片信息
				insert("mainTask.savePhotoInfo",photoBean);
			}
		}
		return "";
	}

	/**
	 * 巡查任务数据上传接口
	 * 返回业务数据
	 * @throws Exception 
	 */
	public String saveTask(String uid, String jsonParams,ServletContext servletContext){
		JSONObject returnBeanObject = null;
		try {
			JSONObject jsonObject = JSONObject.fromObject(jsonParams);
			RouteInfoBean routeInfoBean = (RouteInfoBean) jsonObject.toBean(jsonObject, RouteInfoBean.class);
			Integer routeState = routeInfoBean.getRouteState();
			Integer routeId = routeInfoBean.getRouteID();
			//查询flag标识 如果为1 则不进行存储信息 只进行匹配率计算
			RouteInfoBean tempBean = new RouteInfoBean();
			tempBean.setRouteID(routeId);
			tempBean.setUserId(uid);
			tempBean = (RouteInfoBean) getObject("mainTask.getRouteInfo",tempBean);
			String flag = tempBean.getFlag();
			
			List<LocusPoint> wayList = new ArrayList<LocusPoint>();//用于存放轨迹信息
			
			JSONObject startJsonObject = (JSONObject) jsonObject.get("startPosition");
			PointlikeResourceInfoBean startPointBean = (PointlikeResourceInfoBean) startJsonObject.toBean(startJsonObject, PointlikeResourceInfoBean.class);
			JSONObject endJsonObject = (JSONObject) jsonObject.get("endPosition");
			PointlikeResourceInfoBean endPointBean = (PointlikeResourceInfoBean) endJsonObject.toBean(endJsonObject, PointlikeResourceInfoBean.class);
			
			String startPointName = startPointBean.getResourceName();//起点资源名称
			String endPointName = endPointBean.getResourceName();//终点资源名称
			
			//处理起点信息
			startPointBean.setCreateTime(new Date());
			startPointBean.setType(0);
			if(!"1".equals(flag)){
				//存储起点信息
				insert("mainTask.savePointInfo",startPointBean);
			}
			//得到起点信息中的照片信息
			JSONArray startPhotoJsonArray = (JSONArray) startJsonObject.get("files");
			if(null!=startPhotoJsonArray && startPhotoJsonArray.size()!=0){//存在照片信息
				for(int i=0;i<startPhotoJsonArray.size();i++){
					JSONObject startPhotoJso = startPhotoJsonArray.getJSONObject(i);
					PhotoInfoBean startPhotoBean = (PhotoInfoBean) startPhotoJso.toBean(startPhotoJso, PhotoInfoBean.class);
					if(servletContext!=null){
						startPhotoBean.setPath(servletContext.getRealPath("uploadPhoto/startPointPhoto/"));
					}
					startPhotoBean.setCreateTime(new Date());
					if(!"1".equals(flag)){
						//存储起点照片信息
						insert("mainTask.savePhotoInfo",startPhotoBean);
					}
				}
			}
			//处理终点信息
			endPointBean.setCreateTime(new Date());
			endPointBean.setType(1);
			if(!"1".equals(flag)){
				//存储起点信息
				insert("mainTask.savePointInfo",endPointBean);
			}
			//得到起点信息中的照片信息
			JSONArray endPhotoJsonArray = (JSONArray) endJsonObject.get("files");
			if(null!=endPhotoJsonArray && endPhotoJsonArray.size()!=0){//存在照片信息
				for(int i=0;i<endPhotoJsonArray.size();i++){
					JSONObject endPhotoJso = endPhotoJsonArray.getJSONObject(i);
					PhotoInfoBean endPhotoBean = (PhotoInfoBean) endPhotoJso.toBean(endPhotoJso, PhotoInfoBean.class);
					if(servletContext!=null){
						endPhotoBean.setPath(servletContext.getRealPath("uploadPhoto/endPointPhoto/"));
					}
					endPhotoBean.setCreateTime(new Date());
					if(!"1".equals(flag)){
						//存储起点照片信息
						insert("mainTask.savePhotoInfo",endPhotoBean);
					}
				}
			}
			
			//处理轨迹信息
			//轨迹点周边100米范围之内的资源点
			List<String> pointListNearbyTrail = new ArrayList<String>();
			
			JSONArray wayJsonArray = (JSONArray) jsonObject.get("locusPoints");
			final List<LocusPoint> valueList = new ArrayList<LocusPoint>();
//			String sbStr = "select int_id as resourceID,zh_label as resourceName,resource_type as resourceType,longitude,latitude from RES_RESOURCE_POINT where longitude > #xMin and longitude < #xMax and latitude > #yMin and latitude < #yMax ";
//			StringBuffer sb = new StringBuffer("");
			if(null!=wayJsonArray && wayJsonArray.size()!=0){//存在轨迹信息
				for(int i=0;i<wayJsonArray.size();i++){
					JSONObject wayJso = wayJsonArray.getJSONObject(i);
					LocusPoint wayBean = (LocusPoint) wayJso.toBean(wayJso,LocusPoint.class);
					wayBean.setCreateTime(new Date());
					//将轨迹信息放入集合中 计算匹配率使用
					wayList.add(wayBean);
					
//					Double longitude = wayBean.getLongitude();
//					Double latitude = wayBean.getLatitude();
//					Double xMax = longitude+(100.0/33/3600);
//					Double xMin = longitude-(100.0/33/3600);
//					Double yMax = latitude+(100.0/33/3600);
//					Double yMin = latitude-(100.0/33/3600);
//					/*ResourceRelationBean fBean = new ResourceRelationBean();
//					fBean.setxMax(xMax);
//					fBean.setxMin(xMin);
//					fBean.setyMax(yMax);
//					fBean.setyMin(yMin);*/
//					//获取周围的资源信息
//					/*List<ResourceInfoBean> fBeanList = getObjects("mainTask.queryResInfo",fBean);
//					List<ResourceLineBean> lineList = new ArrayList<ResourceLineBean>();
//					Map<Integer,ResourceInfoBean> map = new HashMap<Integer,ResourceInfoBean>();
//					for(int j=0;null!=fBeanList && j<fBeanList.size();j++){
//						ResourceInfoBean pointResource = fBeanList.get(j);
//						String resourceId = String.valueOf(pointResource.getResourceID());
//						if(!pointListNearbyTrail.contains(resourceId))
//							pointListNearbyTrail.add(resourceId);
//					}
//					*/
//					
//					String tmpStr = sbStr;
//					tmpStr = tmpStr.replaceAll("\\#xMin", String.valueOf(xMin));
//					tmpStr = tmpStr.replaceAll("\\#xMax", String.valueOf(xMax));
//					tmpStr = tmpStr.replaceAll("\\#yMin", String.valueOf(yMin));
//					tmpStr = tmpStr.replaceAll("\\#yMax", String.valueOf(yMax));
//
//					if(sb.toString().equals(""))
//						sb.append(tmpStr);
//					else{
//						sb.append(" union ").append(tmpStr);
//					}
//					if(i%100 == 0){
//						List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString());
//						for(Map<String,Object> mapObj : list){
//							String resourceId = mapObj.get("resourceID").toString();
//							if(!pointListNearbyTrail.contains(resourceId))
//								pointListNearbyTrail.add(resourceId);
//						}
//						sb = new StringBuffer("");
//					}
				}
			}
			valueList.addAll(wayList);
			/*
			 * 剩下的点
			 */
			/*List<Map<String,Object>> dumplist = jdbcTemplate.queryForList(sb.toString());
			for(Map<String,Object> dumpmapObj : dumplist){
				String dumpresourceId = dumpmapObj.get("resourceID").toString();
				if(!pointListNearbyTrail.contains(dumpresourceId))
					pointListNearbyTrail.add(dumpresourceId);
			}
			*/
			
			
			if(!"1".equals(flag)){
				String insertSql = "INSERT INTO HEBEI_TRAIL(ID, CREATE_TIME, LONGITUDE, LATITUDE, DESCRIPTION, AREA, ROUTE_ID) "+
						"VALUES(SEQ_HEBEI_TRAIL.nextval,sysdate,?,?,?,?,?)";
				jdbcTemplate.batchUpdate(insertSql, 
						new BatchPreparedStatementSetter() {
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return valueList.size();
					}
					@Override
					public void setValues(PreparedStatement ps,
							int i) throws SQLException {
						// TODO Auto-generated method stub
						LocusPoint lpoint = valueList.get(i);
						ps.setDouble(1, lpoint.getLongitude());
						ps.setDouble(2, lpoint.getLatitude());
						ps.setString(3, lpoint.getDescription());
						ps.setString(4, lpoint.getArea());
						ps.setInt(5, lpoint.getRouteID());
					}
					
				});
			}
			
			/**
			 * 调用存储过程
			 */		
			try{
				System.out.println(new Date()+"-----SUBMITTRANS_TEST start ");
				jdbcTemplate.execute("{call SUBMITTRANS_TEST( "+routeId+",120) }");
				String sb = " select * from tmp_route_gis_"+routeId;
				List<Map<String,Object>> dumplist = jdbcTemplate.queryForList(sb.toString());
				for(Map<String,Object> dumpmapObj : dumplist){
					String dumpresourceId = dumpmapObj.get("RESOURCEID").toString();
					if(!pointListNearbyTrail.contains(dumpresourceId))
						pointListNearbyTrail.add(dumpresourceId);
				}
//				this.jdbcTemplate.execute("drop table tmp_route_gis_"+routeId);
				System.out.println(new Date()+"-----SUBMITTRANS_TEST end ");
			}catch(Exception e){
				e.printStackTrace();
			}
			
			/*Double longitude = wayBean.getLongitude();
			Double latitude = wayBean.getLatitude();
			Double xMax = longitude+(80.0/33/3600);
			Double xMin = longitude-(80.0/33/3600);
			Double yMax = latitude+(80.0/33/3600);
			Double yMin = latitude-(80.0/33/3600);
			ResourceRelationBean fBean = new ResourceRelationBean();
			fBean.setxMax(xMax);
			fBean.setxMin(xMin);
			fBean.setyMax(yMax);
			fBean.setyMin(yMin);
			//获取周围的资源信息
			List<ResourceInfoBean> fBeanList = getObjects("mainTask.queryResInfo",fBean);
			List<ResourceLineBean> lineList = new ArrayList<ResourceLineBean>();
			Map<Integer,ResourceInfoBean> map = new HashMap<Integer,ResourceInfoBean>();
			for(int j=0;null!=fBeanList && j<fBeanList.size();j++){
				ResourceInfoBean pointResource = fBeanList.get(j);
				String resourceId = String.valueOf(pointResource.getResourceID());
				if(!pointListNearbyTrail.contains(resourceId))
					pointListNearbyTrail.add(resourceId);
			}*/
			
			
			//处理轨迹照片信息
			
			

//			JSONArray wayPhotoJsonArray = (JSONArray) jsonObject.get("locusPhotos");
			JSONArray wayPhotoJsonArray = (JSONArray) jsonObject.get("locusResourcePosition");

			if(null!=wayPhotoJsonArray && wayPhotoJsonArray.size()!=0){//存在轨迹照片信息
				for(int i=0;i<wayPhotoJsonArray.size();i++){
					JSONObject wayPhotoJso = wayPhotoJsonArray.getJSONObject(i);
//					PhotoInfoBean wayPhotoBean = (PhotoInfoBean) wayPhotoJso.toBean(wayPhotoJso, PhotoInfoBean.class);
//					PointlikeResourceInfoBean pResourceBean = (PointlikeResourceInfoBean) wayPhotoJso.toBean(wayPhotoJso, PointlikeResourceInfoBean.class);
					JSONArray middleFiles = (JSONArray) wayPhotoJso.get("files");
					pointOrderQueue.add(String.valueOf(wayPhotoJso.get("resourceID")));
					if(null!=middleFiles && middleFiles.size()!=0){//存在照片信息
						for(int j=0;j<middleFiles.size();j++){
							JSONObject photoJso = middleFiles.getJSONObject(j);
							PhotoInfoBean wayPhotoBean = (PhotoInfoBean) photoJso.toBean(photoJso, PhotoInfoBean.class);
							if(servletContext!=null){
								wayPhotoBean.setPath(servletContext.getRealPath("uploadPhoto/wayPhoto/"));
							}
							wayPhotoBean.setPath(servletContext.getRealPath("uploadPhoto/wayPhoto/"));
							if(!"1".equals(flag)){
								//存储起点照片信息
								insert("mainTask.savePhotoInfo",wayPhotoBean);
							}
						}
					}
					
					/*ArrayList<PhotoInfoBean> files = pResourceBean.getFiles();
					pointOrderQueue.add(String.valueOf(pResourceBean.getResourceID()));
					if(null != files && files.size()>0){
						for(PhotoInfoBean wayPhotoBean : files){
							if(servletContext!=null){
								wayPhotoBean.setPath(servletContext.getRealPath("uploadPhoto/wayPhoto/"));
							}
							wayPhotoBean.setCreateTime(new Date());
							if(!"1".equals(flag)){
								//存储轨迹照片信息
								insert("mainTask.savePhotoInfo",wayPhotoBean);
							}
						}
					}*/
				}
			}
			
			//处理隐患信息
			JSONArray errorJsonArray = (JSONArray) jsonObject.get("errors");
			if(null!=errorJsonArray && errorJsonArray.size()!=0){//存在隐患信息
				for(int i=0;i<errorJsonArray.size();i++){
					JSONObject errorJsonObject = errorJsonArray.getJSONObject(i);
					ErrorInfoBean errorBean = (ErrorInfoBean) errorJsonObject.toBean(errorJsonObject, ErrorInfoBean.class);
					errorBean.setCreateTime(new Date());
					if(!"1".equals(flag)){
						//存储隐患信息
						insert("mainTask.saveErrorInfo",errorBean);
					}
					//得到隐患信息中的照片信息
					JSONArray errorPhotoJsonArray = (JSONArray) errorJsonObject.get("files");
					if(null!=errorPhotoJsonArray && errorPhotoJsonArray.size()!=0){//存在照片信息
						for(int j=0;j<errorPhotoJsonArray.size();j++){
							JSONObject errorPhotoJso = errorPhotoJsonArray.getJSONObject(i);
							PhotoInfoBean errorPhotoBean = (PhotoInfoBean) errorPhotoJso.toBean(errorPhotoJso, PhotoInfoBean.class);
							if(servletContext!=null){
								errorPhotoBean.setPath(servletContext.getRealPath("uploadPhoto/errorPhoto/"));
							}
							errorPhotoBean.setCreateTime(new Date());
							if(!"1".equals(flag)){
								//存储隐患照片信息
								insert("mainTask.savePhotoInfo",errorPhotoBean);
							}
						}
					}
				}
			}
			
			//处理路由信息
			routeInfoBean.setDeliveryDate(new Date());
			routeInfoBean.setName(startPointName+"——"+endPointName);
			
			//处理bean中Date格式
			JsonConfig jsonConfig = new JsonConfig();
			//jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
			
			JSONObject json = new JSONObject();
			if(!"1".equals(flag)){
				routeInfoBean.setFlag("1");
				update("mainTask.updateRouteInfo",routeInfoBean);
			}else{
				update("mainTask.updateRouteInfo",routeInfoBean);
			}
			
			//路由状态选择"正确"时 才进行匹配率计算操作
			if(routeState==0){
				//计算匹配率
				Double rate = 0.0;
				Double fz = 0.0;
				Double fm = 0.0;
				Integer z_object_id = 0;
				Map<String,Object> map = new HashMap<String,Object>();
				
				/**
				 * 修改之后
				 */
				
				if(wayList==null || wayList.size()==0){
					return "没有查询到资源信息或没有上传轨迹点";
				}
				//点资源信息标记
				String ids = "";
				String ids_2 = "";
				String ids_3 = "";
				String ids_4 = "";
				String ids_5 = "";
				String ids2 = "";
				String ids2_2 = "";
				String ids2_3 = "";
				String ids2_4 = "";
				String ids2_5 = "";
				String ids3 = "";
				String ids3_2 = "";
				String ids3_3 = "";
				String ids3_4 = "";
				String ids3_5 = "";
				String ids4 = "";
				String ids4_2 = "";
				String ids4_3 = "";
				String ids4_4 = "";
				String ids4_5 = "";
				String ids5 = "";
				String ids5_2 = "";
				String ids5_3 = "";
				String ids5_4 = "";
				String ids5_5 = "";
				String ids6 = "";
				String ids6_2 = "";
				String ids6_3 = "";
				String ids6_4 = "";
				String ids6_5 = "";
				
				//JavaBean转JSON
				ResourceInfoBean spoint = new ResourceInfoBean();
				spoint.setLatitude(startPointBean.getLatitude());
				spoint.setLongitude(startPointBean.getLongitude());
				spoint.setResourceID(startPointBean.getResourceID());
				spoint.setResourceName(startPointBean.getResourceName());
				spoint.setResourceType(startPointBean.getResourceType());
				
				ResourceInfoBean epoint = new ResourceInfoBean();
				epoint.setLatitude(endPointBean.getLatitude());
				epoint.setLongitude(endPointBean.getLongitude());
				epoint.setResourceID(endPointBean.getResourceID());
				epoint.setResourceName(endPointBean.getResourceName());
				epoint.setResourceType(endPointBean.getResourceType());
				dfsTravel(spoint,epoint,pointListNearbyTrail);
				
				pointListNearbyTrail = null;
				
				Map<String,ResourceInfoBean> path = this.getPath();
				
				Stack<ResourceInfoBean> stack = new Stack<ResourceInfoBean>();
				/**
				 ** 终点资源点
				 */
				ResourceInfoBean endResouce = path.get(String.valueOf(epoint.getResourceID()));
				// 轨迹点之内的资源点不包括终点
				if(null == endResouce){
					for (Map.Entry<String, ResourceInfoBean> entry : path.entrySet()) {
						stack.push(entry.getValue());		
					}
				}else{
					stack.add(epoint);
			        for (ResourceInfoBean location = path.get(String.valueOf(epoint.getResourceID())) ;
			        		false == location.getResourceID().equals(spoint.getResourceID()) ; 
			        		location = path.get(String.valueOf(location.getResourceID()))) {
			            stack.push(location);
			        }
			        stack.push(spoint);
				}
		        
		        while (!stack.isEmpty()) {
		        	ResourceInfoBean rinfo = stack.pop();
					if(null!=rinfo){
						Double sysLongi = rinfo.getLongitude();
						Double sysLati = rinfo.getLatitude();
						
						log.debug("start-end-route==="+rinfo.getResourceName()+"-lo:"+rinfo.getLongitude()+" -:la="+rinfo.getLatitude());
						Integer intId = rinfo.getResourceID();
						String resourceType = rinfo.getResourceType();
						if("站点".equals(resourceType)){
							ids += intId+",";
						}
						if("标石".equals(resourceType)){
							ids2 += intId+",";
						}
						if("资源点".equals(resourceType)){
							ids3 += intId+",";
						}
						if("电杆".equals(resourceType)){
							ids4 += intId+",";
						}
						if("管井".equals(resourceType)){
							ids5 += intId+",";
						}
						if("撑点".equals(resourceType)){
							ids6 += intId+",";
						}
						
						for(int j=0;j<wayList.size();j++){
							LocusPoint locusPoint = wayList.get(j);
							Double wayLongi = locusPoint.getLongitude();
							Double wayLati = locusPoint.getLatitude();
							Double distance = getDistance(sysLongi, sysLati, wayLongi, wayLati);
//							log.debug("distance==="+distance);
							if(distance<80){//距离之差小于80米 则匹配通过
								fz++;
								break;
							}
						}
						fm++;
					}
				}
		        if(fm == 0.0)
		        	fm = 1.0;
				rate = (fz/fm)*100.0;
				/**
				 * 张昊之前
				
				map = getMateRate(startPointBean,endPointBean,wayList,z_object_id,fz,fm,0,rate,map);
				log.debug("匹配率rate===="+map.get("rate").toString());
				if("NaN".equals(map.get("rate").toString())){
					return "没有查询到资源信息或没有上传轨迹点";
				}else if("110".equals(map.get("rate").toString())){
					return "没有查询到该起点的段信息";
				}else if("120".equals(map.get("rate").toString())){
					return "没有查询到该终点的段信息";
				}
				 */
				
				ResourceRelationBean upBean = new ResourceRelationBean();
				ResourceRelationBean upBean2 = new ResourceRelationBean();
				ResourceRelationBean upBean3 = new ResourceRelationBean();
				ResourceRelationBean upBean4 = new ResourceRelationBean();
				ResourceRelationBean upBean5 = new ResourceRelationBean();
				ResourceRelationBean upBean6 = new ResourceRelationBean();
				/**
				 * 张昊之前
				 
				List<ResourceRelationBean> rrList = (List<ResourceRelationBean>) map.get("list");
				
				
				for(int i=0;null!=rrList && i<rrList.size();i++){
					ResourceRelationBean rrBean = rrList.get(i);
					Integer intId = rrBean.getInt_id();
					//System.out.println("intId===="+intId);
					String resourceType = rrBean.getResourceType();
					if("站点".equals(resourceType)){
						ids += intId+",";
					}
					if("标石".equals(resourceType)){
						ids2 += intId+",";
					}
					if("光交".equals(resourceType)){
						ids3 += intId+",";
					}
					if("电杆".equals(resourceType)){
						ids4 += intId+",";
					}
					if("管井".equals(resourceType)){
						ids5 += intId+",";
					}
					if("撑点".equals(resourceType)){
						ids6 += intId+",";
					}
				}
				*/
				if(StringUtils.isNotBlank(ids)){
					ids = ids.substring(0, ids.length()-1);
					String[] idsArray = ids.split(",");
					ids = "";
					for(int i=0;i<idsArray.length;i++){
						if(i<=800){
							ids += idsArray[i]+",";
						}
						if(i>800 && i<=1600){
							ids_2 += idsArray[i]+",";
						}
						if(i>1600 && i<=2400){
							ids_3 += idsArray[i]+",";
						}
						if(i>2400 && i<=3200){
							ids_4 += idsArray[i]+",";
						}
						if(i>3200 && i<=4000){
							ids_5 += idsArray[i]+",";
						}
					}
					upBean.setUpIds(ids.substring(0, ids.length()-1));
					upBean.setUpIds_2(ids_2==""?"":ids_2.substring(0, ids_2.length()-1));
					upBean.setUpIds_3(ids_3==""?"":ids_3.substring(0, ids_3.length()-1));
					upBean.setUpIds_4(ids_4==""?"":ids_4.substring(0, ids_4.length()-1));
					upBean.setUpIds_5(ids_5==""?"":ids_5.substring(0, ids_5.length()-1));
				}
				if(StringUtils.isNotBlank(ids2)){
					ids2 = ids2.substring(0, ids2.length()-1);
					String[] idsArray = ids2.split(",");
					ids2 = "";
					for(int i=0;i<idsArray.length;i++){
						if(i<=800){
							ids2 += idsArray[i]+",";
						}
						if(i>800 && i<=1600){
							ids2_2 += idsArray[i]+",";
						}
						if(i>1600 && i<=2400){
							ids2_3 += idsArray[i]+",";
						}
						if(i>2400 && i<=3200){
							ids2_4 += idsArray[i]+",";
						}
						if(i>3200 && i<=4000){
							ids2_5 += idsArray[i]+",";
						}
					}
					upBean2.setUpIds2(ids2.substring(0, ids2.length()-1));
					upBean2.setUpIds2_2(ids2_2==""?"":ids2_2.substring(0, ids2_2.length()-1));
					upBean2.setUpIds2_3(ids2_3==""?"":ids2_3.substring(0, ids2_3.length()-1));
					upBean2.setUpIds2_4(ids2_4==""?"":ids2_4.substring(0, ids2_4.length()-1));
					upBean2.setUpIds2_5(ids2_5==""?"":ids2_5.substring(0, ids2_5.length()-1));
				}
				if(StringUtils.isNotBlank(ids3)){
					ids3 = ids3.substring(0, ids3.length()-1);
					String[] idsArray = ids3.split(",");
					ids3 = "";
					for(int i=0;i<idsArray.length;i++){
						if(i<=800){
							ids3 += idsArray[i]+",";
						}
						if(i>800 && i<=1600){
							ids3_2 += idsArray[i]+",";
						}
						if(i>1600 && i<=2400){
							ids3_3 += idsArray[i]+",";
						}
						if(i>2400 && i<=3200){
							ids3_4 += idsArray[i]+",";
						}
						if(i>3200 && i<=4000){
							ids3_5 += idsArray[i]+",";
						}
					}
					upBean3.setUpIds3(ids3.substring(0, ids3.length()-1));
					upBean3.setUpIds3_2(ids3_2==""?"":ids3_2.substring(0, ids3_2.length()-1));
					upBean3.setUpIds3_3(ids3_3==""?"":ids3_3.substring(0, ids3_3.length()-1));
					upBean3.setUpIds3_4(ids3_4==""?"":ids3_4.substring(0, ids3_4.length()-1));
					upBean3.setUpIds3_5(ids3_5==""?"":ids3_5.substring(0, ids3_5.length()-1));
				}
				if(StringUtils.isNotBlank(ids4)){
					ids4 = ids4.substring(0, ids4.length()-1);
					String[] idsArray = ids4.split(",");
					ids4 = "";
					for(int i=0;i<idsArray.length;i++){
						if(i<=800){
							ids4 += idsArray[i]+",";
						}
						if(i>800 && i<=1600){
							ids4_2 += idsArray[i]+",";
						}
						if(i>1600 && i<=2400){
							ids4_3 += idsArray[i]+",";
						}
						if(i>2400 && i<=3200){
							ids4_4 += idsArray[i]+",";
						}
						if(i>3200 && i<=4000){
							ids4_5 += idsArray[i]+",";
						}
					}
					upBean4.setUpIds4(ids4.substring(0, ids4.length()-1));
					upBean4.setUpIds4_2(ids4_2==""?"":ids4_2.substring(0, ids4_2.length()-1));
					upBean4.setUpIds4_3(ids4_3==""?"":ids4_3.substring(0, ids4_3.length()-1));
					upBean4.setUpIds4_4(ids4_4==""?"":ids4_4.substring(0, ids4_4.length()-1));
					upBean4.setUpIds4_5(ids4_5==""?"":ids4_5.substring(0, ids4_5.length()-1));
				}
				if(StringUtils.isNotBlank(ids5)){
					ids5 = ids5.substring(0, ids5.length()-1);
					String[] idsArray = ids5.split(",");
					ids5 = "";
					for(int i=0;i<idsArray.length;i++){
						if(i<=800){
							ids5 += idsArray[i]+",";
						}
						if(i>800 && i<=1600){
							ids5_2 += idsArray[i]+",";
						}
						if(i>1600 && i<=2400){
							ids5_3 += idsArray[i]+",";
						}
						if(i>2400 && i<=3200){
							ids5_4 += idsArray[i]+",";
						}
						if(i>3200 && i<=4000){
							ids5_5 += idsArray[i]+",";
						}
					}
					upBean5.setUpIds5(ids5.substring(0, ids5.length()-1));
					upBean5.setUpIds5_2(ids5_2==""?"":ids5_2.substring(0, ids5_2.length()-1));
					upBean5.setUpIds5_3(ids5_3==""?"":ids5_3.substring(0, ids5_3.length()-1));
					upBean5.setUpIds5_4(ids5_4==""?"":ids5_4.substring(0, ids5_4.length()-1));
					upBean5.setUpIds5_5(ids5_5==""?"":ids5_5.substring(0, ids5_5.length()-1));
				}
				if(StringUtils.isNotBlank(ids6)){
					ids6 = ids6.substring(0, ids6.length()-1);
					String[] idsArray = ids6.split(",");
					ids6 = "";
					for(int i=0;i<idsArray.length;i++){
						if(i<=800){
							ids6 += idsArray[i]+",";
						}
						if(i>800 && i<=1600){
							ids6_2 += idsArray[i]+",";
						}
						if(i>1600 && i<=2400){
							ids6_3 += idsArray[i]+",";
						}
						if(i>2400 && i<=3200){
							ids6_4 += idsArray[i]+",";
						}
						if(i>3200 && i<=4000){
							ids6_5 += idsArray[i]+",";
						}
					}
					upBean6.setUpIds6(ids6.substring(0, ids6.length()-1));
					upBean6.setUpIds6_2(ids6_2==""?"":ids6_2.substring(0, ids6_2.length()-1));
					upBean6.setUpIds6_3(ids6_3==""?"":ids6_3.substring(0, ids6_3.length()-1));
					upBean6.setUpIds6_4(ids6_4==""?"":ids6_4.substring(0, ids6_4.length()-1));
					upBean6.setUpIds6_5(ids6_5==""?"":ids6_5.substring(0, ids6_5.length()-1));
				}
				
				routeInfoBean.setMatchScores(String.valueOf(rate));
				//routeInfoBean.setRouteState(0);//路由状态
				if(rate>80){
					routeInfoBean.setDeliveryState(0);//通过
					upBean.setIsCheckedPassed("已巡检通过");
					upBean2.setIsCheckedPassed("已巡检通过");
					upBean3.setIsCheckedPassed("已巡检通过");
					upBean4.setIsCheckedPassed("已巡检通过");
					upBean5.setIsCheckedPassed("已巡检通过");
					upBean6.setIsCheckedPassed("已巡检通过");
				}else{
					routeInfoBean.setDeliveryState(1);//不通过
					upBean.setIsCheckedPassed("已巡检未通过");
					upBean2.setIsCheckedPassed("已巡检未通过");
					upBean3.setIsCheckedPassed("已巡检未通过");
					upBean4.setIsCheckedPassed("已巡检未通过");
					upBean5.setIsCheckedPassed("已巡检未通过");
					upBean6.setIsCheckedPassed("已巡检未通过");
				}
				if(StringUtils.isNotBlank(ids)){
					update("mainTask.upResrouceSite",upBean);
				}
				if(StringUtils.isNotBlank(ids2)){
					update("mainTask.upResrouceStone",upBean2);
				}
				if(StringUtils.isNotBlank(ids3)){
					update("mainTask.upResrouceLight",upBean3);
				}
				if(StringUtils.isNotBlank(ids4)){
					update("mainTask.upResroucePole",upBean4);
				}
				if(StringUtils.isNotBlank(ids5)){
					update("mainTask.upResroucePipe",upBean5);
				}
				if(StringUtils.isNotBlank(ids6)){
					update("mainTask.upResroucePoint",upBean6);
				}
				
				update("mainTask.updateRouteInfo",routeInfoBean);
			}
			
			//JavaBean转JSON
			returnBeanObject = json.fromObject(routeInfoBean, jsonConfig);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnBeanObject.toString();
	}

	/**
	 * 传输线路-我的
	 */
	@SuppressWarnings("unchecked")
	public String getMyRoute(String uid, String jsonParams) {
		JSONObject jso = JSONObject.fromObject(jsonParams);
		Integer page = jso.getInt("page");
		Integer pageSize = jso.getInt("pageSize");
		RouteInfoBean routeInfoBean = new RouteInfoBean();
		routeInfoBean.setUserId(uid);
		routeInfoBean.setStart(page*pageSize);
		routeInfoBean.setLimit((page-1)*pageSize);
		List<RouteInfoBean> list = getObjects(GET_ROUTE, routeInfoBean);
		//处理路由实体下起终点信息、轨迹信息、隐患信息
		if(null!=list && list.size()>0){
			for(int i=0;i<list.size();i++){
				RouteInfoBean rBean = list.get(i);
				if(StringUtils.isBlank(rBean.getName())){//如果路由名称为空 则移除该条记录
					list.remove(rBean);
					continue;
				}
				//通过routeID查找各个信息
				//起点
				PointlikeResourceInfoBean startBean = new PointlikeResourceInfoBean();
				startBean.setRouteID(rBean.getRouteID());
				startBean.setType(0);
				startBean = (PointlikeResourceInfoBean) getObject("mainTask.getPointInfo",startBean);
				rBean.setStartPosition(startBean);
				//终点
				PointlikeResourceInfoBean endBean = new PointlikeResourceInfoBean();
				endBean.setRouteID(rBean.getRouteID());
				endBean.setType(1);
				endBean = (PointlikeResourceInfoBean) getObject("mainTask.getPointInfo",endBean);
				rBean.setEndPosition(endBean);
				//轨迹
				List<LocusPoint> wayList = new ArrayList<LocusPoint>();
				LocusPoint wayBean = new LocusPoint();
				wayBean.setRouteID(rBean.getRouteID());
				wayList = getObjects("mainTask.getWayInfo",wayBean);
				rBean.setLocusPoints(wayList);
				//隐患
				List<ErrorInfoBean> errorList = new ArrayList<ErrorInfoBean>();
				ErrorInfoBean errorBean = new ErrorInfoBean();
				errorBean.setRouteID(rBean.getRouteID());
				errorList = getObjects("mainTask.getErrorInfo",errorBean);
				rBean.setErrors(errorList);
			}
		}
		//处理bean中Date格式
		JsonConfig jsonConfig = new JsonConfig();
		//jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jArray = new JSONArray();
		JSONArray returnJsonArray = jArray.fromObject(list, jsonConfig);
		
		return returnJsonArray.toString();
	}
	
	//计算经纬度两点距离
	public static double getDistance(double long1, double lat1, double long2, double lat2){
		double a, b, R;
		R = 6378137; //地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)*Math.cos(lat2) * sb2 * sb2));
		return d;
	}

	/**
	 * 清理垃圾路由数据
	 */
	public String deleteRoute(String uid, String jsonParams) {
		String mess = "";
		if(StringUtils.isNotBlank(jsonParams)){
			JSONObject jsonObject = JSONObject.fromObject(jsonParams);
			Integer routeId = (Integer) jsonObject.get("routeid");
			RouteInfoBean rBean = new RouteInfoBean();
			rBean.setID(routeId);
			delete("mainTask.clearRouteInfo",rBean);
		}else{
			mess = "routeId为空!";
			return mess;
		}
		return mess;
	}
	
	/*public static void main(String[] args) throws IOException {
		//System.out.println(getDistance(117.12322210609652, 36.66234526326412, 117.12325213026386, 36.66243822981022));
		Double longi = 114.6023;
		Double lati = 37.60359;
		String str = transBD09ToGPS(longi, lati);
		System.out.println(str);
	}*/
	
	
	public String transType(String type){
		String segType = "";
		if("标石".equals(type)){
			segType = "直埋段";
		}else if("电杆".equals(type)){
			segType = "杆路段";
		}else if("撑点".equals(type)){
			segType = "挂墙段";
		}else if("管井".equals(type)){
			segType = "管道段";
		}
		return segType;
	}
	public HashMap<String,Object> queryResource(HashMap<String,Object> map,List<Integer> list,List<Double> disList,List<Double> dislist,Integer preOid,Integer objectId,String restype,PointlikeResourceInfoBean endPointBean,Double distance,Integer type,Double minLong,Double maxLong,Double minLati,Double maxLati){
		Double disL = disList.get(0);
		Double disl = 0.0;
		for(int i=0;i<dislist.size();i++){
			disl += dislist.get(i);
		}
		Double disss = disL+disl;
		log.debug("disssssssssssssssss==="+disss);
		boolean tip = (Boolean) map.get("tip");
		if(disss<=10000){//超过10公里 则不进行查询操作
			SegInfoBean segInfoBean = new SegInfoBean();
			if(0==type){//正向段
				segInfoBean.setA_object_id(objectId);
			}else if(1==type){//逆向段
				segInfoBean.setZ_object_id(objectId);
			}
			segInfoBean.setType(restype);
			List<SegInfoBean> segList = getObjects("mainTask.querySegInfo",segInfoBean);
			if(null!=segList && segList.size()>0){
				for(int i=0;i<segList.size();i++){
					if(map.get("flag")!=null){//判断如果找到终点了，则直接跳出递归
						if("ok".equals(map.get("flag"))){
							return map;
						}
					}
					map.put("dislist", dislist);
					SegInfoBean segBean = segList.get(i);
					Integer oId = 0;
					if(0==type){
						oId = segBean.getZ_object_id();
					}else if(1==type){
						oId = segBean.getA_object_id();
					}
//					log.debug(">>>>>>>>>>>>preOid======"+preOid);
//					log.debug(">>>>>>>>>>>>oId======"+oId);
					if(preOid.equals(oId)){
						continue;
					}
					if(objectId.equals(oId)){
						continue;
					}
					if(list.contains(oId)){
						continue;
					}
					/*查询oId是否在范围内 在内则添加到集合中*/
					ResourceRelationBean resourceRelationBean = new ResourceRelationBean();
					resourceRelationBean.setInt_id(oId);
					resourceRelationBean = (ResourceRelationBean) getObject("mainTask.queryRP",resourceRelationBean);
					Double longt = resourceRelationBean.getLongitude();
					Double latit = resourceRelationBean.getLatitude();
					if(longt > minLong && longt < maxLong && latit > minLati && latit < maxLati){
						dislist.add(segBean.getC_length());
						list.add(oId);
					}
//					log.debug(">>>>>>>>>>>>终点id======"+endPointBean.getResourceID());
					if(!oId.equals(endPointBean.getResourceID())){
						distance += segBean.getC_length();
						//递归查询
						map = this.queryResource(map,list,disList,dislist,objectId,oId,restype,endPointBean,distance,type,minLong,maxLong,minLati,maxLati);
					}else{
						map.put("flag", "ok");
						disList.addAll(dislist);
					}
				}
			}
			if(map.get("flag")==null){//判断如果没有找到终点，则需要传一个标识，需要转向查询
				tip = true;
			}else{
				tip = false;
			}
			if(segList.size()==0 || (segList.size()>0 && tip)){//段方向可能改变，故转向查询，或者正向查到数据了，但没有匹配到终点则需要查反向
				segInfoBean = new SegInfoBean();
				if(0==type){
					segInfoBean.setZ_object_id(objectId);
					type=1;
				}else if(1==type){
					segInfoBean.setA_object_id(objectId);
					type=0;
				}
				segInfoBean.setType(restype);
				segList = getObjects("mainTask.querySegInfo",segInfoBean);
				if(null!=segList && segList.size()>0){
					for(int i=0;i<segList.size();i++){
						if(map.get("flag")!=null){//判断如果找到终点了，则直接跳出递归
							if("ok".equals(map.get("flag"))){
								return map;
							}
						}
						map.put("dislist", dislist);
						SegInfoBean segBean = segList.get(i);
						Integer oId = 0;
						if(0==type){
							oId = segBean.getZ_object_id();
						}else if(1==type){
							oId = segBean.getA_object_id();
						}
//						log.debug("<<<<<<<<<<<preOid======"+preOid);
//						log.debug("<<<<<<<<<<<oId======"+oId);
						if(preOid.equals(oId)){
							continue;
						}
						if(objectId.equals(oId)){
							continue;
						}
						if(list.contains(oId)){
							continue;
						}
						/*查询oId是否在范围内 在内则添加到集合中*/
						ResourceRelationBean resourceRelationBean = new ResourceRelationBean();
						resourceRelationBean.setInt_id(oId);
						resourceRelationBean = (ResourceRelationBean) getObject("mainTask.queryRP",resourceRelationBean);
						Double longt = resourceRelationBean.getLongitude();
						Double latit = resourceRelationBean.getLatitude();
						if(longt > minLong && longt < maxLong && latit > minLati && latit < maxLati){
							dislist.add(segBean.getC_length());
							list.add(oId);
						}
						if(!oId.equals(endPointBean.getResourceID())){
							distance += segBean.getC_length();
							//递归查询
							map = this.queryResource(map,list,disList,dislist,objectId,oId,restype,endPointBean,distance,type,minLong,maxLong,minLati,maxLati);
						}else{
							map.put("flag", "ok");
							disList.addAll(dislist);
						}
					}
				}else{//说明正向逆向都查不到了 将距离还原到最近的一个分支时
					dislist = (List<Double>) map.get("dislist");
				}
			}
		}else{//超过5公里还没有匹配到终点 则清空
			list.clear();
			dislist = (List<Double>) map.get("dislist");
		}
		map.put("dlist", list);
		map.put("dislist", dislist);
		return map;
	}
	/**
	 * 计算匹配率
	 * @param startPointBean
	 * @param endPointBean
	 * @param wayList
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> getMateRate(PointlikeResourceInfoBean startPointBean,PointlikeResourceInfoBean endPointBean,List<LocusPoint> wayList,Integer z_object_id,Double fz,Double fm,Integer count,Double rate,Map<String,Object> map){
		log.debug("===============================计算匹配率开始=============================");
		try {
			/*遍历轨迹集合排序，找出最大和最小经纬度*/
			Double minLong = 0.0;
			Double maxLong = 0.0;
			Double minLati = 0.0;
			Double maxLati = 0.0;
			if(wayList==null || wayList.size()==0){
				map.put("rate", "NaN");
				return map;
			}else{
				List<Double> longList = new ArrayList<Double>();
				List<Double> latiList = new ArrayList<Double>();
				for(int i=0;i<wayList.size();i++){
					LocusPoint lp = wayList.get(i);
					Double longitude = lp.getLongitude();
					Double latitude = lp.getLatitude();
					longList.add(longitude);
					latiList.add(latitude);
				}
				//将起点和终点也加入集合排序
				longList.add(startPointBean.getLongitude());
				longList.add(endPointBean.getLongitude());
				latiList.add(startPointBean.getLatitude());
				latiList.add(endPointBean.getLatitude());
				Collections.sort(longList);
				Collections.sort(latiList);
				//向四周延展30米
				minLong = longList.get(0)-(30.0/33/3600);
				maxLong = longList.get(longList.size()-1)+(30.0/33/3600);
				minLati = latiList.get(0)-(30.0/33/3600);
				maxLati = latiList.get(latiList.size()-1)+(30.0/33/3600);
			}
			
			List<ResourceRelationBean> rrList = null;
			HashMap<String,Object> dmap = new HashMap<String, Object>();
			List<Integer> idsList = null;
			boolean tip = false;
			dmap.put("tip", tip);
			List<Double> disList = new ArrayList<Double>();
			List<Double> dislist = new ArrayList<Double>();
			SegInfoBean startSegInfoBean = new SegInfoBean();
			startSegInfoBean.setA_object_id(startPointBean.getResourceID());
//			startSegInfoBean.setA_object_id(360093264);
			startSegInfoBean.setType(this.transType(startPointBean.getResourceType()));
//			startSegInfoBean.setType("管道段");
			SegInfoBean endSegInfoBean = new SegInfoBean();
//			endSegInfoBean.setZ_object_id(endPointBean.getResourceID());
//			endSegInfoBean.setZ_object_id(360078834);
//			endSegInfoBean.setType(this.transType(endPointBean.getResourceType()));
//			endSegInfoBean.setType("管道段");
			List<SegInfoBean> startSegList = getObjects("mainTask.querySegInfo",startSegInfoBean);
			List<SegInfoBean> endSegList = null;
			if(null!=startSegList && startSegList.size()>0){
				idsList = new ArrayList<Integer>();
				Double distance = 0.0;//计算距离
				Integer zoId = 0;
				for(int i=0;i<startSegList.size();i++){//有可能会有分支情况 遍历集合
					startSegInfoBean = startSegList.get(i);
					zoId =  startSegInfoBean.getZ_object_id();
					if(!zoId.equals(endPointBean.getResourceID())){//说明没有找到 继续查询
						idsList.add(zoId);
						distance += startSegInfoBean.getC_length();
						disList.add(startSegInfoBean.getC_length());
						dmap = this.queryResource(dmap,idsList,disList,dislist, startPointBean.getResourceID(),zoId,this.transType(startPointBean.getResourceType()), endPointBean, distance, 0,minLong,maxLong,minLati,maxLati);
						idsList = (List<Integer>) dmap.get("dlist");
						if(null!=dmap.get("flag")){
							if("ok".equals(dmap.get("flag").toString())){
								break;
							}
						}else{
							tip = true;
							dmap.put("tip", tip);
						}
					}else{//找到终点 跳出循环
						idsList.add(zoId);
						break;
					}
				}
				if(!tip){
					if(!idsList.contains(endPointBean.getResourceID())){//判断是否找到终点
						log.debug("没有匹配到终点");
						map.put("rate", "NaN");
						return map;
					}
					idsList.add(startPointBean.getResourceID());//将起点放入集合中
				}
			}
			if(startSegList.size()==0 || (startSegList.size()>0 && tip)){//未获取到该起点的段信息 说明段信息是反向的 起点赋值到终点字段上查询,或者正向查到了，但没有匹配到
//				startSegInfoBean.setA_object_id(endPointBean.getResourceID());
//				startSegInfoBean.setType(this.transType(endPointBean.getResourceType()));
				startSegInfoBean = new SegInfoBean();
				startSegInfoBean.setZ_object_id(startPointBean.getResourceID());
				startSegInfoBean.setType(this.transType(startPointBean.getResourceType()));
				startSegList = getObjects("mainTask.querySegInfo",startSegInfoBean);
				if(null!=startSegList && startSegList.size()>0){
					idsList = new ArrayList<Integer>();
					Double distance = 0.0;//计算距离
					Integer aoId = 0;
					for(int i=0;i<startSegList.size();i++){//有可能会有分支情况 遍历集合
						startSegInfoBean = startSegList.get(i);
						aoId =  startSegInfoBean.getA_object_id();
						if(!aoId.equals(endPointBean.getResourceID())){//说明没有找到 继续查询
							idsList.add(aoId);
							distance += startSegInfoBean.getC_length();
							disList.add(startSegInfoBean.getC_length());
							dmap = this.queryResource(dmap,idsList,disList,dislist, startPointBean.getResourceID(),aoId,this.transType(startPointBean.getResourceType()), endPointBean, distance, 1,minLong,maxLong,minLati,maxLati);
							idsList = (List<Integer>) dmap.get("dlist");
							if(null!=dmap.get("flag")){
								if("ok".equals(dmap.get("flag").toString())){
									break;
								}
							}
						}else{//找到终点 跳出循环
							idsList.add(aoId);
							break;
						}
					}
					if(!idsList.contains(endPointBean.getResourceID())){//判断是否找到终点
						log.debug("没有匹配到终点");
						map.put("rate", "NaN");
						return map;
					}
					idsList.add(startPointBean.getResourceID());//将起点放入集合中
				}else{
					rate = 110.0;
					map.put("rate", "110");
					return map;
				}
			}
			//System.out.println(dmap.get("distance"));
			/*SegInfoBean segInfoBean = new SegInfoBean();
			segInfoBean.setMinId(startSegInfoBean.getId());
			segInfoBean.setMaxId(endSegInfoBean.getId());
			//获取起点和终点之间的段信息
			List<SegInfoBean> segList = getObjects("mainTask.querySegList",segInfoBean);*/
			
			//多个ids 防止条件语句in后面的数量超过1000
			String ids = "";
			String ids2 = "";
			String ids3 = "";
			String ids4 = "";
			String ids5 = "";
			String ids6 = "";
			String ids7 = "";
			String ids8 = "";
			String ids9 = "";
			String ids10 = "";
			if(null!=idsList && idsList.size()>0){
				for(int i=0;i<idsList.size();i++){//循环结果集 拼装id串
					Integer id = idsList.get(i);
					if(i<=800){
						ids += id.toString()+",";
					}
					if(i>800 && i<=1600){
						ids2 += id.toString()+",";
					}
					if(i>1600 && i<=2400){
						ids3 += id.toString()+",";
					}
					if(i>2400 && i<=3200){
						ids4 += id.toString()+",";
					}
					if(i>3200 && i<=4000){
						ids5 += id.toString()+",";
					}
					if(i>4000 && i<=4800){
						ids6 += id.toString()+",";
					}
					if(i>4800 && i<=5600){
						ids7 += id.toString()+",";
					}
					if(i>5600 && i<=6400){
						ids8 += id.toString()+",";
					}
					if(i>6400 && i<=7200){
						ids9 += id.toString()+",";
					}
					if(i>7200 && i<=8000){
						ids10 += id.toString()+",";
					}
					//将另一端点加上
					/*if(i==segList.size()-1){
						ids += segBean.getA_object_id()+",";
					}*/
				}
				ResourceRelationBean resourceRelationBean = new ResourceRelationBean();
				if(StringUtils.isNotBlank(ids)){
					ids = ids.substring(0,ids.length()-1);
					resourceRelationBean.setIds(ids);
				}
				if(StringUtils.isNotBlank(ids2)){
					ids2 = ids2.substring(0,ids2.length()-1);
					resourceRelationBean.setIds2(ids2);
				}
				if(StringUtils.isNotBlank(ids3)){
					ids3 = ids3.substring(0, ids3.length()-1);
					resourceRelationBean.setIds3(ids3);
				}
				if(StringUtils.isNotBlank(ids4)){
					ids4 = ids4.substring(0, ids4.length()-1);
					resourceRelationBean.setIds4(ids4);
				}
				if(StringUtils.isNotBlank(ids5)){
					ids5 = ids5.substring(0, ids5.length()-1);
					resourceRelationBean.setIds5(ids5);
				}
				if(StringUtils.isNotBlank(ids6)){
					ids6 = ids6.substring(0, ids6.length()-1);
					resourceRelationBean.setIds6(ids6);
				}
				if(StringUtils.isNotBlank(ids7)){
					ids7 = ids7.substring(0, ids7.length()-1);
					resourceRelationBean.setIds7(ids7);
				}
				if(StringUtils.isNotBlank(ids8)){
					ids8 = ids8.substring(0, ids8.length()-1);
					resourceRelationBean.setIds8(ids8);
				}
				if(StringUtils.isNotBlank(ids9)){
					ids9 = ids9.substring(0, ids9.length()-1);
					resourceRelationBean.setIds3(ids9);
				}
				if(StringUtils.isNotBlank(ids10)){
					ids10 = ids10.substring(0, ids10.length()-1);
					resourceRelationBean.setIds10(ids10);
				}
				//360080543 石家庄市长江大道管道010号井 114.58378404 38.03648301
				//当ids串都为空时 赋一个默认值 以免查询整表导致超时
				if(StringUtils.isBlank(ids) && StringUtils.isBlank(ids2) && StringUtils.isBlank(ids3)){
					resourceRelationBean.setIds("360080543");
				}
				rrList = getObjects("mainTask.queryRP",resourceRelationBean);
				fm = (double) rrList.size();
				if(null!=rrList && rrList.size()>0){
					for(int i=0;i<rrList.size();i++){
						resourceRelationBean = rrList.get(i);
						if(null!=resourceRelationBean){
							Double sysLongi = resourceRelationBean.getLongitude();
							Double sysLati = resourceRelationBean.getLatitude();
							for(int j=0;j<wayList.size();j++){
								LocusPoint locusPoint = wayList.get(j);
								Double wayLongi = locusPoint.getLongitude();
								Double wayLati = locusPoint.getLatitude();
								Double distance = getDistance(sysLongi, sysLati, wayLongi, wayLati);
								log.debug("distance==="+distance);
								if(distance<80){//距离之差小于80米 则匹配通过
									fz++;
								}
							}
						}
					}
				}
			}else{
				map.put("rate", "NaN");
				return map;
			}
			/*else{//起终点置换 段信息是倒序的
				segInfoBean.setMinId(endSegInfoBean.getId());
				segInfoBean.setMaxId(startSegInfoBean.getId());
				List<SegInfoBean> segList2 = getObjects("mainTask.querySegList",segInfoBean);
				
				if(null!=segList2 && segList2.size()>0){
					for(int i=0;i<segList2.size();i++){
						SegInfoBean segBean = segList2.get(i);
						Integer zId = segBean.getZ_object_id();
						if(i<=800){
							ids += zId.toString()+",";
						}
						if(i>800 && i<=1600){
							ids2 += zId.toString()+",";
						}
						if(i>1600 && i<=2400){
							ids3 += zId.toString()+",";
						}
						if(i>2400 && i<=3200){
							ids4 += zId.toString()+",";
						}
						if(i>3200 && i<=4000){
							ids5 += zId.toString()+",";
						}
						if(i>4000 && i<=4800){
							ids6 += zId.toString()+",";
						}
						if(i>4800 && i<=5600){
							ids7 += zId.toString()+",";
						}
						if(i>5600 && i<=6400){
							ids8 += zId.toString()+",";
						}
						if(i>6400 && i<=7200){
							ids9 += zId.toString()+",";
						}
						if(i>7200 && i<=1600){
							ids10 += zId.toString()+",";
						}
						if(i==segList.size()-1){
							//增加另一端点
							ids += segBean.getA_object_id()+",";
						}
					}
					if(StringUtils.isNotBlank(ids)){
						ids = ids.substring(0,ids.length()-1);
					}
					if(StringUtils.isNotBlank(ids2)){
						ids2 = ids2.substring(0,ids2.length()-1);
					}
					if(StringUtils.isNotBlank(ids3)){
						ids3 = ids3.substring(0, ids3.length()-1);
					}
					if(StringUtils.isNotBlank(ids4)){
						ids4 = ids4.substring(0, ids4.length()-1);
					}
					if(StringUtils.isNotBlank(ids5)){
						ids5 = ids5.substring(0, ids5.length()-1);
					}
					if(StringUtils.isNotBlank(ids6)){
						ids6 = ids6.substring(0, ids6.length()-1);
					}
					if(StringUtils.isNotBlank(ids7)){
						ids7 = ids7.substring(0, ids7.length()-1);
					}
					if(StringUtils.isNotBlank(ids8)){
						ids8 = ids8.substring(0, ids8.length()-1);
					}
					if(StringUtils.isNotBlank(ids9)){
						ids9 = ids9.substring(0, ids9.length()-1);
					}
					if(StringUtils.isNotBlank(ids10)){
						ids10 = ids10.substring(0, ids10.length()-1);
					}
					ResourceRelationBean resourceRelationBean = new ResourceRelationBean();
					resourceRelationBean.setIds(ids);
					resourceRelationBean.setIds2(ids2);
					resourceRelationBean.setIds3(ids3);
					resourceRelationBean.setIds4(ids4);
					resourceRelationBean.setIds5(ids5);
					resourceRelationBean.setIds6(ids6);
					resourceRelationBean.setIds7(ids7);
					resourceRelationBean.setIds8(ids8);
					resourceRelationBean.setIds3(ids9);
					resourceRelationBean.setIds10(ids10);
					rrList = getObjects("mainTask.queryRP",resourceRelationBean);
					fm = (double) rrList.size();
					if(null!=rrList && rrList.size()>0){
						for(int i=0;i<rrList.size();i++){
							resourceRelationBean = rrList.get(i);
							if(null!=resourceRelationBean){
								Double sysLongi = resourceRelationBean.getLongitude();
								Double sysLati = resourceRelationBean.getLatitude();
								for(int j=0;j<wayList.size();j++){
									LocusPoint locusPoint = wayList.get(j);
									Double wayLongi = locusPoint.getLongitude();
									Double wayLati = locusPoint.getLatitude();
									Double distance = getDistance(sysLongi, sysLati, wayLongi, wayLati);
									log.debug("distance==="+distance);
									if(distance<100){//距离之差小于80米 则匹配通过
										fz++;
									}
								}
							}
						}
					}
				}
			}*/
			
			log.debug("fz======"+fz+",fm======="+fm);
			log.debug("轨迹点数量======="+wayList.size());
			rate = ((fz/fm)*100.0)/wayList.size();
			map.put("rate", rate);
			map.put("list", rrList);
		} catch (Exception e) {
			log.debug("===============================计算匹配率出错=============================");
			e.printStackTrace();
		}
		log.debug("===============================计算匹配率结束=============================");
		return map;
	}

	/**
	 * 获取轨迹周边80米范围的所有资源点 ---------yp
	 * @param routId
	 */
	
	public List<String> getTrailPointNearBy(String routId){
		String searchSql = " select a.longitude,a.latitude from hebei_trail where route_id"+routId;
		List<Map<String,Object>> trailList = jdbcTemplate.queryForList(searchSql);
		List<String> pointListNearbyTrail = new ArrayList<String>();
		if(null != trailList && !trailList.isEmpty()){
			for(Map<String,Object> trailObj : trailList){
				Double longitude = Double.valueOf(trailObj.get("longitude").toString());
				Double latitude = Double.valueOf(trailObj.get("latitude").toString());
				Double xMax = longitude+(80.0/33/3600);
				Double xMin = longitude-(80.0/33/3600);
				Double yMax = latitude+(80.0/33/3600);
				Double yMin = latitude-(80.0/33/3600);
				ResourceRelationBean fBean = new ResourceRelationBean();
				fBean.setxMax(xMax);
				fBean.setxMin(xMin);
				fBean.setyMax(yMax);
				fBean.setyMin(yMin);
				//获取周围的资源信息
				List<ResourceInfoBean> fBeanList = getObjects("mainTask.queryResInfo",fBean);
				List<ResourceLineBean> lineList = new ArrayList<ResourceLineBean>();
				Map<Integer,ResourceInfoBean> map = new HashMap<Integer,ResourceInfoBean>();
				for(int i=0;null!=fBeanList && i<fBeanList.size();i++){
					ResourceInfoBean tempBean = fBeanList.get(i);
					String resourceId = String.valueOf(tempBean.getResourceID());
					if(!pointListNearbyTrail.contains(resourceId))
						pointListNearbyTrail.add(resourceId);
				}
			}
		}
		return pointListNearbyTrail;
	}
	/**
	 * 获取路由段信息
	 */
	public String getSegInfo(String uid, String jsonParams) {
		String mess = "";
		JSONObject jsonObject = JSONObject.fromObject(jsonParams);
		Double longitude = (Double) jsonObject.get("longitude");
		Double latitude = (Double) jsonObject.get("latitude");
		//转换坐标
		/*try {
			String s = transGCJ02ToWGS84(longitude, latitude);
			String[] aa = s.split(",");
			longitude = Double.parseDouble(aa[0]);
			latitude = Double.parseDouble(aa[1]);
			log.debug("转换后的坐标：Longi==="+longitude+",Lati==="+latitude);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		Double xMax = longitude+(300.0/33/3600);
		Double xMin = longitude-(300.0/33/3600);
		Double yMax = latitude+(300.0/33/3600);
		Double yMin = latitude-(300.0/33/3600);
		/*try {
			String s = transGCJ02ToWGS84(xMax, yMax);
			String[] bb = s.split(",");
			xMax = Double.parseDouble(bb[0]);
			yMax = Double.parseDouble(bb[1]);
			log.debug("转换后的坐标：xMax==="+longitude+",yMax==="+latitude);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String s = transGCJ02ToWGS84(xMin, yMin);
			String[] cc = s.split(",");
			xMin = Double.parseDouble(cc[0]);
			yMin = Double.parseDouble(cc[1]);
			log.debug("转换后的坐标：xMin==="+longitude+",yMin==="+latitude);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		log.debug("xMax==="+xMax+",xMin==="+xMin+",yMax==="+yMax+",yMin==="+yMin);
		ResourceRelationBean fBean = new ResourceRelationBean();
		fBean.setxMax(xMax);
		fBean.setxMin(xMin);
		fBean.setyMax(yMax);
		fBean.setyMin(yMin);
		//获取周围的资源信息
		List<ResourceInfoBean> fBeanList = getObjects("mainTask.queryResInfo",fBean);
		String ids = "";
		List<ResourceLineBean> lineList = new ArrayList<ResourceLineBean>();
		Map<Integer,ResourceInfoBean> map = new HashMap<Integer,ResourceInfoBean>();
		for(int i=0;null!=fBeanList && i<fBeanList.size();i++){
			ResourceInfoBean tempBean = fBeanList.get(i);
			Integer resourceId = tempBean.getResourceID();
			ids += resourceId+",";
			//将int_id和对应的bean对象放入map 后面封装用到
			map.put(resourceId, tempBean);
		}
		if(StringUtils.isNotBlank(ids)){
			ids = ids.substring(0, ids.length()-1);
		}else{
			mess = "没有查询到资源信息";
			return mess;
		}
		
		SegInfoBean segInfoBean = new SegInfoBean();
		segInfoBean.setIds(ids);
		List<SegInfoBean> segList = getObjects("mainTask.querySegInfoByLocation",segInfoBean);
		for(int j=0;j<segList.size();j++){
			SegInfoBean segBean = segList.get(j);
			ResourceLineBean lineBean = new ResourceLineBean();
			lineBean.setRelatedBranch(segBean.getRelated_branch());
			//封装ResourceInfoBean start 直接取之前map中放入的bean对象
			ResourceInfoBean startBean = map.get(segBean.getA_object_id());
			
			//封装ResourceInfoBean end 直接取之前map中放入的bean对象
			ResourceInfoBean endBean = map.get(segBean.getZ_object_id());
			
			lineBean.setStart(startBean);
			lineBean.setEnd(endBean);
			
			lineList.add(lineBean);
		}
		log.debug("返回数据数量"+lineList.size());
		if(lineList.size()==0){
			mess = "没有查询到段信息";
			return mess;
		}
		JSONArray jsonArray = JSONArray.fromObject(lineList);
		mess = jsonArray.toString();
		
		return mess;
	}

	/**
	 * 易采前台-我的路由
	 * @param routeInfoBean
	 * @return
	 */
	public List<RouteInfoBean> getMyRouteInfo(RouteInfoBean routeInfoBean) {
		List<RouteInfoBean> list = getObjects(GET_ROUTE, routeInfoBean);
		return list;
	}
	
	/**
	 * 通过HttpClient请求百度API返回坐标 计算获取GPS坐标
	 * @param longi
	 * @param lati
	 * @return
	 * @throws IOException
	 */
	public static String transBD09ToGPS(Double longi,Double lati) throws IOException {
		String returnStr = "";
		HttpClient client = new HttpClient();
		// 设置代理服务器地址和端口
		//client.getHostConfiguration().setProxy( "111.13.101.208", 80 );
		// 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https
		//Double x = 114.6023;
		//Double y = 37.60359;
		String url = "http://api.map.baidu.com/ag/coord/convert?from=0&to=4&x="+longi+"&y="+lati;
//		String url = "http://123.125.65.237/ag/coord/convert?from=0&to=4&x="+longi+"&y="+lati;
		HttpMethod method = new GetMethod(url );

		// 这里设置字符编码，避免乱码
		method.setRequestHeader( "Content-Type", "text/html;charset=utf-8" );

		client.executeMethod( method );
		// 打印服务器返回的状态
		log.debug( method.getStatusLine() );

		// 获取返回的html页面
		byte[] body = method.getResponseBody();
		// 返回的信息
		String str = new String (body, "utf-8");
		JSONObject jso = JSONObject.fromObject(str);
		String x = jso.getString("x");
		String y = jso.getString("y");
		x = new String(decode(x),"utf-8");
		y = new String(decode(y),"utf-8");
		Double gpsLongi = longi*2-Double.parseDouble(x);
		Double gpsLati = lati*2-Double.parseDouble(y);
		returnStr = gpsLongi+","+gpsLati;
		
		log.debug(new String(decode(x),"utf-8")+","+new String(decode(y),"utf-8"));
		// 释放连接
		method.releaseConnection();
		
		return returnStr;
	}
	
	/**
	 * base64解码
	 * @param str
	 * @return
	 */
	public static byte[] decode(String str){    
	   byte[] bt = null;    
	   try {    
	      /* sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();    
	       bt = decoder.decodeBuffer( str );   */ 
	   } catch (Exception e) {    
	       e.printStackTrace();    
	   }    
       return bt;    
    }  
	
	///////////////////////////////////////////////////////坐标转换/////////////////////////////////////////////////////////////////////////
	/**
	 * 将GCJ-02转换成WGS-84坐标
	 * @param gcjLati
	 * @param gcjLongi
	 * @return
	 */
	public String transGCJ02ToWGS84(Double gcjLongi,Double gcjLati){
		Double initDelta = 0.01;
		Double threshold = 0.000000001;
		Double dLat = initDelta, dLon = initDelta;
		Double mLat = gcjLati - dLat, mLon = gcjLongi - dLon;
		Double pLat = gcjLati + dLat, pLon = gcjLongi + dLon;
		Double wgsLat, wgsLon;
		int i = 0;
		while (true) {
			wgsLat = (mLat + pLat) / 2;
			wgsLon = (mLon + pLon) / 2;
			String tmp = this.gcjEncrypt(wgsLat, wgsLon);
			String[] tt = tmp.split(",");
			dLat = Double.parseDouble(tt[1]) - gcjLati;
			dLon = Double.parseDouble(tt[0]) - gcjLongi;
			if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold))
			break;
	
			if (dLat > 0) pLat = wgsLat; else mLat = wgsLat;
			if (dLon > 0) pLon = wgsLon; else mLon = wgsLon;
	
			if (++i > 10000) break;
		}
		
		return wgsLon+","+wgsLat;
	}
	public String gcjEncrypt(Double wgsLat,Double wgsLon){
		if(this.outOfChina(wgsLat,wgsLon)){
			return wgsLon+","+wgsLat;
		}
		String d = this.delta(wgsLat, wgsLon);
		String[] dd = d.split(",");
		return (wgsLon + Double.parseDouble(dd[0]))+","+(wgsLat + Double.parseDouble(dd[1]));
	}
	public boolean outOfChina(Double lat,Double lon){
		if (lon < 72.004 || lon > 137.8347){
			return true;
		}
		if (lat < 0.8293 || lat > 55.8271){
			return true;
		}
		return false;
	}
	public String delta(Double lat,Double lon){
		// Krasovsky 1940
		//
		// a = 6378245.0, 1/f = 298.3
		// b = a * (1 - f)
		// ee = (a^2 - b^2) / a^2;
		Double a = 6378245.0;
		Double ee = 0.00669342162296594323;
		Double dLat = this.transformLat(lon - 105.0, lat - 35.0);
		Double dLon = this.transformLon(lon - 105.0, lat - 35.0);
		Double radLat = lat / 180.0 * this.PI;
		Double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		Double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * this.PI);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * this.PI);
		return dLon+","+dLat;
	}
	public Double transformLat(Double x,Double y){
		Double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * this.PI) + 20.0 * Math.sin(2.0 * x * this.PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * this.PI) + 40.0 * Math.sin(y / 3.0 * this.PI)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * this.PI) + 320 * Math.sin(y * this.PI / 30.0)) * 2.0 / 3.0;
		return ret;
	}
	public Double transformLon(Double x,Double y){
		Double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * this.PI) + 20.0 * Math.sin(2.0 * x * this.PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * this.PI) + 40.0 * Math.sin(x / 3.0 * this.PI)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * this.PI) + 300.0 * Math.sin(x / 30.0 * this.PI)) * 2.0 / 3.0;
		return ret;
	}
	///////////////////////////////////////////////////////坐标转换/////////////////////////////////////////////////////////////////////////

	public String getRouteInfoById(String uid, String jsonParams) {
		RouteInfoBean rBean = new RouteInfoBean();
		rBean.setUserId(uid);
		JSONObject jso = JSONObject.fromObject(jsonParams);
		Integer routeId = Integer.parseInt(jso.get("routeID").toString());
		rBean.setRouteID(routeId);
		rBean.setName("name");
		rBean = (RouteInfoBean) getObject("mainTask.getRouteInfo",rBean);
		//处理bean中Date格式
		JsonConfig jsonConfig = new JsonConfig();
		JSONObject returnJso = JSONObject.fromObject(rBean,jsonConfig);
		return returnJso.toString();
	}
	/**
	 * 算法入口
	 */
	private List<String> pointOrderQueue = new LinkedList<String>();
	private List<String> visitedVertex;
	private Map<String, ResourceInfoBean> path = new HashMap<String,ResourceInfoBean>();
	public Map<String, ResourceInfoBean> getPath() {
	    return path;
	}
	  
	public void dfsTravel(ResourceInfoBean startPointBean,ResourceInfoBean endPointBean,List<String> pointListNearbyTrail){
		Queue q = new LinkedList<ResourceInfoBean>();
		visitedVertex = new ArrayList<String>();
		visitedVertex.add(String.valueOf(startPointBean.getResourceID()));
		q.add(startPointBean);
		boolean flag = false;
		while(false == q.isEmpty() ){
			if(flag)
				break;
			ResourceInfoBean rbean = (ResourceInfoBean)q.poll();
//			System.out.println("这个遍历的是："+rbean.getResourceName());  
			SegInfoBean startSegInfoBean = new SegInfoBean();
			startSegInfoBean.setA_object_id(rbean.getResourceID());
			startSegInfoBean.setType(this.transType(rbean.getResourceType()));
			List<SegInfoBeanAZ> nextSegList = getObjects("mainTask.querySegInfoByza",startSegInfoBean);
			//a端-z端
			if( null != nextSegList && !nextSegList.isEmpty()){
				for(SegInfoBeanAZ sbean: nextSegList){
					//没找到，继续遍历
					ResourceInfoBean fBean = new ResourceInfoBean();
					if(sbean.getVflag().equals("1"))
						fBean.setResourceID(sbean.getZ_object_id());
					else
						fBean.setResourceID(sbean.getA_object_id());
					
					fBean.setResourceType(sbean.getType());
					List<ResourceInfoBean> resouceInfoList = getObjects("mainTask.queryPointInfo",fBean);
					ResourceInfoBean infoBean = new ResourceInfoBean();
					if(null != resouceInfoList && !resouceInfoList.isEmpty())
						infoBean = resouceInfoList.get(0);
					else
						break;
					//找到
					if(infoBean.getResourceID().equals(endPointBean.getResourceID())){
						System.out.println("=====pointOrderQueue: "+pointOrderQueue.isEmpty());
						if(pointOrderQueue.isEmpty()){
							flag = true;
							visitedVertex.add(String.valueOf(infoBean.getResourceID()));
							path.put(String.valueOf(infoBean.getResourceID()), rbean);
							break;
						}else
							continue;
					}
					if(false == visitedVertex.contains(String.valueOf(infoBean.getResourceID()))){
//						String resourceId = pointOrderQueue.peek();
//						System.out.println(infoBean.getResourceID()+"==============================="+resourceId);
//						String infoResourceId = infoBean.getResourceID().toString();
//						if(infoResourceId.equals(resourceId))
//							pointOrderQueue.poll();
						String infoResourceId = infoBean.getResourceID().toString();
						if(pointOrderQueue.contains(infoResourceId)){
							pointOrderQueue.remove(infoResourceId);
						}
						if(pointListNearbyTrail.contains(String.valueOf(infoBean.getResourceID()))){
							visitedVertex.add(String.valueOf(infoBean.getResourceID()));
							path.put(String.valueOf(infoBean.getResourceID()), rbean);
							q.add(infoBean);
						}
					}
				}
			}
		}
	}
}
