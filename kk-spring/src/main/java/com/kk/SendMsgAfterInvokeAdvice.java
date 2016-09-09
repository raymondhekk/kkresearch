/**
 * 
 */
package com.kk;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

/**
 * 
 * SendMsgBeforeInvokeAdvice. 发消息切面
 * @author kk
 *
 */
@Aspect
public class SendMsgAfterInvokeAdvice extends AbstractSendMessageAdvice {
	
	/**
	 * 方法调用后发送消息
	 * @see com.kk.AbstractSendMessageAdvice#sendMessageAfter(java.lang.reflect.Method, java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	@Override
	protected void sendMessageAfter(Method method, String topic, Object[] args, Object result) {
		super.sendMessageAfter(method, topic, args, result);
	}
}
