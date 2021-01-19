package com.example.inspect.entity;

import lombok.Data;

/**
 * @author soloist
 * @version 1.0
 * @describe 检查类型对应表
 * @date 2021/1/6 15:30
 */
@Data
public class StandardType {
    /**
     * 检查类型id
     */
    private Integer typeId;
    /**
     * 检查类型名称
     */
    private String typeName;
    /**
     * 检查类型描述
     */
    private String typeDescription;

}
