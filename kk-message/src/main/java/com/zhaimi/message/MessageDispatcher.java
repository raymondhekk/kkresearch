/**
 * 
 */
package com.zhaimi.message;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhaimi.message.client.KafkaReceiver;

/**
 * @author kk
 *
 */
public class MessageDispatcher {
	
	private static Logger log = LoggerFactory.getLogger(MessageDispatcher.class);
	
	private ListenerRegistry listenerRegistry ;
	
	private MessageCenter localConsumerMsgCenter = new DefaultMessageCenter();
	
	private ReentrantLock lock = new ReentrantLock();
	
	public void setListenerRegistry(ListenerRegistry listenerRegistry) {
		this.listenerRegistry = listenerRegistry;
	}
	
	public void setLocalConsumerMsgCenter(MessageCenter localConsumerMsgCenter) {
		this.localConsumerMsgCenter = localConsumerMsgCenter;
	}
	
	public MessageCenter getLocalConsumerMsgCenter() {
		return localConsumerMsgCenter;
	}
	
	/**
	 * Loop for receive message and dispathcer to listener by topic 
	 */
	public void start() {
		
		while(true) {
			//on message of topic
			MsgBean<String, String>  msgBean = localConsumerMsgCenter.takeMessage();
			
			if(log.isDebugEnabled())
				log.debug("取到一条消息:" + msgBean);
			String topic = msgBean.getTopic(); //get topic by msg
			MessageListener listener = listenerRegistry.getListenerMap().get(topic);
			
			//trigger Event
			if( listener == null ) {
				log.info("已订阅Topic,但对应的监听器未找到,topic={}",topic);
				continue;
			}
				
			try {
				String message = msgBean.getValue();
				listener.onMessage(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
