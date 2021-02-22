package com.example.train.controller;

import com.example.inspect.entity.Users;
import com.example.train.service.ServiceHi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {
    /**
     * 注入服务"service-hi"的Feign客户端ServiceHi
     */
    @Autowired
    private ServiceHi serviceHi;

    @GetMapping("/hi")
    public List<Users> find() {
        return serviceHi.findAll();
    }

    @RequestMapping("/la")
    public String la() {
        return "你好";
    }
}

