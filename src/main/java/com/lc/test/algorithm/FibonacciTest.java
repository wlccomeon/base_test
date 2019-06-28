package com.lc.test.algorithm;

import org.junit.jupiter.api.Test;

/**
 * @Description: 斐波那契数列的获取
 * 				0,1,1,2,3,5,8,13,21.... 从第3项开始第N项为f(n-1)+f(n-2)
 * 			    要点：
 * 			    	1.排除第1项和第2项
 * 			    	2.变量交换
 * 			    	3.传递参数的时候，应该将n变为n-2，如第想得到第10位，则应该传8.
 * @author: wlc
 * @date: 2019/6/26 0026 14:39
 **/
public class FibonacciTest {

	/**定义循环的第n项*/
	static long in = 0;
	/**定义第1项*/
	static long n1 = 0;
	/**定义第2项*/
	static long n2 = 1;

	public static void main(String[] args) {
//		long startTime = System.currentTimeMillis();
		System.out.println(0);
		System.out.println(1);
		System.out.println("notRecurse result----");
		notRecurse(8);
//		System.out.println("recurse result----");
//		recurse(8);
//		long endTime = System.currentTimeMillis();
//		long totalTime = endTime-startTime;
//		System.out.println("recurse totalTime:"+totalTime+"ms");
		//recurse totalTime:5ms
//		System.out.println("notrecurse totalTime:"+totalTime+"ms");
		//notrecurse totalTime:5、6、2、3ms，运行速度差距好大啊。。


		//结论：非递归调用要比递归调用更快。。。

	}

	/**
	 * 使用非递归算法获取第N项
	 * 循环的时候需要从2开始，前2项不在循环内
	 */

	public static void notRecurse(long n){
		for (int i = 0; i < n; i++) {
			in = n1 + n2;
			n1 = n2;
			n2 = in;
			System.out.println(in);
		}
	}


	/**
	 * 使用递归算法获取第N项
	 * 第一次n传递的时候，需要减去2，因为前两项不在这里计算。
	 */
	public static void recurse(long n){
		if (n > 0) {
			in = n1 + n2;
			n1 = n2;
			n2 = in;
			System.out.println(in);
			//对n进行递减，知道n为0
			recurse(n - 1);
		}
	}


}
