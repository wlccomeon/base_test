package com.lc.test.designpattern.proxy;

import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author wlc
 * @desc: 测试动态代理生成的代理类
 * @datetime: 2022/3/31 18:24
 */
public class JDKProxySourceTest {

    public static void main(String[] args) throws Exception {
        //只能使用接口强转，否则的话会报：java.lang.ClassCastException: com.sun.proxy.$Proxy0 cannot be cast to com.lc.test.designpattern.proxy.Math
        IMath math = (IMath) new JdkDynamicProxy().getProxyObject(new Math());
        math.add(1,2);
        //通过反编译工具jad可以查看源代码$Proxy0.class（$Proxy0是生成的代理类的名称）
        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0",new Class[]{IMath.class});
        FileOutputStream os = new FileOutputStream("D://$Proxy0.class");
        os.write(bytes);
        os.close();
        //反编译结果：
        //源码当中的h就是InvocationHandler，这里其实就是它的实现类JdkDynamicProxy
        //// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
        //// Jad home page: http://www.kpdus.com/jad.html
        //// Decompiler options: packimports(3)
        //
        //import com.lc.test.designpattern.proxy.IMath;
        //import java.lang.reflect.*;
        //
        //public final class $Proxy0 extends Proxy
        //    implements IMath
        //{
        //
        //    public $Proxy0(InvocationHandler invocationhandler)
        //    {
        //        super(invocationhandler);
        //    }
        //
        //    public final boolean equals(Object obj)
        //    {
        //        try
        //        {
        //            return ((Boolean)super.h.invoke(this, m1, new Object[] {
        //                obj
        //            })).booleanValue();
        //        }
        //        catch(Error _ex) { }
        //        catch(Throwable throwable)
        //        {
        //            throw new UndeclaredThrowableException(throwable);
        //        }
        //    }
        //
        //    public final String toString()
        //    {
        //        try
        //        {
        //            return (String)super.h.invoke(this, m2, null);
        //        }
        //        catch(Error _ex) { }
        //        catch(Throwable throwable)
        //        {
        //            throw new UndeclaredThrowableException(throwable);
        //        }
        //    }
        //
        //    public final int add(int i, int j)
        //    {
        //        try
        //        {
        //            return ((Integer)super.h.invoke(this, m3, new Object[] {
        //                Integer.valueOf(i), Integer.valueOf(j)
        //            })).intValue();
        //        }
        //        catch(Error _ex) { }
        //        catch(Throwable throwable)
        //        {
        //            throw new UndeclaredThrowableException(throwable);
        //        }
        //    }
        //
        //    public final int mut(int i, int j)
        //    {
        //        try
        //        {
        //            return ((Integer)super.h.invoke(this, m4, new Object[] {
        //                Integer.valueOf(i), Integer.valueOf(j)
        //            })).intValue();
        //        }
        //        catch(Error _ex) { }
        //        catch(Throwable throwable)
        //        {
        //            throw new UndeclaredThrowableException(throwable);
        //        }
        //    }
        //
        //    public final int sub(int i, int j)
        //    {
        //        try
        //        {
        //            return ((Integer)super.h.invoke(this, m6, new Object[] {
        //                Integer.valueOf(i), Integer.valueOf(j)
        //            })).intValue();
        //        }
        //        catch(Error _ex) { }
        //        catch(Throwable throwable)
        //        {
        //            throw new UndeclaredThrowableException(throwable);
        //        }
        //    }
        //
        //    public final int div(int i, int j)
        //    {
        //        try
        //        {
        //            return ((Integer)super.h.invoke(this, m5, new Object[] {
        //                Integer.valueOf(i), Integer.valueOf(j)
        //            })).intValue();
        //        }
        //        catch(Error _ex) { }
        //        catch(Throwable throwable)
        //        {
        //            throw new UndeclaredThrowableException(throwable);
        //        }
        //    }
        //
        //    public final int hashCode()
        //    {
        //        try
        //        {
        //            return ((Integer)super.h.invoke(this, m0, null)).intValue();
        //        }
        //        catch(Error _ex) { }
        //        catch(Throwable throwable)
        //        {
        //            throw new UndeclaredThrowableException(throwable);
        //        }
        //    }
        //
        //    private static Method m1;
        //    private static Method m2;
        //    private static Method m3;
        //    private static Method m4;
        //    private static Method m6;
        //    private static Method m5;
        //    private static Method m0;
        //
        //    static
        //    {
        //        try
        //        {
        //            m1 = Class.forName("java.lang.Object").getMethod("equals", new Class[] {
        //                Class.forName("java.lang.Object")
        //            });
        //            m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
        //            m3 = Class.forName("com.lc.test.designpattern.proxy.IMath").getMethod("add", new Class[] {
        //                Integer.TYPE, Integer.TYPE
        //            });
        //            m4 = Class.forName("com.lc.test.designpattern.proxy.IMath").getMethod("mut", new Class[] {
        //                Integer.TYPE, Integer.TYPE
        //            });
        //            m6 = Class.forName("com.lc.test.designpattern.proxy.IMath").getMethod("sub", new Class[] {
        //                Integer.TYPE, Integer.TYPE
        //            });
        //            m5 = Class.forName("com.lc.test.designpattern.proxy.IMath").getMethod("div", new Class[] {
        //                Integer.TYPE, Integer.TYPE
        //            });
        //            m0 = Class.forName("java.lang.Object").getMethod("hashCode", new Class[0]);
        //        }
        //        catch(NoSuchMethodException nosuchmethodexception)
        //        {
        //            throw new NoSuchMethodError(nosuchmethodexception.getMessage());
        //        }
        //        catch(ClassNotFoundException classnotfoundexception)
        //        {
        //            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        //        }
        //    }
        //}
    }

}

