package com.example.rocket.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ProducerTransactionConfigure {

    private Logger log = LoggerFactory.getLogger(ProducerTransactionConfigure.class);

    @Value("${rocketmq.producer.transaction.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.producer.transaction.groupName}")
    private String groupName;


    /**
     * 创建普通消息发送者实例
     */
    @Bean
    public TransactionMQProducer  transactionMQProducer() throws MQClientException {
        log.info("defaultProducer 正在创建---------------------------------------");
        //开启消息轨迹
        TransactionMQProducer producer = new TransactionMQProducer(groupName);
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });
        producer.setExecutorService(executorService);
        producer.setNamesrvAddr(namesrvAddr);
        producer.start();
        log.info("rocketmq producer server开启成功---------------------------------.");
        return producer;
    }
}
