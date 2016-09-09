/**
 * 
 */
package com.kk.trading.mapper;

/**
 * @author kk
 *
 */
public interface OrderMapper {
	
	String getOrder(String orderId,Order order);

	String afterNewOrder(Order order);

	String createOrder(String memberId,Order order);
	
	String updateOrder(String orderId,Order order);
	
	String insertOrder(String orderId,Order order);

	/**
	 * @param orderId
	 * @param order
	 * @return
	 */
	String delete(String orderId, Order order);
}
