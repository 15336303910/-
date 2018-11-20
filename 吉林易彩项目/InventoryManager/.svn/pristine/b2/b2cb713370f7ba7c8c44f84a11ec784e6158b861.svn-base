package manage.version.service;

import interfaces.irmsInterface.interfaces.pojo.IrmsPojo;
import interfaces.irmsInterface.interfaces.pojo.MoveResPojo;
import interfaces.irmsInterface.utils.InterfaceAddr;
import interfaces.irmsInterface.utils.RequestUtil;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.ImageUtil;
import base.util.MapUtil;
import base.util.TextUtil;
import base.util.pojo.Point;
import manage.equt.pojo.EqutInfoBean;
import manage.equt.pojo.ODMInfoBean;
import manage.generator.pojo.GeneratorInfoBean;
import manage.generator.pojo.StationBaseInfoBean;
import manage.point.pojo.PointInfoBean;
import manage.route.pojo.CableInfoBean;
import manage.route.pojo.FiberInfoBean;
import manage.version.pojo.VersionControl;
import manage.version.service.impl.IversionControlService;

public class VersionControlService extends DataBase implements IversionControlService{
	private static String getNewestVersion = "versionControl.getNewestVersion";
	private JdbcTemplate jdbcTemplate;
	/**
	 * 得到最新的version
	 * @return
	 */
	public VersionControl getNewestVersion(VersionControl version){
		List<VersionControl> list = getObjects(getNewestVersion,version);
		if(list!=null && list.size() >0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	
	public void addRes(){
		try{
			String sql = "select requestStr,date_format(eventDate,'%Y-%c-%d %h:%i:%s') as eventDate,username"
					+ " from job_event where method = 'insertEqut' and username = 'lihua'";
			List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
			if(TextUtil.isNotNull(list)){
				for(Map<String, Object> map : list){
					String eventDate = (map.get("eventDate")+"");
					SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date cDate = sdf.parse(eventDate);
					
					
					String userName = map.get("username")+"";
					String str = (map.get("requestStr")+"");
					if(str.startsWith("limit = 10")){
						str = str.replaceAll("limit = 10\n", "");
					}
					str = str.split("=")[1];
					str = str.split("]")[0].trim()+"]";
					if(str.endsWith("longiner]")){
						str = str.replaceAll("longiner]", "");
					}
					System.out.println(str);
					//站点
					/*StationBaseInfoBean station = (StationBaseInfoBean) getRequestObject(StationBaseInfoBean.class,str);
					System.out.println(station.getStationName()+"====================");
					System.out.println(map.get("username")+"-----------------------");
					System.out.println(map.get("eventDate")+"-----------------------");
					station.setParties(userName);
					station.setDataQualityPrincipal(userName);
					Point point = MapUtil.bd_wgs_encrypt(Double.parseDouble(station.getLat()), Double.parseDouble(station.getLon()));
					station.setLat(point.getLat()+"");
					station.setLon(point.getLng()+"");
					int id = (Integer) insert("pdagenerator.insertStationBase", station);
					System.out.println("==================="+id+"=============================");*/
					//机房
					/*GeneratorInfoBean generator = (GeneratorInfoBean) getRequestObject(GeneratorInfoBean.class,str);
					generator.setParties(userName);
					generator.setDataQualityPrincipal(userName);
					int id = (Integer) insert("pdagenerator.insertGenerator", generator);
					System.out.println("==================="+id+"=============================");*/
					
					
					EqutInfoBean equt = (EqutInfoBean) getRequestObject(EqutInfoBean.class,str);
					/*if(equt.getEtype().equals("1") ){
						if(equt.getEname().contains("/")){
							String gname = equt.getEname().split("/")[0];
							String gSql = "select generatorId as gid from job_generator where generatorName ='"+gname+"'";
							List<Map<String, Object>> gList = this.jdbcTemplate.queryForList(gSql);
							if(TextUtil.isNotNull(gList)){
								Map<String, Object> gMap = gList.get(0);
								equt.setGid(gMap.get("gid")+"");
							}
						}
						
						int gid = this.jdbcTemplate.queryForInt("select generatorId from job_generator where generatorName ='"+gname+"'");
						equt.setGid(gid+"");
					}*/
					equt.setPosX("0.2");
					equt.setPosY("0.5");
					if(TextUtil.isNotNull(equt.getLat()) && TextUtil.isNotNull(equt.getLon())){
						Point point = MapUtil.bd_wgs_encrypt(Double.parseDouble(equt.getLat()), Double.parseDouble(equt.getLon()));
						equt.setLat(point.getLat()+"");
						equt.setLon(point.getLng()+"");
					}
					equt.setParties(userName);
					if(equt.getEtype().equals("3")){
						equt.setEid("EIU_"+System.currentTimeMillis());
					}
					equt.setMtime(cDate);
					int id = ((Integer) insert("pdaequt.insertEqut", equt)).intValue();
					System.out.println("==================="+id+"=============================");
					
					/*CableInfoBean cable = (CableInfoBean)getRequestObject(CableInfoBean.class,str);
					cable.setParties(userName);
					int i = ((Integer)insert("pdaroute.insertCable", cable));
					cable.setCableid(Integer.valueOf(i));
					this.addFiber(cable);*/
					//模块
					/*ODMInfoBean odm = (ODMInfoBean) getRequestObject(ODMInfoBean.class,str);
					System.out.println(odm.getEid());*/
					/*List<ODMInfoBean> odmList = (List)getRequestObject(ODMInfoBean.class,str);
					for(ODMInfoBean odm :odmList){
						this.insertODM(odmList,userName);
					}*/
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void insertODM(List<ODMInfoBean> odmList,String loginer) throws DataAccessException {
		for(ODMInfoBean o: odmList){
			//根据EID 得到对象
			EqutInfoBean equt = new EqutInfoBean();
			List eqtList = this.jdbcTemplate.queryForList("select ENAME from job_equtinfo where EID ='"+o.getEid()+"'");
			if(TextUtil.isNull(eqtList)){
				return;
			}else{
				equt.setEid(o.getEid());
				List eList = this.getEqutObj(equt);
				if(TextUtil.isNotNull(eList)){
					if(o.getEid().startsWith("E")){
						o.setOdmName(equt.getEname()+"-"+o.getOdmCode());
					}
					o.setCuser(loginer);
					int id  = (Integer) this.insert("pdaequt.insertODM", o);
					o.setOdmId(id);
					int row = Integer.parseInt(o.getTerminalRowQuantity());
					int col = Integer.parseInt(o.getTerminalColumnQuantity());
					List pointList = new ArrayList();
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
					batchInsert("pdaequt.insertPoint", pointList);
				}
				
			}
			
		}
	}
	
	
	public List<EqutInfoBean> getEqutObj(EqutInfoBean equt){
		List<EqutInfoBean>  list = this.getObjects("pdaequt.getEqut", equt);
		return list;
	}
	
	
	public void addFiber(CableInfoBean cable){
		   Date fiberDate = new Date();
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   try{
			   if(TextUtil.isNotNull(cable.getFibercount())){
				   for(int i=0;i<cable.getFibercount();i++){
					   FiberInfoBean fiber = new FiberInfoBean();
					   fiber.setCableId(cable.getCableid()+"");
					   fiber.setCableName(cable.getCablename());
					   int num =i+1;
					   if((num+"").length()==1){
						   fiber.setZhLabel(cable.getCablename()+"_0"+(i+1)+"芯");
					   }else{
						   fiber.setZhLabel(cable.getCablename()+"_"+(i+1)+"芯");
					   }
					   fiber.setStatus(0);
					   fiber.setAlias((i+1)+"");
					   fiber.setDeleteflag("0");
					   fiber.setCreateTime(sdf.format(fiberDate));
					   insert("pdaroute.insertFiber", fiber);
				   }
			   }
		   }catch(Exception e){
			   e.printStackTrace();
		   }
	   }
	
	
	 public  Object getRequestObject(Class classOfT,String jsonRequest)
	  {
	    if ((jsonRequest == null) || (jsonRequest.equals("")))
	      return null;
	    Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create();
	    if (!(jsonRequest.startsWith("["))) {
	      return gson.fromJson(jsonRequest, classOfT);
	    }
	    JsonParser parser = new JsonParser();
	    JsonArray Jarray = parser.parse(jsonRequest).getAsJsonArray();
	    List requestlist = new ArrayList();
	    for (JsonElement obj : Jarray) {
	      Object cse = gson.fromJson(obj, classOfT);
	      requestlist.add(cse);
	    }
	    return requestlist;
	  }
	 
	 
	 /**
	  * 转换
	  */
	 public void setLonLat(){
		 /*String sql = "select id,lon,lat,glon,glat from a_linshi ";
		 List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		 for(Map<String, Object> map : list){
			 String id = map.get("id")+"";
			 String lon = map.get("lon")+"";
			 String lat = map.get("lat")+"";
			 Point point = MapUtil.wgs_gcj_encrypts(Double.parseDouble(lat), Double.parseDouble(lon));
			 String upSql = ""
			 		+ "update a_linshi"
			 		+ " set glon ='"+point.getLng()+"',glat ='"+point.getLat()+"'"
			 		+ " where id ='"+id+"'"
			 		+ "";
			 this.jdbcTemplate.execute(upSql);
		 }*/
		 
		 
		 /*String sql = "select outStr from irms_interface where faceType ='addTranBox' and faceResult ='success'";
		 List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		 for(Map<String, Object> map : list){
			 String resId = this.getAddRes(map.get("outStr")+"");
			 EqutInfoBean equt = new EqutInfoBean();
			 equt.setResNum(resId);
			 String equtSql = "select ENAME as ename,lon as lon ,lat as lat,resNum as resNum  from job_equtinfo where resNum ='"+resId+"'";
			 List<Map<String, Object>> equtList = this.jdbcTemplate.queryForList(equtSql);
			 if(TextUtil.isNotNull(equtList)){
				 Map<String, Object> emap= equtList.get(0);
				 equt.setEname(emap.get("ename")+"");
				 equt.setLon(emap.get("lon")+"");
				 equt.setLat(emap.get("lat")+"");
				 MoveResPojo obj = new MoveResPojo();
				 obj.setAppType("moveTranbox00000");
				 obj.setLatitude(equt.getLat());
				 obj.setLongitude(equt.getLon());
				 obj.setResId(equt.getResNum());
				 obj.setResType("guangjiaojiexiang");
				 obj.setResName(equt.getEname());
				 this.moveRes(obj);
			 }
		 }*/
		 
		 /*String sql = "select id,name ,type,region  from v_resId where region like '%曲靖%'";
		 List<Map<String, Object>> resList = this.jdbcTemplate.queryForList(sql);
		 if(TextUtil.isNotNull(resList)){
			 for(Map<String, Object> map : resList){
				 String id  = map.get("id")+"";
				 String type = map.get("type")+"";
				 String name = map.get("name")+"";
				 String imagSql = "select ImagePath from resource_images where type ='"+type+"' and  resourceId ='"+id+"'";
				 List<Map<String, Object>> list = this.jdbcTemplate.queryForList(imagSql);
				 for(Map<String, Object> image: list){
					 System.out.println(image.get("ImagePath")+"");
				 }
			 }
		 }*/
		 
		 
		 /*String sql = "select i.ImagePath,r.name,i.ImageName from resource_images i , v_resId r where i.resourceId = r.id and i.type = r.type and  region like '%玉溪%'";
		 List<Map<String, Object>>  list = this.jdbcTemplate.queryForList(sql);
		 if(TextUtil.isNotNull(list)){
			 for(Map<String, Object> map : list){
				 System.out.println(map.get("ImagePath")+"");
				 try{
					 ImageUtil.download("http://218.57.146.247:8328/image/upload/"+map.get("ImagePath"),
							 "d:\\玉溪\\"+map.get("name"),map.get("ImageName")+"");
				 }catch(Exception e){
					 e.printStackTrace();
				 }
				 
			 }
		 }*/
	 }
	 
	 
	 
	 public void moveRes(MoveResPojo movePojo){
			try{
				if(TextUtil.isNotNull(movePojo.getResId())){
					String xml="<xmldata mode=\"SinglePointEditMode\">"
							+ "<mc type=\""+movePojo.getResType()+"\">"
							+ "<mo group=\"1\">"
							+ "<fv k=\"ZH_LABEL\" v=\""+movePojo.getResName()+"\"/>"
							+ "<fv k=\"LONGITUDE\" v=\""+movePojo.getLongitude()+"\"/>"
							+ "<fv k=\"LATITUDE\" v=\""+movePojo.getLatitude()+"\"/>"
							+ "<fv k=\"INT_ID\" v=\""+movePojo.getResId()+"\"/>"
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
	 
	 public String getAddRes(String xml){
		 String resourceId = "";
		 try{
			SAXReader reader = new SAXReader();            
			Document document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			Element root = document.getRootElement();
			Element ids = root.element("idsMapping");
			Element id = ids.element("idMapping");
			for(Iterator i = ids.elementIterator("idMapping");i.hasNext();){
				Element foo = (Element) i.next(); 
				String oldId = foo.attributeValue("oldid");
				resourceId =foo.attributeValue("newid");
			}
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return resourceId;
	 }
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
