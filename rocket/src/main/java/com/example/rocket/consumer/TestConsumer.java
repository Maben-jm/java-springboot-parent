package com.example.rocket.consumer;

import com.example.rocket.config.ConsumerConfigure;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Configuration
public class TestConsumer extends ConsumerConfigure implements ApplicationListener<ContextRefreshedEvent> {

    private Logger log = LoggerFactory.getLogger(TestConsumer.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        try {
            super.listener("TopicTest", "Tag1");
        } catch (MQClientException e) {
            log.error("消费者监听器启动失败", e);
        }

    }

    @Override
    public ConsumeConcurrentlyStatus dealBody(List<MessageExt> msgs) {
        int num = 1;
        log.info("进入:::"+msgs.size());
        for (MessageExt msg : msgs) {
            log.info("第" + num + "次消息");
            try {
                String msgStr = new String(msg.getBody(), "utf-8");
                if (msgStr.contains("transaction")) {
                    throw new Exception("事务MQ,测试一哈");
                }
                log.info(msgStr);
            } catch (Exception e) {
                log.error("consumer error:" + e.getMessage());
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}

