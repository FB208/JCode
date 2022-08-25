package com.fb208.jcode.main.rocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 顺序消费示例
 */
@Slf4j
public class ConsumerMain3 {
    public static void main(String[] args) {
        AclClientRPCHook acl = new AclClientRPCHook(new SessionCredentials("demodemo", "demodemo"));
        //消息队列分配算法AllocateMessageQueueAveragely
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("demo-consumer2", acl, new AllocateMessageQueueAveragely());
        consumer.setNamesrvAddr("47.92.51.45:9876");
        //设置消费者的消费模式：广播模式：所有客户端接收的消息都是一样的；默认的模式：负载均衡
        consumer.setMessageModel(MessageModel.CLUSTERING);
        //消费模式:一个新的订阅组第一次启动从队列的最后位置开始消费 后续再启动接着上次消费的进度开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //订阅主题和 标签（ * 代表所有标签)下信息
        try {
            consumer.subscribe("demo-topic", "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        //注册消费的监听 并在此监听中消费信息，并返回消费的状态信息
        consumer.registerMessageListener(new MessageListenerOrderly() {

            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                // 设置自动提交
                context.setAutoCommit(true);
                for (MessageExt msg : msgs) {
                    String body = new String(msg.getBody(), StandardCharsets.UTF_8);
                    log.info("打印队列消息：{}",body);
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        try {
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("消费者启动完毕！");
    }
}

