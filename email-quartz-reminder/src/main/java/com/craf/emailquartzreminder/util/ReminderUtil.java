package com.craf.emailquartzreminder.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.craf.emailquartzreminder.entity.Reminder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReminderUtil {
	
	public static Date calculateTriggerDate(Reminder reminder) {
		LocalDateTime reminderDate = LocalDateTime.of(reminder.getYear(), reminder.getMonth(), reminder.getDay(), reminder.getHour(), reminder.getMinute());
		
		LocalDateTime triggerDate;
		switch(reminder.getUnit()) {
			case m:
				triggerDate = reminderDate.minusMinutes(reminder.getInterval());
				break;
			case h:
				triggerDate = reminderDate.minusHours(reminder.getInterval());
				break;
			case d:
				triggerDate = reminderDate.minusDays(reminder.getInterval());
				break;
			case w:
				triggerDate = reminderDate.minusWeeks(reminder.getInterval());
				break;
			case M:
				triggerDate = reminderDate.minusMonths(reminder.getInterval());
				break;
			default:
				triggerDate = LocalDateTime.now();
				break;
		}
		
		return Date.from(triggerDate.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static Map<String,Object> convertEntityToMap(Reminder reminder) {
		return new ObjectMapper().convertValue(reminder,new TypeReference<Map<String,Object>>(){});
	}
	
	public static Reminder convertMapToEntity(Scheduler scheduler, JobKey jobKey) throws SchedulerException {
		Reminder reminder = new ObjectMapper().convertValue(scheduler.getJobDetail(jobKey).getJobDataMap(),Reminder.class);
		reminder.setReminderId(scheduler.getJobDetail(jobKey).getKey().getName());
		return reminder;
	}

}
