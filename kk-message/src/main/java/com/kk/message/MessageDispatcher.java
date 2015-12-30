/**
 * 
 */
package com.kk.message;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author kk
 *
 */
public class MessageDispatcher {
	
	private ListenerRegistry listenerRegistry ;
	
	private MessageCenter localConsumerMsgCenter = new DefaultMessageCenter();
	
	private ReentrantLock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
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
			Message<String, String>  msg = localConsumerMsgCenter.takeMessage();
			
			String topic = msg.getTopic(); //get topic by msg
			MessageListener listener = listenerRegistry.getListenerMap().get(topic);
			
			//trigger Event
			listener.onMessage(msg);
//			try {
//				//condition.await(1, TimeUnit.MILLISECONDS);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}
}
