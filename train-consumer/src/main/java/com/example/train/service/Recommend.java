package com.example.train.service;

import com.example.inspect.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>一个Feign服务消费者接口</p>
 **/
@FeignClient(value = "inspect-provider")
public interface Recommend {

    /**
     * <p>通过Feign伪Http客户端调用service-hi提供的服务</p>
     * @author hanchao 2018/5/19 17:59
     **/
    @GetMapping("/hi/{name}")
    String sayHiFromServiceHi(@PathVariable(value = "name") String name);

    @GetMapping("/recommend/rpcRecommend")
    Result getRecommend(@RequestParam(value="companyId") Integer companyId);

}
