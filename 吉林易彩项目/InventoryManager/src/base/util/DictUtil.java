package base.util;

import java.util.List;
import java.util.Map;

import manage.dictionary.pojo.DicTable;

import org.springframework.jdbc.core.JdbcTemplate;

import edu.emory.mathcs.backport.java.util.LinkedList;

public class DictUtil {
	private static JdbcTemplate jdbcTemplate;

	/**
	 * 得到实体对象列表
	 * 英文字典
	 * @param enName
	 * @return
	 */
	public static List<DicTable> getEnDicList(String enName){
		List<DicTable> list = new LinkedList();
		try{
			String sql = "select value,text from dic_table t,dic_type p"
					+ " where t.dicId = p.id and enName ='"+enName+"'"
					+ " order by t.orderValue asc ";
			List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
			if(TextUtil.isNotNull(result)){
				for(Map<String,Object> map : result){
					DicTable dic = new DicTable();
					if(map.get("value") != null){
						dic.setValue(map.get("value")+"");
					}
					if(map.get("text") != null){
						dic.setText(map.get("text")+"");
					}
					list.add(dic);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 得到实体对象列表
	 * 汉语字典
	 * @param enName
	 * @return
	 */
	public static List<DicTable> getCnDicList(String cnName){
		List<DicTable> list = new LinkedList();
		try{
			String sql = "select value,text from dic_table t,dic_type p"
					+ " where t.dicId = p.id and cnName ='"+cnName+"'"
					+ " order by t.orderValue asc ";
			List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
			if(TextUtil.isNotNull(result)){
				for(Map<String,Object> map : result){
					DicTable dic = new DicTable();
					if(map.get("value") != null){
						dic.setValue(map.get("value")+"");
					}
					if(map.get("text") != null){
						dic.setText(map.get("text")+"");
					}
					list.add(dic);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据key得到value
	 * 英文字典
	 * @param enName
	 * @param key
	 * @return
	 */
	public String getEValueBykey(String enName,String key){
		String result ="";
		try{
			String sql = " select value from dic_table t,dic_type p "
					+ " where t.dicId = p.id"
					+ " and enName ='"+enName+"' and text ='"+key+"' ";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			if(TextUtil.isNotNull(list)){
				Map<String, Object> map = list.get(0);
				result = map.get("value")+"";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据值得到key
	 * 英文字典
	 * @param enName
	 * @param value
	 * @return
	 */
	public String getEKeyByValue(String enName ,String value){
		String result= "";
		try{
			String sql = " select text from dic_table t,dic_type p "
					+ " where t.dicId = p.id"
					+ " and enName ='"+enName+"' and value ='"+value+"' ";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			if(TextUtil.isNotNull(list)){
				Map<String, Object> map = list.get(0);
				result = map.get("value")+"";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 根据key得到value
	 * * 汉语字典
	 * @param enName
	 * @param key
	 * @return
	 */
	public String getCValueBykey(String cnName,String key){
		String result ="";
		try{
			String sql = " select value from dic_table t,dic_type p "
					+ " where t.dicId = p.id"
					+ " and cnName ='"+cnName+"' and text ='"+key+"' ";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			if(TextUtil.isNotNull(list)){
				Map<String, Object> map = list.get(0);
				result = map.get("value")+"";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 根据值得到key
	 * * 汉语字典
	 * @param enName
	 * @param value
	 * @return
	 */
	public String getCKeyByValue(String cnName ,String value){
		String result= "";
		try{
			String sql = " select text from dic_table t,dic_type p "
					+ " where t.dicId = p.id"
					+ " and cnName ='"+cnName+"' and value ='"+value+"' ";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			if(TextUtil.isNotNull(list)){
				Map<String, Object> map = list.get(0);
				result = map.get("value")+"";
			}
		}catch(Exception e){
			e.printStackTrace();
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
