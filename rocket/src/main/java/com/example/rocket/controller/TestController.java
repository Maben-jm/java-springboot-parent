package com.example.rocket.controller;

import com.alibaba.fastjson.JSON;
import com.example.rocket.config.TestTransactionListener;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private DefaultMQProducer defaultMQProducer;



    @GetMapping("/test")
    public void test(String info) throws Exception {
        Message message = new Message("TopicTest", "Tag1", "12345", info.getBytes());
                // 这里用到了这个mq的异步处理，类似ajax，可以得到发送到mq的情况，并做相应的处理
               //不过要注意的是这个是异步的
        defaultMQProducer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("传输成功");
                log.info(JSON.toJSONString(sendResult));
            }
            @Override
            public void onException(Throwable e) {
                log.error("传输失败", e);
            }
        });
    }
    @Autowired
    private TransactionMQProducer transactionMQProducer;

    @Autowired
    private TestTransactionListener testTransactionListener;

    @GetMapping("/test/transaction")
    public void testTransaction(String info) throws Exception {
        Message message = new Message("TopicTest", "Tag1", "12345", info.getBytes());
        // 这里用到了这个mq的异步处理，类似ajax，可以得到发送到mq的情况，并做相应的处理
        //不过要注意的是这个是异步的
        transactionMQProducer.setTransactionListener(testTransactionListener);
        transactionMQProducer.setSendMsgTimeout(10000);
        transactionMQProducer.sendMessageInTransaction(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("传输成功---transaction");
                log.info(JSON.toJSONString(sendResult));
            }
            @Override
            public void onException(Throwable e) {
                log.error("传输失败", e);
            }
        });
    }
}
