package com.lc.test.baseknowlege.object;

import com.lc.test.baseknowlege.copy.Person;
import com.lc.test.baseknowlege.polymorphic.A;
import com.lc.test.entity.AllTypeEntity;
import com.lc.test.entity.User;
import org.openjdk.jol.info.ClassLayout;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;

/**
 * @description 计算对象大小
 * @author wlc
 * @date 2020/12/11 0011 10:29
 */
public class CalculateObjectSize {

    private static Instrumentation instrumentation;

    public static void main(String[] args) {
        String person = ClassLayout.parseClass(Person.class).toPrintable();
        //s = com.lc.test.baseknowlege.copy.Person object internals:
        // OFFSET  SIZE                                TYPE DESCRIPTION                               VALUE
        //      0    12                                     (object header)                           N/A
        //     12     4                    java.java.lang.String Person.name                               N/A
        //     16     4   com.lc.test.baseknowlege.copy.Dog Person.pet                                N/A
        //     20     4                                     (loss due to the next object alignment)
        //Instance size: 24 bytes
        //Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
        System.out.println("person = " + person);

        AllTypeEntity allTypeEntity = createEntity();
        String calResult = ClassLayout.parseInstance(allTypeEntity).toPrintable();
        //allTypeEntity = com.lc.test.entity.AllTypeEntity object internals:
        // OFFSET  SIZE                 TYPE DESCRIPTION                               VALUE
        //      0     4                      (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
        //      4     4                      (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
        //      8     4                      (object header)                           21 cd 00 20 (00100001 11001101 00000000 00100000) (536923425)
        //     12     4    java.java.lang.Integer AllTypeEntity.id                          223
        //     16     4     java.java.lang.Double AllTypeEntity.weight                      null
        //     20     4      java.java.lang.Float AllTypeEntity.height                      20.8
        //     24     4       java.java.lang.Long AllTypeEntity.createTime                  1608086720103
        //     28     4      java.java.lang.Short AllTypeEntity.age                         10
        //     32     4       java.java.lang.Byte AllTypeEntity.status                      1
        //     36     4    java.java.lang.Boolean AllTypeEntity.available                   true
        //     40     4     java.java.lang.String AllTypeEntity.name                        (object)
        //     44     4   java.java.lang.String[] AllTypeEntity.array                       [(object), (object), (object)]
        //     48     4       java.util.List AllTypeEntity.props                       (object)
        //     52     4        java.util.Map AllTypeEntity.exts                        (object)
        //Instance size: 56 bytes
        //Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
        System.out.println("allTypeEntity = " + calResult);

        //当对象属性由包装类转换为基础类型的时候，结果如下：
        // OFFSET  SIZE                 TYPE DESCRIPTION                               VALUE
        //      0     4                      (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
        //      4     4                      (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
        //      8     4                      (object header)                           43 c1 00 20 (01000011 11000001 00000000 00100000) (536920387)
        //     12     4                  int AllTypeEntity.id                          223
        //     16     8               double AllTypeEntity.weight                      0.0
        //     24     8                 long AllTypeEntity.createTime                  1608088389031
        //     32     4                float AllTypeEntity.height                      20.8
        //     36     2                short AllTypeEntity.age                         10
        //     38     1                 byte AllTypeEntity.status                      1
        //     39     1              boolean AllTypeEntity.available                   true
        //     40     4     java.java.lang.String AllTypeEntity.name                        (object)
        //     44     4   java.java.lang.String[] AllTypeEntity.array                       [(object), (object), (object)]
        //     48     4       java.util.List AllTypeEntity.props                       (object)
        //     52     4        java.util.Map AllTypeEntity.exts                        (object)
        //Instance size: 56 bytes
        //Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

//        long objectSize = getObjectSize(allTypeEntity);
//        System.out.println("objectSize = " + objectSize);
    }

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object o) {
        return instrumentation.getObjectSize(o);
    }

    /**
     * 创建一个实体类
     * @return
     */
    public static AllTypeEntity createEntity(){
        AllTypeEntity allTypeEntity = new AllTypeEntity();
        allTypeEntity.setAge((short) 10);
        allTypeEntity.setAvailable(true);
        allTypeEntity.setCreateTime(System.currentTimeMillis());
        allTypeEntity.setHeight((float)20.8);
        allTypeEntity.setId(223);
        allTypeEntity.setName("测试对象大小");
        allTypeEntity.setStatus((byte) 1);
        allTypeEntity.setProps(Arrays.asList("1","2","3"));
        allTypeEntity.setArray(new String[]{"3003o","30ddo","eik20"});
        return allTypeEntity;
    }


}
