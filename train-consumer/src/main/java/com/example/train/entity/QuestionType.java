package com.example.train.entity;

import lombok.Data;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/2/22 10:41
 */
@Data
public class QuestionType {
    /**
     * 题目类别Id
     */
    private Integer questionTypeId;
    /**
     * 题目类别名称
     */
    private String questionTypeName;
    /**
     * 类别描述
     */
    private String describe;
}
