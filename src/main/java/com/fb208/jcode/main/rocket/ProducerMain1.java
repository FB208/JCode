package com.fb208.jcode.main.rocket;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
public class ProducerMain1 {
    public static void main(String[] args) {
        AclClientRPCHook acl = new AclClientRPCHook(new SessionCredentials("demodemo", "demodemo"));

        //示例生产者
        DefaultMQProducer  producer = new DefaultMQProducer("group1", acl);
        //不开启vip通道 开通口端口会减2
        producer.setVipChannelEnabled(false);
        producer.setDefaultTopicQueueNums(8);
        //绑定name server
        producer.setNamesrvAddr("47.92.51.45:9876");
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }



        try {
            int index = 0;
            while (true)
            {
                //创建生产信息
                Message message = new Message("demo-topic", "group1", ("main1:"+index++).getBytes());
                SendResult sendResult = producer.send(message);
                log.info(sendResult.getMsgId());
                Thread.sleep(1000*10);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            producer.shutdown();
        }
    }
}
