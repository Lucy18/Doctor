package com.winsky.vo;

import com.winsky.bean.CategoryBean;
import com.winsky.bean.ChatBean;
import com.winsky.bean.PhotoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author: winsky
 * date: 2017/12/14
 * description:
 */
public class ChatVO extends ChatBean {
    private List<PhotoBean> photos = new ArrayList<>();
    private List<List<CategoryBean>> categories = new ArrayList<>();

    public ChatVO(ChatBean bean) {
        setId(bean.getId());
        setDiseaseId(bean.getDiseaseId());
        setDescription(bean.getDescription());
        setPhotoTime(bean.getPhotoTime());
        setType(bean.getType());
        setCreateTime(bean.getCreateTime());
    }

    public List<PhotoBean> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoBean> photos) {
        this.photos = photos;
    }

    public List<List<CategoryBean>> getCategories() {
        return categories;
    }

    public void setCategories(List<List<CategoryBean>> categories) {
        this.categories = categories;
    }

    public void addPhoto(PhotoBean photo) {
        if (this.photos == null) this.photos = new ArrayList<>();
        this.photos.add(photo);
    }
}
