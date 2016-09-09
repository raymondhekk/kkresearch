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

import java.text.Format;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Calculator
 * @author kk hekun@zhai.me
 * @since 1.0
 * 2016年7月6日
 *
 */
public class Calculator extends RecursiveTask<Integer>{
	
	private static final int _Threshold = 2;
	private static AtomicInteger taskCount = new AtomicInteger(0);
	private int start;
	private int end;
	
	private int level ;
	
	public Calculator(int start,int end) {
		this.start = start;
		this.end = end;
		int c = taskCount.incrementAndGet();
	}
	
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Caculator@"+ this.hashCode()+ "|" + level + "[" + start+ ","+end + "]#" + Thread.currentThread();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -1225924275782561943L;

	@Override
	protected Integer compute() {
		
		int gap = end-start;
		int sum = 0;
		
		System.out.printf("Calculator %d=%s,    %s\n",taskCount.intValue(), this, Thread.currentThread());
		
		if( gap < _Threshold) {
			for(int i=start; i<=end; i++) {
				sum += i;
				
				if(start==100)
					new Exception().printStackTrace();
			}
		}else {   //cut down
			int mid = (start+end)/2;
			
			Calculator left = new Calculator(start, mid);
			Calculator right = new Calculator(mid+1, end);
			
			left.setLevel(this.getLevel()+1);
			right.setLevel(this.getLevel()+1);
			
			left.fork();
			right.fork();
		
			sum += left.join() + right.join();
		}
		return sum;
	}
	
}
