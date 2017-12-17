package com.winsky.client.api;

import com.alibaba.fastjson.JSONObject;
import com.winsky.APIUtil;
import com.winsky.bean.PhotoCategoryBean;
import com.winsky.service.PhotoCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * author: winsky
 * date: 2017/12/15
 * description:
 */
@Controller
@RequestMapping("/api/photoCategory")
public class PhotoCategoryAPI {
    @Resource
    private PhotoCategoryService photoCategoryService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(String json) {
        PhotoCategoryBean bean = JSONObject.parseObject(json, PhotoCategoryBean.class);
        long id = photoCategoryService.saveOrUpdate(bean);
        if (id <= 0) {
            return APIUtil.genErrorResult();
        } else {
            return APIUtil.genDataResult(id);
        }
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public Object del(long chatId, long categoryId) {
        boolean result = photoCategoryService.del(chatId,categoryId);
        return APIUtil.genBooleanResult(result);
    }
}
