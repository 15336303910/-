package manage.buried.web;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import manage.buried.pojo.BuriedInfoObj;
import manage.buried.service.impl.IburiedService;
import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ModelDriven;

import base.util.JsonUtil;
import base.util.TextUtil;
import base.web.PaginationAction;

public class BuriedAction extends PaginationAction implements ModelDriven{
	private int start = 0;
	private int length = 15;
	private static final Logger log = Logger.getLogger(BuriedAction.class);
	private BuriedInfoObj object = new BuriedInfoObj();
	private IburiedService buriedService;
	private String jsonString;
	private java.io.File file;
	private String fileFileName;
	
	/**
	 * 直埋
	 * 得到分页数据
	 */
	public void getBuriedGrid(){
		try{
			this.object.setStart(start);
			this.object.setLimit(length);
			List<BuriedInfoObj> list = buriedService.getBuriedGrid(object);
			int count = buriedService.getBuriedCount(object);
			StringBuffer result = new StringBuffer();
			result.append("{totalCount:\"" + count + "\",");
			result.append("root:").append(JsonUtil.getJsonString4List(list));
			jsonString=result.append("}").toString();
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 保存直埋信息
	 */
	public void saveBuried(){
		try{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(date);
			object.setCreateTime(time);
			buriedService.insertBuried(object);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 修改直埋信息
	 */
	public void updateBuried(){
		try{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(date);
			object.setLastUpTime(time);
			buriedService.updateBuried(object);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 得到直埋详情
	 */
	public void getDetailBuried(){
		try{
			String id = this.getRequest().getParameter("id");
			object.setBuriedId(Integer.parseInt(id));
			List<BuriedInfoObj> list = buriedService.getBuried(object);
			if(TextUtil.isNotNull(list)){
				BuriedInfoObj obj = list.get(0);
				jsonString = JsonUtil.beanToJson(obj);
				this.printString(jsonString, null);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void getBuriedList(){
		try{
			List<BuriedInfoObj> list = buriedService.getBuried(object);
			jsonString="[";
			for(int i=0;i<list.size();i++){
				BuriedInfoObj obj  = list.get(i);
				jsonString+="{name:'"+obj.getBuriedName()+"',id:"+obj.getBuriedId()+"}";
				if(i!=list.size()-1)
				{
				  jsonString+=",";
				}
			}
			jsonString+="]";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 删除直埋数据
	 */
	public void delBuried(){
		try{
			String parm = this.getRequest().getParameter("parms");
			buriedService.delBurieds(parm);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出数据
	 */
	public void expData(){
		try{
			buriedService.expData(object,getRequest(), getResponse());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出数据模板
	 */
	public void expTemp(){
		try{
			buriedService.expTemp(getRequest(), getResponse());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 导入数据
	 */
	public void impData(){
		try{
			buriedService.impData(file, fileFileName);
			jsonString = "{success:true,msg:'导入成功'}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
			jsonString = "{success:false,msg:'导入失败'}";
			try {
				this.printString(jsonString, null);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
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
	public BuriedInfoObj getObject() {
		return object;
	}
	public void setObject(BuriedInfoObj object) {
		this.object = object;
	}
	public IburiedService getBuriedService() {
		return buriedService;
	}
	public void setBuriedService(IburiedService buriedService) {
		this.buriedService = buriedService;
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
	public java.io.File getFile() {
		return file;
	}
	public void setFile(java.io.File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return object;
	}
}
