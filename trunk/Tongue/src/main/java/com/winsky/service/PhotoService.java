package com.winsky.service;

import com.winsky.Config;
import com.winsky.bean.PhotoBean;
import com.winsky.dao.PhotoDAO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * author: winsky
 * date: 2017/12/14
 * description:
 */
@Service
public class PhotoService {
    @Resource
    private PhotoDAO photoDAO;
    @Resource
    private PhotoCategoryService photoCategoryService;

    public List<PhotoBean> getPhotos(long chatId) {
        if (chatId <= 0) return new ArrayList<>();
        return photoDAO.getPhotos(chatId);
    }

    public PhotoBean getFirstPhoto(long chatId) {
        List<PhotoBean> photos = getPhotos(chatId);
        if (photos.size() != 0) {
            return photos.get(0);
        }
        return null;
    }

    public long save(PhotoBean bean) {
        return photoDAO.save(bean);
    }

    public List<PhotoBean> transferPhotos(String photosStr) {
        List<PhotoBean> photoBeans = new ArrayList<>();
        if (StringUtils.isNotEmpty(photosStr)) {
            String[] photos = photosStr.split(Config.PIC_SPLIT);
            for (String photo : photos) {
                PhotoBean photoBean = new PhotoBean();
                photoBean.setPhotoUrl(photo);
                photoBeans.add(photoBean);
            }
        }
        return photoBeans;
    }
}
