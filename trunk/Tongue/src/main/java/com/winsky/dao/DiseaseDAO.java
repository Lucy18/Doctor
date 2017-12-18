package com.winsky.dao;

import com.winsky.bean.DiseaseBean;
import com.winsky.enums.UserTypeEnum;
import com.winsky.page.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * author: winsky
 * date: 2017/12/14
 * description:
 */
@Service
public class DiseaseDAO extends BaseDAO {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    public List<DiseaseBean> getPageList(Page page, DiseaseBean diseaseBean) {
        String sql = "select a.* from disease a ";
        List<Object> objectList = new ArrayList<Object>();
        String sqlWhere = " where 1=1 ";
        if (diseaseBean != null) {
            if (diseaseBean.getId() != null) {
                objectList.add(diseaseBean.getId());
                sqlWhere += " AND a.id = ? ";
            }
            if (diseaseBean.getOpenId() != null && diseaseBean.getOpenId().trim().length() > 0) {
                objectList.add(diseaseBean.getOpenId());
                sqlWhere += " AND a.open_id = ? ";
            }
            if (diseaseBean.getCreateTime() != null) {
                long today = diseaseBean.getCreateTime();
                long tomorrow = today + 24 * 3600;
                objectList.add(today);
                objectList.add(tomorrow);
                sqlWhere += " AND a.create_time >= ? and a.create_time < ?";
            }
            if (diseaseBean.getLastChat() != null) {
                objectList.add(diseaseBean.getLastChat());
                sqlWhere += " AND a.last_chat = ? ";
            }
        }
        sql = sql + sqlWhere;
        Object[] pram = objectList.toArray();

        if (page != null) {
            if (page.getSortname() != null && page.getSortorder() != null) {
                sql += " order by " + page.getSortname() + " " + page.getSortorder();
            }
            page.setTotalRows(j.queryForInteger("select count(*) from disease a " + sqlWhere, pram));
            return j.queryForPageList(DiseaseBean.class, sql, page.getPageNo(), page.getPageSize(), pram);
        } else {
            return j.queryForList(DiseaseBean.class, sql, pram);
        }
    }


    public boolean updateLastChat(long id, UserTypeEnum user) {
        String sql = "update disease set last_chat=? where id=?";
        return j.execute(sql, user.value(), id);
    }
}
