package com.lc.test.io.img;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;

/**
 * @author wlc
 * @description
 * @date 2021/3/2 0002 15:34
 */
public class ImgTest {


    public static void main(String[] args) throws Exception{
            int width = 100;

            int height = 100;

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            Graphics2D g2d = bufferedImage.createGraphics();

            g2d.setColor(Color.white);

            g2d.fillRect(0, 0, width, height);

            g2d.setColor(Color.black);

            g2d.fillOval(0, 0, width, height);

            g2d.dispose();

            RenderedImage rendImage = bufferedImage;

            File file = new File("newimage1.png");

            ImageIO.write(rendImage, "png", file);
            file.delete();

            file = new File("newimage1.jpg");

            ImageIO.write(rendImage, "jpg", file);
    }

}
