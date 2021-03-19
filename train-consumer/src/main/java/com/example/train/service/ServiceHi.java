package com.example.train.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * <p>一个Feign服务消费者接口</p>
 **/
@FeignClient(value = "inspect-provider")
public interface ServiceHi {

    /**
     * <p>通过Feign伪Http客户端调用service-hi提供的服务</p>
     * @author hanchao 2018/5/19 17:59
     **/
    @GetMapping("/hi/{name}")
    String sayHiFromServiceHi(@PathVariable(value = "name") String name);

    @GetMapping("/report/unqualified")
    Map<String,List<String>> findUnqualified();



}
