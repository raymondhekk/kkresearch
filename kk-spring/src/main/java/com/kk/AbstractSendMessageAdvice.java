package com.kk;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * AbstractSendMessageAdvice
 * @author kk hekun@zhai.me
 * @since 1.0
 * 2016年1月20日
 *
 */
public class AbstractSendMessageAdvice {
	
	private static Logger log = LoggerFactory.getLogger(AbstractSendMessageAdvice.class);
	
	public AbstractSendMessageAdvice() {
	}
	

	/**
	 * SendMsg 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		
		Method method = getMethod(pjp);
		String beforeTopic = getBeforeSendMsgTopicOnMethod(method);
		Object[] args = pjp.getArgs();
		
		if(beforeTopic != null) {
			if(log.isDebugEnabled()) 
				log.debug("BeforeTopic='{}',Method={} "  ,beforeTopic, method);
			sendMessageBefore(method, beforeTopic,args);
		}
		
		String afterTopic = getAfterSendMsgTopicOnMethod(method);
		try {
			//调用实际代码
			result = pjp.proceed();
			
			if( afterTopic != null ) {
				if(log.isDebugEnabled()) 
					log.debug("AfterTopic='{}', Method {}"  ,afterTopic, method);
				sendMessageAfter(method, afterTopic,args,result);
			}
			
		} catch (Throwable e) {
			
			if(log.isDebugEnabled()) 
				log.debug("sendMessageAfterException exception " + e);
			
			try { sendMessageAfterException(method,afterTopic,args,e); } catch(Throwable t) { /* do nothing */ }
			
			throw e;
		}
		if(log.isDebugEnabled()) 
			log.debug("SendMsgBeforeInvokeAdvice result " + result);
		return result;
	}
	
	/**
	 *  Send message after method invocation,using args and return values
	 * @param targetClass
	 * @param method
	 * @param args
	 */
	protected void sendMessageBefore(Method method,String topic, Object[] args) {
		if(log.isDebugEnabled()) 
			log.debug("[发送调用前消息]Send message before invoke, Topic={},Method={}, Args:{}",topic,method,Arrays.asList(args));
	}
	
	/**
	 * Send message before method invocation, using args
	 * @param targetClass
	 * @param method
	 * @param args
	 */
	protected void sendMessageAfter(Method method,String topic, Object[] args,Object result) {
		if(log.isDebugEnabled()) 
			log.debug("[发送调用后消息]Send message after invoke,Topic={}, Method={}, Args:{},Result:{}",topic, method,Arrays.asList(args),result);
	}
	
	/**
	 * 
	 * @param targetClass
	 * @param method
	 * @param args
	 */
	protected void sendMessageAfterException(Method method,String topic, Object[] args,Throwable t) {
		if(log.isDebugEnabled()) 
			log.debug("[发送调用后消息]Send message after invoke,Topic={},  Method={}, Args:{},Throwable:{}",topic,method,Arrays.asList(args),t);
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

	
	/**
	 * Get after Topic Name on Method
	 * @param pjp
	 * @return
	 * @throws NoSuchMethodException
	 */
	protected String getAfterSendMsgTopicOnMethod(Method method) throws NoSuchMethodException {
		SendMessageAfter sendMsgannotation = method.getAnnotation( SendMessageAfter.class );
		String topic = sendMsgannotation!=null ? sendMsgannotation.topic() : null;
		
		if( topic==null) {
			 topic = method.getDeclaringClass().getName() +"." + method.getName();
			 topic = fixTopicName( topic );
		}
		
		return topic;
	}
	
	private final static Map<String,Method> methodCache = new ConcurrentHashMap<>(1024);
	/**
	 * Get method of joinpoint
	 * @param pjp
	 * @return
	 */
	protected Method getMethod(ProceedingJoinPoint pjp) {
		Class<?> clazz = pjp.getTarget().getClass();
		String methodName = pjp.getSignature().getName();
		Method  method = null;
		
		Object[] args = pjp.getArgs();
		Class<?>[] argClasses = new Class[args.length];
		for (int i = 0; i < args.length; i++) {
			argClasses[i] = args[i]!=null ? args[i].getClass() : null;
		}
	    try {
	    	String key = clazz.getName() + "." + methodName +"#" + Arrays.asList(argClasses);
	    	if( (method = methodCache.get(key)) == null ) {
	    		method = clazz.getMethod(methodName, argClasses);
	    		if(log.isDebugEnabled())
					log.debug("找到方法:{}",method);
	    		methodCache.put(key, method);
	    	}
    		
		} catch (Exception e) {
			throw new RuntimeException("Method not found!" + clazz.getName() +"." + methodName, e);
		}
		return method;
	}
	
	protected String fixTopicName( String topicName0) {
		String topicNameStr = topicName0;
		topicNameStr = StringUtils.replace(topicNameStr, "MapperImpl", "");
//		topicNameStr = StringUtils.replace(topicNameStr, "impl.", "");
		return topicNameStr;
	}

}
