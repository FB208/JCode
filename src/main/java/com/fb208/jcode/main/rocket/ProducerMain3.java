package com.fb208.jcode.main.rocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

/**
 * 顺序生产示例
 */
@Slf4j
public class ProducerMain3 {
    public static void main(String[] args) {
        AclClientRPCHook acl = new AclClientRPCHook(new SessionCredentials("demodemo", "demodemo"));

        //示例生产者
        DefaultMQProducer producer = new DefaultMQProducer("group1", acl);
        //不开启vip通道 开通口端口会减2
        producer.setVipChannelEnabled(false);
        //绑定name server
        producer.setNamesrvAddr("47.92.51.45:9876");
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }


        try {
            int index = 0;
            while (true) {
                //创建生产信息
                Message message = new Message("demo-topic", "group1", ("main3:" + index++).getBytes());

                SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        Integer id = (Integer) arg;
                        int index = id % mqs.size();
                        return mqs.get(index);
                    }
                }, 0);
                log.info(sendResult.getMsgId());
                Thread.sleep(1000 * 10);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }
}
