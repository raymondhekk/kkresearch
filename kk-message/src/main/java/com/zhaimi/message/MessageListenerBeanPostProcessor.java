/**
 * 
 */
package com.zhaimi.message;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import com.zhaimi.message.client.KafkaConsumerLauncher;
/**
 * MessageListenerBeanPostProcessor
 * @author kk
 *
 */
public class MessageListenerBeanPostProcessor implements BeanPostProcessor {
	
	private static final Logger log = LoggerFactory.getLogger(MessageListenerBeanPostProcessor.class);
	
	@Autowired
	private KafkaConsumerLauncher consumerLauncher;
	
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
	
	/**
	 * Find and register message listeners.
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		
	    Method method = null;
		if( bean instanceof MessageListener) {
			log.info(" MessageListener found:" + bean);
			
			MessageListener listener = (MessageListener)bean;
			
			Class<?> clazz = bean.getClass();
		    method = getMethod(clazz,"onMessage");
			Listen listen = method.getAnnotation(Listen.class);
			
			String topic = null;
			if( listen == null) {
				log.warn("@Listen annotation not declared on event method 'onMessage' of bean " + bean);
				topic = listener.getTopic();
			}else {
				 topic = listen.topic();
			}
			
			if(topic !=null) {
				
				consumerLauncher.getListenerRegistry().getListenerMap().put(topic,listener);
				log.info("Register listener '{}' on topic '{}'", listener, topic);
			}
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
