/**
 * 
 */
package com.zhaimi.message.sender;

/**
 * @author kk
 *
 */
public interface MessageSender {
	
	public void send(String topic,String msg);
	
	public void send(String topic,String key, String msg) ;
}
