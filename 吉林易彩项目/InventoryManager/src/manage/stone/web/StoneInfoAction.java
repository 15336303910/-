package manage.stone.web;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import manage.stone.pojo.StoneInfoBean;
import manage.stone.service.impl.IstoneInfoService;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ModelDriven;

import base.util.JsonUtil;
import base.util.TextUtil;
import base.web.PaginationAction;

public class StoneInfoAction extends PaginationAction implements ModelDriven{
	private int start = 0;
	private int length = 15;
	private static final Logger log = Logger.getLogger(StoneInfoAction.class);
	private StoneInfoBean stoneInfo = new StoneInfoBean();
	private IstoneInfoService stoneService;
	private String jsonString;
	private java.io.File file;
	private String fileFileName;
	
	/**
	 * 得到数据
	 */
	public void getStoneGrid(){
		try{
			stoneInfo.setStart(Integer.parseInt(this.getRequest().getParameter("start")+""));
			stoneInfo.setLimit(length);
			List<StoneInfoBean> list = stoneService.getStoneGrid(stoneInfo);
			int stoneCount = stoneService.getStoneCount(stoneInfo);
			StringBuffer result = new StringBuffer();
			result.append("{totalCount:\"" + stoneCount + "\",");
			result.append("root:").append(JsonUtil.getJsonString4List(list));
			jsonString=result.append("}").toString();
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存标石信息
	 */
	public void saveStone(){
		try{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(date);
			stoneInfo.setCreateTime(time);
			stoneService.insertStone(stoneInfo);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改标石信息
	 */
	public void updateStone(){
		try{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(date);
			stoneInfo.setLastUpTime(time);
			stoneService.updateStone(stoneInfo);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 得到标石详情
	 */
	public void getDetailStone(){
		try{
			String id = this.getRequest().getParameter("id");
			stoneInfo.setStoneId(Integer.parseInt(id));
			List<StoneInfoBean> list = stoneService.getStone(stoneInfo);
			if(TextUtil.isNotNull(list)){
				StoneInfoBean obj = list.get(0);
				jsonString = JsonUtil.beanToJson(obj);
				this.printString(jsonString, null);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除标石数据
	 */
	public void delStone(){
		try{
			String parm = this.getRequest().getParameter("parms");
			stoneService.delStones(parm);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出数据模板
	 */
	public void expTemp(){
		try{
			stoneService.expTemp(getRequest(), getResponse());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 导出数据
	 */
	public void expData(){
		try{
			stoneService.expData(stoneInfo,getRequest(), getResponse());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 导入数据
	 */
	public void impData(){
		try{
			stoneService.impData(file, fileFileName);
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
	public IstoneInfoService getStoneService() {
		return stoneService;
	}
	public void setStoneService(IstoneInfoService stoneService) {
		this.stoneService = stoneService;
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
	public StoneInfoBean getStoneInfo() {
		return stoneInfo;
	}
	public void setStoneInfo(StoneInfoBean stoneInfo) {
		this.stoneInfo = stoneInfo;
	}
	public static Logger getLog() {
		return log;
	}
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
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
		return stoneInfo;
	}
}
