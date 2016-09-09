package com.kk.backup;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kk.SendMessageAfter;
import com.kk.SendMessageBefore;

/**
 * 
 * AbstractSendMessageAdvice
 * @author kk hekun@zhai.me
 * @since 1.0
 * 2016年1月20日
 *
 */
public abstract class SendMsgAbstractAdvice {
	
	private static Logger log = LoggerFactory.getLogger(SendMsgAbstractAdvice.class);
	
	public SendMsgAbstractAdvice() {
	}
	

	/**
	 * SendMsg 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		String targetClass = pjp.getTarget().getClass().getName();
		if(log.isDebugEnabled()) log.debug("SendMsgBeforeInvokeAdvice around.... " +  targetClass);
		
		Object result = null;
		Method method = getMethod(pjp);
		
		String beforeTopic = getBeforeSendMsgTopicOnMethod(method);
		
		Object[] args = pjp.getArgs();
		Object object = args.length>0 ? args[0] : null;
		
		if(log.isDebugEnabled()) log.debug("beforeTopic .... "  + beforeTopic);
		
		try {
			if(beforeTopic != null) {
				
				sendBeforeMsg(targetClass,method,args);
				
				if(log.isDebugEnabled()) log.debug("Send message before Topic.... " +  beforeTopic + ",arg object:" + object);
			}else {
				String afterTopic = getAfterSendMsgTopicOnMethod(method);
				if( afterTopic!=null) {
					result = pjp.proceed();
					
					sendAfterMsg(targetClass,method,args);
					if(log.isDebugEnabled()) log.debug("Send message after Topic.... " +  afterTopic + ",arg object:" + object + ",result=" + result);
				}
			}
			
		} catch (Throwable e) {
			
			//e.printStackTrace();
			if(log.isDebugEnabled()) log.debug("SendMsgBeforeInvokeAdvice exception " + e);
			
			sendAfterException(targetClass,method,args);
			
			throw e;
		}
		if(log.isDebugEnabled()) log.debug("SendMsgBeforeInvokeAdvice result " + result);
		return result;
	}
	
	
	/**
	 * Send message before method invocation, using args
	 * @param targetClass
	 * @param method
	 * @param args
	 */
	protected void sendAfterMsg(String targetClass, Method method, Object[] args) {
		
	}
	
	/**
	 *  Send message after method invocation,using args and return values
	 * @param targetClass
	 * @param method
	 * @param args
	 */
	protected void sendBeforeMsg(String targetClass, Method method, Object[] args) {
		
	}
	
	/**
	 * 
	 * @param targetClass
	 * @param method
	 * @param args
	 */
	protected void sendAfterException(String targetClass, Method method, Object[] args) {
		
	}
	
	/**
	 * Get before Topic Name on Method
	 * @param pjp
	 * @return
	 * @throws NoSuchMethodException
	 */
	protected String getBeforeSendMsgTopicOnMethod(Method method) throws NoSuchMethodException {
		
		SendMessageBefore sendMsgannotation = method.getAnnotation( SendMessageBefore.class);
		String topic = sendMsgannotation!=null ? sendMsgannotation.topic() : null;

		if( topic == null) {
			 topic = method.getDeclaringClass().getName() +"." + method.getName();
			 topic = fixTopicName( topic );
		}
		
		return topic;
	}

	
	protected String fixTopicName( String topicName0) {
		String topicNameStr = topicName0;
		topicNameStr = StringUtils.replace(topicNameStr, "MapperImpl", "");
//		topicNameStr = StringUtils.replace(topicNameStr, "impl.", "");
		return topicNameStr;
	}
	/**
	 * Get after Topic Name on Method
	 * @param pjp
	 * @return
	 * @throws NoSuchMethodException
	 */
	protected String getAfterSendMsgTopicOnMethod(Method method) throws NoSuchMethodException {
//		
		SendMessageAfter sendMsgannotation = method.getAnnotation( SendMessageAfter.class );
		String topic = sendMsgannotation!=null ? sendMsgannotation.topic() : null;
		
		if( topic==null) {
			 topic = method.getDeclaringClass().getName() +"." + method.getName();
			 topic = fixTopicName( topic );
		}
		
		return topic;
	}
	
	/**
	 * Get method of joinpoint
	 * @param pjp
	 * @return
	 */
	protected Method getMethod(ProceedingJoinPoint pjp) {
		Class<?> clazz = pjp.getTarget().getClass();
		String methodName = pjp.getSignature().getName();
		
		Method[] methods = clazz.getMethods();
		Method  method = null;
		for (int i = 0; i < methods.length; i++) {
			method = methods[i];
			if(method.getName().equals(methodName))
				break;
		}
		//TODO: cache method
//		Mode 2
//		Object[] args = pjp.getArgs();
//		Class<?>[] argClasses = new Class[args.length];
//		for (int i = 0; i < args.length; i++) {
//			argClasses[i] = args[i]!=null ? args[i].getClass() : null;
//		}
//	    method = clazz.getMethod(methodName, argClasses);
		return method;
	}

}
