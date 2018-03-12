package com.mindata.blockchain.socket.base;

import com.mindata.blockchain.socket.body.BaseBody;
import com.mindata.blockchain.socket.common.Const;
import com.mindata.blockchain.socket.packet.BlockPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * 基础handler
 * @author tanyaowu
 * 2017年3月27日 下午9:56:16
 */
public abstract class AbstractBlockHandler<T extends BaseBody> implements HandlerInterface {
	private static Logger log = LoggerFactory.getLogger(AbstractBlockHandler.class);

	public AbstractBlockHandler() {
	}

	public abstract Class<T> bodyClass();

	@Override
	public Object handler(BlockPacket packet, ChannelContext channelContext) throws Exception {
		String jsonStr;
		T bsBody = null;
		if (packet.getBody() != null) {
			jsonStr = new String(packet.getBody(), Const.CHARSET);
			bsBody = Json.toBean(jsonStr, bodyClass());
		}

		return handler(packet, bsBody, channelContext);
	}

	public abstract Object handler(BlockPacket packet, T bsBody, ChannelContext channelContext) throws Exception;

}
