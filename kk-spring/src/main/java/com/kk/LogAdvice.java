/**
 * 
 */
package com.kk;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author kk
 *
 */
//@Aspect
public class LogAdvice {
	
	@Pointcut("execution(* com.kk.trading.mapper.impl.OrderMapperImpl.*(..))")
	public void poincut() { }
	
	@Before("poincut()")
	public void logMessge0(JoinPoint joinPoint) {
		String name = joinPoint.getSignature().getName();
		
		List<?> argList = Arrays.asList( joinPoint.getArgs() );
		System.out.println("logMessge0 - @Before name:" + name +", " + argList);
		System.out.println("kind=" + joinPoint.getKind() +", " + joinPoint.getSourceLocation() + ", " + joinPoint.getTarget());
		
		System.out.println("declareType=" +joinPoint.getSignature().getDeclaringType() +", " + joinPoint.getSignature().getDeclaringTypeName() );
		
	}
	

	
	@After("poincut() && args(orderId, orderName)")
	public void logMessage1(String orderId, String orderName) {
		
		
		System.out.println("logMessge1 - @After orderId xx:" + orderId + ",orderName=" + orderName );
//		String name = joinPoint.getSignature().getName();
//		List<?> argList = Arrays.asList( joinPoint.getArgs() );
//		System.out.println("logMessge1 - @@After name:" + name +", " + argList);
		
	}
	
	@AfterReturning(pointcut="poincut()",returning="returnVal") //,argNames="argNames" ) //, value="val",argNames="argNames")
	public void logMessage2(JoinPoint joinPoint,Object returnVal) //,String[] argNames)  //,Object val) 
	{
		String name = joinPoint.getSignature().getName();
		
		
		List<?> argList = Arrays.asList( joinPoint.getArgs() );
		System.out.println("logMessge2 - @AfterReturning name:" + name +", " + argList);
		System.out.println("logMessge2 - @AfterReturning returnVal:" + returnVal ); //+", value=" + value +",argNames=" + Arrays.asList(argNames));
		
	}
	
	@AfterThrowing(pointcut="poincut()",throwing="t")
	public void logMessage3(JoinPoint joinPoint,Throwable t) {
		String name = joinPoint.getSignature().getName();
		
		List<?> argList = Arrays.asList( joinPoint.getArgs() );
		System.out.println("logMessge2 - @AfterThrowing name:" + name +", " + argList + ", t=" + t);
	}
	
	/**
	 * Around
	 * @param pjp
	 * @throws Throwable
	 */
//	@Around("poincut()")
//	public void acount(ProceedingJoinPoint pjp) throws Throwable {
//		String name =" name=" + pjp.getSignature().getName();
//		System.out.println("starting around.... " +  name);
//		
//		Object result = null;
//		try {
//			result = pjp.proceed();
//			
//		} catch (Throwable e) {
//			
//			//e.printStackTrace();
//			System.out.println("around exception " + e);
//			throw e;
//		}
//		System.out.println("around result " + result);
//	}
}
