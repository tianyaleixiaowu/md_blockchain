package com.mindata.blockchain.core.controller;

import com.mindata.blockchain.common.exception.TrustSDKException;
import com.mindata.blockchain.core.bean.BaseData;
import com.mindata.blockchain.core.bean.ResultGenerator;
import com.mindata.blockchain.core.requestbody.BlockRequestBody;
import com.mindata.blockchain.core.service.BlockService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/3/7.
 */
@RestController
@RequestMapping("/block")
public class BlockController {
    @Resource
    private BlockService blockService;

    /**
     * 添加一个block
     * @param blockRequestBody 指令的集合
     * @return 结果
     */
    @PostMapping
    public BaseData add(@RequestBody BlockRequestBody blockRequestBody) throws TrustSDKException {
        if (blockService.check(blockRequestBody) != null) {
            return ResultGenerator.genFailResult(blockService.check(blockRequestBody));
        }
        return ResultGenerator.genSuccessResult(blockService.addBlock(blockRequestBody));
    }
}
