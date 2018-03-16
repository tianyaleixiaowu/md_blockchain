package com.mindata.blockchain.core.sqlite.config;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * 构建一个存放表名和model实体class的对应关系，如account_entity:AccountEntity.class
 *
 * @author wuweifeng wrote on 2018/3/2.
 */
@Configuration
@AutoConfigureAfter(JpaConfiguration.class)
public class ModelMetaData {

    @Bean(name = "metaMap")
    public Map<String, Class> metaMap(EntityManagerFactory factory) throws ClassNotFoundException {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        SessionFactory sessionFactory = factory.unwrap(SessionFactory.class);
        Map<String, ClassMetadata> metaMap = sessionFactory.getAllClassMetadata();
        Map<String, Class> map = new HashMap<>(metaMap.size());
        for (String key : metaMap.keySet()) {
            AbstractEntityPersister classMetadata = (AbstractEntityPersister) metaMap
                    .get(key);
            String tableName = classMetadata.getTableName().toLowerCase();
            int index = tableName.indexOf(".");
            if (index >= 0) {
                tableName = tableName.substring(index + 1);
            }
            map.put(tableName, Class.forName(key));
        }
        return map;
    }

}
