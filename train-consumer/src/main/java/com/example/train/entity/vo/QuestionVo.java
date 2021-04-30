package com.example.train.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/4/1 21:34
 */
@Data
public class QuestionVo {
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
    private List<String> questionOption;
    /**
     * 题目正确答案
     */
    private String answer;
    /**
     * 题目所属类别Id
     */
    private Integer questionTypeId;
    /**
     * 题目所属类别
     */
    private String questionTypeName;
}
