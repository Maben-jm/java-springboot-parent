package com.example.rocket.config;

import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestTransactionListener implements TransactionListener {
    Logger log = LoggerFactory.getLogger(TestTransactionListener.class);
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        return LocalTransactionState.COMMIT_MESSAGE;
    }

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info(new String(msg.getBody()));
        return LocalTransactionState.COMMIT_MESSAGE;
    }

}