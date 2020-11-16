package com.springboot.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.service.IUserServer;
import org.springframework.stereotype.Component;

/**
 * @author wangquan
 * @date 2020/11/16
 */
@Component
@Service(interfaceClass = IUserServer.class)
public class UserServiceImpl implements IUserServer {
    @Override
    public String sayHello(String name) {
        return "Hello: "+name;
    }
}
