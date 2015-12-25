/**
 * 
 */
package com.kk.trading.service;

/**
 * @author kk
 *
 */
public class OrderServiceImpl2 extends OrderServiceImpl {
	
	@Override
	public String createOrder(Order order) {
		
		String ret = "*" + super.createOrder(order);
		return ret;
	}
}
