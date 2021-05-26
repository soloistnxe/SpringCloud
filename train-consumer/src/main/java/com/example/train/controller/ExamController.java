package com.example.train.controller;

import com.example.train.common.Result;
import com.example.train.entity.vo.RecordVo;
import com.example.train.service.ExamService;
import com.example.train.service.Recommend;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/5/14 16:12
 */
@RestController
@RequestMapping("/exam")
public class ExamController {
    @Resource
    private ExamService examService;

    /**
     * 注入推荐服务的Feign客户端Recommend
     */
    @Resource
    private Recommend recommend;

    @RequestMapping("/random")
    public Result randomExam() {
        return examService.randomExam();
    }

    @RequestMapping("/commit")
    public Result commit(@RequestBody RecordVo recordVo) {
        return examService.commitExam(recordVo);
    }

    @RequestMapping("/recommend/{companyId}")
    public Result find(@PathVariable(value = "companyId") Integer companyId) {
        com.example.inspect.common.Result res = recommend.getRecommend(companyId);
        Result result = new Result();
        if (res.getCode() != 200) {
            result.setCode(res.getCode());
            result.setMessage(res.getMessage());
            result.setSuccess(res.getSuccess());
            return result;
        } else {
            Object recommend1 = res.getData().get("recommend");
            String[] split = recommend1.toString().split(",");
            List<String> data = new ArrayList<>();
            for (String s : split) {
                data.add(s.trim());
            }
            return examService.recommendExam(data);
        }
    }
}
