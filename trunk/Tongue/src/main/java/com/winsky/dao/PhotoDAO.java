package com.winsky.dao;

import com.winsky.bean.PhotoBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author winsky
 */
@Service
public class PhotoDAO extends BaseDAO {
    public List<PhotoBean> getPhotos(long chatId) {
        String sql = "select * from photo where chat_id=?";
        return j.queryForList(PhotoBean.class, sql, chatId);
    }
}