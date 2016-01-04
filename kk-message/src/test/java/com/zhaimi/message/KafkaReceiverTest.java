package com.zhaimi.message;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.zhaimi.message.client.ConsumerLauncher;
import com.zhaimi.message.client.MessageReceiver;

import junit.textui.TestRunner;


public class KafkaReceiverTest extends AbstractDependencyInjectionSpringContextTests {
	
	private ConsumerLauncher consumerLauncher;
	
	public void setConsumerLauncher(ConsumerLauncher consumerLauncher) {
		this.consumerLauncher = consumerLauncher;
	}
	
	@Override
	protected String[] getConfigLocations() {
		return new String[]{ "classpath:consumer-context.xml"};
	}
	
	@Test
	public void test() {
		
		String topic = "topic123";
		String msg = "message123";
		//messageReceiver.receive();
		
		consumerLauncher.start();
	}
	
	public static void main(String[] args) {
		TestRunner.run(KafkaReceiverTest.class);
	}
}
