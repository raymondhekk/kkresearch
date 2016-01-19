/**
 * 
 */
package com.kk.trading.service;

/**
 * @author kk
 *
 */
public interface OrderService {

	String getOrder(String orderId);

	String afterNewOrder(Order order);

	String createOrder(String memberId,Order order);

}
