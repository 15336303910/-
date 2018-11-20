package com.function.index.service;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.function.index.model.UserScore;
@Repository("userAuditService")
public class UserAuditService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserScoreService userScoreService;
	
	/*
	 * 	执行算分.
	 * 
	 * */
	public void auditScore(){
		/*获取具备算分条件的用户*/
		String sql = "";
		sql+="SELECT T.USER_ID FROM (";
		sql+="	  SELECT USER_ID,SUM(COUNT_RATIO) AS TOTAL_RATIO FROM INDEX_USER GROUP BY USER_ID";
		sql+=") T WHERE T.TOTAL_RATIO = 100";
		List<Map<String,Object>> userIdList = jdbcTemplate.queryForList(sql);
		if(userIdList.size()>0){
			/*遍历用户列表*/
			for(int a=0;a<userIdList.size();a++){
				Map<String,Object> userDetail = userIdList.get(a);
				Integer userCode = Integer.parseInt(userDetail.get("USER_ID").toString());
				/*创建用户得分*/
				UserScore userScore = new UserScore();
				userScore.setUSER_ID(userCode);
				userScore.setLAST_AUDIT_DATE(new Date());
				userScore.setOH_MY_GOD(0);
				/*获取用户包含的指标列表及权重*/
				List<Map<String,Object>> indexList = jdbcTemplate.queryForList("SELECT * FROM INDEX_USER WHERE USER_ID = "+userCode);
				if(indexList.size()>0){
					/*指标是否已全部算出总分.*/
					Boolean isAllAudit = true;
					for(int b=0;b<indexList.size();b++){
						Map<String,Object> indexObject = indexList.get(b);
						Integer isIndexAudit = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM INDEX_SCORE WHERE INDEX_ID = "+indexObject.get("INDEX_ID").toString()+" AND IS_OK = 'Y'");
						if(isIndexAudit!=1){
							isAllAudit = false;
							break;
						}
					}
					jdbcTemplate.execute("DELETE FROM USER_SCORE WHERE USER_ID = "+userCode);
					if(isAllAudit){
						Double finalScore = 0.0;
						for(int c=0;c<indexList.size();c++){
							Map<String,Object> indexObject = indexList.get(c);
							if(indexObject.get("COUNT_RATIO")!=null){
								Double countRatio = Double.parseDouble(indexObject.get("COUNT_RATIO").toString());
								List<Map<String,Object>> indexScores = jdbcTemplate.queryForList("SELECT * FROM INDEX_SCORE WHERE INDEX_ID = "+indexObject.get("INDEX_ID").toString());
								if(indexScores.size()>0){
									finalScore+=Double.parseDouble(indexScores.get(0).get("FINAL_SCORE").toString())/100*countRatio;
								}
							}
						}
						DecimalFormat decimalFormat = new DecimalFormat("#.0");
						userScore.setIS_OK("Y");
						userScore.setFINAL_SCORE(Double.parseDouble(decimalFormat.format(finalScore)));
						userScoreService.insertModel(userScore);
					}else{
						userScore.setIS_OK("N");
						userScore.setACTION_DESCRIBE("此用户下存在尚未计算得分的指标.无法计算用户得分.");
						userScoreService.insertModel(userScore);
					}
				}
			}
		}
	}
}
