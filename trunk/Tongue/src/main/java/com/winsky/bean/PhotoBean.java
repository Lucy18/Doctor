package com.winsky.bean;

import com.avatar.db.annotation.Column;
import com.avatar.db.annotation.GeneratorType;
import com.avatar.db.annotation.Table;

/**
 * @author winsky
 */
@Table(name = "photo")
public class PhotoBean {

    /**
     *
     */
    @Column(name = "id", primaryKey = true, generatorType = GeneratorType.AUTO_INCREMENT)
    private Long id;

    /**
     *
     */
    @Column(name = "chat_id")
    private Long chatId;

    /**
     *
     */
    @Column(name = "photo_url")
    private String photoUrl;

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

    /**
     * @return
     */
    public Long getChatId() {
        return chatId;
    }

    /**
     * @param
     */
    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    /**
     * @return
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * @param
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}