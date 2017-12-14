package com.winsky.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * author: ysk13
 * date: 2017-7-20
 * description:
 */
public class ImageUtil {
    /**
     * 直接指定压缩后的宽高：
     *
     * @param oldFile   要进行压缩的文件全路径
     * @param width     压缩后的宽度
     * @param height    压缩后的高度
     * @param quality   压缩质量
     * @param smallIcon 文件名的小小前缀,如压缩文件名是yasuo.jpg,则压缩后文件名是smallIcon_yasuo.jpg
     * @return 返回压缩后的文件的全路径
     */
    public static String zipImageFile(String oldFile, int width, int height, float quality, String smallIcon) {
        if (oldFile == null) {
            return null;
        }
        String newImage = null;
        FileOutputStream out = null;
        try {
            /*对服务器上的临时文件进行处理 */
            Image srcFile = ImageIO.read(new File(oldFile));
            /* 宽,高设定 */
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
            /* 压缩后的文件名 */
            newImage = rename(oldFile, smallIcon);
            /* 压缩之后临时存放位置 */
            out = new FileOutputStream(newImage);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
            /* 压缩质量 */
            jep.setQuality(quality, true);
            encoder.encode(tag, jep);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException ignored) {
            }
        }
        return newImage;
    }

    /**
     * 等比例压缩算法：
     * 算法思想：根据压缩基数和压缩比来压缩原图，生产一张图片效果最接近原图的缩略图
     *
     * @param srcURL    原图地址
     * @param comBase   压缩基数,越大图片越大
     * @param scale     压缩限制(宽/高)比例  一般用1：
     *                  当scale>=1,缩略图height=comBase,width按原图宽高比例;若scale<1,缩略图width=comBase,height按原图宽高比例
     * @param smallIcon 文件名的小小前缀,如压缩文件名是yasuo.jpg,则压缩后文件名是smallIcon_yasuo.jpg
     * @throws Exception
     */
    public static void saveMinPhoto(String srcURL, double comBase, double scale, String smallIcon) throws Exception {
        File srcFile = new File(srcURL);
        Image src = ImageIO.read(srcFile);
        int srcHeight = src.getHeight(null);
        int srcWidth = src.getWidth(null);
        int deskHeight;// 缩略图高
        int deskWidth;// 缩略图宽
        double srcScale = (double) srcHeight / srcWidth;
        /*缩略图宽高算法*/
        if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
            if (srcScale >= scale || 1 / srcScale > scale) {
                if (srcScale >= scale) {
                    deskHeight = (int) comBase;
                    deskWidth = srcWidth * deskHeight / srcHeight;
                } else {
                    deskWidth = (int) comBase;
                    deskHeight = srcHeight * deskWidth / srcWidth;
                }
            } else {
                if ((double) srcHeight > comBase) {
                    deskHeight = (int) comBase;
                    deskWidth = srcWidth * deskHeight / srcHeight;
                } else {
                    deskWidth = (int) comBase;
                    deskHeight = srcHeight * deskWidth / srcWidth;
                }
            }
        } else {
            deskHeight = srcHeight;
            deskWidth = srcWidth;
        }
        BufferedImage tag = new BufferedImage(deskWidth, deskHeight, BufferedImage.TYPE_3BYTE_BGR);
        tag.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null); //绘制缩小后的图
        FileOutputStream destImage = null;
        try {
            String newName = rename(srcURL, smallIcon);
            destImage = new FileOutputStream(newName); //输出到文件流
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(destImage);
            encoder.encode(tag); //近JPEG编码
        } finally {
            if (destImage != null) destImage.close();
        }
    }

    /**
     * 对文件重命名,在文件名上添加上前缀
     *
     * @param old       原文件名的全路径
     * @param smallIcon 重命名文件的前缀
     * @return 重命名后的文件名
     */
    private static String rename(String old, String smallIcon) {
        File file = new File(old);
        return file.getParent() + smallIcon + "_" + file.getName();
    }

    public static void main(String args[]) throws Exception {
        ImageUtil.zipImageFile("D:/666666.jpg", 1280, 1280, 1f, "s");
        ImageUtil.saveMinPhoto("D:/666666.jpg", 500, 1, "ss_");
    }
}