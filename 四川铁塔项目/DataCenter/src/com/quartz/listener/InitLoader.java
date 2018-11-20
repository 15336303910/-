package com.quartz.listener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMISocketFactory;
import java.util.List;
import java.util.Map;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import com.quartz.job.Authority;
import com.quartz.job.QuartzChartJob;
import com.quartz.job.ReportJob;
import com.quartz.util.FactoryUtil;
import com.rmi.inters.IService;
import com.rmi.service.IServiceImpl;
public class InitLoader implements ApplicationListener<ContextRefreshedEvent>{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static String JOB_GROUP_NAME = "JOB_GROUP";
	
	private static String TRIGGER_GROUP_NAME = "TRIGGER_GROUP";

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event){
		try{
			SchedulerFactory factory = FactoryUtil.getSchedulerFactory();
	        Scheduler scheduer = factory.getScheduler();
			if(event.getApplicationContext().getParent() == null){
				/*
		         * 	添加数据清洗调度任务.
		         * 
		         * */
				System.out.println("DQM: ======================Init task begin.==========================");
				List<Map<String,Object>> ruleDetails = jdbcTemplate.queryForList("SELECT * FROM RULE_DETAIL WHERE RULE_STATE = 'Y' and CON_EXPRESSION != '-'");		        
		        if(ruleDetails.size()>0){
		        	Map<String,Object> ruleDetail = null;
		        	for(int i=0;i<ruleDetails.size();i++){
		        		ruleDetail = ruleDetails.get(i);
						//JobDetail jobDetail = new JobDetail("JobName"+ruleDetail.get("ID").toString(),JOB_GROUP_NAME,ReportJob.class);
						//jobDetail.getJobDataMap().put("ruleCode",ruleDetail.get("ID").toString());
						//CronTrigger cronTrigger = new CronTrigger("TriggerName"+ruleDetail.get("ID").toString(),TRIGGER_GROUP_NAME);
				        //cronTrigger.setCronExpression(new CronExpression(ruleDetail.get("CON_EXPRESSION").toString()));
				        //scheduer.scheduleJob(jobDetail,cronTrigger);
				        //System.out.println("DQM: Add data clean quartz job successfully:"+ruleDetail.get("ID").toString()+".");
			      }       
		        }
		        /*同步权限数据.*/
		        //JobDetail authorityJob = new JobDetail("Authority","Authoritys",Authority.class);
		        //CronTrigger authorityTrigger = new CronTrigger("AuthorityTrigger","AuthorityTriggerClass");
		        //authorityTrigger.setCronExpression(new CronExpression("0 0/5 * * * ?"));
		        //authorityTrigger.setCronExpression(new CronExpression("0 0/30 * * * ?"));
		        //authorityTrigger.setCronExpression(new CronExpression("0 0 */12 * * ?"));
		        //scheduer.scheduleJob(authorityJob,authorityTrigger);
		        //System.out.println("DQM: QXGL System Data Synchronized Quartz Job Start Successfully.");
		        /*
		         * 	同步首页报表数据.
		         * 
		         * */
		        //JobDetail chartJob = new JobDetail("ChartData","ChartDatas",QuartzChartJob.class);
		        //CronTrigger chartTrigger = new CronTrigger("ChartTrigger","ChartTriggerClass");
		        //chartTrigger.setCronExpression(new CronExpression("0 0 */12 * * ?"));
		        //scheduer.scheduleJob(chartJob,chartTrigger);
		        //System.out.println("DQM: Chart Data Start Successfully.");
		        /*
		         * 	启动调度任务.
		         * 
		         * */
		        if(!scheduer.isShutdown()){
		        	scheduer.start();
				}
		        /*
		         * 	注册RMI服务.
		         * 
		         * */
		        final Integer registerPort = Integer.parseInt(jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'registerPort'").get("PRO_VALUE").toString());
	        	final Integer serverPort = Integer.parseInt(jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'serverPort'").get("PRO_VALUE").toString());
	        	LocateRegistry.createRegistry(registerPort);
	        	RMISocketFactory.setSocketFactory(new RMISocketFactory(){						
	        		@Override
					public Socket createSocket(String host,int port) throws IOException{
						return new Socket(host,port);
					}
					@Override
					public ServerSocket createServerSocket(int port) throws IOException {
						if(port==0){
							port = serverPort;
						}
						return new ServerSocket(port);
					}
				});
				IService iServiceImpl = new IServiceImpl("iServiceImpl");
				Naming.rebind("rmi://localhost:"+Integer.parseInt(jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'registerPort'").get("PRO_VALUE").toString())+"/iServiceImpl",iServiceImpl);
				System.out.println("DQM: RMI Service Register Successfully.");
				System.out.println("DQM: ================================================================");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
