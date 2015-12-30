/**
 * 
 */
package com.kk.message;

import java.util.Date;

/**
 * Message。消息封装类。能维护topic,key,value消息的生产和接收消费时间。
 * @author kk
 *
 */
public class Message<K,V> {
	
	private String topic;
	private K key;
	private V value;
	private Date produceTime;
	private Date consumeTime;
	
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}
	public Date getProduceTime() {
		return produceTime;
	}
	public void setProduceTime(Date produceTime) {
		this.produceTime = produceTime;
	}
	public Date getConsumeTime() {
		return consumeTime;
	}
	public void setConsumeTime(Date consumeTime) {
		this.consumeTime = consumeTime;
	}
	
	@Override
	public boolean equals(Object obj) {
		//TODO:待实现
		return super.equals(obj);
	}
	
	
}
