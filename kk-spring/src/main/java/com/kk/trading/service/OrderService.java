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

	String createOrder(Order order);

}
