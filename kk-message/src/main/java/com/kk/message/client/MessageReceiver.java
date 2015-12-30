/**
 * 
 */
package com.kk.message.client;

import java.util.Properties;

import com.kk.message.ListenerRegistry;
import com.kk.message.MessageCenter;

/**
 * @author kk
 *
 */
public interface MessageReceiver {

	boolean init();
	
	public void start();

	void setProperties(Properties properties);
	
	void setListenerRegistry(ListenerRegistry listenerRegistry);

	void setMessageCenter(MessageCenter messageCenter);
}
