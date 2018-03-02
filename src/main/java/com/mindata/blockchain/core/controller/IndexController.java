package com.mindata.blockchain.core.controller;

import com.mindata.blockchain.core.model.AccountEntity;
import com.mindata.blockchain.core.model.convert.ConvertTableName;
import com.mindata.blockchain.core.repository.CodeAreaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/3/2.
 */
@RestController
public class IndexController {
    @Resource
    private CodeAreaRepository codeAreaRepository;
    @Resource
    private ConvertTableName convertTableName;

    @RequestMapping("")
    public Object a() {
        AccountEntity accountEntity = new AccountEntity();


        return codeAreaRepository.save(accountEntity);
    }

    @RequestMapping("/find")
    public Object b() {
        return convertTableName.convertOf("account_entity");

        //return codeAreaRepository.findAll();
    }

    @RequestMapping("/class")
    public Object c() {
        return convertTableName.convertOf(AccountEntity.class);
    }
}
