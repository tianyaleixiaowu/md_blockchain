package com.mindata.blockchain.core.sqlite;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;


/**
 * 配置sqlite数据库的DataSource
 * @author wuweifeng wrote on 2018/3/2.
 */
@Configuration
public class DataSourceConfiguration {

    @Bean(destroyMethod = "", name = "EmbeddeddataSource")
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:" + "example.db");
        dataSourceBuilder.type(SQLiteDataSource.class);
        return dataSourceBuilder.build();
    }

}
