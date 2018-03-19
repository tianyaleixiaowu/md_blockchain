package com.mindata.blockchain.socket.handler.client;

import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.CheckBlockBody;
import com.mindata.blockchain.socket.client.RequestResponseMap;
import com.mindata.blockchain.socket.holder.BaseResponse;
import com.mindata.blockchain.socket.packet.BlockPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

/**
 * 对别人请求生成区块的回应
 * @author wuweifeng wrote on 2018/3/12.
 */
public class GenerateBlockResponseHandler extends AbstractBlockHandler<CheckBlockBody> {
    private Logger logger = LoggerFactory.getLogger(GenerateBlockResponseHandler.class);

    @Override
    public Class<CheckBlockBody> bodyClass() {
        return CheckBlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, CheckBlockBody checkBlockBody, ChannelContext channelContext) {
        logger.info("收到来自于<" + checkBlockBody.getAppId() + ">的回复，<回应生成Block>，code为：" + checkBlockBody.getCode() +
                "，message为：" + checkBlockBody.getMessage());
        //节点回应时会带着当初客户端请求时的msgId
        String respId = checkBlockBody.getResponseMsgId();
        //code为0时为同意
        int code = checkBlockBody.getCode();
        //将该回复添加到list中，并做过半校验
        BaseResponse<Block> baseResponse = new BaseResponse<>(checkBlockBody);
        baseResponse.setAgree(code == 0);
        baseResponse.setObject(checkBlockBody.getBlock());
        RequestResponseMap.add(respId, baseResponse);

        return null;
    }
}
