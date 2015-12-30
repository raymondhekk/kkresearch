/**
 * 
 */
package com.kk.message;

import org.springframework.stereotype.Component;

/**
 * @author kk
 *
 */
@Component
public class OrderListener implements MessageListener {
	
	@Override
	public String getTopic() {
		return "topic123";
	}
	
	@SuppressWarnings("unchecked")
	@Listen(topic="topic123")
	public String onMessage(Object message) {
		System.out.println("收到消息" + message );
		Message<String,String> msg = (Message<String,String>)message;
		System.out.println("收到消息" + msg.getValue());
		return (String)msg.getValue();
	}
}
