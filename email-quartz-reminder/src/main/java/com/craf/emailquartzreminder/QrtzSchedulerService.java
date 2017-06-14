package com.craf.emailquartzreminder;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Service;

import com.craf.emailquartzreminder.job.SendEmailJob;

@Service
public class QrtzSchedulerService {

	@Autowired SpringBeanJobFactory jobFactory;
	
	public void schedule() throws SchedulerException {
		JobDetail job = newJob()
				.ofType(SendEmailJob.class)
				.storeDurably()
				.withIdentity(JobKey.jobKey("Qrtz_Job_Detail"))
				.withDescription("Invoke Sample Job service...")
				.build();
		Trigger trigger = newTrigger()
				.forJob(job)
				.withIdentity(TriggerKey.triggerKey("Qrtz_Trigger"))
				.withDescription("Sample trigger")
				.withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever())
				.build();
		
		StdSchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = factory.getScheduler();
		scheduler.setJobFactory(jobFactory);
		scheduler.scheduleJob(job, trigger);
		
		scheduler.start();
		
	}
	
}
