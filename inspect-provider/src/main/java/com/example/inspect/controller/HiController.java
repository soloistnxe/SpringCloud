package com.example.inspect.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {

    /**
     * 获取端口号
     */
    @Value("${server.port}")
    String port;

    /**
     * 定义一个简单接口
     *
     * @param name
     * @return
     */
    @GetMapping("/hi/{name}")
    public String home(@PathVariable String name) {
        return "你好 " + name + ",I am from port :" + port;
    }
}
