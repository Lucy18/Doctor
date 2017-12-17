package com.winsky.vo;

import com.winsky.bean.DiseaseBean;
import com.winsky.bean.UserBean;

/**
 * author: winsky
 * date: 2017/12/14
 * description:
 */
public class DiseaseVO extends DiseaseBean {
    private String cover;
    private String description;
    private UserBean userBean;

    public DiseaseVO(DiseaseBean bean) {
        setId(bean.getId());
        setOpenId(bean.getOpenId());
        setCreateTime(bean.getCreateTime());
        setLastChat(bean.getLastChat());
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}
