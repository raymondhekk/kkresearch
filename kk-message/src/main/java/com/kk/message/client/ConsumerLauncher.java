package com.kk.message.client;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kk.message.DefaultMessageCenter;
import com.kk.message.ListenerRegistry;
import com.kk.message.MessageCenter;
import com.kk.message.MessageDispatcher;
import com.kk.message.MessageListener;

/**
 * 
 * @author kk
 *
 */
public class ConsumerLauncher {
	
	private static Logger log = LoggerFactory.getLogger(ConsumerLauncher.class);
	
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
	public  static class MessageReceiverThread extends Thread {
		
		private Properties properties;
		private  MessageCenter messageCenter ;
		private ListenerRegistry listenerRegistry;
		
		public MessageReceiverThread( Properties properties,  MessageCenter messageCenter ,ListenerRegistry listenerRegistry) {
			super("MessageReceiverThread");
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
	public static class MessageDispatcherThread extends Thread {
		
		private MessageCenter messageCenter;
		private ListenerRegistry listenerRegistry;
		
		public MessageDispatcherThread(MessageCenter messageCenter, ListenerRegistry listenerRegistry) {
			super("MessageDispatcherThread");
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
		
		int receiverCount = 2;
		int dispatcherCount = 2;
		startMessageReceiver(receiverCount);
		startMessageDispatcher(dispatcherCount);
	}

	private void startMessageReceiver( int size) {
		ExecutorService receiverPool = Executors.newFixedThreadPool(size);
		
		for (int i = 0; i < size; i++) {
			Runnable consumerThread =  new MessageReceiverThread(properties,this.messageCenter,this.listenerRegistry);
			receiverPool.execute( consumerThread );
		}
	}
	
	private void startMessageDispatcher(int size) {
		
		//Start dispatcher thread pool
		ExecutorService dispatcherPool = Executors.newFixedThreadPool(size);
		for (int i = 0; i < size; i++) {
			MessageDispatcherThread messageDispatcherThread = new MessageDispatcherThread(this.messageCenter,this.listenerRegistry);
			dispatcherPool.execute(messageDispatcherThread);
		}
	}
	
	public ListenerRegistry getListenerRegistry() {
		return listenerRegistry;
	}
	
	public static void main(String[] args) {
		ConsumerLauncher consumerLauncher = new ConsumerLauncher();
		consumerLauncher.start();
	}
	
	
}


