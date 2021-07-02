package com.lc.test.utils.zip;

import com.twelvemonkeys.io.FileUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipUtil {

    public static void main(String[] args) {
        File file = new File("C:\\Users\\lenovo\\Downloads\\00001.zip");
        ZipFile zipFile = null;
        ZipInputStream zin = null;
        FileInputStream fis = null;
        try {
            Charset gbk = Charset.forName("GBK");
            zipFile = new ZipFile(file, gbk);
            fis = new FileInputStream(file);
            zin = new ZipInputStream(fis, gbk);

            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                String path = ze.getName();
                System.out.println(path);
                if (!ze.isDirectory() && ze.toString().endsWith("txt")) {
                    InputStream inputStream = zipFile.getInputStream(ze);
                    List<String> texts = IOUtils.readLines(inputStream);
                    for (String text : texts) {
                        System.out.println("  " + text);
                    }
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (zin != null) {
                    zin.closeEntry();
                    zin.close();
                }
                if (fis != null)
                    fis.close();
                if (zipFile != null)
                    zipFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
//    public static void main(String[] args) throws Exception {
//        try {
//            readZipFile("C:\\Users\\lenovo\\Downloads\\00001.zip");
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    public static void readZipFile(String file) throws Exception {
//        ZipFile zf = new ZipFile(file);
//        InputStream in = new BufferedInputStream(new FileInputStream(file));
//        ZipInputStream zin = new ZipInputStream(in);
//        ZipEntry ze;
//        //Exception: only DEFLATED entries can have EXT descriptor
//        while ((ze = zin.getNextEntry()) != null) {
//            if (ze.isDirectory()) {
//            } else {
//                System.err.println("file - " + ze.getName() + " : "
//                        + ze.getSize() + " bytes");
//                long size = ze.getSize();
//                if (size > 0) {
//                    BufferedReader br = new BufferedReader(
//                            new InputStreamReader(zf.getInputStream(ze)));
//                    String line;
//                    while ((line = br.readLine()) != null) {
//                        System.out.println(line);
//                    }
//                    br.close();
//                }
//                System.out.println();
//            }
//        }
//        zin.closeEntry();
//    }


    //读取zip文件内的文件,返回文件名称列表
    public static List<String> readZipFileName(String path){
        List<String> list = new ArrayList<>();
        try {
            ZipFile zipFile = new ZipFile(path);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                list.add(entries.nextElement().getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    //读取zip文件内的文件,返回文件内容列表
    public static List<String> readZipFile(String path){
        List<String> list = new ArrayList<>();
        List<List<String>> ddlList=null;
        try {
            ZipFile zipFile = new ZipFile(path);
            InputStream in = new BufferedInputStream(new FileInputStream(path));
            ZipInputStream zin = new ZipInputStream(in);
            ZipEntry ze;
            while ((ze = zin.getNextEntry()) != null) {
                ddlList=new ArrayList<>();
                if (ze.isDirectory()) {
                } else {
                    System.err.println("file - " + ze.getName() + " : "+ ze.getSize() + " bytes");
                    long size = ze.getSize();
                    if (size > 0) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(zipFile.getInputStream(ze), Charset.forName("gbk")));
                        String line;
                        while ((line = br.readLine()) != null) {
                            String[] index = line.split(",");
                            List<String> indexList = Arrays.asList(index);
                            ddlList.add(indexList);
                        }
                        br.close();
                    }
                }
                //处理ddlList,此时ddlList为每个文件的内容,while每循环一次则读取一个文件
            }
            zin.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //此处返回无用,懒得修改了
        return list;
    }

    //保存zip文件
    public String saveZipFile(MultipartFile file, String path) {
        String resultPath = "";
        ZipInputStream zipInputStream = null;
        FileOutputStream fs = null;
        try {
            resultPath = "D:" + path+"/" + file.getOriginalFilename();
            File zipFile = new File(resultPath);
            if (!zipFile.exists()) {
                new File(zipFile.getParent()).mkdirs();
                zipFile.createNewFile();
            }
            zipInputStream = new ZipInputStream(file.getInputStream(), Charset.forName("GBK"));
            BufferedInputStream stream = new BufferedInputStream(file.getInputStream());
            fs = new FileOutputStream(zipFile);
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = stream.read(buffer)) != -1) {
                fs.write(buffer, 0, i);
                fs.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                zipInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultPath;
    }

    public int getZipFileCount(String zipFilePath) {
        ZipFile zf = null;
        int count = 0;
        try {
            zf = new ZipFile(zipFilePath);
            count = zf.size();     //返回zip文件中的条目数
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                zf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    /**
     * 解压zip压缩包
     * @param path  要解压的文件路径
     * @param name  要解压的文件名
     * @param resultPath    解压后文件存放路径
     */
    public static final void unzip(String path,String name,String resultPath,String code){
        try {
            ZipFile zipFile = new ZipFile(path+"/"+name);
            InputStream in = new BufferedInputStream(new FileInputStream(path+"/"+name));
            ZipInputStream zin = new ZipInputStream(in);
            ZipEntry ze;
            while ((ze = zin.getNextEntry()) != null) {
                System.err.println("file - " + ze.getName() + " : "+ ze.getSize() + " bytes");
                //创建文件
                String newTextPath = resultPath+"/"+ze.getName();
                long size = ze.getSize();
                if (size > 0) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(zipFile.getInputStream(ze), Charset.forName(code)));
                    String line;
                    while ((line = br.readLine()) != null) {
//                        FileUtil.appendString(line+"\r\n", newTextPath, "UTF-8");
                    }
                    br.close();
                }
            }
            zin.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
