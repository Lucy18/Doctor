package com.winsky.client.api;

import com.winsky.APIUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author: ysk13
 * date: 2017-5-3
 * description:
 */
@Controller
@RequestMapping("/api/user")
public class UserAPI {
    /**
     * 普通风格的url访问形式
     * http://localhost:8080/project_demo/api/user/getAll?name=1&password=2
     *
     * @param name
     * @param password
     * @return
     */
    @RequestMapping(value = "/getAll")
    @ResponseBody
    public Object getAll(String name, String password) {
        return APIUtil.genDataResult("from normal api: name=" + name + ",password=" + password);
    }

    /**
     * Rest风格的url访问形式
     * http://localhost:8080/project_demo/api/user/getAllRest/name/1/password/2
     *
     * @param name
     * @param password
     * @return
     */
    @RequestMapping(value = "/getAllRest/name/{name}/password/{password}", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllRest(@PathVariable String name, @PathVariable String password) {
        System.out.println(name + ":" + password);
        return APIUtil.genDataResult("from REST api: name=" + name + ",password=" + password);
    }
}
