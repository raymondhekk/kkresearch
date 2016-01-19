/**
 * 
 */
package com.kk;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kk.trading.service.Order;
import com.kk.trading.service.OrderService;
import com.zhaimi.customer.member.service.MemberService.MemberService;

/**
 * @author kk
 *
 */
public class Demo {
	
	public static void main(String[] args) {
		System.out.println("kk research lab.");
		
//		OrderServiceImpl orderService  = new OrderServiceImpl();
//		orderService.createOrder("001","鲜花订单001");
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		OrderService orderService = (OrderService) context.getBean("orderService");
		
		Order order = new Order();
		order.setOrderId("002");
		order.setOrderName("鲜花订单002");
		orderService.createOrder("A001",order);
		
		orderService.afterNewOrder(order);
		
		String newOrder = orderService.getOrder("099");
		System.out.println("****** createOrder " + newOrder);
		
		
//		MemberService memberService= (MemberService) context.getBean("memberService");
//		System.out.println("****** memberService " + memberService);
		
		
	}
}
