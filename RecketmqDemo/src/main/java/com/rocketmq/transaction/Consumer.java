package com.rocketmq.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author w
 * @date 2020/11/19
 */
public class Consumer {
    public static void main(String[] args) throws MQClientException {
        //1.����������Consumer���ƶ�����������
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2.ָ��Nameserver��ַ
        consumer.setNamesrvAddr("134.98.104.247:9876;134.98.104.248:9876");
        //3.��������Topic��Tag
        consumer.subscribe("TransactionTopic", "*");


        //4.���ûص�������������Ϣ
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            //������Ϣ����
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println("consumeThread=" + Thread.currentThread().getName() + "," + new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5.����������consumer
        consumer.start();
        System.out.println("����������");
    }
}
