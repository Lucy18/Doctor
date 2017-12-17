package com.winsky;

/**
 * author: winsky
 * date: 2017/12/14
 * description:
 */
public class Config {
    /**
     * 服务器地址
     */
    public static final String HOST = "http://www.ufengtech.xyz/";

    /**
     * 图片顶层分类的pid
     */
    public static final int INIT_CATEGORY_PID = 0;

    /**
     * 服务器上图片存储目录
     */
    public static final String PIC_PATH = "/home/tongue";

    /**
     * 图片访问的基础地址，后直接加图片名称就好了
     */
    public static final String PIC_BASE_URL = HOST + "tongue/";

    // TODO: 2017/12/14 需要指定默认图片
    /**
     * 默认的图片地址
     */
    public static final String DEFAULT_PIC_URL = PIC_BASE_URL + "1.jpeg";


    /**
     * 客户端提交的多个图片的分隔符
     */
    public static final String PIC_SPLIT = ",";

    /**
     * 医生的openId
     */
    public static final String DOCTOR_OPEN_ID = "0";
}
