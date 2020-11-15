package com.rocketmq.test.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;



/**
 * @author wangquan
 * @date 2020/11/15
 */
@Slf4j
@RocketMQMessageListener(topic = "springboot-mq",consumerGroup = "${rocketmq.consumer.group}")
@Component
public class Comsumer implements RocketMQListener<String>{

    @Override
    public void onMessage(String s) {
        System.out.println("接收到消息："+s);
    }
}
