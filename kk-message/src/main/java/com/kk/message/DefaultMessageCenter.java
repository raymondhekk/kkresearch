/**
 * 
 */
package com.kk.message;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kk.message.sender.KafkaSender;

/**
 * @author kk
 *
 */
public class DefaultMessageCenter implements MessageCenter {
	
	private static Logger log = LoggerFactory.getLogger(KafkaSender.class);
	
	private LinkedBlockingQueue<MsgBean<String,String>> msgQ = new LinkedBlockingQueue<>();
	
	/**
	 * 消息本地入队
	 */
	@Override
	public void putMessage(MsgBean<String, String> msg) {
		try {
			msgQ.put(msg);
//			if(log.isDebugEnabled())
//				log.debug("消息本地入队,{}",msg );
		} catch (InterruptedException e) {
			throw new RuntimeException("Fail to put msg to Q!" + e.getMessage(),e);
		}
	}
	
	@Override
	public MsgBean<String, String>  takeMessage() {
		try {
			return msgQ.take();
		} catch (InterruptedException e) {
			throw new RuntimeException("Fail to take msg from Q!" + e.getMessage(),e);
		}
	}
}
