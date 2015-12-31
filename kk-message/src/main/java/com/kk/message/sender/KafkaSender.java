/**
 * 
 */
package com.kk.message.sender;

import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSON;
import com.kk.message.MsgBean;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * @author kk
 *
 */
//@Component
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
		send(topic, null, msg);
	}
	
	/**
	 * 发送消息
	 * @param topic 消息主题
	 * @param key   消息键值
	 * @param msg   消息内容
	 */
	@Override
	public void send(String topic,String key, String msg) {
		KeyedMessage<String,String> keyedMessage = null;
		
		MsgBean<String, String> msgBean = new MsgBean<>();
		msgBean.setTopic(topic);
		msgBean.setKey(key);
		msgBean.setValue( msg );
		msgBean.setProduceTime(new Date());
		
		String json = JSON.toJSONString(msgBean);
		if( key == null) 
			keyedMessage = new KeyedMessage<>(topic, key,json);
		else
			keyedMessage = new KeyedMessage<>(topic,json);
		
		this.producer.send( keyedMessage);
		
		if(log.isDebugEnabled()) {
			log.debug("发送消息. topic='{}',key='{}',msg='{}',json='{}'",topic,key,msg,json);
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
