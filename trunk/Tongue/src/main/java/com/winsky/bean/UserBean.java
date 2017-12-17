package com.winsky.bean;

import com.avatar.db.annotation.Column;
import com.avatar.db.annotation.Table;
import com.winsky.enums.SexEnum;

/**
 * Users and global privileges
 *
 * @author winsky
 */
@Table(name = "user")
public class UserBean {

    /**
     * 微信id
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 年龄
     */
    @Column(name = "age")
    private Integer age;

    /**
     * 0-女，1-男
     */
    @Column(name = "sex")
    private Integer sex;

    /**
     * 联系电话
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 工作
     */
    @Column(name = "job")
    private String job;

    /**
     * 病史
     */
    @Column(name = "history")
    private String history;

    /**
     * 微信id
     *
     * @return
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 微信id
     *
     * @param
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 姓名
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 姓名
     *
     * @param
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 年龄
     *
     * @return
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 年龄
     *
     * @param
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 0-女，1-男
     *
     * @return
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 0-女，1-男
     *
     * @param
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSexStr() {
        SexEnum sexEnum = SexEnum.fromValue(sex);
        if (sexEnum != null) {
            return sexEnum.type();
        }
        return null;
    }

    public void setSexStr(String sexStr) {
        SexEnum sexEnum = SexEnum.fromType(sexStr);
        if (sexEnum != null) {
            sex = sexEnum.value();
        }
    }

    /**
     * 联系电话
     *
     * @return
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 联系电话
     *
     * @param
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 工作
     *
     * @return
     */
    public String getJob() {
        return job;
    }

    /**
     * 工作
     *
     * @param
     */
    public void setJob(String job) {
        this.job = job;
    }

    /**
     * 病史
     *
     * @return
     */
    public String getHistory() {
        return history;
    }

    /**
     * 病史
     *
     * @param
     */
    public void setHistory(String history) {
        this.history = history;
    }
}