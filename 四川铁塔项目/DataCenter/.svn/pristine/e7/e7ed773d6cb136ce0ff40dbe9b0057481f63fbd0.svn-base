package com.function.index.service;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.function.index.model.IndexScore;
@Repository("indexAuditService")
public class IndexAuditService{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private IndexScoreService indexScoreService;
	
	/*
	 * 	执行算分.
	 * 
	 * */
	public void auditScore(){
		/*获取具备算分资格的指标（INDEX_DETAIL）*/
		String sql = "";
		sql+="SELECT * FROM INDEX_DETAIL WHERE IS_USING = 'Y' AND ID IN(";
		sql+="	  SELECT T.INDEX_ID FROM(";
		sql+="		  SELECT INDEX_ID,SUM(COUNT_RATIO) AS TOTAL_RATIO FROM INDEX_ITEMS GROUP BY INDEX_ID";
		sql+="	  ) T WHERE T.TOTAL_RATIO = 100";
		sql+=")";
		List<Map<String,Object>> indexList = jdbcTemplate.queryForList(sql);
		if(indexList.size()>0){
			/*遍历指标列表*/
			for(int a=0;a<indexList.size();a++){
				Map<String,Object> indexDetail = indexList.get(a);
				/*创建指标得分*/
				IndexScore indexScore = new IndexScore();
				indexScore.setINDEX_ID(Integer.parseInt(indexDetail.get("ID").toString()));
				indexScore.setLAST_AUDIT_DATE(new Date());
				/*获取指标包含的规则列表*/
				String ruleSql = "";
				ruleSql+="SELECT ID AS RULE_ID,RULE_NAME,RULE_STATE FROM RULE_DETAIL WHERE ID IN(";
				ruleSql+="	  SELECT RULE_ID FROM INDEX_ITEMS WHERE INDEX_ID = "+indexDetail.get("ID").toString();
				ruleSql+=")";
				List<Map<String,Object>> ruleList = jdbcTemplate.queryForList(ruleSql);
				if(ruleList.size()>0){
					/*
					 * 	规则是否全部在用.
					 * 
					 * */
					Boolean isAllUsing = true;
					for(int b=0;b<ruleList.size();b++){
						Map<String,Object> ruleObject = ruleList.get(b);
						if("N".equals(ruleObject.get("RULE_STATE").toString())){
							isAllUsing = false;
							break;
						}
					}
					if(isAllUsing){
						/*
						 * 	再查看规则是否已经全部有得分.
						 * 
						 * */
						Boolean isAllScored = true;
						for(int c=0;c<ruleList.size();c++){
							Integer jobCount = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM RULE_JOB WHERE RULE_ID = "+ruleList.get(c).get("RULE_ID").toString()+" AND IS_FINISHED = 'Y'");
							if(jobCount==0){
								isAllScored = false;
								break;
							}
						}
						if(isAllScored){
							/*指标包含的规则都在用、并且都已核查出结果[包含得分情况]，则根据权重计算得分.*/
							Double finalScore = 0.0;
							for(int d=0;d<ruleList.size();d++){
								Map<String,Object> scoreObject = jdbcTemplate.queryForMap("SELECT A.RULE_ID,B.NORMAL_RATIO AS SCORE,A.COUNT_RATIO FROM INDEX_ITEMS A,RULE_JOB B WHERE A.RULE_ID = "+ruleList.get(d).get("RULE_ID").toString()+" AND A.RULE_ID = B.RULE_ID");
								finalScore += Double.parseDouble(scoreObject.get("SCORE").toString())/100*Double.parseDouble(scoreObject.get("COUNT_RATIO").toString());
							}
							DecimalFormat decimalFormat = new DecimalFormat("#.0");
							indexScore.setIS_OK("Y");
							indexScore.setFINAL_SCORE(Double.parseDouble(decimalFormat.format(finalScore)));
							/*先删除，再插入新数据*/
							jdbcTemplate.execute("DELETE FROM INDEX_SCORE WHERE INDEX_ID = "+indexDetail.get("ID").toString());
							indexScoreService.insertModel(indexScore);
						}else{
							indexScore.setIS_OK("N");
							indexScore.setACTION_DESCRIBE("存在尚未核查的规则.无法计算总分.");
							indexScoreService.insertModel(indexScore);
						}
					}else{
						indexScore.setIS_OK("N");
						indexScore.setACTION_DESCRIBE("存在停用的规则.无法计算总分.");
						indexScoreService.insertModel(indexScore);
					}
				}
			}
		}
	}
}