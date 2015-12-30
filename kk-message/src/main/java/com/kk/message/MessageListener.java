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
	
	public String onMessage(Object msg);
}
