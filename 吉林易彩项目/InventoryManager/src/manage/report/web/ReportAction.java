package manage.report.web;

import interfaces.irmsInterface.interfaces.opticTran.service.impl.IirmsOpticTranService;
import interfaces.pdainterface.equt.service.PDAEqutInfoService;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import manage.dictionary.pojo.DicTable;
import manage.equt.pojo.EqutInfoBean;
import manage.report.pojo.ReportBean;
import manage.report.service.impl.IreportService;
import base.util.DictUtil;
import base.util.GetProperties;
import base.util.JsonUtil;
import base.util.TextUtil;
import base.web.PaginationAction;

import com.opensymphony.xwork2.ModelDriven;

public class ReportAction  extends PaginationAction implements ModelDriven{
	private String jsonString;
	private IreportService reportService;
	private Integer limit =15;
	private ReportBean object = new ReportBean();
	public static GetProperties properties = new GetProperties();
	
	private PDAEqutInfoService pdaEqutInfoService;
	private IirmsOpticTranService irmsOpticTranService;
	
	/**
	 * 地市采集
	 * 热点数据
	 * 
	 */
	public void getResReport(){
		String result= "";
		try{
			jsonString="{"
					+ "title:{"
					+ "text:'"+properties.getValueByKey("province")+"资源数据采集',"
					+ "subtext:''"
					+ "},"
					+ "legend:{"
					+ "data:'采集数据热点分布图'"
					+ "},series:{"
					+ "name:'采集数据热点分布图',"
					+ "mapType:'"+properties.getValueByKey("province")+"',"
					+ "data:[";
			List<Map<String, Object>> list = this.reportService.getCityHot();
			if(TextUtil.isNotNull(list)){
				String data ="";
				for(int i=0;i<list.size();i++){
					Map<String, Object> map = list.get(i);
					data+="{"
						+ "name:'"+map.get("city")+"',"
						+ "value:'"+map.get("num")+"'"
						+ "},";
				}
				if(data.endsWith(",")){
					data = data.substring(0,data.length()-1);
				}
				jsonString += data;
			}
			jsonString+= "]"
					+ "}"
					+ "}";
			result = "{success:true,message:\""+jsonString+"\"}";
			this.printString(result, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到资源列表
	 */
	public void getResGrid(){
		try{
			object.setLimit(this.limit);
			if(TextUtil.isNull(object.getStart())){
				object.setStart(0);
			}
			if(TextUtil.isNotNull(object.getResType())){
				List<Map<String, Object>> list =  this.reportService.getresList(object);
				int count = this.reportService.getResCount(object);
				StringBuffer result = new StringBuffer();
				result.append("{totalCount:\"" + count + "\",");
				result.append("root:").append(JsonUtil.getJsonString4List(list));
				jsonString=result.append("}").toString();
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
			String resTime = getRequest().getParameter("resTime");
			String resType = getRequest().getParameter("resType");
			ReportBean report = new ReportBean();
			if(TextUtil.isNotNull(resTime)){
				report.setResTime(resTime);
			}
			if(TextUtil.isNotNull(resType)){
				report.setResType(resType);
			}
			if(resType.equals("all")){
				reportService.expAllData(report, getRequest(), getResponse());
			}else if(resType.equals("coord")){
				reportService.expCoord(getRequest(), getResponse());
			}else{
				reportService.expData(report, getRequest(), getResponse());
			}
			/*String eid = "EIU_1477113263682";
			EqutInfoBean equt = new EqutInfoBean();
			equt.setEid(eid);
			equt = this.pdaEqutInfoService.getEqutObj(equt);
			irmsOpticTranService.addOptiTranBox(equt);*/
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
	
	
	
	String getRandom(int max,int min){
		String ran = "";
		ran = ((int)Math.round(Math.random()*(max-min)+min))+"";
		return ran ;
	}
	
	public PDAEqutInfoService getPdaEqutInfoService() {
		return pdaEqutInfoService;
	}

	public void setPdaEqutInfoService(PDAEqutInfoService pdaEqutInfoService) {
		this.pdaEqutInfoService = pdaEqutInfoService;
	}

	public IirmsOpticTranService getIrmsOpticTranService() {
		return irmsOpticTranService;
	}

	public void setIrmsOpticTranService(IirmsOpticTranService irmsOpticTranService) {
		this.irmsOpticTranService = irmsOpticTranService;
	}

	public static GetProperties getProperties() {
		return properties;
	}
	public static void setProperties(GetProperties properties) {
		ReportAction.properties = properties;
	}
	@Override
	public Object getModel() {
		return object;
	}
	public ReportBean getObject() {
		return object;
	}
	public void setObject(ReportBean object) {
		this.object = object;
	}
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	public IreportService getReportService() {
		return reportService;
	}
	public void setReportService(IreportService reportService) {
		this.reportService = reportService;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
