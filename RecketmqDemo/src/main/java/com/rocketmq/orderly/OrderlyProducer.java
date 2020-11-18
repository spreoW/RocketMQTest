package com.rocketmq.orderly;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author w
 * @date 2020/11/18
 * Ĭ�ϵ��������Ϣ���ͻ��ȡRound Robin��ѯ��ʽ����Ϣ���͵���ͬ��queue(��������)����������Ϣ��ʱ��Ӷ��queue����ȡ��Ϣ
 * ����������Ʒ��͵�˳����Ϣֻ���η��͵�ͬһ��queue�У����ѵ�ʱ��ֻ�����queue��������ȡ����ͱ�֤��˳��
 * �����ͺ����Ѳ����queueֻ��һ��������ȫ������������queue���룬��Ϊ�������򣬼����ÿ��queue����Ϣ��������ġ�
 */
public class OrderlyProducer {
    public static void main(String[] args) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {

        //1.����product��ָ��������
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2.ָ��NameServer��ַ
        producer.setNamesrvAddr("134.98.104.247:9876;134.98.104.248:9876");
        //3.������Ϣ����
        List<OrderStep> orderSteps = new ArrayList<OrderStep>();
        //4.������Ϣ
        for (int i = 0; i < orderSteps.size(); i++) {
            String body = orderSteps.get(i) + "";
            Message message = new Message("OrderTopic","Order","i"+i,body.getBytes());
            /**
             * ����һ����Ϣ����
             * ����������Ϣ���е�ѡ����
             * ��������ѡ����е�ҵ���ʶ������ID��
             */
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object arg) {
                    String argString = (String) arg;
                    long orderId = Long.valueOf(argString);
                    long index = orderId % list.size();
                    return list.get((int) index);
                }
            }, orderSteps.get(i).getOrderId());
            System.out.println("���ͽ����" + sendResult);
        }
        //5.�ر�������
        producer.shutdown();
    }
}
