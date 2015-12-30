package com.kk.message;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.kk.message.sender.MessageSender;

import junit.textui.TestRunner;


public class KafkaSenderTest extends AbstractDependencyInjectionSpringContextTests {
	
	private MessageSender messageSender;
	
	public void setMessageSender(MessageSender messageSender) {
		this.messageSender = messageSender;
	}
	
	@Override
	protected String[] getConfigLocations() {
		return new String[]{ "classpath:producer-context.xml"};
	}
	
	@Test
	public void test() {
		
		String topic = "topic123";
		String msgBase = "message";
		for (int i = 0; i < 10 ; i++) {
			String key = msgBase + i +"_" + System.currentTimeMillis();
			String msg = key;
			messageSender.send(topic, key, msg);
		}
		
	}
	
	public static void main(String[] args) {
		TestRunner.run(KafkaSenderTest.class);
	}
}
