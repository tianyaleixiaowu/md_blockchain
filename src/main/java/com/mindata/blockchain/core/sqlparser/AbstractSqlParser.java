package com.mindata.blockchain.core.sqlparser;

import com.mindata.blockchain.core.model.base.BaseEntity;

/**
 * @author wuweifeng wrote on 2018/3/21.
 */
public abstract class AbstractSqlParser<T extends BaseEntity> {
    abstract void parse(byte operation, String id, T entity);

    abstract Class getEntityClass();
}
