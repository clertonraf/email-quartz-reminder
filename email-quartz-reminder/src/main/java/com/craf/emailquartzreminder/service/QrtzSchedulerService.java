package com.craf.emailquartzreminder.service;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDataMap;
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
import com.craf.emailquartzreminder.util.ReminderUtil;

@Service
public class QrtzSchedulerService {

	@Autowired SpringBeanJobFactory jobFactory;
	
	public String schedule(String userId, Reminder reminder) throws SchedulerException {
		
		String jobId = JobKey.createUniqueName(userId);
		
		JobDetail job = newJob()
				.ofType(SendEmailJob.class)
				.storeDurably()
				.withIdentity(jobId, userId)
				.withDescription(reminder.getEventName())
				.usingJobData(new JobDataMap(ReminderUtil.convertEntityToMap(reminder)))
				.build();
		
		Trigger trigger = newTrigger()
				.startAt(ReminderUtil.calculateTriggerDate(reminder))
				.forJob(job)
				.withDescription(reminder.getEventName())
				.withSchedule(simpleSchedule().withMisfireHandlingInstructionFireNow())
				.withIdentity(jobId, userId)
				.build();
		
		StdSchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = factory.getScheduler();
		scheduler.setJobFactory(jobFactory);
		scheduler.scheduleJob(job, trigger);
		
		scheduler.start();
		
		return jobId;
	}
	
}