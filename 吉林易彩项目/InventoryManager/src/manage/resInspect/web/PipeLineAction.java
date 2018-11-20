package manage.resInspect.web;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import interfaces.pdainterface.GIS.pojo.GisPojo;
import interfaces.pdainterface.GIS.service.impl.IPDAGisService;
import manage.resInspect.service.impl.IPipeLineService;

import org.apache.log4j.Logger;

import base.util.JsonUtil;
import base.util.TextUtil;
import base.web.PaginationAction;

import com.opensymphony.xwork2.ModelDriven;


/**
 * 管线资产核查同步
 * @author chenqp
 *
 */
public class PipeLineAction extends PaginationAction implements ModelDriven{
	private int start = 0;
	private int length = 15;
	private static final Logger log = Logger.getLogger(PipeLineAction.class);
	private GisPojo object = new GisPojo();
	private String jsonString;
	private java.io.File file;
	private String fileFileName;
	private IPipeLineService pipeLineService;
	private IPDAGisService gisService;
	
	/**
	 * 得到管道的数据
	 */
	public void getPipeGrid(){
		try{
			this.object.setStart(start);
			this.object.setLimit(length);
			String lineName = super.getRequest().getParameter("lineName");
			String startType = super.getRequest().getParameter("startType");
			if(TextUtil.isNotNull(lineName)){
				object.setName(lineName);
			}
			String type = "";
			if(TextUtil.isNotNull(startType)){
				if(startType.equals("well")){
					type ="pipe";
				}else if (startType.equals("pole")){
					type ="pole";
				}else if (startType.equals("buried")){
					type = "buried";
				}
				object.setType(type);
				/*List<Map<String, Object>> list = gisService.getPipe(object);
				int count = gisService.getPipeCount(object);*/
				/*StringBuffer result = new StringBuffer();
				result.append("{totalCount:\"" + count + "\",");
				result.append("root:").append(JsonUtil.getJsonString4List(list));
				jsonString=result.append("}").toString();*/
			}else{
				jsonString = "";
			}
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 得到管线的数据
	 */
	public void getPipeDetail(){
		try{
			String lineId = super.getRequest().getParameter("lineId");
			String startId = super.getRequest().getParameter("startId");
			String endId = super.getRequest().getParameter("endId");
			String type = super.getRequest().getParameter("type");
			if(type.equals("well")){
				jsonString = this.getPipeStr(lineId, startId, endId);
			}else if(type.equals("pole")){
				jsonString = this.getPoleStr(lineId, startId, endId);
			}else if(type.equals("stone")){
				jsonString = this.getBuriedStr(lineId, startId, endId);
			}
			this.printString(jsonString,null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到管道段的详细信息
	 * @param pipeId
	 * @param startId
	 * @param endId
	 * @return
	 */
	String getPipeStr(String pipeId,String startId,String endId){
		StringBuffer sb = new StringBuffer();
		String pipeStr = pipeLineService.getPipeSeg(pipeId);
		String startStr = pipeLineService.getWellInfo(startId);
		String endStr = pipeLineService.getWellInfo(endId);
		sb.append("{"
				+ " 'pipe':"+pipeStr+","
				+ " 'start':"+startStr+","
				+ " 'end':"+endStr+""
				+ "}");
		
		return sb.toString();
	}
	
	
	/**
	 * 得到杆路的数据
	 * @param poleId
	 * @param startId
	 * @param endId
	 * @return
	 */
	String getPoleStr(String poleId,String startId,String endId){
		StringBuffer sb = new StringBuffer();
		String poleSeg = pipeLineService.getPoleSeg(poleId);
		String startStr = pipeLineService.getPoleInfo(startId);
		String endStr = pipeLineService.getPoleInfo(endId);
		sb.append("{"
				+ " 'pole':"+poleSeg+","
				+ " 'start':"+startStr+","
				+ " 'end':"+endStr+""
				+ "}");
		
		return sb.toString();
	}
	
	
	/**
	 * 封装直埋数据
	 * @param buriedId
	 * @param startId
	 * @param endId
	 * @return
	 */
	String getBuriedStr(String buriedId,String startId,String endId){
		StringBuffer sb = new StringBuffer();
		String buriedSeg = pipeLineService.getBuriedPart(buriedId);
		String startStr = pipeLineService.getStoneInfo(startId);
		String endStr = pipeLineService.getStoneInfo(endId);
		sb.append("{"
				+ " 'buried':"+buriedSeg+","
				+ " 'start':"+startStr+","
				+ " 'end':"+endStr+""
				+ "}");
		
		return sb.toString();
	}
	
	
	
	
	
	/**
	 * 前台输出数据
	 * @param string
	 * @param contentType
	 * @throws Exception
	 */
	public void printString(String string, String contentType) throws Exception {
		this.getResponse().setHeader("Cache-Control", "no-cache");
		this.getResponse().setContentType(contentType);
		this.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter pw = this.getResponse().getWriter();
		pw.write(string);
		pw.close();
	}
	public IPDAGisService getGisService() {
		return gisService;
	}
	public void setGisService(IPDAGisService gisService) {
		this.gisService = gisService;
	}
	public IPipeLineService getPipeLineService() {
		return pipeLineService;
	}
	public void setPipeLineService(IPipeLineService pipeLineService) {
		this.pipeLineService = pipeLineService;
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
	public GisPojo getObject() {
		return object;
	}
	public void setObject(GisPojo object) {
		this.object = object;
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
	public static Logger getLog() {
		return log;
	}
	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return object;
	}

}
