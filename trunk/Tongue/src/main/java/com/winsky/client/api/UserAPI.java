package com.winsky.client.api;

import com.alibaba.fastjson.JSONObject;
import com.winsky.APIUtil;
import com.winsky.bean.UserBean;
import com.winsky.service.UserService;
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
        boolean result = userService.saveOrUpdate(userBean);
        return APIUtil.genBooleanResult(result);
    }
}
