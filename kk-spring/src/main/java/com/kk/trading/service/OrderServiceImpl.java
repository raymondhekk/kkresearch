/**
 * 
 */
package com.kk.trading.service;

import com.kk.AfterSendMsg;
import com.kk.BeforeSendMsg;
import com.kk.message.Listen;
import com.kk.message.MessageListener;

/**
 * @author kk
 *
 */

public class OrderServiceImpl implements OrderService, MessageListener {
	
	@Override
	@BeforeSendMsg(topic="testTopic")
	public String createOrder(Order order) {
		System.out.println( String.format("createOrder创建订单: %s,%s, %s" ,order.getOrderId(), order.getOrderName(),this));
		//throw new NullPointerException("ssssss");
		
		return "NEW_ORDER" + order.getOrderId();
	}
	
	
	@Override
	@AfterSendMsg(topic="testTopic")
	public String afterNewOrder(Order order) {
		System.out.println( String.format("afterNewOrder创建订单: %s,%s, %s" ,order.getOrderId(), order.getOrderName(),this));
		//throw new NullPointerException("ssssss");
		
		return "NEW_ORDER" + order.getOrderId();
	}
	
	@Override
	public String getOrder(String orderId) {
		System.out.println(">>>>> getOrder " + orderId);
		return "ORDER_" + orderId;
	}
	
	@Listen(topic="topic123")
	@Override
	public String onMessage(Object msg) {
		System.out.println(">>>>> onMessage msg=" + msg);
		return null;
	}
}
