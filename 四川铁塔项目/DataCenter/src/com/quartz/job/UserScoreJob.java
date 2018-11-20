package com.quartz.job;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.function.index.service.UserAuditService;
import com.quartz.util.ApplicationUtil;
public class UserScoreJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		UserAuditService userAuditService = (UserAuditService)ApplicationUtil.getBean("userAuditService");
		if(userAuditService!=null){
			try{				
				userAuditService.auditScore();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}