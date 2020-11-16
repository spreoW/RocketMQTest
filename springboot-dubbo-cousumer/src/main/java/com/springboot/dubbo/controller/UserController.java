package com.springboot.dubbo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.service.IUserServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangquan
 * @date 2020/11/16
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private IUserServer userService;

    @RequestMapping("/sayHello")
    public String sayHello(String name){
        return userService.sayHello(name);
    }

}
