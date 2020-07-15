package com.example.rocket.config;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public abstract class ConsumerConfigure {
    private Logger log = LoggerFactory.getLogger(ConsumerConfigure.class);
    @Value("${rocketmq.producer.default.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.producer.default.groupName}")
    private String groupName;

    // 开启消费者监听服务
    public void listener(String topic, String tag) throws MQClientException {
        log.info("开启" + topic + ":" + tag + "消费者-------------------");
        //开启消息轨迹
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName,true);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.subscribe(topic, tag);
        // 开启内部类实现监听
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                return ConsumerConfigure.this.dealBody(msgs);
            }
        });
        consumer.start();
        log.info("rocketmq启动成功---------------------------------------");

    }
    
    // 处理body的业务
    public abstract ConsumeConcurrentlyStatus dealBody(List<MessageExt> msgs);

}
