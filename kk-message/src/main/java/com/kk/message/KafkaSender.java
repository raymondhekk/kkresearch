/**
 * 
 */
package com.kk.message;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * @author kk
 *
 */
@Component
public class KafkaSender implements MessageSender , InitializingBean {
	
	private static Logger log = LoggerFactory.getLogger(KafkaSender.class);
	
	private  Properties properties;
	
	private Producer<String, String> producer;
	
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	/**
	 * send msg to topic
	 */
	@Override
	public void send(String topic, String msg) {
		this.producer.send(new KeyedMessage<String, String>(topic, msg));
		if(log.isDebugEnabled()) {
			log.debug("Send msg. topic='{}',msg='{}'",topic,msg);
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		if( properties == null) {
			throw new IllegalArgumentException("Properties are required when init KafkaSender!");
		}
		this.producer = new Producer<>( new ProducerConfig(this.properties));
	}

}
