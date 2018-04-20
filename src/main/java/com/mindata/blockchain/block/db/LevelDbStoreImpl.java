package com.mindata.blockchain.block.db;

import org.iq80.leveldb.DB;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static org.iq80.leveldb.impl.Iq80DBFactory.asString;
import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;

/**
 * levelDB
 *
 * @author wuweifeng wrote on 2018/4/20.
 */
@Component
@ConditionalOnProperty("db.levelDB")
public class LevelDbStoreImpl implements DbStore {
    @Resource
    private DB db;

    @Override
    public void put(String key, String value) {
        db.put(bytes(key), bytes(value));
    }

    @Override
    public String get(String key) {
        return asString(db.get(bytes(key)));
    }

    @Override
    public void remove(String key) {
        db.delete(bytes(key));
    }
}
