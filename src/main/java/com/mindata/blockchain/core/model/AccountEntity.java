package com.mindata.blockchain.core.model;

import com.mindata.blockchain.core.model.basebase.BaseEntity;

import javax.persistence.*;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
@Entity
@Table(name = "account_entity")
public class AccountEntity extends BaseEntity {
    private String name;
    private String grade;
    private String parentId;

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "grade")
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Basic
    @Column(name = "parent_id")
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
