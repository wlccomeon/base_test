package com.lc.test.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * @Description: redis事务测试
 * @author: wlc
 * @date: 2019/7/1 0001 22:10
 **/
public class TransactionTest {

	public boolean transMethod() {
		String host = "192.168.20.139";
		int port = 6380;
		Jedis jedis = new Jedis(host, port);

		int balance;// 可用余额
		int debt;// 欠额
		int amtToSubtract = 10;// 实刷额度

		jedis.watch("balance");
		//如果在下面代码执行之前另一个线程修改了balance的值，而且小于10块钱。那么这里肯定无法修改余额。
		balance = Integer.parseInt(jedis.get("balance"));
		//如果在上面的代码(balance的值小于10)执行之后修改了balance，那么balance < amtToSubtract这句将得到错误的结果(这个问题怎么修正？)
//		jedis.set("balance","11");
		if (balance < amtToSubtract) {
			jedis.unwatch();
			System.out.println("modify");
			return false;
		} else {
			System.out.println("***********transaction");
			Transaction transaction = jedis.multi();
			transaction.decrBy("balance", amtToSubtract);
			transaction.incrBy("debt", amtToSubtract);
			List<Object> execResult = transaction.exec();
			System.out.println("execResult--->>"+execResult.toString());
			balance = Integer.parseInt(jedis.get("balance"));
			debt = Integer.parseInt(jedis.get("debt"));

			System.out.println("*******" + balance);
			System.out.println("*******" + debt);
			return true;
		}
	}

	/**
	 * 通俗点讲，watch命令就是标记一个键，如果标记了一个键，
	 * 在提交事务前如果该键被别人修改过，那事务就会失败，这种情况通常可以在程序中
	 * 重新再尝试一次。
	 * 首先标记了键balance，然后检查余额是否足够，不足就取消标记，并不做扣减；
	 * 足够的话，就启动事务进行更新操作，
	 * 如果在此期间键balance被其它人修改， 那在提交事务（执行exec）时就会报错，
	 * 程序中通常可以捕获这类错误再重新执行一次，直到成功。
	 */
	public static void main(String[] args) {
		TransactionTest test = new TransactionTest();
		boolean retValue = test.transMethod();
		System.out.println("main retValue-------: " + retValue);
	}



}
