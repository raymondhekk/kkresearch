/**
 * 
 */
package com.zhaimi.message.client;

import java.util.Properties;

import com.zhaimi.message.ListenerRegistry;
import com.zhaimi.message.MessageCenter;

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
