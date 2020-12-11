package com.lc.test.baseknowlege.object;

import com.lc.test.baseknowlege.copy.Person;
import com.lc.test.entity.AllTypeEntity;
import com.lc.test.entity.User;
import org.openjdk.jol.info.ClassLayout;

/**
 * @description 计算对象大小
 * @author wlc
 * @date 2020/12/11 0011 10:29
 */
public class CalculateObjectSize {


    public static void main(String[] args) {
        String person = ClassLayout.parseClass(Person.class).toPrintable();
        //s = com.lc.test.baseknowlege.copy.Person object internals:
        // OFFSET  SIZE                                TYPE DESCRIPTION                               VALUE
        //      0    12                                     (object header)                           N/A
        //     12     4                    java.lang.String Person.name                               N/A
        //     16     4   com.lc.test.baseknowlege.copy.Dog Person.pet                                N/A
        //     20     4                                     (loss due to the next object alignment)
        //Instance size: 24 bytes
        //Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
        System.out.println("person = " + person);

        //allTypeEntity = com.lc.test.entity.AllTypeEntity object internals:
        // OFFSET  SIZE                 TYPE DESCRIPTION                               VALUE
        //      0     4                      (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
        //      4     4                      (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
        //      8     4                      (object header)                           21 cd 00 20 (00100001 11001101 00000000 00100000) (536923425)
        //     12     4    java.lang.Integer AllTypeEntity.id                          null
        //     16     4     java.lang.Double AllTypeEntity.weight                      null
        //     20     4      java.lang.Float AllTypeEntity.height                      null
        //     24     4       java.lang.Long AllTypeEntity.createTime                  1607654825040
        //     28     4      java.lang.Short AllTypeEntity.age                         null
        //     32     4       java.lang.Byte AllTypeEntity.status                      null
        //     36     4    java.lang.Boolean AllTypeEntity.available                   null
        //     40     4     java.lang.String AllTypeEntity.name                        null
        //     44     4   java.lang.String[] AllTypeEntity.array                       [(object), (object), (object)]
        //     48     4       java.util.List AllTypeEntity.props                       (object)
        //     52     4        java.util.Map AllTypeEntity.exts                        (object)
        //Instance size: 56 bytes
        //Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
        String allTypeEntity = ClassLayout.parseInstance(new AllTypeEntity()).toPrintable();
        System.out.println("allTypeEntity = " + allTypeEntity);
    }


}
