//package com.lc.test.io.img;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.net.ConnectException;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.List;
//
///**
// * @author wlc
// * @description
// * @date 2021/3/1 0001 16:47
// */
//@Slf4j
//public class ImageUtils {
//    /**
//     * 操作合成图片
//     * @param SynthesisList 操作单张合成图
//     * @return
//     */
//    public String optImage(List<SynthesisListVo> SynthesisList) {
//        try {
//            // 与前端约定  合成图片列表的第一张图片为底图
//            SynthesisListVo backgroundImageInfo = SynthesisList.get(0);
//            //设置图片大小  宽  高  网络图片
//            // 获取网络图片
//            BufferedImage getBackgroundUrlImage = this.getUrlImage(backgroundImageInfo.getImageUrl());
//            BufferedImage background = this.resizeImagePng(backgroundImageInfo.getWidth(), backgroundImageInfo.getHeight(), getBackgroundUrlImage);
//            // 底部宽
//            Integer SynthesisListVo = backgroundImageInfo.getWidth();
//            //在背景图片中添加入需要写入的信息，
//            Graphics2D g = background.createGraphics();  // x58  y74.3
//            //设置为透明覆盖
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
//            Boolean isFirst  = true;
//            for (SynthesisListVo SynthesisListItem : SynthesisList) {
//                if (isFirst) {
//                    isFirst = false;
//                    continue;
//                }
//                if (SynthesisListItem.getType().equals("Image")) {
//                    // 图片处理 创建图片
//                    // 获取网络图片
//                    BufferedImage getUrlImage = this.getUrlImage(SynthesisListItem.getImageUrl());
//                    // 重新定义图片尺寸
//                    BufferedImage userImage = this.resizeImagePng(SynthesisListItem.getWidth(), SynthesisListItem.getHeight(), getUrlImage);
//                    // 图片 偏移x  偏移y 图片的宽 图片的高
//                    g.drawImage(userImage, SynthesisListItem.getOffsetX(), SynthesisListItem.getOffsetY(), userImage.getWidth(), userImage.getHeight(), null);
//                } else if (SynthesisListItem.getType().equals("Text")) {
//                    // Font pinfang_44 = new Font("苹方-简 中黑体", Font.BOLD, 44);
//                    Font pinfang = new Font(SynthesisListItem.getTextFontFamily(), Font.PLAIN, SynthesisListItem.getTextFontSize());
//                    // 写字
//                    //指定字体
//                    FontMetrics metrics = g.getFontMetrics(pinfang);
//                    //指定要绘制的字符串
//                    String useMeg = String.valueOf(SynthesisListItem.getText());
//                    //得到字符串绘制宽度
//                    int logoW = metrics.stringWidth(useMeg);
//                    //计算绘制原点坐标，
//                    //文本最左边位于x轴logoX
//                    // 判断是否水平居中
//                    int logoX = 0;
//                    if (SynthesisListItem.getTextAlignCenter() != null && SynthesisListItem.getTextAlignCenter()) {
//                        // 水平居中
//                        logoX = (maxBackgroundWidth - logoW) / 2;
//                    } else {
//                        logoX = SynthesisListItem.getOffsetX();
//                    }
//                    //文本基于logoH
//                    int logoH = SynthesisListItem.getOffsetY() + (metrics.getAscent());
//                    //绘制文本框
//                    g.setFont(pinfang);
//                    g.setColor(new Color(SynthesisListItem.getTextColor().get(0),SynthesisListItem.getTextColor().get(1),SynthesisListItem.getTextColor().get(2)));
//                    g.drawString(useMeg, logoX, logoH);
//                }
//            }
//            g.dispose();
//            //输出图片 上传七牛
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            ImageIO.write(background, "png", out);
//            byte[] b = out.toByteArray();
//            String hash = UploadImageUtil.uploadByteImage(b);
//            if (StringUtils.isNotEmpty(hash)) {
//                // 返回图片路径
//                return UploadImageUtil.IMG_HOST + hash;
//            }
//            ImageIO.write(background,"png",new FileOutputStream())
////            ImageIO.write(background, "png", new FileOutputStream(outPutPath));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 获取网络图片数据
//     * @param url
//     * @return
//     */
//    public BufferedImage getUrlImage(String url) {
//        byte[] bytes = getImageBytes(url);
//        InputStream buffin = new ByteArrayInputStream(bytes,0,bytes.length);
//        BufferedImage img = null;
//        try {
//            img = ImageIO.read(buffin);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return img;
//    }
//
//    public static byte[] getImageBytes(String imgUrl) {
//        URL url = null;
//        InputStream is = null;
//        ByteArrayOutputStream outStream = null;
//        HttpURLConnection httpUrl = null;
//        try {
//            url = new URL(imgUrl);
//            httpUrl = (HttpURLConnection) url.openConnection();
//            httpUrl.connect();
//            httpUrl.getInputStream();
//            is = httpUrl.getInputStream();
//            outStream = new ByteArrayOutputStream();
//            //创建一个Buffer字符串
//            byte[] buffer = new byte[1024];
//            //每次读取的字符串长度，如果为-1，代表全部读取完毕
//            int len = 0;
//            //使用一个输入流从buffer里把数据读取出来
//            while ((len = is.read(buffer)) != -1) {
//                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
//                outStream.write(buffer, 0, len);
//            }
//            // 对字节数组Base64编码
//            return outStream.toByteArray();
//        } catch (ConnectException e) {
//            log.error("获取图片时连接异常，url:{}",imgUrl,e);
//        } catch (MalformedURLException e) {
//            log.error("url出现异常，url:{}",imgUrl,e);
//        } catch (IOException e) {
//            log.error("读取图片时发生异常，url:{}",imgUrl,e);
//        } finally {
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (outStream != null) {
//                try {
//                    outStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (httpUrl != null) {
//                httpUrl.disconnect();
//            }
//        }
//        return null;
//    }
//    /**
//     * 重定义图片尺寸
//     * @param x
//     * @param y
//     * @param bfi
//     * @return
//     */
//    public BufferedImage resizeImagePng(int x, int y, BufferedImage bfi) {
//        BufferedImage bufferedImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
//        bufferedImage.getGraphics().drawImage(
//                bfi.getScaledInstance(x, y, Image.SCALE_SMOOTH), 0, 0, null);
//        return bufferedImage;
//    }
//}
