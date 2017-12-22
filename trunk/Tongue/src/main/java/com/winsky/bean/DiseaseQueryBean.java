package com.winsky.bean;

import com.winsky.TimeUtil;
import com.winsky.enums.UserTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 病单
 *
 * @author winsky
 */
public class DiseaseQueryBean {
    private Long id;

    /**
     * 用户id
     */
    private List<String> openIds = new ArrayList<>();

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 上次谁回复的，0-医生，1-患者
     */
    private Integer lastChat;

    public DiseaseQueryBean(DiseaseBean diseaseBean) {
        this.id = diseaseBean.getId();
        String openId = diseaseBean.getOpenId();
        if (openId != null) {
            this.openIds.add(openId);
        }
        this.createTime = diseaseBean.getCreateTime();
        this.lastChat = diseaseBean.getLastChat();
    }

    public DiseaseQueryBean() {
    }

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

    public List<String> getOpenIds() {
        return openIds;
    }

    public void setOpenIds(List<String> openIds) {
        this.openIds = openIds;
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

    public void addOpenId(String openId) {
        openIds.add(openId);
    }

    public void addOpenIds(List<String> openIds) {
        this.openIds.addAll(openIds);
    }

}