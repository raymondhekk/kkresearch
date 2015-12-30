/**
 * 
 */
package com.kk.message;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author kk
 *
 */
public class DefaultMessageCenter implements MessageCenter {
	
	private LinkedBlockingQueue<Message<String,String>> msgQ = new LinkedBlockingQueue<>();
	
	@Override
	public void putMessage(Message<String, String> msg) {
		try {
			msgQ.put(msg);
		} catch (InterruptedException e) {
			throw new RuntimeException("Fail to put msg to Q!" + e.getMessage(),e);
		}
	}
	
	@Override
	public Message<String, String>  takeMessage() {
		try {
			return msgQ.take();
		} catch (InterruptedException e) {
			throw new RuntimeException("Fail to take msg from Q!" + e.getMessage(),e);
		}
	}
}
