/**
 * 
 */
package com.kk.message;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Message。消息封装类。能维护topic,key,value消息的生产和接收消费时间。
 * @author kk
 *
 */
public class MsgBean<K,V> implements Serializable {
	
	private static final long serialVersionUID = -4387811273059946901L;
	
	private String topic;
	private K key;
	private V value;
	
	private Date produceTime;
	private String produceIP;
	private String producePid;
	private String produceTid;
	
	private Date consumeTime;
	private String cosumerIP;
	private String ccosumerPid;
	private String cosumerTid;
	
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
	
	
	public String getProduceIP() {
		return produceIP;
	}
	public void setProduceIP(String produceIP) {
		this.produceIP = produceIP;
	}
	public String getProducePid() {
		return producePid;
	}
	public void setProducePid(String producePid) {
		this.producePid = producePid;
	}
	public String getProduceTid() {
		return produceTid;
	}
	public void setProduceTid(String produceTid) {
		this.produceTid = produceTid;
	}
	public String getCosumerIP() {
		return cosumerIP;
	}
	public void setCosumerIP(String cosumerIP) {
		this.cosumerIP = cosumerIP;
	}
	public String getCcosumerPid() {
		return ccosumerPid;
	}
	public void setCcosumerPid(String ccosumerPid) {
		this.ccosumerPid = ccosumerPid;
	}
	public String getCosumerTid() {
		return cosumerTid;
	}
	public void setCosumerTid(String cosumerTid) {
		this.cosumerTid = cosumerTid;
	}
	@Override
	public boolean equals(Object obj) {
		//TODO:待实现
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	
}
