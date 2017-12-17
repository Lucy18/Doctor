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
    public UserBean getUser(String openId) {
        String sql = "select * from user where open_id=? limit 1";
        return j.queryForBean(UserBean.class, sql, openId);
    }

    public boolean update(UserBean user) {
        String sql = "update user set name=? , age=? , sex=? , mobile=? , job=? , history=? where open_id=? limit 1";
        return j.execute(sql, user.getName(), user.getAge(), user.getSex(), user.getMobile(), user.getJob(), user.getHistory(), user.getOpenId());
    }

    public UserBean getByName(String name) {
        String sql = "select * from user where name=? limit 1";
        return j.queryForBean(UserBean.class, sql, name);
    }
}
