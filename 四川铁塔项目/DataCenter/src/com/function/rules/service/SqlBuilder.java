package com.function.rules.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.function.dbmanage.model.BasicColumn;
import com.function.dbmanage.model.BasicDbTable;
import com.function.dbmanage.service.BasicColumnService;
import com.function.dbmanage.service.BasicDbTableService;
import com.function.rules.model.CheckDetail;
import com.function.rules.model.RuleItem;
import com.function.rules.model.RuleItemConnect;
@Repository("sqlBuilder")
@Scope("prototype")
public class SqlBuilder{
	
	@Autowired
	private RuleConnectService ruleConnectService;
	
	@Autowired
	private BasicDbTableService basicDbTableService;
	
	@Autowired
	private BasicColumnService basicColumnService;
	
	CheckDetail detail = new CheckDetail();
	
	/*
	 * 	输入具体的规则，转化为对应的SQL. 找出问题数据.
	 * 	
	 * */
	public CheckDetail buildSql(String tableName,String prName,String cnName,String vaName,String exName,BasicColumn checkingColumn,RuleItem ruleItem,Boolean IS_PROVINCE,String ctColumn,String ctName){
		String isIgnoreNull = ruleItem.getIS_FILTER();
		String sql = "";
		if("基础规则核查".equals(ruleItem.getAUDIT_TYPE())){
			String checkExpress = ruleItem.getEXPRESSION();
			if(checkExpress.indexOf("~")!=-1){
				/*
				 * 	取值范围核查. ===================================================================================
				 * 
				 * */
				String[] args = checkExpress.split("~");
				sql+="SELECT "+prName+","+cnName+","+vaName+" AS "+exName+" FROM "+tableName+" WHERE "+vaName+" NOT BETWEEN "+args[0]+" AND "+args[1];
				if(!IS_PROVINCE && !"".equals(ctName)){
					sql+=" AND "+ctColumn+" LIKE '%"+ctName+"%'";
				}
				/*
				 * 	如果不忽略空值[未选定]，则为[不忽略空值]，即空值为异常.
				 * 
				 * */
				if("Y".equals(isIgnoreNull)){
					sql+=" AND "+vaName+" IS NOT NULL";
				}
				detail.setPROBLEM_DETAIL(checkingColumn.getCOLUMN_ALIAS()+"不在"+args[0]+"和"+args[1]+"之间；");
			}else if(checkExpress.indexOf(",")!=-1){
				/*
				 * 	简单枚举值核查. =================================================================================
				 * 
				 * */
				sql+="SELECT "+prName+","+cnName+","+vaName+" AS "+exName+" FROM "+tableName+" WHERE "+vaName+" NOT IN("+checkExpress+")";
				if(!IS_PROVINCE && !"".equals(ctName)){
					sql+=" AND "+ctColumn+" LIKE '%"+ctName+"%'";
				}
				/*
				 * 	如果不忽略空值[未选定]，则为[不忽略空值]，即空值为异常.
				 * 
				 * */
				if("N".equals(isIgnoreNull)){
					sql+=" OR "+vaName+" IS NULL";
				}
				detail.setPROBLEM_DETAIL(checkingColumn.getCOLUMN_ALIAS()+"不符合枚举值["+checkExpress+"]；");
			}else if(checkExpress.indexOf("NOT NULL")!=-1){
				/*
				 * 	非空校验. ======================================================================================
				 * 
				 * */
				sql+="SELECT "+prName+","+cnName+","+vaName+" AS "+exName+" FROM "+tableName+" WHERE "+vaName+" IS NULL";
				if(!IS_PROVINCE && !"".equals(ctName)){
					sql+=" AND "+ctColumn+" LIKE '%"+ctName+"%'";
				}
				detail.setPROBLEM_DETAIL(checkingColumn.getCOLUMN_ALIAS()+"的值为空；");
			}else if(checkExpress.indexOf("UNIQUE")!=-1){
				/*
				 * 	唯一性校验. =====================================================================================
				 * 
				 * */
				sql+="SELECT "+prName+","+cnName+","+vaName+" AS "+exName+" FROM "+tableName+" WHERE "+vaName+" IN(";
					sql+="SELECT T.COLUMN_NAME AS COLUMN_NAMES FROM (";
						sql+="SELECT "+vaName+" AS COLUMN_NAME,COUNT(1) AS TOTAL_COUNT FROM "+tableName;
						if("Y".equals(isIgnoreNull)){
							sql+=" WHERE "+vaName+" IS NOT NULL";
						}
						sql+=" GROUP BY "+vaName;				
					sql+=") T WHERE T.TOTAL_COUNT > 1";				
				sql+=")";
				if(!IS_PROVINCE && !"".equals(ctName)){
					sql+=" AND "+ctColumn+" LIKE '%"+ctName+"%'";
				}
				detail.setPROBLEM_DETAIL(checkingColumn.getCOLUMN_ALIAS()+"的值在全表数据中不唯一；");
			}else if(checkExpress.indexOf(">")!=-1 || checkExpress.indexOf(">=")!=-1 || checkExpress.indexOf("<")!=-1 || checkExpress.indexOf("<=")!=-1){
				/*
				 * 	取值必须在一定范围[数字范围：arg >= 150]. ==========================================================
				 * 
				 * */
				checkExpress = checkExpress.replace("arg","");
				sql+="SELECT "+prName+","+cnName+","+vaName+" AS "+exName+" FROM "+tableName+" WHERE "+vaName+" "+checkExpress;
				if(!IS_PROVINCE && !"".equals(ctName)){
					sql+=" AND "+ctColumn+" LIKE '%"+ctName+"%'";
				}
				/*
				 * 	如果不忽略空值[未选定]，则为[不忽略空值]，即空值为异常.
				 * 
				 * */
				if("Y".equals(isIgnoreNull)){
					sql+=" AND "+vaName+" IS NOT NULL";
				}else if("N".equals(isIgnoreNull)){
					sql+=" OR "+vaName+" IS NULL";
				}
				detail.setPROBLEM_DETAIL(checkingColumn.getCOLUMN_ALIAS()+"的值不在指定（"+checkExpress+"）范围；");
			}else{
				/*
				 * 	其他
				 * 
				 * */
				sql+="SELECT "+prName+","+cnName+","+vaName+" AS "+exName+" FROM "+tableName+" WHERE "+vaName+" NOT IN("+checkExpress+")";
				if(!IS_PROVINCE && !"".equals(ctName)){
					sql+=" AND "+ctColumn+" LIKE '%"+ctName+"%'";
				}
				detail.setPROBLEM_DETAIL(checkingColumn.getCOLUMN_ALIAS()+"不在指定的值范围；");
			}
		}else if("关联规则核查".equals(ruleItem.getAUDIT_TYPE())){
			if("核查枚举值".equals(ruleItem.getEXPRESSION())){
				List<RuleItemConnect> conns = ruleConnectService.selectModelsByHql("from RuleItemConnect where BELONG_ITEM = "+ruleItem.getID());
				if(conns.size()==0){
					/*
					 * 	1.在[无]关联键的情况下直接使用Distinct获取枚举值=================================================
					 * 
					 * */;
					sql+="SELECT "+prName+","+cnName+","+vaName+" AS "+exName+" FROM "+tableName+" WHERE "+vaName+" NOT IN (";
						sql+="SELECT DISTINCT("+basicColumnService.selectModel(ruleItem.getVALUE_COLUMN()).getCOLUMN_NAME()+") FROM "+basicDbTableService.selectModel(ruleItem.getVALUE_TABLE()).getTABLE_NAME();
					sql+=")";
					if(!IS_PROVINCE && !"".equals(ctName)){
						sql+=" AND "+ctColumn+" LIKE '%"+ctName+"%'";
					}
					if("Y".equals(isIgnoreNull)){
						sql+=" AND "+vaName+" IS NOT NULL";
					}
				}else if(conns.size()>0){
					/*
					 * 	2.在[有]关联键的情况下进行关联查询==============================================================
					 * 
					 * */
					BasicColumn aColumn = null;
					BasicColumn zColumn = null;
					sql+="SELECT "+prName+","+cnName+","+vaName+" AS "+exName+" FROM "+tableName+" A WHERE NOT EXISTS (";						
						sql+="SELECT 1 FROM "+basicDbTableService.selectModel(ruleItem.getVALUE_TABLE()).getTABLE_NAME()+" B WHERE A."+checkingColumn.getCOLUMN_NAME()+" = B."+basicColumnService.selectModel(ruleItem.getVALUE_COLUMN()).getCOLUMN_NAME()+"";
						for(int w=0;w<conns.size();w++){
							RuleItemConnect itemConnect = conns.get(w);
							aColumn = basicColumnService.selectModel(itemConnect.getCHECK_COLUMN_ID());
							zColumn = basicColumnService.selectModel(itemConnect.getGLASS_COLUMN_ID());				
							sql+=" AND A."+aColumn.getCOLUMN_NAME()+" = B."+zColumn.getCOLUMN_NAME();				
						}
					sql+=")";
					if(!IS_PROVINCE && !"".equals(ctName)){
						sql+=" AND "+ctColumn+" LIKE '%"+ctName+"%'";
					}
					if("Y".equals(isIgnoreNull)){
						sql+=" AND "+checkingColumn.getCOLUMN_NAME()+" IS NOT NULL";
					}
				}
				detail.setPROBLEM_DETAIL(checkingColumn.getCOLUMN_ALIAS()+"不符合枚举范围；");
			}else if("核查关联数据条数".equals(ruleItem.getEXPRESSION())){
				/*
				 * 	关联数量核查====================================================================================
				 * 
				 * */
				List<RuleItemConnect> conns = ruleConnectService.selectModelsByHql("from RuleItemConnect where BELONG_ITEM = "+ruleItem.getID());
				if(conns.size()>0){	
					BasicDbTable glassTable = basicDbTableService.selectModel(ruleItem.getVALUE_TABLE());					
					sql+="SELECT "+prName+","+cnName+","+vaName+" AS "+exName+" FROM (SELECT A.*,(SELECT COUNT(1) FROM "+glassTable.getTABLE_NAME()+" B WHERE 1 = 1";
					if(!IS_PROVINCE && !"".equals(ctName)){
						sql+=" AND B."+ctColumn+" LIKE '%"+ctName+"%' ";
					}
					BasicColumn aColumn = null;
					BasicColumn zColumn = null;					
					for(int i=0;i<conns.size();i++){
						RuleItemConnect connect = conns.get(i);
						aColumn = basicColumnService.selectModel(connect.getCHECK_COLUMN_ID());
						zColumn = basicColumnService.selectModel(connect.getGLASS_COLUMN_ID());					
						sql+=" AND A."+aColumn.getCOLUMN_NAME()+" = B."+zColumn.getCOLUMN_NAME();										
					}				
					sql+=") CONNECT_COUNT FROM "+tableName+" A) WHERE CONNECT_COUNT";	
					if(ruleItem.getMIN_NUMBER()==0 && ruleItem.getMAX_NUMBER()==0){
						sql+=" >= 1";
						detail.setPROBLEM_DETAIL("与表["+glassTable.getTABLE_ALIAS()+"]相关联的记录数量多于或等于1条；");
					}else if(ruleItem.getMIN_NUMBER()!=0 && ruleItem.getMAX_NUMBER()!=0){
						sql+=" NOT BETWEEN "+ruleItem.getMIN_NUMBER()+" AND "+ruleItem.getMAX_NUMBER();
						detail.setPROBLEM_DETAIL("与表["+glassTable.getTABLE_ALIAS()+"]相关联的记录数量不在"+ruleItem.getMIN_NUMBER()+"~"+ruleItem.getMAX_NUMBER()+"之间；");
					}else if(ruleItem.getMIN_NUMBER()!=0 && ruleItem.getMAX_NUMBER()==0){
						sql+=" < "+ruleItem.getMIN_NUMBER();
						detail.setPROBLEM_DETAIL("与表["+glassTable.getTABLE_ALIAS()+"]相关联的记录数量少于"+ruleItem.getMIN_NUMBER()+"条；");
					}else if(ruleItem.getMIN_NUMBER()==0 && ruleItem.getMAX_NUMBER()!=0){
						sql+=" > "+ruleItem.getMAX_NUMBER();
						detail.setPROBLEM_DETAIL("与表["+glassTable.getTABLE_ALIAS()+"]相关联的记录数量多于"+ruleItem.getMAX_NUMBER()+"条；");
					}
				}			
			}else if("多属性匹配".equals(ruleItem.getEXPRESSION())){				
				BasicDbTable valueTable = basicDbTableService.selectModel(ruleItem.getVALUE_TABLE());
				String valueTableName = valueTable.getTABLE_NAME();
				if("T".equals(ruleItem.getDIMENSION_TYPE())){
					/*
					 * 	表格级别的匹配：如果在表格的A_COLUMN、B_COLUMN ... 中未发现与[核查目标字段的值]匹配的值，则视为异常数据.
					 * 
					 * */
					String columnNames = "";
					if("OR".equals(ruleItem.getMATCH_TYPE())){
						/*
						 * 	Or
						 * 
						 * */
						sql+="SELECT "+prName+","+cnName+","+vaName+" AS "+exName+" FROM "+tableName+" WHERE ";
						if(!IS_PROVINCE && !"".equals(ctName)){
							sql+=ctColumn+" LIKE '%"+ctName+"%' AND ";
						}
						sql+=prName+" NOT IN(SELECT DISTINCT(A."+prName+") FROM "+tableName+" A,"+valueTableName+" B WHERE 1 = 1 ";						
						List<BasicColumn> matchColumns = basicColumnService.selectModelsByHql("from BasicColumn where ID in("+ruleItem.getCOLUMN_IDS()+")");
						if(matchColumns.size()>0){
							for(int i=0;i<matchColumns.size();i++){
								if("".equals(columnNames)){
									columnNames = matchColumns.get(i).getCOLUMN_ALIAS();
								}else{
									columnNames+= ","+matchColumns.get(i).getCOLUMN_ALIAS();
								}
								if(matchColumns.size()>1){
									if(i==0){
										sql+=" AND ( A."+vaName+" = B."+matchColumns.get(i).getCOLUMN_NAME();
									}else if(i==(matchColumns.size()-1)){
										sql+=" OR A."+vaName+" = B."+matchColumns.get(i).getCOLUMN_NAME()+")";
									}else{
										sql+=" OR A."+vaName+" = B."+matchColumns.get(i).getCOLUMN_NAME();
									}						
								}else if(matchColumns.size()==1){
									sql+=" AND A."+vaName+" = B."+matchColumns.get(i).getCOLUMN_NAME();
								}						
							}
						}					
						sql+=")";
					}else if("AND".equals(ruleItem.getMATCH_TYPE())){
						/*
						 * 	And
						 * 
						 * */
						sql+="SELECT "+prName+","+cnName+","+vaName+" AS "+exName+" FROM "+tableName+" WHERE ";
						if(!IS_PROVINCE && !"".equals(ctName)){
							sql+=ctColumn+" LIKE '%"+ctName+"%' AND ";
						} 
						sql+=prName+" NOT IN(SELECT DISTINCT(A."+prName+") FROM "+tableName+" A,"+valueTableName+" B WHERE 1 = 1 ";						
						List<BasicColumn> matchColumns = basicColumnService.selectModelsByHql("from BasicColumn where ID in("+ruleItem.getCOLUMN_IDS()+")");
						if(matchColumns.size()>0){
							for(int i=0;i<matchColumns.size();i++){
								if("".equals(columnNames)){
									columnNames = matchColumns.get(i).getCOLUMN_ALIAS();
								}else{
									columnNames+= ","+matchColumns.get(i).getCOLUMN_ALIAS();
								}
								sql+=" AND A."+vaName+" = B."+matchColumns.get(i).getCOLUMN_NAME();
							}
						}					
						sql+=")";
					}
					BasicDbTable glassTable = basicDbTableService.selectModel(ruleItem.getVALUE_TABLE());
					detail.setPROBLEM_DETAIL("["+checkingColumn.getCOLUMN_ALIAS()+"]在全表["+glassTable.getTABLE_ALIAS()+"]的["+columnNames+"]中未发现匹配的值；");
				}
			}
		}
		if(!"".equals(sql)){
			detail.setIS_SUPPORTED(true);
		}else{
			detail.setIS_SUPPORTED(false);
		}
		detail.setCOUNT_SQL("SELECT COUNT(1) AS ROW_COUNT FROM ("+sql+")");
		detail.setSQL("SELECT T.*,ROWNUM AS RN FROM("+sql+") T WHERE ROWNUM >= 0");
		return detail;
	}
}
