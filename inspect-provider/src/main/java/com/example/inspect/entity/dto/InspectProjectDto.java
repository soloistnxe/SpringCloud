package com.example.inspect.entity.dto;

import lombok.Data;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/1/19 16:16
 */
@Data
public class InspectProjectDto {
    private Integer id;

    private Integer projectTypeId;

    private String projectTypeName;

    private String projectContent;

    private String projectOption;
}
