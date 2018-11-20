package manage.opticalTerminal.web;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import manage.opticalTerminal.pojo.OpticalTerminalObj;
import manage.opticalTerminal.service.impl.IopticalTerminalService;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ModelDriven;

import base.util.JsonUtil;
import base.util.TextUtil;
import base.web.PaginationAction;

public class OpticalTerminalAction extends PaginationAction implements ModelDriven{
	private int start = 0;
	private int length = 15;
	private static final Logger log = Logger.getLogger(OpticalTerminalAction.class);
	private OpticalTerminalObj object = new OpticalTerminalObj();
	private IopticalTerminalService opticalTerminalService;
	private String jsonString;
	private java.io.File file;
	private String fileFileName;
	
	/**
	 * 得到光终端盒的数据
	 */
	public void getOpticalTerGrid(){
		try{
			this.object.setStart(start);
			this.object.setLimit(length);
			List<OpticalTerminalObj> list = opticalTerminalService.getOpticalTerGrid(object);
			int count = opticalTerminalService.getOpticalTerCount(object);
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
	 * 保存光终端盒
	 */
	public void saveOpticalTerminal(){
		try{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(date);
			object.setCreateTime(time);
			opticalTerminalService.insertOpticalTer(object);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改光终端盒
	 */
	public void updateOpticalTerminal(){
		try{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(date);
			object.setLastUpTime(time);
			opticalTerminalService.updateOpticalTer(object);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 删除光终端盒
	 */
	public void delOpticalTerminal(){
		try{
			String parm = this.getRequest().getParameter("parms");
			opticalTerminalService.delOpticalTer(parm);
			
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到光终端盒
	 */
	public void getDetailOpt(){
		try{
			String id = this.getRequest().getParameter("id");
			object.setId(Integer.parseInt(id));
			List<OpticalTerminalObj> list = opticalTerminalService.getOpticalTer(object);
			if(TextUtil.isNotNull(list)){
				OpticalTerminalObj obj = list.get(0);
				jsonString = JsonUtil.beanToJson(obj);
				this.printString(jsonString, null);
			}
		}catch(Exception e){
			
		}
	}
	
	/**
	 * 导出数据
	 */
	public void expData(){
		try{
			opticalTerminalService.expData(object,getRequest(), getResponse());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出数据模板
	 */
	public void expTemp(){
		try{
			opticalTerminalService.expTemp(getRequest(), getResponse());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 导入数据
	 */
	public void impData(){
		try{
			opticalTerminalService.impData(file, fileFileName);
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
	public OpticalTerminalObj getObject() {
		return object;
	}
	public void setObject(OpticalTerminalObj object) {
		this.object = object;
	}
	public IopticalTerminalService getOpticalTerminalService() {
		return opticalTerminalService;
	}
	public void setOpticalTerminalService(
			IopticalTerminalService opticalTerminalService) {
		this.opticalTerminalService = opticalTerminalService;
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
