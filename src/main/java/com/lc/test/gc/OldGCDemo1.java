package com.lc.test.gc;

/**
 * @desc: 老年代GC---测试动态年龄判断规则
 * 老年代和新生代均为10M
 * VM参数：-XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:SurvivorRatio=8
 * -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails
 * -XX:+PrintGCTimeStamps -Xloggc:gc.log
 * @author wlc
 * @datetime: 2022/2/27 21:18
 */
public class OldGCDemo1 {
    public static final int _1M = 1024 * 1024;
    public static void main(String[] args) {
        byte[] array1 = new byte[2*_1M];
        array1 = new byte[2*_1M];
        array1 = new byte[2*_1M];
        array1 = null;
        byte[] array2 = new byte[128 * 1024];
        byte[] array3 = new byte[2*_1M];
        //GC日志：
        //Java HotSpot(TM) 64-Bit Server VM (25.251-b08) for windows-amd64 JRE (1.8.0_251-b08), built on Mar 12 2020 06:31:49 by "" with MS VC++ 10.0 (VS2010)
        //Memory: 4k page, physical 24446012k(15171984k free), swap 28247100k(8534448k free)
        //CommandLine flags: -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:MaxNewSize=10485760 -XX:MaxTenuringThreshold=15 -XX:NewSize=10485760 -XX:OldPLABSize=16 -XX:PretenureSizeThreshold=10485760 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC
        //0.137: [GC (Allocation Failure) 0.137: [ParNew: 7600K->1024K(9216K), 0.0016237 secs] 7600K->3278K(19456K), 0.0018077 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        //Heap
        // par new generation   total 9216K, used 5570K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
        //  eden space 8192K,  55% used [0x00000000fec00000, 0x00000000ff070ae0, 0x00000000ff400000)
        //  from space 1024K, 100% used [0x00000000ff500000, 0x00000000ff600000, 0x00000000ff600000)
        //  to   space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
        // concurrent mark-sweep generation total 10240K, used 2254K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
        // Metaspace       used 3254K, capacity 4496K, committed 4864K, reserved 1056768K
        //  class space    used 350K, capacity 388K, committed 512K, reserved 1048576K

        array3 = new byte[2*_1M];
        array3 = new byte[2*_1M];
        array3 = new byte[128 * 1024];
        array3 = null;

        byte[] array4 = new byte[2*_1M];

        //运行结果：
        //Java HotSpot(TM) 64-Bit Server VM (25.251-b08) for windows-amd64 JRE (1.8.0_251-b08), built on Mar 12 2020 06:31:49 by "" with MS VC++ 10.0 (VS2010)
        //Memory: 4k page, physical 24446012k(15450508k free), swap 28247100k(7435424k free)
        //CommandLine flags: -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:MaxNewSize=10485760 -XX:MaxTenuringThreshold=15 -XX:NewSize=10485760 -XX:OldPLABSize=16 -XX:PretenureSizeThreshold=10485760 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC
        //0.144: [GC (Allocation Failure) 0.144: [ParNew: 7600K->1024K(9216K), 0.0015787 secs] 7600K->3277K(19456K), 0.0017838 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        //0.147: [GC (Allocation Failure) 0.147: [ParNew: 7536K->188K(9216K), 0.0027983 secs] 9789K->5456K(19456K), 0.0028254 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        //0.150: [GC (CMS Initial Mark) [1 CMS-initial-mark: 5267K(10240K)] 7559K(19456K), 0.0002073 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        //0.150: [CMS-concurrent-mark-start]
        //Heap
        // par new generation   total 9216K, used 4706K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
        //  eden space 8192K,  55% used [0x00000000fec00000, 0x00000000ff069788, 0x00000000ff400000)
        //  from space 1024K,  18% used [0x00000000ff400000, 0x00000000ff42f370, 0x00000000ff500000)
        //  to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
        // concurrent mark-sweep generation total 10240K, used 5267K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
        // Metaspace       used 3252K, capacity 4496K, committed 4864K, reserved 1056768K
        //  class space    used 350K, capacity 388K, committed 512K, reserved 1048576K
        //0.150: [CMS-concurrent-mark: 0.001/0.001 secs] [Times: user=0.08 sys=0.05, real=0.00 secs]
    }
}

