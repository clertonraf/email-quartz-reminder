package com.craf.emailquartzreminder.service;

import java.util.List;

import org.quartz.SchedulerException;

import com.craf.emailquartzreminder.entity.Reminder;

public interface QrtzScheduleService {

	String schedule(String userId, Reminder reminder) throws SchedulerException;

	boolean unschedule(String userId, String reminderId) throws SchedulerException;
	
	boolean unscheduleAll() throws SchedulerException;

	Reminder getReminder(String userId, String reminderId) throws SchedulerException;

	List<Reminder> getAllReminders(String userId) throws SchedulerException;

	List<Reminder> getAllRemindersAllUsers() throws SchedulerException;

	boolean updateReminder(String userId, Reminder reminder) throws SchedulerException;

}