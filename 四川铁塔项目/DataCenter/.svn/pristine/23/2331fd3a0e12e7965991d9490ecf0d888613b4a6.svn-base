package com.function.rules.service;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.springframework.stereotype.Repository;
import com.quartz.job.ReportJob;
import com.quartz.util.FactoryUtil;
@Repository("quartzService")
public class QuartzService{
	
	/*
	 * 	移除调度任务
	 * 
	 * */
	public void stopJob(Integer ruleDetailId)throws Exception{		
		SchedulerFactory factory = FactoryUtil.getSchedulerFactory();
		Scheduler sched = factory.getScheduler();
		/*1.1.停止触发器*/
		sched.pauseTrigger("TriggerName"+ruleDetailId,"TRIGGER_GROUP");
		/*1.2.移除触发器*/
		sched.unscheduleJob("TriggerName"+ruleDetailId,"TRIGGER_GROUP");
		/*1.3.删除任务*/
		sched.deleteJob("JobName"+ruleDetailId,"JOB_GROUP");
	}
	
	/*
	 * 	添加调度
	 * 
	 * */
	public void createJob(Integer ruleDetailId,String conExpression){
		try{
			SchedulerFactory factory = FactoryUtil.getSchedulerFactory();
	        Scheduler scheduer = factory.getScheduler();
	        /*
	         * 	JobDetail
	         * 	
	         * */
	        JobDetail jobDetail = new JobDetail("JobName"+ruleDetailId,"JOB_GROUP",ReportJob.class);
			jobDetail.getJobDataMap().put("ruleCode",ruleDetailId);
			/*
			 * 	CronTrigger
			 * 
			 * */
			CronTrigger cronTrigger = new CronTrigger("TriggerName"+ruleDetailId,"TRIGGER_GROUP");
	        cronTrigger.setCronExpression(new CronExpression(conExpression));
	        scheduer.scheduleJob(jobDetail,cronTrigger);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
