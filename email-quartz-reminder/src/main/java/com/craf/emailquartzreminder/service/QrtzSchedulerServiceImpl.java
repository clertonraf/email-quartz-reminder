package com.craf.emailquartzreminder.service;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Service;

import com.craf.emailquartzreminder.entity.Reminder;
import com.craf.emailquartzreminder.job.SendEmailJob;
import com.craf.emailquartzreminder.util.ReminderUtil;

@Service
public class QrtzSchedulerServiceImpl implements QrtzScheduleService {

	@Autowired SpringBeanJobFactory jobFactory;

	/* (non-Javadoc)
	 * @see com.craf.emailquartzreminder.service.QrtzScheduleService#schedule(java.lang.String, com.craf.emailquartzreminder.entity.Reminder)
	 */
	@Override
	public String schedule(String userId, Reminder reminder) throws SchedulerException {

		String jobId = JobKey.createUniqueName(userId);

		JobDetail job = newJob()
				.storeDurably(false)
				.requestRecovery(true)
				.ofType(SendEmailJob.class)
				.withIdentity(jobId, userId)
				.withDescription(reminder.getEventName())
				.usingJobData(new JobDataMap(ReminderUtil.convertEntityToMap(reminder)))
				.usingJobData("userId", userId)
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
	
	/* (non-Javadoc)
	 * @see com.craf.emailquartzreminder.service.QrtzScheduleService#unschedule(java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean unschedule(String userId, String reminderId) throws SchedulerException {
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		for(String groupName : scheduler.getJobGroupNames()) {
			for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
				if(scheduler.getJobDetail(jobKey).getKey().getGroup().equalsIgnoreCase(userId) && 
						scheduler.getJobDetail(jobKey).getKey().getName().equalsIgnoreCase(reminderId)) {
					
					List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
					
					return triggers.isEmpty() ? scheduler.deleteJob(jobKey) : scheduler.unscheduleJob(triggers.get(0).getKey()) && scheduler.deleteJob(jobKey);
					
				}
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.craf.emailquartzreminder.service.QrtzScheduleService#getReminder(java.lang.String, java.lang.String)
	 */
	@Override
	public Reminder getReminder(String userId, String reminderId) throws SchedulerException {
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		for(String groupName : scheduler.getJobGroupNames()) {
			for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
				if(scheduler.getJobDetail(jobKey).getKey().getGroup().equalsIgnoreCase(userId) && 
						scheduler.getJobDetail(jobKey).getKey().getName().equalsIgnoreCase(reminderId)) {
					return ReminderUtil.convertMapToEntity(scheduler, jobKey);
				}
			}
		}
		return new Reminder();

	}


	/* (non-Javadoc)
	 * @see com.craf.emailquartzreminder.service.QrtzScheduleService#getAllReminders(java.lang.String)
	 */
	@Override
	public List<Reminder> getAllReminders(String userId) throws SchedulerException {
		List<Reminder> reminderLst = new ArrayList<Reminder>();
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		for(String groupName : scheduler.getJobGroupNames()) {
			for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
				if(scheduler.getJobDetail(jobKey).getKey().getGroup().equalsIgnoreCase(userId)) {
					reminderLst.add(ReminderUtil.convertMapToEntity(scheduler, jobKey));
				}
			}
		}
		return reminderLst;

	}

	/* (non-Javadoc)
	 * @see com.craf.emailquartzreminder.service.QrtzScheduleService#getAllRemindersAllUsers()
	 */
	@Override
	public List<Reminder> getAllRemindersAllUsers() throws SchedulerException {
		List<Reminder> reminderLst = new ArrayList<Reminder>();
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		for(String groupName : scheduler.getJobGroupNames()) {
			for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
				reminderLst.add(ReminderUtil.convertMapToEntity(scheduler, jobKey));
			}
		}
		return reminderLst;
	}
	
	/* (non-Javadoc)
	 * @see com.craf.emailquartzreminder.service.QrtzScheduleService#updateReminder(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateReminder(String userId, String reminderId) throws SchedulerException {
		//TODO
		return false;
	}
}