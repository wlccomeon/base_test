package com.lc.test.baseknowlege.copy;

import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;

/**
 * @author wlc
 * @desc
 * @date 2019-09-29 21:16:27
 */
public class DeepCopyTest {

	@Test
	public void test() throws Exception{
		Dog pet = new Dog("jingba");
		Person p1 = new Person("xiaoming", pet);
		System.out.println(p1.getPet());
		System.out.println(p1.getPet().getName());
		Person p2 = (Person) ObjectCopyUtil.deeplyCopy(p1);
		System.out.println(p2.getPet());
		System.out.println(p2.getPet().getName());
	}

	@Test
	public void testSerializationUtil(){
		Dog pet = new Dog("金毛");
		Person p1 = new Person("张三", pet);
		Dog oldPet = SerializationUtils.clone(p1.getPet());
		Dog oldPet2 = p1.getPet();
		Dog chinaDog = new Dog("中华田园犬");
		Person p2 = new Person("李四", chinaDog);
		try {
			BeanUtils.copyProperties(p1.getPet(),chinaDog);
			System.out.println("JSON.toJSONString(p1.getPet()) = " + JSON.toJSONString(p1.getPet()));
			System.out.println("oldPet.getName() = " + oldPet.getName());
			System.out.println("oldPet2.getName() = " + oldPet2.getName());
		}catch (Exception e){

		}

		System.out.println(p2.getPet());
		System.out.println(p2.getPet().getName());
	}

}
