package com.quartz.job;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Repository;
import com.function.index.service.IndexAuditService;
import com.quartz.util.ApplicationUtil;
@Repository("indexScoreJob")
public class IndexScoreJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		IndexAuditService indexAuditService = (IndexAuditService)ApplicationUtil.getBean("indexAuditService");
		if(indexAuditService!=null){
			try{				
				indexAuditService.auditScore();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
