/**
 * 
 */
package com.kk;

/**
 * @author kk
 *
 */
public class OrderServiceImpl {
	
	public String createOrder(String orderId, String orderName) {
		System.out.println( String.format("创建订单: %s,%s, %s" ,orderId, orderName,this));
		//throw new NullPointerException("ssssss");
		return "NEW_ORDER" + orderId;
	}
	
	@BeforeSendMsg(topic="testTopic")
	public String getOrder(String orderId) {
		return "ORDER_" + orderId;
	}
}
