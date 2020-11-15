package com.rocketmq.test;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RocketmqTestProviderApplicationTests {

	@Autowired
	private RocketMQTemplate rocketMQTemplate;

	@Test
	void contextLoads() {
		rocketMQTemplate.convertAndSend("springboot-mq","hello springboot rocketmq");
	}

}
