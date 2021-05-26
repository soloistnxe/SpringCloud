package com.example.train.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/5/17 10:40
 */
@Data
public class QuestionDetail{
    /**
     * 题目Id
     */
    private Integer questionId;
    /**
     * 题目题干
     */
    private String questionName;
    /**
     * 题目选项
     */
    private List<String> questionOption;
    /**
     * 用户提交的答案
     */
    private String consumerOption;
    /**
     * 题目正确答案
     */
    private String correctAnswer;
    /**
     * 是否答对
     */
    private String isCorrect;

}
