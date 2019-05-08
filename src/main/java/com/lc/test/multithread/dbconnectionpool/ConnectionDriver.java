package com.lc.test.multithread.dbconnectionpool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * 仅仅演示一下，不需要真正的获取数据库连接，通过动态代理创建一个。
 * @author wlc
 */
public class ConnectionDriver {
    static class ConnectionHandler implements InvocationHandler{
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("commit".equals(method.getName())){
                //只是睡眠一下
                TimeUnit.MILLISECONDS.sleep(100);
            }
            return null;
        }
    }
    //创建一个Connection的代理，在commit时休眠100毫秒
    public static final Connection createConnection(){
        return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),
                new Class<?>[]{Connection.class},
                new ConnectionHandler());
    }
}
