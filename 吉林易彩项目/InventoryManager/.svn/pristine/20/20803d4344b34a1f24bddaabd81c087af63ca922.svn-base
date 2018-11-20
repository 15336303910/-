package manage.transmissionEquipment.web;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ModelDriven;

import base.util.JsonUtil;
import base.util.TextUtil;
import base.web.PaginationAction;
import manage.stone.pojo.StoneInfoBean;
import manage.stone.web.StoneInfoAction;
import manage.transmissionEquipment.pojo.TransmissionEquipmentBean;
import manage.transmissionEquipment.service.impl.ItransmissionEquipmentService;

public class TransmissionEquipmentAction extends PaginationAction implements ModelDriven{

	private int start = 0;
	private int length = 15;
	private static final Logger log = Logger.getLogger(StoneInfoAction.class);
	private TransmissionEquipmentBean equipmentInfo = new TransmissionEquipmentBean();
	private ItransmissionEquipmentService equipmentService;
	private String jsonString;
	private java.io.File file;
	private String fileFileName;
	
	/**
	 * 得到数据
	 */
	public void getTransmissionEquipmentGrid(){
		try{
			equipmentInfo.setStart(Integer.parseInt(this.getRequest().getParameter("start")+""));
			equipmentInfo.setLimit(length);
			List<TransmissionEquipmentBean> list = equipmentService.getTransmissionEquipmentGrid(equipmentInfo);
			int equipmentCount = equipmentService.getTransmissionEquipmentCount(equipmentInfo);
			StringBuffer result = new StringBuffer();
			result.append("{totalCount:\"" + equipmentCount + "\",");
			result.append("root:").append(JsonUtil.getJsonString4List(list));
			jsonString=result.append("}").toString();
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存传输设备
	 */
	public void saveTransmissionEquipment(){
		try{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(date);
			equipmentInfo.setCreateTime(time);
			equipmentService.insertTransmissionEquipment(equipmentInfo);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改传输设备
	 */
	public void updateTransmissionEquipment(){
		try{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(date);
			equipmentInfo.setModifyTime(time);
			equipmentService.updateTransmissionEquipment(equipmentInfo);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 得到详情
	 */
	public void getDetailTransmissionEquipment(){
		try{
			String id = this.getRequest().getParameter("id");
			equipmentInfo.setEquipmentId(Integer.parseInt(id));
			List<TransmissionEquipmentBean> list = equipmentService.getTransmissionEquipmentList(equipmentInfo);
			if(TextUtil.isNotNull(list)){
				TransmissionEquipmentBean obj = list.get(0);
				jsonString = JsonUtil.beanToJson(obj);
				this.printString(jsonString, null);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除传输设备
	 */
//	public void delStone(){
//		try{
//			String parm = this.getRequest().getParameter("parms");
//			equipmentService.delTransmissionEquipments(parm);
//			jsonString="{success:true}";
//			this.printString(jsonString, null);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	
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
		return equipmentInfo;
	}

}
