package com.function.rules.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.function.dbmanage.model.BasicDb;
import com.function.dbmanage.service.BasicDbService;
import com.function.dbmanage.service.BasicDbTableService;
import com.function.rules.model.RuleDetail;

import java.sql.Connection;
import java.sql.DriverManager;
@Repository("dbService")
public class DbService{
	
	@Autowired
	private BasicDbService basicDbService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private BasicDbTableService basicDbTableService;
	
	/*
	 * 	1.根据规则绑定的数据表获取对应的数据源信息
	 * 
	 * */
	public Connection getDbConnection(RuleDetail ruleDetail)throws Exception{
		Connection connection = null;
		BasicDb basicDb = basicDbService.selectModel(basicDbTableService.selectModel(ruleDetail.getBIND_TABLE()).getBELONG_DB());
		if("Oracle".equals(basicDb.getDB_TYPE())){
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("=>"+"jdbc:oracle:thin:@"+basicDb.getIP_ADDRESS()+":"+basicDb.getPORT()+":"+basicDb.getSID()+" - "+basicDb.getUSER_NAME()+","+basicDb.getUSER_PASS());
			connection = DriverManager.getConnection("jdbc:oracle:thin:@"+basicDb.getIP_ADDRESS()+":"+basicDb.getPORT()+":"+basicDb.getSID()+"",basicDb.getUSER_NAME(),basicDb.getUSER_PASS());
		}else{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://"+basicDb.getIP_ADDRESS()+":"+basicDb.getPORT()+"/"+basicDb.getSID()+"",basicDb.getUSER_NAME(),basicDb.getUSER_PASS());
		}
		return connection;
	}
	
	/*
	 * 	2.获取数据源连接用于核查时插入数据
	 * 
	 * */
	public Connection getConnection()throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection(
			jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'jdbc_url'").get("PRO_VALUE").toString(),
			jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'jdbc_username'").get("PRO_VALUE").toString(),
			jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'jdbc_password'").get("PRO_VALUE").toString()
		);
		return connection;
	}
}
