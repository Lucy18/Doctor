package com.winsky.client.api;

import com.alibaba.fastjson.JSONObject;
import com.winsky.APIUtil;
import com.winsky.bean.CategoryBean;
import com.winsky.service.CategoryService;
import com.winsky.vo.CategoryVO;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/api/category")
public class CategoryAPI {
    @Resource
    private CategoryService categoryService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(String json) {
        CategoryBean bean = JSONObject.parseObject(json, CategoryBean.class);
        long cid = categoryService.save(bean);
        if (cid <= 0) {
            return APIUtil.genErrorResult("该分类已存在");//实际上是cid为-1的时候才是分类已存在，这里忽略其他错误导致的cid为0的情况
        }
        return APIUtil.genDataResult(cid);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(String json) {
        if (StringUtils.isEmpty(json)) return APIUtil.genErrorResult("修改的内容为空");
        CategoryBean bean = JSONObject.parseObject(json, CategoryBean.class);
        String msg = categoryService.update(bean);
        return msg == null ? APIUtil.genSuccessResult() : APIUtil.genErrorResult(msg);
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public Object del(long id) {
        if (id <= 0) return APIUtil.genErrorResult("入参不合法");
        String msg = categoryService.del(id);
        return msg == null ? APIUtil.genSuccessResult() : APIUtil.genErrorResult(msg);
    }

    @RequestMapping(value = "/getChildByName")
    @ResponseBody
    public Object getChild(String name) {
        CategoryVO categoryVO = categoryService.getChild(name);
        return APIUtil.genDataResult(categoryVO);
    }


    @RequestMapping(value = "/getChildById")
    @ResponseBody
    public Object getChild(long categoryId) {
        CategoryVO categoryVO = categoryService.getChild(categoryId);
        return APIUtil.genDataResult(categoryVO);
    }
}
