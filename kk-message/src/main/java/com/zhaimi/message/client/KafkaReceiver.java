/**
 * 
 */
package com.zhaimi.message.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.zhaimi.message.ListenerRegistry;
import com.zhaimi.message.MessageCenter;
import com.zhaimi.message.MessageListener;
import com.zhaimi.message.MsgBean;
import com.zhaimi.message.MsgBeanDecoder;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.consumer.Whitelist;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.serializer.Decoder;
import kafka.serializer.DefaultDecoder;


/**
 * @author kk
 *
 */
public class KafkaReceiver implements MessageReceiver {
	
	private static Logger log = LoggerFactory.getLogger(KafkaReceiver.class);
	
	private  MessageCenter messageCenter ;
	
	private ListenerRegistry listenerRegistry;
	
	private  Properties properties;
	
	private ConsumerConnector consumer;
	
	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	@Override
	public void setMessageCenter(MessageCenter messageCenter) {
		this.messageCenter = messageCenter;
	}
	
	@Override
	public void setListenerRegistry(ListenerRegistry listenerRegistry) {
		this.listenerRegistry = listenerRegistry;
	}
	
	/**
	 * 启动 MessageReceiver，开始监听topic消息
	 */
	@Override
	public void start() {
		
		if(consumer == null) {
//			log.error("KafkaReceiver consumer not init! Maybe init() method should be called first!");
			init();
		}
		
	   int partitions = 1;
	   String topicString = buildTopicsString();
//		   Map<String, Integer> topicCountMap = buildTopicCountMap(partitions);  
//	       Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);  
      
       Whitelist topicFilter = new Whitelist( topicString );
       List<KafkaStream<byte[], byte[]>> streamList = consumer.createMessageStreamsByFilter(topicFilter,partitions);
       
       if( org.apache.commons.collections.CollectionUtils.isEmpty( streamList ))
		try {
			TimeUnit.MILLISECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
       processStreamsByTopic("未指定", streamList);
	
	}

	private void processStreamsByTopic(String topicKey,List<KafkaStream<byte[], byte[]>> streamList) {
		//遍历stream
		int i = 0;
		  for (KafkaStream<byte[], byte[]> stream : streamList) {
			  
			  if(log.isDebugEnabled())
    			  log.debug("处理消息流KafkaStream,topic={}, No.={}" ,topicKey ,i++);
			  
			  ConsumerIterator<byte[], byte[]> consumerIterator = stream.iterator();
			  
			  processStreamByConsumer(topicKey, consumerIterator);
			  
		  }
	}

	private void processStreamByConsumer(String topicKey,ConsumerIterator<byte[], byte[]> consumerIterator) {
		
		int i = 0;
		while(consumerIterator.hasNext() ){
			  MessageAndMetadata<byte[],byte[]> msgAndMeta = consumerIterator.next(); 
			  
			   byte[] keyBytes = msgAndMeta.key();
			   byte[] messageBytes = msgAndMeta.message();
			   
			   try {
		    	   String key = keyBytes!=null ? new String(keyBytes,"UTF-8") : null;
		    	   String msgString = new String(messageBytes,"UTF-8");
		    	   
		           if(log.isDebugEnabled())
		        	   log.debug("*********** 收到消息:No.={},key={},message={}" ,i++, key ,msgString);  
		           
//		           MsgBean<String,String> msgBean = new MsgBean<>();
//		           msgBean.setTopic( msgAndMeta.topic() );
//		           msgBean.setConsumeTime(new Date());
//		           msgBean.setKey(key);
//		           msgBean.setValue(msgString);
		           
		           @SuppressWarnings("unchecked")
		           MsgBean<String,String> msgBean = JSON.parseObject(msgString, MsgBean.class);
		           //put to local buffer
		           messageCenter.putMessage( msgBean );
		           
		       } catch (Exception e) {  
		           e.printStackTrace();  
		       }  
		  }
	}
	
	/**
	 * buildTopicCountMap
	 * @param batchSize
	 * @return
	 */
	private Map<String, Integer> buildTopicCountMap(int partitions) {
		Map<String, Integer> topicCountMap = new HashMap<>();  
	    
	    Map<String,MessageListener> listnersMap = listenerRegistry.getListenerMap();
	    
	    for(String key: listnersMap.keySet()) {
	    	topicCountMap.put(key, partitions);
	    }
		return topicCountMap;
	}
	
	/**
	 * topic string, e.g.  "topic123,topic456,topic789"
	 * @param partitions
	 * @return
	 */
	private String buildTopicsString() {
	    
	    final Map<String,MessageListener> listnersMap = listenerRegistry.getListenerMap();
	    
	    String topicStr = "";
	    int i = 0;
	    int size = listnersMap.keySet().size();
	    for(String key: listnersMap.keySet()) {
	    	topicStr += key;
	    	if(i++ < (size-1 )) topicStr +=",";
	    }
		return topicStr;
	}
	
   private  ConsumerConfig createConsumerConfig()  
   {  
       return new ConsumerConfig(this.properties);  
   }  
   
   /**
    * 初始化
    * @return
    */
   @Override
   public boolean init() {
	   this.consumer = kafka.consumer.Consumer.createJavaConsumerConnector( createConsumerConfig());
	   return true;
   }
}
