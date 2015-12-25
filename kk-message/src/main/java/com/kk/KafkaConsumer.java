/**
 * 
 */
package com.kk;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.QueueChannel;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;  
 
/** 
* @author kk
*/  
public class KafkaConsumer extends Thread  
{  
   private final ConsumerConnector consumer;  
   private final String topic;  
 
   public KafkaConsumer(String topic)  
   {  
       consumer = kafka.consumer.Consumer.createJavaConsumerConnector(  
               createConsumerConfig());  
       this.topic = topic;  
   }  
 
   private static ConsumerConfig createConsumerConfig()  
   {  
       Properties props = new Properties();  
       props.put("zookeeper.connect", KafkaProperties.zkConnect);  
       props.put("group.id", KafkaProperties.groupId);  
       props.put("zookeeper.session.timeout.ms", "40000");  
       props.put("zookeeper.sync.time.ms", "200");  
       props.put("auto.commit.interval.ms", "1000");  
       return new ConsumerConfig(props);  
   }  
 
   @Override  
   public void run() {  
       Map<String, Integer> topicCountMap = new HashMap<String, Integer>();  
       topicCountMap.put(topic, new Integer(1));  
       Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);  
      
       KafkaStream<byte[], byte[]> stream = consumerMap.get(topic).get(0);  
       ConsumerIterator<byte[], byte[]> it = stream.iterator();  
       while (it.hasNext()) {  
           System.out.println("receive：" + new String(it.next().message()));  
           try {  
               sleep(3000);  
           } catch (InterruptedException e) {  
               e.printStackTrace();  
           }  
       }  
   }  
   
   public static void main(String[] args)  
   {  
//       KafkaProducer producerThread = new KafkaProducer(KafkaProperties.topic);  
//       producerThread.start();  
// 
       KafkaConsumer consumerThread = new KafkaConsumer(KafkaProperties.topic);  
       consumerThread.start();  
      
   }  
	   
//   	public static void main(String[] args) throws InterruptedException {
//   		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer-context.xml");
//		QueueChannel channel = (QueueChannel)context.getBean("inputFromKafka",QueueChannel.class);
//		
//		org.springframework.messaging.Message msg = channel.receive();
//		System.out.println("QueueChannel:"  + channel.getClass() + "," + msg);
//		while((msg=channel.receive())!=null) {
//			System.out.println("receive:" );
//			Thread.sleep(2000);
//			HashMap map = (HashMap) msg.getPayload();
//			
//			Set<Map.Entry> set = map.entrySet();
//            for (Map.Entry entry : set) {
//                String topic = (String)entry.getKey();
//                System.out.println("Topic:" + topic);
//                ConcurrentHashMap<Integer,List<byte[]>> messages = (ConcurrentHashMap<Integer,List<byte[]>>)entry.getValue();
//                Collection<List<byte[]>> values = messages.values();
//                for (Iterator<List<byte[]>> iterator = values.iterator(); iterator.hasNext();) {
//                    List<byte[]> list = iterator.next();
//                    for (byte[] object : list) {
//                        String message = new String(object);
//                        System.out.println("\tMessage: " + message);
//                    }
//                }
//            }
//		}
//		
//		context.close();
//	}
//   
   
}  