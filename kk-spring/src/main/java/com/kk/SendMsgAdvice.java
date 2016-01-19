/**
 * 
 */
package com.kk;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

/**
 * 
 * SendMsgAdvice. 发消息切面
 * @author kk
 *
 */
@Aspect
public class SendMsgAdvice {
	
	/**
	 * SendMsg 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		String targetClass = pjp.getTarget().getClass().getName();
		System.out.println("SendMsgAdvice around.... " +  targetClass);
		
		Object result = null;
		Method method = getMethod(pjp);
		
		String beforeTopic = getBeforeSendMsgTopicOnMethod(method);
		
		Object[] args = pjp.getArgs();
		Object object = args.length>0 ? args[0] : null;
		
		System.out.println("beforeTopic .... "  + beforeTopic);
		
		try {
			if(beforeTopic != null) {
				
				sendBeforeMsg(targetClass,method,args);
				
				System.out.println("Send message before Topic.... " +  beforeTopic + ",arg object:" + object);
			}else {
				String afterTopic = getAfterSendMsgTopicOnMethod(method);
				if( afterTopic!=null) {
					result = pjp.proceed();
					
					sendAfterMsg(targetClass,method,args);
					System.out.println("Send message after Topic.... " +  afterTopic + ",arg object:" + object + ",result=" + result);
				}
			}
			
		} catch (Throwable e) {
			
			//e.printStackTrace();
			System.out.println("SendMsgAdvice exception " + e);
			
			sendAfterException(targetClass,method,args);
			
			throw e;
		}
		System.out.println("SendMsgAdvice result " + result);
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
		
		
		BeforeSendMsg sendMsgannotation = method.getAnnotation( BeforeSendMsg.class);
		String topic = sendMsgannotation!=null ? sendMsgannotation.topic() : null;
		return topic;
	}


	/**
	 * Get after Topic Name on Method
	 * @param pjp
	 * @return
	 * @throws NoSuchMethodException
	 */
	protected String getAfterSendMsgTopicOnMethod(Method method) throws NoSuchMethodException {
		
		AfterSendMsg sendMsgannotation = method.getAnnotation( AfterSendMsg.class );
		String topic = sendMsgannotation!=null ? sendMsgannotation.topic() : null;
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
