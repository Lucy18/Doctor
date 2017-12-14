package com.winsky.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * author: ysk13
 * date: 2017-5-6
 * description:
 */
@Controller
@RequestMapping(value = "/base")
public class BaseController {
    private static final String AUTH_SESSION = "auth";

    @RequestMapping(value = "/gotoPage")
    public ModelAndView turnToPage(String view) {
        ModelAndView result = new ModelAndView(view);
        return result;
    }
}
