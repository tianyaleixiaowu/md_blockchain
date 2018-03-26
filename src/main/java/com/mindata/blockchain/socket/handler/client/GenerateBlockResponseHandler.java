package com.mindata.blockchain.socket.handler.client;

import com.mindata.blockchain.ApplicationContextProvider;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.core.event.AddBlockEvent;
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
        baseResponse.setObject(rpcCheckBlockBody.getBlock());
        HalfAgreeChecker.halfCheck(respId, baseResponse, new HalfAgreeCallback() {
            @Override
            public void agree() {
                //发布新生成了区块事件
                ApplicationContextProvider.publishEvent(new AddBlockEvent(baseResponse.getObject()));
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
