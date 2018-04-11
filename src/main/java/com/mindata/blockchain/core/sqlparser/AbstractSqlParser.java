package com.mindata.blockchain.core.sqlparser;

import com.mindata.blockchain.core.model.base.BaseEntity;

/**
 * @author wuweifeng wrote on 2018/3/21.
 */
public abstract class AbstractSqlParser<T extends BaseEntity> {
    /**
     * 解析sql的方法
     * @param operation 是什么操作
     * @param id 主键
     * @param entity 对象entity
     */
    abstract void parse(byte operation, String id, T entity);

    /**
     * 对象的类
     *
     * @return Class
     */
    abstract Class getEntityClass();
}
