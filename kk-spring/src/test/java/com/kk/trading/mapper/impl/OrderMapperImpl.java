/**
 * 
 */
package com.kk.trading.mapper.impl;

import com.kk.SendMessageAfter;
import com.kk.SendMessageBefore;
import com.kk.trading.mapper.Order;
import com.kk.trading.mapper.OrderMapper;

/**
 * @author kk
 *
 */

public class OrderMapperImpl implements OrderMapper {
	

	@SendMessageBefore(topic="testTopic")
	public String createOrder(String memberId,Order order) {
		System.out.println( String.format("createOrder创建订单: %s,%s, %s" ,order.getOrderId(), order.getOrderName(),this));
		//throw new NullPointerException("ssssss");
		
		return "NEW_ORDER" + order.getOrderId();
	}
	
	

	@SendMessageAfter(topic="testTopic789")
	public String afterNewOrder(Order order) {
		System.out.println( String.format("afterNewOrder创建订单: %s,%s, %s" ,order.getOrderId(), order.getOrderName(),this));
		//throw new NullPointerException("ssssss");
		
		return "NEW_ORDER" + order.getOrderId();
	}
	
	public String getOrder(String orderId,Order order) {
		System.out.println(">>>>> getOrder " + orderId);
		return "ORDER_" + orderId;
	}
	
	
	@Override
	public String updateOrder(String orderId, Order order) {
		System.out.println(">>>>> updateOrder " + orderId);
		return "UPDATEORDER_" + orderId;
	}
	/* (non-Javadoc)
	 * @see com.kk.trading.mapper.OrderMapper#insert(java.lang.String, com.kk.trading.mapper.Order)
	 */
	@Override
	public String insertOrder(String orderId, Order order) {
		System.out.println(">>>>> insert Order " + orderId);
		return "INSERT " + orderId;
	}
	
	/* (non-Javadoc)
	 * @see com.kk.trading.mapper.OrderMapper#insert(java.lang.String, com.kk.trading.mapper.Order)
	 */
	@Override
	public String delete(String orderId, Order order) {
		System.out.println(">>>>> insert Order " + orderId);
		return "INSERT " + orderId;
	}
}
