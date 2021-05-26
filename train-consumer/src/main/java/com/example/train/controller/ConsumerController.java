package com.example.train.controller;

import com.example.train.common.Result;
import com.example.train.dao.ConsumerDao;
import com.example.train.entity.Consumer;
import com.example.train.service.ConsumerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/2/24 11:35
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Resource
    private ConsumerDao consumerDao;
    @Resource
    private ConsumerService consumerService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(String consumerName,String password){
        return consumerService.login(consumerName,password);
    }

    @RequestMapping("/findAll")
    public List<Consumer> findAll(){
        List<Consumer> all = new ArrayList<>();
        try {
             all = consumerDao.findAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return all;
    }
    @RequestMapping("/findPage")
    public Result findPage(@RequestParam(value="pageNum") Integer pageNum,
                           @RequestParam(value="pageSize") Integer pageSize,
                           @RequestParam(value="query") String query){
        return consumerService.findPage(pageNum,pageSize,query);

    }
    @RequestMapping("/insertConsumer")
    public Result insertConsumer(Consumer consumer){
        return consumerService.insertConsumer(consumer);
    }
    @RequestMapping("/updateConsumer")
    public Result update(Consumer consumer){
        return consumerService.update(consumer);
    }
    @RequestMapping("/deleteConsumer/{consumerId}")
    public Result delete(@PathVariable(value = "consumerId") Integer consumerId){
        return consumerService.delete(consumerId);
    }



}
