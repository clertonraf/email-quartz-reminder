package com.craf.emailquartzreminder.service;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.DateBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Service;

import com.craf.emailquartzreminder.entity.Reminder;
import com.craf.emailquartzreminder.job.SendEmailJob;

@Service
public class QrtzSchedulerService {

	@Autowired SpringBeanJobFactory jobFactory;
	
	public void schedule(String userId, Reminder reminder) throws SchedulerException {
		JobDetail job = newJob()
				.ofType(SendEmailJob.class)
				.storeDurably()
				.withIdentity(JobKey.createUniqueName(userId), userId)
				.withDescription("Invoke Sample Job service...")
				.build();
		
		Trigger trigger = newTrigger()
				.startAt(DateBuilder.dateOf(reminder.getHour(), 
											reminder.getMinute(), 
											0, 
											reminder.getDay(), 
											reminder.getMonth(), 
											reminder.getYear()))
				.forJob(job)
				.withDescription("Sample trigger")
				.withSchedule(simpleSchedule().withMisfireHandlingInstructionFireNow())
				.build();
		
		StdSchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = factory.getScheduler();
		scheduler.setJobFactory(jobFactory);
		scheduler.scheduleJob(job, trigger);
		
		scheduler.start();
	}
	
}