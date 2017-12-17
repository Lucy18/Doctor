package com.winsky.client.api;

import com.alibaba.fastjson.JSONObject;
import com.winsky.APIUtil;
import com.winsky.bean.UserBean;
import com.winsky.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * author: ysk13
 * date: 2017-5-3
 * description:
 */
@Controller
@RequestMapping("/api/user")
public class UserAPI {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/getUser")
    @ResponseBody
    public Object getUser(String openId) {
        UserBean user = userService.getUser(openId);
        return APIUtil.genDataResult(user);
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(String json) {
        UserBean userBean = JSONObject.parseObject(json, UserBean.class);
        String msg = checkUser(userBean);
        if (msg != null) return APIUtil.genErrorResult(msg);

        boolean result = userService.saveOrUpdate(userBean);
        return APIUtil.genBooleanResult(result);
    }

    private String checkUser(UserBean user) {
        String msg = null;
        if (user == null) msg = "用户信息不能为空";
        else if (StringUtils.isEmpty(user.getOpenId())) msg = "用户编号不能为空";
        else if (StringUtils.isEmpty(user.getName())) msg = "请在微信公众号内打开";
        else if (StringUtils.isEmpty(user.getName())) msg = "姓名不能为空";
        else if (StringUtils.isEmpty(user.getName())) msg = "年龄不能为空";
        else if (StringUtils.isEmpty(user.getName())) msg = "性别不能为空";
        return msg;
    }
}
