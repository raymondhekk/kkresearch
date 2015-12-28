/**
 * 
 */
package com.kk.message;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.framework.AopProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
/**
 * MessageListenerBeanPostProcessor
 * @author kk
 *
 */
public class MessageListenerBeanPostProcessor implements BeanPostProcessor {
	
	private static final Log log = LogFactory.getLog(MessageListenerBeanPostProcessor.class);
	
	private  MessageDispatcher messageDispatcher;
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
	
	/**
	 * Find and register message listeners.
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Object proxyBean = null;
		if(bean instanceof AopProxy ) {
			AopProxy proxy = (AopProxy)bean; 
			proxyBean = proxy.getProxy();
			
			System.out.println("proxyBean ......" + proxyBean);
		}
		
		final Method method = null;
		if( bean instanceof MessageListener) {
			System.out.println("register MessageListener " + bean);
			MessageListener listener = (MessageListener)bean;
			
			Class<?> clazz = bean.getClass();
				
			
			   ReflectionUtils.doWithMethods(bean.getClass(), new ReflectionUtils.MethodCallback() {
	               @Override
	               public void doWith(Method paramMethod) throws IllegalArgumentException, IllegalAccessException {
	                 
	                 
	                 Listen listen = paramMethod.getAnnotation(Listen.class);
	                 System.out.println("paramMethod " + paramMethod.getName() + ",listen=" + listen);
	               }	
	           });
			   
			   
//		    method = getMethod(clazz,"onMessage");
//			Listen listen = method.getAnnotation(Listen.class);
//			
//			if( listen == null) {
//				log.warn("@Listen annotation not declared on event method 'onMessage' of bean " + bean);
//			}else {
//				String topic = listen.topic();
//				messageDispatcher.register(topic,listener);
//			}
		}
		return bean;
	}
	
	/**
	 * 获取第一个方法
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	protected Method getMethod(Class<?> clazz, String methodName) {
		
		Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz); //clazz.getDeclaredMethods();
		Method  method = null;
		for (int i = 0; i < methods.length; i++) {
			method = methods[i];
			if(method.getName().equals(methodName))
				break;
		}
		return method;
	}
}
