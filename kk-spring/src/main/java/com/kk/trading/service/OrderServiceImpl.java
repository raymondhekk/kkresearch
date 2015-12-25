/**
 * 
 */
package com.kk.trading.service;

import com.kk.AfterSendMsg;
import com.kk.BeforeSendMsg;

/**
 * @author kk
 *
 */
public class OrderServiceImpl {
	
	@BeforeSendMsg(topic="testTopic")
	public String createOrder(Order order) {
		System.out.println( String.format("createOrder创建订单: %s,%s, %s" ,order.getOrderId(), order.getOrderName(),this));
		//throw new NullPointerException("ssssss");
		
		return "NEW_ORDER" + order.getOrderId();
	}
	
	
	@AfterSendMsg(topic="testTopic")
	public String afterNewOrder(Order order) {
		System.out.println( String.format("afterNewOrder创建订单: %s,%s, %s" ,order.getOrderId(), order.getOrderName(),this));
		//throw new NullPointerException("ssssss");
		
		return "NEW_ORDER" + order.getOrderId();
	}
	
	public String getOrder(String orderId) {
		System.out.println(">>>>> getOrder " + orderId);
		return "ORDER_" + orderId;
	}
}
