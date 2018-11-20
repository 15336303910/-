package manage.dictionary.service.impl;

import java.util.List;
import java.util.Map;

import manage.dictionary.pojo.DicTable;
import manage.dictionary.pojo.DicType;

public interface IdictService {

	/**
	 * 得到字典
	 * 基本信息
	 * @param obj
	 * @return
	 */
	public List<DicType> getDicTypeList(DicType obj);
	
	/**
	 * 根据对象得到
	 * 查询数据总数
	 * @param obj
	 * @return
	 */
	public int getDicTypeCount(DicType obj);
	
	
	/**
	 * 保存字典
	 * @param obj
	 * @return
	 */
	public int saveDicType(DicType obj);
	
	
	
	/**
	 * 得到区县信息
	 * @param cityId
	 * @return
	 */
	public List<Map<String, Object>> getImCounty(String cityId,String domainCode);
	
	
	/**
	 * 保存字典属性
	 * @param obj
	 * @return
	 */
	public int saveDicTable(DicTable obj);
	
	
	/**
	 * 根据对象得到属性值
	 * @param obj
	 * @return
	 */
	public List<DicTable> getDicValues(DicType obj);
	
	
	/**
	 * 根据英文标识得到数据对象
	 * @param type
	 * @return
	 */
	public Map<String,String> getDicMap(String type);
	
	
	/**
	 * 根据数据得到值
	 * @param type
	 * @param text
	 * @return
	 */
	public String getDicValue(String type,String text);
	
	
	/**
	 * 得到字典具体数据
	 * @param obj
	 * @return
	 */
	public List<DicTable> getDicTableList(DicTable obj);
	
	
	/**
	 * 根据对象得到查询
	 * 数据总和
	 * @param obj
	 * @return
	 */
	public int getDicTableCount(DicTable obj);
	
	
	
	/**
	 * 得到地市列表
	 * @return
	 */
	public List<Map<String, Object>> getCityList();
	
	
	/**
	 * 得到区县列表
	 * @param cityId
	 * @return
	 */
	public List<Map<String, Object>> getCountyList(String cityId);
	/**
	 * 删除数据
	 * @param ids
	 */
	public void delDicTable(String ids);
}
