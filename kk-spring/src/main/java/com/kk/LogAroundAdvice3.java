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
public class LogAroundAdvice3 {
	
//	@Pointcut("execution(* com.kk.OrderServiceImpl.*(..))")
	public void poincut() { }
	/**
	 * Around
	 * @param pjp
	 * @throws Throwable
	 */
//	@Around("poincut()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		String name =" name=" + pjp.getSignature().getName();
		System.out.println("starting3 around.... " +  name);
		
		Object result = null;
		try {
			result = pjp.proceed();
			
		} catch (Throwable e) {
			
			//e.printStackTrace();
			System.out.println("around3 exception " + e);
			throw e;
		}
		System.out.println("around3 result " + result);
		return result;
	}
}
