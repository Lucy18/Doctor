package com.winsky.dao;

import com.winsky.bean.PhotoCategoryBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author winsky
 */
@Service
public class PhotoCategoryDAO extends BaseDAO {
    public PhotoCategoryBean get(PhotoCategoryBean bean) {
        String sql = "select * from photo_category where chat_id=? and category_id=?";
        bean = j.queryForBean(PhotoCategoryBean.class, sql, bean.getChatId(), bean.getCategoryId());
        return bean;
    }


    public List<PhotoCategoryBean> getByChatId(long chatId) {
        String sql = "select * from photo_category where chat_id=?";
        return j.queryForList(PhotoCategoryBean.class, sql, chatId);
    }

    /**
     * 判断是否有图片使用了某个分类
     *
     * @param categoryId 分类编号
     * @return true：分类在使用中，false：分类没有使用
     */
    public boolean isCategoryInUse(long categoryId) {
        String sql = "select * from photo_category where category_id=? limit 1";
        PhotoCategoryBean bean = j.queryForBean(PhotoCategoryBean.class, sql, categoryId);
        return bean != null;
    }

    public boolean delete(long chatId, long categoryId) {
        String sql = "delete from photo_category where chat_id = ? and category_id=? limit 1";
        return j.execute(sql, chatId, categoryId);
    }
}