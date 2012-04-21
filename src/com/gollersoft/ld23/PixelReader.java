package com.gollersoft.ld23;

import com.gollersoft.jultragame.binding.swing.UGSwing;
import com.gollersoft.jultragame.binding.swing.display.SwingImage;
import com.gollersoft.jultragame.core.UG;
import com.gollersoft.jultragame.core.display.UGImage;

import java.awt.*;
import java.awt.image.PixelGrabber;

/**
 * Created with IntelliJ IDEA.
 * User: neosam-code
 * Date: 4/21/12
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class PixelReader {
    static Pixel[] extractPixels(Image image) {
        final int width = image.getWidth(null);
        final int height = image.getHeight(null);
        int[] pixels = new int[width * height];
        PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);

        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        final Pixel[] res = new Pixel[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int p = pixels[y * width + x];
                final int r = (p & 0xff0000) >> 16;
                final int g = (p & 0xff00) >> 8;
                final int b = p & 0xff;
                final Pixel entry = new Pixel(r, g, b);
                res[y * width + x] = entry;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        UG ug = new UGSwing(300, 200);
        UGImage img = ug.getImage("level.png");
        SwingImage swImg = (SwingImage) img;
        Pixel[] pixels = extractPixels(swImg.image);
        System.out.println(pixels[0]);
        System.out.println(pixels[1]);
        System.out.println(pixels[2]);
        System.out.println(pixels[3]);
    }
}
