package com.winsky.service;

import com.winsky.bean.CategoryBean;
import com.winsky.bean.PhotoCategoryBean;
import com.winsky.dao.PhotoCategoryDAO;
import com.winsky.vo.PhotoCategoryVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * author: winsky
 * date: 2017/12/15
 * description:
 */
@Service
public class PhotoCategoryService {
    @Resource
    private PhotoCategoryDAO photoCategoryDAO;
    @Resource
    private CategoryService categoryService;

    public long saveOrUpdate(PhotoCategoryBean bean) {
        PhotoCategoryBean exist = select(bean);
        long pcId = 0;
        if (exist == null) {
            try {
                pcId = photoCategoryDAO.save(bean);
            } catch (Exception ignored) {
            }
        } else {
            bean.setId(exist.getId());
            boolean result = photoCategoryDAO.update(bean);
            if (!result) {
                pcId = 0L;
            } else {
                pcId = bean.getId();
            }
        }
        return pcId;
    }

    public boolean del(long chatId, long categoryId) {
        return photoCategoryDAO.delete(chatId, categoryId);
    }

    private PhotoCategoryBean select(PhotoCategoryBean bean) {
        if (bean == null) return null;
        return photoCategoryDAO.get(bean);
    }

    public List<PhotoCategoryVO> getByChatId(long chatId) {
        List<PhotoCategoryVO> result = new ArrayList<>();
        List<PhotoCategoryBean> beans = photoCategoryDAO.getByChatId(chatId);
        beans.forEach(bean -> {
            PhotoCategoryVO vo = new PhotoCategoryVO(bean);
            List<CategoryBean> categories = categoryService.get(bean.getCategoryId(), false);
            vo.setCategories(categories);
            result.add(vo);
        });
        return result;
    }

    public List<List<CategoryBean>> getCategoryByChatId(long chatId) {
        List<List<CategoryBean>> result = new ArrayList<>();
        List<PhotoCategoryBean> beans = photoCategoryDAO.getByChatId(chatId);
        beans.forEach(bean -> {
            List<CategoryBean> categories = categoryService.get(bean.getCategoryId(), false);
            result.add(categories);
        });
        return result;
    }

    /**
     * 判断是否有图片使用了分类
     *
     * @param categoryId 分类id
     * @return true：该分类已被使用，false：该分类尚未被使用
     */
    public boolean isCategoryUsed(long categoryId) {
        return photoCategoryDAO.isCategoryInUse(categoryId);
    }
}
