package com.example.inspect.entity;

import lombok.Data;

/**
 * @author soloist
 * @version 1.0
 * @describe 巡检项目
 * @date 2021/1/6 11:34
 */
@Data
public class InspectProject {
    /**
     * 检查项目Id
     */
    private Integer id;
    /**
     * 检查项目分类类型Id
     */
    private Integer projectTypeId;
    /**
     * 检查项目具体内容
     */
    private String projectContent;
    /**
     * 检查项目选项，字符串类型以‘#’分割
     */
    private String projectOption;
}
