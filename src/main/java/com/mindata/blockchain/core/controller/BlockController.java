package com.mindata.blockchain.core.controller;

import com.mindata.blockchain.core.bean.BaseData;
import com.mindata.blockchain.core.bean.ResultGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuweifeng wrote on 2018/3/7.
 */
@RestController
@RequestMapping("/block")
public class BlockController {

    @PostMapping
    public BaseData add() {

        return ResultGenerator.genSuccessResult();
    }
}
