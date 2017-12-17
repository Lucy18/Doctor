package com.winsky.bean;

import com.avatar.db.annotation.*;
import com.winsky.TimeUtil;
import com.winsky.enums.UserTypeEnum;

/**
 * @author winsky
 */
@Table(name = "chat")
public class ChatBean {

    /**
     *
     */
    @Column(name = "id", primaryKey = true, generatorType = GeneratorType.AUTO_INCREMENT)
    private Long id;

    /**
     * 病单
     */
    @Column(name = "disease_id")
    private Long diseaseId;

    /**
     *
     */
    @Column(name = "description")
    private String description;

    /**
     * 创建时间
     */
    @Column(name = "photo_time")
    private Long photoTime;

    /**
     * 0-医生，1-患者
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Long createTime;

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
     * 病单
     *
     * @return
     */
    public Long getDiseaseId() {
        return diseaseId;
    }

    /**
     * 病单
     *
     * @param
     */
    public void setDiseaseId(Long diseaseId) {
        this.diseaseId = diseaseId;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 创建时间
     *
     * @return
     */
    public Long getPhotoTime() {
        return photoTime;
    }

    /**
     * 创建时间
     *
     * @param
     */
    public void setPhotoTime(Long photoTime) {
        this.photoTime = photoTime;
    }

    /**
     * 0-医生，1-患者
     *
     * @return
     */
    public Integer getType() {
        return type;
    }

    /**
     * 0-医生，1-患者
     *
     * @param
     */
    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeStr() {
        UserTypeEnum userType = UserTypeEnum.fromValue(type);
        if (userType != null) {
            return userType.type();
        }
        return null;
    }

    public void setTypeStr(String userType) {
        UserTypeEnum userTypeEnum = UserTypeEnum.fromType(userType);
        if (userTypeEnum != null) {
            type = userTypeEnum.value();
        }
    }

    public String getPhotoTimeStr() {
        return TimeUtil.timeLongToStr(photoTime);
    }

    public void setPhotoTimeStr(String createTime) {
        this.photoTime = TimeUtil.timeStrToLong(createTime);
    }

    /**
     * 创建时间
     *
     * @return
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     *
     * @param
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        return TimeUtil.timeLongToStr(createTime);
    }

    public void setCreateTimeStr(String createTime) {
        this.createTime = TimeUtil.timeStrToLong(createTime);
    }
}