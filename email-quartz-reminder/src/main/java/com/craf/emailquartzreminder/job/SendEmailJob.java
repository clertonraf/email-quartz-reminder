package com.craf.emailquartzreminder.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SendEmailJob implements Job{

	@Autowired
    public JavaMailSender emailSender;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("A job given is a job done");
		try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("clertonfilho@gmail.com");
            message.setSubject("teste");
            message.setText("test");

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
	}

}
