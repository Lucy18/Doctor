package com.winsky.utils;

import com.winsky.Config;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * author: ysk13
 * date: 2017-6-6
 * description: 文件上传
 */
public class FileUploadUtil {
    private static final List<String> ALLOW_TYPES = Arrays.asList("image/jpg", "image/jpeg", "image/png", "image/gif");

    /**
     * @param file
     * @param targetDir
     * @return
     */
    public static String upload(MultipartFile file, String targetDir) {
        String fileUrl = null;
        if (file != null) {
            String myFileName = file.getOriginalFilename();
            if (!Objects.equals(myFileName.trim(), "")) {
                String fileName = rename(myFileName);
                //定义上传路径
                String path = targetDir + "/" + fileName;
                File localFile = new File(path);
                try {
                    file.transferTo(localFile);

                    fileUrl = Config.PIC_BASE_URL + fileName;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileUrl;
    }

    //文件重命名
    private static String rename(String fileName) {
        int i = fileName.lastIndexOf(".");
        String str = fileName.substring(i);
        return System.currentTimeMillis() / 1000 + "" + new Random().nextInt(99999999) + str;
    }

    //校验文件类型是否是被允许的
    public static boolean allowUpload(String postfix) {
        return ALLOW_TYPES.contains(postfix);
    }

    public static String buildUrl(String path) {
        return Config.HOST + path.replace(Config.PIC_PATH, "");
    }
}
