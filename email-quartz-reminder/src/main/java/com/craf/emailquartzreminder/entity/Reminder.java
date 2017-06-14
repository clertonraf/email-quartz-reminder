package com.craf.emailquartzreminder.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Reminder {

	private String reminderId;
	@NotNull @Max(23) @Min(0) private int hour;
	@NotNull @Max(59) @Min(0) private int minute;
	@NotNull @Max(31) @Min(1) private int day;
	@NotNull @Max(12) @Min(1) private int month;
	@NotNull @Max(3000) @Min(2017) private int year;
	@NotNull @Max(99) @Min(0) private int interval;
	private String emailDestination;
	private String eventName;
	private String eventLink;
	private Unit unit;
	private String userId;
	
	public String getReminderId() {
		return reminderId;
	}
	public void setReminderId(String reminderId) {
		this.reminderId = reminderId;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public String getEmailDestination() {
		return emailDestination;
	}
	public void setEmailDestination(String emailDestination) {
		this.emailDestination = emailDestination;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventLink() {
		return eventLink;
	}
	public void setEventLink(String eventLink) {
		this.eventLink = eventLink;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Reminder [reminderId=" + reminderId + ", hour=" + hour + ", minute=" + minute + ", day=" + day
				+ ", month=" + month + ", year=" + year + ", interval=" + interval + ", emailDestination="
				+ emailDestination + ", eventName=" + eventName + ", eventLink=" + eventLink + ", unit=" + unit
				+ ", userId=" + userId + "]";
	}	
}
