package com.example.train.controller;

import com.example.train.common.Result;
import com.example.train.entity.Record;
import com.example.train.service.RecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/5/24 10:44
 */
@RestController
@RequestMapping("/record")
public class RecordController {
    @Resource
    private RecordService recordService;

    @RequestMapping("/findPage")
    public Result find(
            @RequestParam(value = "consumerId") Integer consumerId,
            @RequestParam(value = "pageNum") Integer pageNum,
                       @RequestParam(value = "pageSize") Integer pageSize,
                       @RequestParam(value = "startDate") String startdate,
                       @RequestParam(value = "endDate") String enddate) {

        return recordService.findpage(consumerId,pageNum,pageSize,startdate,enddate);
    }
    @RequestMapping("/rank")
    public Result rank(){
        return recordService.findRank();
    }
}
