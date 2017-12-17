package com.winsky.vo;

import com.winsky.bean.CategoryBean;
import com.winsky.bean.PhotoCategoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author: winsky
 * date: 2017/12/15
 * description:
 */
public class PhotoCategoryVO extends PhotoCategoryBean {
    private List<CategoryBean> categories = new ArrayList<>();// 从前往后依次是分类从上至下

    public PhotoCategoryVO(PhotoCategoryBean bean) {
        setId(bean.getId());
        setChatId(bean.getChatId());
        setCategoryId(bean.getCategoryId());
    }

    public List<CategoryBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryBean> categories) {
        this.categories = categories;
    }
}
