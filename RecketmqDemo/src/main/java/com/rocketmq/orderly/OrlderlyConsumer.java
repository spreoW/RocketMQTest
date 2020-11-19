package com.rocketmq.orderly;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author w
 * @date 2020/11/18
 */
public class OrlderlyConsumer {
    public static void main(String[] args) throws MQClientException {
        //1.创建消息消费者，指定消费组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2.指定nameserver地址
        consumer.setNamesrvAddr("192.168.31.37:9876;192.168.31.38:9876");
        //3.订阅Topic和Tag
        consumer.subscribe("OrderTopic", "Order");
        //4.注册消息监听器
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                for (MessageExt msg : list) {
                    System.out.println("线程名称：【"+Thread.currentThread().getName()+"】"+new String(msg.getBody()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        //5.启动消费者
        consumer.start();
        System.out.println("Consumer Started.");
    }
}
