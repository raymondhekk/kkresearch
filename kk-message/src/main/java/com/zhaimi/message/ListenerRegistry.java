/**
 * 
 */
package com.zhaimi.message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kk
 *
 */
public class ListenerRegistry {
	
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
}
