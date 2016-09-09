/**
 * 
 */
package com.kk;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author kk
 *
 */
@Aspect
public class LogAroundAdvice {
	
//	@Pointcut("execution(* com.kk.trading.mapper.OrderServiceImpl.*(..))")
	public void poincut() { }
	/**
	 * Around
	 * @param pjp
	 * @throws Throwable
	 */
//	@Around("poincut()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		String name =" name=" + pjp.getSignature().getName();
		System.out.println("starting around.... " +  name);
		
		Object result = null;
		try {
			result = pjp.proceed();
			
		} catch (Throwable e) {
			
			//e.printStackTrace();
			System.out.println("around exception " + e);
			throw e;
		}
		System.out.println("around result " + result);
		return result;
	}
}
