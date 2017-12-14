package com.winsky.dao;

import com.winsky.bean.UserBean;
import org.springframework.stereotype.Service;

/**
 * author: ysk13
 * date: 2017-5-3
 * description:
 */
@Service
public class UserDAO extends BaseDAO {
    public UserBean getUserByName(String name) {
        String sql = "select * from user where name='" + name + "'";
        return j.queryForBean(UserBean.class, sql);
    }
}
