package com.winsky.service;

import com.winsky.bean.UserBean;
import com.winsky.dao.UserDAO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * author: ysk13
 * date: 2017-5-3
 * description:
 */
@Service
public class UserService {
    @Resource
    private UserDAO userDAO;

    /**
     * 用户登录
     *
     * @param name
     * @param password
     * @return
     */
    public UserBean login(String name, String password) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        UserBean user = userDAO.getUserByName(name);
        if (user != null) {
            String pwd = user.getPassword();
            if (password != null && password.equals(pwd)) {
                return user;
            }
        }
        return null;
    }
}