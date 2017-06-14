package com.craf.emailquartzreminder.job;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;

public class SendEmailJob implements Job{

	private @Autowired JavaMailSender emailSender;
	private @Autowired Configuration fmConfiguration;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		JobDataMap model = context.getJobDetail().getJobDataMap();
		
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		 
        try {
 
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
 
            mimeMessageHelper.setSubject(model.getString("eventName"));
            mimeMessageHelper.setTo(model.getString("emailDestination"));
            mimeMessageHelper.setText(getContentFromTemplate(model), true);
 
            emailSender.send(mimeMessageHelper.getMimeMessage());
    		
    		System.out.println("\"Mission given is mission done\"");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public String getContentFromTemplate(Map <String, Object> model) {
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils
                .processTemplateIntoString(fmConfiguration.getTemplate("email-template.ftl"), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

}
