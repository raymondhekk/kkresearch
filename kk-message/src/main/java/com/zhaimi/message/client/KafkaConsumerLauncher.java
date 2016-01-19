package com.zhaimi.message.client;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhaimi.message.DefaultMessageCenter;
import com.zhaimi.message.ListenerRegistry;
import com.zhaimi.message.MessageCenter;
import com.zhaimi.message.MessageDispatcher;
import com.zhaimi.message.MessageListener;
import com.zhaimi.message.NamedThreadFactory;

/**
 * 
 * @author kk
 *
 */
public class KafkaConsumerLauncher implements ConsumerLauncher {
	
	private static Logger log = LoggerFactory.getLogger(KafkaConsumerLauncher.class);
	
	private  Properties properties;
	
	//local message buffer
	private  MessageCenter messageCenter = new DefaultMessageCenter();
	
	private ListenerRegistry listenerRegistry = new ListenerRegistry();
	
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	/**
	 * ConsumerThread. 每个topic由一个线程接收。
	 * @author kk
	 *
	 */
	public  static class MessageReceiverThread implements Runnable {
		
		private Properties properties;
		private  MessageCenter messageCenter ;
		private ListenerRegistry listenerRegistry;
		
		public MessageReceiverThread( Properties properties,  MessageCenter messageCenter ,ListenerRegistry listenerRegistry) {
			this.properties = properties;
			this.messageCenter = messageCenter;
			this.listenerRegistry = listenerRegistry;
		}
		
		@Override
		public void run() {
			
			MessageReceiver messageReceiver = new KafkaReceiver();
			messageReceiver.setProperties(this.properties);
			messageReceiver.setMessageCenter(this.messageCenter);
			messageReceiver.setListenerRegistry(listenerRegistry);
			messageReceiver.init();
			messageReceiver.start();
			
		}
	}
	
	/**
	 * 消息分发器线程
	 * @author kk
	 *
	 */
	public static class MessageDispatcherThread implements Runnable {
		
		private MessageCenter messageCenter;
		private ListenerRegistry listenerRegistry;
		
		public MessageDispatcherThread(MessageCenter messageCenter, ListenerRegistry listenerRegistry) {
			this.messageCenter = messageCenter;
			this.listenerRegistry = listenerRegistry;
		}
		
		@Override
		public void run() {
			MessageDispatcher messageDispatcher = new MessageDispatcher();
			messageDispatcher.setLocalConsumerMsgCenter(messageCenter);
			messageDispatcher.setListenerRegistry(listenerRegistry);
			messageDispatcher.start();
		}
	}
	

	/**
	 * 启动 ConsumerLauncher
	 */
	public void start() {
		
		Map<String,MessageListener> listeners = listenerRegistry.getListenerMap();
		if(log.isInfoEnabled())
			log.info("ListenerRegistry listeners:{}" ,listeners );
		
		int receiverCount = 1;   //just need 1 receiver thread
		int dispatcherCount = 2;
		startMessageReceiver(receiverCount);
		startMessageDispatcher(dispatcherCount);
	}

	private void startMessageReceiver( int size) {
		ExecutorService receiverPool = Executors.newFixedThreadPool(size,new NamedThreadFactory(MessageReceiverThread.class.getSimpleName()));
		
		for (int i = 0; i < size; i++) {
			Runnable consumerThread =  new MessageReceiverThread(properties,this.messageCenter,this.listenerRegistry);
			receiverPool.execute( consumerThread );
		}
	}
	
	private void startMessageDispatcher(int size) {
		
		//Start dispatcher thread pool
		ExecutorService dispatcherPool = Executors.newFixedThreadPool(size,new NamedThreadFactory(MessageDispatcherThread.class.getSimpleName()));
		for (int i = 0; i < size; i++) {
			MessageDispatcherThread messageDispatcherThread = new MessageDispatcherThread(this.messageCenter,this.listenerRegistry);
			dispatcherPool.execute(messageDispatcherThread);
		}
	}
	
	public ListenerRegistry getListenerRegistry() {
		return listenerRegistry;
	}
	
	public static void main(String[] args) {
		KafkaConsumerLauncher consumerLauncher = new KafkaConsumerLauncher();
		consumerLauncher.start();
	}
	
	
}


