/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kk.parallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ForkJoinMain
 * @author kk hekun@zhai.me
 * @since 1.0
 * 2016年7月6日
 *
 */
public class ForkJoinMain {


	public static void main(String[] args) {
		
//		int p = Runtime.getRuntime().availableProcessors();
//		
//		ForkJoinPool forkJoinPool = new ForkJoinPool( p );
//		
//		Calculator c = new Calculator(1, 100);
//		Future<Integer> f = forkJoinPool.submit( c );
//		try {
//			int s = f.get();
//			
//			System.out.printf("p=%d, out=%d, %s" ,p, s, f.getClass());
//			
//			forkJoinPool.shutdown();
//			forkJoinPool.awaitTermination(1, TimeUnit.SECONDS);
//			
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		}
		
		
//		ExecutorService pool = Executors.newFixedThreadPool(1);
		R r = new R();
		Thread t = new Thread(r);
		t.start();
//		pool.execute( r);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	static class R implements Runnable {
		ReentrantLock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		@Override
		public void run() {
			while(true) {
				System.out.println("current:" + System.nanoTime());
				try {
					condition.awaitNanos(10L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
