package com.lc.test.gc;

/**
 * @desc: 对发生YoungGC的测试
 * Eden:4M,S0:0.5M,S1:0.5M,Old:4M
 * VM参数:-XX:NewSize=5242880 -XX:MaxNewSize=5242880 -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:SurvivorRatio=8
 * -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 * @author wlc
 * @datetime: 2022/2/27 18:12
 */
public class YoungGCDemo1 {

    public static final int _1M = 1024 * 1024;

    public static void main(String[] args) {
        byte[] array1 = new byte[_1M];
        array1 = new byte[_1M];
        array1 = new byte[_1M];
        array1 = null;
        byte[] array2 = new byte[2*_1M];
        //运行结果：
        //Java HotSpot(TM) 64-Bit Server VM (25.251-b08) for windows-amd64 JRE (1.8.0_251-b08), built on Mar 12 2020 06:31:49 by "" with MS VC++ 10.0 (VS2010)
        //Memory: 4k page, physical 24446012k(14951964k free), swap 28247100k(8596076k free)
        //CommandLine flags: -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:MaxNewSize=5242880 -XX:NewSize=5242880 -XX:OldPLABSize=16 -XX:PretenureSizeThreshold=10485760 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC

        //不明白为什么发生了两次Allocation Failure((4608K)代表的是新生代可用总空间是4.5M=Eden+S0区，(9728K)代表的是整个堆内存可用空间9.5M=Eden+S0+Old)
        //0.135: [GC (Allocation Failure) 0.135: [ParNew: 3207K->511K(4608K), 0.0031433 secs] 3207K->1175K(9728K), 0.0035811 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        //0.139: [GC (Allocation Failure) 0.139: [ParNew: 3702K->103K(4608K), 0.0008703 secs] 4366K->1275K(9728K), 0.0009135 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        //Heap
        // par new generation   total 4608K, used 2221K [0x00000000ff600000, 0x00000000ffb00000, 0x00000000ffb00000)

        // array1的对象全部被清除掉了，Eden区最后保留了array2的对象
        //  eden space 4096K,  51% used [0x00000000ff600000, 0x00000000ff811638, 0x00000000ffa00000)
        //  from space 512K,  20% used [0x00000000ffa00000, 0x00000000ffa19fe0, 0x00000000ffa80000)
        //  to   space 512K,   0% used [0x00000000ffa80000, 0x00000000ffa80000, 0x00000000ffb00000)
        // concurrent mark-sweep generation total 5120K, used 1171K [0x00000000ffb00000, 0x0000000100000000, 0x0000000100000000)
        // Metaspace       used 3220K, capacity 4496K, committed 4864K, reserved 1056768K
        //  class space    used 350K, capacity 388K, committed 512K, reserved 1048576K
    }

}

