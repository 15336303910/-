package interfaces.pdainterface.equt.action;
import base.util.MapUtil;
import base.util.TextUtil;
import base.util.functions;
import base.util.pojo.Point;
import base.web.InterfaceAction;
import interfaces.irmsInterface.interfaces.opticTran.service.impl.IirmsOpticTranService;
import interfaces.irmsInterface.interfaces.station.service.impl.ISyncSiteDataService;
import interfaces.irmsInterface.interfaces.station.service.impl.IirmsQueryService;
import interfaces.irmsInterface.interfaces.station.service.impl.IirmsStationService;
import interfaces.pdainterface.equt.pojo.EqutCableInfo;
import interfaces.pdainterface.equt.pojo.EqutRankInfo;
import interfaces.pdainterface.equt.pojo.OdmFiberInfo;
import interfaces.pdainterface.equt.service.PDAEqutInfoService;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import manage.device.service.impl.IFiberRackService;
import manage.equt.pojo.EqutInfoBean;
import manage.equt.pojo.ODMInfoBean;
import manage.images.service.ResourceImageService;
import manage.point.pojo.PointInfoBean;
import manage.route.pojo.CableInfoBean;
import manage.route.pojo.FiberInfoBean;
import org.apache.log4j.Logger;
public class PDAEqut extends InterfaceAction{

	private static final long serialVersionUID = -3486300674796123267L;
	private static final Logger log = Logger.getLogger(PDAEqut.class);
	private PDAEqutInfoService pdaEqutInfoService;
	private ResourceImageService resourceImageService= null;
	private IFiberRackService fiberRackService;
	private IirmsStationService irmsStationService;
	private IirmsOpticTranService irmsOpticTranService;
	private IirmsQueryService irmsQueryService;
	private ISyncSiteDataService syncSiteDataService;
  
	/**
	 *	 机架设备光交箱
	 * @return
	 */
	public String getEqut(){
		try{
			EqutInfoBean equt = (EqutInfoBean)getRequestObject(EqutInfoBean.class);
			if(equt!=null){
				if(equt.getEtype().equals("3")){
					if(TextUtil.isNotNull(equt.getLat()) && TextUtil.isNotNull(equt.getLon())){
						if(isWGS){
							Point point = MapUtil.phone_db_encrypt(Double.parseDouble(equt.getLat()),Double.parseDouble(equt.getLon()));
							equt.setLat(point.getLat() + "");
							equt.setLon(point.getLng() + "");
						}
						double[] arr = functions.getAround(Double.parseDouble(equt.getLat()), Double.parseDouble(equt.getLon()), ((this.start/this.limit)+1)*Integer.parseInt(properties.getValueByKey("gisLength")));
						equt.setLatl(String.valueOf(arr[0]));
						equt.setLonl(String.valueOf(arr[1]));
						equt.setLath(String.valueOf(arr[2]));
						equt.setLonh(String.valueOf(arr[3]));
					}else{
						equt.setStart(this.start);
						equt.setLimit(this.limit);
					}
					if(TextUtil.isNotNull(super.areaName)){
						equt.setEaddr(super.areaName);
					}
				}else{
					equt.setStart(this.start);
					equt.setLimit(100);
				}
				List<EqutInfoBean> equtList = new LinkedList<EqutInfoBean>();
				if(equt.getEtype().equals("1") && super.fromIrms){
					equtList = irmsQueryService.getEqutList(equt.getGid());
				}else{
					equtList = this.pdaEqutInfoService.getEqut(equt);
					if(TextUtil.isNull(equtList) && TextUtil.isNotNull(equt.getEid()) && !(equt.getEid().startsWith("EIU")) && TextUtil.isNull(equt.getId())){
						equt.setId(Integer.parseInt(equt.getEid()));
						equt.setEid(null);
						equtList = this.pdaEqutInfoService.getEqut(equt);
					}
				}
				//得到最新的机架信息
				for(int i=0;i<equtList.size();i++){
					EqutInfoBean object = equtList.get(i);
					if(object.getEtype().equals("1")) {
						//针对最近综资更改了机架的命名
						int count = this.countChar(object.getEname(), '/');
						if(count >1) {
							String ename = object.getEname().replaceFirst("/", "");
							object.setEname(ename);
						}
					}
					if(isWGS && TextUtil.isNotNull(object.getLat()) && TextUtil.isNotNull(object.getLon())){
						Point point = MapUtil.db_phone_encrypt(Double.parseDouble(object.getLat()), Double.parseDouble(object.getLon()));
						object.setLat(point.getLat()+"");
						object.setLon(point.getLng()+"");
					}
					double posx = (0.095*(i%10))+0.019148936;
					object.setPosX(posx+"");
					double posy =(0.23035599*(i/8))+0.006472492;
					object.setPosY(posy+"");
		          	/*if(TextUtil.isNull(object.getPosX())) {
		          		double posx = (0.095*(i%10))+0.019148936;
		              	object.setPosX(posx+"");
		          	}
		          	if(TextUtil.isNull(object.getPosY())) {
		          		double posy =(0.21035599*(i/8))+0.006472492;
		              	object.setPosY(posy+"");
		          	}*/
				}
				if(equt.getEtype().equals("1")){
					equtList = this.setEqutList(equtList,equt);
				}
				sendResponse(Integer.valueOf(0), equtList);
			}else{
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		}catch(Exception e){
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDALogin.login ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
		}
		return "success";
  }
  
  /**
   * 得到字符串中包含几个斜杠
   * @param str
   * @param ch
   * @return
   */
  public int countChar(String str,char ch) {
	    char[] chs = str.toCharArray();
	    int count = 0;
	    for(int i = 0;i < chs.length;i++) {
	        if(chs[i] == ch) {
	        count++;
	        }
	    }
	    return count;
	}
  
  List<EqutInfoBean> setEqutList(List<EqutInfoBean> equtList,EqutInfoBean equt){
      //下面的方法是没有办法了，只能虚拟出一个机架来，无线设备没有机架概念，怎么弄
      String deviceSql = "select count(*) from job_device where roomId ='"+equt.getGid()+"'"
      		+ " and eid is null and resNum is not null ";
      int id = this.pdaEqutInfoService.getDeviceCount(deviceSql);
      if(id >0){
      	EqutInfoBean synRack = new EqutInfoBean();
      	synRack.setEtype("1");
      	synRack.setEid("EID_"+this.getRandomRack(7));
      	synRack.setEname("/"+"综合机架");
      	synRack.setGid(equt.getGid());
      	synRack.setPosX("0.5");
      	synRack.setJijialeixing(2);
      	synRack.setPosY("0.5");
      	synRack.setMflag(1);
      	synRack.setHasallow(0);
      	int synId = this.pdaEqutInfoService.insertEqut(synRack);
      	synRack.setId(synId);
      	String upSql = "update job_device set eid ='"+synRack.getEid()+"' where roomId ='"+equt.getGid()+"' ";
      	this.pdaEqutInfoService.exeSql(upSql);
      	equtList.add(synRack);
      }
      return equtList;
  }

  	/**
  	 * 更改机架
  	 * @return
  	 */
	public String updateEqut() {
		try {
			EqutInfoBean equt = (EqutInfoBean) getRequestObject(EqutInfoBean.class);
			if (equt != null && TextUtil.isNotNull(equt.getEid())) {
				if(isWGS && TextUtil.isNotNull(equt.getLat()) && TextUtil.isNotNull(equt.getLon())){
					Point point = MapUtil.phone_db_encrypt(Double.parseDouble(equt.getLat()), Double.parseDouble(equt.getLon()));
					equt.setLat(point.getLat()+"");
					equt.setLon(point.getLng()+"");
				}
				if(TextUtil.isNotNull(super.realName)){
					equt.setParties(super.realName);
				}
				int result = this.pdaEqutInfoService.updateEqut(equt);
				if (result == 0) {
					sendResponse(Integer.valueOf(0), "修改成功。");
					new UptranBox(equt.getEid()).start();
				} else {
					sendResponse(Integer.valueOf(2), "资源标识无权删除!");
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * 光交箱修改
	 * @author chenqp
	 *
	 */
	class UptranBox extends Thread{
		private String eid;
		public UptranBox(String eid){
			this.eid= eid;
		}
		@Override
		public void run() {
			if(toIrms){
				EqutInfoBean equt = new EqutInfoBean();
				equt.setEid(eid);
				equt = pdaEqutInfoService.getEqutObj(equt);
				irmsOpticTranService.moveOptiTranBox(equt);
			}
			super.run();
		}
	}

  /**
   * 增加机架
   * @return
   */
	public String insertEqut() {
		try {
			EqutInfoBean equt = (EqutInfoBean) getRequestObject(EqutInfoBean.class);
			if (equt != null) {
				if (this.checkEqut(equt.getEname(),null) > 0) {
					sendResponse(Integer.valueOf(2), "资产标签已被占用。");
				} else {
					if(equt.getEtype().equals("1") && (TextUtil.isNull(equt.getPosX()) || TextUtil.isNull(equt.getPosY())) && TextUtil.isNotNull(equt.getJijialiehao()) && TextUtil.isNotNull(equt.getJijiahanghao())){
						java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.##");  
						equt.setPosX((df.format(Integer.parseInt(equt.getJijialiehao())*0.1))+"");
						equt.setPosY((df.format(Integer.parseInt(equt.getJijialiehao())*0.1+0.3))+"");
					}
					if(isWGS && TextUtil.isNotNull(equt.getLat()) && TextUtil.isNotNull(equt.getLon())){
						Point point = MapUtil.phone_db_encrypt(Double.parseDouble(equt.getLat()), Double.parseDouble(equt.getLon()));
						equt.setLat(point.getLat()+"");
						equt.setLon(point.getLng()+"");
					}
					if(TextUtil.isNotNull(super.realName)){
						equt.setParties(super.realName);
					}
					if(equt.getEtype().equals("3")){
						equt.setEid("EIU_"+System.currentTimeMillis());
					}
					int id = this.pdaEqutInfoService.insertEqut(equt);
					if (id != -1) {
						equt.setId(Integer.valueOf(id));
						List<EqutInfoBean> list = this.pdaEqutInfoService.getEqut(equt);
						EqutInfoBean obj = list.get(0);
						//调用综资实时增加机架
						if(super.toIrms){
							new addEqut(obj.getEid()).start();
						}
						if(isWGS && TextUtil.isNotNull(obj.getLat()) && TextUtil.isNotNull(obj.getLon())){
							Point point = MapUtil.db_phone_encrypt(Double.parseDouble(obj.getLat()), Double.parseDouble(obj.getLon()));
							obj.setLat(point.getLat()+"");
							obj.setLon(point.getLng()+"");
						}
						sendResponse(Integer.valueOf(0), obj);
					} else {
						sendResponse(Integer.valueOf(2), "资产标签已被占用。");
					}
				}
			} else {
				sendResponse(Integer.valueOf(2), "请求参数错误。");
			}
		} catch (Exception e) {
			this.exception = e;
			sendResponse(Integer.valueOf(3), "应用服务器异常。");
			log.error("PDALogin.login ERROR\nJsonRequest:" + getJsonRequest()
					+ "\n" + getJsonResponse(), e);
		}
		return "success";
	}
	
	/**
	 * 增加一个机架
	 * @author chenqp
	 *
	 */
	class addEqut extends Thread{
		
		private String eid;
		public addEqut(String eid){
			this.eid = eid;
		}
		@Override
		public void run() {
			EqutInfoBean equt = new EqutInfoBean();
			equt.setEid(eid);
			equt = pdaEqutInfoService.getEqutObj(equt);
			if(equt.getEtype().equals("1")){
				irmsStationService.insertOdf(equt);
			}else if(equt.getEtype().equals("3")){
				irmsOpticTranService.addOptiTranBox(equt);
			}
			super.run();
		}
	}
  
  /**
   * 检查 机架
   * @param name
   */
  public Integer checkEqut(String name ,String id){
	  String sql ="select count(*) from job_equtinfo where del ='0' and ENAME ='"
			  +name+"'";
	  int size = this.getJdbcTemplate().queryForInt(sql);
	  return size;
  }

  /**
   * 查询得到ODM信息
   * @return
   */
  public String getODM() {
    try {
      ODMInfoBean odm = (ODMInfoBean)getRequestObject(ODMInfoBean.class);
      if (odm != null) {
        List<ODMInfoBean> odmList = this.pdaEqutInfoService.getODM(odm);
        //这个操作是没有办法的办法
        for(int i=0;i<odmList.size();i++){
        	ODMInfoBean obj = odmList.get(i);
        	int rows = (odmList.size()/10 +1);
        	if(TextUtil.isNull(obj.getOdmCode()) || TextUtil.isNull(obj.getPosX()) || TextUtil.isNull(obj.getPosY())){
        		if(TextUtil.isNull(obj.getOdmCode())){
        			obj.setOdmCode(i+"");
        		}
        		if(TextUtil.isNull(obj.getPosX())) {
        			double posX = (0.2597235*(i%rows))+0.18611112;
        			obj.setPosX(posX+"");
        		}
        		if(TextUtil.isNull(obj.getPosY())) {
        			double posY = (0.1126 * i/rows)+0.0508;
        			obj.setPosY(posY+"");
        		}
        		/*if(TextUtil.isNull(obj.getPosX())){
        			obj.setPosX(this.getNonce());
        		}
        		if(TextUtil.isNull(obj.getPosY())){
        			obj.setPosY(this.getNonce());
        		}*/
        	}
        }
        sendResponse(Integer.valueOf(0), odmList);
      }else{
        sendResponse(Integer.valueOf(2), "请求参数错误。");  
      }
    }
    catch (Exception e) {
      this.exception = e;
      sendResponse(Integer.valueOf(3), "应用服务器异常。");
      log.error("PDALogin.login ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
    }
    return "success";
  }
  
  String getNonce(){
	  double num = Math.random();
	  DecimalFormat  df=new DecimalFormat("#.####");
	  return df.format(num);
  }
  
  
  /**
   * 重置odm
   * @return
   */
  public String resetODM() {
	  try {
		  ODMInfoBean odm = (ODMInfoBean)getRequestObject(ODMInfoBean.class);
		  if(null != odm) {
			  String odmName = odm.getOdmName();
			  odm.setOdmName(null);
			  List<ODMInfoBean> list = this.pdaEqutInfoService.getODM(odm);
			  if(TextUtil.isNotNull(list)) {
				  ODMInfoBean bean = list.get(0);
				  bean.setOdmName(odmName);
				  bean.setInfo(odm.getInfo());
				  this.pdaEqutInfoService.updateOdmObj(bean);
			  }
			  sendResponse(Integer.valueOf(0), "修改成功!");
		  }else {
			  sendResponse(Integer.valueOf(2), "请求参数错误。");  
		  }
	  }catch(Exception e) {
		  this.exception = e;
	      sendResponse(Integer.valueOf(3), "应用服务器异常。");
	      log.error("PDAequt.resetODM ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
	  }
	  return "success";
  }

  /**
   * 更新ODM信息
   * @return
   */
  public String updateODM() {
    try {
      List odmList = (List)getRequestObject(ODMInfoBean.class);
      if (odmList != null) {
    	  ODMInfoBean odm = (ODMInfoBean) odmList.get(0);
    	  odm.setCuser(this.longiner);
    	  if(TextUtil.isNotNull(odm.getOdmId())){
    		  this.pdaEqutInfoService.updateODM(odmList);
    	  }else{
    		  this.pdaEqutInfoService.upAndSaveOdm(odmList);
    	  }
        sendResponse(Integer.valueOf(0), "修改成功。");
        //更新综资数据
        if(super.toIrms){
        	if(odm.getDeleteFlag().equals("1")){
            	//机架下面的删除ODM
            	if(odm.getEid().startsWith("EQU")){
            		new delOdm(odm).start();
            	}else if(odm.getEid().startsWith("EIU")){
            		new delOdm(odm).start();
            	}
            }
        }
      }else{
        sendResponse(Integer.valueOf(2), "请求参数错误。");
      }
    }
    catch (Exception e) {
      this.exception = e;
      sendResponse(Integer.valueOf(3), "应用服务器异常。");
      log.error("PDALogin.login ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
    }
    return "success";
  }
  
  /**
   * 删除ODM
   * @author chenqp
   *
   */
  class delOdm extends Thread{
	private ODMInfoBean odm;
	public delOdm(ODMInfoBean odm){
		this.odm = odm;
	}
	@Override
	public void run() {
		irmsStationService.delOdm(odm);
		super.run();
	}
  }
  
  

  /**
   * 添加ODM设备
   * @return
   */
  public String insertODM() {
    try {
      List odmList = (List)getRequestObject(ODMInfoBean.class);
      if (odmList != null) {
        List<ODMInfoBean> list =this.pdaEqutInfoService.insertODM(odmList,this.longiner);
        sendResponse(Integer.valueOf(0), "添加成功。");
        //进行同步到综资
        if(toIrms){
        	for(int i =0;i<list.size();i++){
            	ODMInfoBean obj = list.get(i);
            	if(obj.getEid().startsWith("EQU")){
                	new addOdm(obj).start();
                }else if(obj.getEid().startsWith("EIU")){
                	new addOdm(obj).start();
                }
            }
        }
        
      }else{
    	  sendResponse(Integer.valueOf(2), "请求参数错误。");
      }
    }
    catch (Exception e) {
      this.exception = e;
      sendResponse(Integer.valueOf(3), "应用服务器异常。");
      log.error("PDALogin.login ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
    }
    return "success";
  }
  
  /**
   * 增加ODM
   * @author chenqp
   *
   */
  class addOdm extends Thread{
	  private ODMInfoBean odm;
	  public addOdm(ODMInfoBean odm){
		  this.odm = odm;
	  }
	  @Override
	public void run() {
		irmsStationService.insertOdm(odm);
		super.run();
	}
  }
  
 

  /**
   * 得到端口
   * 信息
   * @return
   */
  public String getPoint() {
    try {
      PointInfoBean point = (PointInfoBean)getRequestObject(PointInfoBean.class);
      if (point != null) {
        List<PointInfoBean> pointList = this.pdaEqutInfoService.getPoint(point);
        List<PointInfoBean> syncList = this.getSyncPoint(pointList);
        if(TextUtil.isNotNull(syncList)) {
        	this.pdaEqutInfoService.insertPoint(pointList);
        	for(PointInfoBean sync : syncList) {
        		pointList.add(sync);
        	}
        }
        
        sendResponse(Integer.valueOf(0), pointList);
      }else{
    	sendResponse(Integer.valueOf(2), "请求参数错误。");  
      }
    }
    catch (Exception e) {
      this.exception = e;
      sendResponse(Integer.valueOf(3), "应用服务器异常。");
      log.error("PDALogin.login ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
    }
    return "success";
  }
  
  
  /**
   * 得到实时端子数据
   * @param pointList
   * @return
   */
  public List<PointInfoBean> getSyncPoint(List<PointInfoBean> pointList){
	  List<PointInfoBean> list = new LinkedList<PointInfoBean>();
	  String odmId = "";
	  String resNumId = "";
	  List<Integer> lineList = new ArrayList<Integer>();
	  List<Integer> rowList = new ArrayList<Integer>();
	  for(PointInfoBean point : pointList) {
		  odmId = point.getOdmId()+"";
		  lineList.add(Integer.parseInt(point.getPlineno()));
		  rowList.add(Integer.parseInt(point.getProwno()));
		  if(TextUtil.isNotNull(point.getResNum())) {
			  resNumId +=point.getResNum()+",";
		  }
		 
	  }
	  if(TextUtil.isNotNull(resNumId)) {
		  if(resNumId.endsWith(",")) {
			  resNumId = resNumId.substring(0, resNumId.length()-1);
		  }
		  int lineNum = Collections.max(lineList);
		  int rowNum = Collections.max(rowList);
		  if(pointList.size() / lineNum != rowNum) {
			  list = syncSiteDataService.recordPoint(odmId, resNumId);
		  }
	  }
	  return list;
  }
  
  
  /**
   * 得到端子详情
   * @return
   */
  public String getPointObj(){
	  try {
	      PointInfoBean point = (PointInfoBean)getRequestObject(PointInfoBean.class);
	      if (point != null) {
	        point =  this.pdaEqutInfoService.getPointObj(point);
	        sendResponse(Integer.valueOf(0), point);
	      }else{
	    	sendResponse(Integer.valueOf(2), "请求参数错误。");  
	      }
	    }
	    catch (Exception e) {
	      this.exception = e;
	      sendResponse(Integer.valueOf(3), "应用服务器异常。");
	      log.error("PDAEqut.getPointObj ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
	    }
	    return "success";
  }

  /**
   * 修改端口
   * 信息
   * @return
   */
  public String updatePoint() {
    try {
      PointInfoBean point = (PointInfoBean)getRequestObject(PointInfoBean.class);
      if (point != null) {
        this.pdaEqutInfoService.updatePoint(point);
        sendResponse(Integer.valueOf(0), "修改成功。");
      }else{
    	sendResponse(Integer.valueOf(2), "请求参数错误。");  
      }
    }
    catch (Exception e) {
      this.exception = e;
      sendResponse(Integer.valueOf(3), "应用服务器异常。");
      log.error("PDAEqut.updatePoint ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
    }
    return "success";
  }
  
  /**
   * 删除端子的业务信息
   * @return
   */
  public String delPointBusiness(){
	  try{
		  PointInfoBean point = (PointInfoBean) getRequestObject(PointInfoBean.class);
		  if(point !=null){
			  point = this.pdaEqutInfoService.getPointObj(point);
			  point.setPstat("0");
			  this.pdaEqutInfoService.updatePoint(point);
			  sendResponse(Integer.valueOf(0), "更改成功。");
			  new delPointFiber(point.getId()).start();
		  }else{
			  sendResponse(Integer.valueOf(2), "参数列表错误。");
		  }
	  }catch(Exception e){
		  this.exception = e;
	      sendResponse(Integer.valueOf(3), "应用服务器异常。");
	      log.error("PDAEqut.delPointBusiness ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
	  }
	  return "success";
  }
  
  class delPointFiber extends Thread{
	private Integer pointId;
	public delPointFiber(Integer pointId){
		this.pointId = pointId;
	}
	@Override
	public void run() {
		
		PointInfoBean point = new PointInfoBean();
		point.setId(pointId);
		point = pdaEqutInfoService.getPointObj(point);
		if(toIrms){
			irmsStationService.delFiber(point);
		}
		pdaEqutInfoService.delPointBusiness(point);
		super.run();
	}
  }

  /**
   * 增加端口
   * @return
   */
  public String insertPoint() {
    try {
      List pointList = (List)getRequestObject(PointInfoBean.class);
      if (pointList != null) {
        this.pdaEqutInfoService.insertPoint(pointList);
        sendResponse(Integer.valueOf(0), "添加成功。");
      }else{
    	sendResponse(Integer.valueOf(2), "请求参数错误。");
      }
    }
    catch (Exception e) {
      this.exception = e;
      sendResponse(Integer.valueOf(3), "应用服务器异常。");
      log.error("pdaequt.insertPoint ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
    }
     return "success";
  }
  
  /**
   * 根据机架的EID和机架
   * 的类型得到成端光缆段
   * @return
   */
  public String getEqutCable(){
	  try{
		  EqutCableInfo ecable = (EqutCableInfo) getRequestObject(EqutCableInfo.class);
		  List<EqutCableInfo> list = this.pdaEqutInfoService.getEqutCable(ecable);
		  if(TextUtil.isNotNull(list)){
			  sendResponse(Integer.valueOf(0), list);
		  }else{
			  sendResponse(Integer.valueOf(1), "查无数据。");
		  }
	  }catch(Exception e){
		  this.exception = e;
	      sendResponse(Integer.valueOf(3), "应用服务器异常。");
	      log.error("pdaequt.getEqutCable ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
	  }
	  return "success";
  }
  
  /**
   * 纤芯上架
   * @return
   */
  public String equtCableRank(){
	  try{
		  EqutRankInfo equtRank = (EqutRankInfo) getRequestObject(EqutRankInfo.class);
		  if(equtRank != null){
			  PointInfoBean bean = new PointInfoBean();
			  bean.setPstat("0");
			  bean.setOdmId(equtRank.getOdmId());
			  List<PointInfoBean> list = this.pdaEqutInfoService.getNullPoint(bean);
			  if(TextUtil.isNotNull(list)){
				  sendResponse(Integer.valueOf(0), "完成落架");
				  new appSaveFiber(list, equtRank).start();
			  }else{
				  sendResponse(Integer.valueOf(1), "无空闲端子。");
			  }
		  }else{
			  sendResponse(Integer.valueOf(2), "请求参数错误。");
		  }
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	  return "success";
  }
  
  
  /**
   * APP保存纤芯数据
   * @author chenqp
   *
   */
  class appSaveFiber extends Thread{
	 private List<PointInfoBean> list ;
	 private EqutRankInfo equtRank;
	  public appSaveFiber(List<PointInfoBean> list,EqutRankInfo equtRank){
		  this.list = list;
		  this.equtRank = equtRank;
	  }
	  @Override
	public void run() {
		  List<PointInfoBean> pointList = new ArrayList<PointInfoBean>();
		  int size = equtRank.getFiberNum();
		  if(size > list.size()){
			  size = list.size();
		  }
		  for(int i=0;i<size;i++){
			  PointInfoBean point = list.get(i);
			  point.setPstat("1");
			  pointList.add(point);
		  }
		  int id = fiberRackService.batchFiberOdmPoint(pointList,equtRank);
		  if(toIrms){
			  irmsStationService.saveFiber(equtRank);
		  }
		super.run();
	}
  }
  
  
  
  /**
   * 纤芯落架单个纤芯
   * @return
   */
  public String fallApart(){
	  try{
		  EqutRankInfo equtRank = (EqutRankInfo) getRequestObject(EqutRankInfo.class);
		  if(equtRank != null){
			  CableInfoBean  cable = this.fiberRackService.getCableById(equtRank.getCableId());
			  PointInfoBean point = this.fiberRackService.getPointInfo(equtRank.getPointId());
			  EqutInfoBean equt = this.fiberRackService.getEqutByPoint(point.getEid());
			  FiberInfoBean fiber = this.fiberRackService.getFiberById(equtRank.getFiberId());
			  if(TextUtil.isNotNull(cable.getCablename()) && TextUtil.isNotNull(fiber.getZhLabel()) && TextUtil.isNotNull(point.getEid())){
				  sendResponse(Integer.valueOf(0), "完成落架");
				  new FallAport(cable, point, equt, fiber).start();
			  }else{
				  sendResponse(Integer.valueOf(1), "落架失败,资源数据为空");
			  }
			 
		  }else{
			 sendResponse(Integer.valueOf(2), "请求参数错误。");
		  }
	  }catch(Exception e){
		  this.exception = e;
	      sendResponse(Integer.valueOf(3), "应用服务器异常。");
	      log.error("pdaequt.fallApart ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
	  }
	  return "success";
  }
  
  class FallAport extends Thread{
	  private CableInfoBean cable;
	  private PointInfoBean point;
	  private EqutInfoBean equt;
	  private FiberInfoBean fiber;
	  public FallAport(CableInfoBean cable,PointInfoBean point,EqutInfoBean equt,FiberInfoBean fiber){
		  this.cable = cable;
		  this.point = point;
		  this.equt = equt;
		  this.fiber = fiber;
	  }
	  
	  @Override
	public void run() {
		String dection = fiberRackService.fallApartPoint(cable, equt, point, fiber);
		if(toIrms){
			EqutRankInfo equtRank = new EqutRankInfo();
			equtRank.setCableId(cable.getCableid());
			equtRank.setFiberId(fiber.getId());
			equtRank.setPointId(point.getId());
			equtRank.setCableItem(dection);
			irmsStationService.saveFiber(equtRank);
		}
		super.run();
	}
  }
  
  String getRandomRack(int strLength) {  
	    Random rm = new Random();  
	    // 获得随机数  
	    double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);  
	    // 将获得的获得随机数转化为字符串  
	    String fixLenthString = String.valueOf(pross);  
	    // 返回固定的长度的随机数  
	    return fixLenthString.substring(2, strLength + 1);  
	} 
  
  
  /**
   * 增加odm落架
   * @return
   */
  public String addOdmFiber(){
	  try{
		  OdmFiberInfo odmFiber = (OdmFiberInfo) getRequestObject(OdmFiberInfo.class);
		  if(odmFiber != null){
			  CableInfoBean  cable = this.fiberRackService.getCableById(odmFiber.getCableId());
			  String result = this.pdaEqutInfoService.batchOdmFiber(odmFiber, cable);
			  if(result.equals("true")){
				  int id = this.pdaEqutInfoService.insertOdmFiber(odmFiber);
				  odmFiber.setId(id);
				  sendResponse(Integer.valueOf(0), "落架成功。");
			  }else{
				  sendResponse(Integer.valueOf(1), "所选光缆无空余纤芯。");
			  }
		  }else{
			  sendResponse(Integer.valueOf(2), "请求参数错误。");
		  }
	  }catch(Exception e){
		  this.exception = e;
	      sendResponse(Integer.valueOf(3), "应用服务器异常。");
	      log.error("pdaequt.addOdmFiber ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
	  }
	  return "success";
  }
  
  
  /**
   * 落架信息
   * @return
   */
  public String getOdmFiber(){
	  try{
		  OdmFiberInfo odmFiber = (OdmFiberInfo) getRequestObject(OdmFiberInfo.class);
		  if(odmFiber != null){
			  List<OdmFiberInfo> list = this.pdaEqutInfoService.getOdmFiber(odmFiber);
			  sendResponse(Integer.valueOf(0), list); 
		  }else{
			  sendResponse(Integer.valueOf(2), "请求参数错误。");
		  }
	  }catch(Exception e){
		  this.exception = e;
	      sendResponse(Integer.valueOf(3), "应用服务器异常。");
	      log.error("pdaequt.getOdmFiber ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
	  }
	  return "success";
  }
  
  
  /**
   * 删除ODMfiber
   * @return
   */
  public String delOdmFiber(){
	  try{
		  OdmFiberInfo odmFiber = (OdmFiberInfo) getRequestObject(OdmFiberInfo.class);
		  if(odmFiber != null){
			  List<OdmFiberInfo> list = this.pdaEqutInfoService.getOdmFiber(odmFiber);
			  if(TextUtil.isNotNull(list)){
				  odmFiber = list.get(0);
				  this.pdaEqutInfoService.delOdmFiber(odmFiber);
			  }
			  sendResponse(Integer.valueOf(0), "删除成功"); 
		  }else{
			  sendResponse(Integer.valueOf(2), "请求参数错误。");
		  }
	  }catch(Exception e){
		  this.exception = e;
	      sendResponse(Integer.valueOf(3), "应用服务器异常。");
	      log.error("pdaequt.delOdmFiber ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
	  }
	  return "success";
  }

  public PDAEqutInfoService getPdaEqutInfoService() {
    return this.pdaEqutInfoService;
  }
  
  public IFiberRackService getFiberRackService() {
	return fiberRackService;
}

public void setFiberRackService(IFiberRackService fiberRackService) {
	this.fiberRackService = fiberRackService;
}

public IirmsStationService getIrmsStationService() {
	return irmsStationService;
}

public void setIrmsStationService(IirmsStationService irmsStationService) {
	this.irmsStationService = irmsStationService;
}

public IirmsOpticTranService getIrmsOpticTranService() {
	return irmsOpticTranService;
}

public void setIrmsOpticTranService(IirmsOpticTranService irmsOpticTranService) {
	this.irmsOpticTranService = irmsOpticTranService;
}

public IirmsQueryService getIrmsQueryService() {
	return irmsQueryService;
}

public void setIrmsQueryService(IirmsQueryService irmsQueryService) {
	this.irmsQueryService = irmsQueryService;
}
public ISyncSiteDataService getSyncSiteDataService() {
	return syncSiteDataService;
}
public void setSyncSiteDataService(ISyncSiteDataService syncSiteDataService) {
	this.syncSiteDataService = syncSiteDataService;
}
public ResourceImageService getResourceImageService() {
	return resourceImageService;
  }
  public void setResourceImageService(ResourceImageService resourceImageService) {
	this.resourceImageService = resourceImageService;
  }
  public void setPdaEqutInfoService(PDAEqutInfoService pdaEqutInfoService) {
    this.pdaEqutInfoService = pdaEqutInfoService;
  }
}