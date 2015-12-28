/**
 * 
 */
package com.kk;

/**
 * @author kk
 *
 */
public class KafkaConsumerProducerDemo  
{  
    public static void main(String[] args)  
    {  
        KafkaProducer producerThread = new KafkaProducer(KafkaProperties.topic);  
        producerThread.start();  
  
        KafkaConsumer consumerThread = new KafkaConsumer(KafkaProperties.topic);  
        consumerThread.start();  
    }  
	
//	public static void main(String[] args) {
//		
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
//		MessageChannel channel = (MessageChannel)context.getBean("inputToKafka");
//		
//		for (int i = 0; i < 10; i++) {
//			channel.send(new GenericMessage<>("Msg" + i));
//			
//			System.out.println("send message" + i);
//		}
//		context.close();
//	}
} 
