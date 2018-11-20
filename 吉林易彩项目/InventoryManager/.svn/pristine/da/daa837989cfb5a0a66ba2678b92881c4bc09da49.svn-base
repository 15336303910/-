package manage.dictionary.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import edu.emory.mathcs.backport.java.util.LinkedList;
import manage.dictionary.pojo.DicTable;
import manage.dictionary.pojo.DicType;
import manage.dictionary.service.impl.IdictService;
import base.database.DataBase;
import base.util.TextUtil;

public class DictService extends DataBase implements IdictService{
	
	private static String dictGrid="dict.dictGrid";
	private static String dictCount="dict.dictCount";
	private static String saveDicType= "dict.saveDicType";
	private static String saveDicTable = "dict.saveDicTable";
	private static String getDicValues ="dict.getDicValues";
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 得到字典
	 * 基本信息
	 * @param obj
	 * @return
	 */
	public List<DicType> getDicTypeList(DicType obj){
		List<DicType> list = getObjects(dictGrid, obj);
		if(TextUtil.isNotNull(list)){
			return list;
		}else{
			return null;
		}
	}
	
	
	/**
	 * 得到字典具体数据
	 * @param obj
	 * @return
	 */
	public List<DicTable> getDicTableList(DicTable obj){
		List<DicTable> list = getObjects("dict.dictTableGrid", obj);
		if(TextUtil.isNotNull(list)){
			return list;
		}else{
			return null;
		}
	}
	
	/**
	 * 根据对象得到
	 * 查询数据总数
	 * @param obj
	 * @return
	 */
	public int getDicTypeCount(DicType obj){
		return getCount(dictCount, obj);
	}
	
	
	/**
	 * 根据对象得到查询
	 * 数据总和
	 * @param obj
	 * @return
	 */
	public int getDicTableCount(DicTable obj){
		return getCount("dict.dictTableCount", obj);
	}
	/**
	 * 保存字典
	 * @param obj
	 * @return
	 */
	public int saveDicType(DicType obj){
		return (Integer) insert(saveDicType, obj);
	}
	
	/**
	 * 保存字典属性
	 * @param obj
	 * @return
	 */
	public int saveDicTable(DicTable obj){
		return (Integer) insert(saveDicTable, obj);
	}
	
	
	/**
	 * 删除数据
	 * @param ids
	 */
	public void delDicTable(String ids){
		delete("dict.delDicTable", ids);
	}
	
	/**
	 * 根据对象得到属性值
	 * @param obj
	 * @return
	 */
	public List<DicTable> getDicValues(DicType obj){
		List<DicTable> list = getObjects(getDicValues, obj);
		if(TextUtil.isNotNull(list)){
			return list;
		}else{
			return null;
		}
	}
	
	
	/**
	 * 得到地市信息
	 * @return
	 */
	public List<Map<String, Object>> getCityList(){
		List<Map<String, Object>> list = new LinkedList();
		String sql = "select zhLabel as name ,resNum as id"
				+ " from rms_city where 1=1 ";
		list = this.jdbcTemplate.queryForList(sql);
		return list;
	}
	
	
	/**
	 * 得到区县列表
	 * @param cityId
	 * @return
	 */
	public List<Map<String, Object>> getCountyList(String cityId){
		List<Map<String, Object>> list = new LinkedList();
		String sql = "select towerName as name ,resNum as id"
				+ " from rms_county where resCity = "+cityId+"";
		list = this.jdbcTemplate.queryForList(sql);
		return list;
	}
	
	/**
	 * 得到区县信息
	 * @param cityId
	 * @return
	 */
	public List<Map<String, Object>> getImCounty(String cityId,String domainCode){
		List<Map<String, Object>> list = new LinkedList();
		String sql = "select domainName as name ,domainCode as id"
				+ " from sys_domain where parentId ='"+cityId+"'";
		if(TextUtil.isNotNull(domainCode) 
				&& !(domainCode.equals("0"))
				&& !(domainCode.equals("01020"))) {
			sql +=" and domainCode ='"+domainCode+"'";
		}
		list = this.jdbcTemplate.queryForList(sql);
		return list;
		
	}
	/**
	 * 根据英文标识得到数据对象
	 * @param type
	 * @return
	 */
	public Map<String,String> getDicMap(String type){
		DicType obj = new DicType();
		obj.setEnName(type);
		List<DicTable> list = getObjects(getDicValues, obj);
		Map<String, String> map = new HashMap<String, String>();
		if(TextUtil.isNotNull(list)){
			for(Iterator<DicTable> it = list.iterator();it.hasNext();){
				DicTable tab = it.next();
				if(!map.containsKey(tab.getValue())){
					map.put(tab.getValue(), tab.getText());
				}
			}
		}
		return map;
	}
	
	
	
	
	
	/**
	 * 根据数据得到值
	 * @param type
	 * @param text
	 * @return
	 */
	public String getDicValue(String type,String text){
		DicType obj = new DicType();
		obj.setEnName(type);
		List<DicTable> list = getObjects(getDicValues, obj);
		String result = "";
		if(TextUtil.isNotNull(list)){
			for(Iterator<DicTable> it = list.iterator();it.hasNext();){
				DicTable tab = it.next();
				if(text.equals(tab.getText())){
					result = tab.getValue();
					break;
				}
			}
		}
		return result;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
