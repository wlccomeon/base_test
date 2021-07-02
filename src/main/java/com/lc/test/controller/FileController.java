package com.lc.test.controller;

import com.lc.test.utils.zip.PackageTypeEnum;
import com.lc.test.utils.zip.Zip4jUtil;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * 关于File的练习
 * @author wlc
 */
@Slf4j
@Controller
@RequestMapping(value = "/file")
public class FileController {

    @GetMapping("/test")
    public void testPrint(String str){
        System.out.println("str = " + str);
    }

    @PostMapping(value = "upload/zip",headers = "content-type=multipart/*")
    public void uploadZip(HttpServletRequest request,@RequestParam(value="file") MultipartFile multipartFile){
        if (null == multipartFile) {
            System.out.println("请上传压缩文件");
            return;
        }
        try{
            boolean isZipPack = true;
            String fileContentType = multipartFile.getContentType();
            System.out.println("fileContentType = " + fileContentType);
            //将压缩包保存在指定路径
//            String packFilePath = "packParam.getDestPath()" + File.separator + multipartFile.getOriginalFilename();
//            System.out.println("packFilePath = " + packFilePath);
//            if (PackageTypeEnum.ZIP.type.equals(fileContentType)) {
//                //zip解压缩处理
//                packFilePath += PackageTypeEnum.ZIP.fileSuffix;
//            }else{
//                System.out.println("请使用zip格式的压缩包上传");
//                return;
//            }
            String originalFilename = multipartFile.getOriginalFilename();
            if (!originalFilename.contains(PackageTypeEnum.ZIP.fileSuffix)){
                System.out.println("请使用zip格式的压缩包上传");
                return;
            }
            //在项目中需要改成 "/tmp/"
            String imgRoot = "D:\\var";
            String filename = imgRoot + multipartFile.getOriginalFilename()+ System.currentTimeMillis()+".zip";
            File file = new File(filename);
            multipartFile.transferTo(file);
            ZipFile zipFile = new ZipFile(file);
            List<FileHeader> fileHeaders = zipFile.getFileHeaders();
            fileHeaders.stream().forEach(fileHeader -> System.out.println(fileHeader.getFileName()));
            for (FileHeader fileHeader : fileHeaders) {
                String fileName = fileHeader.getFileName();
                //为index.json文件则读取内容
                if ("index.json".equals(fileName)) {
                    InputStream inputStream = zipFile.getInputStream(fileHeader);
                    System.out.println("readStream(inputStream) = " + Zip4jUtil.readStream(inputStream));
                }else{
                    //其他的则为图片
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
