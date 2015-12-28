/**
 * 
 */
package com.kk.message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kk
 *
 */
public class MessageDispatcher {
	
	private Map<String,MessageListener> listenerMap = new ConcurrentHashMap<>();
	
	/**
	 * Resigster listener
	 * @param topic
	 * @param listener
	 */
	public void register(String topic, MessageListener listener) {
		listenerMap.put(topic, listener);
	}
	
	public Map<String, MessageListener> getListenerMap() {
		return listenerMap;
	}
	
	/**
	 * Loop for receive message and dispathcer to listener by topic 
	 */
	public void dispatchLoop() {
		
//		while(true) {  //topic count map, msg
//			
//		}
		
		//on message of topic
		String topic = "topic123";
		String msg = "msg123";
		
		while(true) {
			MessageListener listener = listenerMap.get(topic);
			listener.onMessage(msg);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
