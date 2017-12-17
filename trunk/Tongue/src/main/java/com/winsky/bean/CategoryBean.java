package com.winsky.bean;

import com.avatar.db.annotation.Column;
import com.avatar.db.annotation.GeneratorType;
import com.avatar.db.annotation.Table;

/**
 * @author winsky
 */
@Table(name = "category")
public class CategoryBean {

    /**
     *
     */
    @Column(name = "id", primaryKey = true, generatorType = GeneratorType.AUTO_INCREMENT)
    private Long id;

    /**
     *
     */
    @Column(name = "node_name")
    private String nodeName;

    /**
     *
     */
    @Column(name = "pid")
    private Long pid;

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
    public String getNodeName() {
        return nodeName;
    }

    /**
     * @param
     */
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * @return
     */
    public Long getPid() {
        return pid;
    }

    /**
     * @param
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }
}