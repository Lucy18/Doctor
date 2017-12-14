package com.winsky.bean;

import com.avatar.db.annotation.Column;
import com.avatar.db.annotation.GeneratorType;
import com.avatar.db.annotation.Table;

/**
 * author: ysk13
 * date: 2017-5-3
 * description:
 */
@Table(name = "user")
public class UserBean {
    /**
     *
     */
    @Column(name = "id", primaryKey = true, generatorType = GeneratorType.AUTO_INCREMENT)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
