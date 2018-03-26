package com.mindata.blockchain.block.db;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuweifeng wrote on 2018/3/13.
 */
@Configuration
public class DbInitConfig {
    static {
        RocksDB.loadLibrary();
    }

    @Bean
    public RocksDB rocksDB() {
        Options options = new Options().setCreateIfMissing(true);
        try {
            return RocksDB.open(options, "./rocksDB");
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
