/**
 * 
 */
package com.kk.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kk.message.sender.KafkaSender;

/**
 * @author kk
 *
 */
@Component
public class DemoListener2 implements MessageListener {
	
	private static Logger log = LoggerFactory.getLogger(DemoListener2.class);
	
	@Override
	public String getTopic() {
		return "topic789";
	}
	
	@SuppressWarnings("unchecked")
	@Listen(topic="topic789")
	public void onMessage(Object message) {
//		log.info("收到消息对象" + message );
//		MsgBean<String,String> msg = (MsgBean<String,String>)message;
//		log.info("收到消息内容" + msg.getValue());
//		return (String)msg.getValue();
		
		log.info("收到消息内容:" + message);
	}
}
