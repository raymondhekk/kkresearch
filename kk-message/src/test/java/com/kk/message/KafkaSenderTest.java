package com.kk.message;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import junit.textui.TestRunner;


public class KafkaSenderTest extends AbstractDependencyInjectionSpringContextTests {
	
	@Autowired
	private MessageSender messageSender;
	
	public void setMessageSender(MessageSender messageSender) {
		this.messageSender = messageSender;
	}
	
	@Override
	protected String[] getConfigLocations() {
		return new String[]{ "classpath:context.xml"};
	}
	
	@Test
	public void test() {
		
		String topic = "topic123";
		String msg = "message123";
		messageSender.send(topic, msg);
	}
	
	public static void main(String[] args) {
		TestRunner.run(KafkaSenderTest.class);
	}
}
