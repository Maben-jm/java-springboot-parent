package com.example.rocket.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ProducerConfigure {

    private Logger log = LoggerFactory.getLogger(ProducerConfigure.class);

    @Value("${rocketmq.producer.default.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.producer.default.groupName}")
    private String groupName;


    /**
     * 创建普通消息发送者实例
     */
    @Bean
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        log.info("defaultProducer 正在创建---------------------------------------");
        //开启消息轨迹
        DefaultMQProducer producer = new DefaultMQProducer(groupName,true);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setVipChannelEnabled(false);
        producer.setRetryTimesWhenSendAsyncFailed(10);
        producer.start();
        log.info("rocketmq producer server开启成功---------------------------------.");
        return producer;
    }
}
