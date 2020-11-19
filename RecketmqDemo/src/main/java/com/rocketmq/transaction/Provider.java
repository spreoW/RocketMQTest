package com.rocketmq.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.TimeUnit;

/**
 * @author w
 * @date 2020/11/19
 */
public class Provider {
    public static void main(String[] args) throws MQClientException, InterruptedException {
        //1.����������Ϣ�����ߣ�ָ����Ϣ��
        TransactionMQProducer producer = new TransactionMQProducer("group5");
        //2.����nameserver��ַ
        producer.setNamesrvAddr("134.98.104.247:9876;134.98.104.248:9876");
        //������������
        producer.setTransactionListener(new TransactionListener() {
            /**
             * �ڸ÷�����ִ�б�������
             * @param message
             * @param o
             * @return
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                if (StringUtils.equals("TAGA", message.getTags())) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if (StringUtils.equals("TAGB", message.getTags())) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else if (StringUtils.equals("TAGC", message.getTags())) {
                    return LocalTransactionState.UNKNOW;
                }
                return LocalTransactionState.UNKNOW;
            }

            /**
             * �÷���ʱMQ������Ϣ����״̬�ز�
             * @param messageExt
             * @return
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("��Ϣ��Tag:" + messageExt.getTags());
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        producer.start();
        //3.������Ϣ������
        String[] tags = {"TAGA", "TAGB", "TAGC"};

        for (int i = 0; i < 3; i++) {
            //4.������Ϣ����ָ������Topic��Tag����Ϣ��
            /**
             * ����һ����Ϣ����Topic
             * ����������ϢTag
             * ����������Ϣ����
             */
            Message msg = new Message("TransactionTopic", tags[i], ("Hello World" + i).getBytes());
            //5.������Ϣ
            SendResult result = producer.sendMessageInTransaction(msg, null);
            //����״̬
            SendStatus status = result.getSendStatus();

            System.out.println("���ͽ��:" + result);

            //�߳�˯1��
            TimeUnit.SECONDS.sleep(2);
        }
        //6.�ر���Ϣ������
        //producer.shutdown();
    }
}
