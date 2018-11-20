package manage.buriedPart.web;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import manage.buriedPart.pojo.BuriedPartObj;
import manage.buriedPart.service.impl.IburiedPartService;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ModelDriven;

import base.util.JsonUtil;
import base.util.TextUtil;
import base.web.PaginationAction;

public class BuriedPartAction extends PaginationAction implements ModelDriven{
	private int start = 0;
	private int length = 15;
	private static final Logger log = Logger.getLogger(BuriedPartAction.class);
	private BuriedPartObj object = new BuriedPartObj();
	private IburiedPartService buriedPartServie;
	private String jsonString;
	private java.io.File file;
	private String fileFileName;
	
	
	/**
	 * 查询操作
	 */
	public void getBuriedPartGrid(){
		try{
			this.object.setStart(start);
			this.object.setLimit(length);
			List<BuriedPartObj> list = buriedPartServie.getBuriedPartGrid(object);
			int count = buriedPartServie.getBuriedPartCount(object);
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
	 * 保存直埋段
	 */
	public void saveBuriedPart(){
		try{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(date);
			object.setCreateTime(time);
			this.buriedPartServie.insertBuriedPart(object);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 修改直埋段
	 */
	public void updateBuriedPart(){
		try{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(date);
			object.setLastUpTime(time);
			this.buriedPartServie.updateBuriedPart(object);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除直埋段
	 */
	public void delBuriedPart(){
		try{
			String parm = this.getRequest().getParameter("parms");
			this.buriedPartServie.delBuriedPart(parm);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到直埋段
	 */
	public void getBuriedPart(){
		try{
			String id = this.getRequest().getParameter("id");
			object.setId(Integer.parseInt(id));
			List<BuriedPartObj> list = buriedPartServie.getBuriedPart(object);
			if(TextUtil.isNotNull(list)){
				BuriedPartObj obj = list.get(0);
				jsonString = JsonUtil.beanToJson(obj);
				this.printString(jsonString, null);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 导出数据
	 */
	public void expData(){
		try{
			buriedPartServie.expData(object,getRequest(), getResponse());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出数据模板
	 */
	public void expTemp(){
		try{
			buriedPartServie.expTemp(getRequest(), getResponse());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 导入数据
	 */
	public void impData(){
		try{
			buriedPartServie.impData(file, fileFileName);
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
	public BuriedPartObj getObject() {
		return object;
	}
	public void setObject(BuriedPartObj object) {
		this.object = object;
	}
	public IburiedPartService getBuriedPartServie() {
		return buriedPartServie;
	}
	public void setBuriedPartServie(IburiedPartService buriedPartServie) {
		this.buriedPartServie = buriedPartServie;
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
