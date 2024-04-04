package io.windfree.thread.perf.image.process;

import javax.crypto.spec.PSource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String SOURCE_IMG =  "src/main/resources/many-flowers.jpg";
    public static final String DESTINATION_IMG = "target/many-flowers.jpg";
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        BufferedImage originalImage = ImageIO.read(new File(SOURCE_IMG));
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(),originalImage.getHeight(),BufferedImage.TYPE_INT_RGB);

        long startTime = System.currentTimeMillis();
        //recolorSingleThread(originalImage, resultImage);
        int numberOfThreads = 4;
        recolorMultithreaded(originalImage, resultImage, numberOfThreads);
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;

        File outputFile = new File(DESTINATION_IMG);
        ImageIO.write(resultImage, "jpg", outputFile);

        System.out.println(String.valueOf(duration));
    }

    public static void recolorMultithreaded(BufferedImage originalImage, BufferedImage resultImage, int numberOfThreads) {
        List<Thread> threads = new ArrayList<>();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight() / numberOfThreads;

        for(int i = 0; i < numberOfThreads ; i++) {
            final int threadMultiplier = i;

            Thread thread = new Thread(() -> {
                int xOrigin = 0 ;
                int yOrigin = height * threadMultiplier;

                recolorImage(originalImage, resultImage, xOrigin, yOrigin, width, height);
            });

            threads.add(thread);
        }

        for(Thread thread : threads) {
            thread.start();
        }

        for(Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
        }
    }

    public static  void recolorSingleThread(BufferedImage originalImage, BufferedImage resultImage) {
        recolorImage(originalImage, resultImage, 0,0, originalImage.getWidth(), originalImage.getHeight());
    }

    public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage,
                                   int leftCorner, int topCorner, int width, int height) {
        for (int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x++) {
            for (int y = topCorner; y < topCorner + height && y < originalImage.getHeight(); y++) {
                recolorPixel(originalImage, resultImage, x, y );
                recolorPixel(originalImage, resultImage, x, y );
            }
        }
    }


    public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
        int rgb = originalImage.getRGB(x,y);
        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed, newGreen, newBlue;

        if (isShadeGray(red,green,blue)) {
            newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green - 80);
            newBlue = Math.max(0, blue - 20);
        } else {
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }
        int newRGB = createRGBFromColors(newRed,newGreen,newBlue);
        setRGB(resultImage, x, y , newRGB);

    }

    public static  void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x,y, image.getColorModel().getDataElements(rgb,null));
    }


    public static int getBlue(int rgb) {
        return rgb &  0x000000FF;
    }

    public  static  int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;
    }

    public  static  int getGreen(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

     public  static int createRGBFromColors(int red, int green, int blue) {
        int rgb = 0;
        rgb |= blue;
        rgb |= green << 8;
        rgb |= red << 16;

        rgb |= 0xFF000000;
        return rgb;
     }

    /**
     * 회색에 근접한 색깔임을 판단하게 하는 메서드
     * @param red
     * @param green
     * @param blue
     * @return
     */
     public static boolean isShadeGray(int red, int green , int blue) {
        return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
     }

}
