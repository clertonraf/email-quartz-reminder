package com.craf.emailquartzreminder.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reminders/v1")
public class ReminderRestController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/{userId}/{reminderId}", method= RequestMethod.GET)
	public String getReminder() {
		logger.debug("debugging");
		return "ok";
	}
}
