package com.winsky.client.html;

import com.winsky.APIUtil;
import com.winsky.Config;
import com.winsky.utils.FileUploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Objects;

/**
 * author: ysk13
 * date: 2017-6-6
 * description:
 */
@Controller
@RequestMapping("/file")
public class UploadController {
    private static final String TARGET_DIR = Config.PIC_PATH;

    @RequestMapping("/upload")
    @ResponseBody
    public Object upload(HttpServletRequest request) {
        String msg = StringUtils.EMPTY;
        String fileUrl = null;

        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {  //判断 request 是否有文件上传,即多部分请求
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            // 一次只允许上传一个文件
            // while (iter.hasNext()) {
            MultipartFile file = multiRequest.getFile(iter.next());
            if (FileUploadUtil.allowUpload(file.getContentType())) {
                fileUrl = FileUploadUtil.upload(file, TARGET_DIR);
            } else {
                msg = "文件类型不合法,只能是 jpg、gif、png、jpeg 类型！";
            }
            // }
            if (fileUrl == null && Objects.equals(msg, "")) {
                msg = "文件上传失败";
            }
        } else {
            msg = "没有要上传的文件";
        }

        if (fileUrl != null) {
            return APIUtil.genDataResult(fileUrl);
        } else {
            return APIUtil.genErrorResult(msg);
        }
    }

    @RequestMapping("/toUpload")
    public String toUpload() {
        return "upload";
    }
}
