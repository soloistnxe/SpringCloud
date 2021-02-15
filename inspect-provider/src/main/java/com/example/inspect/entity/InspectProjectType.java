package com.example.inspect.entity;

import lombok.Data;

/**
 * @author soloist
 * @version 1.0
 * @describe 巡检项目分类
 * @date 2021/1/7 11:46
 */
@Data
public class InspectProjectType {
    /**
     * 检查项目分类Id
     */
    private Integer projectTypeId;
    /**
     * 检查项目分类名称
     */
    private String projectTypeName;
}
