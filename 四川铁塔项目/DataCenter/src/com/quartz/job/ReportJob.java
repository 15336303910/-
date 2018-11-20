package com.quartz.job;
import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Repository;

import com.function.rules.service.TaskBuilder;
import com.quartz.util.ApplicationUtil;
@Repository("reportJob")
public class ReportJob implements Job{
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException{
		Object ruleObject = context.getJobDetail().getJobDataMap().get("ruleCode");				
		if(ruleObject!=null){
			TaskBuilder taskBuilder = (TaskBuilder)ApplicationUtil.getBean("taskBuilder");
			if(taskBuilder!=null){
				try{				
					taskBuilder.buildCheck(null,Integer.parseInt(ruleObject.toString()),getToken());
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}else{
			System.out.println("Hi~");
		}
	}
	
	public String getToken(){
		String jobToken = "";
		List<String> pool = new ArrayList<String>();
		pool.add("A");
		pool.add("B");
		pool.add("C");
		pool.add("D");
		pool.add("E");
		pool.add("F");
		pool.add("G");
		pool.add("H");
		pool.add("I");
		pool.add("J");
		pool.add("K");
		pool.add("L");
		pool.add("M");
		pool.add("N");
		pool.add("O");
		pool.add("P");
		pool.add("Q");
		pool.add("R");
		pool.add("S");
		pool.add("T");
		pool.add("U");
		pool.add("V");
		pool.add("W");
		pool.add("X");
		pool.add("Y");
		pool.add("Z");
		while(jobToken.length()<=30){
			Double randomDouble = Math.random()*25;
			Integer randomIndex = randomDouble.intValue();
			if(randomIndex>25){
				randomIndex = 25;
			}
			jobToken+=pool.get(randomIndex);
		}
		return jobToken;
	}
}
