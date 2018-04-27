package com.mindata.blockchain.socket.distruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.mindata.blockchain.socket.distruptor.base.BaseEvent;
import com.mindata.blockchain.socket.distruptor.base.BaseEventFactory;
import com.mindata.blockchain.socket.distruptor.base.MessageProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author wuweifeng wrote on 2018/4/20.
 */
@Configuration
public class DisruptorConfig {

    private Disruptor<BaseEvent> disruptor() {
        ThreadFactory producerFactory = Executors.defaultThreadFactory();
        BaseEventFactory eventFactory = new BaseEventFactory();
        int bufferSize = 1024;
        Disruptor<BaseEvent> disruptor = new Disruptor<>(eventFactory, bufferSize, producerFactory,
                ProducerType.SINGLE, new BlockingWaitStrategy());
        //两个消费者，任何消息都会同时被两个消费者消费，消费者会根据type来判断哪个是该自己处理的
        disruptor.handleEventsWith(new DisruptorServerHandler(), new DisruptorClientHandler());

        disruptor.start();

        return disruptor;
    }

    @Bean
    public MessageProducer messageProducer() {
        return new DisruptorProducer(disruptor());
    }
}
