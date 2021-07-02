package com.lc.test.utils.zip;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.util.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Zip4jUtil {
    public static void main(String[] args) throws IOException {
        ZipFile zipFile = new ZipFile("C:\\Users\\lenovo\\Downloads\\00001.zip");
//        FileHeader fileHeader = zipFile.getFileHeader("index.json");
//        if (fileHeader == null) {
//            System.out.println("fileHeader 为空 ");
//        }
//        InputStream inputStream = zipFile.getInputStream(fileHeader);
//        System.out.println("readStream(inputStream) = " + readStream(inputStream));

        List<FileHeader> fileHeaders = new ZipFile("C:\\Users\\lenovo\\Downloads\\00001.zip").getFileHeaders();
        fileHeaders.stream().forEach(fileHeader -> {
            String fullFileName = fileHeader.getFileName();
            System.out.println("fullFileName = " + fullFileName);
            String imgStr = "img/";
            if (fullFileName.contains(imgStr)){
                String substring = fullFileName.substring(fullFileName.indexOf(imgStr) + imgStr.length());
                if (StringUtils.isNotBlank(substring)){
                    System.out.println("substring = " + substring);
                }
            }
        });
        //结果：img/
        //img/arrow.png
        //img/arrow@2x.png
        //img/arrowdown.png
        //img/arrowdown@2x.png
        //img/bg.png
        //img/bg@2x.png
        //img/prebg.png
        //img/prebg@2x.png
        //index.json


    }

    /**
     * 读取 InputStream 到 String字符串中
     */
    public static String readStream(InputStream in) {
        try {
            //<1>创建字节数组输出流，用来输出读取到的内容
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //<2>创建缓存大小 1kb
            byte[] buffer = new byte[1024];
            //每次读取到内容的长度
            int len = -1;
            //<3>开始读取输入流中的内容
            //当等于-1说明没有数据可以读取了
            while ((len = in.read(buffer)) != -1) {
                //把读取到的内容写到输出流中
                baos.write(buffer, 0, len);
            }
            //<4> 把字节数组转换为字符串
            String content = baos.toString();
            //<5>关闭输入流和输出流
            in.close();
            baos.close();
            //<6>返回字符串结果
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return  e.getMessage();
        }
    }

}
