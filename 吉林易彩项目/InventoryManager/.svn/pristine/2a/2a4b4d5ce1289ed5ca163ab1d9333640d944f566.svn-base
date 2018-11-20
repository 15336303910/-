package manage.gd.checkConfig.service;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import manage.gd.checkConfig.pojo.CheckItem;
import manage.gd.checkConfig.service.impl.IcheckItemService;
import base.database.DataBase;
public class CheckItemService extends DataBase implements IcheckItemService{	
	
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public JdbcTemplate getTemplate(){
		return this.jdbcTemplate;
	}
	
	/*
	 * 	分页查询
	 * 
	 * */
	public CheckItem getChecksList(CheckItem checkItem){
		 List checkItems = getObjects("itemConfig.getConfigs",checkItem);
	     int total = getCount("itemConfig.getConfigsCount",checkItem);
	     CheckItem thisObject = new CheckItem();
	     thisObject.setCheckItems(checkItems);
	     thisObject.setTotal(Integer.valueOf(total));
	     return thisObject;
	}
	
	/*
	 * 	查询记录
	 * 
	 * */
	public CheckItem getCheckObject(CheckItem checkItem){
		List checkItems = getObjects("itemConfig.getConfigs",checkItem);
		if(checkItems.size()>0){
			return (CheckItem)checkItems.get(0);
		}else{
			return null;
		}
	}
	
	/*
	 * 	插入记录
	 * 
	 * */
	public int insertModel(CheckItem checkItem){
		return ((Integer)insert("itemConfig.insertItem",checkItem)).intValue();
	}
	
	/*
	 * 	更新记录
	 * 
	 * */
	public int updateCheckObject(CheckItem checkItem){
		int rows = update("itemConfig.updateItem",checkItem);
		return rows;
	}
	
	/*
	 * 	删除记录
	 * 
	 * */
	public void deleteCheckObject(CheckItem checkItem){
		delete("itemConfig.deleteItem",checkItem);
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
