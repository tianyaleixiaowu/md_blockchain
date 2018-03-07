package com.mindata.blockchain.core.model.convert;

import com.mindata.blockchain.core.model.base.BaseEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 表名和实体类的对应
 * @author wuweifeng wrote on 2018/3/2.
 */
@Component
public class ConvertTableName<T extends BaseEntity> {
    @Qualifier(value = "metaMap")
    @Resource
    private Map<String, Class<T>> metaMap;

    /**
     * 根据表名获取class名
     * @return
     * 表对应的实体类
     */
    public Class<T> convertOf(String tableName) {
        return metaMap.get(tableName);
    }

    /**
     * 根据类名取表名
     * @param clazz
     * 类名
     * @return
     * 表名
     */
    public String convertOf(Class<T> clazz) {
        for (String key : metaMap.keySet()) {
            if (metaMap.get(key).equals(clazz)) {
                return key;
            }
        }
        return null;
    }
}
