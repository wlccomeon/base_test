package com.lc.test.io;

import org.apache.tools.ant.filters.StringInputStream;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @desc 流对字符串的操作
 * @author wlc
 * @date 2020-08-31 11:04:38
 */
public class StringSreamTest {
	public static void main(String[] args) {
		StringInputStream sis = new StringInputStream("原来如此换行之后的字符串");
		copyStringTest(sis);
	}

	public static void copyStringTest(InputStream is){
		/*BufferedReader相当于一个大桶，其实就是内存，这里实现了大量大量的读写 ，而不是读一个字节或字符就直接写如硬盘，加强了对硬盘的保护。*/
		BufferedReader bf = null;
		try{
			while(true){
				bf = new BufferedReader(new InputStreamReader(is));
				/*System.in 为标准输入，System.out为标准输出*/
				/*InputStreamReader用于将字节流到字符流的转化，这也就是处理流了
				 *在这里相当与2个管道接在System.in与程序之间。
				 *readLine()方法功能比较好用，也就通过处理流来实现更好功能。
				 **/
				String line = bf.readLine();
				System.out.println(line);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//一定要关闭流，用完后。最好放在
			try{
				if(bf!=null){
					bf.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
