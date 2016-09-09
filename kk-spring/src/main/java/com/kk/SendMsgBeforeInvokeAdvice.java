/**
 * 
 */
package com.kk;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

/**
 * 
 * SendMsgBeforeInvokeAdvice. 发消息切面
 * @author kk
 *
 */
public class SendMsgBeforeInvokeAdvice extends AbstractSendMessageAdvice {
	
	/** 
	 * 方法调用前发送消息。Send message before method invocation, using args
	 * @param method
	 * @param topic MQ topic
	 * @param args
	 */
	@Override
	protected void sendMessageBefore(Method method, String topic, Object[] args) {
		super.sendMessageBefore(method, topic, args);
	}
}
