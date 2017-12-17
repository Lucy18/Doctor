package com.winsky.bean;

import com.avatar.db.annotation.Column;
import com.avatar.db.annotation.GeneratorType;
import com.avatar.db.annotation.Table;
import com.winsky.TimeUtil;
import com.winsky.enums.UserTypeEnum;

/**
 * 病单
 *
 * @author winsky
 */
@Table(name = "disease")
public class DiseaseBean {

    /**
     *
     */
    @Column(name = "id", primaryKey = true, generatorType = GeneratorType.AUTO_INCREMENT)
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Long createTime;

    /**
     * 上次谁回复的，0-医生，1-患者
     */
    @Column(name = "last_chat")
    private Integer lastChat;

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
     * 用户id，医生则为0
     *
     * @return
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 用户id，医生则为0
     *
     * @param
     */
    public void setOpenId(String openId) {
        this.openId = openId;
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

    /**
     * 上次谁回复的，0-医生，1-患者
     *
     * @return
     */
    public Integer getLastChat() {
        return lastChat;
    }

    /**
     * 上次谁回复的，0-医生，1-患者
     *
     * @param
     */
    public void setLastChat(Integer lastChat) {
        this.lastChat = lastChat;
    }

    public String getCreateTimeStr() {
        return TimeUtil.timeLongToStr(createTime);
    }

    public void setCreateTimeStr(String createTime) {
        this.createTime = TimeUtil.timeStrToLong(createTime);
    }

    public String getLastChatStr() {
        UserTypeEnum userType = UserTypeEnum.fromValue(lastChat);
        if (userType != null) {
            return userType.type();
        }
        return null;
    }

    public void setLastChatStr(String lastChatStr) {
        UserTypeEnum userTypeEnum = UserTypeEnum.fromType(lastChatStr);
        if (userTypeEnum != null) {
            lastChat = userTypeEnum.value();
        }
    }
}