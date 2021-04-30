package com.example.train.controller;

import com.example.inspect.common.Result;
import com.example.train.service.Recommend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    /**
     * 注入服务"service-hi"的Feign客户端ServiceHi
     */
    @Autowired
    private Recommend recommend;

    @RequestMapping("/hi")
    public Result find(@RequestParam(value="companyId") Integer companyId) {
        return  recommend.getRecommend(companyId);
    }

    @RequestMapping("/la")
    public String la() {
        return "你好";
    }
}

