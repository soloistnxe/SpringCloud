package com.example.inspect.controller;

import com.example.inspect.common.Result;
import com.example.inspect.service.RpcRecommendService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/4/27 14:14
 */
@RequestMapping("/recommend")
@RestController
public class RecommendController {
    @Resource
    private RpcRecommendService rpcRecommendService;

    @RequestMapping("/rpcRecommend")
    public Result getRecommend(@RequestParam(value="companyId") Integer companyId){
        return rpcRecommendService.recommend(companyId);
    }
}
