package com.example.inspect.controller;

import com.example.inspect.Arithmetic.knn.KNNData;
import com.example.inspect.common.Result;
import com.example.inspect.service.InspectWorkService;
import com.example.inspect.service.ReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
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

    /**
     * 主成分分析
     * @return
     */
    @RequestMapping("/pca")
    public Result getPCA(){
        return reportService.getPCA();
    }

    /**
     * 查询所有不合格项目
     * @return
     */
    @RequestMapping("/unqualified")
    public Map<String,List<String>> findUnqualified(){
        return inspectWorkService.unqualified();
    }

    /**
     * 推荐
     * @return
     */
    @RequestMapping("/apriori")
    public Result getApriori(){
        return reportService.getAprioriReport();
    }

    /**
     * K近邻算法推荐，还在开发中
     * @return
     */
    @RequestMapping("/knn")
    public Result map(Integer companyId){
        Result result = new Result();
        Map<String,Object> map = new HashMap<>();
        List<KNNData> knnData = inspectWorkService.getKnnData();

        map.put("knndata",knnData);
        result.setData(map);
        return result;
    }
}
