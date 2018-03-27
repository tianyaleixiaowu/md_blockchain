package com.mindata.blockchain.socket.handler.client;

import com.mindata.blockchain.ApplicationContextProvider;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.core.event.AddBlockEvent;
import com.mindata.blockchain.core.manager.DbBlockManager;
import com.mindata.blockchain.core.requestbody.BlockRequestBody;
import com.mindata.blockchain.core.service.BlockService;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.RpcCheckBlockBody;
import com.mindata.blockchain.socket.holder.BaseResponse;
import com.mindata.blockchain.socket.holder.HalfAgreeCallback;
import com.mindata.blockchain.socket.holder.HalfAgreeChecker;
import com.mindata.blockchain.socket.packet.BlockPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

/**
 * 对别人请求生成区块的回应
 * @author wuweifeng wrote on 2018/3/12.
 */
public class GenerateBlockResponseHandler extends AbstractBlockHandler<RpcCheckBlockBody> {
    private Logger logger = LoggerFactory.getLogger(GenerateBlockResponseHandler.class);

    @Override
    public Class<RpcCheckBlockBody> bodyClass() {
        return RpcCheckBlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, RpcCheckBlockBody rpcCheckBlockBody, ChannelContext channelContext) {
        logger.info("收到来自于<" + rpcCheckBlockBody.getAppId() + ">的回复，<回应生成Block>，code为：" + rpcCheckBlockBody.getCode() +
                "，message为：" + rpcCheckBlockBody.getMessage());
        //节点回应时会带着当初客户端请求时的msgId
        String respId = rpcCheckBlockBody.getResponseMsgId();
        //code为0时为同意
        int code = rpcCheckBlockBody.getCode();
        //将该回复添加到list中，并做过半校验
        BaseResponse<Block> baseResponse = new BaseResponse<>(rpcCheckBlockBody);
        baseResponse.setAgree(code == 0);
        Block block = rpcCheckBlockBody.getBlock();
        baseResponse.setObject(block);
        HalfAgreeChecker.halfCheck(respId, baseResponse, new HalfAgreeCallback() {
            @Override
            public void agree() {
                logger.info("大家已同意生成block");
                DbBlockManager dbBlockManager = ApplicationContextProvider.getBean(DbBlockManager.class);
                //判断是否确实可以生成，避免在大家投票期间，已经拉取了新的区块
                if(dbBlockManager.checkCanBeNextBlock(block)) {
                    //发布新生成了区块事件
                    ApplicationContextProvider.publishEvent(new AddBlockEvent(baseResponse.getObject()));
                } else {
                    logger.info("生成新区块时，本地校验不通过！正在重新构建区块，并群发请求");
                    //重新构建新区块
                    BlockService blockService = ApplicationContextProvider.getBean(BlockService.class);
                    BlockRequestBody blockRequestBody = new BlockRequestBody();
                    blockRequestBody.setBlockBody(block.getBlockBody());
                    blockRequestBody.setPublicKey(block.getBlockBody().getInstructions().get(0).getPublicKey());
                    blockService.addBlock(blockRequestBody);
                }
            }

            @Override
            public void reject() {

            }

            @Override
            public void agreeCount(int agreeCount, int rejectCount) {
                logger.info("已有<" + agreeCount + ">个同意,<" + rejectCount + ">个拒绝");
            }
        });

        return null;
    }
}
