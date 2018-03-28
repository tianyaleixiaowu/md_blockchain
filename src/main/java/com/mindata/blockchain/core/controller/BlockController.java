package com.mindata.blockchain.core.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.mindata.blockchain.ApplicationContextProvider;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.block.InstructionPair;
import com.mindata.blockchain.block.Operation;
import com.mindata.blockchain.common.exception.TrustSDKException;
import com.mindata.blockchain.core.bean.BaseData;
import com.mindata.blockchain.core.bean.ResultGenerator;
import com.mindata.blockchain.core.event.DbSyncEvent;
import com.mindata.blockchain.core.manager.MessageManager;
import com.mindata.blockchain.core.manager.SyncManager;
import com.mindata.blockchain.core.manager.DbBlockManager;
import com.mindata.blockchain.core.requestbody.BlockRequestBody;
import com.mindata.blockchain.core.requestbody.InstructionBody;
import com.mindata.blockchain.core.service.BlockService;
import com.mindata.blockchain.core.service.InstructionService;
import com.mindata.blockchain.socket.body.RpcBlockBody;
import com.mindata.blockchain.socket.client.PacketSender;
import com.mindata.blockchain.socket.packet.BlockPacket;
import com.mindata.blockchain.socket.packet.PacketBuilder;
import com.mindata.blockchain.socket.packet.PacketType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    @Resource
    private InstructionService instructionService;
    @Resource
    private SyncManager syncManager;
    @Resource
    private MessageManager messageManager;

    /**
     * 添加一个block
     *
     * @param blockRequestBody
     *         指令的集合
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
    public BaseData test(String content) throws Exception {
        InstructionBody instructionBody = new InstructionBody();
        instructionBody.setOperation(Operation.ADD);
        instructionBody.setTable("message");
        instructionBody.setJson("{\"content\":\"" + content + "\"}");
        instructionBody.setPublicKey("A8WLqHTjcT/FQ2IWhIePNShUEcdCzu5dG+XrQU8OMu54");
        instructionBody.setPrivateKey("yScdp6fNgUU+cRUTygvJG4EBhDKmOMRrK4XJ9mKVQJ8=");
        InstructionPair instructionPair = instructionService.build(instructionBody);

        BlockRequestBody blockRequestBody = new BlockRequestBody();
        blockRequestBody.setPublicKey("A8WLqHTjcT/FQ2IWhIePNShUEcdCzu5dG+XrQU8OMu54");
        com.mindata.blockchain.block.BlockBody blockBody = new com.mindata.blockchain.block.BlockBody();
        blockBody.setInstructionReverses(CollectionUtil.newArrayList(instructionPair.getInstructionReverse()));
        blockBody.setInstructions(CollectionUtil.newArrayList(instructionPair.getInstruction()));

       blockRequestBody.setBlockBody(blockBody);

        return ResultGenerator.genSuccessResult(blockService.addBlock(blockRequestBody));
    }

    @GetMapping("/sqlite")
    public BaseData lastBlock() {
        return ResultGenerator.genSuccessResult(messageManager.findAll());
    }

    @GetMapping("db")
    public BaseData getRockDB() {
        return ResultGenerator.genSuccessResult(dbBlockManager.getLastBlock());
    }

    @GetMapping("sync")
    public BaseData async(@PageableDefault Pageable pageable) {
        ApplicationContextProvider.publishEvent(new DbSyncEvent(""));
        return ResultGenerator.genSuccessResult(syncManager.findAll(pageable));
    }

    @GetMapping("/next")
    public BaseData nextBlock() {
        Block block = dbBlockManager.getFirstBlock();
        BlockPacket packet = new PacketBuilder<RpcBlockBody>()
                .setType(PacketType.NEXT_BLOCK_INFO_REQUEST)
                .setBody(new RpcBlockBody(block)).build();
        packetSender.sendGroup(packet);
        return null;
    }
}
