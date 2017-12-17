package com.winsky.dao;

import com.winsky.bean.ChatBean;
import com.winsky.page.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author winsky
 */
@Service
public class ChatDAO extends BaseDAO {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    public List<ChatBean> getPageList(Page page, ChatBean chatBean) {
        String sql = "select a.* from chat a ";
        List<Object> objectList = new ArrayList<Object>();
        String sqlWhere = " where 1=1 ";
        if (chatBean != null) {
            if (chatBean.getId() != null) {
                objectList.add(chatBean.getId());
                sqlWhere += " AND a.id = ? ";
            }
            if (chatBean.getDiseaseId() != null) {
                objectList.add(chatBean.getDiseaseId());
                sqlWhere += " AND a.disease_id = ? ";
            }
            if (chatBean.getDescription() != null && chatBean.getDescription().trim().length() > 0) {
                objectList.add(chatBean.getDescription());
                sqlWhere += " AND a.description = ? ";
            }
            if (chatBean.getPhotoTime() != null) {
                objectList.add(chatBean.getPhotoTime());
                sqlWhere += " AND a.photo_time = ? ";
            }
            if (chatBean.getType() != null) {
                objectList.add(chatBean.getType());
                sqlWhere += " AND a.type = ? ";
            }
            if (chatBean.getCreateTime() != null) {
                objectList.add(chatBean.getCreateTime());
                sqlWhere += " AND a.create_time = ? ";
            }
        }
        sql = sql + sqlWhere;
        Object[] pram = objectList.toArray();
        if (page.getSortname() != null && page.getSortorder() != null) {
            sql += " order by " + page.getSortname() + " " + page.getSortorder();
        }
        page.setTotalRows(j.queryForInteger("select count(*) from chat a " + sqlWhere, pram));
        return j.queryForPageList(ChatBean.class, sql, page.getPageNo(), page.getPageSize(), pram);
    }

    public ChatBean getFirstChat(long diseaseId) {
        String sql = "select * from chat where disease_id=? limit 1";
        return j.queryForBean(ChatBean.class, sql, diseaseId);
    }
}