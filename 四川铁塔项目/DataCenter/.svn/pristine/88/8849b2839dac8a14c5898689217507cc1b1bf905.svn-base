package com.quartz.job;
import java.util.List;
import java.util.Map;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.function.rules.service.ReportBuilder;
import com.quartz.util.ApplicationUtil;
@Repository("createFileJob")
public class CreateFileJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException{
		Object jdbcObject = ApplicationUtil.getBean("jdbcTemplate");
		if(jdbcObject!=null){
			JdbcTemplate jdbcTool = (JdbcTemplate)jdbcObject;			
			List<Map<String,Object>> maps = jdbcTool.queryForList("SELECT ID AS RULE_ID FROM RULE_DETAIL WHERE RULE_STATE = 'Y' AND FILE_REPORT IS NULL AND ID IN(SELECT RULE_ID FROM RULE_JOB WHERE IS_FINISHED = 'Y' AND FATUAL_TOTAL > 0 )");
			if(maps.size()>0){
				Object createrObject = ApplicationUtil.getBean("reportBuilder");
				if(createrObject!=null){
					ReportBuilder reportCreater = (ReportBuilder)createrObject;
					for(int i=0;i<maps.size();i++){
						try{
							reportCreater.createReport(Integer.parseInt(maps.get(i).get("RULE_ID").toString()));
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}				
			}		
		}else{
			System.out.println("Hi~");
		}
	}
}
