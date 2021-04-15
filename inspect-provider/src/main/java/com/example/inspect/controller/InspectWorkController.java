package com.example.inspect.controller;

import com.example.inspect.common.Result;
import com.example.inspect.entity.InspectWork;
import com.example.inspect.entity.vo.InspectWorkVo;
import com.example.inspect.service.InspectWorkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/2/10 19:00
 */
@RestController
@RequestMapping("/work")
public class InspectWorkController {
    @Resource
    private InspectWorkService inspectWorkService;

    @RequestMapping(value = "/findPage")
    public Result findWork(@RequestParam(value="pageNum") Integer pageNum,
                              @RequestParam(value="pageSize") Integer pageSize,
                              @RequestParam(value="query") String query){
        return inspectWorkService.findPage(pageNum,pageSize,query);
    }
    @RequestMapping(value = "/insertWork",method = RequestMethod.POST)
    public Result insertWork(InspectWork inspectWork){
        return inspectWorkService.insertWork(inspectWork);
    }

    @RequestMapping(value = "/updateWork",method = RequestMethod.POST)
    public Result updateWork(@RequestBody InspectWorkVo inspectWorkVo){
        System.out.println(inspectWorkVo);
        return inspectWorkService.updateWork(inspectWorkVo);
    }
    @RequestMapping("/deleteWork/{id}")
    public Result deleteWork(@PathVariable(value = "id") Integer workId){
        return inspectWorkService.deleteWork(workId);
    }
}
