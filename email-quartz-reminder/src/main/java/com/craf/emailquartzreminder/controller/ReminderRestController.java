package com.craf.emailquartzreminder.controller;

import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.craf.emailquartzreminder.entity.Reminder;
import com.craf.emailquartzreminder.service.QrtzScheduleService;

@RestController
@RequestMapping("/reminders/v1")
public class ReminderRestController {
	
	//private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired QrtzScheduleService service;
	
	@RequestMapping(value="/{userId}/{reminderId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Reminder getReminder(@PathVariable("userId") String userId, @PathVariable("reminderId") String reminderId) throws SchedulerException {
		return service.getReminder(userId, reminderId);
	}
	
	@RequestMapping(value="/{userId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Reminder> getAllReminders(@PathVariable("userId") String userId) throws SchedulerException {
		return service.getAllReminders(userId);
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Reminder> getAllRemindersAllUsers() throws SchedulerException {
		return service.getAllRemindersAllUsers();
	}
	
	@RequestMapping(value="/{userId}", method= RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody String schedule(@PathVariable("userId") String userId, @RequestBody Reminder reminder) throws SchedulerException{
		return service.schedule(userId,reminder);
	}
	
	@RequestMapping(value="/{userId}/{reminderId}", method= RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean unschedule(@PathVariable("userId") String userId, @PathVariable("reminderId") String reminderId) throws SchedulerException {
		return service.unschedule(userId, reminderId);
	}
}
