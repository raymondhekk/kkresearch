/**
 * 
 */
package com.kk;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kk.trading.mapper.Order;
import com.kk.trading.mapper.OrderMapper;
import com.zhaimi.customer.member.service.MemberService.MemberService;

/**
 * @author kk
 *
 */
public class SendMsgAopDemo {
	
	public static void main(String[] args) {
		System.out.println("kk research lab.");
		
//		OrderMapperImpl orderService  = new OrderMapperImpl();
//		orderService.createOrder("001","鲜花订单001");
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		OrderMapper orderService = (OrderMapper) context.getBean("orderService");
		
		Order order = new Order();
		order.setOrderId("002");
		order.setOrderName("鲜花订单002");
		orderService.createOrder("A001",order);
//		
//		orderService.afterNewOrder(order);
		
		String newOrder = orderService.getOrder("099", order);
		System.out.println("****** getOrder " + newOrder);
		
		newOrder = orderService.getOrder("199", order);
		System.out.println("****** getOrder " + newOrder);
		
		newOrder = orderService.updateOrder("399", order);
		System.out.println("****** updateOrder " + order);
		
		newOrder = orderService.insertOrder("499", order);
		System.out.println("****** insert Order  " + order);
		
		newOrder = orderService.delete("799", order);
		System.out.println("****** delete Order  " + order);
	}
}
