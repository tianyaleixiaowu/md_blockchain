package com.mindata.blockchain.core.controller;

import com.mindata.blockchain.core.model.convert.ConvertTableName;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/3/2.
 */
@RestController
public class IndexController {
    @Resource
    private ConvertTableName convertTableName;

    @RequestMapping("/find")
    public Object b() {
        return convertTableName.convertOf("account_entity");

        //return codeAreaRepository.findAll();
    }

}
