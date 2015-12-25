/**
 * 
 */
package com.kk;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

/**
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
		String methodName = pjp.getSignature().getName();
		System.out.println("SendMsgAdvice around.... " +  methodName);
		
		Object result = null;
		try {
			Method method = getMethod(pjp);
			
			String beforeTopic = getBeforeSendMsgTopicOnMethod(method);
			
			Object[] args = pjp.getArgs();
			Object object = args.length>0 ? args[0] : null;
			
			System.out.println("beforeTopic .... "  + beforeTopic);
					
			if(beforeTopic != null) {
				System.out.println("Send message before Topic.... " +  beforeTopic + ",arg object:" + object);
			}else {
				String afterTopic = getAfterSendMsgTopicOnMethod(method);
				if( afterTopic!=null) {
					result = pjp.proceed();
					System.out.println("Send message after Topic.... " +  afterTopic + ",arg object:" + object + ",result=" + result);
				}
			}
			
		} catch (Throwable e) {
			
			//e.printStackTrace();
			System.out.println("SendMsgAdvice exception " + e);
			throw e;
		}
		System.out.println("SendMsgAdvice result " + result);
		return result;
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
		Class<?> clazz = pjp.getSignature().getDeclaringType();
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
