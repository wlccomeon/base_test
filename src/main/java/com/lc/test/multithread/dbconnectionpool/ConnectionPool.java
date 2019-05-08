package com.lc.test.multithread.dbconnectionpool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 模拟数据库连接池的作用
 */
public class ConnectionPool {
    /**使用双向队列*/
    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initialSize){
        if (initialSize>0){
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    public void releaseConnection(Connection connection){
        if (connection!=null){
            synchronized (pool){
                //链接释放后需要进行通知，这样其它消费者能够感知到连接池已经归还了一个连接
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    public Connection fetchConnection(long mills) throws InterruptedException{
        synchronized (pool){
            //完全超时
            if (mills<=0){
                while (pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else{
                //如果有指定超时时间，则在连接池为空的时候判定在该时间内是否可以获取到连接，获取不到，则返回null
                long future = System.currentTimeMillis()+mills;
                long remaining = mills;
                while (pool.isEmpty()&&remaining>0){
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                //如果有其他线程释放了连接，则从队列中获取第一个
                if (!pool.isEmpty()){
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }

}
