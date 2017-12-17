package com.winsky.service;

import com.winsky.Config;
import com.winsky.bean.CategoryBean;
import com.winsky.dao.CategoryDAO;
import com.winsky.vo.CategoryVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * author: winsky
 * date: 2017/12/15
 * description:
 */
@Service
public class CategoryService {
    @Resource
    private CategoryDAO categoryDAO;
    @Resource
    private PhotoCategoryService photoCategoryService;

    /**
     * 根据分类id获取当前分类的层级关系，list元素从前往后表示分类从上至下的关系，到当前id表示的分类截止
     *
     * @param categoryId 分类id
     * @return 分类的层级关系
     */
    public List<CategoryBean> get(long categoryId, boolean containTopLevel) {
        List<CategoryBean> categories = new ArrayList<>();
        CategoryBean bean = categoryDAO.select(CategoryBean.class, categoryId);
        if (containTopLevel) {
            while (bean != null) {
                categories.add(bean);
                if (Config.INIT_CATEGORY_PID != bean.getPid()) {
                    bean = categoryDAO.select(CategoryBean.class, bean.getPid());
                } else {
                    bean = null;
                }
            }
        } else {
            while (bean != null && Config.INIT_CATEGORY_PID != bean.getPid()) {
                categories.add(bean);
                bean = categoryDAO.select(CategoryBean.class, bean.getPid());
            }
        }
        Collections.reverse(categories);
        return categories;
    }

    public CategoryVO getChild(String name) {
        CategoryVO result = null;

        CategoryBean parent = categoryDAO.getByName(name);
        if (parent != null) {
            result = getChild(parent);
        }
        return result;
    }

    public CategoryVO getChild(long cid) {
        CategoryBean bean = categoryDAO.select(CategoryBean.class, cid);
        if (bean == null) return null;
        else return getChild(bean);
    }

    private CategoryVO getChild(CategoryBean bean) {
        if (bean == null) return null;
        CategoryVO result = new CategoryVO(bean);
        List<CategoryBean> beans = categoryDAO.getByPId(bean.getId());
        List<CategoryVO> children = new ArrayList<>();
        if (!CollectionUtils.isEmpty(beans)) {
            beans.forEach(categoryBean -> {
                CategoryVO vo = new CategoryVO(categoryBean);
                CategoryVO child = getChild(vo);
                vo.setChildren(child.getChildren());
                children.add(vo);
            });
        }
        result.setChildren(children);
        return result;
    }

    public long save(CategoryBean bean) {
        boolean exist = categoryDAO.checkExist(bean);
        if (!exist) {
            return categoryDAO.save(bean);
        } else {
            return -1;
        }
    }

    public String update(CategoryBean bean) {
        String msg = null;
        boolean exist = categoryDAO.checkExist(bean);
        if (!exist) {
            boolean result = categoryDAO.update(bean);
            if (!result) {
                msg = "修改出错";
            }
        } else {
            msg = "分类已经存在";
        }

        return msg;
    }

    public String del(long id) {
        String msg = null;
        boolean hasChild = hasChild(id);
        if (hasChild) {
            msg = "该分类下有子分类，不能被删除";
        } else {
            boolean isUsed = photoCategoryService.isCategoryUsed(id);
            if (!isUsed) {
                boolean result = categoryDAO.delete(CategoryBean.class, id);
                if (!result) {
                    msg = "删除出错";
                }
            } else {
                msg = "分类已被使用，不可以删除";
            }
        }
        return msg;
    }

    private boolean hasChild(long categoryId) {
        List<CategoryBean> children = categoryDAO.getByPId(categoryId);
        return !CollectionUtils.isEmpty(children);
    }
}
