package com.function.compare.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.function.compare.model.CompareColumn;
import com.function.compare.model.CompareMonitor;
import com.function.dbmanage.model.BasicColumn;
import com.function.dbmanage.model.BasicDb;
import com.function.dbmanage.model.BasicDbTable;
import com.function.dbmanage.service.BasicColumnService;
import com.function.dbmanage.service.BasicDbService;
import com.function.dbmanage.service.BasicDbTableService;
import com.function.rules.service.DbService;
import com.system.LoginUserUtil;
@Repository("doCompareService")
public class DoCompareService{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	@Autowired
	private CompareMonitorService compareMonitorService;
	
	@Autowired
	private DbService dbService;
	
	private static Integer pageSize = 4000;
	
	/*
	 * 	以<ConnectColumn>为键，将ResultSet转化为<HashMap>为值.
	 * 
	 * */
	public static Map<String,Object> transferSetToList(ResultSet resultSet,String connectColumn)throws Exception{
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columnCount = metaData.getColumnCount();
		Map<String,Object> acheItems = new HashMap<String,Object>();
		Map<String,Object> detailObj = new HashMap<String,Object>();
		Integer curIndex = 1;
		while(resultSet.next()){
			if(resultSet.getObject(connectColumn)!=null){
				String acheKey = resultSet.getObject(connectColumn).toString();
				detailObj = new HashMap<String,Object>(columnCount);   
	            for(int i=1;i<=columnCount;i++){
	            	detailObj.put(metaData.getColumnName(i),resultSet.getObject(i));   
	            }   
	            //System.out.println("***:"+acheKey);
	            acheItems.put(acheKey,detailObj);
			}
			curIndex++;
        }   
		//System.out.println("=>共转换了："+(curIndex-1)+"条数据.");
        return acheItems;
	}
	
	/*
	 * 	将指定的字段抽取出来使用‘,’隔开形成字符串.
	 * 
	 * */
	public static String abstractKeys(
		Map<String,Object> acheItems,//aMaps
		String key,//'TO_Z_COLUMN'
		Integer start,//0
		Integer limit,//500
		Boolean isZConnString//true
	){
		Integer totalCount = 0;
		String columnKeys = "";
		Iterator<String> keySet = acheItems.keySet().iterator();
		while(keySet.hasNext()){
			String thisKey = keySet.next();
			if(totalCount>=start && totalCount<=(limit-1)){
				HashMap<String,Object> objMap = (HashMap<String,Object>)acheItems.get(thisKey);
				if(objMap.containsKey(key) && objMap.get(key)!=null && !"".equals(objMap.get(key).toString())){
					String id = objMap.get(key).toString();
					if("".equals(columnKeys)){
						if(isZConnString){
							columnKeys = "'"+id+"'";
						}else{
							columnKeys = id;
						}
					}else{
						if(isZConnString){
							columnKeys+= ",'"+id+"'";
						}else{
							columnKeys+= ","+id;
						}
					}
				}
			}
			totalCount++;
			if(totalCount>(limit-1)){
				break;
			}
		}
		return columnKeys;
	}
	
	/*
	 * 	核查A端和Z端不一致的情况
	 * 
	 * */
	public Integer doCompareUniform(
		CompareMonitor monitor,
		List<CompareColumn> matchedColumns,
		Integer aModelId,
		Integer zModelId,
		HttpServletRequest request
	)throws Exception{
		/*
		 * 	A表数据源相关变量
		 * 
		 * */
		Connection aConnection = null;
		Statement aStatement = null;
		ResultSet aResultSet = null;
		/*
		 * 	Z表数据源相关变量
		 * 
		 * */
		Connection zConnection = null;
		Statement zStatement = null;
		ResultSet zResultSet = null;
		Integer problemCount = 0;
		try{
			BasicDbTable aModel = basicDbTableService.selectModel(aModelId);
			BasicDbTable zModel = basicDbTableService.selectModel(zModelId);
			BasicDb aDatabase = basicDbService.selectModel(aModel.getBELONG_DB());
			BasicDb zDatabase = basicDbService.selectModel(zModel.getBELONG_DB());
			/*
			 * 	1.A表[主键]字段
			 *	2.A表[资源名称]字段
			 *	3.A表[所属地市]字段
			 *  4.A表[全字段]
			 *  
			 * */
			String aPrName = "";
			String aChName = "";
			String citName = "";
			String aColumnString = "";
			List<BasicColumn> aColumns = basicColumnService.selectModelsByHql("from BasicColumn where BELONG_TABLE = "+aModelId);
			for(int l=0;l<aColumns.size();l++){
				if("Y".equals(aColumns.get(l).getIS_PRIMARY_KEY()) && "Y".equals(aColumns.get(l).getIS_EXPORT())){
					/*A表[主键]字段*/
					aPrName = aColumns.get(l).getCOLUMN_NAME().toUpperCase();
				}else{
					if("N".equals(aColumns.get(l).getIS_PRIMARY_KEY()) && "Y".equals(aColumns.get(l).getIS_EXPORT())){
						/*A表[标识]字段*/
						aChName = aColumns.get(l).getCOLUMN_NAME().toUpperCase();
					}
				}
				/*A表[地市]字段*/
				if("地市".equals(aColumns.get(l).getDATA_TYPE())){
					citName = aColumns.get(l).getCOLUMN_NAME().toUpperCase();
				}
				if("".equals(aColumnString)){
					aColumnString = "C."+aColumns.get(l).getCOLUMN_NAME().toUpperCase(); 
				}else{
					aColumnString+= ",C."+aColumns.get(l).getCOLUMN_NAME().toUpperCase();
				}
			}
			System.out.println("=>主键字段:"+aPrName+",资源名称字段："+aChName+",地市字段："+citName);
			/* 
			 * 	Z表[全]字段.
			 * 
			 * */
			String zColumnStringA = "";
			String zColumnStringC = "";
			List<BasicColumn> zColumns = basicColumnService.selectModelsByHql("from BasicColumn where BELONG_TABLE = "+zModelId);
			for(int l=0;l<zColumns.size();l++){
				if("".equals(zColumnStringA)){
					zColumnStringA = "A."+zColumns.get(l).getCOLUMN_NAME().toUpperCase();
					zColumnStringC = "C."+zColumns.get(l).getCOLUMN_NAME().toUpperCase();
				}else{
					zColumnStringA+= ",A."+zColumns.get(l).getCOLUMN_NAME().toUpperCase();
					zColumnStringC+= ",C."+zColumns.get(l).getCOLUMN_NAME().toUpperCase();
				}
			}
			/* 
			 * 	A表和Z表[关联]字段.
			 * 
			 * 	1.A表用于关联Z表所用的[关联]字段.
			 * 	2.Z表用于关联A表所用的[关联]字段.
			 *  3.Z表中的关联字段是否是[字符型]?.
			 *  
			 * */
			String aCnName = "";
			String zCnName = "";
			Boolean isZCnString = false;
			if(matchedColumns.size()>0){
				for(int l=0;l<matchedColumns.size();l++){
					/*
					 * 	如果是关联字段.
					 * 
					 * */
					if("Y".equals(matchedColumns.get(l).getIS_CONNECT_COLUMN())){
						/*
						 * 	A表用于关联Z表所用的[关联]字段.
						 * 
						 * */
						aCnName = matchedColumns.get(l).getA_COLUMN_NAME().toUpperCase();
						BasicColumn aColumn = basicColumnService.selectModel(matchedColumns.get(l).getA_COLUMN_ID());
						if(aColumn!=null){
							if(aColumn.getDATA_TYPE().indexOf("字符")!=-1){
								Boolean isACnString = true;
							}else{
								Boolean isACnString = false;
							}
						}
						/*Z表[关联]字段*/
						zCnName = matchedColumns.get(l).getZ_COLUMN_NAME().toUpperCase();
						BasicColumn zColumn = basicColumnService.selectModel(matchedColumns.get(l).getZ_COLUMN_ID());
						if(zColumn!=null){
							if(zColumn.getDATA_TYPE().indexOf("字符")!=-1){
								isZCnString = true;
							}else{
								isZCnString = false;
							}
						}
						break;
					}
				}
			}
			System.out.println("=>A->Z关联字段："+aCnName+",Z->A关联字段："+zCnName+",Z端关联字段是否为字符："+isZCnString);
			/*
			 * 	初始化A数据库链接
			 * 
			 * */
			aConnection = getDbConnection(aModelId);
			aStatement = aConnection.createStatement();
			aStatement.setQueryTimeout(10800);
			/*
			 * 	初始化Z数据库链接
			 * 
			 * */
			zConnection = getDbConnection(zModelId);
			zStatement = zConnection.createStatement();
			zStatement.setQueryTimeout(10800);
			/*
			 * 	缓存地市信息.
			 * 
			 * 	A_DB_USER_NAME.A_TABLE and A_CONNECT_COLUMN is not null.
			 * 	
			 * 	distinct(A_TABLE.CITY_COLUMN)
			 * 
			 * */
			Map<String,Integer> cityCountMap = new HashMap<String,Integer>();
			System.out.println("SQL:SELECT DISTINCT(A."+citName+") AS CITY_NAME FROM "+aDatabase.getUSER_NAME()+"."+aModel.getTABLE_NAME()+" A WHERE A."+aCnName+" IS NOT NULL ");
			aResultSet = aStatement.executeQuery("SELECT DISTINCT(A."+citName+") AS CITY_NAME FROM "+aDatabase.getUSER_NAME()+"."+aModel.getTABLE_NAME()+" A WHERE A."+aCnName+" IS NOT NULL ");
			while(aResultSet.next()){
				if(aResultSet.getObject("CITY_NAME")!=null){
					String CITY_NAME = aResultSet.getObject("CITY_NAME").toString();
					cityCountMap.put(CITY_NAME,0);
				}
			}
			/*
			 * 	执行核查：把效用属性“不为空”且“唯一”的数据找出来进行比对.
			 * 
			 * */
			String aClientSql = "";
			aClientSql+="SELECT A."+aPrName+",A."+aChName+",A."+aCnName+" AS AAA ";
			aClientSql+="FROM "+aDatabase.getUSER_NAME()+"."+aModel.getTABLE_NAME()+" A ";
			aClientSql+="WHERE A."+aCnName+" IS NOT NULL ";
			aResultSet = aStatement.executeQuery("SELECT COUNT(1) AS ROW_COUNT FROM("+aClientSql+")");
			aResultSet.next();
			Integer aRecordCount = aResultSet.getInt("ROW_COUNT");
			System.out.println("=>Compare:在A端表中共发现"+aRecordCount+"条可被核查（效用字段不为空）数据.");
			if(aRecordCount>0){
				/*
				 * 	初始化本地（DataQuality）链接.
				 * 
				 * */
				Connection connection = dbService.getConnection();
				connection.setAutoCommit(false);
				String insertSql = "";
				insertSql+="INSERT INTO COMPARE_DATA(";
				insertSql+="	ID,PRIMARY_VALUE,RESOURCE_NAME,PROBLEM_TYPE,PROBLEM_DESC,BELONG_MONITOR,DATA_CITY";
				insertSql+=")VALUES(SEQ_COMPARE_DATA.NEXTVAL,?,?,?,?,?,?)";
				PreparedStatement pStatement = connection.prepareStatement(insertSql);
				/*
				 * 	计算分页.
				 * 
				 * */
				Integer totalPage = 0;
				if(aRecordCount>0 && aRecordCount<=2000){
					totalPage = 1;
				}else{
					if(aRecordCount%2000==0){
						totalPage = aRecordCount/2000;
					}else{
						totalPage = aRecordCount/2000+1;
					}
				}
				System.out.println("=>Compare:这些数据被分为"+totalPage+"批次进行比对.每批2000条.");
				Boolean isPrint = false;
				for(int p=1;p<=totalPage;p++){
					if(((p*2000)%10000)==0){
						System.out.println("=>Compare:已分析至第"+(p*2000)+"条数据.");
					}
					/*
					 * 	分页查询待核查的数据		
					 * 
					 * */
					String pageSql = "";
					pageSql+="SELECT M.* FROM (";
					pageSql+="	  SELECT P.*,ROWNUM AS RN FROM (";
					pageSql+="	  	  SELECT "+aColumnString+",C."+aCnName+" AS TO_Z_COLUMN ";
					pageSql+="	  	  FROM "+aDatabase.getUSER_NAME()+"."+aModel.getTABLE_NAME()+" C ";
					pageSql+="	 	  WHERE C."+aCnName+" IS NOT NULL ORDER BY C."+aCnName+" ASC";
					pageSql+="	  ) P WHERE ROWNUM <= "+(p*2000);
					pageSql+=") M WHERE M.RN > "+((p-1)*2000);
					if(!isPrint){
						//System.out.println("###:"+pageSql);
						//System.out.println("###:开始获取A端当前页.");
					}
					aResultSet = aStatement.executeQuery(pageSql);
					if(!isPrint){
						//System.out.println("###:获取A端当前页结束.");
					}
					/*
					 * 	将获取的ResultSet通过HashMap的形式缓存.以<ConnectColumn>为键，将ResultSet转化为<HashMap>为值.
					 * 
					 * */
					Map<String,Object> aMaps = transferSetToList(aResultSet,aCnName);
					/*
					 * 	根据<A:IDs>查询Z端对应的数据并进行缓存.	
					 * 
					 * */
					String zSql = "";
					zSql+="SELECT "+zColumnStringA+" ";
					zSql+="FROM "+zDatabase.getUSER_NAME()+"."+zModel.getTABLE_NAME()+" A ";
					zSql+="WHERE A."+zCnName+" IS NOT NULL ";
					for(int g=1;g<=4;g++){
						Integer startPage = (g-1)*500;//0,500,1000,1500
						Integer limitPage = g*500;//500,1000,1500,2000
						String ids = abstractKeys(aMaps,"TO_Z_COLUMN",startPage,limitPage,isZCnString);
						if(ids!=null && !"".equals(ids)){
							if(g==1){
								zSql+=" AND A."+zCnName+" IN ("+ids+")";
							}else{
								zSql+=" UNION ALL SELECT "+zColumnStringC+" FROM "+zDatabase.getUSER_NAME()+"."+zModel.getTABLE_NAME()+" C WHERE C."+zCnName+" IS NOT NULL AND C."+zCnName+" IN("+ids+")";
							}
						}
					}
					if(!isPrint){
						//System.out.println("###:开始获取Z端当前页.");
					}
					zResultSet = zStatement.executeQuery(zSql);
					if(!isPrint){
						//System.out.println("###:获取Z端当前页结束.");
						//System.out.println("###:"+zSql);
						//isPrint = true;
					}
					/*
					 * 	将获取的ResultSet通过HashMap的形式缓存. 以<ConnectColumn>为键，将ResultSet转化为<HashMap>为值.
					 * 
					 * */
					Map<String,Object> zMaps = transferSetToList(zResultSet,zCnName);
					System.out.println("=>当前："+aMaps.size()+"："+zMaps.size());
					/*
					 * 	开始由aMap到zMap进行遍历.
					 * 
					 * */
					Iterator<String> keySets = aMaps.keySet().iterator();
					while(keySets.hasNext()){
						String aKey = keySets.next();
						if(zMaps.containsKey(aKey)){
							/*
							 * 	===> 在Z端已找到匹配，开始比对.
							 * 
							 * */
							String matchedDescribe = "";
							Map<String,Object> aMap = (Map<String,Object>)aMaps.get(aKey);
							Map<String,Object> zMap = (Map<String,Object>)zMaps.get(aKey);
							for(int c=0;c<matchedColumns.size();c++){
								CompareColumn compareColumn = matchedColumns.get(c);
								/*
								 * 	如果不是效用属性，则是比对属性.
								 * 
								 * */
								if("N".equals(compareColumn.getIS_CONNECT_COLUMN())){
									if(aMap.get(compareColumn.getA_COLUMN_NAME().toUpperCase())==null && zMap.get(compareColumn.getZ_COLUMN_NAME().toUpperCase())==null){
										/*
										 * 	1.如果匹配字段都为空（A:null;B:null），则跳过比对. 视为一致.
										 * 
										 * */
									}else if(aMap.get(compareColumn.getA_COLUMN_NAME().toUpperCase())!=null && zMap.get(compareColumn.getZ_COLUMN_NAME().toUpperCase())==null){
										/*
										 * 	2.如果A端不为空，但是Z端为空，则视为异常.
										 * 
										 * */
										if("".equals(matchedDescribe)){
											matchedDescribe = aModel.getTABLE_ALIAS()+"["+compareColumn.getA_COLUMN_ALIAS()+"]的值不为空（"+aMap.get(compareColumn.getA_COLUMN_NAME().toUpperCase()).toString()+"），但是"+zModel.getTABLE_ALIAS()+"["+compareColumn.getZ_COLUMN_ALIAS()+"]的值为空.";
										}else{
											matchedDescribe+= "；"+aModel.getTABLE_ALIAS()+"["+compareColumn.getA_COLUMN_ALIAS()+"]的值不为空（"+aMap.get(compareColumn.getA_COLUMN_NAME().toUpperCase()).toString()+"），但是"+zModel.getTABLE_ALIAS()+"["+compareColumn.getZ_COLUMN_ALIAS()+"]的值为空.";
										}
										if(cityCountMap.containsKey(aMap.get(citName.toUpperCase()).toString())){
											cityCountMap.put(aMap.get(citName.toUpperCase()).toString(),Integer.parseInt(cityCountMap.get(aMap.get(citName.toUpperCase()).toString()).toString())+1);
										}
									}else if(aMap.get(compareColumn.getA_COLUMN_NAME().toUpperCase())==null && zMap.get(compareColumn.getZ_COLUMN_NAME().toUpperCase())!=null){
										/*
										 * 	3.如果A端为空，但是Z端不为空，则视为异常.
										 * 
										 * */
										if("".equals(matchedDescribe)){
											matchedDescribe = aModel.getTABLE_ALIAS()+"["+compareColumn.getA_COLUMN_ALIAS()+"]的值为空，但是"+zModel.getTABLE_ALIAS()+"["+compareColumn.getZ_COLUMN_ALIAS()+"]的值（"+zMap.get(compareColumn.getZ_COLUMN_NAME().toUpperCase()).toString()+"）不为空.";
										}else{
											matchedDescribe+= "；"+aModel.getTABLE_ALIAS()+"["+compareColumn.getA_COLUMN_ALIAS()+"]的值为空，但是"+zModel.getTABLE_ALIAS()+"["+compareColumn.getZ_COLUMN_ALIAS()+"]的值（"+zMap.get(compareColumn.getZ_COLUMN_NAME().toUpperCase()).toString()+"）不为空.";
										}
										if(citName!=null && aMap.get(citName.toUpperCase())!=null && cityCountMap.containsKey(aMap.get(citName.toUpperCase()).toString())){
											cityCountMap.put(aMap.get(citName.toUpperCase()).toString(),Integer.parseInt(cityCountMap.get(aMap.get(citName.toUpperCase()).toString()).toString())+1);
										}
									}else if(aMap.get(compareColumn.getA_COLUMN_NAME().toUpperCase())!=null && zMap.get(compareColumn.getZ_COLUMN_NAME().toUpperCase())!=null){
										/*
										 * 	4.如果A端和Z端都不为空，则比对一致性.
										 * 
										 * */
										String aValue = aMap.get(compareColumn.getA_COLUMN_NAME().toUpperCase()).toString();
										String zValue = zMap.get(compareColumn.getZ_COLUMN_NAME().toUpperCase()).toString();
										if(!aValue.equals(zValue)){
											if("".equals(matchedDescribe)){
												matchedDescribe = aModel.getTABLE_ALIAS()+"["+compareColumn.getA_COLUMN_ALIAS()+"]的值（"+aMap.get(compareColumn.getA_COLUMN_NAME().toUpperCase()).toString()+"）与"+zModel.getTABLE_ALIAS()+"["+compareColumn.getZ_COLUMN_ALIAS()+"]的值（"+zMap.get(compareColumn.getZ_COLUMN_NAME().toUpperCase()).toString()+"）不一致.";
											}else{
												matchedDescribe+= "；"+aModel.getTABLE_ALIAS()+"["+compareColumn.getA_COLUMN_ALIAS()+"]的值（"+aMap.get(compareColumn.getA_COLUMN_NAME().toUpperCase()).toString()+"）与"+zModel.getTABLE_ALIAS()+"["+compareColumn.getZ_COLUMN_ALIAS()+"]的值（"+zMap.get(compareColumn.getZ_COLUMN_NAME().toUpperCase()).toString()+"）不一致.";
											}
											if(aMap.get(citName.toUpperCase())!=null && cityCountMap.containsKey(aMap.get(citName.toUpperCase()).toString())){
												cityCountMap.put(aMap.get(citName.toUpperCase()).toString(),Integer.parseInt(cityCountMap.get(aMap.get(citName.toUpperCase()).toString()).toString())+1);
											}
										}
									}
								}
							}
							if(!"".equals(matchedDescribe)){
								pStatement.setString(1,aMap.get(aPrName.toUpperCase())==null?"":aMap.get(aPrName.toUpperCase()).toString());
								pStatement.setString(2,aMap.get(aChName.toUpperCase())==null?"":aMap.get(aChName.toUpperCase()).toString());
								pStatement.setString(3,"IS_UNIFORM");
								pStatement.setString(4,matchedDescribe);
								pStatement.setInt(5,monitor.getID());
								pStatement.setString(6,aMap.get(citName.toUpperCase())==null?"":aMap.get(citName.toUpperCase()).toString());
								pStatement.addBatch();
								problemCount++;
							}
						}else{
							//在Z端未找到匹配，不执行比对.
						}	
					}
					pStatement.executeBatch();
					connection.commit();
					pStatement.clearBatch();
					monitor.setNOT_UNIFORM(problemCount);
					compareMonitorService.updateModel(monitor);
				}
				if(pStatement!=null){
					pStatement.close();
				}
				if(connection!=null){
					connection.close();
				}
			}else{
				System.out.println("=>Compare:A端表中未发现可被核查数据.");
			}
			if(cityCountMap!=null){
				Iterator<String> car = cityCountMap.keySet().iterator();
				while(car.hasNext()){
					String CITY_NAME = car.next();
					String FATAL_COUNT = cityCountMap.get(CITY_NAME).toString();
					//System.out.println("=>Compare:开始分地市记录数据：'IS_UNIFORM':"+FATAL_COUNT);
					jdbcTemplate.execute("INSERT INTO COMPARE_CITY_COUNT(ID,MONITOR_ID,FATAL_TYPE,CITY_NAME,FATAL_COUNT)VALUES(SEQ_COMPARE_CITY_COUNT.NEXTVAL,"+monitor.getID()+",'IS_UNIFORM','"+CITY_NAME+"',"+FATAL_COUNT+")");
				}
			}
			return problemCount;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}finally{
			/*
			 * 	关闭A端数据源
			 * 
			 * */
			if(aResultSet!=null){
				aResultSet.close();
			}
			if(aStatement!=null){
				aStatement.close();
			}
			if(aConnection!=null){
				aConnection.close();
			}
			/*
			 * 	关闭Z端数据源
			 * 
			 * */
			if(zResultSet!=null){
				zResultSet.close();
			}
			if(zStatement!=null){
				zStatement.close();
			}
			if(zConnection!=null){
				zConnection.close();
			}
		}
	}
	
	/*
	 * 	核查仅A端有数据或仅Z端有数据
	 * 
	 * 
	 * */
	public Integer doCompareSameDb(Integer newMonitorCode,List<CompareColumn> matchedColumns,Integer aModelId,Integer zModelId,String checkType)throws Exception{
		Connection connByModelId = null;
		Statement queryStatement = null;
		ResultSet resultSet = null;
		BasicDbTable aModel = basicDbTableService.selectModel(aModelId);
		BasicDbTable zModel = basicDbTableService.selectModel(zModelId);
		BasicDb aModelDb = basicDbService.selectModel(aModel.getBELONG_DB());
		BasicDb zModelDb = basicDbService.selectModel(zModel.getBELONG_DB());
		Integer problemCount = 0;
		try{
			String prColumn = "";
			String chColumn = "";
			String ciColumn = "";
			/*
			 * 	根据'IS_A_ONLY'和'IS_Z_ONLY'获取A表和Z表相应的主键字段、名称字段.
			 * 
			 * */
			List<BasicColumn> specialColumns = null;
			if("IS_A_ONLY".equals(checkType)){
				specialColumns = basicColumnService.selectModelsByHql("from BasicColumn where BELONG_TABLE = "+aModelId+" and (IS_EXPORT = 'Y' or DATA_TYPE = '地市')");
			}else if("IS_Z_ONLY".equals(checkType)){
				specialColumns = basicColumnService.selectModelsByHql("from BasicColumn where BELONG_TABLE = "+zModelId+" and (IS_EXPORT = 'Y' or DATA_TYPE = '地市')");
			}
			if(specialColumns!=null && specialColumns.size()>0){
				for(int l=0;l<specialColumns.size();l++){
					if("Y".equals(specialColumns.get(l).getIS_PRIMARY_KEY()) && "Y".equals(specialColumns.get(l).getIS_EXPORT())){
						/*某端[主键]字段*/
						prColumn = specialColumns.get(l).getCOLUMN_NAME();
					}
					if("N".equals(specialColumns.get(l).getIS_PRIMARY_KEY()) && "Y".equals(specialColumns.get(l).getIS_EXPORT())){
						/*某端[主键]字段*/
						chColumn = specialColumns.get(l).getCOLUMN_NAME();
					}
					if("地市".equals(specialColumns.get(l).getDATA_TYPE())){
						/*某端[地市]字段*/
						ciColumn = specialColumns.get(l).getCOLUMN_NAME();
					}
				}
			}
			/*
			 * 	根据比对规则配置，获取效用属性.
			 * 
			 * */
			String aCColumn = "";
			String zCColumn = "";
			for(int i=0;i<matchedColumns.size();i++){
				CompareColumn compareColumn = matchedColumns.get(i);
				if("Y".equals(compareColumn.getIS_CONNECT_COLUMN())){
					/*A端[效用]字段*/
					aCColumn = compareColumn.getA_COLUMN_NAME();
					/*Z端[效用]字段*/
					zCColumn = compareColumn.getZ_COLUMN_NAME();
					break;
				}
			}
			/*核查SQL.*/
			String sql = "";
			/*问题描述DESC.*/
			String des = "";
			if("IS_A_ONLY".equals(checkType)){
				
				/*
				 * 	查找仅A端存在的数据.
				 * 
				 * */
				sql+="SELECT "+prColumn+","+chColumn+","+ciColumn+" AS DQM_CITY_NAME FROM "+aModelDb.getUSER_NAME()+"."+aModel.getTABLE_NAME()+" WHERE "+aCColumn+" NOT IN(";
				sql+="	SELECT DISTINCT("+zCColumn+") FROM "+zModelDb.getUSER_NAME()+"."+zModel.getTABLE_NAME()+" WHERE "+zCColumn+" IS NOT NULL";
				sql+=")";
				des = "仅["+aModel.getTABLE_ALIAS()+"]表中有数据.";
				
			}else if("IS_Z_ONLY".equals(checkType)){
				
				/*
				 * 	查找仅Z端存在的数据.
				 * 
				 * */
				sql+="SELECT "+prColumn+","+chColumn+","+ciColumn+" AS DQM_CITY_NAME FROM "+zModelDb.getUSER_NAME()+"."+zModel.getTABLE_NAME()+" WHERE "+zCColumn+" NOT IN(";
				sql+="	SELECT DISTINCT("+aCColumn+") FROM "+aModelDb.getUSER_NAME()+"."+aModel.getTABLE_NAME()+" WHERE "+aCColumn+" IS NOT NULL";
				sql+=")";
				des = "仅["+zModel.getTABLE_ALIAS()+"]表中有数据.";
			}
			System.out.println("=>Compare:"+sql);
			/*
			 * 	初始化数据库链接.
			 * 
			 * */
			connByModelId = getDbConnection(aModelId);
			queryStatement = connByModelId.createStatement();
			queryStatement.setQueryTimeout(10800);
			/*
			 * 	初始化地市统计.
			 * 
			 * */
			Map<String,Integer> cityCountMap = new HashMap<String,Integer>();
			String distinctCity = "";
			if("IS_A_ONLY".equals(checkType)){
				distinctCity = "SELECT DISTINCT(A."+ciColumn+") AS CITY_NAME FROM "+aModelDb.getUSER_NAME()+"."+aModel.getTABLE_NAME()+" A WHERE A."+ciColumn+" IS NOT NULL ";
			}else if("IS_Z_ONLY".equals(checkType)){
				distinctCity = "SELECT DISTINCT(A."+ciColumn+") AS CITY_NAME FROM "+zModelDb.getUSER_NAME()+"."+zModel.getTABLE_NAME()+" A WHERE A."+ciColumn+" IS NOT NULL ";
			}
			resultSet = queryStatement.executeQuery(distinctCity);
			while(resultSet.next()){
				String CITY_NAME = resultSet.getObject("CITY_NAME").toString();
				cityCountMap.put(CITY_NAME,0);
			}
			/*
			 * 	执行统计.
			 * 
			 * */
			resultSet = queryStatement.executeQuery("SELECT COUNT(1) AS ROW_COUNT FROM ("+sql+")");
			resultSet.next();
			Integer recordCount = resultSet.getInt("ROW_COUNT");
			if(recordCount>0){
				/*
				 * 	配置本地链接、PreparedStatement.
				 * 
				 * */
				Connection connection = dbService.getConnection();
				connection.setAutoCommit(false);
				String insertSql = "";
				insertSql+="INSERT INTO COMPARE_DATA(";
				insertSql+="	ID,PRIMARY_VALUE,RESOURCE_NAME,PROBLEM_TYPE,PROBLEM_DESC,BELONG_MONITOR,DATA_CITY";
				insertSql+=")VALUES(SEQ_COMPARE_DATA.NEXTVAL,?,?,?,?,?,?)";
				PreparedStatement pStatement = connection.prepareStatement(insertSql);
				/*
				 * 	将问题数据分页入库.
				 * 
				 * */
				Integer totalPage = recordCount/pageSize+1;
				for(int p=1;p<=totalPage;p++){
					Integer startIndex = (p-1)*pageSize;
					Integer limitIndex = p*pageSize;
					String pageSql = "";
					pageSql+="SELECT * FROM (";
					pageSql+="	  SELECT W.*,ROWNUM AS RN FROM("+sql+") W WHERE ROWNUM <= "+limitIndex;
					pageSql+=") M WHERE RN > "+startIndex;
					resultSet = queryStatement.executeQuery(pageSql);
					while(resultSet.next()){
						pStatement.setString(1,resultSet.getObject(prColumn)==null?"":resultSet.getString(prColumn));
						pStatement.setString(2,resultSet.getObject(chColumn)==null?"":resultSet.getString(chColumn));
						pStatement.setString(3,checkType);
						pStatement.setString(4,des);
						pStatement.setInt(5,newMonitorCode);
						pStatement.setString(6,resultSet.getObject("DQM_CITY_NAME")==null?"":resultSet.getString("DQM_CITY_NAME"));
						pStatement.addBatch();
						problemCount++;
						/*
						 * 	按照地市进行分流.
						 * 
						 * */
						if(cityCountMap.keySet().size()!=0 && resultSet.getObject("DQM_CITY_NAME")!=null && cityCountMap.containsKey(resultSet.getString("DQM_CITY_NAME"))){
							cityCountMap.put(resultSet.getString("DQM_CITY_NAME"),Integer.parseInt(cityCountMap.get(resultSet.getString("DQM_CITY_NAME")).toString())+1);
						}
					}
					pStatement.executeBatch();
					connection.commit();
					pStatement.clearBatch();
				}
				if(pStatement!=null){
					pStatement.close();
				}
				if(connection!=null){
					connection.close();
				}
			}
			/*
			 * 	保存地市统计信息.
			 * 
			 * */
			if(cityCountMap!=null && cityCountMap.keySet().size()>0){
				Iterator<String> car = cityCountMap.keySet().iterator();
				while(car.hasNext()){
					String CITY_NAME = car.next();
					String FATAL_COUNT = cityCountMap.get(CITY_NAME).toString();
					//System.out.println("=>Compare:开始分地市记录数据："+checkType+":"+FATAL_COUNT);
					jdbcTemplate.execute("INSERT INTO COMPARE_CITY_COUNT(ID,MONITOR_ID,FATAL_TYPE,CITY_NAME,FATAL_COUNT)VALUES(SEQ_COMPARE_CITY_COUNT.NEXTVAL,"+newMonitorCode+",'"+checkType+"','"+CITY_NAME+"',"+FATAL_COUNT+")");
				}
			}
			return problemCount;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}finally{
			if(resultSet!=null){
				resultSet.close();
			}
			if(queryStatement!=null){
				queryStatement.close();
			}
			if(connByModelId!=null){
				connByModelId.close();
			}
		}
	}

	/*
	 * 	核查：A端异常数据、Z端异常数据
	 * 
	 * */
	public Integer doCompare(Integer newMonitorCode,Integer modelId,List<CompareColumn> matchedColumns,String checkType,HttpServletRequest request)throws Exception{
		Connection connByModelId = null;
		Statement queryState = null;
		ResultSet resultSet = null;
		Integer problemCount = 0;
		try{
			String prColumn = "";
			String chColumn = "";
			String cityColu = "";
			String coColumn = "";
			BasicDbTable checkingModel = basicDbTableService.selectModel(modelId);
			BasicDb checkingDb = basicDbService.selectModel(checkingModel.getBELONG_DB());
			List<BasicColumn> specialColumns = basicColumnService.selectModelsByHql("from BasicColumn where (BELONG_TABLE = "+modelId+" and IS_EXPORT = 'Y') or (BELONG_TABLE = "+modelId+" and DATA_TYPE = '地市')");
			if(specialColumns.size()>0){
				for(int l=0;l<specialColumns.size();l++){
					if("Y".equals(specialColumns.get(l).getIS_PRIMARY_KEY()) && "Y".equals(specialColumns.get(l).getIS_EXPORT())){
						/*A表[主键]字段*/
						prColumn = specialColumns.get(l).getCOLUMN_NAME();
					}else{
						if("Y".equals(specialColumns.get(l).getIS_EXPORT())){
							/*A表[标识]字段*/
							chColumn = specialColumns.get(l).getCOLUMN_NAME();
						}
					}
					/*A表[地市]字段*/
					if("地市".equals(specialColumns.get(l).getDATA_TYPE())){
						cityColu = specialColumns.get(l).getCOLUMN_NAME();
					}
				}
			}
			for(int i=0;i<matchedColumns.size();i++){
				CompareColumn compareColumn = matchedColumns.get(i);
				if("Y".equals(compareColumn.getIS_CONNECT_COLUMN())){
					if("IS_A_NULL".equals(checkType) || "IS_A_NOT_UNIQUE".equals(checkType)){
						/*A表[效用]字段*/
						coColumn = compareColumn.getA_COLUMN_NAME();
					}else if("IS_Z_NULL".equals(checkType) || "IS_Z_NOT_UNIQUE".equals(checkType)){
						/*Z表[效用]字段*/
						coColumn = compareColumn.getZ_COLUMN_NAME();
					}
					break;
				}
			}
			String auditSql = "";
			String probDesc = "";
			if("IS_A_NULL".equals(checkType) || "IS_Z_NULL".equals(checkType)){
				/*
				 * 	清洗效用字段[为空]；SQL
				 * 
				 * */
				auditSql = "select "+prColumn+","+chColumn+","+cityColu+" AS DQM_CITY_NAME from "+checkingDb.getUSER_NAME()+"."+checkingModel.getTABLE_NAME()+" where "+coColumn+" is null";
				/*
				 * 	清洗效用字段[为空]；DESC
				 * 
				 * */
				probDesc = "模型["+checkingModel.getTABLE_ALIAS()+"]中的效用字段["+coColumn+"]的值为空.";
			}else if("IS_A_NOT_UNIQUE".equals(checkType) || "IS_Z_NOT_UNIQUE".equals(checkType)){
				/*
				 * 	清洗效用字段[不唯一]；SQL
				 * 
				 * */
				auditSql+="SELECT "+prColumn+","+chColumn+","+cityColu+" AS DQM_CITY_NAME FROM "+checkingDb.getUSER_NAME()+"."+checkingModel.getTABLE_NAME()+" WHERE "+coColumn+" IN(";
				auditSql+="	  SELECT T."+coColumn+" FROM (";
				auditSql+="	  	  SELECT "+coColumn+",COUNT(1) AS RECORD_COUNT FROM "+checkingDb.getUSER_NAME()+"."+checkingModel.getTABLE_NAME()+" WHERE "+coColumn+" IS NOT NULL GROUP BY "+coColumn;
				auditSql+="	  ) T WHERE T.RECORD_COUNT > 1";
				auditSql+=")";
				/*
				 * 	清洗效用字段[不唯一]；DESC
				 * 
				 * */
				probDesc = "模型["+checkingModel.getTABLE_ALIAS()+"]中的效用字段["+coColumn+"]不唯一.";
			}
			connByModelId = getDbConnection(modelId);
			queryState = connByModelId.createStatement();
			queryState.setQueryTimeout(10800);
			/*
			 * 	初始化地市统计.
			 * 
			 * */
			Map<String,Integer> cityCountMap = new HashMap<String,Integer>();
			resultSet = queryState.executeQuery("SELECT DISTINCT(A."+cityColu+") AS CITY_NAME FROM "+checkingDb.getUSER_NAME()+"."+checkingModel.getTABLE_NAME()+" A WHERE A."+cityColu+" IS NOT NULL");
			while(resultSet.next()){
				String CITY_NAME = resultSet.getObject("CITY_NAME").toString();
				cityCountMap.put(CITY_NAME,0);
			}
			/*
			 * 	执行数量统计.
			 * 
			 * */
			resultSet = queryState.executeQuery("SELECT COUNT(1) AS ROW_COUNT FROM ("+auditSql+")");
			resultSet.next();
			Integer recordCount = resultSet.getInt("ROW_COUNT");
			if(recordCount>0){
				/*配置链接*/
				Connection connection = dbService.getConnection();
				connection.setAutoCommit(false);
				String insertSql = "";
				insertSql+="INSERT INTO COMPARE_DATA(";
				insertSql+="	ID,PRIMARY_VALUE,RESOURCE_NAME,PROBLEM_TYPE,PROBLEM_DESC,BELONG_MONITOR,DATA_CITY";
				insertSql+=")VALUES(SEQ_COMPARE_DATA.NEXTVAL,?,?,?,?,?,?)";
				PreparedStatement pStatement = connection.prepareStatement(insertSql);
				/*分页入库*/
				Integer totalPage = recordCount/pageSize+1;
				for(int p=1;p<=totalPage;p++){
					Integer startIndex = (p-1)*pageSize+1;
					Integer limitIndex = p*pageSize;
					String pageSql = "";
					pageSql+="SELECT * FROM (";
					pageSql+="	  SELECT W.*,ROWNUM AS RN FROM("+auditSql+") W WHERE ROWNUM <= "+limitIndex;
					pageSql+=") M WHERE RN >= "+startIndex;
					resultSet = queryState.executeQuery(pageSql);
					while(resultSet.next()){
						pStatement.setString(1,resultSet.getObject(prColumn)==null?"":resultSet.getString(prColumn));
						pStatement.setString(2,resultSet.getObject(chColumn)==null?"":resultSet.getString(chColumn));
						if("IS_A_NULL".equals(checkType) || "IS_A_NOT_UNIQUE".equals(checkType)){
							pStatement.setString(3,"IS_A_FATAL");
						}else if("IS_Z_NULL".equals(checkType) || "IS_Z_NOT_UNIQUE".equals(checkType)){
							pStatement.setString(3,"IS_Z_FATAL");
						}
						pStatement.setString(4,probDesc);
						pStatement.setInt(5,newMonitorCode);
						pStatement.setString(6,resultSet.getObject("DQM_CITY_NAME")==null?"":resultSet.getString("DQM_CITY_NAME"));
						pStatement.addBatch();
						problemCount++;
						/*
						 * 	按照地市进行分流.
						 * 
						 * */
						if(cityCountMap.keySet().size()!=0 && cityCountMap.containsKey(resultSet.getString("DQM_CITY_NAME")) && resultSet.getObject("DQM_CITY_NAME")!=null){
							cityCountMap.put(resultSet.getString("DQM_CITY_NAME"),Integer.parseInt(cityCountMap.get(resultSet.getString("DQM_CITY_NAME")).toString())+1);
						}
					}
					pStatement.executeBatch();
					connection.commit();
					pStatement.clearBatch();
				}
				if(pStatement!=null){
					pStatement.close();
				}
				if(connection!=null){
					connection.close();
				}
			}
			/*
			 * 	保存地市统计信息.
			 * 
			 * */
			if(cityCountMap!=null && cityCountMap.keySet().size()>0){
				String recordType = "";
				if("IS_A_NULL".equals(checkType) || "IS_A_NOT_UNIQUE".equals(checkType)){
					recordType = "IS_A_FATAL";
				}else if("IS_Z_NULL".equals(checkType) || "IS_Z_NOT_UNIQUE".equals(checkType)){
					recordType = "IS_Z_FATAL";
				}
				if(!"".equals(recordType)){
					Iterator<String> car = cityCountMap.keySet().iterator();
					while(car.hasNext()){
						String CITY_NAME = car.next();
						String FATAL_COUNT = cityCountMap.get(CITY_NAME).toString();
						List<Map<String,Object>> recordedItems = jdbcTemplate.queryForList("SELECT * FROM COMPARE_CITY_COUNT WHERE MONITOR_ID = "+newMonitorCode+" AND FATAL_TYPE = '"+recordType+"' AND CITY_NAME = '"+CITY_NAME+"'");
						if(recordedItems.size()==0){
							jdbcTemplate.execute("INSERT INTO COMPARE_CITY_COUNT(ID,MONITOR_ID,FATAL_TYPE,CITY_NAME,FATAL_COUNT)VALUES(SEQ_COMPARE_CITY_COUNT.NEXTVAL,"+newMonitorCode+",'"+recordType+"','"+CITY_NAME+"',"+FATAL_COUNT+")");
						}else{
							Integer currentCount = Integer.parseInt(recordedItems.get(0).get("FATAL_COUNT").toString());
							Integer newCount = currentCount+Integer.parseInt(FATAL_COUNT);
							jdbcTemplate.execute("UPDATE COMPARE_CITY_COUNT SET FATAL_COUNT = "+newCount+" WHERE ID = "+recordedItems.get(0).get("ID").toString());
						}
					}
				}
			}
			return problemCount;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}finally{
			if(resultSet!=null){
				resultSet.close();
			}
			if(queryState!=null){
				queryState.close();
			}
			if(connByModelId!=null){
				connByModelId.close();
			}
		}
	}
	
	/*
	 * 	根据模型编号获取数据库连接.
	 * 
	 * */
	
	@Autowired
	private BasicDbService basicDbService;
	
	@Autowired
	private BasicDbTableService basicDbTableService;
	
	@Autowired
	private BasicColumnService basicColumnService;
	
	public Connection getDbConnection(Integer modelId)throws Exception{
		Connection connection = null;
		BasicDb basicDb = basicDbService.selectModel(
			basicDbTableService.selectModel(modelId).getBELONG_DB()
		);
		if("Oracle".equals(basicDb.getDB_TYPE())){
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@"+basicDb.getIP_ADDRESS()+":"+basicDb.getPORT()+":"+basicDb.getSID()+"",basicDb.getUSER_NAME(),basicDb.getUSER_PASS());
		}else{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://"+basicDb.getIP_ADDRESS()+":"+basicDb.getPORT()+"/"+basicDb.getSID()+"",basicDb.getUSER_NAME(),basicDb.getUSER_PASS());
		}
		return connection;
	}
	
	public Boolean isSameDb(Integer aModelId,Integer bModelId)throws Exception{
		BasicDb aDb = basicDbService.selectModel(basicDbTableService.selectModel(aModelId).getBELONG_DB());
		BasicDb bDb = basicDbService.selectModel(basicDbTableService.selectModel(bModelId).getBELONG_DB());
		if(aDb.getIP_ADDRESS().equals(bDb.getIP_ADDRESS()) && aDb.getPORT().equals(bDb.getPORT()) && aDb.getSID().equals(bDb.getSID()) && aDb.getUSER_NAME().equals(bDb.getUSER_NAME())){
			return true;
		}else{
			return false;
		}
	}
}
