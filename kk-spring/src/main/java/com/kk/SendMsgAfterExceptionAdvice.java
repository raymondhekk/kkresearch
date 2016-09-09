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
public class SendMsgAfterExceptionAdvice extends AbstractSendMessageAdvice {
	
	/**
	 * 方法调用异常时发送消息
	 * @see com.kk.AbstractSendMessageAdvice#sendMessageAfterException(java.lang.reflect.Method, java.lang.String, java.lang.Object[], java.lang.Throwable)
	 */
	@Override
	protected void sendMessageAfterException(Method method, String topic, Object[] args, Throwable t) {
		super.sendMessageAfterException(method, topic, args, t);
	}
}
