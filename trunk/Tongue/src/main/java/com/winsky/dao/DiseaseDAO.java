package com.winsky.dao;

import com.winsky.bean.DiseaseBean;
import com.winsky.bean.DiseaseQueryBean;
import com.winsky.enums.UserTypeEnum;
import com.winsky.page.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    public List<DiseaseBean> getPageList(Page page, DiseaseQueryBean queryBean) {
        String sql = "select a.* from disease a ";
        List<Object> objectList = new ArrayList<Object>();
        StringBuilder sqlWhere = new StringBuilder(" where 1=1 ");
        if (queryBean != null) {
            if (queryBean.getId() != null) {
                objectList.add(queryBean.getId());
                sqlWhere.append(" AND a.id = ? ");
            }
            List<String> openIds = queryBean.getOpenIds();
            if (!CollectionUtils.isEmpty(openIds)) {
                sqlWhere.append(" AND ( ");
                for (int i = 0; i < openIds.size(); i++) {
                    String openId = openIds.get(i);
                    objectList.add(openId);
                    sqlWhere.append(" a.open_id = ? ");
                    if (i != openIds.size() - 1) {
                        sqlWhere.append(" or ");
                    }
                }
                sqlWhere.append(" ) ");
            }
            if (queryBean.getCreateTime() != null) {
                long today = queryBean.getCreateTime();
                long tomorrow = today + 24 * 3600;
                objectList.add(today);
                objectList.add(tomorrow);
                sqlWhere.append(" AND a.create_time >= ? and a.create_time < ?");
            }
            if (queryBean.getLastChat() != null) {
                objectList.add(queryBean.getLastChat());
                sqlWhere.append(" AND a.last_chat = ? ");
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
