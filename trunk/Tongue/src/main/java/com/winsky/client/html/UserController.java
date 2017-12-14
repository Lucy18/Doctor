package com.winsky.client.html;

import com.winsky.bean.UserBean;
import com.winsky.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * author: ysk13
 * date: 2017-5-3
 * description:
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/hello")
    public String hello(Model model) {
        return "hello";
    }

    @RequestMapping("/info")
    public String info(Model model, HttpSession session) {
        UserBean user = (UserBean) session.getAttribute("auth");
        model.addAttribute("name",user.getName());
        return "user/info";
    }
}
