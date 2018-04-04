package com.mindata.blockchain.core.sqlparser;

import com.mindata.blockchain.block.InstructionBase;
import com.mindata.blockchain.common.FastJsonUtil;
import com.mindata.blockchain.core.model.base.BaseEntity;
import com.mindata.blockchain.core.model.convert.ConvertTableName;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/3/21.
 */
@Service
public class InstructionParserImpl<T extends BaseEntity> implements InstructionParser {
    @Resource
    private ConvertTableName<T> convertTableName;
    @Resource
    private AbstractSqlParser<T>[] sqlParsers;

    @Override
    public boolean parse(InstructionBase instructionBase) {
        byte operation = instructionBase.getOperation();
        String table = instructionBase.getTable();
        String json = instructionBase.getOldJson();
        //表对应的类名，如MessageEntity.class
        Class<T> clazz = convertTableName.convertOf(table);
        T object = FastJsonUtil.toBean(json, clazz);
        for (AbstractSqlParser<T> sqlParser : sqlParsers) {
            if (clazz.equals(sqlParser.getEntityClass())) {
                sqlParser.parse(operation, instructionBase.getInstructionId(), object);
                break;
            }
        }

        return true;
    }
}
