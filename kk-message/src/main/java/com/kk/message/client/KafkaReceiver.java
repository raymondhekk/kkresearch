/**
 * 
 */
package com.kk.message.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kk.message.ListenerRegistry;
import com.kk.message.Message;
import com.kk.message.MessageCenter;
import com.kk.message.MessageListener;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;


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
		int batchSize = 1;
		   Map<String, Integer> topicCountMap = buildTopicCountMap(batchSize);  
	       Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);  
	      
	       //遍历所有监听的topic
	       for(String topicKey: consumerMap.keySet()) {
	    	  List<KafkaStream<byte[], byte[]>> streamList = consumerMap.get(topicKey);
	    	  processStreamsByTopic(topicKey, streamList);
	       }
	       
	       
//	       KafkaStream<byte[], byte[]> stream = consumerMap.get(this.topic).get(0);  
//	       ConsumerIterator<byte[], byte[]> it = stream.iterator();  
//	       while (it.hasNext()) {  
//	    	   
//	    	   MessageAndMetadata<byte[],byte[]> msgAndMeta = it.next();
//	    	   
//	    	   byte[] keyBytes = msgAndMeta.key();
//	    	   byte[] messageBytes = msgAndMeta.message();
//	    	   
//	    	   try {
//		    	   String key = keyBytes!=null ? new String(keyBytes) : null;
//		    	   String message = new String(messageBytes);
//		    	   
//		           if(log.isInfoEnabled())
//		        	   log.info("*********** 收到消息:key={},message={}" , key ,message);  
//		           
//		           //put to local buffer
//		           messageDispatcher.getLocalConsumerMsgCenter().putMessage( message );
//		           
//	           } catch (Exception e) {  
//	               e.printStackTrace();  
//	           }  
//	       }  
	}

	private void processStreamsByTopic(String topicKey,List<KafkaStream<byte[], byte[]>> streamList) {
		//遍历stream
		  for (KafkaStream<byte[], byte[]> stream : streamList) {
			
			  ConsumerIterator<byte[], byte[]> consumerIterator = stream.iterator();
			  
			  processStreamByConsumer(topicKey, consumerIterator);
			  
		  }
	}

	private void processStreamByConsumer(String topicKey,ConsumerIterator<byte[], byte[]> consumerIterator) {
		while(consumerIterator.hasNext() ){
			  MessageAndMetadata<byte[],byte[]> msgAndMeta = consumerIterator.next(); 
			  
			   byte[] keyBytes = msgAndMeta.key();
			   byte[] messageBytes = msgAndMeta.message();
			   
			   try {
		    	   String key = keyBytes!=null ? new String(keyBytes) : null;
		    	   String msgString = new String(messageBytes);
		    	   
		           if(log.isInfoEnabled())
		        	   log.info("*********** 收到消息:key={},message={}" , key ,msgString);  
		           
		           Message<String,String> message = new Message<>();
		           message.setTopic(topicKey);
		           message.setConsumeTime(new Date());
		           message.setKey(key);
		           message.setValue(msgString);
		           //put to local buffer
		           messageCenter.putMessage( message );
		           
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
	private Map<String, Integer> buildTopicCountMap(int batchSize) {
		Map<String, Integer> topicCountMap = new HashMap<>();  
	    
	    Map<String,MessageListener> listnersMap = listenerRegistry.getListenerMap();
	    
	    for(String key: listnersMap.keySet()) {
	    	topicCountMap.put(key, batchSize);
	    }
		return topicCountMap;
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
