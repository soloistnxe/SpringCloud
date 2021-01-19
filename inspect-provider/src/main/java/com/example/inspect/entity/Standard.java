package com.example.inspect.entity;

import lombok.Data;

/**
 * @describe 检查标准实体类
 * @author soloist
 * @version 1.0
 * @date 2021/1/6 10:38
 */
@Data
public class Standard {
    /**
     * 检查标准Id
     */
    private int standardId;
    /**
     * 检查类型
     */
    private int standardType;
    /**
     * 检查标准内容
     */
    private String standardContent;
}
