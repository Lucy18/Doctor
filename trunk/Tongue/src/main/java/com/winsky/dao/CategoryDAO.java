package com.winsky.dao;

import com.winsky.bean.CategoryBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: winsky
 * date: 2017/12/15
 * description:
 */
@Service
public class CategoryDAO extends BaseDAO {
    public CategoryBean getByName(String name) {
        String sql = "select * from category where node_name = ?";
        return j.queryForBean(CategoryBean.class, sql, name);
    }

    public List<CategoryBean> getByPId(long pid) {
        String sql = "select * from category where pid = ?";
        return j.queryForList(CategoryBean.class, sql, pid);
    }

    /**
     * 判断分类数据是否已存在
     *
     * @param bean 分类数据
     * @return true：已存在，false：不存在
     */
    public boolean checkExist(CategoryBean bean) {
        String sql = "select id from category where node_name = ? and pid = ? ";
        Long id = j.queryForBean(Long.class, sql, bean.getNodeName(), bean.getPid());
        if (bean.getId() != null) {
            return bean.getId().longValue() != id.longValue();
        } else {
            return id != null;
        }
    }
}
