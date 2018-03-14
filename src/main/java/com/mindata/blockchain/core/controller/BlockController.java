package com.mindata.blockchain.core.controller;

import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.common.exception.TrustSDKException;
import com.mindata.blockchain.core.bean.BaseData;
import com.mindata.blockchain.core.bean.ResultGenerator;
import com.mindata.blockchain.core.requestbody.BlockRequestBody;
import com.mindata.blockchain.core.service.BlockService;
import com.mindata.blockchain.socket.body.GenerateBlockBody;
import com.mindata.blockchain.socket.client.PacketSender;
import com.mindata.blockchain.socket.packet.BlockPacket;
import com.mindata.blockchain.socket.packet.PacketBuilder;
import com.mindata.blockchain.socket.packet.PacketType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/3/7.
 */
@RestController
@RequestMapping("/block")
public class BlockController {
    @Resource
    private BlockService blockService;
    @Resource
    private PacketSender packetSender;

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

    @GetMapping
    public BaseData test() throws Exception {
        Block block = new Block();
        block.setHash("123456");

        BlockPacket packet = new PacketBuilder<GenerateBlockBody>()
                .setType(PacketType.GENERATE_BLOCK_REQUEST)
                .setBody(new GenerateBlockBody(block)).build();
        packetSender.sendGroup(packet);
        return null;
    }
}
