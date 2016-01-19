/**
 * 
 */
package com.kk.trading.service.impl;

import com.kk.AfterSendMsg;
import com.kk.BeforeSendMsg;
import com.kk.trading.service.Order;
import com.kk.trading.service.OrderService;

/**
 * @author kk
 *
 */

public class OrderServiceImpl implements OrderService {
	

	@BeforeSendMsg(topic="testTopic")
	public String createOrder(String memberId,Order order) {
		System.out.println( String.format("createOrder创建订单: %s,%s, %s" ,order.getOrderId(), order.getOrderName(),this));
		//throw new NullPointerException("ssssss");
		
		return "NEW_ORDER" + order.getOrderId();
	}
	
	

	@AfterSendMsg(topic="testTopic789")
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
	
//	@Listen(topic="topic123")
//	@Override
//	public String onMessage(Object msg) {
//		System.out.println(">>>>> onMessage msg=" + msg);
//		return null;
//	}
}
