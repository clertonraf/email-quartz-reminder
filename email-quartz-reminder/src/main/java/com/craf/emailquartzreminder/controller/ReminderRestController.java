package com.craf.emailquartzreminder.controller;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.craf.emailquartzreminder.entity.Reminder;
import com.craf.emailquartzreminder.entity.Unit;
import com.craf.emailquartzreminder.service.QrtzSchedulerService;

@RestController
@RequestMapping("/reminders/v1")
public class ReminderRestController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired QrtzSchedulerService service;
	
	@RequestMapping(value="/{userId}/{reminderId}", 
			method=RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public Reminder getReminder() {
		logger.debug("debugging");
		Reminder reminder = new Reminder();
		reminder.setUnit(Unit.m);
		reminder.setInterval(1);
		reminder.setDay(13);
		reminder.setMonth(6);
		reminder.setYear(2017);
		reminder.setHour(22);
		reminder.setMinute(20);
		reminder.setEventName("Event");
		reminder.setEventLink("http://www.google.com");
		reminder.setEmailDestination("clertonfilho@gmail.com");
		
		return reminder;
	}
	
	@RequestMapping(value="/{userId}", method= RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public String schedule(@PathVariable("userId") String userId, @RequestBody Reminder reminder) throws SchedulerException {
		service.schedule(userId,reminder);
		return "ok";
	}
	
	@RequestMapping(value="/{userId}/{reminderId}", method= RequestMethod.DELETE)
	public String unschedule() {
		return "ok";
	}
}
