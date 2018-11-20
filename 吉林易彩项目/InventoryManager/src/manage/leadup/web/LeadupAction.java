package manage.leadup.web;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import base.jsonDateConvert.DateJsonValueProcessor;
import manage.leadup.pojo.HangWallPojo;
import manage.leadup.pojo.LeadupPojo;
import manage.leadup.pojo.SupportPointPojo;
import manage.leadup.service.impl.IleadupService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;

import base.util.TextUtil;
import base.web.PaginationAction;
/**
 * 引上后台处理
 * @author chenqp
 *
 */
public class LeadupAction extends PaginationAction {
	private int start = 0;
	private int length = 15;
	private static final Logger log = Logger.getLogger(LeadupAction.class);
	private LeadupPojo object = new LeadupPojo();
	private String jsonString;
	private IleadupService leadupService;
	private HangWallPojo hangWallPojo = new HangWallPojo();
	private SupportPointPojo supportPointPojo = new SupportPointPojo();
	private String sportName;
	private String purpose;
	private String maintArea;
	private String maintDept;
	private String longitude;
	private String latitude;
	private String maintainer;
	private String maintMode;
	private Integer limit;
	
	private String hangWallName;
	private String direction;
	private String username;
	private Double hangLength;
	private Double maintLength;
	private String startDeviceName;
	private String endDeviceName;
	private Integer startDeviceId;//起始撑点id 
	private Integer endDeviceId;//终止撑点id
	
	public Integer getStartDeviceId() {
		return startDeviceId;
	}
	public void setStartDeviceId(Integer startDeviceId) {
		this.startDeviceId = startDeviceId;
	}
	public Integer getEndDeviceId() {
		return endDeviceId;
	}
	public void setEndDeviceId(Integer endDeviceId) {
		this.endDeviceId = endDeviceId;
	}
	public HangWallPojo getHangWallPojo() {
		return hangWallPojo;
	}
	public void setHangWallPojo(HangWallPojo hangWallPojo) {
		this.hangWallPojo = hangWallPojo;
	}
	public String getHangWallName() {
		return hangWallName;
	}
	public void setHangWallName(String hangWallName) {
		this.hangWallName = hangWallName;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Double getHangLength() {
		return hangLength;
	}
	public void setHangLength(Double hangLength) {
		this.hangLength = hangLength;
	}
	public Double getMaintLength() {
		return maintLength;
	}
	public void setMaintLength(Double maintLength) {
		this.maintLength = maintLength;
	}
	public String getStartDeviceName() {
		return startDeviceName;
	}
	public void setStartDeviceName(String startDeviceName) {
		this.startDeviceName = startDeviceName;
	}
	public String getEndDeviceName() {
		return endDeviceName;
	}
	public void setEndDeviceName(String endDeviceName) {
		this.endDeviceName = endDeviceName;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public String getSportName() {
		return sportName;
	}
	public void setSportName(String sportName) {
		this.sportName = sportName;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getMaintArea() {
		return maintArea;
	}
	public void setMaintArea(String maintArea) {
		this.maintArea = maintArea;
	}
	public String getMaintDept() {
		return maintDept;
	}
	public void setMaintDept(String maintDept) {
		this.maintDept = maintDept;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getMaintainer() {
		return maintainer;
	}
	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}
	public String getMaintMode() {
		return maintMode;
	}
	public void setMaintMode(String maintMode) {
		this.maintMode = maintMode;
	}
	public SupportPointPojo getSupportPointPojo() {
		return supportPointPojo;
	}
	public void setSupportPointPojo(SupportPointPojo supportPointPojo) {
		this.supportPointPojo = supportPointPojo;
	}
	public IleadupService getLeadupService() {
		return leadupService;
	}
	public void setLeadupService(IleadupService leadupService) {
		this.leadupService = leadupService;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public LeadupPojo getObject() {
		return object;
	}
	public void setObject(LeadupPojo object) {
		this.object = object;
	}
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	public static Logger getLog() {
		return log;
	}
	
	/**
	 * 查询所有撑点
	 * @return
	 */
	public String getSupportPointList() {
		supportPointPojo.setStart(start);
		supportPointPojo.setLimit(limit);
		
		List<SupportPointPojo> supportPointPojoList = this.leadupService.getsPointList(supportPointPojo);
		
		int total = this.leadupService.getsPointCount(supportPointPojo);
		supportPointPojo.setSupportPointPojoList(supportPointPojoList);
		supportPointPojo.setTotal(total);
		
		return "getSupportPointList";
	}
	
	/**
	 * 添加撑点
	 */
	public void saveSupportPointPojo() {
		try{
			supportPointPojo.setCreateTime(new Date());
			supportPointPojo.setSportName(sportName);
			supportPointPojo.setPurpose(purpose);
			supportPointPojo.setMaintArea(maintArea);
			supportPointPojo.setMaintDept(maintDept);
			supportPointPojo.setLongitude(longitude);
			supportPointPojo.setLatitude(latitude);
			supportPointPojo.setMaintainer(maintainer);
			supportPointPojo.setMaintMode(Integer.parseInt(maintMode));
			supportPointPojo.setStatus(0);
			leadupService.addSupportPoint(supportPointPojo);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到撑点详情
	 */
	public String getDetailSupportPoint() {
		try{
			String id = this.getRequest().getParameter("id");
			System.out.println("id:" + id);
			supportPointPojo.setId(Integer.parseInt(id));
			List<SupportPointPojo> list = leadupService.getsPointList(supportPointPojo);
			if(TextUtil.isNotNull(list)){
				supportPointPojo = list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "getDetailSupportPoint";
	}
	
	/**
	 * 修改撑点详情
	 */
	public void updateSupportPoint(){
		try{
			
			String id = this.getRequest().getParameter("id");
			
			supportPointPojo.setId(Integer.parseInt(id));
			supportPointPojo.setSportName(sportName);
			supportPointPojo.setPurpose(purpose);
			supportPointPojo.setMaintArea(maintArea);
			supportPointPojo.setMaintDept(maintDept);
			supportPointPojo.setLongitude(longitude);
			supportPointPojo.setLatitude(latitude);
			supportPointPojo.setMaintainer(maintainer);
			
			if ("自维".equals(maintMode)) {
				maintMode = "1";
				supportPointPojo.setMaintMode(Integer.parseInt(maintMode));
			} else if("代维".equals(maintMode)) {
				maintMode = "2";
				supportPointPojo.setMaintMode(Integer.parseInt(maintMode));
			} else if ("其他".equals(maintMode)) {
				maintMode = "0";
				supportPointPojo.setMaintMode(Integer.parseInt(maintMode));
			}
			
			
			supportPointPojo.setLastUpTime(new Date());
			leadupService.upsPoint(supportPointPojo);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除撑点
	 */
	public void delSupportPoint(){
		try{
			String parm = this.getRequest().getParameter("parms");
			leadupService.delSupportPoint(parm);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询所有挂墙段
	 * @return
	 */
	public void getHangWallList() {
		try {
			hangWallPojo.setStart(start);
			hangWallPojo.setLimit(limit);
			
			List<HangWallPojo> hangWallPojoList = this.leadupService.getHangWallList(hangWallPojo);
			int total = this.leadupService.getHangWallCount(hangWallPojo);
			
			JsonConfig cfg = new JsonConfig();  
			cfg.registerJsonValueProcessor(java.util.Date.class,new DateJsonValueProcessor());
			JSONArray jsonArray = JSONArray.fromObject(hangWallPojoList, cfg);
			String hangWallPojoJson = jsonArray.toString();
			String json = "{"+"hangWallList:" + hangWallPojoJson+",total:"+total+"}";
			
			System.out.println(hangWallPojoJson);
			
			printString(json, null);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加挂墙段
	 */
	public void saveHangWallPojo() {
		try{
			hangWallPojo.setHangWallName(hangWallName);
			
			if ("1".equals(maintMode)) {
				hangWallPojo.setMaintMode("自维");
			} else if("2".equals(maintMode)) {
				hangWallPojo.setMaintMode("代维");
			} else if ("0".equals(maintMode)) {
				hangWallPojo.setMaintMode("其他");
			}
			
			hangWallPojo.setMaintArea(maintArea);
			hangWallPojo.setDirection(direction);
			hangWallPojo.setPurpose(purpose);
			hangWallPojo.setUsername(username);
			hangWallPojo.setHangLength(hangLength);
			hangWallPojo.setMaintLength(maintLength);
			hangWallPojo.setStartDeviceName(startDeviceName);
			hangWallPojo.setEndDeviceName(endDeviceName);
			hangWallPojo.setMaintainer(maintainer);
			hangWallPojo.setEndDeviceId(endDeviceId);
			hangWallPojo.setStartDeviceId(startDeviceId);
			
			hangWallPojo.setCreateTime(new Date());
			
			leadupService.insertHangWall(hangWallPojo);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除挂墙段
	 */
	public void delHangWall(){
		try{
			String parm = this.getRequest().getParameter("parms");
			leadupService.delHangWall(parm);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到挂墙段详情
	 */
	public void getDetailHangWall() {
		try{
			String id = this.getRequest().getParameter("id");
			hangWallPojo.setId(Integer.parseInt(id));
			List<HangWallPojo> list = leadupService.getHangWallList(hangWallPojo);
			if(TextUtil.isNotNull(list)){
				hangWallPojo = list.get(0);
			}
			
			JSONObject jsonObject = JSONObject.fromObject(hangWallPojo);
			printString(jsonObject.toString(), null);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 修改挂墙段详情
	 */
	public void updateHangWall(){
		try{
			
			String id = this.getRequest().getParameter("id");
			hangWallPojo.setId(Integer.parseInt(id));
			
			hangWallPojo.setHangWallName(hangWallName);
			
			hangWallPojo.setMaintArea(maintArea);
			hangWallPojo.setDirection(direction);
			hangWallPojo.setPurpose(purpose);
			hangWallPojo.setUsername(username);
			hangWallPojo.setHangLength(hangLength);
			hangWallPojo.setMaintLength(maintLength);
			hangWallPojo.setStartDeviceName(startDeviceName);
			hangWallPojo.setEndDeviceName(endDeviceName);
			hangWallPojo.setMaintainer(maintainer);
			hangWallPojo.setEndDeviceId(endDeviceId);
			hangWallPojo.setStartDeviceId(startDeviceId);
			
			/*if ("1".equals(maintMode)) {
				hangWallPojo.setMaintMode("自维");
			} else if("2".equals(maintMode)) {
				hangWallPojo.setMaintMode("代维");
			} else if ("0".equals(maintMode)) {
				hangWallPojo.setMaintMode("其他");
			}*/
			
			hangWallPojo.setMaintMode(maintMode);
			hangWallPojo.setLastUpTime(new Date());
			leadupService.updateHangWall(hangWallPojo);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void printString(String string, String contentType) throws Exception {
		this.getResponse().setHeader("Cache-Control", "no-cache");
		this.getResponse().setContentType(contentType);
		this.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter pw = this.getResponse().getWriter();
		pw.write(string);
		pw.close();
	}
	
	/*@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return object;
	}*/

}
