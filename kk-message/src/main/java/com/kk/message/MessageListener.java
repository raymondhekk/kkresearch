/**
 * 
 */
package com.kk.message;

/**
 * @author kk
 *
 */
public interface MessageListener {
	
	public String getTopic();
	
	public void onMessage(Object msg);
}
