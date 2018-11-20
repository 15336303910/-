package com.function.rules.service;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.function.dbmanage.model.BasicColumn;
import com.function.dbmanage.model.BasicDb;
import com.function.dbmanage.model.BasicDbTable;
import com.function.dbmanage.service.BasicColumnService;
import com.function.dbmanage.service.BasicDbService;
import com.function.dbmanage.service.BasicDbTableService;
import com.function.rules.model.CheckDetail;
import com.function.rules.model.RuleDetail;
import com.function.rules.model.RuleHistory;
import com.function.rules.model.RuleItem;
import com.function.rules.model.RuleJob;
import com.function.rules.model.RuleJobItem;
import com.system.LoginUserUtil;
@Repository("taskBuilder")
@Scope("prototype")
public class TaskBuilder{
	
	public Connection $connection = null;
	public Statement $statement = null; 
	public ResultSet $resultSet = null;
	
	public Integer buildCheck(HttpServletRequest request,Integer ruleId,String jobToken)throws Exception{
		System.out.println("============================ Data Clean Begin ============================");
		Integer problemCount = 0;
		Integer historyId = -1;
		try{
			/*
			 * 	执行核查之前先清空历史数据. 只保存最后一次核查结果.
			 * 
			 * */
			if(ruleId!=null){
				/*
				 * 	RULE_JOB_DATA
				 * 
				 * */
				String dataDelete = "";
				dataDelete+="DELETE FROM RULE_JOB_DATA WHERE JOB_ITEM_ID IN(";
				dataDelete+="	SELECT ID AS ITEM_ID FROM RULE_JOB_ITEM WHERE JOB_ID IN(";
				dataDelete+="		SELECT ID AS JOB_ID FROM RULE_JOB WHERE RULE_ID = "+ruleId;
				dataDelete+="	)";
				dataDelete+=")";
				jdbcTemplate.execute(dataDelete);
				/*
				 * 	RULE_JOB_ITEM
				 * 
				 * */
				String itemDelete = "";
				itemDelete+="DELETE FROM RULE_JOB_ITEM WHERE JOB_ID IN(";
				itemDelete+="	SELECT ID AS JOB_ID FROM RULE_JOB WHERE RULE_ID = "+ruleId;
				itemDelete+=")";
				jdbcTemplate.execute(itemDelete);
				/*
				 * 	RULE_JOB
				 * 
				 * */
				jdbcTemplate.execute("DELETE FROM RULE_JOB WHERE RULE_ID = "+ruleId);
			}
			if(ruleId!=null){
				/*
				 * 	创建一个监控
				 * 	
				 * */
				RuleJob ruleJob = new RuleJob(ruleId,jobToken);
				historyId = ruleJobService.insertModel(ruleJob);
				/*
				 * 	开始进行核查
				 * 
				 * */			
				RuleDetail ruleDetail = ruleDetailService.selectModel(ruleId);
				/*
				 * 	获取数据源连接.
				 * 
				 * */
				Class.forName("oracle.jdbc.driver.OracleDriver");
				BasicDb basicDb = basicDbService.selectModel(basicDbTableService.selectModel(ruleDetail.getBIND_TABLE()).getBELONG_DB());
				$connection = DriverManager.getConnection("jdbc:oracle:thin:@"+basicDb.getIP_ADDRESS()+":"+basicDb.getPORT()+":"+basicDb.getSID()+"",basicDb.getUSER_NAME(),basicDb.getUSER_PASS());
				if($connection!=null){
					System.out.println("Clean: Get DataBase Source Connection Successfully.");
					$statement = $connection.createStatement();
				}
				if("配置核查".equals(ruleDetail.getRULE_TYPE())){
					/*
					 * 	获取:Table_Name
					 * 
					 * */
					BasicDbTable bindedTable = basicDbTableService.selectModel(ruleDetail.getBIND_TABLE());
					/*
					 * 	主键字段；中文标识字段
					 * 
					 * */
					String prName = "";
					String chName = "";
					String ctName = "";
					List<BasicColumn> columns = basicColumnService.selectModelsByHql("from BasicColumn where BELONG_TABLE = "+bindedTable.getID()+" and IS_EXPORT = 'Y'");
					if(columns.size()>0){
						for(int l=0;l<columns.size();l++){
							if("Y".equals(columns.get(l).getIS_PRIMARY_KEY())){
								/*主键*/
								prName = columns.get(l).getCOLUMN_NAME();
							}else{
								/*名称*/
								chName = columns.get(l).getCOLUMN_NAME();
							}
							if("地市".equals(columns.get(l).getDATA_TYPE())){
								/*地市*/
								ctName = columns.get(l).getCOLUMN_NAME();
							}
						}
					}
					if(!"".equals(prName) && !"".equals(chName)){
						System.out.println("Clean: Get PrimaryKey and ChName Columns Successfully. "+prName+" and "+chName);
					}
					/*
					 * 	获取当前核查表格的[记录总数]
					 * 
					 * */
					String totalRecordSum = "SELECT COUNT(1) AS ROW_COUNT FROM "+bindedTable.getTABLE_NAME();
					Boolean IS_PROVINCE = true;
					String CITY_NAME = "";
					List<Map<String,Object>> users = jdbcTemplate.queryForList("SELECT * FROM S_SYSTEM_USER WHERE ID = "+ruleDetail.getCREATE_USER());
					if(users.size()>0){
						Map<String,Object> assignedUser = users.get(0);
						if(assignedUser.get("BELONG_USER")!=null){
							String BELONG_AREA = assignedUser.get("BELONG_AREA")==null?"":assignedUser.get("BELONG_AREA").toString();
							if("".equals(BELONG_AREA) || BELONG_AREA.indexOf("省")!=-1 || BELONG_AREA.indexOf("四川")!=-1){
								IS_PROVINCE = true;
							}else{
								IS_PROVINCE = false;
								CITY_NAME = BELONG_AREA;
							}
						}
					}
					if(!IS_PROVINCE && !"".equals(ctName)){
						totalRecordSum+=" WHERE "+ctName+" LIKE '%"+CITY_NAME+"%'";
					}
					$resultSet = $statement.executeQuery(totalRecordSum);
					$resultSet.next();
					Integer recordCount = $resultSet.getInt("ROW_COUNT");
					System.out.println("Clean: Get Number Of The All Records In This Table Successfully. "+recordCount);
					ruleJob = ruleJobService.selectModel(historyId);
					ruleJob.setRECORD_TOTAL(recordCount);
					/*
					 * 	逐条规则进行核查.
					 * 
					 * */
					List<RuleItem> ruleItems = ruleItemService.selectModelsByHql("from RuleItem where BELONG_RULE = "+ruleId+" order by ID asc");
					if(ruleItems.size()>0){
						for(int i=0;i<ruleItems.size();i++){
							RuleItem ruleItem = ruleItems.get(i);
							/*
							 * 	获取当前RuleItem的核查Column属性.
							 * 
							 * */
							BasicColumn checkingColumn = basicColumnService.selectModel(ruleItem.getCOLUMN_ID());
							String vaName = checkingColumn.getCOLUMN_NAME();
							String exName = vaName; 
							if(prName.equals(vaName) || chName.equals(vaName)){
								/*
								 * 	如果要核查的字段和主键、名称字段重名，则起个别名.
								 * 
								 * */
								exName = vaName+"_AAA";
							}
							/*
							 * 	针对每一个核查项，先找出[问题数据]
							 * 
							 * */
							CheckDetail detail = sqlBuilder.buildSql(bindedTable.getTABLE_NAME(),prName,chName,vaName,exName,checkingColumn,ruleItem,IS_PROVINCE,ctName,CITY_NAME);
							if(detail.getIS_SUPPORTED()){
								System.out.println("=>SQL:"+detail.getSQL());
								$resultSet = $statement.executeQuery(detail.getCOUNT_SQL());
								$resultSet.next();
								Integer rowCount = $resultSet.getInt("ROW_COUNT");
								System.out.println("Clean: Now find "+rowCount+" fatal records.");
								/*
								 * 	将当前规则下发现的问题数据数量，根据监控的编号进行保存.
								 * 
								 * */
								RuleJobItem jobItem = new RuleJobItem();
								jobItem.setJOB_ID(historyId);
								jobItem.setCHECK_COLUMN(checkingColumn.getCOLUMN_NAME());
								jobItem.setCHECK_EXPRESS(ruleItem.getEXPRESSION());
								jobItem.setPROBLEM_COUNT(rowCount);
								Integer newItemId = jobItemService.insertModel(jobItem);
								/*
								 * 	执行清洗
								 * 
								 * */
								Integer pageNumber = 0;
								if(rowCount!=0 && rowCount<=2000){
									pageNumber = 1;
								}else{
									if(rowCount%2000==0){
										pageNumber = rowCount/2000;
									}else{
										pageNumber = rowCount/2000+1;
									}
								}
								System.out.println("Clean: Now find "+pageNumber+" page of fatal records need to be cleaned.");
								if(rowCount>0){
									Connection connection = dbService.getConnection();
									connection.setAutoCommit(false);
									String sql = "";
									sql+="insert into RULE_JOB_DATA(";
									sql+="	ID,JOB_ITEM_ID,PRIMARY_VALUE,NAME_VALUE,COLUMN_VALUE,PROBLEM_DESC";
									sql+=")values(SEQ_RULE_JOB_DATA.NEXTVAL,?,?,?,?,?)";					
									PreparedStatement pStatement = connection.prepareStatement(sql);
									for(int q=1;q<=pageNumber;q++){
										/*
										 * 	分页查询问题数据	
										 * 
										 * */
										String querySql = "";
										querySql+="SELECT B.* FROM(";
										querySql+="	  SELECT A.* FROM("+detail.getSQL()+") A WHERE RN <= "+(q*2000);
										querySql+=") B WHERE B.RN > "+((q-1)*2000);
										$resultSet = $statement.executeQuery(querySql);
										/*
										 * 	执行入库	
										 * 
										 * */
										while($resultSet.next()){
											pStatement.setInt(1,newItemId);
											pStatement.setString(2,$resultSet.getObject(prName)==null?"":$resultSet.getString(prName));
											pStatement.setString(3,$resultSet.getObject(chName)==null?"":$resultSet.getString(chName));
											if("关联规则核查".equals(ruleItem.getAUDIT_TYPE()) && "核查关联数据条数".equals(ruleItem.getEXPRESSION())){
												pStatement.setString(4,"-");
											}else{
												if($resultSet.getObject(exName)!=null && !"".equals($resultSet.getString(exName))){
													pStatement.setString(4,$resultSet.getObject(exName)==null?"":$resultSet.getString(exName));
												}else{
													pStatement.setString(4,"-");
												}
											}
											pStatement.setString(5,detail.getPROBLEM_DETAIL());
											pStatement.addBatch();
										}
										pStatement.executeBatch();
										connection.commit();
										pStatement.clearBatch();
										if(((q*2000)%10000)==0){
											System.out.println("Clean: Step to "+(q*2000)+" index have been cleaned.");
										}
									}
									System.out.println("Clean: Clean Job finished.");
									/*
									 * 	关闭数据库连接.
									 * 
									 * */
									if(pStatement!=null){
										pStatement.close();
									}
									if(connection!=null){
										connection.close();
									}
								}
							}		
						}
					}
					/*
					 * 	全部错误信息入库后，清算有问题的数据数量、错误率、正常率.
					 * 
					 * */
					String problemSql = "";
					problemSql+="SELECT COUNT(1) AS PROBLEM_COUNT FROM(";
					problemSql+="	SELECT PRIMARY_VALUE,NAME_VALUE FROM(";
					problemSql+="		SELECT * FROM RULE_JOB_DATA WHERE JOB_ITEM_ID IN(";
					problemSql+="			SELECT ID AS JOB_ITEM_ID FROM RULE_JOB_ITEM WHERE JOB_ID IN(";
					problemSql+="				SELECT ID AS JOB_ID  FROM RULE_JOB WHERE RULE_ID = "+ruleId;
					problemSql+="			)";
					problemSql+="		)ORDER BY JOB_ITEM_ID ASC,PRIMARY_VALUE ASC";
					problemSql+="	)GROUP BY PRIMARY_VALUE,NAME_VALUE ORDER BY PRIMARY_VALUE ASC";
					problemSql+=")";
					problemCount = jdbcTemplate.queryForInt(problemSql);			
					ruleJob.setFATUAL_TOTAL(problemCount);				
					Double problemRatio = new Double(problemCount)/new Double(recordCount);				
					Double fatualRatio = problemRatio*100;
					if(fatualRatio!=null && fatualRatio.toString().length()>5){
						fatualRatio = Double.parseDouble(fatualRatio.toString().substring(0,4));
					}
					if(fatualRatio!=null){
						BigDecimal bigDecimal = new BigDecimal(fatualRatio);
						ruleJob.setFATUAL_RATIO(bigDecimal.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue());
						ruleJob.setNORMAL_RATIO(100-bigDecimal.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue());
						ruleJobService.updateModel(ruleJob);
					}
				}else if("自定义核查".equals(ruleDetail.getRULE_TYPE())){
					/*
					 * 	获取:Table_Name
					 * 
					 * */
					BasicDbTable bindedTable = basicDbTableService.selectModel(ruleDetail.getBIND_TABLE());
					/*
					 * 	获取当前核查表格的[记录总数]
					 * 
					 * */
					$resultSet = $statement.executeQuery("SELECT COUNT(1) AS ROW_COUNT FROM "+bindedTable.getTABLE_NAME());
					$resultSet.next();
					Integer recordCount = $resultSet.getInt("ROW_COUNT");
					System.out.println("Clean: Get Number Of The All Records In This Table Successfully. "+recordCount);
					ruleJob = ruleJobService.selectModel(historyId);
					ruleJob.setRECORD_TOTAL(recordCount);
					List<RuleItem> ruleItems = ruleItemService.selectModelsByHql("from RuleItem where BELONG_RULE = "+ruleId+" order by ID asc");
					if(ruleItems.size()>0){
						RuleItem sqlAuditItem = ruleItems.get(0);
						String auditSql = sqlAuditItem.getAUDIT_SQL();
						$resultSet = $statement.executeQuery("SELECT COUNT(1) AS ROW_COUNT FROM ("+auditSql+")");
						$resultSet.next();
						Integer rowCount = $resultSet.getInt("ROW_COUNT");
						System.out.println("Clean: Now find "+rowCount+" fatal records.");
						$resultSet = $statement.executeQuery(auditSql);
						/*
						 * 	将头信息保存入数据库.
						 * 
						 * */
						String[] columnKeys = sqlAuditItem.getEXPORT_COLUMNS().split(",");
						RuleJobItem jobItem = new RuleJobItem();
						jobItem.setJOB_ID(historyId);
						jobItem.setCHECK_COLUMN(columnKeys[2]);
						jobItem.setCHECK_EXPRESS("自定义核查");
						jobItem.setPROBLEM_COUNT(rowCount);
						Integer newItemId = jobItemService.insertModel(jobItem);
						/*
						 * 	执行清洗
						 * 
						 * */
						Integer pageNumber = 0;
						if(rowCount!=0 && rowCount<=2000){
							pageNumber = 1;
						}else{
							if(rowCount%2000==0){
								pageNumber = rowCount/2000;
							}else{
								pageNumber = rowCount/2000+1;
							}
						}
						System.out.println("Clean: Now find "+pageNumber+" page of fatal records need to be cleaned.");
						if(rowCount>0){
							Connection connection = dbService.getConnection();
							connection.setAutoCommit(false);
							String sql = "";
							sql+="insert into RULE_JOB_DATA(";
							sql+="	ID,JOB_ITEM_ID,PRIMARY_VALUE,NAME_VALUE,COLUMN_VALUE,PROBLEM_DESC";
							sql+=")values(SEQ_RULE_JOB_DATA.NEXTVAL,?,?,?,?,?)";					
							PreparedStatement pStatement = connection.prepareStatement(sql);
							for(int q=1;q<=pageNumber;q++){
								/*
								 * 	分页查询问题数据	
								 * 
								 * */
								String querySql = "";
								querySql+="SELECT B.* FROM(";
								querySql+="	  SELECT A.*,ROWNUM AS RN FROM("+auditSql+") A WHERE ROWNUM <= "+(q*2000);
								querySql+=") B WHERE B.RN > "+((q-1)*2000);
								$resultSet = $statement.executeQuery(querySql);
								/*
								 * 	开始组织数据入库
								 * 
								 * */
								while($resultSet.next()){
									/*
									 *  填充预编译SQL.
									 * 
									 * */
									pStatement.setInt(1,newItemId);
									pStatement.setString(2,$resultSet.getObject(columnKeys[0])==null?"":$resultSet.getString(columnKeys[0]));
									pStatement.setString(3,$resultSet.getObject(columnKeys[1])==null?"":$resultSet.getString(columnKeys[1]));
									pStatement.setString(4,$resultSet.getObject(columnKeys[2])==null?"":$resultSet.getString(columnKeys[2]));
									pStatement.setString(5,ruleDetail.getRULE_DESC());
									pStatement.addBatch();
								}
								/*
								 * 	执行数据插入.
								 * 
								 * */
								pStatement.executeBatch();
								connection.commit();
								pStatement.clearBatch();
								if(((q*2000)%10000)==0){
									System.out.println("Clean: Step to "+(q*2000)+" index have been cleaned.");
								}
							}
							System.out.println("Clean: Clean Job finished.");
							/*
							 * 	关闭数据库连接.
							 * 
							 * */
							if(pStatement!=null){
								pStatement.close();
							}
							if(connection!=null){
								connection.close();
							}
						}				
					}
					/*
					 * 	全部错误信息入库后，清算有问题的数据数量、错误率、正常率.
					 * 
					 * */
					String problemSql = "";
					problemSql+="SELECT COUNT(1) AS PROBLEM_COUNT FROM(";
					problemSql+="	SELECT PRIMARY_VALUE,NAME_VALUE FROM(";
					problemSql+="		SELECT * FROM RULE_JOB_DATA WHERE JOB_ITEM_ID IN(";
					problemSql+="			SELECT ID AS JOB_ITEM_ID FROM RULE_JOB_ITEM WHERE JOB_ID IN(";
					problemSql+="				SELECT ID AS JOB_ID  FROM RULE_JOB WHERE RULE_ID = "+ruleId;
					problemSql+="			)";
					problemSql+="		)ORDER BY JOB_ITEM_ID ASC,PRIMARY_VALUE ASC";
					problemSql+="	)GROUP BY PRIMARY_VALUE,NAME_VALUE ORDER BY PRIMARY_VALUE ASC";
					problemSql+=")";
					problemCount = jdbcTemplate.queryForInt(problemSql);
					ruleJob.setFATUAL_TOTAL(problemCount);				
					Double problemRatio = new Double(problemCount)/new Double(recordCount);				
					Double fatualRatio = problemRatio*100;
					BigDecimal bigDecimal = new BigDecimal(fatualRatio);
					ruleJob.setFATUAL_RATIO(bigDecimal.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue());
					ruleJob.setNORMAL_RATIO(100-bigDecimal.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue());
					ruleJobService.updateModel(ruleJob);
				}
				/*
				 * 	更新规则的最后执行时间和文件名
				 *
				 * */
				ruleDetail.setFILE_REPORT(null);
				ruleDetail.setLAST_ACTION_TIME(new Date());
				ruleDetailService.updateModel(ruleDetail);
				Date $date = new Date();
				Integer hourOfDay = $date.getHours();
				if((problemCount>0 && problemCount<20000) || ((hourOfDay>=22 && hourOfDay<=24) || (hourOfDay>=0 && hourOfDay<=9))){
					reportBuilder.createReport(ruleId);
				}
				/*
				 * 	记录核查历史
				 *
				 * */
				RuleHistory ruleHistory = new RuleHistory();
				ruleHistory.setRULE_ID(ruleDetail.getID());
				String dateToken = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				Integer maxOrder = jdbcTemplate.queryForInt("SELECT MAX(DAY_ORDER) FROM RULE_HISTORY WHERE RULE_ID = "+ruleId+" AND DATE_TOKEN LIKE '"+dateToken+"%'");
				if(maxOrder==null || maxOrder==0){
					dateToken = dateToken+"-01";
					maxOrder = 1;
				}else{
					maxOrder++;
					if(maxOrder<10){
						dateToken = dateToken+"-0"+maxOrder;
					}else{
						dateToken = dateToken+"-"+maxOrder;
					}
				}	
				ruleHistory.setDATE_TOKEN(dateToken);
				ruleHistory.setEXEC_DATE(new Date());
				ruleHistory.setPROBLEM_COUNT(problemCount);
				ruleHistory.setDAY_ORDER(maxOrder);
				ruleHistoryService.insertModel(ruleHistory);
				System.out.println("Clean: The Business Rule Coded By '"+ruleId+"' has ran successfully.");
				/*
				 * 	修改监控状态.
				 * 
				 * */
				RuleJob thisJob = ruleJobService.selectModel(historyId);
				if(thisJob!=null){
					thisJob.setEND_TIME(new Date());
					thisJob.setIS_FINISHED("Y");
					ruleJobService.updateModel(thisJob);
				}
				/*
				 * 	插入日志.
				 * 
				 * */
				String logSql = "";
				if(request!=null){
					logSql+="INSERT INTO S_SYSTEM_LOG(";
					logSql+="	ID,LOGIN_USER,BELONG_CITY,LOG_TYPE,LOG_INFO,EXEC_DATE";
					logSql+=")VALUES(";
					logSql+="	SEQ_S_SYSTEM_LOG.NEXTVAL,'"+loginUserUtil.getUserAccount(request)+"','"+loginUserUtil.getBelongArea(request)+"','规则执行','手动执行["+ruleDetail.getID()+"]["+ruleDetail.getRULE_NAME()+"]清洗规则.',SYSDATE";
					logSql+=")";
				}else{
					logSql+="INSERT INTO S_SYSTEM_LOG(";
					logSql+="	ID,LOGIN_USER,BELONG_CITY,LOG_TYPE,LOG_INFO,EXEC_DATE";
					logSql+=")VALUES(";
					logSql+="	SEQ_S_SYSTEM_LOG.NEXTVAL,'-','-','规则执行','系统按约定时间自动执行["+ruleDetail.getID()+"]["+ruleDetail.getRULE_NAME()+"]清洗规则.',SYSDATE";
					logSql+=")";
				}
				jdbcTemplate.execute(logSql);
			}
		}catch(Exception e){
			e.printStackTrace();
			jdbcTemplate.execute("UPDATE RULE_JOB SET TOKEN = '异常退出',FATAL_DESCRIBE = '"+e.getMessage()+"' WHERE ID = "+historyId);
		}finally{
			if($resultSet!=null){
				$resultSet.close();
			}
			if($statement!=null){
				$statement.close();
			}
			if($connection!=null){
				$connection.close();
			}
			System.out.println("============================ Data Clean Done ============================");
		}	
		return problemCount;
	}
	
	/*
	 * 	ModelConfig
	 * 
	 * */
	@Autowired
	private BasicDbService basicDbService;
	
	@Autowired
	private BasicDbTableService basicDbTableService;
	
	@Autowired
	private BasicColumnService basicColumnService;
	
	/*
	 * 	Rule
	 * 
	 * */
	
	@Autowired
	private RuleDetailService ruleDetailService;
	
	@Autowired
	private RuleHistoryService ruleHistoryService;
	
	@Autowired
	private RuleItemService ruleItemService;
	
	/*
	 * 	Job
	 * 
	 * */
	
	@Autowired
	private JobItemService jobItemService;
	
	@Autowired
	private JobDataService jobDataService;
	
	@Autowired
	private ReportBuilder reportBuilder;
	
	/*
	 * 	DbService and SqlBuilder
	 * 
	 * */
	
	@Autowired
	private DbService dbService;
	
	@Autowired
	private SqlBuilder sqlBuilder;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private RuleJobService ruleJobService;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
}