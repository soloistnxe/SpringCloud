package com.example.train.controller;

import com.example.train.common.Result;
import com.example.train.entity.vo.QuestionVo;
import com.example.train.service.QuestionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/4/1 20:56
 */
@RestController
@RequestMapping("/question")
public class QuertionController {
    @Resource
    private QuestionService questionService;

    @RequestMapping("/findPage")
    public Result findQuestionPage(@RequestParam(value="pageNum") Integer pageNum,
                                   @RequestParam(value="pageSize") Integer pageSize,
                                   @RequestParam(value="query") String query){

        return questionService.findPage(pageNum,pageSize,query);
    }
    @RequestMapping("/findQuestionType")
    public Result findQuestionType(){
        return questionService.findQuestionType();
    }
    @RequestMapping("/insertQuestion")
    public Result insertQuestion(QuestionVo questionVo){
        return questionService.insertQuestion(questionVo);
    }
    @RequestMapping("/updateQuestion")
    public Result updateQuestion(QuestionVo questionVo){
        return questionService.updateQuestion(questionVo);
    }
    @RequestMapping("/deleteQuestion/{questionId}")
    public Result deleteQuestion(@PathVariable(value = "questionId") Integer questionId){
        return questionService.deleteQuestion(questionId);
    }

}
