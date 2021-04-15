package com.example.train.entity.dto;

import lombok.Data;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/4/1 21:06
 */
@Data
public class QuestionDto {
    /**
     * 题目Id
     */
    private Integer questionId;
    /**
     * 题干
     */
    private String questionName;
    /**
     * 题目选项
     */
    private String questionOption;
    /**
     * 题目正确答案
     */
    private String answer;
    /**
     * 题目所属类别
     */
    private String questionTypeName;
}
