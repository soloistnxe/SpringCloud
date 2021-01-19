package com.example.inspect.entity;

import lombok.Data;
/**
 * @author soloist
 * @version 1.0
 * @describe 抽检检查工作
 * @date 2021/1/7 11:29
 */
@Data
public class Work {
    /**
     * 主键Id,检查工作id
     */
    private Integer id;
    /**
     * 检查工作名称
     */
    private String workName;
    /**
     * 检查工作描述
     */
    private String workDescription;

}
