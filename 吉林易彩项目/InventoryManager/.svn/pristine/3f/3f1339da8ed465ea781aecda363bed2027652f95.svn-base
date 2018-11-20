package manage.dictionary.web;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import manage.dictionary.pojo.DicTable;
import manage.dictionary.pojo.DicType;
import manage.dictionary.service.impl.IdictService;
import manage.user.pojo.UserInfoBean;

import org.apache.log4j.Logger;

import base.util.JsonUtil;
import base.util.TextUtil;
import base.web.PaginationAction;

import com.opensymphony.xwork2.ModelDriven;

public class DictAction extends PaginationAction implements ModelDriven{
	private int start = 0;
	private int length = 15;
	private static final Logger log = Logger.getLogger(DictAction.class);
	private DicType dicType = new DicType();
	private DicTable dicTable = new DicTable();
	private IdictService dictService;
	private String jsonString;
	
	
	/**
	 * 字典数据
	 */
	public void getDictGrid(){
		try{
			if(getRequest().getParameter("start") != null){
				start = Integer.parseInt(getRequest().getParameter("start"));
			}
			dicType.setStart(start);
			dicType.setLimit(length);
			List<DicType> list = dictService.getDicTypeList(dicType);
			int count = dictService.getDicTypeCount(dicType);
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
	 * 得到字典具体数据
	 */
	public void getDictTabGird(){
		try{
			if(getRequest().getParameter("start") != null){
				start = Integer.parseInt(getRequest().getParameter("start"));
			}
			if(getRequest().getParameter("dicId") != null){
				dicTable.setDicId(getRequest().getParameter("dicId"));
			}
			dicTable.setStart(start);
			dicTable.setLimit(length);
			List<DicTable> list = dictService.getDicTableList(dicTable);
			int count  = dictService.getDicTableCount(dicTable);
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
	 * 保存字典
	 */
	public void saveDictType(){
		try{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(date);
			dicType.setCreateTime(time);
			this.dictService.saveDicType(dicType);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存属性对象
	 */
	public void saveDictTable(){
		try{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(date);
			DicTable dicTable = new DicTable();
			dicTable.setCreateTime(time);
			dicTable.setDicId(getRequest().getParameter("dicId"));
			dicTable.setValue(getRequest().getParameter("value"));
			dicTable.setText(getRequest().getParameter("text"));
			dicTable.setOrderValue(getRequest().getParameter("orderValue"));
			this.dictService.saveDicTable(dicTable);
			jsonString="{success:true}";
			this.printString(jsonString, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据字典
	 * 英文得到属性字典
	 */
	public void getDicValues(){
		try{
			String enName = getRequest().getParameter("type");
			dicType.setEnName(enName);
			List<DicTable> list = this.dictService.getDicValues(dicType);
			jsonString="[";
			for(int i=0;i<list.size();i++){
				DicTable tableObj  = list.get(i);
				jsonString+="{name:'"+tableObj.getText()+"',id:"+Integer.parseInt(tableObj.getValue())+"}";
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
	 * 得到地市
	 */
	public void getCity(){
		try{
			List<Map<String, Object>> list = this.dictService.getCityList();
			jsonString ="[";
			for(int i=0;i<list.size();i++){
				Map<String, Object> map = list.get(i);
				String name = map.get("name")+"";
				String id = map.get("id")+"";
				jsonString+="{name:'"+name+"',id:"+Integer.parseInt(id)+"}";
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
	 * 获取区县
	 */
	public void getCounty(){
		try{
			String cityId = this.getRequest().getParameter("cityId");
			List<Map<String, Object>> list =this.dictService.getCountyList(cityId);
			jsonString ="[";
			for(int i=0;i<list.size();i++){
				Map<String, Object> map = list.get(i);
				String name = map.get("name")+"";
				String id = map.get("id")+"";
				jsonString+="{name:'"+name+"',id:"+Integer.parseInt(id)+"}";
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
	 * 得到区县信
	 * 息存在跨地市
	 */
	public void getImCounty() {
		try {
			UserInfoBean userInfoBean=(UserInfoBean)getRequest().getSession().getAttribute("userBean");
			System.out.println(userInfoBean.getAreano()+"====================");
			String cityId = this.getRequest().getParameter("cityId");
			List<Map<String, Object>> list =this.dictService.getImCounty(cityId,userInfoBean.getAreano());
			jsonString ="[";
			for(int i=0;i<list.size();i++){
				Map<String, Object> map = list.get(i);
				String name = map.get("name")+"";
				String id = map.get("id")+"";
				jsonString+="{name:'"+name+"',id:"+Integer.parseInt(id)+"}";
				if(i!=list.size()-1)
				{
				  jsonString+=",";
				}
				
			}
			jsonString+="]";
			this.printString(jsonString, null);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除数据
	 */
	public void delDicTable(){
		try{
			String ids = getRequest().getParameter("ids");
			this.dictService.delDicTable(ids);
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
	public DicType getDicType() {
		return dicType;
	}
	public void setDicType(DicType dicType) {
		this.dicType = dicType;
	}
	public DicTable getDicTable() {
		return dicTable;
	}
	public void setDicTable(DicTable dicTable) {
		this.dicTable = dicTable;
	}
	public IdictService getDictService() {
		return dictService;
	}
	public void setDictService(IdictService dictService) {
		this.dictService = dictService;
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
	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return dicType;
	}

}
