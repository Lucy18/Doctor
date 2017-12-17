package com.winsky.bean;

import com.avatar.db.annotation.Column;
import com.avatar.db.annotation.GeneratorType;
import com.avatar.db.annotation.Table;

/**
 * @author winsky
 */
@Table(name = "photo_category")
public class PhotoCategoryBean {

    /**
     *
     */
    @Column(name = "id", primaryKey = true, generatorType = GeneratorType.AUTO_INCREMENT)
    private Long id;

    /**
     * 会话id，一个会话下的所有图片属于同一个id
     */
    @Column(name = "chat_id")
    private Long chatId;

    /**
     * 分类id
     */
    @Column(name = "category_id")
    private Long categoryId;

    /**
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * @param
     */
    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    /**
     * 分类id
     *
     * @return
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * 分类id
     *
     * @param
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

}