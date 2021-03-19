package com.example.inspect.controller;

import com.example.inspect.common.Result;
import com.example.inspect.service.InspectWorkService;
import com.example.inspect.service.ReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/2/17 11:25
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Resource
    private ReportService reportService;
    @Resource
    private InspectWorkService inspectWorkService;
    @RequestMapping("/score")
    public Result findScore(){
        return reportService.findScore();
    }

    @RequestMapping("/unqualified")
    public Map<String,List<String>> findUnqualified(){
        return inspectWorkService.unqualified();
    }
    @RequestMapping("/recommend")
    public Result getRec(){
        return reportService.getRecommend();
    }
}
