package com.mindata.blockchain.core.controller;

import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.block.BlockHeader;
import com.mindata.blockchain.common.exception.TrustSDKException;
import com.mindata.blockchain.core.bean.BaseData;
import com.mindata.blockchain.core.bean.ResultGenerator;
import com.mindata.blockchain.core.manager.DbBlockManager;
import com.mindata.blockchain.core.requestbody.BlockRequestBody;
import com.mindata.blockchain.core.service.BlockService;
import com.mindata.blockchain.socket.body.BlockBody;
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
    @Resource
    private DbBlockManager dbBlockManager;

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
    public BaseData test() {
        Block block = new Block();
        BlockHeader blockHeader = new BlockHeader();
        blockHeader.setTimeStamp(System.currentTimeMillis());
        blockHeader.setHashPreviousBlock("1");
        block.setHash("2");
        block.setBlockHeader(blockHeader);

        BlockPacket packet = new PacketBuilder<BlockBody>()
                .setType(PacketType.GENERATE_BLOCK_REQUEST)
                .setBody(new BlockBody(block)).build();

        packetSender.sendGroup(packet);
        return null;
    }

    @GetMapping("/last")
    public BaseData lastBlock() throws Exception {
        BlockPacket packet = new PacketBuilder<BlockBody>()
                .setType(PacketType.LAST_BLOCK_INFO_REQUEST)
                .setBody(new BlockBody()).build();
        packetSender.sendGroup(packet);
        return null;
    }


    @GetMapping("/next")
    public BaseData nextBlock() throws Exception {
        Block block = dbBlockManager.getFirstBlock();
        BlockPacket packet = new PacketBuilder<BlockBody>()
                .setType(PacketType.NEXT_BLOCK_INFO_REQUEST)
                .setBody(new BlockBody(block)).build();
        packetSender.sendGroup(packet);
        return null;
    }
}
