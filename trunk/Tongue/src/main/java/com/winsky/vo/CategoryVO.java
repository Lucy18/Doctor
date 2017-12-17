package com.winsky.vo;

import com.winsky.bean.CategoryBean;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author: winsky
 * date: 2017/12/15
 * description:
 */
public class CategoryVO extends CategoryBean {
    private List<CategoryVO> children = new ArrayList<>();

    public CategoryVO(CategoryBean bean) {
        setId(bean.getId());
        setPid(bean.getPid());
        setNodeName(bean.getNodeName());
    }

    public List<CategoryVO> getChildren() {
        if (CollectionUtils.isEmpty(children)) return null;
        return children;
    }

    public void setChildren(List<CategoryVO> children) {
        this.children = children;
    }
}
