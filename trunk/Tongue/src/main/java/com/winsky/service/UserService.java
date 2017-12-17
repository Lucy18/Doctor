package com.winsky.service;

import com.winsky.Config;
import com.winsky.bean.UserBean;
import com.winsky.dao.UserDAO;
import com.winsky.enums.UserTypeEnum;
import org.apache.commons.lang3.StringUtils;
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

    public UserBean getUser(String openId) {
        return userDAO.getUser(openId);
    }

    public boolean saveOrUpdate(UserBean userBean) {
        String openId = userBean.getOpenId();
        UserBean exist = userDAO.getUser(openId);
        if (exist == null) {
            return userDAO.insert(userBean);
        } else {
            return userDAO.update(userBean);
        }
    }

    public static UserTypeEnum getUserTypeByOpenId(String openId) {
        if (StringUtils.isEmpty(openId)) return UserTypeEnum.DOCTOR;
        return Config.DOCTOR_OPEN_ID.equals(openId) ? UserTypeEnum.DOCTOR : UserTypeEnum.PATIENT;
    }

    /**
     * 判断用户是否存在
     *
     * @param openId 用户openId
     * @return true：用户存在，false：用户不存在
     */
    public boolean checkExist(String openId) {
        if (StringUtils.isEmpty(openId)) return false;
        UserBean user = getUser(openId);
        return user != null;
    }

    public UserBean getByName(String name) {
        return userDAO.getByName(name);
    }
}