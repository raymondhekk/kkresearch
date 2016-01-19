/**
 * 
 */
package com.kk.trading.service.impl;

import com.kk.trading.service.Order;

/**
 * @author kk
 *
 */
public class OrderServiceImpl2 extends OrderServiceImpl {
	
	@Override
	public String createOrder(String memberId,Order order) {
		
		String ret = "*" + super.createOrder(memberId,order);
		return ret;
	}
}
