package com.patdivillyfitness.runcoach.service;

import javax.mail.internet.InternetAddress;

public interface EmailService {
	
	public boolean sendPlainMail(String body, InternetAddress toAdd, String subject );
	
}