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
		
		String topic1 = "topic123";
		String msgBase1 = "message";
		
		int n = 10;
		for (int i = 0; i < n ; i++) {
			String key = msgBase1 + i +"_" + System.currentTimeMillis();
			String msg = key;
			messageSender.send(topic1, key, msg);
		}
		
		String topic2 = "topic789";
		String msgBase2 = "orderId_";
		for (int i = 0; i < n ; i++) {
			String key = msgBase2 + i +"_" + System.currentTimeMillis();
			String msg = key;
			messageSender.send(topic2, key, msg);
		}
		
		
	}
	
	public static void main(String[] args) {
		TestRunner.run(KafkaSenderTest.class);
	}
}
