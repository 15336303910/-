package manage.user.web;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import manage.dictionary.pojo.DicTable;
import manage.user.pojo.MaintainGroupBean;
import manage.user.service.UserInfoService;

import org.apache.log4j.Logger;

import base.util.JsonUtil;
import base.util.TextUtil;
import base.web.PaginationAction;

import com.opensymphony.xwork2.ModelDriven;

/**
 * banzu xinxi 
 * @author chenqp
 *maintainGroupAction
 */
public class maintainGroupAction extends PaginationAction implements ModelDriven {
	private int start = 0;
	private int length = 15;
	private static final Logger log = Logger.getLogger(maintainGroupAction.class);
	private UserInfoService userInfoService;
	private String jsonString;
	private java.io.File file;
	private String fileFileName;
	private MaintainGroupBean object = new MaintainGroupBean();
	
	/**
	 * 得到班组的列表信息
	 */
	public void getMtGroupGrid(){
		try{
			String dCode = this.getRequest().getParameter("dCode");
			if(TextUtil.isNull(this.getRequest().getParameter("start"))) {
				object.setStart(0);
			}else {
				object.setStart(Integer.parseInt(this.getRequest().getParameter("start")+""));
			}
			object.setLimit(length);
			object.setDomainId(dCode);
			List<MaintainGroupBean> list = userInfoService.getGroupList(object);
			int count = userInfoService.getGroupCount(object);
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
	 * 得到
	 */
	public void getMtComp(){
		try{
			List<Map<String, Object>> list = this.userInfoService.getCompList();
			if(TextUtil.isNotNull(list)){
				jsonString="[";
				for(int i=0;i<list.size();i++){
					Map<String, Object> map = list.get(i);
					jsonString+="{name:'"+map.get("name")+"',id:"+Integer.parseInt(map.get("id")+"")+"}";
					if(i!=list.size()-1)
					{
					  jsonString+=",";
					}
				}
				jsonString+="]";
				this.printString(jsonString, null);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到相应的班组信息
	 */
	public void getMtGoup(){
		try{
			String domainCode = this.getRequest().getParameter("domainCode");
			String county = this.getRequest().getParameter("county");
			List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
			if(TextUtil.isNotNull(domainCode)){
				list = this.userInfoService.getGroupList(domainCode);
			}else{
				list = this.userInfoService.getGroupByCountry(county);
			}
			if(TextUtil.isNotNull(list)){
				jsonString="[";
				for(int i=0;i<list.size();i++){
					Map<String, Object> map = list.get(i);
					jsonString+="{name:'"+map.get("name")+"',id:"+Integer.parseInt(map.get("id")+"")+"}";
					if(i!=list.size()-1)
					{
					  jsonString+=",";
					}
				}
				jsonString+="]";
				this.printString(jsonString, null);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存班组信息
	 */
	public void saveGroup(){
		try{
			this.userInfoService.saveGroup(object);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 删除班组信息
	 */
	public void delGroup(){
		try{
			String parm = this.getRequest().getParameter("parms");
			this.userInfoService.delGroup(parm);
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
	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return object;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public UserInfoService getUserInfoService() {
		return userInfoService;
	}
	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
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
	public MaintainGroupBean getObject() {
		return object;
	}
	public void setObject(MaintainGroupBean object) {
		this.object = object;
	}
	public static Logger getLog() {
		return log;
	}
}
