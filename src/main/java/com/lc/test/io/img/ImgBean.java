package com.lc.test.io.img;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author wlc
 */
@Slf4j
public class ImgBean{

    /**
     * 组合网络图片和指定文字
     * @param s1
     * @param s2
     * @param imgNetUrl
     */
    public static void assembleImageText(String s1, String s2, String imgNetUrl){
        try{

            File file = getFile(imgNetUrl);
            Image image = ImageIO.read(file);
            int width=image.getWidth(null);
            int height=image.getHeight(null);
            //创建一个不带透明色的图像缓存对象
            BufferedImage bufferedImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            //画图
            Graphics g=bufferedImage.createGraphics();
            g.drawImage(image,0,0,width,height,null);
            //设置字体颜色
            g.setColor(Color.WHITE);
            //size字体大小
            g.setFont(new Font("STKaiti",Font.PLAIN,10));
            //width控制字体距离右侧边缘距离  height控制字体距离底部距离
            g.drawString(s1,width-200,height-200);
            g.drawString(s2,width-200,height-100);
            g.dispose();

            //将图片临时保存为文件，上传到腾讯云完毕之后，将本地文件删除
            String typeName = imgNetUrl.substring(imgNetUrl.lastIndexOf(".")+1);
            RenderedImage rendImage = bufferedImage;
            File uploadFile = new File("temp."+typeName);
            ImageIO.write(rendImage, typeName, uploadFile);
            uploadFile.delete();
//            //将图片输出到指定位置，这里放到本地
//            FileOutputStream out=new FileOutputStream("C:\\Users\\lenovo\\Pictures\\baidu\\new.png");
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            encoder.encode(bufferedImage);
//            out.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        assembleImageText("第一行字","第二行字","https://test-img.justmi.cn/cover/img/20200618070240_3.png");
    }

    @Test
    public void fontTest(){
        Font[] fonts = GraphicsEnvironment
                .getLocalGraphicsEnvironment().getAllFonts();
        for (Font f : fonts) {
            System.out.println("Name:" + f.getFontName());
        }
    }

    /**
     * 获取网络图片数据
     * @param imgNetUrl 图片网络地址
     * @return
     */
    public static BufferedImage getUrlImage(String imgNetUrl) {
        byte[] bytes = getImageBytes(imgNetUrl);
        InputStream in = new ByteArrayInputStream(bytes,0,bytes.length);
        BufferedImage img = null;
        try {
            img = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * 根据网络地址获取图片对象 File
     * @param url
     * @return
     * @throws Exception
     */
    public static File getFile(String url) throws Exception {
        String fileName = url.substring(url.lastIndexOf("."),url.length());
        File file = null;

        URL urlfile;
        InputStream inStream = null;
        OutputStream os = null;
        try {
            file = File.createTempFile("net_url", fileName);
            //下载
            urlfile = new URL(url);
            inStream = urlfile.openStream();
            os = new FileOutputStream(file);

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (null != inStream) {
                    inStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return file;
    }


    /**
     * 根据网络图片地址获取图片并转换为byte数组
     * @param imgUrl
     * @return
     */
    public static byte[] getImageBytes(String imgUrl) {
        URL url = null;
        InputStream is = null;
        ByteArrayOutputStream outStream = null;
        HttpURLConnection httpUrl = null;
        try {
            url = new URL(imgUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            httpUrl.getInputStream();
            is = httpUrl.getInputStream();
            outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = is.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            // 对字节数组Base64编码
            return outStream.toByteArray();
        } catch (ConnectException e) {
            log.error("获取图片时连接异常，url:{}",imgUrl,e);
        } catch (MalformedURLException e) {
            log.error("url出现异常，url:{}",imgUrl,e);
        } catch (IOException e) {
            log.error("读取图片时发生异常，url:{}",imgUrl,e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpUrl != null) {
                httpUrl.disconnect();
            }
        }
        return null;
    }
}