package manage.gd.checkConfig.service.impl;
import org.springframework.jdbc.core.JdbcTemplate;

import manage.gd.checkConfig.pojo.CheckItem;
public interface IcheckItemService {

	/*
	 * 	分页查询
	 * 
	 * */
	public CheckItem getChecksList(CheckItem checkItem);
	
	/*
	 * 	单条记录查询
	 * 
	 * */
	public CheckItem getCheckObject(CheckItem checkItem);
	
	/*
	 * 	插入记录
	 * 
	 * */
	public int insertModel(CheckItem checkItem);
	
	/*
	 * 	更新记录
	 * 
	 * */
	public int updateCheckObject(CheckItem checkItem);
	
	
	/*
	 * 	删除记录
	 * 
	 * */
	public void deleteCheckObject(CheckItem checkItem);
	
	public JdbcTemplate getTemplate();
	
}
